package com.nseit.health.dao.impl;

import com.nseit.generic.util.BaseDao;
import com.nseit.health.dao.HealthDao;
import com.nseit.health.models.Health;
import com.nseit.queries.QueryConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class HealthDaoImpl extends BaseDao implements HealthDao {

    private static final Logger logger = LogManager.getLogger(HealthDaoImpl.class);

    @Override
    public Health getHealth() {
        Health health = new Health();
        try {
            jdbcTemplate.queryForObject(QueryConstants.HEALTH_CHECK, Integer.class);
            health.setStatus("OK");
            logger.debug("DAO returning health status: OK");
        } catch (DataAccessException e) {
            logger.error("Health check failed", e);
            health.setStatus("ERROR");
        }
        return health;
    }
}
