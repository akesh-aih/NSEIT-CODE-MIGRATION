package com.nseit.generic.queries;

public interface RoleQueries {

	String GET_ROLE_SEARCH = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY orm_role_pk) AS num,orm_role_pk,orm_role_code,orm_role_desc,orm_status"
			+ " from OES_ROLE_MASTER WHERE " +
			"  orm_status=CASE WHEN ?='B' THEN orm_status ELSE ? END AND orm_role_type=?";
	
	String GET_ACTIVE_ROLE = "select orm_role_pk,orm_role_code,orm_role_desc,orm_status"
		+ " from OES_ROLE_MASTER WHERE " +
		"  orm_status='A' AND orm_role_type=?";

	String INSERT_ROLE = "Insert into OES_ROLE_MASTER(orm_role_pk, orm_role_code, orm_role_desc,"
			+ " orm_status, orm_CREATED_BY, orm_CREATED_DATE,orm_role_type) " +
			" values((select COALESCE(max(orm_role_pk)+1) from OES_ROLE_MASTER), ?,?, ?,?, current_timestamp,?)";
	
	String INSERT_ACCESS_CONTROL = "Insert into OES_ACCESS_CONTROL(oac_acc_cntrl_pk, oac_menu_fk, oac_role_fk, oac_status) VALUES ( " +
		" (select COALESCE(max(oac_acc_cntrl_pk)+1) from OES_ACCESS_CONTROL), ?,?, 'A')";

	String UPDATE_ACCESS_CONTROL = "update oes_access_control set oac_status='D' where oac_role_fk=? ";
	
	String GET_ROLE_EDIT_DETAILS = "select orm_role_pk,orm_role_code,orm_role_desc,orm_status"
		+ " from OES_ROLE_MASTER WHERE orm_role_pk=?";

	String UPDATE_ROLE_DETAILS = "update OES_ROLE_MASTER set orm_role_code=?,orm_role_desc=?,orm_status=?,orm_role_type=? where orm_role_pk=?";


	String GET_ROLE_PK = "Select orm_role_pk from OES_ROLE_MASTER where orm_role_code=?";
	
	String IS_ACCESS_EXIST_FOR_ROLE = "SELECT COUNT(1) FROM OES_access_control WHERE oac_role_fk=? and oac_status='A' ";
	
	String GET_ROLE_MENU_DETAILS = "SELECT OMCM_MENU_KEY as menuKey, cast(floor (CAST(OMCM_MENU_KEY as numeric)) as TEXT) as displayName FROM oes_menu_control_master,OES_access_control WHERE oac_menu_fk =OMCM_MENU_PK and oac_role_fk=? and oac_status='A' ";

	Object GET_ROLE_COUNT = " select count(*) from OES_ROLE_MASTER WHERE orm_status=CASE WHEN ?='B' THEN orm_status ELSE ? END AND orm_role_type=?";
	
}
