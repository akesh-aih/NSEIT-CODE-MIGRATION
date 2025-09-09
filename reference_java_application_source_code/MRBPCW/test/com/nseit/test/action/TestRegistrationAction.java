package com.nseit.test.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

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
public class TestRegistrationAction extends StrutsSpringTestCase {

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
	private void initialize(String methodType, RegistrationAction registrationAction) {
		setMethodType(methodType);
		setServlet(registrationAction);
		setRequestAttribute(registrationAction);
		setSessionAttribute(registrationAction);
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
	 * @param registrationAction
	 */
	private void setSessionAttribute(RegistrationAction registrationAction) {
		registrationAction.setSession(getSessionAttribute());
	}

	/**
	 * @param registrationAction
	 */
	private void setRequestAttribute(RegistrationAction registrationAction) {
		registrationAction.setRequest(getRequestAttribute());
	}

	/**
	 * @param registrationAction
	 */
	private void setServlet(RegistrationAction registrationAction) {
		registrationAction.setServletRequest(request);
		registrationAction.setServletResponse(response);
	}

	/**
	 * @param registrationAction
	 */
	// private void setModelValue(RegistrationAction registrationAction) {
	// registrationAction.getModel().setCourses("1");
	// registrationAction.getModel().setCandidateFirstName("john");
	// registrationAction.getModel().setCandidateLastName("connor");
	// registrationAction.getModel().setCandidateMiddleName("terminator");
	// registrationAction.getModel().setCandidateName("john connor");
	// registrationAction.getModel().setEmailAddress("junit13@test.com");
	// registrationAction.getModel().setConfirmEmailAddress("junit13@test.com");
	// registrationAction.getModel().setMobileNo("9517532492");
	// registrationAction.getModel().setVerifyMobileOTPFlag("true");
	// registrationAction.getModel().setIpin("172.25.18.140");
	// registrationAction.getModel().setGender("1");
	// registrationAction.getModel().setNationVal("4");
	// registrationAction.getModel().setCaptcha("31939");
	//
	// }

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
	// private void loadStartupServlet() throws ServletException {
	// StartupServlet servlet = new StartupServlet();
	// servlet.init();
	// }

//	@Test
//	public void testValidateFirstNameWithValidParameter() throws Exception {
//		log.info("testValidateFirstNameWithValidParameter() method called");
//		RegistrationAction registrationAction = (RegistrationAction) getActionProxy(
//				"/RegistrationAction_registerCandidate.action").getAction();
//		initialize("POST", registrationAction);
//		registrationAction.getModel().setCandidateFirstName("Ajay");
//		log.info("testValidateFirstNameWithValidParameter model data  : {} ", registrationAction.getModel());
//		List<String> errorMessage = new ArrayList<>();
//		registrationAction.getRegistrationBeanValidator().validateCandidateFirstName(registrationAction.getModel(), errorMessage);
//		assertNull("test case field error field is not null : ",
//				CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
//	}

//	@Test
//	public void testValidateFirstNameWithNullParameter() throws Exception {
//		log.info("testValidateFirstNameWithNullParameter() method called");
//		RegistrationAction registrationAction = (RegistrationAction) getActionProxy(
//				"/RegistrationAction_registerCandidate.action").getAction();
//		initialize("POST", registrationAction);
//		registrationAction.getModel().setCandidateFirstName(null);
//		log.info("testValidateFirstNameWithNullParameter model data  : {} ", registrationAction.getModel());
//		List<String> errorMessage = new ArrayList<>();
//		registrationAction.getRegistrationBeanValidator().validateCandidateFirstName(registrationAction.getModel(), errorMessage);
//		assertNull("test case field error field is null : ",
//				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
//	}
//	
	

	@Test
	public void testValidateFirstNameWithBlankSpaceParameter() throws Exception {
		log.info("testValidateFirstNameWithBlankSpaceParameter() method called");
		RegistrationAction registrationAction = (RegistrationAction) getActionProxy(
				"/RegistrationAction_registerCandidate.action").getAction();
		initialize("POST", registrationAction);
		registrationAction.getModel().setCandidateFirstName(" ");
		log.info("testValidateFirstNameWithBlankSpaceParameter model data  : {} ", registrationAction.getModel());
		List<String> errorMessage = new ArrayList<>();
		registrationAction.getRegistrationBeanValidator().validateCandidateFirstName(registrationAction.getModel(), errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}
//
//	@Test
//	public void testValidateFirstNameWithEmptyParameter() throws Exception {
//		log.info("testValidateFirstNameWithEmptyParameter() method called");
//		RegistrationAction registrationAction = (RegistrationAction) getActionProxy(
//				"/RegistrationAction_registerCandidate.action").getAction();
//		initialize("POST", registrationAction);
//		registrationAction.getModel().setCandidateFirstName("");
//		log.info("testValidateFirstNameWithEmptyParameter model data  : {} ", registrationAction.getModel());
//		List<String> errorMessage = new ArrayList<>();
//		registrationAction.validateFirstName(errorMessage);
//		assertNull("test case field error field is null : ",
//				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
//	}
//
//	@Test
//	public void testValidateFirstNameWithValidPattern() throws Exception {
//		log.info("testValidateFirstNameWithValidPattern() method called");
//		RegistrationAction registrationAction = (RegistrationAction) getActionProxy(
//				"/RegistrationAction_registerCandidate.action").getAction();
//		initialize("POST", registrationAction);
//		log.info("testValidateFirstNameWithValidPattern model data  : {} ", registrationAction.getModel());
//		// assertSame("pattern should be valid", true,
//		// !registrationAction.validateNamePattern("Ajay"));
//		assertTrue("pattern should be valid", !registrationAction.validateNamePattern("Ajay"));
//	}
//
//	@Test
//	public void testValidateFirstNameWithInvalidPattern() throws Exception {
//		log.info("testValidateFirstNameWithInvalidPattern() method called");
//		RegistrationAction registrationAction = (RegistrationAction) getActionProxy(
//				"/RegistrationAction_registerCandidate.action").getAction();
//		initialize("POST", registrationAction);
//		log.info("testValidateFirstNameWithInvalidPattern model data  : {} ", registrationAction.getModel());
//		boolean valid = registrationAction.validateNamePattern("!@#$%^&*");
//		assertTrue("pattern should be Invalid", valid);
//	}

}
