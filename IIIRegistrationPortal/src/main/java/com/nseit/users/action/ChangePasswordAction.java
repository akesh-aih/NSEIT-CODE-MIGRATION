//package com.nseit.users.action;
//
//import com.nseit.generic.action.BaseAction;
//import com.nseit.users.models.User;
//import com.nseit.users.service.LoginService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.struts2.ModelDriven;
//
//public class ChangePasswordAction extends BaseAction implements ModelDriven<User> {
//
//    private static final Logger logger = LogManager.getLogger(ChangePasswordAction.class);
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
//    public String changePassword() {
//        logger.info("Change password request for Login ID: {}", user.getUserLoginId());
//
//        if (user.getPassword() == null || !user.getPassword().equals(user.getConfirmNewPassword())) {
//            addActionError(getText("error.password.mismatch"));
//            return INPUT;
//        }
//
//        try {
//            String result = loginService.changePassword(user.getUserLoginId(), user.getOldPassword(), user.getPassword());
//
//            if (SUCCESS.equals(result)) {
//                addActionMessage(getText("message.password.changed.success"));
//                return SUCCESS;
//            } else {
//                addActionError(result);
//                return INPUT;
//            }
//        } catch (Exception e) {
//            logger.error("Change password failed for Login ID {}: {}", user.getUserLoginId(), e.getMessage(), e);
//            addActionError(getText("error.password.change.failed"));
//            return INPUT;
//        }
//    }
//
//    @Override
//    public void resetModel() {
//        this.user = new User();
//    }
//}