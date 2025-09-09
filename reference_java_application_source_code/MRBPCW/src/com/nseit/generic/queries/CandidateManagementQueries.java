package com.nseit.generic.queries;

// CandidateManagement related generic queries added by Tarka on 03-Jan-2012
public interface CandidateManagementQueries {

	String GET_CANDIDATE_HALLTICKET_DETAILS = "SELECT  gtcm.otcm_TEST_CENTRE_NAME,ged.oed_ENROLLMENT_PK , ged.OED_ATTEMPT, gcd.OCD_user_fk,"
			+ "  gcd.OCD_CAND_TITLE,gcd.OCD_FIRST_NAME,gcd.OCD_MIDDLE_NAME,gcd.OCD_LAST_NAME , " + " gcd.OCD_date_of_birth, gbd.obd_test_date, "
			+ " TO_CHAR (obd_test_start_time, 'HH12:MI') || ' ' || SUBSTR (obd_test_start_time, -2) start_time , gbd.obd_test_end_time,  grvm.ORVM_reference_value, "
			+ " gtcm.otcm_address  ,gtm.OTM_TEST_NAME ,gtm.OTM_DURATION ,gum.OUM_USER_ID , gum.OUM_PASSWORD, TO_CHAR (obd_REPORTING_TIME, 'HH12:MI') || ' ' || SUBSTR (obd_REPORTING_TIME, -2) REPORTING_TIME, ged.OED_ROLL_NO "
			+ " FROM OES_candidate_details gcd,  OES_entrollment_details ged,   OES_batch_details gbd,  OES_reference_value_master grvm, "
			+ " OES_test_center_master gtcm,OES_USER_MASTER gum ,OES_TEST_MASTER gtm " + " WHERE gcd.OCD_user_fk = ged.oed_gcpd_user_fk AND gcd.OCD_user_fk = gum.OUM_USER_PK "
			+ " AND gbd.obd_batch_pk = ged.oed_batch_fk AND grvm.ORVM_reference_pk = gcd.OCD_gender_fk "
			+ "  AND gtcm.otcm_test_centre_pk = gbd.obd_test_centre_fk and gum.OUM_USER_ID = ? " + " and gtm.OTM_TEST_PK = gum.OUM_TEST_FK  "
			+ " and gcd.OCD_STATUS_ID_FK >= ? and ged.oed_ATTEMPT = ? ";

	String GET_CANDIDATE_DETAILS_BY_USER_ID = "select gcd.OCD_CAND_TITLE,gcd.OCD_FIRST_NAME,gcd.OCD_MIDDLE_NAME,gcd.OCD_LAST_NAME,gum.OUM_USER_ID,gtcm.otcm_TEST_CENTRE_NAME,gbd.obd_TEST_START_TIME,gbd.obd_TEST_END_TIME ,gtm.OTM_TEST_NAME ,to_char(gbd.obd_TEST_DATE,'dd-Mon-yyyy')  as testDate "
			+ " from  OES_CANDIDATE_DETAILS gcd, OES_ENTROLLMENT_DETAILS gec ,   OES_USER_MASTER gum,OES_BATCH_DETAILS gbd ,OES_TEST_CENTER_MASTER gtcm ,OES_TEST_MASTER gtm  "
			+ " where gcd.OCD_USER_FK = gec.oed_GCPD_USER_FK   and gcd.OCD_USER_FK = gum.OUM_USER_PK   "
			+ " and gum.OUM_USER_PK = gec.oed_GCPD_USER_FK   and gec.oed_BATCH_FK = gbd.obd_BATCH_PK "
			+ " and gbd.obd_TEST_CENTRE_FK = gtcm.otcm_TEST_CENTRE_PK and gum.OUM_TEST_FK = gtm.OTM_TEST_PK " + "  and gum.OUM_USER_ID = ?  ";

	String GET_CANDIDATE_DETAILS = "SELECT gum.OUM_USER_ID AS user_id, gtm.OTM_test_name AS test_name,  " + "   gtcm.otcm_test_centre_name AS test_center, obd_test_start_time"
			+ "  obd_test_end_time FROM OES_user_master gum, " + "  OES_entrollment_details gec,   OES_test_master gtm,  OES_batch_details gbd,  "
			+ "  OES_test_center_master gtcm " + "  WHERE gum.OUM_user_pk = gec.oed_gcpd_user_fk  AND gum.OUM_test_fk = nvl(?,gtm.OTM_test_pk)  "
			+ "  AND gec.oed_batch_fk = gbd.obd_batch_pk  AND gbd.obd_test_centre_fk = nvl(?,gtcm.otcm_test_centre_pk) " + " and gbd.obd_TEST_DATE = nvl(?,gbd.obd_TEST_DATE) "
			+ " and to_char(gbd.obd_TEST_START_TIME,'HH24:mi')|| to_char(gbd.obd_TEST_End_TIME,'HH24:mi') = nvl(?,to_char(gbd.obd_TEST_START_TIME,'HH24:mi')|| to_char(gbd.obd_TEST_End_TIME,'HH24:mi') ) ";

