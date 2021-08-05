package com.hb.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.model.User;

@Service
public class SessionService {

	@Autowired
	private HttpSession httpSession;
	
	public void setSession(User user) {
		httpSession.setAttribute("user", user);
	}
	
	public User getSession() {
		User user = (User) httpSession.getAttribute("user");
		return user;
	}
	
}
