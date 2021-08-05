package com.hb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hb.service.S3Service;
import com.hb.service.ZohoProcessorService;

import net.minidev.json.JSONObject;

@RestController
public class ZohoController {
	
	@Autowired
	private ZohoProcessorService zohoProcessorService;
	
	@Autowired
	private S3Service s3Service;

//	private class ProcessAccountJson {
//		public String code;
//
//		public ProcessAccountJson(String code) {
//			super();
//			this.code = code;
//		}
//	}

	@RequestMapping(value = "/processZohoAccount", method = RequestMethod.POST)
	public void processZohoAccount(@RequestBody JSONObject requestJson) {
		zohoProcessorService.zohoToken(requestJson.get("token").toString());
////		s3Service.createdFolder("Atribs");
//		zohoProcessorService.zohoAttachment("1000.7ed5c1827d58518758dd403ca0035763.b6cd2a7681eeb15230825b985953efe2", "7974916000000008002", "7974916000000008014", "1622386732841100004", "138416795318690190", "Computation sheet - Clearing management(3).xlsx");
	}

}
