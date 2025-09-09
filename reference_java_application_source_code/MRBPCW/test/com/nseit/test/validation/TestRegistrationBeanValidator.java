package com.nseit.test.validation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nseit.generic.action.RegistrationAction;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.validation.RegistrationBeanValidator;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;



/**
 * 
 */

/**
 * @author ajadhav
 *
 */
@Slf4j
@Data
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestRegistrationBeanValidator {
	//private RegistrationBean bean;
	/*@Before
	public void init() {
		bean=new RegistrationBean();
	}*/
	
	@Autowired public RegistrationBeanValidator validator;
	@Autowired public RegistrationBean bean;
	@Test
	public void testValidateInitialsWithValidParameters() throws Exception {
		bean.setInitials("1");
		log.info("Bean value for Registration Validator for initials with valid parameters: {} ",bean);
		List<String> errorsList= new ArrayList<>();
		validator.validateInitials(bean, errorsList);
		assertNull("test case field error field is not null : ",
			CollectionUtils.isEmpty(errorsList) ? null : errorsList);
	}
	
	@Test
	public void testValidateInitialsWithNullParameter() throws Exception {
		log.info("testValidateInitialsWithNullParameter() method called");
		bean.setInitials(null);
		log.info("testValidateInitialsWithNullParameter   : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateInitials(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateGenderWithValidParameters() throws Exception {
		log.info("testValidateGenderWithValidParameters() method called");
		bean.setGender("10");
		log.info("Bean value for testValidateGenderWithValidParameters: {} ",bean);
		List<String> errorsList =new ArrayList<>();
		validator.validateGender(bean, errorsList);
		assertNull("test case field error field is not null : ",
				CollectionUtils.isEmpty(errorsList) ? null : errorsList);
	}
	
	@Test
	public void testValidateGenderWithNullParameter() throws Exception {
		log.info("testValidateGenderWithNullParameter() method called");
		bean.setGender(null);
		log.info("testValidateGenderWithNullParameter   : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateInitials(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateGenderWithInitialsCase1() throws Exception {
		log.info("testValidateGenderWithInitialsCase1() method called");
		bean.setGender("1");
		bean.setInitials("8");
		log.info("testValidateGenderWithNullParameter   : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateGenderWithInitialsCase1(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}
	@Test
	public void testValidateGenderWithInitialsCase2() throws Exception {
		log.info("testValidateGenderWithInitialsCase2() method called");
		bean.setGender("2");
		bean.setInitials("9");
		log.info("testValidateGenderWithNullParameter   : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateGenderWithInitialsCase1(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}
	@Test
	public void testValidateGenderWithInitialsCase3() throws Exception {
		log.info("testValidateGenderWithInitialsCase3() method called");
		bean.setGender("2");
		bean.setInitials("10");
		log.info("testValidateGenderWithNullParameter   : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateGenderWithInitialsCase1(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}
	
	@Test
	public void testValidateFirstNameWithValidParameter() throws Exception {
		log.info("testValidateFirstNameWithValidParameter() method called");
		bean.setCandidateFirstName("Akanksha");
		log.info("testValidateFirstNameWithValidParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCandidateFirstName(bean, errorMessage);
		assertNull("test case field error field is not null : ",
				CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}
	
	@Test
	public void testValidateFirstNameWithBlankSpaceParameter() throws Exception {
		log.info("testValidateFirstNameWithBlankSpaceParameter() method called");
		bean.setCandidateFirstName(" ");
		log.info("testValidateFirstNameWithBlankSpaceParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCandidateFirstName(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateFirstNameWithEmptyParameter() throws Exception {
		log.info("testValidateFirstNameWithEmptyParameter() method called");
		bean.setCandidateFirstName("");
		log.info("testValidateFirstNameWithEmptyParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCandidateFirstName(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateFirstNameWithValidPattern() throws Exception {
		log.info("testValidateFirstNameWithValidPattern() method called");
		bean.setCandidateFirstName("Akanksha");
		log.info("testValidateFirstNameWithValidPattern model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateValidPatternForFirstName(bean, errorMessage);
		assertTrue("pattern should be valid", Pattern.matches("[a-zA-Z ]{1,50}", bean.getCandidateFirstName()));
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}

	

	@Test
	public void testValidateFirstNameWithInvalidPattern() throws Exception {
		log.info("testValidateFirstNameWithInvalidPattern() method called");
		bean.setCandidateFirstName("Akanksha%");
		log.info("testValidateFirstNameWithInvalidPattern model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateValidPatternForFirstName(bean, errorMessage);
		assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z ]{1,50}", bean.getCandidateFirstName()));
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	//for candidateMiddleName starts here
	
	@Test
	public void testValidateMiddleNameWithValidPattern() throws Exception {
		log.info("testValidateMiddleNameWithValidPattern() method called");
		bean.setCandidateMiddleName("Akanksha");
		log.info("testValidateMiddleNameWithValidPattern model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCandidateMiddleName(bean, errorMessage);
		assertTrue("pattern should be valid", Pattern.matches("[a-zA-Z ]{1,50}", bean.getCandidateMiddleName()));
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}

	

	@Test
	public void testValidateMiddleNameWithInvalidPattern() throws Exception {
		log.info("testValidateMiddleNameWithInvalidPattern() method called");
		bean.setCandidateMiddleName("Akanksha%");
		log.info("testValidateMiddleNameWithInvalidPattern model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCandidateMiddleName(bean, errorMessage);
		assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z ]{1,50}", bean.getCandidateMiddleName()));
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateMiddleNameWithBlankSpaceParameter() throws Exception {
		log.info("testValidateMiddleNameWithBlankSpaceParameter() method called");
		bean.setCandidateMiddleName(" ");
		log.info("testValidateMiddleNameWithBlankSpaceParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCandidateMiddleName(bean, errorMessage);
		assertNotNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	//for candidateLastName starts here
	
	@Test
	public void testValidateLastNameWithValidPattern() throws Exception {
		log.info("testValidateLastNameWithValidPattern() method called");
		bean.setCandidateLastName("Akanksha");
		log.info("testValidateLastNameWithValidPattern model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCandidateLastName(bean, errorMessage);
		assertTrue("pattern should be valid", Pattern.matches("[a-zA-Z ]{1,50}", bean.getCandidateLastName()));
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}

	

	@Test
	public void testValidateLastNameWithInvalidPattern() throws Exception {
		log.info("testValidateLastNameWithInvalidPattern() method called");
		bean.setCandidateLastName("Akanksha%");
		log.info("testValidateLastNameWithInvalidPattern model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCandidateLastName(bean, errorMessage);
		assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z ]{1,50}", bean.getCandidateLastName()));
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateLastNameWithBlankSpaceParameter() throws Exception {
		log.info("testValidateLastNameWithBlankSpaceParameter() method called");
		bean.setCandidateLastName(" ");
		log.info("testValidateLastNameWithBlankSpaceParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCandidateLastName(bean, errorMessage);
		assertNotNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}

	//for email
	@Test
	public void testValidateEmailAddressWithValidParameters() throws Exception {
		log.info("testValidateEmailAddressWithValidParameters() method called");
		bean.setEmailAddress("ajadhav@nseit.com");
		log.info("testValidateEmailAddressWithValidParameters model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateEmailAddress(bean, errorMessage);
		assertNull("test case field error field is not null : ",
				CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}
	
	
	@Test
	public void testValidateEmailAddressWithBlankSpaceParameter() throws Exception {
		log.info("testValidateEmailAddressWithBlankSpaceParameter() method called");
		bean.setEmailAddress(" ");
		log.info("testValidateEmailAddressWithBlankSpaceParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateEmailAddress(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateEmailAddressWithEmptyParameter() throws Exception {
		log.info("testValidateEmailAddressWithEmptyParameter() method called");
		bean.setEmailAddress("");
		log.info("testValidateEmailAddressWithEmptyParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateEmailAddress(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateEmailAddressWithValidPattern() throws Exception {
		log.info("testValidateEmailAddressWithValidPattern() method called");
		bean.setEmailAddress("ajadhav@nseit.com");
		log.info("testValidateEmailAddressWithValidPattern model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		String EMAIL_PATTERN = 
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		validator.validateValidEmailAddress(bean, errorMessage);
		assertTrue("pattern should be valid", Pattern.matches(EMAIL_PATTERN, bean.getEmailAddress()));
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}

	

	@Test
	public void testValidateEmailAddressWithInvalidPattern() throws Exception {
		log.info("testValidateEmailAddressWithInvalidPattern() method called");
		bean.setEmailAddress("adhj#jfgk");
		log.info("testValidateFirstNameWithInvalidPattern model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		String EMAIL_PATTERN = 
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		validator.validateValidEmailAddress(bean, errorMessage);
		assertTrue("pattern should be Invalid", !Pattern.matches(EMAIL_PATTERN, bean.getEmailAddress()));
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	//test cases for confirm email ID starts here.
	@Test
	public void testValidateConfirmEmailAddressWithValidParameters() throws Exception {
		log.info("testValidateConfirmEmailAddressWithValidParameters() method called");
		bean.setConfirmEmailAddress("ajadhav@nseit.com");
		log.info("testValidateConfirmEmailAddressWithValidParameters model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateConfirmEmailAddress(bean, errorMessage);
		assertNull("test case field error field is not null : ",
				CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}
	
	
	@Test
	public void testValidateConfirmEmailAddressWithBlankSpaceParameter() throws Exception {
		log.info("testValidateEmailAddressWithBlankSpaceParameter() method called");
		bean.setEmailAddress(" ");
		log.info("testValidateEmailAddressWithBlankSpaceParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateEmailAddress(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateConfirmEmailAddressWithEmptyParameter() throws Exception {
		log.info("testValidateConfirmEmailAddressWithEmptyParameter() method called");
		bean.setConfirmEmailAddress("");
		log.info("testValidateConfirmEmailAddressWithEmptyParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateConfirmEmailAddress(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateConfirmEmailAddressWithValidPattern() throws Exception {
		log.info("testValidateConfirmEmailAddressWithValidPattern() method called");
		bean.setConfirmEmailAddress("ajadhav@nseit.com");
		log.info("testValidateConfirmEmailAddressWithValidPattern model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		String EMAIL_PATTERN = 
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		validator.validateValidConfirmEmailAddress(bean, errorMessage);
		assertTrue("pattern should be valid", Pattern.matches(EMAIL_PATTERN, bean.getConfirmEmailAddress()));
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
	}

	

	@Test
	public void testValidateConfirmEmailAddressWithInvalidPattern() throws Exception {
		log.info("testValidateConfirmEmailAddressWithInvalidPattern() method called");
		bean.setConfirmEmailAddress("adhj#jfgk");
		log.info("testValidateConfirmEmailAddressWithInvalidPattern model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		String EMAIL_PATTERN = 
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		validator.validateValidConfirmEmailAddress(bean, errorMessage);
		assertTrue("pattern should be Invalid", !Pattern.matches(EMAIL_PATTERN, bean.getConfirmEmailAddress()));
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}

	@Test
	public void testValidateBothEmailsAreSame() throws Exception {
		log.info("testValidateBothEmailsAreSame() method called");
		bean.setEmailAddress("ajadhav@nseit.com");
		bean.setConfirmEmailAddress("ajadhav@nseit.com");
		log.info("testValidateBothEmailsAreSame model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateBothEmails(bean, errorMessage);
		assertNull("test case field if error occurs", CollectionUtils.isEmpty(errorMessage) ? null : errorMessage);
		
	}
	
	//test cases for captcha starts here
	@Test
	public void testValidateCaptchaWithBlankSpaceParameter() throws Exception {
		log.info("testValidateCaptchaWithBlankSpaceParameter() method called");
		bean.setCaptcha(" ");
		log.info("testValidateCaptchaWithBlankSpaceParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCaptcha(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}
	
	@Test
	public void testValidateCaptchaWithEmptyParameter() throws Exception {
		log.info("testValidateCaptchaWithEmptyParameter() method called");
		bean.setCaptcha("");
		log.info("testValidateCaptchaWithEmptyParameter model data  : {} ", bean);
		List<String> errorMessage = new ArrayList<>();
		validator.validateCaptcha(bean, errorMessage);
		assertNull("test case field error field is null : ",
				CollectionUtils.isEmpty(errorMessage) ? errorMessage : null);
	}

}