	String GET_TEST_DATES = " select * from( select distinct (to_char(gbd.obd_TEST_DATE,'dd-Mon-yyyy')) test_date from OES_BATCH_DETAILS gbd) order by test_date ";

	String GET_TEST_SLOTS = " select distinct  to_char(GBD.obd_TEST_START_TIME,'HH24:MI') as start_date,to_char(GBD.obd_TEST_END_TIME,'HH24:MI') as end_date from  OES_BATCH_DETAILS gbd where trunc(gbd.obd_TEST_DATE) = ? ";

	String GET_REGISTRATION_DETAILS_FOR_CANDIDATE = "SELECT gcd.OCD_CAND_TITLE,gcd.OCD_FIRST_NAME,gcd.OCD_MIDDLE_NAME,gcd.OCD_LAST_NAME,gum.OUM_USER_ID AS user_id, gtm.OTM_test_name AS test_name,"
			+ "  gtcm.otcm_test_centre_name AS test_center, obd_test_start_time,  "
			+ "   obd_test_end_time , to_char(gbd.obd_TEST_DATE,'dd-Mon-yyyy')  as testDate  FROM OES_user_master gum,  OES_entrollment_details gec, "
			+ " OES_test_master gtm,  OES_batch_details gbd, OES_test_center_master gtcm ,OES_candidate_details gcd"
			+ " WHERE gum.OUM_user_pk = gec.oed_gcpd_user_fk AND gum.OUM_test_fk = nvl(?,gum.OUM_test_fk) " + " and gcd.OCD_USER_FK = gum.OUM_USER_PK "
			+ " and gtcm.otcm_TEST_CENTRE_PK = gbd.obd_TEST_CENTRE_FK " + " and gum.OUM_TEST_FK = gtm.OTM_TEST_PK "
			+ " AND gec.oed_batch_fk = gbd.obd_batch_pk AND gbd.obd_test_centre_fk = nvl(?,gtcm.otcm_test_centre_pk) "
			+ " and trunc(gbd.obd_TEST_DATE) = nvl(?,trunc(gbd.obd_TEST_DATE)) "
			+ " and to_char(gbd.obd_TEST_START_TIME,'HH24:mi')|| '-' || to_char(gbd.obd_TEST_End_TIME,'HH24:mi') = nvl(?,to_char(gbd.obd_TEST_START_TIME,'HH24:mi')|| '-' || to_char(gbd.obd_TEST_End_TIME,'HH24:mi') )";

	String GET_BATCH_WISE_CAPACITY_FOR_EACH_CENTER = "SELECT   otcm_test_centre_pk,OBD_BATCH_PK,      "
			+ "   NVL (obd_available_capacity, 0) available_capacity    FROM oes_batch_details, oes_test_center_master"
			+ "   WHERE obd_test_centre_fk = otcm_test_centre_pk     AND obd_status = 'A'     AND obd_batch_type = 'N'  " + "   AND obd_test_date > SYSTIMESTAMP "
			+ "   order by otcm_test_centre_pk,OBD_BATCH_PK";

	/*
	 * String GET_CANDIDATE_DATA_FOR_SEAT_ALLOCATION =
	 * "SELECT ocpd.ocpd_pref_pk, ocpd.ocpd_user_fk, ocpd.ocpd_pref_test_centre_1_fk, "
	 * + " ocpd.ocpd_pref_test_centre_2_fk, ocpd.ocpd_pref_test_centre_3_fk "+
	 * " FROM oes_cand_pref_details ocpd, oes_payment_details opd,oes_user_master oum,OES_CAND_STAGE_MASTER ocsm "
	 * + " WHERE ocpd.ocpd_user_fk = opd.opd_user_fk "+
	 * " and oum.OUM_USER_PK = opd.opd_user_fk "+
	 * " and oum.OUM_USER_PK = ocpd.ocpd_user_fk "+ " and oum.OUM_STAGE_FK =? "+
	 * " AND oum.OUM_stage_fk = ocsm.OCSM_STAGE_PK "+
	 * " AND opd.opd_validated_status = 'A' "+
	 * " and ocpd.OCPD_TEST_CENTRE_FK is null ";
	 */

	/*
	 * String GET_CANDIDATE_DATA_FOR_SEAT_ALLOCATION =
	 * "SELECT ocpd.ocpd_pref_pk, ocpd.ocpd_user_fk, ocpd.ocpd_pref_test_centre_1_fk,    ocpd.ocpd_pref_test_centre_2_fk,"
	 * + " ocpd.ocpd_pref_test_centre_3_fk " +
	 * " FROM oes_cand_pref_details ocpd,   oes_user_master oum,   oes_candidate_details ocd,  oes_status_master osm "
	 * +
	 * " WHERE oum.oum_user_pk = ocpd.ocpd_user_fk  AND ocd.ocd_user_fk = oum.oum_user_pk  AND ocd.ocd_user_fk = ocpd.ocpd_user_fk  "
	 * +
	 * " AND osm.osm_status_pk = ocd.ocd_status_id_fk  AND ocd.ocd_status_id_fk >= ?  "
	 * + " AND ocpd.ocpd_test_centre_fk IS NULL " +
	 * "  and ocd.OCD_VALIDATED_STATUS = ?	order by ocpd.OCPD_APPL_DATE asc";
	 */

