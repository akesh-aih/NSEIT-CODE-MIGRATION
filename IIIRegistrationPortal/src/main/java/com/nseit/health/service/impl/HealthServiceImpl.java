package com.nseit.health.service.impl;

import com.nseit.health.dao.HealthDao;
import com.nseit.health.models.Health;
import com.nseit.health.service.HealthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HealthServiceImpl implements HealthService {

    private static final Logger logger = LogManager.getLogger(HealthServiceImpl.class);
    private HealthDao healthDao;

    public void setHealthDao(HealthDao healthDao) {
        this.healthDao = healthDao;
    }

    @Override
    public Health checkHealth() {
        logger.info("Checking application health");
        Health health = healthDao.getHealth();
        logger.debug("Service returning health status: {}", health.getStatus());
        return health;
    }
}
