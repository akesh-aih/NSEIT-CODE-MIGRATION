package com.nseit.generic.queries;

public interface RegistrationQueries {

	String FORGOT_PASSWORD = "UPDATE oes_user_master SET oum_forgot_password = 'true', oum_password = ?, oum_status = 'A', oum_inval_attempt_count = null WHERE oum_user_id = UPPER(?)";
	
	String CHECK_IF_EMAIL_EXISTS_ENC1 = "SELECT COUNT(*) FROM oes_user_master oum WHERE pgp_sym_decrypt(oum.oum_email_id::bytea, ?,'cipher-algo=aes256') = ?";
	
	String CHECK_IF_SMS_EMAIL_IS_VERIFIED = "SELECT COUNT(*) FROM OES_MOB_OTP_MASTER omom WHERE (omom_to_address = ? or  omom_to_address = ?) and omom_status = 'verified' ";
	
	String CHECK_IF_EMAIL_EXISTS = "Select count(*) from oes_user_master where oum_email_id = ?";
	
	String UPDATE_USER_PASSWORD = "UPDATE oes_user_master SET oum_password = ?, oum_first_login ='N' WHERE oum_user_pk = ? AND oum_password = ?";

	String UPDATE_USER_PASSWORD_FOR_FIRST_TIME = "update OES_USER_MASTER set OUM_PASSWORD = ? , OUM_FIRST_LOGIN = ? where OUM_USER_ID = ? AND OUM_PASSWORD = ? ";

	String SELECT_GENERATED_USER_ID = "SELECT OUM_USER_ID,OUM_USER_PK FROM OES_USER_MASTER WHERE pgp_sym_decrypt(oum_mobile_no::bytea, ?,'cipher-algo=aes256')  = ? AND pgp_sym_decrypt(oum_email_id::bytea, ?,'cipher-algo=aes256')  = ? AND OUM_USER_TYPE = 'C' AND OUM_TEST_FK = ?";

	String GET_FORGOT_USER_ID_DTLS = " SELECT oum.OUM_USER_ID  FROM oes_user_master oum, oes_qstn_answ oqa WHERE oum.oum_email_id = ? "
			+ " and oqa.OQA_REF_FK = ? and oqa.OQA_QSTN_ANS = ? and oum.OUM_USER_PK = oqa.OQA_USER_FK";

	String CHANGE_PASSWORD = " update OES_USER_MASTER set OUM_PASSWORD = ?,OUM_FORGOT_PASSWORD='false' where OUM_USER_ID = upper(?)";

	String CHECK_IF_POST_EXISTS = "Select count(*) from oes_user_master where oum_email_id = ? and oum_test_fk = ? ";

	String NOOFDIGITSRANDOM_SEQUENCE = "select nextval('RANDOM_SEQ')";

	String INSERT_USER_DETAILS = "INSERT INTO OES_USER_MASTER ( OUM_USER_PK,OUM_USER_ID,OUM_MOBILE_NO, OUM_EMAIL_ID,oum_first_name,oum_initial,OUM_NATIONALITY, oum_nativity_cert_dist,oum_test_fk,OUM_PASSWORD, "
			+ "  OUM_IPIN, OUM_FIRST_LOGIN, OUM_USER_TYPE,OUM_STAGE_FK,OUM_ROLE_FK,"// oum_category_fk,
			+ " OUM_CREATED_BY, OUM_CREATED_DATE,"
			+ " declaration_checkbox_status)" + " VALUES (nextVal('OES_USER_MASTER_SEQ'),?, ?,?,?,?,?,?,?," + "?,?,?,?,?,?," + "?,CURRENT_TIMESTAMP,'true')";

