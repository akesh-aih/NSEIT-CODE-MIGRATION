package com.nseit.users.action;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.ModelDriven;
import org.apache.struts2.action.SessionAware;

import com.nseit.users.models.User;
import com.nseit.users.service.UserService;


/**
 * Handles user login and logout.
 * This action follows the ModelDriven pattern similar to the reference application.
 * However, it adheres to the .NET application's business logic for authentication,
 * which involves server-side password encryption.
 */
public class LoginAction extends ActionSupport implements SessionAware, ModelDriven<User> {

    private static final Logger logger = LogManager.getLogger(LoginAction.class);

    private User user = new User();
    private UserService userService;
    private Map<String, Object> session;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getModel() {
        return user;
    }

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * Authenticates the user. Receives plain-text credentials from the login form,
     * calls the service layer to perform encryption and validation, and manages the session.
     * @return SUCCESS if authentication is successful, ERROR otherwise.
     */
    public String authenticateUser() {
        logger.info("Attempting to authenticate user: {}", user.getUserLoginId());
        try {
            // The user object from getModel() contains the plain-text password from the form.
            // The userService is responsible for encrypting it before validation.
            User authenticatedUser = userService.authenticate(user.getUserLoginId(), user.getPassword());
            session.put("user", authenticatedUser);
            logger.info("User {} authenticated successfully.", authenticatedUser.getUserLoginId());
            return SUCCESS;
        } catch (Exception e) {
            logger.error("Login failed for user {}: {}", user.getUserLoginId(), e.getMessage());
            addActionError(e.getMessage()); // Add the error message to be displayed on the JSP.
            return ERROR;
        }
    }

    /**
     * Invalidates the user session during logout.
     * @return SUCCESS
     */
    public String logout() {
        logger.info("Logging out user.");
        if (session != null) {
            session.remove("user");
            session.clear();
        }
        return SUCCESS;
    }
}
