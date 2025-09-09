package com.nseit.generic.queries;

public interface CandidateQueries {

	String CHECK_IMAGE_EXISTS_FOR_USERFK = "SELECT COUNT(1) FROM OES_CANDIDATE_IMAGES WHERE OCI_USER_FK = ? ";

	String GET_CANDIDATE_IMAGES = "SELECT oci_photo_image, oci_candidate_image_pk FROM oes_candidate_images WHERE oci_status='A' AND oci_user_fk=? ";

	String INSERT_CANDIDATE_IMAGES = "INSERT INTO oes_candidate_images(oci_candidate_image_pk, oci_user_fk, oci_photo_image, oci_status, oci_created_by,"
			+ " oci_created_date, oci_photo_image_path) VALUES (nextval('OES_CANDIDATE_IMAGES_SEQ'),?,?,'A',?,CURRENT_TIMESTAMP,?) ";

	String UPDATE_CANDIDATE_IMAGES = "UPDATE oes_candidate_images SET oci_photo_image= ?, oci_updated_by=?, oci_updated_date=CURRENT_TIMESTAMP,"
			+ " oci_photo_image_path=? WHERE oci_user_fk = ? ";

	public String SELECT_CANDIDATE_DETAILS = "SELECT oum.oum_test_fk, ocd.ocd_cand_prsnl_detl_pk, ocd.ocd_status_id_fk, ocd.ocd_discipline, ocd.ocd_first_name, "
			+ "oum.oum_initial, oum.oum_nationality, oum.oum_email_id, oum.oum_mobile_no, oum.oum_nativity_cert_dist, ocd.ocd_domicile, ocd.ocd_date_of_birth, "
			+ "ocd.ocd_age, ocd.ocd_genderfk, ocd.ocd_marital_status, ocd.ocd_cand_father_first_name, ocd.ocd_father_initial, ocd.ocd_cand_mother_first_name, "
			+ "ocd.ocd_mother_initial, ocd.ocd_religion, ocd.ocd_other_religion, ocd.ocd_cat_cert, ocd.ocd_category_fk, "
			+ "ocd.ocd_perm_address, ocd.ocd_perm_state_fk, ocd.ocd_perm_district_fk, ocd.ocd_perm_pin_code, ocd.ocd_comm_same_as_perm, "
			+ "ocd.ocd_comm_address, ocd.ocd_comm_state_fk, ocd.ocd_comm_district_fk, ocd.ocd_comm_pin_code "
			+ "FROM oes_user_master oum INNER JOIN oes_candidate_details ocd ON ocd.ocd_user_fk = oum_user_pk WHERE oum.oum_user_pk = ?";

	public String SELECT_CANDIDATE_DETAILS_ENC1 = "SELECT oum.oum_test_fk, ocd.ocd_cand_prsnl_detl_pk, ocd.ocd_status_id_fk, ocd.ocd_discipline,"
			+ " pgp_sym_decrypt(oum.oum_full_name::bytea,?,'cipher-algo=aes256') AS oum_full_name, "
			+ " pgp_sym_decrypt(oum.oum_initial::bytea,?,'cipher-algo=aes256') AS oum_initial, "
			+ " oum.oum_nationality,oum.oum_age,"
			+ " pgp_sym_decrypt(oum.oum_email_id::bytea,?,'cipher-algo=aes256') AS oum_email_id, "
			+ " pgp_sym_decrypt(oum.oum_mobile_no::bytea,?,'cipher-algo=aes256') AS oum_mobile_no, "
			+ " oum.oum_nativity_cert_dist, ocd.ocd_domicile,"
			+ " pgp_sym_decrypt(ocd.ocd_date_of_birth::bytea,?,'cipher-algo=aes256') AS ocd_date_of_birth, "
			+ " pgp_sym_decrypt(ocd.ocd_age::bytea,?,'cipher-algo=aes256') AS ocd_age,"
			+ " ocd.ocd_marital_status,ocd.ocd_spouse_name,ocd.ocd_nativity,ocd.ocd_other_nativity,ocd_relative_fk,ocd_guardian_name,"
			+ " ocd_cand_father_first_name,"
			+ " pgp_sym_decrypt(ocd.ocd_father_initial::bytea,?,'cipher-algo=aes256') AS ocd_father_initial,"
			+ " ocd_cand_mother_first_name, "
			+ " pgp_sym_decrypt(ocd.ocd_mother_initial::bytea,?,'cipher-algo=aes256') AS ocd_mother_initial,"
			+ " oum_genderfk, oum_widowYesNo, oum_widowCertNo, oum_widowIssueDate, oum_widowIssueAuth, oum_widowIssueDist, oum_widowIssueDistOther, oum_widowSubDivision, oum_widowCheckbox,"
			+ " ocd.ocd_govt_date,ocd.ocd_govt_age,ocd.ocd_religion,"
			+ " ocd_other_religion,"
			+ " ocd.ocd_cat_cert, ocd.ocd_category_fk, "
			+ " pgp_sym_decrypt(ocd.ocd_perm_address::bytea,?,'cipher-algo=aes256') AS ocd_perm_address,"
			+ " ocd.ocd_perm_state_fk, ocd.ocd_perm_district_fk,ocd_perm_district_textbox,ocd_perm_city,"
			+ " ocd_perm_pin_code,"
			+ " ocd.ocd_comm_same_as_perm, "
			+ " pgp_sym_decrypt(ocd.ocd_comm_address::bytea,?,'cipher-algo=aes256') AS ocd_comm_address,"
			+ " ocd.ocd_comm_state_fk, ocd.ocd_comm_district_fk,ocd_comm_district_textbox,ocd_comm_city,"
			+ " ocd.ocd_comm_pin_code AS ocd_comm_pin_code,oum_exserviceman, oum_discharge_date, oum_ppo_number, oum_comm_cert_yesno, "
			+ " oum_community, oum_subcaste, oum_issue_auth_comm_cert, oum_comm_cert_number, oum_comm_cert_issue_place, oum_comm_cert_issue_date, "
			+ " oum_disability_yesno, oum_disability_type, oum_disability_percent, oum_date_of_birth,ocd_govt_servant,ocd_certificate_declaration,"
			+ " ocd_org_name,ocd_curr_designation,ocd_plc_of_wrk,ocd_photoidproof1,ocd_photoidproof1val,ocd_mother_tongue  "
			+ "FROM oes_user_master oum INNER JOIN oes_candidate_details ocd ON ocd.ocd_user_fk = oum_user_pk WHERE oum.oum_user_pk = ?";

	public String SELECT_CANDIDATE_DETAILS1 = "SELECT * FROM oes_cand_pref_details WHERE ocpd_user_fk =?";

	String checkDataForUserExistInPaymentDetails = "SELECT COUNT(1) FROM oes_payment_details WHERE opd_user_fk = ? AND opd_payment_fk=5;";

	public String SELECT_CANDIDATE_DETAILS_FOR_FINAL_PRINT_PAGE = "SELECT oum_photo_sign_status, oum_sign_status, otm_test_pk, otm_test_name,"
			+ " ocd.ocd_status_id_fk, ocd.ocd_discipline, ocd.ocd_first_name, oum_initial, oum_nationality, oum_email_id, oum_mobile_no,"
			+ " oum_nativity_cert_dist, ocd.ocd_domicile, ocd.ocd_date_of_birth, ocd.ocd_age, ocd.ocd_genderfk, ocd.ocd_marital_status,"
			+ " ocd.ocd_cand_father_first_name, ocd.ocd_father_initial, ocd.ocd_cand_mother_first_name, ocd.ocd_mother_initial, ocd.ocd_religion,"
			+ " ocd.ocd_other_religion, ocd.ocd_cat_cert, ocd.ocd_category_fk, ocd.ocd_perm_address, ocd.ocd_perm_state_fk as ocd_perm_state_id,"
			+ " osm1.osm_state_name as ocd_perm_state_fk, opsr1.opsr_sub_rank as ocd_perm_district_fk, ocd.ocd_perm_pin_code, ocd.ocd_comm_same_as_perm,"
			+ " ocd.ocd_comm_address, ocd.ocd_comm_state_fk as ocd_comm_state_id, osm2.osm_state_name as ocd_comm_state_fk,"
			+ " opsr2.opsr_sub_rank as ocd_comm_district_fk, ocd.ocd_comm_pin_code, ocd.ocd_form_submited_date "
			+ " FROM oes_user_master INNER JOIN oes_candidate_details ocd ON (oum_user_pk=ocd.ocd_user_fk)"
			+ " LEFT OUTER JOIN oes_test_master ON (otm_test_pk=oum_test_fk)"
			+ " LEFT OUTER JOIN oes_state_master osm1 ON (osm1.osm_state_pk=ocd.ocd_perm_state_fk)"
			+ " LEFT OUTER JOIN oes_posting_sub_ranks opsr1 ON (opsr1.opsr_opsm_fk::varchar=ocd.ocd_perm_district_fk::varchar)"
			+ " LEFT OUTER JOIN oes_state_master osm2 ON (osm2.osm_state_pk=ocd_comm_state_fk)"
			+ " LEFT OUTER JOIN oes_posting_sub_ranks opsr2 ON (opsr2.opsr_opsm_fk::varchar=ocd_comm_district_fk::varchar) WHERE ocd.ocd_user_fk = ?";

