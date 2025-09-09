package com.nseit.generic.aws.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.LogicError;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

//Code is based on AWS SDKV2
public class UploadPhotoSignInS3sdkV2 {
	private Logger logger = LoggerHome.getLogger(getClass());
	private String BUCKET_NAME = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.BUCKET_NAME).toLowerCase();
	private Region BUCKET_REGION = Region.of(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.BUCKET_REGION).toLowerCase());
	private String CANDIDATE_FOLDER_NAME;
	private String CANDIDATE_PHOTO_NAME;
	private String CANDIDATE_PDF_NAME;
	private String DOCUMENT_PATH;
	private String LOGO_PATH;
	
	public void uploadPhotoSignInCandFolderInS3(Users loggedInUser, CandidateBean candidateBean, String suffix) throws LogicError {
		CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		CANDIDATE_PHOTO_NAME = loggedInUser.getUsername() + suffix;
		if ("_photo.jpg".equals(suffix)) {
			DOCUMENT_PATH = candidateBean.getAttachmentPhoto().getPath();
		} else {
			DOCUMENT_PATH = candidateBean.getAttachmentSignature().getPath();
		}
		
		S3Client s3Client = getS3Client();
		if (!isBucketExists(s3Client)) {
			createBucket(s3Client);
		}

		saveDocInCandFolderWithinS3Bucket(s3Client, CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_PHOTO_NAME, DOCUMENT_PATH);
		s3Client.close();
	}

	private void createBucket(S3Client s3Client) throws LogicError {
		
		try {
			CreateBucketConfiguration bucketConfiguration = CreateBucketConfiguration.builder().locationConstraint(BUCKET_REGION.id()).build();
			CreateBucketRequest bucketRequest = CreateBucketRequest.builder().bucket(BUCKET_NAME).createBucketConfiguration(bucketConfiguration).build();

			s3Client.createBucket(bucketRequest);
			s3Client.waiter().waitUntilBucketExists(HeadBucketRequest.builder().bucket(BUCKET_NAME).build());
		} catch (S3Exception e) {
			throw new LogicError("Error ocurred while creating bucket with name." + BUCKET_NAME + " in the Region:" + BUCKET_REGION.id());
		}
	}

	private String saveDocInCandFolderWithinS3Bucket(S3Client s3Client, String objectNamekey, String objectPath) throws LogicError {
		try {
			Map<String, String> metadata = new HashMap<>();
			metadata.put("x-amz-meta-myVal", "test");
			PutObjectRequest putOb = PutObjectRequest.builder().bucket(BUCKET_NAME).key(objectNamekey).metadata(metadata).build();
			PutObjectResponse response = s3Client.putObject(putOb, RequestBody.fromBytes(getObjectFile(objectPath)));
			return response.eTag();
		} catch (S3Exception e) {
			throw new LogicError("Error ocurred while saving document. Bucket with name." + BUCKET_NAME + " doc path:" + objectPath);
		}
	}

	private byte[] getObjectFile(String filePath) throws LogicError {
		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		try {
			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);
		} catch (IOException e) {
			throw new LogicError("Error while getting object path." + filePath);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					logger.info(e.getMessage()+" IOException occurred");
				}
			}
		}
		return bytesArray;
	}

	private boolean isBucketExists(S3Client s3Client) {
		HeadBucketRequest headBucketRequest = HeadBucketRequest.builder().bucket(BUCKET_NAME).build();
		try {
			s3Client.headBucket(headBucketRequest);
			return true;
		} catch (NoSuchBucketException e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// ##############################################################################################################################
	public String getPhotoSignMdPathFromCandFolderInS3(Users loggedInUser, CandidateBean candidateBean, String suffix) throws IOException {
		S3Client s3Client = getS3Client();
		CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		CANDIDATE_PHOTO_NAME = loggedInUser.getUsername() + suffix;
		if ("_photo.jpg".equals(suffix)) {
			DOCUMENT_PATH = candidateBean.getAttachmentPhoto().getPath();
		} else {
			DOCUMENT_PATH = candidateBean.getAttachmentSignature().getPath();
		}

		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_PHOTO_NAME).build();
		ResponseInputStream<GetObjectResponse> responseObjectInputStream = s3Client.getObject(getObjectRequest);
		String path = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] responseObjectInByteArray = IOUtils.toByteArray(responseObjectInputStream);
			md.update(responseObjectInByteArray);
			byte[] mdbytes = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			path = sb.toString();
		} catch (final IOException e) {
			logger.error(e.getMessage());
			return null;
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} finally {
			IOUtils.closeQuietly(responseObjectInputStream);
			s3Client.close();
		}
		return path;
	}

	// ##############################################################################################################################
	public DeleteObjectResponse rmPhotoSignFromCandFolderInS3(Users loggedInUser, CandidateBean candidateBean, String suffix) throws IOException {
		S3Client s3Client = getS3Client();
		CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		CANDIDATE_PHOTO_NAME = loggedInUser.getUsername() + suffix;
		if ("_photo.jpg".equals(suffix)) {
			DOCUMENT_PATH = candidateBean.getAttachmentPhoto().getPath();
		} else {
			DOCUMENT_PATH = candidateBean.getAttachmentSignature().getPath();
		}

		DeleteObjectResponse deleteObjectResponse = null;
		try {
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_PHOTO_NAME).build();
			deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);
		} catch (NoSuchKeyException e) {
			logger.info("NoSuchKeyException occurred for s3" + e.getMessage());
		} catch (AwsServiceException | SdkClientException e) {
			logger.info("SdkClientException / AwsServiceException occurred for s3" + e.getMessage());
		}finally {
			s3Client.close();
		}
		return deleteObjectResponse;
	}

	// ##############################################################################################################################
	//
	public boolean isPhotoSignaturePresentInS3(Users loggedInUser, String suffix) throws IOException {
		S3Client s3Client = getS3Client();
		CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		CANDIDATE_PHOTO_NAME = loggedInUser.getUsername() + suffix;

		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_PHOTO_NAME).build();
			ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
			byte[] data = objectBytes.asByteArray();
			InputStream is = new ByteArrayInputStream(data);
			SdkBytes sourceBytes = SdkBytes.fromInputStream(is);
			ImageIO.read(sourceBytes.asInputStream());
			return true;
		} catch (AwsServiceException | SdkClientException e) {
			return false;
		} finally {
			s3Client.close();
		}
	}
	
	// ##############################################################################################################################
	public BufferedImage getPhotoSignatureBufferedImageFromS3(Users loggedInUser, String suffix) throws IOException {
		S3Client s3Client = getS3Client();
		CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		CANDIDATE_PHOTO_NAME = loggedInUser.getUsername() + suffix;

		BufferedImage bufferedImage = null;
		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_PHOTO_NAME).build();
			ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
			byte[] data = objectBytes.asByteArray();
			InputStream is = new ByteArrayInputStream(data);
			SdkBytes sourceBytes = SdkBytes.fromInputStream(is);
			bufferedImage = ImageIO.read(sourceBytes.asInputStream());
		} catch (AwsServiceException | SdkClientException e) {
			logger.info("No Image found in S3");
		} finally {
			s3Client.close();
		}
		return bufferedImage;
	}

	// ##############################################################################################################################
	public byte[] getByteArrayOfPhotoSignFromCandFolderInS3(Users loggedInUser, String suffix) throws IOException {
		S3Client s3Client = getS3Client();
		CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		CANDIDATE_PHOTO_NAME = loggedInUser.getUsername() + suffix;

		byte[] data = null;
		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_PHOTO_NAME).build();
			ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
			data = objectBytes.asByteArray();
		} catch (AwsServiceException | SdkClientException e) {
			logger.info("No Image found in S3");
		}finally {
			s3Client.close();
		}
		return data;
	}
	
	//##############################################################################################################################
	private S3Client getS3Client() {
		String aws_access_key_id = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AWS_ACCESS_KEY_ID);
		String aws_secret_access_key = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AWS_SECRET_ACCESS_KEY);
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create( aws_access_key_id, aws_secret_access_key);
		S3Client s3 = S3Client.builder().region(BUCKET_REGION).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
		return s3;
	}

	public byte[] getCandidateDocFromCandFolderInS3(Users users, String suffix,String documentFileName) {
		S3Client s3Client = getS3Client();
		CANDIDATE_FOLDER_NAME = users.getUsername();
		//CANDIDATE_PDF_NAME = suffix+".pdf";
		CANDIDATE_PDF_NAME = suffix+ "." + getExtensionByStringHandling(documentFileName);
		byte[] data = null;
		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_PDF_NAME).build();
			ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
			data = objectBytes.asByteArray();
		} catch (AwsServiceException | SdkClientException e) {
			logger.info("No Doc found in S3");
		}finally {
			s3Client.close();
		}
		return data;
	}

	public BufferedImage getLogoFromBufferedImageFromS3(String logoFileName) throws IOException{
		S3Client s3Client = getS3Client();
		CANDIDATE_FOLDER_NAME = "logo";
		LOGO_PATH = logoFileName;

		BufferedImage bufferedImage = null;
		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + LOGO_PATH).build();
			ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
			byte[] data = objectBytes.asByteArray();
			InputStream is = new ByteArrayInputStream(data);
			SdkBytes sourceBytes = SdkBytes.fromInputStream(is);
			bufferedImage = ImageIO.read(sourceBytes.asInputStream());
		} catch (AwsServiceException | SdkClientException e) {
			logger.info("No Logo found in S3");
		} finally {
			s3Client.close();
		}
		return bufferedImage;
	}
	
	private String getExtensionByStringHandling(String filename) {
		   String fileExtension = "";
			if(filename != null && ! Strings.isEmpty(filename) && filename.contains(".")) {
				fileExtension = filename.substring(filename.lastIndexOf(".") + 1);
		   }
			return fileExtension;
		}
}
