/**
 * 
 */
package com.nseit.syncutility.quartz;

import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.nseit.generic.util.SpringUtil;
import com.nseit.payment.action.SBIEPayResponseAction;


/**
 * @author nirajs
 *
 */
public class Sbiepay_Bulk_offline_Call  implements Job {
	Logger logger = com.nseit.generic.util.LoggerHome.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Bulk Offline Sbiepay Call To Payment_Job For getDoubleVerificationResponseBulk Call.");
		
		try {
			logger.info("Start Payment_Job Execution on :"+new Date());
			
			ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
			SBIEPayResponseAction paymentProcessJobsQuartz = (SBIEPayResponseAction) applicationContext.getBean("sBIEPayResponseAction");
			//paymentProcessJobsQuartz.getDoubleVerificationResponseBulk();
			
			logger.info("End Payment_Job Execution on :"+new Date());
			
		}  catch (Exception e) {
			logger.fatal(e, e);
		} catch (Throwable e) {
			logger.fatal(e, e);
		} 
}

}