	public String SELECT_CANDIDATE_DETAILS_FOR_FINAL_PRINT_PAGE_ENC1 = "SELECT oum_photo_sign_status, oum_sign_status, otm_test_pk, otm_test_name,"
			+ "	ocd.ocd_status_id_fk, ocd.ocd_discipline, "
			+ " pgp_sym_decrypt(oum_full_name::bytea,?,'cipher-algo=aes256') AS oum_full_name, "
			+ " pgp_sym_decrypt(oum_initial::bytea,?,'cipher-algo=aes256') AS oum_initial, "
			+ " oum_nationality,"
			+ " pgp_sym_decrypt(oum_email_id::bytea,?,'cipher-algo=aes256') AS oum_email_id,"
			+ " pgp_sym_decrypt(oum_mobile_no::bytea,?,'cipher-algo=aes256') AS oum_mobile_no,"
			+ " oum_nativity_cert_dist,oum_exserviceman, oum_discharge_date, oum_ppo_number, oum_comm_cert_yesno, "
			+ " oum_community, oum_subcaste, oum_issue_auth_comm_cert, oum_comm_cert_number, oum_comm_cert_issue_place,oum_age, "
			+ " oum_comm_cert_issue_date, oum_disability_yesno, oum_diiferently_abled_checkbox, oum_disability_type, oum_disability_percent, oum_date_of_birth,ocd_govt_servant,ocd_certificate_declaration," 
			+ " ocd_org_name,ocd_curr_designation,ocd_plc_of_wrk,"
			+ " (select native_drop_down_values from oes_nativity_master where onm_native_pk::varchar=ocd_nativity) as  ocd_nativity,"
			+ " (select osm_state_name from oes_state_master where osm_state_pk::varchar= ocd_other_nativity) as ocd_other_nativity,ocd_photoidproof1,ocd_photoidproof1val,"
			+ " ocd_mother_tongue,ocd.ocd_domicile,ocd.ocd_govt_date,ocd.ocd_govt_age,"
			+ " oum_date_of_birth,"
			+ " oum_age,oum_widowYesNo, oum_widowCertNo, oum_widowIssueDate, oum_widowIssueAuth, oum_widowIssueDist, oum_widowIssueDistOther, oum_widowSubDivision, oum_widowCheckbox,"
			+ " ocd.ocd_genderfk, ocd.ocd_marital_status,ocd.ocd_spouse_name,ocd.ocd_relative_fk,"
			+ " ocd.ocd_cand_father_first_name,"
			+ " pgp_sym_decrypt(ocd.ocd_father_initial::bytea,?,'cipher-algo=aes256') AS ocd_father_initial,"
			+ " ocd.ocd_cand_mother_first_name,ocd.ocd_guardian_name,"
			+ " pgp_sym_decrypt(ocd.ocd_mother_initial::bytea,?,'cipher-algo=aes256') AS ocd_mother_initial,"
			+ " ocd.ocd_religion,"
			+ " ocd.ocd_other_religion,"
			+ " ocd.ocd_cat_cert, ocd.ocd_category_fk, "
			+ " pgp_sym_decrypt(ocd.ocd_perm_address::bytea,?,'cipher-algo=aes256') AS ocd_perm_address,"
			+ " ocd.ocd_perm_state_fk as ocd_perm_state_id, osm1.osm_state_name as ocd_perm_state_fk, opsr1.opsr_sub_rank as ocd_perm_district_fk,ocd_perm_district_textbox,ocd_perm_city,"
			+ " ocd.ocd_perm_pin_code, "
			+ " ocd.ocd_comm_same_as_perm,"
			+ " pgp_sym_decrypt(ocd.ocd_comm_address::bytea,?,'cipher-algo=aes256') AS ocd_comm_address, "
			+ " ocd.ocd_comm_state_fk as ocd_comm_state_id, osm2.osm_state_name as ocd_comm_state_fk, opsr2.opsr_sub_rank as ocd_comm_district_fk,ocd_comm_district_textbox,ocd_comm_city,"
			+ " ocd.ocd_comm_pin_code,"
			+ " ocd.ocd_form_submited_date "
			+ " FROM oes_user_master INNER JOIN oes_candidate_details ocd ON (oum_user_pk=ocd.ocd_user_fk)"
			+ " LEFT OUTER JOIN oes_test_master ON (otm_test_pk=oum_test_fk) "
			+ " LEFT OUTER JOIN oes_state_master osm1 ON (osm1.osm_state_pk=ocd.ocd_perm_state_fk)"
			+ " LEFT OUTER JOIN oes_posting_sub_ranks opsr1 ON (opsr1.opsr_opsm_fk::varchar=ocd.ocd_perm_district_fk::varchar)"
			+ " LEFT OUTER JOIN oes_state_master osm2 ON (osm2.osm_state_pk=ocd_comm_state_fk)"
			+ " LEFT OUTER JOIN oes_posting_sub_ranks opsr2 ON (opsr2.opsr_opsm_fk::varchar=ocd_comm_district_fk::varchar) WHERE ocd.ocd_user_fk = ?";

	public String SELECT_CANDIDATE_DETAILS_FOR_DASHBORAD = "SELECT oum_initial, ocd_first_name, ocd_date_of_birth, oum_email_id, oum_mobile_no,"
			+ " ocd_genderfk, ocd_status_id_fk, ocd_discipline FROM oes_user_master, oes_candidate_details ocd"
			+ " WHERE oum_user_pk = ocd.ocd_user_fk AND ocd_user_fk = ? ";

	public String SELECT_CANDIDATE_DETAILS_FOR_DASHBORAD_ENC1 = "SELECT pgp_sym_decrypt(oum_initial::bytea,?,'cipher-algo=aes256') AS oum_initial,"
			+ "pgp_sym_decrypt(oum_full_name::bytea,?,'cipher-algo=aes256') AS oum_full_name,"
			+ "oum_date_of_birth,"
			+ "pgp_sym_decrypt(oum_email_id::bytea,?,'cipher-algo=aes256') AS oum_email_id,"
			+ "pgp_sym_decrypt(oum_mobile_no::bytea,?,'cipher-algo=aes256') AS oum_mobile_no,"
			+ "oum_genderfk, ocd_status_id_fk, ocd_discipline FROM oes_user_master, oes_candidate_details ocd"
			+ " WHERE oum_user_pk = ocd.ocd_user_fk AND ocd_user_fk = ? ";

	public String INSERT_CANDIDATE_DETAILS = "INSERT INTO oes_candidate_details(ocd_cand_prsnl_detl_pk, ocd_user_fk, ocd_discipline, ocd_first_name, "
			+ "ocd_domicile, ocd_date_of_birth, ocd_age, ocd_genderfk, ocd_marital_status, ocd_cand_father_first_name, ocd_father_initial, "
			+ "ocd_cand_mother_first_name, ocd_mother_initial, ocd_religion, ocd_other_religion, ocd_cat_cert, ocd_category_fk,"
			+ "ocd_perm_address, ocd_perm_state_fk, ocd_perm_district_fk, ocd_perm_pin_code, ocd_comm_same_as_perm, "
			+ "ocd_comm_address, ocd_comm_state_fk, ocd_comm_district_fk, ocd_comm_pin_code, ocd_created_by, ocd_created_date) "
			+ "VALUES (nextval('oes_candidate_details_seq'),?,?,?, " + " ?,?,?,?,?,?,?, " + " ?,?,?,?,?,?, " + " ?,?,?,?,?, " + " ?,?,?,?,?," + "CURRENT_TIMESTAMP)";

