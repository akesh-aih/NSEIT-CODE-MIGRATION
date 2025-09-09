/**
 * 
 */
package com.nseit.test.util;

import org.junit.Assert;
//import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nseit.generic.util.EmailValidator;
import com.nseit.generic.util.ValidatorUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ajays
 *
 */
@Slf4j
@Data
@RunWith(JUnit4.class)
//@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestValidatorUtil {
	
	@Test
	public void testValidatorPatternWithSpecialCharater() throws Exception {
		log.info("Special character included : {} ","-/\\& ,.");
		Boolean validatePattern = ValidatorUtil.validatePattern("AbC123 -.,//\\&", "1", "100");
		log.info("validatePattern : {} ",validatePattern);
		Assert.assertTrue(validatePattern);
	}
	
	@Test
	public void testValidatorWithEmailPattern() throws Exception {
		log.info("Email pattern include  : {} ","alphanumeric+specialCharacter.@alphabet.alphabet");
		Boolean condition = EmailValidator.validate("test@gmail.com");
		log.info("condition : {} ",condition);
		Assert.assertTrue("failed condition should be true", !condition);
	}
}
