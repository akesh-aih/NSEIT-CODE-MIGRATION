package com.nseit.generic.util.resource;

public interface ValidationMessageConstants
{
	String LOGIN_USERNAME_REQUIRED = "login.usernamerequired";
	String LOGIN_PASSWORD_REQUIRED = "login.passwordrequired";
	String LOGIN_USERNNAME_SPACE = "login.spacenotallowed";
	String LOGIN_PASSWORD_VALIDATION = "login.invalidpassword";
	
	String ENROLL_FATHER_NAME = "enroll.fathernamerequired";
	String ENROLL_MOTHER_NAME = "enroll.mothernamerequired";
	String ENROLL_FIRST_NAME = "enroll.firstnamerequired";
	String ENROLL_LAST_NAME = "enroll.lastrnamerequired";
	String ENROLL_ADDR_NAME = "enroll.addressrequired";
	String ENROLL_ALTER_ADDR = "enroll.alternateaddrequired";
	String ENROLL_MOBILE_NO = "enroll.mobilenorequired";
	String ENROLL_MOBILE_NUMERIC = "enroll.numericmobile";
	String ENROLL_EMAIL_ADD = "enroll.emailaddrequired";
	String ENROLL_GENDER = "enroll.genderrequired";
	String ENROLL_FIRST_TSTCNTR = "enroll.firsttstcntrrequired";
	String ENROLL_SECOND_TSTCNTR = "enroll.secondtstcntrrequired";
	String ENROLL_THIRD_TSTCNTR = "enroll.thirdtstcntrrequired";
	String ENROLL_DOB = "enroll.birthdaterequired";
	String ENROLL_CITY = "enroll.cityfkrequired";
	String ENROLL_CITY_NUMERIC = "enroll.cityfknumeric";
	String ENROLL_ALTER_CITY = "enroll.altercityfkrequired";
	String ENROLL_STATE = "enroll.statefkrequired";
	String ENROLL_STATE_FK_NUMERIC = "enroll.statefknumeric";
	String ENROLL_MOTHER_MAIDEN_NAME = "enroll.mothermaidennamerequired";
	String ENROLL_CAPCTHA = "enroll.captcharequired";
	String ENROLL_CAPTCHA_MISMATCH = "enroll.captchamismatch";
	String ENROLL_STATUS = "enroll.statusrequired";
	String ENROLL_ALTERNATE_STATE = "enroll.alternatestatefkrequired";
	String ENROLL_PINCODE = "enroll.pincoderequired";
	String ENROLL_PINCODE_NUMERIC = "enroll.pincodenumeric";
	String ENROLL_ALTER_PINCODE = "enroll.alterpincoderequired";
	String ENROLL_QUALIFICATION = "enroll.qualificationrequired";
	String ENROLL_QUALIFICATION_YEAR = "enroll.qualificationyearrequired";
	String ENROLL_PERCENTAGE = "enroll.percentage";
	String ENROLL_BOARD_UNIVERSITY = "enroll.boarduniversity";
	String ENROLL_DISCIPLINE = "enroll.disciplinerequired";
	String ENROLL_DISCIPLINE_NUMERIC = "enroll.numericdisciplineid";
	
	String SCHEDULE_TST_SLOT = "schedule.tstslotrequired";
	String SCHEDULE_TST_SLOT_NUMERIC = "schedule.numerictstslotrequired";
	String SCHEDULE_INTERVIEW_SLOT = "schedule.interviewslotrequired";
	String SCHEDULE_INTERVIEW_SLOT_NUMERIC = "schedule.numericinterviewslotrequired";
	String SCHEDULE_TEST_CENTER = "schedule.tstcntrrequired";
	String SCHEDULE_TEST_DATE = "schedule.tstdtrequired";
	String SCHEDULE_TEST_CENTER_PK_NUMERIC = "schedule.numerictstcntrpkrequired";
	String SCHEDULE_VALID_TEST_DATE = "schedule.validtstdtrequired";
	String SCHEDULE_REASON = "schedule.reasontoretest";
	
	String SCHEDULE_TST_ID = "schedule.testId";
	String SCHEDULE_TST_ID_NUMERIC = "schedule.testIdNumeric";
	String SCHEDULE_AVAILABLE_CAPACITY = "schedule.availablecapacity";
	
	
	String TEST_DATE_EMPTY = "testDate.empty";
	String TEST_DATE_VALID = "testDate.valid";
	
	
	
	String SCHEDULE_TST_CENTER_ID_NUMERIC = "schedule.testCenterId";
	String SCHEDULE_TST_CENTER_ID = "schedule.testCenterIdIdNumeric";
	
	String REGISTER_USERNAME_REQUIRED = "register.usernamerequired";
	String REGISTER_QUESTION_REQUIRED = "register.questionrequired";
	String REGISTER_ANSWER_REQUIRED = "register.answerrequired";
	
