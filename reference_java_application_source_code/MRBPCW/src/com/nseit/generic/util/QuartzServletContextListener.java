package com.nseit.generic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.nseit.syncutility.quartz.Safex_Offline_Call;
import com.nseit.syncutility.quartz.Type_O_Call_To_Atom_Bulk;

public class QuartzServletContextListener implements ServletContextListener {
	public static final String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";

	private static Logger logger = LoggerHome.getLogger(QuartzServletContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pStmt = null;
		try {
			StdSchedulerFactory.getDefaultScheduler().shutdown();

		} catch (SchedulerException ex) {
			logger.fatal(ex, ex);
		}

		catch (Exception e) {
			logger.fatal(e, e);
		} finally {
			try {
				if (pStmt != null) {
					pStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.fatal(e, e);
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		JobDetail jDetail1 = null;
		CronTrigger crTrigger1 = null;
		String cronExpression = null;

		try {
			Properties properties = new Properties();
			String filename = System.getProperty("user.dir") + File.separator + "MRB_properties" + File.separator + "job_properties.pro";
			logger.info("properties filename=" + filename);
			try {
				properties.load(new FileInputStream(filename));
			} catch (FileNotFoundException e) {
				logger.info("FileNotFoundException Error :" + e.getMessage());
			} catch (IOException e) {
				logger.info("IOException  Error :" + e.getMessage());
			}
			if (properties.getProperty("Payment_Job_timing") != null) {// properties.getProperty("Payment_Job_timing") != null
				cronExpression = properties.getProperty("Payment_Job_timing");
				// cronExpression = "*/30 * * ? * *";
				logger.info("Payment_Job_timing cronExpression :" + cronExpression);
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler scheduler = sf.getScheduler();
				scheduler.start();
				logger.info("*****:: Initializing Payment_Job For Payment Offline Call ::*******");
				jDetail1 = new JobDetail("Payment_Job", "Payment_Job", Type_O_Call_To_Atom_Bulk.class);
				crTrigger1 = new CronTrigger("cronTrigger1", "Payment_Job", cronExpression);
				scheduler.scheduleJob(jDetail1, crTrigger1);
				scheduler.start();
			} else {
				logger.info("cronExpression as 'Payment_Job_timing' Not Found in bin/MRB_properties/job_properties.pro :" + cronExpression);
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public QuartzServletContextListener() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
