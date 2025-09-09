package com.nseit.generic.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.ModuleBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.TestGroup;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.ModuleService;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.opensymphony.xwork2.ModelDriven;

public class ModuleMasterAction extends BaseAction implements ModelDriven<ModuleBean>, UserAware {

	private static final long serialVersionUID = -6209186396968615376L;
	private Logger logger = LoggerHome.getLogger(getClass());
	private Pagination pagination = new Pagination(10, 1);
	private ModuleBean moduleBean = new ModuleBean();
	private ModuleService moduleService;

	@Override
	public void resetModel() {
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	@Override
	public ModuleBean getModel() {
		return moduleBean;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	@Override
	public void setLoggedInUser(Users users) {
		loggedInUser = users;
	}

	public String getModuleMaster() {
		//logger.info("calling getModuleMaster method ");
		displayMenus();
		moduleMasterHome();
		return "moduleMaster";
	}

	public String moduleMasterHome() {
		//logger.info("calling moduleMasterHome method ");
		displayMenus();
		try {
			moduleBean.setStatus(null);
			moduleBean.setSyllabusValue(null);
			//moduleBean.setSyllabusList(moduleService.getSyllabus());
			moduleBean.setSelectList(getStatusList());

		} catch (Exception e) {
		}
		return "moduleMaster";
	}

	private List<CommonBean> getStatusList() {
		List<CommonBean> radioList = new ArrayList<CommonBean>();

		CommonBean commonBean = new CommonBean();
		commonBean.setLabelId("A");
		commonBean.setLabelValue("Active");

		CommonBean bean = new CommonBean();
		bean.setLabelId("D");
		bean.setLabelValue("Inactive");

		radioList.add(commonBean);
		radioList.add(bean);
		return radioList;
	}

	public String searchModule() {
		try {
			logger.debug("calling searchModule method ");
			displayMenus();
			//moduleBean.setSearchDetailsList(moduleService.getModuleSearch(moduleBean));
			int count=moduleService.getModuleCount(moduleBean);
			pagination.setProperties(count);
			//logger.info(moduleBean.getStatus());
			List<ModuleBean> lBean= moduleService.getSearchModuleDetails(moduleBean,pagination);
			List<TestGroup> lDbBean=moduleService.getTestGroupList();
			List<ModuleBean> lScreenBean=new ArrayList<ModuleBean>();
			for(ModuleBean mbean:lBean){
				for(TestGroup tg:lDbBean){
					if(null != tg.getOtg_test_pk() && tg.getOtg_test_pk().equals(mbean.getTestGroup())){
						mbean.setExamName(tg.getOtg_test_name());	
					}
					
					
				}
				lScreenBean.add(mbean);
				
			}
			moduleBean.setSearchDetailsList(lScreenBean);
			moduleBean.setTotalRecord(String.valueOf(count));//new Integer(moduleBean.getSearchDetailsList().size()).toString());
			moduleBean.setShowGrid("true");
			moduleBean.setAvailableRecordFlag("true");
			moduleBean.setTestGroupList(moduleService.getTestGroupList());
			//moduleBean.setSyllabusList(moduleService.getSyllabus());
			moduleBean.setSelectList(getStatusList());
			if(moduleBean.getSearchDetailsList().size()<1){
				moduleBean.setAvailableRecordFlag("false");	
			}
		
			

		} catch (Exception e) {
			
		}
		return "moduleMaster";
	}

	public String addModule() {
		logger.debug("calling addModule method ");
		Map<Integer, String> testGroupList1 = null;
		try {
			//moduleBean.setSyllabusValue("");
			moduleBean.setStatus("A");
			moduleBean.setSelectList(getStatusList());
			moduleBean.setSearchCategoryList(moduleService.getCategoryDetails());
			moduleBean.setTestGroupList(moduleService.getTestGroupList());
			
			//moduleBean.setExamMasterList(moduleService.getExamMasterDataList());
		} catch (Exception e) {
			logger.debug(e);
		}
		displayMenus();
		return "addModule";
	}

	public String addModuleDetails() {
		logger.debug("calling addModuleDetails method ");
		//moduleBean.setSyllabusValue("");
		try
		{
		moduleBean.setStatus(moduleBean.getStatus());
		moduleBean.setSelectList(getStatusList());
		moduleBean.setModuleName(moduleBean.getModuleName());
		moduleBean.setUsername(loggedInUser.getUsername());
		moduleBean.setmoduleDescription(moduleBean.getmoduleDescription());
		String fromDate = moduleBean.getFromDate();
		moduleBean.setFromDate(moduleBean.getFromDate());
		moduleBean.setToDate(moduleBean.getToDate());
		moduleBean.setPaymentStartDate(moduleBean.getPaymentStartDate());
		moduleBean.setPaymentEndDate(moduleBean.getPaymentEndDate());
        if(moduleBean.getStatus()==null || moduleBean.getStatus().trim().equals(""))
		moduleBean.setStatus("A");
        moduleBean.setAdvertisementnumber(moduleBean.getAdvertisementnumber());
        
        moduleBean.setTestGroup(moduleBean.getTestGroup());
        moduleBean.setTestGroupList(moduleService.getTestGroupList());
        //moduleBean.setSearchCategoryList(moduleService.getCategoryDetails());
        //logger.info(moduleBean.getSearchCategoryList().size());
        List<ModuleBean> moduleCategoryList=moduleBean.getSearchCategoryList();
        /*for (ModuleBean iterable_element : moduleCategoryList) {
        	logger.info(iterable_element.getFees());
        	logger.info(iterable_element.getFeesInWords());
		}*/
        //logger.info(moduleBean.getSearchCategoryList().size());
		displayMenus();
		int updateCount = 0;
		int module_PK = 0;
		int module_PK_Count =0;
		StringBuilder err_msg = new StringBuilder("");
		ArrayList<String > errorField=new ArrayList<String>();
		try {

			/*if (moduleBean.getModuleCode() == null
					|| moduleBean.getModuleCode().trim().equals("")) {
				err_msg = err_msg.append("<li>Please enter Module ID .</li>");
			}
			if (err_msg.toString().trim().equals("")) {
				module_PK = moduleService.getModulePK(moduleBean.getModuleCode());

				moduleBean.setSelectList(getStatusList());
				moduleBean.setUsername(loggedInUser.getUsername());
				// module_PK =
				// moduleService.getModulePK(moduleBean.getModuleCode());
			}
			if (module_PK != 0 && moduleBean.getSetFlag().equals("")) {
				err_msg = err_msg.append("<li>Please enter unique Module ID .</li>");
			}
			 */
			
			
			if (moduleBean.getModuleName() == null
					|| moduleBean.getModuleName().trim().equals("")) {
				err_msg = err_msg.append("<li>Please enter Module Name.</li>");
				errorField.add("moduleName");
			}
			/*if (moduleBean.getSyllabusValue() == null
					|| moduleBean.getSyllabusValue().trim().equals("")) {
				err_msg = err_msg.append("<li>Please enter Syllabus Name .</li>");
			}*/

			if (moduleBean.getmoduleDescription() == null
					|| moduleBean.getmoduleDescription().trim().equals("")) {
				err_msg = err_msg.append("<li>Please enter Description.</li>");
				errorField.add("moduleDescription");
			}

			else if (moduleBean.getmoduleDescription().trim().length() > 100) {
				err_msg = err_msg.append("<li>Do not enter Description more than 100 characters.</li>");
				errorField.add("moduleDescription");
			}
			if (moduleBean.getFromDate() == null
					|| moduleBean.getFromDate().trim().equals("")) {
				err_msg = err_msg.append("<li>Please select Application Start Date.</li>");
				errorField.add("fromDate");
			}
			
			if (moduleBean.getToDate() == null
					|| moduleBean.getToDate().trim().equals("")) {
				err_msg = err_msg.append("<li>Please select Application End Date.</li>");
				errorField.add("toDate");
			}
			if (moduleBean.getPaymentStartDate() == null
					|| moduleBean.getPaymentStartDate().trim().equals("")) {
				err_msg = err_msg.append("<li>Please select Payment Start Date.</li>");
				errorField.add("paymentStartDate");
			}
			if (moduleBean.getPaymentEndDate() == null
					|| moduleBean.getPaymentEndDate().trim().equals("")) {
				err_msg = err_msg.append("<li>Please select Payment End Date.</li>");
				errorField.add("paymentEndDate");
			}
			if (moduleBean.getDateOfNotification() == null
					|| moduleBean.getDateOfNotification().trim().equals("")) {
				err_msg = err_msg.append("<li>Please select Date of Notification.</li>");
				errorField.add("dateOfNotification");
			}
			if (moduleBean.getAdvertisementnumber() == null
					|| moduleBean.getAdvertisementnumber().trim().equals("")) {
				err_msg = err_msg.append("<li>Please enter Advertisement Number.</li>");
				errorField.add("advertisementnumber");
			}
			
			for (ModuleBean mb : moduleCategoryList) {
				if (mb.getFees() == null
						|| mb.getFees().trim().equals("")) {
					err_msg = err_msg.append("<li>Please enter Module Fees for Category " +mb.getOCTM_CATEGORY_CODE()+ "</li>");
					errorField.add("fees"+mb.getOCTM_CATEGORY_CODE());
				}
				
				if (mb.getFeesInWords() == null
						|| mb.getFeesInWords().trim().equals("")) {
					err_msg = err_msg.append("<li>Please enter Module Fees in Words for Category " +mb.getOCTM_CATEGORY_CODE()+ "</li>");
					errorField.add("feesInWords"+mb.getOCTM_CATEGORY_CODE());
				}
				
			}
			
			if (!err_msg.toString().trim().equals("")) {
				addActionMessage(err_msg.toString());
				moduleBean.setErrMsg(err_msg.toString());
				moduleBean.setErrorField(errorField);
				moduleBean.setShowModuleDetails("false");
			}

			if (err_msg.toString().trim().equals("")) {

				//module_PK_Count = moduleService.getModulePKCount(moduleBean.getModuleName());
				
				if (moduleBean.getModuleId()==null || moduleBean.getModuleId().equals("")) {

					int moduleInsertCount = moduleService.insertModuleDetails(moduleBean,moduleCategoryList);

					if (moduleInsertCount > 0) {
						module_PK = moduleService.getModulePK(moduleBean.getModuleName());

						getModule();
						moduleBean.setModuleId(new Integer(module_PK).toString());
						moduleBean.setSetFlag("true");
						moduleBean.setShowModuleDetails("true");
						moduleBean.setSucMsg("Module Added successfully!!");
					} else {
						moduleBean.setShowModuleDetails("false");
						
					}
				}

				else {

					//module_PK = moduleService.getModulePK(moduleBean.getModuleName());

					//moduleBean.setModuleId(new Integer(module_PK).toString());
					

					int moduleUpdateCount = moduleService.updateModuleDetails(moduleBean,moduleCategoryList);

					boolean flag = false;
					
					if (moduleUpdateCount > 0) {
						//moduleBean.setSetFlag("true");
						getModule();
						moduleBean.setShowModuleDetails("true");
						moduleBean.setSucMsg("Module Updated successfully!!");
					} else {
						moduleBean.setShowModuleDetails("false");
						
					}
					
				}
			}
			//moduleBean.setExamMasterList(moduleService.getExamMasterDataList());
		} catch (Exception e) {
			logger.fatal(e.getMessage());
		}
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "addModule";
	}

	public String editModuleDetails() throws Exception{
		logger.debug("calling editModuleDetails method ");
		displayMenus();
		ModuleBean moduleBeanResult = new ModuleBean();
		try {
			int module_PK = moduleService.getModulePK(moduleBean.getModuleName());
			moduleBean.setModuleId(new Integer(module_PK).toString());
			moduleBeanResult = moduleService.getModuleEditDetails(moduleBean);
			moduleBean.setSearchCategoryList(moduleService.getCategoryDetails());
			List<ModuleBean> categoryList = moduleBean.getSearchCategoryList();
			//moduleBean.setSearchCategoryList(moduleService.getModuleFees(moduleBean, categoryList));
			List<ModuleBean> fees = moduleService.getModuleFees(moduleBean, categoryList);
			moduleBean.setTestGroupList(moduleService.getTestGroupList());
			moduleBean.setSearchCategoryList(fees);
			//ModuleBean count = moduleService.getModuleFeesEdit(moduleBean, categoryList);
			/*List<ModuleSyllabusDetails> ModuleSyllabusBeanList = moduleService.getSectionDetails(moduleBean);
			List<ModuleSyllabusDetails> ModuleSyllabusBeanListNew = new ArrayList<ModuleSyllabusDetails>();
			for (ModuleSyllabusDetails ModuleSyllabusBean : ModuleSyllabusBeanList) {
				//ModuleSyllabusBean.setOES_PAPER_SHORT_NAME(ModuleSyllabusBean.getOES_PAPER_SHORT_NAME().replace('-', ','));
				ModuleSyllabusBeanListNew.add(ModuleSyllabusBean);
			}
			moduleBean.setSearchDetailsList(ModuleSyllabusBeanListNew);
			moduleBean.setExamMasterList(moduleService.getExamMasterDataList());*/
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		/*for (ModuleBean mb : fees) {
			moduleBean
		}*/
		//moduleBean.setModuleCode(moduleBeanResult.getModuleCode());
		moduleBean.setmoduleDescription(moduleBeanResult.getOTM_DESCRIPTION());
		//moduleBean.setSyllabusValue(moduleBeanResult.getSyllabusValue());
		moduleBean.setPaymentStartDate(moduleBeanResult.getOTM_PAYMENT_START_DATE());
		moduleBean.setPaymentEndDate(moduleBeanResult.getOTM_PAYMENT_END_DATE());
		moduleBean.setModuleName(moduleBeanResult.getOTM_TEST_NAME());
		moduleBean.setSelectList(getStatusList());
		moduleBean.setStatus(moduleBeanResult.getOTM_STATUS());
		moduleBean.setFromDate(moduleBeanResult.getOTM_FROM_DATE());
		moduleBean.setToDate(moduleBeanResult.getOTM_TO_DATE());
		moduleBean.setDateOfNotification(moduleBeanResult.getDateOfNotification());
		moduleBean.setTestGroup(moduleBeanResult.getTestGroup());
		moduleBean.setAdvertisementnumber(moduleBeanResult.getAdvertisementnumber());
		
		//moduleBean.setExamCodeValue(moduleBeanResult.getExamCodeValue());
		//if(moduleBeanResult.getExamCodeValue()!=null){
			/*ExamMasterBean examNameObjectBean = moduleService.getExamObject(Long.valueOf(moduleBeanResult.getExamCodeValue()));
			moduleBean.setExamName(examNameObjectBean.getEXAM_NAME());*/
		//}
		
			moduleBean.setSetFlag("true");
			moduleBean.setShowModuleDetails("true");
			//moduleBean.setSucMsg("Module Added successfully!!");
		
		
 		return "addModule";
	}

	
	

	public String addSection() {
		try {
			moduleBean.setStatus(moduleBean.getStatus());
			moduleBean.setSelectList(getStatusList());
			moduleBean.setModuleName(moduleBean.getModuleName());
			moduleBean.setmoduleDescription(moduleBean.getmoduleDescription());
			moduleBean.setFromDate(moduleBean.getFromDate());
			moduleBean.setToDate(moduleBean.getToDate());
			//moduleBean.setExamMasterList(moduleService.getExamMasterDataList());
			/*moduleBean.setShowModuleDetails("true");
			List<ModuleSyllabusDetails> searchDetailsList = moduleBean.getSearchDetailsList();
			if (searchDetailsList == null || searchDetailsList.size() < 0) {
				searchDetailsList = new ArrayList<ModuleSyllabusDetails>();
			}
			searchDetailsList.add(new ModuleSyllabusDetails());
			moduleBean.setSearchDetailsList(searchDetailsList);*/

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		displayMenus();
		if (moduleBean.getEditFlag() != null && moduleBean.getEditFlag().equals("1"))
			return "editModule";
		else
			return "addModule";
	}

	
	public String resetModule() {
		//logger.info("calling moduleMasterHome method ");
		displayMenus();
		try {
			//moduleBean.setSyllabusList(moduleService.getSyllabus());
			moduleBean.setSelectList(getStatusList());
			//moduleBean.setSyllabusValue(null);
			moduleBean.setStatus(null);

		} catch (Exception e) {
		}
		return "moduleMaster";
	}
	public void getModule()
	{
		CandidateBean candidateBean = new CandidateBean();
        Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
        
		try {
			discliplineList = moduleService.getUpdatedModule();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
        
        if (discliplineList!=null && !discliplineList.isEmpty()){
            candidateBean.setDiscliplineList(discliplineList);
            ConfigurationConstants.getInstance().setDisciplineMap(discliplineList);
            ConfigurationConstants.getInstance().setApplyDisciplineMap(discliplineList);
        }
	}

	
	@Override
	public void withSession(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
		
	}

	@Override
	public void withParameters(HttpParameters httpParameters) {
		this.httpParameters = httpParameters;
		
	}

	@Override
	public void withServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	@Override
	public void withServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
}
