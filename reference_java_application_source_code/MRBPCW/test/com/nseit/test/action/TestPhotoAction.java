/**
 * 
 */
package com.nseit.test.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.struts2.StrutsSpringTestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.nseit.generic.action.CandidateAction;
import com.nseit.generic.action.RegistrationAction;
import com.opensymphony.xwork2.ActionProxy;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
@TransactionConfiguration(transactionManager = "genericTransactionManager", defaultRollback = true)
@TestExecutionListeners(value = TransactionalTestExecutionListener.class)


/**
 * @author ajadhav
 *
 */
public class TestPhotoAction extends StrutsSpringTestCase {
   

	@Before
	public void init() throws Exception {
		log.info("init() method called");
		super.setUp();
		log.info("init() method end");

	}

	/**
	 * application - context path for junit
	 */
	private static final String DEFAULT_CONTEXT_LOCATION = "classpath:application-context.xml";

	/**
	 * 
	 */
	private Map<String, Object> requestAttribute = new HashMap<>();

	/**
	 * 
	 */
	private Map<String, Object> sessionAttribute = new HashMap<>();

	/**
	 * @param methodType
	 * @param registrationAction
	 */
	private void initialize(String methodType, CandidateAction candidateAction) {
		setMethodType(methodType);
		setServlet(candidateAction);
		setRequestAttribute(candidateAction);
		setSessionAttribute(candidateAction);
	}

	/**
	 * 
	 */
	// private void addGenerateOtpRequestParameter() {
	// request.setParameter("post", "1");
	// request.setParameter("otpFlag", "mobile");
	// request.setParameter("mobileNo1", "9517532492");
	// }

	/**
	 * 
	 */
	// private void addVerifyEmailRequestParameter() {
	// request.setParameter("post", "1");
	// request.setParameter("emailId", "junit@test.com");
	// request.setParameter("confEmailId", "junit@test.com");
	// }

	// private void addRegisterCandidateRequestParameter() {
	//
	// }

	/**
	 * @param candidateAction
	 */
	private void setSessionAttribute(CandidateAction candidateAction) {
		getSessionAttribute().put("SESSION_USER", null);
		candidateAction.setSession(getSessionAttribute());
	}

	/**
	 * @param candidateAction
	 */
	private void setRequestAttribute(CandidateAction candidateAction) {
		candidateAction.setRequest(getRequestAttribute());
	}

	/**
	 * @param candidateAction
	 */
	private void setServlet(CandidateAction candidateAction) {
		candidateAction.setServletRequest(request);
		candidateAction.setServletResponse(response);
	}

	/**
	 * @param registrationAction
	 */
	

	/**
	 * @param methodType
	 */
	private void setMethodType(String methodType) {
		request.setMethod(methodType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.StrutsTestCase#getActionProxy(java.lang.String)
	 */
	@Override
	protected ActionProxy getActionProxy(String uri) {
		return super.getActionProxy(uri);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.StrutsSpringTestCase#getContextLocations()
	 * 
	 * @return @configuration file location
	 */
	@Override
	protected String getContextLocations() {
		return DEFAULT_CONTEXT_LOCATION;
	}

	/**
	 * @throws ServletException
	 */

	

	@Test
	public void testValidateFirstNameWithBlankSpaceParameter() throws Exception {
		request.getSession().setAttribute("SESSION_USER", null);
		log.info("testValidateFirstNameWithBlankSpaceParameter() method called");
		ActionProxy actionProxy = getActionProxy("/CandidateAction_showUploadPhotograph.action");
		Map<String, Object> sessionMap = new HashMap<>();
		sessionMap.put("SESSION_USER", null);
		actionProxy.getInvocation().getInvocationContext().setSession(sessionMap);
		CandidateAction candidateAction = (CandidateAction) actionProxy.getAction();
		
//		actionProxy.execute();
		
		initialize("POST", candidateAction);
		//candidateAction.getModel().setCandidateFirstName(" ");
		log.info("testValidateFirstNameWithBlankSpaceParameter model data  : {} ", candidateAction.getModel());
		List<String> errorMessage = new ArrayList<>();
		candidateAction.getPhotoBeanValidator().validateAttachedPhoto(errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}


}
