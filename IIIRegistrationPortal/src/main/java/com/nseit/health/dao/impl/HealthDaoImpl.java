package com.nseit.health.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.nseit.generic.dao.BaseDao;
import com.nseit.health.dao.HealthDao;
import com.nseit.health.models.HealthBean;
import com.nseit.queries.QueryConstants;

public class HealthDaoImpl extends BaseDao implements HealthDao {

    private static final Logger logger = LogManager.getLogger(HealthDaoImpl.class);

    @Override
    public HealthBean checkHealth(HealthBean healthBean) {
        logger.info("In HealthDaoImpl.checkHealth(), testName: {}", healthBean.getTestName());
        String sql = QueryConstants.HEALTH_CHECK;
        return (HealthBean) getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(HealthBean.class));
    }
}
