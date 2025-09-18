package com.nseit.users.models;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int userId;
    private String userName;
    private String userLoginId;
    private String password;
    private int roleId;
    private String roleName;
    private String roleCode;
    private int insurerUserId;
    private String insurerName;
    private String insurerCode;
    private String insurerType;
    private String insurerTypeNew;
    private int dpUserId;
    private String dpName;
    private int agentCounselorUserId;
    private String agentCounselorName;
    private Date lastLoggedInDateTime;
    private String userEmailId;
    private char caId;
    private String topUserLoginId;
    private String key;
    private String iv;
    private boolean isActive;
    private boolean isSuspended;
    private boolean changePasswordOnNextLogin;

    // Getters and Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public int getInsurerUserId() {
        return insurerUserId;
    }

    public void setInsurerUserId(int insurerUserId) {
        this.insurerUserId = insurerUserId;
    }

    public String getInsurerName() {
        return insurerName;
    }

    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }

    public String getInsurerCode() {
        return insurerCode;
    }

    public void setInsurerCode(String insurerCode) {
        this.insurerCode = insurerCode;
    }

    public String getInsurerType() {
        return insurerType;
    }

    public void setInsurerType(String insurerType) {
        this.insurerType = insurerType;
    }

    public String getInsurerTypeNew() {
        return insurerTypeNew;
    }

    public void setInsurerTypeNew(String insurerTypeNew) {
        this.insurerTypeNew = insurerTypeNew;
    }

    public int getDpUserId() {
        return dpUserId;
    }

    public void setDpUserId(int dpUserId) {
        this.dpUserId = dpUserId;
    }

    public String getDpName() {
        return dpName;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
    }

    public int getAgentCounselorUserId() {
        return agentCounselorUserId;
    }

    public void setAgentCounselorUserId(int agentCounselorUserId) {
        this.agentCounselorUserId = agentCounselorUserId;
    }

    public String getAgentCounselorName() {
        return agentCounselorName;
    }

    public void setAgentCounselorName(String agentCounselorName) {
        this.agentCounselorName = agentCounselorName;
    }

    public Date getLastLoggedInDateTime() {
        return lastLoggedInDateTime;
    }

    public void setLastLoggedInDateTime(Date lastLoggedInDateTime) {
        this.lastLoggedInDateTime = lastLoggedInDateTime;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public char getCaId() {
        return caId;
    }

    public void setCaId(char caId) {
        this.caId = caId;
    }

    public String getTopUserLoginId() {
        return topUserLoginId;
    }

    public void setTopUserLoginId(String topUserLoginId) {
        this.topUserLoginId = topUserLoginId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public boolean isChangePasswordOnNextLogin() {
        return changePasswordOnNextLogin;
    }

    public void setChangePasswordOnNextLogin(boolean changePasswordOnNextLogin) {
        this.changePasswordOnNextLogin = changePasswordOnNextLogin;
    }
}
