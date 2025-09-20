package com.nseit.health.models;

import java.io.Serializable;

public class HealthBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status;
    private String testName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
