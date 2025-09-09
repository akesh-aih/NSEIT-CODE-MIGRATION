package com.nseit.payment.queries;

public interface PaymentQueries {

	String BANK_DETAILS = "SELECT OBM_BANK_NAME,OBM_BRANCH_CODE FROM OES_BANK_MASTER WHERE OBM_STATUS='A'";
	/*
	 * String GET_FEES_DETAIL =
	 * "select trim(to_char(OFM_FEES ,'9999999.99')) as OFM_FEES from oes_candidate_details ocd, oes_fees_master ofm"
	 * + " where ocd.ocd_test_fk= ofm.ofm_test_fk " +
	 * " and ocd.ocd_category_fk= ofm.ofm_category_fk and OCD_USER_FK=?";
	 */
	String GET_FEES_DETAIL = "  select otm_fees_open from oes_test_master where otm_test_pk=?";

	String GET_DATE = " SELECT TO_CHAR(OTM_PAYMENT_START_DATE,'DD-MON-YYYY') as OTM_PAYMENT_START_DATE,TO_CHAR(OTM_PAYMENT_END_DATE,'DD-MON-YYYY') as OTM_PAYMENT_END_DATE FROM OES_TEST_MASTER OTM "
			+ " INNER JOIN OES_CANDIDATE_DETAILS ON OCD_TEST_FK=OTM_TEST_PK WHERE OCD_USER_FK=? AND OTM_STATUS='A';";

	/*
	 * String UPDATE_ONLINE_DETAILS =
	 * " UPDATE oes_payment_details SET OPD_DD_CHALLAN_RECEIPT_NO = ?, opd_dd_date = ?, "
	 * +
	 * " opd_validated_status = ?, opd_amount = ?, opd_bank_code = ?, opd_bank_ref_no =?,  opd_updated_by = ?, opd_updated_date = CURRENT_TIMESTAMP, "
	 * +
	 * " OPD_AUTHENTICATE_STATUS = ?, opd_status = ? , OPD_ERROR_DESC = ?, opd_txn_type = ?, opd_check_sum = ?, "
	 * +
	 * " opd_client_meta_data = ? ,opd_merchant_id = ?, opd_txn_ref_no = ?, OPD_OFFLINE_STATUS = ? "
	 * + " WHERE opd_user_fk = ? AND OPD_TRANSACTION_NO = ? ";
	 */

	String UPDATE_ONLINE_DETAILS = " UPDATE oes_payment_details SET opd_txn_date = ?, "
			+ " opd_validated_status = ?, opd_updated_by = ?, opd_updated_date = CURRENT_TIMESTAMP, "
			+ " opd_authenticate_status = ?, opd_status = ?, opd_error_desc = ?, opd_txn_type = ?,"
			+ " opd_bank_ref_no = ?, opd_txn_ref_no =?, opd_bank_name = ?, opd_reconcile_flag='Y', opd_txn_amount=? "
			+ " WHERE opd_user_fk = ? AND opd_transaction_no = ?";

	String UPDATE_ONLINE_DETAILS_TP = " UPDATE oes_payment_details SET OPD_DD_CHALLAN_RECEIPT_NO = ?, opd_dd_date = ?, "
			+ " opd_validated_status = ?, opd_amount = ? ,opd_bank_code = ?, opd_updated_by = ?, opd_updated_date = CURRENT_TIMESTAMP, "
			+ " OPD_AUTHENTICATE_STATUS = ?, opd_status = 'A', OPD_ERROR_DESC = ?, opd_txn_type = ?, opd_check_sum = ?, opd_client_meta_data = ? "
			+ " WHERE opd_user_fk = ? AND OPD_TRANSACTION_NO = ? ";
	String INSERT_TRANSACTION_NUMBER_IN_OPD = " INSERT INTO oes_payment_details(oes_payment_dtl_pk, opd_payment_fk, opd_user_fk,"
			+ " opd_transaction_date, opd_transaction_no, opd_currency, opd_status, opd_created_by, opd_created_date, opd_amount) "
			+ " VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

	String INSERT_TRANSACTION_NUMBER_IN_OPD1 = " INSERT INTO oes_payment_details( " + " oes_payment_dtl_pk, opd_payment_fk, opd_user_fk, opd_transaction_date, opd_transaction_no, "
			+ " opd_currency, opd_status, opd_created_by, opd_created_date, opd_test_fk,opd_amount) " + " VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?,?)";

	String UPDATE_TRANSACTION_NUMBER_IN_OPD = "Update oes_payment_details\r\n" + "set opd_transaction_date = CURRENT_TIMESTAMP\r\n"
			+ "WHERE opd_user_fk = ? AND OPD_TRANSACTION_NO = ?";

