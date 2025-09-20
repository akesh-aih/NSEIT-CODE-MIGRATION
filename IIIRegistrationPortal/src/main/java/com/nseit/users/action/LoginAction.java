package com.nseit.users.action;

import com.nseit.generic.action.BaseAction;
import com.nseit.users.models.UserBean;
import com.nseit.users.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ModelDriven;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

public class LoginAction extends BaseAction implements ModelDriven<UserBean> {

    private static final Logger logger = LogManager.getLogger(LoginAction.class);
    private UserBean user = new UserBean();

    private LoginService loginService;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    @Override
    @StrutsParameter(depth = 1)
    public UserBean getModel() {
        return user;
    }

    @Override
    public String execute() {
        logger.info("Login attempt for user: {}", user.getUserLoginId());

        try {
            UserBean authenticatedUser = loginService.authenticate(user.getUserLoginId(), user.getPassword());

            logger.info("User {} authenticated successfully.", authenticatedUser.getUserLoginId());
            return SUCCESS;

        } catch (Exception e) {
            logger.error("Login failed for user {}: {}", user.getUserLoginId(), e.getMessage());
            addActionError(getText("error.login.invalid"));
            return ERROR;
        }
    }
    
    @StrutsParameter(depth = 1)    
    public void resetModel() {
        this.user = new UserBean();
    }
}