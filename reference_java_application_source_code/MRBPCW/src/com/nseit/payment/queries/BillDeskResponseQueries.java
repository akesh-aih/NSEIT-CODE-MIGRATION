package com.nseit.payment.queries;

public interface BillDeskResponseQueries {

	
	String INSERT_ONLINE_DETAILS = "INSERT INTO OES_PAYMENT_DETAILS" +
			" ( OES_PAYMENT_DTL_PK, OPD_PAYMENT_FK, OPD_USER_FK, " +
			"  OPD_TRANSACTION_DATE, OPD_TRANSACTION_NO, OPD_VALIDATED_STATUS, " +
			"  OPD_CURRENCY, OPD_STATUS, OPD_CREATED_BY, " +
			"  OPD_CREATED_DATE, OPD_MERCHANT_ID, OPD_CUSTOMER_ID, " +
			"  OPD_TXN_REF_NO, OPD_BANK_REF_NO, OPD_TXN_AMOUNT, " +
			"  OPD_BANK_ID, OPD_TXN_TYPE, OPD_SECURITY_ID,  " +
			" OPD_TXN_DATE, OPD_AUTHENTICATE_STATUS, OPD_ERROR_DESC, " +
			"  OPD_ERROR_STATUS, OPD_CHECK_SUM,OPD_AMOUNT)" +
			" VALUES ( OES_PAYMENT_DETAILS_SEQ.nextval,? ,? ,?,? ,? " +
			" ,?,? ,? ,sysdate, ?,? ,?,? " +
			" ,? ,?,? ,? ,?, " +
			" ?,? ,?,?,?)";
}
