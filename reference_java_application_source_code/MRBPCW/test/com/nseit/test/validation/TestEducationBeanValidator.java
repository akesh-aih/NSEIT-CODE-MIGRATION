package com.nseit.test.validation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.util.validation.EducationBeanValidator;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })

public class TestEducationBeanValidator {
	@Autowired
	public EducationBeanValidator eduBeanValidator;
	@Autowired
	public EducationDetailsBean eduBean;
	@Autowired
	public CandidateBean candidatebean;
	
//test cases for SSC Board	
	@Test
	public void testValidateSscBoardWithNullParameter() throws Exception {
		log.info("testValidateSscBoardWithNullParameter() method called");
		eduBean.setNameOfUniv(null);
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateSscBoard(eduBean, errorField, errorList, index);
		log.info("value : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscBoardWithValidParameter() throws Exception {
		log.info("testValidateSscBoardWithValidParameter() method called");
		eduBean.setNameOfUniv("1");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateSscBoard(eduBean, errorField, errorList, index);
		log.info("value : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNotNull("Parameter should be valid : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}
	
//Test Cases for SSC Other Board Field
	@Test
	public void testValidateSscOtherBoardWithNullParameter() throws Exception {
		log.info("testValidateSscOtherBoardWithNullParameter() method called");
		eduBean.setUniversityOth(null);
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);		
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateSscOtherBoard(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscOtherBoardWithEmptyParameter() throws Exception {
		log.info("testValidateSscOtherBoardWithEmptyParameter() method called");
		eduBean.setUniversityOth("");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);		
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateSscOtherBoard(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscOtherBoardWithBlankSpaceParameter() throws Exception {
		log.info("testValidateSscOtherBoardWithBlankSpaceParameter() method called");
		eduBean.setUniversityOth(" ");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);		
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateSscOtherBoard(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscOtherBoardWithValidParameter() throws Exception {
		log.info("testValidateSscOtherBoardWithValidParameter() method called");
		eduBean.setUniversityOth("abcd123,\"':.&-");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);		
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateSscOtherBoard(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertEquals(errorList, new ArrayList<>());
	}	
	@Test
	public void testValidateSscOtherBoardWithInvalidParameter() throws Exception {
		log.info("testValidateSscOtherBoardWithInvalidParameter() method called");
		eduBean.setUniversityOth("~!@#$%^*()_+<>?/[]|");  
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);		
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateSscOtherBoard(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertEquals("error : ", errorList , new ArrayList<>());
	}	
	@Test
	public void testValidateSscOtherBoardWithMinValue() throws Exception {
		log.info("testValidateSscOtherBoardWithMinValue() method called");
		ArrayList<String> errorField = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		log.info("testValidateSscOtherBoardWithMinValue : {} ", eduBean);		
		eduBeanValidator.validateFieldLength("Jyoti", 1, errorList, "error", Boolean.TRUE, errorField, "SscOtherBoard");
		log.info("eduBean {} : errorList {}  : ", eduBean,errorList);	
		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscOtherBoardWithMaxValue() throws Exception {
		log.info("testValidateSscOtherBoardWithMaxValue() method called");
		ArrayList<String> errorField = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		log.info("testValidateSscOtherBoardWithMaxValue : {} ", eduBean);		
		eduBeanValidator.validateFieldLength("Jyoti", 100, errorList, "error", Boolean.FALSE, errorField, "registrationNo");
		log.info("eduBean {} : errorField {} : errorList {}  : ", eduBean,errorList);	
		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}

//Test Cases For SSC Year & Month of Passing	
	@Test
	public void testValidateSscYrOfPassingWithNullParameter() throws Exception {
		log.info("testValidateSscYrOfPassingWithNullParameter() method called");
		eduBean.setDateOfPassing(null);
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);		
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateSscYrOfPassing(eduBean, errorField, errorList, index,"10-11-1980", "1992", "ssc");
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}
	@Test
	public void testValidateSscYrOfPassinWithEmptyParameter() throws Exception {
		log.info("testValidateSscYrOfPassinWithEmptyParameter() method called");
		eduBean.setDateOfPassing("");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateSscYrOfPassing(eduBean, errorField, errorList, index,"10-11-1980", "1992", "ssc");
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}
	@Test
	public void testValidateSscPassingDtCompareWitihDob() throws Exception {
		log.info("testValidateSscPassingDtCompareWitihDob() method called");
		eduBean.setDateOfPassing("19-10-2002");
		candidatebean.setDateOfBirth("02-05-1990");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);		
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateSscPassingDtCompareWitihDob(errorField, errorList, index, eduBean,"02-05-1990", "2002", "ssc");
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}
	
//Test Cases For SSC Roll No Field	
	@Test
	public void testValidateSscRollNoWithNullParameter() throws Exception {
		log.info("testValidateSscRollNoWithNullParameter() method called");
		eduBean.setRegistrationNo(null);
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		log.info("errorField : {} index : {} ", errorField, index);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateRegistrationNo(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscRollNoWithEmptyParameter() throws Exception {
		log.info("testValidateSscRollNoWithEmptyParameter() method called");
		eduBean.setRegistrationNo("");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		log.info("errorField : {} index : {} ", errorField, index);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateRegistrationNo(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscRollNoWithBlankSpaceParameter() throws Exception {
		log.info("testValidateSscRollNoWithBlankSpaceParameter() method called");
		eduBean.setRegistrationNo(" ");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateRegistrationNo(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscRollNoWithValidParameter() throws Exception {
		log.info("testValidateSscRollNoWithValidParameter() method called");
		eduBean.setRegistrationNo("Jyoti123/\\-");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		List<String> errorList = new ArrayList<>();	
		eduBeanValidator.validateRegistrationNo(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("Parameter should be valid : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscRollNoWithInvalidParameter() throws Exception {
		log.info("testValidateSscRollNoWithInvalidParameter() method called");
		eduBean.setRegistrationNo("~!@#$%^&*(),.<>?+_");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateRegistrationNo(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNotEquals("test case field error field is null : ", errorList , new ArrayList<>());		
	}		
	@Test
	public void testValidateSscRollNoWithMinValue() throws Exception {
		log.info("testValidateSscRollNoWithMinValue() method called");
		ArrayList<String> errorField = new ArrayList<>();
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateFieldLength("Jyoti", 1, errorList, "error", Boolean.TRUE, errorField, "registrationNo");
		log.info("testValidateSscRollNoWithMinValue {} : errorList {}  : ", eduBean,errorList);	
		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscRollNoWithMaxValue() throws Exception {
		log.info("testValidateSscRollNoWithMaxValue() method called");
		ArrayList<String> errorField = new ArrayList<>();
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateFieldLength("Jyoti", 25, errorList, "error", Boolean.FALSE, errorField, "registrationNo");
		log.info("testValidateSscRollNoWithMaxValue {} : errorList {}  : errorList {} :", eduBean,errorList);	
		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
//	@Test
//	public void testValidateRollNoWithValidPattern() throws Exception {
//		log.info("testValidateRollNoWithValidPattern() method called");
//		ArrayList<String> errorField = new ArrayList<>();
//		List<String> errorList = new ArrayList<>();
//		log.info("testValidateRollNoWithValidPattern : {} ", eduBean);		
//		eduBeanValidator.validatePatternWithMinMax("[a-zA-Z0-9 \\/-]","Jyoti123/\\-",1,25);
//		log.info("testValidateRollNoWithValidPattern {} : errorList {}  : ", eduBean,errorList);	
//		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
//	}
	
//Test Cases For SSC Certificate Number Field
	@Test
	public void testValidateSscCertSrNoWithNullParameter() throws Exception {
		log.info("testValidateSscCertSrNoWithNullParameter() method called");
		eduBean.setCertiSerialNum(null);
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateCertSerialNo(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscCertSrNoWithEmptyParameter() throws Exception {
		log.info("testValidateSscCertSrNoWithEmptyParameter() method called");
		eduBean.setCertiSerialNum("");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		log.info("testValidateSscCertSrNoWithEmptyParameter : {} ", eduBean);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateCertSerialNo(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscCertSrNoWithBlankSpaceParameter() throws Exception {
		log.info("testValidateSscCertSrNoWithBlankSpaceParameter() method called");
		eduBean.setCertiSerialNum(" ");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		List<String> errorList = new ArrayList<>();				
		eduBeanValidator.validateCertSerialNo(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscCertSrNoNoWithValidParameter() throws Exception {
		log.info("testValidateSscCertSrNoNoWithValidParameter() method called");
		eduBean.setCertiSerialNum("Jyoti123,/\\-");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		List<String> errorList = new ArrayList<>();				
		eduBeanValidator.validateCertSerialNo(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscCertSrNoWithInvalidParameter() throws Exception {
		log.info("testValidateSscCertSrNoWithInvalidParameter() method called");
		eduBean.setCertiSerialNum("~!@#$%^&*().<>?+_");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(0);
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateCertSerialNo(eduBean, errorField, errorList, index);
		log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
		assertNotEquals("error : ", errorList , new ArrayList<>());
//		("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);		
	}	
	@Test
	public void testValidateSscCertSrNoWithMinValue() throws Exception {
		log.info("testValidateSscCertSrNoWithMinValue() method called");
		ArrayList<String> errorField = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		log.info("testValidateSscCertSrNoWithMinValue : {} errorField : {}", eduBean,errorField);
		eduBeanValidator.validateFieldLength("Jyoti", 1, errorList, "error", Boolean.TRUE, errorField, "certiSerialNo");
		log.info("testValidateSscCertSrNoWithMinValue {} : errorList {}  : ", eduBean,errorList);	
		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateSscCertSrNoWithMaxValue() throws Exception {
		log.info("testValidateSscCertSrNoWithMaxValue() method called");
		ArrayList<String> errorField = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		log.info("testValidateSscCertSrNoWithMaxValue : {} errorField : {}", eduBean,errorField);	
		eduBeanValidator.validateFieldLength("Jyoti", 25, errorList, "error", Boolean.FALSE, errorField, "certiSerialNo");
		log.info("testValidateSscCertSrNoWithMaxValue {} : errorList {}  : ", eduBean,errorList);	
		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
//	@Test
//	public void testValidateCertSrNoWithValidPattern() throws Exception {
//		log.info("testValidateCertSrNoWithValidPattern() method called");
//		ArrayList<String> errorField = new ArrayList<>();
//		List<String> errorList = new ArrayList<>();
//		log.info("testValidateCertSrNoWithValidPattern : {} ", eduBean);		
//		eduBeanValidator.validatePatternWithMinMax("[a-zA-Z0-9 \\/-]","Jyoti123/\\-",1,25);
//		log.info("testValidateCertSrNoWithValidPattern {} : errorList {}  : ", eduBean,errorList);	
//		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
//	}
	
//Test Cases for Diploma university	
		@Test
		public void testValidateDipUiversityWithNullParameter() throws Exception {
			log.info("testValidateDipUiversityWithNullParameter() method called");
			eduBean.setNameOfUniv(null);
			ArrayList<String> errorField = new ArrayList<>();
			AtomicInteger index = new AtomicInteger(1);
			List<String> errorList = new ArrayList<>();
			eduBeanValidator.validateDipUiversity(eduBean, errorField, errorList, index);
			log.info("value : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
			assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		}	
		@Test
		public void testValidateDipUiversityWithValidParameter() throws Exception {
			log.info("testValidateDipUiversityWithValidParameter() method called");
			eduBean.setNameOfUniv("223");
			ArrayList<String> errorField = new ArrayList<>();
			AtomicInteger index = new AtomicInteger(1);
			List<String> errorList = new ArrayList<>();
			eduBeanValidator.validateDipUiversity(eduBean, errorField, errorList, index);
			log.info("value : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
			assertNotNull("Parameter should be valid : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		}
		
//Test Cases for Diploma other university	
		@Test
		public void testValidateDipOtherUnivWithNullParameter() throws Exception {
			log.info("testValidateDipOtherUnivWithNullParameter() method called");
			eduBean.setUniversityOth(null);
			ArrayList<String> errorField = new ArrayList<>();
			AtomicInteger index = new AtomicInteger(1);		
			List<String> errorList = new ArrayList<>();
			eduBeanValidator.validateDipOtherUniv(eduBean, errorField, errorList, index);
			log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
			assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		}	
		@Test
		public void testValidateDipOtherUnivdWithEmptyParameter() throws Exception {
			log.info("testValidateDipOtherUnivdWithEmptyParameter() method called");
			eduBean.setUniversityOth("");
			ArrayList<String> errorField = new ArrayList<>();
			AtomicInteger index = new AtomicInteger(1);		
			List<String> errorList = new ArrayList<>();
			eduBeanValidator.validateDipOtherUniv(eduBean, errorField, errorList, index);
			log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
			assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		}	
		@Test
		public void testValidateDipOtherUnivWithBlankSpaceParameter() throws Exception {
			log.info("testValidateSscOtherBoardWithBlankSpaceParameter() method called");
			eduBean.setUniversityOth(" ");
			ArrayList<String> errorField = new ArrayList<>();
			AtomicInteger index = new AtomicInteger(1);		
			List<String> errorList = new ArrayList<>();
			eduBeanValidator.validateDipOtherUniv(eduBean, errorField, errorList, index);
			log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
			assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		}	
		@Test
		public void testValidateDipOtherUnivWithValidParameter() throws Exception {
			log.info("testValidateDipOtherUnivWithValidParameter() method called");
			eduBean.setUniversityOth("abcd123,\"':.&-");
			ArrayList<String> errorField = new ArrayList<>();
			AtomicInteger index = new AtomicInteger(1);		
			List<String> errorList = new ArrayList<>();
			eduBeanValidator.validateDipOtherUniv(eduBean, errorField, errorList, index);
			log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
			assertEquals(errorList, new ArrayList<>());
		}	
		@Test
		public void testValidateDipOtherUnivWithInvalidParameter() throws Exception {
			log.info("testValidateSscOtherBoardWithInvalidParameter() method called");
			eduBean.setUniversityOth("~!@#$%^*()_+<>?/[]|");  
			ArrayList<String> errorField = new ArrayList<>();
			AtomicInteger index = new AtomicInteger(1);		
			List<String> errorList = new ArrayList<>();
			eduBeanValidator.validateDipOtherUniv(eduBean, errorField, errorList, index);
			log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
			assertEquals("error : ", errorList , new ArrayList<>());
		}	
		@Test
		public void testValidateDipOtherUnivWithMinValue() throws Exception {
			log.info("testValidateSscOtherBoardWithMinValue() method called");
			ArrayList<String> errorField = new ArrayList<>();
			List<String> errorList = new ArrayList<>();
			log.info("testValidateSscOtherBoardWithMinValue : {} ", eduBean);
//			AtomicInteger index = new AtomicInteger(1);	
//			eduBeanValidator.validatePatternDipOtherUniv(eduBean, errorField, errorList,index);
			eduBeanValidator.validateFieldLength("Jyoti", 1, errorList, "error", Boolean.TRUE, errorField, "SscOtherBoard");
			log.info("eduBean {} : errorList {}  : ", eduBean,errorList);	
			assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		}	
		@Test
		public void testValidateDipOtherUnivWithMaxValue() throws Exception {
			log.info("testValidateSscOtherBoardWithMaxValue() method called");
			ArrayList<String> errorField = new ArrayList<>();
			List<String> errorList = new ArrayList<>();
			log.info("testValidateSscOtherBoardWithMaxValue : {} ", eduBean);
//			AtomicInteger index = new AtomicInteger(1);	
//			eduBeanValidator.validatePatternDipOtherUniv(eduBean, errorField, errorList,index);
			eduBeanValidator.validateFieldLength("Jyoti", 100, errorList, "error", Boolean.FALSE, errorField, "registrationNo");
			log.info("eduBean {} : errorField {} : errorList {}  : ", eduBean,errorList);	
			assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		}

//Test Cases For Diploma Year & Month of Passing	
		@Test
		public void testValidateDipYrOfPassingWithNullParameter() throws Exception {
			log.info("testValidateDipYrOfPassingWithNullParameter() method called");
			eduBean.setDateOfPassing(null);
			ArrayList<String> errorField = new ArrayList<>();
			AtomicInteger index = new AtomicInteger(1);		
			List<String> errorList = new ArrayList<>();
			eduBeanValidator.validateDipYrOfPassing(eduBean, errorField, errorList, index,"10-11-1980", "1992", "ssc");
			log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
			assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		}
		@Test
		public void testValidateDipYrOfPassinWithEmptyParameter() throws Exception {
			log.info("testValidateDipYrOfPassinWithEmptyParameter() method called");
			eduBean.setDateOfPassing("");
			ArrayList<String> errorField = new ArrayList<>();
			AtomicInteger index = new AtomicInteger(1);
			List<String> errorList = new ArrayList<>();
			eduBeanValidator.validateDipYrOfPassing(eduBean, errorField, errorList, index,"10-11-1980", "1992", "ssc");
			log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
			assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		}
		@Test
		public void testValidateDipPassYrDtCompareWitihSscPassYr() throws Exception {
			log.info("testValidateDipPassYrDtCompareWitihSscPassYr() method called");
			eduBean.setDateOfPassing("19-10-2002");
			candidatebean.setDateOfBirth("02-05-1990");
			ArrayList<String> errorField = new ArrayList<>();
			AtomicInteger index = new AtomicInteger(1);		
			List<String> errorList = new ArrayList<>();
			eduBeanValidator.validateDipPassYrDtCompareWitihSscPassYr(errorField, errorList, index, eduBean,"02-05-1990", "2002", "ssc");
			log.info("eduBean : {} errorField : {} index : {} errorList : {}", eduBean, errorField, index, errorList);
			assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		}
//Test Cases for Roll Number of Diploma 
	@Test
	public void testValidateDipRollNoWithNullParameter() throws Exception {
		log.info("testValidateDipRollNoWithNullParameter() method called");
		eduBean.setRegistrationNo(null);
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);
		log.info("testValidateDipRollNoWithNullParameter : {} ", eduBean);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateRegistrationNo(eduBean, errorField, errorList, index);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateDipRollNoWithEmptyParameter() throws Exception {
		log.info("testValidateDipRollNoWithEmptyParameter() method called");
		eduBean.setRegistrationNo("");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);
		log.info("testValidateDipRollNoWithEmptyParameter : {} ", eduBean);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateRegistrationNo(eduBean, errorField, errorList, index);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateDipRollNoWithBlankSpaceParameter() throws Exception {
		log.info("testValidateDipRollNoWithBlankSpaceParameter() method called");
		eduBean.setRegistrationNo(" ");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);
		List<String> errorList = new ArrayList<>();
		log.info("testValidateDipRollNoWithBlankSpaceParameter : {} ", eduBean);		
		eduBeanValidator.validateRegistrationNo(eduBean, errorField, errorList, index);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateDipRollNoWithValidParameter() throws Exception {
		log.info("testValidateDipRollNoWithValidParameter() method called");
		eduBean.setRegistrationNo("Jyoti123/\\-");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);
		List<String> errorList = new ArrayList<>();
		log.info("testValidateDipRollNoWithValidParameter : {} ", eduBean);		
		eduBeanValidator.validateRegistrationNo(eduBean, errorField, errorList, index);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateDipRollNoWithInvalidParameter() throws Exception {
		log.info("testValidateDipRollNoWithInvalidParameter() method called");
		eduBean.setRegistrationNo("~!@#$%^&*(),.<>?+_");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);
		List<String> errorList = new ArrayList<>();
		log.info("testValidateDipRollNoWithInvalidParameter : {} ", eduBean);		
		eduBeanValidator.validateRegistrationNo(eduBean, errorField, errorList, index);
		assertNotEquals("error : ", errorList , new ArrayList<>());
//		("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
		
	}
	@Test
	public void testValidateDipRollNoWithMinValue() throws Exception {
		log.info("testValidateDipRollNoWithMinValue() method called");
		ArrayList<String> errorField = new ArrayList<>();
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateFieldLength("Jyoti", 1, errorList, "error", Boolean.TRUE, errorField, "registrationNo");
		log.info("testValidateDipRollNoWithMinValue {} : errorList {}  : ", eduBean,errorList);	
		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateDipRollNoWithMaxValue() throws Exception {
		log.info("testValidateDipRollNoWithMaxValue() method called");
		ArrayList<String> errorField = new ArrayList<>();
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateFieldLength("Jyoti", 25, errorList, "error", Boolean.FALSE, errorField, "registrationNo");
		log.info("testValidateDipRollNoWithMaxValue {} : errorList {}  : errorField {} :", eduBean,errorList,errorField);	
		assertNotNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	
//Test Cases For Category Cert of Diploma	
	@Test
	public void testValidateDipCertSrNoWithNullParameter() throws Exception {
		log.info("testValidateDipCertSrNoWithNullParameter() method called");
		eduBean.setCertiSerialNum(null);
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);		
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateCertSerialNo(eduBean, errorField, errorList, index);
		log.info("testValidateDipCertSrNoWithNullParameter {} : errorList {}  : errorField {} :", eduBean,errorList,errorField);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}
	
	@Test
	public void testValidateDipCertSrNoWithEmptyParameter() throws Exception {
		log.info("testValidateDipCertSrNoWithEmptyParameter() method called");
		eduBean.setCertiSerialNum("");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);		
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateCertSerialNo(eduBean, errorField, errorList, index);
		log.info("testValidateDipCertSrNoWithEmptyParameter {} : errorList {}  : errorField {} :", eduBean);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}
	
	@Test
	public void testValidateDipCertSrNoWithBlankSpaceParameter() throws Exception {
		log.info("testValidateDipCertSrNoWithBlankSpaceParameter() method called");
		eduBean.setCertiSerialNum(" ");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);
		List<String> errorList = new ArrayList<>();
		log.info("testValidateDipCertSrNoWithBlankSpaceParameter : {} ", eduBean);		
		eduBeanValidator.validateCertSerialNo(eduBean, errorField, errorList, index);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}
	
	@Test
	public void testValidateDipCertSrNoNoWithValidParameter() throws Exception {
		log.info("testValidateDipCertSrNoNoWithValidParameter() method called");
		eduBean.setCertiSerialNum("Jyoti123,/\\-");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);
		List<String> errorList = new ArrayList<>();
		log.info("testValidateDipCertSrNoNoWithValidParameter : {} ", eduBean);		
		eduBeanValidator.validateCertSerialNo(eduBean, errorField, errorList, index);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}
	
	@Test
	public void testValidateDipCertSrNoWithInvalidParameter() throws Exception {
		log.info("testValidateDipCertSrNoWithInvalidParameter() method called");
		eduBean.setCertiSerialNum("~!@#$%^&*().<>?+_");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);
		List<String> errorList = new ArrayList<>();
		log.info("testValidateDipCertSrNoWithInvalidParameter : {} ", eduBean);		
		eduBeanValidator.validateCertSerialNo(eduBean, errorField, errorList, index);
		assertNotEquals("error : ", errorList , new ArrayList<>());
//		("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);		
	}
	
//Test Cases For Trade in Diploma	
	@Test
	public void testValidateDegreeSubjectWithNullParameter() throws Exception {
		log.info("testValidateDegreeSubjectWithNullParameter() method called");
		eduBean.setDegreeSubject(null);		
		ArrayList<String> errorField = new ArrayList<>();		
		AtomicInteger index = new AtomicInteger(1);		
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateDegreeSubject(eduBean, errorField, errorList, index);
		log.info("bean : {} errorField {} :", eduBean,errorField);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateDegreeSubjectWithValidParameter() throws Exception {
		log.info("testValidateDegreeSubjectWithValidParameter() method called");
		eduBean.setDegreeSubject("1");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(1);
		log.info("testvalidateDegreeSubjectWithValidParameter : {} ", eduBean);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateDegreeSubject(eduBean, errorField, errorList, index);
		assertEquals(errorList, new ArrayList<>());;	
	}
	
//Test Cases for Doeacc Certificate	
	@Test
	public void testValidateDoeaccWithNullParameter() throws Exception {
		log.info("testValidateDoeaccWithNullParameter() method called");
		candidatebean.setDoeacc(null);		
		ArrayList<String> errorField = new ArrayList<>();		
		AtomicInteger index = new AtomicInteger(2);		
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateDoeacc(candidatebean, errorField, errorList, index);
		log.info("bean : {} errorField {} :", eduBean,errorField);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateDoeacctWithValidParameter() throws Exception {
		log.info("testValidateDoeacctWithValidParameter() method called");
		candidatebean.setDoeacc("6");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(2);
		log.info("testValidateDoeacctWithValidParameter : {} ", eduBean);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateDoeacc(candidatebean, errorField, errorList, index);
		assertEquals(errorList, new ArrayList<>());;	
	}
	@Test
	public void testValidateDoeaccWithBlankParameter() throws Exception {
		log.info("testValidateDoeaccWithBlankParameter() method called");
		candidatebean.setDoeacc("");
		ArrayList<String> errorField = new ArrayList<>();
		log.info("testValidateDoeaccWithBlankParameter : {} ", eduBean);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateIsFieldBlank(null, errorList, errorField, "error", "certB");
//		assertNotEquals("error {} :", "", new ArrayList<>());
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateTeriArmyWithNullParameter() throws Exception {
		log.info("testValidateTeriArmyWithNullParameter() method called");
		candidatebean.setTerriArmy(null);		
		ArrayList<String> errorField = new ArrayList<>();		
		AtomicInteger index = new AtomicInteger(2);		
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateTeriArmy(candidatebean, errorField, errorList, index);
		log.info("bean : {} errorField {} :", eduBean,errorField);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateTeriArmyWithValidParameter() throws Exception {
		log.info("testValidateTeriArmyWithValidParameter() method called");
		candidatebean.setTerriArmy("6");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(2);
		log.info("testValidateDoeacctWithValidParameter : {} ", eduBean);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateTeriArmy(candidatebean, errorField, errorList, index);
		assertEquals(errorList, new ArrayList<>());;	
	}
	@Test
	public void testValidateTeriArmyWithBlankParameter() throws Exception {
		log.info("testValidateTeriArmyWithBlankParameter() method called");
		candidatebean.setTerriArmy("");
		ArrayList<String> errorField = new ArrayList<>();
		log.info("testValidateTeriArmyWithBlankParameter : {} ", eduBean);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateIsFieldBlank(null, errorList, errorField, "error", "certB");
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateCertiBWithNullParameter() throws Exception {
		log.info("testValidateCertiBWithNullParameter() method called");
		candidatebean.setCertiB(null);		
		ArrayList<String> errorField = new ArrayList<>();		
		AtomicInteger index = new AtomicInteger(2);		
		List<String> errorList = new ArrayList<>();		
		eduBeanValidator.validateCertiB(candidatebean, errorField, errorList, index);
		log.info("bean : {} errorField {} :", eduBean,errorField);
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}	
	@Test
	public void testValidateCertiBWithValidParameter() throws Exception {
		log.info("testValidateCertiBWithValidParameter() method called");
		candidatebean.setCertiB("6");
		ArrayList<String> errorField = new ArrayList<>();
		AtomicInteger index = new AtomicInteger(2);
		log.info("testValidateCertiBWithValidParameter : {} ", eduBean);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateCertiB(candidatebean, errorField, errorList, index);
		assertNotEquals("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);	
	}
	@Test
	public void testValidateCertiBWithBlankParameter() throws Exception {
		log.info("testValidateCertiBWithBlankParameter() method called");
		candidatebean.setCertiB("");
		ArrayList<String> errorField = new ArrayList<>();
		log.info("testValidateCertiBWithValidParameter : {} ", eduBean);
		List<String> errorList = new ArrayList<>();
		eduBeanValidator.validateIsFieldBlank(null, errorList, errorField, "error", "certB");
		assertNull("test case field error field is null : ", CollectionUtils.isEmpty(errorList) ? errorList : null);
	}		
}
