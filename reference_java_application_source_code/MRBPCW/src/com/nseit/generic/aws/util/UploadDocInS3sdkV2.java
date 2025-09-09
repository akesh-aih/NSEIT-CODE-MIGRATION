package com.nseit.generic.aws.util;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDocBean;
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
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

//Code is based on AWS SDKV2
public class UploadDocInS3sdkV2 {
	private Logger logger = LoggerHome.getLogger(getClass());
	private String BUCKET_NAME = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.BUCKET_NAME).toLowerCase();
	private Region BUCKET_REGION = Region.of(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.BUCKET_REGION).toLowerCase());
	private String CANDIDATE_PDF_NAME;
	private String DOCUMENT_PATH;
	
	public void uploadDocInCandFolderInS3(Users loggedInUser, CandidateBean candidateBean) throws LogicError {
		String CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		String flag = candidateBean.getOcdFlagValue();
		String documentName = candidateBean.getData1FileName();
		String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + "_" + flag + "." + getExtensionByStringHandling(documentName);	
		//String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + "_" + flag + ".pdf" ;
		String DOCUMENT_PATH = candidateBean.getData1().getPath();
		S3Client s3Client = getS3Client();

		if (!(documentName.toLowerCase().endsWith(".jpg") || documentName.toLowerCase().endsWith(".jpeg") 
				|| documentName.toLowerCase().endsWith(".pdf"))) {
			return;
		}
		String CANDIDATE_DOC_NAME1 = loggedInUser.getUsername() + "_" + flag + ".pdf" ;
		String CANDIDATE_DOC_NAME2 = loggedInUser.getUsername() + "_" + flag + ".jpg" ;
		String CANDIDATE_DOC_NAME3 = loggedInUser.getUsername() + "_" + flag + ".jpeg" ;
		
		if(getExtensionByStringHandling(documentName)!=null&& getExtensionByStringHandling(documentName).toLowerCase().equals("pdf")) {
			deleteOtherExtDoc(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME2,DOCUMENT_PATH);
			deleteOtherExtDoc(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME3,DOCUMENT_PATH);
		}else if(getExtensionByStringHandling(documentName)!=null&& getExtensionByStringHandling(documentName).toLowerCase().equals("jpg")) {
			deleteOtherExtDoc(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME1,DOCUMENT_PATH);
			deleteOtherExtDoc(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME3,DOCUMENT_PATH);
		}else if(getExtensionByStringHandling(documentName)!=null && getExtensionByStringHandling(documentName).toLowerCase().equals("jpeg")) {
			deleteOtherExtDoc(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME2,DOCUMENT_PATH);
			deleteOtherExtDoc(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME1,DOCUMENT_PATH);
		}
		saveDocInCandFolderWithinS3Bucket(s3Client, CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME, DOCUMENT_PATH);
		s3Client.close();
	}
	private void deleteOtherExtDoc( String objectNamekey, String objectPath) {
		S3Client s3Client = getS3Client();
		DeleteObjectResponse deleteObjectResponse = null;
		
		try {
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(BUCKET_NAME).key(objectNamekey).build();
			deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);
		} catch (NoSuchKeyException e) {
		} catch (AwsServiceException | SdkClientException e) {
		}finally {
			s3Client.close();
		}
	}
	private String getExtensionByStringHandling(String filename) {
	   String fileExtension = "";
		if(filename != null && ! Strings.isEmpty(filename) && filename.contains(".")) {
			fileExtension = filename.substring(filename.lastIndexOf(".") + 1);
	   }
		return fileExtension;
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
					logger.info("inputStream close failed"+e.getMessage());
				}
			}
		}
		return bytesArray;
	}

	//##############################################################################################################################
	public String getDocMdPathFromCandFolderInS3(Users loggedInUser, CandidateBean candidateBean) throws IOException {
		String CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		String flag = candidateBean.getOcdFlagValue();
		String documentName = candidateBean.getData1FileName();
		String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + "_" + flag + "." + getExtensionByStringHandling(documentName);	
		//String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + "_" + flag + "_" + documentName;
		S3Client s3Client = getS3Client();

		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME).build();
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
			logger.info("IOException occurred while reading doc file for user: "+loggedInUser.getUsername());
			return null;
		} catch (NoSuchAlgorithmException e) {
			logger.info("NoSuchAlgorithmException occurred while reading doc file for user: "+loggedInUser.getUsername());
			return null;
		} finally {
			IOUtils.closeQuietly(responseObjectInputStream);
			s3Client.close();
		}
		return path;
	}

	//##############################################################################################################################
	public DeleteObjectResponse rmDocFromCandFolderInS3(Users loggedInUser, CandidateBean candidateBean) throws IOException {
		String CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		String flag = candidateBean.getOcdFlagValue();
		String documentName = candidateBean.getData1FileName();
		String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + "_" + flag + "." + getExtensionByStringHandling(documentName);	
		S3Client s3Client = getS3Client();
		DeleteObjectResponse deleteObjectResponse = null;
		try {
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME).build();
			deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);
		} catch (NoSuchKeyException e) {
			logger.info("NoSuchKeyException occurred while reading doc file for user: "+loggedInUser.getUsername());
		} catch (AwsServiceException | SdkClientException e) {
			logger.info("SdkClientException occurred while reading doc file for user: "+loggedInUser.getUsername());
		}finally {
			s3Client.close();
		}
		return deleteObjectResponse;
	}

	// ################################################################################################################
	public boolean isDocFilePresentInCandFolderOfS3(Users loggedInUser, CandidateDocBean candidateDocBean) {
		String CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		String flag = candidateDocBean.getOCD_FLAG();
		String documentName = candidateDocBean.getOCD_DOC_FILE_NAME();
		String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + "_" + flag + "." + getExtensionByStringHandling(documentName);
		//String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + "_" + flag + "_" + documentName;
		S3Client s3Client = getS3Client();
		try {
			HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME).build();
			s3Client.headObject(headObjectRequest);
			return true;
		} catch (NoSuchKeyException e) {
			logger.info(e.getMessage()+" No document found in s3 for :"+loggedInUser.getUsername());
			return false;
		} catch (AwsServiceException | SdkClientException e) {
			logger.info(e.getMessage()+" No document found in s3 for :"+loggedInUser.getUsername());
			return false;
		}finally {
			s3Client.close();
		}
	}
	
	public boolean isDocFilePresentInCandFolderOfS3(Users loggedInUser, CandidateBean candidateBean) {
		String CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		String flag = candidateBean.getOcdFlagValue();
		String documentName = candidateBean.getDocumentFileName1();
		
		String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + "_" + flag + "." + getExtensionByStringHandling(documentName);
		S3Client s3Client = getS3Client();
		try {
			HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME).build();
			s3Client.headObject(headObjectRequest);
			return true;
		} catch (NoSuchKeyException e) {
			logger.info(e.getMessage()+" No document found in s3 for :"+loggedInUser.getUsername());
			return false;
		} catch (AwsServiceException | SdkClientException e) {
			logger.info(e.getMessage()+" No document found in s3 for :"+loggedInUser.getUsername());
			return false;
		}finally {
			s3Client.close();
		}
	}
	
	
	public boolean isDocFilePresentInProjectFolderOfS3(String folderName, Users loggedInUser) {
		String CANDIDATE_FOLDER_NAME = folderName;
		String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + ".pdf";
		S3Client s3Client = getS3Client();
		try {
			HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME).build();
			s3Client.headObject(headObjectRequest);
			return true;
		} catch (NoSuchKeyException e) {
			logger.info(e.getMessage()+" No document found in s3 for :"+loggedInUser.getUsername());
			return false;
		} catch (AwsServiceException | SdkClientException e) {
			logger.info(e.getMessage()+" No document found in s3 for :"+loggedInUser.getUsername());
			return false;
		} finally {
			s3Client.close();
		}
	}

	//##############################################################################################################################
	public InputStream getInputStreamOfDocFromCandFolderInS3(Users loggedInUser, CandidateBean candidateBean) throws IOException {
		String CANDIDATE_FOLDER_NAME = loggedInUser.getUsername();
		String flag = candidateBean.getOcdFlagValue();
		String documentName = candidateBean.getDocumentFileName();
		String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + "_" + flag + "." + getExtensionByStringHandling(documentName);	
		S3Client s3Client = getS3Client();
		
		InputStream inputStream = null;
		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME).build();
			ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
			inputStream = objectBytes.asInputStream();
		} catch (AwsServiceException | SdkClientException e) {
			logger.info(e.getMessage()+" No document found in s3 for :"+loggedInUser.getUsername());
		}finally {
			s3Client.close();
		}
		return inputStream;
	}
	//##############################################################################################################################
	public InputStream getDocumentsFromS3(Users loggedInUser, String path) throws IOException {
		String CANDIDATE_FOLDER_NAME = path;
		String CANDIDATE_DOC_NAME = loggedInUser.getUsername() + ".pdf";	
		S3Client s3Client = getS3Client();
		
		InputStream inputStream = null;
		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_FOLDER_NAME + "/" + CANDIDATE_DOC_NAME).build();
			ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
			inputStream = objectBytes.asInputStream();
		} catch (AwsServiceException | SdkClientException e) {
			logger.info(e.getMessage()+" No document found in s3 for :"+loggedInUser.getUsername());
		}finally {
			s3Client.close();
		}
		return inputStream;
	}
	//############################################################################################################################## JSON

	public byte[] getCandidateJsonFromS3(Users loggedInUser, CandidateBean candidateBean) {
		S3Client s3Client = getS3Client();		
		String CANDIDATE_JSON_NAME = loggedInUser.getUsername() + "/" + loggedInUser.getUsername() + ".json";
		byte[] byteArray = {};
		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_JSON_NAME).build();
			ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
			byteArray = objectBytes.asByteArray();;
		} catch (AwsServiceException | SdkClientException e) {
			logger.info(e.getMessage()+" No Json found in s3 for :"+loggedInUser.getUsername());
		}finally {
			s3Client.close();
		}
		return byteArray;
		
	}
	
	public String saveJsonInS3(Users loggedInUser, CandidateBean candidateBean, byte[] bytesToWrite) throws LogicError, JsonProcessingException {
		S3Client s3Client = getS3Client();
		String CANDIDATE_JSON_NAME = loggedInUser.getUsername() + "/" + loggedInUser.getUsername() + ".json";
//		ObjectMetadata omd = new ObjectMetadata();
//		omd.setContentLength(bytesToWrite.length);
//		transferManager.upload(bucketName, filename, new ByteArrayInputStream(bytesToWrite), omd);
		try {
			Map<String, String> metadata = new HashMap<>();
			metadata.put("x-amz-meta-myVal", "json");
			PutObjectRequest putOb = PutObjectRequest.builder().bucket(BUCKET_NAME).key(CANDIDATE_JSON_NAME).metadata(metadata).build();
			PutObjectResponse response = s3Client.putObject(putOb, RequestBody.fromBytes(bytesToWrite));
			return response.eTag();
		} catch (S3Exception e) {
			throw new LogicError("Error ocurred while saving json. Bucket with name." + BUCKET_NAME + " json path:" + CANDIDATE_JSON_NAME);
		}
	}
	//##############################################################################################################################
	private S3Client getS3Client() {
		/*
		 * C:\Users\{user}\.aws\credentials
		 * ---------------------------------------------
		 * Content of credentials 
		 * [default]
		 * aws_access_key_id={value from aws console} 
		 * aws_secret_access_key={value from aws console}
		 * ----------------------------------------------
		 * to access in code
		 * -----------------------------------------------
		 * ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
		 * S3Client s3 = S3Client.builder().region(BUCKET_REGION).credentialsProvider(credentialsProvider).build();
		 * 
		 */
		String aws_access_key_id = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AWS_ACCESS_KEY_ID);
		String aws_secret_access_key = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.AWS_SECRET_ACCESS_KEY);
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create( aws_access_key_id, aws_secret_access_key);
		S3Client s3 = S3Client.builder().region(BUCKET_REGION).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
		return s3;
	}

	public String saveApplicationPdfInS3Bucket(Users loggedInUser, byte[] byteData) throws LogicError {
		DOCUMENT_PATH = "Bulk_PDF"+"/"+ loggedInUser.getUsername() + ".pdf";
		try {
			S3Client s3Client = getS3Client();
			Map<String, String> metadata = new HashMap<>();
			metadata.put("x-amz-meta-myVal", "pdf");
			PutObjectRequest putOb = PutObjectRequest.builder().bucket(BUCKET_NAME).key(DOCUMENT_PATH).metadata(metadata).build();
			PutObjectResponse response = s3Client.putObject(putOb, RequestBody.fromBytes(byteData));
			return response.eTag();
		} catch (S3Exception e) {
			throw new LogicError("Error ocurred while saving document. Bucket with name." + BUCKET_NAME + " doc path:" + DOCUMENT_PATH);
		}
	}
	
}
