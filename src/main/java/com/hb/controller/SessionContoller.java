package com.hb.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hb.dao.UserDao;
import com.hb.jwt.JwtTokenUtil;
import com.hb.model.User;
import com.hb.render.CustomMessages;
import com.hb.render.ResponseBuilder;
import com.hb.render.ResponseData;
import com.hb.service.CryptingService;
import com.hb.service.SessionService;
import com.hb.validator.Signin;

@RestController
public class SessionContoller {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CryptingService cryptingService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<ResponseData> signin(@RequestBody @Valid Signin signin, Errors errors) {		
		ResponseData responseData = new ResponseData();
		try {
			ResponseEntity<ResponseData> responseEntity = ResponseBuilder.processErrors(errors, responseData); 
			if(responseEntity != null) {
				return responseEntity;
			}
			Optional<User> user = userDao.findByUserName(signin.getUserName());
			if(!user.isPresent()) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.USER_NOT_FOUND);
			}
			if(!user.get().getActiveStatus()) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.USER_INACTIVE);
			}
			if(user.get().getPassword() != cryptingService.encrypt(signin.getPassword())) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.USER_INVALID);
			}
			sessionService.setSession(user.get());
			final String token = jwtTokenUtil.generateToken(user.get());
			responseData.setToken(token);
			return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.USER_LOGIN_SUCCESS, user.get());
		} catch(Exception exception) {	
			return ResponseBuilder.internalErrorBuilder(responseData);
		}		
	}	
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
}
