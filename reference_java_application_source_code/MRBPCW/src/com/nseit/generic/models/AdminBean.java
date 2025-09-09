package com.nseit.generic.models;

import java.io.File;

public class AdminBean {
	private File attachedQbank;
	private String qbankPassword;
	private String fileNameForUpload;
	private String fileUploaded;
	
	
	
	
	
	public File getAttachedQbank() {
		return attachedQbank;
	}

	public void setAttachedQbank(File attachedQbank) {
		this.attachedQbank = attachedQbank;
	}

	public String getQbankPassword() {
		return qbankPassword;
	}

	public void setQbankPassword(String qbankPassword) {
		this.qbankPassword = qbankPassword;
	}

	public String getFileNameForUpload() {
		return fileNameForUpload;
	}

	public void setFileNameForUpload(String fileNameForUpload) {
		this.fileNameForUpload = fileNameForUpload;
	}

	public void setFileUploaded(String fileUploaded) {
		this.fileUploaded = fileUploaded;
	}

	public String getFileUploaded() {
		return fileUploaded;
	}

}
