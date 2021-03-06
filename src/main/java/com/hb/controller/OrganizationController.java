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

import com.hb.dao.OrganizationDao;
import com.hb.model.Organization;
import com.hb.render.CustomMessages;
import com.hb.render.ResponseBuilder;
import com.hb.render.ResponseData;
import com.hb.service.S3Service;

@RestController
public class OrganizationController {

	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private S3Service s3Service;

	//	@RequestMapping(value="/organizations", method=RequestMethod.GET)
	//	public 

	@RequestMapping(value="/organizations", method=RequestMethod.POST)
	public ResponseEntity<ResponseData> save(@RequestBody @Valid Organization organization, Errors errors) {
		ResponseData responseData = new ResponseData();
		try {
			ResponseEntity<ResponseData> responseEntity = ResponseBuilder.processErrors(errors, responseData); 
			if(responseEntity != null) {
				return responseEntity;
			}
			if(organizationDao.findByCode(organization.getCode()).isPresent()) {
				return ResponseBuilder.unprocessableErrorBuilder(responseData, CustomMessages.CODE_EXIST);
			}
			organization = organizationDao.saveOrganization(organization);
			s3Service.createdFolder(organization.getCode());
		} catch(Exception exception) {	
			return ResponseBuilder.internalErrorBuilder(responseData);
		}
		return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.DEPARTMENT_SAVED, organization);
	}

	@RequestMapping(value="/organizations/{id}", method=RequestMethod.GET)
	public ResponseEntity<ResponseData> get(@PathVariable Long id) {
		ResponseData responseData = new ResponseData();
		try {
			Optional<Organization> organization = organizationDao.findById(id);  
			if(!organization.isPresent()) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.DEPARTMENT_NOT_FOUND);
			}	
			return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.SUCCESS, organization.get());
		} catch(Exception exception) {
			System.out.println(exception.getMessage());
			return ResponseBuilder.internalErrorBuilder(responseData);
		}
	}

	@RequestMapping(value="/organizations/{id}", method=RequestMethod.PUT)
	public ResponseEntity<ResponseData> update(@PathVariable Long id, @RequestBody @Valid Organization organization, Errors errors) {
		System.out.println("sdfsdfsdfsdfdsfsdf");
		ResponseData responseData = new ResponseData();
		try {
			if(!organizationDao.findById(id).isPresent()) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.DEPARTMENT_NOT_FOUND);
			}
			ResponseEntity<ResponseData> responseEntity = ResponseBuilder.processErrors(errors, responseData); 
			if(responseEntity != null) {
				return responseEntity;
			}
			organization = organizationDao.saveOrganization(organization);
		} catch(Exception exception) {
			return ResponseBuilder.internalErrorBuilder(responseData);
		}
		return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.DEPARTMENT_UPDATED, organization);
	}

	@RequestMapping(value="/organizations/{id}/activate", method=RequestMethod.PUT)
	public ResponseEntity<ResponseData> activate(@PathVariable Long id) {
		ResponseData responseData = new ResponseData();
		try {
			Optional<Organization> organization = organizationDao.findById(id);  
			if(!organization.isPresent()) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.DEPARTMENT_NOT_FOUND);
			}	
			organization.get().setActiveStatus(true);
			organizationDao.saveOrganization(organization.get());
			return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.DEPARTMENT_UPDATED, organization);
		} catch(Exception exception) {
			return ResponseBuilder.internalErrorBuilder(responseData);
		}		
	}

	@RequestMapping(value="/organizations/{id}/deactivate", method=RequestMethod.PUT)
	public ResponseEntity<ResponseData> deactivate(@PathVariable Long id) {
		ResponseData responseData = new ResponseData();
		try {
			Optional<Organization> organization = organizationDao.findById(id);  
			if(!organization.isPresent()) {
				return ResponseBuilder.notFoundErrorBuilder(responseData, CustomMessages.DEPARTMENT_NOT_FOUND);
			}	
			organization.get().setActiveStatus(false);
			organizationDao.saveOrganization(organization.get());
			return ResponseBuilder.successWithDataBuilder(responseData, CustomMessages.DEPARTMENT_UPDATED, organization);
		} catch(Exception exception) {
			return ResponseBuilder.internalErrorBuilder(responseData);
		}		
	}

	@GetMapping("/organizations")
	public ResponseEntity<Map<String, Object>> getAllTutorials(@RequestParam(required = false) String search, @RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "10") int max) {

		try {
			Map<String, Object> response = new HashMap<>();
			Pageable paging = PageRequest.of(offset, max);
			organizationDao.filterOrganizations(search, paging, response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
