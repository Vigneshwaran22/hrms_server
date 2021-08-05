package com.hb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {

	private static final String ACCESS_KEY = "AKIAXG3BJ5OYHTWZ4MJ4";
	private static final String SECRET_KEY = "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";
	private static final String REGION = "ap-south-1";

	@Bean
	public AmazonS3 s3() {
		System.out.println("CREDENTIAL SETUP PROCESSING");
		AWSCredentials awsCredential = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		return AmazonS3ClientBuilder.standard().withRegion(REGION)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredential)).build();
	}
	
}
