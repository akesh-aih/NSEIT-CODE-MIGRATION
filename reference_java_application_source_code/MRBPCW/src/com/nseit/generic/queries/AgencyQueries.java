package com.nseit.generic.queries;

// Agency related generic queries added by Tarka on 03-Jan-2012
public interface AgencyQueries {

	String SELECT_NEXT_VALUE = " select OES_user_master_seq.nextval from dual ";

	String SELECT_REQUEST_ID = "SELECT MAX(OUM_REQUEST_ID) FROM OES_USER_MASTER ";

	String SELECT_LOGIN_CREDENTIALS = "   SELECT gum.OUM_USER_PK, gum.OUM_USER_ID, gum.OUM_PASSWORD, gum.OUM_IPIN, "
			+ " gum.OUM_REQUEST_ID, to_char(gum.OUM_CREATED_DATE,'dd-Mon-yyyy') as date1, to_char(gum.OUM_CREATED_DATE,'hh24:mi') as date2, gtm.OTM_TEST_NAME "
			+ " FROM  OES_USER_MASTER gum, OES_TEST_MASTER gtm "
			+ " WHERE gum.OUM_TEST_FK = gtm.OTM_TEST_PK and gum.OUM_REQUEST_ID = (SELECT MAX(OUM_REQUEST_ID) FROM OES_USER_MASTER)";

	String SELECT_LOGIN_CREDENTIAL_DISPLAY = "  select * from( " + " select distinct OUM_REQUEST_ID,to_char(OUM_CREATED_DATE,'dd-Mon-yyyy') as date1, "
			+ " to_char(OUM_CREATED_DATE,'hh24:mi') as date2, " + " count(OUM_REQUEST_ID) over(partition by OUM_REQUEST_ID) as count1,"
			+ " max(to_char(OUM_CREATED_DATE,'hh24:mi')) over(partition by OUM_REQUEST_ID order by OUM_REQUEST_ID) max_tm "
			+ " from OES_user_master where OUM_REQUEST_ID is not null and OUM_user_type='C' and OUM_stage_fk=0 and OUM_FIRST_LOGIN = 'N' " + " order by OUM_REQUEST_ID desc "
			+ " ) a " + " where date2=max_tm ";

	String SELECT_TEST_MASTER = " SELECT OTM_TEST_PK, OTM_TEST_NAME FROM OES_TEST_MASTER where OTM_STATUS='A'" + " ORDER BY OTM_TEST_NAME ";

	String SELECT_TEST_NAME = " SELECT  OTM_TEST_NAME FROM OES_TEST_MASTER WHERE OTM_TEST_PK = ? ";

	String SELECT_LOGIN_COUNT = " select count(*) from OES_user_master " + " where OUM_REQUEST_ID is not null and OUM_user_type='C' and OUM_stage_fk=0 and OUM_FIRST_LOGIN = 'N'  ";

	String SELECT_ALL_DATA_TO_EXPORT = " SELECT gum.OUM_USER_PK, gum.OUM_USER_ID, gum.OUM_PASSWORD, gum.OUM_IPIN, "
			+ " gum.OUM_REQUEST_ID, to_char(gum.OUM_CREATED_DATE,'dd-Mon-yyyy') as date1, to_char(gum.OUM_CREATED_DATE,'hh24:mi') as date2, gtm.OTM_TEST_NAME "
			+ " FROM OES_USER_MASTER gum, OES_TEST_MASTER gtm " + " WHERE gum.OUM_TEST_FK = gtm.OTM_TEST_PK and gum.OUM_STAGE_FK = 0 AND gum.OUM_FIRST_LOGIN = 'N' "
			+ " AND gum.OUM_USER_TYPE = 'C' AND gum.OUM_REQUEST_ID IS NOT NULL " + " ORDER BY gum.OUM_REQUEST_ID desc, gtm.OTM_TEST_NAME, gum.OUM_USER_ID ";

	String SELECT_DATA_FOR_REQUESTID_TO_EXPORT = " SELECT gum.OUM_USER_PK, gum.OUM_USER_ID, gum.OUM_PASSWORD, gum.OUM_IPIN, "
			+ " gum.OUM_REQUEST_ID, to_char(gum.OUM_CREATED_DATE,'dd-Mon-yyyy') as date1, to_char(gum.OUM_CREATED_DATE,'hh24:mi') as date2, gtm.OTM_TEST_NAME "
			+ " FROM OES_USER_MASTER gum, OES_TEST_MASTER gtm " + " WHERE gum.OUM_TEST_FK = gtm.OTM_TEST_PK and gum.OUM_STAGE_FK = 0 AND gum.OUM_FIRST_LOGIN = 'N' "
			+ " AND gum.OUM_USER_TYPE = 'C' AND gum.OUM_REQUEST_ID =? ORDER BY gum.OUM_REQUEST_ID, gum.OUM_USER_ID ";

	String INSERT_LOGIN_DETAILS = " INSERT INTO OES_USER_MASTER "
			+ " (OUM_USER_PK, OUM_USER_ID, OUM_PASSWORD, OUM_IPIN, OUM_FIRST_LOGIN, OUM_STAGE_FK, OUM_USER_TYPE, OUM_REQUEST_ID, "
			+ " OUM_TEST_FK, OUM_CREATED_BY, OUM_CREATED_DATE) "
			+ " Values(?, ? || LPAD (?, 8, 0), ?, UTL_I18N.string_to_raw (?, 'AL32UTF8'), 'N', 0, 'C', ?, ?, ?, SYSTIMESTAMP)  ";

	// Queries for Dashboard
	// START For Application Form Management

	// For Total Candidates

	String GET_TOTAL_NO_OF_CANDIDATES = "SELECT COUNT(1) FROM OES_USER_MASTER GUM WHERE GUM.OUM_USER_TYPE='C' ";

	// For Initiated
	String GET_NO_OF_INITIATED_CANDIDATE = "SELECT COUNT(1) FROM oes_user_master oum, oes_candidate_details ocd, "
			+ "oes_status_master osm WHERE oum.oum_user_pk = ocd.ocd_user_fk " + "AND ocd.ocd_status_id_fk = osm.osm_status_pk " + "AND  ocd.OCD_STATUS_ID_FK < 5 ";

	// For Submitted
	/*
	 * String GET_NO_OF_CANDIDATE_APPLICATION_SUBMITTED =
	 * "SELECT COUNT(1) FROM oes_user_master oum, oes_candidate_details ocd, oes_category_master ocm, "
	 * + "oes_status_master osm WHERE oum.oum_user_pk = ocd.ocd_user_fk "+
	 * "AND ocd.ocd_category_fk = ocm.octm_category_pk "+
	 * "AND ocd.ocd_status_id_fk = osm.osm_status_pk "+
	 * "AND osm.osm_status_pk >= ?";
	 */
	/*
	 * String GET_NO_OF_CANDIDATE_APPLICATION_SUBMITTED =
	 * "SELECT COUNT(1) FROM oes_user_master oum \r\n" +
	 * "inner join oes_candidate_details ocd  on oum.oum_user_pk = ocd.ocd_user_fk  \r\n"
	 * +
	 * "inner join oes_category_master ocm on ocd.ocd_category_fk = ocm.octm_category_pk   \r\n"
	 * +
	 * "inner join oes_status_master osm on ocd.ocd_status_id_fk = osm.osm_status_pk     \r\n"
	 * +
	 * "inner join oes_state_master ostm on coalesce(ocd.ocd_perm_state_fk, ocd.ocd_perm_union_terr_fk) = ostm.osm_state_pk   \r\n"
	 * +
	 * "left outer join oes_test_master otm on ocd.ocd_test_fk = otm.otm_test_pk \r\n"
	 * +
	 * "and otm.otm_test_pk = CASE WHEN 0 = 0 THEN otm.otm_test_pk ELSE 0 END   \r\n"
	 * +
	 * "left outer join OES_CAND_PREF_DETAILS ocpd  on ocd.ocd_user_fk = ocpd.OCPD_USER_FK \r\n"
	 * +
	 * "left  join oes_candidate_job_mapping ocjm  on ocjm.ocjm_user_fk=ocd.ocd_user_fk  \r\n"
	 * +
	 * " WHERE ocm.octm_category_pk = CASE WHEN 0 = 0 THEN ocm.octm_category_pk ELSE 0 END \r\n"
	 * + " AND ocd.ocd_form_submited_date IS NOT NULL  \r\n" +
	 * " AND osm.osm_status_pk >= ? ";
	 */

	String GET_NO_OF_CANDIDATE_APPLICATION_SUBMITTED = "SELECT COUNT(1) FROM oes_user_master oum, oes_candidate_details ocd, \r\n"
			+ "            oes_status_master osm WHERE oum.oum_user_pk = ocd.ocd_user_fk \r\n" + "            AND ocd.ocd_status_id_fk = osm.osm_status_pk  \r\n"
			+ "            AND  ocd.OCD_STATUS_ID_FK >= 5";

