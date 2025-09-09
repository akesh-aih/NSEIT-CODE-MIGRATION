package com.nseit.generic.queries;

public interface ReportQueries {
	
	
	String GET_TEST_DATES = "select obd_test_date from " +
							"(select  distinct TO_CHAR (obd_test_date, 'dd-Mon-yyyy') AS obd_test_date " +
							" from oes_batch_details where OBD_TEST_CENTRE_FK = ?) order by obd_test_date";

	
	String GET_TEST_SLOTS = "select distinct batch_slot from( select TO_CHAR (obd_test_start_time, 'hh:mi')  " +
			"   || '-'  || TO_CHAR (obd_test_end_time, 'hh:mi') AS batch_slot ,rank()" +
			" over( order by OBD_TEST_CENTRE_FK,obd_test_date) as rnk from oes_batch_details obd   " +
			" where trunc(OBD_TEST_DATE)=? order by obd_batch_pk )where rnk=1  group by batch_slot,rnk";
	
	
	
	String GET_HALLTICKET_DATA_FOR_BULK_DOWNLOAD_SORT_BY_ENROLLMENT_ID = "SELECT OTM_TEST_NAME, OUM_USER_ID, OED_ENROLLMENT_PK, OCD_CAND_TITLE || ' ' || OCD_FIRST_NAME || ' ' || OCD_MIDDLE_NAME || ' ' || OCD_LAST_NAME as OCD_NAME, OTCM_TEST_CENTRE_NAME, OED_ATTEMPT, "+ 
		" OTCM_ADDRESS, TO_CHAR (OBD_TEST_DATE, 'DD-MON-YYYY')  as TEST_DATE, TO_CHAR (OBD_REPORTING_TIME, 'HH12:MI')||' '||substr(OBD_REPORTING_TIME,-2) as REPORTING_TIME, TO_CHAR (OBD_TEST_START_TIME, 'HH12:MI')||' '||substr(OBD_TEST_START_TIME,-2)  as EXAM_START_TIME, OUM_PASSWORD, oed.OED_ROLL_NO, OCI_PHOTO_IMAGE, OCI_SIGN_IMAGE  "+ 
		" FROM oes_user_master oum,oes_test_master otm, OES_ENTROLLMENT_DETAILS oed, oes_candidate_details ocd, oes_batch_details obd, oes_test_center_master otcm, oes_candidate_images oci "+
		" where oum.OUM_TEST_FK = otm.OTM_TEST_PK "+ 
		" AND oum.OUM_USER_PK = oed.OED_GCPD_USER_FK "+
		" AND oum.OUM_USER_PK = ocd.OCD_USER_FK  "+
		" AND oed.OED_BATCH_FK = obd.OBD_BATCH_PK  "+
		" AND obd.OBD_TEST_CENTRE_FK = otcm.OTCM_TEST_CENTRE_PK "+
		" AND oum.OUM_USER_PK = oci.oci_user_fk(+) "+
		" AND oed.OED_ATTEMPT = DECODE (?, 'All', oed.OED_ATTEMPT, ?) "+ 
		" AND TRUNC (obd.obd_test_date) = decode(?,'All',TRUNC (obd.obd_test_date),?) "+
		" AND otcm.otcm_test_centre_pk = DECODE (?, 'All', otcm.otcm_test_centre_pk, ?) "+
		" AND otm.otm_test_pk = DECODE (?, 'All', otm.otm_test_pk,?) "+
		" AND TO_CHAR (obd_test_start_time, 'hh:mi')|| '-'|| TO_CHAR (obd_test_end_time, 'hh:mi')= decode(?,'All',TO_CHAR (obd_test_start_time, 'hh:mi')|| '-'|| TO_CHAR (obd_test_end_time, 'hh:mi'),?) "+
		" order by OED_ENROLLMENT_PK,obd.obd_test_date,TO_CHAR (obd_test_start_time, 'hh:mi')|| '-'|| TO_CHAR (obd_test_end_time, 'hh:mi')";
	

	String GET_HALLTICKET_DATA_FOR_BULK_DOWNLOAD_SORT_BY_ROLL_NUMBER = "SELECT OTM_TEST_NAME, OUM_USER_ID, OED_ENROLLMENT_PK, OCD_CAND_TITLE || ' ' || OCD_FIRST_NAME || ' ' || OCD_MIDDLE_NAME || ' ' || OCD_LAST_NAME as OCD_NAME, OTCM_TEST_CENTRE_NAME, OED_ATTEMPT, "+ 
    " OTCM_ADDRESS, TO_CHAR (OBD_TEST_DATE, 'DD-MON-YYYY')  as TEST_DATE, TO_CHAR (OBD_REPORTING_TIME, 'HH12:MI')||' '||substr(OBD_REPORTING_TIME,-2) as REPORTING_TIME, TO_CHAR (OBD_TEST_START_TIME, 'HH12:MI')||' '||substr(OBD_TEST_START_TIME,-2)  as EXAM_START_TIME, OUM_PASSWORD, oed.OED_ROLL_NO, OCI_PHOTO_IMAGE, OCI_SIGN_IMAGE  "+ 
    " FROM oes_user_master oum,oes_test_master otm, OES_ENTROLLMENT_DETAILS oed, oes_candidate_details ocd, oes_batch_details obd, oes_test_center_master otcm, oes_candidate_images oci "+
    " where oum.OUM_TEST_FK = otm.OTM_TEST_PK "+ 
    " AND oum.OUM_USER_PK = oed.OED_GCPD_USER_FK "+
    " AND oum.OUM_USER_PK = ocd.OCD_USER_FK  "+
    " AND oed.OED_BATCH_FK = obd.OBD_BATCH_PK  "+
    " AND obd.OBD_TEST_CENTRE_FK = otcm.OTCM_TEST_CENTRE_PK "+
    " AND oum.OUM_USER_PK = oci.oci_user_fk(+) "+
    " AND oed.OED_ATTEMPT = DECODE (?, 'All', oed.OED_ATTEMPT, ?) "+ 
    " AND TRUNC (obd.obd_test_date) = decode(?,'All',TRUNC (obd.obd_test_date),?) "+
    " AND otcm.otcm_test_centre_pk = DECODE (?, 'All', otcm.otcm_test_centre_pk, ?) "+
    " AND otm.otm_test_pk = DECODE (?, 'All', otm.otm_test_pk,?) "+
    " AND TO_CHAR (obd_test_start_time, 'hh:mi')|| '-'|| TO_CHAR (obd_test_end_time, 'hh:mi')= decode(?,'All',TO_CHAR (obd_test_start_time, 'hh:mi')|| '-'|| TO_CHAR (obd_test_end_time, 'hh:mi'),?) "+
    " order by OED_ENROLLMENT_PK,obd.obd_test_date,TO_CHAR (obd_test_start_time, 'hh:mi')|| '-'|| TO_CHAR (obd_test_end_time, 'hh:mi')";
	
	
	String GET_SCHEDULE_REPORT_COUNT = "SELECT COUNT(1)" +
						 " FROM OES_USER_MASTER OUM,OES_TEST_MASTER OTM,OES_CANDIDATE_DETAILS OCD," +
						 " (SELECT OTCM_TEST_CENTRE_PK,OED_FLAG, OED_GCPD_USER_FK, OTCM_TEST_CENTRE_NAME, OBD_TEST_DATE , OBD_TEST_START_TIME, OBD_TEST_END_TIME, OED_ADMIT_CARD_DATE," +
						 " OED_CREATED_DATE FROM OES_ENTROLLMENT_DETAILS OED, OES_BATCH_DETAILS OBD, OES_TEST_CENTER_MASTER OTCM" +
						 " WHERE OED.OED_BATCH_FK = OBD.OBD_BATCH_PK" +
						 " AND OBD.OBD_TEST_CENTRE_FK = OTCM.OTCM_TEST_CENTRE_PK) MAIN" +
						 " WHERE OUM.OUM_USER_PK = OCD.OCD_USER_FK" +
						 " AND OUM.OUM_TEST_FK = OTM.OTM_TEST_PK" +
						 " AND OCD.OCD_USER_FK = MAIN.OED_GCPD_USER_FK(+)" ;
	
