/**
 * 
 */
package com.nseit.test.validation;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.StartupServlet;
import com.nseit.generic.util.validation.PhotoBeanValidator;
import java.io.File;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;



/**
 * 
 * @author ajadhav
 *
 */
@Slf4j
@Data
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestPhotoBeanValidator {
	
	@Autowired 
	private PhotoBeanValidator validator ;
//	ConfigurationConstants  configurationConstants;
	//@Autowired public CandidateBean bean;
	@Before
    public void setUp()  {
        //instantiate and populate the dependencies
		StartupServlet startupServlet=new StartupServlet();
		//ConfigurationConstants  configurationConstants= new ConfigurationConstants();
		try {
			startupServlet.init();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // etc...
		//validator.setDependencies(configurationConstants); //And other dependencies....);
    }
	@Test
	public void testvalidateAttachedPhotoForNullPointer() throws Exception {
		log.info("candidate bean data : {} ",validator.getCandidateBean());
		validator.getCandidateBean().setAttachmentPhoto(null);
		log.info("testvalidateAttachedPhoto: {} ",validator.getCandidateBean());
		List<String> errorsList= new ArrayList<>();
		validator.validateAttachedPhoto(errorsList);
		assertNull("test case field error field is not null : ",
			CollectionUtils.isEmpty(errorsList) ? errorsList : null);
	}
	@Test
	public void testvalidateFormatForPhotoForNullPointer() throws Exception {
		log.info("candidate bean data : {} ",validator.getCandidateBean());
		validator.getCandidateBean().setAttachmentPhotoFileName("abc");
		log.info("testvalidateAttachedPhoto: {} ",validator.getCandidateBean());
		List<String> errorsList= new ArrayList<>();
		String imageType="jpg";
		String ext="noext";
		validator.validateFormatForPhoto(validator.getCandidateBean(), errorsList, imageType, ext);
		assertNull("test case field error field is not null : ",
			CollectionUtils.isEmpty(errorsList) ? errorsList : null);
	}
	@Test
	public void testvalidateAttachedPhotoSize() throws Exception {
		log.info("candidate bean data : {} ",validator.getCandidateBean());
		validator.getCandidateBean().setAttachmentPhotoFileName("abc");
		log.info("testvalidateAttachedPhoto: {} ",validator.getCandidateBean());
		List<String> errorsList= new ArrayList<>();
		String fname="abc";
		String regex = "^[a-zA-Z0-9_-]*$";
		validator.validateAttachedPhotoSize(fname, errorsList, regex);
		assertNull("test case field error field is not null : ",
			CollectionUtils.isEmpty(errorsList) ? null : errorsList);
	}
	@Test
	public void testvalidateUploadImageSizeLimit() throws Exception {
		log.info("candidate bean data : {} ",validator.getCandidateBean());
		validator.getCandidateBean().setAttachmentPhotoFileName("abc");
		log.info("testvalidateUploadImageSizeLimit: {} ",validator.getCandidateBean());
		List<String> errorsList= new ArrayList<>();
		String imageType="jpg";
		String ext="noext";
		File photoPath= new File(validator.getCandidateBean().getAttachmentPhotoFileName());
		String errorMessage="error";
		validator.validateUploadImageSizeLimit(photoPath, errorMessage, errorsList, imageType, ext);
		assertNull("test case field error field is not null : ",
			CollectionUtils.isEmpty(errorsList) ? errorsList : null);
	}
	
	
}