	String GET_CANDIDATE_DATA_FOR_SEAT_ALLOCATION = " select ocpd.ocpd_pref_pk, ocpd.ocpd_user_fk, ocpd.ocpd_pref_test_centre_1_fk, "
			+ "	ocpd.ocpd_pref_test_centre_2_fk,    ocpd.ocpd_pref_test_centre_3_fk    "
			+ "	from oes_cand_pref_details ocpd,   oes_user_master oum,   oes_candidate_details ocd, " + "	oes_status_master osm      "
			+ "	where oum.oum_user_pk = ocpd.ocpd_user_fk  and ocd.ocd_user_fk = oum.oum_user_pk  "
			+ " 	and ocd.ocd_user_fk = ocpd.ocpd_user_fk     and osm.osm_status_pk = ocd.ocd_status_id_fk  " + "   and ocpd.ocpd_test_centre_fk is null ";

	String GET_CANDIDATE_DETAILS_FOR_ROLL_NUMBER_GENERATION = "select obd_batch_pk,otcm.OTCM_TEST_CENTRE_PK,to_char(obd.OBD_TEST_DATE,'dd-mm-yyyy') as test_date,oed.OED_GCPD_USER_FK "
			+ "  from OES_ENTROLLMENT_DETAILS oed, oes_batch_details obd, oes_test_center_master otcm " + "  where  obd.OBD_BATCH_PK = oed.OED_BATCH_FK  "
			+ "  and obd.OBD_TEST_CENTRE_FK = otcm.OTCM_TEST_CENTRE_PK  " + "  and oed.OED_ROLL_NO is null";

	String GET_ROLL_NUMBER_SEQ_NEXT_VAL = "SELECT LPAD(OES_ROLL_NO.nextval, ?,'0') FROM dual";

	String UPDATE_CANDIDATE_ROLL_NUMBERS = " update oes_entrollment_details set OED_ROLL_NO = ?,OED_UPDATED_BY = ?,OED_UPDATED_DATE = systimestamp where OED_BATCH_FK = ? and OED_GCPD_USER_FK = ?";

	/*
	 * String GET_CANDIDATE_DATA_FOR_ADMIT_CARD_USER_ID =
	 * "  SELECT oum.oum_user_id, oed.oed_enrollment_pk, ocd.ocd_name,oum.oum_user_pk, oed.OED_ROLL_NO,oed.oed_attempt, "
	 * +
	 * "  otcm.otcm_test_centre_name,     TO_CHAR (TRUNC (obd.obd_test_date), 'dd-mm-yyyy') AS test_date,    "
	 * +
	 * "  TO_CHAR (obd.obd_test_start_time, 'hh24:mi')      || ' - '      || TO_CHAR (obd.obd_test_end_time, 'hh24:mi') AS test_slot "
	 * +
	 * "  FROM oes_user_master oum,    oes_entrollment_details oed,     oes_candidate_details ocd,     oes_test_center_master otcm,   "
	 * +
	 * "  oes_batch_details obd  WHERE oum.oum_user_pk = ocd.ocd_user_fk  AND ocd.ocd_user_fk = oed.oed_gcpd_user_fk  "
	 * +
	 * "  AND oed.oed_batch_fk = obd.obd_batch_pk  AND obd.obd_test_centre_fk = otcm.otcm_test_centre_pk  and oed.OED_FLAG = 0 "
	 * +
	 */
	// " AND oum.OUM_USER_ID = ? and oed.OED_ROLL_NO is not null order by
	// oed_attempt,oum_user_id";

	String GET_CANDIDATE_DATA_FOR_ADMIT_CARD_USER_ID = "  SELECT oum.oum_user_id, oed.oed_enrollment_pk, "
			+ " ocd.OCD_CAND_TITLE,ocd.OCD_FIRST_NAME,ocd.OCD_MIDDLE_NAME,ocd.OCD_LAST_NAME,	"
			+ " oum.oum_user_pk, oed.OED_ROLL_NO,oed.oed_attempt, otcm.otcm_test_centre_name,  " + "   TO_CHAR (TRUNC (obd.obd_test_date), 'dd-mm-yyyy') AS test_date,"
			+ " TO_CHAR (obd.obd_test_start_time, 'hh24:mi')      || ' - '      || " + " TO_CHAR (obd.obd_test_end_time, 'hh24:mi') AS test_slot"
			+ " FROM oes_user_master oum,    oes_entrollment_details oed,     oes_candidate_details ocd, " + "    oes_test_center_master otcm,    oes_batch_details obd "
			+ "  WHERE oum.oum_user_pk = ocd.ocd_user_fk  AND ocd.ocd_user_fk = oed.oed_gcpd_user_fk "
			+ "   AND oed.oed_batch_fk = obd.obd_batch_pk  AND obd.obd_test_centre_fk = otcm.otcm_test_centre_pk " + " and oed.OED_FLAG = 0 AND oum.OUM_USER_ID = ? "
			+ " and  oed.OED_ROLL_NO is not  null order by oed_attempt,oum_user_id ";