	String GET_SCHEDULE_REPORT = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,OCD_USER_FK,OCD_VALIDATED_STATUS,OUM_USER_ID, " +
			" OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME, " +
			"OTM_TEST_NAME, OUM_MOBILE_NO, OUM_EMAIL_ID," +
								 " TO_CHAR (OBD_TEST_DATE, 'DD-MON-YYYY') AS TEST_DATE,OTCM_TEST_CENTRE_NAME," +
								 " TO_CHAR (OBD_TEST_START_TIME, 'HH12:MI')||'-'||TO_CHAR (OBD_TEST_END_TIME, 'HH12:MI')||' '||SUBSTR(OBD_TEST_END_TIME,-2) AS BATCH," +
								 " TO_CHAR(OED_CREATED_DATE,'DD-MON-YYYY') AS SCHEDULE_DATE, TO_CHAR(OED_ADMIT_CARD_DATE, 'DD-MON-YYYY') AS ADMIT_DATE" +
								 " FROM OES_USER_MASTER OUM,OES_TEST_MASTER OTM,OES_CANDIDATE_DETAILS OCD," +
								 " (SELECT OTCM_TEST_CENTRE_PK,OED_FLAG, OED_GCPD_USER_FK, OTCM_TEST_CENTRE_NAME, OBD_TEST_DATE , OBD_TEST_START_TIME, OBD_TEST_END_TIME, OED_ADMIT_CARD_DATE," +
								 " OED_CREATED_DATE FROM OES_ENTROLLMENT_DETAILS OED, OES_BATCH_DETAILS OBD, OES_TEST_CENTER_MASTER OTCM" +
								 " WHERE OED.OED_BATCH_FK = OBD.OBD_BATCH_PK" +
								 " AND OBD.OBD_TEST_CENTRE_FK = OTCM.OTCM_TEST_CENTRE_PK) MAIN" +
								 " WHERE OUM.OUM_USER_PK = OCD.OCD_USER_FK" +
								 " AND OUM.OUM_TEST_FK = OTM.OTM_TEST_PK" +
								 " AND OCD.OCD_USER_FK = MAIN.OED_GCPD_USER_FK(+)";
	
	String GET_PAYMENT_REPORT = "SELECT OUM.OUM_USER_ID, OCD.OCD_FIRST_NAME||' '||OCD.OCD_MIDDLE_NAME||' '||OCD.OCD_LAST_NAME AS CND_NAME, OTM.OTM_TEST_NAME, OPM.OPTM_PAYMENT_CODE, " +
								 " TO_CHAR(OPD.OPD_CREATED_DATE,'DD-MON-YYYY') AS OPD_CREATED_DATE , OPD.OPD_AMOUNT, OPD.OPD_BANK_NAME," +
								 " DECODE(OPD.OPD_VALIDATED_STATUS,'A','APPROVED','R','REJECTED') AS OPD_VALIDATED_STATUS,OPD.OPD_REMARKS " +
								 " FROM OES_USER_MASTER OUM,OES_TEST_MASTER OTM, OES_PAYMENT_DETAILS OPD, OES_CANDIDATE_DETAILS OCD, OES_PAYMENT_MASTER OPM" +
								 " WHERE OUM.OUM_USER_PK = OCD.OCD_USER_FK" +
								 " AND OUM.OUM_TEST_FK = OTM.OTM_TEST_PK AND OUM.OUM_USER_PK = OPD.OPD_USER_FK AND OPD.OPD_PAYMENT_FK = OPM.OPTM_PAYMENT_PK" +
								 " AND OPD.OPD_VALIDATED_STATUS = DECODE(?,'All',OPD.OPD_VALIDATED_STATUS,?)" +
								 " AND OTM.OTM_TEST_PK = DECODE(?,0,OTM.OTM_TEST_PK,?)" +
								 " AND OPM.OPTM_PAYMENT_PK = DECODE(?,0,OPM.OPTM_PAYMENT_PK,?)" +
								 " AND TRUNC(OPD.OPD_CREATED_DATE) BETWEEN DECODE(?,'',TRUNC(OPD.OPD_CREATED_DATE),?) " +
								 " AND DECODE(?,'',TRUNC(OPD.OPD_CREATED_DATE),?)" +
								 " AND OUM.OUM_USER_ID = DECODE(?,'',OUM.OUM_USER_ID,?)" +
								 " ORDER BY OUM.OUM_USER_ID";
	