	// For Approved and Rejected with value 1 or 0
	// String GET_NO_OF_CANDIDATE_APPROVED = "SELECT COUNT(1) FROM
	// OES_CANDIDATE_DETAILS GUM WHERE GUM.OCD_VALIDATED_STATUS = ? ";

	String GET_NO_OF_CANDIDATE_AS_PER_STATUS = "SELECT COUNT(1) FROM OES_CANDIDATE_DETAILS GUM WHERE GUM.OCD_STATUS_ID_FK = ? ";
	// String GET_NO_OF_CANDIDATE_PAYMENT = "SELECT COUNT(1) FROM
	// OES_PAYMENT_DETAILS WHERE CAST(OPD_VALIDATED_STATUS as TEXT) = CAST(? as
	// TEXT) AND CAST(OPD_PAYMENT_FK as INT) = CAST(? as INT)";
	/*
	 * String GET_NO_OF_CANDIDATE_PAYMENT = "   select sum(cnt) from       \r\n"
	 * + "			             (\r\n" +
	 * "			             select  opd_validated_status,count(distinct opd_user_fk) cnt from  OES_PAYMENT_DETAILS opd  \r\n"
	 * +
	 * "						INNER JOIN oes_candidate_details ocd  ON ocd.ocd_user_fk = opd.opd_user_fk \r\n"
	 * +
	 * "						INNER JOIN oes_user_master oum  ON ocd.ocd_user_fk = oum.oum_user_pk AND oum.oum_user_pk = opd.opd_user_fk  \r\n"
	 * +
	 * "						INNER JOIN oes_test_master otm  ON otm.otm_test_pk = oum.oum_test_fk   \r\n"
	 * + "			      group by opd_validated_status\r\n" +
	 * "						union all  select 'A',0 \r\n" +
	 * "			      union all  select 'R',0 \r\n" +
	 * "			            ) v \r\n" +
	 * "			      where opd_validated_status = 'A'\r\n" +
	 * "			     group by opd_validated_status";
	 */
	String GET_NO_OF_CANDIDATE_PAYMENT = "SELECT COUNT(1) FROM OES_PAYMENT_DETAILS where opd_payment_fk=1 and opd_validated_status='A' ";

	String GET_NO_OF_CANDIDATE_APPLICATION_PENDING = "SELECT COUNT(1) FROM OES_CANDIDATE_DETAILS GUM WHERE  GUM.OCD_STATUS_ID_FK >= ? AND GUM.OCD_VALIDATED_STATUS IS NULL";

	// Attempt wise
	// String GET_CANDIDATE_COUNT_OF_ADMIT_CARD = "select count(*) from
	// oes_entrollment_details oed where oed.OED_ATTEMPT = ? and oed.OED_FLAG =
	// ?";

	// For all attempt
	String GET_CANDIDATE_COUNT_OF_ADMIT_CARD = "select count(1) from oes_entrollment_details where oed_flag =1 and oed_admit_card_date is not null";

	String GET_DATA_FOR_ATTEMPT = "select count(1) from oes_entrollment_details oed where oed.OED_ATTEMPT = ?";

	String GET_PAYMENT_SUBMITTED_COUNT = "SELECT count(*) from OES_PAYMENT_DETAILS";

	String GET_PAYMENT_APPROVAL_PENDING_COUNT = "SELECT count(*) from OES_PAYMENT_DETAILS WHERE OPD_VALIDATED_STATUS IS NULL AND CAST(OPD_PAYMENT_FK as INT)= CAST(? as INT)";

	String GET_TEST_SLOTS = " select * from( SELECT DISTINCT TO_CHAR (gbd.gbd_test_start_time, 'HH24:MI') AS start_date, TO_CHAR (gbd.gbd_test_end_time, 'HH24:MI') AS end_date FROM OES_batch_details gbd) ORDER BY start_date";

	String GET_DISCIPLINE = " select gtm.OTM_TEST_NAME from OES_test_master gtm ";

	String SELECT_CANDIDATE_DATA_FOR_GENERIC_MAILS = "SELECT gum.OUM_USER_PK,gum.OUM_USER_ID,gum.OUM_EMAIL_ID, gum.OUM_MOBILE_NO "
			+ " FROM OES_user_master gum,      OES_candidate_details gcd,      OES_test_master gtm,     OES_test_center_master gtcm,     OES_batch_details gbd,    OES_entrollment_details ged "
			+ " WHERE gcd.OCD_user_fk = gum.OUM_user_pk   AND gtm.OTM_test_pk = gum.OUM_test_fk  AND ged.oed_batch_fk = gbd.obd_batch_pk "
			+ " AND gbd.obd_test_centre_fk = gtcm.otcm_test_centre_pk  AND ged.oed_gcpd_user_fk = gum.OUM_user_pk  AND ged.oed_gcpd_user_fk = gcd.OCD_user_fk "
			+ " AND gum.OUM_test_fk IN (%s)  AND gtcm.otcm_test_centre_pk IN (%s)  and trunc(gbd.oBD_TEST_DATE) between NVL (?, TRUNC (gbd.obd_test_date)) and NVL (?, TRUNC (gbd.obd_test_date))";

	String INSERT_USER_IMPORT_DETAILS = "INSERT INTO OES_USER_MASTER ( OUM_USER_PK, OUM_USER_ID,OUM_EMAIL_ID,OUM_MOBILE_NO, OUM_PASSWORD, "
			+ "  OUM_IPIN, OUM_FIRST_LOGIN, OUM_STAGE_FK, OUM_USER_TYPE, OUM_TEST_FK, OUM_ROLE_FK, OUM_CREATED_BY, OUM_CREATED_DATE) "
			+ " VALUES (nextVal('OES_USER_MASTER_SEQ'), ?,?,?,?,?,?,?,?,?,?,?,systimestamp)";

	/*
	 * String INSERT_USER_QUESTION =
	 * "INSERT INTO OES_QSTN_ANSW (OQA_USER_FK, OQA_REF_FK, OQA_QSTN_ANS,OQA_CREATED_BY, OQA_CREATED_DATE)"
	 * + " VALUES (CAST(? as INT) ,CAST(? as INT) ,? ,?,CURRENT_TIMESTAMP)";
	 */

	String GET_HELP_CENTER_LIST = "select GHCM_HELP_CENTER_PK,GHCM_HELP_CENTER_NAME  from OES_HELP_CENTER_MASTER ORDER BY GHCM_HELP_CENTER_NAME";

	String UPDATE_HELP_CENTER_USER_PASSWORD = " update OES_USER_MASTER  set OUM_PASSWORD = ?  where  OUM_HELPCENTRE_FK = ? ";

	/*
	 * String CHECK_QUESTION_ANSWER =
	 * "SELECT OQA.OQA_QSTN_ANS FROM OES_USER_MASTER OUM, OES_QSTN_ANSW OQA  WHERE OUM.OUM_USER_ID = ? AND OQA.OQA_REF_FK = ?"
	 * + " AND OUM.OUM_USER_PK = OQA.OQA_USER_FK";
	 */

	/*
	 * String CHECK_IPIN =
	 * "SELECT OUM_IPIN FROM OES_USER_MASTER WHERE OUM_USER_ID=?";
	 * 
	 * String CHECK_USER_ID_USING_IPIN =
	 * "SELECT OUM.OUM_USER_ID FROM OES_USER_MASTER OUM, OES_QSTN_MASTER OQM WHERE OQM.OQM_QSTN_PK = ?"
	 * + " AND OUM.OUM_IPIN = ? AND OUM.OUM_EMAIL_ID = ? ";
	 */

	// OFFLINE SCHEDULING
	String GET_COUNT_OF_SCHEDULED_CANDIDATE = "SELECT COUNT(1) FROM OES_ENTROLLMENT_DETAILS";

	String GET_COUNT_OF_NON_SCHEDULED_CANDIDATE_PAYMENT_ON = "select count(1) from oes_candidate_details ocd where ocd_user_fk in (select opd_user_fk from oes_payment_details where (OPD_VALIDATED_STATUS is null";

	String GET_COUNT_OF_NON_SCHEDULED_CANDIDATE_PAYMENT_OFF = "select count(1) from oes_candidate_details ocd where " + "  ocd.ocd_validated_status = 1 "
			+ " and ocd.ocd_user_fk not in( " + " select OED_GCPD_USER_FK from oes_entrollment_details" + " )";

	String GET_COUNT_OF_PENDING_FOR_SCHEDULED_CANDIDATE_PAYMENT_ON = "select count(1) from oes_candidate_details ocd where ocd_user_fk in (select opd_user_fk from oes_payment_details where opd_validated_status  is not null";