	String TSTMGMT_FROM_DATE_REQUIRED = "tstmgmt.fromdtrequired";
	String TSTMGMT_TO_DATE_REQUIRED = "tstmgmt.todtrequired";
	String TSTMGMT_VALID_DATE = "tstmgmt.datenotvalid";
	String TSTMGMT_ADDRESS_REQUIRED = "tstmgmt.addrequired";
	
	String SETTING_STAGE_REQUIRED = "sett.stagevaluereq";
	String SETTING_START_DT_REQUIRED = "sett.startdtreq";
	String SETTING_END_DT_REQUIRED = "sett.enddtreq";
	
	// Login Page Text
	String LOGIN_USERNAME_REQUIRE="login.errorlabelusername";
	String LOGIN_PWD_REQUIRE="login.errorlabelpwd";
		
	// Forgot UserID Page Text.
	String ERROR_LABEL_MOBILENO="forgotuserid.errorlablemobileno";
	String ERROR_LABEL_EMAILADDRESS = "forgotuserid.errorlabelemailaddress";
	String ERROR_LABEL_DOB="forgotuserid.errorlabeldateofbirth";
	String ERROR_LABEL_MOBILENO_NUMERIC = "forgotuserid.errorlablemobilenonumeric";
	
	// Forgot Password Page Text.
	String FORGOT_EMAILTOGETPWD_REQUIRE ="forgotpassword.errorlabelemailtogetpwd";
	String FORGOT_USERID_ALPHANUMERIC = "forgotpassword.alphanumericuserid";
	String INVALID_USERID = "invalid.userid";
	String INVALID_USERID_OR_EMAILID ="invalid.useridoremail";
	
	//# [Start] Registration Page Text
	String REGISTRATION_FIRSTNAME_REQUIRE="register.errlabelfirstname";
	String REGISTRATION_LASTTNAME_REQUIRE="register.errlabellastname";
	String REGISTRATION_MOTHERMAIDENNAME_REQUIRE="register.errlabelmothermaidenname";
	String REGISTRATION_VALIDDATE="register.errlabelvaliddate";
	String REGISTRATION_MOBILE_REQUIRE="register.errlabelmobile";
	String REGISTRATION_ENTEREMAIL_REQUIRE="register.errlabelemail";
	String REGISTRATION_REENTEREMAIL_REQUIRE="register.errlabelreenteremail";
	String REGISTRATION_CAPTCHA_REQUIRE="register.errlabelcaptcha";
	String REGISTRATION_BLACKLISTED_IP = "register.blacklistedip";
	String REGISTRATION_DUPLICATE_DETAILS = "register.duplicatedetails";
	String REGISTRATION_DUPLICATE_EMAIL_MOBILE = "register.duplicateemailmobile";
	String REGISTRATION_DUPLICATE_EMAIL = "register.duplicateemail";
	String REGISTRATION_DUPLICATE_MOBILE = "register.duplicatemobile";
	// Select Discipline Page[SelectDiscipline.jsp].
	String DISCIPLINE_SELECT_REQUIRE="selectdiscipline.errlblseldiscipline";
	
	// View Update Personal Information Page[viewUpdatePersonalInformation.jsp].
	String VIEWUPDATE_FIRSTNAME_REQUIRE="viewupdatepersonalinfo.errlblfirstname";
	String VIEWUPDATE_LASTNAME_REQUIRE="viewupdatepersonalinfo.errlbllastname";
	String VIEWUPDATE_FATHERNAME_REQUIRE="viewupdatepersonalinfo.errlblfathername";
	String VIEWUPDATE_PERADDRESS_REQUIRE="viewupdatepersonalinfo.errlblperaddress";
	String VIEWUPDATE_ALTERNATEADD_REQUIRE="viewupdatepersonalinfo.errlblalternateaddress";
	String VIEWUPDATE_STATE_REQUIRE="viewupdatepersonalinfo.errlblstateforperaddress";
	String VIEWUPDATE_ALRSATE_REQUIRE="viewupdatepersonalinfo.errlblstateforaltraddress";
	String VIEWUPDATE_CITYFORPERADDR_REQUIRE="viewupdatepersonalinfo.errlblcityforperaddress";
	String VIEWUPDATE_CITYALTADDR_REQUIRE="viewupdatepersonalinfo.errlblforalternateaddress";
	String VIEWUPDATE_PINCODE_REQUIRE="viewupdatepersonalinfo.errlblvalidpincode";
	String VIEWUPDATE_ALTPIN_REQUIRE="viewupdatepersonalinfo.errlblalternatepincode";
	String VIEWUPDATE_EMAIL_REQUIRE="viewupdatepersonalinfo.errlblemail";
	String VIEWUPDATE_MOBILE_REQUIRE="viewupdatepersonalinfo.errlblvalidmobile";
	
