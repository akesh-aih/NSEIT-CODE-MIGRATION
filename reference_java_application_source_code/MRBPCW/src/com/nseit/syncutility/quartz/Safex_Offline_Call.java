package com.nseit.syncutility.quartz;

import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.nseit.generic.util.SpringUtil;
import com.nseit.payment.action.SafexResponseAction;

public class Safex_Offline_Call {
	Logger logger = com.nseit.generic.util.LoggerHome.getLogger(this.getClass());

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Bulk Offline Safex Call To Payment_Job For getDoubleVerificationResponseBulk Call.");
		
		try {
			logger.info("Start Payment_Job Execution on :"+new Date());
			
			ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
			SafexResponseAction paymentProcessJobsQuartz = (SafexResponseAction) applicationContext.getBean("safexResponseAction");
			paymentProcessJobsQuartz.getDoubleVerificationResponseBulk();
			
			logger.info("End Payment_Job Execution on :"+new Date());
			
		}  catch (Exception e) {
			logger.fatal(e, e);
		} catch (Throwable e) {
			logger.fatal(e, e);
		} 
}

}
