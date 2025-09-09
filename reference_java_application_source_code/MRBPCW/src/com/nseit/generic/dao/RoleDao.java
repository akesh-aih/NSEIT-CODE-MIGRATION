package com.nseit.generic.dao;

import java.util.List;

import com.nseit.generic.models.MenuBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.RoleBean;

public interface RoleDao {
	

	public List<RoleBean> getRoleSearch(
			RoleBean roleMasterBean,Pagination pagination) throws Exception;
	
	public List<RoleBean> getRole(RoleBean roleMasterBean) throws Exception;
	
	public int insertRoleDetails(RoleBean roleBean) throws Exception ;
	
	public RoleBean getRoleEditDetails(RoleBean roleBean) throws Exception;
	
	public List<MenuBean> getRoleMenuDetails(String rolePk) throws Exception ;
		
	public int updateRoleDetails(RoleBean roleBean) throws Exception ;
	
	public int getRolePK(String moduleCode) throws Exception ;	
	public int isScheduleExistForRole(int moduleId) throws Exception;

	public int[] insertAccessControlDetails(List<String> menuDetails,String role)throws Exception;

	public int deleteAccessControl(String role)throws Exception;

	public int getRoleCount(RoleBean roleBean)throws Exception;
}
