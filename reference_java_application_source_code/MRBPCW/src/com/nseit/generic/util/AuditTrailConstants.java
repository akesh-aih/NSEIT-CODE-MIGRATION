package com.nseit.generic.util;

public interface AuditTrailConstants
{
	//Registrations Page Labels for audit
	String REGISTRATION_PAGE = "Basic Registration Screen";
	String REG_PAGE_SUBMIT = "Registration Page-Submit";
	String POST_APPLIED= "Post Applying for";
	String CANDIDATE_ID= "Candidate Id";
	String COMPLETE_NAME= "Name";
	String NATIONALITY= "Nationality";
	String EXSERVICEMAN_YESNO= "Are you an exservicemen?";
	String DISCHARGE_DATE = "Date of Discharge / Probable Discharge";
	String PPONUMBER = "PPO Number";
	String COMMUNITY_YESNO = "Do you have community certificate issued by Tamil Nadu Government?";
	String COMMUNITY = "Community";
	String SUBCASTE = "Sub Caste";
	String COMM_CERT = "Issuing Authority of Community Certificate";
	String COMM_CERT_NO = "Community Certificate Number.";
	String COMM_CERT_PLACE = "Community Certificate Place of Issue";
	String COMM_CERT_DATE = "Community Certificate Issuing Date";
	String DISABLITY_YESNO= "Are you a differently abled?";
	String DISABLED_CHKBOX = "I agree to provide Differently Abled Person Certificate at the time of Certificate Verification";
	String DISABILITY_CATEGORY = "Differently Abled Category";
	String DISABILITY_PERCENT = "Percentage of Disability";
	String WIDOW_CERT_NO = "WIDOW_CERT_NO";
	String WIDOW_ISSUE_AUTHORITY = "WIDOW_ISSUE_AUTHORITY";
	String WIDOW_ISSUE_DATE = "WIDOW_ISSUE_DATE";
	String WIDOW_DISTRICT = "WIDOW_DISTRICT";
	String WIDOW_OTHER_DISTRICT = "WIDOW_OTHER_DISTRICT";
	String WIDOW_SUBDIVISION = "WIDOW_SUBDIVISION";
	String DOB= "DOB";
	String AGE_AS_ON= "Age as on";
	String AGE_AS_ON_DURATION= "Age as on duration";
	String EMAIL= "Email";
	String CONFIRM_EMAIL= "Confirm Email";
	String MOBILENO= "Mobile No";
	
	
	//Personal Page Labels for audit
	String GENDER= "Gender";
	String FATHER_NAME= "Father Name";
	String MOTHER_NAME= "Mother Name";
	String MARITAL_STATUS = "Are you married?";
	String SPOUSE_NAME = "Spouse Name";
	String NATIVITY = "Nativity";
	String OTHER_NATIVITY = "Other Nativity";
	String RELIGION = "Religion";
	String OTHER_RELIGION = "Other Religion";
	String IS_GOVT_SERVICE = "Are you govt servant";
	String GOVT_SERVICE_CHKBOX = "I agree to provide No Objection Certificate from the concerned Government Department at the time of Certificate Verification";
	String ORG_NAME = "Department Name";
	String CURR_DESIGNATION = "Current Designation";
	String PLACE_OF_WORK = "Place of Work";
	String GOVT_DATE = "GOVT_DATE";
	String GOVT_AGE = "Period of Service as on the  Date of Notification 04-Apr-2025";
	String MOTHER_TONGUE = "Mother Tongue";
	String MOTHER_TONGUE_OTHER = "Mother Tongue Others";
	String PHOTO_ID = "Photo ID";
	String PHOTO_ID_PROOF_NO = "Photo ID Proof No";
	String PERMANENT_ADDRESS_LINE_1 = "Permanent Address Line 1";
	String PERMANENT_ADDRESS_LINE_2 = "Permanent Address Line 2";
	String PERM_STATE = "State";
	String PERM_OTHER_STATE = "Other State";
	String PERM_DISTRICT_DROPDOWN = "District";
	String PERM_DISTRICT_TEXTBOX = "Other District";
	String PERM_TALUK_VAL = "Taluk_Dropdown";
	String PERM_TALUK = "Main_Taluk_Textbox";
	String PERM_OTHER_TALUK = "Other_Taluk";
	String PERM_CITY = "City";
	String PERM_PINCODE = "PINCODE";
	
	String SAME_AS_PERM = "Same as Permanent Address(yes/no)";
	String CORRESPONDENCE_ADDRESS_LINE_1 = "Correspondence Address Line 1";
	String CORRESPONDENCE_ADDRESS_LINE_2 = "Correspondence Address Line 2";
	String CORS_STATE = "State";
	String CORS_OTHER_STATE = "Other State";
	String CORS_DISTRICT_DROPDOWN = "District";
	String CORS_DISTRICT_TEXTBOX = "Other District";
	String CORS_TALUK = "Main_Taluk_Textbox";
	String CORS_TALUK_VAL = "Taluk_Dropdown";
	String CORS_OTHER_TALUK = "Other Taluk";
	String CORS_CITY = "City";
	String CORS_PINCODE = "PINCODE";
	
	
	// Work EXP Const
//	String RELEVANT_EXP = "Relevant exp";
//	String ORGANIZATION = "Organization";
//	String POSITION = "Position";
//	String FROM = "From";
//	String TO = "To";
//	String TOTAL_PERIOD = "Total Period";
	
