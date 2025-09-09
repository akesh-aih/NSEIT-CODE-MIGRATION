package com.nseit.otbs.queries;

public interface SchedulingTestQueries {

	String GET_CITY = " select distinct otcm_CITY from OES_TEST_CENTER_MASTER order by otcm_CITY";
	
	String GET_TEST_CENTRE_BASED_ON_CITY_FOR_ATTEMPT_1 = "SELECT otcm_TEST_CENTRE_NAME,otcm_TEST_CENTRE_PK,otcm_ADDRESS FROM OES_TEST_CENTER_MASTER WHERE otcm_CITY = ? AND otcm_STATUS = 'A' and otcm_STATUS_ATTEMPT_1='A'  order by otcm_TEST_CENTRE_NAME ";
	
	String GET_TEST_CENTRE_BASED_ON_CITY_FOR_ATTEMPT_2 = "SELECT otcm_TEST_CENTRE_NAME,otcm_TEST_CENTRE_PK,otcm_ADDRESS FROM OES_TEST_CENTER_MASTER WHERE otcm_CITY = ? AND otcm_STATUS = 'A' and otcm_STATUS_ATTEMPT_2='A' order by otcm_TEST_CENTRE_NAME ";
	
	String GET_ADDRESS_FOR_TEST_CENTER = "SELECT otcm_ADDRESS FROM OES_TEST_CENTER_MASTER WHERE otcm_TEST_CENTRE_PK =?";
	
	String GET_TEST_DATES_FOR_TEST_CENTER = "SELECT DISTINCT TO_CHAR(obd_TEST_DATE,'DD-MON-YYYY') AS TEST_DATE FROM OES_BATCH_DETAILS WHERE obd_TEST_CENTRE_FK = ? AND trunc(obd_TEST_DATE) between ? and ? and OBD_DATE_WINDOW_FK = ?  ORDER BY TEST_DATE";
	
	String GET_TEST_DATES_FOR_TEST_CENTER_FOR_SECOND_ATTEMPT ="SELECT DISTINCT TO_CHAR(obd_TEST_DATE,'DD-MON-YYYY') AS TEST_DATE FROM OES_BATCH_DETAILS WHERE obd_TEST_CENTRE_FK = ?"+
															 "  AND TRUNC (obd_test_date)>? AND trunc(obd_TEST_DATE) between ? and ?  and OBD_DATE_WINDOW_FK = ? ORDER BY TEST_DATE";
	
	String GET_BATCH_DETAILS_FOR_TEST_CENTER ="SELECT TO_CHAR(obd_TEST_DATE,'DD-MON-YYYY')  TEST_DATE, "+
											  " TO_CHAR(obd_TEST_DATE,'Day')  TEST_DAY, "+
											  " TO_CHAR (obd_TEST_START_TIME, 'HH12:MI')||' '||substr(obd_TEST_START_TIME,-2)  START_TIME, "+
											  " TO_CHAR (obd_TEST_END_TIME, 'HH12:MI')||' '||substr(obd_TEST_END_TIME,-2)  END_TIME, obd_BATCH_PK, (obd_AVAILABLE_CAPACITY - obd_BLOCKED) AS AVAILABLE_SEATS," +
											  " TO_CHAR (obd_REPORTING_TIME, 'HH12:MI')||' '||substr(obd_REPORTING_TIME,-2) as REPORTING_TIME"+ 
											  " FROM OES_BATCH_DETAILS a  "+
											  " WHERE obd_STATUS = 'A' and obd_TEST_CENTRE_FK = ? AND TRUNC(obd_TEST_DATE) BETWEEN ? AND ? " +
											  " and OBD_DATE_WINDOW_FK = ? order by obd_TEST_DATE,obd_test_start_time,AVAILABLE_SEATS ";
	
	
	/*String GET_BATCH_DETAILS_FOR_SECOND_ATTEMPT ="  SELECT   obd_test_centre_fk,TO_CHAR (obd_test_date, 'DD-MON-YYYY') test_date, "+
	 " TO_CHAR (obd_test_date, 'Day') test_day,  TO_CHAR (obd_test_start_time, 'HH12:MI')  || ' ' "+
	 "  || SUBSTR (obd_test_start_time, -2) start_time, TO_CHAR (obd_test_end_time, 'HH12:MI')   || ' ' "+
	 "  || SUBSTR (obd_test_end_time, -2) end_time,  obd_batch_pk, (obd_available_capacity - obd_blocked) AS available_seats "+
	 " FROM OES_batch_details a "+    
	 "	WHERE obd_test_centre_fk = ?  AND TRUNC (obd_test_date)=?   AND TO_CHAR (obd_test_start_time, 'HH24:MI') > ? "+
	 "	AND  TRUNC(obd_TEST_DATE) BETWEEN ? AND ? ORDER BY obd_test_start_time";*/
	
