package com.hb.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
	
	  @Autowired
	  AmazonS3 amazonS3;

	  private static final String BUCKET_NAME = "myhrmstest";
	  
	  public void createdFolder(String folderName) { 		  
		  ObjectMetadata metadata = new ObjectMetadata();
		    metadata.setContentLength(0);
		    // create empty content
		    InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		    // create a PutObjectRequest passing the folder name suffixed by /
		    PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, folderName + "/", emptyContent, metadata);
		    // send request to S3 to create folder
		    amazonS3.putObject(putObjectRequest);
	  }
	  
	  public void uploadFile(String folderName, String fileName, byte[] file) {
		  
		  ObjectMetadata metadata = new ObjectMetadata();
		    metadata.setContentLength(file.length);
		    // create empty content
		    InputStream fileContent = new ByteArrayInputStream(file);
		    // create a PutObjectRequest passing the folder name suffixed by /
		    PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, folderName + "/" + fileName, fileContent, metadata);
		    // send request to S3 to create folder
		    amazonS3.putObject(putObjectRequest);
	  }

}