	/*
	 * String GET_CANDIDATE_DATA_FOR_ADMIT_CARD =
	 * "SELECT oum.oum_user_id, oed.oed_enrollment_pk, ocd.ocd_name, oum.oum_user_pk,oed.oed_attempt,oed.OED_ROLL_NO,  "
	 * +
	 * "   otcm.otcm_test_centre_name,   TO_CHAR (TRUNC (obd.obd_test_date), 'dd-mm-yyyy') AS test_date,    "
	 * +
	 * "     TO_CHAR (obd.obd_test_start_time, 'hh24:mi')     || ' - '   || TO_CHAR (obd.obd_test_end_time, 'hh24:mi') AS test_slot "
	 * +
	 * " FROM oes_user_master oum,   oes_entrollment_details oed,     oes_candidate_details ocd,    oes_test_center_master otcm, "
	 * +
	 * "  oes_batch_details obd   WHERE oum.oum_user_pk = ocd.ocd_user_fk  AND ocd.ocd_user_fk = oed.oed_gcpd_user_fk  "
	 * +
	 * "  AND oed.oed_batch_fk = obd.obd_batch_pk  AND obd.obd_test_centre_fk = decode(?,0,otcm.otcm_test_centre_pk,?) "
	 * +
	 * "  AND oed.oed_attempt = decode(?,0,oed.oed_attempt,?)  and oed.oed_flag = 0  and otcm.OTCM_TEST_CENTRE_PK = obd.OBD_TEST_CENTRE_FK  and  oed.OED_ROLL_NO is not null order by oed_attempt,oum_user_id"
	 * ;
	 */

	String GET_CANDIDATE_DATA_FOR_ADMIT_CARD_COUNT = "SELECT count(1) "
			+ "  FROM oes_user_master oum,   oes_entrollment_details oed,     oes_candidate_details ocd,    oes_test_center_master otcm, "
			+ "  oes_batch_details obd   WHERE oum.oum_user_pk = ocd.ocd_user_fk  AND ocd.ocd_user_fk = oed.oed_gcpd_user_fk  "
			+ "  AND oed.oed_batch_fk = obd.obd_batch_pk  AND obd.obd_test_centre_fk = decode(?,0,otcm.otcm_test_centre_pk,?) "
			+ "  AND oed.oed_attempt = decode(?,0,oed.oed_attempt,?)  and oed.oed_flag = 0  and otcm.OTCM_TEST_CENTRE_PK = obd.OBD_TEST_CENTRE_FK  and oed.OED_ROLL_NO is not null order by oed_attempt,oum_user_id";

	String GET_CANDIDATE_DATA_FOR_ADMIT_CARD = "select * from (SELECT ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,oum.oum_user_id, oed.oed_enrollment_pk, "
			+ "  ocd.OCD_CAND_TITLE,ocd.OCD_FIRST_NAME,ocd.OCD_MIDDLE_NAME,ocd.OCD_LAST_NAME," + "  oum.oum_user_pk,oed.oed_attempt,oed.OED_ROLL_NO,  "
			+ "   otcm.otcm_test_centre_name,   TO_CHAR (TRUNC (obd.obd_test_date), 'dd-mm-yyyy') AS test_date,    "
			+ "     TO_CHAR (obd.obd_test_start_time, 'hh24:mi')     || ' - '   || TO_CHAR (obd.obd_test_end_time, 'hh24:mi') AS test_slot "
			+ "  FROM oes_user_master oum,   oes_entrollment_details oed,     oes_candidate_details ocd,    oes_test_center_master otcm, "
			+ "  oes_batch_details obd   WHERE oum.oum_user_pk = ocd.ocd_user_fk  AND ocd.ocd_user_fk = oed.oed_gcpd_user_fk  "
			+ "  AND oed.oed_batch_fk = obd.obd_batch_pk  AND obd.obd_test_centre_fk = decode(?,0,otcm.otcm_test_centre_pk,?) "
			+ "  AND oed.oed_attempt = decode(?,0,oed.oed_attempt,?)  and oed.oed_flag= 0  and otcm.OTCM_TEST_CENTRE_PK = obd.OBD_TEST_CENTRE_FK  and oed.OED_ROLL_NO is not null"
			+ "  order by oed_attempt,oum_user_id) where num between ? and ?";

	String UPDATE_GENERATE_HALLTICKET_DATA = "UPDATE OES_ENTROLLMENT_DETAILS OED SET OED.OED_FLAG = ? , OED.OED_UPDATED_BY = ? , OED.OED_ADMIT_CARD_DATE  = systimestamp ,OED.OED_UPDATED_DATE = systimestamp WHERE OED.OED_ENROLLMENT_PK = ?";

