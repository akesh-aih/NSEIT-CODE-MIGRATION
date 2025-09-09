package com.nseit.generic.util;

public class MenuMasterChildBean {

	private String srno;
	private String OMCM_MENU_DESC;
	private String OMCM_NEXT_STAGE;
	private String OMCM_MENU_KEY;
	private String OMCM_MENU_LINK;
	private String OMCM_MENU_MANDATORY;
	private String OMCM_USER_TYPE;
	private String dynamicLink;
	private String OMCM_ACTIVE_STATUS;
	private Integer ORM_ROLE_PK;
	private Integer OMCM_PARENT_MENU_KEY;
	private String menu;
	private String OMCM_MENU_PK;

	public String getSrno() {
		return srno;
	}

	public void setSrno(String srno) {
		this.srno = srno;
	}

	public String getOMCM_MENU_DESC() {
		return OMCM_MENU_DESC;
	}

	public void setOMCM_MENU_DESC(String oMCMMENUDESC) {
		OMCM_MENU_DESC = oMCMMENUDESC;
	}

	public String getOMCM_NEXT_STAGE() {
		return OMCM_NEXT_STAGE;
	}

	public void setOMCM_NEXT_STAGE(String oMCMNEXTSTAGE) {
		OMCM_NEXT_STAGE = oMCMNEXTSTAGE;
	}

	public String getOMCM_MENU_KEY() {
		return OMCM_MENU_KEY;
	}

	public void setOMCM_MENU_KEY(String oMCMMENUKEY) {
		OMCM_MENU_KEY = oMCMMENUKEY;
	}

	public String getOMCM_MENU_LINK() {
		return OMCM_MENU_LINK;
	}

	public void setOMCM_MENU_LINK(String oMCMMENULINK) {
		OMCM_MENU_LINK = oMCMMENULINK;
	}

	public String getOMCM_MENU_MANDATORY() {
		return OMCM_MENU_MANDATORY;
	}

	public void setOMCM_MENU_MANDATORY(String oMCMMENUMANDATORY) {
		OMCM_MENU_MANDATORY = oMCMMENUMANDATORY;
	}

	public String getOMCM_USER_TYPE() {
		return OMCM_USER_TYPE;
	}

	public void setOMCM_USER_TYPE(String oMCMUSERTYPE) {
		OMCM_USER_TYPE = oMCMUSERTYPE;
	}

	public String getDynamicLink() {
		return dynamicLink;
	}

	public void setDynamicLink(String dynamicLink) {
		this.dynamicLink = dynamicLink;
	}

	public String getOMCM_ACTIVE_STATUS() {
		return OMCM_ACTIVE_STATUS;
	}

	public void setOMCM_ACTIVE_STATUS(String oMCMACTIVESTATUS) {
		OMCM_ACTIVE_STATUS = oMCMACTIVESTATUS;
	}

	public Integer getORM_ROLE_PK() {
		return ORM_ROLE_PK;
	}

	public void setORM_ROLE_PK(Integer oRMROLEPK) {
		ORM_ROLE_PK = oRMROLEPK;
	}

	public void setOMCM_PARENT_MENU_KEY(Integer oMCM_PARENT_MENU_KEY) {
		OMCM_PARENT_MENU_KEY = oMCM_PARENT_MENU_KEY;
	}

	public Integer getOMCM_PARENT_MENU_KEY() {
		return OMCM_PARENT_MENU_KEY;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getMenu() {
		return menu;
	}

	public void setOMCM_MENU_PK(String oMCM_MENU_PK) {
		OMCM_MENU_PK = oMCM_MENU_PK;
	}

	public String getOMCM_MENU_PK() {
		return OMCM_MENU_PK;
	}
}
