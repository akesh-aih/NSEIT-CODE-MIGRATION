package com.nseit.generic.queries;

import org.springframework.jdbc.core.PreparedStatementCreator;

public interface UserQueries {

	String SELECT_USER_FOR_LOGIN = "SELECT GUM.OUM_USER_PK, GUM.OUM_USER_ID, GUM.OUM_PASSWORD, GUM.OUM_IPIN,"
			+ " GUM.OUM_FIRST_LOGIN, GUM.OUM_USER_TYPE,GUM.OUM_EMAIL_ID,GUM.OUM_MOBILE_NO, ocd_genderfk,oum_category_fk,oum_last_name,oum_middle_name,oum_first_name,"
			+ " OUM_STAGE_FK,OCD_USER_FK, OUM_TEST_FK, OUM_FIRST_NAME,OUM_MIDDLE_NAME,OUM_LAST_NAME,"
			+ " OCD_STATUS_ID_FK, OPD_VALIDATED_STATUS,OUM_ROLE_FK,OUM_INITIAL,declaration_checkbox_status,OUM_NATIVITY_CERT_DIST,"
			+ "	oum_nationality,ocd_date_of_birth as ocd_date_of_birth,ocd_test_center1,ocd_test_center2,ocd_test_center3,"
			+ " ocd_discipline as ocd_discipline, coalesce (oum_inval_attempt_count,0) as invalid_count,oum_status,oum_full_name,ocd_category_fk,OCD_BENCHMARKDISABILITY "
			+ " FROM OES_USER_MASTER GUM LEFT JOIN OES_CANDIDATE_DETAILS ON GUM.OUM_USER_PK=OCD_USER_FK "
			+ " LEFT JOIN OES_PAYMENT_DETAILS ON GUM.OUM_USER_PK = OPD_USER_FK  WHERE GUM.OUM_USER_ID  = ?";

	String SELECT_USER_FOR_LOGIN_ENC1 = "SELECT gum.oum_user_pk, gum.oum_user_id, oum_password, gum.oum_ipin, gum.oum_first_login, gum.oum_user_type,"
			+ "pgp_sym_decrypt(oum_email_id::bytea, ?,'cipher-algo=aes256') AS oum_email_id,"
			+ "pgp_sym_decrypt(oum_mobile_no::bytea, ?,'cipher-algo=aes256') AS oum_mobile_no,"
			+ "oum_genderfk, oum_category_fk, oum_last_name, oum_middle_name,oum_age,"
			+ "pgp_sym_decrypt(oum_first_name::bytea,?,'cipher-algo=aes256') AS oum_first_name,"
			+ "oum_stage_fk, ocd_user_fk, oum_test_fk, oum_widowYesNo, oum_widowCertNo, oum_widowIssueDate, oum_widowIssueAuth, oum_widowIssueDist, oum_widowIssueDistOther, oum_widowSubDivision, oum_widowCheckbox,"
			+ "oum_middle_name, oum_last_name, ocd_status_id_fk, opd_validated_status, oum_role_fk,"
			+ "pgp_sym_decrypt(oum_initial::bytea,?,'cipher-algo=aes256') AS oum_initial,"
			+ "declaration_checkbox_status, oum_nativity_cert_dist, oum_nationality,"
			+ "pgp_sym_decrypt(ocd_date_of_birth::bytea,?,'cipher-algo=aes256') AS ocd_date_of_birth, ocd_test_center1, ocd_test_center2, ocd_test_center3,"
			+ "ocd_discipline AS ocd_discipline, COALESCE(oum_inval_attempt_count,0) AS invalid_count, oum_status,pgp_sym_decrypt(oum_full_name::bytea,?,'cipher-algo=aes256') AS oum_full_name,"
			+ "oum_exserviceman,oum_discharge_date,oum_ppo_number,oum_comm_cert_yesno, oum_community, oum_subcaste, oum_issue_auth_comm_cert, oum_comm_cert_number," 
			+ "oum_comm_cert_issue_place, oum_comm_cert_issue_date, oum_disability_yesno, oum_diiferently_abled_checkbox, oum_disability_type, oum_disability_percent, oum_date_of_birth,"
			+ "ocd_category_fk, ocd_benchmarkdisability FROM oes_user_master gum LEFT JOIN oes_candidate_details ON gum.oum_user_pk=ocd_user_fk "
			+ "LEFT JOIN oes_payment_details ON gum.oum_user_pk=opd_user_fk WHERE gum.oum_user_id  = ?";