	String GET_BATCH_DETAILS_FOR_SECOND_ATTEMPT = " select * from( SELECT   obd_test_centre_fk, TO_CHAR (obd_test_date, 'DD-MON-YYYY') test_date, "+
													" TO_CHAR (obd_test_date, 'Day') test_day, TO_CHAR (obd_test_start_time, 'HH12:MI') || ' ' || SUBSTR (obd_test_start_time, -2) start_time, "+
													" TO_CHAR (obd_test_end_time, 'HH12:MI') || ' ' || SUBSTR (obd_test_end_time, -2) end_time,  obd_batch_pk,  "+
													" (obd_available_capacity - obd_blocked) AS available_seats   FROM OES_batch_details a "+
													" WHERE obd_test_centre_fk = ?  AND (TRUNC (obd_test_date) BETWEEN ? AND ? or TRUNC (obd_test_date) >= ?)"+     
													" minus "+
													" SELECT   obd_test_centre_fk, TO_CHAR (obd_test_date, 'DD-MON-YYYY') test_date, "+
													" TO_CHAR (obd_test_date, 'Day') test_day,  TO_CHAR (obd_test_start_time, 'HH12:MI') "+
													" || ' '  || SUBSTR (obd_test_start_time, -2) start_time,   TO_CHAR (obd_test_end_time, 'HH12:MI')  || ' ' || SUBSTR (obd_test_end_time, -2) end_time,"+
													" obd_batch_pk,     (obd_available_capacity - obd_blocked) AS available_seats "+
													" FROM OES_batch_details a   WHERE obd_test_centre_fk = ? "+ 
													"and TRUNC (obd_test_date) <= ?  AND TO_CHAR( obd_test_end_time, 'HH24:MI')<= ? ) ORDER BY available_seats, start_time"; 

	String INSERT_ENROLLMENT_DTLS  = "Insert into OES_ENTROLLMENT_DETAILS "+
												  " (oed_ENROLLMENT_PK, oed_GCPD_USER_FK, oed_LANGUAGE_FK, oed_BATCH_FK,  "+
												   " oed_STATUS, oed_ATTEMPT, oed_CREATED_BY, oed_CREATED_DATE, oed_UPDATED_BY, "+
												   " oed_UPDATED_DATE)  Values "+
												   "(OES_ENTROLLMENT_DETAILS_SEQ.nextVal, ?, ?, ?, "+
												   " 'A', ?, ?, sysdate,?, "+
												   " sysdate)";
	
							    
	String INSERT_BAR_CODE_IMAGE = "Insert into OES_BARCODE_DETAILS " +
											    "(OBRD_ENROLLMENT_FK, OBRD_BARCODE_IMAGE) Values" +
											    "(?, ?) ";
	
	String UPDATE_STAGE_CANDIDATE = "update OES_USER_MASTER set OUM_STAGE_FK =? ,OUM_UPDATED_BY=?, OUM_UPDATED_DATE = sysdate where OUM_USER_PK =?";
	String UPDATE_BATCH_CAPCITY_TO_HOLD_SEAT =" UPDATE OES_batch_details      SET obd_blocked = obd_blocked + 1 "+
                                      " WHERE obd_batch_pk = ?";
	
	String UPDATE_BATCH_CAPCITY_RELEASE_SEAT =" UPDATE OES_batch_details      SET obd_blocked = obd_blocked - 1 "+
    " WHERE obd_batch_pk = ?";
	
	String UPDATE_BATCH_CAPCITY_TO_CONFIRM_SEAT =" UPDATE OES_batch_details "+
      										  " SET obd_blocked = obd_blocked - 1,     obd_available_capacity = obd_available_capacity - 1 "+
      										  " WHERE obd_batch_pk = ?";
	
	String DELETE_BLOCKED_DETAILS ="DELETE FROM OES_blocked_details  WHERE gbld_user_fk = ?";
	
	String INSERT_BLOCKED_DETAILS = " INSERT INTO OES_blocked_details    VALUES (?, ?, SYSTIMESTAMP)";
	
	String GET_SEAT_COUNT_FOR_CANDIDATE = "SELECT (obd_AVAILABLE_CAPACITY-obd_BLOCKED) AS AVAILABLE_SEATS FROM OES_BATCH_DETAILS WHERE obd_BATCH_PK = ?";
	
	String GET_BATCH_DETAILS_OF_FIRST_ATTEMPT =" SELECT TO_CHAR (obd_test_date, 'DD-MON-YYYY') as testDate ,TO_CHAR (obd_test_end_time, 'HH24:MI') as testEndTime "+ 
											   " FROM  OES_BATCH_DETAILS "+
											   " WHERE obd_BATCH_PK IN (SELECT oed_BATCH_FK FROM OES_ENTROLLMENT_DETAILS WHERE  oed_ATTEMPT =1 AND oed_GCPD_USER_FK = ?  )";
	
	String GET_DOWNLOAD_PATH = " select OCPT_PATH_VALUE from OES_CONFIG_PATHS  where OCPT_STATUS='A' and OCPT_PATH_NAME = ? ";
	
	String GET_ENROLLMENT_DETAILS_BASED_ON_USER_ID = "SELECT oed_ENROLLMENT_PK enrollmentPK, oed_ATTEMPT batchAttempt FROM OES_ENTROLLMENT_DETAILS WHERE oed_GCPD_USER_FK =? AND oed_ATTEMPT = ?";
	
