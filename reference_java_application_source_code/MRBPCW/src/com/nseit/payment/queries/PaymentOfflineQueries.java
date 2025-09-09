package com.nseit.payment.queries;

// Payment related generic queries added by Tarka on 16-Dec-2011
public interface PaymentOfflineQueries {

	String GET_FEE_DETAILS = " select TEST.TEST_PK,TEST.TEST_NAME,FEE.AMOUNT " + " from FEES_MASTER FEE, TEST_MASTER TEST " + " where FEE.STATUS='A' AND TEST.STATUS='A' AND "
			+ " TEST.TEST_PK = FEE.TEST_FK ";

	String GET_FEE_DETAILS_FOR_CATERGORY = " select TEST.TEST_PK,TEST.TEST_NAME,FEE.AMOUNT " + " from FEES_MASTER FEE, TEST_MASTER TEST "
			+ " where FEE.STATUS='A' AND TEST.STATUS ='A' AND " + " TEST.TEST_PK = FEE.TEST_FK AND CATEGORY_CODE = ? ";

	String GET_FEE_DETAILS_FOR_DATE_RANGE = " select TEST.TEST_PK,TEST.TEST_NAME,FEE.AMOUNT " + " from FEES_MASTER FEE, TEST_MASTER TEST "
			+ " where FEE.STATUS='A' AND TEST.STATUS ='A' AND " + " TEST.TEST_PK = FEE.TEST_FK AND TRUNC(?) BETWEEN FROM_DATE AND TO_DATE ";

	String GET_DD_DETAILS_FOR_ENROLLMENT = "SELECT DD.OES_PAYMENT_DTL_PK,PM.OPTM_PAYMENT_DESC,DD.OPD_USER_FK, TO_CHAR(DD.OPD_TRANSACTION_DATE,'DD-MON-YYYY') AS OPD_TRANSACTION_DATE ,DD.OPD_TRANSACTION_NO,  "
			+ "TO_CHAR(DD.OPD_DD_DATE,'DD-MON-YYYY') AS  OPD_DD_DATE ,DD.OPD_CITY , DD.OPD_BANK_NAME, DD.OPD_DD_CHALLAN_RECEIPT_NO,DD.OPD_DD_CHALLAN_RECEIPT_NO,  "
			+ "DD.OPD_AMOUNT FROM OES_PAYMENT_DETAILS DD, OES_CANDIDATE_DETAILS ED, OES_PAYMENT_MASTER PM WHERE DD.OPD_STATUS='A' AND DD.OPD_USER_FK=ED.OCD_USER_FK  AND DD.OPD_PAYMENT_FK=PM.OPTM_PAYMENT_PK  "
			+ "AND DD.OPD_PAYMENT_FK = 3 AND DD.OPD_USER_FK = ?";

	String GET_CHALLAN_DETAILS_FOR_ENROLLMENT = " select CHALLAN.OES_PAYMENT_DTL_PK, PM.OPTM_PAYMENT_DESC, CHALLAN.OPD_USER_FK, TO_CHAR(CHALLAN.OPD_TRANSACTION_DATE,'DD-MON-YYYY') AS OPD_TRANSACTION_DATE, TO_CHAR(CHALLAN.OPD_DD_DATE,'DD-MON-YYYY') AS OPD_DD_DATE, "
			+ "CHALLAN.OPD_CITY, CHALLAN.OPD_DD_CHALLAN_RECEIPT_NO, CHALLAN.OPD_AMOUNT, CHALLAN.OPD_BANK_NAME, CHALLAN.OPD_BRANCH_NAME "
			+ "FROM OES_PAYMENT_DETAILS CHALLAN, OES_CANDIDATE_DETAILS ED, OES_PAYMENT_MASTER PM " + "WHERE CHALLAN.OPD_STATUS ='A' AND CHALLAN.OPD_PAYMENT_FK = 4 "
			+ " AND CHALLAN.OPD_PAYMENT_FK=PM.OPTM_PAYMENT_PK " + "AND CHALLAN.OPD_USER_FK = ED.OCD_USER_FK AND CHALLAN.OPD_USER_FK = ?";

	String CHECK_IF_DD_EXIST = " select count(1) from OES_PAYMENT_DETAILS where OPD_DD_CHALLAN_RECEIPT_NO = ? ";

	String CHECK_IF_CHALLAN_EXIST = " select count(1) from OES_PAYMENT_DETAILS where OPD_DD_CHALLAN_RECEIPT_NO = ? ";

	String GET_PG_DETAILS_FOR_ENROLLMENT = "SELECT OES_PAYMENT_DTL_PK,OPD_PAYMENT_FK,OPD_USER_FK,OPD_TRANSACTION_DATE,OPD_TRANSACTION_NO,"
			+ " OPD_AMOUNT,OPD_CREATED_BY,OPD_CREATED_DATE,opd_validated_status,OPD_UPDATED_DATE, opd_payment_status_date " + " FROM OES_PAYMENT_DETAILS WHERE OPD_USER_FK = ?";

	/** SELECT STATEMENTS FOR PAYMENT QUERIES **/

	/** INSERT STATEMENTS FOR PAYMENT QUERIES **/
	String INSERT_DD_DETAILS_FOR_ENROLLMENT = "INSERT INTO OES_PAYMENT_DETAILS (OES_PAYMENT_DTL_PK, OPD_PAYMENT_FK, OPD_USER_FK, OPD_TRANSACTION_DATE, OPD_DD_DATE, OPD_BANK_NAME, OPD_CITY,"
			+ " OPD_DD_CHALLAN_RECEIPT_NO, OPD_AMOUNT, OPD_STATUS,OPD_CREATED_BY,OPD_CREATED_DATE)"
			+ " VALUES (NEXTVAL('OES_PAYMENT_DETAILS_SEQ'),3, ?,CURRENT_TIMESTAMP,?,?,?,?,?,'A',?,CURRENT_TIMESTAMP)";

