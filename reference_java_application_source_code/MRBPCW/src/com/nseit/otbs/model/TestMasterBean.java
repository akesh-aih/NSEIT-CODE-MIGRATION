package com.nseit.otbs.model;
/*
 * 
 * @author Raman Pawar 
 * Bean to get Test Details
 */
public class TestMasterBean {
	
	private String testName;
	private String testDescription;
	private long testFeesForOpen;
	private long testFessForSCST;
	private int testDuration;
	private int testPK;
	
	
	/**
	 * @author vijaya 
	 * @category Online / Offline Payment
	 * Date : 28th July 2012
	 * Description: Add to display on Challan Receipt   
	 */
	private String feesInWordsOpen;
	private String feesInWordsSCST;
	
	
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	public long getTestFeesForOpen() {
		return testFeesForOpen;
	}
	public void setTestFeesForOpen(long testFees) {
		this.testFeesForOpen = testFees;
	}
	public long getTestFessForSCST() {
		return testFessForSCST;
	}
	public void setTestFessForSCST(long testFessForSCST) {
		this.testFessForSCST = testFessForSCST;
	}
	public void setTestDuration(int testDuration) {
		this.testDuration = testDuration;
	}
	public int getTestDuration() {
		return testDuration;
	}
	public void setTestPK(int testPK) {
		this.testPK = testPK;
	}
	public int getTestPK() {
		return testPK;
	}
	public String getFeesInWordsOpen() {
		return feesInWordsOpen;
	}
	public void setFeesInWordsOpen(String feesInWordsOpen) {
		this.feesInWordsOpen = feesInWordsOpen;
	}
	public String getFeesInWordsSCST() {
		return feesInWordsSCST;
	}
	public void setFeesInWordsSCST(String feesInWordsSCST) {
		this.feesInWordsSCST = feesInWordsSCST;
	}
	

}
