package com.nseit.users.action;

import com.nseit.users.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.ServletRequestAware;
import jakarta.servlet.http.HttpServletRequest;

public class ChangePasswordAction extends ActionSupport implements ServletRequestAware {

    private static final Logger logger = LogManager.getLogger(ChangePasswordAction.class);

    private String loginId;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    private UserService userService;
    private HttpServletRequest request;

    // Setter for UserService (Spring injection)
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
        logger.info("ServletRequestAware: HttpServletRequest set for ChangePasswordAction.");
    }

    // Action method to display the change password form
    public String input() {
        return INPUT;
    }

    // Action method to handle the change password submission
    public String changePassword() {
        // Manually retrieve parameters from HttpServletRequest
        String submittedLoginId = request.getParameter("loginId");
        String submittedOldPassword = request.getParameter("oldPassword");
        String submittedNewPassword = request.getParameter("newPassword");
        String submittedConfirmNewPassword = request.getParameter("confirmNewPassword");

        // Set them on the action properties
        this.setLoginId(submittedLoginId);
        this.setOldPassword(submittedOldPassword);
        this.setNewPassword(submittedNewPassword);
        this.setConfirmNewPassword(submittedConfirmNewPassword);

        logger.info("Change password request for Login ID: {}", loginId);

        // Basic validation: New password and confirm new password must match
        if (newPassword == null || !newPassword.equals(confirmNewPassword)) {
            addActionError("New Password and Confirm New Password do not match.");
            return INPUT;
        }

        try {
            // Call the service to handle password change logic
            String result = userService.changePassword(loginId, oldPassword, newPassword);

            if (SUCCESS.equals(result)) {
                addActionMessage("Password changed successfully.");
                return SUCCESS;
            } else {
                addActionError(result); // Display error message from service
                return INPUT;
            }
        } catch (Exception e) {
            logger.error("Change password failed for Login ID {}: {}", loginId, e.getMessage(), e);
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
