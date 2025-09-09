
package com.nseit.test.dao;

import static org.junit.Assert.assertNotNull;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nseit.generic.dao.LoginDao;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.StartupServlet;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;

/**
 * @author ajays
 *
 */
//@ExtendWith()
//@ExtendWith(org.junit.jupiter.api.extension.Extension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
//@TestExecutionListeners(value = TransactionalTestExecutionListener.class)
//@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
//@TransactionConfiguration(transactionManager = "genericTransactionManager", defaultRollback = true)
@Accessors
@Slf4j
@Data
public class TestLoginDao {
	
	
	
//	@SuppressWarnings("deprecation")
//	@Rule
//	public ExpectedException exceptionRule = ExpectedException.none();
	
//	@Autowired
//	private StartupServlet startupServlet;
	
	@Before
	public void init() throws ServletException {
		StartupServlet startupServlet = new StartupServlet();
		startupServlet.init();
//		try {
//			
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//			log.debug("startup servlet throws an error : {} ");
//		}
	}


	@Autowired
	private LoginDao loginDao;
	
	@Test
//	@Transactional
//	@Rollback(value = true)
	public void testGetUserForUsername() throws Exception {
		log.info("testGetUserForUsername method called : {} ");
//		exceptionRule.expect(NullPointerException.class);
//	    exceptionRule.expectMessage("got null pointer");
		Users userForUsername = loginDao.getUserForUsername("REFH0000004");
		
		assertNotNull("User with REFH0000004 should be present", userForUsername);
//		Assert.assertEquals("", NullPointerException.class, () -> {loginDao.getUserForUsername("test")});
		

	}

}
