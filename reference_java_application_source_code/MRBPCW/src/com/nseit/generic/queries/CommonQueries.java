package com.nseit.generic.queries;

public interface CommonQueries {
	String SELECT_CONFIG_ALL = "SELECT GCP.OCP_KEY_NAME, GCP.OCP_KEY_VALUE, GCP.OCP_DATA_TYPE FROM OES_CONFIG_PARAMETER GCP";

	String SELECT_ALL_AGEMATRIX_DATA = "SELECT oar_pk,category,isesm,maxage_yrs,mindate,isupgovtemp from oes_age_rules";

	String SELECT_ACTIVITY_ALL = "select OSM_STATUS_PK,OSM_STATUS_DESC from OES_STATUS_MASTER where OSM_STATUS = 'A' order by OSM_STATUS_DESC";

	String SELECT_STAGE_ALL = "SELECT OCSM_STAGE_PK, OCSM_STAGE_NAME FROM OES_CAND_STAGE_MASTER WHERE OCSM_STATUS='A'";

	String SELECT_STAGE_STATUS_ALL = "SELECT OCSM_STAGE_PK,OCSM_STATUS FROM OES_CAND_STAGE_MASTER";

	String GET_BOARD_NAME = "SELECT oebm_pk, oebm_board_name FROM oes_education_board_master where oebm_status='A' ORDER BY oebm_pk ASC";

	String SELECT_STAGE_ACTION_URL = "SELECT OCSM_STAGE_PK, OCSM_STAGE_LINK FROM OES_CAND_STAGE_MASTER WHERE OCSM_STATUS='A'";

	String SELECT_MENU_STAGE = "SELECT OMM_CURRENT_STAGE, OMM_NEXT_STAGE FROM OES_MENU_MASTER WHERE OMM_STATUS='A'";

	String INSERT_EMAIL_SMS_DETAIL = "INSERT INTO oes_sms_email_tran_master (osetm_sms_email_tran_pk, osetm_subject, osetm_mail_sms_object, "
			+ " osetm_to_address, osetm_sms_mail_flag, osetm_send_status, osetm_status , osetm_created_by, osetm_created_date, osetm_status_fk) "
			+ " VALUES (nextVal('OES_SMS_EMAIL_TRAN_MASTER_SEQ'), ?, ?, ?, ?, ?, ?,?, CURRENT_TIMESTAMP,?)";

	String SELECT_EMAIL_FOR_MAILING = "SELECT OSETM_SMS_EMAIL_TRAN_PK, OSETM_SUBJECT, OSETM_MAIL_SMS_OBJECT, OSETM_FROM_ADDRESS, OSETM_TO_ADDRESS, "
			+ "OSETM_CC_ADDRESS, OSETM_BCC_ADDRESS, OSETM_SEND_FAILURE_COUNT FROM OES_SMS_EMAIL_TRAN_MASTER  "
			+ "WHERE OSETM_SEND_FAILURE_COUNT =? AND  OSETM_SMS_MAIL_FLAG=? AND OSETM_STATUS='A' AND OSETM_SEND_STATUS  = 'N' ORDER BY OSETM_CREATED_DATE LIMIT ?";

	String SELECT_SMS_FOR_MAILING = "SELECT OSETM_SMS_EMAIL_TRAN_PK, OSETM_MAIL_SMS_OBJECT, OSETM_FROM_ADDRESS, OSETM_TO_ADDRESS , OSETM_SEND_FAILURE_COUNT "
			+ "FROM OES_SMS_EMAIL_TRAN_MASTER WHERE OSETM_SEND_FAILURE_COUNT =? AND OSETM_SMS_MAIL_FLAG =? AND OSETM_STATUS ='A' AND OSETM_SEND_STATUS = 'N' "
			+ "ORDER BY OSETM_CREATED_DATE LIMIT ?";

	String SELECT_EMAIL_SMS_MASTER = "SELECT OSEM_SMS_EMAIL_PK, OSEM_TEST_FK, OSEM_ACTIVITY_FK, OSEM_MAIL_OBJECT, OSEM_SMS_OBJECT, "
			+ " OSEM_SUBJECT, OSEM_CC_ADDRESS, coalesce(OSEM_MAIL_APPLICABLE,'N') as OSEM_MAIL_APPLICABLE, coalesce(OSEM_SMS_APPLICABLE, 'N') OSEM_SMS_APPLICABLE , "
			+ " OSEM_STATUS, OSEM_CREATED_BY,  OSEM_UPDATED_BY, coalesce(OSEM_CREATED_DATE, OSEM_UPDATED_DATE) as LAST_UPDATED_DATE "
			+ " FROM OES_SMS_EMAIL_MASTER WHERE OSEM_STATUS='A' ";

	String SELECT_CANDIDATE_INFO_FOR_EMAIL_SMS = "SELECT gum.OUM_user_id as user_id , gum.OUM_password as user_password, gtm.OTM_test_name AS discipline, "
			+ "   to_char(gcd.OCD_updated_date,'dd-Mon-yyyy') AS registration_rejection_date, gum.OUM_MOBILE_NO as mobile_no ,  "
			+ " gcd.OCD_remarks AS rejection_remark,    to_char(gum.OUM_updated_date,'dd-Mon-yyyy') AS booked_date, "
			+ "    to_char(gcd.OCD_updated_date,'dd-Mon-yyyy') AS registration_approval_date,  "
			+ "   to_char(gcd.OCD_REG_CONF,'dd-Mon-yyyy') AS registration_submision_date , gum.OUM_EMAIL_ID AS mail_address ,"
			+ "  gcd.OCD_PHONE_NUMERIC as alternateMobileNo , gcd.OCD_FIRST_NAME||' '||gcd.OCD_LAST_NAME as candidateName ,"
			+ "  to_char(gcd.OCD_DATE_OF_BIRTH , 'dd-Mon-yyyy') as dateOfBirth FROM OES_user_master gum, OES_test_master gtm, OES_candidate_details gcd "
			+ " WHERE gum.OUM_test_fk = gtm.OTM_test_pk  AND gum.OUM_user_pk = gcd.OCD_user_fk AND gum.OUM_user_id = ? ";