	String GET_COUNT_OF_PENDING_FOR_SCHEDULED_CANDIDATE_PAYMENT_OFF = "select count(1) from oes_candidate_details ocd " + " where  ocd.ocd_validated_status = 1"
			+ "  and ocd.ocd_user_fk not in( " + " select OED_GCPD_USER_FK from oes_entrollment_details" + " )";
	// OTBS

	String GET_OTBS_SCHEDULED_CANDIDATE_ATTEMPT_WISE = "select count(*) from oes_entrollment_details oed where oed.OED_ATTEMPT = ? and oed.OED_FLAG = ?";

	/*
	 * String GET_CANDIDATE_DETAILS_COUNT_FOR_REPORT_DISPLAY =
	 * "SELECT COUNT(1) FROM oes_user_master oum,  "+
	 * "oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, oes_status_master osm, oes_state_master ostm WHERE oum.oum_user_pk = ocd.ocd_user_fk  "
	 * +
	 * "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk  "
	 * +
	 * "AND nvl(ocd.ocd_perm_state_fk, ocd.ocd_perm_union_terr_fk) = ostm.osm_state_pk  "
	 * + "AND ocd.ocd_status_id_fk = osm.osm_status_pk  "+
	 * "AND otm.otm_test_pk = DECODE (?, 'All', otm.otm_test_pk, ?) "+ //
	 * $P{PROGRAM_NAME} = 2
	 * "AND ocm.octm_category_pk = DECODE (?, 'All', ocm.octm_category_pk, ?) ";
	 */

	/*
	 * String GET_CANDIDATE_DETAILS_COUNT_FOR_REPORT_DISPLAY =
	 * "SELECT COUNT(1) FROM oes_user_master oum,  "+
	 * "oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, oes_status_master osm, oes_state_master ostm , OES_CAND_PREF_DETAILS ocpd  WHERE oum.oum_user_pk = ocd.ocd_user_fk  AND ocd.ocd_user_fk =+ ocpd.OCPD_USER_FK "
	 * +
	 * "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk "
	 * +
	 * "AND coalesce(ocd.ocd_perm_state_fk, ocd.ocd_perm_union_terr_fk) = ostm.osm_state_pk  "
	 * + "AND ocd.ocd_status_id_fk = osm.osm_status_pk  "+
	 * "AND otm.otm_test_pk = CASE WHEN ? = 0 THEN otm.otm_test_pk ELSE ? END "+
	 * // $P{PROGRAM_NAME} = 2
	 * "AND ocm.octm_category_pk = CASE WHEN ? = 0 THEN ocm.octm_category_pk ELSE ? END "
	 * ;
	 */

	/*
	 * String GET_CANDIDATE_DETAILS_COUNT_FOR_REPORT_DISPLAY =
	 * "SELECT COUNT(1) FROM oes_user_master oum inner join oes_candidate_details ocd  on oum.oum_user_pk = ocd.ocd_user_fk and  oum_user_id ilike'%USRB%' "
	 * +
	 * " inner join oes_category_master ocm on ocd.ocd_category_fk = ocm.octm_category_pk  "
	 * +
	 * " inner join oes_status_master osm on ocd.ocd_status_id_fk = osm.osm_status_pk    "
	 * +
	 * " inner join oes_state_master ostm on coalesce(ocd.ocd_perm_state_fk, ocd.ocd_perm_union_terr_fk) = ostm.osm_state_pk  "
	 * +
	 * " left outer join oes_test_master otm on ocd.ocd_test_fk = otm.otm_test_pk and otm.otm_test_pk = CASE WHEN ? = -1 THEN otm.otm_test_pk ELSE ? END  "
	 * +
	 * " left outer join OES_CAND_PREF_DETAILS ocpd  on ocd.ocd_user_fk = ocpd.OCPD_USER_FK"
	 * +
	 * " left  join oes_candidate_job_mapping ocjm  on ocjm.ocjm_user_fk=ocd.ocd_user_fk "
	 * +
	 * " WHERE ocm.octm_category_pk = CASE WHEN ? = -1 THEN ocm.octm_category_pk ELSE ? END"
	 * ;
	 */
	String GET_CANDIDATE_DETAILS_COUNT_FOR_REPORT_DISPLAY = "select  count(1)  FROM oes_user_master oum left join oes_candidate_details ocd  on oum.oum_user_pk = ocd.ocd_user_fk\r\n"
			+ "  left join oes_category_master octm on octm.OCTM_CATEGORY_PK ::varchar= ocd.ocd_community \r\n"
			+ "  left join oes_test_master otm on oum.oum_test_fk=otm.otm_test_pk and otm_status='A' \r\n"
			+ "  WHERE (otm_test_pk in(?)  or  -1 in  (?) )  and (octm_category_pk in(?)  or  -1 in  (?)) and OUM_USER_TYPE = 'C'";

	/*
	 * String GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY =
	 * "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,oum_user_id, "
	 * + " OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME , " +
	 * "trunc(ocd.ocd_date_of_birth) date_of_birth, ocd_comm_city, "+
	 * "osm_state_name, oum_email_id, oum_mobile_no, otm_test_name, octm_category_code, trunc(ocd.ocd_created_date) created_date FROM oes_user_master oum,  "
	 * +
	 * "oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, oes_status_master osm, oes_state_master ostm WHERE oum.oum_user_pk = ocd.ocd_user_fk  "
	 * +
	 * "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk "
	 * +
	 * "AND nvl(ocd.ocd_comm_state_fk, ocd.OCD_COMM_UNION_TERR_FK) = ostm.osm_state_pk  "
	 * + "AND ocd.ocd_status_id_fk = osm.osm_status_pk  "+
	 * "AND otm.otm_test_pk = DECODE (?, 'All', otm.otm_test_pk, ?) "+ //
	 * $P{PROGRAM_NAME} = 2
	 * "AND ocm.octm_category_pk = DECODE (?, 'All', ocm.octm_category_pk, ?) ";
	 * // $P{CATEGORY} = 2
	 */
	/*
	 * String GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY =
	 * "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,oum_user_id, "
	 * + " OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME , " +
	 * "to_char(ocd.ocd_date_of_birth,'dd-MON-yyyy') date_of_birth, ocd_comm_city, "
	 * +
	 * "osm_state_name, oum_email_id, oum_mobile_no, otm_test_name, octm_category_code, to_char(ocd.ocd_created_date,'dd-MON-yyyy') created_date ,OCPD_PREF_TEST_CENTRE_1_FK as centre1 , OCPD_PREF_TEST_CENTRE_2_FK as centre2 FROM oes_user_master oum,  "
	 * +
	 * "oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, oes_status_master osm, oes_state_master ostm ,OES_CAND_PREF_DETAILS ocpd  "
	 * +
	 * " WHERE oum.oum_user_pk = ocd.ocd_user_fk and ocd.ocd_user_fk =+ ocpd.OCPD_USER_FK  "
	 * +
	 * "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk "
	 * +
	 * "AND coalesce(ocd.ocd_comm_state_fk, ocd.OCD_COMM_UNION_TERR_FK) = ostm.osm_state_pk  "
	 * + "AND ocd.ocd_status_id_fk = osm.osm_status_pk  "+
	 * "AND otm.otm_test_pk = CASE WHEN ? = 0 THEN otm.otm_test_pk ELSE ? END "+
	 * // $P{PROGRAM_NAME} = 2
	 * "AND ocm.octm_category_pk = CASE WHEN ? = 0 THEN ocm.octm_category_pk ELSE ? END "
	 * ;
	 */
	// " AND OILM_INT_LOC_PK = DECODE (?,'All', OILM_INT_LOC_PK,?)"; //
	// $P{CATEGORY} = 2

	/*
	 * String GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY =
	 * "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,oum_user_id, "
	 * + " OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME , " +
	 * "to_char(ocd.ocd_date_of_birth,'dd-MON-yyyy') date_of_birth, ocd_comm_city, "
	 * +
	 * "osm_state_name, oum_email_id, oum_mobile_no, otm_test_name, octm_category_code, to_char(ocd.ocd_created_date,'dd-MON-yyyy') created_date ,OCPD_PREF_TEST_CENTRE_1_FK as centre1 , OCPD_PREF_TEST_CENTRE_2_FK as centre2 "
	 * +
	 * " FROM oes_user_master oum inner join oes_candidate_details ocd  on oum.oum_user_pk = ocd.ocd_user_fk "
	 * +
	 * " inner join oes_category_master ocm on ocd.ocd_category_fk = ocm.octm_category_pk  "
	 * +
	 * " inner join oes_status_master osm on ocd.ocd_status_id_fk = osm.osm_status_pk    "
	 * +
	 * " inner join oes_state_master ostm on coalesce(ocd.ocd_perm_state_fk, ocd.ocd_perm_union_terr_fk) = ostm.osm_state_pk  "
	 * +
	 * " left outer join oes_test_master otm on ocd.ocd_test_fk = otm.otm_test_pk and otm.otm_test_pk = CASE WHEN ? = 0 THEN otm.otm_test_pk ELSE ? END  "
	 * +
	 * " left outer join OES_CAND_PREF_DETAILS ocpd  on ocd.ocd_user_fk = ocpd.OCPD_USER_FK"
	 * +
	 * " WHERE ocm.octm_category_pk = CASE WHEN ? = 0 THEN ocm.octm_category_pk ELSE ? END"
	 * ;
	 */