	String GET_EXISTING_TXN_NUMBER = "select opd_transaction_no as clientTransactionNo, '1' as defaultValue from oes_payment_details where opd_user_fk = ? and opd_payment_fk=?  and (opd_status = 'N' OR OPD_VALIDATED_STATUS='R') ";

	String GET_EXISTING_TXN_NUMBER1 = "select opd_transaction_no as clientTransactionNo, '1' as defaultValue from oes_payment_details where opd_user_fk = ? and opd_payment_fk=?  and (opd_status = 'N' OR OPD_VALIDATED_STATUS='R') ";

	String GET_JOB_NAME = "select otm_test_name from oes_candidate_job_mapping " + " inner join oes_test_master on otm_test_pk = ocjm_test_fk " + " where ocjm_user_fk = ? ";

	String UPDATE_PAYMENT_STATUS_IN_OCJM = "update oes_candidate_job_mapping set ocjm_payment_stts = 'A', ocjm_updated_by = ?, ocjm_updated_date = CURRENT_TIMESTAMP where ocjm_user_fk = ? and ocjm_payment_stts = 'N'";

	String GET_CANDIDATE_COURSE_APPLIED = " select otm_test_pk, otm_test_name from oes_candidate_job_mapping ocjm "
			+ " inner join oes_test_master otm on otm.otm_test_pk = ocjm.ocjm_test_fk " + " where ocjm_user_fk = ? and ocjm_flag=1 " + " and ocjm_test_fk=?::numeric"
			+ " and not exists (select 1 from oes_payment_details opd where opd.opd_user_fk = ocjm.ocjm_user_fk and opd_validated_status = 'A' and ocjm_test_fk = opd_test_fk) "
			+ " order by ocjm_sr_pk desc ";

	String GET_CANDIDATE_APPLICATION_DETAILS = " select ocjm_app_no, otm_test_name, ocjm_test_fk from oes_candidate_job_mapping ocjm "
			+ " inner join oes_test_master otm on otm.otm_test_pk = ocjm_test_fk " + " where ocjm_user_fk = ? "
			+ " and not exists (select 1 from oes_payment_details opd where opd.opd_user_fk = ocjm.ocjm_user_fk and opd_validated_status = 'A' and ocjm_test_fk = opd_test_fk) ";

	String GET_PENDING_PAYMENT_DETAILS = "select opd_user_fk,to_char(opd_transaction_date::timestamp,'yyyy-MM-dd') opd_transaction_date,"
			+ " opd_transaction_no, opd_created_by, opd_test_fk, opd_app_no, oum_mobile_no as opd_mobile_no,"
			+ " oum_email_id as opd_email_id,opd_amount  from oes_payment_details "
			+ "inner join oes_user_master oum on oum.oum_user_pk = opd_user_fk  where opd_validated_status is null"
			+ " and opd_transaction_date::timestamp <= (now()-interval '30 min') and opd_payment_fk = 1 ";
	
	String GET_PENDING_PAYMENT_DETAILS_ENC1 = "SELECT opd_user_fk, TO_CHAR(opd_transaction_date::TIMESTAMP,'yyyy-MM-dd') opd_transaction_date,"
			+ " opd_transaction_no, opd_created_by, opd_test_fk, opd_app_no,"
			+ " pgp_sym_decrypt(oum.oum_mobile_no::bytea,?,'cipher-algo=aes256') AS opd_mobile_no,"
			+ " pgp_sym_decrypt(oum.oum_email_id::bytea,?,'cipher-algo=aes256') AS opd_email_id,"
			+ " opd_amount FROM oes_payment_details INNER JOIN oes_user_master oum ON oum.oum_user_pk = opd_user_fk "
			+ " WHERE opd_validated_status IS null AND opd_transaction_date::TIMESTAMP <= (now()-interval '30 min') AND opd_payment_fk = 1 ";

	String GET_PENDING_PAYMENT_DETAILS_FOR_Check_STATUS = "SELECT opd_user_fk, TO_CHAR(opd_transaction_date::TIMESTAMP,'yyyy-MM-dd')"
			+ " opd_transaction_date, opd_validated_status, opd_transaction_no, opd_created_by, opd_test_fk, opd_app_no, opd_amount "
			+ "	FROM oes_payment_details WHERE opd_user_fk = ? AND opd_payment_fk = 1 ";

	String GET_TRANSACTION_DETAILS_FOR_SINGLE_OFFLINE_CALL = " select opd_user_fk,to_char(opd_transaction_date::timestamp,'dd-mm-yyyy') opd_transaction_date, opd_validated_status, opd_transaction_no, opd_created_by, opd_test_fk, opd_app_no, oum_mobile_no as opd_mobile_no, oum_email_id as opd_email_id "
			+ " from oes_payment_details " + " inner join oes_user_master oum on oum.oum_user_pk = opd_user_fk " + " where opd_transaction_no = ? " + " and opd_payment_fk = 1";

