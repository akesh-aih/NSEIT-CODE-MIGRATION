//package com.nseit.users.action;
//
//import com.nseit.generic.action.BaseAction;
//import com.nseit.users.models.User;
//import com.nseit.users.service.LoginService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.struts2.ModelDriven;
//
//public class ForgotPasswordAction extends BaseAction implements ModelDriven<User> {
//
//    private static final Logger logger = LogManager.getLogger(ForgotPasswordAction.class);
//
//    private User user = new User();
//
//    private LoginService loginService;
//
//    public void setLoginService(LoginService loginService) {
//        this.loginService = loginService;
//    }
//
//    @Override
//    public User getModel() {
//        return user;
//    }
//
//    public String input() {
//        return INPUT;
//    }
//
//    public String forgotPassword() {
//        logger.info("Forgot password request for Login ID: {} and Email ID: {}", user.getUserLoginId(), user.getUserEmailId());
//
//        if (user.getUserLoginId() == null || user.getUserLoginId().trim().isEmpty()) {
//            addActionError(getText("error.loginid.required"));
//            return INPUT;
//        }
//        if (user.getUserEmailId() == null || user.getUserEmailId().trim().isEmpty()) {
//            addActionError(getText("error.emailid.required"));
//            return INPUT;
//        }
//
//        try {
//            loginService.resetPassword(user.getUserLoginId(), user.getUserEmailId());
//
//            addActionMessage(getText("message.password.reset.success"));
//            return SUCCESS;
//
//        } catch (Exception e) {
//            logger.error("Forgot password failed for Login ID {} and Email ID {}: {}", user.getUserLoginId(), user.getUserEmailId(), e.getMessage(), e);
//            addActionError(getText("error.password.reset.failed") + ": " + e.getMessage());
//            return INPUT;
//        }
//    }
//
//    @Override
//    public void resetModel() {
//        this.user = new User();
//    }
//}