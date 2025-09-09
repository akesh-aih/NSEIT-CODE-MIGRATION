package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.DATE_FORMAT_DEFAULT;
import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.models.AcademicDetailsBean;
import com.nseit.generic.models.SettingsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.AgencyService;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.service.SettingsService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;
import com.nseit.otbs.service.SchedulingTestService;
import com.opensymphony.xwork2.ModelDriven;

public class SettingsAction extends BaseAction implements ModelDriven<SettingsBean>, UserAware
{

	private Logger logger = LoggerHome.getLogger(getClass());
	
    private static final long serialVersionUID = 1L;

    private SettingsBean settingsBean = new SettingsBean();

    private SettingsService settingsService;

    private CandidateService candidateService;

    private SchedulingTestService schedulingTestService;
    
    private AgencyService agencyService;
    
    private CommonService commonService;
    

    private Map<Integer, String> disciplineList = new LinkedHashMap<Integer, String>();

    private List<AcademicDetailsBean> getParameterListForEmailSms = new ArrayList<AcademicDetailsBean>();

    private Map<Integer,String> activityDetailsMap = new LinkedHashMap<Integer,String>();

    List<SettingsBean> appFormDateList = null;

    List<SettingsBean> approveCanDateList = null;

    List<SettingsBean> otbsDateList = null;
    
    List<SettingsBean> attemptOneDateList = null;

    List<SettingsBean> attemptTwoDateList = null;
    
    List<SettingsBean> paymentDateList = null;
    
    public SettingsAction() {
		//logger.info("SettingsAction is calling");
		displayMenus();
	}
    
    public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public void setSchedulingTestService(SchedulingTestService schedulingTestService) {
		this.schedulingTestService = schedulingTestService;
	}

	public void setSettingsService(SettingsService settingsService)
    {
        this.settingsService = settingsService;
    }

    public void setCandidateService(CandidateService candidateService)
    {
        this.candidateService = candidateService;
    }
    public void setAgencyService(AgencyService agencyService) {
		this.agencyService = agencyService;
	}

