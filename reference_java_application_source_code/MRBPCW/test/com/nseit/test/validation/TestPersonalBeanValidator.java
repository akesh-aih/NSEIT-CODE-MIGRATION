/**
 * 
 */
package com.nseit.test.validation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.validation.PersonalBeanValidator;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ajadhav
 *
 */
@Slf4j
@Data
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestPersonalBeanValidator {
@Autowired public PersonalBeanValidator validator;
//@Autowired public CandidateBean bean;

// test cases for domicile started.
@Test
public void testValidateDomcileUpNullCheck() throws Exception {
	log.info("candidate bean data : {} ",validator.getCandidateBean());
	validator.getCandidateBean().setDomicileUp(null);
	log.info("testValidateDomcileUpNullCheck: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateDomcileUpNullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateDomcileUpWithValidParameters() throws Exception {
	validator.getCandidateBean().setDomicileUp("1");
	log.info("testValidateDomcileUpWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateDomcileUpNullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
	CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateDomcileWithValidParameters() throws Exception {
	validator.getCandidateBean().setDomicileCertificateUp("1");
	log.info("testValidateDomcileWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validDomicile(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateDomcileWithNullParameters() throws Exception {
	validator.getCandidateBean().setDomicileCertificateUp(null);
	log.info("testValidateDomcileWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validDomicile(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateDomcileWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setDomicileCertificateUp("");
	log.info("testValidateDomcileWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validDomicile(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateDomcileWithBlankSpace() throws Exception {
	validator.getCandidateBean().setDomicileCertificateUp(" ");
	log.info("testValidateDomcileWithBlankSpace: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validDomicile(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateDomcileWithInValidParameters() throws Exception {
	validator.getCandidateBean().setDomicileCertificateUp("Akanksha");
	log.info("testValidateDomcileWithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "5",value = "Akanksha";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validDomicile(validator.getCandidateBean(), errorsList, errorField);
	//assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+"}",value.trim()));
	assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getDomicileCertificateUp()));
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
//test cases for domicile certificate date starts here
@Test
public void testValidateDomcileCertDateNullParameters() throws Exception {
	validator.getCandidateBean().setDomicileCertificate(null);
	log.info("testValidateDomcileCertDateNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validDomicileIssueDate(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateDomcileCertDateWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setDomicileCertificate("");
	log.info("testValidateDomcileCertDateWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validDomicileIssueDate(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateDomcileCertDateAndDOBCompare() throws Exception {
	validator.getCandidateBean().setDomicileCertificate("19-10-1991");
	validator.getCandidateBean().setDateOfBirth("19-10-1999");
	log.info("testValidateDomcileCertDateWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateDomicileCertDt(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}
//test cases for domicile certificate serial no. starts here

@Test
public void testValidateDomcileCertSerialNoNullParameters() throws Exception {
	validator.getCandidateBean().setDomicileSerial(null);
	log.info("testValidateDomcileCertSerialNoNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateDomicileSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateDomcileCertSerialNoWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setDomicileSerial("");
	log.info("testValidateDomcileCertSerialNoWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateDomicileSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateDomcileCertSerialNoWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().setDomicileSerial(" ");
	log.info("testValidateDomcileCertSerialNoWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateDomicileSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateDomcileCertSerialNoWithValidParameters() throws Exception {
	validator.getCandidateBean().setDomicileSerial("1");
	log.info("testValidateDomcileCertSerialNoWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateDomicileSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testValidateDomcileCertSerialNoWithInvalidParameters() throws Exception {
	validator.getCandidateBean().setDomicileSerial("Akanksha");
	log.info("testValidateDomcileCertSerialNoWithInvalidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3",value = "Akanksha";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateDomicileSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getDomicileSerial()));
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

//test cases for domicile app no start here

@Test
public void testvalidateCertAppNoNullParameters() throws Exception {
	validator.getCandidateBean().setAppNoDomCert(null);
	log.info("testvalidateCertAppNoNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAppNoCert(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateDomcileCertAppNoWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setDomicileSerial("");
	log.info("testValidateDomcileCertAppNoWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAppNoCert(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateDomcileCertAppNoWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().setDomicileSerial(" ");
	log.info("testValidateDomcileCertAppNoWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAppNoCert(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateDomcileCertAppNoWithValidParameters() throws Exception {
	validator.getCandidateBean().setAppNoDomCert("1");
	log.info("testValidateDomcileCertSerialNoWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAppNoCert(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testValidateDomcileCertAppNoWithInvalidParameters() throws Exception {
	validator.getCandidateBean().setAppNoDomCert("Akanksha");
	log.info("testValidateDomcileCertSerialNoWithInvalidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3",value = "Akanksha";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAppNoCert(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getAppNoDomCert()));
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

 //test cases for category starts here
@Test
public void testValidateCategoryWithValidParameters() throws Exception {
	validator.getCandidateBean().setCategoryValDesc("1");
	log.info("testValidateCategoryWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCategorySelect(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testValidateCategoryWithNullParameters() throws Exception {
	validator.getCandidateBean().setCategoryValDesc("1");
	log.info("testValidateCategoryWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCategorySelect(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateCategoryCertAuthWithNullParameters() throws Exception {
	validator.getCandidateBean().setCatCertNo(null);
	log.info("testValidateCategoryCertAuthWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertIssueAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCategoryCertAuthWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setCatCertNo("");
	log.info("testValidateCategoryCertAuthWithEmnptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertIssueAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCategoryCertAuthWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().setCatCertNo("");
	log.info("testValidateCategoryCertAuthWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertIssueAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCategoryCertAuthWithValidParameters() throws Exception {
	validator.getCandidateBean().setCatCertNo("1");
	log.info("testValidateCategoryCertAuthWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertIssueAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateCategoryCertAuthWithInValidParameters() throws Exception {
	validator.getCandidateBean().setCatCertNo("Hello");
	log.info("testValidateCategoryCertAuthWithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3",value = "Akanksha";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertIssueAuth(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getCatCertNo()));
	assertNull("Pattern should be invalid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateCatCertIssueDtNullParameters() throws Exception {
	validator.getCandidateBean().setCategoryCertIssDt(null);
	log.info("testValidateCatCertIssueDtNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertIssueDt(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCatCertIssueDtWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setDomicileCertificate("");
	log.info("testValidateCatCertIssueDtWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertIssueDt(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCertIssueDtCompare() throws Exception {
	validator.getCandidateBean().setCategoryCertIssDt("19-10-1991");
	validator.getCandidateBean().setCategoryValDesc("2");
	log.info("testValidateCertIssueDtCompare: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertIssueDtCompare(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}
@Test
public void testValidateCertIssueDtWithDobCompare() throws Exception {
	validator.getCandidateBean().setCategoryCertIssDt("19-10-1971");
	validator.getCandidateBean().setDateOfBirth("02-05-1988");
	log.info("testValidateCertIssueDtWithDobCompare: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertIssueDtWithDob(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCatCertSerialNoForNullParameters() throws Exception {
	validator.getCandidateBean().setCatSerial(null);
	log.info("testValidateCatCertSerialNoForNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCatCertSerialNoForEmptyParameters() throws Exception {
	validator.getCandidateBean().setCatSerial("");
	log.info("testValidateCatCertSerialNoForEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCatCertSerialNoForBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().setCatSerial(" ");
	log.info("testValidateCatCertSerialNoForBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCatCertSerialforValidParameters() throws Exception {
	validator.getCandidateBean().setCatSerial("Akanksha");
	log.info("testValidateCatCertSerialforValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Parameters should be Invalid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateCatCertSerialforInValidParameters() throws Exception {
	validator.getCandidateBean().setCatSerial("Akanksha&");
	log.info("testValidateCatCertSerialforValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getCatSerial()));
	assertNotNull("Parameters should be Invalid : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}
// test cases for category app number starts here:

@Test
public void testValidateCatCertAppNoForNullParameters() throws Exception {
	validator.getCandidateBean().setAppNoForCat(null);
	log.info("testValidateCatCertAppNoForNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertAppNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCatCertAppNoForEmptyParameters() throws Exception {
	validator.getCandidateBean().setCatSerial("");
	log.info("testValidateCatCertAppNoForEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertAppNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCatCertAppNoForBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().setAppNoForCat(" ");
	log.info("testValidateCatCertAppNoForBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertAppNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateCatCertAppforValidParameters() throws Exception {
	validator.getCandidateBean().setAppNoForCat("Akanksha");
	log.info("testValidateCatCertAppforValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertAppNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Parameters should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateCatCertAppforInValidParameters() throws Exception {
	validator.getCandidateBean().setAppNoForCat("Akanksha&");
	log.info("testValidateCatCertAppforInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCertAppNo(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getAppNoForCat()));
	assertNotNull("Parameters should be Invalid : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

//Test Cases for SECTION-II Starts here **********************


//test cases for freedom fighter starts here

@Test
public void testValidatedFreedomFighterSelectForNullParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighter(null);
	log.info("testValidatedFreedomFighterSelectForNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validatedFreedomFighterSelect(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateFreedomFighterSelectforValidParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighter("Akanksha");
	log.info("testValidateFreedomFighterSelectforValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validatedFreedomFighterSelect(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Parameters should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateFreedomFighterDtforNullParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterDt(null);
	log.info("testValidateFreedomFighterDtforNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreedomFighterDtNullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateFreedomFighterDtWithDobCompare() throws Exception {
	validator.getCandidateBean().setFreedomFighterDt("19-10-1971");
	validator.getCandidateBean().setDateOfBirth("02-05-1988");
	log.info("testValidateFreedomFighterDtWithDobCompare: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreedomFighterDtwithDob(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateFreedomFighterSerialNoNullParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterSerial(null);
	log.info("testValidateFreedomFighterSerialNoNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreedomFighterSerialNoNullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateFreedomFighterSerialNoWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterSerial("");
	log.info("testValidateFreedomFighterSerialNoWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreeDomFighterSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNotNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateFreedomFighterSerialNoWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterSerial(" ");
	log.info("testValidateFreedomFighterSerialNoWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreeDomFighterSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNotNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateFreedomFighterSerialNoWithValidParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterSerial("1");
	log.info("testValidateFreedomFighterSerialNoWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreeDomFighterSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testValidateFreedomFighterSerialNoWithInvalidParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterSerial("Akanksha");
	log.info("testValidateFreedomFighterSerialNoWithInvalidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3",value = "Akanksha";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreeDomFighterSerialNo(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getFreedomFighterSerial()));
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
//test cases for freedom fighter authority starts here
@Test
public void testValidateFreedomFighterCertIssueAuthNoNullParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterAuthority(null);
	log.info("testValidateFreedomFighterCertIssueAuthNoNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreedomFighterCertIssueAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateFreedomFighterCertIssueAuthWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterAuthority("");
	log.info("testValidateFreedomFighterCertIssueAuthWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreedomFighterCertIssueAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateFreedomFighterCertIssueAuthWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterAuthority(" ");
	log.info("testValidateFreedomFighterCertIssueAuthWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreedomFighterCertIssueAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateFreedomFighterCertIssueAuthWithValidParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterAuthority("1");
	log.info("testValidateFreedomFighterCertIssueAuthWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreedomFighterCertissueAuthValidity(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testValidateFreedomFighterCertIssueAuthWithInValidParameters() throws Exception {
	validator.getCandidateBean().setFreedomFighterAuthority("Akanksha");
	log.info("testValidateFreedomFighterCertIssueAuthWithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3",value = "Akanksha";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFreedomFighterCertissueAuthValidity(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getFreedomFighterAuthority()));
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

//test cases for Govt Employee starts here

@Test
public void testValidateGovtEmpNullParameters() throws Exception {
	validator.getCandidateBean().setGovernmentEmp(null);
	log.info("testValidateGovtEmpNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateGovEmpOptions(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateGovtEmpValidParameters() throws Exception {
	validator.getCandidateBean().setGovernmentEmp("1");
	log.info("testValidateGovtEmpValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateGovEmpOptions(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}


@Test
public void testValidateGovtEmpDtforNullParameters() throws Exception {
	validator.getCandidateBean().setGovernmentEmpDt(null);
	log.info("testValidateGovtEmpDtforNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateGovtEmpDt(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateGovtEmpDtWithDobCompare() throws Exception {
	validator.getCandidateBean().setGovernmentEmpDt("19-10-1971");
	validator.getCandidateBean().setDateOfBirth("02-05-1988");
	log.info("testValidateGovtEmpDtWithDobCompare: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCompareDobnGovtEmpDt(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

//test cases for NOC starts here:

@Test
public void testValidateNocDtforNullParameters() throws Exception {
	validator.getCandidateBean().setNocDt(null);
	log.info("testValidateNocDtforNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateNocDt(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateGovtEmpDtWithNOCDtCompare() throws Exception {
	validator.getCandidateBean().setGovernmentEmpDt("19-10-1971");
	validator.getCandidateBean().setNocDt("02-05-1968");
	log.info("testValidateGovtEmpDtWithNOCDtCompare: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCompareGovtEmpDtNocDt(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateGovtEmpDtWithValidParameters() throws Exception {
	validator.getCandidateBean().setNocSerial("1");
	log.info("testValidateGovtEmpDtWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateNocSerial(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testValidateNocSerialWithWithInValidParameters() throws Exception {
	validator.getCandidateBean().setNocSerial("Akanksha");
	log.info("testValidateNocSerialWithWithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateNocSerial(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getNocSerial()));
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

//noc authority


@Test
public void testValidateNocAuthNullParameters() throws Exception {
	validator.getCandidateBean().setNocAuthority(null);
	log.info("testValidateNocAuthNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateNocAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateNocAuthWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setNocAuthority("");
	log.info("testValidateNocAuthWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateNocAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateNocAuthWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().setNocAuthority(" ");
	log.info("testValidateNocAuthWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateNocAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateNocAuthWithValidParameters() throws Exception {
	validator.getCandidateBean().setNocAuthority("1");
	log.info("testValidateNocAuthWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateNocAuth(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testValidateNocAuthWithInValidParameters() throws Exception {
	validator.getCandidateBean().setNocAuthority("Akanksha");
	log.info("testValidateNocAuthWithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3",value = "Akanksha";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateNocAuth(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getNocAuthority()));
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

//Test cases for exservicemen starts here

@Test
public void testValidateExServicemenNullParameters() throws Exception {
	validator.getCandidateBean().setIsExServicemen(null);
	log.info("testValidateExServicemenNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateExServicemen(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateExServicemenValidParameters() throws Exception {
	validator.getCandidateBean().setIsExServicemen("6");
	log.info("testValidateExServicemenValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateExServicemen(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}


@Test
public void testValidateEsmDateNullParameters() throws Exception {
	validator.getCandidateBean().setIsExServicemen("6");
	validator.getCandidateBean().setEsmDateOfEnlistment(null);
	log.info("testValidateEsmDateNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateExServicemen(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testvalidateEsmDtofDischargeNullParameters() throws Exception {
	validator.getCandidateBean().setEsmDateOfDischarge(null);
	log.info("testValidateEsmDateNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateEsmDtofDischarge(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateDobNullCheckParameters() throws Exception {
	validator.getCandidateBean().setDateOfBirth(null);
	log.info("testValidateDobNullCheckParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateDobNullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

//SECTION III Starts Here*******************

@Test
public void testValidateFatherNameWithValidParameters() throws Exception {
	validator.getCandidateBean().setFathersName("Sanjay");
	log.info("testValidateCategoryWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFatherName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testValidateFatherNameWithNullParameters() throws Exception {
	validator.getCandidateBean().setFathersName(null);
	log.info("testValidateCategoryWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFatherName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}



@Test
public void testValidateFatherNameWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setFathersName("");
	log.info("testValidateFatherNameWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFatherName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateFatherNameWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().setFathersName("");
	log.info("testValidateFatherNameWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFatherName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}


@Test
public void testValidateFatherNameWithInValidParameters() throws Exception {
	validator.getCandidateBean().setFathersName("Hello");
	log.info("testValidateFatherNameWithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateFatherName(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("Pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getFathersName()));
	assertNull("Pattern should be invalid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}


@Test
public void testValidateMotherNameWithValidParameters() throws Exception {
	validator.getCandidateBean().setMothersName("Sanjay");
	log.info("testValidateMotherNameWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateMotherName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testValidateMotherNameWithNullParameters() throws Exception {
	validator.getCandidateBean().setMothersName(null);
	log.info("testValidateMotherNameWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateMotherName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}



@Test
public void testValidateMotherNameWithEmptyParameters() throws Exception {
	validator.getCandidateBean().setMothersName("");
	log.info("testValidateMotherNameWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateMotherName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testValidateMotherNameWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().setMothersName(" ");
	log.info("testValidateFatherNameWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateMotherName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}


@Test
public void testValidateMotherNameWithInValidParameters() throws Exception {
	validator.getCandidateBean().setMothersName("rajni");
	log.info("testValidateMotherNameWithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateMotherName(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("Pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getMothersName()));
	assertNull("Pattern should be invalid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

//Address test cases starts here.



@Test
public void testvalidateAddressField1WithValidParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAddressFiled1("Sanjay");
	log.info("testValidateMotherNameWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAddressField1(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testvalidateAddressField1WithNullParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAddressFiled1(null);
	log.info("testValidateMotherNameWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAddressField1(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}



@Test
public void testvalidateAddressField1WithEmptyParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAddressFiled1("");
	log.info("testValidateMotherNameWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAddressField1(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testvalidateAddressField1WithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAddressFiled1(" ");
	log.info("testvalidateAddressField1WithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAddressField1(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}


@Test
public void testvalidateAddressField1WithInValidParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAddressFiled1("India");
	log.info("testvalidateAddressField1WithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAddressField1(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("Pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getAddressBean().getAddressFiled1()));
	assertNull("Pattern should be invalid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}


//test cases for perm state
@Test
public void testvalidateStateWithNullParameters() throws Exception {
	validator.getCandidateBean().setStateVal(null);
	log.info("testvalidateStateWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateStateNull(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}
//test cases for perm district

@Test
public void testvalidateDistrictForStateWithNullParameters() throws Exception {
	validator.getCandidateBean().setDistrictVal(null);
	log.info("testvalidateDistrictForStateWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateDistrictForState(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}
//test cases for perm city

@Test
public void testvalidateCityNameWithValidParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setCityNameother("Panaji");
	log.info("testvalidateCityNameWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCityNameValidity(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testvalidateCityNameWithNullParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setCityNameother(null);
	log.info("testvalidateCityNameWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCityName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}



@Test
public void testvalidateCityNameWithEmptyParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setCityNameother("");
	log.info("testvalidateCityNameWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCityName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testvalidateCityNameWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setCityNameother(" ");
	log.info("testvalidateCityNameWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCityName(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}


@Test
public void testvalidateCityNameWithInValidParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setCityNameother("India");
	log.info("testvalidateCityNameWithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateCityName(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("Pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getAddressBean().getCityNameother()));
	assertNull("Pattern should be invalid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
//test cases for perm pincode

@Test
public void testvalidatePincodeWithValidParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setPinCode("413004");
	log.info("testvalidatePincodeWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validatePincodeNullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testvalidatePincodeWithNullParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setPinCode(null);
	log.info("testvalidateCityNameWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validatePincodeNullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}



@Test
public void testvalidatePincodeWithEmptyParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setPinCode("");
	log.info("testvalidatePincodeWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validatePincodeNullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testvalidatePincodeWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setPinCode(" ");
	log.info("testvalidatePincodeWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validatePincodeNullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

//test cases for correspondence address starts here


@Test
public void testvalidateAlternateAddressField1WithValidParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAlternateAddressFiled1("hello");
	log.info("testvalidateAlternateAddressField1WithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateAddressField1NullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testvalidateAlternateAddressField1WithNullParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAlternateAddressFiled1(null);
	log.info("testvalidateAlternateAddressField1WithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateAddressField1NullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}



@Test
public void testvalidateAlternateAddressField1WithEmptyParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAlternateAddressFiled1("");
	log.info("testvalidateAlternateAddressField1WithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateAddressField1NullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}

@Test
public void testvalidateAlternateAddressField1WithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAlternateAddressFiled1(" ");
	log.info("testvalidateAddressField1WithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateAddressField1NullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}


@Test
public void testvalidateAlternateAddressField1WithInValidParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAlternateAddressFiled1("India");
	log.info("testvalidateAddressField1WithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateAddressField1NullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("Pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getAddressBean().getAlternateAddressFiled1()));
	assertNull("Pattern should be invalid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}


//test cases for Alt state
@Test
public void testvalidateAltStateWithNullParameters() throws Exception {
	validator.getCandidateBean().setAltStateVal(null);
	log.info("testvalidateAltStateWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAltStateValNullCheck(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}
//test cases for Alt district

@Test
public void testvalidateAlternateDistrictForStateWithNullParameters() throws Exception {
	validator.getCandidateBean().setAltDistrictVal(null);
	log.info("testvalidateAlternateDistrictForStateWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateDistrictVal(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}
//test cases for Alt city

@Test
public void testvalidateAlternateCityNameOtherWithValidParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAlternateCityother("Panaji");
	log.info("testvalidateAlternateCityNameOtherWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateCityNameOther(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
@Test
public void testvalidateAlternateCityNameOtherWithNullParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAlternateCityother(null);
	log.info("testvalidateAlternateCityNameOtherWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateCityNameOther(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}



@Test
public void testvalidateAlternateCityNameOtherWithEmptyParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAlternateCityother("");
	log.info("testvalidateAlternateCityNameOtherWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateCityNameOther(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testvalidateAlternateCityNameOtherWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAlternateCityother(" ");
	log.info("testvalidateAlternateCityNameOtherWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateCityNameOther(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}


@Test
public void testvalidateAlternateCityNameOtherWithInValidParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setAlternateCityother("India");
	log.info("testvalidateAlternateCityNameOtherWithInValidParameters: {} ",validator.getCandidateBean());
	String initLength = "1",maxLength = "3";
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternateCityNameOther(validator.getCandidateBean(), errorsList, errorField);
	assertTrue("Pattern should be Invalid", !Pattern.matches("[a-zA-Z0-9 /\\\\.-]{"+initLength+ ","+maxLength+ "}",validator.getCandidateBean().getAddressBean().getAlternateCityother()));
	assertNull("Pattern should be invalid : ",
		CollectionUtils.isEmpty(errorsList) ? null : errorsList);
}
//test cases for Alt pincode

@Test
public void testvalidateAlternatePincodeWithValidParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setPinCode("413004");
	log.info("testvalidateAlternatePincodeWithValidParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternatePincode(validator.getCandidateBean(), errorsList, errorField);
	assertNull("Pattern should be valid : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}
@Test
public void testvalidateAlternatePincodeWithNullParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setPinCode(null);
	log.info("testvalidateAlternatePincodeWithNullParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternatePincode(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}



@Test
public void testvalidateAlternatePincodeWithEmptyParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setPinCode("");
	log.info("testvalidateAlternatePincodeWithEmptyParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternatePincode(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}

@Test
public void testvalidateAlternatePincodeWithBlankSpaceParameters() throws Exception {
	validator.getCandidateBean().getAddressBean().setPinCode(" ");
	log.info("testvalidateAlternatePincodeWithBlankSpaceParameters: {} ",validator.getCandidateBean());
	List<String> errorsList= new ArrayList<>();
	ArrayList<String> errorField= new ArrayList<>();
	validator.validateAlternatePincode(validator.getCandidateBean(), errorsList, errorField);
	assertNull("test case field error field is not null : ",
		CollectionUtils.isEmpty(errorsList) ? errorsList : null);
}






}