	// View / Update Academic Details [viewUpdateAcademicDetails.jsp]
	String ACADEMIC_QUALIFIEDEXAM_REQUIRE="viewupdateacademicdetails.errlblqualifiedexam";
	String ACADEMIC_YEATOFEXAM_REQUIRE="viewupdateacademicdetails.errlblyrofexam";
	String ACADEMIC_PERCENTAGE_REQUIRE="viewupdateacademicdetails.errlblper";
	String ACADEMIC_SELECTUNI_REQUIRE="viewupdateacademicdetails.errlblselectuni";
	
	// Select Venue[selectVenue.jsp]
	String VENUE_FIRSTPRETSTCNR_REQUIRE="venue.errlblfirsttestcenter";
	String VENUE_SECONDPRETSTCNR_REQUIRE="venue.errlblsecondprefertectcen";
	String VENUE_THIRDPRETSTCNR_REQUIRE="venue.errlblthirdprefertestcen";
	
	// Payment
	String PAYMENT_WINDOW_CLOSED="payment.windowclosed";
	String PAYMENT_SEARCH_PARAM = "payment.searchparam";
	
	//DD Details[insertDDDetails.jsp]
	String DD_DDNO_REQUIRE="dd.errlblddno";
	String DD_DDNO_NUMERIC="dd.errlblddnonumeric";
	String DD_DDDATE_REQUIRE="dd.errlbldddate";
	String DD_BANKNAME_REQUIRE="dd.errlblbankname";
	String DD_BANKNAME_NUMERIC="dd.errlblbanknamenumeric";
	String DD_SELCITY_REQUIRE ="dd.errlblpropercity";
	String DD_SELCITY_NUMERIC ="dd.errlblpropercitynumeric";
	String DD_SUBMIT_VALUE = "dd.submitvalue";
	
	//Challan Details[insertChallanDetails.jsp]
	String CHALLAN_CHALLANDATE_REQUIRE="challan.errlbldate";
	String CHALLAN_JOURNAL_REQUIRE="challan.errlblscroll";
	
	String CHALLAN_BANKCODE_REQUIRE="challan.errlblselbankcode";
	String CHALLAN_BRANCH_CODE_REQUIRE="challan.errlblselbranchcode";
	
	String CHALLAN_BANKNAME_REQUIRE="challan.errlblselbankname";
	String CHALLAN_SELCITY_REQUIRE="challan.errlblselcity";
	
	// Request Retest Page.
	String RETEST_REQUIRE="requestretest.errlblretest";
	String RETEST_REASON_REQUIRE="requestretest.errlblreasonretest";
	
	


	// Request Change.
	String RETEST_CHANGE_REQUIRE="requestchange.errlblrequestChange";
	
	String UPLOAD_IMAGE_SIZE_LIMIT_MESSAGE = "photosize.valid";
	String UPLOAD_DOCUMENT_SIZE_LIMIT_MESSAGE = "documentsize.valid";
	String UPLOAD_IMAGE_REQUIRED = "photo.required";
	String UPLOAD_SIGNATURE_SIZE_LIMIT_MESSAGE = "signaturesize.valid";
	String UPLOAD_SIGNATURE_REQUIRED = "sign.required";
	String IMAGE_UPLOAD_SUCCESS = "imageupload.success";
	String INVALID_IMAGE_FORMAT = "invalidimage.format";
	String INVALID_SIGNATURE_FORMAT = "invalidsignature.format";
	String INVALID_DOCUMENT_FORMAT = "invaliddocument.format";
	
	String UPLOAD_MARKS_ROW_ERROR_MESSAGE = "marks.uploadRow";
	String UPLOAD_OFFLINE_PAYMENT_ROW_ERROR_MESSAGE = "offlinePayment.uploadRow";
	String UPLOAD_CANDIATE_ID = "candidateId.upload";
	String UPLOAD_ENROLLMENT_ID = "enrollmentId.upload";
	String UPLOAD_TEST_CENTER_ID = "testCenterId.upload";
	String UPLOAD_TEST_CENTER_NAME = "testCenterName.upload";
	String UPLOAD_MODULE_ID = "moduleId.upload";
	String UPLOAD_TEST_DATE = "testDate.upload";
	String UPLOAD_TEST_TIME = "testTime.upload";
	
	// Security.
	String SECURITY_CLICKJACKING = "security.clickjacking";
	
	/**
	 * Server side Validation for candidate login
	 * @author Pankaj
	 */
	String SS_COMMON_WARNING_START = "ss.commonwarningstart";
	String SS_COMMON_WARNING_END = "ss.commonwarningend";
	
	String SS_LOGINACTION_USERID = "ss.missinguserid";
	String SS_LOGINACTION__PWD = "ss.missingpwd";
	
	// Change password
	String OLD_PASSWORD = "oldpassword.required";
	String NEW_PASSWORD = "newpassword.required";
	String USE_DIFFERENT_PASSWORD = "different.password";
	//Mantis 0000164
	String INVALID_PASSWORD_FORMAT ="newpassword.invalidformat";
	
