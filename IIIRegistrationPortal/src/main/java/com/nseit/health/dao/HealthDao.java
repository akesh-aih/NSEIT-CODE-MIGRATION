package com.nseit.health.dao;

import com.nseit.health.models.HealthBean;

public interface HealthDao {
    HealthBean checkHealth(HealthBean healthBean);
}
