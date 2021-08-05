package com.hb.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class CryptingService {

	public String encrypt(String toBeEncrypt) {
		return DigestUtils.md5Hex(toBeEncrypt).toUpperCase(); 
	}
}