	String GET_USER_DETAILS_FOR_ENROLLMENT_ID = "SELECT oum.oum_test_fk,oum.OUM_USER_ID, oum.oum_user_pk, oed.oed_attempt  FROM oes_user_master oum, oes_entrollment_details oed ,oes_test_master otm WHERE oed.oed_gcpd_user_fk = oum.oum_user_pk AND oed.oed_enrollment_pk = ? and otm.OTM_TEST_PK = oum.oum_test_fk";

	/*
	 * String GET_REGISTERED_CANDIDATE =
	 * "SELECT oum.OUM_USER_PK,oum.oum_user_id, ocd_name, otm_test_name, octm_category_code,OCD_REMARKS,ocd.ocd_validated_status FROM oes_candidate_details ocd,oes_test_master otm,oes_user_master oum,oes_category_master ocm"
	 * +
	 * " WHERE ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_user_fk = oum.oum_user_pk AND ocd.ocd_category_fk = ocm.octm_category_pk "
	 * ;
	 */

	String GET_REGISTERED_CANDIDATE = "select * from (SELECT ROW_NUMBER() OVER (ORDER BY oum.oum_user_id) AS num,oum.OUM_USER_PK,oum.oum_user_id, OCD_CAND_TITLE, OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME ,"
			+ " otm_test_name, octm_category_code,OCD_REMARKS,ocd.ocd_validated_status" + "  FROM oes_candidate_details ocd,oes_test_master otm,oes_user_master oum,"
			+ " oes_category_master ocm" + " WHERE ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_user_fk = oum.oum_user_pk AND ocd.ocd_category_fk = ocm.octm_category_pk ";

	String GET_REGISTERED_CANDIDATE_COUNT = "SELECT  COUNT(1) AS CNT FROM OES_CANDIDATE_DETAILS OCD,OES_TEST_MASTER OTM,OES_USER_MASTER OUM, "
			+ " OES_CATEGORY_MASTER OCM WHERE OCD.OCD_TEST_FK = OTM.OTM_TEST_PK AND OCD.OCD_USER_FK = OUM.OUM_USER_PK" + " AND OCD.OCD_CATEGORY_FK = OCM.OCTM_CATEGORY_PK ";

	String GET_SLOT_DETAILS_FOR_CENTER_DATE = "select OBD_BATCH_PK,OBD_AVAILABLE_CAPACITY from oes_batch_details " + " where OBD_TEST_CENTRE_FK = ? and trunc(obd_test_date) = ? "
			+ " and to_char(OBD_TEST_START_TIME,'hh:mi') ||'-'|| to_char(OBD_TEST_END_TIME,'hh:mi') = ?";

	/*
	 * String GET_CANDIDATE_DATA_FOR_SEAT_ALLOCATION_WITH_TEST_DATES =
	 * "SELECT ocpd.ocpd_pref_pk, ocpd.ocpd_user_fk, ocpd.ocpd_pref_test_centre_1_fk,    ocpd.ocpd_pref_test_centre_2_fk,"
	 * + " ocpd.ocpd_pref_test_centre_3_fk ," +
	 * " TO_CHAR(ocpd.OCPD_PREF_TEST_DATE1,'DD-Mon-YYYY') AS OCPD_PREF_TEST_DATE1,"
	 * +
	 * " TO_CHAR(ocpd.OCPD_PREF_TEST_DATE2,'DD-Mon-YYYY') AS OCPD_PREF_TEST_DATE2,"
	 * +
	 * " TO_CHAR(ocpd.OCPD_PREF_TEST_DATE3,'DD-Mon-YYYY') AS OCPD_PREF_TEST_DATE3 "
	 * +
	 * " FROM oes_cand_pref_details ocpd,   oes_user_master oum,   oes_candidate_details ocd,  oes_status_master osm "
	 * +
	 * " WHERE oum.oum_user_pk = ocpd.ocpd_user_fk  AND ocd.ocd_user_fk = oum.oum_user_pk  AND ocd.ocd_user_fk = ocpd.ocpd_user_fk  "
	 * +
	 * " AND osm.osm_status_pk = ocd.ocd_status_id_fk  AND ocd.ocd_status_id_fk >= ?  "
	 * + " AND ocpd.ocpd_test_centre_fk IS NULL " +
	 * "  and ocd.OCD_VALIDATED_STATUS = ?	order by ocpd.OCPD_APPL_DATE asc";
	 */

	String GET_CANDIDATE_DATA_FOR_SEAT_ALLOCATION_TEST_DATES = " select ocpd.ocpd_pref_pk, ocpd.ocpd_user_fk, ocpd.ocpd_pref_test_centre_1_fk, "
			+ "	ocpd.ocpd_pref_test_centre_2_fk,    ocpd.ocpd_pref_test_centre_3_fk  ," + "   to_char(OCPD.OCPD_PREF_TEST_DATE1,'dd-Mon-yyyy')as OCPD_PREF_TEST_DATE1 ,"
			+ "   to_char(OCPD.OCPD_PREF_TEST_DATE2,'dd-Mon-yyyy')as OCPD_PREF_TEST_DATE2 ," + "   to_char(OCPD.OCPD_PREF_TEST_DATE3,'dd-Mon-yyyy')as OCPD_PREF_TEST_DATE3  "
			+ "	from oes_cand_pref_details ocpd,   oes_user_master oum,   oes_candidate_details ocd, " + "	oes_status_master osm      "
			+ "	where oum.oum_user_pk = ocpd.ocpd_user_fk  and ocd.ocd_user_fk = oum.oum_user_pk  "
			+ " 	and ocd.ocd_user_fk = ocpd.ocpd_user_fk     and osm.osm_status_pk = ocd.ocd_status_id_fk  " + "   and ocpd.ocpd_test_centre_fk is null ";