	String SELECT_CANDIDATE_INFO_FOR_SEAT_BOOKING = "SELECT gum.OUM_user_id as user_id , gum.OUM_MOBILE_NO as mobile_no ,gum.OUM_EMAIL_ID AS mail_address ,"
			+ "  gum.OUM_password as user_password,gtcm.otcm_test_centre_name as centerName, gtcm.otcm_address centerAddress, "
			+ " to_char(gbd.obd_test_date,'dd-Mon-yyyy') testDate,ged.oed_attempt, "
			+ " TO_CHAR (obd_test_start_time, 'HH12:MI') || ' ' || SUBSTR (obd_test_start_time, -2) start_time" + "  FROM OES_test_center_master gtcm, OES_candidate_details gcd ,"
			+ " OES_batch_details gbd,     OES_user_master gum,     OES_entrollment_details ged WHERE gbd.obd_test_centre_fk = gtcm.otcm_test_centre_pk "
			+ " and gum.OUM_user_pk = gcd.OCD_user_fk  and ged.oed_GCPD_USER_FK = gum.OUM_USER_PK"
			+ " AND gbd.obd_batch_pk = ged.oed_batch_fk  AND gum.OUM_user_id = ?  AND ged.oed_attempt = ? ";

	String SELECT_CANDIDATE_DATA_FOR_PAYMENT = "SELECT oum.oum_user_id AS user_id, "
			+ " oum.oum_mobile_no AS mobile_no, oum.oum_email_id AS mail_address, (select otm_test_name from oes_test_master where otm_test_pk=oum.oum_test_fk) as discipline,"
			+ " opm.optm_payment_desc AS payment_mode, TO_CHAR (opd_created_date, 'dd-Mon-yyyy') AS payment_submitted_date, "
			+ " opd.opd_remarks AS payment_rejection_remarks,opd.opd_customer_id AS regi_id FROM oes_user_master oum,oes_candidate_details ocd, oes_payment_details opd, oes_payment_master opm"
			+ " WHERE oum.oum_user_pk = ocd.ocd_user_fk AND ocd.ocd_user_fk = opd.opd_user_fk AND opd.opd_user_fk = oum.oum_user_pk"
			+ " AND ocd.ocd_user_fk = oum.oum_user_pk AND opm.OPTM_PAYMENT_PK = opd.OPD_PAYMENT_FK AND oum.oum_user_id = ? ";

	String SELECT_CANDIDATE_DATA_FOR_PAYMENT_ENC1 = "SELECT oum.oum_user_id AS user_id,"
			+ " pgp_sym_decrypt(oum_mobile_no::bytea, ?,'cipher-algo=aes256') AS mobile_no,"
			+ " pgp_sym_decrypt(oum_email_id::bytea, ?,'cipher-algo=aes256') AS mail_address,"
			+ " (SELECT otm_test_name FROM oes_test_master WHERE otm_test_pk=oum.oum_test_fk) AS discipline,"
			+ " opm.optm_payment_desc AS payment_mode, TO_CHAR(opd_created_date, 'dd-Mon-yyyy') AS payment_submitted_date, "
			+ " opd.opd_remarks AS payment_rejection_remarks, opd.opd_customer_id AS regi_id"
			+ " FROM oes_user_master oum, oes_candidate_details ocd,"
			+ " oes_payment_details opd, oes_payment_master opm "
			+ " WHERE oum.oum_user_pk = ocd.ocd_user_fk AND ocd.ocd_user_fk = opd.opd_user_fk AND opd.opd_user_fk = oum.oum_user_pk "
			+ " AND ocd.ocd_user_fk = oum.oum_user_pk AND opm.optm_payment_pk = opd.opd_payment_fk AND oum.oum_user_id = ? ";
	
	String SELECT_MENU = "select * from OES_MENU_CONTROL_MASTER";
	String SELECT_STATUS_FK = "SELECT OSO_STATUS_FK FROM OES_STAGE_OFF WHERE CTID IN ( SELECT MAX(CTID) FROM OES_STAGE_OFF GROUP BY OSO_STATUS_FK )";
	String SELECT_STAGE_ON_STATUS_FK = "SELECT OSON_STATUS_FK FROM OES_STAGE_ON WHERE CTID IN ( SELECT MAX(CTID) FROM OES_STAGE_ON GROUP BY OSON_STATUS_FK )";

