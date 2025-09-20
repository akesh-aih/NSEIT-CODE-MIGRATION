//package com.nseit.users.service.impl;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.struts2.ActionSupport; // Keep this if ActionSupport.SUCCESS is used
//
//import com.nseit.generic.util.LoginEncryptor;
//import com.nseit.generic.util.emailsms.EmailUtil;
//import com.nseit.users.dao.LoginDao; // Use LoginDao
//import com.nseit.users.models.User;
//import com.nseit.users.service.LoginService; // Implement LoginService
//
//import com.nseit.generic.util.GenericException;
//
//public class LoginServiceImpl implements LoginService {
//
//    private static final Logger logger = LogManager.getLogger(LoginServiceImpl.class);
//
//    private LoginDao loginDao;
//
//    public void setLoginDao(LoginDao loginDao) {
//        this.loginDao = loginDao;
//    }
//
//    private EmailUtil emailUtil;
//
//    public void setEmailUtil(EmailUtil emailUtil) {
//        this.emailUtil = emailUtil;
//    }
//
//    @Override
//    public User authenticate(String username, String password) {
//        logger.info("Authenticating user: {}", username);
//
//        User user = loginDao.findByUsername(username);
//
//        if (user == null) {
//            logger.warn("Authentication failed: User not found - {}", username);
//            throw new GenericException("Invalid credentials");
//        }
//
//        if (user.isSuspended()) {
//            logger.warn("Authentication failed: User is suspended - {}", username);
//            throw new GenericException("User has been suspended.");
//        }
//
//        if (!user.isActive()) {
//            logger.warn("Authentication failed: User is inactive - {}", username);
//            throw new GenericException("User is not active.");
//        }
//
//        // Encrypt the provided password for comparison
//        String encryptedProvidedPassword;
//        try {
//            encryptedProvidedPassword = LoginEncryptor.encrypt(password);
//        } catch (Exception e) {
//            logger.error("Error encrypting provided password: {}", e.getMessage(), e);
//            throw new GenericException("Authentication failed due to encryption error", e);
//        }
//        logger.debug("Encrypted Password - {}",encryptedProvidedPassword);
//        
//        if (!encryptedProvidedPassword.equals(user.getPassword())) {
//            logger.warn("Authentication failed: Invalid password for user - {}", username);
//            throw new GenericException("Invalid credentials");
//        }
//
//        if (user.isChangePasswordOnNextLogin()) {
//            logger.info("User must change password on next login: {}", username);
//            throw new GenericException("Change password before login.");
//        }
//
//        // Authentication successful, update login status
//        logger.info("Before logLogin - User ID: {}, User Login ID: {}", user.getUserId(), user.getUserLoginId());
//        loginDao.logLogin(user.getUserId(), user.getUserLoginId());
//        loginDao.updateLoginStatus(user.getUserLoginId());
//
//        logger.info("User authenticated successfully: {}", username);
//
//        // The full user object with all details is returned
//        return user;
//    }
//
//    @Override
//    public void resetPassword(String loginId, String emailId) {
//        logger.info("Attempting to reset password for Login ID: {} and Email ID: {}", loginId, emailId);
//
//        // 1. Verify User
//        User user = loginDao.findUserByLoginIdAndEmail(loginId, emailId);
//
//        if (user == null) {
//            logger.warn("Password reset failed: User not found or email mismatch for Login ID: {}", loginId);
//            throw new GenericException("Invalid Login ID or Email ID.");
//        }
//
//        // 2. Status Checks (from SP logic)
//        if (!user.isActive()) {
//            logger.warn("Password reset failed: User is inactive - {}", loginId);
//            throw new GenericException("User ID is inactive. Please contact Technical Support");
//        }
//
//        if (user.isSuspended()) {
//            logger.warn("Password reset failed: User is suspended - {}", loginId);
//            throw new GenericException("User ID is suspended. Please contact Technical Support");
//        }
//
//        // 3. Generate New Passwords
//        String newPassword = java.util.UUID.randomUUID().toString().substring(0, 8);
//        String newTrxnPassword = java.util.UUID.randomUUID().toString().substring(0, 8);
//
//        logger.info("Generated new password for {}: {}", loginId, newPassword);
//        logger.info("Generated new transaction password for {}: {}", loginId, newTrxnPassword);
//
//
//        // 4. Encrypt New Passwords
//        String encryptedNewPassword;
//        String encryptedNewTrxnPassword;
//        try {
//            encryptedNewPassword = LoginEncryptor.encrypt(newPassword);
//            encryptedNewTrxnPassword = LoginEncryptor.encrypt(newTrxnPassword);
//        } catch (Exception e) {
//            logger.error("Error encrypting new password for {}: {}", loginId, e.getMessage(), e);
//            throw new GenericException("Failed to encrypt new password.", e);
//        }
//
//        // 5. Update Database
//        loginDao.updatePassword(loginId, encryptedNewPassword, encryptedNewTrxnPassword, true, false);
//        logger.info("Password and transaction password updated for Login ID: {}", loginId);
//
//
//        // 6. Send Email
//        String subject = "Your Password for IIIRegistrationPortal has been Reset";
//        String body = "Dear User,<br><br>"
//                    + "Your password for IIIRegistrationPortal has been reset.<br>"
//                    + "Your new Login Password is: <b>" + newPassword + "</b><br>"
//                    + "Your new Transaction Password is: <b>" + newTrxnPassword + "</b><br><br>"
//                    + "Kindly change your password on next login.<br><br>"
//                    + "Thanks and Regards,<br>IIIRegistrationPortal Team";
//        try {
//            emailUtil.sendEmail(emailId, subject, body);
//            logger.info("Email with new password sent to {} (Login ID: {})", emailId, loginId);
//        } catch (Exception e) {
//            logger.error("Failed to send password reset email to {} (Login ID: {}): {}", emailId, loginId, e.getMessage(), e);
//            throw new GenericException("Failed to send password reset email. Please contact support.", e);
//        }
//    }
//
//    @Override
//    public String changePassword(String loginId, String oldPassword, String newPassword) {
//        logger.info("Attempting to change password for Login ID: {}", loginId);
//
//        // 1. User Verification and Status Checks
//        User user = loginDao.findByUsername(loginId);
//
//        if (user == null) {
//            logger.warn("Change password failed: User not found for Login ID: {}", loginId);
//            return "Invalid Login ID";
//        }
//
//        if (!user.isActive()) {
//            logger.warn("Change password failed: User is inactive - {}", loginId);
//            return "User ID is inactive. Please contact Technical Support";
//        }
//
//        if (user.isSuspended()) {
//            logger.warn("Change password failed: User is suspended - {}", loginId);
//            return "User ID is suspended. Please contact Technical Support";
//        }
//
//        // 2. Verify Old Password
//        String encryptedOldPassword;
//        try {
//            encryptedOldPassword = LoginEncryptor.encrypt(oldPassword);
//        } catch (Exception e) {
//            logger.error("Error encrypting old password for {}: {}", loginId, e.getMessage(), e);
//            return "Failed to encrypt old password.";
//        }
//
//        if (!encryptedOldPassword.equals(user.getPassword())) {
//            logger.warn("Change password failed: Invalid old password for Login ID: {}", loginId);
//            return "Invalid Old Password";
//        }
//
//        // 3. Encrypt New Password
//        String encryptedNewPassword;
//        try {
//            encryptedNewPassword = LoginEncryptor.encrypt(newPassword);
//        } catch (Exception e) {
//            logger.error("Error encrypting new password for {}: {}", loginId, e.getMessage(), e);
//            return "Failed to encrypt new password.";
//        }
//
//        // 4. Update Database
//        loginDao.updatePassword(loginId, encryptedNewPassword, user.getTransactionPassword(), false, false);
//        logger.info("Password changed successfully for Login ID: {}", loginId);
//
//        return ActionSupport.SUCCESS;
//    }
//}
