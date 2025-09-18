package com.nseit.health.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.ModelDriven;

import com.nseit.health.models.Health;
import com.nseit.health.service.HealthService;

public class HealthAction extends ActionSupport implements ModelDriven<Health> {

    private static final Logger logger = LogManager.getLogger(HealthAction.class);
    private Health health = new Health(); // Renamed back to 'health'
    private HealthService healthService;

    // getModel() method for ModelDriven
    @Override
    public Health getModel() {
        return health;
    }

    public void setHealthService(HealthService healthService) {
        this.healthService = healthService; // This line is incorrect, should be this.healthService = healthService;
    }

    @Override
    public String execute() {
        logger.info("Executing HealthAction");
        try {
            Health result = healthService.checkHealth();
            // Populate the existing 'health' model object
            health.setStatus(result.getStatus());
            logger.debug("Action has set health status to: {}", health.getStatus());

            if ("OK".equals(health.getStatus())) {
                return SUCCESS;
            } else {
                addActionError("Health check failed. See logs for details.");
                return ERROR;
            }
        } catch (Exception e) {
            logger.error("Error during health check", e);
            addActionError("An unexpected error occurred during the health check.");
            return ERROR;
        }
    }
}