	String GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY = "SELECT * FROM (SELECT  ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,\r\n" + "	otm_test_name as \"Post Applied for\",\r\n"
			+ "	oum_user_id,\r\n" + "	oum_initial, \r\n" + "	oum_candidate_name as name, \r\n"
			+ "	(SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk=ocd_gender_fk) as ocd_gender_fk,\r\n"
			+ "	to_char(oum_date_of_birth,'dd-MON-yyyy') date_of_birth,\r\n"
			+ "	(SELECT octm_category_desc FROM oes_category_master WHERE OCTM_CATEGORY_PK ::varchar= ocd_community) as ocd_community,\r\n"
			+ "	(SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar=ocd_community_cert) as ocd_community_cert,\r\n"
			+ "	(SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar =OUM_DISABILITY) as OUM_DISABILITY,\r\n"
			+ "	oum_scribe_requied as Scribe,\r\n"
			+ "	(SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar=ocd_nativity) as ocd_nativity,\r\n"
			+ "	 (SELECT osm_state_name FROM oes_state_master WHERE osm_state_pk::varchar =ocd_nativity_other) as ocd_nativity_other,\r\n" + "	 ocd_perm_city, \r\n"
			+ "       (SELECT osm_state_name FROM oes_state_master WHERE osm_state_pk = ocd_perm_state_fk) as ocd_perm_state_fk, \r\n" + "	oum_email_id, \r\n"
			+ "	oum_mobile_no,\r\n"
			+ "	(SELECT OTCM_TEST_CENTRE_NAME FROM OES_TEST_CENTER_MASTER WHERE OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_1_FK) AS OCPD_PREF_TEST_CENTRE_1_FK,\r\n"
			+ "       (SELECT OTCM_TEST_CENTRE_NAME FROM OES_TEST_CENTER_MASTER WHERE OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_2_FK) AS OCPD_PREF_TEST_CENTRE_2_FK,\r\n"
			+ "       (SELECT OTCM_TEST_CENTRE_NAME FROM OES_TEST_CENTER_MASTER WHERE OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_3_FK) AS OCPD_PREF_TEST_CENTRE_3_FK,\r\n"
			+ "       (case when ACDH_USER_ID =oum_user_id then 'YES' else 'NO' end ) as \"Admit Card Download\", \r\n"
			+ "	(select max(to_char(ACDH_DOWNLOAD_TIME, 'dd-MON-yyyy')) from ADMIT_CARD_DOWNLOAD_HISTORY where ACDH_USER_ID=oum_user_id )as \"LatestAdmitCard DownloadDate\",  \r\n"
			+ "	(case when SCDH_USER_ID=oum_user_id then 'YES' else 'NO' end ) as \"Score Card Downloaded\",\r\n"
			+ "	(select max(to_char(SCDH_DOWNLOAD_TIME, 'dd-MON-yyyy')) from SCORE_CARD_DOWNLOAD_HISTORY where SCDH_USER_ID=oum_user_id )as \"LatestscoreCard DownloadDate\",\r\n"
			+ "	(case when cldh_user_id =oum_user_id then 'YES' else 'NO' end ) as \"Call Letter Downloaded\",\r\n"
			+ "	(select max(to_char(cldh_download_time, 'dd-MON-yyyy')) from callletter_download_history where cldh_user_id=oum_user_id )as \"LatestCall Letter DownloadDate\",\r\n"
			+ "        ocd_form_submited_date,to_char(oum_created_date,'dd-MON-yyyy') as basic_register_date,\r\n" + "        opd.opd_transaction_date,opd.opd_amount,\r\n"
			+ "	case when opd.opd_validated_status='A' then 'Approved'  when opd.opd_validated_status='R' then 'Rejected' end as Payment_status,\r\n" + "	optm_payment_desc,\r\n"
			+ "   (select osdm_sub_degree_desc from oes_sub_degree_master where osdm_sub_degree_pk=oacd3.oacd_degree_subject_fk) as ug_degree_name,\r\n"
			+ "	(SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar=oacd3.oacd_major_subject_fk) as ug_subject_name,\r\n"
			+ "	(select osdm_sub_degree_desc from oes_sub_degree_master where osdm_sub_degree_pk=oacd4.oacd_degree_subject_fk) as pg_degree_name,\r\n"
			+ "	(SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar=oacd4.oacd_major_subject_fk) as pg_subject_name,\r\n"
			+ "	(select osdm_sub_degree_desc from oes_sub_degree_master where osdm_sub_degree_pk=oacd5.oacd_degree_subject_fk) as edu_degree_name,\r\n"
			+ "	(SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar=oacd5.oacd_major_subject_fk) as edu_subject_name \r\n"
			+ "    FROM oes_user_master oum  \r\n" + "    inner join oes_candidate_details ocd  on oum.oum_user_pk = ocd.ocd_user_fk \r\n"
			+ "    left join oes_state_master ostm on coalesce(ocd.ocd_perm_state_fk, ocd.ocd_perm_union_terr_fk) = ostm.osm_state_pk \r\n"
			+ "    left join oes_category_master octm on octm.OCTM_CATEGORY_PK ::varchar= ocd.ocd_community\r\n"
			+ "    left join oes_test_master otm on oum.oum_test_fk=otm.otm_test_pk and otm_status='A'\r\n"
			+ "    left join oes_acdm_cand_details oacd5 on ocd_user_fk = oacd5.oacd_user_fk and oacd5.oacd_acdm_fk=5\r\n"
			+ "    left join oes_acdm_cand_details oacd4 on ocd_user_fk = oacd4.oacd_user_fk and oacd4.oacd_acdm_fk=4\r\n"
			+ "    left join oes_acdm_cand_details oacd3 on ocd_user_fk = oacd3.oacd_user_fk and oacd3.oacd_acdm_fk=3 \r\n"
			+ "    left outer join oes_payment_details  opd on opd.opd_user_fk=ocd.ocd_user_fk  \r\n"
			+ "    left outer join oes_payment_master on optm_payment_pk=opd_payment_fk \r\n" + "    LEFT OUTER JOIN OES_CAND_PREF_DETAILS ON (OCPD_USER_FK=ocd.ocd_user_fk)\r\n"
			+ "    left outer join (select distinct ACDH_USER_ID  from ADMIT_CARD_DOWNLOAD_HISTORY) acdh on (acdh.ACDH_USER_ID=oum.oum_user_id) \r\n"
			+ "    left outer join (select distinct SCDH_USER_ID from SCORE_CARD_DOWNLOAD_HISTORY) scdh on (scdh.SCDH_USER_ID=oum.oum_user_id) \r\n"
			+ "    left outer join (select distinct cldh_user_id from callletter_download_history) cldh on (cldh.cldh_user_id=oum.oum_user_id)\r\n"
			+ "	WHERE (otm_test_pk in(?)  or  -1 in  (?) )  and (octm_category_pk in(?)  or  -1 in  (?)) ";

