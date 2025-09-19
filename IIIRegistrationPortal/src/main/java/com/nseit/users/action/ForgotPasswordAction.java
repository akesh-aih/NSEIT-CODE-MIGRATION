package com.nseit.users.action;

import com.nseit.users.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.ServletRequestAware;
import jakarta.servlet.http.HttpServletRequest;

public class ForgotPasswordAction extends ActionSupport implements ServletRequestAware {

    private static final Logger logger = LogManager.getLogger(ForgotPasswordAction.class);

    private String loginId;
    private String emailId;

    private UserService userService;

    // Setter for UserService (Spring injection)
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private HttpServletRequest request;

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
        logger.info("ServletRequestAware: HttpServletRequest set for ForgotPasswordAction.");
    }

    // Action method to display the forgot password form
    public String input() {
        return INPUT;
    }

    // Action method to handle the reset password submission
    public String resetPassword() {
        // Manually retrieve parameters from HttpServletRequest
        String submittedLoginId = request.getParameter("loginId");
        String submittedEmailId = request.getParameter("emailId");

        // Set them on the action properties
        this.setLoginId(submittedLoginId);
        this.setEmailId(submittedEmailId);

        logger.info("Forgot password request for Login ID: {} and Email ID: {}", loginId, emailId);
        try {
            // Call the service to handle password reset logic
            userService.resetPassword(loginId, emailId);
            addActionMessage("A new password has been sent to your registered email ID.");
            return SUCCESS;
        } catch (Exception e) {
            logger.error("Forgot password failed for Login ID {}: {}", loginId, e.getMessage(), e);
            addActionError(e.getMessage()); // Display error message from service
            return INPUT; // Return to the form on error
        }
    }

    // Getters and Setters for form fields
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
