package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.MenuBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.RoleBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.RoleService;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.MenuMasterChildBean;
import com.nseit.generic.util.MenuMasterParentBean;
import com.nseit.generic.util.aware.UserAware;
import com.opensymphony.xwork2.ModelDriven;

public class RoleMasterAction extends BaseAction implements ModelDriven<RoleBean>, UserAware {

	private static final long serialVersionUID = -8082649344209891280L;

	private Logger logger = LoggerHome.getLogger(getClass());

	private RoleBean roleBean = new RoleBean();
	private RoleService roleService;
	private Pagination pagination = new Pagination(10, 1);

	@Override
	public void resetModel() {
		// TODO Auto-generated method stub

	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public RoleBean getModel() {
		return roleBean;
	}

	@Override
	public void setLoggedInUser(Users users) {
		loggedInUser = users;
	}
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}


	public String getRoleMaster()  throws Exception{
		//logger.info("calling getRoleMaster method ");
		displayMenus();
		roleMasterHome();
		return "roleMaster";
	}

	public String roleMasterHome()  throws Exception{
		//logger.info("calling roleMasterHome method ");
		displayMenus();
		/*try {*/
			roleBean.setStatus(null);
			roleBean.setSelectList(getStatusList());
			roleBean.setRoleType(null);
		/*} catch (Exception e) {
			logger.error(e);
		}*/
		return "roleMaster";
	}