	/** UPDATE STATEMENTS FOR PAYMENT QUERIES **/
	/*
	 * New Query For Update PAYMENT_DETAILS
	 */
	String UPDATE_DD_DETAILS_FOR_ENROLLMENT = " update OES_PAYMENT_DETAILS set " + " OPD_PAYMENT_FK = 3, OPD_TRANSACTION_DATE=CURRENT_TIMESTAMP, "
			+ " OPD_DD_DATE=?, OPD_BANK_NAME =?, OPD_CITY=?, OPD_AMOUNT = ?, OPD_DD_CHALLAN_RECEIPT_NO =?," + " OPD_UPDATED_BY=?, OPD_UPDATED_DATE= CURRENT_TIMESTAMP "
			+ " where OPD_USER_FK =? ";

	String INSERT_CHALLAN_DETAILS_FOR_ENROLLMENT =
			/*
			 * " INSERT INTO OES_PAYMENT_DETAILS(OES_PAYMENT_DTL_PK, OPD_PAYMENT_MODE, OPD_USER_FK, OPD_TRANSACTION_DATE, OPD_DD_DATE, OPD_BANK_NAME, OPD_BRANCH_NAME, OPD_DD_CHALLAN_RECEIPT_NO, OPD_AMOUNT, OPD_STATUS,OPD_CREATED_BY,OPD_CREATED_DATE)"
			 * + " SELECT OES_PAYMENT_DETAILS_SEQ.nextval,'CH',?,systimestamp,?,?,?,?, decode(OCD_CATEGORY_FK,19,OTM_FEES_OPEN,OTM_FEES_SC_ST) as FEES,'A',?,systimestamp " +
			 * " FROM OES_CANDIDATE_DETAILS INNER JOIN OES_USER_MASTER ON OCD_USER_FK = OUM_USER_PK INNER JOIN OES_TEST_MASTER ON OTM_TEST_PK = OUM_TEST_FK WHERE OCD_USER_FK=? " ;
			 */
			" INSERT INTO OES_PAYMENT_DETAILS (OES_PAYMENT_DTL_PK, OPD_PAYMENT_FK, OPD_USER_FK, OPD_TRANSACTION_DATE, OPD_DD_DATE, OPD_BANK_NAME, OPD_BRANCH_NAME, OPD_BRANCH_CODE, "
					+ " OPD_DD_CHALLAN_RECEIPT_NO, OPD_AMOUNT, OPD_STATUS,OPD_CREATED_BY,OPD_CREATED_DATE)"
					+ " VALUES (NEXTVAL('OES_PAYMENT_DETAILS_SEQ'),4, ?,CURRENT_TIMESTAMP,?,?,?,?,?,?,'A',?,CURRENT_TIMESTAMP)";

	/*
	 * New Query For Update PAYMENT_DETAILS in case of Challan
	 */

	String INSERT_CHALLAN_BARCODE_DETAILS = "INSERT INTO OES_CHALLAN_BARCODE_DETAILS(OCBD_PK,OCBD_USER_FK,OCBD_USER_ID,OCBD_APPLICATION_NO,"
			+ "OCBD_GENERATED_BARCODE_NO,OCBD_GENERATED_DATE)" + "VALUES(NEXTVAL('OES_CHALLAN_BARCODE_SEQ'),?::numeric,?,?,?,CURRENT_TIMESTAMP)";

	String UPDATE_CHALLAN_DETAILS_FOR_ENROLLMENT = " update OES_PAYMENT_DETAILS set " + " OPD_PAYMENT_FK = 4, OPD_TRANSACTION_DATE=CURRENT_TIMESTAMP, "
			+ " OPD_DD_DATE=?, OPD_AMOUNT = ? , OPD_BANK_NAME =?, OPD_BRANCH_NAME=?, OPD_BRANCH_CODE = ? , OPD_DD_CHALLAN_RECEIPT_NO =?,"
			+ " OPD_UPDATED_BY=?, OPD_UPDATED_DATE= CURRENT_TIMESTAMP " + " where OPD_USER_FK =? ";

	String INSERT_EPOST_DETAILS_FOR_ENROLLMENT = " INSERT INTO OES_PAYMENT_DETAILS (OES_PAYMENT_DTL_PK, OPD_PAYMENT_FK, OPD_USER_FK, OPD_TRANSACTION_DATE, OPD_DD_DATE, OPD_BRANCH_NAME, OPD_BRANCH_CODE, "
			+ " OPD_DD_CHALLAN_RECEIPT_NO, OPD_AMOUNT, OPD_STATUS,OPD_CREATED_BY,OPD_CREATED_DATE)"
			+ " VALUES (NEXTVAL('OES_PAYMENT_DETAILS_SEQ'),5, ?,CURRENT_TIMESTAMP,?,?,?,?,?,'A',?,CURRENT_TIMESTAMP)";

	/*
	 * New Query For Update PAYMENT_DETAILS in case of Challan
	 */

	String UPDATE_EPOST_DETAILS_FOR_ENROLLMENT = " update OES_PAYMENT_DETAILS set " + " OPD_PAYMENT_FK = 5, OPD_TRANSACTION_DATE=CURRENT_TIMESTAMP, "
			+ " OPD_DD_DATE=?, OPD_AMOUNT = ? , OPD_BRANCH_NAME=?, OPD_BRANCH_CODE = ? , OPD_DD_CHALLAN_RECEIPT_NO =?," + " OPD_UPDATED_BY=?, OPD_UPDATED_DATE= CURRENT_TIMESTAMP "
			+ " where OPD_USER_FK =? ";

	/*
	 * Note Add CITY_FK in Both Case (Old Query)String INSERT_CHALLAN_DETAILS_FOR_ENROLLMENT =
	 * " insert into PAYMENT_DETAILS(PAYMENT_PK,PAYMENT_MODE,ENROLLMENT_FK,TRANSACTION_DATE,TRANSACTION_NO,BANK_FK,DD_CHALLAN_RECEIPT_NO,AMOUNT, " +
	 * " STAGE,STATUS,CREATED_BY,CREATED_DATE) "+ " values(SEQ_PAYMENT_PK.nextval,'CH',?,systimestamp,?,?,?, ?,?,'A',?,systimestamp)" ;
	 */

