package com.nseit.health.models;

import java.io.Serializable;

public class Health implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
