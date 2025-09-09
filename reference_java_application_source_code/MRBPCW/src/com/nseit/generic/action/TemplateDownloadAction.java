package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDetailsBean;
import com.nseit.generic.models.CandidateDocBean;
import com.nseit.generic.models.TemplateDownloadBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.TemplateDownloadService;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.ExcelCreator;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.opensymphony.xwork2.ModelDriven;

public class TemplateDownloadAction extends BaseAction implements ModelDriven<TemplateDownloadBean>, UserAware{

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerHome.getLogger(getClass());
	private TemplateDownloadBean templateDownloadBean = new TemplateDownloadBean();
	private TemplateDownloadService templateDownloadService;
	
	public TemplateDownloadAction() {
		//logger.info("TemplateDownloadAction is calling");
		displayMenus();
	}
	
	@Override
	public void resetModel() {
		templateDownloadBean = new TemplateDownloadBean();
	}

	@Override
	public TemplateDownloadBean getModel() {
		return templateDownloadBean;
	}

	@Override
	public void setLoggedInUser(Users users) {
		 loggedInUser = users;
		
	}

	public void setTemplateDownloadService(TemplateDownloadService templateDownloadService) {
		this.templateDownloadService = templateDownloadService;
	}

	
	public String goToTemplateDownload() {
		//logger.info("goToTemplateDownload");
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
	
	public String showTemplateDownloadPage() throws Exception{
		String result = null;
		try {
			result = "showTemplateDownloadPage";
			
			templateDownloadBean.setDdChallanTemplateStatus(ConfigurationConstants.getInstance().getTemplateStatusForDesc(GenericConstants.TEMPLATE_DD_CHALLAN));
			
			templateDownloadBean.setTemplateUserCredentialsStatus(ConfigurationConstants.getInstance().getTemplateStatusForDesc(GenericConstants.TEMPLATE_USER_CREDENTIALS));
			
			templateDownloadBean.setTemplateUploadStatus(ConfigurationConstants.getInstance().getTemplateStatusForDesc(GenericConstants.TEMPLATE_UPLOAD_MARKS));
			
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		return result;
	}
	
	public void downloadTemplate() throws Exception{
		String result = null;
		try {
			result = "successCsv";
			//result = "showTemplateDownloadPage";
			/*logger.info("type "+templateDownloadBean.getTemplateDownloadType());
			logger.info("type "+request.getParameter("templateDownloadType").toString());*/
			TemplateDownloadBean tmpDwnldBean =null;
			if (request.getParameter("templateDownloadType")!=null && !request.getParameter("templateDownloadType").equals("")){
				/*tmpDwnldBean = templateDownloadService.getTemplate(request.getParameter("templateDownloadType"));
				
				if (tmpDwnldBean!=null){
					logger.info(tmpDwnldBean);
					templateDownloadBean.setDocumentInputStream(new ByteArrayInputStream(tmpDwnldBean.getTemplateDocumentByte()));
					templateDownloadBean.setTemplateFileName(request.getParameter("templateDownloadType"));
				}*/
				if(request.getParameter("templateDownloadType").equals("TEMPLATE_CHALLAN_RECONCILIATION"))
				{
					ServletContext context = ServletActionContext.getServletContext();
		        	String fullPath = context.getRealPath("/uploads/");
					PrintWriter out = response.getWriter();
					response.setHeader("Content-Disposition", "attachment; filename=ChallanReconcillationTemplate.csv");
					response.setHeader("Cache-control","no-cache, no-store");
					response.setHeader("Pragma","no-cache");
					response.setHeader("Expires","-1");
					FileInputStream fileInputStream = new FileInputStream(fullPath
							+ "/ChallanReconcillationTemplate.csv");
			 
					int i;
					while ((i = fileInputStream.read()) != -1) {
						out.write(i);
					}
					fileInputStream.close();
					out.flush();
					out.close();
				}
				else
				{
					ServletContext context = ServletActionContext.getServletContext();
		        	String fullPath = context.getRealPath("/uploads/");
					PrintWriter out = response.getWriter();
					response.setHeader("Content-Disposition", "attachment; filename=ePostReconcillationTemplate.csv");
					response.setHeader("Cache-control","no-cache, no-store");
					response.setHeader("Pragma","no-cache");
					response.setHeader("Expires","-1");
					FileInputStream fileInputStream = new FileInputStream(fullPath
							+ "/ePostReconcillationTemplate.csv");
			 
					int i;
					while ((i = fileInputStream.read()) != -1) {
						out.write(i);
					}
					fileInputStream.close();
					out.flush();
					out.close();
				}
			}
					
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		//return result;
	}
	
	public String showCandidateDataExportPage() throws Exception{
		String result = null;
		try {
			result = "candidateExportData";
			templateDownloadBean.setSuccessMessageFlag("false");
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		return result;
	}

	
	
	public void createZipFile() throws Exception{
		String result = null;
		List<CandidateDetailsBean> candidateDetailsList = null;
		try {
			 File f = new File("");
			 String path = f.getAbsolutePath();
			 
			 File deleteZipFile = new File(path+File.separator+"CandidateDetails.zip");
			 if (deleteZipFile.exists()){
				boolean deleteZipFileResult = deleteZipFile.delete();
				//logger.info("deleteZipFileResult    "+deleteZipFileResult);
			 }
			 
			 deleteDir(new File(path+File.separator+"Photo"));
			 deleteDir(new File(path+File.separator+"Sign"));
			 deleteFile(new File(path+File.separator+"candidateDetails.xls"));
			 
			 result = "candidateExportData";
			// String scheduling = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SCHEDULING_ON);
			 
			// if (scheduling!=null && !scheduling.equals("") && scheduling.equals("0")){
				 candidateDetailsList = templateDownloadService.getCandidateDetailsList();
			// }
			 //if (scheduling!=null && !scheduling.equals("") && scheduling.equals("1")){
				// candidateDetailsList = templateDownloadService.getCandidateDetailsListWithScheduling();
			// }

			 if (candidateDetailsList!=null){
				 for (CandidateDetailsBean candidateDetailsBean : candidateDetailsList) {
					if (candidateDetailsBean!=null){
						String address = "";
						String address2 = "";
						String address3 = "";
						String address4 = "";
						String commAddress = "";
						String[] temp = null;
						
						
						String paddress = "";
						String paddress2 = "";
						String paddress3 = "";
						String paddress4 = "";
						String permAddress = "";
						String[] permTemp = null;
						
		                String delimiter = "\\$\\$";
		                temp = candidateDetailsBean.getOcd_comm_address().split(delimiter);

		                    if (temp != null) {
		                        for (int i = 0; i < temp.length; i++) {
		                            if (i == 0) {
		                            	address = temp[i];
		                            }
		                            if (i == 1) {
		                            	address2 = temp[i];
		                            }
		                            if (i == 2) {
		                            	address3 = temp[i];
		                            }
		                            if (i == 3) {
		                            	address4 = temp[i];
		                            }
		                        }

		                    }
		                    commAddress = address;
		                    
		                    if (address2!=null && !address2.equals("")){
		                    	commAddress = commAddress+" "+address2;
		                    }
		                    
		                    if (address3!=null && !address3.equals("")){
		                    	commAddress = commAddress+" "+address3;
		                    }
		                    
		                    if (address4!=null && !address4.equals("")){
		                    	commAddress = commAddress+" "+address4;
		                    }
		                    
		                    candidateDetailsBean.setOcd_comm_address(commAddress);
		                    
		                    
		                    //permTemp = candidateDetailsBean.getOCD_PERM_ADDRESS().split(delimiter);

		                   /* if (permTemp != null) {
		                        for (int i = 0; i < permTemp.length; i++) {
		                            if (i == 0) {
		                            	paddress = permTemp[i];
		                            }
		                            if (i == 1) {
		                            	paddress2 = permTemp[i];
		                            }
		                            if (i == 2) {
		                            	paddress3 = permTemp[i];
		                            }
		                            if (i == 3) {
		                            	paddress4 = permTemp[i];
		                            }
		                        }

		                    }
		                    permAddress = paddress;
		                    
		                    if (paddress2!=null && !paddress2.equals("")){
		                    	permAddress = permAddress+" "+paddress2;
		                    }
		                    
		                    if (paddress3!=null && !paddress3.equals("")){
		                    	permAddress = permAddress+" "+paddress3;
		                    }
		                    
		                    if (paddress4!=null && !paddress4.equals("")){
		                    	permAddress = permAddress+" "+paddress4;
		                    }
		                    
		                    candidateDetailsBean.setOCD_PERM_ADDRESS(permAddress);*/
		                    
		                    if (candidateDetailsBean.getOcd_dbt_prog()!=null && candidateDetailsBean.getOcd_dbt_prog().equals("Yes")){
		                    	candidateDetailsBean.setOcd_dbt_prog("TRUE");
		                    }
		                    
		                    if (candidateDetailsBean.getOcd_dbt_prog()!=null && candidateDetailsBean.getOcd_dbt_prog().equals("No")){
		                    	candidateDetailsBean.setOcd_dbt_prog("FALSE");
		                    }
		                    
		                    if (candidateDetailsBean.getOcd_apply_jrf()!=null && candidateDetailsBean.getOcd_apply_jrf().equals("Yes")){
		                    	candidateDetailsBean.setOcd_apply_jrf("TRUE");
		                    }
		                    
		                    if (candidateDetailsBean.getOcd_apply_jrf()!=null && candidateDetailsBean.getOcd_apply_jrf().equals("No")){
		                    	candidateDetailsBean.setOcd_apply_jrf("FALSE");
		                    }
		                    
		                    
		                    if (candidateDetailsBean.getOcd_trained_bitp()!=null && candidateDetailsBean.getOcd_trained_bitp().equals("Yes")){
		                    	candidateDetailsBean.setOcd_trained_bitp("TRUE");
		                    }
		                    
		                    if (candidateDetailsBean.getOcd_trained_bitp()!=null && candidateDetailsBean.getOcd_trained_bitp().equals("No")){
		                    	candidateDetailsBean.setOcd_trained_bitp("FALSE");
		                    }
		                    
		                    
		                    if (candidateDetailsBean.getGender()!=null && !candidateDetailsBean.getGender().equals("")){
		                    	candidateDetailsBean.setGender(ConfigurationConstants.getInstance().getRefDesc(Integer.parseInt(candidateDetailsBean.getGender())));
		                    }
		                    
		                    
		                    if (candidateDetailsBean.getPattern()!=null && !candidateDetailsBean.getPattern().equals("")){
		                    	candidateDetailsBean.setPattern(ConfigurationConstants.getInstance().getRefDesc(Integer.parseInt(candidateDetailsBean.getPattern())));
		                    }
		                    
		                    if (candidateDetailsBean.getPayment()!=null && !candidateDetailsBean.getPayment().equals("")){
		                    	if (Integer.parseInt(candidateDetailsBean.getPayment())>0){
		                    		candidateDetailsBean.setPayment("TRUE");
		                    	}else{
		                    		candidateDetailsBean.setPayment("TRUE");
		                    	}
		                    }
		                    
		                    if (candidateDetailsBean.getDoc()!=null && !candidateDetailsBean.getDoc().equals("")){
		                    	if (Integer.parseInt(candidateDetailsBean.getDoc())>0){
		                    		candidateDetailsBean.setDoc("TRUE");
		                    	}else{
		                    		candidateDetailsBean.setDoc("TRUE");
		                    	}
		                    }
		                    
		                    
		                    if (candidateDetailsBean.getOcd_comm_city()!=null && !candidateDetailsBean.getOcd_comm_city().equals("")){
		                    	candidateDetailsBean.setOcd_comm_city(String.valueOf(ConfigurationConstants.getInstance().getCityIdForVal(candidateDetailsBean.getOcd_comm_city())));
		                    }
		                    
		                    if (candidateDetailsBean.getOCD_PERM_CITY()!=null && !candidateDetailsBean.getOCD_PERM_CITY().equals("")){
		                    	candidateDetailsBean.setOCD_PERM_CITY(String.valueOf(ConfigurationConstants.getInstance().getCityIdForVal(candidateDetailsBean.getOCD_PERM_CITY())));
		                    }
		                    
		                    if (candidateDetailsBean.getOcd_date_of_birth()!=null && !candidateDetailsBean.getOcd_date_of_birth().equals("")){
		                    	//Date d = new SimpleDateFormat("dd-MM-yyyy").parse(candidateDetailsBean.getOcd_date_of_birth());
		                    	//long diffDays = (new Date().getTime()-d.getTime())/ (24 * 60 * 60 * 1000);
		                    	//long age = diffDays/365;
		                    	candidateDetailsBean.setAge(candidateDetailsBean.getOcd_date_of_birth());
		                    }
		                    
					}
				}
				 
				 FileOutputStream fileOutputStream = new FileOutputStream(path+File.separator+"candidateDetails.xls");
				 ExcelCreator excelCreator = new ExcelCreator();
				 HSSFWorkbook workbook = excelCreator.createWorkbookForCandidateDetails(candidateDetailsList);
				 workbook.write(fileOutputStream);
			 }
			 
			 extractCandidatePhoto();
			 extractCandidateSign();
			 
			 doZip(path+File.separator+"Photo", path+File.separator+"CandidateDetails.zip");
			 doZip(path+File.separator+"Sign", path+File.separator+"CandidateDetails.zip");
			 doZipFile(new File(path+File.separator+"candidateDetails.xls"),path+File.separator+"CandidateDetails.zip");

			 boolean photoDeleted = deleteDir(new File(path+File.separator+"Photo"));
			 boolean signDeleted = deleteDir(new File(path+File.separator+"Sign"));
			 boolean fileDeleted= deleteFile(new File(path+File.separator+"candidateDetails.xls"));

			 //templateDownloadBean.setSuccessMessage("Zip created successfully");
			 templateDownloadBean.setSuccessMessageFlag("true");
			 
			 String actualFileName = "CandidateDetails.zip";
			 response.setContentType("application/zip");
			 String disHeader = "Attachment; Filename="+actualFileName;
             response.setHeader("Content-Disposition", disHeader);
             response.setHeader("Cache-control","no-cache, no-store");
             response.setHeader("Pragma","no-cache");
             response.setHeader("Expires","-1");
             //response.setHeader( "Content-Disposition", "Attachment; Filename="+actualFileName);

             InputStream in = null;
             ServletOutputStream outs = response.getOutputStream();

             in = new BufferedInputStream(new FileInputStream(new File(path+File.separator+"CandidateDetails.zip")));
             int ch;

             while ((ch = in.read()) != -1)
             {
                 outs.print((char) ch);
             }

             outs.flush();
             outs.close();
             in.close();
			 
			 
			 
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		//return result;
	}
	
	
	public void createDocumentZipFile() throws Exception{
		String result = null;
		List<CandidateDetailsBean> candidateDetailsList = null;
		try {
			 File f = new File("");
			 String path = f.getAbsolutePath();
			 
			 File deleteZipFile = new File(path+File.separator+"CandidateDocumentDetails.zip");
			 if (deleteZipFile.exists()){
				boolean deleteZipFileResult = deleteZipFile.delete();
				//logger.info("deleteZipFileResult    "+deleteZipFileResult);
			 }
			 
			 //deleteDir(new File(path+File.separator+"Photo"));
			// deleteDir(new File(path+File.separator+"Sign"));
			// deleteFile(new File(path+File.separator+"candidateDetails.xls"));
			 
			 result = "candidateExportData";
			// String scheduling = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SCHEDULING_ON);
			 
			// if (scheduling!=null && !scheduling.equals("") && scheduling.equals("0")){
				 candidateDetailsList = templateDownloadService.getCandidateDetailsList();
			// }
			 //if (scheduling!=null && !scheduling.equals("") && scheduling.equals("1")){
				// candidateDetailsList = templateDownloadService.getCandidateDetailsListWithScheduling();
			// }
				 
				 

			 /*if (candidateDetailsList!=null){
				 for (CandidateDetailsBean candidateDetailsBean : candidateDetailsList) {
					if (candidateDetailsBean!=null){
						String address = "";
						String address2 = "";
						String address3 = "";
						String address4 = "";
						String commAddress = "";
						String[] temp = null;
						
					
						
		                String delimiter = "\\$\\$";
		                temp = candidateDetailsBean.getOcd_comm_address().split(delimiter);

		                    if (temp != null) {
		                        for (int i = 0; i < temp.length; i++) {
		                            if (i == 0) {
		                            	address = temp[i];
		                            }
		                            if (i == 1) {
		                            	address2 = temp[i];
		                            }
		                            if (i == 2) {
		                            	address3 = temp[i];
		                            }
		                            if (i == 3) {
		                            	address4 = temp[i];
		                            }
		                        }

		                    }
		                    commAddress = address;
		                    
		                    if (address2!=null && !address2.equals("")){
		                    	commAddress = commAddress+" "+address2;
		                    }
		                    
		                    if (address3!=null && !address3.equals("")){
		                    	commAddress = commAddress+" "+address3;
		                    }
		                    
		                    if (address4!=null && !address4.equals("")){
		                    	commAddress = commAddress+" "+address4;
		                    }
		                    
		                    candidateDetailsBean.setOcd_comm_address(commAddress);
		                    
		                    
		                    //permTemp = candidateDetailsBean.getOCD_PERM_ADDRESS().split(delimiter);

		                    if (permTemp != null) {
		                        for (int i = 0; i < permTemp.length; i++) {
		                            if (i == 0) {
		                            	paddress = permTemp[i];
		                            }
		                            if (i == 1) {
		                            	paddress2 = permTemp[i];
		                            }
		                            if (i == 2) {
		                            	paddress3 = permTemp[i];
		                            }
		                            if (i == 3) {
		                            	paddress4 = permTemp[i];
		                            }
		                        }

		                    }
		                    permAddress = paddress;
		                    
		                    if (paddress2!=null && !paddress2.equals("")){
		                    	permAddress = permAddress+" "+paddress2;
		                    }
		                    
		                    if (paddress3!=null && !paddress3.equals("")){
		                    	permAddress = permAddress+" "+paddress3;
		                    }
		                    
		                    if (paddress4!=null && !paddress4.equals("")){
		                    	permAddress = permAddress+" "+paddress4;
		                    }
		                    
		                    candidateDetailsBean.setOCD_PERM_ADDRESS(permAddress);
		                    
		                    if (candidateDetailsBean.getOcd_dbt_prog()!=null && candidateDetailsBean.getOcd_dbt_prog().equals("Yes")){
		                    	candidateDetailsBean.setOcd_dbt_prog("TRUE");
		                    }
		                    
		                    if (candidateDetailsBean.getOcd_dbt_prog()!=null && candidateDetailsBean.getOcd_dbt_prog().equals("No")){
		                    	candidateDetailsBean.setOcd_dbt_prog("FALSE");
		                    }
		                    
		                    if (candidateDetailsBean.getOcd_apply_jrf()!=null && candidateDetailsBean.getOcd_apply_jrf().equals("Yes")){
		                    	candidateDetailsBean.setOcd_apply_jrf("TRUE");
		                    }
		                    
		                    if (candidateDetailsBean.getOcd_apply_jrf()!=null && candidateDetailsBean.getOcd_apply_jrf().equals("No")){
		                    	candidateDetailsBean.setOcd_apply_jrf("FALSE");
		                    }
		                    
		                    
		                    if (candidateDetailsBean.getOcd_trained_bitp()!=null && candidateDetailsBean.getOcd_trained_bitp().equals("Yes")){
		                    	candidateDetailsBean.setOcd_trained_bitp("TRUE");
		                    }
		                    
		                    if (candidateDetailsBean.getOcd_trained_bitp()!=null && candidateDetailsBean.getOcd_trained_bitp().equals("No")){
		                    	candidateDetailsBean.setOcd_trained_bitp("FALSE");
		                    }
		                    
		                    
		                    if (candidateDetailsBean.getGender()!=null && !candidateDetailsBean.getGender().equals("")){
		                    	candidateDetailsBean.setGender(ConfigurationConstants.getInstance().getRefDesc(Integer.parseInt(candidateDetailsBean.getGender())));
		                    }
		                    
		                    
		                    if (candidateDetailsBean.getPattern()!=null && !candidateDetailsBean.getPattern().equals("")){
		                    	candidateDetailsBean.setPattern(ConfigurationConstants.getInstance().getRefDesc(Integer.parseInt(candidateDetailsBean.getPattern())));
		                    }
		                    
		                    if (candidateDetailsBean.getPayment()!=null && !candidateDetailsBean.getPayment().equals("")){
		                    	if (Integer.parseInt(candidateDetailsBean.getPayment())>0){
		                    		candidateDetailsBean.setPayment("TRUE");
		                    	}else{
		                    		candidateDetailsBean.setPayment("TRUE");
		                    	}
		                    }
		                    
		                    if (candidateDetailsBean.getDoc()!=null && !candidateDetailsBean.getDoc().equals("")){
		                    	if (Integer.parseInt(candidateDetailsBean.getDoc())>0){
		                    		candidateDetailsBean.setDoc("TRUE");
		                    	}else{
		                    		candidateDetailsBean.setDoc("TRUE");
		                    	}
		                    }
		                    
		                    
		                    if (candidateDetailsBean.getOcd_comm_city()!=null && !candidateDetailsBean.getOcd_comm_city().equals("")){
		                    	candidateDetailsBean.setOcd_comm_city(String.valueOf(ConfigurationConstants.getInstance().getCityIdForVal(candidateDetailsBean.getOcd_comm_city())));
		                    }
		                    
		                    if (candidateDetailsBean.getOCD_PERM_CITY()!=null && !candidateDetailsBean.getOCD_PERM_CITY().equals("")){
		                    	candidateDetailsBean.setOCD_PERM_CITY(String.valueOf(ConfigurationConstants.getInstance().getCityIdForVal(candidateDetailsBean.getOCD_PERM_CITY())));
		                    }
		                    
		                    if (candidateDetailsBean.getOcd_date_of_birth()!=null && !candidateDetailsBean.getOcd_date_of_birth().equals("")){
		                    	//Date d = new SimpleDateFormat("dd-MM-yyyy").parse(candidateDetailsBean.getOcd_date_of_birth());
		                    	//long diffDays = (new Date().getTime()-d.getTime())/ (24 * 60 * 60 * 1000);
		                    	//long age = diffDays/365;
		                    	candidateDetailsBean.setAge(candidateDetailsBean.getOcd_date_of_birth());
		                    }
		                    
					}
				}
				 
				 FileOutputStream fileOutputStream = new FileOutputStream(path+File.separator+"candidateDetails.xls");
				 ExcelCreator excelCreator = new ExcelCreator();
				 HSSFWorkbook workbook = excelCreator.createWorkbookForCandidateDetails(candidateDetailsList);
				 workbook.write(fileOutputStream);
			 }*/
			 
			extractCandidateDocument();
			 //extractCandidateSign();
			 
			 doZip(path+File.separator+"CandidateDocument", path+File.separator+"CandidateDocument.zip");
			 //doZip(path+File.separator+"Sign", path+File.separator+"CandidateDetails.zip");
			// doZipFile(new File(path+File.separator+"candidateDetails.xls"),path+File.separator+"CandidateDetails.zip");

			// boolean photoDeleted = deleteDir(new File(path+File.separator+"CandidateDocument"));
			// boolean signDeleted = deleteDir(new File(path+File.separator+"Sign"));
			// boolean fileDeleted= deleteFile(new File(path+File.separator+"candidateDetails.xls"));

			 templateDownloadBean.setSuccessMessage("Zip created successfully");
			 templateDownloadBean.setSuccessMessageFlag("true");
			 
			 String actualFileName = "CandidateDocument.zip";
			 response.setContentType("application/zip");
			 String disHeader = "Attachment; Filename="+actualFileName;
             response.setHeader("Content-Disposition", disHeader);
             response.setHeader("Cache-control","no-cache, no-store");
             response.setHeader("Pragma","no-cache");
             response.setHeader("Expires","-1");
             //response.setHeader( "Content-Disposition", "Attachment; Filename="+actualFileName);

             InputStream in = null;
             ServletOutputStream outs = response.getOutputStream();

             in = new BufferedInputStream(new FileInputStream(new File(path+File.separator+"CandidateDocument.zip")));
             int ch;

             while ((ch = in.read()) != -1)
             {
                 outs.print((char) ch);
             }

             outs.flush();
             outs.close();
             in.close();
			 
			 
			 
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		//return result;
	}
	
	private void extractCandidateDocument() throws Exception{
		Map<String, List<CandidateDocBean>> lMapUserDoc=templateDownloadService.getCandidateDocumentMap();
		String extPath = "";
		extPath = File.separator+"CandidateDocument";
		File file = new File("");
		String path = file.getAbsolutePath();
		String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
		 File docFolder = new File(path+extPath);
		 if(!docFolder.exists())
		 	docFolder.mkdir();
		for (Map.Entry<String, List<CandidateDocBean>> entry : lMapUserDoc.entrySet())
		{
			//System.out.println(entry.getKey() + "/" + entry.getValue());
			List<CandidateDocBean> lCandidateDocBeanList= (List<CandidateDocBean>)entry.getValue();
			if (lCandidateDocBeanList !=null && !lCandidateDocBeanList.isEmpty()){
				for (CandidateDocBean candidateDocBean : lCandidateDocBeanList) {
					if (candidateDocBean!=null){
						FileOutputStream fop = null;
						FileInputStream inp = null;
						File newfolder = new File(path+extPath+File.separator+String.valueOf(candidateDocBean.getOcd_created_by()));
						if(!newfolder.exists())
							newfolder.mkdir();

						File f = new File(path+extPath+File.separator+String.valueOf(candidateDocBean.getOcd_created_by())+File.separator+String.valueOf(candidateDocBean.getOCD_DOC_FILE_NAME()));
						if (!f.exists()) {
							f.createNewFile();
						}
						inp=new FileInputStream(new File(DocumentPath+""+candidateDocBean.getOcd_created_by()+"\\"+candidateDocBean.getOCD_FLAG()+"_"+candidateDocBean.getOCD_DOC_FILE_NAME()));
						fop = new FileOutputStream(f);
						IOUtils.copy(inp, fop);
						fop.close();
					}
				}
			}


		}
		
	}
	

	private void extractCandidatePhoto() throws Exception{
		List<CandidateBean> candidateImageList = null;
		String extPath = "";
		 extPath = File.separator+"Photo";
		 
		 File file = new File("");
		 String path = file.getAbsolutePath();
		
		 File newfile = new File(path+extPath);
		 newfile.mkdir();
		 
		 String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
		 //String scheduling = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SCHEDULING_ON);
		 
		 //if (scheduling!=null && !scheduling.equals("") && scheduling.equals("0")){
			 candidateImageList = templateDownloadService.getCandidateImage();
		 //}
		/* if (scheduling!=null && !scheduling.equals("") && scheduling.equals("1")){
			 candidateImageList = templateDownloadService.getCandidateImageForScheduling();
		 }*/
		 
		 //candidateImageList = templateDownloadService.getCandidateImage();
		 
		 if (candidateImageList!=null && !candidateImageList.isEmpty()){
			 for (CandidateBean candidateBean : candidateImageList) {
				if (candidateBean!=null){
					FileOutputStream fop = null;
					FileInputStream inp = null;
					File f = new File(path+extPath+File.separator+candidateBean.getUserId()+".jpg");
					if (!f.exists()) {
						f.createNewFile();
					}
					inp=new FileInputStream(new File(DocumentPath+""+candidateBean.getUserId()+"\\"+candidateBean.getUserId()+"_photo.jpg"));
					fop = new FileOutputStream(f);
					IOUtils.copy(inp, fop);
					fop.close();
				}
			}
		 }
	}
	

	private void extractCandidateSign() throws Exception, IOException {
		 List<CandidateBean> candidateSignList = null;
		 String extPath = "";
		 extPath = File.separator+"Sign";
		 
		 File file = new File("");
		 String path = file.getAbsolutePath();
		
		 File newfile = new File(path+extPath);
		 newfile.mkdir();
		 
		 String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
		 //String scheduling = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SCHEDULING_ON);
		 
		 //if (scheduling!=null && !scheduling.equals("") && scheduling.equals("0")){
			 candidateSignList = templateDownloadService.getCandidateSignature();
		// }
		 
		 //if (scheduling!=null && !scheduling.equals("") && scheduling.equals("1")){
			// candidateSignList = templateDownloadService.getCandidateSignatureForScheduling();
		// }
		 if (candidateSignList!=null && !candidateSignList.isEmpty()){
			 for (CandidateBean candidateBean : candidateSignList) {
				if (candidateBean!=null){
					FileOutputStream fop = null;
					FileInputStream inp = null;
					File f = new File(path+extPath+File.separator+candidateBean.getUserId()+".jpg");
					if (!f.exists()) {
						f.createNewFile();
					}
					inp=new FileInputStream(new File(DocumentPath+""+candidateBean.getUserId()+"\\"+candidateBean.getUserId()+"_sign.jpg"));
					fop = new FileOutputStream(f);
					IOUtils.copy(inp, fop);
				}
			}
		 }
	}
	
	private void doZip(String folderToAdd, String destZipFile) throws ZipException{
		ZipFile zipFile = new ZipFile(destZipFile);		
		ZipParameters parameters = new ZipParameters();
		zipFile.addFolder(folderToAdd, parameters);
	}
	
	private void doZipFile(File fileToAdd, String destZipFile) throws ZipException{
		ZipFile zipFile = new ZipFile(destZipFile);		
		ZipParameters parameters = new ZipParameters();
		if (fileToAdd.exists()){
			zipFile.addFile(fileToAdd, parameters);	
		}
	}
	
	private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
        	boolean delete = false;
            File[] children = dir.listFiles();
            for (int i=0; i<children.length; i++) {
                if (children[i].isFile()){
                	//logger.info("children  is  "+children[i]);
                	delete = children[i].delete();
                }                
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
	}
	
	private boolean deleteFile(File fileToDelete) {
		boolean delete = false;
		if (fileToDelete.exists()){
			delete = fileToDelete.delete();
		}
        return delete;
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