	String INSERT_ONLINE_DETAILS_FOR_ENROLLMENT = " insert into PAYMENT_DETAILS(PAYMENT_PK,PAYMENT_MODE,ENROLLMENT_FK,TRANSACTION_DATE,TRANSACTION_NO,BANK_FK,BID,ITC,CURRENCY,AMOUNT,STAGE, "
			+ " STATUS,CREATED_BY,CREATED_DATE) " + " values(nextval('SEQ_PAYMENT_PK'),'PG',?,CURRENT_TIMESTAMP,?,?,?,?,?,?,?,'A',?,CURRENT_TIMESTAMP) ";

	/*
	 * String INSERT_ONLINE_PAYMENT_DETAILS =
	 * "INSERT INTO OES_PAYMENT_DETAILS(OES_PAYMENT_DTL_PK,OPD_PAYMENT_MODE,OPD_USER_FK,OPD_TRANSACTION_DATE,OPD_TRANSACTION_NO,OPD_BANK_NAME,OPD_AMOUNT,OPD_STATUS,OPD_CREATED_BY,OPD_CREATED_DATE, OPD_VALIDATED_STATUS) "
	 * + "SELECT OES_PAYMENT_DETAILS_SEQ.nextval,?,?,systimestamp,OES_PAYMENT_DETAILS_SEQ.currval,?,?,'A',?,systimestamp, 'A' " +
	 * " FROM OES_CANDIDATE_DETAILS INNER JOIN OES_USER_MASTER ON OCD_USER_FK = OUM_USER_PK INNER JOIN OES_TEST_MASTER ON OTM_TEST_PK = OUM_TEST_FK WHERE OCD_USER_FK=? " ;
	 */

	String INSERT_ONLINE_PAYMENT_DETAILS = "INSERT INTO OES_PAYMENT_DETAILS (OES_PAYMENT_DTL_PK,OPD_PAYMENT_FK,OPD_USER_FK,OPD_TRANSACTION_DATE,"
			+ " OPD_TRANSACTION_NO,OPD_BANK_NAME,OPD_AMOUNT,OPD_STATUS,OPD_CREATED_BY,OPD_CREATED_DATE, OPD_VALIDATED_STATUS)"
			+ " VALUES (NEXTVAL('OES_PAYMENT_DETAILS_SEQ'), ?, ?, CURRENT_TIMESTAMP,?,?,?,'A',?,CURRENT_TIMESTAMP,'A')";

	String UPDATE_ONLINE_PAYMENT_DETAILS = "UPDATE OES_PAYMENT_DETAILS set OPD_PAYMENT_FK=?,OPD_TRANSACTION_DATE=CURRENT_TIMESTAMP,OPD_TRANSACTION_NO=?,"
			+ "OPD_BANK_NAME=?,OPD_AMOUNT=?," + "OPD_UPDATED_BY=?, OPD_UPDATED_DATE=CURRENT_TIMESTAMP where OPD_USER_FK =?";

	String INSERT_ONLINE_PAYMENT_DETAILS_FOR_NETBANKING_OR_CREDIR_CARD = "INSERT INTO OES_PAYMENT_DETAILS (OES_PAYMENT_DTL_PK,OPD_PAYMENT_FK,OPD_USER_FK,OPD_TRANSACTION_DATE,"
			+ " OPD_TRANSACTION_NO,OPD_BANK_NAME,OPD_AMOUNT,OPD_STATUS,OPD_CREATED_BY,OPD_CREATED_DATE, OPD_VALIDATED_STATUS)"
			+ " VALUES (NEXTVAL('OES_PAYMENT_DETAILS_SEQ'), ?, ?, CURRENT_TIMESTAMP,?,?,?,'A',?,CURRENT_TIMESTAMP,'R')";

	String UPDATE_ONLINE_PAYMENT_DETAILSFOR_NETBANKING_OR_CREDIR_CARD = "UPDATE OES_PAYMENT_DETAILS set OPD_PAYMENT_FK=?,OPD_TRANSACTION_DATE=CURRENT_TIMESTAMP,OPD_TRANSACTION_NO=?,"
			+ "OPD_BANK_NAME=?,OPD_AMOUNT=?," + "OPD_UPDATED_BY=?, OPD_UPDATED_DATE=CURRENT_TIMESTAMP where OPD_USER_FK =?";

	String GET_FESS_DETAILS_FOR_ENROLLMENT = " select decode(OCD_CATEGORY_FK,19,OTM_FEES_OPEN,OTM_FEES_SC_ST) as FEES FROM OES_CANDIDATE_DETAILS INNER JOIN OES_USER_MASTER ON OCD_USER_FK = OUM_USER_PK INNER JOIN OES_TEST_MASTER ON OTM_TEST_PK = OUM_TEST_FK WHERE OCD_USER_FK=?";

	// FOR LOGO OF CHALLAN RECEIPT
	String GET_BANK_CODE_FOR_CHALLAN_RECEIPT = "select p.OPD_BANK_NAME from OES_PAYMENT_DETAILS p where p.OPD_USER_FK = ?";

	String INSERT_ONLINE_PAYMENT_DETAILS_FOR_ENROLLMENT = " insert into PAYMENT_DETAILS(PAYMENT_PK,PAYMENT_MODE,ENROLLMENT_FK,TRANSACTION_DATE,BID,ITC,CURRENCY,AMOUNT, "
			+ " STATUS,CREATED_BY,CREATED_DATE,BANK_NAME) " + " values(nextval('SEQ_PAYMENT_PK'),'Online',?,CURRENT_TIMESTAMP,?,?,?,?,'A',?,CURRENT_TIMESTAMP,?) ";

	// common query - to be used for updating the stage details for the
	// candidate
	String UPDATE_STAGE = " update OES_USER_MASTER set " + " OUM_STAGE_FK = '10', OUM_UPDATED_BY=?, OUM_UPDATED_DATE= CURRENT_TIMESTAMP " + " where OUM_USER_PK = ? ";

	String GET_BANK_MASTER = " select OBM_BANK_PK,OBM_BANK_NAME " + " from OES_BANK_MASTER " + " where OBM_STATUS='A' " + " order by OBM_BANK_NAME ";

	String GET_BANK_LOGO_IMAGE = " select OBM_BANK_IMAGES " + " from OES_BANK_MASTER " + " where OBM_STATUS='A' AND OBM_BANK_PK = ?";

