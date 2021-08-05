package com.hb.service;

public class GenericService {

	public String generateOrganizationCode(String name) {
		String[] strArr = name.split(" ");
		String code = "HB";
		if(strArr.length > 1) {
			for(String str : strArr) {
				code += str.charAt(0);
			}
		} else {
			if(name.length() > 2) {
				code = name.substring(0, 2);
			} else {
				code = name;
			}
		}
		
		return code;
	}
	
}
