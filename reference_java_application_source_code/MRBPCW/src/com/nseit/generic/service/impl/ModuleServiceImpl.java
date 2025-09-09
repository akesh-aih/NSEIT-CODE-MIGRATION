package com.nseit.generic.service.impl;

import java.util.List;
import java.util.Map;

import com.nseit.generic.dao.ModuleDao;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.ModuleBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.TestGroup;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.ModuleService;

public class ModuleServiceImpl implements ModuleService {
	ModuleDao moduleDao;

	public void setModuleDao(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

	public List<CommonBean> getSyllabus() throws Exception {
		return moduleDao.getSyllabus();
	}

	
	
	public ModuleBean getModuleEditDetails(ModuleBean moduleBean) throws Exception {
		return moduleDao.getModuleEditDetails(moduleBean);	
	}
	
	public int deleteSectionDetails(ModuleBean moduleBean) throws Exception {
		return moduleDao.deleteSectionDetails(moduleBean);
	}
	
	
	
	public int getModulePK(String moduleCode) throws Exception {
		return moduleDao.getModulePK(moduleCode);
	}

	
	public int isScheduleExistForModule(int moduleId) throws Exception{
		return moduleDao.isScheduleExistForModule(moduleId);
	}
	public int deleteSections(ModuleBean moduleBean) throws Exception{
		return moduleDao.deleteSections(moduleBean);
	}
	

	@Override
	public List<ModuleBean> getSearchModuleDetails(ModuleBean moduleBean, Pagination pagination)
			throws Exception {
		
		return moduleDao.getSearchModuleDetails(moduleBean, pagination);
	}

	@Override
	public List<ModuleBean> getCategoryDetails() throws Exception {
		
		return moduleDao.getCategoryDetails();
	}

	@Override
	public int insertModuleDetails(ModuleBean moduleBean,
			List<ModuleBean> moduleCategoryList) throws Exception {
		
		return moduleDao.insertModuleDetails(moduleBean, moduleCategoryList);
	}

	@Override
	public int updateModuleDetails(ModuleBean moduleBean,
			List<ModuleBean> moduleCategoryList) throws Exception {
		
		return moduleDao.updateModuleDetails(moduleBean,moduleCategoryList);
	}

	@Override
	public ModuleBean getModuleFeesEdit(ModuleBean moduleBean,
			List<ModuleBean> categoryList) throws Exception {
		
		return moduleDao.getModuleFeesEdit(moduleBean,categoryList);
	}

	@Override
	public List<ModuleBean> getModuleFees(ModuleBean moduleBean,
			List<ModuleBean> categoryList) throws Exception {
		
		return moduleDao.getModuleFee(moduleBean,categoryList);
	}

	@Override
	public int getModuleCount(ModuleBean moduleBean) throws Exception {
		
		return moduleDao.getModuleCount(moduleBean);
	}

	@Override
	public int getModulePKCount(String moduleName) throws Exception {
		return moduleDao.getModulePKCount(moduleName);
	}

	@Override
	public Map<Integer, String> getUpdatedModule() throws Exception {
		return moduleDao.getUpdatedModules();
	}

	@Override
	public List<TestGroup> getTestGroupList() throws Exception {
		return moduleDao.getTestGroupList();
	}

	
}