	String GET_STATUS_FOR_USER = "select oum_status from oes_user_master where OUM_USER_ID = ?";
	
	String GET_USER_PK_FOR_USER = "select oum_user_pk from oes_user_master where OUM_USER_ID = ?";
	
	String CHECK_IF_USER_EXISTS1 = "select count(*) from oes_user_master where OUM_USER_ID = ?";

	String CHECK_IF_USER_EMAIL_EXIST = "select count(*) from oes_user_master oum where oum.OUM_USER_ID = ? and oum.oum_email_id=? ";

	// queries for USER MANAGEMENT

	String INSERT_NEW_USER = "INSERT INTO oes_user_master(oum_user_pk, oum_user_id, oum_password, oum_user_type, oum_email_id, oum_mobile_no, oum_role_fk, "
			+ "oum_created_by, oum_created_date, oum_user_name, oum_status) "
			+ "VALUES (nextval('oes_user_master_seq'), ?, ?, ?, ?, " + "?, ?, ?, CURRENT_TIMESTAMP, ?, ?)";

	String UPDATE_USER = "UPDATE OES_USER_MASTER SET oum_password = ?, oum_email_id = ?, oum_mobile_no = ?, oum_role_fk = ?, oum_user_name = ?, "
			+ "oum_status = ?, oum_updated_by = ?, oum_updated_date = CURRENT_TIMESTAMP where oum_user_id = ? ";