	String GET_CITY_MASTER = " select CITY.OCM_CITY_PK, CITY.OCM_CITY_CODE, CITY.OCM_CITY_NAME " + " from OES_CITY_MASTER CITY " + " where CITY.OCM_STATUS='A' "
			+ " order by CITY.OCM_CITY_CODE ";

	String GET_TEST_DETAILS = " select TEST.OTM_TEST_PK, TEST.OTM_TEST_NAME " + " from OES_TEST_MASTER TEST " + " where TEST.OTM_STATUS='A' " + " order by TEST.OTM_TEST_NAME ";

	String GET_PAYMENT_MODE_DETAILS = "SELECT * " + " FROM OES_PAYMENT_MASTER WHERE OPTM_STATUS ='A' " + " ORDER BY OPTM_PAYMENT_PK ";

	String GET_COUNT_PAYMENT_APPROVAL_FOR_DD_TEST_PAYMENT_DATE_RANGE = " SELECT count(1) "
			+ " FROM oes_user_master usm, oes_candidate_details cd, oes_payment_details pd, oes_test_master tm, oes_fees_master ofm "
			+ " WHERE usm.oum_user_pk = cd.ocd_user_fk  AND cd.ocd_user_fk = pd.opd_user_fk AND usm.oum_test_fk = tm.otm_test_pk "
			+ " AND tm.otm_test_pk = usm.oum_test_fk AND ofm.ofm_test_fk = cd.ocd_test_fk  AND ofm.ofm_category_fk = cd.ocd_category_fk AND pd.OPD_PAYMENT_FK = 3";

	String GET_PAYMENT_APPROVAL_FOR_DD_TEST_PAYMENT_DATE_RANGE = " SELECT * FROM (SELECT  ROW_NUMBER() OVER (ORDER BY usm.oum_user_id) AS num,pd.OPD_AMOUNT, pd.OPD_CITY, pd.OPD_BANK_NAME, usm.oum_user_id, pd.opd_validated_status, "
			+ " cd.OCD_CAND_TITLE,cd.OCD_FIRST_NAME,cd.OCD_MIDDLE_NAME,cd.OCD_LAST_NAME, " + " usm.OUM_MOBILE_NO, tm.otm_test_name, "
			+ " pd.opd_dd_challan_receipt_no, TO_CHAR(pd.OPD_DD_DATE,'DD-MON-YYYY') AS OPD_DD_DATE, trim(to_char(ofm.ofm_fees ,'9999999.99')) as OFM_FEES, "
			+ " pd.OES_PAYMENT_DTL_PK, OPD_TRANSACTION_DATE,  " + " pd.opd_transaction_no," + " usm.oum_stage_fk, usm.oum_user_pk, pd.opd_remarks "
			+ " FROM oes_user_master usm, oes_candidate_details cd, oes_payment_details pd, oes_test_master tm, oes_fees_master ofm "
			+ " WHERE usm.oum_user_pk = cd.ocd_user_fk  AND cd.ocd_user_fk = pd.opd_user_fk AND usm.oum_test_fk = tm.otm_test_pk "
			+ " AND tm.otm_test_pk = usm.oum_test_fk AND ofm.ofm_test_fk = cd.ocd_test_fk  AND ofm.ofm_category_fk = cd.ocd_category_fk AND pd.OPD_PAYMENT_FK = 3";

	String GET_COUNT_PAYMENT_APPROVAL_FOR_CHALLAN_TEST_PAYMENT_DATE_RANGE = " SELECT COUNT(1) AS CNT "
			+ " FROM OES_USER_MASTER usm, OES_CANDIDATE_DETAILS cd, OES_PAYMENT_DETAILS pd,OES_TEST_MASTER tm, OES_FEES_MASTER OFM "
			+ " WHERE usm.OUM_USER_PK = cd.OCD_USER_FK AND cd.OCD_USER_FK = pd.OPD_USER_FK " + " AND usm.OUM_TEST_FK = tm.OTM_TEST_PK " + " AND pd.OPD_PAYMENT_FK = 4 "
			+ " AND ofm.ofm_test_fk = cd.ocd_test_fk" + " AND ofm.ofm_category_fk = cd.ocd_category_fk" + " AND tm.OTM_TEST_PK = usm.OUM_TEST_FK";

	String GET_PAYMENT_APPROVAL_FOR_CHALLAN_TEST_PAYMENT_DATE_RANGE = "SELECT * FROM(SELECT  ROW_NUMBER() OVER (ORDER BY usm.oum_user_id) AS num,pd.OPD_AMOUNT, usm.OUM_USER_ID,"
			+ " cd.OCD_CAND_TITLE,cd.OCD_FIRST_NAME,cd.OCD_MIDDLE_NAME,cd.OCD_LAST_NAME, pd.opd_validated_status, "
			+ " usm.OUM_MOBILE_NO, tm.OTM_TEST_NAME, pd.OPD_DD_CHALLAN_RECEIPT_NO, TO_CHAR(pd.OPD_DD_DATE,'DD-MON-YYYY') AS OPD_DD_DATE,  trim(to_char(ofm.ofm_fees ,'9999999.99')) as OFM_FEES, pd.OES_PAYMENT_DTL_PK, "
			+ " TO_CHAR(CAST(pd.OPD_TRANSACTION_DATE as DATE),'DD-MON-YYYY') AS OPD_TRANSACTION_DATE, pd.OPD_TRANSACTION_NO, pd.OPD_BANK_NAME, pd.OPD_CITY, usm.OUM_STAGE_FK, pd.OPD_BRANCH_NAME, pd.OPD_BRANCH_CODE, usm.OUM_USER_PK, pd.OPD_REMARKS "
			+ " FROM OES_USER_MASTER usm, OES_CANDIDATE_DETAILS cd, OES_PAYMENT_DETAILS pd,OES_TEST_MASTER tm, OES_FEES_MASTER OFM "
			+ " WHERE usm.OUM_USER_PK = cd.OCD_USER_FK AND cd.OCD_USER_FK = pd.OPD_USER_FK " + " AND usm.OUM_TEST_FK = tm.OTM_TEST_PK " + " AND pd.OPD_PAYMENT_FK = 4 "
			+ " AND ofm.ofm_test_fk = cd.ocd_test_fk" + " AND ofm.ofm_category_fk = cd.ocd_category_fk" + " AND tm.OTM_TEST_PK = usm.OUM_TEST_FK";

