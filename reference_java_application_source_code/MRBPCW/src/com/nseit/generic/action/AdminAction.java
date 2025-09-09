package com.nseit.generic.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.HttpParameters;

import com.nseit.generic.models.AdminBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.aware.UserAware;
import com.opensymphony.xwork2.ModelDriven;

public class AdminAction extends BaseAction implements ModelDriven<AdminBean>, UserAware{
	
	  private AdminBean adminBean = new AdminBean();
	  private Logger logger = LoggerHome.getLogger(getClass());
	  
	  @Override
	public void resetModel() {
		adminBean = new AdminBean();
		
	}

	@Override
	public AdminBean getModel() {
		return adminBean;
	}

	@Override
	public void setLoggedInUser(Users users) {
		loggedInUser = users;
		
	}
	
	
	public String getHomePageForAdmin() {
		//logger.info("getHomePageForAdmin()");
		String result = null;
		try {
			result = "adminHomePage";
		} catch (Throwable e) {
			logger.fatal(e,e);
		}
		return result;
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
	
	/*public String getEncryptQBank(){
		logger.info("getEncryptQBank()");
		String result = null;
		try{
			result = "encryptQBank";
		}
		catch (Exception e) {
			logger.fatal(e,e);
		}
		return result;
	}*/
	
	/*public String uploadQBank(){
		logger.info("uploadQBank()");
		try{
			FileInputStream fileInputStream = null;
			FileOutputStream fileOutputStream = null;
			File qBankPath = null;
			String password = null;
			
			if(adminBean.getQbankPassword()!=null){
				password = adminBean.getQbankPassword();
			}
			
			if (adminBean.getAttachedQbank()!= null){
				qBankPath = adminBean.getAttachedQbank();
				String fileName = qBankPath.getName();
				
				fileInputStream = new FileInputStream(qBankPath);
				fileOutputStream = new FileOutputStream(qBankPath + ".des");
				
				PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
				SecretKey passwordKey = keyFactory.generateSecret(keySpec);
				
				byte[] salt = new byte[8];
				Random random = new Random();
				random.nextBytes(salt);
				int iterations = 100;
				 
				PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterations);
				 
				Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
				cipher.init(Cipher.ENCRYPT_MODE, passwordKey, parameterSpec);
				
				fileOutputStream.write(salt);
				
				byte[] input = new byte[64];
				int bytesRead;
				while ((bytesRead = fileInputStream.read(input)) != -1)
				{
					byte[] output = cipher.update(input, 0, bytesRead);
				    if (output != null) 
				    	fileOutputStream.write(output);
				}
				
				byte[] output = cipher.doFinal();
				
				if (output != null) {
					fileOutputStream.write(output);
				}
				
				
				
				//String extn = adminBean.getFileNameForUpload();
				
				
				File fileForUpload =null;
				fileForUpload = new File(qBankPath + ".des");
				
				
				File file = new File("");
                String path = file.getAbsolutePath();
                
                String old = "\\old";
                String extPath = "";
                extPath = "\\QBANK";
                
                path = path + extPath;
	
                
                String fileNameForUpload = fileForUpload.getName();
				
				String destFilePath = path+"\\Upload_QBank_"+new SimpleDateFormat("dd-MMM-yyyy HHmmss").format(new Date(System.currentTimeMillis()))+".csv";
                
				File destFile = new File(destFilePath);
                
                File newFile = new File(path);

                if (!newFile.exists())
                {
                    newFile.mkdirs();
                }else {
                	File fileForDelete = new File(path);
                	
                	File[] listOfFiles = fileForDelete.listFiles();
                	
                	if (listOfFiles!=null){
                		for (int i = 0; i < listOfFiles.length; i++) {
							if (listOfFiles[i].isFile()){
								listOfFiles[i].delete();
							}
						}
                	}
                	
                	
                	

                    }
              //  File srcFile = adminBean.getAttachedQbank();                
                
			
				
				FileUtils.copyFile(fileForUpload, destFile) ;
				
				if(fileInputStream != null){
					int bytesRead = 0;
					byte[] byteBuff = new byte[1000];
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					
					while ((bytesRead = fileInputStream.read(byteBuff, 0, 1000)) != -1)
					{
						byteArrayOutputStream.write(byteBuff, 0, bytesRead);
					}
					
					sessionAttributes.put(GenericConstants.SESSION_QBANK, byteArrayOutputStream);
				}
			}
			
			adminBean.setFileUploaded("Y");
		}
		catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return "uploaded";
		
	}*/
	
	
	/*public String downloadEncryptedFile(){
		logger.info("downloadEncryptedFile()");
		String result =null;
		try {
			File fileForDownload = new File("");
			String path = fileForDownload.getAbsolutePath();

			String extPath = "QBANK";
			
			String actualPath = path +"\\"+extPath;
			
            File actualFile = new File(actualPath);

            File[] lstFiles = null;
            
            lstFiles = actualFile.listFiles();
            String actualFileName = "";
            if (lstFiles!=null){
            	for (int i = 0; i < lstFiles.length; i++) {
					actualFileName = lstFiles[i].getName();
				}
            }
            
            
            String disHeader = "Attachment; Filename="+actualFileName;
            response.setHeader("Content-Disposition", disHeader);
            response.setHeader("Cache-control","no-cache, no-store");
            response.setHeader("Pragma","no-cache");
            response.setHeader("Expires","-1");
            //response.setHeader( "Content-Disposition", "Attachment; Filename="+actualFileName);
            
            String downloadPath2 =	actualPath + "\\" + actualFileName;
            File fileToDownload2 = new File(downloadPath2);

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
            
			
		} catch (Exception e) {
			logger.fatal(e,e);
		}
		
		return result;
		
	}*/
	

}