	/*
	 * String GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY =
	 * "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num,oum_user_id, "
	 * + " OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME , " +
	 * "to_char(ocd.ocd_date_of_birth,'dd-MON-yyyy') date_of_birth, " +
	 * " case when length(trim(ocd_perm_city)) > 0 AND ocd_perm_city is not null then (select odm_district_name from oes_district_master where odm_district_pk::varchar= ocd_perm_city) "
	 * +
	 * " when length(trim(ocd_perm_city)) = 0 then ocd_perm_district_other else ocd_comm_city end as ocd_comm_city, "
	 * +
	 * "osm_state_name, oum_email_id, oum_mobile_no, otm_test_name, octm_category_code, to_char(ocd.ocd_created_date,'dd-MON-yyyy') created_date "
	 * +
	 * " FROM oes_user_master oum inner join oes_candidate_details ocd  on oum.oum_user_pk = ocd.ocd_user_fk "
	 * +
	 * " inner join oes_category_master ocm on ocd.ocd_category_fk = ocm.octm_category_pk  "
	 * +
	 * " inner join oes_status_master osm on ocd.ocd_status_id_fk = osm.osm_status_pk    "
	 * +
	 * " inner join oes_state_master ostm on coalesce(ocd.ocd_perm_state_fk, ocd.ocd_perm_union_terr_fk) = ostm.osm_state_pk  "
	 * +
	 * " left join oes_candidate_job_mapping ocjm  on ocjm.ocjm_user_fk=ocd.ocd_user_fk "
	 * +
	 * " left join oes_test_master otm on ocjm.ocjm_test_fk=otm.otm_test_pk   "+
	 * " WHERE (otm_test_pk in(?)  or  -1 in  (?) ) "+
	 * " and (octm_category_pk in(?)  or  -1 in  (?)) ";
	 */
	/*
	 * String GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY_EXCEL =
	 * "SELECT oum_user_id, " +
	 * " OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME , " +
	 * "to_char(ocd.ocd_date_of_birth,'HH12:MI:SS') date_of_birth, ocd_comm_city, "
	 * +
	 * "osm_state_name, oum_email_id, oum_mobile_no, otm_test_name, octm_category_code, to_char(ocd.ocd_created_date,'HH12:MI:SS') created_date , OCPD_PREF_TEST_CENTRE_1_FK as centre1 , OCPD_PREF_TEST_CENTRE_2_FK as centre2 FROM oes_user_master oum,  "
	 * +
	 * "oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, oes_status_master osm, oes_state_master ostm ,OES_CAND_PREF_DETAILS ocpd  WHERE oum.oum_user_pk = ocd.ocd_user_fk and ocd.ocd_user_fk =+ ocpd.OCPD_USER_FK "
	 * +
	 * "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk "
	 * +
	 * "AND coalesce(ocd.ocd_comm_state_fk, ocd.OCD_COMM_UNION_TERR_FK) = ostm.osm_state_pk  "
	 * + "AND ocd.ocd_status_id_fk = osm.osm_status_pk  "+
	 * "AND otm.otm_test_pk = CASE WHEN ? = 0 THEN otm.otm_test_pk ELSE ? END "+
	 * // $P{PROGRAM_NAME} = 2
	 * "AND ocm.octm_category_pk = CASE WHEN ? = 0 THEN ocm.octm_category_pk ELSE ? END"
	 * ;
	 */

	/*
	 * String GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY_EXCEL =
	 * "SELECT oum_user_id, " +
	 * " OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME , " +
	 * "to_char(ocd.ocd_date_of_birth,'dd-MON-yyyy') date_of_birth, ocd_comm_city, "
	 * +
	 * "osm_state_name, oum_email_id, oum_mobile_no, otm_test_name, octm_category_code, to_char(ocd.ocd_created_date,'dd-MON-yyyy') created_date , OCPD_PREF_TEST_CENTRE_1_FK as centre1 , OCPD_PREF_TEST_CENTRE_2_FK as centre2 "
	 * +
	 * " FROM oes_user_master oum inner join oes_candidate_details ocd  on oum.oum_user_pk = ocd.ocd_user_fk "
	 * +
	 * " inner join oes_category_master ocm on ocd.ocd_category_fk = ocm.octm_category_pk  "
	 * +
	 * " inner join oes_status_master osm on ocd.ocd_status_id_fk = osm.osm_status_pk    "
	 * +
	 * " inner join oes_state_master ostm on coalesce(ocd.ocd_perm_state_fk, ocd.ocd_perm_union_terr_fk) = ostm.osm_state_pk  "
	 * +
	 * " left outer join oes_test_master otm on ocd.ocd_test_fk = otm.otm_test_pk and otm.otm_test_pk = CASE WHEN ? = 0 THEN otm.otm_test_pk ELSE ? END  "
	 * +
	 * " left outer join OES_CAND_PREF_DETAILS ocpd  on ocd.ocd_user_fk = ocpd.OCPD_USER_FK"
	 * +
	 * " WHERE ocm.octm_category_pk = CASE WHEN ? = 0 THEN ocm.octm_category_pk ELSE ? END"
	 * ;
	 */// commented on 31-07-2017
	String GET_CANDIDATE_DETAILS_FOR_REPORT_DISPLAY_EXCEL = "SELECT oum_user_id, " + " OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME , "
			+ "to_char(ocd.ocd_date_of_birth,'dd-MON-yyyy') date_of_birth, "
			+ " case when length(trim(ocd_perm_city)) > 0 AND ocd_perm_city is not null then (select odm_district_name from oes_district_master where odm_district_pk::varchar= ocd_perm_city) "
			+ " when length(trim(ocd_perm_city)) = 0 then ocd_perm_district_other else ocd_comm_city end as ocd_comm_city, "
			+ "osm_state_name, oum_email_id, oum_mobile_no, otm_test_name, octm_category_code, to_char(ocd.ocd_created_date,'dd-MON-yyyy') created_date "
			+ " FROM oes_user_master oum inner join oes_candidate_details ocd  on oum.oum_user_pk = ocd.ocd_user_fk "
			+ " inner join oes_category_master ocm on ocd.ocd_category_fk = ocm.octm_category_pk  "
			+ " inner join oes_status_master osm on ocd.ocd_status_id_fk = osm.osm_status_pk    "
			+ " inner join oes_state_master ostm on coalesce(ocd.ocd_perm_state_fk, ocd.ocd_perm_union_terr_fk) = ostm.osm_state_pk  "
			+ " left join oes_candidate_job_mapping ocjm  on ocjm.ocjm_user_fk=ocd.ocd_user_fk " + " left join oes_test_master otm on ocjm.ocjm_test_fk=otm.otm_test_pk   "
			+ " WHERE (otm_test_pk in(?)  or  -1 in  (?) ) " + " and (octm_category_pk in(?)  or  -1 in  (?)) ";

	String GET_DETAILS_OF_CANDIDATE_APPROVED_COUNT = "SELECT COUNT(1) FROM oes_user_master oum, "
			+ "oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, oes_status_master osm, oes_state_master ostm ,OES_CAND_PREF_DETAILS ocpd , OES_INTERVIEW_LOCATION_MASTER oilm "
			+ "WHERE oum.oum_user_pk = ocd.ocd_user_fk  AND ocd.ocd_user_fk =+ ocpd.OCPD_USER_FK "
			+ "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk AND ocd.OCD_INT_LOC_FK = oilm.OILM_INT_LOC_PK "
			+ "AND coalesce(ocd.ocd_perm_state_fk, ocd.ocd_perm_union_terr_fk) = ostm.osm_state_pk  "
			+ "AND ocd.OCD_VALIDATED_STATUS = ? AND ocd.ocd_form_submited_date IS NOT NULL " + "AND ocd.ocd_status_id_fk = osm.osm_status_pk  "
			+ "AND otm.otm_test_pk = CASE WHEN ? = 0 THEN otm.otm_test_pk ELSE ? END " + // $P{PROGRAM_NAME}
																							// =
																							// 2
			"AND ocm.octm_category_pk = CASE WHEN ? = 0 THEN ocm.octm_category_pk ELSE ? END " + "  AND OILM_INT_LOC_PK =  CASE WHEN ? = 0 THEN OILM_INT_LOC_PK ELSE ? END"; // $P{CATEGORY}
																																												// =
																																												// 2

	/*
	 * String GET_DETAILS_OF_CANDIDATE_APPROVED =
	 * "SELECT * FROM (SELECT distinct ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num, oum_user_id, "
	 * + " OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME , " +
	 * "trunc(ocd.ocd_date_of_birth) date_of_birth, ocd_comm_city, "+
	 * "osm_state_name, oum_email_id, oum_mobile_no, otm_test_name, octm_category_code, trunc(ocd.ocd_created_date) created_date FROM oes_user_master oum, "
	 * +
	 * "oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, oes_status_master osm, oes_state_master ostm  "
	 * + "WHERE oum.oum_user_pk = ocd.ocd_user_fk  "+
	 * "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk "
	 * +
	 * "AND nvl(ocd.ocd_comm_state_fk, ocd.OCD_COMM_UNION_TERR_FK) = ostm.osm_state_pk  "
	 * +
	 * "AND ocd.OCD_VALIDATED_STATUS = ? AND ocd.ocd_form_submited_date IS NOT NULL "
	 * + "AND ocd.ocd_status_id_fk = osm.osm_status_pk  "+
	 * "AND otm.otm_test_pk = DECODE (?, 'All', otm.otm_test_pk, ?) "+ //
	 * $P{PROGRAM_NAME} = 2
	 * "AND ocm.octm_category_pk = DECODE (?, 'All', ocm.octm_category_pk, ?) ";
	 * // $P{CATEGORY} = 2
	 */

