package com.hb.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenParser {

	private String error;
	private String access_token;
	
	public TokenParser() {
		
	}
	
	public TokenParser(String error, String access_token) {
		super();
		this.error = error;
		this.access_token = access_token;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	@Override
	public String toString() {
		return "TokenParser [error=" + error + ", access_token=" + access_token + "]";
	}
	
	
}