	//Admin portal Email Sms Settings 
	String ACTIVITY_REQD = "activity.reqd" ;
	String EMAIL_CHK_BOX_REQD = "emailChk.reqd" ;
	String SMS_CHK_BOX_REQD = "smsChk.reqd" ;
	String SMS_CONTENT_REQD = "smsConetnt.reqd" ;
	String EMAIL_CONTENT_REQD = "emailContent.reqd" ;
	String CC_ADDRESS_REQD = "ccAddress.reqd" ;
	String SUBJECT_REQD = "subject.reqd" ;
	
	
	String DISCIPLINE_NUMERIC_REQD = "discipline.numeric";
	String ACTIVITY_NUMERIC_REQD = "activity.numeric";
	
	String ADDRESS_BLANK = "address.blank";
	
	
	
	
	// sign up page
	String USER_ID_REQD = "userid.reqd";
	String EMAIL_REQD = "email.reqd";
	String CONFIRM_EMAIL_REQD ="confirmemail.reqd";
	String INVALID_EMAIL = "email.invalid";
	String INVALID_CONFIRMEMAIL="confirmemail.invalid";
	String MOBILE_REQD = "mobile.regd";
	String INVALID_MOBILE_NO = "mobile.invalid";
	String CONFIRM_PASSWORD_REQD = "confirmpassword.required";
	String NEW_PASSWORD_REQD = "newpassword.required";
	String NEW_PASSWORD_LENGTH = "newpassword.length";
	String NEW_PASSWORD_MAX_LENGTH="newpassword.maxlength";
	String IPIN_REQD = "ipin.required";
	String DISCIPLINE_REQD = "discipline.required";
	String CAPTCHA_REQD = "captcha.required";
	String DISCIPLINE_NUMERIC = "discipline.numeric";
	String OTP_REQD = "otp.reqd";
	String MOB_OTP_REQD = "mobotp.reqd";
	String NEW_PASSWORD_MATCH = "newpassword.match";
	String INITIAL_REQD="initial.reqd";
	String VALID_INITIAL_REQD="validinitial.reqd";
	String GENDER_REQD="gender.reqd";
	String CATEGORY_REQD="category.reqd";
	String FIRST_NAME_REQD="firstname.reqd";
	String VALID_FIRST_NAME_REQD="validfirstname.reqd";
	String VALID_MIDDLE_NAME_REQD="validmiddlename.reqd";
	String VALID_LAST_NAME_REQD="validlastname.reqd";
	String CONFIRMEMAIL_EMAIL_REQD="bothemails.reqd";
	String MIDDLENAME_CANNOT_HAVE_BLANKSPACE="middlename_blankspace";
	String LASTNAME_CANNOT_HAVE_BLANKSPACE="lastname_blankspace";
	//app form page
	String ADDRESS_REQD = "address.reqd";
	String CITY_REQD = "city.reqd";
	String PINCODE_REQD = "pincode.reqd";
	String OTHER_STATE_FIELD_REQD = "otherStateField.reqd";
	String ALTERNATE_ADDRESS_REQD = "altAddress.reqd";
	String ALTERNATE_CITY_REQD = "altCity.reqd";
	String ALTERNATE_PINCODE_REQD = "altPincode.reqd";
	String ALTERNATE_OTHER_STATE_FIELD_REQD = "alternateOtherStateField.reqd";
	
