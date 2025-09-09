package com.nseit.generic.util.emailsms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;

import com.nseit.generic.models.EmailSMSTransactionBean;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.SpringUtil;

public class SMSSenderRunnable implements Runnable
{
	private Integer failureCount;
	
	@Override
	public void run()
	{
		int delayBetweenSMS = 0;
		Logger logger = LoggerHome.getLogger(getClass());
		List<EmailSMSTransactionBean> lstEmailSMSBeans = null;
		EmailSMSTransactionBean emailSMSTransactionBean = null;
		int maxRecords = 100;
		ApplicationContext applicationContext = null;
		int currRecordCount = 0;
		int currRecord=0;
		JavaMailSender mailSender = null;
		CommonService commonService = null;
		String vendorName=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.VENDOR_NAME);
		try
		{
			delayBetweenSMS = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DELAY_BETWEEN_SMS_IN_SEC)) * 100 * (failureCount+1);
			applicationContext = SpringUtil.getInstance().getApplicationContext();
			ExecutorService threadExecutorService = Executors.newFixedThreadPool(20);	
			while (true)
			{
				try
				{
					commonService = (CommonService) applicationContext.getBean("commonService");		
					
					
					lstEmailSMSBeans = commonService.getSMSForSending(failureCount, maxRecords);
						//System.out.println("SMS Count: "+lstEmailSMSBeans.size());	
						if(lstEmailSMSBeans != null && lstEmailSMSBeans.size()>0)
						{
							currRecordCount = lstEmailSMSBeans.size();
							for(currRecord=0; currRecord<currRecordCount; currRecord++)
							{
								emailSMSTransactionBean = lstEmailSMSBeans.get(currRecord);							
								//threadExecutorService.submit(new EmailSmsSenderTask(emailSMSTransactionBean,mailSender));
								if(null != vendorName && vendorName.equalsIgnoreCase("valuefirst"))
	                threadExecutorService.submit(new EmailSmsSenderTask(emailSMSTransactionBean,mailSender));
	              else
	                threadExecutorService.submit(new SmsSenderNetCore(emailSMSTransactionBean,mailSender));
	              
								//emailSMSTransactionBean = lstEmailSMSBeans.get(currRecord);
								
								//EmailSMSUtil.sendSms(emailSMSTransactionBean);
								//EmailSMSUtil.sendSmsNewChanged(emailSMSTransactionBean);
								emailSMSTransactionBean.setSentStatus("Y");
							}
							
							commonService.updateEmailSMSStatusPostSending(lstEmailSMSBeans);
							commonService = null;
						}
					
				}
				catch (Exception ex)
				{
					List<EmailSMSTransactionBean> list = new ArrayList<EmailSMSTransactionBean>(); 
					
					emailSMSTransactionBean.setEmailSmsFailureCount(emailSMSTransactionBean.getEmailSmsFailureCount() + 1);
					
					
					emailSMSTransactionBean.setSentStatus("D");
					list.add(emailSMSTransactionBean);
					commonService.updateEmailSMSStatusPostSending(list);
					logger.fatal("Error in run method of EmailSenderRunnable", ex);
					
					
				}
				
				try
				{
					Thread.sleep(delayBetweenSMS);
				}
				catch (InterruptedException ex)
				{
					logger.fatal("Error in run method of EmailSenderRunnable", ex); 
				}
			}
		}
		catch (Exception ex)
		{
			logger.fatal("Error Initilizing SMS Sender", ex);
		}
	}

	public void setFailureCount(Integer failureCount)
	{
		this.failureCount = failureCount;
	}

	public Integer getFailureCount()
	{
		return failureCount;
	}
}