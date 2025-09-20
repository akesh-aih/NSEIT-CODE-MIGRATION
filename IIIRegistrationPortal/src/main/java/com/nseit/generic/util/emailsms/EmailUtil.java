//package com.nseit.generic.util.emailsms;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;
//
//public class EmailUtil {
//
//    private static final Logger logger = LogManager.getLogger(EmailUtil.class);
//
//    // Configuration properties for email server
//    private String host;
//    private String port;
//    private String username;
//    private String password;
//    private boolean auth;
//    private boolean starttlsEnable;
//
//    // Setters for Spring injection
//    public void setHost(String host) {
//        this.host = host;
//    }
//
//    public void setPort(String port) {
//        this.port = port;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setAuth(boolean auth) {
//        this.auth = auth;
//    }
//
//    public void setStarttlsEnable(boolean starttlsEnable) {
//        this.starttlsEnable = starttlsEnable;
//    }
//
//    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
//        logger.info("Attempting to send email to: {} with subject: {}", toEmail, subject);
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.port", port);
//        props.put("mail.smtp.auth", auth ? "true" : "false");
//        props.put("mail.smtp.starttls.enable", starttlsEnable ? "true" : "false");
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username)); // Sender
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail)); // Recipient
//            message.setSubject(subject);
//            message.setContent(body, "text/html"); // Assuming HTML content
//
//            Transport.send(message);
//            logger.info("Email sent successfully to: {}", toEmail);
//        } catch (MessagingException e) {
//            logger.error("Failed to send email to {}: {}", toEmail, e.getMessage(), e);
//            throw e; // Re-throw to be handled by calling service
//        }
//    }
//}