	String GET_SCHEDULINGTEST_BEAN_BASED_ON_USER_ID_ATTEMPT_TYPE = "select OTM_TEST_NAME as discipline, OCD_NAME as candidateName, otcm_TEST_CENTRE_NAME as testCenter, otcm_ADDRESS as tstCenterAddress, TO_CHAR (obd_TEST_DATE, 'DD-MON-YYYY') as testDate, TO_CHAR (obd_REPORTING_TIME, 'HH12:MI')||' '||substr(obd_REPORTING_TIME,-2) as reportingTime, TO_CHAR (obd_TEST_START_TIME, 'HH12:MI')||' '||substr(obd_TEST_START_TIME,-2) as testTime, ged.OED_ROLL_NO  from OES_user_master gum, OES_test_master gtm, OES_CANDIDATE_DETAILS gcd , OES_ENTROLLMENT_DETAILS ged, OES_batch_details gbd, OES_test_center_master gtcm where gum.OUM_TEST_FK = gtm.OTM_TEST_PK and gum.OUM_USER_PK = gcd.OCD_USER_FK and gum.OUM_USER_PK = ged.oed_GCPD_USER_FK  and ged.oed_BATCH_FK = gbd.obd_BATCH_PK and gbd.obd_TEST_CENTRE_FK = gtcm.otcm_TEST_CENTRE_PK and gum.OUM_USER_PK = ? and ged.oed_ENROLLMENT_PK = ? and ged.oed_ATTEMPT = ?";

	
	String GET_HALLTICKET_DATA = "SELECT otm.otm_test_name, oum.oum_user_id, oed.oed_enrollment_pk , oed.OED_ROLL_NO ,oed.OED_FLAG, " +
			"   oum.oum_password, ocd.OCD_CAND_TITLE,ocd.OCD_FIRST_NAME,ocd.OCD_MIDDLE_NAME,ocd.OCD_LAST_NAME, otcm.otcm_address,   otcm.otcm_test_centre_name, " +
			" TO_CHAR (obd_test_date, 'DD-MON-YYYY') test_date,   " +
			"  TO_CHAR (obd_test_start_time, 'HH12:MI')      || ' '    || SUBSTR (obd_test_start_time, -2) start_time, " +
			"     TO_CHAR (obd_reporting_time, 'HH12:MI')  || ' '   || SUBSTR (obd_reporting_time, -2) AS reporting_time " +
			"  FROM oes_user_master oum,      oes_entrollment_details oed,  oes_batch_details obd,  oes_test_master otm, oes_test_center_master otcm, " +
			" oes_candidate_details ocd WHERE oum.oum_user_pk = ocd.ocd_user_fk AND oum.oum_user_pk = oed.oed_gcpd_user_fk " +
			" AND oed.oed_gcpd_user_fk = ocd.ocd_user_fk  AND oed.oed_batch_fk = obd.obd_batch_pk " +
			" AND otm.otm_test_pk = oum.oum_test_fk  AND otcm.otcm_test_centre_pk = obd.obd_test_centre_fk " +
			" AND oed.oed_attempt = ?  AND ocd.ocd_user_fk = ?";
	
	
	
	String GET_HALLTICKET_DATA_FOR_USER_ID = "SELECT otm.otm_test_name, oum.oum_user_id, oed.oed_enrollment_pk , oed.OED_ROLL_NO ,oed.OED_FLAG, " +
	"   oum.oum_password, ocd.OCD_CAND_TITLE,ocd.OCD_FIRST_NAME,ocd.OCD_MIDDLE_NAME,ocd.OCD_LAST_NAME, otcm.otcm_address,   otcm.otcm_test_centre_name, " +
	" TO_CHAR (obd_test_date, 'DD-MON-YYYY') test_date,   " +
	"  TO_CHAR (obd_test_start_time, 'HH12:MI')      || ' '    || SUBSTR (obd_test_start_time, -2) start_time, " +
	"     TO_CHAR (obd_reporting_time, 'HH12:MI')  || ' '   || SUBSTR (obd_reporting_time, -2) AS reporting_time " +
	"  FROM oes_user_master oum,      oes_entrollment_details oed,  oes_batch_details obd,  oes_test_master otm, oes_test_center_master otcm, " +
	" oes_candidate_details ocd WHERE oum.oum_user_pk = ocd.ocd_user_fk AND oum.oum_user_pk = oed.oed_gcpd_user_fk " +
	" AND oed.oed_gcpd_user_fk = ocd.ocd_user_fk  AND oed.oed_batch_fk = obd.obd_batch_pk " +
	" AND otm.otm_test_pk = oum.oum_test_fk  AND otcm.otcm_test_centre_pk = obd.obd_test_centre_fk " +
	" AND oed.oed_attempt = ?  AND oum.oum_user_id = ?";

	
	String GET_CANDIDATE_DETAILS_OF_BOOKING = " select oed.OED_ENROLLMENT_PK from oes_entrollment_details oed where oed.OED_GCPD_USER_FK = ?";
	
}
