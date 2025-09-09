package com.nseit.generic.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUnzipUtility {	
		
	public void doZip(String folderToAdd, String destZipFile, String password) throws ZipException{
		ZipFile zipFile = new ZipFile(destZipFile);		
				
		// Initiate Zip Parameters which define various properties such
		// as compression method, etc.
		ZipParameters parameters = new ZipParameters();
		
		// set compression method to store compression
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		
		// Set the compression level
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		
		// Set the encryption flag to true
		// If this is set to false, then the rest of encryption properties are ignored
		parameters.setEncryptFiles(true);
		
		// Set the encryption method to AES Zip Encryption
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		
		// Set AES Key strength. Key strengths available for AES encryption are:
		// AES_STRENGTH_128 - For both encryption and decryption
		// AES_STRENGTH_192 - For decryption only
		// AES_STRENGTH_256 - For both encryption and decryption
		// Key strength 192 cannot be used for encryption. But if a zip file already has a
		// file encrypted with key strength of 192, then Zip4j can decrypt this file
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		
		// Set password
		parameters.setPassword(password);
				
		System.out.println("Compressing folder - " + folderToAdd);
		// Add folder to the zip file		
		zipFile.addFolder(folderToAdd, parameters);		
		System.out.println("Result zip file location - " + destZipFile);
		
	}
	
	public boolean extractAll(String destPath, String sourceZipFile) throws ZipException{
		boolean flag=false;
		ZipFile zipFile = new ZipFile(sourceZipFile);
		
		System.out.println("Extracting: " + sourceZipFile);
		
		// Extracts all files to the path specified		
		//zipFile.setPassword(password);	
		zipFile.extractAll(destPath);
		flag=true;
		System.out.println("Created folder at: " + destPath);
				
		return flag;	
	}
	
	/*public void validate(String destPath, String sourceZipFile) throws ZipException{
		extractAll(destPath, sourceZipFile);
		ZipFile zipFile = new ZipFile(destPath);
		
		List fileHeaderList;		
		fileHeaderList = zipFile.getFileHeaders();		
				
		for (int i = 0; i < fileHeaderList.size(); i++) {
			FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
			// FileHeader contains all the properties of the file
			System.out.println("File Details for: " + fileHeader.getFileName());
			System.out.println("Name: " + fileHeader.getFileName());
			System.out.println("File Length: " + fileHeader.getFileNameLength());
			System.out.println("Compressed Size: " + fileHeader.getCompressedSize());
			System.out.println("Uncompressed Size: " + fileHeader.getUncompressedSize());
			System.out.println("CRC: " + fileHeader.getCrc32());					
		}		
	}*/

}