	public String INSERT_CANDIDATE_DETAILS_ENC1 = "INSERT INTO oes_candidate_details(ocd_cand_prsnl_detl_pk, ocd_user_fk, ocd_discipline, ocd_first_name,"
			+ " ocd_domicile, ocd_date_of_birth, ocd_age, ocd_genderfk, ocd_marital_status, ocd_cand_father_first_name, ocd_father_initial, "
			+ " ocd_cand_mother_first_name, ocd_mother_initial, ocd_religion, ocd_other_religion, ocd_cat_cert, ocd_category_fk,"
			+ " ocd_perm_address, ocd_perm_state_fk, ocd_perm_district_fk, ocd_perm_pin_code, ocd_comm_same_as_perm, "
			+ " ocd_comm_address, ocd_comm_state_fk, ocd_comm_district_fk, ocd_comm_pin_code, ocd_created_by, ocd_created_date) "
			+ " VALUES (nextval('oes_candidate_details_seq'), ?, ?, pgp_sym_encrypt(?, ?, 'cipher-algo=aes256'), "
			+ " ?, pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ?, ?, pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), "
			+ " pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ?, pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ?, ?, "
			+ " pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ?, ?, pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ?, "
			+ " pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ?, ?, pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ?, CURRENT_TIMESTAMP)";

	public String UPDATE_CANDIDATE_DETAILS = "UPDATE oes_candidate_details SET ocd_discipline = ?, ocd_first_name = ?,"
			+ "ocd_domicile = ?, ocd_date_of_birth = ?, ocd_age = ?, ocd_genderfk = ?, ocd_marital_status = ?, ocd_cand_father_first_name = ?,"
			+ "ocd_father_initial = ?, ocd_cand_mother_first_name = ?, ocd_mother_initial = ?, ocd_religion = ?, ocd_other_religion = ?, ocd_cat_cert = ?,"
			+ "ocd_category_fk = ?, ocd_perm_address = ?, ocd_perm_state_fk = ?, ocd_perm_district_fk = ?, ocd_perm_pin_code = ?, ocd_comm_same_as_perm = ?, "
			+ "ocd_comm_address = ?, ocd_comm_state_fk = ?, ocd_comm_district_fk = ?, ocd_comm_pin_code = ?, ocd_updated_by = ?, "
			+ "ocd_updated_date = CURRENT_TIMESTAMP WHERE ocd_user_fk = ?";

	public String UPDATE_CANDIDATE_DETAILS_ENC1 = "UPDATE oes_candidate_details SET ocd_discipline = ?,"
			+ " ocd_first_name = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_domicile = ?, ocd_date_of_birth = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'),"
			+ " ocd_age = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_genderfk = ?, ocd_marital_status = ?,"
			+ " ocd_cand_father_first_name = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_father_initial = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'),"
			+ " ocd_cand_mother_first_name = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_mother_initial = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'),"
			+ " ocd_religion = ?, ocd_other_religion = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_cat_cert = ?, ocd_category_fk = ?,"
			+ " ocd_perm_address = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_perm_state_fk = ?, ocd_perm_district_fk = ?,"
			+ " ocd_perm_pin_code = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_comm_same_as_perm = ?,"
			+ " ocd_comm_address = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_comm_state_fk = ?, ocd_comm_district_fk = ?,"
			+ " ocd_comm_pin_code = pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), ocd_updated_by = ?, ocd_updated_date = CURRENT_TIMESTAMP WHERE ocd_user_fk = ?";

	String SELECT_BASIC_CANDIDATE_DETAILS = "SELECT ocd.ocd_first_name, oum.oum_initial, ocd_benchmarkdisability, ocd_date_of_birth, ocd_genderfk,"
			+ " ocd_category_fk,ocd_discipline FROM oes_candidate_details ocd, oes_user_master oum WHERE oum.oum_user_pk = ocd.ocd_user_fk AND ocd_user_fk=?";

	String SELECT_BASIC_CANDIDATE_DETAILS_ENC1 = "SELECT pgp_sym_decrypt(oum.oum_full_name::bytea,?,'cipher-algo=aes256') AS oum_full_name,"
			+ " pgp_sym_decrypt(oum.oum_initial::bytea,?,'cipher-algo=aes256') as oum_initial,"
			+ " pgp_sym_decrypt(ocd.ocd_date_of_birth::bytea,?,'cipher-algo=aes256') as ocd_date_of_birth,"
			+ " oum_genderfk, ocd_category_fk, ocd_benchmarkdisability, ocd_discipline,oum.oum_date_of_birth "
			+ "	FROM oes_candidate_details ocd, oes_user_master oum WHERE oum.oum_user_pk = ocd.ocd_user_fk AND ocd_user_fk=?";

	String CHECK_CANDIDATE_IMAGES_EXISTS = "SELECT COUNT(1) image_count FROM oes_candidate_images WHERE oci_user_fk = ? AND oci_status='A'";

	String UPDATE_REG_DATE = "UPDATE oes_candidate_details SET ocd_reg_conf = CURRENT_TIMESTAMP WHERE ocd_user_fk = ?";

	String INSERT_CANDIDATE_SIGNATURE = "INSERT INTO oes_candidate_images(oci_candidate_image_pk, oci_user_fk, oci_sign_image, oci_status, oci_created_by,"
			+ " oci_created_date, oci_sign_image_path) VALUES (nextval('OES_CANDIDATE_IMAGES_SEQ'),?,?,'A',?,CURRENT_TIMESTAMP,?) ";

	String UPDATE_CANDIDATE_SIGNATURE = "UPDATE oes_candidate_images SET oci_sign_image= ?, oci_updated_by=?, oci_updated_date=CURRENT_TIMESTAMP,"
			+ " oci_sign_image_path =? WHERE oci_user_fk = ? ";

	String GET_CANDIDATE_SIGNATURE = " Rselect OCI_SIGN_IMAGE, OCI_CANDIDATE_IMAGE_PK " + " from OES_CANDIDATE_IMAGES  " + " where OCI_STATUS='A' AND OCI_USER_FK=? ";

	String CHECK_CANDIDATE_SIGNATURE_EXISTS = "SELECT COUNT(1) IMAGE_COUNT FROM OES_CANDIDATE_IMAGES WHERE OCI_USER_FK = ? AND OCI_STATUS='A' ";

	String UPDATE_CANDIDATE_POST = "UPDATE OES_CANDIDATE_DETAILS " + "SET OCD_TEST_FK = ? " + "WHERE OCD_USER_FK = ?";

	String INSERT_CANDIDATE_DOCUMENT = "INSERT INTO OES_CANDIDATE_DOC ( OCD_CAND_DOC_PK, OCD_USER_FK,OCD_WRKDOC_ID,OCD_DOC_FILE_NAME,"
			+ " OCD_DOCUMENT,OCD_FLAG, OCD_STATUS, OCD_CREATED_BY, OCD_CREATED_DATE,ocd_checkbox)"
			+ "VALUES ( nextval('OES_CANDIDATE_DOC_SEQ'),?,? ,? ,?,?, 'A', ?,CURRENT_TIMESTAMP,?)";

	String DELETE_CANDIDATE_DOCS = "DELETE FROM OES_CANDIDATE_DOC WHERE OCD_USER_FK = ?";

	String GET_CANDIDATE_DOCUMENTS = "SELECT OCD_CAND_DOC_PK,OCD_FLAG,OCD_DOC_FILE_NAME,OCD_DOCUMENT,OCD_CHECKBOX, ocd_doc_verify_status FROM OES_CANDIDATE_DOC WHERE OCD_USER_FK = ? AND OCD_STATUS='A'";
	String GET_CANDIDATE_DOCUMENT = "SELECT OCD_CAND_DOC_PK,OCD_FLAG,OCD_DOC_FILE_NAME,OCD_DOCUMENT FROM OES_CANDIDATE_DOC WHERE OCD_CAND_DOC_PK = ? AND OCD_STATUS='A'";
	String IS_CANDIDATE_EXIST_FOR_DOC = "SELECT OCD_CAND_DOC_PK FROM OES_CANDIDATE_DOC WHERE OCD_USER_FK = ? AND OCD_FLAG = ?";

	String UPDATE_CANDIDATE_DOCUMENT = "UPDATE OES_CANDIDATE_DOC SET OCD_DOC_FILE_NAME = ?,OCD_WRKDOC_ID =?, OCD_DOCUMENT = ?,"
			+ " OCD_UPDATED_BY = ? ,OCD_UPDATED_DATE = CURRENT_TIMESTAMP,OCD_CHECKBOX=? WHERE OCD_CAND_DOC_PK = ? ";