	String NEW_GET_TRANSACTION_DETAILS_FOR_SINGLE_OFFLINE_CALL = " select opd_user_fk,to_char(opd_transaction_date::timestamp,'dd-mm-yyyy') opd_transaction_date, opd_validated_status, opd_transaction_no, opd_created_by, opd_test_fk, opd_app_no, oum_mobile_no as opd_mobile_no, oum_email_id as opd_email_id "
			+ " from oes_payment_details " + " inner join oes_user_master oum on oum.oum_user_pk = opd_user_fk " + " where opd_created_by = ? " + " and opd_payment_fk = 1";
	// added by Prudhvi 17-09-2017
	String GET_APPLICANT_PROVODED_Info = "SELECT oum.oum_user_pk, oum.oum_user_id," + "oum.oum_mobile_no,opd.opd_app_no," + "opd.opd_transaction_no," + "opd.opd_validated_status"
			+ "  FROM oes_user_master oum" + " Inner join oes_payment_details opd " + " on oum.oum_user_pk=opd.opd_user_fk" + " and opd.opd_app_no=?"
			+ " order by opd_transaction_date desc";
	// added by Prudhvi 17-09-2017
	String GET_OPD_TXN_NUMBER = "select opd_transaction_no  from oes_payment_details where  opd_app_no = ?";
	/// changed by vijay to get Active Post
	String GET_CANDIDATE_JOBMOPPING_Info = "select  ocjm_app_no, ocjm_test_fk,  " + "	ocjm_applicationnumber from oes_candidate_job_mapping   "
			+ "	inner join oes_test_master on otm_test_pk = ocjm_test_fk" + "	 where ocjm_user_fk =? and otm_status = 'A' order by ocjm_test_fk desc";

	/*
	 * String UPDATE_OFFLINE_SUCCESS_RESPONSE =
	 * " update oes_payment_details set OPD_DD_CHALLAN_RECEIPT_NO = ?, opd_dd_date = ?, opd_validated_status = ?, opd_amount = ?, opd_bank_code = ?, "
	 * +
	 * " OPD_AUTHENTICATE_STATUS = ?, opd_status = 'A', OPD_ERROR_DESC = ?, opd_txn_type = ?, opd_offline_status = ? "
	 * + " WHERE OPD_TRANSACTION_NO = ? ";
	 */

	String UPDATE_OFFLINE_SUCCESS_RESPONSE = "update oes_payment_details set \r\n" + "OPD_DD_CHALLAN_RECEIPT_NO = ?,\r\n" + "OPD_TXN_TYPE=?,\r\n" + "opd_dd_date = ?, \r\n"
			+ "opd_validated_status = ?, \r\n" + "opd_bank_code = ?, \r\n" + "OPD_UPDATED_BY = OPD_CREATED_BY, \r\n" + "opd_updated_date = CURRENT_TIMESTAMP, \r\n"
			+ "opd_status = 'A', \r\n" + "OPD_ERROR_DESC = ?, \r\n" + "OPD_REMARKS = ?,opd_authenticate_status=? \r\n" + "WHERE OPD_TRANSACTION_NO = ?";

	String UPDATE_OFFLINE_RESPONSE = "update oes_payment_details set opd_txn_type = ?, OPD_AUTHENTICATE_STATUS = ?, OPD_ERROR_DESC = ?, opd_offline_status = ?,opd_validated_status=? where OPD_TRANSACTION_NO = ? ";

