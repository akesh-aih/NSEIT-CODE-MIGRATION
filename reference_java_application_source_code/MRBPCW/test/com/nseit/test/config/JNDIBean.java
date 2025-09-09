/**
 * 
 */
package com.nseit.test.config;

/**
 * @author ajays
 *
 */
import javax.naming.NamingException;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
//import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
//import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
// @NoArgsConstructor
public class JNDIBean {
	public JNDIBean(String driver, String url, String username, String password) {
		try {
			log.info("JNDIBean constructor value  : driver  {} : url {} : username {} : password {} ", driver, url, username, password);
			DriverAdapterCPDS cpds = new DriverAdapterCPDS();
			cpds.setDriver(driver);
			cpds.setUrl(url);
			cpds.setUser(username);
			cpds.setPassword(password);

			log.info(" connection pool data : {} ", cpds);
			SharedPoolDataSource dataSource = new SharedPoolDataSource();
			dataSource.setConnectionPoolDataSource(cpds);
			dataSource.setMaxTotal(10);
			dataSource.setDefaultAutoCommit(Boolean.FALSE);
			// dataSource.setDefaultMaxWait(java.time.Duration.ofMillis(1000));

			log.info(" datasource object : {} ", dataSource);
			SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
			builder.bind("java:comp/env/jdbc/GenericDataSource", dataSource);
			builder.bind("java:comp/env/jdbc/WriteDataSource", dataSource);
			builder.activate();
			log.info("jndi datasource object : {} ", builder);
			log.info("jndibean method end : : ");
		} catch (NamingException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
}