	String UPDATE_CANDIDATE_DOCUMENTS = "UPDATE OES_CANDIDATE_DOC SET OCD_UPDATED_BY = ? ,OCD_UPDATED_DATE = CURRENT_TIMESTAMP WHERE OCD_CAND_DOC_PK = ? ";

	String GET_PAYMENT_DTLS_BASED_ON_USER_ID = "SELECT OCD_CAND_TITLE, OCD_FIRST_NAME, OCD_MIDDLE_NAME, OCD_LAST_NAME, OES_PAYMENT_DTL_PK, pm.OPTM_PAYMENT_DESC, opd_transaction_no, "
			+ "to_char(CAST(opd_transaction_date as DATE),'DD-MON-YYYY') transactiondate , " + "opd_dd_challan_receipt_no, trim(to_char(ofm.ofm_fees ,'9999999.99')) AS opd_amount "
			+ "FROM oes_payment_details pd, oes_candidate_details cd,oes_fees_master ofm, OES_PAYMENT_MASTER pm " + "WHERE pd.opd_user_fk = cd.ocd_user_fk "
			+ "AND ofm.ofm_test_fk = cd.ocd_test_fk " + "AND pm.OPTM_PAYMENT_PK = pd.OPD_PAYMENT_FK " + "AND ofm.ofm_category_fk = cd.ocd_category_fk "
			+ " and OPD_VALIDATED_STATUS='A' " + "AND pd.opd_user_fk = ?";

	String GET_USER_ID = "SELECT OCD_USER_FK FROM OES_CANDIDATE_DETAILS WHERE OCD_REGISTRATION_ID = ?";

	String GET_USER_PK = "SELECT OUM_USER_PK FROM OES_USER_MASTER WHERE OUM_USER_ID = ?";

	String GET_USER_NAME = "SELECT OUM_USER_ID FROM OES_USER_MASTER WHERE OUM_USER_PK = ?";

	String CHECK_OFFLINE_PAYMENT_EXISTS = "SELECT COUNT(1) FROM OES_PAYMENT_DETAILS WHERE OPD_USER_FK = ? ";

	String INSERT_OFFLINE_PAYMENT_DETAILS = "INSERT INTO OES_PAYMENT_DETAILS ( OES_PAYMENT_DTL_PK, " + "OPD_PAYMENT_MODE, OPD_DD_DATE, OPD_USER_FK, OPD_STATUS, "
			+ "OPD_DD_CHALLAN_RECEIPT_NO, OPD_BANK_NAME, OPD_BRANCH_NAME, " + "OPD_BANK_CODE, OPD_BRANCH_CODE, OPD_CITY, OPD_CREATED_BY, OPD_CREATED_DATE) "
			+ "VALUES ( OES_PAYMENT_DETAILS_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

	String GET_OFFLINE_PAYMENT_PK = "SELECT OES_PAYMENT_DTL_PK FROM OES_PAYMENT_DETAILS WHERE OPD_USER_FK = ? ";

	String UPDATE_OFFLINE_PAYMENT_DETAILS = "UPDATE OES_PAYMENT_DETAILS SET OPD_PAYMENT_MODE = ?, " + "OPD_DD_DATE = ?, OPD_DD_CHALLAN_RECEIPT_NO = ?, "
			+ "OPD_BANK_NAME = ?, OPD_BRANCH_NAME = ?, OPD_BANK_CODE = ?, " + "OPD_BRANCH_CODE = ?, OPD_CITY=?, OPD_UPDATED_BY = ?, "
			+ "OPD_UPDATED_DATE = CURRENT_TIMESTAMP WHERE OES_PAYMENT_DTL_PK = ?";

	String UPDATE_USER_DETAILS = "UPDATE oes_user_master SET oum_test_fk=CAST(? AS INT), oum_email_id=pgp_sym_encrypt(?, ?,'cipher-algo=aes256'),"
			+ " oum_mobile_no=pgp_sym_encrypt(?, ?,'cipher-algo=aes256'), oum_updated_date=CURRENT_TIMESTAMP, oum_updated_by=? WHERE oum_user_pk=?";

	String UPDATE_OES_PAYMENT_DETAILS = "UPDATE OES_PAYMENT_DETAILS SET OPD_RECONCILE_FLAG = 'Y', OPD_VALIDATED_STATUS = 'A' WHERE OPD_DD_CHALLAN_RECEIPT_NO = ? AND OPD_PAYMENT_FK = ?";

	String UPDATE_OES_PAYMENT_DETAILS_FOR_REJECT = "UPDATE OES_PAYMENT_DETAILS SET OPD_RECONCILE_FLAG = 'N', OPD_VALIDATED_STATUS = 'R' WHERE OPD_DD_CHALLAN_RECEIPT_NO = ? AND OPD_PAYMENT_FK = ?";

	String UPDATE_CANDIDATE_STATUS = "UPDATE OES_CANDIDATE_DETAILS SET OCD_STATUS_ID_FK = ? WHERE OCD_USER_FK = ?";

	String INSERT_CANDIDATE_ACADEMIC_DETAILS = "INSERT INTO OES_ACDM_CAND_DETAILS ( oacd_acdm_pk, oacd_degree_fk, oacd_user_fk, oacd_acdm_fk,"
			+ " oacd_university, oacd_university_other, oacd_prd_of_study_from, oacd_prd_of_study_to, oacd_duration_of_study, oacd_institution, oacd_year_of_passing,"
			+ " oacd_percentage, oacd_dipmarks_yesno, oacd_total_marks_agg, oacd_obtained_marks_agg, oacd_med_of_inst, oacd_tamil_lang,"
			+ " oacd_ugdeg_yesno, oacd_pgdeg_yesno, oacd_specialization, oacd_yesno_pgdip, oacd_specialization_pgdip, oacd_dateofpass_pgdip,"
			+ " oacd_pstm_pref, oacd_tamil_medium, oacd_tamil_medium_ug, oacd_created_by, oacd_created_date) "
			+ " VALUES (nextval('OES_ACDM_CAND_DETAILS_SEQ'),?,?,?,"
			+ " ?,?,?,?,?,?,?,"
			+ " ?,?,?,?,?,?,"
			+ " ?,?,?,?,?,?,"
			+ " ?,?,?,?,CURRENT_TIMESTAMP)";

	String UPDATE_CANDIDATE_ACADEMIC_DETAILS = "UPDATE OES_ACDM_CAND_DETAILS  SET    OACD_SPECIALIZATION  = ?,      "
			+ " OACD_YEAR_OF_PASSING = ?,       OACD_UNIVERSITY      = ?,      " + " OACD_ACTUAL_MARKS    = ?,       OACD_MAX_MARKS       = ?,       OACD_PERCENTAGE      = ?,     "
			+ "  OACD_UPDATED_BY      = ?,       OACD_UPDATED_DATE    = CURRENT_TIMESTAMP,oacd_part_full_time=?,   "
			+ "  OACD_MONTH_OF_PASSING=? ,oacd_exam_name_fk=?  where  OACD_USER_FK         = ?";

	String CHECK_ACADEMIC_DETAILS = "select count (*) from OES_ACDM_CAND_DETAILS where OACD_USER_FK = ?";

	String GET_ACADEMIC_DETAILS_FOR_CANDIATE = "SELECT oacd_acdm_fk, oacd_result_status, oacd_year_of_passing"
			+ " FROM oes_acdm_cand_details WHERE oacd_acdm_fk = 3 AND oacd_user_fk = ?";

	String DELETE_ACADEMIC_DETAILS_ENTRY = " delete from oes_acdm_cand_details oacd where oacd.OACD_USER_FK = ?";

	String GET_CANDIATE_ACADEMIC_DETAILS_USER_ID = "SELECT * FROM oes_acdm_cand_details WHERE oacd_user_fk = ?";

