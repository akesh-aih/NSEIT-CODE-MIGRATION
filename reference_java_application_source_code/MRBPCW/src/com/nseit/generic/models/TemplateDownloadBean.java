package com.nseit.generic.models;

import java.io.InputStream;

public class TemplateDownloadBean {
	
	private String ddChallanTemplateStatus;
	private String templateDownloadType;
	private byte[] templateDocumentByte;
	private InputStream documentInputStream;
	private String templateFileName;
	private String templateUserCredentialsStatus;
	private String templateUploadStatus;
	private String successMessage;
	private String successMessageFlag;
	
	public TemplateDownloadBean() {
	}
	
	
	
	

	public String getDdChallanTemplateStatus() {
		return ddChallanTemplateStatus;
	}





	public void setDdChallanTemplateStatus(String ddChallanTemplateStatus) {
		this.ddChallanTemplateStatus = ddChallanTemplateStatus;
	}





	public String getTemplateUserCredentialsStatus() {
		return templateUserCredentialsStatus;
	}





	public void setTemplateUserCredentialsStatus(
			String templateUserCredentialsStatus) {
		this.templateUserCredentialsStatus = templateUserCredentialsStatus;
	}





	public String getTemplateDownloadType() {
		return templateDownloadType;
	}

	public void setTemplateDownloadType(String templateDownloadType) {
		this.templateDownloadType = templateDownloadType;
	}

	public byte[] getTemplateDocumentByte() {
		return templateDocumentByte;
	}

	public void setTemplateDocumentByte(byte[] templateDocumentByte) {
		this.templateDocumentByte = templateDocumentByte;
	}

	public InputStream getDocumentInputStream() {
		return documentInputStream;
	}

	public void setDocumentInputStream(InputStream documentInputStream) {
		this.documentInputStream = documentInputStream;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getTemplateUploadStatus() {
		return templateUploadStatus;
	}

	public void setTemplateUploadStatus(String templateUploadStatus) {
		this.templateUploadStatus = templateUploadStatus;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getSuccessMessageFlag() {
		return successMessageFlag;
	}

	public void setSuccessMessageFlag(String successMessageFlag) {
		this.successMessageFlag = successMessageFlag;
	}

}