	String SELECT_PARENT = "SELECT OMCM_MENU_PK,OMCM_MENU_DESC, OMCM_MENU_KEY, OMCM_MENU_LINK, OMCM_MENU_MANDATORY,"
			+ " OAC_ROLE_FK ,ORM_ROLE_CODE AS OMCM_USER_TYPE FROM (SELECT OMCM_MENU_PK,OMCM_MENU_DESC, OMCM_MENU_KEY, OMCM_MENU_LINK,OMCM_MENU_MANDATORY,"
			+ " RANK () OVER (PARTITION BY FLOOR (OMCM_MENU_KEY) ORDER BY OMCM_MENU_KEY) RNK,"
			+ " CEIL (OMCM_MENU_KEY) - FLOOR (OMCM_MENU_KEY) DIFF, OAC_ROLE_FK,ORM_ROLE_CODE,OMCM_SORT_ORDER "
			+ " FROM OES_MENU_CONTROL_MASTER OMCM, OES_ACCESS_CONTROL OAC,OES_ROLE_MASTER orm "
			+ " WHERE OMCM.OMCM_MENU_PK = OAC.OAC_MENU_FK AND ORM.ORM_ROLE_PK = OAC.OAC_ROLE_FK AND OAC.OAC_STATUS='A' AND OMCM.OMCM_ACTIVE_STATUS='A') as alisOES"
			+ " WHERE RNK = 1 AND DIFF = 0 ORDER BY OMCM_SORT_ORDER ";

	String SELECT_CHILD = "SELECT * FROM ( SELECT row_number() over() SRNO,OMCM.* FROM( "
			+ " SELECT OMCM_MENU_DESC, OMCM_NEXT_STAGE, OMCM_MENU_KEY, OMCM_MENU_LINK, OMCM_MENU_MANDATORY, "
			+ " OMCM_USER_TYPE,OMCM_ACTIVE_STATUS,OAC_ROLE_FK  FROM OES_MENU_CONTROL_MASTER OMCM, OES_ACCESS_CONTROL OAC"
			+ " WHERE CAST(OMCM.OMCM_MENU_PK as INT) =  CAST(OAC_MENU_FK as INT) "
			+ " AND FLOOR (CAST(OMCM_MENU_KEY as FLOAT)) = CAST(? as FLOAT) AND CAST(OAC_ROLE_FK as INT) = CAST(? as INT) AND CAST(OMCM_ACTIVE_STATUS as TEXT) = 'A' AND CAST(OAC.OAC_STATUS as TEXT)='A' ORDER BY OMCM_MENU_KEY) OMCM) as aliasOES WHERE SRNO > 1 "
			+ " ORDER BY OMCM_MENU_KEY";

	String SELECT_ALL_CHILD = "SELECT * FROM ( SELECT row_number() over() SRNO,OMCM.* FROM(  "
			+ " SELECT OMCM_MENU_PK,OMCM_MENU_DESC, OMCM_NEXT_STAGE, OMCM_MENU_KEY, OMCM_MENU_LINK, OMCM_MENU_MANDATORY, floor (CAST(OMCM_MENU_KEY as numeric)) as OMCM_PARENT_MENU_KEY,  "
			+ " OMCM_USER_TYPE,OMCM_ACTIVE_STATUS,OAC_ROLE_FK,CEIL (OMCM_MENU_KEY) - FLOOR (OMCM_MENU_KEY) DIFF  FROM OES_MENU_CONTROL_MASTER OMCM, OES_ACCESS_CONTROL OAC  "
			+ " WHERE CAST(OMCM.OMCM_MENU_PK as INT) =  CAST(OAC_MENU_FK as INT)   "
			+ " AND CAST(OMCM_ACTIVE_STATUS as TEXT) = 'A' AND CAST(OAC.OAC_STATUS as TEXT)='A' ORDER BY OMCM_MENU_KEY) OMCM) as aliasOES WHERE SRNO > 1  "
			+ " and diff=1 ORDER BY OMCM_MENU_KEY;";

	String SELECT_CATEGORY = "SELECT Order_caste,OCTM_CATEGORY_PK,OCTM_CATEGORY_CODE,OCTM_CATEGORY_DESC FROM OES_CATEGORY_MASTER where octm_status='A' order by Order_caste";

	String SELECT_SUBCATEGORY = "SELECT OCTM_CATEGORY_PK,OCTM_CATEGORY_CODE,OCTM_CATEGORY_DESC FROM OES_CATEGORY_MASTER";
	String SELECT_DESIGNATIONCATEGORY = "SELECT OCTM_CATEGORY_PK,OCTM_CATEGORY_CODE,OCTM_CATEGORY_DESC FROM OES_CATEGORY_MASTER";

	String SELECT_POST = "SELECT OPM_POST_PK,OPM_POST_DESC FROM OES_POST_MASTER";

	String SELECT_STAGE_OFF = "SELECT OSO_STAGE_OFF FROM OES_STAGE_OFF WHERE CAST(OSO_STATUS_FK as TEXT) = ? AND CAST(OSO_STATUS as TEXT) = 'A' ORDER BY OSO_STAGE_OFF";

	String SELECT_STAGE_ON = "SELECT OSON_STAGE_ON FROM OES_STAGE_ON WHERE CAST(OSON_STATUS_FK as TEXT) = ? AND CAST(OSON_STATUS as TEXT) = 'A' ORDER BY OSON_STAGE_ON";

	String UPDATE_EMAIL_SMS_TRANSACTION_STATUS = "UPDATE OES_sms_email_tran_master SET OSETM_SEND_FAILURE_COUNT =?, OSETM_SEND_STATUS=? WHERE OSETM_SMS_EMAIL_TRAN_PK = ?";
	String SELECT_REFERENCE_VALUE_MASTER = "SELECT ORVM_REFERENCE_PK, ORVM_REFERENCE_NAME, ORVM_REFERENCE_VALUE FROM OES_REFERENCE_VALUE_MASTER where ORVM_STATUS='A'"
			+ " ORDER BY ORVM_REFERENCE_NAME, ORVM_REFERENCE_VALUE";