	String GET_EDU_DETAILS_FOR_FINAL_PRINT_PAGE = "SELECT oacd.oacd_acdm_fk,"
			+ " (SELECT oum_university_desc FROM oes_university_master WHERE oum_university_pk::varchar = oacd.oacd_university) AS oacd_university, oacd.oacd_university_other,"
			+ " oacd.oacd_prd_of_study_from, oacd.oacd_prd_of_study_to, oacd.oacd_duration_of_study, oacd.oacd_institution, oacd.oacd_year_of_passing,"
			+ " (SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar = oacd.oacd_dipmarks_yesno) AS oacd_dipmarks_yesno,"
			+ " oacd.oacd_total_marks_agg, oacd.oacd_obtained_marks_agg, oacd.oacd_percentage,"
			+ " (SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar = oacd.oacd_med_of_inst) AS oacd_med_of_inst,"
			+ " (SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar = oacd.oacd_tamil_lang) AS oacd_tamil_lang,"
			+ " (SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar = oacd.oacd_ugdeg_yesno) AS oacd_ugdeg_yesno,"
			+ " (SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar = oacd.oacd_pgdeg_yesno) AS oacd_pgdeg_yesno,"
			+ " oacd.oacd_specialization, "
			+ " (SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar = oacd.oacd_yesno_pgdip) AS oacd_yesno_pgdip,"
			+ " oacd.oacd_specialization_pgdip, oacd.oacd_dateofpass_pgdip,"
			+ " (SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar = oacd.oacd_pstm_pref) AS oacd_pstm_pref,"
			+ " (SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar = oacd.oacd_tamil_medium) AS oacd_tamil_medium,"
			+ " (SELECT orvm_reference_value FROM oes_reference_value_master WHERE orvm_reference_pk::varchar = oacd.oacd_tamil_medium_ug) AS oacd_tamil_medium_ug"
			+ " FROM oes_acdm_cand_details oacd WHERE oacd.oacd_user_fk = ? order by oacd.oacd_acdm_fk";

	String GET_EDU_DETAILS_FOR_FINAL_PRINT_PAGE_ENC = "SELECT *," + "osm1.osm_state_name as oacd_state_fk, " + "			 opsr1.opsr_sub_rank as oacd_district_fk "
			+ "			  FROM  oes_acdm_cand_details oacd " + " LEFT OUTER JOIN oes_state_master osm1 ON (osm1.osm_state_pk::varchar=oacd.oacd_state) "
			+ "LEFT OUTER JOIN oes_posting_sub_ranks opsr1 ON (opsr1.opsr_opsm_fk::varchar=oacd.oacd_district::varchar) "
			+ "			where oacd_user_fk = ? order by oacd_acdm_fk";

	String GET_POST_GRADUATE_DETAILS_FOR_FINAL_PRINT_PAGE_FOR_USER_ID = "  SELECT oacd.oacd_degree_fk, oacd.oacd_acdm_fk, oacd.oacd_actual_marks, "
			+ "   oacd.oacd_max_marks, oacd.oacd_percentage, oacd.oacd_specialization, "
			+ "  oacd.oacd_university, oacd.oacd_year_of_passing,oacd.oacd_month_of_passing,oacd.OACD_RESULT_STATUS FROM oes_user_master gum,"
			+ "  oes_acdm_cand_details oacd WHERE gum.oum_user_pk = oacd.oacd_user_fk " + "  AND gum.oum_user_ID = ?";

	String UPDATE_VALIDATE_STATUS = "update oes_candidate_details set   OCD_VALIDATED_STATUS = null ,OCD_FORM_SUBMITED_DATE = CURRENT_DATE where OCD_USER_FK = ?";

	String GET_VALIDATE_STATUS = "select ocd.OCD_VALIDATED_STATUS from oes_candidate_details ocd where OCD_USER_FK = ?";
	String GET_VALIDATE_STATUS_ENC1 = "SELECT ocd.ocd_validated_status FROM oes_candidate_details ocd WHERE ocd_user_fk = ?";

	String TEST_CENTER_NAME = "SELECT OTCM_TEST_CENTRE_NAME FROM OES_TEST_CENTER_MASTER " + "WHERE OTCM_TEST_CENTRE_CODE = ?";

	String SELECT_CANDIDATE_NAME = "SELECT (ocd_first_name || case when ocd_middle_name is not null and ocd_middle_name<>'' then ' ' ||ocd_middle_name else '' end || ' ' || ocd_last_name) as candidate_name "
			+ "FROM OES_CANDIDATE_DETAILS " + "WHERE OCD_USER_FK = ?"; // ocd_cand_title

	String CHECK_TEST_CENTER = "select count(*) from oes_test_center_master where otcm_test_centre_code = ?";

	String UPDATE_CANDIDATE = " UPDATE OES_CANDIDATE_DETAILS " + " SET  OCD_CANDIDATE_BIRTH_PLACE = ?," + " OCD_CAND_FATHER_FIRST_NAME = ?," + " OCD_CAND_FATHER_MIDDLE_NAME = ?,"
			+ " OCD_CAND_FATHER_LAST_NAME = ?," + " OCD_FATHER_BIRTH_PLACE = ?, " + " OCD_CAND_MOTHER_FIRST_NAME = ?," + " OCD_CAND_MOTHER_MIDDLE_NAME = ?,"
			+ " OCD_CAND_MOTHER_LAST_NAME = ?," + " OCD_MOTHER_TONGUE = ?, " + " OCD_NATIONALITY_FK = ?, " + " OCD_GENDER_FK = ?,  " + " OCD_MARITAL_STATUS= ?, "
			+ " OCD_RELIGION_BELIEF= ?," + " OCD_MOBILE_NO = ?," + " OCD_PHONE_NUMERIC = ?,  " + " OCD_EMAIL_ID = ?, " + " OCD_IS_HANDICAPED = ?, " + " OCD_EX_SERVICEMAN= ? ,"
			+ " OCD_DEPARTMENTAL_CANDIDATE=?," + " OCD_CATEGORY_FK = ?,   " + " OCD_UPDATED_BY = ?," + " OCD_UPDATED_DATE = CURRENT_TIMESTAMP " + " WHERE  OCD_USER_FK = ? ";

	String SELECT_EDUCATION_LIST = "select oacd_acdm_fk from oes_acdm_cand_details where oacd_user_fk=?";
	
	String GET_EDU_DETAILS_UPLOAD_DOC = "SELECT oacd_acdm_fk, oacd_ugdeg_yesno, oacd_pgdeg_yesno, oacd_yesno_pgdip, oacd_tamil_medium_ug FROM oes_acdm_cand_details"
			+ " WHERE oacd_user_fk=? ORDER BY oacd_acdm_fk ASC";
	
	String SELECT_EXAMINATION_LIST = "select oatm_acdm_pk,oatm_acdm_code from oes_acdm_type_master where oatm_acdm_pk not in(6,7,8) order by oatm_acdm_pk";

	String SELECT_DEGREE_LIST = "select osdm_sub_degree_pk,osdm_sub_degree_desc from oes_sub_degree_master where osdm_acdm_fk=3 and osdm_sub_status='A' order by osdm_sub_degree_pk ";

	String SELECT_DIPLOMA_LIST = "select osdm_sub_degree_pk,osdm_sub_degree_desc from oes_sub_degree_master where osdm_acdm_fk=4 and osdm_sub_status='A' order by osdm_sub_degree_pk ";

	String SELECT_DIPLOMA_LAW_LIST = "select osdm_sub_degree_pk,osdm_sub_degree_desc from oes_sub_degree_master where osdm_acdm_fk=5 and osdm_sub_status='A' order by osdm_sub_degree_pk ";

	String SELECT_ADD_EXAMINATION_LIST = "select oatm_acdm_pk,oatm_acdm_desc from oes_acdm_type_master where oatm_acdm_pk not in(1,2,3,4,5)";

	String CHECK_ADDITIONAL_ACADEMIC_DETAILS = "select count (*) from oes_additional_acdm_cand_details where oaacd_user_fk = ?";

	String INSERT_CANDIDATE_ADDITIONAL_ACADEMIC_DETAILS = "INSERT INTO oes_additional_acdm_cand_details "
			+ " ( OAACD_ACDM_PK,OAACD_DEGREE_FK, OAACD_USER_FK, OAACD_ACDM_FK, oaacd_registration_no, oaacd_degree_subject_fk, oaacd_major_subject_fk, "
			+ " oaacd_marks_grade, oaacd_percentage, oaacd_year_of_passing, oaacd_no_of_attempts, oaacd_desig_authority, oaacd_month_of_passing, "
			+ " oaacd_part_full_time , OAACD_CREATED_BY,  OAACD_CREATED_DATE , oaacd_university,oaacd_abbr) "
			+ " VALUES (nextval('OES_ACDM_CAND_DETAILS_SEQ'),?,? ,? ,? ,?,? ,? ,?,? ,? ,?,?,?,?, CURRENT_TIMESTAMP ,?,?)";