	String CANDIDATE_FIRST_NAME_REQD = "candidateName.reqd";
	String CANDIDATE_MIDDLE_NAME_REQD = "candidateMiddleName.reqd";
	String CANDIDATE_LAST_NAME_REQD = "candidateLastName.reqd";
	String CANDIDATE_DOB_REQD = "candidateDOB.reqd";
	String CANDIDATE_EMAIL_REQD = "candidateEmail.reqd";
	String CANDIDATE_MOBILE_REQD = "candidateMobile.reqd";
	String CANDIDATE_MOBILE_NUMERIC = "candidateMobile.numeric";
	String CANDIDATE_ISD_REQD = "candidateIsd.reqd";
	String CANDIDATE_ISD_NUMERIC = "candidateIsd.numeric";
	String CANDIDATE_STD_CODE_REQD = "candidateStd.reqd";
	String CANDIDATE_STD_CODE_NUMERIC = "candidateStd.numeric";
	String CANDIDATE_PHONE_REQD = "candidatePhone.reqd";
	String CANDIDATE_PHONE_NUMERIC = "candidatePhone.numeric";
	String CANDIDATE_CATEGORY_REQD = "candidateCategory.reqd";
	String CANDIDATE_CATEGORY_NUMERIC = "candidateCategory.numeric";
	String CANDIDATE_HANDICAPPED_REQD = "candidateHandicapped.reqd";
	String CANDIDATE_HANDICAPPED_NUMERIC = "candidateHandicapped.numeric";
	String CANDIDATE_HANDICAPPED_PERC_REQD = "candidateHandicappedPerc.reqd";
	String CANDIDATE_HANDICAPPED_PERC_NUMERIC = "candidateHandicappedPerc.numeric";
	String CANDIDATE_POVERTY_REQD = "candidatePoverty.reqd";
	String CANDIDATE_POVERTY_NUMERIC = "candidatePoverty.numeric";
	String CANDIDATE_FATHER_FIRST_NAME_REQD = "candidateFatherName.reqd";
	String CANDIDATE_POST1_REQD = "candidatePost1.reqd";
	String SIMILAR_POST1_POST2 = "candidatepost1post2.similar";
	String SIMILAR_POST1_POST3 = "candidatepost1post3.similar";
	String SIMILAR_POST2_POST3 = "candidatepost2post3.similar";
	String CANDIDATE_NATIONALITY_REQD = "candidateNationality.reqd";
	String CANDIDATE_NATIONALITY_NUMERIC = "candidateNationality.numeric";
	String CANDIDATE_GENDER_REQD = "candidateGender.reqd";
	String CANDIDATE_MARITAL_STATUS_REQD = "candidateMaritalStauts.reqd";
	String CANDIDATE_PHYSICAL_DISABILITY_REQD = "candidatePhysicalDisability.reqd";
	String CANDIDATE_EX_SERVICEMAN_REQD ="candidateExServiceman.reqd";
	String CANDIDATE_DEPARTMENTAL_CANDIDATE_REQD = "candidateDepartmentalCandidate.reqd"; 
	String CANDIDATE_RELIGION_REQD = "candidateReligion.reqd";
	String CANDIDATE_OTHER_RELIGION_REQD = "candidateOtherReligion.reqd";
	String CANDIDATE_OTHER_RELIGION_INVALID = "candidateOtherReligion.invalid";
	String CANDIDATE_GENDER_NUMERIC = "candidateGender.numeric";
	String CANDIDATE_STATE_REQD = "candidateState.reqd";
	String CANDIDATE_STATE_NUMERIC = "candidateState.numeric";
	String CANDIDATE_UNION_TERRITORY_REQD = "candidateUnionTerritory.reqd";
	String CANDIDATE_UNION_TERRITORY_NUMERIC = "candidateUnionTerritory.numeric";
	String CANDIDATE_COUNTRY_REQD = "candidateCountry.reqd";
	String CANDIDATE_COUNTY_NUMERIC = "candidateCountry.numeric";
	String CANDIDATE_ALT_STATE_REQD = "candidateAlternateState.reqd";
	String CANDIDATE_ALT_STATE_NUMERIC = "candidateAlternateState.numeric";
	String CANDIDATE_ALT_UNION_TERRITORY_REQD = "candidateAlteranteUnionTerritory.reqd";
	String CANDIDATE_ALT_UNION_TERRITORY_NUMERIC = "candidateAlteranteUnionTerritory.numeric";
	String CANDIDATE_ALT_COUNTRY_REQD = "candidateAlteranteCountry.reqd";
	String CANDIDATE_ALT_COUNTRY_NUMERIC = "candidateAlteranteCountry.numeric";
	
	String CANDIDATE_DEGREE_TYPE_REQD = "candidateDegreeType.reqd";
	String CANDIDATE_DEGREE_TYPE_NUMERIC = "candidateDegreeType.numeric";
	String CANDIDATE_DEGREE_REQD = "candidateDegree.reqd";
	String CANDIDATE_DEGREE_NUMERIC = "candidateDegree.numeric";
	String CANDIDATE_DEGREE_OTHER_REQD = "candidateDegreeOther.reqd";
	String CANDIDATE_DEGREE_OTHER_NUMERIC = "candidateDegreeOther.numeric";
	String CANDIDATE_RESULT_GRADUATION_REQD = "candidateGraduationResult.reqd";
	String CANDIDATE_DEGREE_RESULT_GRADUATION_NUMERIC = "candidateGraduationResult.numeric";
	