	String UPDATE_OFFLINE_RESPONSE_IN_HISTORY = "update OES_PAYMENT_DETAILS_HISTORY set opd_txn_type = ?, OPD_AUTHENTICATE_STATUS = ?, opd_updated_date = CURRENT_TIMESTAMP, "
			+ "OPD_ERROR_DESC = ?, OPD_REMARKS = ? , opd_validated_status = ? where OPD_TRANSACTION_NO = ? ";
	/*
	 * String GET_PENDING_BULK_PAYMENT_DETAILS_TP =
	 * " SELECT 'opd' OPD_TABLE_CHECK,OPD_USER_FK, TO_CHAR(TO_DATE(SUBSTR(OPD_CREATED_DATE,1,9), 'dd-MM-YY'), 'dd-MM-yyyy') as OPD_TRANSACTION_DATE, \r\n"
	 * +
	 * " OPD_TRANSACTION_NO, OPD_CREATED_BY,OPD_AMOUNT, OPD_TEST_FK, OPD_SUB_TEST_FK, OPD_APPLICATION_NO,OUM_USER_ID, OUM_MOBILE_NO AS OPD_MOBILE_NO, OUM_EMAIL_ID AS OPD_EMAIL_ID ,( EXTRACT(DAY FROM systimestamp - OPD_CREATED_DATE ) *24 + (EXTRACT(Hour FROM systimestamp - OPD_CREATED_DATE )) ) as opd_trax_pd_time \r\n"
	 * + " FROM OES_PAYMENT_DETAILS  \r\n" +
	 * " INNER JOIN OES_USER_MASTER OUM ON OUM.OUM_USER_PK = OPD_USER_FK \r\n" +
	 * " WHERE OPD_VALIDATED_STATUS IS NULL  AND  OPD_CREATED_DATE<(SYSTIMESTAMP - INTERVAL '30' MINUTE(1)) \r\n"
	 * + " AND OPD_PAYMENT_FK = 1 \r\n" + " UNION ALL  \r\n" +
	 * " SELECT 'opdh' OPD_TABLE_CHECK,OPD_USER_FK, TO_CHAR(TO_DATE(SUBSTR(OPD_CREATED_DATE,1,9), 'dd-MM-YY'), 'dd-MM-yyyy') as OPD_TRANSACTION_DATE,\r\n"
	 * +
	 * " OPD_TRANSACTION_NO, OPD_CREATED_BY,OPD_AMOUNT, OPD_TEST_FK, OPD_SUB_TEST_FK, OPD_APPLICATION_NO,OUM_USER_ID,  OUM_MOBILE_NO AS OPD_MOBILE_NO, OUM_EMAIL_ID AS OPD_EMAIL_ID ,( EXTRACT(DAY FROM systimestamp - OPD_CREATED_DATE ) *24 + (EXTRACT(Hour FROM systimestamp - OPD_CREATED_DATE )) ) as opd_trax_pd_time \r\n"
	 * + " FROM OES_PAYMENT_HISTORY  \r\n" +
	 * " INNER JOIN OES_USER_MASTER OUM ON OUM.OUM_USER_PK = OPD_USER_FK \r\n" +
	 * " WHERE OPD_VALIDATED_STATUS IS NULL  AND  OPD_CREATED_DATE<(SYSTIMESTAMP - INTERVAL '30' MINUTE(1)) \r\n"
	 * + " AND OPD_PAYMENT_FK = 1   \r\n" + " ORDER BY  OPD_TABLE_CHECK";
	 */
	String GET_PENDING_BULK_PAYMENT_DETAILS_TP = "SELECT 'opd' OPD_TABLE_CHECK,OPD_USER_FK, TO_CHAR(OPD_CREATED_DATE, 'dd-MM-yyyy') as OPD_TRANSACTION_DATE,\r\n"
			+ " OPD_TRANSACTION_NO, OPD_CREATED_BY,OPD_AMOUNT, OPD_TEST_FK, OUM_USER_ID, OUM_MOBILE_NO AS OPD_MOBILE_NO, OUM_EMAIL_ID AS OPD_EMAIL_ID,\r\n"
			+ "( EXTRACT(DAY FROM current_timestamp  - OPD_CREATED_DATE ) *24 + (EXTRACT(Hour FROM current_timestamp - OPD_CREATED_DATE )) ) as opd_trax_pd_time\r\n"
			+ " FROM oes_payment_details \r\n" + " INNER JOIN OES_USER_MASTER OUM ON OUM.OUM_USER_PK = OPD_USER_FK\r\n"
			+ " WHERE OPD_VALIDATED_STATUS IS NULL  AND  OPD_CREATED_DATE<(current_timestamp - INTERVAL '30 MINUTEs')\r\n" + " UNION ALL\r\n"
			+ " SELECT 'oph' OPD_TABLE_CHECK,OPD_USER_FK, TO_CHAR(OPD_CREATED_DATE, 'dd-MM-yyyy') as OPD_TRANSACTION_DATE,\r\n"
			+ " OPD_TRANSACTION_NO, OPD_CREATED_BY,OPD_AMOUNT, OPD_TEST_FK, OUM_USER_ID, OUM_MOBILE_NO AS OPD_MOBILE_NO, OUM_EMAIL_ID AS OPD_EMAIL_ID,\r\n"
			+ "( EXTRACT(DAY FROM current_timestamp  - OPD_CREATED_DATE ) *24 + (EXTRACT(Hour FROM current_timestamp - OPD_CREATED_DATE )) ) as opd_trax_pd_time\r\n"
			+ " FROM oes_payment_details_history \r\n" + " INNER JOIN OES_USER_MASTER OUM ON OUM.OUM_USER_PK = OPD_USER_FK\r\n"
			+ " WHERE OPD_VALIDATED_STATUS IS NULL  AND  OPD_CREATED_DATE<(current_timestamp - INTERVAL '30 MINUTEs')";