	String DELETE_ADDITIONAL_ACADEMIC_DETAILS_ENTRY = " delete from oes_additional_education_details  where oaed_user_fk = ?";

	String SELECT_PG_LIST = "select osdm_sub_degree_pk,osdm_sub_degree_desc from oes_sub_degree_master where osdm_acdm_fk=6 and osdm_sub_status='A' order by osdm_sub_degree_pk ";

	String SELECT_SUB_GRUP_LIST = "select osgm_sub_grp_pk,osgm_sub_grp_desc from oes_subject_grp_master where osgm_sub_grp_status='A' order by osgm_sub_grp_pk ";

	String SELECT_SUB_GRUP_Main_SUB_LIST = "select osmsm_sub_main_pk,osmsm_sub_main_desc from oes_subject_main_sub_master where osmsm_sub_main_status='A' and osmsm_sub_grp_fk = CAST(? as INT)  order by osmsm_sub_main_pk ";

	String SELECT_SUB_GRUP_EQU_SUB_LIST = "select osesm_sub_equ_pk,osesm_sub_equ_desc from oes_subject_equivalent_sub_master where osesm_sub_equ_status='A' and (osesm_sub_main_fk = CAST(? as INT) OR osesm_sub_main_fk = 0)  order by osesm_sub_equ_pk ";

	String SELECT_SUB_GRUP_EQU_UNIVER_LIST = " select oum_university_pk, oum_university_desc from  oes_university_master um "
			+ "    where  um.oum_university_pk in (select osesm_university_fk from oes_subject_equivalent_sub_master "
			+ "    where osesm_sub_equ_desc =(select osesm_sub_equ_desc from oes_subject_equivalent_sub_master where osesm_sub_equ_pk=  CAST(? as INT) )) "
			+ "    and oum_status='A' ";

	// added for additional academic sravyav
	String GET_ADD_GRADUATE_DETAILS_FOR_FINAL_PRINT_PAGE = " SELECT oaed_doeacc, " // oaed_ocert_insti, oaed_ocert_issue_auth, oaed_ocert_num,oaed_ugc_degree,
			+ " oaed_terri_army, oaed_bcerti FROM oes_user_master gum, oes_additional_education_details WHERE gum.oum_user_pk = oaed_user_fk AND gum.oum_user_pk = ?";

	String GET_ADD_GRADUATE_DETAILS_FOR_FINAL_PRINT_PAGE_ENC = " SELECT oaed_doeacc, oaed_terri_army, oaed_bcerti FROM oes_user_master gum,"
			+ " oes_additional_education_details WHERE gum.oum_user_pk = oaed_user_fk AND gum.oum_user_pk = ?";

	String SELECT_ADD_PG_LIST = "select osdm_sub_degree_pk,osdm_sub_degree_desc from oes_sub_degree_master where osdm_acdm_fk=8 and osdm_sub_status='A' order by osdm_sub_degree_pk ";
	String SELECT_ADD_DEGREE_LIST = "select osdm_sub_degree_pk,osdm_sub_degree_desc from oes_sub_degree_master where osdm_acdm_fk=7 and osdm_sub_status='A' order by osdm_sub_degree_pk ";
	String SELECT_ADD_DIPLOMA_LIST = "select osdm_sub_degree_pk,osdm_sub_degree_desc from oes_sub_degree_master where osdm_acdm_fk=6 and osdm_sub_status='A' order by osdm_sub_degree_pk ";

	public String SELECT_CANDIDATE_QUOTA_DETAILS = " SELECT * FROM oes_candidate_quota_details " + " WHERE ocad_user_fk=? and otm_test_fk=?";

	String UPDATE_DOCUMENT_VERIFY_STATUS = "UPDATE oes_candidate_doc SET ocd_doc_verify_status= 'Confirmed', ocd_doc_verify_updated_date = CURRENT_TIMESTAMP "
			+ " WHERE ocd_cand_doc_pk = ? AND ocd_doc_file_name = ?";

	String DOC_COUNT_FILENAME = "select ( " + " SELECT count(*) " + " FROM oes_candidate_doc " + " WHERE ocd_user_fk = ? " + " AND ocd_checkbox = 'true' " + " ) as uploaded, "
			+ " ( " + " SELECT count(*) " + " FROM oes_candidate_doc  " + " WHERE ocd_user_fk = ? " + " AND ocd_doc_verify_status = 'Confirmed' " + " ) as confirmed, " + " ( "
			+ " SELECT string_agg(ocd_doc_file_name, ',') " + " FROM oes_candidate_doc  " + " WHERE ocd_user_fk = ? " + " AND ocd_doc_verify_status IS NULL " + " ) as filenames ";

	String DELETE_CANDIDATE_ADDITIONAL_DOCUMENT = "Delete from  oes_candidate_additional_doc WHERE ocad_flag in (?) AND ocad_user_fk = ?  AND ocad_test_fk=? ";

	String GET_CANDIDATE_ADDITIONAL_DOCUMENT = "SELECT ocad_cand_doc_pk,ocad_flag,ocad_doc_file_name,ocad_document FROM oes_candidate_additional_doc WHERE ocad_cand_doc_pk = ? AND ocad_status='A'";

	String GET_CANDIDATE_CRIME_DETAILS = "SELECT * FROM oes_cand_crime_details WHERE occd_user_fk=?";

	String UPDATE_ADD_DOCUMENT_VERIFY_STATUS = "UPDATE oes_candidate_additional_doc set " + " ocad_doc_verify_status= 'Confirmed' , ocad_doc_verify_updated_date=CURRENT_TIMESTAMP "
			+ " where ocad_cand_doc_pk = ? and ocad_doc_file_name=? ";

	String ADD_DOC_COUNT_FILENAME = "select ( " + " SELECT count(*) " + " FROM oes_candidate_additional_doc " + " WHERE ocad_user_fk = ? " + " AND ocad_checkbox = 'true' "
			+ " ) as uploaded, " + " ( " + " SELECT count(*) " + " FROM oes_candidate_additional_doc  " + " WHERE ocad_user_fk = ? " + " AND ocad_doc_verify_status = 'Confirmed' "
			+ " ) as confirmed, " + " ( " + " SELECT string_agg(ocad_doc_file_name, ',') " + " FROM oes_candidate_additional_doc  " + " WHERE ocad_user_fk = ? "
			+ " AND ocad_doc_verify_status IS NULL " + " ) as filenames ";

	String GET_CANDIDATE_COURSE_APPLIED = "SELECT" 
			+ "    otm_test_name," 
			+ "    CASE " 
			+ "        WHEN opd_validated_status IS NULL AND opd_status='N' THEN 'Payment Pending' "
			+ "        WHEN opd_validated_status = 'A' THEN 'Payment Approved' "
			+ "        WHEN opd_error_desc LIKE '%PLEASE DO NOT RE-INITIATE TRANSACTION%' THEN 'Payment Under Process' "
			+ "        WHEN opd_validated_status = 'R' THEN 'Payment Rejected' " + "        ELSE 'Payment Pending' " + "    END AS payment_status," + "    CASE "
			+ "        WHEN opd_validated_status = 'A' THEN '10'" + "        WHEN opd_validated_status = 'R' THEN '11' " + "        ELSE '20' " + "    END AS stage_update,"
			+ "    opd_txn_type," + "    opd_payment_fk," + "    ocd_discipline" + "FROM oes_candidate_details" + " INNER JOIN oes_test_master ON otm_test_pk = 1"
			+ "INNER JOIN oes_payment_details ON (ocd_user_fk=opd_user_fk)" + "WHERE ocd_user_fk = ?" + "GROUP BY ocd_user_fk," + "    ocd_discipline,"
			+ "    opd_validated_status," + "    opd_txn_type," + "    opd_error_desc ," + "    opd_payment_fk," + "    opd_status," + "    otm_test_name";