	private List<CommonBean> getStatusList()  throws Exception{
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

	public String searchRole()  throws Exception{
		/*try {*/
			//logger.info("calling searchRole method ");
			displayMenus();
			int count = roleService.getRoleCount(roleBean);
			pagination.setProperties(count);
			roleBean.setSearchDetailsList(roleService.getRoleSearch(roleBean,pagination));
			roleBean.setTotalRecord(new Integer(roleBean.getSearchDetailsList().size()).toString());
			roleBean.setShowGrid("true");
			roleBean.setAvailableRecordFlag("true");
			roleBean.setSelectList(getStatusList());
			if(roleBean.getSearchDetailsList().size()<1){
				roleBean.setAvailableRecordFlag("false");	
			}
		/*} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}*/
		return "roleMaster";
	}
	
	public String searchRoleMenu()  throws Exception{
		/*try {*/
			Users user = null;
		
			//logger.info("calling searchRoleMenu method ");
			displayMenus();
			HttpSession session = request.getSession();
			if (session.getAttribute("SESSION_USER") != null) {
				user = (Users) session.getAttribute("SESSION_USER");
			}
			List<MenuMasterParentBean> newParentList = new ArrayList<MenuMasterParentBean>();
			// This line of code get the all parent menu list from cache
			List<MenuMasterParentBean> parentList = ConfigurationConstants.getInstance().getParentMenuList();

			if (user != null) {

				for (int i = 0; i < parentList.size(); i++) {
					for (MenuMasterParentBean menuMasterParentBean : parentList) {
						if (menuMasterParentBean.getUserType().equals(GenericConstants.USER_TYPE_SUPER_ADMIN)) {
							newParentList.add(menuMasterParentBean);
						}
					}
				}
				List<MenuMasterParentBean> uniqueParentList = removeParentDuplicates(newParentList);
				List<MenuMasterParentBean> stageOnParentList = getStageOnParentList(user, uniqueParentList);
				List<MenuMasterParentBean> finalParentList = getStageOffParentList(user, stageOnParentList);

				roleBean.setParentMenuDetailsList(finalParentList);

				List<MenuMasterChildBean> childList =  ConfigurationConstants.getInstance().getChildMenuList();
				
				List<MenuMasterChildBean> uniqueChildList = removeChildDuplicates(childList);
				roleBean.setChildMenuDetailsList(uniqueChildList);
				
			}
			roleBean.setTotalRecord(new Integer(roleBean.getParentMenuDetailsList().size()).toString());
			roleBean.setShowGrid("true");
			roleBean.setAvailableRecordFlag("true");
			
			if(roleBean.getParentMenuDetailsList().size()<1){
				roleBean.setAvailableRecordFlag("false");	
			}
		/*} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}*/
		return "roleMenuMaster";
	}
	public String getRole()  throws Exception
	{
		/*try{*/
		List<RoleBean> rolelist=roleService.getRole(roleBean);
		String role="";
		for (int i=0;i<rolelist.size();i++) {
			
			role = role + rolelist.get(i).getORM_ROLE_PK()+"#"+rolelist.get(i).getORM_ROLE_DESC()+",";
		}
		
		if (role != null && !role.equals("")
				&& role.endsWith(",")) {
			role = role.substring(0, role.length() - 1);
		}

		request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, role);
		/*}catch(Exception e)
		{
			logger.error(e);
		}*/
		return "writePlainText";
	}
	public String getRoleMenu() throws Exception
	{
		//logger.info("calling getRoleMenu method ");
		/*try
		{*/
			String menuVal="";
			
			String rolePk=roleBean.getRole();
			
			List<MenuBean> menuForRole=roleService.getRoleMenuDetails(rolePk);
			
			for (int i=0;i<menuForRole.size();i++) {
				if (menuForRole.get(i).getMenuKey().contains(".00")) {
					menuVal = menuVal +  menuForRole.get(i).getMenuKey().split("\\.")[0].toString()+",";
				}else
				{
					menuVal = menuVal + menuForRole.get(i).getDisplayName()+"_"+menuForRole.get(i).getMenuKey()+",";
				}
				
			}
			
			if (menuVal != null && !menuVal.equals("")
					&& menuVal.endsWith(",")) {
				menuVal = menuVal.substring(0, menuVal.length() - 1);
			}
			
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, menuVal);
		/*}catch(Exception e)
		{
			logger.error(e);
		}*/
		return "writePlainText";
	}

	public String addRole()  throws Exception{
		//logger.info("calling addRole method ");
		/*try {*/
			roleBean.setStatus("A");
			roleBean.setSelectList(getStatusList());
		/*} catch (Exception e) {
			logger.info(e);
		}*/
		displayMenus();
		return "addRole";
	}

	public String addRoleDetails()  throws Exception{
		//logger.info("calling addRoleDetails method ");

		if(roleBean.getStatus()==null || roleBean.getStatus().trim().equals(""))
			roleBean.setStatus("A");
		roleBean.setSelectList(getStatusList());
		displayMenus();
		
		int module_PK = 0;
		StringBuilder err_msg = new StringBuilder("");
		/*try {*/

			if (roleBean.getRoleCode() == null
					|| roleBean.getRoleCode().trim().equals("")) {
				err_msg = err_msg.append("<li>Please enter Role Code .</li>");
			}
			if (err_msg.toString().trim().equals("")) {
				module_PK = roleService.getRolePK(roleBean.getRoleCode());

				roleBean.setSelectList(getStatusList());
				roleBean.setUsername(loggedInUser.getUsername());
			}
			if (module_PK != 0 && roleBean.getSetFlag().equals("")) {
				err_msg = err_msg.append("<li>Please enter unique Role Code .</li>");
			}

			if (roleBean.getModuleName() == null
					|| roleBean.getModuleName().trim().equals("")) {
				err_msg = err_msg.append("<li>Please enter Role Name .</li>");
			}
			else if (roleBean.getModuleName().trim().length() > 100) {
				err_msg = err_msg.append("<li>Do not enter Role Name more than 100 characters.</li>");
			}

			if (!err_msg.toString().trim().equals("")) {
				addActionMessage(err_msg.toString());
				roleBean.setErrMsg(err_msg.toString());
				roleBean.setShowModuleDetails("false");
			}

			if (err_msg.toString().trim().equals("")) {

				if (!roleBean.getSetFlag().equals("true")) {

					int moduleInsertCount = roleService.insertRoleDetails(roleBean);

					if (moduleInsertCount > 0) {
						roleBean.setSetFlag("true");
						roleBean.setShowModuleDetails("true");
						roleBean.setSucMsg("Role Added successfully!!");
					} else {
						roleBean.setShowModuleDetails("false");

					}
				}

			}

		/*} catch (Exception e) {
			logger.error(e);
		}*/


		return "addRole";
	}
	
	public String addRoleMenu()  throws Exception{
		//logger.info("calling addRoleMenu method ");

		displayMenus();
		StringBuilder err_msg = new StringBuilder("");
		/*try {*/

			if (roleBean.getRoleType() == null
					|| roleBean.getRoleType().trim().equals("0")) {
				err_msg = err_msg.append("<li>Please enter Role Type .</li>");
			}
			if (roleBean.getRole() == null
					|| roleBean.getRole().trim().equals("0")) {
				err_msg = err_msg.append("<li>Please enter Role Name .</li>");
			}
			if (err_msg.toString().trim().equals("")) {
				//module_PK = roleService.getRolePK(roleBean.getRole());

				roleBean.setSelectList(getStatusList());
				roleBean.setUsername(loggedInUser.getUsername());
			}
			
			if (!err_msg.toString().trim().equals("")) {
				addActionMessage(err_msg.toString());
				roleBean.setErrMsg(err_msg.toString());
				roleBean.setShowModuleDetails("false");
			}

			if (err_msg.toString().trim().equals("")) {

					List<String> list=new ArrayList<String>();
					
					for (MenuMasterParentBean value : roleBean.getParentMenuDetailsList()) {
						if(value!=null){
							if(!value.getMenu().equals("false")){
								list.add(value.getMenu());
								
								//logger.info(value.getMenu());
							}
						}
					}
					for (MenuMasterChildBean value : roleBean.getChildMenuDetailsList()) {
						if(value!=null){
							if(!value.getMenu().equals("false")){
								list.add(value.getMenu());
								
								//logger.info(value.getMenu());
							}
						}
					}
					if(list.size()>0){
						int delete=roleService.deleteAccessControl(roleBean.getRole());
						int[] roleAccessInsertCount = roleService.insertAccessControlDetails(list,roleBean.getRole());
	
						if (roleAccessInsertCount.length > 0) {
							roleBean.setSetFlag("true");
							roleBean.setShowModuleDetails("true");
							roleBean.setSucMsg("Mapping Added successfully.");
						} else {
							roleBean.setShowModuleDetails("false");
	
						}
					}else
					{
						err_msg = err_msg.append("<li>Please select atleast one checkbox .</li>");
						addActionMessage(err_msg.toString());
						roleBean.setErrMsg(err_msg.toString());
						roleBean.setShowModuleDetails("false");
					}
			}
			searchRoleMenu();
		/*} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}*/


		return "roleMenuMaster";
	}

	public String getRoleMenuMappingExists() throws Exception
	{
		//logger.info("calling getRoleMenuMappingExists method ");
		String result="writePlainText";
		
		/*try
		{*/
			String rolePk=roleBean.getRole();
			
			List<MenuBean> menuForRole=roleService.getRoleMenuDetails(rolePk);
			
			request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, String.valueOf(menuForRole.size()));
			
		/*}catch(Exception e)
		{
			logger.error(e);
		}*/
		return result;
	}
	
	public String editRoleDetails() throws Exception{
		//logger.info("calling editRoleDetails method ");
		displayMenus();
		RoleBean roleBeanResult = new RoleBean();
		/*try {*/
			roleBeanResult = roleService.getRoleEditDetails(roleBean);
			roleBean.setRoleCode(roleBeanResult.getORM_ROLE_CODE());
			roleBean.setModuleId(roleBeanResult.getORM_ROLE_PK());
			roleBean.setModuleName(roleBeanResult.getORM_ROLE_DESC());
			roleBean.setSelectList(getStatusList());
			roleBean.setStatus(roleBeanResult.getORM_STATUS());
		/*} catch (Exception e) {
			logger.error(e);
		}*/
		return "editRole";
	}

	

	public String updateRoleDetails() throws Exception{
		//logger.info("calling updateRoleDetails method ");
		displayMenus();

		int updateCount = 0;
		roleBean.setSelectList(getStatusList());
		roleBean.setStatus(roleBean.getStatus());

		StringBuilder err_msg = new StringBuilder("");
		if (roleBean.getRoleCode() == null
				|| roleBean.getRoleCode().trim().equals("")) {
			err_msg = err_msg.append("<li>Please enter Role Code .</li>");
		}
		if (roleBean.getModuleName() == null
				|| roleBean.getModuleName().trim().equals("")) {
			err_msg = err_msg.append("<li>Please enter Role Name .</li>");
		}else if (roleBean.getModuleName().trim().length() > 100) {
			err_msg = err_msg.append("<li>Role Name shouldn't exceed 100 characters.</li>");
		}

		if (!err_msg.toString().trim().equals("")) {
			roleBean.setErrMsg(err_msg.toString());
		} else {
			/*try {*/
				boolean flag = false;

				int moduleId = roleService.getRolePK(roleBean.getRoleCode());
				if(moduleId==0){
					flag=false;
				}
			
				int moduleUpdateCount = 0;
				if (!flag) {
					moduleUpdateCount = roleService.updateRoleDetails(roleBean);
				}


				if (flag) {
					roleBean.setErrMsg(err_msg.toString());
				}

				if (err_msg.toString().trim().equals("")&& moduleUpdateCount > 0 && updateCount > 0) {
					roleBean.setShowModuleDetails("true");
					roleBean.setSucMsg("Role Updated successfully!!");
					roleBean.setPopUpFlag("Y");
				} else if (err_msg.toString().trim().equals("")
						&& moduleUpdateCount > 0
						) {
					roleBean.setShowModuleDetails("true");
					roleBean.setSucMsg("Role Updated successfully!!");
					roleBean.setPopUpFlag("Y");
				}

				else {
					roleBean.setShowModuleDetails("false");
				}

			/*} catch (Exception e) {
				logger.info(e.getMessage());
			}*/
		}
		if (!err_msg.toString().trim().equals("")) {
			addActionMessage(err_msg.toString());
			roleBean.setErrMsg(err_msg.toString());
		}
		return "editRole";
	}

	
	public String resetRole()  throws Exception{
		//logger.info("calling resetRole method ");
		displayMenus();
		/*try {*/
			roleBean.setSelectList(getStatusList());
			roleBean.setStatus(null);

		/*} catch (Exception e) {
		}*/
		return "roleMaster";
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
