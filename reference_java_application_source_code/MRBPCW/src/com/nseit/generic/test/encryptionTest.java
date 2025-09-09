package com.nseit.generic.test;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nseit.payment.models.PayInstrument;

public class encryptionTest {

	public static void main(String[] args) throws Exception {
	    try
		{
			String response = "[{\"settlementDetails\":{\"reconStatus\":\"NRNS\"},\"merchDetails\":{\"merchId\":317156,\"merchTxnId\":\"TEGDEV-31\",\"merchTxnDate\":\"2023-06-14 17:34:50\"},\"payDetails\":{\"atomTxnId\":11000000387288,\"product\":\"NSE\",\"amount\":200.00,\"surchargeAmount\":0.00,\"totalAmount\":200.00},\"payModeSpecificData\":{\"subChannel\":\"NB\",\"bankDetails\":{\"bankTxnId\":\"NToj8vp6vSatWpgqCDdQ\",\"otsBankName\":\"Atom Bank\"}},\"responseDetails\":{\"statusCode\":\"OTS0000\",\"message\":\"SUCCESS\",\"description\":\"\"}}]";

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
			objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			List<PayInstrument> responseList = objectMapper.readValue(response,new TypeReference<List<PayInstrument>>() {});
			PayInstrument payInstrument = responseList.get(0);
			System.out.println(payInstrument.getMerchDetails().getMerchTxnDate());
		}
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
};