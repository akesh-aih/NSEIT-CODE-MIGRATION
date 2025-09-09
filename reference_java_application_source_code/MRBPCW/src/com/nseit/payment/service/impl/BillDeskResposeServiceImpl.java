package com.nseit.payment.service.impl;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.logging.log4j.Logger;

import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.payment.dao.BillDeskResposeDAO;
import com.nseit.payment.models.BillDeskResponseBean;
import com.nseit.payment.service.BillDeskResposeService;

public class BillDeskResposeServiceImpl implements BillDeskResposeService {

	private Logger logger = LoggerHome.getLogger(getClass());
	private BillDeskResposeDAO billDeskResposeDAO;
	private CommonDao commonDao;

	public void setBillDeskResposeDAO(BillDeskResposeDAO billDeskResposeDAO) {
		this.billDeskResposeDAO = billDeskResposeDAO;
	}
	
	
	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}



	@Override
	public BillDeskResponseBean insertOnlinTransactionDetails(String responseMsg,Users users) throws Exception {
		int saveUpdate  = 0;
		BillDeskResponseBean billDeskResponseBean = new BillDeskResponseBean();
		try {
			StringTokenizer  stringTokenizer  = new StringTokenizer(responseMsg,"|");
			java.util.List <String> list = new ArrayList<String>();
			while (stringTokenizer.hasMoreElements()) {
				String tempVal = (String)stringTokenizer.nextElement();
				if (tempVal!=null) {
					list.add(tempVal);
				}
			}
			
			billDeskResponseBean.setMerchantId(list.get(0));
			billDeskResponseBean.setCustomerId(list.get(1));
			billDeskResponseBean.setTxnReferenceNo(list.get(2));
			billDeskResponseBean.setBankReferenceNo(list.get(3));
			billDeskResponseBean.setTxnAmount(list.get(4));
			billDeskResponseBean.setBankId(list.get(5));
			billDeskResponseBean.setBankMerchantId(list.get(6));
			billDeskResponseBean.setTxnType(list.get(7));
			
			if(billDeskResponseBean.getBankId().equals(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.TYPE_OF_PAYMENT))){
				logger.info("Payment Type is Credit Card");
				billDeskResponseBean.setTxnType("2");	
			}else{
				logger.info("Payment Type is NetBanking/Debit Card");
				billDeskResponseBean.setTxnType("1");
			}
			
			billDeskResponseBean.setSecurityID(list.get(11));
			billDeskResponseBean.setAuthStatus(list.get(14));
			billDeskResponseBean.setErrorStatus(list.get(23));
			billDeskResponseBean.setErrorDescription(list.get(24));
			billDeskResponseBean.setCheckSum(list.get(25));
			billDeskResponseBean.setTxnDate(list.get(13));
			
			if(billDeskResponseBean.getAuthStatus().equals("0300")){
				logger.info("Payment is Approved");
				billDeskResponseBean.setValidateStatus(GenericConstants.PAYMENT_APP_FLAG);
				saveUpdate = billDeskResposeDAO.insertOnlinTransactionDetails(billDeskResponseBean,users);
			}else{
				logger.info("Payment is Rejected");
				billDeskResponseBean.setValidateStatus(GenericConstants.PAYMENT_REJ_FLAG);
				saveUpdate = billDeskResposeDAO.insertOnlinTransactionDetails(billDeskResponseBean,users);
			}
			if (saveUpdate>0){
				logger.info("Database Insert is Successfull");
				
				billDeskResponseBean.setSuccessFlag("true");
			}else{
				logger.info("Database Insert is Failed");
				
				billDeskResponseBean.setSuccessFlag("false");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return billDeskResponseBean;
	}
	
	
}

