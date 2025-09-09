package com.nseit.generic.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.UserBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.UserService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.EmailValidator;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.MobileValidator;
import com.nseit.generic.util.aware.UserAware;
import com.opensymphony.xwork2.ModelDriven;

public class UserMasterAction extends BaseAction implements ModelDriven<UserBean>, UserAware {

	private static final long serialVersionUID = -843583614398012294L;

	private Logger logger = LoggerHome.getLogger(getClass());

	private UserBean userBean = new UserBean();
	private Pagination pagination = new Pagination(10, 1);
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}


	private UserService userService;

	@Override
	public void resetModel() {
		// TODO Auto-generated method stub

	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserBean getModel() {
		return userBean;
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


	public String getUserMaster() throws Exception{
		//logger.info("calling getUserMaster method ");
		displayMenus();
		userBean.setStatus(null);
		userBean.setRole(null);
		userBean.setRoleType(null);
		userBean.setUserId(null);
		userBean.setSelectList(getStatusList());
		return "addUser";
	}

	public String userMasterHome() throws Exception{
		//System.out.println("asdasdasdasdasdasdasdas");
		//logger.info("calling userMasterHome method ");
		displayMenus();
//		try {
			userBean.setRoleType(null);
			userBean.setStatus("A");
			userBean.setSelectList(getStatusList());

		/*} catch (Exception e) {
			logger.error(e);
		}*/
		return "userMaster";
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

	public String searchUser()  throws Exception{
//		try {
			//logger.info("calling searchUser method ");
			displayMenus();
			
			//logger.info(userBean.getStatus());
			List<UserBean> searchDetailsList = new ArrayList<UserBean>();
			int count = userService.getUserCount(userBean);
			pagination.setProperties(count);
			searchDetailsList = userService.getSearchUserDetails(userBean,pagination);
			userBean.setSearchDetailsList(userService.getSearchUserDetails(userBean,pagination));
			userBean.setTotalRecord(String.valueOf(count));
			userBean.setShowGrid("true");
			userBean.setAvailableRecordFlag("true");
			userBean.setSelectList(getStatusList());
			if(userBean.getSearchDetailsList().size()<1){
				userBean.setAvailableRecordFlag("false");	
			}			

		/*} catch (Exception e) {
			logger.error(e);
		}*/
		return "addUser";
	}

	public String addUser()  throws Exception{
		//logger.info("calling addUser method ");
//		try {
			
			userBean.setRoleType(userBean.getRoleType());
			userBean.setRole(userBean.getRole());
			userBean.setUserName(userBean.getUserName());
			userBean.setUserId(userBean.getUserId());
			userBean.setEmail(userBean.getEmail());
			userBean.setMobile(userBean.getMobile());
			userBean.setPassword(userBean.getPassword());
			userBean.setConfirmPassword(userBean.getConfirmPassword());
			userBean.setStatus(userBean.getStatus());
			userBean.setLoggedIn(loggedInUser.getUsername());
			userBean.setSelectList(getStatusList());
			int emailExist = 1;//userService.checkEmailId(userBean);
			int mobileExist = 1;//userService.checkMobile(userBean);
			int userIdExist= 1; //userService.checkUserId(userBean);
			
			
			
			
			StringBuilder err_msg = new StringBuilder("");
			
			if (userBean.getRoleType() == null
					|| userBean.getRoleType().trim().equals("") || userBean.getRoleType().equals("0") ) {
				err_msg = err_msg.append("<li>Please Select Role Type .</li>");
			}
			
			if (userBean.getRole() == null
					|| userBean.getRole().trim().equals("") || userBean.getRole().equals("0")) {
				err_msg = err_msg.append("<li>Please Select Role Name .</li>");
			}
			
			if (userBean.getUserName() == null
					|| userBean.getUserName().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter User Name .</li>");
			}
				
			if (userBean.getUserId() == null
					|| userBean.getUserId().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter User ID .</li>");
			}
			else
			{
				if(userIdExist > 0)
				{
					err_msg = err_msg.append("<li>User ID already exists.</li>");
				}
			}
			
			if (userBean.getEmail() == null
					|| userBean.getEmail().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter Email ID .</li>");
			}
			else
			{
				if(emailExist > 0)
				{
					err_msg = err_msg.append("<li>Email ID already exists.</li>");
				}
			}
			
			if (userBean.getEmail() != null ) {
				if(EmailValidator.validate(userBean.getEmail()))
				{}
				else
				{
					err_msg = err_msg.append("<li>Please Enter Correct Email ID .</li>");
				}	
			}
			
			if (userBean.getMobile() == null
					|| userBean.getMobile().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter Mobile Number .</li>");
			}
			else
			{
				if(mobileExist > 0)
				{
					err_msg = err_msg.append("<li>Mobile Number already exists.</li>");
				}
			}
			
			if(userBean.getMobile() != null)
			{
			if(MobileValidator.isValidMsisdn(userBean.getMobile()))
			{
			}
			else
			{
				err_msg = err_msg.append("<li>Please Enter Correct Mobile Number .</li>");
			}
			}
			
			if (userBean.getPassword() == null
					|| userBean.getPassword().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter Password .</li>");
			}
			
			if (userBean.getConfirmPassword() == null
					|| userBean.getConfirmPassword().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter Confirm Password .</li>");
				
			}
			if (!userBean.getPassword().equals(userBean.getConfirmPassword())) {
				err_msg = err_msg.append("<li>New Password and Confirm Password shoulb be same .</li>");
			}
			if (userBean.getStatus() == null
					|| userBean.getStatus().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Select Status .</li>");
			}
			
			if (!err_msg.toString().trim().equals("")) {
				addActionMessage(err_msg.toString());
				userBean.setErrMsg(err_msg.toString());
			}
			
			if (err_msg.toString().trim().equals("")) {
				int result = 0;
				
				result = userService.insertUserDetails(userBean);
				
				if(result > 0)
				{
					userBean.setShowModuleDetails("true");
					userBean.setSucMsg("User Added successfully!!");
				}
				else
				{
					userBean.setShowModuleDetails("false");
				}
			}
		/*} catch (Exception e) {
			logger.error(e);
		}*/
		displayMenus();
		return "userMaster";
	}

	public String editUserDetails() throws Exception{
		//logger.info("calling editUserDetails method ");
		displayMenus();
		UserBean userBeanResult = new UserBean();
//		try {

			userBeanResult = userService.getUserEditDetails(userBean);
			userBean.setUserPk(userBeanResult.getUserPk());
			userBean.setUserId(userBeanResult.getUserId());
			userBean.setUserName(userBeanResult.getUserName());
			userBean.setRole(userBeanResult.getRole());
			userBean.setSelectList(getStatusList());
			userBean.setStatus(userBeanResult.getStatus());
			userBean.setEmail(userBeanResult.getEmail());
			userBean.setMobile(userBeanResult.getMobile());
			userBeanResult.setPassword(DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(userBeanResult.getPassword()));
			userBean.setPassword(userBeanResult.getPassword());
			userBean.setSetFlag("true");
			userBean.setShowModuleDetails("true");
		/*} catch (Exception e) {
			logger.info(e);
		}*/
		return "editUser";
	}

	

	public String updateUserDetails() throws Exception{
		displayMenus();
		logger.info("calling updateUserDetails method ");
//		try {
			userBean.setRoleType(userBean.getRoleType());
			userBean.setRole(userBean.getRole());
			userBean.setUserName(userBean.getUserName());
			userBean.setUserId(userBean.getUserId());
			userBean.setEmail(userBean.getEmail());
			userBean.setMobile(userBean.getMobile());
			userBean.setPassword(userBean.getPassword());
			userBean.setConfirmPassword(userBean.getConfirmPassword());
			userBean.setStatus(userBean.getStatus());
			userBean.setLoggedIn(loggedInUser.getUsername());
			userBean.setSelectList(getStatusList());
			
			StringBuilder err_msg = new StringBuilder("");
			
			if (userBean.getRoleType() == null
					|| userBean.getRoleType().trim().equals("") || userBean.getRoleType().equals("0") ) {
				err_msg = err_msg.append("<li>Please Select Role Type .</li>");
			}
			
			if (userBean.getRole() == null
					|| userBean.getRole().trim().equals("") || userBean.getRole().equals("0")) {
				err_msg = err_msg.append("<li>Please Select Role Name .</li>");
			}
			
			if (userBean.getUserName() == null
					|| userBean.getUserName().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter User Name .</li>");
			}
				
			if (userBean.getUserId() == null
					|| userBean.getUserId().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter User ID .</li>");
			}
			
			if (userBean.getEmail() == null
					|| userBean.getEmail().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter Email ID .</li>");
			}
			
			if (userBean.getEmail() != null ) {
				if(EmailValidator.validate(userBean.getEmail()))
				{}
				else
				{
					err_msg = err_msg.append("<li>Please Enter Correct Email ID .</li>");
				}	
			}
			
			if (userBean.getMobile() == null
					|| userBean.getMobile().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter Mobile Number .</li>");
			}
			
			if(userBean.getMobile() != null)
			{
			if(MobileValidator.isValidMsisdn(userBean.getMobile()))
			{
			}
			else
			{
				err_msg = err_msg.append("<li>Please Enter Correct Mobile Number .</li>");
			}
			}
			
			if (userBean.getPassword() == null
					|| userBean.getPassword().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter Password .</li>");
			}
			
			if (userBean.getConfirmPassword() == null
					|| userBean.getConfirmPassword().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Enter Confirm Password .</li>");
				
			}
			if (!userBean.getPassword().equals(userBean.getConfirmPassword())) {
				err_msg = err_msg.append("<li>New Password and Confirm Password shoulb be same .</li>");
			}
			if (userBean.getStatus() == null
					|| userBean.getStatus().trim().equals("")) {
				err_msg = err_msg.append("<li>Please Select Status .</li>");
			}
			
			if (!err_msg.toString().trim().equals("")) {
				addActionMessage(err_msg.toString());
				userBean.setErrMsg(err_msg.toString());
			}
			
			if (err_msg.toString().trim().equals("")) {
				int result = 0;
				
				result = userService.updateUserDetails(userBean);
				
				if(result > 0)
				{
					userBean.setShowModuleDetails("true");
					userBean.setSucMsg("User Updated successfully!!");
				}
				else
				{
					userBean.setShowModuleDetails("false");
				}
			}
		/*} catch (Exception e) {
			logger.info(e);
		}*/
		
		return "editUser";
	}

	
	public String resetUser()  throws Exception{
		//logger.info("calling resetUser method ");
		displayMenus();
//		try {
			userBean.setRole(null);
			userBean.setRoleType(null);
			userBean.setUserId(null);
			userBean.setSelectList(getStatusList());
			userBean.setStatus(null);

		/*} catch (Exception e) {
			logger.info(e);
		}*/
		return "addUser";
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
