package com.nseit.generic.service.impl;

import java.util.List;

import com.nseit.generic.dao.RoleDao;
import com.nseit.generic.models.MenuBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.RoleBean;
import com.nseit.generic.service.RoleService;

public class RoleServiceImpl implements RoleService {
	RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<RoleBean> getRoleSearch(
			RoleBean roleMasterBean,Pagination pagination) throws Exception {
		return roleDao.getRoleSearch(roleMasterBean,pagination);
	}
	
	public List<RoleBean> getRole(
			RoleBean roleMasterBean) throws Exception {
		return roleDao.getRole(roleMasterBean);
	}

	public int[] insertAccessControlDetails(List<String> menuDetails,String role) throws Exception {
		return roleDao.insertAccessControlDetails(menuDetails,role);
	}
	
	public int deleteAccessControl(String role) throws Exception {
		return roleDao.deleteAccessControl(role);
	}
	
	public int insertRoleDetails(RoleBean roleBean) throws Exception {
		return roleDao.insertRoleDetails(roleBean);
	}
	public RoleBean getRoleEditDetails(RoleBean roleBean) throws Exception {
		return roleDao.getRoleEditDetails(roleBean);	
	}
		
	public int updateRoleDetails(RoleBean roleBean) throws Exception {
		return roleDao.updateRoleDetails(roleBean);
	}
	
	public int getRolePK(String moduleCode) throws Exception {
		return roleDao.getRolePK(moduleCode);
	}

	public int isScheduleExistForRole(int moduleId) throws Exception{
		return roleDao.isScheduleExistForRole(moduleId);
	}

	public List<MenuBean> getRoleMenuDetails(String rolePk) throws Exception 
	{
		return roleDao.getRoleMenuDetails(rolePk);
	}

	@Override
	public int getRoleCount(RoleBean roleBean) throws Exception {
		return roleDao.getRoleCount(roleBean);
	}
}