	String GET_BATCH_DETAILS_FOR_TEST_DATES = "select obd.OBD_BATCH_PK,obd.OBD_AVAILABLE_CAPACITY from oes_batch_details obd where trunc(obd.OBD_TEST_DATE) = ? and obd.OBD_TEST_CENTRE_FK = ? and obd.OBD_STATUS = 'A' order by obd.OBD_TEST_CENTRE_FK , obd.OBD_TEST_DATE,obd.OBD_BATCH_PK  asc";

	String GET_PAYMENT_REPORT_SEARCH_RESULT_COUNT =
			/*
			 * "SELECT COUNT(1) " +
			 * " FROM OES_PAYMENT_DETAILS opd, OES_PAYMENT_MASTER opm, OES_CANDIDATE_DETAILS ocd, OES_USER_MASTER oum, "
			 * +
			 * " OES_TEST_MASTER otm, OES_CATEGORY_MASTER ocm, OES_FEES_MASTER ofm "
			 * +
			 * " WHERE opd.OPD_PAYMENT_FK = opm.OPTM_PAYMENT_PK AND ocd.OCD_USER_FK = opd.OPD_USER_FK "
			 * +
			 * " AND ocd.OCD_TEST_FK = otm.OTM_TEST_PK AND ocd.OCD_CATEGORY_FK = ocm.OCTM_CATEGORY_PK "
			 * +
			 * " AND ofm.OFM_TEST_FK = ocd.OCD_TEST_FK AND ocd.OCD_CATEGORY_FK = ofm.OFM_CATEGORY_FK "
			 * +
			 * " AND ocd.OCD_USER_FK = oum.OUM_USER_PK AND oum.OUM_USER_PK = opd.OPD_USER_FK AND opd.OPD_PAYMENT_FK = ?"
			 * ;
			 */
			"   SELECT  count(*) \r\n" + "    FROM oes_user_master oum \r\n" + "    INNER JOIN oes_candidate_details ocd on (ocd.ocd_user_fk = oum.oum_user_pk)\r\n"
					+ "    INNER JOIN oes_category_master ocm on (ocm.octm_category_pk = ocd.ocd_category_fk)\r\n"
					+ "    INNER  JOIN oes_test_master otm on(otm.otm_test_pk= oum.oum_test_fk)\r\n"
					+ "    INNER JOIN oes_payment_details opd on (opd.opd_user_fk=ocd.ocd_user_fk)\r\n"
					+ "    INNER JOIN oes_payment_master opm ON (opm.optm_payment_pk = opd.opd_payment_fk)\r\n" + "    where opd.opd_payment_fk=?";
	String GET_PAYMENT_REPORT_SEARCH_RESULT = /*
												 * "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,oum.OUM_USER_ID, "
												 * +
												 * " ocd.OCD_CAND_TITLE,ocd.OCD_FIRST_NAME,ocd.OCD_MIDDLE_NAME,ocd.OCD_LAST_NAME , "
												 * +
												 * "otm.OTM_TEST_NAME, ocm.OCTM_CATEGORY_CODE, opm.OPTM_PAYMENT_DESC, ofm.OFM_FEES, oum.OUM_MOBILE_NO,oum.OUM_EMAIL_ID, "
												 * +
												 * "TO_CHAR(opd.OPD_UPDATED_DATE,'DD-MON-YYYY') AS UPDATED_DATE, TO_CHAR(opd.OPD_CREATED_DATE ,'DD-MON-YYYY') AS CREATED_DATE, "
												 * +
												 * "TO_CHAR(opd.OPD_DD_DATE ,'DD-MON-YYYY') AS OPD_DD_DATE, opd.OPD_DD_CHALLAN_RECEIPT_NO, opd.OPD_BRANCH_NAME, opd.OPD_BRANCH_CODE, opd.OPD_BANK_NAME, opd.OPD_CITY, opd.OPD_TRANSACTION_NO,opd_transaction_date AS online_transaction_date, opd.OPD_VALIDATED_STATUS "
												 * +
												 * "FROM OES_PAYMENT_DETAILS opd, OES_PAYMENT_MASTER opm, OES_CANDIDATE_DETAILS ocd, OES_USER_MASTER oum, "
												 * +
												 * "OES_TEST_MASTER otm, OES_CATEGORY_MASTER ocm, OES_FEES_MASTER ofm "
												 * +
												 * "WHERE opd.OPD_PAYMENT_FK = opm.OPTM_PAYMENT_PK AND ocd.OCD_USER_FK = opd.OPD_USER_FK "
												 * +
												 * "AND ocd.OCD_TEST_FK = otm.OTM_TEST_PK AND ocd.OCD_CATEGORY_FK = ocm.OCTM_CATEGORY_PK "
												 * +
												 * "AND ofm.OFM_TEST_FK = ocd.OCD_TEST_FK AND ocd.OCD_CATEGORY_FK = ofm.OFM_CATEGORY_FK "
												 * +
												 * "AND ocd.OCD_USER_FK = oum.OUM_USER_PK AND oum.OUM_USER_PK = opd.OPD_USER_FK AND opd.OPD_PAYMENT_FK = ?"
												 * ;
												 */"SELECT * FROM \r\n" + "(\r\n" + "    SELECT  ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,\r\n"
			+ "            oum.OUM_USER_ID,  \r\n" + "            ocd.OCD_CAND_TITLE,\r\n" + "            ocd.OCD_FIRST_NAME,\r\n" + "            ocd.OCD_MIDDLE_NAME,\r\n"
			+ "            ocd.OCD_LAST_NAME , \r\n" + "            otm.OTM_TEST_NAME, \r\n" + "            ocm.OCTM_CATEGORY_CODE, \r\n"
			+ "            opm.OPTM_PAYMENT_DESC, \r\n" + "            opd.OPD_AMOUNT, \r\n" + "            oum.OUM_MOBILE_NO,\r\n" + "            oum.OUM_EMAIL_ID, \r\n"
			+ "            TO_CHAR(opd.OPD_UPDATED_DATE,'DD-MON-YYYY') AS UPDATED_DATE, \r\n" + "            TO_CHAR(opd.OPD_CREATED_DATE ,'DD-MON-YYYY') AS CREATED_DATE, \r\n"
			+ "            TO_CHAR(opd.OPD_DD_DATE ,'DD-MON-YYYY') AS OPD_DD_DATE, \r\n" + "            opd.OPD_DD_CHALLAN_RECEIPT_NO, \r\n"
			+ "            opd.OPD_BRANCH_NAME, \r\n" + "            opd.OPD_BRANCH_CODE, \r\n" + "            opd.OPD_BANK_NAME, \r\n" + "            opd.OPD_CITY, \r\n"
			+ "            opd.OPD_TRANSACTION_NO,\r\n" + "            opd_dd_date AS online_transaction_date, \r\n" + "            opd.OPD_VALIDATED_STATUS \r\n"
			+ "    FROM oes_user_master oum \r\n" + "    INNER JOIN oes_candidate_details ocd on (ocd.ocd_user_fk = oum.oum_user_pk)\r\n"
			+ "    INNER JOIN oes_category_master ocm on (ocm.octm_category_pk = ocd.ocd_category_fk)\r\n" +
			// " INNER JOIN oes_candidate_job_mapping ON
			// (oes_candidate_job_mapping.ocjm_user_fk = oum.oum_user_pk)\r\n" +
			"    INNER JOIN oes_test_master otm on(otm.otm_test_pk= oum.oum_test_fk)\r\n" + "    INNER JOIN oes_payment_details opd on (opd.opd_user_fk=ocd.ocd_user_fk)\r\n"
			+ "    INNER JOIN oes_payment_master opm ON (opm.optm_payment_pk = opd.opd_payment_fk)\r\n" + "    where opd.opd_payment_fk=?";