	String GET_USER_SEARCH = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY oum_user_pk) AS num,oum_user_pk,oum_user_id,oum_user_name,oum_email_id,oum_mobile_no,oum_status from oes_user_master where oum_user_type <> 'C' and oum_role_fk =CASE WHEN ?='0' THEN oum_role_fk ELSE ? END  and oum_status=CASE WHEN ?='B' THEN oum_status ELSE ? END ";

	String GET_USER_COUNT = " select count(*) from oes_user_master where oum_user_type <> 'C' and oum_role_fk =CASE WHEN ?='0' THEN oum_role_fk ELSE ? END  and oum_status=CASE WHEN ?='B' THEN oum_status ELSE ? END ";

	String GET_USER_EDIT_DETAILS = "select oum_user_pk,oum_user_id, oum_password,oum_user_type,oum_email_id, oum_mobile_no, oum_role_fk,oum_user_name, oum_status from oes_user_master where oum_user_pk = ? ";

	String GET_MOBILE_IN_USER_MASTER = "SELECT OUM_MOBILE_NO from OES_USER_MASTER WHERE oum_user_id = ?";

	String CHECK_IF_OTP_FOR_EMAIL_EXISTS = "SELECT oeom_otp from oes_email_otp_master WHERE oeom_to_address = ?";

	String CHECK_OTP_EXPIRY = "SELECT omom_otp from oes_mob_otp_master WHERE omom_to_address = ? and otp_time::timestamp >= (now()-interval '2 min')";

	String CHECK_FORGOT_PASSWORD_FLAG_EXISTS = "SELECT oum_forgot_password FROM oes_user_master WHERE oum_user_id = ?";

	String CHANGE_PASSWORD = "UPDATE oes_user_master SET oum_password=?, oum_forgot_password='false' WHERE oum_user_id=?";

	String UPDATE_FORGOT_PASSWORD_KEY = "UPDATE oes_user_master SET oum_forgot_key=? WHERE oum_user_id = ?";

	String GET_FORGOT_PASSWORD_KEY = "SELECT oum_forgot_key FROM oes_user_master WHERE oum_user_id = ?";

	String GET_OLD_PASSWORD = "SELECT oum_password FROM oes_user_master WHERE oum_user_id = ?";

	String UPDATE_USER_STATUS = " UPDATE oes_user_master SET oum_status=?, oum_inval_attempt_count=? WHERE oum_user_id = ?";

	String CHECK_IF_USER_ID_EXISTS = "SELECT COUNT(0) FROM oes_user_master WHERE oum_user_id = ?";

	String GET_USER_FK_FOR_PAY_APP = "select opd_user_fk from oes_payment_details where opd_transaction_no = ? and opd_dd_challan_receipt_no = ?";

	String UPDATE_USER_CHECKBOX_STATUS = "update oes_user_master set declaration_checkbox_status = ? where oum_user_pk = ?";

	String CHECK_IF_USER_FOR_EMAIL_AND_POST_EXISTS_ENC1 = "SELECT COUNT(1) FROM oes_user_master oum "
			+ "WHERE pgp_sym_decrypt(oum.oum_email_id::bytea, ?,'cipher-algo=aes256') = ? AND oum_test_fk=?";
	
	String CHECK_IF_USER_FOR_MOBILE_AND_POST_EXISTS_ENC1 = "SELECT COUNT(1) FROM oes_user_master oum "
			+ "WHERE pgp_sym_decrypt(oum.oum_mobile_no::bytea, ?,'cipher-algo=aes256') = ? AND oum_test_fk=?";
	
	/*String CHECK_IF_USER_FOR_MOBILE_AND_POST_EX = "select string_agg(flag,',')a from (select 'M' FLAG from oes_user_master WHERE pgp_sym_decrypt(oum_mobile_no::bytea,?,'cipher-algo=aes256') = ?" + 
			"	 union select 'E' FLAG from oes_user_master WHERE pgp_sym_decrypt(oum_email_id::bytea, ?,'cipher-algo=aes256') = ? AND oum_test_fk=? order by 1)a";*/
	
	String CHECK_IF_USER_FOR_MOBILE_AND_POST_EX = "select string_agg(flag,',')a from (select 'M' FLAG from oes_user_master WHERE oum_mobile_enc = SHA256(?::bytea) " + 
			"	 union select 'E' FLAG from oes_user_master WHERE oum_email_enc = SHA256(?::bytea) AND oum_test_fk=? order by 1)a";

	String CHECK_IF_USER_FOR_EMAIL_AND_POST_EXISTS1 = "select count(1) from oes_user_master where OUM_EMAIL_ID=? and OUM_TEST_FK = ?";

	String CHECK_IF_USER_FOR_MOBILE_AND_POST_EXISTS1 = "select count(1) from oes_user_master where OUM_MOBILE_NO=? and OUM_TEST_FK = ?";

	String GET_FIRST_LOGGED_FLAG = "SELECT oum_first_login FROM oes_user_master WHERE oum_user_pk=?";

	String GET_AGE_MATRIX = "SELECT  to_char(oam_min_date ,'dd-Mon-yyyy') AS START_DATE "
			+ "	FROM OES_AGE_MATRIX WHERE oam_exservicemen=? and oam_pwd=? and oam_category_fk=? and oam_destitute_widow=?";

	String GET_USER_FROM_EMAIL = "SELECT GUM.OUM_USER_PK, GUM.OUM_USER_ID, GUM.OUM_PASSWORD, GUM.OUM_IPIN,"
			+ " GUM.OUM_FIRST_LOGIN, GUM.OUM_USER_TYPE,GUM.OUM_EMAIL_ID,GUM.OUM_MOBILE_NO, oum_genderfk,oum_last_name,oum_middle_name,oum_first_name,"
			+ "(oum_first_name || ' ' || oum_middle_name || ' ' || oum_last_name) as oum_name,"
			+ " OUM_STAGE_FK,OCD_USER_FK, OUM_TEST_FK, OUM_FIRST_NAME,OUM_MIDDLE_NAME,OUM_LAST_NAME ,OCD_DATE_OF_BIRTH, "
			+ "OCD_STATUS_ID_FK, OPD_VALIDATED_STATUS,OUM_ROLE_FK,OUM_INITIAL,declaration_checkbox_status,"
			+ " (select orvm_reference_value from oes_reference_value_master where orvm_reference_pk::varchar = GUM.oum_nationality) as oum_nationality,"
			+ "	ocd_date_of_birth as ocd_date_of_birth," + "	ocd_discipline as ocd_discipline, coalesce (oum_inval_attempt_count,0) as invalid_count "
			+ " FROM OES_USER_MASTER GUM " + " LEFT JOIN OES_CANDIDATE_DETAILS ON GUM.OUM_USER_PK=OCD_USER_FK " + " LEFT JOIN OES_PAYMENT_DETAILS ON GUM.OUM_USER_PK = OPD_USER_FK "
			+ "  WHERE GUM.OUM_EMAIL_ID  = ?";
	
	String GET_USER_FROM_EMAIL_ENC1 = "SELECT gum.oum_user_pk, gum.oum_user_id, gum.oum_password, gum.oum_ipin, gum.oum_first_login, gum.oum_user_type,"
			+ "pgp_sym_decrypt(oum_email_id::bytea, ?,'cipher-algo=aes256') AS oum_email_id," 
			+ "pgp_sym_decrypt(oum_mobile_no::bytea, ?,'cipher-algo=aes256') AS oum_mobile_no,"
			+ "oum_genderfk, oum_last_name, oum_middle_name," 
			+ "pgp_sym_decrypt(oum_first_name::bytea,?,'cipher-algo=aes256') AS oum_first_name"
			+ "(pgp_sym_decrypt(oum_first_name::bytea,?,'cipher-algo=aes256') AS oum_first_name || ' ' || oum_middle_name || ' ' || oum_last_name) AS oum_name,"
			+ "oum_stage_fk, ocd_user_fk, oum_test_fk,"  
			+ "oum_middle_name, oum_last_name,"
			+ " ocd_status_id_fk, opd_validated_status, oum_role_fk," 
			+ "pgp_sym_decrypt(oum_initial::bytea,?,'cipher-algo=aes256') AS oum_initial,"
			+ "declaration_checkbox_status, oum_nationality,"
			+ "pgp_sym_decrypt(ocd_date_of_birth::bytea,?,'cipher-algo=aes256') AS ocd_date_of_birth,"
			+ " ocd_discipline AS ocd_discipline, COALESCE(oum_inval_attempt_count,0) AS invalid_count "
			+ "FROM oes_user_master gum LEFT JOIN oes_candidate_details ON gum.oum_user_pk=ocd_user_fk" 
			+ "LEFT JOIN oes_payment_details ON gum.oum_user_pk = opd_user_fk "
			+ "WHERE pgp_sym_decrypt(oum_email_id::bytea, ?,'cipher-algo=aes256') = ?";
	
	String CHECK_IF_EMAIL_EXISTS = "SELECT COUNT(0) from OES_USER_MASTER WHERE OUM_EMAIL_ID = ?";
	
	String CHECK_IF_EMAIL_EXISTS_ENC1 = "SELECT COUNT(1) FROM oes_user_master oum WHERE oum_email_enc = SHA256(?::bytea)";
	
	String CHECK_IF_MOBILE_EXISTS = "SELECT COUNT(0) from OES_USER_MASTER WHERE OUM_MOBILE_NO = ?";
	
	String CHECK_IF_MOBILE_EXISTS_ENC1 = "SELECT COUNT(1) FROM oes_user_master oum WHERE oum_mobile_enc = SHA256(?::bytea)";
	
	String CHECK_IF_USER_EXISTS = "select OUM_USER_PK from oes_user_master where OUM_USER_ID = ?";
	
	
	String CHECK_IF_USER_MOBILE_EXIST = "SELECT COUNT(*) FROM oes_user_master WHERE oum_mobile_no=? ";
	
	String CHECK_IF_USER_MOBILE_EXIST_ENC1 = "SELECT CASE WHEN COUNT(*) > 0 THEN 'true' ELSE 'false' END FROM oes_user_master oum WHERE oum_mobile_enc = SHA256(?::bytea) "
			+ " AND oum_user_id = ?";
	
	String CHECK_IF_EMAIL_EXIST_FOR_USER = "SELECT CASE WHEN COUNT(*) > 0 THEN 'true' ELSE 'false' END FROM oes_user_master oum WHERE oum_email_enc = SHA256(?::bytea) "
			+ " AND oum_user_id = ?";
	
	String UPDATE_ATTEMPT_COUNT = "UPDATE oes_user_master SET oum_request_id = oum_request_id + 1 where oum_user_id = ? and oum_request_id <= 5";

	String GET_DETAILS_BY_LOGIN_ID = "select pgp_sym_decrypt(OUM_EMAIL_ID::bytea, ?,'cipher-algo=aes256') AS OUM_EMAIL_ID from oes_user_master where OUM_USER_ID=?";
	

}
