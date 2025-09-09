package com.nseit.generic.util;

import java.math.BigDecimal;

public class MenuMasterParentBean {

	private Integer menuMasterPk;
	private String roleFK;
	private String menuKey;
	private String displayName;
	private String menuLink;
	private String userType;
	private Double currentStage;
	private Double nextStage;
	private String parentStageOnValue;
	private String menu;

	public Integer getMenuMasterPk() {
		return menuMasterPk;
	}

	public void setMenuMasterPk(Integer menuMasterPk) {
		this.menuMasterPk = menuMasterPk;
	}

	public String getRoleFK() {
		return roleFK;
	}

	public void setRoleFK(String roleFK) {
		this.roleFK = roleFK;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMenuLink() {
		return menuLink;
	}

	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Double getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Double currentStage) {
		this.currentStage = currentStage;
	}

	public Double getNextStage() {
		return nextStage;
	}

	public void setNextStage(Double nextStage) {
		this.nextStage = nextStage;
	}

	public String getParentStageOnValue() {
		return parentStageOnValue;
	}

	public void setParentStageOnValue(String parentStageOnValue) {
		this.parentStageOnValue = parentStageOnValue;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getMenu() {
		return menu;
	}

}
