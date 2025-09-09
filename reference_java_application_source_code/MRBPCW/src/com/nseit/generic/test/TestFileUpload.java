package com.nseit.generic.test;

//import com.nseit.generic.upload.util.UploadPhotoInS3sdkV2;
import com.nseit.generic.util.LogicError;

public class TestFileUpload {
	public static void main(String[] args) throws LogicError, Exception {
//		UploadPhotoInS3sdkV2 uploadDocInS3UsingSDKv2 = new UploadPhotoInS3sdkV2();
//		uploadDocInS3UsingSDKv2.uploadDocInCandFolderInS3();
		//System.out.println(uploadDocInS3UsingSDKv2.getDocFromCandFolderInS3());
//		uploadDocInS3UsingSDKv2.rmDocFromCandFolderInS3();
//		uploadDocInS3UsingSDKv2.getImage();
		
		String fileName = "xyz.pdf";
		System.out.println(fileName.split("\\.")[1]);
	}
}