	String CANDIDATE_SSC_YEAR_PASSNG_REQD = "candidateSscmarks.reqd";
	String CANDIDATE_SSC_YEAR_PASSNG_NUMERIC = "candidateSscmarks.numeric";
	String CANDIDATE_HSC_YEAR_PASSNG_REQD = "candidateHscmarks.reqd";
	String CANDIDATE_HSC_YEAR_PASSNG_NUMERIC = "candidateHscmarks.numeric";
	String CANDIDATE_BE_YEAR_PASSNG_REQD = "candidateBEmarks.reqd";
	String CANDIDATE_BE_YEAR_PASSNG_NUMERIC = "candidateBEmarks.numeric";
	String CANDIDATE_SSC_UNIVERSITY_REQD = "candidateSscUniversity.reqd";
	String CANDIDATE_HSC_UNIVERSITY_REQD = "candidateHscUniversity.reqd";
	String CANDIDATE_BE_UNIVERSITY_REQD = "candidateBEUniversity.reqd";
	String CANDIDATE_SSC_OBTAINED_MARKS_REQD = "candidateSscObtainedmarks.reqd";
	String CANDIDATE_SSC_OBTAINED_MARKS_NUMERIC = "candidateSscObtainedmarks.numeric";
	String CANDIDATE_HSC_OBTAINED_MARKS_REQD = "candidateHscObtainedmarks.reqd";
	String CANDIDATE_HSC_OBTAINED_MARKS_NUMERIC = "candidateHscObtainedmarks.numeric";
	String CANDIDATE_BE_OBTAINED_MARKS_REQD = "candidateBEObtainedmarks.reqd";
	String CANDIDATE_BE_OBTAINED_MARKS_NUMERIC = "candidateBEObtainedmarks.numeric";
	String CANDIDATE_SSC_MAX_MARKS_REQD = "candidateSscMaxmarks.reqd";
	String CANDIDATE_SSC_MAX_MARKS_NUMERIC = "candidateSscMaxmarks.numeric";
	String CANDIDATE_HSC_MAX_MARKS_REQD = "candidateHscMaxmarks.reqd";
	String CANDIDATE_HSC_MAX_MARKS_NUMERIC = "candidateHscMaxmarks.numeric";
	String CANDIDATE_BE_MAX_MARKS_REQD = "candidateBEMaxmarks.reqd";
	String CANDIDATE_BE_MAX_MARKS_NUMERIC = "candidateBEMaxmarks.numeric";
	String CANDIDATE_SSC_PERCENTAGE_REQD = "candidateSscPerc.reqd";
	String CANDIDATE_SSC_PERCENTAGE_NUMERIC = "candidateSscPerc.numeric";
	String CANDIDATE_HSC_PERCENTAGE_REQD = "candidateHscPerc.reqd";
	String CANDIDATE_HSC_PERCENTAGE_NUMERIC = "candidateHscPerc.numeric";
	String CANDIDATE_BE_PERCENTAGE_REQD = "candidateBEPerc.reqd";
	String CANDIDATE_BE_PERCENTAGE_NUMERIC = "candidateBEPerc.numeric";
	String CANDIDATE_PHOTO_REQD = "candidatePhoto.reqd";
	String CANDIDATE_SIGN_REQD = "candidateSign.reqd";
	String CANDIDATE_TEST_CENTER_1_REQD = "candidateTestCenter1.reqd";
	String CANDIDATE_TEST_CENTER_1_NUMERIC = "candidateTestCenter1.numeric";
	String CANDIDATE_TEST_CENTER_2_REQD = "candidateTestCenter2.reqd";
	String CANDIDATE_TEST_CENTER_2_NUMERIC = "candidateTestCenter2.numeric";
	String CANDIDATE_TEST_CENTER_3_REQD = "candidateTestCenter3.reqd";
	String CANDIDATE_TEST_CENTER_3_NUMERIC = "candidateTestCenter3.numeric";
	String CANDIDATE_TEST_LANGUAGE_REQD = "candidateTestLanguage.reqd";
	String CANDIDATE_DOCUMENT_REQD = "candidateDocument.reqd";
	String CANDIDATE_LOGIN_REQD = "candidateLogin.reqd";
	String CANDIDATE_pASSWORD_REQD = "candidatePassword.reqd";
	String CANDIDATE_CENTER_ERROR1 = "candidateCenterError1";
	String CANDIDATE_CENTER_ERROR2 = "candidateCenterError2";
	String CANDIDATE_CENTER_ERROR3 = "candidateCenterError3";
	String CANDIDATE_CENTER_DIFF = "candidateCenterDiff";
	String MSG_FOR_DUPLICATE_USER = "msgForDuplicateUser";
	
	String CANDIDATE_TEST_DATE_1_REQD = "candidateTestDate1Reqd";
	String CANDIDATE_TEST_DATE_2_REQD = "candidateTestDate2Reqd";
	String CANDIDATE_TEST_DATE_3_REQD = "candidateTestDate3Reqd";
	String CANDIDATE_TEST_DATE_ERROR1 = "candidateTestDateError1";
	String CANDIDATE_TEST_DATE_ERROR2 = "candidateTestDateError2";
	String CANDIDATE_TEST_DATE_ERROR3 = "candidateTestDateError3";
	String CANDIDATE_TEST_DATE_DIFF = "candidateTestDateFiff";
	
	String PASSWORD_VALIDATION = "password.validation";
	
	String TITLE_REQUIRED = "title.required";
	String DISCIPLINE_REQUIRED = "discipline.required";
	String DISTRICT_REQUIRED = "district.required";
	String ALT_DISTRICT_REQUIRED = "altdistrict.required";
	
	
	String NAME_BANK_REC = "namebankrec.required";
	String NAME_BANK = "namebank.required";
	String BANK_BRANCH_ADDRESS = "bankBranchAddr.required";
	String ACC_NUMBER = "accNumber.required";
	String IFS_CODE = "ifsCode.required";
	