	String UPDATE_STAGE = " update OES_USER_MASTER set OUM_STAGE_FK = ?, OUM_UPDATED_BY=?, OUM_UPDATED_DATE= CURRENT_TIMESTAMP where OUM_USER_PK = ? ";

	String SELECT_HELP_TEST_CENTER = "SELECT GHCM_HELP_CENTER_PK, GHCM_HELP_CENTER_NAME FROM OES_HELP_CENTER_MASTER WHERE GHCM_STATUS='A' " + " ORDER BY GHCM_HELP_CENTER_NAME ";

	String SELECT_TEST_CENTER = "SELECT otcm_TEST_CENTRE_PK, otcm_TEST_CENTRE_NAME FROM OES_TEST_CENTER_MASTER WHERE otcm_STATUS='A' ORDER BY otcm_TEST_CENTRE_NAME ";
	String SELECT_preferred_center_master_startup = "SELECT opcm_preferred_center_pk, opcm_test_centre_name  FROM oes_preferred_center_master WHERE opcm_STATUS='A' ORDER BY opcm_test_centre_name";

	String preferred_center_SELECTED = "SELECT opc_rank, opc_center_fk  FROM oes_preferred_center WHERE opc_user_fk= ? and opc_flag=?  order by opc_rank;";
	String CENTER_NAME = "Select (select opcm_test_centre_name from oes_preferred_center_master where opcm_preferred_center_pk=opc_center_fk) as opc_center_fk from oes_preferred_center WHERE opc_user_fk= ? and opc_flag=? order by opc_rank";

	//String SELECT_CANDIDATE_STAGE = "SELECT SUM(OUM_STAGE_FK) OUM_STAGE_FK FROM OES_USER_MASTER WHERE OUM_USER_PK=?";
	
	String SELECT_CANDIDATE_STAGE = "SELECT ocd_status_id_fk FROM oes_candidate_details WHERE ocd_created_by = ?";

	String GET_ADDITIONAL_EDU_DETAILS = "SELECT OAED_PHD_REG_DATE,OAED_DATE_VIVA FROM OES_ADDITIONAL_EDUCATION_DETAILS WHERE OAED_USER_FK = ?";

	String GET_CANDIDATE_DISCIPLINE_FOR_USER_FK = "SELECT oum.OUM_TEST_FK,oum.OUM_USER_ID  FROM oes_user_master oum, oes_candidate_details ocd, oes_test_master otm "
			+ " WHERE oum.oum_user_pk = ocd.ocd_user_fk   AND otm.otm_test_pk = oum.oum_test_fk AND ocd.ocd_user_fk = ?";

	String CHECK_IF_STAGE_ACTIVE = "SELECT COUNT(1) FROM OES_DATE_WINDOW WHERE STAGE=? AND systimestamp between START_DATE AND (END_DATE+1)";

	String SELECT_PROGRAM_MASTER = "SELECT OTM_TEST_PK, OTM_TEST_NAME FROM OES_TEST_MASTER WHERE OTM_STATUS='A' ORDER BY OTM_TEST_NAME ";

	String SELECT_STATUS_MASTER = "SELECT * FROM OES_STATUS_MASTER WHERE OSM_STATUS ='A' ORDER BY OSM_STATUS_DESC ";

	String SELECT_PAYMENT_MASTER = "SELECT * FROM OES_PAYMENT_MASTER WHERE OPTM_STATUS ='A' ORDER BY OPTM_PAYMENT_PK ";

	String SELECT_STATUS_MASTER_FOR_REPORTS = "SELECT OSM_STATUS_PK, OSM_STATUS_DESC FROM OES_STATUS_MASTER WHERE OSM_STATUS ='A' AND OSM_STATUS_REPORT_FILTER = 'Y'"
			+ " ORDER BY OSM_STATUS_DESC ";

	String UPDATE_CANDIDATE_STATUS = "update OES_CANDIDATE_DETAILS set OCD_STATUS_ID_FK = ?, OCD_UPDATED_BY=?, OCD_UPDATED_DATE=CURRENT_TIMESTAMP " 
			+ " where OCD_USER_FK = ? ";

	String UPDATE_CANDIDATE_STATUS_FORM_SUBMISSION = " update OES_CANDIDATE_DETAILS set OCD_STATUS_ID_FK = ?, OCD_UPDATED_BY=?,"
			+ " OCD_UPDATED_DATE=CURRENT_TIMESTAMP, OCD_FORM_SUBMITED_DATE=CURRENT_TIMESTAMP where OCD_USER_FK = ? ";

	String GET_EMAIL_STATUS = "SELECT osm.osm_status_desc, osm.osm_email  FROM oes_status_master osm WHERE osm.osm_status = 'A'";

	String GET_SMS_STATUS = "SELECT osm.osm_status_desc, osm.OSM_SMS  FROM oes_status_master osm WHERE osm.osm_status = 'A'";

	String GET_CANDIDATE_DATA_FOR_ADMIT_CARD = " SELECT oum.OUM_user_id as user_id,oed.OED_ATTEMPT as attempt ,oum.oum_mobile_no AS mobile_no, oum.oum_email_id AS mail_address FROM oes_user_master oum,  "
			+ "    oes_candidate_details ocd, oes_entrollment_details oed  WHERE oed.oed_gcpd_user_fk = ocd.ocd_user_fk  "
			+ "  AND oed.oed_gcpd_user_fk = oum.oum_user_pk  AND oum.oum_user_pk = ocd.ocd_user_fk and oum.OUM_user_id = ? and oed.OED_ATTEMPT = ?";