	// "(pd.OPD_VALIDATED_STATUS = 'R' OR pd.OPD_VALIDATED_STATUS IS NULL) " +
	// " AND tm.OTM_TEST_PK = DECODE(?, 0, tm.OTM_TEST_PK, ?)"+
	// " AND TRUNC(pd.OPD_DD_DATE) BETWEEN ? AND ? " +
	// " ORDER BY usm.OUM_USER_ID";

	String GET_COUNT_PAYMENT_APPROVAL_FOR_EPOST_PAYMENT_DATE_RANGE = " SELECT COUNT(1) AS CNT "
			+ " FROM OES_USER_MASTER usm, OES_CANDIDATE_DETAILS cd, OES_PAYMENT_DETAILS pd,OES_TEST_MASTER tm, OES_FEES_MASTER OFM "
			+ " WHERE usm.OUM_USER_PK = cd.OCD_USER_FK AND cd.OCD_USER_FK = pd.OPD_USER_FK " + " AND usm.OUM_TEST_FK = tm.OTM_TEST_PK " + " AND pd.OPD_PAYMENT_FK = 5 "
			+ " AND ofm.ofm_test_fk = cd.ocd_test_fk" + " AND ofm.ofm_category_fk = cd.ocd_category_fk" + " AND tm.OTM_TEST_PK = usm.OUM_TEST_FK";

	String GET_PAYMENT_APPROVAL_FOR_EPOST_PAYMENT_DATE_RANGE = "SELECT * FROM(SELECT  ROW_NUMBER() OVER (ORDER BY usm.oum_user_id) AS num,pd.OPD_AMOUNT, usm.OUM_USER_ID,"
			+ " cd.OCD_CAND_TITLE,cd.OCD_FIRST_NAME,cd.OCD_MIDDLE_NAME,cd.OCD_LAST_NAME, pd.opd_validated_status, "
			+ " usm.OUM_MOBILE_NO, tm.OTM_TEST_NAME, pd.OPD_DD_CHALLAN_RECEIPT_NO, TO_CHAR(pd.OPD_DD_DATE,'DD-MON-YYYY') AS OPD_DD_DATE,  trim(to_char(ofm.ofm_fees ,'9999999.99')) as OFM_FEES, pd.OES_PAYMENT_DTL_PK, "
			+ " TO_CHAR(CAST(pd.OPD_TRANSACTION_DATE as DATE),'DD-MON-YYYY') AS OPD_TRANSACTION_DATE, pd.OPD_TRANSACTION_NO, pd.OPD_BANK_NAME, pd.OPD_CITY, usm.OUM_STAGE_FK, pd.OPD_BRANCH_NAME, pd.OPD_BRANCH_CODE, usm.OUM_USER_PK, pd.OPD_REMARKS "
			+ " FROM OES_USER_MASTER usm, OES_CANDIDATE_DETAILS cd, OES_PAYMENT_DETAILS pd,OES_TEST_MASTER tm, OES_FEES_MASTER OFM "
			+ " WHERE usm.OUM_USER_PK = cd.OCD_USER_FK AND cd.OCD_USER_FK = pd.OPD_USER_FK " + " AND usm.OUM_TEST_FK = tm.OTM_TEST_PK " + " AND pd.OPD_PAYMENT_FK = 5 "
			+ " AND ofm.ofm_test_fk = cd.ocd_test_fk" + " AND ofm.ofm_category_fk = cd.ocd_category_fk" + " AND tm.OTM_TEST_PK = usm.OUM_TEST_FK";

	String GET_PAYMENT_APPROVAL_FOR_ENROLLMENT_PK = " SELECT OPD_AMOUNT, OTM_TEST_NAME, usm.OUM_USER_ID, opd_validated_status, "
			+ " cd.OCD_CAND_TITLE,cd.OCD_FIRST_NAME,cd.OCD_MIDDLE_NAME,cd.OCD_LAST_NAME ,"
			+ " usm.OUM_MOBILE_NO, tm.OTM_TEST_NAME, pd.OPD_DD_CHALLAN_RECEIPT_NO, pd.OPD_DD_DATE, trim(to_char(ofm.ofm_fees ,'9999999.99')) as OFM_FEES, pd.OES_PAYMENT_DTL_PK, "
			+ " pd.OPD_TRANSACTION_DATE, pd.OPD_TRANSACTION_NO, pd.OPD_BANK_NAME, pd.OPD_CITY, usm.OUM_STAGE_FK, pd.OPD_PAYMENT_FK,  pd.OPD_BRANCH_NAME, pd.OPD_BRANCH_CODE, usm.OUM_USER_PK, pd.OPD_REMARKS "
			+ " FROM OES_USER_MASTER usm, OES_CANDIDATE_DETAILS cd, OES_PAYMENT_DETAILS pd,OES_TEST_MASTER tm, OES_FEES_MASTER OFM "
			+ " WHERE usm.OUM_USER_PK = cd.OCD_USER_FK AND cd.OCD_USER_FK = pd.OPD_USER_FK " +
			// " AND tm.OTM_TEST_PK = cd.OCD_TEST_FK " +
			" AND usm.OUM_TEST_FK = tm.OTM_TEST_PK " +
			// " AND (pd.OPD_VALIDATED_STATUS = 'R' OR pd.OPD_VALIDATED_STATUS
			// IS NULL) " +
			" AND OFM.OFM_TEST_FK = CD.OCD_TEST_FK " + " AND OFM.OFM_CATEGORY_FK = CD.OCD_CATEGORY_FK " + " AND usm.OUM_USER_ID =?";

	String UPDATE_PAYMENT_APPROVAL = " update OES_PAYMENT_DETAILS set " + "  OPD_VALIDATED_STATUS  = ? , OPD_UPDATED_BY=?, OPD_UPDATED_DATE= CURRENT_TIMESTAMP, OPD_REMARKS = ? "
			+ " where OES_PAYMENT_DTL_PK = ? ";

	String UPDATE_PAYMENT_REJECTION = " update OES_PAYMENT_DETAILS set " + "  OPD_VALIDATED_STATUS  = ? , OPD_UPDATED_BY=?, OPD_UPDATED_DATE= CURRENT_TIMESTAMP, OPD_REMARKS = ?"
			+ " where OES_PAYMENT_DTL_PK = ? ";