	String SSLC_REGISTRATION_REQD = "sslcregistration.reqd";
	String SSLC_PASSING_REQD = "sslcpassing.reqd";
	String SSLC_EDU_BOARD_REQD = "sslcboard.reqd";
	
	String CANDIDATE_POST_REQD = "post.reqd";
	String TEST_CENTER_REQD = "testCenter.reqd";
	
	String CANDIDATE_BIRTH_PLACE_REQD = "candBirthPlace.reqd";
	String FATHER_BIRTH_PLACE_REQD = "fatherBirthPlace.reqd";
	String MOTHER_TONGUE_REQD = "motherTongue.reqd";
	
	String FATHER_FIRST_NAME_REQD = "fatherFirstName.reqd";
	String FATHER_LAST_NAME_REQD = "fatherLastName.reqd";
	
	String MOTHER_FIRST_NAME_REQD = "motherFirstName.reqd";
	String MOTHER_LAST_NAME_REQD = "motherLastName.reqd";
	
	String DISTRICT_REQD = "destrict.reqd";
	String ALT_DISTRICT_REQD = "altDistrict.reqd";
	String TITLE_GENDER_NOT_MATCH = "titleGender.notMatch";
	String ID_MARKS_REQD = "idMarks.reqd";
	String ID_MARKS1_REQD = "idMarks1.reqd";
	String ALTERNATE_ID_REQD = "alternateID.reqd";
	String UPLOAD_IMAGE_SIZE_LIMIT_MESSAGE_MIN="photosizemin.valid";
	String UPLOAD_SIGNATURE_SIZE_LIMIT_MESSAGE_MIN="signsizemin.valid";
	String UPLOAD_DOCUMENT_SIZE_LIMIT_MESSAGE_MIN="minsize.valid";
	String CANDIDATE_COMMUNITY_REQ = "community.reqd";
	String CANDIDATE_COMMUNITY_CERT_REQ="communityCert.reqd";
	String CANDIDATE_RELIGION_COMMUNITY_MISMATCH ="candidate.religionCommunityMismatch";
	String CANDIDATE_CERT_COMMUNITY_MISMATCH ="candidate.certCommunityMismatch";
	String CANDIDATE_MOTHER_FIRST_NAME_REQD = "candidateMotherName.reqd";
	

