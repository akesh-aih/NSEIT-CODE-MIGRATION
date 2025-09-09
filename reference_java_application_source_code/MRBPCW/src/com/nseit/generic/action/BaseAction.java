package com.nseit.generic.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ParametersAware;
import org.apache.struts2.action.ServletRequestAware;
import org.apache.struts2.action.ServletResponseAware;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.util.TokenHelper;

import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.MenuMasterChildBean;
import com.nseit.generic.util.MenuMasterParentBean;
import com.nseit.generic.util.StageOffBean;
import com.nseit.generic.util.StageOnBean;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements SessionAware,
ParametersAware, ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 7358294271669344446L;
	private  Log logger = LogFactory.getLog(BaseAction.class);
	protected Map<String, Object> sessionAttributes;
	protected Map<String, Object> requestAttributes;
	protected Users loggedInUser;
	protected ActionInvocation actionInvocation;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpParameters httpParameters;

	public void setSession(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}

	public void setRequest(Map<String, Object> requestAttributes) {
		this.requestAttributes = requestAttributes;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void setActionInvocation(ActionInvocation actionInvocation) {
		this.actionInvocation = actionInvocation;
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
	
	public abstract void resetModel();

	public String redirectToNextActivePage(String menuKey, Users loggedInUser) {
		String result = null;
		try {
			if (menuKey != null) {
				List<MenuMasterChildBean> childList = new ArrayList<MenuMasterChildBean>();
				if (loggedInUser != null) {
					if (loggedInUser.getRoleFK() != null
							&& loggedInUser.getRoleFK().contains(",")) {
						String[] str = loggedInUser.getRoleFK().split(",");
						for (int i = 0; i < str.length; i++) {
							String keyForChild = menuKey + "#" + str[i];
							if (ConfigurationConstants.getInstance()
									.getChildListForParent(keyForChild) != null) {
								childList.addAll(ConfigurationConstants
										.getInstance().getChildListForParent(
												keyForChild));
							}
						}
					} else {
						String keyForChild = menuKey + "#"
								+ loggedInUser.getRoleFK();
						logger.info(ConfigurationConstants.getInstance()
								.getChildListForParent(keyForChild));
						if (ConfigurationConstants.getInstance()
								.getChildListForParent(keyForChild) != null) {
							childList.addAll(ConfigurationConstants
									.getInstance().getChildListForParent(
											keyForChild));
						}
					}
				}
				for (MenuMasterChildBean menuMasterChildBean : childList) {
					String activeStatus = menuMasterChildBean
							.getOMCM_ACTIVE_STATUS();
					if (activeStatus != null && activeStatus.equals("A")) {
						String actionUrl = ConfigurationConstants.getInstance()
								.getActionUrl(
										menuMasterChildBean.getOMCM_MENU_KEY());
						result = actionUrl;
						/*
						 * request.setAttribute(DESTINATION_URL,actionUrl);
						 * result = REDIRECT_ACTION_URL;
						 */
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public void displayMenus() {
		Users user = null;
		try {
			
			//logger.info("BaseAction method displayMenus() call");
			request = ServletActionContext.getRequest();

			HttpSession session = request.getSession();
			if (session.getAttribute("SESSION_USER") != null) {
				user = (Users) session.getAttribute("SESSION_USER");
			}
			List<MenuMasterParentBean> newParentList = new ArrayList<MenuMasterParentBean>();
			// This line of code get the all parent menu list from cache
			List<MenuMasterParentBean> parentList = ConfigurationConstants
					.getInstance().getParentMenuList();

			if (user != null) {
				if (user.getRoleFK() != null && user.getRoleFK().contains(",")) {
					String[] str = user.getRoleFK().split(",");
					for (int i = 0; i < str.length; i++) {
						for (MenuMasterParentBean menuMasterParentBean : parentList) {
							if (menuMasterParentBean.getRoleFK().equals(str[i])) {
								newParentList.add(menuMasterParentBean);
							}
						}
					}
				} else {
					for (MenuMasterParentBean menuMasterParentBean : parentList) {
						if (menuMasterParentBean.getRoleFK().equals(
								user.getRoleFK())) {
							newParentList.add(menuMasterParentBean);
						}
					}
				}

				List<MenuMasterParentBean> uniqueParentList = removeParentDuplicates(newParentList);
				List<MenuMasterParentBean> stageOnParentList = getStageOnParentList(
						user, uniqueParentList);
				List<MenuMasterParentBean> finalParentList = getStageOffParentList(
						user, stageOnParentList);
				String dataNotFound = request.getParameter("dataNotFound");
				if (dataNotFound != null) {
					request.setAttribute("dataNotFound", dataNotFound);
				} else {
					request.setAttribute("dataNotFound", "");
				}
				session.setAttribute("parentList", finalParentList);
				StringBuilder actionName = getAction(user);
				String menuKey = ConfigurationConstants.getInstance()
						.getMenuKeyForUrl(actionName.toString());

				// If some parameter are exist in the action request then it
				// will be getting by this line of code
				String queryString = ((HttpServletRequest) request)
						.getQueryString();
				if (queryString != null) {
					if (menuKey != null && !menuKey.equals("")
							&& menuKey.equals(user.getStage())) {
						menuKey = ConfigurationConstants.getInstance()
								.getNextStageForMenuKey(menuKey);
					}
				}
				// This line of code is used to get menu key after submission of
				// jsp page
				String requestMenuKeyParam = request.getParameter("menuKey");
				String sessionMenuKeyParam = (String) session.getAttribute("menuKey");
				if (menuKey == null || menuKey.equals("")) {
					menuKey = requestMenuKeyParam;
				}
				if (menuKey == null || menuKey.equals("")) {
					menuKey = sessionMenuKeyParam;
				}
				// This parameter is used to get the current menu key in every
				// jsp page
				request.setAttribute("menuKey", menuKey);
				session.setAttribute("menuKey", menuKey);
				//logger.info("menukey " + menuKey + " for user "
				//		+ user.getUsername());

				if (menuKey != null) {
					if (menuKey.contains(".")) {
						menuKey = menuKey.split("\\.")[0].toString();
					}
				}
				// This parameter is used to get the current parent menu key in
				// every jsp page
				request.setAttribute("parentMenuKey", menuKey);

				// This line of code is used to compare the condition for
				// payment menu in menu.jsp using this we check that is current
				// menu is payment
				// then all the child menu will be clickable
				request.setAttribute("paymentMenuKey", ConfigurationConstants
						.getInstance().getMenuKeyByMenuDesc(
								GenericConstants.PAYMENT));

				// This line of code is used to compare the condition for
				// payment menu in menu.jsp using this we check that is current
				// menu is payment
				// then all the child menu will be clickable
				request.setAttribute("viewHallTicketMenuKey",
						ConfigurationConstants.getInstance()
								.getMenuKeyByMenuDesc(
										GenericConstants.VIEW_HALLTICKET));

				// every menu key is set in session so that current status of
				// the page can be handle
				List<MenuMasterChildBean> childList = new ArrayList<MenuMasterChildBean>();

				user.setCurrentMenuKey(menuKey);
				session.setAttribute("SESSION_USER", user);
				// As per parent menu key this code will return the list of
				// child
				String keyForChild = null;

				if (user.getRoleFK() != null && user.getRoleFK().contains(",")) {
					String[] str = user.getRoleFK().split(",");
					for (int i = 0; i < str.length; i++) {
						keyForChild = menuKey + "#" + str[i];
						if (ConfigurationConstants.getInstance()
								.getChildListForParent(keyForChild) != null) {
							childList.addAll(ConfigurationConstants
									.getInstance().getChildListForParent(
											keyForChild));
						}
					}
				} else {
					keyForChild = menuKey + "#" + user.getRoleFK();
					if (ConfigurationConstants.getInstance()
							.getChildListForParent(keyForChild) != null) {
						childList.addAll(ConfigurationConstants.getInstance()
								.getChildListForParent(keyForChild));
					}
				}
				List<MenuMasterChildBean> uniqueChildList = removeChildDuplicates(childList);
				request.setAttribute("childList", uniqueChildList);
				session.setAttribute("childList", uniqueChildList);
				
				//For Skipping Special Marks Tab
			    /*String agequotaradiocheck = user.getAgequotaradiocheck();
			    String underQuotaOption = user.getUnderQuotaOption();
			    boolean skipSpeMrkFlag = false;
			    
				if(keyForChild != null && keyForChild.equals("2#1")) { // For Application Tab
					
					 if(agequotaradiocheck != null && agequotaradiocheck.equals("3")) { // Age Relxn Department 
			    		  
			    		  skipSpeMrkFlag =true;
			    	  }
			    	  else if (underQuotaOption != null && underQuotaOption.equals("1")) { //Departmental Under Quota
			    		  
			    		  skipSpeMrkFlag =true;
			    	  }
					 
					 if(skipSpeMrkFlag) {
						 //Remove Special Marks Menu from childList 
						 uniqueChildList.remove(6);
						 request.setAttribute("childList", uniqueChildList);
					 }
				}*/
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage());
		}
	}

	private StringBuilder getAction(Users user) {
		String url = request.getServletPath();
		String[] splitUrl = null;
		if (url.contains("/")) {
			splitUrl = url.split("/");
			url = splitUrl[1];
		}
		StringBuilder actionName = new StringBuilder();
		actionName.append(url);
		String queryString = ((HttpServletRequest) request).getQueryString();
		if (queryString != null) {
			actionName.append("?");
			actionName.append(queryString);
		}
		return actionName;
	}

	public List<MenuMasterParentBean> getStageOffParentList(Users user,
			List<MenuMasterParentBean> stageOnParentList) {
		//logger.info("getStageOffParentList() is called");
		List<MenuMasterParentBean> stageOffParentList = new ArrayList<MenuMasterParentBean>();
		List<StageOffBean> stageOffList = null;
		if (user != null) {
			stageOffList = ConfigurationConstants.getInstance()
					.getStageOffList(
							String.valueOf(user.getCandidateStatusId()));
			if (user.getUserType() != null && user.getUserType().equals("C")) {
				if (stageOffList == null) {
					stageOffParentList = stageOnParentList;
				} else {
					for (MenuMasterParentBean menuMasterParentBean : stageOnParentList) {
						// if(menuMasterParentBean.getUserType().equals("C")){
						Integer counter = 0;
						for (StageOffBean stageOffBean : stageOffList) {
							if (menuMasterParentBean.getMenuKey().equals(
									stageOffBean.getOSO_STAGE_OFF())) {
							} else {
								counter++;
							}
						}
						if (counter.equals(stageOffList.size())) {
							stageOffParentList.add(menuMasterParentBean);
						}
						// }
					}
				}
			} else {
				stageOffParentList = stageOnParentList;
			}
		} else {
			stageOffParentList = stageOnParentList;
		}

		return stageOffParentList;
	}

	public List<MenuMasterParentBean> getStageOnParentList(Users user,
			List<MenuMasterParentBean> parentList) {
		//logger.info("getStageOnParentList() is called");
		Users sessionUser = null;
		HttpSession session = request.getSession();
		if (session.getAttribute("SESSION_USER") != null) {
			sessionUser = (Users) session.getAttribute("SESSION_USER");
		}
		List<MenuMasterParentBean> stageOnParentList = new ArrayList<MenuMasterParentBean>();
		List<StageOnBean> stageOnList = null;
		if (user != null) {
			stageOnList = ConfigurationConstants.getInstance().getStageOnList(
					String.valueOf(sessionUser.getCandidateStatusId()));
			if (user.getUserType() != null && user.getUserType().equals("C")) {
				for (MenuMasterParentBean menuMasterParentBean : parentList) {
					// if(menuMasterParentBean.getUserType().equals("C")){
					for (StageOnBean stageOnBean : stageOnList) {
						if (menuMasterParentBean.getMenuKey().equals(
								stageOnBean.getOSON_STAGE_ON())) {
							stageOnParentList.add(menuMasterParentBean);
						}
					}
					// }
				}
			} else {
				stageOnParentList = parentList;
			}
		} else {
			stageOnParentList = parentList;
		}
		return stageOnParentList;
	}

	public List<MenuMasterParentBean> removeParentDuplicates(
			List<MenuMasterParentBean> originalList) {
		List<MenuMasterParentBean> uniqlst = new ArrayList<MenuMasterParentBean>();
		Set<String> displayName = new HashSet<String>();
		for (MenuMasterParentBean masterParentBean : originalList) {
			if (displayName.add(masterParentBean.getDisplayName())) {
				uniqlst.add(masterParentBean);
			}
		}
		return uniqlst;
	}

	public List<MenuMasterChildBean> removeChildDuplicates(
			List<MenuMasterChildBean> originalList) {
		List<MenuMasterChildBean> uniqlst = new ArrayList<MenuMasterChildBean>();
		Set<String> displayName = new HashSet<String>();
		for (MenuMasterChildBean masterChildBean : originalList) {
			if (displayName.add(masterChildBean.getOMCM_MENU_DESC())) {
				uniqlst.add(masterChildBean);
			}
		}
		return uniqlst;
	}

}
