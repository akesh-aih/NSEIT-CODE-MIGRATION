package com.nseit.generic.models;

public class CandidateDocBean {

	private String OCD_CAND_DOC_PK;
	private String OCD_FLAG;
	private String OCD_DOC_FILE_NAME;
	private byte[] OCD_DOCUMENT;
	private String OCD_CHECKBOX;
	private String ocd_doc_verify_status;

	private String ocad_cand_doc_pk;
	private String ocad_flag;
	private String ocad_doc_file_name;
	private byte[] ocad_document;
	private String ocad_checkbox;
	private String ocad_doc_verify_status;
	private String OCD_USER_FK;
	private String ocd_created_by;
	private String OCD_WRKDOC_ID;
	
	public String getOCD_WRKDOC_ID() {
		return OCD_WRKDOC_ID;
	}
	public void setOCD_WRKDOC_ID(String oCD_WRKDOC_ID) {
		OCD_WRKDOC_ID = oCD_WRKDOC_ID;
	}
	public String getOcd_created_by() {
		return ocd_created_by;
	}
	public void setOcd_created_by(String ocdCreatedBy) {
		ocd_created_by = ocdCreatedBy;
	}

	public String getOCD_USER_FK() {
		return OCD_USER_FK;
	}
	public void setOCD_USER_FK(String oCDUSERFK) {
		OCD_USER_FK = oCDUSERFK;
	}
	public String getOCD_CAND_DOC_PK() {
		return OCD_CAND_DOC_PK;
	}
	public String getOCD_CHECKBOX() {
		return OCD_CHECKBOX;
	}
	public void setOCD_CHECKBOX(String oCDCHECKBOX) {
		OCD_CHECKBOX = oCDCHECKBOX;
	}
	public void setOCD_CAND_DOC_PK(String oCDCANDDOCPK) {
		OCD_CAND_DOC_PK = oCDCANDDOCPK;
	}
	public String getOCD_FLAG() {
		return OCD_FLAG;
	}
	public void setOCD_FLAG(String oCDFLAG) {
		OCD_FLAG = oCDFLAG;
	}
	public String getOCD_DOC_FILE_NAME() {
		return OCD_DOC_FILE_NAME;
	}
	public void setOCD_DOC_FILE_NAME(String oCDDOCFILENAME) {
		OCD_DOC_FILE_NAME = oCDDOCFILENAME;
	}
	public void setOCD_DOCUMENT(byte[] oCD_DOCUMENT) {
		OCD_DOCUMENT = oCD_DOCUMENT;
	}
	public byte[] getOCD_DOCUMENT() {
		return OCD_DOCUMENT;
	}
	public void setOcd_doc_verify_status(String ocd_doc_verify_status) {
		this.ocd_doc_verify_status = ocd_doc_verify_status;
	}
	public String getOcd_doc_verify_status() {
		return ocd_doc_verify_status;
	}
	public String getOcad_cand_doc_pk() {
		return ocad_cand_doc_pk;
	}
	public void setOcad_cand_doc_pk(String ocadCandDocPk) {
		ocad_cand_doc_pk = ocadCandDocPk;
	}
	public String getOcad_flag() {
		return ocad_flag;
	}
	public void setOcad_flag(String ocadFlag) {
		ocad_flag = ocadFlag;
	}
	public String getOcad_doc_file_name() {
		return ocad_doc_file_name;
	}
	public void setOcad_doc_file_name(String ocadDocFileName) {
		ocad_doc_file_name = ocadDocFileName;
	}
	public byte[] getOcad_document() {
		return ocad_document;
	}
	public void setOcad_document(byte[] ocadDocument) {
		ocad_document = ocadDocument;
	}
	public String getOcad_checkbox() {
		return ocad_checkbox;
	}
	public void setOcad_checkbox(String ocadCheckbox) {
		ocad_checkbox = ocadCheckbox;
	}
	public String getOcad_doc_verify_status() {
		return ocad_doc_verify_status;
	}
	public void setOcad_doc_verify_status(String ocadDocVerifyStatus) {
		ocad_doc_verify_status = ocadDocVerifyStatus;
	}
	
}