	String GET_PAYMENT_REPORT_SEARCH_RESULT_EXCEL =

			"SELECT * FROM \r\n" + "(\r\n" + "    SELECT  ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,\r\n" + "            oum.OUM_USER_ID,  \r\n"
					+ "            ocd.OCD_CAND_TITLE,\r\n" + "            ocd.OCD_FIRST_NAME,\r\n" + "            ocd.OCD_MIDDLE_NAME,\r\n"
					+ "            ocd.OCD_LAST_NAME , \r\n" + "            otm.OTM_TEST_NAME, \r\n" + "            ocm.OCTM_CATEGORY_CODE, \r\n"
					+ "            opm.OPTM_PAYMENT_DESC, \r\n" + "            opd.opd_amount as OFM_FEES, \r\n" + "            oum.OUM_MOBILE_NO,\r\n"
					+ "            oum.OUM_EMAIL_ID, \r\n" + "            TO_CHAR(opd.OPD_UPDATED_DATE,'DD-MON-YYYY') AS UPDATED_DATE, \r\n"
					+ "            TO_CHAR(opd.OPD_CREATED_DATE ,'DD-MON-YYYY') AS CREATED_DATE, \r\n" + "            TO_CHAR(opd.OPD_DD_DATE ,'DD-MON-YYYY') AS OPD_DD_DATE, \r\n"
					+ "            opd.OPD_DD_CHALLAN_RECEIPT_NO, \r\n" + "            opd.OPD_BRANCH_NAME, \r\n" + "            opd.OPD_BRANCH_CODE, \r\n"
					+ "            opd.OPD_BANK_NAME, \r\n" + "            opd.OPD_CITY, \r\n" + "            opd.OPD_TRANSACTION_NO,\r\n"
					+ "            opd_dd_date AS online_transaction_date, \r\n" + "            opd.OPD_VALIDATED_STATUS \r\n" + "    FROM oes_user_master oum \r\n"
					+ "    INNER JOIN oes_candidate_details ocd on (ocd.ocd_user_fk = oum.oum_user_pk)\r\n"
					+ "    INNER JOIN oes_category_master ocm on (ocm.octm_category_pk = ocd.ocd_category_fk)\r\n"
					+ "    INNER JOIN oes_candidate_job_mapping ON (oes_candidate_job_mapping.ocjm_user_fk = oum.oum_user_pk)\r\n"
					+ "    INNER JOIN oes_test_master otm on(otm.otm_test_pk= oes_candidate_job_mapping.ocjm_test_fk)\r\n"
					+ "    INNER JOIN oes_payment_details opd on (opd.opd_user_fk=ocd.ocd_user_fk and opd.opd_test_fk=otm.otm_test_pk)\r\n"
					+ "    INNER JOIN oes_payment_master opm ON (opm.optm_payment_pk = opd.opd_payment_fk)\r\n" + "    where opd.opd_payment_fk=?";
	String GET_CANDIDATE_DETAILS_FOR_PAYMENT_NOT_SUBMITTED_COUNT = "select count(1)\r\n" + "from oes_user_master oum\r\n"
			+ "INNER JOIN oes_candidate_details ocd ON (oum.OUM_USER_PK = ocd.OCD_USER_FK)\r\n" + "LEFT JOIN oes_test_master otm ON (otm.OTM_TEST_PK = ocd.OCD_TEST_FK\r\n"
			+ "AND otm.OTM_TEST_PK = (CASE WHEN ? = 0 THEN otm.OTM_TEST_PK ELSE ? END ))\r\n"
			+ "LEFT JOIN oes_category_master ocm  ON (ocm.OCTM_CATEGORY_PK = ocd.OCD_CATEGORY_FK)\r\n" + "where 1=1\r\n" + " and ocd.OCD_STATUS_ID_FK = '20'  \r\n"
			+ "and ocd.OCD_USER_FK not in (select opd.OPD_USER_FK from oes_payment_details opd)";