	String UPDATE_OFFLINE_SUCCESS_RESPONSE_IN_HISTORY = "update OES_PAYMENT_DETAILS_HISTORY set \r\n" + "OPD_DD_CHALLAN_RECEIPT_NO = ?,\r\n" + "OPD_TXN_TYPE=?,\r\n"
			+ "opd_dd_date = ?, \r\n" + "opd_validated_status = ?, \r\n" + "opd_bank_code = ?, \r\n" + "OPD_UPDATED_BY = OPD_CREATED_BY, \r\n"
			+ "opd_updated_date = CURRENT_TIMESTAMP, \r\n" + "opd_status = 'A', \r\n" + "OPD_ERROR_DESC = ?, \r\n" + "OPD_REMARKS = ? ,opd_authenticate_status = ?\r\n"
			+ "WHERE OPD_TRANSACTION_NO = ?";

	String GET_CANDIDATE_RECORD_FROM_OPD = "SELECT OPD_USER_FK from OES_PAYMENT_DETAILS where OPD_USER_FK= ? and (OPD_VALIDATED_STATUS = 'R' OR OPD_VALIDATED_STATUS is null)";

	String GET_CANDIDATE_APPROVED_RECORD_FROM_OPD = "SELECT OPD_USER_FK from OES_PAYMENT_DETAILS where OPD_USER_FK= ? and (OPD_VALIDATED_STATUS = 'A')";

	/*
	 * String INSERT_INTO_PAYMENT_HISTORY_O_CALL =
	 * "INSERT INTO OES_PAYMENT_DETAILS_HISTORY (OES_PAYMENT_DTL_PK, "+
	 * "OPD_PAYMENT_FK,OPD_USER_FK,OPD_TRANSACTION_DATE,OPD_TRANSACTION_NO,OPD_DD_DATE,OPD_CITY, "
	 * +
	 * "OPD_DD_CHALLAN_RECEIPT_NO,OPD_VALIDATED_STATUS,OPD_CURRENCY,OPD_AMOUNT,OPD_STAGE,OPD_BANK_NAME,OPD_BRANCH_NAME, "
	 * +
	 * "OPD_BANK_IMAGES,OPD_BANK_CODE,OPD_BRANCH_CODE,OPD_REMARKS,OPD_RECONCILE_FLAG, "
	 * + "OPD_STATUS,OPD_CREATED_BY,OPD_CREATED_DATE,OPD_UPDATED_BY, "+
	 * "OPD_UPDATED_DATE,OPD_MERCHANT_ID,OPD_CUSTOMER_ID,OPD_TXN_REF_NO,OPD_BANK_REF_NO, "
	 * +
	 * "OPD_TXN_AMOUNT,OPD_BANK_ID,OPD_TXN_TYPE,OPD_SECURITY_ID,OPD_TXN_DATE,OPD_AUTHENTICATE_STATUS, "
	 * + "OPD_ERROR_DESC,OPD_CHECK_SUM,OPD_TRAINING_CENTRE_FK, "+
	 * "OPD_FEE_WAIVER_FLAG,OPD_MONTH_DIFF_FLAG,OPD_DISPLAY_FLAG,OPD_TEST_FK,OPD_SUB_TEST_FK,OPD_APPLICATION_NO,OPD_CLIENT_META_DATA) "
	 * +
	 * "SELECT OES_PAYMENT_DTL_PK,OPD_PAYMENT_FK,OPD_USER_FK,OPD_TRANSACTION_DATE,OPD_TRANSACTION_NO,OPD_DD_DATE,OPD_CITY, "
	 * + "OPD_DD_CHALLAN_RECEIPT_NO,OPD_VALIDATED_STATUS, "+
	 * "OPD_CURRENCY,OPD_AMOUNT,OPD_STAGE,OPD_BANK_NAME,OPD_BRANCH_NAME,OPD_BANK_IMAGES, "
	 * +
	 * "OPD_BANK_CODE,OPD_BRANCH_CODE,OPD_REMARKS,OPD_RECONCILE_FLAG,OPD_STATUS,OPD_CREATED_BY, "
	 * +
	 * "OPD_CREATED_DATE,OPD_UPDATED_BY,OPD_UPDATED_DATE,OPD_MERCHANT_ID,OPD_CUSTOMER_ID,OPD_TXN_REF_NO, "
	 * +
	 * "OPD_BANK_REF_NO,OPD_TXN_AMOUNT,OPD_BANK_ID,OPD_TXN_TYPE,OPD_SECURITY_ID,OPD_TXN_DATE, "
	 * + "OPD_AUTHENTICATE_STATUS,OPD_ERROR_DESC,OPD_CHECK_SUM, "+
	 * "OPD_TRAINING_CENTRE_FK,OPD_FEE_WAIVER_FLAG,OPD_MONTH_DIFF_FLAG, "+
	 * "OPD_DISPLAY_FLAG,OPD_TEST_FK,OPD_SUB_TEST_FK,OPD_APPLICATION_NO,OPD_CLIENT_META_DATA "
	 * +
	 * "FROM OES_PAYMENT_DETAILS WHERE OES_PAYMENT_DETAILS.OPD_USER_FK = ? AND (OES_PAYMENT_DETAILS.OPD_VALIDATED_STATUS = 'R' OR OES_PAYMENT_DETAILS.OPD_VALIDATED_STATUS is null)"
	 * ;
	 */