	String GET_PAYMENT_COLLECTION_REPORT_COUNT =" SELECT COUNT(1) "+
												" FROM (SELECT ROW_NUMBER () OVER (ORDER BY OPD_CREATED_DATE) AS NUM, MAIN.* "+  
												" FROM ( "+
												" SELECT to_char (opd.opd_created_date,'dd-MON-yyyy') AS opd_created_date, "+
												" opm.optm_payment_code, opm.optm_payment_desc,  "+
												" SUM (CAST(opd.opd_amount as FLOAT)) AS opd_amount "+
												" FROM oes_payment_details opd, oes_payment_master opm "+
												" WHERE opd.opd_payment_fk = opm.optm_payment_pk "+
												" AND opm.optm_payment_pk = "+
												" CASE WHEN ? = 0 THEN opm.optm_payment_pk ELSE ? END "+
												" AND  OPD_CREATED_DATE::date   BETWEEN CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-1990'),'dd-Mon-yyyy')  END "+
												" AND CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-2300'),'dd-Mon-yyyy')  END "+
												" AND opd.opd_validated_status = 'A' "+
												" and opm.optm_payment_pk <> 5 "+
												" GROUP BY to_char (opd.opd_created_date,'dd-MON-yyyy'),opm.optm_payment_code, "+
												" opm.optm_payment_desc "+
												" UNION "+
												" SELECT to_char(oes_payment_details.opd_created_date, 'dd-Mon-yyyy') AS opd_created_date, "+
												" OES_PAYMENT_MASTER.optm_payment_code, OES_PAYMENT_MASTER.optm_payment_desc,  "+
												" SUM (CAST(oes_payment_details.opd_amount as FLOAT)) AS opd_amount  "+
												" from oes_user_master "+
												" inner join oes_candidate_details ON (oes_candidate_details.ocd_user_fk = oes_user_master.oum_user_pk) "+
												" left outer join oes_candidate_job_mapping ON (oes_candidate_job_mapping.ocjm_user_fk = oes_user_master.oum_user_pk) "+
												" left outer join oes_epost_rec ON (oes_epost_rec.rec_apl_no = oes_candidate_job_mapping.ocjm_applicationnumber) "+
												" left outer join oes_payment_details ON (oes_payment_details.opd_user_fk = oes_user_master.oum_user_pk) "+
												" left outer join OES_PAYMENT_MASTER ON oes_payment_details.OPD_PAYMENT_FK = OES_PAYMENT_MASTER.OPTM_PAYMENT_PK "+
												" where oes_payment_details.opd_payment_fk = 5 and "+
												" OES_PAYMENT_MASTER.OPTM_PAYMENT_PK = "+
												" CASE WHEN ? = 0 THEN OES_PAYMENT_MASTER.OPTM_PAYMENT_PK ELSE ? END   "+ 
												" AND  OPD_CREATED_DATE::date   BETWEEN CASE WHEN  ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-1990'),'dd-Mon-yyyy')  END "+
												" AND CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-2300'),'dd-Mon-yyyy')  END "+
												" and OPD_VALIDATED_STATUS='A' "+
												" AND oes_epost_rec.rec_trsc_dt NOT LIKE '%gdgdgd%' "+
												" GROUP BY to_char(oes_payment_details.opd_created_date, 'dd-Mon-yyyy'), optm_payment_code, optm_payment_desc "+
												" ) main) test ";
		/*"SELECT COUNT(1)" +
								 " FROM (SELECT ROW_NUMBER () OVER (ORDER BY OPD_CREATED_DATE) AS NUM, MAIN.*  " +
								 " FROM (SELECT to_char (opd.opd_created_date,'dd-MON-yyyy') AS opd_created_date," +
								 " opm.optm_payment_code, opm.optm_payment_desc, " +
								 " SUM (CAST(opd.opd_amount as FLOAT)) AS opd_amount" +
								 " FROM oes_payment_details opd, oes_payment_master opm" +
								 " WHERE opd.opd_payment_fk = opm.optm_payment_pk" +
								 " AND opm.optm_payment_pk =" +
								 " CASE WHEN ? = 0 THEN opm.optm_payment_pk ELSE ? END" +
								 " AND to_char (OPD.OPD_CREATED_DATE,'dd-MON-yyyy') BETWEEN CASE WHEN ? = '' THEN to_char (OPD.OPD_CREATED_DATE,'dd-MON-yyyy') ELSE ? END" +
								 " AND CASE WHEN ? = '' THEN to_char (OPD.OPD_CREATED_DATE,'dd-MON-yyyy') ELSE ? END" +
								 " AND opd.opd_validated_status = 'A' GROUP BY to_char (opd.opd_created_date,'dd-MON-yyyy'),opm.optm_payment_code," +
								 " opm.optm_payment_desc ORDER BY to_char (opd.opd_created_date,'dd-MON-yyyy')) main) test" ;*/
	
	/*String GET_PAYMENT_COLLECTION_REPORT = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY opd_created_date) AS num, main.* from (select to_char(OPD.OPD_CREATED_DATE,'dd-MON-yyyy') AS OPD_CREATED_DATE," +
								 " OPM.OPTM_PAYMENT_CODE, OPM.OPTM_PAYMENT_DESC,SUM (CAST(OPD.OPD_AMOUNT as FLOAT)) AS OPD_AMOUNT" +
								 " FROM OES_PAYMENT_DETAILS OPD, OES_PAYMENT_MASTER OPM  " +
								 " WHERE OPD.OPD_PAYMENT_FK = OPM.OPTM_PAYMENT_PK" +
								 " AND OPM.OPTM_PAYMENT_PK = CASE WHEN ? = 0 THEN OPM.OPTM_PAYMENT_PK ELSE ? END " +
								 " AND to_char (OPD.OPD_CREATED_DATE,'dd-MON-yyyy') BETWEEN CASE WHEN ? = '' THEN to_char (OPD.OPD_CREATED_DATE,'dd-MON-yyyy') ELSE ? END" +
								 " AND CASE WHEN ? = '' THEN to_char (OPD.OPD_CREATED_DATE,'dd-MON-yyyy') ELSE ? END" +
								 " and opd.OPD_VALIDATED_STATUS='A' "+
								 " GROUP BY to_char(OPD.OPD_CREATED_DATE,'dd-MON-yyyy'),OPM.OPTM_PAYMENT_CODE,OPM.OPTM_PAYMENT_DESC" +
								 " ORDER BY to_char(OPD.OPD_CREATED_DATE,'dd-MON-yyyy')) main) test" +
								 " WHERE NUM BETWEEN ? AND ?";*/
	String GET_PAYMENT_COLLECTION_REPORT ="SELECT *"+ 
	" FROM ("+
	"	SELECT"+ 
	"		ROW_NUMBER() OVER (ORDER BY opd_created_date) AS num, main.* "+
	"	from ("+
	"		select"+ 
	"			to_char(OPD.OPD_CREATED_DATE,'dd-MON-yyyy') AS OPD_CREATED_DATE,"+
	"			OPM.OPTM_PAYMENT_CODE,"+ 
	"			OPM.OPTM_PAYMENT_DESC,"+
	"			SUM (CAST(OPD.OPD_AMOUNT as FLOAT)) AS OPD_AMOUNT"+
	"		FROM OES_PAYMENT_DETAILS OPD, OES_PAYMENT_MASTER OPM"+ 
	"		WHERE OPD.OPD_PAYMENT_FK = OPM.OPTM_PAYMENT_PK"+
	"		AND OPM.OPTM_PAYMENT_PK = CASE WHEN ? = 0 THEN OPM.OPTM_PAYMENT_PK ELSE ? END "+
	"		 AND  OPD_CREATED_DATE::date   BETWEEN CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-1990'),'dd-Mon-yyyy')  END "+
	" AND CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-2300'),'dd-Mon-yyyy')  END "+
	"		and opd.OPD_VALIDATED_STATUS='A'"+
	"		and opd.opd_payment_fk = 1"+
	"	GROUP BY to_char(OPD.OPD_CREATED_DATE,'dd-MON-yyyy'),OPM.OPTM_PAYMENT_CODE,OPM.OPTM_PAYMENT_DESC"+
	"		UNION ALL"+
	"		select "+ 
	"			to_char(oes_payment_details.opd_created_date, 'dd-Mon-yyyy'),"+
	"			NULL as code,"+
	"			'Offline Post Office Payment' as optm_payment_desc,"+
	"			SUM(rec_fees::numeric(16,0))"+
	"		from oes_user_master"+
	"		inner join oes_candidate_details ON (oes_candidate_details.ocd_user_fk = oes_user_master.oum_user_pk)"+
	"		left outer join oes_candidate_job_mapping ON (oes_candidate_job_mapping.ocjm_user_fk = oes_user_master.oum_user_pk)"+
	"		left outer join oes_epost_rec ON (oes_epost_rec.rec_apl_no = oes_candidate_job_mapping.ocjm_applicationnumber)"+
	"		left outer join oes_payment_details ON (oes_payment_details.opd_user_fk = oes_user_master.oum_user_pk)"+
	"       left outer join OES_PAYMENT_MASTER ON oes_payment_details.OPD_PAYMENT_FK = OES_PAYMENT_MASTER.OPTM_PAYMENT_PK"+
	"		where oes_payment_details.opd_payment_fk = 5 and"+
	"       OES_PAYMENT_MASTER.OPTM_PAYMENT_PK = CASE WHEN ? = 0 THEN OES_PAYMENT_MASTER.OPTM_PAYMENT_PK ELSE ? END "+  
	"		and OPD_VALIDATED_STATUS='A'"+
	" AND  OPD_CREATED_DATE::date   BETWEEN CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-1990'),'dd-Mon-yyyy')  END "+
	" AND CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-2300'),'dd-Mon-yyyy')  END "+
	"		AND oes_epost_rec.rec_trsc_dt NOT LIKE '%gdgdgd%' "+
	"		GROUP BY to_char(oes_payment_details.opd_created_date, 'dd-Mon-yyyy')"+
	"		ORDER BY 1"+
	"	    ) main"+
	" ) test"+
	" WHERE NUM BETWEEN ? AND ?";

	
	/*String GET_PAYMENT_COLLECTION_REPORT_EXCEL = "SELECT TO_CHAR(OPD.OPD_CREATED_DATE, 'DD-MON-YYYY') AS OPD_CREATED_DATE," +
								 " OPM.OPTM_PAYMENT_CODE, OPM.OPTM_PAYMENT_DESC,SUM (CAST(OPD.OPD_AMOUNT as FLOAT)) AS OPD_AMOUNT" +
								 " FROM OES_PAYMENT_DETAILS OPD, OES_PAYMENT_MASTER OPM  " +
								 " WHERE OPD.OPD_PAYMENT_FK = OPM.OPTM_PAYMENT_PK" +
								 " AND OPM.OPTM_PAYMENT_PK = CASE WHEN ? = 0 THEN OPM.OPTM_PAYMENT_PK ELSE ? END " +
								 " AND TO_CHAR (OPD.OPD_CREATED_DATE, 'DD-MON-YYYY') BETWEEN CASE WHEN ? = '' THEN to_char (OPD.OPD_CREATED_DATE, 'DD-MON-YYYY') ELSE ? END" +
								 " AND CASE WHEN ? = '' THEN TO_CHAR (OPD.OPD_CREATED_DATE, 'DD-MON-YYYY') ELSE ? END" +
								 " and opd.OPD_VALIDATED_STATUS='A' "+
								 " GROUP BY TO_CHAR(OPD.OPD_CREATED_DATE, 'DD-MON-YYYY'),OPM.OPTM_PAYMENT_CODE,OPM.OPTM_PAYMENT_DESC" +
								 " ORDER BY TO_CHAR(OPD.OPD_CREATED_DATE, 'DD-MON-YYYY')";*/
	
