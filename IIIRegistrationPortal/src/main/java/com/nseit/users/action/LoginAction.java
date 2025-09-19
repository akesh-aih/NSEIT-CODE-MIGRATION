package com.nseit.users.action;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.ModelDriven;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.action.ServletRequestAware;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.struts2.action.ParametersAware;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.users.models.User;
import com.nseit.users.service.UserService;

public class LoginAction extends ActionSupport implements SessionAware, ModelDriven<User>, ServletRequestAware {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2751378558531333548L;

	private static final Logger logger = LogManager.getLogger(LoginAction.class);

    private User user = new User();
    private UserService userService;
    private Map<String, Object> session;
    private HttpServletRequest request;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void withServletRequest(HttpServletRequest request) {
        this.request = request;
        logger.info("ServletRequestAware: HttpServletRequest set.");
    }

    /**
     * Input method - displays the login form
     */
    public String input() {
        logger.info("Displaying login form");
        return INPUT;
    }

    /**
     * Authenticates the user and stores user details in the session.
     */
    public String authenticateUser() {
        // Manually retrieve parameters from HttpServletRequest
        String submittedUserLoginId = request.getParameter("userLoginId");
        String submittedPassword = request.getParameter("password");

        // Set them on the user model
        user.setUserLoginId(submittedUserLoginId);
        user.setPassword(submittedPassword);

        logger.info("=== DEBUG: authenticateUser method called ===");
        logger.info("User object: {}", user);
        logger.info("UserLoginId: {}", user != null ? user.getUserLoginId() : "USER_IS_NULL");
        logger.info("Password provided: {}", (user != null && user.getPassword() != null && !user.getPassword().trim().isEmpty()) ? "YES" : "NO");
        
        // Validate input
        if (user.getUserLoginId() == null || user.getUserLoginId().trim().isEmpty()) {
            logger.warn("No username provided");
            addActionError("Username is required");
            return ERROR;
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            logger.warn("No password provided for user: {}", user.getUserLoginId());
            addActionError("Password is required");
            return ERROR;
        }

        logger.info("Attempting to authenticate user: {}", user.getUserLoginId());
        
        try {
            User authenticatedUser = userService.authenticate(user.getUserLoginId().trim(), user.getPassword().trim());
            
            if (authenticatedUser == null) {
                logger.error("UserService returned null authenticated user");
                addActionError("Authentication failed");
                return ERROR;
            }
            
            session.put("user", authenticatedUser);
            logger.info("User {} authenticated successfully.", authenticatedUser.getUserLoginId());
            return SUCCESS;
            
        } catch (Exception e) {
            logger.error("Login failed for user {}: {}", user.getUserLoginId(), e.getMessage(), e);
            addActionError(e.getMessage());
            return ERROR;
        }
    }

    /**
     * Logs out the user by clearing the session.
     */
    public String logout() {
        logger.info("Logout method called");
        
        if (session != null) {
            User currentUser = (User) session.get("user");
            if (currentUser != null) {
                logger.info("Logging out user: {}", currentUser.getUserLoginId());
            }
            session.remove("user");
            session.clear();
        }
        
        logger.info("User logged out successfully");
        return SUCCESS;  // This matches your struts.xml result name
    }

    /**
     * ModelDriven interface implementation
     * This method is called by Struts to get the model object for parameter binding
     */
    @Override
    public User getModel() {
        if (user == null) {
            user = new User();
            logger.debug("Created new User model instance");
        }
        return user;
    }

    /**
     * SessionAware interface implementation
     * Updated to use withSession for Struts 2.5+ compatibility (required for Struts 2 ~7.0.0)
     */
    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    // Additional debugging methods - remove these after fixing the issue
    
    /**
     * This method can be called for debugging purposes
     */
    public String debug() {
        logger.info("=== DEBUG METHOD ===");
        logger.info("User object exists: {}", user != null);
        if (user != null) {
            logger.info("UserLoginId: '{}'", user.getUserLoginId());
            logger.info("Password length: {}", user.getPassword() != null ? user.getPassword().length() : 0);
        }
        logger.info("Session: {}", session);
        return SUCCESS;
    }

    // Getters for debugging - these help ensure proper parameter binding
    public String getUserLoginId() {
        return user != null ? user.getUserLoginId() : null;
    }

    public String getPassword() {
        return user != null ? user.getPassword() : null;
    }
}