	String GET_CANDIDATE_DETAILS_FOR_PAYMENT_NOT_SUBMITTED = "select *\r\n" + "from \r\n"
			+ "(select ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,oum.OUM_USER_ID,oum.Oum_MOBILE_NO,  \r\n"
			+ "			 oum.Oum_EMAIL_ID,ocd.OCD_FIRST_NAME,ocd.OCD_MIDDLE_NAME,ocd.OCD_LAST_NAME,  \r\n" + "			 otm.OTM_TEST_NAME,ocm.OCTM_CATEGORY_code  \r\n"
			+ "from oes_user_master oum\r\n" + "INNER JOIN oes_candidate_details ocd ON (oum.OUM_USER_PK = ocd.OCD_USER_FK)\r\n"
			+ "LEFT JOIN oes_test_master otm ON (otm.OTM_TEST_PK = oum.oum_test_fk\r\n" + "AND otm.OTM_TEST_PK = (CASE WHEN ? = 0 THEN otm.OTM_TEST_PK ELSE ? END ))\r\n"
			+ "LEFT JOIN oes_category_master ocm  ON (ocm.OCTM_CATEGORY_PK = ocd.OCD_CATEGORY_FK)\r\n" + "where 1=1\r\n" + " and ocd.OCD_STATUS_ID_FK = '20'  \r\n"
			+ "and ocd.OCD_USER_FK not in (select opd.OPD_USER_FK from oes_payment_details opd)\r\n" + "order by oum.OUM_USER_ID asc ) as aliasTEST  \r\n"
			+ "			 where num between ? and ?\r\n" + "			 ";

	String GET_CANDIDATE_DETAILS_FOR_PAYMENT_NOT_SUBMITTED_EXCEL = "select oum.OUM_USER_ID,oum.Oum_MOBILE_NO, "
			+ " oum.Oum_EMAIL_ID,ocd.OCD_FIRST_NAME,ocd.OCD_MIDDLE_NAME,ocd.OCD_LAST_NAME, " + " otm.OTM_TEST_NAME,ocm.OCTM_CATEGORY_code "
			+ " from oes_user_master oum,oes_candidate_details ocd,oes_test_master otm,oes_category_master ocm " + " where oum.OUM_USER_PK = ocd.OCD_USER_FK  "
			+ " and otm.OTM_TEST_PK = oum.oum_test_fk " + " and ocm.OCTM_CATEGORY_PK = ocd.OCD_CATEGORY_FK "
			+ " AND otm.OTM_TEST_PK = (CASE WHEN ? = 0 THEN otm.OTM_TEST_PK ELSE ? END )  " + " and ocd.OCD_USER_FK not in (select opd.OPD_USER_FK from oes_payment_details opd) "
			+ " order by oum.OUM_USER_ID asc  ";
}