	/*
	 * String INSERT_INTO_PAYMENT_HISTORY_O_CALL =
	 * "INSERT INTO OES_PAYMENT_DETAILS_HISTORY (OES_PAYMENT_DTL_PK, OPD_PAYMENT_FK,OPD_USER_FK,OPD_TRANSACTION_DATE,\r\n"
	 * +
	 * "OPD_TRANSACTION_NO,OPD_DD_DATE,OPD_CITY, OPD_DD_CHALLAN_RECEIPT_NO,OPD_VALIDATED_STATUS,OPD_CURRENCY,OPD_AMOUNT,\r\n"
	 * +
	 * "OPD_STAGE,OPD_BANK_NAME,OPD_BRANCH_NAME, OPD_BANK_IMAGES,OPD_BANK_CODE,OPD_BRANCH_CODE,OPD_REMARKS,OPD_RECONCILE_FLAG,\r\n"
	 * +
	 * " OPD_STATUS,OPD_CREATED_BY,OPD_CREATED_DATE,OPD_UPDATED_BY, OPD_UPDATED_DATE,OPD_MERCHANT_ID,OPD_CUSTOMER_ID,\r\n"
	 * +
	 * " OPD_TXN_REF_NO,OPD_BANK_REF_NO, OPD_TXN_AMOUNT,OPD_BANK_ID,OPD_TXN_TYPE,OPD_SECURITY_ID,OPD_TXN_DATE,OPD_AUTHENTICATE_STATUS, \r\n"
	 * +
	 * " OPD_ERROR_DESC,OPD_CHECK_SUM, OPD_FEE_WAIVER_FLAG,OPD_MONTH_DIFF_FLAG,OPD_DISPLAY_FLAG,\r\n"
	 * +
	 * " OPD_TEST_FK,OPD_SUB_TEST_FK,OPD_APPLICATION_NO,OPD_CLIENT_META_DATA) SELECT * FROM OES_PAYMENT_DETAILS WHERE OES_PAYMENT_DETAILS.OPD_USER_FK = ? AND \r\n"
	 * +
	 * "(OES_PAYMENT_DETAILS.OPD_VALIDATED_STATUS = 'R' OR OES_PAYMENT_DETAILS.OPD_VALIDATED_STATUS is null)"
	 * ;
	 */

	String INSERT_INTO_PAYMENT_HISTORY_O_CALL = "INSERT INTO OES_PAYMENT_DETAILS_HISTORY(SELECT * FROM OES_PAYMENT_DETAILS WHERE OES_PAYMENT_DETAILS.OPD_USER_FK = ? AND \r\n"
			+ "(OES_PAYMENT_DETAILS.OPD_VALIDATED_STATUS = 'R' OR OES_PAYMENT_DETAILS.OPD_VALIDATED_STATUS is null))";

	String DELETE_REJECTED_PAYMENT_DETAILS_FROM_OPD = "DELETE FROM OES_PAYMENT_DETAILS WHERE OPD_USER_FK = ? AND (OPD_VALIDATED_STATUS is null OR OPD_VALIDATED_STATUS='R')";

	String INSERT_INTO_OES_PAYMENT_DETAILS_O_CALL = "INSERT INTO OES_PAYMENT_DETAILS (SELECT * FROM OES_PAYMENT_DETAILS_HISTORY WHERE OES_PAYMENT_DETAILS_HISTORY.OPD_USER_FK = ? AND OES_PAYMENT_DETAILS_HISTORY.OPD_TRANSACTION_NO = ? AND OES_PAYMENT_DETAILS_HISTORY.OPD_VALIDATED_STATUS = 'A')";

