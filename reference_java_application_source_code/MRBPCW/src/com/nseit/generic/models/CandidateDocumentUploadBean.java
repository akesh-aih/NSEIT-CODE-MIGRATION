package com.nseit.generic.models;

public class CandidateDocumentUploadBean {

	private String candidateDocPk;
	private String fileName;
	private byte[] file;
	private String flagValue;

	public String getCandidateDocPk() {
		return candidateDocPk;
	}

	public void setCandidateDocPk(String candidateDocPk) {
		this.candidateDocPk = candidateDocPk;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFlagValue() {
		return flagValue;
	}

	public void setFlagValue(String flagValue) {
		this.flagValue = flagValue;
	}

}
