package com.nseit.payment.service;

import com.nseit.generic.models.Users;
import com.nseit.payment.models.BillDeskResponseBean;

public interface BillDeskResposeService {

	BillDeskResponseBean insertOnlinTransactionDetails(String responseMsg,Users users) throws Exception;
	
	

}
