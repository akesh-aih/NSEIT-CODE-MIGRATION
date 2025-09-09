package com.nseit.test.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Slf4j
public class S3PhotoTest {
	private String BUCKET_NAME = "NCFEfordemo".toLowerCase();
	private Region BUCKET_REGION = Region.of("us-west-2");
	private String CANDIDATE_FOLDER_NAME = "REFH0000089";
	private String CANDIDATE_PHOTO_NAME = "REFH0000089_sign.jpg";
	private String CANDIDATE_PDF_NAME;
	private String DOCUMENT_PATH;
	private String LOGO_PATH;
	
	public static void main(String[] args) throws IOException {
		S3PhotoTest s3PhotoTest = new S3PhotoTest();
//		System.out.println(s3PhotoTest.isPhotoSignaturePresentInS3());
		System.out.println(s3PhotoTest.rmPhotoSignFromCandFolderInS3());
		
	}
	
	public boolean isPhotoSignaturePresentInS3() throws IOException {
		long startTime = System.nanoTime();
		S3Client s3Client = getS3Client();
		//CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		//CANDIDATE_PHOTO_NAME = loggedInUser.getUsername() + suffix;

		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_PHOTO_NAME).build();
			ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
			byte[] data = objectBytes.asByteArray();
			InputStream is = new ByteArrayInputStream(data);
			SdkBytes sourceBytes = SdkBytes.fromInputStream(is);
			ImageIO.read(sourceBytes.asInputStream());
			System.out.println("Candidate photo is present in S3 :"+CANDIDATE_PHOTO_NAME);
			return true;
		} catch (AwsServiceException | SdkClientException e) {
			e.printStackTrace();
			System.out.println("Candidate photo is not present in S3 :"+CANDIDATE_PHOTO_NAME);
			return false;
		} finally {
			s3Client.close();
			long endTime = System.nanoTime();
			System.out.println("Photo/Sign exists check Time in miliseconds : "+(endTime-startTime)/1e+6+ " in Seconds : "+(endTime-startTime)/1e+9);
			
		}
	}
	
	private S3Client getS3Client() {
		long startTime = System.nanoTime();


		String aws_access_key_id = "AKIAVW5Q54TX7IDKVGUY";
		String aws_secret_access_key = "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";
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create( aws_access_key_id, aws_secret_access_key);
		S3Client s3 = S3Client.builder().region(BUCKET_REGION).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
		System.out.println("S3 client build : "+s3.toString());
		long endTime = System.nanoTime();
		System.out.println("S3 client Creation Execution Time in miliseconds : "+(endTime-startTime)/1e+6+ " in Seconds : "+(endTime-startTime)/1e+9);
		
		return s3;
	}
	
	
	public DeleteObjectResponse rmPhotoSignFromCandFolderInS3() throws IOException {
		long startTime = System.nanoTime();
		S3Client s3Client = getS3Client();
//		CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
//		CANDIDATE_PHOTO_NAME = loggedInUser.getUsername() + suffix;
//		if ("_photo.jpg".equals(suffix)) {
//			DOCUMENT_PATH = candidateBean.getAttachmentPhoto();
//		} else {
//			DOCUMENT_PATH = candidateBean.getAttachmentSignature();
//		}

		DeleteObjectResponse deleteObjectResponse = null;
		try {
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_PHOTO_NAME).build();
			deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);
			log.info("Candidate photo has been removed from S3 :"+CANDIDATE_PHOTO_NAME);
		} catch (NoSuchKeyException e) {
			log.error(e.getLocalizedMessage());
		} catch (AwsServiceException | SdkClientException e) {
			log.error(e.getLocalizedMessage());
		}finally {
			s3Client.close();
		}
		long endTime = System.nanoTime();
		log.info("Photo/Sign from S3 Deletion Time in miliseconds : "+(endTime-startTime)/1e+6+ " in Seconds : "+(endTime-startTime)/1e+9);
		
		return deleteObjectResponse;
	}
	
	
}