	String GET_DETAILS_OF_CANDIDATE_APPROVED = "SELECT * FROM (SELECT distinct ROW_NUMBER() OVER (ORDER BY oum_user_id) AS num, oum_user_id, "
			+ " OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME , " + "to_char(ocd.ocd_date_of_birth,'HH12:MI:SS') date_of_birth, ocd_comm_city, "
			+ "osm_state_name, oum_email_id, oum_mobile_no, otm_test_name, octm_category_code, to_char(ocd.ocd_created_date,'HH12:MI:SS') created_date , oilm.OILM_INT_LOC_DESC , OCPD_PREF_TEST_CENTRE_1_FK as centre1 , OCPD_PREF_TEST_CENTRE_2_FK as centre2 FROM oes_user_master oum, "
			+ "oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, oes_status_master osm, oes_state_master ostm ,OES_CAND_PREF_DETAILS ocpd , OES_INTERVIEW_LOCATION_MASTER oilm   "
			+ "WHERE oum.oum_user_pk = ocd.ocd_user_fk  and ocd.ocd_user_fk =+ ocpd.OCPD_USER_FK "
			+ "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk  AND ocd.OCD_INT_LOC_FK = oilm.OILM_INT_LOC_PK "
			+ "AND coalesce(ocd.ocd_comm_state_fk, ocd.OCD_COMM_UNION_TERR_FK) = ostm.osm_state_pk  "
			+ "AND ocd.OCD_VALIDATED_STATUS = ? AND ocd.ocd_form_submited_date IS NOT NULL " + "AND ocd.ocd_status_id_fk = osm.osm_status_pk  "
			+ "AND otm.otm_test_pk = CASE WHEN ? = 0 THEN otm.otm_test_pk ELSE ? END " + // $P{PROGRAM_NAME}
																							// =
																							// 2
			"AND ocm.octm_category_pk = CASE WHEN ? = 0 THEN ocm.octm_category_pk ELSE ? END " + "   AND OILM_INT_LOC_PK =  CASE WHEN ? = 0 THEN OILM_INT_LOC_PK ELSE ? END"; // $P{CATEGORY}
																																												// =
																																												// 2

	String GET_DETAILS_OF_CANDIDATE_APPROVED_EXCEL = "SELECT distinct oum_user_id, " + " OCD_CAND_TITLE,OCD_FIRST_NAME,OCD_MIDDLE_NAME,OCD_LAST_NAME , "
			+ "to_char(ocd.ocd_date_of_birth,'HH12:MI:SS') date_of_birth, ocd_comm_city, "
			+ "osm_state_name, oum_email_id, oum_mobile_no, otm_test_name, octm_category_code, to_char(ocd.ocd_created_date,'HH12:MI:SS') created_date , oilm.OILM_INT_LOC_DESC , OCPD_PREF_TEST_CENTRE_1_FK as centre1 , OCPD_PREF_TEST_CENTRE_2_FK as centre2 FROM oes_user_master oum, "
			+ "oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, oes_status_master osm, oes_state_master ostm ,OES_CAND_PREF_DETAILS ocpd , OES_INTERVIEW_LOCATION_MASTER oilm "
			+ "WHERE oum.oum_user_pk = ocd.ocd_user_fk and ocd.ocd_user_fk =+ ocpd.OCPD_USER_FK "
			+ "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk AND ocd.OCD_INT_LOC_FK = oilm.OILM_INT_LOC_PK "
			+ "AND coalesce(ocd.ocd_comm_state_fk, ocd.OCD_COMM_UNION_TERR_FK) = ostm.osm_state_pk  "
			+ "AND ocd.OCD_VALIDATED_STATUS = ? AND ocd.ocd_form_submited_date IS NOT NULL " + "AND ocd.ocd_status_id_fk = osm.osm_status_pk  "
			+ "AND otm.otm_test_pk = CASE WHEN ? = 0 THEN otm.otm_test_pk ELSE ? END " + // $P{PROGRAM_NAME}
																							// =
																							// 2
			"AND ocm.octm_category_pk = CASE WHEN ? = 0 THEN ocm.octm_category_pk ELSE ?) " + // $P{CATEGORY}
																								// =
																								// 2
			"   AND OILM_INT_LOC_PK =  CASE WHEN ? = 0 THEN OILM_INT_LOC_PK ELSE ? END";

	// String GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED = "select count(*) from
	// oes_payment_details opd where opd.OPD_PAYMENT_FK = ? and
	// opd.OPD_VALIDATED_STATUS = ?";

	/*
	 * String GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED =
	 * "   select sum(cnt) from      \r\n" + "			             (\r\n" +
	 * "			             select  opd_validated_status,count(distinct opd_user_fk) cnt from  OES_PAYMENT_DETAILS opd  \r\n"
	 * +
	 * "						INNER JOIN oes_candidate_details ocd  ON ocd.ocd_user_fk = opd.opd_user_fk  \r\n"
	 * +
	 * "						INNER JOIN oes_user_master oum  ON ocd.ocd_user_fk = oum.oum_user_pk AND oum.oum_user_pk = opd.opd_user_fk   \r\n"
	 * +
	 * "						INNER JOIN oes_test_master otm  ON otm.otm_test_pk = oum.oum_test_fk \r\n"
	 * + "			      group by opd_validated_status\r\n" +
	 * "						union all  select 'A',0 \r\n" +
	 * "			      union all  select 'R',0 \r\n" +
	 * "			            ) v \r\n" +
	 * "			      where opd_validated_status = 'R'\r\n" +
	 * "			      group by opd_validated_status ";
	 */

	String GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED = "SELECT COUNT(1) FROM OES_PAYMENT_DETAILS where opd_payment_fk=1 and opd_validated_status='R' ";

//	String GET_RANDOM_NUM = "select lpad(num::varchar,5,'0') from RANDOM_NUM_TBL where sr_no =?";

	String GET_TOTAL_NO_OF_CANDIDATES_ACC = "SELECT COUNT(1) FROM OES_USER_MASTER GUM WHERE GUM.OUM_USER_TYPE='C' ";

	String GET_NO_OF_INITIATED_CANDIDATE_ACC = "SELECT COUNT(1) FROM oes_user_master oum, oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, "
			+ "oes_status_master osm WHERE oum.oum_user_pk = ocd.ocd_user_fk " + "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk "
			+ "AND ocd.ocd_status_id_fk = osm.osm_status_pk ";

	String GET_NO_OF_CANDIDATE_APPLICATION_SUBMITTED_ACC = "SELECT COUNT(1) FROM oes_user_master oum, oes_candidate_details ocd, oes_test_master otm, oes_category_master ocm, "
			+ "oes_status_master osm WHERE oum.oum_user_pk = ocd.ocd_user_fk " + "AND ocd.ocd_test_fk = otm.otm_test_pk AND ocd.ocd_category_fk = ocm.octm_category_pk "
			+ "AND ocd.ocd_status_id_fk = osm.osm_status_pk " + "AND osm.osm_status_pk >= ? AND ocd.OCD_TRAINING_CENTRE_FK = ?";

	String GET_NO_OF_CANDIDATE_APPROVED = "SELECT COUNT(1) FROM OES_CANDIDATE_DETAILS GUM WHERE GUM.OCD_VALIDATED_STATUS = ? ";

	String GET_PAYMENT_SUBMITTED_COUNT_ACC = "SELECT sum (count) from (select count(*) as count from OES_PAYMENT_DETAILS where OPD_TRAINING_CENTRE_FK = ? union all select count(*) as count from oes_payment_details_history where OPD_TRAINING_CENTRE_FK = ?) ";

	String GET_NO_OF_CANDIDATE_PAYMENT_ACC = "SELECT sum(count) FROM (select count(1) as count from OES_PAYMENT_DETAILS WHERE OPD_VALIDATED_STATUS = ? AND OPD_PAYMENT_FK = ? AND OPD_TRAINING_CENTRE_FK = ? union all select count(1) as count from OES_PAYMENT_DETAILS_HISTORY WHERE OPD_VALIDATED_STATUS = ? AND OPD_PAYMENT_FK = ? AND OPD_TRAINING_CENTRE_FK = ?)";

	String GET_CANDIDATE_COUNT_FOR_PAYMENT_FAILED_ACC = "select count(opd_user_fk) from ( "
			+ "(select opd_user_fk from oes_payment_details opd where opd.OPD_PAYMENT_FK = ? and opd.OPD_VALIDATED_STATUS = ? and OPD_TRAINING_CENTRE_FK = ?) " + "union all "
			+ "(select opd_user_fk from oes_payment_details_history opd where opd.OPD_PAYMENT_FK = ? and opd.OPD_VALIDATED_STATUS = ? and OPD_TRAINING_CENTRE_FK = ?) " + " )";

	String GET_PAYMENT_APPROVAL_PENDING_COUNT_ACC = "SELECT count(*) from OES_PAYMENT_DETAILS WHERE OPD_VALIDATED_STATUS IS NULL AND OPD_PAYMENT_FK = ? AND OPD_TRAINING_CENTRE_FK = ?";

	String GET_NO_OF_NOT_INITIATED_CANDIDATE = "select count(*) from oes_user_master where oum_user_pk not in (select ocd_user_fk from oes_candidate_details) and OUM_USER_TYPE = 'C'";