	// Payment ( execute() ) Const
	    String PAYMENT ="Payment Page - Payment Response";
		String TXN_MODE = "Txn_Mode";
		String USER_ID = "User_ID";
		String FEES = "Fees";
		String TXN_REF_NUMBER = "TxnRefNumber";
		String TRANSACTION_DATE = "Transaction_Date";
		String AUTH_STATUS = "TransStatus";
		String TXN_REF_NO = "TransRefNo";
		String ITEM_CODE = "Item_code";
		String CURRENCY = "Currency";
		String CUST_ID = "Customer_id";
		String ERROR_DESC = "Error_desc";
		String BANK_CODE = "Bank_code";
		String BANK_REF_NO = "Bank_Ref_No";
		String COUNTRY = "Country";
		String CIN = "CIN";
		
		
	// Payment ( CTS() ) Const
		String CTS = "Check Status Button Click";
	    String CTS_USER_ID ="CTS_User_ID";
		String CTS_TIME = "Clicked_Date_time ";
		
		
	// Payment ( CTS:Response() ) Const
	    String CTS_R ="Payment Details For Check Status single User Response";
	    String CTS_RESPONSE = "Payment Page - CTS Response";
		String CTS_R_TXN_MODE = "Payment_Mode";
		String CTS_R_USER_ID = "User_ID";
		String CTS_R_TXN_REF_NUMBER = "TxnRefNumber";
		String CTS_R_TRANSACTION_DATE = "Transaction_Date";
		String CTS_R_AUTH_STATUS = "Authentication_Status";
		
		
	// Payment ( connectToGateway() ) Const
	    String PAYMENT_CONNECT ="Click on Proceed To Pay";
		String PAYMENT_USER_ID = "Candidate_User_Id";
		String PAYMENT_TXN_NUMBER = "TxnRefNumber";
		String PAYMENT_FEES = "paymentAmount";
		String PAYMENT_DISABILITY = "disability";
		String PAYMENT_CASTE = "caste";
		
	// Education Page Labels starts here
		
	String EDU_DETAILS_SAVE = "Education Details Saved As: ";

	String SSC_DETAILS = "Users 10th / SSLC Details ";
	String TWELVE_DETAILS = "Users 12th / HSC Details";

	String NAME_OF_BOARD = "Name of Board";
	String OTHER_BOARD = "Other Board";
	String M_Y_PASSING = "Month & Year of Passing";
	String PERCENTAGE = "Percentage of Marks";
	String MEDIUM_INSTRUCTION = "Medium of Instruction";
	String TAMIL_LANG = "Have you studied Tamil as one of the language (Part-1)";

	String DIPLOMA_DETAILS = "Diploma Details ";
	String DIPLOMA_NAME = "Diploma Name";
	String OTHER_DIPLOMA = "Other Equivalent Diploma";
	String PRD_OF_STUDY_FROM = "Period of Study From";
	String PRD_OF_STUDY_TO = "Period of Study To";
	String DURATION_OF_STUDY = "Duration of Study (Number of Years)";
	String NAME_OF_INSTIUTION = "Name of Institution";
	String DIP_MARKS_YESNO = "Do you have marks for the Diploma Course?";
	String TOTAL_MARKS = "Total Maximum Marks";
	String OBTAINED_MARKS = "Total Obtained Marks";

	String GRADUATION_DETAILS = "Under Graduation Details ";
	String PG_DETAILS = "Post Graduation Details ";
	String UG_YESNO = "Do you have UG Degree";
	String PG_YESNO = "Do you have PG Degree";
	String PGDIP_YESNO = "Do you have PG Diploma";
	String SPECIALIZATION = "Specialization";

	String QE_DETAILS = "Super Specialty Details";
	String QE_EXAMINATION = "Do you have Super Specialty";

	String PSTM_PREFERENCE = "Are you eligible to avail PSTM preference?";
	String TAMIL_MED_SSC = "Have you studied in Tamil medium from 1st standard to 12th standard?";
	String TAMIL_MED_UG = "Have you studied your UG in Tamil medium?";

	String EDU_SAVE_DETAILS = "Education Qualification Page - Save & Continue";

	// Education Page Labels ends here
	
	  //Covid duty certificate page labels starts here
	    String RELEVANT_EXP = "Have you worked in covid period ?";
		String INSTITUTION_TYPE = "Institution Type";
		String NAME_OF_MED_INST = "Name of the Medical Institution";
		String DISTRICT = "District";
		String ADDRESS_OF_INST = "Address of the Institution";
		String PERIOD_OF_WORK_FROM = "Period of Work-From";
		String PERIOD_OF_WORK_TO = "Period of Work-To";
		String DURATION_OF_COVID_SERV = "Duration of Covid Service";
		String CERTI_SIGNED_BY = "Certificate Signed by";
		String CERTI_COUNTER_SIGNED_BY = "Certificate Counter Signed by";
		String YEAR_OF_TOTAL_SERVICE = "Year Of Total Service";
		String MONTH_OF_TOTAL_SERVICE = "Month Of Total Service";
		String DAY_OF_TOTAL_SERVICE = "Day Of TotalService";
		String TOTAL_WRK_EXP = "Total Duration of Covid Service";
		String PARENT_OR_GUARDIAN = "Would you like to give Father and Mother name or Guardian name";
		String GUARDIAN_NAME = "Guardian Name";
	    
}