package com.nseit.generic.action;

import static com.nseit.generic.util.GenericConstants.DESTINATION_URL;
import static com.nseit.generic.util.GenericConstants.REDIRECT_ACTION_URL;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;

import com.csvreader.CsvReader;
import com.nseit.generic.models.UploadManagementBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.service.UploadManagementService;
import com.nseit.generic.util.CSVFileReaderUtil;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ZipUnzipUtility;
import com.nseit.generic.util.aware.UserAware;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;
import com.opensymphony.xwork2.ModelDriven;

import net.lingala.zip4j.exception.ZipException;

/**
 * @author shailendras
 */
public class UploadManagementAction extends BaseAction implements ModelDriven<UploadManagementBean>, UserAware {

	private static final long serialVersionUID = -6209186396968615376L;
	private Logger logger = LoggerHome.getLogger(getClass());
	private byte[] byteArrImage = null;

	private UploadManagementBean uploadManagementBean = new UploadManagementBean();
	private UploadManagementService uploadManagementService;
	private CandidateService candidateService;

	String databaseImageType = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.IMAGE_TYPE).toLowerCase();
	String databaseSignatureType = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SIGNATURE_TYPE).toLowerCase();

	public UploadManagementAction() {
		// logger.info("UploadManagementAction constructor call ");
		displayMenus();
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public String getUploadManagement() {
		// logger.info("getUploadManagement");
		String menuKey = null;
		String result = null;
		try {
			if (request.getAttribute("parentMenuKey") != null) {
				menuKey = request.getAttribute("parentMenuKey").toString();
			}
			String actionUrl = redirectToNextActivePage(menuKey, loggedInUser);
			if (actionUrl == null) {
				request.setAttribute(DESTINATION_URL, actionUrl);
				result = "childNotFoundForMenu";
			} else {
				request.setAttribute(DESTINATION_URL, actionUrl);
				result = REDIRECT_ACTION_URL;
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;
	}

	public String getUploadBulkManagement() {
		// logger.info("getUploadBulkManagement()");
		// addActionMessage(null);
		return "getUploadBulkManagement";
	}

	public String getUploadCandidateMarks() {
		// logger.info("getUploadCandidateMarks()");
		return "getUploadCandidateMarks";
	}

	public String getUploadOfflinePayments() {
		// logger.info("getUploadOfflinePayments()");
		return "getUploadOfflinePayments";
	}

	public String getUploadCandidateSchedule() {
		// logger.info("getUploadCandidateSchedule()");
		return "getUploadCandidateSchedule";
	}

	public String getUploadCandidateWrittenTestScore() {
		// logger.info("getUploadCandidateWrittenTestScore()");
		return "getUploadCandidateWrittenTestScore";
	}

	public String getUploadCandidateInterviewScore() {
		// logger.info("getUploadCandidateInterviewScore()");
		return "getUploadCandidateInterviewScore";
	}

	public String getUploadCandidateFieldTestScore() {
		// logger.info("getUploadCandidateFieldTestScore()");
		return "getUploadCandidateFieldTestScore";
	}

	public String getUploadFieldSchedule() {
		// logger.info("getUploadFieldSchedule()");
		return "getUploadFieldSchedule";
	}

	public String getUploadCandidateMedicalScore() {
		// logger.info("getUploadCandidateFieldTestScore()");
		return "getUploadCandidateMedicalScore";
	}

	public String getUploadInterviewSchedule() {
		// logger.info("getUploadInterviewSchedule()");
		return "getUploadInterviewSchedule";
	}

	public String getUploadMedicalSchedule() {
		// logger.info("getUploadMedicalSchedule()");
		return "getUploadMedicalSchedule";
	}

	/**
	 * @author Shailendra sharma
	 * @return
	 */
	public String bulkUpload() {
		/*
		 * logger.info("bulkUpload()"); logger.info("File :" +
		 * uploadManagementBean.getFileUpload()); logger.info("Filename : "+
		 * uploadManagementBean.getFileUploadFileName());
		 * logger.info("File type : "+
		 * uploadManagementBean.getFileUploadContentType());
		 */
		Long startTime = System.currentTimeMillis();
		String executionTime = null;
		// logger.info("Start Time :: "+startTime);
		if (uploadManagementBean.getFileUploadFileName() != null && uploadManagementBean.getFileUploadFileName().split("\\.")[1].equals("zip")) {
			try {
				String zipFilePath = System.getProperty("user.dir") + "//";
				File srcFile = uploadManagementBean.getFileUpload();
				final String newFilePath = zipFilePath + uploadManagementBean.getFileUploadFileName();
				String folderName = uploadManagementBean.getFileUploadFileName().split("\\.")[0];
				File destFile = new File(newFilePath);
				FileUtils.copyFile(srcFile, destFile);
				String imageType = uploadManagementBean.getImageType();
				String signType = uploadManagementBean.getSignType();
				String candidateDetailsType = uploadManagementBean.getCandidateDetailsType();
				if (candidateDetailsType == null) {
					candidateDetailsType = "false";
				}
				if (!imageType.equals("Image") && !signType.equals("Sign") && !candidateDetailsType.equals("CandDtls")) {
					addActionMessage("Please select Upload Type");
					displayMenus();
					return "getUploadBulkManagement";
				}
				unZipFolder(zipFilePath, newFilePath, folderName);

				if (!imageType.equals("false")) {
					uploadManagementBean.setUploaded("true");
					int imageCount = readImageFilesAndInsertUpdateInDataBase(zipFilePath, imageType, folderName);
					uploadManagementBean.setImageCount(imageCount);
					// logger.info("Image Count "+imageCount);
				}
				if (!signType.equals("false")) {
					uploadManagementBean.setUploaded("true");
					int signCount = readSignFilesAndInsertUpdateInDataBase(zipFilePath, signType, folderName);
					uploadManagementBean.setSignCount(signCount);
					// logger.info("Sign Count "+signCount);
				}
				if (!candidateDetailsType.equals("false")) {
					uploadManagementBean.setUploaded("true");
					readCSVFilesAndInsertUpdateInDataBase(zipFilePath, folderName);
				}
				boolean folderDeleted = deleteDir(new File(zipFilePath + File.separator + folderName));
				// logger.info(folderName +" Deleted "+folderDeleted);
				boolean zipDeleted = new File(zipFilePath + File.separator + uploadManagementBean.getFileUploadFileName()).delete();
				/*
				 * logger.info(uploadManagementBean.getFileUploadFileName()
				 * +" Deleted "+zipDeleted); logger.info("Done");
				 */

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			addActionMessage("Please browse ZIP file for upload the data");
		}
		Long endTime = System.currentTimeMillis();
		/*
		 * logger.info("End Time :: "+System.currentTimeMillis());
		 * logger.info("Total Time In Execution :"+(endTime-startTime));
		 */
		executionTime = getExecutionTime(endTime - startTime);

		uploadManagementBean.setExecutionTime(executionTime);
		displayMenus();
		return "getUploadBulkManagement";
	}

	@SuppressWarnings("deprecation")
	public String uploadOfflinePayments() {

		// logger.info("Calling uploadOfflinePayments()");
		long StartTime = System.currentTimeMillis();
		// logger.info("Start Time "+StartTime);
		try {
			/*
			 * logger.info("uploadOfflinePayments in try"); logger.info("File :"
			 * + uploadManagementBean.getFileUpload());
			 * logger.info("Filename : "+
			 * uploadManagementBean.getFileUploadFileName());
			 * logger.info("File type : "+
			 * uploadManagementBean.getFileUploadContentType());
			 */

			if (uploadManagementBean.getFileUpload() == null || uploadManagementBean.getFileUpload().equals("")) {
				addActionMessage("Please select a File to Upload");
				displayMenus();
				return "getUploadOfflinePayments";
			}

			String csvFilePath = System.getProperty("user.dir") + "//";
			File srcFile = uploadManagementBean.getFileUpload();
			final String newFilePath = csvFilePath + uploadManagementBean.getFileUploadFileName();

			int pos = newFilePath.lastIndexOf('.');
			String extn = newFilePath.substring(pos + 1);
			if (extn != null && !extn.equals("")) {
				if (!(extn.equals("csv"))) {
					addActionMessage("Please select a CSV file for upload.");
					displayMenus();
					return "getUploadOfflinePayments";
				}
			}

			File destFile = new File(newFilePath);
			FileUtils.copyFile(srcFile, destFile);

			// CSVFileReaderUtil.readCSVFile(newFilePath);
			LinkedHashMap<String, String[]> paymentsLinkedHashMap = CSVFileReaderUtil.readCSVFile(newFilePath);
			String[] rowValueLHM = null;

			StringBuilder sb;
			StringBuilder strBuildPaymentSumm = new StringBuilder();

			Set<String> setOfflinePayment = paymentsLinkedHashMap.keySet();
			int csvRecordCount = 0;

			for (String strOfflinePayment : setOfflinePayment) {
				try {
					rowValueLHM = paymentsLinkedHashMap.get(strOfflinePayment);
					UploadManagementBean uploadManagementBackingBean = new UploadManagementBean();
					sb = new StringBuilder();
					for (int colCount = 0; colCount < rowValueLHM.length; colCount++) {
						switch (colCount) {
						case 0:
							uploadManagementBackingBean.setCandidateID(rowValueLHM[colCount].toString());
							break;
						case 1:
							sb.append(rowValueLHM[colCount].toString() + " ");
							break;
						case 2:
							sb.append(rowValueLHM[colCount].toString());
							uploadManagementBackingBean.setCandidateName(sb.toString());
							break;
						case 3:
							uploadManagementBackingBean.setCandidateProgram(Long.parseLong(rowValueLHM[colCount]));
							break;
						case 4:
							uploadManagementBackingBean.setTransactionType(rowValueLHM[colCount].toString());
							break;
						case 5:
							uploadManagementBackingBean.setTransactionNo(rowValueLHM[colCount].toString());
							break;
						case 6:
							uploadManagementBackingBean.setTransactionDate(CommonUtil.formatDate(new Date(rowValueLHM[colCount].toString()), GenericConstants.DATE_FORMAT_DEFAULT));
							break;
						case 7:
							uploadManagementBackingBean.setBankName(rowValueLHM[colCount].toString());
							break;
						case 8:
							uploadManagementBackingBean.setBankCode(rowValueLHM[colCount].toString());
							break;
						case 9:
							uploadManagementBackingBean.setBankBranchName(rowValueLHM[colCount].toString());
							break;
						case 10:
							uploadManagementBackingBean.setBankBranchCode(rowValueLHM[colCount].toString());
							break;
						case 11:
							uploadManagementBackingBean.setBankCityName(rowValueLHM[colCount].toString());
							break;
						case 12:
							uploadManagementBackingBean.setTransactionDtls(rowValueLHM[colCount].toString());
							break;
						}
						uploadManagementBackingBean.setPaymentStatus("A");

						// logger.info("paymentsLinkedHashMap Values :
						// "+rowValueLHM[colCount].toString() +" for key
						// "+strOfflinePayment);
					}

					Long userPk = uploadManagementService.getUserPK(uploadManagementBackingBean.getCandidateID());
					// logger.info("Check for valid user
					// "+uploadManagementBackingBean.getCandidateID()+ " :
					// "+userPk.equals(new Long(0)));

					if (!userPk.equals(new Long(0))) {

						/**
						 * Check for Type of Transaction: Transaction :
						 * uploadManagementBackingBean.getTransactionType()
						 */
						if (uploadManagementBackingBean.getTransactionType().toUpperCase().equals("DD")
								|| uploadManagementBackingBean.getTransactionType().toUpperCase().equals("CH")) {
							/**
							 * Check for NULL AND EMPTY: BANK NAME :
							 * uploadManagementBackingBean.getBankName() BANK
							 * BRANCH NAME :
							 * uploadManagementBackingBean.getBankBranchName()
							 * BANK CODE :
							 * uploadManagementBackingBean.getBankCode() BANK
							 * BRANCH CODE :
							 * uploadManagementBackingBean.getBankBranchCode()
							 * BANK CITY NAME :
							 * uploadManagementBackingBean.getBankCityName()
							 */
							if ((uploadManagementBackingBean.getBankName() != null) && (uploadManagementBackingBean.getBankBranchName() != null)
									&& (uploadManagementBackingBean.getBankCode() != null) && (uploadManagementBackingBean.getBankBranchCode() != null)
									&& (uploadManagementBackingBean.getBankCityName() != null) && (!uploadManagementBackingBean.getBankName().equals(""))
									&& (!uploadManagementBackingBean.getBankBranchName().equals("")) && (!uploadManagementBackingBean.getBankCode().equals(""))
									&& (!uploadManagementBackingBean.getBankBranchCode().equals("")) && (!uploadManagementBackingBean.getBankCityName().equals(""))) {

								int recordCount = uploadManagementService.saveOfflinePaymentDtls(uploadManagementBackingBean, userPk, loggedInUser);

								// logger.info("Record "+recordCount);
								if (recordCount > 0) {
									csvRecordCount++;
								}
							} else {
								strBuildPaymentSumm.append(uploadManagementBean.getFileUploadFileName() + " : "
										+ ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_OFFLINE_PAYMENT_ROW_ERROR_MESSAGE) + " : "
										+ strOfflinePayment.toString() + "<br/>");
							}
						} else {
							strBuildPaymentSumm.append(uploadManagementBean.getFileUploadFileName() + " : "
									+ ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_OFFLINE_PAYMENT_ROW_ERROR_MESSAGE) + " : "
									+ strOfflinePayment.toString() + "<br/>");
						}
					} else {
						strBuildPaymentSumm.append(uploadManagementBean.getFileUploadFileName() + " : "
								+ ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_OFFLINE_PAYMENT_ROW_ERROR_MESSAGE) + " : "
								+ strOfflinePayment.toString() + "<br/>");
					}
				} catch (Exception e) {
					strBuildPaymentSumm.append(uploadManagementBean.getFileUploadFileName() + " : "
							+ ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_OFFLINE_PAYMENT_ROW_ERROR_MESSAGE) + " : "
							+ strOfflinePayment.toString() + "<br/>");
					logger.fatal(e.getMessage());
					continue;
				}
			}
			long endTime = System.currentTimeMillis();
			String executionTime = getExecutionTime(endTime - StartTime);
			uploadManagementBean.setUploaded("true");
			uploadManagementBean.setCsvRecordCountSuccess(csvRecordCount);
			uploadManagementBean.setExecutionTime(executionTime);
			uploadManagementBean.setPaymentSummary(strBuildPaymentSumm.toString());

		} catch (Exception ex) {
			logger.fatal(ex.getMessage());
		} finally {

		}
		displayMenus();
		return "getUploadOfflinePayments";

	}

	/**
	 * @author Shailendra Sharma
	 * @return
	 */
	private int readImageFilesAndInsertUpdateInDataBase(String zipFilePath, String uploadType, String folderName) throws Exception {
		int count = 0;
		StringBuilder strBlrd = new StringBuilder();
		File files = new File(zipFilePath + File.separator + folderName + File.separator + uploadType);
		if (files.listFiles() != null) {
			int imageCount = 0;
			for (File file : files.listFiles()) {
				File photoPath = null;
				photoPath = new File(zipFilePath + File.separator + folderName + File.separator + uploadType + File.separator + file.getName());
				FileInputStream fi = new FileInputStream(photoPath);
				if (fi != null) {
					int bytesRead = 0;
					byte[] byteBuff = new byte[1000];
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					try {
						while ((bytesRead = fi.read(byteBuff, 0, 1000)) != -1) {
							byteArrayOutputStream.write(byteBuff, 0, bytesRead);
						}
					} catch (IOException e) {
						throw new GenericException(e);
					} finally {
						try {
							fi.close();
						} catch (IOException e) {
							LoggerHome.getLogger(getClass()).fatal(e, e);
						}
					}
					byteArrImage = byteArrayOutputStream.toByteArray();
					uploadManagementBean.setByteArrayImage(byteArrImage);
				}
				String registrationId = file.getName().split("\\.")[0];
				Long userId = uploadManagementService.getUserId(registrationId);
				loggedInUser.setUserId(userId);
				if (userId != 0) {
					if (!databaseImageType.contains(file.getName().split("\\.")[1].trim().toLowerCase())) {
						strBlrd.append("File <b>" + file.getName() + "</b> " + " type is not supported  only "
								+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.IMAGE_TYPE).toLowerCase() + " supported <br/>");
					} else if ((photoPath.length() / 1024) > Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_IMAGE_SIZE_IN_KB))) {
						strBlrd.append("File <b>" + file.getName() + "</b> " + " size exceeds "
								+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_IMAGE_SIZE_IN_KB) + "KB<br/>");
					} else {
						int candidateImageCount = uploadManagementService.insertCandidateImages(uploadManagementBean, loggedInUser);
						if (candidateImageCount > 0) {
							imageCount++;
						}
					}
				} else {
					strBlrd.append("User <b> " + registrationId + " </b>does not exist<br/>");
				}
			}
			uploadManagementBean.setImageSummary(strBlrd.toString());
			count = imageCount;
		} else {
			addActionMessage(uploadType + " Folder Is not Available");
		}
		displayMenus();
		return count;
	}

	/**
	 * @author Shailendra Sharma
	 * @return
	 */
	private int readSignFilesAndInsertUpdateInDataBase(String zipFilePath, String uploadType, String folderName) throws Exception {
		// logger.info("Calling readSignFilesAndInsertUpdateInDataBase()");
		StringBuilder strBlrd = new StringBuilder();
		int count = 0;
		File files = new File(zipFilePath + File.separator + folderName + File.separator + uploadType);
		if (files.listFiles() != null) {
			int signCount = 0;
			for (File file : files.listFiles()) {
				File photoPath = null;
				photoPath = new File(zipFilePath + File.separator + folderName + File.separator + uploadType + File.separator + file.getName());
				FileInputStream fi = new FileInputStream(photoPath);
				if (fi != null) {
					int bytesRead = 0;
					byte[] byteBuff = new byte[1000];
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					try {
						while ((bytesRead = fi.read(byteBuff, 0, 1000)) != -1) {
							byteArrayOutputStream.write(byteBuff, 0, bytesRead);
						}
					} catch (IOException e) {
						throw new GenericException(e);
					} finally {
						try {
							fi.close();
						} catch (IOException e) {
							LoggerHome.getLogger(getClass()).fatal(e, e);
						}
					}
					byteArrImage = byteArrayOutputStream.toByteArray();
					uploadManagementBean.setByteArrayImage(byteArrImage);
				}
				String registrationId = file.getName().split("\\.")[0];
				Long userId = uploadManagementService.getUserId(registrationId);
				loggedInUser.setUserId(userId);
				if (userId != 0) {
					if (!databaseSignatureType.contains(file.getName().split("\\.")[1].trim().toLowerCase())) {
						strBlrd.append("File <b>" + file.getName() + "</b> " + " type is not supported  only "
								+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SIGNATURE_TYPE).toLowerCase() + " supported <br/>");
					} else if ((photoPath.length() / 1024) > Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_SIGNATURE_SIZE_IN_KB))) {
						strBlrd.append("File <b>" + file.getName() + "</b> " + " size exceeds "
								+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_SIGNATURE_SIZE_IN_KB) + "KB<br/>");
					} else {
						int candidateSignCount = uploadManagementService.insertCandidateSign(uploadManagementBean, loggedInUser);
						if (candidateSignCount > 0) {
							signCount++;
						}
					}
				} else {
					strBlrd.append("User <b> " + registrationId + " </b>does not exist<br/>");
				}
			}
			uploadManagementBean.setSignSummary(strBlrd.toString());
			count = signCount;
		} else {
			addActionMessage(uploadType + " Folder Is not Available");
		}
		displayMenus();
		return count;

	}

	/**
	 * @author Shailendra Sharma
	 * @return
	 */
	private void readCSVFilesAndInsertUpdateInDataBase(String zipFilePath, String folderName) throws IOException {
		// logger.info("Calling readCSVFilesAndInsertUpdateInDataBase()");
		CsvReader csvReader = null;
		try {
			File files = new File(zipFilePath + File.separator + folderName);
			for (File file : files.listFiles()) {
				File photoPath = null;
				photoPath = new File(zipFilePath + File.separator + folderName + File.separator + file.getName());
				csvReader = new CsvReader(photoPath.toString());
				csvReader.readHeaders();
				if (csvReader != null) {
					csvReader.readHeaders();
					while (csvReader.readRecord()) {
						// TODO 2:
					}
				}
				// TODO candidate details insertion code should be here
			}

		} catch (FileNotFoundException e) {
			logger.fatal(e.getMessage());
		}
	}

	/**
	 * @author Shailendra Sharma
	 * @return
	 */
	private void unZipFolder(String zipFilePath, String newFilePath, String extractFolderName) throws FileNotFoundException, IOException {
		// logger.info("Calling unZipFolder()");
		ZipUnzipUtility unzipUtility = new ZipUnzipUtility();
		try {
			unzipUtility.extractAll(zipFilePath + File.separator + extractFolderName, newFilePath);
		} catch (ZipException e) {
			logger.fatal("error", e.getMessage());
		}
	}

	/**
	 * @author Shailendra Sharma
	 * @return
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}

	public String getExecutionTime(long timeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(timeMillis);
	}

	@Override
	public void resetModel() {
		uploadManagementBean = new UploadManagementBean();

	}

	@Override
	public UploadManagementBean getModel() {
		return uploadManagementBean;
	}

	@Override
	public void setLoggedInUser(Users users) {
		loggedInUser = users;
	}

	public void setUploadManagementService(UploadManagementService uploadManagementService) {
		this.uploadManagementService = uploadManagementService;
	}

	public byte[] getImageBytes() {
		return byteArrImage;
	}

	// download templates
	public void downloadScheduleTemplate() throws Exception {
		// logger.info("downloadScheduleTemplate");
		/* try{ */
		ServletContext context = ServletActionContext.getServletContext();
		String fullPath = context.getRealPath("/uploads/");
		PrintWriter out = response.getWriter();
		response.setHeader("Content-Disposition", "attachment; filename=WrittenSchedule.csv");
		/*
		 * response.setHeader("Cache-control","no-cache, no-store");
		 * response.setHeader("Pragma","no-cache");
		 * response.setHeader("Expires","-1");
		 */
		FileInputStream fileInputStream = new FileInputStream(fullPath + "/WrittenSchedule.csv");

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.flush();
		out.close();
		/*
		 * } catch (Throwable e) { logger.fatal(e,e); }
		 */

	}

	public void downloadWrittenTestTemplate() throws Exception {
		logger.info("downloadWrittenTestTemplate");
		/* try{ */
		ServletContext context = ServletActionContext.getServletContext();
		String fullPath = context.getRealPath("/uploads/");
		PrintWriter out = response.getWriter();
		response.setHeader("Content-Disposition", "attachment; filename=WrittenScore.csv");
		/*
		 * response.setHeader("Cache-control","no-cache, no-store");
		 * response.setHeader("Pragma","no-cache");
		 * response.setHeader("Expires","-1");
		 */
		FileInputStream fileInputStream = new FileInputStream(fullPath + "/WrittenScore.csv");

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.flush();
		out.close();
		/*
		 * } catch (Throwable e) { logger.fatal(e,e); }
		 */

	}

	public void downloadFieldTestTemplate() throws Exception {
		// logger.info("downloadWrittenTestTemplate");
		/* try{ */
		ServletContext context = ServletActionContext.getServletContext();
		String fullPath = context.getRealPath("/uploads/");
		PrintWriter out = response.getWriter();
		response.setHeader("Content-Disposition", "attachment; filename=FieldScore.csv");
		/*
		 * response.setHeader("Cache-control","no-cache, no-store");
		 * response.setHeader("Pragma","no-cache");
		 * response.setHeader("Expires","-1");
		 */
		FileInputStream fileInputStream = new FileInputStream(fullPath + "/FieldScore.csv");

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.flush();
		out.close();
		/*
		 * } catch (Throwable e) { logger.fatal(e,e); }
		 */

	}

	public void downloadFieldTestScheduleTemplate() throws Exception {
		// logger.info("downloadFieldTestScheduleTemplate");
		/* try{ */
		ServletContext context = ServletActionContext.getServletContext();
		String fullPath = context.getRealPath("/uploads/");
		PrintWriter out = response.getWriter();
		response.setHeader("Content-Disposition", "attachment; filename=FieldSchedule.csv");
		/*
		 * response.setHeader("Cache-control","no-cache, no-store");
		 * response.setHeader("Pragma","no-cache");
		 * response.setHeader("Expires","-1");
		 */
		FileInputStream fileInputStream = new FileInputStream(fullPath + "/FieldSchedule.csv");

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.flush();
		out.close();
		/*
		 * } catch (Throwable e) { logger.fatal(e,e); }
		 */

	}

	public void downloadInterviewTemplate() throws Exception {
		// logger.info("downloadWrittenTestTemplate");
		/* try{ */
		ServletContext context = ServletActionContext.getServletContext();
		String fullPath = context.getRealPath("/uploads/");
		PrintWriter out = response.getWriter();
		response.setHeader("Content-Disposition", "attachment; filename=InterviewScore.csv");
		/*
		 * response.setHeader("Cache-control","no-cache, no-store");
		 * response.setHeader("Pragma","no-cache");
		 * response.setHeader("Expires","-1");
		 */
		FileInputStream fileInputStream = new FileInputStream(fullPath + "/InterviewScore.csv");

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.flush();
		out.close();
		/*
		 * } catch (Throwable e) { logger.fatal(e,e); }
		 */

	}

	public void downloadMedicalTemplate() throws Exception {
		// logger.info("downloadWrittenTestTemplate");
		/* try{ */
		ServletContext context = ServletActionContext.getServletContext();
		String fullPath = context.getRealPath("/uploads/");
		PrintWriter out = response.getWriter();
		response.setHeader("Content-Disposition", "attachment; filename=MedicalScore.csv");
		/*
		 * response.setHeader("Cache-control","no-cache, no-store");
		 * response.setHeader("Pragma","no-cache");
		 * response.setHeader("Expires","-1");
		 */
		FileInputStream fileInputStream = new FileInputStream(fullPath + "/MedicalScore.csv");

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.flush();
		out.close();
		/*
		 * } catch (Throwable e) { logger.fatal(e,e); }
		 */

	}

	public void downloadInterviewScheduleTemplate() throws Exception {
		// logger.info("downloadWrittenTestTemplate");
		/* try{ */
		ServletContext context = ServletActionContext.getServletContext();
		String fullPath = context.getRealPath("/uploads/");
		PrintWriter out = response.getWriter();
		response.setHeader("Content-Disposition", "attachment; filename=InterviewSchedule.csv");
		/*
		 * response.setHeader("Cache-control","no-cache, no-store");
		 * response.setHeader("Pragma","no-cache");
		 * response.setHeader("Expires","-1");
		 */
		FileInputStream fileInputStream = new FileInputStream(fullPath + "/InterviewSchedule.csv");

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.flush();
		out.close();
		/*
		 * } catch (Throwable e) { logger.fatal(e,e); }
		 */

	}

	public void downloadMedicalScheduleTemplate() throws Exception {
		// logger.info("downloadWrittenTestTemplate");
		/* try{ */
		ServletContext context = ServletActionContext.getServletContext();
		String fullPath = context.getRealPath("/uploads/");
		PrintWriter out = response.getWriter();
		response.setHeader("Content-Disposition", "attachment; filename=MedicalSchedule.csv");
		/*
		 * response.setHeader("Cache-control","no-cache, no-store");
		 * response.setHeader("Pragma","no-cache");
		 * response.setHeader("Expires","-1");
		 */

		FileInputStream fileInputStream = new FileInputStream(fullPath + "/MedicalSchedule.csv");

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.flush();
		out.close();
		/*
		 * } catch (Throwable e) { logger.fatal(e,e); }
		 */

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