	String GET_CANDIDATE_COURSE_APPLIED_ENC1 = "SELECT otm_test_name, "
			+ "    CASE "
			+ "        WHEN opd_validated_status IS NULL AND opd_status='N' THEN 'Payment Pending' "
			+ "        WHEN opd_validated_status = 'A' THEN 'Payment Approved' "
			+ "        WHEN opd_error_desc LIKE '%PLEASE DO NOT RE-INITIATE TRANSACTION%' THEN 'Payment Under Process' "
			+ "        WHEN opd_validated_status = 'R' THEN 'Payment Rejected' "
			+ "        ELSE 'Payment Pending' "
			+ "    END AS payment_status, "
			+ "    CASE "
			+ "        WHEN opd_validated_status = 'A' THEN '10' "
			+ "        WHEN opd_validated_status = 'R' THEN '11' "
			+ "        ELSE '20' "
			+ "    END AS stage_update, "
			+ "    opd_txn_type, "
			+ "    opd_payment_fk, "
			+ "    ocd_discipline "
			+ "FROM oes_candidate_details "
			+ "INNER JOIN oes_test_master ON otm_test_pk = 1 "
			+ "INNER JOIN oes_payment_details ON (ocd_user_fk=opd_user_fk) "
			+ "WHERE ocd_user_fk = ? "
			+ "GROUP BY ocd_user_fk, "
			+ "    ocd_discipline, "
			+ "    opd_validated_status, "
			+ "    opd_txn_type, "
			+ "    opd_error_desc, "
			+ "    opd_payment_fk, "
			+ "    opd_status, "
			+ "    otm_test_name";

	String GET_CANDIDATE_Already_Applied_POST_NOT_VERIFIED = "select count(*) from oes_candidate_job_mapping where OCJM_USER_FK = ? and OCJM_TEST_FK = ? and ocjm_flag=1";
	String UPDATE_CANDIDATE_POST_STAGE = "UPDATE oes_candidate_job_mapping " + "SET ocjm_flag = 1 , ocjm_applicationNumber=? " + " where OCJM_USER_FK = ? and OCJM_TEST_FK = ? ";

	String GET_APP_NUMBER = "SELECT " + "	 case when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=0 then '0000000000' "
			+ "	 when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=1 then ocjm_test_fk::character varying||'000000000'||ocjm_user_fk::character varying "
			+ "	 when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=2 then ocjm_test_fk::character varying||'00000000'||ocjm_user_fk::character varying "
			+ "	 when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=3 then ocjm_test_fk::character varying||'0000000'||ocjm_user_fk::character varying "
			+ "	 when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=4 then ocjm_test_fk::character varying||'000000'||ocjm_user_fk::character varying "
			+ "	 when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=5 then ocjm_test_fk::character varying||'00000'||ocjm_user_fk::character varying "
			+ "	 when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=6 then ocjm_test_fk::character varying||'0000'||ocjm_user_fk::character varying "
			+ "	 when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=7 then ocjm_test_fk::character varying||'000'||ocjm_user_fk::character varying "
			+ "	 when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=8 then ocjm_test_fk::character varying||'00'||ocjm_user_fk::character varying "
			+ "	 when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=9 then ocjm_test_fk::character varying||'0'||ocjm_user_fk::character varying "
			+ "	 when length(ocjm_test_fk::character varying||ocjm_user_fk::character varying)=10 then ocjm_test_fk::character varying||ocjm_user_fk::character varying "
			+ "	 end as applicationNumber " + "	 FROM oes_candidate_job_mapping "
			+ "     WHERE ocjm_user_fk =?  and  ((case when ? is not null then ocjm_test_fk =?  end)  or (case when ?  is  null then 1 = 1 end))";

	// epost reconciliation
	String CHECK_EPOST_DUPLICATE_ENTRY = " SELECT COUNT(1) FROM oes_epost_rec WHERE rec_apl_no = ? AND rec_trans_ser=?";

	String INSERT_INTO_OES_EPOST = "INSERT INTO oes_epost_rec (epost_rec_pk, rec_pst_ofc , rec_pincode ,rec_cand_name , rec_apl_no , rec_advt_no ,"
			+ " rec_acc_dt ,rec_fees ,rec_rect_no , rec_trsc_dt ,rec_trans_ser ,reconcile_flag ,rec_created_dt) VALUES (nextval('epostSeq') ,? ,?,? ,?,? ,? ,?,? ,? ,?,'N' ,current_timestamp)";

	String CHECK_APPLICATION_NO_EXIST = "select ocjm_applicationnumber from oes_candidate_job_mapping where ocjm_applicationnumber=?";

	String IS_APP_E_POST_RECONCILE = "SELECT count(*) FROM oes_epost_rec WHERE rec_apl_no = ? and reconcile_flag='Y'";

	String GET_APP_NO_DETAILS = "select ocjm_user_fk ,ocjm_test_fk  from oes_candidate_job_mapping  LEFT JOIN oes_user_master on oum_user_pk=ocjm_user_fk where ocjm_applicationnumber=?";

	String INSERT_E_POST_PAYEMENT_DETAILS = " INSERT INTO oes_payment_details( " + " oes_payment_dtl_pk, opd_payment_fk, opd_user_fk, opd_transaction_date, opd_transaction_no, "
			+ " opd_currency, opd_status,opd_validated_status, opd_created_by, opd_created_date, opd_test_fk, opd_app_no) "
			+ " VALUES (NEXTVAL('OES_PAYMENT_DETAILS_SEQ'), ?,? ,CURRENT_TIMESTAMP, ?, ?, ?, ?,?, CURRENT_TIMESTAMP, ?, ?)";

	String SELECT_CANDIDATE_NAME_DETAILS = "SELECT pgp_sym_decrypt(oum_first_name::bytea,?,'cipher-algo=aes256') AS oum_first_name,"
			+ " oum_middle_name, oum_last_name FROM oes_user_master WHERE oum_user_pk=? ";

	String CANDIDATE_DOB = "select OCD_DATE_OF_BIRTH  from oes_candidate_details where ocd_user_fk = ?";
	String GET_CANDIDATE_Already_Applied_POSTFORID = "select max(ocjm_test_fk) from oes_candidate_job_mapping where OCJM_USER_FK = ? and ocjm_flag=1";

	String getCandidateChallanDownloadedDate = "select  TO_CHAR(opd_created_date, 'dd-MM-yyyy')  from oes_payment_details where opd_user_fk = ? and opd_payment_fk=5";

	String GET_CAND_STATUS_ID = "select count(*) from  oes_candidate_details where ocd_status_id_fk>=5 and ocd_user_fk=?";

	String GET_JSON_PDF1 = " select ocpj_json_string from  oes_candidate_pdf_json where ocpj_user_fk = ? and ocpj_test_fk=? ";

	String INSER_JSON_PDF1 = "INSERT INTO oes_candidate_pdf_json(" + "           ocpj_pk, ocpj_user_fk, ocpj_test_fk, ocpj_json_string, ocpj_created_by,"
			+ "           ocpj_created_dt, ocjp_pdf_flag)" + "   VALUES (nextval('oes_candidate_pdf_json_ocpj_pk_seq'),?, ?,?,?, " + "           now(), 'Y')";

	String GET_PAYMENT_STATUS_FOR_PDF_APPLICATION_NUMBER = "select opd_validated_status from oes_payment_details where opd_user_fk = ?";// and

	String GET_CANDIDATE_PHOTO_NAME = "select oci_photo_image_path from oes_candidate_images where oci_user_fk = ?";

	String GET_CANDIDATE_SIGNATURE_NAME = "select oci_sign_image_path from oes_candidate_images where oci_user_fk = ?";

	String UPDATE_CANDIDATE_ATTEMPT_COUNT = "update OES_CANDIDATE_DETAILS set OCD_ATTEMPT_COUNT = OCD_ATTEMPT_COUNT+1 where OCD_USER_FK = ?";

	String SELECT_ADD_DEGREE_EDU_LIST = "select osdm_sub_degree_pk,osdm_sub_degree_desc from oes_sub_degree_master where osdm_acdm_fk=5 and osdm_sub_status='A' order by osdm_sub_degree_pk";
	String UPDATE_CALL_LETTER_STATUS = "update oes_candidate_details set ocd_call_letter_count =  COALESCE(ocd_call_letter_count,0)+1 where  ocd_user_fk =  ?";
	String UPDATE_ADMIT_STATUS = "update oes_candidate_details set ocd_admit_card_count =  COALESCE(ocd_admit_card_count,0)+1 where  ocd_user_fk =  ?";
	String UPDATE_SCORE_STATUS = "update oes_candidate_details set ocd_score_card_count =  COALESCE(ocd_score_card_count,0)+1 where  ocd_user_fk =  ?";
	String UPDATE_UPLOAD_IMAGE_STATUS = "update oes_user_master set oum_photo_sign_status = 'A' where oum_user_id = ?";
	String UPDATE_UPLOAD_SIGN_STATUS = "update oes_user_master set oum_sign_status = 'B' where oum_user_id = ?";
	String UPDATE_PRACTICE_TEST_STATUS = "update oes_candidate_details set ocd_practice_test_count =  COALESCE(ocd_practice_test_count,0)+1 where  ocd_user_fk =  ?";