	String GET_CANDIDATE_DETAILS_FOR_NEW_REG = "select otm.otm_test_name AS discipline,oum.OUM_USER_ID as user_id,oum.OUM_PASSWORD as USER_PASSWORD , oum.OUM_MOBILE_NO as mobile_no,oum.OUM_EMAIL_ID AS mail_address"
			+ "  from oes_user_master oum,oes_test_master otm where OUM_USER_ID = ?";

	String GET_CANDIDATE_DETAILS_FOR_NEW_REG_ENC1 = "SELECT otm.otm_test_name AS discipline, oum.oum_user_id AS user_id, oum.oum_password AS user_password, "
			+ " pgp_sym_decrypt(oum_mobile_no::bytea, ?,'cipher-algo=aes256') AS mobile_no," 
			+ " pgp_sym_decrypt(oum_email_id::bytea, ?,'cipher-algo=aes256') AS mail_address"
			+ " FROM oes_user_master oum, oes_test_master otm WHERE OUM_USER_ID = ?";
	
	String GET_CANDIDATE_DATA_FOR_TRANSACTION_FAILURE = "  SELECT otm.otm_test_name AS discipline, ofm.ofm_fees AS amount , oum.oum_user_id  as user_id  , oum.OUM_MOBILE_NO as mobile_no,oum.OUM_EMAIL_ID AS mail_address FROM oes_user_master oum,   "
			+ "   oes_fees_master ofm,      oes_candidate_details ocd,    "
			+ "  oes_test_master otm WHERE ocd.ocd_test_fk = ofm.ofm_test_fk   AND otm.otm_test_pk = ofm.ofm_test_fk  AND otm.otm_test_pk = ocd.ocd_test_fk "
			+ "  AND ocd.ocd_category_fk = ofm.ofm_category_fk   and ocd.OCD_USER_FK = oum.OUM_USER_PK   AND oum.oum_user_id = ?";

	String GET_CANDIDATE_DATA_FOR_EXEMPTED = "SELECT ocd_category_fk, oum_genderfk, ocd_test_center1, ocd_test_center2, ocd_test_center3, ocd_discipline,"
			+ " pgp_sym_decrypt(ocd_date_of_birth::bytea, ?,'cipher-algo=aes256') AS ocd_date_of_birth, ocd_ex_serviceman, ocd_esm_dt_of_enlistment,"
			+ " ocd_esm_dt_of_discharge FROM oes_user_master INNER JOIN oes_candidate_details ON oum_user_pk=ocd_user_fk WHERE oum_user_id=?";

	String GET_CANDIDATE_DETAILS_FOR_TEST_CENTER = "SELECT oum.oum_test_fk, oum.oum_user_id  FROM oes_user_master oum,   "
			+ " oes_candidate_details ocd, oes_test_center_master otcm WHERE oum.oum_user_pk = ocd.ocd_user_fk  "
			+ " AND oum.oum_test_fk = otcm.otcm_test_centre_pk   AND oum.oum_user_pk = ?";

	String GET_CANDIDATE_DETAILS_FOR_TEST_CENTER_EMAIL_SMS = "SELECT  oum.OUM_user_id as user_id,oum.OUM_MOBILE_NO as mobile_no ,"
			+ " oum.OUM_EMAIL_ID AS mail_address,otcm.otcm_test_centre_name as centerName FROM oes_cand_pref_details ocpd, oes_test_center_master otcm,oes_user_master oum,"
			+ " oes_candidate_details ocd WHERE oum.oum_user_id = ? AND otcm.otcm_test_centre_pk = ocpd.ocpd_test_centre_fk and ocpd.ocpd_user_fk = oum.OUM_USER_PK  "
			+ " and ocpd.ocpd_user_fk = ocd.OCD_USER_FK and oum.OUM_USER_PK  = ocd.OCD_USER_FK";

	String UPDATE_AVAILABLE_SEATS = "UPDATE OES_BATCH_DETAILS SET OBD_AVAILABLE_CAPACITY = OBD_AVAILABLE_CAPACITY-1 WHERE OBD_BATCH_PK = ?";

	String GET_ACADEMIC_TYPE_DETAILS_DATA = "SELECT OATM.OATM_ACDM_PK,OATM.OATM_ACDM_CODE FROM OES_ACDM_TYPE_MASTER OATM WHERE OATM_STATUS = 'A'";

	String GET_ACADEMIC_MANDATORY_DETAILS_DATA = "SELECT OATM.OATM_ACDM_PK,OATM.OATM_MANDATORY FROM OES_ACDM_TYPE_MASTER OATM";

	String GET_ACADEMIC_TYPE_DETAILS_REVERSE_DATA = "SELECT OATM.OATM_ACDM_CODE,OATM.OATM_ACDM_PK FROM OES_ACDM_TYPE_MASTER OATM";

	String GET_DEGREE_MAPPING_DETAILS = "SELECT ODMD_DEGREE_FK,ODMD_ACDM_FK FROM OES_DEGREE_MAPPING_DETAILS  WHERE ODMD_STATUS = 'A' order by ODMD_ACDM_FK";

	String GET_CANDIDATE_DATA_FOR_FORGOT_USER_ID = "   select otm.otm_test_name AS discipline,oum.OUM_USER_ID as user_id ,oum.OUM_MOBILE_NO as mobile_no ,"
			+ "	oum.OUM_EMAIL_ID AS mail_address from oes_user_master oum" + " left join oes_test_master otm ON otm_test_pk = oum_test_fk" + "where oum.OUM_USER_ID = ? ";
	