	String GET_PAYMENT_COLLECTION_REPORT_EXCEL ="SELECT *"+ 
	" FROM ("+
	"	SELECT"+ 
	"		ROW_NUMBER() OVER (ORDER BY opd_created_date) AS num, main.* "+
	"	from ("+
	"		select"+ 
	"			to_char(OPD.OPD_CREATED_DATE,'dd-MON-yyyy') AS OPD_CREATED_DATE,"+
	"			OPM.OPTM_PAYMENT_CODE,"+ 
	"			OPM.OPTM_PAYMENT_DESC,"+
	"			SUM (CAST(OPD.OPD_AMOUNT as FLOAT)) AS OPD_AMOUNT"+
	"		FROM OES_PAYMENT_DETAILS OPD, OES_PAYMENT_MASTER OPM"+ 
	"		WHERE OPD.OPD_PAYMENT_FK = OPM.OPTM_PAYMENT_PK"+
	"		AND OPM.OPTM_PAYMENT_PK = CASE WHEN ? = 0 THEN OPM.OPTM_PAYMENT_PK ELSE ? END "+
	"		 AND  OPD_CREATED_DATE::date   BETWEEN CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-1990'),'dd-Mon-yyyy')  END "+
	" AND CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-2300'),'dd-Mon-yyyy')  END "+
	"		and opd.OPD_VALIDATED_STATUS='A'"+
	"		and opd.opd_payment_fk = 1"+
	"	GROUP BY to_char(OPD.OPD_CREATED_DATE,'dd-MON-yyyy'),OPM.OPTM_PAYMENT_CODE,OPM.OPTM_PAYMENT_DESC"+
	"		UNION ALL"+
	"		select "+ 
	"			to_char(oes_payment_details.opd_created_date, 'dd-Mon-yyyy'),"+
	"			NULL as code,"+
	"			'Offline Post Office Payment' as optm_payment_desc,"+
	"			SUM(rec_fees::numeric(16,0))"+
	"		from oes_user_master"+
	"		inner join oes_candidate_details ON (oes_candidate_details.ocd_user_fk = oes_user_master.oum_user_pk)"+
	"		left outer join oes_candidate_job_mapping ON (oes_candidate_job_mapping.ocjm_user_fk = oes_user_master.oum_user_pk)"+
	"		left outer join oes_epost_rec ON (oes_epost_rec.rec_apl_no = oes_candidate_job_mapping.ocjm_applicationnumber)"+
	"		left outer join oes_payment_details ON (oes_payment_details.opd_user_fk = oes_user_master.oum_user_pk)"+
	"       left outer join OES_PAYMENT_MASTER ON oes_payment_details.OPD_PAYMENT_FK = OES_PAYMENT_MASTER.OPTM_PAYMENT_PK"+
	"		where oes_payment_details.opd_payment_fk = 5 and"+
	"       OES_PAYMENT_MASTER.OPTM_PAYMENT_PK = CASE WHEN ? = 0 THEN OES_PAYMENT_MASTER.OPTM_PAYMENT_PK ELSE ? END "+  
	"		and OPD_VALIDATED_STATUS='A'"+
	" AND  OPD_CREATED_DATE::date   BETWEEN CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-1990'),'dd-Mon-yyyy')  END "+
	" AND CASE WHEN ? ::varchar = ' ' THEN  OPD_CREATED_DATE::date   ELSE to_date(replace(?,' ','01-Jan-2300'),'dd-Mon-yyyy')  END "+
	"		AND oes_epost_rec.rec_trsc_dt NOT LIKE '%gdgdgd%' "+
	"		GROUP BY to_char(oes_payment_details.opd_created_date, 'dd-Mon-yyyy')"+
	"		ORDER BY 1"+
	"	    ) main"+
	" ) test";/*"SELECT *"+ 
	" FROM ("+
	"	SELECT"+ 
	"		ROW_NUMBER() OVER (ORDER BY opd_created_date) AS num, main.* "+
	"	from ("+
	"		select"+ 
	"			to_char(OPD.OPD_CREATED_DATE,'dd-MON-yyyy') AS OPD_CREATED_DATE,"+
	"			OPM.OPTM_PAYMENT_CODE,"+ 
	"			OPM.OPTM_PAYMENT_DESC,"+
	"			SUM (CAST(OPD.OPD_AMOUNT as FLOAT)) AS OPD_AMOUNT"+
	"		FROM OES_PAYMENT_DETAILS OPD, OES_PAYMENT_MASTER OPM"+ 
	"		WHERE OPD.OPD_PAYMENT_FK = OPM.OPTM_PAYMENT_PK"+
	"		AND OPM.OPTM_PAYMENT_PK = CASE WHEN ? = 0 THEN OPM.OPTM_PAYMENT_PK ELSE ? END "+
	"		AND to_char (OPD.OPD_CREATED_DATE,'dd-MON-yyyy') BETWEEN CASE WHEN ? = '' THEN to_char (OPD.OPD_CREATED_DATE,'dd-MON-yyyy') ELSE ? END AND CASE WHEN ? = '' THEN to_char (OPD.OPD_CREATED_DATE,'dd-MON-yyyy') ELSE ? END"+
	"		and opd.OPD_VALIDATED_STATUS='A'"+
	"		and opd.opd_payment_fk = 1"+
	"	GROUP BY to_char(OPD.OPD_CREATED_DATE,'dd-MON-yyyy'),OPM.OPTM_PAYMENT_CODE,OPM.OPTM_PAYMENT_DESC"+
	"		UNION ALL"+
	"		select "+ 
	"			to_char(to_date(oes_epost_rec.rec_trsc_dt, 'mm/dd/yyyy'), 'dd-Mon-yyyy'),"+
	"			NULL as code,"+
	"			'Offline Post Office Payment' as optm_payment_desc,"+
	"			SUM(rec_fees::numeric(16,0))"+
	"		from oes_user_master"+
	"		inner join oes_candidate_details ON (oes_candidate_details.ocd_user_fk = oes_user_master.oum_user_pk)"+
	"		left outer join oes_candidate_job_mapping ON (oes_candidate_job_mapping.ocjm_user_fk = oes_user_master.oum_user_pk)"+
	"		left outer join oes_epost_rec ON (oes_epost_rec.rec_apl_no = oes_candidate_job_mapping.ocjm_applicationnumber)"+
	"		left outer join oes_payment_details ON (oes_payment_details.opd_user_fk = oes_user_master.oum_user_pk)"+
	"       left outer join OES_PAYMENT_MASTER ON oes_payment_details.OPD_PAYMENT_FK = OES_PAYMENT_MASTER.OPTM_PAYMENT_PK"+
	"		where oes_payment_details.opd_payment_fk = 5 and"+
	"       OES_PAYMENT_MASTER.OPTM_PAYMENT_PK = CASE WHEN ? = 0 THEN OES_PAYMENT_MASTER.OPTM_PAYMENT_PK ELSE ? END "+  
	"		and OPD_VALIDATED_STATUS='A'"+
	"		AND to_char (OPD_CREATED_DATE,'dd-MON-yyyy') BETWEEN CASE WHEN ? = '' THEN to_char (OPD_CREATED_DATE,'dd-MON-yyyy') ELSE ? END AND CASE WHEN ? = '' THEN to_char (OPD_CREATED_DATE,'dd-MON-yyyy') ELSE ? END"+
	"		AND oes_epost_rec.rec_trsc_dt NOT LIKE '%gdgdgd%' "+
	"		GROUP BY to_char(to_date(oes_epost_rec.rec_trsc_dt, 'mm/dd/yyyy'), 'dd-Mon-yyyy')"+
	"		ORDER BY 1"+
	"	    ) main"+
	" ) test";*/

	
	
