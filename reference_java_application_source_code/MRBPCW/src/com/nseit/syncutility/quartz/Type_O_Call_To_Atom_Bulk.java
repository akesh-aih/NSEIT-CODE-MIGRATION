package com.nseit.syncutility.quartz;

import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.nseit.generic.util.SpringUtil;
import com.nseit.payment.action.AtomResponseAction;

public class Type_O_Call_To_Atom_Bulk implements Job{
	Logger logger = com.nseit.generic.util.LoggerHome.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Type O Call To Payment_Job For AIPay Offline Call.");
		try {
			logger.info("Start Payment_Job Execution on :"+new Date());
			ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
			AtomResponseAction paymentProcessJobsQuartz = (AtomResponseAction) applicationContext.getBean("atomResponseAction");
			paymentProcessJobsQuartz.getDoubleVerificationResponseBulk();
			logger.info("End Payment_Job Execution on :"+new Date());
			}catch (Exception e) {
				logger.fatal(e, e);
				}
		}
}