	/*
	 * String INSERT_INTO_OES_PAYMENT_DETAILS_O_CALL =
	 * "INSERT INTO OES_PAYMENT_DETAILS (OES_PAYMENT_DTL_PK, "+
	 * "OPD_PAYMENT_FK,OPD_USER_FK,OPD_TRANSACTION_DATE,OPD_TRANSACTION_NO,OPD_DD_DATE,OPD_CITY, "
	 * +
	 * "OPD_DD_CHALLAN_RECEIPT_NO,OPD_VALIDATED_STATUS,OPD_CURRENCY,OPD_AMOUNT,OPD_STAGE,OPD_BANK_NAME,OPD_BRANCH_NAME, "
	 * +
	 * "OPD_BANK_IMAGES,OPD_BANK_CODE,OPD_BRANCH_CODE,OPD_REMARKS,OPD_RECONCILE_FLAG, "
	 * + "OPD_STATUS,OPD_CREATED_BY,OPD_CREATED_DATE,OPD_UPDATED_BY, "+
	 * "OPD_UPDATED_DATE,OPD_MERCHANT_ID,OPD_CUSTOMER_ID,OPD_TXN_REF_NO,OPD_BANK_REF_NO, "
	 * +
	 * "OPD_TXN_AMOUNT,OPD_BANK_ID,OPD_TXN_TYPE,OPD_SECURITY_ID,OPD_TXN_DATE,OPD_AUTHENTICATE_STATUS, "
	 * +
	 * "OPD_ERROR_DESC,OPD_ERROR_STATUS,OPD_CHECK_SUM,OPD_TRAINING_CENTRE_FK, "+
	 * "OPD_FEE_WAIVER_FLAG,OPD_MONTH_DIFF_FLAG,OPD_DISPLAY_FLAG,OPD_TEST_FK,OPD_SUB_TEST_FK,OPD_APPLICATION_NO,OPD_CLIENT_META_DATA) "
	 * +
	 * "SELECT OES_PAYMENT_DTL_PK,OPD_PAYMENT_FK,OPD_USER_FK,OPD_TRANSACTION_DATE,OPD_TRANSACTION_NO,OPD_DD_DATE,OPD_CITY, "
	 * + "OPD_DD_CHALLAN_RECEIPT_NO,OPD_VALIDATED_STATUS, "+
	 * "OPD_CURRENCY,OPD_AMOUNT,OPD_STAGE,OPD_BANK_NAME,OPD_BRANCH_NAME,OPD_BANK_IMAGES, "
	 * +
	 * "OPD_BANK_CODE,OPD_BRANCH_CODE,OPD_REMARKS,OPD_RECONCILE_FLAG,OPD_STATUS,OPD_CREATED_BY, "
	 * +
	 * "OPD_CREATED_DATE,OPD_UPDATED_BY,OPD_UPDATED_DATE,OPD_MERCHANT_ID,OPD_CUSTOMER_ID,OPD_TXN_REF_NO, "
	 * +
	 * "OPD_BANK_REF_NO,OPD_TXN_AMOUNT,OPD_BANK_ID,OPD_TXN_TYPE,OPD_SECURITY_ID,OPD_TXN_DATE, "
	 * +
	 * "OPD_AUTHENTICATE_STATUS,OPD_ERROR_DESC,OPD_ERROR_STATUS,OPD_CHECK_SUM, "
	 * + "OPD_TRAINING_CENTRE_FK,OPD_FEE_WAIVER_FLAG,OPD_MONTH_DIFF_FLAG, "+
	 * "OPD_DISPLAY_FLAG,OPD_TEST_FK,OPD_SUB_TEST_FK,OPD_APPLICATION_NO,OPD_CLIENT_META_DATA "
	 * +
	 * "FROM OES_PAYMENT_DETAILS_HISTORY WHERE OES_PAYMENT_DETAILS_HISTORY.OPD_USER_FK = ? AND OPD_TRANSACTION_NO = ? AND OES_PAYMENT_DETAILS_HISTORY.OPD_VALIDATED_STATUS = 'A'"
	 * ;
	 */

	String DELETE_APPROVED_PAYMENT_DETAILS_FROM_OPH = "DELETE FROM OES_PAYMENT_DETAILS_HISTORY WHERE OPD_USER_FK = ? AND OPD_TRANSACTION_NO = ?";

	String SELECT_USER_FOR_APPLICATION_NO_EMAIL = "select OUM_USER_PK,OUM_USER_ID,OUM_TEST_FK from oes_user_master where oum_user_pk = ? ";