	String INSERT_ADDITIONAL_EDUCATION_DETAILS = "INSERT INTO oes_additional_education_details(oaed_pk,oaed_user_fk,oaed_doeacc,oaed_terri_army,oaed_bcerti,"
			+ "oaed_created_by,oaed_created_date) VALUES (nextval('OES_ADDITIONAL_EDUCATION_DETAILS_SEQ'),?,?,?,?,?,CURRENT_TIMESTAMP)";

	String SELECT_ADDITIONAL_EDUCATION_DETAILS = "select * from oes_additional_education_details where OAED_USER_FK = ?";
	String SELECT_REG_ADDITIONAL_EDUCATION_LIST = "select osmsm_sub_main_pk,osmsm_sub_main_desc from oes_subject_main_sub_master where osmsm_sub_main_status = 'A'";
	// Added for age matrix starts
	String GET_AGE_MATRIX = "SELECT MAXAGE_YRS,MINDATE AS" + "START_DATE FROM OES_AGE_RULES WHERE " + "CATEGORY =? and ISESM=? and ISUPGOVTEMP=?  ";
	// Added for age matrix ends
	String CHECK_OTP_EXIST_IN_MASTER = "Select count(0) from oes_mob_otp_master where omom_to_address = ?";

	String GET_WORK_EXPERIENCE_DETAILS_FOR_CANDIDATE = "SELECT * FROM oes_work_experience WHERE owe_user_fk=?";

	String GET_WORK_EXP_ADD_DETAILS = "SELECT * FROM oes_work_exp_add_details WHERE owead_user_fk = ? ";

	String IS_USER_EXIST_IN_WORK_EXP = "SELECT COUNT(1) FROM oes_work_experience WHERE owe_user_fk=?";

	String IS_USER_EXIST_IN_WORK_EXP_ADD_DETAILS = "SELECT COUNT(1) FROM oes_work_exp_add_details WHERE owead_user_fk=?";

	String DELETE_WORK_EXP_DETAILS = " DELETE FROM oes_work_experience WHERE owe_user_fk = ?";

	String DELETE_WORK_EXP_ADD_DETAILS = "DELETE FROM oes_work_exp_add_details WHERE owead_user_fk = ?";

	String SELECT_DISCIPLINE_AND_SUBJECT = "SELECT ocd_discipline, ocd_phdspecialization, ocd_shastrasubject, ocd_category_fk"
			+ " FROM oes_candidate_details WHERE ocd_user_fk=?";

	String INSERT_WORK_EXPERIENCE_DETAILS = "INSERT INTO oes_work_experience (owe_we_pk, owe_user_fk, owe_we_organisation, owe_role,"
			+ " owe_yoe_from,owe_current_company, owe_yoe_to, owe_total_experience, owe_last_salary, owe_job_description,"
			+ "owe_created_by, owe_created_date) VALUES (nextVal('oes_work_experience_seq'),?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";

	String INSERT_WORK_EXP_ADD_DETAILS = "INSERT INTO oes_work_exp_add_details (owead_pk, owead_user_fk, owead_total_exp_years, owead_total_exp_months, owead_total_exp_days,"
			+ "owead_created_by, owead_created_date) " + " VALUES (nextval('oes_work_exp_add_details_seq'),?,?,?,?," + "?,CURRENT_TIMESTAMP)";

	public String SELECT_CANDIDATE_ADDITIONAL_DETAILS = "SELECT * FROM oes_cand_additional_details WHERE ocad_user_fk=?";

	String CHECK_IF_ADDITIONAL_EXISTS = "SELECT COUNT(1) FROM oes_cand_additional_details WHERE ocad_user_fk = ?";

	String INSERT_CANDIDATE_ADDITIONAL_DETAILS = "INSERT INTO oes_cand_additional_details" + " (ocad_additional_pk, ocad_user_fk, ocad_academicaward, ocad_advertisement,"
			+ " ocad_appliedinpast, ocad_yearsofapply, ocad_reasonfornotjoining, ocad_stmtofpurpose, ocad_otherinfo,"
			+ " ocad_ref1name, ocad_ref1desig, ocad_ref1isacademician, ocad_ref1add1, ocad_ref1add2,"
			+ " ocad_ref1state, ocad_ref1district, ocad_ref1city, ocad_ref1pincode, ocad_ref1contact,ocad_ref1email,"
			+ " ocad_ref2name, ocad_ref2desig, ocad_ref2isacademician, ocad_ref2add1, ocad_ref2add2,"
			+ " ocad_ref2state, ocad_ref2district, ocad_ref2city, ocad_ref2pincode, ocad_ref2contact,ocad_ref2email)"
			+ " VALUES (nextval('oes_cand_additional_details_seq'),?,?,?,?," + " ?,?,?,?," + " ?,?,?,?,?," + " ?,?,?,?,?,?," + " ?,?,?,?,?," + " ?,?,?,?,?,?)";

	String UPDATE_CANDIDATE_ADDITIONAL_DETAILS = "UPDATE oes_cand_additional_details SET "
			+ " ocad_academicaward=?, ocad_advertisement=?, ocad_appliedinpast=?, ocad_yearsofapply=?, ocad_reasonfornotjoining=?,"
			+ " ocad_stmtofpurpose=?, ocad_otherinfo=?, ocad_ref1name=?, ocad_ref1desig=?, ocad_ref1isacademician=?, ocad_ref1add1=?, ocad_ref1add2=?,"
			+ " ocad_ref1state=?, ocad_ref1district=?, ocad_ref1city=?, ocad_ref1pincode=?, ocad_ref1contact=?, ocad_ref2name=?, ocad_ref2desig=?,"
			+ " ocad_ref2isacademician=?, ocad_ref2add1=?, ocad_ref2add2=?, ocad_ref2state=?, ocad_ref2district=?, ocad_ref2city=?, ocad_ref2pincode=?,"
			+ " ocad_ref2contact=?,ocad_ref1email=?,ocad_ref2email=? WHERE ocad_user_fk=? ";
	
	String IS_USER_EXIST_IN_COVID_DUTY_CERT = "SELECT COUNT(1) FROM oes_covid_duty_certificate WHERE ocdc_user_fk=?";

	String GET_COVID_DUTY_CERT_DETAILS = "SELECT * FROM oes_covid_duty_certificate WHERE ocdc_user_fk=?";

	String DELETE_COVID_DUTY_CERT_DETAILS = "DELETE FROM oes_covid_duty_certificate WHERE ocdc_user_fk = ?";

	String INSERT_COVID_DUTY_CERT_DETAILS = "INSERT INTO oes_covid_duty_certificate (ocdc_cdc_pk, ocdc_user_fk, ocdc_institution_type," //ocdc_work_in_covid_yesno,
			+ " ocdc_institution_name,ocdc_district, ocdc_institution_address, ocdc_period_of_work_from, ocdc_period_of_work_to, ocdc_duration_of_covid_service, ocdc_certificate_signed_by, ocdc_certi_counter_signed_by,"
			+ "ocdc_created_by, ocdc_created_date) VALUES (nextVal('oes_covid_duty_certificate_seq'),?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";

	String IS_USER_EXIST_IN_COVID_DUTY_CERT_ADD_DETAILS = "SELECT COUNT(1) FROM oes_covid_duty_certi_add_details WHERE ocdcad_user_fk = ?";

	String GET_COVID_DUTY_CERTI_ADD_DETAILS = "SELECT * FROM oes_covid_duty_certi_add_details WHERE ocdcad_user_fk = ? ";

	String DELETE_COVID_DUTY_CERTI_ADD_DETAILS = "DELETE FROM oes_covid_duty_certi_add_details WHERE ocdcad_user_fk = ? ";

	String INSERT_COVID_DUTY_CERTI_ADD_DETAILS = "INSERT INTO oes_covid_duty_certi_add_details (ocdcad_pk, ocdcad_user_fk, ocdcad_work_in_covid_yesno,"
			+ " ocdcad_years_of_total_service, ocdcad_months_of_total_service, ocdcad_days_of_total_service, ocdcad_total_wrkexp, ocdcad_created_by, ocdcad_created_date) "
			+ " VALUES (nextval('oes_work_exp_add_details_seq'),?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";

}
