package com.nseit.generic.util.emailsms;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.nseit.generic.models.EmailSMSTransactionBean;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
public class EmailSenderTask implements Runnable
{
	
	EmailSMSTransactionBean emailSMSTransactionBean = null;
	JavaMailSender mailSender = null;
	
	@Override
	public void run()
	{
		try {
			MimeMessage message = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setText(emailSMSTransactionBean.getSmsEmailContent(),true);
			helper.setSubject(emailSMSTransactionBean.getEmailSubject());
			/*if (emailSMSTransactionBean.getFromAddress() != null )
				helper.setFrom(emailSMSTransactionBean.getFromAddress());*/
			helper.setFrom(new InternetAddress(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SENDER)));
			if (emailSMSTransactionBean.getToAddress() != null && emailSMSTransactionBean.getToAddress().size() > 0)
				helper.setTo(emailSMSTransactionBean.getToAddress().toArray(new String[0]));
				//mailMessage.setTo(emailSMSTransactionBean.getToAddress().toArray(new String[0]));
			   

			if (emailSMSTransactionBean.getCcAddress() != null && emailSMSTransactionBean.getCcAddress().size() > 0)
				helper.setCc(emailSMSTransactionBean.getCcAddress().toArray(new String[0]));
				//mailMessage.setCc(emailSMSTransactionBean.getCcAddress().toArray(new String[0]));

			if (emailSMSTransactionBean.getBccAddress() != null && emailSMSTransactionBean.getBccAddress().size() > 0)
				helper.setBcc(emailSMSTransactionBean.getBccAddress().toArray(new String[0]));
				//mailMessage.setBcc(emailSMSTransactionBean.getBccAddress().toArray(new String[0]));
			
			mailSender.send(message);
			System.out.println("--------------------sending Email to "+ emailSMSTransactionBean.getToAddress());
			
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public EmailSenderTask(EmailSMSTransactionBean emailSMSTransactionBean,
			JavaMailSender mailSender) {
		super();
		this.emailSMSTransactionBean = emailSMSTransactionBean;
		this.mailSender = mailSender;
	}

	
}
