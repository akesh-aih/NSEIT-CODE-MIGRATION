package com.nseit.otbs.model;

import com.itextpdf.text.pdf.codec.Base64.InputStream;

public class EnrollmentDetailsBean {
	
	private String enrollmentPK;
	private int batch_FK;
	private int batchAttempt;
	private long userFK;
	private String languageFK;
	private int stage;
	

	public String getEnrollmentPK() {
		return enrollmentPK;
	}
	public void setEnrollmentPK(String enrollmentPK) {
		this.enrollmentPK = enrollmentPK;
	}
	public int getBatch_FK() {
		return batch_FK;
	}
	public void setBatch_FK(int batchFK) {
		batch_FK = batchFK;
	}
	public int getBatchAttempt() {
		return batchAttempt;
	}
	public void setBatchAttempt(int batchAttempt) {
		this.batchAttempt = batchAttempt;
	}
	public void setUserFK(long userFK) {
		this.userFK = userFK;
	}
	public long getUserFK() {
		return userFK;
	}
	public void setLanguageFK(String languageFK) {
		this.languageFK = languageFK;
	}
	public String getLanguageFK() {
		return languageFK;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public int getStage() {
		return stage;
	}
	
	

}
