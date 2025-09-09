package com.nseit.test.validation;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.util.validation.DocumentBeanValidator;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })

public class TestDocumentBeanValidator {
	@Autowired
	public DocumentBeanValidator docBeanValidator;

	@Autowired
	public CandidateBean candidateBean;

	@Test
	public void testValidateIsPathExist() throws Exception {
		log.info("candidate bean data : {} ", candidateBean.getData1());
		candidateBean.setData1("abc");
		log.info("testvalidateIsPathExistForNullPointer: {} ", candidateBean.getData1());
		List<String> errorsList = new ArrayList<>();
		File photoPath = new File(candidateBean.getData1());
		docBeanValidator.validateIsPathExist(candidateBean, errorsList, photoPath);
		assertNull("test case field error field is not null : ", CollectionUtils.isEmpty(errorsList) ? errorsList : null);
	}
	//check if all doc exists
	@Test
	public void testValidateIsAllDocExist() throws Exception {
		List<String> errorsList = new ArrayList<>();
		boolean sscDocPresent=false;
		boolean categoryPresent=false;
		boolean ewsPresent=false;
		boolean govEmpPresent=false;
		boolean domicilePresent=false;
		boolean freedomPresent=false;
		boolean ocertiPresent=false;
		boolean bcertiPresent=false;
		boolean terriPresent=false;
		boolean dipPresent=false;
		docBeanValidator.validateIsAllDocExist(errorsList, sscDocPresent, categoryPresent, ewsPresent, govEmpPresent, domicilePresent, 
				freedomPresent, ocertiPresent, bcertiPresent, terriPresent, dipPresent);
		assertNull("test case field error field is not null : ", CollectionUtils.isEmpty(errorsList) ? errorsList : null);
	}
	
	
	@Test
	public void testValidateDocFormatIsValidOrNot() throws Exception {
		log.info("candidate bean data : {} ", candidateBean.getData1());
		candidateBean.setData1("abc");
		candidateBean.setData1FileName("abcd");
		log.info("testValidateDocFormatIsValidOrNot: {} ", candidateBean.getData1());
		List<String> errorsList = new ArrayList<>();
		String documentType=".pdf";
		docBeanValidator.validateDocFormatIsValidOrNot(candidateBean, errorsList, documentType);
		assertNull("test case field error field is not null : ", CollectionUtils.isEmpty(errorsList) ? errorsList : null);
	}
	
	@Test
	public void testvalidateIsOcdFlagNull() throws Exception {
		log.info("candidate bean data : {} ", candidateBean.getData1());
		candidateBean.setData1(null);
		candidateBean.setData1FileName("abcd");
		log.info("testvalidateIsOcdFlagNull: {} ", candidateBean.getData1());
		List<String> errorsList = new ArrayList<>();
		String documentType=".pdf";
		docBeanValidator.validateIsOcdFlagNull(candidateBean, errorsList, documentType);
		assertNull("test case field error field is not null : ", CollectionUtils.isEmpty(errorsList) ? errorsList : null);
	}
	
	@Test
	public void testValidateFileNameAndSize() throws Exception {
		log.info("candidate bean data : {} ", candidateBean.getData1());
		candidateBean.setData1("abc");
		candidateBean.setData1FileName("abcd");
		log.info("testValidateFileNameAndSize: {} ", candidateBean.getData1());
		List<String> errorsList = new ArrayList<>();
		String documentType=".pdf";
		File photoPath = new File(candidateBean.getData1());
		String regex = "^[a-zA-Z0-9_-]*$";
		String fname="xyz";
		docBeanValidator.validateFileNameAndSize(candidateBean, errorsList, photoPath, regex, fname, documentType);
		assertNull("test case field error field is not null : ", CollectionUtils.isEmpty(errorsList) ? null : errorsList);
	}
	
	@Test
	public void testValidateFileMaxSize() throws Exception{
		log.info("candidate bean data : {} ", candidateBean.getData1());
		log.info("testValidateFileMaxSize: {} ", candidateBean.getData1());
		List<String> errorsList = new ArrayList<>();
		float value=88;
		int valMax = 100;
		docBeanValidator.validateFileMaxSize(errorsList, value, valMax);
		assertNull("test case field error field is not null : ", CollectionUtils.isEmpty(errorsList) ? null : errorsList);
	}
	
	@Test
	public void testValidateFileMinSize() throws Exception{
		log.info("candidate bean data : {} ", candidateBean.getData1());
		log.info("testValidateFileMaxSize: {} ", candidateBean.getData1());
		List<String> errorsList = new ArrayList<>();
		float value=88;
		int valMin = 56;
		docBeanValidator.validateFileMinSize(errorsList, value, valMin);
		assertNull("test case field error field is not null : ", CollectionUtils.isEmpty(errorsList) ? null : errorsList);
	}
	
}