	String INSERT_USER_DETAILS_ENC1 = "INSERT INTO oes_user_master (oum_user_pk, oum_user_id,"
			+ " oum_mobile_no, oum_email_id, oum_full_name, oum_initial, oum_nationality, oum_nativity_cert_dist, oum_test_fk, oum_password,"
			+ " oum_ipin, oum_first_login, oum_user_type, oum_stage_fk, oum_role_fk, oum_created_by, oum_created_date, declaration_checkbox_status, oum_request_id,"
			+ " oum_exserviceman, oum_discharge_date, oum_ppo_number, oum_comm_cert_yesno, oum_community, oum_subcaste, oum_issue_auth_comm_cert, oum_comm_cert_number, "
			+ " oum_comm_cert_issue_place, oum_comm_cert_issue_date, oum_disability_yesno, oum_disability_type, oum_disability_percent, oum_date_of_birth,oum_age,oum_mobile_enc,oum_email_enc,oum_diiferently_abled_checkbox,"
			+ " oum_widowYesNo, oum_widowCertNo, oum_widowIssueDate, oum_widowIssueAuth, oum_widowIssueDist, oum_widowIssueDistOther, oum_widowSubDivision, oum_widowCheckbox,oum_genderfk) "
			+ " VALUES (nextVal('OES_USER_MASTER_SEQ'),?," + " pgp_sym_encrypt(?, ?,'cipher-algo=aes256')," + " pgp_sym_encrypt(?, ?,'cipher-algo=aes256'),"
			+ " pgp_sym_encrypt(?, ?,'cipher-algo=aes256')," + " pgp_sym_encrypt(?, ?,'cipher-algo=aes256'),"
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, 'true',0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SHA256(?::bytea),SHA256(?::bytea),?,?,?,?,?,?,?,?,?,?)";
	
	String CHECK_IF_MOB_FOR_OTP_EXISTS = "SELECT omom_status , SEND_OTP_COUNT from oes_mob_otp_master WHERE omom_to_address = ? ";

	String UPDATE_MOB_OTP_DETAIL = "update OES_MOB_OTP_MASTER set OMOM_OTP=?, OTP_TIME = now(),SEND_OTP_COUNT=(SEND_OTP_COUNT +1) where OMOM_TO_ADDRESS=?";
	
	String UPDATE_MOB_OTP_DETAIL1 = "update OES_MOB_OTP_MASTER set OMOM_OTP=?, SEND_OTP_COUNT=(SEND_OTP_COUNT +1) where OMOM_TO_ADDRESS=?";

	String INSERT_MOB_OTP_DETAIL = "INSERT INTO OES_MOB_OTP_MASTER (OMOM_PK, OMOM_TO_ADDRESS, OMOM_OTP,OMOM_STATUS,OTP_TIME,SEND_OTP_COUNT,omom_sms_email_tran_pk) " //,OMOM_TEST_FK
			+ " VALUES (nextVal('OES_MOB_OTP_MASTER_SEQ'), ?, ?,?,now(),1,0)";

	String CHECK_IF_OTP_FOR_MOBILE_EXISTS = "SELECT omom_otp from OES_MOB_OTP_MASTER WHERE omom_to_address = ? and OMOM_TEST_FK =?";

	String CHECK_IF_OTP_FOR_EMAIL_EXISTS = "SELECT oeom_otp from oes_email_otp_master WHERE oeom_to_address = ? and OEOM_TEST_FK =? order by OTP_TIME desc limit 1";

	String GET_MOBILE_IN_USER_MASTER = "SELECT OUM_MOBILE_NO from OES_USER_MASTER WHERE oum_user_id = ?";

	String UPDATE_MOBILE_OTP_DETAIL = "UPDATE oes_mob_otp_master SET omom_status=?, omom_user_id=? WHERE omom_to_address IN (?,?)";

	String GET_USER_FK = "SELECT oum_user_pk FROM oes_user_master WHERE oum_user_id = ?";
	
	String VERIFY_OTP = "SELECT otp_time FROM oes_mob_otp_master WHERE omom_otp = ? AND omom_to_address= ?";
	String SELECT_MAX_TRY = "SELECT send_otp_count FROM oes_mob_otp_master WHERE omom_to_address = ?";
	String UPDATE_VERIFIED_COUNT = "UPDATE oes_mob_otp_master SET omom_sms_email_tran_pk = omom_sms_email_tran_pk + 1 WHERE omom_to_address = ?";
	
	String SEQ_1 = "SELECT nextval('phd_seq')";
	String SEQ_2 = "SELECT nextval('msc_seq')";
	
	String CHECK_IF_FGT_PASS_OTP_FOR_MOBILE_EXISTS = "SELECT ofpmom_otp FROM oes_forgot_pass_mob_otp_master WHERE ofpmom_to_address = ? AND ofpmom_user_id = ?";
	
	String CHECK_IF_MOB_FOR_FGT_PASS_OTP_EXISTS = "SELECT CASE WHEN COUNT(ofpmom_to_address) > 0 THEN 'true' ELSE 'false' END FROM oes_forgot_pass_mob_otp_master "
			+ "WHERE ofpmom_to_address = ? AND ofpmom_user_id = ?"; 
	
	String UPDATE_FGT_PASS_MOB_OTP_DETAIL = "UPDATE oes_forgot_pass_mob_otp_master SET ofpmom_otp=?, ofpmom_otp_time = NOW(), ofpmom_send_otp_count=(ofpmom_send_otp_count +1) WHERE ofpmom_to_address=? ";

	String INSERT_FGT_PASS_MOB_OTP_DETAIL = "INSERT INTO oes_forgot_pass_mob_otp_master (ofpmom_pk, ofpmom_to_address, ofpmom_otp, ofpmom_otp_time, ofpmom_user_id, ofpmom_sms_email_tran_pk) " 
			+ "VALUES (nextVal('oes_forgot_pass_mob_otp_master_seq'), ?, ?, NOW(), ?, 0)";

	
	String GET_FORGOT_USER_ID_DTLS_EXIST_EMAIL = "SELECT oum.OUM_USER_ID FROM oes_user_master oum WHERE lower(oum.oum_email_id) = ?"; 
	 
	String GET_FORGOT_USER_ID_DTLS_EXIST_EMAIL_ENC1 = "SELECT oum.oum_user_id FROM oes_user_master oum WHERE oum_email_enc = SHA256(?::bytea) AND (oum_status = 'A' or oum_status = 'D' or oum_status is NULL)"; 
	
	String GET_FORGOT_USER_ID_DTLS_EXIST_MOB = "SELECT oum.OUM_USER_ID FROM oes_user_master oum WHERE oum.oum_mobile_no = ?";
	
	String GET_FORGOT_USER_ID_DTLS_EXIST_MOB_ENC1 = "SELECT oum.OUM_USER_ID FROM oes_user_master oum WHERE oum_mobile_enc = SHA256(?::bytea) AND (oum_status = 'A' or oum_status = 'D' or oum_status is NULL)";

	String CHECK_IF_EMAIL_FOR_FGT_PASS_OTP_EXISTS ="SELECT CASE WHEN COUNT(ofpmom_to_address) > 0 THEN 'true' ELSE 'false' END FROM oes_forgot_pass_mob_otp_master "
			+ "WHERE ofpmom_to_address = ? AND ofpmom_user_id = ?";

	String UPDATE_FGT_PASS_EMAIL_OTP_DETAIL = "UPDATE oes_forgot_pass_mob_otp_master SET ofpmom_otp=?, ofpmom_otp_time = NOW(), ofpmom_send_otp_count=(ofpmom_send_otp_count +1) WHERE ofpmom_to_address=? ";

	String INSERT_FGT_PASS_EMAIL_OTP_DETAIL = "INSERT INTO oes_forgot_pass_mob_otp_master (ofpmom_pk, ofpmom_to_address, ofpmom_otp, ofpmom_otp_time, ofpmom_user_id, ofpmom_sms_email_tran_pk) " 
			+ "VALUES (nextVal('oes_forgot_pass_mob_otp_master_seq'), ?, ?, NOW(), ?, 0)";
	
	String GET_RANDOM_NUM = "select lpad(?::varchar,6,'0')";
	
	String USER_ID_EXIST = "select count(1) from oes_user_master where oum_user_id = ?||?";

}

