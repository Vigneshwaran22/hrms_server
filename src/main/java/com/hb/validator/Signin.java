package com.hb.validator;

import javax.validation.constraints.NotNull;

public class Signin {

	@NotNull(message = "User Name must be required...")
	private String userName;
	
	@NotNull(message = "Password must be required...")
	private String password;

	public Signin() {}
	
	public Signin(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Signin [userName=" + userName + ", password=" + password + "]";
	}	
	
}