	// For User ID
	String GET_PAYMENT_RECONCILLIATION_DATA_FOR_USER_ID = "select main.*,obcd_challan_no, obcd_fees from" +
			" ( SELECT oum_user_id, otm_test_name,     ocd_cand_title   || ' '   || ocd_first_name  || ' '  || ocd_middle_name  || ' '   || ocd_last_name" +
			" AS cand_name,   octm_category_code, oum_mobile_no, " +
			"  TO_CHAR (TRUNC (opd_dd_date), 'DD-MON-YYYY') AS opd_dd_date,  " +
			" opd_branch_name, opd_branch_code, opd_amount, ofm_fees AS fees,  " +
			"   opd_reconcile_flag, opd_dd_challan_receipt_no " +
			" FROM oes_candidate_details ocd,   oes_test_master otm, " +
			"   oes_user_master oum, " +
			"  oes_payment_details opd, " +
			"   oes_fees_master ofm,  " +
			"   oes_category_master ocm," +
			"    oes_payment_master opm" +
			" WHERE ocd.ocd_test_fk = otm.otm_test_pk" +
			"   AND opm.optm_payment_pk = opd_payment_fk " +
			"  AND oum.oum_user_pk = ocd.ocd_user_fk " +
			"  AND ocd.ocd_user_fk = opd.opd_user_fk  " +
			" AND ofm.ofm_test_fk = otm.otm_test_pk  " +
			" AND ofm.ofm_category_fk = ocm.octm_category_pk " +
			"  AND ocd.ocd_category_fk = ocm.octm_category_pk " +
			"  AND ocd.ocd_validated_status = 1) main, oes_bank_challan_details obcd " +
			"  where obcd.obcd_challan_no = opd_dd_challan_receipt_no (+) " +
			"  AND oum_user_id = ?   ";
    
	// Summary For All Status All Discipline with No date range
		
