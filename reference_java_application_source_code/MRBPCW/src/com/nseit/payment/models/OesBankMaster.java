package com.nseit.payment.models;

public class OesBankMaster {

	private String OBM_BANK_PK;
	private String OBM_BANK_NAME;
	private String OBM_BRANCH_NAME;
	private String OBM_BRANCH_CODE;
	private String OBM_STATUS;

	public String getOBM_BANK_PK() {
		return OBM_BANK_PK;
	}

	public void setOBM_BANK_PK(String oBMBANKPK) {
		OBM_BANK_PK = oBMBANKPK;
	}

	public String getOBM_BANK_NAME() {
		return OBM_BANK_NAME;
	}

	public void setOBM_BANK_NAME(String oBMBANKNAME) {
		OBM_BANK_NAME = oBMBANKNAME;
	}

	public String getOBM_BRANCH_NAME() {
		return OBM_BRANCH_NAME;
	}

	public void setOBM_BRANCH_NAME(String oBMBRANCHNAME) {
		OBM_BRANCH_NAME = oBMBRANCHNAME;
	}

	public String getOBM_BRANCH_CODE() {
		return OBM_BRANCH_CODE;
	}

	public void setOBM_BRANCH_CODE(String oBMBRANCHCODE) {
		OBM_BRANCH_CODE = oBMBRANCHCODE;
	}

	public String getOBM_STATUS() {
		return OBM_STATUS;
	}

	public void setOBM_STATUS(String oBMSTATUS) {
		OBM_STATUS = oBMSTATUS;
	}
}
