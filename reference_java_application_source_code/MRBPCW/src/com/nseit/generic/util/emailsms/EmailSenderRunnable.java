package com.nseit.generic.util.emailsms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
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

public class EmailSenderRunnable implements Runnable
{
	private Integer failureCount;
	
	@Override
	public void run()
	{
		int delayBetweenEmails = 0;
		Logger logger = LoggerHome.getLogger(getClass());
		List<EmailSMSTransactionBean> lstEmailSMSBeans = null;
		EmailSMSTransactionBean emailSMSTransactionBean = null;
		int maxRecords = 100;
		ApplicationContext applicationContext = null;
		int currRecordCount = 0;
		int currRecord=0;
		JavaMailSender mailSender = null;
		CommonService commonService = null;
		
		try
		{
			delayBetweenEmails =Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DELAY_BETWEEN_EMAIL_IN_SEC)) * 100 * (failureCount+1);
			applicationContext = SpringUtil.getInstance().getApplicationContext();
			mailSender = (JavaMailSender) applicationContext.getBean("mailSender");
			ExecutorService threadExecutorService = Executors.newFixedThreadPool(20);	
			while (true)
			{
				try
				{
					//System.out.println("FailureCount -- " + failureCount + " Start Time "+new Date());
					commonService = (CommonService) applicationContext.getBean("commonService");	
					
						lstEmailSMSBeans = commonService.getEmailForMailing(failureCount, maxRecords);
						//System.out.println("----------------"+failureCount + " Got " + lstEmailSMSBeans.size() + " Candidate" );
						//System.out.println("Email Count: "+lstEmailSMSBeans.size());
						if(lstEmailSMSBeans != null && lstEmailSMSBeans.size()>0)
						{
							currRecordCount = lstEmailSMSBeans.size();
							for(currRecord=0; currRecord<currRecordCount; currRecord++)
							{
								emailSMSTransactionBean = lstEmailSMSBeans.get(currRecord);									
								threadExecutorService.submit(new EmailSenderTask(emailSMSTransactionBean,mailSender));
								//EmailSMSUtil.sendEmail(emailSMSTransactionBean, mailSender);
								//EmailSMSUtil.sendEmailMailBySite(emailSMSTransactionBean, mailSender);
								emailSMSTransactionBean.setSentStatus("Y");
							//	System.out.println("-----------sending Email to -----"+ emailSMSTransactionBean.getToAddress());
								
							}
							
							commonService.updateEmailSMSStatusPostSending(lstEmailSMSBeans);
							commonService = null;
						}
					
				}
				catch (Exception ex)
				{
					List<EmailSMSTransactionBean> list = new ArrayList<EmailSMSTransactionBean>(); 
					emailSMSTransactionBean.setEmailSmsFailureCount(emailSMSTransactionBean.getEmailSmsFailureCount()!=null?emailSMSTransactionBean.getEmailSmsFailureCount():0 + 1);
					emailSMSTransactionBean.setSentStatus("D");
					list.add(emailSMSTransactionBean);
					commonService.updateEmailSMSStatusPostSending(list);
					logger.fatal("Error in run method of EmailSenderRunnable", ex);
				
					ex.printStackTrace();
					 
				}
				
				try
				{
					Thread.sleep(delayBetweenEmails);
				}
				catch (InterruptedException ex)
				{
					logger.fatal("Error in run method of EmailSenderRunnable", ex); 
				}
				//System.out.println("failureCount -- " + failureCount + " End Time "+new Date());
			}
		}
		catch (Exception ex)
		{
			logger.fatal("Error Initilizing Email Mailer", ex);
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