	String GET_PAYMENT_RECONCILLIATION_SUMMARY_RESULT_DATA_FOR_ALL_STATUS = "SELECT  nvl(otm_test_name,'Payment submission pending*') as  otm_test_name, COUNT (1) AS total_candidates,    SUM (ofm_fees) AS applicable_fees_amt " +
			" from ( select  otm_test_name, ofm_fees, opd_dd_challan_receipt_no " +
			" FROM oes_candidate_details ocd,    oes_test_master otm,  oes_user_master oum,  " +
			"  oes_payment_details opd,      oes_fees_master ofm,    " +
			" oes_category_master ocm,   oes_payment_master opm  " +
			"   WHERE ocd.ocd_test_fk = otm.otm_test_pk " +
			" AND oum.oum_user_pk = ocd.ocd_user_fk  " +
			" AND ocd.ocd_user_fk = opd.opd_user_fk  " +
			"  AND opm.optm_payment_pk = opd_payment_fk  " +
			"  AND ofm.ofm_test_fk = otm.otm_test_pk " +
			" AND ofm.ofm_category_fk = ocm.octm_category_pk   " +
			"  AND ocd.ocd_category_fk = ocm.octm_category_pk " +
			"    AND otm.otm_test_pk = DECODE (?, 0, otm.otm_test_pk, ?)" +
			"   AND ocd.ocd_validated_status = 1) , oes_bank_challan_details  obcd  " ;
			
	
	// Candidate wise data For All Status with No date range
	String GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_ALL_STATUS = "select * from (select ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,main.*, obcd_challan_no, to_char(obcd_fees,'999999.99') as obcd_fees , obcd_reconcile_flag from" +
			" ( SELECT oum_user_id, otm_test_name,      ocd_cand_title  || ' ' || ocd_first_name  || ' '   || ocd_middle_name   || ' '   || ocd_last_name AS cand_name,  " +
			"  octm_category_code, oum_mobile_no,  " +
			"  TO_CHAR (TRUNC (opd_dd_date), 'DD-MON-YYYY') AS opd_dd_date, " +
			"  opd_branch_name, opd_branch_code, opd_amount, to_char(ofm_fees,'999999.99') AS fees,  " +
			"  opd_reconcile_flag, opd_dd_challan_receipt_no  " +
			" FROM oes_candidate_details ocd,   oes_test_master otm, " +
			"  oes_user_master oum,    oes_payment_details opd,     oes_fees_master ofm,   " +
			"  oes_category_master ocm,   oes_payment_master opm " +
			" WHERE ocd.ocd_test_fk = otm.otm_test_pk " +
			"  AND opm.optm_payment_pk = opd_payment_fk  " +
			" AND oum.oum_user_pk = ocd.ocd_user_fk " +
			"  AND ocd.ocd_user_fk = opd.opd_user_fk" +
			"   AND ofm.ofm_test_fk = otm.otm_test_pk  " +
			" AND ofm.ofm_category_fk = ocm.octm_category_pk  " +
			" AND ocd.ocd_category_fk = ocm.octm_category_pk  " +
			" AND otm.otm_test_pk = DECODE (0, 0, otm.otm_test_pk, 0) " +
			"  AND ocd.ocd_validated_status = 1) main, oes_bank_challan_details obcd  " +
			" where obcd.obcd_challan_no = opd_dd_challan_receipt_no (+)";
    	 
    	//+
    	//" AND OTM_TEST_NAME = ? "; 
    	    
	// Summary For Reconcile Status All Discipline with No date range
    String GET_PAYMENT_RECONCILLIATION_SUMMARY_RESULT_DATA_FOR_RECONCILE_STATUS = "select OTM_TEST_NAME, count(1) as TOTAL_CANDIDATES," +
    		" nvl(sum(OFM_FEES),0) as APPLICABLE_FEES_AMT from " +
    		"("+
        " select OTM_TEST_NAME, OFM_FEES, OPD_DD_CHALLAN_RECEIPT_NO from oes_candidate_details ocd, " +
        "oes_test_master otm, oes_user_master oum, oes_payment_details opd," +
        " oes_fees_master ofm, oes_category_master ocm," +
        " OES_PAYMENT_MASTER OPM "+
        " where  ocd.OCD_TEST_FK = otm.OTM_TEST_PK" +
        "  and oum.OUM_USER_PK = ocd.OCD_USER_FK and ocd.OCD_USER_FK = opd.OPD_USER_FK " +
        " and ofm.OFM_TEST_FK = otm.OTM_TEST_PK and ofm.OFM_CATEGORY_FK = ocm.OCTM_CATEGORY_PK" +
        " and OPM.OPTM_PAYMENT_PK = OPD_PAYMENT_FK "+
        " and ocd.OCD_CATEGORY_FK = ocm.OCTM_CATEGORY_PK " +
        "and OCD_FORM_SUBMITED_DATE is not null" +
        " and otm.OTM_TEST_PK = decode(?,0,otm.OTM_TEST_PK,?)"+
        "  AND ocd.ocd_validated_status = 1 " ;
        //" AND opm.OPTM_PAYMENT_PK = ?";
    
    // Candidate wise data For Reconcile Status with No date range
    String GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_RECONCILE_STATUS = "select * from (select ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,main.*,OBCD_CHALLAN_NO, to_char(obcd_fees,'999999.99') as OBCD_FEES,OBCD_RECONCILE_FLAG from( "+
            "select OUM_USER_ID,OTM_TEST_NAME, OCD_CAND_TITLE ||' '|| OCD_FIRST_NAME ||' '|| OCD_MIDDLE_NAME ||' '|| OCD_LAST_NAME as cand_name, "+
            "OCTM_CATEGORY_CODE, OUM_MOBILE_NO, OPD_DD_CHALLAN_RECEIPT_NO, TO_CHAR(TRUNC(OPD_DD_DATE), 'DD-MON-YYYY') AS OPD_DD_DATE, OPD_BRANCH_NAME, OPD_BRANCH_CODE, to_char(OPD_AMOUNT,'999999.99') as OPD_AMOUNT ,to_char(OFM_FEES,'999999.99') as fees,OPD_RECONCILE_FLAG "+  
            "from oes_candidate_details ocd, oes_test_master otm, oes_user_master oum, oes_payment_details opd, oes_fees_master ofm, oes_category_master ocm, OES_PAYMENT_MASTER OPM "+
            " where  ocd.OCD_TEST_FK = otm.OTM_TEST_PK and OPM.OPTM_PAYMENT_PK = OPD_PAYMENT_FK and oum.OUM_USER_PK = ocd.OCD_USER_FK and ocd.OCD_USER_FK = opd.OPD_USER_FK and ofm.OFM_TEST_FK = otm.OTM_TEST_PK "+
            " and ofm.OFM_CATEGORY_FK = ocm.OCTM_CATEGORY_PK and ocd.OCD_CATEGORY_FK = ocm.OCTM_CATEGORY_PK and OCD_FORM_SUBMITED_DATE is not null "+
            " and otm.OTM_TEST_PK = decode(0,0,otm.OTM_TEST_PK,0) " +
            " AND ocd.ocd_validated_status=1 "; 
            //+
           // " AND OTM_TEST_NAME = ? ";
        
    // Summary For Not Reconcile Status All Discipline with No date range
    String GET_PAYMENT_RECONCILLIATION_SUMMARY_RESULT_DATA_FOR_NOT_RECONCILE_STATUS = "select nvl(OTM_TEST_NAME,'Payment submission pending*') OTM_TEST_NAME, count(1) as TOTAL_CANDIDATES," +
    		" sum(OFM_FEES) as APPLICABLE_FEES_AMT from ( "+
            " select OTM_TEST_NAME, OFM_FEES, OPD_DD_CHALLAN_RECEIPT_NO " +
            " from oes_candidate_details ocd, oes_test_master otm, " +
            " oes_user_master oum, oes_payment_details opd, oes_fees_master ofm," +
            " oes_category_master ocm, OES_PAYMENT_MASTER OPM "+
            " where ocd.OCD_TEST_FK = otm.OTM_TEST_PK  " +
            " and oum.OUM_USER_PK = ocd.OCD_USER_FK and OPM.OPTM_PAYMENT_PK = OPD_PAYMENT_FK " +
            " and ocd.OCD_USER_FK = opd.OPD_USER_FK and ofm.OFM_TEST_FK = otm.OTM_TEST_PK" +
            "  and ofm.OFM_CATEGORY_FK = ocm.OCTM_CATEGORY_PK "+
            " and ocd.OCD_CATEGORY_FK = ocm.OCTM_CATEGORY_PK " +
            " and OCD_FORM_SUBMITED_DATE is not null" +
            " and otm.OTM_TEST_PK = decode(?,0,otm.OTM_TEST_PK,?) "+
            " AND ocd.ocd_validated_status = 1       " +     
            " AND opd.opd_reconcile_flag is not null ";
            