	String ADMIT_CARD_DOWNLOAD_COUNT = "Select count(1) from (\r\n" + "			select distinct(ACDH_USER_ID) as COUNT\r\n" + "			from  ADMIT_CARD_DOWNLOAD_HISTORY ACDH\r\n"
			+ "            INNER JOIN oes_user_master oum  ON ACDH.ACDH_USER_ID = oum.OUM_USER_ID \r\n"
			+ "			INNER JOIN oes_candidate_details ocd  ON ocd.ocd_user_fk = oum.OUM_USER_PK\r\n"
			+ "            INNER JOIN OES_PAYMENT_DETAILS OPD ON ocd.ocd_user_fk= opd.opd_user_fk\r\n" + "			) ";

	String PRAC_TEST_DOWNLOAD_COUNT = "Select count(1) from (\r\n" + "			select distinct(omd_user_id) as COUNT\r\n" + "			from  OES_MOCKTEST_DETAILS OMD\r\n"
			+ "            INNER JOIN oes_user_master oum  ON OMD.OMD_USER_ID = oum.OUM_USER_ID \r\n"
			+ "			INNER JOIN oes_candidate_details ocd  ON ocd.ocd_user_fk = oum.OUM_USER_PK\r\n"
			+ "            INNER JOIN OES_PAYMENT_DETAILS OPD ON ocd.ocd_user_fk= opd.opd_user_fk\r\n" + "			)";

	String GET_NO_OF_APPLICATION_SUBMITTED = " Select count(1) from (SELECT distinct(OPD_USER_FK) as COUNT FROM OES_PAYMENT_DETAILS WHERE OPD_VALIDATED_STATUS ='A') v";

	String GET_CATEGORY_WISE_REPORT_FOR_PU = "select otm_test_name,oc,sc,st,mbc,bc,bcm, Total     \r\n" + "						from (      \r\n"
			+ "							select '1' as rank , OTM_TEST_NAME ,      \r\n"
			+ "							   sum(case when octm_category_code='OC' and  OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) OC,   \r\n"
			+ "							   sum(case when octm_category_code='SC' and  OPD_VALIDATED_STATUS = 'A' then 1 else 0 end ) SC,     \r\n"
			+ "							   sum(case when octm_category_code='ST' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) ST,    \r\n"
			+ "		                                           sum(case when octm_category_code='MBC / DNC' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) MBC,    \r\n"
			+ "		                                           sum(case when octm_category_code='BC' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) BC,  \r\n"
			+ "		                                           sum(case when octm_category_code='BCM' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) BCM, \r\n"
			+ "		                                           sum(case when octm_category_code='OC' and  OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end )  +   \r\n"
			+ "							   sum(case when octm_category_code='SC' and  OPD_VALIDATED_STATUS = 'A' then 1 else 0 end )  +  \r\n"
			+ "		                                           sum(case when octm_category_code='ST' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) +    \r\n"
			+ "		                                           sum(case when octm_category_code='MBC / DNC' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) +    \r\n"
			+ "		                                           sum(case when octm_category_code='BC' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) + \r\n"
			+ "							   sum(case when octm_category_code='BCM' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) as TOTAL     \r\n"
			+ "								from oes_test_master      \r\n"
			+ "							   left join oes_user_master oum ON (oum.OUM_TEST_FK = oes_test_master.OTM_TEST_PK and OUM_USER_TYPE = 'C') \r\n"
			+ "							   left join oes_candidate_details ocd on (ocd.ocd_user_fk= oum.oum_user_pk)     \r\n"
			+ "							   left join oes_payment_details on (opd_user_fk = oum.oum_user_pk  and OPD_VALIDATED_STATUS = 'A')     \r\n"
			+ "							   left join oes_category_master on  (ocd.ocd_community=octm_category_pk::varchar)     \r\n"
			+ "								where otm_status='A' group by otm_test_name    \r\n" + "								union       \r\n"
			+ "								select '2' as rank, 'Grand Total 'as test_name ,      \r\n"
			+ "							  sum(case when octm_category_code='OC' and  OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) OC,   \r\n"
			+ "							  sum(case when octm_category_code='SC' and  OPD_VALIDATED_STATUS = 'A' then 1 else 0 end ) SC,     \r\n"
			+ "							  sum(case when octm_category_code='ST' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) ST,    \r\n"
			+ "		                                          sum(case when octm_category_code='MBC / DNC' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) MBC,    \r\n"
			+ "		                                          sum(case when octm_category_code='BC' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) BC,  \r\n"
			+ "		                                          sum(case when octm_category_code='BCM' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) BCM, \r\n"
			+ "		                                          sum(case when octm_category_code='OC' and  OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end )  +   \r\n"
			+ "							  sum(case when octm_category_code='SC' and  OPD_VALIDATED_STATUS = 'A' then 1 else 0 end )  +  \r\n"
			+ "		                                          sum(case when octm_category_code='ST' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) +    \r\n"
			+ "		                                          sum(case when octm_category_code='MBC / DNC' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) +    \r\n"
			+ "		                                          sum(case when octm_category_code='BC' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) + \r\n"
			+ "							  sum(case when octm_category_code='BCM' and OPD_VALIDATED_STATUS = 'A'  then 1 else 0 end ) as TOTAL  \r\n"
			+ "							     from oes_test_master     \r\n"
			+ "								left join oes_user_master oum ON (oum.OUM_TEST_FK = oes_test_master.OTM_TEST_PK  and OUM_USER_TYPE = 'C' ) \r\n"
			+ "								left join oes_candidate_details ocd on (ocd.ocd_user_fk= oum.oum_user_pk)     \r\n"
			+ "								left join oes_payment_details on (opd_user_fk = oum.oum_user_pk  and OPD_VALIDATED_STATUS = 'A')     \r\n"
			+ "								left join oes_category_master on  (ocd.ocd_community=octm_category_pk::varchar) where otm_status='A' ) a     \r\n"
			+ "							 order by rank,otm_test_name";

	/*
	 * String
	 * GET_TEST_CENTER_WISE_REPORT_FOR_PU="SELECT '1' AS row_count, OTCM_TEST_CENTRE_NAME AS \"Name of City\",\r\n"
	 * +
	 * "(CASE WHEN oum_test_fk=1 THEN   count(oum_user_id) ELSE 0 END) AS Candidate_count, \r\n"
	 * + "COUNT( DISTINCT OMD_USER_ID) AS PRACTICE_TEST,\r\n" +
	 * "COUNT (DISTINCT ACDH_USER_ID) AS DOWNLOAD\r\n" +
	 * " FROM oes_cand_pref_details \r\n" +
	 * "JOIN oes_test_center_master ON (OTCM_TEST_CENTRE_PK = OCPD_PREF_TEST_CENTRE_1_FK AND OTCM_STATUS='A')\r\n"
	 * + "JOIN  oes_user_master ON (OUM_USER_PK = OCPD_USER_FK)\r\n" +
	 * "inner JOIN oes_test_master ON (OTM_TEST_PK = OUM_TEST_FK)\r\n" +
	 * "JOIN oes_payment_details ON (oum_user_pk = opd_user_fk and opd_validated_status = 'A')\r\n"
	 * +
	 * "left outer join ADMIT_CARD_DOWNLOAD_HISTORY on (ACDH_USER_ID=OUM_USER_ID)\r\n"
	 * +
	 * "left outer join (SELECT DISTINCT omd_user_id FROM oes_mocktest_details) ON ( OMD_USER_ID=OUM_USER_ID)\r\n"
	 * + "WHERE opd_validated_status = 'A' \r\n" +
	 * "GROUP BY oum_test_fk,OTCM_TEST_CENTRE_NAME\r\n" + "UNION\r\n" +
	 * "SELECT row_count, 'Total', SUM(Total),SUM(PRACTICE_TEST),SUM(TOTALS)\r\n"
	 * + "FROM \r\n" + "(\r\n" + "SELECT '2' AS row_count, 'Total',\r\n" +
	 * "SUM(CASE WHEN OTM_TEST_NAME = 'Group C to JTO (T)' THEN 1 ELSE 0 END) AS Total,\r\n"
	 * + "COUNT( DISTINCT OMD_USER_ID) AS PRACTICE_TEST\r\n" +
	 * ", COUNT(DISTINCT ACDH_USER_ID) AS TOTALS\r\n" +
	 * "FROM oes_cand_pref_details \r\n" +
	 * "JOIN oes_test_center_master ON (OTCM_TEST_CENTRE_PK = OCPD_PREF_TEST_CENTRE_1_FK AND OTCM_STATUS='A')\r\n"
	 * + "JOIN oes_user_master ON (OUM_USER_PK = OCPD_USER_FK)\r\n" +
	 * "JOIN oes_test_master ON (OTM_TEST_PK = OUM_TEST_FK)\r\n" +
	 * "JOIN oes_payment_details ON (oum_user_pk = opd_user_fk and opd_validated_status = 'A')\r\n"
	 * +
	 * "left outer join ADMIT_CARD_DOWNLOAD_HISTORY on (ACDH_USER_ID=OUM_USER_ID)\r\n"
	 * +
	 * "left outer join (SELECT DISTINCT omd_user_id FROM oes_mocktest_details) ON ( OMD_USER_ID=OUM_USER_ID)\r\n"
	 * + "--WHERE opd_validated_status = 'A'\r\n" +
	 * "GROUP BY otcm_test_centre_name\r\n" + ") d\r\n" +
	 * "GROUP BY row_count\r\n" + "ORDER BY row_count, 2";
	 */

