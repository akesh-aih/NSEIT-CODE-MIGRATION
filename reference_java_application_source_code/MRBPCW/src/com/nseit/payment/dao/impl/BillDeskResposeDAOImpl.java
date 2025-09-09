package com.nseit.payment.dao.impl;

import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.payment.dao.BillDeskResposeDAO;
import com.nseit.payment.models.BillDeskResponseBean;
import com.nseit.payment.queries.BillDeskResponseQueries;

public class BillDeskResposeDAOImpl extends BaseDAO implements BillDeskResposeDAO {
	
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}
	
	private Logger logger = LoggerHome.getLogger(getClass());
	@Override
	public int insertOnlinTransactionDetails(BillDeskResponseBean billDeskResponseBean,Users users) throws Exception {
		int saveUpdate = 0;
		try {
			saveUpdate = writeJdbcTemplate.update(BillDeskResponseQueries.INSERT_ONLINE_DETAILS,
					new Object[]{
								billDeskResponseBean.getTxnType(),
								users.getUserId(),
								billDeskResponseBean.getTxnDate(),
								billDeskResponseBean.getTxnReferenceNo(),
								billDeskResponseBean.getValidateStatus(),
								ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.CURRENCY_TYPE),
								"A",
								users.getUsername(),
								billDeskResponseBean.getMerchantId(),
								billDeskResponseBean.getCustomerId(),
								billDeskResponseBean.getTxnReferenceNo(),
								billDeskResponseBean.getBankReferenceNo(),
								billDeskResponseBean.getTxnAmount(),
								billDeskResponseBean.getBankId(),
								billDeskResponseBean.getTxnType(),
								billDeskResponseBean.getSecurityID(),
								billDeskResponseBean.getTxnDate(),
								billDeskResponseBean.getAuthStatus(),
								billDeskResponseBean.getErrorDescription(),
								billDeskResponseBean.getErrorStatus(),
								billDeskResponseBean.getCheckSum(),
								billDeskResponseBean.getTxnAmount()
					}
			);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return saveUpdate;
	}

}