    // Candidate wise data For Not Reconcile Status with No date range
    String GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_NOT_RECONCILE_STATUS = "select * from (select ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,main.*,OBCD_CHALLAN_NO, to_char(obcd_fees,'999999.99') as obcd_fees,OBCD_RECONCILE_FLAG from( "+
            "select OUM_USER_ID,OTM_TEST_NAME, OCD_CAND_TITLE ||' '|| OCD_FIRST_NAME ||' '|| OCD_MIDDLE_NAME ||' '|| OCD_LAST_NAME as cand_name, "+
            "OCTM_CATEGORY_CODE, OUM_MOBILE_NO, OPD_DD_CHALLAN_RECEIPT_NO, TO_CHAR(TRUNC(OPD_DD_DATE), 'DD-MON-YYYY') AS OPD_DD_DATE, OPD_BRANCH_NAME, OPD_BRANCH_CODE, to_char(OPD_AMOUNT,'999999.99') as OPD_AMOUNT, " +
            "to_char(OFM_FEES ,'999999.99') as fees,OPD_RECONCILE_FLAG "+  
            "from oes_candidate_details ocd, oes_test_master otm, oes_user_master oum, oes_payment_details opd, oes_fees_master ofm, oes_category_master ocm, OES_PAYMENT_MASTER OPM "+ 
            "where  ocd.OCD_TEST_FK = otm.OTM_TEST_PK and OPM.OPTM_PAYMENT_PK = OPD_PAYMENT_FK and oum.OUM_USER_PK = ocd.OCD_USER_FK and ocd.OCD_USER_FK = opd.OPD_USER_FK and ofm.OFM_TEST_FK = otm.OTM_TEST_PK "+
            "and ofm.OFM_CATEGORY_FK = ocm.OCTM_CATEGORY_PK and ocd.OCD_CATEGORY_FK = ocm.OCTM_CATEGORY_PK and OCD_FORM_SUBMITED_DATE is not null "+ 
            //"/*and nvl(OTM_TEST_NAME,'Payment submission pending*')= ? */" +
            " AND ocd.ocd_validated_status=1";
            //+
           // " AND OTM_TEST_NAME = ? ";
    
    // Summary For Payment Submitted Status All Discipline with No date range
    
    String GET_PAYMENT_RECONCILLIATION_SUMMARY_RESULT_DATA_FOR_PAYMENT_SUBMITTED_STATUS = "select OTM_TEST_NAME, count(1) as TOTAL_CANDIDATES, nvl(sum(OFM_FEES),0) as APPLICABLE_FEES_AMT from "+
    " oes_candidate_details ocd, oes_test_master otm, oes_user_master oum, oes_payment_details opd, oes_fees_master ofm, oes_category_master ocm, OES_PAYMENT_MASTER OPM "+ 
    " where  ocd.OCD_TEST_FK = otm.OTM_TEST_PK and OPM.OPTM_PAYMENT_PK = OPD_PAYMENT_FK and oum.OUM_USER_PK = ocd.OCD_USER_FK and ocd.OCD_USER_FK = opd.OPD_USER_FK and ofm.OFM_TEST_FK = otm.OTM_TEST_PK "+
    " and ofm.OFM_CATEGORY_FK = ocm.OCTM_CATEGORY_PK and ocd.OCD_CATEGORY_FK = ocm.OCTM_CATEGORY_PK and OCD_FORM_SUBMITED_DATE is not null and otm.OTM_TEST_PK = decode(?,0,otm.OTM_TEST_PK,?) AND ocd.ocd_validated_status = 1 ";
    // Summary For Payment Submitted Status All Discipline with No date range
    String GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_PAYMENT_SUBMITTED_STATUS = "select main.*,OBCD_CHALLAN_NO,OBCD_FEES from( "+
            " select OUM_USER_ID,OTM_TEST_NAME, OCD_CAND_TITLE ||' '|| OCD_FIRST_NAME ||' '|| OCD_MIDDLE_NAME ||' '|| OCD_LAST_NAME as cand_name, "+
            " OCTM_CATEGORY_CODE, OUM_MOBILE_NO, OPD_DD_CHALLAN_RECEIPT_NO, TO_CHAR(TRUNC(OPD_DD_DATE), 'DD-MON-YYYY') AS OPD_DD_DATE, OPD_BRANCH_NAME, OPD_BRANCH_CODE, OPD_AMOUNT,OFM_FEES as fees,OPD_RECONCILE_FLAG "+  
            " from oes_candidate_details ocd, oes_test_master otm, oes_user_master oum, oes_payment_details opd, oes_fees_master ofm, oes_category_master ocm, OES_PAYMENT_MASTER OPM where  ocd.OCD_TEST_FK = otm.OTM_TEST_PK "+
            " and OPM.OPTM_PAYMENT_PK = OPD_PAYMENT_FK and oum.OUM_USER_PK = ocd.OCD_USER_FK and ocd.OCD_USER_FK = opd.OPD_USER_FK and ofm.OFM_TEST_FK = otm.OTM_TEST_PK and ofm.OFM_CATEGORY_FK = ocm.OCTM_CATEGORY_PK and ocd.OCD_CATEGORY_FK = ocm.OCTM_CATEGORY_PK "+
            " and OCD_FORM_SUBMITED_DATE is not null "+
            " AND ocd.ocd_validated_status=1";
             
            //+
            //" AND OTM_TEST_NAME = ? ";
    
    // Summary For Payment NOT Submitted Status All Discipline with No date range
   
    
    String GET_PAYMENT_RECONCILLIATION_SUMMARY_RESULT_DATA_FOR_PAYMENT_NOT_SUBMITTED_STATUS = "SELECT   otm_test_name, COUNT (1) AS total_candidates, " +
    		"  NVL (SUM (ofm_fees), 0) AS applicable_fees_amt,opd.opd_user_fk  " +
    		"  FROM oes_candidate_details ocd,  oes_test_master otm,  oes_user_master oum, " +
    		"  oes_fees_master ofm,       oes_category_master ocm, oes_payment_details opd " +
    		"  WHERE ocd.ocd_test_fk = otm.otm_test_pk " +
    		"  AND oum.oum_user_pk = ocd.ocd_user_fk  " +
    		"  AND ofm.ofm_test_fk = otm.otm_test_pk  " +
    		"  AND ofm.ofm_category_fk = ocm.octm_category_pk " +
    		"  AND ocd.ocd_category_fk = ocm.octm_category_pk " +
    		"  AND ocd.ocd_user_fk = opd.opd_user_fk(+) " +
    		"  AND opd.opd_user_fk IS NULL   " +
    		"  AND ocd_form_submited_date IS NOT NULL " +
    		"  AND otm.otm_test_pk = DECODE (?, 0, otm.otm_test_pk, ?) " +
    		"  and ocd.ocd_validated_status = 1  " +
    		"  GROUP BY otm_test_name,opd.opd_user_fk" ;
            