	String GET_CANDIDATE_DATA_FOR_FORGOT_USER_ID_ENC1 = "SELECT otm.otm_test_name AS discipline, oum.oum_user_id AS user_id,"
			+ " pgp_sym_decrypt(oum_mobile_no::bytea, ?,'cipher-algo=aes256') AS mobile_no,"
			+ " pgp_sym_decrypt(oum_email_id::bytea, ?,'cipher-algo=aes256') AS mail_address"
			+ " FROM oes_user_master oum LEFT JOIN oes_test_master otm ON otm_test_pk = oum_test_fk WHERE oum.oum_user_id = ?";

	String GET_CANDIDATE_DATA_FOR_FORGOT_PASSWORD = "   select otm.otm_test_name AS discipline,oum.OUM_PASSWORD as user_password ,oum.oum_mobile_no as mobile_no ,"
			+ "	oum.oum_email_id AS mail_address from oes_user_master oum,oes_test_master otm where oum.OUM_USER_ID = ? ";

	String GET_CANDIDATE_DATA_FOR_FORGOT_PASSWORD_ENC = "   select otm.otm_test_name AS discipline,oum.OUM_PASSWORD as user_password ,oum.OUM_MOBILE_NO as mobile_no ,"
			+ "	oum.OUM_EMAIL_ID AS mail_address from oes_user_master oum,oes_test_master otm where oum.OUM_USER_ID = ? ";
	
	String GET_CANDIDATE_DATA_FOR_FORGOT_PASSWORD_ENC1 = "SELECT otm.otm_test_name AS discipline, oum.oum_password AS user_password,"
			+ " pgp_sym_decrypt(oum_mobile_no::bytea, ?,'cipher-algo=aes256') AS mobile_no,"
			+ " pgp_sym_decrypt(oum_email_id::bytea, ?,'cipher-algo=aes256') AS mail_address"
			+ " FROM oes_user_master oum, oes_test_master otm WHERE oum.oum_user_id = ? ";

	String GET_POST_GRADUATE_MASTER_DATA = "SELECT O.OPGM_PG_PK, O.OPGM_PG_CODE FROM OES_POST_GRD_MASTER O where o.OPGM_STATUS = 'A'";

	String GET_REFERENCE_VALUE_MANDATORY_STATUS = "select orvm.ORVM_REFERENCE_VALUE,orvm.ORVM_FLAG from oes_reference_value_master orvm";

	String GET_REFERENCE_VALUE_ACTIVE_STATUS = "select ORVM.ORVM_REFERENCE_VALUE,ORVM.ORVM_STATUS from OES_REFERENCE_VALUE_MASTER orvm";;

	String GET_PAYMENT_MASTER_ACTIVE_STATUS_MAP = "SELECT OPTM_PAYMENT_DESC,OPTM_STATUS FROM OES_PAYMENT_MASTER";

	String GET_DEGREE_LIST = "SELECT ODM.ODM_DEGREE_PK,ODM.ODM_DEGREE_DESC FROM  OES_DEGREE_MASTER ODM WHERE ODM.ODM_STATUS = 'A'";

	String GET_SUB_DEGREE_LIST = "SELECT OSDM.OSDM_SUB_DEGREE_PK,OSDM.OSDM_SUB_DEGREE_DESC FROM OES_SUB_DEGREE_MASTER OSDM WHERE OSDM.OSDM_SUB_STATUS = 'A' AND OSDM.OSDM_ACDM_FK = 3 ORDER BY OSDM.OSDM_SUB_DEGREE_PK";

	String GET_UNIVERSITY_LIST = "SELECT OUM.OUM_UNIVERSITY_PK,OUM.OUM_UNIVERSITY_DESC  FROM OES_UNIVERSITY_MASTER OUM WHERE OUM.OUM_STATUS = 'A'  ORDER BY OUM.OUM_UNIVERSITY_DESC";

	String GET_YEAR_LIST = "SELECT  OYM.OUM_YEAR_PK,OYM.OUM_YEAR_DESC  FROM OES_YEAR_MASTER OYM WHERE OYM.OUM_STATUS='A'";

	String GET_AREA_OF_PROJ_LIST = "select OAOPM_PROJECT_PK,OAOPM_PROJECT_DESC from OES_AREA_OF_PROJECT_MASTER where OAOPM_STATUS='A'";

	String GET_FILED_OF_INTREST_LIST = "SELECT OFOIM_FILED_INTREST_PK,OFOIM_FILED_INTREST_DESC FROM OES_FIELD_OF_INTREST_MASTER WHERE OFOIM_STATUS='A'";

	String GET_INTERVIEW_LOC_LIST = "SELECT OILM_INT_LOC_PK,OILM_INT_LOC_DESC FROM OES_INTERVIEW_LOCATION_MASTER WHERE OILM_STATUS='A'";

	String GET_CITY_MASTER_DETAILS = "select OCM_CITY_NAME from OES_CITY_MASTER ocm where ocm.OCM_STATUS='A' order by lower(OCM_CITY_NAME)";

	String GET_EXAM_PATTERN_DETAILS = " Select ORVM_REFERENCE_PK , ORVM_REFERENCE_VALUE from OES_REFERENCE_VALUE_MASTER where ORVM_REFERENCE_NAME = 'Pattern_Type' ";

	String GET_CITY_MASTER_MAP = "select OCM_CITY_NAME,OCM_CITY_PK from OES_CITY_MASTER where OCM_STATUS='A'";

	String GET_REFERNCE_VALUE_MASTER_MAP = "select ORVM_REFERENCE_PK,ORVM_REFERENCE_VALUE,orvm_reference_name from OES_REFERENCE_VALUE_MASTER orvm where orvm.ORVM_STATUS = 'A'";

