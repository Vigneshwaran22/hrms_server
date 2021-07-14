package com.hb.render;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public class ResponseBuilder {
	
	public static ResponseEntity<ResponseData>  processErrors(Errors errors, ResponseData responseData) {
		ResponseEntity<ResponseData> responseEntity = null;
		if(errors.hasErrors()) {
			List<String> errorList = errors.getAllErrors().stream().map( x -> x.getDefaultMessage()).collect(Collectors.toList());
			responseData.setCode(422);
			responseData.setMessage(CustomMessages.FAILURE);
			responseData.setErrors(errorList);
			responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return responseEntity;
	}
	
	public static ResponseEntity<ResponseData> successWithDataBuilder(ResponseData responseData, String message, Object data) {
		responseData.setMessage(message);
		responseData.setCode(200);
		responseData.setData(data);
		return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
		
	}
	
	public static ResponseEntity<ResponseData> internalErrorBuilder(ResponseData responseData) {
		responseData.setCode(500);
		responseData.setMessage(CustomMessages.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ResponseData>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public static ResponseEntity<ResponseData> notFoundErrorBuilder(ResponseData responseData, String message) {
		responseData.setCode(404);
		responseData.setMessage(message);
		return new ResponseEntity<ResponseData>(responseData, HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<ResponseData> unprocessableErrorBuilder(ResponseData responseData, String message) {
		responseData.setCode(422);
		responseData.setMessage(CustomMessages.FAILURE);
		responseData.setErrors(Stream.of(message).collect(Collectors.toList()));
		return new ResponseEntity<ResponseData>(responseData, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	

}
