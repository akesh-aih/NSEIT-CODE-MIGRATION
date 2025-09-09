package com.nseit.generic.models;

public class StipendDetailsBean {
	
	private String bankNameAsPerRec;
	private String bankName;
	private String bankBranchAddr;
	private String accountNo;
	private String ifsCode;
	
	public StipendDetailsBean() {
	}

	public String getBankNameAsPerRec() {
		return bankNameAsPerRec;
	}

	public void setBankNameAsPerRec(String bankNameAsPerRec) {
		this.bankNameAsPerRec = bankNameAsPerRec;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranchAddr() {
		return bankBranchAddr;
	}

	public void setBankBranchAddr(String bankBranchAddr) {
		this.bankBranchAddr = bankBranchAddr;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getIfsCode() {
		return ifsCode;
	}

	public void setIfsCode(String ifsCode) {
		this.ifsCode = ifsCode;
	}
	

}