    // Summary For Payment NOT Submitted Status All Discipline with No date range
	    String GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_PAYMENT_NOT_SUBMITTED_STATUS = " SELECT oum_user_id, otm_test_name,     ocd_cand_title|| ' '|| ocd_first_name|| ' '|| ocd_middle_name || ' '|| ocd_last_name AS cand_name, " +
       " octm_category_code, oum_mobile_no, opd_dd_challan_receipt_no, " +
       " opd_dd_date, opd_branch_name, opd_branch_code, opd_amount, " +
       " ofm_fees AS fees,  " +
       " opd_reconcile_flag " +
       " FROM oes_candidate_details ocd, " +
       " oes_test_master otm, " +
       " oes_user_master oum, " +
       " oes_payment_details opd, " +
       " oes_fees_master ofm, " +
       " oes_category_master ocm " +
       " WHERE ocd.ocd_test_fk = otm.otm_test_pk " +
       " AND oum.oum_user_pk = ocd.ocd_user_fk " +
       " AND ocd.ocd_user_fk = opd.opd_user_fk (+) " +
       " AND ofm.ofm_test_fk = otm.otm_test_pk " +
       " AND ofm.ofm_category_fk = ocm.octm_category_pk " +
       " AND ocd.ocd_category_fk = ocm.octm_category_pk " +
       " AND ocd_form_submited_date IS NOT NULL " +
       " AND ocd.OCD_VALIDATED_STATUS = 1 " ;


		String GET_BULK_ADMIT_CARD_WRITTEN_TEST = "select oum_user_pk,oum_user_id,oed_roll_no,"+
		" (ocd_cand_title || ' ' || ocd_first_name || case when ocd_middle_name is not null and ocd_middle_name<>'' then ' '|| ocd_middle_name else ocd_middle_name end || ' ' || ocd_last_name) as candidate_name,"+
		"  otm_test_name,otcm_test_centre_name ,otcm_address,to_char(oed_test_date,'dd-Mon-yyyy') as oed_test_date,"+
		" (oed_test_start_time || ' - ' || oed_test_end_time) as oed_test_time, oed_reporting_time,octm.octm_category_code,ocd_comm_address"+
		" from oes_user_master oum,oes_candidate_details ocd, oes_test_master otm,"+
		" oes_entrollment_details oed, oes_test_center_master otcm,oes_category_master octm"+
		" where oum.oum_user_pk = ocd.ocd_user_fk"+
		" and ocd.ocd_user_fk = oed.oed_gcpd_user_fk"+
		" and oed.oed_test_fk= otm.otm_test_pk"+
		" and oed.oed_test_centre_fk = cast(otcm.otcm_test_centre_code as INT)"+
		" and ocd.ocd_category_fk = octm.octm_category_pk ";
		//"and oum.oum_user_pk = $P{USER_ID}";
		
		String GET_BULK_ADMIT_CARD_FIELD_TEST = "select oum_user_pk,oum_user_id,oetd_roll_no as oed_roll_no,"+
		" (ocd_cand_title || ' ' || ocd_first_name || case when ocd_middle_name is not null and ocd_middle_name<>'' then ' '|| ocd_middle_name else ocd_middle_name end || ' ' || ocd_last_name) as candidate_name,"+
		"  otm_test_name,ocm_city_name as otcm_test_centre_name ,ocm_city_name as otcm_address,to_char(oetd_test_date_for_field,'dd-Mon-yyyy') as oed_test_date,"+
		" (oetd_test_start_time_for_field || ' - ' || oetd_test_end_time_for_field) as oed_test_time,oetd_reporting_time_for_field as" +
		" oed_reporting_time,octm.octm_category_code,ocd_comm_address"+
		" from oes_user_master oum,oes_candidate_details ocd, oes_test_master otm,"+
		" oes_entrollment_test_details oed, oes_city_master ocm,oes_category_master octm"+
		" where oum.oum_user_pk = ocd.ocd_user_fk"+
		" and ocd.ocd_user_fk = oed.oetd_gcpd_user_fk"+
		" and oed.oetd_test_fk= otm.otm_test_pk"+
		" and oed.oetd_test_centre_fk_for_field = ocm.ocm_city_code"+
		" and ocd.ocd_category_fk = octm.octm_category_pk ";
		
		String GET_BULK_ADMIT_CARD_INTERVIEW = "select oum_user_pk,oum_user_id,oetd_roll_no as oed_roll_no,"+
		" (ocd_cand_title || ' ' || ocd_first_name || case when ocd_middle_name is not null and ocd_middle_name<>'' then ' '|| ocd_middle_name else ocd_middle_name end || ' ' || ocd_last_name) as candidate_name,"+
		"  otm_test_name,ocm_city_name as otcm_test_centre_name ,ocm_city_name as otcm_address,to_char(oetd_test_date_for_interview,'dd-Mon-yyyy') as oed_test_date,"+
		" (oetd_test_start_time_for_interview || ' - ' || oetd_test_end_time_for_interview) as oed_test_time,oetd_reporting_time_for_interview as" +
		" oed_reporting_time,octm.octm_category_code,ocd_comm_address"+
		" from oes_user_master oum,oes_candidate_details ocd, oes_test_master otm,"+
		" oes_entrollment_test_details oed, oes_city_master ocm,oes_category_master octm"+
		" where oum.oum_user_pk = ocd.ocd_user_fk"+
		" and ocd.ocd_user_fk = oed.oetd_gcpd_user_fk"+
		" and oed.oetd_test_fk= otm.otm_test_pk"+
		" and oed.oetd_test_centre_fk_for_interview = ocm.ocm_city_code"+
		" and ocd.ocd_category_fk = octm.octm_category_pk ";
		
		
		String GET_BULK_ADMIT_CARD_MEDICAL_TEST = "select oum_user_pk,oum_user_id,oetd_roll_no as oed_roll_no,"+
		" (ocd_cand_title || ' ' || ocd_first_name || case when ocd_middle_name is not null and ocd_middle_name<>'' then ' '|| ocd_middle_name else ocd_middle_name end || ' ' || ocd_last_name) as candidate_name,"+
		"  otm_test_name,ocm_city_name as otcm_test_centre_name ,ocm_city_name as otcm_address,to_char(oetd_test_date_for_medical,'dd-Mon-yyyy') as oed_test_date,"+
		" (oetd_test_start_time_for_medical || ' - ' || oetd_test_end_time_for_medical) as oed_test_time,oetd_reporting_time_for_medical as" +
		" oed_reporting_time,octm.octm_category_code,ocd_comm_address"+
		" from oes_user_master oum,oes_candidate_details ocd, oes_test_master otm,"+
		" oes_entrollment_test_details oed, oes_city_master ocm,oes_category_master octm"+
		" where oum.oum_user_pk = ocd.ocd_user_fk"+
		" and ocd.ocd_user_fk = oed.oetd_gcpd_user_fk"+
		" and oed.oetd_test_fk= otm.otm_test_pk"+
		" and oed.oetd_test_centre_fk_for_medical = ocm.ocm_city_code"+
		" and ocd.ocd_category_fk = octm.octm_category_pk ";
		
}
