/**
 * 
 */
package com.nseit.test.validation;

import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.nseit.generic.util.validation.SignatureBeanValidator;

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
public class TestSignBeanValidator {
	@Autowired 
	private SignatureBeanValidator validator ;
	@Test
	public void testvalidateAttachedSignForNullPointer() throws Exception {
		log.info("candidate bean data : {} ",validator.getCandidateBean());
		validator.getCandidateBean().setAttachmentPhoto(null);
		log.info("testvalidateAttachedSignForNullPointer: {} ",validator.getCandidateBean());
		List<String> errorsList= new ArrayList<>();
		validator.validateAttachmentSign(errorsList);
		assertNull("test case field error field is not null : ",
			CollectionUtils.isEmpty(errorsList) ? errorsList : null);
	}
	
	@Test
	public void testvalidateFormatForSignForNullPointer() throws Exception {
		log.info("candidate bean data : {} ",validator.getCandidateBean());
		validator.getCandidateBean().setAttachmentSignatureFileName("abc");
		log.info("testvalidateFormatForSignForNullPointer: {} ",validator.getCandidateBean());
		List<String> errorsList= new ArrayList<>();
		String signatureType="jpg";
		String ext="noext";
		validator.validateFormatForSign(errorsList, signatureType, ext);
		assertNull("test case field error field is not null : ",
			CollectionUtils.isEmpty(errorsList) ? errorsList : null);
	}
	
	@Test
	public void testvalidateAttachedSignSize() throws Exception {
		log.info("candidate bean data : {} ",validator.getCandidateBean());
		validator.getCandidateBean().setAttachmentSignatureFileName("abc");
		log.info("testvalidateAttachedSignSize: {} ",validator.getCandidateBean());
		List<String> errorsList= new ArrayList<>();
		String fname="abc";
		String regex = "^[a-zA-Z0-9_-]*$";
		validator.validateAttachedSignSize(fname, errorsList, regex);
		assertNull("test case field error field is not null : ",
			CollectionUtils.isEmpty(errorsList) ? null : errorsList);
	}
	
	@Test
	public void testvalidateUploadSignSizeLimit() throws Exception {
		log.info("candidate bean data : {} ",validator.getCandidateBean());
		validator.getCandidateBean().setAttachmentSignatureFileName("abc");
		log.info("testvalidateUploadSignSizeLimit: {} ",validator.getCandidateBean());
		List<String> errorsList= new ArrayList<>();
		String imageType="jpg";
		String ext="noext";
		File photoPath= new File(validator.getCandidateBean().getAttachmentSignatureFileName());
		String errorMessage="error";
		validator.validateUploadSignSizeLimit(photoPath, errorMessage, errorsList, imageType, ext);
		assertNull("test case field error field is not null : ",
			CollectionUtils.isEmpty(errorsList) ? errorsList : null);
	}
}
