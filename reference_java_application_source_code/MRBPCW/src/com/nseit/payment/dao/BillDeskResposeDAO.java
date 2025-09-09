package com.nseit.payment.dao;

import com.nseit.generic.models.Users;
import com.nseit.payment.models.BillDeskResponseBean;

public interface BillDeskResposeDAO {
	
	public int insertOnlinTransactionDetails(BillDeskResponseBean billDeskResponseBean,Users users) throws Exception; 

}