	String GET_LOGIN_TEMPLATES = "Select GCP.OCP_KEY_NAME , GCP.OCP_DESCRIPTION FROM OES_CONFIG_PARAMETER GCP where GCP.OCP_KEY_NAME in ('CANDIDATE_INSTRUCTIONS')";

	String VERSION = "Select GCP.OCP_KEY_NAME , GCP.OCP_DESCRIPTION FROM OES_CONFIG_PARAMETER GCP where GCP.OCP_KEY_NAME in ('VERSION')";
	String CANDIDATE_DECLARATION = "Select GCP.OCP_KEY_NAME , GCP.OCP_DESCRIPTION FROM OES_CONFIG_PARAMETER GCP where GCP.OCP_KEY_NAME in ('CANDIDATE_DECLARATION')";
	String CANDIDATE_DECLARATION1 = "Select GCP.OCP_KEY_NAME , GCP.OCP_DESCRIPTION FROM OES_CONFIG_PARAMETER GCP where GCP.OCP_KEY_NAME in ('CANDIDATE_DECLARATION1')";
	// shekharc apply page confirmation msg start
	String APPLY_POST_CONFIRMATION = "Select GCP.OCP_KEY_NAME , GCP.OCP_DESCRIPTION FROM OES_CONFIG_PARAMETER GCP where GCP.OCP_KEY_NAME in ('APPLY_POST_CONFIRMATION')";
	// shekharc apply page confirmation msg end

	String GET_CANDIDATE_DASHBOARD_FOR_WRITTEN_TEST = "SELECT to_char(oed_test_date,'dd-Mon-yyyy') as oed_test_date,oed_test_start_time,oed_test_end_time,otcm_test_centre_name,oed_exam_status,oed_marks,oed_eligible_for_field_test,ocm_city_name "
			+ " FROM oes_user_master oum, oes_entrollment_details oed, oes_test_center_master otcm,oes_city_master ocm WHERE oum.oum_user_pk = oed.oed_gcpd_user_fk  "
			+ " AND oed.oed_test_centre_fk = cast(otcm.otcm_test_centre_code as INT) "
			+ " AND oed.oed_test_city_code = ocm.ocm_city_code AND oum.oum_user_pk = ? and oum_test_fk=? ";

	String GET_CANDIDATE_DASHBOARD_FOR_OTHER_TEST = "SELECT oetd_gcpd_user_fk, "
			+ " oetd_field_test_status,(select ocm_city_name from oes_city_master where ocm_city_code=oetd_test_centre_fk_for_field) as oetd_test_centre_fk_for_field, to_char(oetd_test_date_for_field,'dd-Mon-yyyy') as oetd_test_date_for_field, "
			+ " oetd_reporting_time_for_field, oetd_interview_status, oetd_test_centre_fk_for_interview,  "
			+ " to_char(oetd_test_date_for_interview,'dd-Mon-yyyy') as oetd_test_date_for_interview, oetd_test_start_time_for_interview,  "
			+ " oetd_test_end_time_for_interview, oetd_reporting_time_for_interview,  "
			+ " oetd_medical_test_status, oetd_test_centre_fk_for_medical, to_char(oetd_test_date_for_medical,'dd-Mon-yyyy') as oetd_test_date_for_medical,  "
			+ " oetd_test_start_time_for_medical, oetd_test_end_time_for_medical,  oetd_reporting_time_for_medical, oetd_transfer_status, oetd_test_fk,  "
			+ " oetd_test_marks_for_field, oetd_marks_for_interview, oetd_marks_for_medical, oetd_interview_location, oetd_medical_location "
			+ " FROM oes_user_master oum, oes_entrollment_test_details oed WHERE oum.oum_user_pk = oed.oetd_gcpd_user_fk  " + " AND oum.oum_user_pk = ? and oum_test_fk=? ";

	String Candidate_Eigibilty_Criteria = "Select GCP.OCP_KEY_NAME , GCP.OCP_DESCRIPTION FROM OES_CONFIG_PARAMETER GCP where GCP.OCP_KEY_NAME in ('ELIGIBILITY_CRITERIA')";

	String GET_SPORTS_LIST = "Select * from oes_sports_master where oes_sports_status='A' order by oes_sports_pk";

	String GET_Rank = "select opu_present_rank from oes_posting_units order by opu_present_rank";

	String INSERT_AUDITRAIL = "INSERT INTO candidate_audit_trail( oat_pk, oat_user_fk, oat_ip, oat_audit_dt, oat_action, oat_audit_trail,oat_audit_datepart) "
			+ " VALUES (nextval('candidate_audit_trail_seq'), ?, ?,CURRENT_TIMESTAMP, ?, pgp_sym_encrypt(?, ?,'cipher-algo=aes256'),CURRENT_DATE)";

	String GET_RECRUMENT_MAP = "Select orvm_reference_pk,orvm_reference_value from oes_reference_value_master where orvm_reference_name='Recrument' and orvm_reference_value not in('CR-2010','SI-2010')";

	String GET_STAGE_MAP = "Select orvm_reference_pk,orvm_reference_value from oes_reference_value_master where orvm_reference_name='Stage'";

	String GET_CATEGORY_MAP1 = "Select orvm_reference_pk,orvm_reference_value from oes_reference_value_master where orvm_reference_name='Categories1'";

	String GET_CATEGORY_MAP2 = "Select orvm_reference_pk,orvm_reference_value from oes_reference_value_master where orvm_reference_name='Categories2'";