	String GET_PAYMENT_DETAILS_FOR_ENROLLMENT = "SELECT CHALLAN.OES_PAYMENT_DTL_PK, PM.OPTM_PAYMENT_DESC,CHALLAN.OPD_USER_FK "
			+ "FROM OES_PAYMENT_DETAILS CHALLAN, OES_CANDIDATE_DETAILS ED, OES_PAYMENT_MASTER PM " + "WHERE CHALLAN.OPD_STATUS ='N' "
			+ "AND CHALLAN.OPD_PAYMENT_FK = PM.OPTM_PAYMENT_PK " + "AND CHALLAN.OPD_USER_FK = ED.OCD_USER_FK AND CHALLAN.OPD_USER_FK = ?";

	// String DELETE_PAYMENT_DETAILS_FOR_ENROLLMENT = "DELETE FROM
	// OES_PAYMENT_DETAILS WHERE OPD_USER_FK = ?";
	String DELETE_PAYMENT_DETAILS_FOR_ENROLLMENT = "DELETE FROM OES_PAYMENT_DETAILS WHERE OPD_USER_FK = ? and (opd_status = 'N' OR  opd_status = 'A')";

	String UPDATE_PAYMENT_DETAILS_FOR_Current_Transaction = "update OES_PAYMENT_DETAILS set " + "	 OPD_VALIDATED_STATUS  = null"
			+ "	where OPD_USER_FK = ? and opd_payment_fk=1	";

	String opd_paymentStatusDateCheckUpdate = "UPDATE oes_payment_details SET opd_payment_status_date  = CURRENT_TIMESTAMP"
			+ "	WHERE opd_user_fk = ? AND opd_payment_fk=1";

	String NoTransactionFoundFlagUpdate = "update OES_PAYMENT_DETAILS set " + "	 opd_validated_status  = 'R'" + "	where OPD_USER_FK = ? and opd_payment_fk=1	";

	/*
	 * String SELECT_CANDIDATE_DETAILS_FOR_CHALLAN_RECEIPT =
	 * "SELECT OCD_CAND_TITLE, OCD_FIRST_NAME, OCD_MIDDLE_NAME, OCD_LAST_NAME, OCD_DATE_OF_BIRTH, OUM_EMAIL_ID, OUM_MOBILE_NO, OCD_COMM_PIN_CODE, OCD_COMM_CITY, OCD_COMM_ADDRESS, OCD_COMM_STATE_OTS, OCD_COMM_COUNTRY_FK, OCD_COMM_STATE_FK, OCD_COMM_UNION_TERR_FK, OCD_CATEGORY_FK, trim(to_char(ofm.ofm_fees ,'9999999.99')) as OFM_FEES, ofm.OFM_FEES_IN_WORDS"
	 * + " FROM oes_fees_master ofm, OES_CANDIDATE_DETAILS ocd, OES_USER_MASTER oum " + " where ocd.ocd_test_fk= ofm.ofm_test_fk and ocd.ocd_category_fk= ofm.ofm_category_fk " +
	 * " and oum.oum_user_pk = ocd.ocd_user_fk and ocd.OCD_USER_FK = ?";
	 */// commneted for epost challan data
	String SELECT_CANDIDATE_DETAILS_FOR_CHALLAN_RECEIPT = " SELECT spellNumericValue(otm_fees_open+12)||' only'  as feesInWords,(otm_fees_open+12)::varchar||'.00' as feesInWords1,OCD_FIRST_NAME, OCD_MIDDLE_NAME, OCD_LAST_NAME,oum_user_id,octm_category_code,OCD_DATE_OF_BIRTH,OUM_MOBILE_NO ,otm_fees_open::varchar||'.00' as otm_fees_open,otm_fees_in_words_open ,ocjm_applicationnumber,oum_user_pk "
			+ " FROM OES_CANDIDATE_DETAILS ocd " + "left join oes_candidate_job_mapping ocjm on ocjm_user_fk= ocd.ocd_user_fk "
			+ " left join oes_test_master otm on otm_test_pk=ocjm_test_fk " + " inner join  OES_USER_MASTER oum on oum.oum_user_pk = ocd.ocd_user_fk "
			+ " inner join oes_category_master on octm_category_pk=ocd_category_fk " + " where ocd.OCD_USER_FK = ? and otm_test_pk=?";

	/*
	 * String SELECT_CANDIDATE_DETAILS_FOR_PAYMENT_RECEIPT=
	 * " select * from  ( SELECT   row_number()over(order by opd_transaction_date desc) rn , (otm_fees_open+12)::varchar||'.00' as feesInWords1,  opd_amount," +
	 * " opd_transaction_date,oum_user_id,ocjm_applicationnumber,OCD_FIRST_NAME || ' ' || OCD_LAST_NAME as OCD_FIRST_NAME, OCD_MIDDLE_NAME,OCD_LAST_NAME," +
	 * " opd_txn_ref_no As opd_dd_challan_receipt_no,opd_payment_fk,optm_payment_desc,otm_test_pk,otm_test_name,opd_dd_date,opd_transaction_no,rec_trans_ser,rec_rect_no,opd_txn_type "
	 * + "	 FROM OES_USER_MASTER oum "+ "	 INNER JOIN OES_CANDIDATE_DETAILS ocd ON (oum.oum_user_pk = ocd.ocd_user_fk) " +
	 * "	 left join oes_candidate_job_mapping ocjm on ocjm_user_fk= ocd.ocd_user_fk  " + "	 left join oes_test_master otm on otm_test_pk=ocjm_test_fk  "+
	 * "	 left join oes_payment_details otp on otp.opd_user_fk=ocd.ocd_user_fk " + "	 left join oes_payment_master ts on ts.optm_payment_pk=opd_payment_fk " +
	 * "	 left join oes_epost_rec oer on oer.rec_apl_no= ocjm.ocjm_applicationnumber " + "	 where 1=1  AND ocd.OCD_USER_FK = ? and otm_test_pk=?)a  where rn = 1 " ;
	 */