	//Start of Personal Bean Validator
	String CANDIDATE_DOB = "candidate.dateOfBirth";
	String CANDIDATE_INVALID_DOB = "candidate.dateOfBirthNaN";
	String AGE_REQUIRED = "candidate.ageRequired";
	String INVALID_AGE = "candidate.invalidAge";
	String AGE_ELIGIBILITY = "candidate.AgeEligibility";
	String CANDIDATE_DISABILITY = "candidate.disability";
	String CANDIDATE_DISABILITY_TYPE ="candidate.disabilityType";
	String CANDIATE_CEREBRALPALSY="candidate.cerebralPalsy";
	String CANDIATE_COMPENSATORYTIME="candidate.compensatoryTime";
	String CANDIATE_DOMWRITING="candidate.domWriting";
	String CANDIATE_SERVOFSCRIBE="candidate.servOfScribe";
	String CANDIATE_BLOOD_GROUP="candidate.bloodgroup";
	String EDU_OF_FATHER="candidate.fatherEdu";
	String ORG_OF_FATHER="candidate.fatherOrg";
	String OCCU_OF_FATHER="candidate.fatherOcc";
	String DESIGNATION_OF_FATHER="candidate.fatherDesignation";
	String EDU_OF_MOTHER="candidate.motherEdu";
	String ORG_OF_MOTHER="candidate.motherOrg";
	String OCCU_OF_MOTHER="candidate.motherOcc";
	String DESIGNATION_OF_MOTHER="candidate.motherDesignation";
	String CANDIDATE_DOMICILE_UP = "candidate.domicileUp";
	String CANDIDATE_DOMICILE_CERT = "candidate.upDomicileCert";
	String CANDIDATE_VALID_DOMICILE_CERT = "candidate.validDomicileCert";
	String CANDIDATE_DOMICILE_ISSUE_DATE = "candidate.domicileIssueDate";
	String CANDIDATE_DOMICILE_SERIAL_NO = "candidate.domicileSerialNo";
	String CANDIDATE_VALID_DOMICILE_SERIAL_NO = "candidate.validDomicileSerialNo";
	String CANDIDATE_DOMICILE_APP_NO = "candidate.domicileAppNo";
	String CANDIDATE_VALID_DOMICILE_APP_NO = "candidate.validDomicileAppNo";
	String CANDIDATE_DOB_DOMICILE_COMPARE = "candidate.compareDobDomicile";
	String CANDIDATE_CATEGORY_SELECT = "candidate.categorySelect";
	String CANDIDATE_CERT_ISSUE_AUTH = "candidate.catCertIssueAuth";
	String CANDIDATE_CAT_ISSUE_DATE = "candidate.catIssueDate";
	String CANDIDATE_VALID_CERT_ISSUE_AUTH = "candidate.validCatCertIssueAuth";
	String CANDIDATE_CAT_ISSUE_DATE_COMPARE = "candidate.compareCatIssueDate";
	String CANDIDATE_DOB_CAT_COMPARE = "candidate.compareDobCatIssueDate";
	String CANDIDATE_CAT_SERIAL_NO = "candidate.categorySerialNo";
	String CANDIDATE_VALID_SERIAL_NO = "candidate.validCatSerialNo";
	String CANDIDATE_CERT_APP_NO = "candidate.appNoCat";
	String CANDIDATE_VALID_CERT_APP_NO = "candidate.validCatAppNo";
	String CANDIDATE_FREEDOM_FIGHTER = "candidate.freedomFighter";
	String CANDIDATE_CERT_ISSUE_DATE = "candidate.catCertIssueDate";
	String CANDIDATE_FREEDOM_CERT_ISSUE_DATE = "candidate.freedomFigtherCertIssueDate";
	String CANDIDATE_DOB_FREE_CERT_COMPATE ="candidate.compareDobFreIssueDate";
	String CANDIDATE_FREEDOM_FIGHTER_CERT_NO = "candidate.freedomFighterCertNum";
	String CANDIDATE_VALID_FREEDOM_FIGHTER_CERT_NO = "candidate.validFreedomFighterCertNum";
	String CANDIDATE_FREEDOM_CERT_ISSUE_AUTH="candidate.freedomFighterCertIssueAuth";
	String CANDIDATE_VALID_FREEDOM_CERT_ISSUE_AUTH="candidate.validFreedomFighterCertIssueAuth";
	String CANDIDATE_UP_GOVT_EMP="candidate.upGovEmp";
	String CANDIDATE_GOVT_JOINING_DATE="candidate.govtDateOfJoining";
	String CANDIDATE_AGE_ELIGIBILITY_GOVT_EMP="candidate.eligibilityGovtEmp";
	String CANDIDATE_DOB_GOVT_DT_COMPARE="candidate.compareDobGovtEmp";
	String CANDIDATE_NOC_DT="candidate.nocDate";
	String CANDIDATE_NOC_GREATER_THAN_DOB="candidate.nocGreaterThanDob";
	//added by jyotis
	String BOARD_NAME_REQ = "boardName.req";
	String UNIVERSITY_NAME_REQ ="universityName.req";
	String OTHER_BOARD_NAME_REQ = "otherBoardName.req";
	String OTHER_UNIVERSITY_NAME_REQ = "otherUniversityName.req";
	String INVALID_OTHER_BOARD_NAME = "invalidOtherBoardName.req";
	String INVALID_OTHER_UNIVERSITY_NAME = "invalidOtherUniName.req";
	String TRADE_NAME_REQ = "tradeName.req";
	String DATE_OF_PASSING_REQ = "dateOfPassing.req";
	String ROLL_NO_REQ = "rollNo.req";
	String INVALID_ROLL_NO = "invalidRoll.req";
	String CERTIFICATE_SR_NO_REQ = "certificateSrNo.req";
	String INVALID_CERTIFICATE_SR_NO = "invalid.certificateSrNo";
	String O_LEVEL_CERTIFICATE_REQ = "oLevelCertificate.req";
	String TERRITORIAL_ARMY = "territorial.army";
	String OBTAINED_B_CERTI = "obtainedB.certi";
	String INVALID_DATE_FORMAT ="invalidDate.format";
	String CANDIDATE_NOC_DT_GREATER_THEN_GOVT_DOJ="candidate.nocGreaterThanGovtjoinDt";
	String EMAIL_AND_CONFIRM_EMAIL_NOT_EQUAL="email_confirmEmail.notEqual";
	String NOC_DATE_ELIGIBILITY="candidate.nocEligibility";
	String CANDICATE_PHOTO_CORRUPTED = "candidate.photo.corrupt";
	String CANDIDATE_PHOTO_INVALID_DATA ="candidate.Invalidphoto.data";
	String CANDICATE_SIGNATURE_CORRUPTED = "candidate.signature.corrupt";
	String CANDIDATE_SIGNATURE_INVALID_DATA ="candidate.InvalidSignature.data";
	//additional page
		String EMAIL_REQDREF1 = "email.reqdref1";
		String INVALID_EMAILREF1 = "email.invalidref1";
		String EMAIL_REQDREF2 = "email.reqdref2";
		String INVALID_EMAILREF2 = "email.invalidref2";
}