	@Override
    public SettingsBean getModel()
    {
        return settingsBean;
    }

  
    public List<String> validateInsertemailSmsSettings() {
    	List<String> errorList = new ArrayList<String>();
		try {
			
				if(ValidatorUtil.isEmpty(settingsBean.getDisciplineId())){
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.DISCIPLINE_REQD));
				}
				if(ValidatorUtil.isEmpty(settingsBean.getActivityId())){
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ACTIVITY_REQD));
				}
				if(ValidatorUtil.isEmpty(settingsBean.getEmailChkBox())){
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.EMAIL_CHK_BOX_REQD));
				}
				if(ValidatorUtil.isEmpty(settingsBean.getSmsChkBox())){
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SMS_CHK_BOX_REQD));
				}
				
				/*if(ValidatorUtil.isEmpty(settingsBean.getSmsContent())){
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SMS_CONTENT_REQD));
				}
				
				if(ValidatorUtil.isEmpty(settingsBean.getEmailContent())){
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.EMAIL_CONTENT_REQD));
				}
				if(ValidatorUtil.isEmpty(settingsBean.getEmailSmsCCAddress())){
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CC_ADDRESS_REQD));
				}*/
				if(ValidatorUtil.isEmpty(settingsBean.getEmailSmsSubject())){
					errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SUBJECT_REQD));
				}
				
				/*if (errorList.size()>0){
					addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_START));
					for(int i=0;i<errorList.size();i++){
						addActionMessage(errorList.get(i));
					}
					addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_END));
				}
				addActionError(" ");*/
				
				
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return errorList;
	}
    
    
    public String insertEmailSMSSettingDetails()
    {
        int emailSMS = 0;
        String result = "";
        List<String> errorList = null;
        
        
        	
        
        try
        {
        	//errorList = validateInsertemailSmsSettings();
        	
        	if (errorList!=null && !errorList.isEmpty()){
        		addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_START));
				for(int i=0;i<errorList.size();i++){
					addActionMessage(errorList.get(i));
				}
				addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_END));
				addActionError(" ");
				displayMenus();
				result = "insertEmailSMSSettingDetails";
				
				
        	}else{
        		emailSMS = settingsService.insertEmailSMSSettingDetails(settingsBean, loggedInUser);
                if (emailSMS > 0)
                {
                	
                	commonService.updateEmailSMSMasterMap();
                	
                    request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Email & SMS Configuration Settings Completed Successfully");
                    /**START ISSUE ID = 67595, Fixed By: Vijaya Sutar DATE = 19-Feb-2012 **/
                    /*request.setAttribute(GenericConstants.MESSAGE_TYPE,
                            GenericConstants.MESSAGE_TYPE_INFORMATION);*/
                    request.setAttribute(GenericConstants.DESTINATION_PATH,
                            "SettingsAction_getEmailSMSSettingDetails.action");
                    /**END ISSUE ID = 67595, Fixed By: Vijaya Sutar DATE = 19-Feb-2012 **/
                    result = "displayMessage";

                }
                else
                {
                    request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Settings Insertion failed");
                    request.setAttribute(GenericConstants.MESSAGE_TYPE,
                            GenericConstants.MESSAGE_TYPE_ERROR);
                    request.setAttribute(GenericConstants.DESTINATION_PATH,
                            "SettingsAction_getEmailSMSSettingDetails.action");
                    result = "displayMessage";
                }
               
        	}
            
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        
        
        return result;
    }
    
    
    public String getActivityList()
    {
    	List<String> errorList = validateActivityList();
    	
        String activityId = "";
        String activityName = "";
        String activityDesc = null;
        
        
        
        
        try
        {
        	String errors = "";

			if(errorList.size()>0){
				errors = ValidatorUtil.validationMessageFormatter(errorList);
				
			}
			if(!ValidatorUtil.isEmpty(errors)){
				
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "9," + errors );
				return "writePlainText";
				
			}else{
				 activityId = settingsBean.getActivityId();
		            if (activityId != null && !activityId.equals("")){
		                activityName = ConfigurationConstants.getInstance().getActivityNameForKey(Integer.valueOf(activityId));
		            }

		            if (activityName != null && !activityName.equals("")){
		                activityDesc = settingsService.getActivityDetails(activityName);
		            }

		            if (activityDesc != null){
		                request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, activityDesc);
		            }else
		            {
		                request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "");
		            }
			}
        	
				
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        
        
        
        return "writePlainText";
    }

    private List<String> validateActivityList() {
    	List<String> errorList = new ArrayList<String>();
    	
    	
		if(ValidatorUtil.isEmpty(settingsBean.getActivityId())){
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ACTIVITY_REQD));
		}
    	
		if(!ValidatorUtil.isNumeric(settingsBean.getActivityId())){
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ACTIVITY_NUMERIC_REQD));
		}
		
		
		return errorList;
	}

	public String getEmailSMSSettingDetails()
    {
		
		  
		  
		  
        try
        {

        	disciplineList = agencyService.getTestMasterDetails();
			
			if (disciplineList!=null){
				settingsBean.setDisciplineList(disciplineList);
			}
			
            //disciplineList = ConfigurationConstants.getInstance().getParameterMapForDomainName("Discipline");

            settingsBean.setDisciplineList(disciplineList);

            /*getParameterListForEmailSms = settingsService.getParameterListForEmailSms("Activity");
            settingsBean.setGetParameterListForEmailSms(getParameterListForEmailSms);*/

            activityDetailsMap = ConfigurationConstants.getInstance().getActivityMap();
            settingsBean.setActivityDetailsMap(activityDetailsMap);

        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        
        return "insertEmailSMSSettingDetails";
    }

    // for file upload
    public String getUploadManagement()
    {
    	
    	
        try
        {
            settingsService.getUploadManagement();
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
    	
    	
    	
        return "getUploadManagement";
    }

    public String downloadSettingDetailsForTraining()
    {
    	
    	
    	
    	
    	String result = null;
        try
        {
            String module = "TrainingUpload";

            File file = new File("");
            String path = file.getAbsolutePath();

            String downloadPath = null;

            if (module != null)
            {
          //      downloadPath = candidateService.getDownloadDetails(module);
            }

            File fileToDownload = new File(path + downloadPath);

            List<String> filname = new ArrayList<String>();

            String fileToCopy = "";

            File[] listOfFiles = fileToDownload.listFiles();

            if (listOfFiles != null)
            {
                for (int i = 0; i < listOfFiles.length; i++)
                {
                    if (listOfFiles[i].isFile())
                    {
                        fileToCopy = listOfFiles[i].toString();
                    }
                }
            }
            int idx = fileToCopy.lastIndexOf("\\");
            String actualFileName = idx >= 0 ? fileToCopy.substring(idx + 1) : fileToCopy;

            
            if (actualFileName!=null && !actualFileName.equals("")){
            	String downloadPath2 = path + downloadPath + "\\" + actualFileName;
                File fileToDownload2 = new File(downloadPath2);
                
                String extn = fileToDownload2.getName();

                int pos = extn.lastIndexOf('.');
                 String ext = extn.substring(pos+1);
                
                response.setContentType(ext);
                String disHeader = "Attachment; Filename="+actualFileName;
                response.setHeader("Content-Disposition", disHeader);
                response.setHeader("Cache-control","no-cache, no-store");
                response.setHeader("Pragma","no-cache");
                response.setHeader("Expires","-1");
                //response.setHeader( "Content-Disposition", "Attachment; Filename="+actualFileName);

                InputStream in = null;
                ServletOutputStream outs = response.getOutputStream();

                in = new BufferedInputStream(new FileInputStream(fileToDownload2));
                int ch;

                while ((ch = in.read()) != -1)
                {
                    outs.print((char) ch);
                }

                outs.flush();
                outs.close();
                in.close();
                
                result = SUCCESS;
            }else{
            	request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sorry, No Downloads Available");
                request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_ERROR);
                request.setAttribute(GenericConstants.DESTINATION_PATH,"SettingsAction_getUploadManagement.action");
                result = "displayMessage";
            }
            
            
            
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        
        return result;

    }

    public String downloadSettingDetailsForEligibility()
    {
    	
    	
    	
    	String result = null;
        try
        {
            String module = "EligibilityUpload";

            File file = new File("");
            String path = file.getAbsolutePath();

            String downloadPath = null;

            if (module != null)
            {
            	downloadPath = schedulingTestService.getDownloadDetails(module);
            }

            File fileToDownload = new File(path + downloadPath);

            List<String> filname = new ArrayList<String>();

            String fileToCopy = "";

            File[] listOfFiles = fileToDownload.listFiles();

            if (listOfFiles != null)
            {
                for (int i = 0; i < listOfFiles.length; i++)
                {
                    if (listOfFiles[i].isFile())
                    {
                        fileToCopy = listOfFiles[i].toString();
                    }
                }
            }
            int idx = fileToCopy.lastIndexOf("\\");
            String actualFileName = idx >= 0 ? fileToCopy.substring(idx + 1) : fileToCopy;
            
            
            if (actualFileName!=null && !actualFileName.equals("")){
                String downloadPath2 = path + downloadPath + "\\" + actualFileName;
                File fileToDownload2 = new File(downloadPath2);
                
                String extn = fileToDownload2.getName();

                int pos = extn.lastIndexOf('.');
                 String ext = extn.substring(pos+1);
                
                response.setContentType(ext);
                String disHeader = "Attachment; Filename="+actualFileName;
                response.setHeader("Content-Disposition", disHeader);
                response.setHeader("Cache-control","no-cache, no-store");
                response.setHeader("Pragma","no-cache");
                response.setHeader("Expires","-1");
                //response.setHeader( "Content-Disposition", "Attachment; Filename="+actualFileName);

                InputStream in = null;
                ServletOutputStream outs = response.getOutputStream();

                in = new BufferedInputStream(new FileInputStream(fileToDownload2));
                int ch;

                while ((ch = in.read()) != -1)
                {
                    outs.print((char) ch);
                }

                outs.flush();
                outs.close();
                in.close();
                result = SUCCESS; 
            }else{
            	request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sorry, No Downloads Available ");
                request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_ERROR);
                request.setAttribute(GenericConstants.DESTINATION_PATH,"SettingsAction_getUploadManagement.action");
                result = "displayMessage";
            }

    
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        return result;
    }

    public String downloadSettingDetailsForTestInstruction()
    {
    	
    	
    	String result = null;
        try
        {
            String module = "TestInstructionUpload";

            File file = new File("");
            String path = file.getAbsolutePath();

            String downloadPath = null;

            if (module != null)
            {
            	downloadPath = schedulingTestService.getDownloadDetails(module);
            }

            File fileToDownload = new File(path + downloadPath);

            List<String> filname = new ArrayList<String>();

            String fileToCopy = "";

            File[] listOfFiles = fileToDownload.listFiles();

            if (listOfFiles != null)
            {
                for (int i = 0; i < listOfFiles.length; i++)
                {
                    if (listOfFiles[i].isFile())
                    {
                        fileToCopy = listOfFiles[i].toString();
                    }
                }
            }
            int idx = fileToCopy.lastIndexOf("\\");
            String actualFileName = idx >= 0 ? fileToCopy.substring(idx + 1) : fileToCopy;
            
            
            if (actualFileName!=null && !actualFileName.equals("")){
                String downloadPath2 = path + downloadPath + "\\" + actualFileName;
                File fileToDownload2 = new File(downloadPath2);
                
                String extn = fileToDownload2.getName();

                int pos = extn.lastIndexOf('.');
                 String ext = extn.substring(pos+1);
                
                response.setContentType(ext);
                String disHeader = "Attachment; Filename="+actualFileName;
                response.setHeader("Content-Disposition", disHeader);
                response.setHeader("Cache-control","no-cache, no-store");
                response.setHeader("Pragma","no-cache");
                response.setHeader("Expires","-1");
                //response.setHeader( "Content-Disposition", "Attachment; Filename="+actualFileName);

                InputStream in = null;
                ServletOutputStream outs = response.getOutputStream();

                in = new BufferedInputStream(new FileInputStream(fileToDownload2));
                int ch;

                while ((ch = in.read()) != -1)
                {
                    outs.print((char) ch);
                }

                outs.flush();
                outs.close();
                in.close();
                
                result = SUCCESS;
            }else {
            	request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sorry, No Downloads Available ");
            	request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_ERROR);
            	request.setAttribute(GenericConstants.DESTINATION_PATH,"SettingsAction_getUploadManagement.action");
            	result = "displayMessage";
			}
            
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        return result;
    }

    public String downloadSettingDetailsForFAQ()
    {
    	
    	
    	
    	String result = null;
        try
        {
            String module = "FAQUpload";

            File file = new File("");
            String path = file.getAbsolutePath();

            String downloadPath = null;

            if (module != null)
            {
            	downloadPath = schedulingTestService.getDownloadDetails(module);
            }

            File fileToDownload = new File(path + downloadPath);

            List<String> filname = new ArrayList<String>();

            String fileToCopy = "";

            File[] listOfFiles = fileToDownload.listFiles();

            if (listOfFiles != null)
            {
                for (int i = 0; i < listOfFiles.length; i++)
                {
                    if (listOfFiles[i].isFile())
                    {
                        fileToCopy = listOfFiles[i].toString();
                    }
                }
            }
            int idx = fileToCopy.lastIndexOf("\\");
            String actualFileName = idx >= 0 ? fileToCopy.substring(idx + 1) : fileToCopy;
            
            
            if (actualFileName!=null && !actualFileName.equals("")) {
                String downloadPath2 = path + downloadPath + "\\" + actualFileName;
                File fileToDownload2 = new File(downloadPath2);
                
                String extn = fileToDownload2.getName();

                int pos = extn.lastIndexOf('.');
                 String ext = extn.substring(pos+1);
                
                response.setContentType(ext);
                String disHeader = "Attachment; Filename="+actualFileName;
                response.setHeader("Content-Disposition", disHeader);
                response.setHeader("Cache-control","no-cache, no-store");
                response.setHeader("Pragma","no-cache");
                response.setHeader("Expires","-1");
                //response.setHeader( "Content-Disposition", "Attachment; Filename="+actualFileName);

                InputStream in = null;
                ServletOutputStream outs = response.getOutputStream();

                in = new BufferedInputStream(new FileInputStream(fileToDownload2));
                int ch;

                while ((ch = in.read()) != -1)
                {
                    outs.print((char) ch);
                }

                outs.flush();
                outs.close();
                in.close();
                result = SUCCESS;
                
			}else {
				request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "Sorry, No Downloads Available ");
            	request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_ERROR);
            	request.setAttribute(GenericConstants.DESTINATION_PATH,"SettingsAction_getUploadManagement.action");
            	result = "displayMessage";
			}
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        
        
        return result;
    }

    public String insertSettingDetailsForTraining()
    {
        String result = "";
        int training = 0;
        
        
        
        
        try
        {
        	String module = "TrainingUpload";
        	if (settingsBean.getFileName()!=null && !settingsBean.getFileName().equals("")){

                String fileName2 = new File(settingsBean.getFileName()).getName();

                
                //String extn = new MimetypesFileTypeMap().getContentType(fileName2);

            	   int pos = fileName2.lastIndexOf('.');
                   String extn = fileName2.substring(pos+1);
                   
                   // for .doc-> "application/msword"
                   // for .docx ->
                   // "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                   // for .xls->"application/vnd.ms-excel"
                   // for .xlsx->
                   // "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                   // for .htm and .html -> "text/html"
                   // for .pdf -> "application/pdf"

                   if (extn != null && !extn.equals(""))
                   {
                       /*if (extn.equals("application/msword")
                               || extn.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                               || extn.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                               || extn.equals("application/vnd.ms-excel") 
                               || extn.equals("text/html")
                               || extn.equals("text/plain")
                               || extn.equals("application/pdf")
                               )*/
                       	
                       	if (extn.equals("doc")
                                   || extn.equals("docx")
                                   || extn.equals("xls")
                                   || extn.equals("xlsx") 
                                   || extn.equals("htm")
                                   || extn.equals("html")
                                   || extn.equals("pdf")
                                   || extn.equals("txt")
                                   )
                       {
                           response.setContentType(extn);


                           File file = new File("");
                           String path = file.getAbsolutePath();

                           String extPath = "";
                           extPath = "\\BARC\\UploadedFile\\";

                           path = path + extPath + module;

                           String old = "\\old";

                           File newFile = new File(path);

                           if (!newFile.exists())
                           {
                               newFile.mkdirs();
                           }
                           else
                           {
                               File oldfile = new File(path);
                               List<String> filname = new ArrayList<String>();

                               String fileToCopy = "";

                               File[] listOfFiles = oldfile.listFiles();

                               if (listOfFiles != null)
                               {
                                   for (int i = 0; i < listOfFiles.length; i++)
                                   {
                                       if (listOfFiles[i].isFile())
                                       {
                                           fileToCopy = listOfFiles[i].toString();
                                       }
                                   }
                               }
                               String backupFilePath = "";

                               int idx = fileToCopy.lastIndexOf("\\");
                               String actualFileName = idx >= 0 ? fileToCopy.substring(idx + 1)
                                       : fileToCopy;

                               String newbackupFilePath = path + old + "\\" + actualFileName;

                               File fileTransfer = new File(fileToCopy);
                               backupFilePath = path + old;

                               File backupFile = null;
                               backupFile = new File(backupFilePath);

                               if (!backupFile.exists())
                               {
                                   backupFile.mkdir();
                               }

                               File newBackupFielPath = new File(newbackupFilePath);

                               FileUtils.copyFile(fileTransfer, newBackupFielPath);

                               File[] listOfFilesExist = oldfile.listFiles();
                               if (listOfFilesExist != null)
                               {
                                   for (int i = 0; i < listOfFilesExist.length; i++)
                                   {
                                       if (listOfFilesExist[i].isFile())
                                       {
                                           listOfFilesExist[i].delete();
                                       }
                                   }
                               }
                           }

                           String newFilePath = "";
                           String s = settingsBean.getFileName();

                           String s2 = new MimetypesFileTypeMap().getContentType(s);

                           File srcFile = settingsBean.getFile1();
                           String fileName = srcFile.getName();

                           newFilePath = path + "\\" + fileName2;

                           File destFile = new File(newFilePath);

                           FileUtils.copyFile(srcFile, destFile);

                           training = settingsService.insertSettingDetails(extPath + module, module);
                           if (training > 0)
                           {
                               request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE,"File Uploaded Successfully");
                               request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_INFORMATION);
                               request.setAttribute(GenericConstants.DESTINATION_PATH,"SettingsAction_getUploadManagement.action");
                               result = "displayMessage";
                           }
                           else
                           {
                               request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE,"File Upload Failed");
                               request.setAttribute(GenericConstants.MESSAGE_TYPE,GenericConstants.MESSAGE_TYPE_ERROR);
                               request.setAttribute(GenericConstants.DESTINATION_PATH,"SettingsAction_getUploadManagement.action");
                               result = "displayMessage";
                           }
                       }
                       else
                       {
                           requestAttributes
                                   .put(GLOBAL_PLAIN_TEXT_MESSAGE,
                                           "Only .pdf, .doc, .docx, .xls, .xlsx, .htm, .html file types supported");
                           request.setAttribute(GenericConstants.MESSAGE_TYPE,
                                   GenericConstants.MESSAGE_TYPE_ERROR);
                           request.setAttribute(GenericConstants.DESTINATION_PATH,
                                   "SettingsAction_getUploadManagement.action");
                           result = "displayMessage";
                       }
                   }
        	}else{
        		addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_START));
        		addActionMessage("Please select file for upload");
				addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_END));
				addActionError(" ");
				result = "getUploadManagement";
        	}
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        return result;
    }

    public String insertSettingDetailsForEligibility()
    {
    	
    	 
    	 
    	 
        int eligibility = 0;
        String result = "";
        try
        {
			
        	if (settingsBean.getFileNameEligibility()!=null && !settingsBean.getFileNameEligibility().equals("")){
        		String fileName2 = new File(settingsBean.getFileNameEligibility()).getName();
                
                int pos = fileName2.lastIndexOf('.');
                String extn = fileName2.substring(pos+1);
                // for .doc-> "application/msword"
                // for .docx ->
                // "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                // for .xls->"application/vnd.ms-excel"
                // for .xlsx->
                // "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                // for .htm and .html -> "text/html"
                // for .pdf -> "application/pdf"
        		  if (extn != null && !extn.equals(""))
                  {
                     /* if (extn.equals("application/msword")
                              || extn
                                      .equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                              || extn
                                      .equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                              || extn.equals("application/vnd.ms-excel") || extn.equals("text/html")  || extn.equals("text/plain")
                              || extn.equals("application/pdf")
                              )
                      	*/
                      	if (extn.equals("doc")
                                  || extn.equals("docx")
                                  || extn.equals("xls")
                                  || extn.equals("xlsx") 
                                  || extn.equals("htm")
                                  || extn.equals("html")
                                  || extn.equals("pdf")
                                  || extn.equals("txt")
                                  )
                      {
                          response.setContentType(extn);

                          String module = "EligibilityUpload";
                          File file = new File("");
                          String path = file.getAbsolutePath();

                          String extPath = "";
                          extPath = "\\BARC\\UploadedFile\\";
                          path = path + extPath + module;

                          String old = "\\old";

                          File newFile = new File(path);
                          if (!newFile.exists())
                          {
                              newFile.mkdirs();
                          }
                          else
                          {
                              File oldfile = new File(path);
                              List<String> filname = new ArrayList<String>();

                              String fileToCopy = "";

                              File[] listOfFiles = oldfile.listFiles();

                              if (listOfFiles != null)
                              {
                                  for (int i = 0; i < listOfFiles.length; i++)
                                  {
                                      if (listOfFiles[i].isFile())
                                      {
                                          fileToCopy = listOfFiles[i].toString();
                                      }
                                  }
                              }
                              String backupFilePath = "";

                              int idx = fileToCopy.lastIndexOf("\\");
                              String actualFileName = idx >= 0 ? fileToCopy.substring(idx + 1)
                                      : fileToCopy;

                              String newbackupFilePath = path + old + "\\" + actualFileName;
                              File fileTransfer = new File(fileToCopy);

                              backupFilePath = path + old;

                              File backupFile = null;
                              backupFile = new File(backupFilePath);

                              if (!backupFile.exists())
                              {
                                  backupFile.mkdir();
                                  
                              }

                              File newBackupFielPath = new File(newbackupFilePath);

                              FileUtils.copyFile(fileTransfer, newBackupFielPath);

                              File[] listOfFilesExist = oldfile.listFiles();
                              if (listOfFilesExist != null)
                              {
                                  for (int i = 0; i < listOfFilesExist.length; i++)
                                  {
                                      if (listOfFilesExist[i].isFile())
                                      {
                                          listOfFilesExist[i].delete();
                                      }
                                  }
                              }
                          }

                          String newFilePath = "";
                          String s = settingsBean.getFileNameEligibility();

                          String s2 = new MimetypesFileTypeMap().getContentType(s);

                          File srcFile = settingsBean.getFile2();
                          String fileName = srcFile.getName();

                          newFilePath = path + "\\" + fileName2;

                          File destFile = new File(newFilePath);

                          FileUtils.copyFile(srcFile, destFile);

                          eligibility = settingsService.insertSettingDetails(extPath + module, module);
                          if (eligibility > 0)
                          {
                              request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE,
                                      "File Uploaded Successfully");
                              request.setAttribute(GenericConstants.MESSAGE_TYPE,
                                      GenericConstants.MESSAGE_TYPE_INFORMATION);
                              request.setAttribute(GenericConstants.DESTINATION_PATH,
                                      "SettingsAction_getUploadManagement.action");
                              result = "displayMessage";
                          }
                          else
                          {
                              request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE,
                                      "File Upload Failed");
                              request.setAttribute(GenericConstants.MESSAGE_TYPE,
                                      GenericConstants.MESSAGE_TYPE_ERROR);
                              request.setAttribute(GenericConstants.DESTINATION_PATH,
                                      "SettingsAction_getUploadManagement.action");
                              result = "displayMessage";
                          }
                      }
                      else
                      {
                          requestAttributes
                                  .put(GLOBAL_PLAIN_TEXT_MESSAGE,
                                          "Only .pdf, .doc, .docx, .xls, .xlsx, .htm, .html file types supported");
                          request.setAttribute(GenericConstants.MESSAGE_TYPE,
                                  GenericConstants.MESSAGE_TYPE_ERROR);
                          request.setAttribute(GenericConstants.DESTINATION_PATH,
                                  "SettingsAction_getUploadManagement.action");
                          result = "displayMessage";
                      }
                  }
        	}else{
        		addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_START));
        		addActionMessage("Please select file for upload");
				addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_END));
				addActionError(" ");
				result = "getUploadManagement";
        	}
            
          
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        return result;

    }


    public String insertSettingDetailsForTestInstruction()
    {
        String result = "";
        int test = 0;
        try
        {
        	 
        	
        	
        	
        	
        	if (settingsBean.getFileNameTestInstr()!=null && !settingsBean.getFileNameTestInstr().equals("")){
        		   String fileName2 = new File(settingsBean.getFileNameTestInstr()).getName();
                   
                   int pos = fileName2.lastIndexOf('.');
                   String extn = fileName2.substring(pos+1);
                   
                   // for .doc-> "application/msword"
                   // for .docx ->
                   // "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                   // for .xls->"application/vnd.ms-excel"
                   // for .xlsx->
                   // "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                   // for .htm and .html -> "text/html"
                   // for .pdf -> "application/pdf"

                   
        		 if (extn != null && !extn.equals(""))
                 {
                    /* if (extn.equals("application/msword")
                             || extn
                                     .equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                             || extn
                                     .equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                             || extn.equals("application/vnd.ms-excel") || extn.equals("text/html")  || extn.equals("text/plain")
                             || extn.equals("application/pdf")
                             )*/
                 	if (extn.equals("doc")
                             || extn.equals("docx")
                             || extn.equals("xls")
                             || extn.equals("xlsx") 
                             || extn.equals("htm")
                             || extn.equals("html")
                             || extn.equals("pdf")
                             || extn.equals("txt")
                             )
                     {
                         response.setContentType(extn);
                         String module = "TestInstructionUpload";
                         File file = new File("");
                         String path = file.getAbsolutePath();

                         String extPath = "";
                         extPath = "\\BARC\\UploadedFile\\";
                         path = path + extPath + module;

                         String old = "\\old";
                         File newFile = new File(path);
                         if (!newFile.exists())
                         {
                             newFile.mkdirs();
                         }
                         else
                         {
                             File oldfile = new File(path);
                             List<String> filname = new ArrayList<String>();

                             String fileToCopy = "";

                             File[] listOfFiles = oldfile.listFiles();

                             if (listOfFiles != null)
                             {
                                 for (int i = 0; i < listOfFiles.length; i++)
                                 {
                                     if (listOfFiles[i].isFile())
                                     {
                                         fileToCopy = listOfFiles[i].toString();
                                     }
                                 }
                             }
                             String backupFilePath = "";

                             int idx = fileToCopy.lastIndexOf("\\");
                             String actualFileName = idx >= 0 ? fileToCopy.substring(idx + 1)
                                     : fileToCopy;

                             String newbackupFilePath = path + old + "\\" + actualFileName;

                             File fileTransfer = new File(fileToCopy);
                             backupFilePath = path + old;

                             File backupFile = null;
                             backupFile = new File(backupFilePath);

                             if (!backupFile.exists())
                             {
                                 backupFile.mkdir();
                             }

                             File newBackupFielPath = new File(newbackupFilePath);

                             FileUtils.copyFile(fileTransfer, newBackupFielPath);

                             File[] listOfFilesExist = oldfile.listFiles();
                             if (listOfFilesExist != null)
                             {
                                 for (int i = 0; i < listOfFilesExist.length; i++)
                                 {
                                     if (listOfFilesExist[i].isFile())
                                     {
                                         listOfFilesExist[i].delete();
                                     }
                                 }
                             }
                         }

                         String newFilePath = "";
                         String s = settingsBean.getFileNameTestInstr();

                         String s2 = new MimetypesFileTypeMap().getContentType(s);

                         File srcFile = settingsBean.getFile3();
                         String fileName = srcFile.getName();

                         newFilePath = path + "\\" + fileName2;

                         File destFile = new File(newFilePath);

                         FileUtils.copyFile(srcFile, destFile);

                         test = settingsService.insertSettingDetails(extPath + module, module);
                  if (test > 0)
                         {
                             request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE,
                                     "File Uploaded Successfully");
                             request.setAttribute(GenericConstants.MESSAGE_TYPE,
                                     GenericConstants.MESSAGE_TYPE_INFORMATION);
                             request.setAttribute(GenericConstants.DESTINATION_PATH,
                                     "SettingsAction_getUploadManagement.action");
                             result = "displayMessage";
                         }
                         else
                         {
                             request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE,
                                     "File Upload Failed");
                             request.setAttribute(GenericConstants.MESSAGE_TYPE,
                                     GenericConstants.MESSAGE_TYPE_ERROR);
                             request.setAttribute(GenericConstants.DESTINATION_PATH,
                                     "SettingsAction_getUploadManagement.action");
                             result = "displayMessage";
                         }
                     }
                     else
                     {
                         requestAttributes
                                 .put(GLOBAL_PLAIN_TEXT_MESSAGE,
                                         "Only .pdf, .doc, .docx, .xls, .xlsx, .htm, .html file types supported");
                         request.setAttribute(GenericConstants.MESSAGE_TYPE,
                                 GenericConstants.MESSAGE_TYPE_ERROR);
                         request.setAttribute(GenericConstants.DESTINATION_PATH,
                                 "SettingsAction_getUploadManagement.action");
                         result = "displayMessage";
                     }
                 }
        	}else {
        		addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_START));
        		addActionMessage("Please select file for upload");
				addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_END));
				addActionError(" ");
				result = "getUploadManagement";
			}
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        return result;

    }

    public String insertSettingDetailsForTestFAQ()
    {
        int faq = 0;
        String result = "";
        
        
        try
        {
        	if (settingsBean.getFileNameFaq()!=null && !settingsBean.getFileNameFaq().equals("")){
        		String fileName2 = new File(settingsBean.getFileNameFaq()).getName();
                
                int pos = fileName2.lastIndexOf('.');
                String extn = fileName2.substring(pos+1);
                // for .doc-> "application/msword"
                // for .docx ->
                // "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                // for .xls->"application/vnd.ms-excel"
                // for .xlsx->
                // "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                // for .htm and .html -> "text/html"
                // for .pdf -> "application/pdf"
                
                
        		if (extn != null && !extn.equals(""))
                {
                    /*if (extn.equals("application/msword")
                            || extn
                                    .equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                            || extn
                                    .equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                            || extn.equals("application/vnd.ms-excel") || extn.equals("text/html")  || extn.equals("text/plain")
                            || extn.equals("application/pdf")
                           )*/
                	if (extn.equals("doc")
                            || extn.equals("docx")
                            || extn.equals("xls")
                            || extn.equals("xlsx") 
                            || extn.equals("htm")
                            || extn.equals("html")
                            || extn.equals("pdf")
                            || extn.equals("txt")
                            )
                    {
                        response.setContentType(extn);

                        String module = "FAQUpload";

                        File file = new File("");
                        String path = file.getAbsolutePath();

                        String extPath = "";
                        extPath = "\\BARC\\UploadedFile\\";
                        path = path + extPath + module;

                        String old = "\\old";

                        File newFile = new File(path);
                        if (!newFile.exists())
                        {
                            newFile.mkdirs();
                        }
                        else
                        {
                            File oldfile = new File(path);
                            List<String> filname = new ArrayList<String>();

                            String fileToCopy = "";

                            File[] listOfFiles = oldfile.listFiles();

                            if (listOfFiles != null)
                            {
                                for (int i = 0; i < listOfFiles.length; i++)
                                {
                                    if (listOfFiles[i].isFile())
                                    {
                                        fileToCopy = listOfFiles[i].toString();
                                    }
                                }
                            }
                            String backupFilePath = "";

                            int idx = fileToCopy.lastIndexOf("\\");
                            String actualFileName = idx >= 0 ? fileToCopy.substring(idx + 1)
                                    : fileToCopy;

                            String newbackupFilePath = path + old + "\\" + actualFileName;

                            File fileTransfer = new File(fileToCopy);
                            backupFilePath = path + old;

                            File backupFile = null;
                            backupFile = new File(backupFilePath);

                            if (!backupFile.exists())
                            {
                                backupFile.mkdir();
                            }

                            File newBackupFielPath = new File(newbackupFilePath);

                            FileUtils.copyFile(fileTransfer, newBackupFielPath);

                            File[] listOfFilesExist = oldfile.listFiles();

                            if (listOfFilesExist != null)
                            {
                                for (int i = 0; i < listOfFilesExist.length; i++)
                                {
                                    if (listOfFilesExist[i].isFile())
                                    {
                                        listOfFilesExist[i].delete();
                                    }
                                }
                            }
                        }

                        String newFilePath = "";
                        String s = settingsBean.getFileNameFaq();
                        String s2 = new MimetypesFileTypeMap().getContentType(s);

                        File srcFile = settingsBean.getFile4();
                        String fileName = srcFile.getName();

                        newFilePath = path + "\\" + fileName2;

                        File destFile = new File(newFilePath);

                        FileUtils.copyFile(srcFile, destFile);

                       faq = settingsService.insertSettingDetails(extPath + module, module);
                       if (faq > 0)
                        {
                            request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE,
                                    "File Uploaded Successfully");
                            request.setAttribute(GenericConstants.MESSAGE_TYPE,
                                    GenericConstants.MESSAGE_TYPE_INFORMATION);
                            request.setAttribute(GenericConstants.DESTINATION_PATH,
                                    "SettingsAction_getUploadManagement.action");
                            result = "displayMessage";
                        }
                       
                        else
                        {
                            request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE,
                                    "File Upload Failed");
                            request.setAttribute(GenericConstants.MESSAGE_TYPE,
                                    GenericConstants.MESSAGE_TYPE_ERROR);
                            request.setAttribute(GenericConstants.DESTINATION_PATH,
                                    "SettingsAction_getUploadManagement.action");
                            result = "displayMessage";
                        }
                    }
                    else
                    {
                        requestAttributes
                                .put(GLOBAL_PLAIN_TEXT_MESSAGE,
                                        "Only .pdf, .doc, .docx, .xls, .xlsx, .htm, .html file types supported");
                        request.setAttribute(GenericConstants.MESSAGE_TYPE,
                                GenericConstants.MESSAGE_TYPE_ERROR);
                        request.setAttribute(GenericConstants.DESTINATION_PATH,
                                "SettingsAction_getUploadManagement.action");
                        result = "displayMessage";
                    }
                }
        	}else{
        		addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_START));
        		addActionMessage("Please select file for upload");
				addActionMessage(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_END));
				addActionError(" ");
				result = "getUploadManagement";
        	}
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        
        
        return result;
    }

    /**
     * server validation for updateDateRangeDetails
     * 
     * @author Pankaj
     * @return List
     */
    public List<String> validateupdateDateDetails(String stage, String disciplineId,
            String startDateTS, String endDateTS)
    {
        List<String> errorList = new ArrayList<String>();

        if (ValidatorUtil.isEmpty(stage)) {
        	errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SETTING_STAGE_REQUIRED));
        }

      //  if (ValidatorUtil.isEmpty(disciplineId)) errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_DISCIPLINE));

        if (ValidatorUtil.isEmpty(endDateTS)){
        	errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SETTING_END_DT_REQUIRED));
        }

        if (ValidatorUtil.isEmpty(startDateTS)){
        	errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SETTING_START_DT_REQUIRED));
        }

      //  if (!ValidatorUtil.isNumeric(disciplineId)) errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_DISCIPLINE_NUMERIC));

        if (!ValidatorUtil.isValidDate(startDateTS)){
        	errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.TSTMGMT_VALID_DATE));
        }

        if (!ValidatorUtil.isValidDate(startDateTS)){
        	errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.TSTMGMT_VALID_DATE));
        }

        return errorList;
    }

    public String updateDateRangeDetails()
    {
    	
    	
        try
        {
            String startDate = "";
            String endDate = "";
            int datePK = 0;

            Timestamp startDateTS = null;
            Timestamp endDateTS = null;

            String desc = settingsBean.getDesc();

            if (desc != null && !desc.equals("") && desc.equals("ApplicationForm"))
            {
            	datePK = 1;
            	startDate = settingsBean.getRegStartDate();
                endDate = settingsBean.getRegEndDate();

                startDateTS = CommonUtil.convertToTimestamp(startDate, DATE_FORMAT_DEFAULT);
                endDateTS = CommonUtil.convertToTimestamp(endDate, DATE_FORMAT_DEFAULT);
            }

            if (desc != null && !desc.equals("") && desc.equals("Candidate"))
            {
            	datePK = 2;
            	startDate = "";
                endDate = "";

                startDate = settingsBean.getRegStartDate();
                endDate = settingsBean.getRegEndDate();

                startDateTS = CommonUtil.convertToTimestamp(startDate, DATE_FORMAT_DEFAULT);
                endDateTS = CommonUtil.convertToTimestamp(endDate, DATE_FORMAT_DEFAULT);
            }

            if (desc != null && !desc.equals("") && desc.equals("OTBS"))
            {
            	datePK = 3;	
                startDate = "";
                endDate = "";

                startDate = settingsBean.getRegStartDate();
                endDate = settingsBean.getRegEndDate();

                startDateTS = CommonUtil.convertToTimestamp(startDate, DATE_FORMAT_DEFAULT);
                endDateTS = CommonUtil.convertToTimestamp(endDate, DATE_FORMAT_DEFAULT);

            }

            if (desc != null && !desc.equals("") && desc.equals("AttemptOne"))
            {
            	datePK = 4;
                startDate = settingsBean.getRegStartDate();
                endDate = settingsBean.getRegEndDate();

                startDateTS = CommonUtil.convertToTimestamp(startDate, DATE_FORMAT_DEFAULT);
                endDateTS = CommonUtil.convertToTimestamp(endDate, DATE_FORMAT_DEFAULT);

            }
            
            if (desc != null && !desc.equals("") && desc.equals("AttemptTwo"))
            {
            	datePK = 5;
            	startDate = settingsBean.getRegStartDate();
                endDate = settingsBean.getRegEndDate();

                startDateTS = CommonUtil.convertToTimestamp(startDate, DATE_FORMAT_DEFAULT);
                endDateTS = CommonUtil.convertToTimestamp(endDate, DATE_FORMAT_DEFAULT);

            }
            if (desc != null && !desc.equals("") && desc.equals("Payment"))
            {
            	datePK = 6;
            	startDate = settingsBean.getRegStartDate();
                endDate = settingsBean.getRegEndDate();

                startDateTS = CommonUtil.convertToTimestamp(startDate, DATE_FORMAT_DEFAULT);
                endDateTS = CommonUtil.convertToTimestamp(endDate, DATE_FORMAT_DEFAULT);

            }

            /*List<String> errorList = validateupdateDateDetails(stage, disciplineId, startDate,endDate);
            if (errorList.size() > 0){
                String errors = null;
                if(errorList.size()>0){
    				errors = ValidatorUtil.validationMessageFormatter(errorList);
    			}
                request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, "9," + errors);
                return "writePlainText";
            }*/
            //else{
                int updatedVal = settingsService.updateDateRangeDetails(startDateTS, endDateTS, loggedInUser,datePK);
            //}
                
            Map<Integer, List<Long>> dateWindowMap=commonService.getDateWindowData();
			ConfigurationConstants.getInstance().setDateWindowMap(dateWindowMap);
        }
        catch (Exception e) {
        	logger.fatal(e, e);
        }
        
        return "displayDateDefinitionDetails";

    }

    public String getDateRangeDetails()
    {
    	
    	
        Map<Long, List<SettingsBean>> dateMap = null;

        SettingsBean appFormDate = null;
        SettingsBean approveCanDate = null;
        SettingsBean otbsDate = null;
        SettingsBean attemptOneDate = null;
        SettingsBean attemptTwoDate = null;
        SettingsBean paymentDate = null;
        try
        {
            dateMap = settingsService.getDateRangeDetails();

            if (dateMap != null)
            {
	            appFormDateList = new ArrayList<SettingsBean>();
                approveCanDateList = new ArrayList<SettingsBean>();
                otbsDateList = new ArrayList<SettingsBean>();
                attemptOneDateList = new ArrayList<SettingsBean>();
                attemptTwoDateList = new ArrayList<SettingsBean>();
                paymentDateList = new ArrayList<SettingsBean>();
                for (Map.Entry<Long, List<SettingsBean>> entry : dateMap.entrySet())
                {
                    if (entry != null)
                    {

                        if (entry.getKey() != null && entry.getKey().equals(new Long(1)))
                        {
                            List<SettingsBean> list = null;
                            list = entry.getValue();

                            for (SettingsBean settingsBean2 : list)
                            {
                                if (settingsBean2 != null)
                                {
                                	appFormDateList.add(settingsBean2);
                                }
                            }
                            
                            if (appFormDateList != null && !appFormDateList.isEmpty())
                            {
                            	appFormDate = appFormDateList.get(0);
                            }
                            if (appFormDate != null)
                            {
                                settingsBean.setAppFormStartDt(CommonUtil.formatTimeStamp(
                                		appFormDate.getStartDate(), DATE_FORMAT_DEFAULT));
                                settingsBean.setAppFormEndDt(CommonUtil.formatTimeStamp(
                                		appFormDate.getEndDate(), DATE_FORMAT_DEFAULT));
                            }
                           
                        }

                        if (entry.getKey() != null && entry.getKey().equals(new Long(2)))
                        {
                            List<SettingsBean> list = null;
                            list = entry.getValue();

                            for (SettingsBean settingsBean3 : list)
                            {
                                if (settingsBean3 != null)
                                {
                                	approveCanDateList.add(settingsBean3);
                                }
                            }
                            
                            if (approveCanDateList != null && !approveCanDateList.isEmpty())
                            {
                            	approveCanDate = approveCanDateList.get(0);
                            }
                            if (approveCanDate != null)
                            {
                                settingsBean.setAprvCandStartDt(CommonUtil.formatTimeStamp(
                                		approveCanDate.getStartDate(), DATE_FORMAT_DEFAULT));
                                settingsBean.setAprvCandEndDt(CommonUtil.formatTimeStamp(
                                		approveCanDate.getEndDate(), DATE_FORMAT_DEFAULT));
                            }
                        }

                        if (entry.getKey() != null && entry.getKey().equals(new Long(3)))
                        {
                            List<SettingsBean> list = null;
                            list = entry.getValue();

                            for (SettingsBean settingsBean4 : list)
                            {
                                if (settingsBean4 != null)
                                {
                                	otbsDateList.add(settingsBean4);
                                }
                            }
                            
                            if (otbsDateList != null && !otbsDateList.isEmpty())
                            {
                            	otbsDate = otbsDateList.get(0);
                            }
                            if (otbsDate != null)
                            {
                                settingsBean.setOtbsEndDt(CommonUtil.formatTimeStamp(
                                		otbsDate.getEndDate(), DATE_FORMAT_DEFAULT));
                                settingsBean.setOtbsStartDt(CommonUtil.formatTimeStamp(
                                		otbsDate.getStartDate(), DATE_FORMAT_DEFAULT));
                            }
                            
                        }

                        if (entry.getKey() != null && entry.getKey().equals(new Long(4)))
                        {
                            List<SettingsBean> list = null;
                            list = entry.getValue();

                            for (SettingsBean settingsBean5 : list)
                            {
                                if (settingsBean5 != null)
                                {
                                	attemptOneDateList.add(settingsBean5);
                                }
                            }
                            
                            if (attemptOneDateList != null && !attemptOneDateList.isEmpty())
                            {
                            	attemptOneDate = attemptOneDateList.get(0);
                            }

                            if (attemptOneDate != null)
                            {
                                settingsBean.setAttempt1StartDt(CommonUtil.formatTimeStamp(
                                		attemptOneDate.getStartDate(), DATE_FORMAT_DEFAULT));
                                settingsBean.setAttempt1EndDt(CommonUtil.formatTimeStamp(
                                		attemptOneDate.getEndDate(), DATE_FORMAT_DEFAULT));
                            }
                        }
                        
                        
                        if (entry.getKey() != null && entry.getKey().equals(new Long(5)))
                        {
                            List<SettingsBean> list = null;
                            list = entry.getValue();

                            for (SettingsBean settingsBean6 : list)
                            {
                                if (settingsBean6 != null)
                                {
                                	attemptTwoDateList.add(settingsBean6);
                                }
                            }
                            
                            if (attemptTwoDateList != null && !attemptTwoDateList.isEmpty())
                            {
                            	attemptTwoDate = attemptTwoDateList.get(0);
                            }
                            if (attemptTwoDate != null)
                            {
                                settingsBean.setAttempt2StartDt(CommonUtil.formatTimeStamp(
                                		attemptTwoDate.getStartDate(), DATE_FORMAT_DEFAULT));
                                settingsBean.setAttempt2EndDt(CommonUtil.formatTimeStamp(
                                		attemptTwoDate.getEndDate(), DATE_FORMAT_DEFAULT));
                            }
                        }
                        if (entry.getKey() != null && entry.getKey().equals(new Long(6)))
                        {
                            List<SettingsBean> list = null;
                            list = entry.getValue();

                            for (SettingsBean settingsBean7 : list)
                            {
                                if (settingsBean7 != null)
                                {
                                	paymentDateList.add(settingsBean7);
                                }
                            }
                            
                            if (paymentDateList != null && !paymentDateList.isEmpty())
                            {
                            	paymentDate = paymentDateList.get(0);
                            }
                            if (paymentDate != null)
                            {
                                settingsBean.setPaymentStartDate(CommonUtil.formatTimeStamp(paymentDate.getStartDate(), DATE_FORMAT_DEFAULT));
                                settingsBean.setPaymentEndDate(CommonUtil.formatTimeStamp(paymentDate.getEndDate(), DATE_FORMAT_DEFAULT));
                            }
                        }
                    }// end of null cond for entry
                }// end of for each loop of map
            }// end of null cond of map
        }
        catch (Exception e)
        {
        	logger.fatal(e, e);
        }
        
        return "displayDateDefinitionDetails";
    }
    
    public String getEmailSMSDetailsForDiscipline()
    {
    	
    	 
    	 
        String discliplineID = "";
        SettingsBean newSettingsBean = null;
        String finalData = "";
        String errors = "";
        try{
            newSettingsBean = settingsService.getEmailSMSDetailsForDiscipline(settingsBean);

            if (newSettingsBean != null){	
            	if (newSettingsBean.getEmailChkBox()!=null && !newSettingsBean.getEmailChkBox().equals("") && newSettingsBean.getEmailChkBox().equals("Y")){
            		settingsBean.setMailChkBoxFlag("true");
            	}
            	if (newSettingsBean.getSmsChkBox()!=null && !newSettingsBean.getSmsChkBox().equals("") && newSettingsBean.getSmsChkBox().equals("Y")){
            		settingsBean.setSmsChkBoxFlag("true");
            	}
            	
            	if (newSettingsBean.getEmailChkBox()!=null && !newSettingsBean.getEmailChkBox().equals("") && newSettingsBean.getEmailChkBox().equals("N")){
            		settingsBean.setMailChkBoxFlag("false");
            	}
            	if (newSettingsBean.getSmsChkBox()!=null && !newSettingsBean.getSmsChkBox().equals("") && newSettingsBean.getSmsChkBox().equals("N")){
            		settingsBean.setSmsChkBoxFlag("false");
            	}
            	
                finalData = finalData + newSettingsBean.getEmailChkBox() + "||"
                        + newSettingsBean.getEmailSmsSubject() + "||"
                        + newSettingsBean.getEmailSmsCCAddress() + "||"
                        + newSettingsBean.getEmailContent() + "||"
                        + newSettingsBean.getSmsChkBox() + "||" + newSettingsBean.getSmsContent();
            }
            request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, finalData);
        }
        catch (Exception e){
        	logger.fatal(e, e);
        }
        

        return "writePlainText";
    }
    
    public String sendToNextActiveAction(){
		//logger.info("getReport");
		String menuKey = null;
		String result = null;
		try {
		if(request.getAttribute("parentMenuKey")!=null){
			menuKey = request.getAttribute("parentMenuKey").toString();
		}
		String actionUrl = redirectToNextActivePage(menuKey,loggedInUser);
		if(actionUrl==null){
			request.setAttribute(DESTINATION_URL,actionUrl);
			result = "childNotFoundForMenu";
		}else{
			request.setAttribute(DESTINATION_URL,actionUrl);
			result = REDIRECT_ACTION_URL;
		}
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		return result;
	}
    
    public String getDateDefinitionDetails(){
        try{
        	String otbsStatus =  ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.OTBS);
        	String attemp1 =  ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.ATTEMPT_1);
        	String attemp2 =  ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.ATTEMPT_2);
        	String paymentStatus =  ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT);
        	String candidateApproveRejectStatus =  ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.CANDIDATE_APPROVE_REJECT);
        	settingsBean.setOtbsStatus(otbsStatus);
        	settingsBean.setAttemp1(attemp1);
        	settingsBean.setAttemp2(attemp2);
        	settingsBean.setPaymentStatus(paymentStatus);
        	settingsBean.setCandidateApproveRejectStatus(candidateApproveRejectStatus);
            getDateRangeDetails();

        }catch (Exception e){
        	logger.fatal(e, e);
        }
        return "getDateDefinitionDetails";
    }
    
   
		

    @Override
    public void resetModel()
    {
        settingsBean = new SettingsBean();
    }

    @Override
    public void setLoggedInUser(Users users)
    {
        loggedInUser = users;
    }

	
	@Override
	public void withSession(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
		
	}

	@Override
	public void withParameters(HttpParameters httpParameters) {
		this.httpParameters = httpParameters;
		
	}

	@Override
	public void withServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	@Override
	public void withServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}

}
