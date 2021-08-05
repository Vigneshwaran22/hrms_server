package com.hb.service;

import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.parser.AccountParser;

import org.json.JSONObject;

@Service
public class ZohoProcessorService {
	
	@Autowired
	private S3Service s3Service;

	private static final String CLIENT_ID = "1000.DAGZND5TXU913OAV6BN8EE2UDBLN5Q";
	private static final String SECRET_ID = "9932bc5ec04769e9893c83a5a40d73a2fe7a47f43c";
	private static final String REDIRECT_URL = "https://d1as2ic5mszh41.cloudfront.net/email-config";
	private static final String SCOPE = "ZohoMail.messages.READ,ZohoMail.accounts.READ";

	private CloseableHttpClient httpClinet = HttpClients.createDefault();
	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

	private ResponseHandler<String> responseHandler = response -> {
		
		int status = response.getStatusLine().getStatusCode();
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();	
			return entity != null ? EntityUtils.toString(entity) : null;
		} else {
			throw new ClientProtocolException("Unexpected response status: " + status);
		}
	};
	
private ResponseHandler<byte[]> imgresponseHandler = response -> {
		
		int status = response.getStatusLine().getStatusCode();
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();	
			return entity != null ? EntityUtils.toByteArray(entity) : null;
		} else {
			throw new ClientProtocolException("Unexpected response status: " + status);
		}
	};

	public void zohoProcessor(String code) {

	}

	// get the auth token through the post api 
	// https://accounts.zoho.com/oauth/v2/token
	public void zohoToken(String code) {
		String URL = "https://accounts.zoho.com/oauth/v2/token?code=" + code
				+ "&grant_type=authorization_code&client_id=" + CLIENT_ID + "&client_secret=" + SECRET_ID
				+ "&redirect_uri=" + REDIRECT_URL + "&SCOPE=" + SCOPE;
		
		HttpPost httpPost = new HttpPost(URL);
		try {
			String responseBody = httpClinet.execute(httpPost, responseHandler);
			JSONObject jsonObject = new JSONObject(responseBody);
			this.zohoAccount(jsonObject.get("access_token").toString());
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	// get the linked account with users through the get api
	// http://mail.zoho.com/api/accounts
	// with barear token
	public void zohoAccount(String token) {		
		String URL = "http://mail.zoho.com/api/accounts";
		HttpGet httpGet = new HttpGet(URL);
		httpGet.setHeader(org.apache.http.HttpHeaders.AUTHORIZATION, "Bearer " + token);
		try {
			String responseBody = httpClinet.execute(httpGet, responseHandler);
			JSONObject jsonObject = new JSONObject(responseBody);			
			List<AccountParser> accountParsers = mapper.readValue(jsonObject.get("data").toString(), new TypeReference<List<AccountParser> >() {});
			for(AccountParser account : accountParsers) {
				System.out.println(account.getAccountId());
				this.zohoMessages(token, account.getAccountId());
			}
		} catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
					

	}

	// get the all messages based on the accountId through the get api
	// http://mail.zoho.com/api/accounts/7974916000000008002/messages/view
	// with barear token
	public void zohoMessages(String token, String accountId) {
		String URL = "http://mail.zoho.com/api/accounts/"+ accountId +"/messages/view";
		HttpGet httpGet = new HttpGet(URL);
		httpGet.setHeader(org.apache.http.HttpHeaders.AUTHORIZATION, "Bearer " + token);
		try {
			String responseBody = httpClinet.execute(httpGet, responseHandler);
			JSONObject jsonObject = new JSONObject(responseBody);			
			List<AccountParser> accountParsers = mapper.readValue(jsonObject.get("data").toString(), new TypeReference<List<AccountParser> >() {});
			for(AccountParser account : accountParsers) {
				int num = Integer.parseInt(account.getHasAttachment());
				if(num > 0) {
					this.zohoAttachmentInfo(token, accountId, account.getFolderId(), account.getMessageId());
				}
			}			
		} catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	// get the all attachment information based on the messageId through the get api
	// http://mail.zoho.com/api/accounts/7974916000000008002/folders/7974916000000008014/messages/1624091635084100004/attachmentinfo
	// with barear token
	public void zohoAttachmentInfo(String token,String accountId, String folderId, String messageId) {
		String URL = "http://mail.zoho.com/api/accounts/" + accountId + "/folders/" + folderId + "/messages/" + messageId + "/attachmentinfo";
		HttpGet httpGet = new HttpGet(URL);
		httpGet.setHeader(org.apache.http.HttpHeaders.AUTHORIZATION,  "Bearer " + token);
		try {
			String responseBody = httpClinet.execute(httpGet, responseHandler);
			JSONObject jsonObject = new JSONObject(responseBody);
			JSONObject data = (JSONObject) jsonObject.get("data");
			List<AccountParser> accountParsers = mapper.readValue(data.get("attachments").toString(), new TypeReference<List<AccountParser> >() {});
			for(AccountParser account : accountParsers) {
				System.out.println(account.getAttachmentId());
				System.out.println(account.getAttachmentName());
				this.zohoAttachment(token, accountId, folderId, messageId, account.getAttachmentId(), account.getAttachmentName());
			}
		} catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}


	// get the attachment  based on the attachmentId through the get api
	// https://mail.zoho.com/api/accounts/7974916000000008002/folders/7974916000000008014/messages/1624091635084100004/attachments/138433844327190070
	// with barear token
	public void zohoAttachment(String token, String accountId, String folderId, String messageId, String attachmentId, String attachmentName) {
		String URL = "https://mail.zoho.com/api/accounts/" + accountId + "/folders/" + folderId + "/messages/" + messageId + "/attachments/" + attachmentId;
		HttpGet httpGet = new HttpGet(URL);
		httpGet.setHeader(org.apache.http.HttpHeaders.AUTHORIZATION,  "Bearer " + token);
		try {
			byte[] responseBody = httpClinet.execute(httpGet, imgresponseHandler);
			s3Service.uploadFile("HB", attachmentName, responseBody);
		} catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

}
