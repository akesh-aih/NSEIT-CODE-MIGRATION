package com.nseit.generic.models;

public class UserDetailsBean {
	private String OUM_TEST_FK;
	private String OUM_USER_ID;
	private String OUM_USER_PK;
	private Integer OED_ATTEMPT;
	private Integer OTCM_TEST_CENTRE_PK;
	private String otcm_test_centre_name;
	
	public UserDetailsBean() {
	}

	public String getOUM_TEST_FK() {
		return OUM_TEST_FK;
	}

	public void setOUM_TEST_FK(String oUMTESTFK) {
		OUM_TEST_FK = oUMTESTFK;
	}

	public String getOUM_USER_ID() {
		return OUM_USER_ID;
	}

	public void setOUM_USER_ID(String oUMUSERID) {
		OUM_USER_ID = oUMUSERID;
	}

	public String getOUM_USER_PK() {
		return OUM_USER_PK;
	}

	public void setOUM_USER_PK(String oUMUSERPK) {
		OUM_USER_PK = oUMUSERPK;
	}

	public Integer getOED_ATTEMPT() {
		return OED_ATTEMPT;
	}

	public void setOED_ATTEMPT(Integer oEDATTEMPT) {
		OED_ATTEMPT = oEDATTEMPT;
	}

	public Integer getOTCM_TEST_CENTRE_PK() {
		return OTCM_TEST_CENTRE_PK;
	}

	public void setOTCM_TEST_CENTRE_PK(Integer oTCMTESTCENTREPK) {
		OTCM_TEST_CENTRE_PK = oTCMTESTCENTREPK;
	}

	public String getOtcm_test_centre_name() {
		return otcm_test_centre_name;
	}

	public void setOtcm_test_centre_name(String otcmTestCentreName) {
		otcm_test_centre_name = otcmTestCentreName;
	}
	
}
