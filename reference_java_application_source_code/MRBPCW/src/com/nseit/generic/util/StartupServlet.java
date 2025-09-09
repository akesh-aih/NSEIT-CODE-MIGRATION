package com.nseit.generic.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.ApplicationContext;

import com.nseit.generic.models.ConfigParam;
import com.nseit.generic.models.Users;
import com.nseit.generic.aws.util.UploadPhotoSignInS3sdkV2;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.emailsms.EmailSenderRunnable;
import com.nseit.generic.util.emailsms.SMSSenderRunnable;
import com.nseit.otbs.model.TestMasterBean;

public class StartupServlet extends HttpServlet
{	
	/**
	 * Constructor of the object.
	 */
	public StartupServlet(){
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy()
	{
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String userFk = CommonUtil.getParameter(request.getParameter("userFk")).trim();
		BufferedInputStream bis = null;
		OutputStream outputStream = null;
		
		if(request.getParameter("cache")!=null && request.getParameter("cache").equals("Y")){
			//onDataBaseChange();
			PrintWriter out = response.getWriter();
			out.write("<html></br>");
			out.write("<p align='center'><strong>cache update successfully</strong></p>");
			out.write("</html>");
		}
		Users users = null;
		if (userFk.compareTo("") == 0) {
			users = (Users) request.getSession().getAttribute(GenericConstants.SESSION_USER);
		} else {
			users = new Users();
			users.setUserId(Long.parseLong(userFk));
		}
		//added to display pdf and image from local folder on click of image on preview confirm of documents[start]
		String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		String fileSuffix = request.getParameter("fileName");
		String documentFileName = request.getParameter("docName");
		if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
			UploadPhotoSignInS3sdkV2 uploadPhotoSignInS3sdkV2 = new UploadPhotoSignInS3sdkV2();
			byte[] byteArrayOfCandidateDocFromCandFolderInS3 = uploadPhotoSignInS3sdkV2.getCandidateDocFromCandFolderInS3(users,fileSuffix,documentFileName);
			if(byteArrayOfCandidateDocFromCandFolderInS3 != null) {
			if(documentFileName.endsWith(".pdf")) {
				response.setContentType("application/pdf");
			}else {
				 response.setContentType("image/jpg");
			}
				response.setHeader("Cache-Control", "no-cache");
				outputStream = response.getOutputStream();
				outputStream.write(byteArrayOfCandidateDocFromCandFolderInS3);
				outputStream.flush();	
			}
		}else {
			
		if(request.getParameter("pdf")!=null && request.getParameter("pdf").equals("pdf"))
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
          File filePath = new File(DocumentPath +File.separator+ request.getParameter("user") + File.separator+ request.getParameter("user") + "_" + request.getParameter("fileName"));
            //File filePath = new File(DocumentPath+File.separator+request.getParameter("user")+File.separator+request.getParameter("fileName")+"_"+request.getParameter("docName"));
            if(filePath!=null && !filePath.equals("") && filePath.exists() && request.getParameter("fileName").toLowerCase().endsWith("pdf"))
            {
            	FileInputStream fi = null;
                fi = new FileInputStream(filePath);
    			bis = new BufferedInputStream(fi);
    			outputStream= response.getOutputStream();
    			int abyte;
                response.setContentType("application/pdf");
                response.setHeader( "Content-disposition", "inline; filename=x.pdf");  
                response.setHeader("Cache-Control", "no-cache");  
                while ((abyte = bis.read()) != -1 ){
        				outputStream.write(abyte);
        			}
                outputStream.flush(); 
            }
            if(filePath!=null && !filePath.equals("") && filePath.exists() && request.getParameter("fileName").toLowerCase().endsWith("png"))
            {
            	FileInputStream fi = null;
                fi = new FileInputStream(filePath);
    			bis = new BufferedInputStream(fi);
    			outputStream= response.getOutputStream();
    			int abyte;
                response.setContentType("image/png");
                response.setHeader( "Content-disposition", "inline; filename=x.png");  
                response.setHeader("Cache-Control", "no-cache");  
                while ((abyte = bis.read()) != -1 ){
        				outputStream.write(abyte);
        			}
                outputStream.flush(); 
            }
            if(filePath!=null && !filePath.equals("") && filePath.exists() && request.getParameter("fileName").toLowerCase().endsWith("jpeg"))
            {
            	FileInputStream fi = null;
                fi = new FileInputStream(filePath);
    			bis = new BufferedInputStream(fi);
    			outputStream= response.getOutputStream();
    			int abyte;
                response.setContentType("image/jpeg");
                response.setHeader( "Content-disposition", "inline; filename=x.jpeg");  
                response.setHeader("Cache-Control", "no-cache");  
                while ((abyte = bis.read()) != -1 ){
        				outputStream.write(abyte);
        			}
                outputStream.flush(); 
            }
            if(filePath!=null && !filePath.equals("") && filePath.exists() && request.getParameter("fileName").toLowerCase().endsWith("jpg"))
            {
            	FileInputStream fi = null;
                fi = new FileInputStream(filePath);
    			bis = new BufferedInputStream(fi);
    			outputStream= response.getOutputStream();
    			int abyte;
                response.setContentType("image/jpg");
                response.setHeader( "Content-disposition", "inline; filename=x.jpg");  
                response.setHeader("Cache-Control", "no-cache");  
                while ((abyte = bis.read()) != -1 ){
        				outputStream.write(abyte);
        			}
                outputStream.flush(); 
            }
		}
 		if(request.getParameter("img")!=null && request.getParameter("img").equals("img"))
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
            File filePath = new File(DocumentPath+File.separator+request.getParameter("user")+File.separator+request.getParameter("fileName"));
            if(filePath!=null && !filePath.equals("") && filePath.exists())
            {
            	FileInputStream fi = null;
                fi = new FileInputStream(filePath);
    			bis = new BufferedInputStream(fi);
    			outputStream= response.getOutputStream();
    			int abyte;
                response.setContentType("image/jpg");
                //response.setHeader( "Content-disposition", "inline; filename=x.pdf");  
                response.setHeader("Cache-Control", "no-cache");  
                while ((abyte = bis.read()) != -1 ){
        				outputStream.write(abyte);
        			}
                outputStream.flush(); 
            }
           
		  }
 		}
		//added to display pdf and image from local folder on click of image on preview confirm of documents[end]
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException
	{
		CommonService commonService = null;
		
		try
		{
			ApplicationContext applicationContext = SpringUtil.getInstance().getApplicationContext();
			commonService = (CommonService) applicationContext.getBean("commonService");
			// for Covid Duty Certificate page
			ConfigurationConstants.getInstance().setSignedByDataMap(commonService.getSignedByDataMap());
			
			ConfigurationConstants.getInstance().setStaticDataMap(commonService.getStaticDataMap());
			ConfigurationConstants.getInstance().setDateWindowMapData(commonService.getDateWindowMap());
		
			ConfigurationConstants.getInstance().setEmailStatusMap(commonService.getEmailStatusMap());
			ConfigurationConstants.getInstance().setSmsStatusMap(commonService.getSmsStatusMap());
			ConfigurationConstants.getInstance().setParentMenuList(commonService.getParentMenuList());
			ConfigurationConstants.getInstance().setChildMenuList(commonService.getChildList());
			ConfigurationConstants.getInstance().setMenuMasterList(commonService.getMenuMasterList());
			ConfigurationConstants.getInstance().setMenuKeyLinkMap(commonService.getMenuKeyLinkMap());
			ConfigurationConstants.getInstance().setMenuKeyMenuLinkMap(commonService.getMenuKeyMenuLinkMap());
			ConfigurationConstants.getInstance().setMenuKeyNextStageMap(commonService.getMenuKeyNextStageMap());
			ConfigurationConstants.getInstance().setMenuKeyMandatoryMap(commonService.getMenuKeyMandatoryMap());
			ConfigurationConstants.getInstance().setMenuKeyStatusMap(commonService.getMenuKeyStatusMap());
			ConfigurationConstants.getInstance().setStageOffList(commonService.getStageOffListMap());
			ConfigurationConstants.getInstance().setStageOnList(commonService.getStageOnListMap());
			ConfigurationConstants.getInstance().setMenuDescMenuKeyMap(commonService.getMenuDescMenuKeyMap());
			ConfigurationConstants.getInstance().setMenuDescActiveStatusMap(commonService.getMenuDescActiveStatusMap());
			ConfigurationConstants.getInstance().setCategoryListMap(commonService.getCategoryListMap());
			ConfigurationConstants.getInstance().setBoardMap(commonService.getBoardMasterMap());
			
			Map<String, ConfigParam> configParamMap = commonService.getAllConfigParams();
			Map<Integer, String> stageMap = commonService.getStageMap();
			Map<Integer, String> stageStatusMap = commonService.getStageStatusMap();
			Map<Integer, String> stageActionUrlMap = commonService.getStageActionUrlMap();
			Map<Integer, String> NativityMap = commonService.getNativityMap();
			ConfigurationConstants.getInstance().setNativityMap(NativityMap);
			ConfigurationConstants.getInstance().updateConfigParams(configParamMap);
			ConfigurationConstants.getInstance().setStageMap(stageMap);
			ConfigurationConstants.getInstance().setStageStatusMap(stageStatusMap);
			ConfigurationConstants.getInstance().setStageActionUrlMap(stageActionUrlMap);
			ConfigurationConstants.getInstance().setActivityMap(commonService.getActivityMap());
			//ConfigurationConstants.getInstance().setTestCenterMasterMap(commonService.getTestCenterMaster());
			//ConfigurationConstants.getInstance().setMenuStageMap(menuStageMap);
			
			ConfigurationConstants.getInstance().setStatusMasterMap(commonService.getStatusMasterMap());
			
			ConfigurationConstants.getInstance().setPaymentMasterMap(commonService.getPaymentMasterMap());
			
			ConfigurationConstants.getInstance().setPaymentMasterReverseMap(commonService.getPaymentMasterReverseMap());
			
			ConfigurationConstants.getInstance().setStatusMasterMapForReport(commonService.getStatusMasterMapForReport());
			
			ConfigurationConstants.getInstance().setStatusDescStatusMap(commonService.getStatusDescStatusMap());
			
			/**
			 * @author Pankaj Sh
			 */
			//Map<Integer, String> countryMap = commonService.getCountryMap();
			
			Map<String, String> getLabelMap = commonService.getCandidateLabelMap();
			ConfigurationConstants.getInstance().setGetLabelMap(getLabelMap);
			
			Map<Integer, Map<Integer, String>> academicMap = commonService.getAcademicMapping();
			ConfigurationConstants.getInstance().setAcademicMap(academicMap);
			Map<Integer, Map<Integer, String>> countryStateMapping = commonService.getCountryStateMapping();
			Map<Integer, Map<Integer, String>> stateDistrictMapping = commonService.getStateDistrictMapping();
			Map<Integer,TestMasterBean> testDetailMap= commonService.getTestDetailsMap();
			
			Map<Integer, String> disciplineMap = commonService.getDisciplineMap();
			ConfigurationConstants.getInstance().setDisciplineMap(disciplineMap);
			disciplineMap = commonService.getApplyDisciplineMap();
			ConfigurationConstants.getInstance().setApplyDisciplineMap(disciplineMap);
			
			Map<Integer, List<Long>> dateWindowMap=commonService.getDateWindowData();
			ConfigurationConstants.getInstance().setDateWindowMap(dateWindowMap);
			
			ConfigurationConstants.getInstance().setCountryStateMap(countryStateMapping);
			ConfigurationConstants.getInstance().setStateDistrictMap(stateDistrictMapping);
			ConfigurationConstants.getInstance().setTestDetailsMap(testDetailMap);
			
			// To add Status Master Details 
			ConfigurationConstants.getInstance().setStatusMap(commonService.getStatusMasterMap());
			
			ConfigurationConstants.getInstance().setAcademicMasterMap(commonService.getAcademicMasterMap());
			ConfigurationConstants.getInstance().setAcademicMandatoryMap(commonService.getAcademicMandatoryMap());
			ConfigurationConstants.getInstance().setDegreeDetailsMap(commonService.getDegreeMappingDetails());
			ConfigurationConstants.getInstance().setAcademicMasterReverseMap(commonService.getAcademicMasterReverseMap());
			ConfigurationConstants.getInstance().setRefernceValueActiveMap(commonService.getReferenceValueActiveMap());
			//ConfigurationConstants.getInstance().setDegreeMap(commonService.getDegreeMap());
			ConfigurationConstants.getInstance().setUniversityMap(commonService.getUniversityMap());
			ConfigurationConstants.getInstance().setSubDegreeMap(commonService.getSubDegreeMap());
			/**
			 * [End] Pankaj Sh
			 */
			//shekharc apply page confirmation msg start
			//shekharc apply page confirmation msg end
			//commonService.updateEmailSMSMasterMap();
			setEncryptionKeyFromContext();			
		//startEmailSMSThreads();
			loadReferenceValueMaster(commonService);
			
			getServletContext().setAttribute("LOGIN_MAX_FAILURE_BEFORE_CAPTCHA", GenericConstants.LOGIN_MAX_FAILURE_BEFORE_CAPTCHA);
			
			Logger logger = createCandidateActivityAuditTrailLogger();
			ConfigurationConstants.getInstance().setCandidateActivityAuditTrailLogger(logger);
			
			Logger importLogger = createImportLogger();
			ConfigurationConstants.getInstance().setImportLogger(importLogger);
		}
		catch (Exception ex)
		{
			LoggerHome.getLogger(getClass()).fatal("--------------- Startup Servlet Initialization failed with following Exception\n", ex);
		}
		
		
	}
	
	private void setEncryptionKeyFromContext() throws NamingException {
		Context initCtx = new InitialContext();
		String encodedKey = (String) initCtx.lookup("java:comp/env/encKey");
		//log("encoded key from context : " + encodedKey);
		String decodedKey = decodeKey(encodedKey);
		ConfigurationConstants.getInstance().setENC_KEY(decodedKey);
	}

	private String decodeKey(String encodedKey) {
		byte[] decodedCipherByte = Base64.getDecoder().decode(encodedKey.getBytes(StandardCharsets.UTF_8));
		String decodedKey = new String(decodedCipherByte);
		//log("decoding string : " + decodedKey);
		return decodedKey;
	}

	private void encodeKey(String value) {
		String encodedString = Base64.getEncoder().encodeToString(value.getBytes());
		 log("encoding string : " + encodedString);
	}
	
	/**
	 * This method is call when user will direct hit from browser
	 * This method is used to prepare new cache
	 * @throws ServletException
	 */
	
	
	private void startEmailSMSThreads()
	{
		Integer currFailureCount=0;
		Integer maxFailureCountForEmail = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_MAX_FAILURE_COUNT));
		Integer maxFailureCountForSMS = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SMS_MAX_FAILURE_COUNT));
		EmailSenderRunnable emailSenderRunnable=null;
		SMSSenderRunnable smsSenderRunnable = null;
		
		ExecutorService threadExecutorService = Executors.newFixedThreadPool(maxFailureCountForEmail);		
		for(currFailureCount=0; currFailureCount<maxFailureCountForEmail; currFailureCount++)
		{
			emailSenderRunnable = new EmailSenderRunnable();
			emailSenderRunnable.setFailureCount(currFailureCount);
			
			threadExecutorService.execute(emailSenderRunnable);
		}
		threadExecutorService.shutdown();
		
		threadExecutorService = Executors.newFixedThreadPool(maxFailureCountForSMS);		
		for(currFailureCount=0; currFailureCount<maxFailureCountForSMS; currFailureCount++)
		{
			smsSenderRunnable = new SMSSenderRunnable();
			smsSenderRunnable.setFailureCount(currFailureCount);
			
			threadExecutorService.execute(smsSenderRunnable);
		}
		threadExecutorService.shutdown();
		
	}
	
	private void loadReferenceValueMaster(CommonService commonService) throws Exception
	{
		ConfigurationConstants.getInstance().setReferenceValueMaster(commonService.getReferenceDomainNameNKeyValueMap());
	}
	
	private Logger createCandidateActivityAuditTrailLogger() throws IOException
	{
		/*Logger logger = Logger.getLogger("CandidateActivityLogger");
		SimpleLayout simpleLayout = new SimpleLayout();
		FileAppender fileAppender = new FileAppende(simpleLayout, "CandidateActivityLogger.log", true);
		logger.addAppender(fileAppender);
		logger.setLevel(Level.INFO);*/
		// Durgesh Changes Start
		
		 /*   LoggerContext context= (LoggerContext) LogManager.getContext();
	        Configuration config= context.getConfiguration();

	        PatternLayout layout= PatternLayout.createLayout("%m%n", null, null, Charset.defaultCharset(),false,false,null,null);
	        Appender appender=ConsoleAppender.createAppender(layout, null, null, "CONSOLE_APPENDER", null, null);
	        appender.start();
	        AppenderRef ref= AppenderRef.createAppenderRef("CONSOLE_APPENDER",null,null);
	        AppenderRef[] refs = new AppenderRef[] {ref};
	        LoggerConfig loggerConfig= LoggerConfig.createLogger("false", Level.INFO,"CONSOLE_LOGGER","com",refs,null,null,null);
	        loggerConfig.addAppender(appender,null,null);

	        config.addAppender(appender);
	        config.addLogger("com", loggerConfig);
	        context.updateLoggers(config);

	        Logger logger=LogManager.getContext().getLogger("com");
	        logger.info("HELLO_WORLD");
		// Durgesh Changes End
*/	        
	        // New Changes 
	        
	        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	        final Configuration config = ctx.getConfiguration();
	        Layout<? extends Serializable> layout = PatternLayout.createLayout(PatternLayout.SIMPLE_CONVERSION_PATTERN, null, config, null,
	            null,true, true,null,null);

	        Appender appender = FileAppender.createAppender("CandidateActivityLogger.log", "false", "false", "File", "true",
	            "false", "false", "4000", layout, null, "false", null, config);
	        appender.start();
	        config.addAppender(appender);
	        AppenderRef ref = AppenderRef.createAppenderRef("File", null, null);
	        AppenderRef[] refs = new AppenderRef[] {ref};
	        LoggerConfig loggerConfig = LoggerConfig.createLogger("false", Level.INFO, "org.apache.logging.log4j",
	            "true", refs, null, config, null );
	        loggerConfig.addAppender(appender, null, null);
	        config.addLogger("simpleTestLogger", loggerConfig);
	        ctx.updateLoggers();


	        Logger l = ctx.getLogger("CandidateActivityLogger");
	        //l.info("message of info level shoud be output properly");
	       // l.error("error message");
		return l;
	}
	
	private Logger createImportLogger() throws IOException
	{
		/*Logger logger = Logger.getLogger("ImportLogger");
		//SimpleLayout simpleLayout = new SimpleLayout();
	   PatternLayout pl=PatternLayout.createDefaultLayout();
		
		FileAppender fileAppender = new FileAppender(pl, "ImportLogger.log", true);
		logger.addAppender(fileAppender);
		logger.setLevel(Level.INFO);*/
		
		 LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	        final Configuration config = ctx.getConfiguration();
	        Layout<? extends Serializable> layout = PatternLayout.createLayout(PatternLayout.SIMPLE_CONVERSION_PATTERN, null, config, null,
	            null,true, true,null,null);

	        Appender appender = FileAppender.createAppender("ImportLogger.log", "false", "false", "File", "true",
	            "false", "false", "4000", layout, null, "false", null, config);
	        appender.start();
	        config.addAppender(appender);
	        AppenderRef ref = AppenderRef.createAppenderRef("File", null, null);
	        AppenderRef[] refs = new AppenderRef[] {ref};
	        LoggerConfig loggerConfig = LoggerConfig.createLogger("false", Level.INFO, "org.apache.logging.log4j",
	            "true", refs, null, config, null );
	        loggerConfig.addAppender(appender, null, null);
	        config.addLogger("ImportLogger", loggerConfig);
	        ctx.updateLoggers();


	        Logger l = ctx.getLogger("ImportLogger");
	       // l.info("message of info level shoud be output properly");
	       // l.error("error message");
		
		return l;
		
		
	}
}
