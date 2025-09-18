package com.nseit.generic.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseDao {
    private static final Logger logger = LogManager.getLogger(BaseDao.class);
    protected JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