	String INSERT_INTO_OES_PAYMENT_TYPE_O_HISTORY = "INSERT INTO OES_PAYMENT_DETAILS_HISTORY (oes_payment_dtl_pk,OPD_USER_FK, "
			+ "OPD_TRANSACTION_DATE,OPD_TRANSACTION_NO,OPD_VALIDATED_STATUS,OPD_CREATED_BY, " + "OPD_TEST_FK) values(nextVal('oes_online_transaction_payment_seq'),?,?,?,?,?,?)";

	String MAX_PAYMENT_ID = "select nextVal('OES_PAYMENT_DETAILS_SEQ')";

	String INSERT_INTO_PAYMENT_HISTORY = "INSERT INTO oes_payment_details_history(oes_payment_dtl_pk, opd_payment_fk,"
			+ " opd_user_fk, opd_transaction_date, opd_transaction_no, opd_currency, opd_status, opd_created_by, "
			+ "opd_created_date, opd_test_fk,opd_amount) "
			+ "select oes_payment_dtl_pk, opd_payment_fk," + 
				" opd_user_fk, opd_transaction_date, opd_transaction_no, opd_currency, opd_status, opd_created_by," + 
			 "opd_created_date, opd_test_fk,opd_amount from oes_payment_details where opd_user_fk = ?  "
			 + "and (opd_status = 'N' OR  opd_status = 'A')";

	String GET_CANDIDATE_CATEGORY = "select ocd_category_fk from oes_candidate_details where ocd_user_fk= ?";

	String GET_CANDIDATE_DISABILITY = "SELECT OCD_IS_HANDICAPED from oes_candidate_details where ocd_user_fk= ?";

	String GET_CANDIDATE_PAYMENT_STATUS = "SELECT OPD_VALIDATED_STATUS from OES_PAYMENT_DETAILS where OPD_USER_FK= ?";

	String GET_CANDIDATE_CATEGORY_ADMIN = /*
											 * "select ocd_community from oes_candidate_details\r\n"
											 * +
											 * "inner join oes_payment_details details on opd_user_fk=ocd_user_fk\r\n"
											 * + " where opd_user_fk=?"
											 */ "select oum_category_fk from oes_user_master\r\n" + "			inner join oes_payment_details details on opd_user_fk=oum_user_pk\r\n"
			+ "			where opd_user_fk=?";

	String GET_CANDIDATE_DISABILITY_FOR_ADMIN = "select oum_disability from oes_user_master\r\n"
			+ "			inner join oes_payment_details details on opd_user_fk=oum_user_pk\r\n" + "			where opd_user_fk=?";

	String GET_DUAL_POST_ELIGIBILITY = "select count(1) from oes_additional_education_details where oaed_pg_eduqst1='227' and oaed_pg_eduqst2='227' and  oaed_pg_eduqst3='227' and oaed_user_fk=?";

	String GET_USER_FOR_TRANS = "select opd_created_by from oes_payment_details where opd_transaction_no = ?";
	
	String COPY_TO_PAYMENT_HISTORY = "INSERT INTO OES_PAYMENT_DETAILS_HISTORY SELECT (select nextVal('oes_payment_details_history_seq')) ,opd_payment_fk, opd_user_fk, opd_transaction_date, opd_transaction_no, opd_dd_date, opd_city, opd_dd_challan_receipt_no, opd_validated_status, opd_currency, opd_amount, opd_stage, opd_bank_name, opd_branch_name, opd_bank_images, opd_bank_code, opd_branch_code, opd_remarks, opd_reconcile_flag, opd_status, opd_created_by, opd_created_date, opd_updated_by, opd_updated_date, opd_merchant_id, opd_customer_id, opd_txn_ref_no, opd_bank_ref_no, opd_txn_amount, opd_bank_id, opd_txn_type, opd_security_id, opd_txn_date, opd_authenticate_status, opd_error_desc, opd_offline_status, opd_check_sum, opd_client_meta_data, opd_test_fk, opd_app_no, opd_payment_status_date FROM OES_PAYMENT_DETAILS WHERE OPD_TRANSACTION_NO = ?";

	String UPDATE_OPD_TRANSACTION = "UPDATE OES_PAYMENT_DETAILS SET OPD_VALIDATED_STATUS = null WHERE opd_user_fk = ?";
	
	String UPDATE_ONLINE_DETAILS1 = " UPDATE oes_payment_details SET opd_bank_ref_no = ?, "
			+ " OPD_TXN_REF_NO = ?, opd_bank_name = ?, opd_txn_date = ?, opd_validated_status = ?, "
			+ " opd_txn_type = ?, opd_updated_by = ?, opd_updated_date = CURRENT_TIMESTAMP, "
			+ " opd_status = ?, opd_txn_amount = ?, OPD_ERROR_DESC = ?"
			+ " WHERE opd_user_fk = ? AND OPD_TRANSACTION_NO = ? and opd_payment_fk=1";
}