	String GET_TXN_NUMBER = "SELECT nextval('OES_PAYMENT_DETAILS_SEQ')";

	String GET_STAGE_PC_MAP = "select * from oes_reference_value_master where orvm_reference_name='PC'";

	String GET_STAGE_SI_MAP = "select * from oes_reference_value_master where orvm_reference_name='SI'";

	String GET_CATEGORY_PC_MAP = "select * from oes_reference_value_master where orvm_reference_name='CR 2010-17'";

	String GET_CATEGORY_SI_MAP = "select * from oes_reference_value_master where orvm_reference_name='SI 2010-15'";

	String GET_KEY_VALUE_MASTER = "select ORVM_REFERENCE_PK,ORVM_REFERENCE_VALUE  from OES_REFERENCE_VALUE_MASTER orvm where orvm_reference_name = ?";

	String GET_RECRUMENT_CITY = "select odm_district_pk,odm_district_name from  oes_district_master order by odm_district_name";

	String UPDATE_EMAIL_SMS_DETAIL = "update OES_SMS_EMAIL_TRAN_MASTER"
			+ "set OSETM_MAIL_SMS_OBJECT = ?, OSETM_SEND_STATUS = 'N' ,osetm_updated_by = ?,osetm_updated_date = CURRENT_TIMESTAMP"
			+ "where OSETM_SMS_EMAIL_TRAN_PK =(select oeom_sms_email_tran_pk from  oes_email_otp_master where OEOM_TO_ADDRESS = ?)";

	String CHECK_IF_MOB_EXISTS = "SELECT COUNT(0) from OES_USER_MASTER WHERE OUM_MOBILE_NO = ?  and OUM_TEST_FK =?";

	String CHECK_MOB_OTP_COUNT = "select SEND_OTP_COUNT from OES_MOB_OTP_MASTER where OMOM_TO_ADDRESS = ? and OMOM_TEST_FK = ? ";

	String CHECK_OTP_COUNT = "select SEND_OTP_COUNT from oes_email_otp_master where OEOM_TO_ADDRESS = ? and OEOM_TEST_FK =? ";

	String SELECT_CANDIDATE_STATUS = "select osm.osm_status_desc from oes_status_master osm inner join oes_candidate_Details ocd on "
			+ "osm.osm_status_pk = ocd.ocd_status_id_fk where ocd.ocd_created_by = ? ";

	String GET_TRAINING_CENTRE_FK = "SELECT OCPD_PREF_TEST_CENTRE_1_FK FROM OES_CAND_PREF_DETAILS WHERE OCPD_USER_FK = ?";

	String GET_CANDIDATE_CATEGORY = "select ocd_community from oes_candidate_details where ocd_user_fk=?";

	String GET_NATIVITYLIST = "select onm_native_pk,native_drop_down_values from oes_nativity_master";

	String GET_USER_DISABILITY = "select ocd_is_handicaped from oes_candidate_details where ocd_user_fk=?";

	String GET_USER_Discipline = "select ocd_discipline from oes_candidate_details where ocd_user_fk=?";

	String GET_ELIGIBILITY_FLAG = "select flag from ("
			+ "select case when oacd_edit_flag='Y' and (select OCP_KEY_VALUE from OES_CONFIG_PARAMETER where OCP_KEY_NAME='EDIT_APPLIED_SUB_FLAG')='1' then 'true' else 'false' end as flag"
			+ "from oes_acdm_cand_details where oacd_user_fk=? and oacd_acdm_fk = 3)d";

	String INSERT_SUBJECT_APPLIED = "INSERT INTO oes_subject_applied_details (osad_pk,osad_user_fk,osad_acdm_fk,osad_subject_applied_fk,osad_created_on)  "
			+ "  values (nextVal('applied_subject_seq'),?,?,?,CURRENT_TIMESTAMP)";

	String UPDATE_ACADEMIC_DETAILS = "update oes_acdm_cand_details set oacd_edit_flag='C' where oacd_user_fk=? and oacd_acdm_fk = 3";

	String GET_ADD_EDU_DETAILS = "select * from oes_additional_education_details where oaed_user_fk=?";

	String GET_DATA_FOR_MPHIL = "select * from oes_acdm_cand_details where oacd_user_fk=? and oacd_acdm_fk=7";

	String GET_POST_APPLIED = "SELECT ocd_discipline FROM oes_candidate_details WHERE ocd_user_fk=?";

	String GET_POST_APPLIED2 = "SELECT ocd_discipline FROM oes_candidate_details WHERE ocd_created_by=?";
	
	String GET_POST_APPLIED_PAYMENT = "SELECT otm_test_name FROM oes_test_master WHERE otm_test_pk= 1";

	String GET_POST_APPLIED_WRKEXP = "SELECT owead_post_applied from oes_work_exp_add_details where owead_user_fk=?";
	String GET_USER_DATA_FOR_PAYMENT = "select ocd_is_handicaped,ocd_category_fk,ocd_gender_fk from oes_candidate_details where ocd_user_fk=?";

	String GET_DATA_FOR_HIGHEST_EDU = "select oacd_acdm_fk, oacd_year_of_passing from oes_acdm_cand_details where oacd_user_fk=? order by oacd_acdm_fk desc limit 1"; // oacd_period_of_study_to,

	String GET_USER_EDIT_FLAG = "Select oum_edit_flag from oes_user_master where oum_user_pk=?";

	// for Covid Duty Certificate page

	String GET_SIGNED_BY_LIST = "SELECT osbd_institution_type_fk, osbd_signed_by, osbd_counter_signed_by FROM oes_signed_by_details";
}
