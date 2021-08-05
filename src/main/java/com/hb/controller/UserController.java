package com.hb.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hb.dao.UserDao;
import com.hb.model.User;
import com.hb.render.CustomMessages;
import com.hb.render.ResponseBuilder;
import com.hb.render.ResponseData;
import com.hb.service.CryptingService;


@RestController
public class UserController {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private CryptingService cryptingService;
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public ResponseEntity<ResponseData> save(@RequestBody @Valid User user, Errors errors) {
		ResponseData responseData = new ResponseData();
		try {
			ResponseEntity<ResponseData> responseEntity = ResponseBuilder.processErrors(errors, responseData); 
			if(responseEntity != null) {
				return responseEntity;
			}
			if(userDao.findByUserName(user.getUserName()).isPresent()) {
				return ResponseBuilder.unprocessableErrorBuilder(responseData, CustomMessages.USERNAME_EXIST);
			}
			if(userDao.findByEmail(user.getEmail()).isPresent()) {
				return ResponseBuilder.unprocessableErrorBuilder(responseData, CustomMessages.EMAIL_EXIST);
			}
			user.setPassword(cryptingService.encrypt(user.getUserName()));
			user = userDao.saveUser(user);			
		} catch(Exception exception) {	
			return ResponseBuilder.internalErrorBuilder(responseData);
		}
		return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.USER_SAVED, user);
	}

	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public ResponseEntity<ResponseData> get(@PathVariable Long id) {
		ResponseData responseData = new ResponseData();
		try {
			Optional<User> user = userDao.findById(id);  
			if(!user.isPresent()) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.USER_NOT_FOUND);
			}	
			return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.SUCCESS, user.get());
		} catch(Exception exception) {
			System.out.println(exception.getMessage());
			return ResponseBuilder.internalErrorBuilder(responseData);
		}
	}

	@RequestMapping(value="/users/{id}", method=RequestMethod.PUT)
	public ResponseEntity<ResponseData> update(@PathVariable Long id, @RequestBody @Valid User user, Errors errors) {
		ResponseData responseData = new ResponseData();
		try {
			if(!userDao.findById(id).isPresent()) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.USER_NOT_FOUND);
			}
			ResponseEntity<ResponseData> responseEntity = ResponseBuilder.processErrors(errors, responseData); 
			if(responseEntity != null) {
				return responseEntity;
			}
			user = userDao.saveUser(user);
		} catch(Exception exception) {
			return ResponseBuilder.internalErrorBuilder(responseData);
		}
		return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.USER_UPDATED, user);
	}

	@RequestMapping(value="/users/{id}/activate", method=RequestMethod.PUT)
	public ResponseEntity<ResponseData> activate(@PathVariable Long id) {
		ResponseData responseData = new ResponseData();
		try {
			Optional<User> user = userDao.findById(id);  
			if(!user.isPresent()) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.USER_NOT_FOUND);
			}	
			user.get().setActiveStatus(true);
			userDao.saveUser(user.get());
			return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.USER_UPDATED, user);
		} catch(Exception exception) {
			return ResponseBuilder.internalErrorBuilder(responseData);
		}		
	}

	@RequestMapping(value="/users/{id}/deactivate", method=RequestMethod.PUT)
	public ResponseEntity<ResponseData> deactivate(@PathVariable Long id) {
		ResponseData responseData = new ResponseData();
		try {
			Optional<User> user = userDao.findById(id);  
			if(!user.isPresent()) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.USER_NOT_FOUND);
			}	
			user.get().setActiveStatus(false);
			userDao.saveUser(user.get());
			return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.USER_UPDATED, user);
		} catch(Exception exception) {
			return ResponseBuilder.internalErrorBuilder(responseData);
		}		
	}

	@GetMapping("/users")
	public ResponseEntity<Map<String, Object>> getAllTutorials(@RequestParam(required = false) String search, @RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "10") int max) {

		try {
			Map<String, Object> response = new HashMap<>();
			Pageable paging = PageRequest.of(offset, max);
			userDao.filterUsers(search, paging, response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
