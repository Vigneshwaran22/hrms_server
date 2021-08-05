package com.hb.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountParser {

	private String access_token;
	private String accountId;
	private String messageId;
	private String folderId;
	private String hasAttachment;
	private String attachmentId;
	private String attachmentName;
	
	public AccountParser() {
		
	}

	public AccountParser(String access_token, String accountId, String messageId, String folderId, String hasAttachment,
			String attachmentId, String attachmentName) {
		super();
		this.access_token = access_token;
		this.accountId = accountId;
		this.messageId = messageId;
		this.folderId = folderId;
		this.hasAttachment = hasAttachment;
		this.attachmentId = attachmentId;
		this.attachmentName = attachmentName;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(String hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	@Override
	public String toString() {
		return "AccountParser [access_token=" + access_token + ", accountId=" + accountId + ", messageId=" + messageId
				+ ", folderId=" + folderId + ", hasAttachment=" + hasAttachment + ", attachmentId=" + attachmentId
				+ ", attachmentName=" + attachmentName + "]";
	}

		
}
