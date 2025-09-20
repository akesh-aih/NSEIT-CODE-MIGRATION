package com.nseit.health.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nseit.health.dao.HealthDao;
import com.nseit.health.models.HealthBean;
import com.nseit.health.service.HealthService;

public class HealthServiceImpl implements HealthService {

    private static final Logger logger = LogManager.getLogger(HealthServiceImpl.class);

    private HealthDao healthDao;

    public void setHealthDao(HealthDao healthDao) {
        this.healthDao = healthDao;
    }

    @Override
    public HealthBean checkHealth(HealthBean healthBean) {
        logger.info("In HealthServiceImpl.checkHealth(), testName: {}", healthBean.getTestName());
        return healthDao.checkHealth(healthBean);
    }
}