	String SELECT_CANDIDATE_DETAILS_FOR_PAYMENT_RECEIPT_ENC1 = "SELECT * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY opd_transaction_date DESC) rn, opd_amount,"
			+ " opd_txn_date, pgp_sym_decrypt(oum_full_name::bytea,?,'cipher-algo=aes256') AS oum_full_name, optm.optm_payment_desc, "
			+ "	oum_middle_name, oum_last_name, oum_user_id, pgp_sym_decrypt(oum_full_name::bytea,?,'cipher-algo=aes256') AS oum_full_name,"
			+ " pgp_sym_decrypt(oum_initial::bytea,?,'cipher-algo=aes256') AS oum_initial, opd_txn_ref_no, opd_payment_fk, opd_transaction_no,"
			+ " opd_txn_type, opd_validated_status FROM oes_user_master oum INNER JOIN oes_candidate_details ocd ON ocd.ocd_user_fk = oum.oum_user_pk"
			+ " LEFT JOIN oes_payment_details opd ON opd.opd_user_fk=oum.oum_user_pk "
			+ " LEFT JOIN oes_payment_master optm ON optm.optm_payment_pk=opd.opd_payment_fk "
			+ " WHERE opd_payment_fk=1  AND oum.oum_user_pk = ?) "
			+ " a WHERE (opd_validated_status='A' and opd_payment_fk=1) ORDER BY opd_payment_fk DESC ";

	String SELECT_CANDIDATE_DETAILS_FOR_PAYMENT_RECEIPT = "select * from " + "( SELECT row_number()over(order by opd_transaction_date desc) rn ,  "
			+ "	opd_amount, opd_txn_date,oum_first_name,optm.optm_payment_desc, "
			+ "	oum_middle_name,oum_last_name, oum_user_id, ocd_first_name, OUM_INITIAL AS OUM_INITIAL,opd_txn_ref_no, "
			+ "	opd_payment_fk, opd_transaction_no,opd_txn_type ,opd_validated_status " + "FROM OES_USER_MASTER oum "
			+ "INNER JOIN OES_CANDIDATE_DETAILS ocd ON ocd.ocd_user_fk = oum.oum_user_pk  " + "left join oes_payment_details opd on opd.opd_user_fk=oum.oum_user_pk "
			+ "left join oes_payment_master optm on optm.optm_payment_pk=opd.opd_payment_fk " + "where opd_payment_fk=1  AND oum.oum_user_pk = ?) "
			+ "a  where (opd_validated_status='A' and opd_payment_fk=1) order by opd_payment_fk desc; ";

	String SELECT_CANDIDATE_DETAILS_FOR_challan_RECEIPT = "  select * from  ( SELECT   row_number()over(order by opd_transaction_date desc) rn ,   \r\n"
			+ "						opd_amount,  \r\n"
			+ "								 opd_transaction_date,ocd_date_of_birth ,oum_mobile_no,oum_first_name,oum_middle_name,oum_last_name, \r\n"
			+ "								 oum_user_id,OUM_CANDIDATE_NAME as OCD_FIRST_NAME, OUM_INITIAL AS OUM_INITIAL,  \r\n"
			+ "								 opd_dd_challan_receipt_no As opd_dd_challan_receipt_no,opd_payment_fk,optm_payment_desc,  \r\n"
			+ "								 opd_dd_date,opd_transaction_no,opd_txn_type    \r\n" + "							 FROM OES_USER_MASTER oum    \r\n"
			+ "							 INNER JOIN OES_CANDIDATE_DETAILS ocd ON (oum.oum_user_pk = ocd.ocd_user_fk)    \r\n" + "						\r\n"
			+ "							 left join oes_payment_details otp on otp.opd_user_fk=ocd.ocd_user_fk     \r\n"
			+ "							 left join oes_payment_master ts on ts.optm_payment_pk=opd_payment_fk     \r\n"
			+ "							 where 1=1  AND ocd.OCD_USER_FK = ? and opd_payment_fk=5 )a  where rn = 1;";

	String No_Do_Verified = " select ( " + "( " + " SELECT count(*) " + " FROM oes_candidate_additional_doc " + " WHERE ocad_user_fk = ? " + " AND ocad_checkbox = 'true' " + " ) "
			+ " -( " + " SELECT count(*) " + " FROM oes_candidate_additional_doc  " + " WHERE ocad_user_fk = ? " + " AND ocad_doc_verify_status = 'Confirmed' " + " ) "
			+ " ) as count";

	String COPY_PAYMENT_DETAILS_FOR_ENROLLMENT = "INSERT INTO oes_payment_details_history "
			+ "( SELECT * FROM OES_PAYMENT_DETAILS WHERE OPD_USER_FK = ? and OPD_STATUS ='N' and opd_payment_fk =1 )";

	String GET_APPROV_PAYMENT_COUNT = "select count(1) from OES_PAYMENT_DETAILS \r\n" + "    where OPD_PAYMENT_FK = 1 \r\n"
			+ "    and OPD_USER_FK = ? and opd_validated_status = 'A' ";

	String GET_APPROV_PAYMENT_COUNT1 = "select count(1) from OES_PAYMENT_DETAILS \r\n" + "    where OPD_PAYMENT_FK = 5 \r\n" + "    and OPD_USER_FK = ? and opd_status = 'N' ";

	String reconcilePaymentAjaxckeck = "select count(1) from OES_PAYMENT_DETAILS \r\n" + "    where OPD_PAYMENT_FK = 1 \r\n"
			+ "    and OPD_USER_FK = ? and opd_status = 'N' and (opd_validated_status is null or opd_validated_status='P' or opd_validated_status = '')";

	String GET_APPROV_PAYMENT_COUNT2 = "select count(1) from OES_PAYMENT_DETAILS  \r\n" + "		    where OPD_PAYMENT_FK = 5  \r\n" + "			    and OPD_USER_FK = ?   \r\n"
			+ "			    AND ( (opd_validated_status is null and OPD_CREATED_DATE ::timestamp < (now()-interval '4320 min' )))";
	// 4320 min means 72 hours and we can also use "(now()-interval '72 hour')"

	String auditTrailTimeCheck = "select count(1) from candidate_audit_trail \r\n"
			+ " where  oat_pk=(select max(oat_pk) from candidate_audit_trail where  oat_action ='check Status Button Click' and oat_user_fk = ? )\r\n"
			+ "and oat_audit_dt ::timestamp >= (now()-interval '10 min' )";

