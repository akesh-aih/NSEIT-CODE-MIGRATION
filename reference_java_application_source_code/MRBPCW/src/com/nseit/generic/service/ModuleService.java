package com.nseit.generic.service;

import java.util.List;
import java.util.Map;

import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.ModuleBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.TestGroup;

public interface ModuleService {
	public List<CommonBean> getSyllabus() throws Exception;


	
	public int insertModuleDetails(ModuleBean moduleBean, List<ModuleBean> moduleCategoryList) throws Exception ;
	

	
	public ModuleBean getModuleEditDetails(ModuleBean moduleBean) throws Exception ;
	
	public int deleteSectionDetails(ModuleBean moduleBean) throws Exception ;
	
	public int updateModuleDetails(ModuleBean moduleBean,List<ModuleBean> moduleCategoryList) throws Exception ;
	

	
	public int getModulePK(String moduleCode) throws Exception ;
	

	public int isScheduleExistForModule(int moduleId) throws Exception;
	public int deleteSections(ModuleBean moduleBean) throws Exception;

	public List<ModuleBean> getSearchModuleDetails(ModuleBean moduleBean, Pagination pagination)throws Exception;

	public List<ModuleBean> getCategoryDetails()throws Exception;

	public ModuleBean getModuleFeesEdit(ModuleBean moduleBean,
			List<ModuleBean> categoryList)throws Exception;

	public List<ModuleBean> getModuleFees(ModuleBean moduleBean,
			List<ModuleBean> categoryList)throws Exception;

	public int getModuleCount(ModuleBean moduleBean)throws Exception;



	public int getModulePKCount(String moduleName)throws Exception;



	public Map<Integer, String> getUpdatedModule()throws Exception;
	
	public List<TestGroup> getTestGroupList() throws Exception;

	
}
