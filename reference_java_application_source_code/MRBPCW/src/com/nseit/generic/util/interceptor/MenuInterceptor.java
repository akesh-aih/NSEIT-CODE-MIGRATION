package com.nseit.generic.util.interceptor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.MenuMasterChildBean;
import com.nseit.generic.util.MenuMasterParentBean;
import com.nseit.generic.util.StageOffBean;
import com.nseit.generic.util.StageOnBean;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class MenuInterceptor implements Interceptor
{

	private HttpServletRequest request;
	
	private static final long serialVersionUID = 2805739524898724406L;
	private  Log logger = LogFactory.getLog(MenuInterceptor.class);

	public void destroy(){
	}

	public void init(){
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception{
		try {
			//logger.info("menu intercept is called");
			request=ServletActionContext.getRequest();
			Users user = null;
			HttpSession session = request.getSession();
			if(session.getAttribute("SESSION_USER")!=null){
				user = (Users)session.getAttribute("SESSION_USER");
			}
			List<MenuMasterParentBean> newParentList = new ArrayList<MenuMasterParentBean>();
			//This line of code get the all parent menu list from cache 
			List<MenuMasterParentBean> parentList = ConfigurationConstants.getInstance().getParentMenuList();
			
			if(user!=null){
				if(user.getRoleFK()!=null && user.getRoleFK().contains(",")){
					String[] str = user.getRoleFK().split(",");
					for (int i = 0; i < str.length; i++) {
						for (MenuMasterParentBean menuMasterParentBean : parentList) {
							if(menuMasterParentBean.getRoleFK().equals(str[i])){
								newParentList.add(menuMasterParentBean);
							}
						}
					}
				}else{
					for (MenuMasterParentBean menuMasterParentBean : parentList) {
						if(menuMasterParentBean.getRoleFK().equals(user.getRoleFK())){
							newParentList.add(menuMasterParentBean);
						}
					}
				}
				
				List<MenuMasterParentBean> uniqueParentList = removeParentDuplicates(newParentList);
				
				//This line of code will get all the stage on list of parent that is assign in OES_STAGE_ON table. on the basis of user status it will get
				//all the mapped stage from table 
				List<MenuMasterParentBean> stageOnParentList = getStageOnParentList(user, session, uniqueParentList);
				//This line of code will remove the parent on the basis of stage off all the stage of parameter are define in OES_STAGE_OFF table.
				List<MenuMasterParentBean> finalParentList = getStageOffParentList(user,stageOnParentList);

				//This line of code will used when we are checking the mandatory condition of stage if stage e.g.(upload signature,upload documents) are
				// mandatory then we have to check that candidate has filled the data or not if the data is already filled then we will get true flag in
				// dataFound attribute in action otherwise we will show the message for mandatory condition
				String dataNotFound = request.getParameter("dataNotFound");
				if(dataNotFound!=null){
					request.setAttribute("dataNotFound", dataNotFound);
				}else{
					request.setAttribute("dataNotFound", "");
				}
				request.setAttribute("parentList", finalParentList);
				
				//This line of code is used for getting the action from browser
				StringBuilder actionName = getAction(actionInvocation);
				//This line of code is used to get the menu key as per the action request that is come from browser 
				String menuKey = ConfigurationConstants.getInstance().getMenuKeyForUrl(actionName.toString());

				//If some parameter are exist in the action request then it will be getting by this line of code 
				String queryString = ((HttpServletRequest)request).getQueryString();
				if(queryString!=null){
					if (menuKey!=null && !menuKey.equals("") && menuKey.equals(user.getStage())){
						menuKey = ConfigurationConstants.getInstance().getNextStageForMenuKey(menuKey);
					}
				}
				//This line of code is used to get menu key after submission of jsp page   
				String requestMenuKeyParam  = request.getParameter("menuKey");
				//logger.info("menuKey Param "+requestMenuKeyParam);
				if(menuKey==null || menuKey.equals("")){
					menuKey = requestMenuKeyParam;
				}
				//This parameter is used to get the current menu key in every jsp page 
				request.setAttribute("menuKey", menuKey);
				//logger.info("menukey "+menuKey);
				
				if(menuKey!=null){
					if(menuKey.contains(".")){
						menuKey = menuKey.split("\\.")[0].toString();
					}
				}
				//This parameter is used to get the current parent menu key in every jsp page 
				request.setAttribute("parentMenuKey", menuKey);
				
				//This line of code is used to compare the condition for payment menu in menu.jsp using this we check that is current menu is payment
				// then all the child menu will be clickable
				request.setAttribute("paymentMenuKey", ConfigurationConstants.getInstance().getMenuKeyByMenuDesc(GenericConstants.PAYMENT));
				
				//This line of code is used to compare the condition for payment menu in menu.jsp using this we check that is current menu is payment
				// then all the child menu will be clickable
				request.setAttribute("viewHallTicketMenuKey", ConfigurationConstants.getInstance().getMenuKeyByMenuDesc(GenericConstants.VIEW_HALLTICKET));
				
				//every menu key is set in session so that current status of the page can be handle
				List<MenuMasterChildBean> childList = new ArrayList<MenuMasterChildBean>();

				user.setCurrentMenuKey(menuKey);
				session.setAttribute("SESSION_USER", user);
				//As per parent menu key this code will return the list of child
				String keyForChild = null;
				
				if(user.getRoleFK()!=null && user.getRoleFK().contains(",")){
					String[] str = user.getRoleFK().split(",");
					for (int i = 0; i < str.length; i++) {
						keyForChild = menuKey+"#"+str[i];
						if(ConfigurationConstants.getInstance().getChildListForParent(keyForChild)!=null){
							childList.addAll(ConfigurationConstants.getInstance().getChildListForParent(keyForChild));
						}
					}
				}else{
					keyForChild = menuKey+"#"+user.getRoleFK();
					if(ConfigurationConstants.getInstance().getChildListForParent(keyForChild)!=null){
						childList.addAll(ConfigurationConstants.getInstance().getChildListForParent(keyForChild));
					}
				}
				List<MenuMasterChildBean> uniqueChildList = removeChildDuplicates(childList);
				//List<MenuMasterChildBean> childList = ConfigurationConstants.getInstance().getChildListForParent(menuKey);
				request.setAttribute("childList", uniqueChildList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage());
		}
		return actionInvocation.invoke();
	}

	private StringBuilder getAction(ActionInvocation actionInvocation) {
		//logger.info(actionInvocation.getProxy().getActionName());
		StringBuilder actionName = new StringBuilder();
		actionName.append(actionInvocation.getProxy().getActionName().concat(".action"));
		String queryString = ((HttpServletRequest)request).getQueryString();
		if(queryString!=null){
			actionName.append("?");
			actionName.append(queryString);
		}
		return actionName;
	}

	private List<MenuMasterParentBean> getStageOffParentList(Users user,List<MenuMasterParentBean> stageOnParentList) {
		//logger.info("getStageOffParentList() is called");
		List<MenuMasterParentBean> stageOffParentList = new ArrayList<MenuMasterParentBean>();
		List<StageOffBean> stageOffList = null;
		if(user!=null){
			stageOffList = ConfigurationConstants.getInstance().getStageOffList(String.valueOf(user.getCandidateStatusId()));
			if(user.getUserType()!=null && user.getUserType().equals("C")){
				if(stageOffList==null){
					stageOffParentList = stageOnParentList;
				}else{
				for (MenuMasterParentBean menuMasterParentBean : stageOnParentList) {
					//if(menuMasterParentBean.getUserType().equals("C")){
						Integer counter = 0;
						for (StageOffBean stageOffBean : stageOffList) {
							if(menuMasterParentBean.getMenuKey().equals(stageOffBean.getOSO_STAGE_OFF())){
							}else{
								counter++;
							}
						}
						if(counter.equals(stageOffList.size())){
							stageOffParentList.add(menuMasterParentBean);
					}
				  //}
				}
			  }
			}else{
				stageOffParentList = stageOnParentList;
			}
		}else{
			stageOffParentList = stageOnParentList;
		}
		
		return stageOffParentList;
	}

	private List<MenuMasterParentBean> getStageOnParentList(Users user, HttpSession session,List<MenuMasterParentBean> parentList) {
		//logger.info("getStageOnParentList() is called");
		List<MenuMasterParentBean> stageOnParentList = new ArrayList<MenuMasterParentBean>();
		List<StageOnBean> stageOnList = null;
		if(user!=null){
			stageOnList = ConfigurationConstants.getInstance().getStageOnList(String.valueOf(user.getCandidateStatusId()));
			if(user.getUserType()!=null && user.getUserType().equals("C")){
				for (MenuMasterParentBean menuMasterParentBean : parentList) {
					//if(menuMasterParentBean.getUserType().equals("C")){
						for (StageOnBean stageOnBean : stageOnList) {
							if(menuMasterParentBean.getMenuKey().equals(stageOnBean.getOSON_STAGE_ON())){
								stageOnParentList.add(menuMasterParentBean);
							}
						}
					//}
				}
			}else{
				stageOnParentList = parentList;
			}
		}else{
			stageOnParentList = parentList;
		}
		return stageOnParentList;
	}
	
	private List<MenuMasterParentBean> removeParentDuplicates(List<MenuMasterParentBean> originalList) {
		List<MenuMasterParentBean> uniqlst = new ArrayList<MenuMasterParentBean>();
		Set<String> displayName = new HashSet<String>();
		for( MenuMasterParentBean masterParentBean : originalList) {
		    if(displayName.add(masterParentBean.getDisplayName())) {
		    	uniqlst.add(masterParentBean);
		    }
		}
	   return uniqlst;
	}
	private List<MenuMasterChildBean> removeChildDuplicates(List<MenuMasterChildBean> originalList) {
		List<MenuMasterChildBean> uniqlst = new ArrayList<MenuMasterChildBean>();
		Set<String> displayName = new HashSet<String>();
		for( MenuMasterChildBean masterChildBean : originalList) {
		    if(displayName.add(masterChildBean.getOMCM_MENU_DESC())) {
		    	uniqlst.add(masterChildBean);
		    }
		}
	   return uniqlst;
	}
	
}