	String opd_paymentStatusDateCheck = "select count(1) from OES_PAYMENT_DETAILS where  opd_user_fk = ? and opd_payment_fk=1 and "
			+ "(opd_created_date ::TIMESTAMP >= (NOW()-INTERVAL '15 min') "
			+ "OR (opd_payment_status_date is not null and opd_payment_status_date ::timestamp >= (now()-interval '15 min' )))";

	// returns 1 if below n equal 10 minutes else 0

	String updatePaymentCreatedDate = " update OES_PAYMENT_DETAILS set " + "  OPD_CREATED_DATE= CURRENT_TIMESTAMP " + " where OPD_USER_FK = ? and OPD_PAYMENT_FK = 5 ";

	String Copytohistoryforchallan = "INSERT INTO oes_payment_details_history " + "( SELECT * FROM OES_PAYMENT_DETAILS WHERE OPD_USER_FK = ? and  OPD_PAYMENT_FK = 5 )";

	String GET_INCOMPLETE_ONLINE_PAYMENT_COUNT = "select count(1) from OES_PAYMENT_DETAILS \r\n" + "    where OPD_PAYMENT_FK = 1 \r\n" + "    and OPD_USER_FK = ?    \r\n"
			+ "    AND ( (opd_validated_status is null and OPD_CREATED_DATE ::timestamp > (now()-interval '45 min' ))\r\n" +
			// " OR \r\n" +
			// " ( opd_validated_status ='R' and OPD_ERROR_DESC = 'Transaction
			// Booked' \r\n" +
			// " and OPD_DD_DATE ::timestamp > (now()-interval '45 min' )) "
			") ";

	String GET_INCOMPLETE_ONLINE_PAYMENT_COUNT1 = "select count(1) from OES_PAYMENT_DETAILS \r\n" + "    where OPD_PAYMENT_FK = 5 \r\n" + "    and OPD_USER_FK = ?    \r\n"
			+ "    AND ( (opd_validated_status is null and OPD_CREATED_DATE ::timestamp >= (now()-interval '4320 min' )))";
	// 4320 min means 72 hours and we can also use "(now()-interval '72 hour')"

	String GET_REINITIATE_ONLINE_PAYMENT_COUNT = "select count(1) from OES_PAYMENT_DETAILS \r\n" + "    where OPD_PAYMENT_FK = 1 \r\n"
			+ "    and OPD_USER_FK = ? and opd_validated_status = 'R' ";
	// " and opd_error_desc like '%PLEASE DO NOT RE-INITIATE TRANSACTION%' ";

	String GET_BOOKED_ONLINE_PAYMENT_COUNT = " select count(*) from OES_PAYMENT_DETAILS \r\n"
			+ " where OPD_PAYMENT_FK = 1 and OPD_VALIDATED_STATUS ='R' AND OPD_TXN_TYPE = 'CASH' and OPD_USER_FK = ?  and OPD_ERROR_DESC = 'Transaction Booked' ";
	// " and OPD_ERROR_DESC = 'Transaction Booked'\r\n" +
	// " AND current_timestamp < OPD_DD_DATE ::timestamp + interval '6 days' ";

	String GET_UNDER_QUOTA_CHECK = "select owe_under_quota from oes_work_experience where owe_user_fk = ?";

	String SELECT_TRAINING_CENTRE_FOR_ACC_USER = " select ottcm.OTTCM_TRAINING_CENTRE_PK from OES_USER_MASTER usm, OES_TP_TRAINING_CENTRE_MASTER ottcm "
			+ " where usm.OUM_USER_PK = ottcm.OTTCM_USER_FK and ottcm.OTTCM_USER_FK = ? ";

	String SELECT_CANDIDATE_DETAILS_FOR_PAYMENT_URL = "SELECT oum.oum_full_name,ocd.ocd_COMM_ADDRESS,ocd.OCD_COMM_STATE_FK,ocd.OCD_COMM_DISTRICT_FK,\r\n"
			+ "			ocd.ocd_COMM_CITY,ocd.OCD_COMM_PIN_CODE\r\n"
			+ "			 FROM oes_user_master oum INNER JOIN oes_candidate_details ocd ON oum.oum_user_pk = ocd.ocd_user_fk \r\n" + "			 WHERE ocd.OCD_USER_FK = ?";

	// Added for auto payment approval
	String INSERT_AUTO_APPROVE_PAYMENT = " INSERT INTO OES_PAYMENT_DETAILS (OES_PAYMENT_DTL_PK, OPD_PAYMENT_FK, OPD_USER_FK, OPD_VALIDATED_STATUS, "
			+ " OPD_AMOUNT, OPD_STATUS, OPD_CREATED_BY, OPD_CREATED_DATE, OPD_RECONCILE_FLAG)"
			+ " VALUES (nextVal('OES_PAYMENT_DETAILS_SEQ'), 1, ?, 'A', ?, 'A', ?, CURRENT_TIMESTAMP, 'Y')";

	String UPDATE_AUTO_APPROVE_PAYMENT = "update oes_payment_details set opd_status = 'A' , opd_validated_status='A', opd_created_date = CURRENT_TIMESTAMP where opd_user_fk = ? and opd_created_by = ?";

	String INSERT_AUTO_REJECT_PAYMENT = " INSERT INTO OES_PAYMENT_DETAILS (OES_PAYMENT_DTL_PK, OPD_PAYMENT_FK, OPD_USER_FK, OPD_VALIDATED_STATUS, "
			+ " OPD_AMOUNT, OPD_STATUS, OPD_CREATED_BY, OPD_CREATED_DATE, OPD_RECONCILE_FLAG)"
			+ " VALUES (nextVal('OES_PAYMENT_DETAILS_SEQ'), 1, ?, 'R', ?, 'R', ?, CURRENT_TIMESTAMP, 'Y')";
	String UPDATE_AUTO_REJECT_PAYMENT = "update oes_payment_details set opd_status = 'R' , opd_validated_status='R', opd_created_date = CURRENT_TIMESTAMP where opd_user_fk = ? and opd_created_by = ?";
}
