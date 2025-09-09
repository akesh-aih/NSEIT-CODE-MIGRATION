package com.nseit.generic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateApproveRejectBean {

	private String OUM_USER_PK;
	private String OUM_USER_ID;
	private String OCD_FIRST_NAME;
	private String OCD_MIDDLE_NAME;
	private String OCD_LAST_NAME;
	private String OCD_CAND_TITLE;
	private String OTM_TEST_NAME;
	private String OCTM_CATEGORY_CODE;
	private String OCD_REMARKS;
	private String OCD_VALIDATED_STATUS;
	private String checkBoxValue;
}
