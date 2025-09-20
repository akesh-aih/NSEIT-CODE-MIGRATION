package com.nseit.health.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ModelDriven;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

import com.nseit.generic.action.BaseAction;
import com.nseit.health.models.HealthBean;
import com.nseit.health.service.HealthService;


public class HealthAction extends BaseAction implements ModelDriven<HealthBean> {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(HealthAction.class);

    private HealthBean healthBean = new HealthBean();
    private HealthService healthService;

    public String checkHealth() {
        logger.info("In HealthAction.checkHealth(), testName: {}", healthBean.getTestName());
        healthBean = healthService.checkHealth(healthBean);
        return SUCCESS;
    }

    @Override
    @StrutsParameter(depth = 1)
    public HealthBean getModel() {
        return healthBean;
    }

    public void setHealthService(HealthService healthService) {
        this.healthService = healthService;
    }
}