	String GET_TEST_CENTER_WISE_REPORT_FOR_PU = "SELECT   1 row_count,      \r\n" + "				                  OTCM_TEST_CENTRE_NAME,     \r\n"
			+ "				                  SUM(CASE WHEN OTM_TEST_NAME = 'Post Graduate Assistants' THEN 1   ELSE 0 END) as \"TETI\"  ,     \r\n"
			+ "				                  SUM(CASE WHEN OTM_TEST_NAME = 'Physical Education Director Grade - 1' THEN 1   ELSE 0 END) as \"TETII\"  ,     \r\n"
			+ "				                   \r\n" + "				                  SUM(CASE WHEN OTM_TEST_NAME = 'Post Graduate Assistants' THEN 1   ELSE 0 END) +      \r\n"
			+ "				                  SUM(CASE WHEN OTM_TEST_NAME = 'Physical Education Director Grade - 1' THEN 1   ELSE 0 END)  as Total     \r\n"
			+ "				                  	                         \r\n" + "				              FROM OES_TEST_CENTER_MASTER      \r\n"
			+ "				              LEFT OUTER JOIN OES_CAND_PREF_DETAILS ON (OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_1_FK)   \r\n"
			+ "				              LEFT OUTER JOIN OES_USER_MASTER ON (OUM_USER_PK=OCPD_USER_FK)    \r\n"
			+ "				              inner JOIN (select distinct opd_user_fk from  OES_PAYMENT_DETAILS where  OPD_VALIDATED_STATUS = 'A') v ON (OPD_USER_FK = OUM_USER_PK)   \r\n"
			+ "				              LEFT OUTER JOIN OES_TEST_MASTER ON (OES_USER_MASTER.OUM_TEST_FK = OES_TEST_MASTER.OTM_TEST_PK AND OTM_STATUS='A')     \r\n"
			+ "				              WHERE OTCM_STATUS='A'  \r\n" + "				              GROUP BY  OTCM_TEST_CENTRE_NAME     \r\n"
			+ "				              union  \r\n" + "				SELECT row_count, 'GrandTotal', SUM(\"TETI\"),SUM(\"TETII\"), \r\n"
			+ "				SUM(Total) FROM(                  \r\n" + "				              SELECT 2 row_count,     \r\n"
			+ "				                  'Grand Total',     \r\n"
			+ "				                  SUM(CASE WHEN OTM_TEST_NAME = 'Post Graduate Assistants' THEN 1   ELSE 0 END) as  \"TETI\"  ,     \r\n"
			+ "				                  SUM(CASE WHEN OTM_TEST_NAME = 'Physical Education Director Grade - 1' THEN 1   ELSE 0 END) as \"TETII\"  ,     \r\n"
			+ "				                      \r\n" + "				                  SUM(CASE WHEN OTM_TEST_NAME = 'Post Graduate Assistants' THEN 1   ELSE 0 END) +      \r\n"
			+ "				                  SUM(CASE WHEN OTM_TEST_NAME = 'Physical Education Director Grade - 1' THEN 1   ELSE 0 END)  \r\n"
			+ "				                  as Total     \r\n" + "				                   \r\n" + "				              FROM OES_TEST_CENTER_MASTER      \r\n"
			+ "				              LEFT OUTER JOIN OES_CAND_PREF_DETAILS ON (OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_1_FK  )     \r\n"
			+ "				              LEFT OUTER JOIN OES_USER_MASTER ON (OUM_USER_PK=OCPD_USER_FK)    \r\n"
			+ "				              inner JOIN (select distinct opd_user_fk from  OES_PAYMENT_DETAILS where  OPD_VALIDATED_STATUS = 'A') v ON (OPD_USER_FK = OUM_USER_PK)   \r\n"
			+ "				              LEFT OUTER JOIN OES_TEST_MASTER ON (OES_USER_MASTER.OUM_TEST_FK = OES_TEST_MASTER.OTM_TEST_PK AND OTM_STATUS='A')     \r\n"
			+ "				              WHERE OTCM_STATUS='A'  \r\n" + "				              GROUP BY  OTCM_TEST_CENTRE_NAME     ) d \r\n"
			+ "				GROUP BY row_count \r\n" + "				ORDER BY row_count,2";

	String GET_TEST_CENTER_WISE_REPORT_FOR_PU1 = "SELECT   1 row_count,     \r\n" + "	                  OTCM_TEST_CENTRE_NAME,    \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Assistant Engineer' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) as  \"AssistantEngineer\"  ,    \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Environmental Scientist' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) as \"EnvironmentalScientist\"  ,    \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Assistant (Junior Assistant)' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) as \"JuniorAssistant\"  ,    \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Typist' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) as \"Typist\"  ,    \r\n"
			+ "	                  \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Assistant Engineer' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) +     \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Environmental Scientist' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END)  +    \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Assistant (Junior Assistant)' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END)+     \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Typist' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) as Total    \r\n"
			+ "	                  	                        \r\n" + "	              FROM OES_TEST_CENTER_MASTER     \r\n"
			+ "	              LEFT OUTER JOIN OES_CAND_PREF_DETAILS ON (OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_1_FK /* OR \r\n"
			+ "	                                                  OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_2_FK OR \r\n"
			+ "	                                                  OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_3_FK */ )    \r\n"
			+ "	              LEFT OUTER JOIN OES_USER_MASTER ON (OUM_USER_PK=OCPD_USER_FK)   \r\n"
			+ "	              inner JOIN (select distinct opd_user_fk from  OES_PAYMENT_DETAILS where  OPD_VALIDATED_STATUS = 'A') ON (OPD_USER_FK = OUM_USER_PK)  \r\n"
			+ "	              LEFT OUTER JOIN OES_TEST_MASTER ON (OES_USER_MASTER.OUM_TEST_FK = OES_TEST_MASTER.OTM_TEST_PK AND OTM_STATUS='A')    \r\n"
			+ "	              WHERE OTCM_STATUS='A' \r\n" + "	              GROUP BY  OTCM_TEST_CENTRE_NAME    \r\n" + "	              union \r\n"
			+ "	SELECT row_count, 'GrandTotal', SUM(\"AssistantEngineer\"),SUM(\"EnvironmentalScientist\"),  SUM(\"JuniorAssistant\"),SUM(\"Typist\"),\r\n"
			+ "	SUM(Total) FROM(                 \r\n" + "	              SELECT 2 row_count,    \r\n" + "	                  'Grand Total',    \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Assistant Engineer' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) as  \"AssistantEngineer\"  ,    \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Environmental Scientist' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) as \"EnvironmentalScientist\"  ,    \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Assistant (Junior Assistant)' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) as \"JuniorAssistant\"  ,    \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Typist' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) as \"Typist\"  ,    \r\n"
			+ "	                      \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Assistant Engineer' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) +     \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Environmental Scientist' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END)  +    \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Assistant (Junior Assistant)' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END)+     \r\n"
			+ "	                  SUM(CASE WHEN OTM_TEST_NAME = 'Typist' /* AND OPD_VALIDATED_STATUS = 'A' */THEN 1   ELSE 0 END) as Total    \r\n" + "	                  \r\n"
			+ "	              FROM OES_TEST_CENTER_MASTER     \r\n"
			+ "	              LEFT OUTER JOIN OES_CAND_PREF_DETAILS ON (OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_1_FK /* OR \r\n"
			+ "	                                                  OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_2_FK OR \r\n"
			+ "	                                                  OTCM_TEST_CENTRE_PK=OCPD_PREF_TEST_CENTRE_3_FK  */ )    \r\n"
			+ "	              LEFT OUTER JOIN OES_USER_MASTER ON (OUM_USER_PK=OCPD_USER_FK)   \r\n"
			+ "	              inner JOIN (select distinct opd_user_fk from  OES_PAYMENT_DETAILS where  OPD_VALIDATED_STATUS = 'A') ON (OPD_USER_FK = OUM_USER_PK)  \r\n"
			+ "	              LEFT OUTER JOIN OES_TEST_MASTER ON (OES_USER_MASTER.OUM_TEST_FK = OES_TEST_MASTER.OTM_TEST_PK AND OTM_STATUS='A')    \r\n"
			+ "	              WHERE OTCM_STATUS='A' \r\n" + "	              GROUP BY  OTCM_TEST_CENTRE_NAME     ) d\r\n" + "	GROUP BY row_count\r\n" + "	ORDER BY row_count,2 ";

}
