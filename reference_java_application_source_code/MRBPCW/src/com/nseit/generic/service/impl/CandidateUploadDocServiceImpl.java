package com.nseit.generic.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.nseit.generic.aws.util.UploadDocInS3sdkV2;
import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.dao.CandidateEducationDao;
import com.nseit.generic.dao.CandidatePersonalDetailsDAO;
import com.nseit.generic.dao.CandidateUploadDocDao;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.dao.WorkExperienceDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDocBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidateUploadDocService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.LogicError;

public class CandidateUploadDocServiceImpl implements CandidateUploadDocService {

	private CandidateDao candidateDao;
	private CommonDao commonDao;
	private CandidateUploadDocDao candidateUploadDocDao;
	private CandidateEducationDao candidateEducationDao;
	private WorkExperienceDao workExperienceDao;
	private CandidatePersonalDetailsDAO candidatePersonalDetailsDAO;

	private Logger logger = LoggerHome.getLogger(getClass());

	public void setCandidateDao(CandidateDao candidateDAO) {
		this.candidateDao = candidateDAO;
	}

	public void setCandidateUploadDocDao(CandidateUploadDocDao candidateUploadDocDao) {
		this.candidateUploadDocDao = candidateUploadDocDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void setCandidateEducationDao(CandidateEducationDao candidateEducationDao) {
		this.candidateEducationDao = candidateEducationDao;
	}

	public void setCandidatePersonalDetailsDAO(CandidatePersonalDetailsDAO candidatePersonalDetailsDAO) {
		this.candidatePersonalDetailsDAO = candidatePersonalDetailsDAO;
	}

	public void setWorkExperienceDao(WorkExperienceDao workExperienceDao) {
		this.workExperienceDao = workExperienceDao;
	}

	@Override
	public String showUploadDocument(Users loggedInUser, CandidateBean candidateBean, Map<String, Object> sessionAttributes, HttpServletRequest request) throws Exception {

		String result = "getUploadManagement";
		Users users = candidateDao.getBasicInfoForUploadDoc(loggedInUser); //for personal details
		List<Integer> acdmSelection = candidateUploadDocDao.getEducationSelectionList(loggedInUser);
		List<EducationDetailsBean > educationDetaiBeanList = candidateUploadDocDao.getEducationBeanList(loggedInUser);// for education

		String exserviceman="";
		String community="";
		String ugDeg="";
		String pgDeg="";
		String pgDiploma="";
		String pstmCerti="";
		String widowYesNo="";
		
		for (int i = 0; i < educationDetaiBeanList.size(); i++) {
			if (educationDetaiBeanList.get(i).getExamination().equals("4") && "6".equals(educationDetaiBeanList.get(i).getUgDeg()))
				ugDeg = "true";
			
			if (educationDetaiBeanList.get(i).getExamination().equals("5") && "6".equals(educationDetaiBeanList.get(i).getPgDeg()))
				pgDeg = "true";
			
			if (educationDetaiBeanList.get(i).getExamination().equals("5") && "6".equals(educationDetaiBeanList.get(i).getPgDipYesNo()))
				pgDiploma = "true";
			
			if (educationDetaiBeanList.get(i).getExamination().equals("7") && "6".equals(educationDetaiBeanList.get(i).getUgTamilMedium()))
				pstmCerti = "true";
		}
		
		if (StringUtils.isNotBlank(users.getExServiceMen())) {
			if ("6".equals(users.getExServiceMen()))
				exserviceman = "true";
		}
		if (StringUtils.isNotBlank(users.getWidowYesNo())) {
			if ("6".equals(users.getWidowYesNo()))
				widowYesNo = "true";
		}
		if (StringUtils.isNotBlank(users.getCommunity())) {
			if (Arrays.asList("1", "2", "3", "4", "5", "6").contains(users.getCommunity()))
				community = "true";
		}

		// for delete doc as per Candidate Selections
		List<String> docDeleteList = new ArrayList<String>();

		StringBuilder enableContinue = new StringBuilder();
		// SignUp & Personal
		if ("true".equals(exserviceman)) {
			enableContinue.append(',');
			enableContinue.append("'ExSM'");
		} else {
			docDeleteList.add("ExSM");
		}
		
		if ("true".equals(widowYesNo)) {
			enableContinue.append(',');
			enableContinue.append("'DW'");
		} else {
			docDeleteList.add("DW");
		}

		if ("true".equals(community)) {
			enableContinue.append(',');
			enableContinue.append("'CC'");
		} else {
			docDeleteList.add("CC");
		}

		// Education Related below
		if (acdmSelection.contains(1)) {
			enableContinue.append(',');
			enableContinue.append("'SSLC'");
		} else {
			docDeleteList.add("SSLC");
		}
		
		if (acdmSelection.contains(2)) {
			enableContinue.append(',');
			enableContinue.append("'HSC'");
		} else {
			docDeleteList.add("HSC");
		}

		if (acdmSelection.contains(3)) {
			enableContinue.append(',');
			enableContinue.append("'DIP'");
		} else {
			docDeleteList.add("DIP");
		}

		if ("true".equals(ugDeg)) {
			enableContinue.append(',');
			enableContinue.append("'UG'");
		} else {
			docDeleteList.add("UG");
		}

		if ("true".equals(pgDeg)) {
			enableContinue.append(',');
			enableContinue.append("'PGDEG'");
		} else {
			docDeleteList.add("PGDEG");
		}

		if ("true".equals(pgDiploma)) {
			enableContinue.append(',');
			enableContinue.append("'PGDIP'");
		} else {
			docDeleteList.add("PGDIP");
		}

		if ("true".equals(pstmCerti)) {
			enableContinue.append(',');
			enableContinue.append("'PSTM'");
		} else {
			docDeleteList.add("PSTM");
		}

		enableContinue.toString();

		if (enableContinue != null && !enableContinue.equals("")) {
			char ch = enableContinue.charAt(0);
			if (ch == ',') {
				enableContinue.deleteCharAt(0);
			}
		}

		int delete = candidateUploadDocDao.deletePreviousDocumentsforAll(enableContinue.toString(), loggedInUser);
		int detDocCnt = 0;
		boolean dltFlag = false;
		String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
			delete = 0;
		}
		
		if (delete > 0) {
			// Delete Previous DocFile From Physical DOC Path
			// delete Exist file
			String documentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);

			File[] f = new File(documentPath + File.separator + loggedInUser.getUsername()).listFiles();
			// int fileList=f.length;
			File rmFldr = new File(documentPath + File.separator + loggedInUser.getUsername() + File.separator + "RemoveDoc");
			rmFldr.mkdir();
			for (File f1 : f) {
				for (String ocdFlagValue : docDeleteList) {
					if (f1.getName().startsWith(loggedInUser.getUsername() + "_" + ocdFlagValue + "_")) {
						FileUtils.copyFile(f1, new File(rmFldr + File.separator + f1.getName()));
						dltFlag = f1.delete();
						detDocCnt++;
						commonDao.insertCandidateAuditrail(loggedInUser, "Deleted Documents",
								"Candidate with User Id " + loggedInUser.getUsername() + " has deleted documents: - " + f1.getName());
						logger.info("Candidate with User Id " + loggedInUser.getUsername() + " has deleted document with name : - " + f1.getName());
					}
				}
			}
			logger.info("DB Doc Cnt :" + delete + "  Delete PhysicalDoc Cnt :" + detDocCnt + " For USER ID :" + loggedInUser.getUsername());
		}

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("global.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.putAll(ConfigurationConstants.getInstance().getGetLabelMap());
		List<CandidateBean> uploadList = new ArrayList<CandidateBean>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			CandidateBean c1 = new CandidateBean();
			
			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("Ex-servicemen Certificate") && "true".equals(exserviceman)) {
				c1.setDocLabel1(entry.getValue());// + properties.getProperty("x"));
				c1.setOcdFlagValue(entry.getKey());
				c1.setDocLabel2(entry.getValue());
			}
			
			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("Destitute Widow Certificate") && "true".equals(widowYesNo)) {
				c1.setDocLabel1(entry.getValue());// + properties.getProperty("x"));
				c1.setOcdFlagValue(entry.getKey());
				c1.setDocLabel2(entry.getValue());
			}
			
			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("Community Certificate") && "true".equals(community)) {
				c1.setDocLabel1(entry.getValue());// + properties.getProperty("x"));
				c1.setOcdFlagValue(entry.getKey());
				c1.setDocLabel2(entry.getValue());
			}

			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("SSLC Certificate") && acdmSelection.contains(1)) {
				c1.setDocLabel1(entry.getValue());// + properties.getProperty("x"));
				c1.setOcdFlagValue(entry.getKey());
				c1.setDocLabel2(entry.getValue());
			}
			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("HSC Certificate") && acdmSelection.contains(2)) {
				c1.setDocLabel1(entry.getValue());// + properties.getProperty("xii"));
				c1.setOcdFlagValue(entry.getKey());
				c1.setDocLabel2(entry.getValue());
			}
			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("Diploma Certificate") && acdmSelection.contains(3)) {
				c1.setDocLabel1(entry.getValue());// + properties.getProperty("xii"));
				c1.setOcdFlagValue(entry.getKey());
				c1.setDocLabel2(entry.getValue());
			}
			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("UG Degree Certificate") && ("true".equals(ugDeg))) {
				c1.setDocLabel1(entry.getValue());// + properties.getProperty("ug"));
				c1.setOcdFlagValue(entry.getKey());
				c1.setDocLabel2(entry.getValue());
			}
		
			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("PG Degree Certificate") && ("true".equals(pgDeg))) {
				c1.setDocLabel1(entry.getValue());// + properties.getProperty("nativity"));
				c1.setOcdFlagValue(entry.getKey());
				c1.setDocLabel2(entry.getValue());
			}
			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("PG Diploma Certificate") && ("true".equals(pgDiploma))) {
				c1.setDocLabel1(entry.getValue());// + properties.getProperty("nativity"));
				c1.setOcdFlagValue(entry.getKey());
				c1.setDocLabel2(entry.getValue());
			}
			
			if (entry.getValue() != null && entry.getValue().equalsIgnoreCase("PSTM Certificate (1st to 12th Standard & Diploma)") && ("true".equals(pstmCerti))) {
				c1.setDocLabel1(entry.getValue());// + properties.getProperty("nativity"));
				c1.setOcdFlagValue(entry.getKey());
				c1.setDocLabel2(entry.getValue());
			}

			if (c1.getDocLabel1() != null) {
				uploadList.add(c1);
			}
		}

		candidateBean.setUploadList(uploadList);
		getDocumentUploaded(loggedInUser);
		// Get DOC Data From DB;
		List<CandidateDocBean> candidateDocBeans = candidateUploadDocDao.getDocumentUploaded(loggedInUser);

		if (candidateDocBeans != null && candidateDocBeans.size() > 0) {
			for (CandidateDocBean candidateDocBean : candidateDocBeans) {
				for (CandidateBean c1 : candidateBean.getUploadList()) {

					if (candidateDocBean.getOCD_FLAG() != null && candidateDocBean.getOCD_FLAG().equals(c1.getOcdFlagValue())) {
						c1.setDocumentUploaded1(true);
						c1.setDocumentFileName1(candidateDocBean.getOCD_DOC_FILE_NAME());
						c1.setCandidateDocPk1(candidateDocBean.getOCD_CAND_DOC_PK());
						c1.setCheckCandidateAcceptance1(candidateDocBean.getOCD_CHECKBOX());
					}
				}
			}
		}
		// enable continue button to candidate go further
		if (candidateDocBeans != null && candidateDocBeans.size() > 0) {
			int docCountInDB = candidateDocBeans.size();
			if (uploadList != null) {
				if (docCountInDB <= uploadList.size()) {
					candidateBean.setDataFound(true);
				}
			}
		}
		candidateBean.setUserId(String.valueOf(loggedInUser.getUserId()));

		if (request.getAttribute("menuKey") == null) {
			HttpSession session = request.getSession();
			request.setAttribute("menuKey", "2.70");
			session.setAttribute("menuKey", "2.70");
		}
		
		if (request.getAttribute("menuKey") != null) {
			String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
			if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
				candidateBean.setDocumentMandatory(true);
			}
		}

		// Doc End
		return result;
	}

	@Override
	public CandidateUploadDocConstants saveUploadDocument(Users loggedInUser, CandidateBean candidateBean, Map<String, Object> sessionAttributes, HttpServletRequest request)
			throws Exception {
		String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
		String saveDataHash;
		int recordCount = 0;
		String docId = "";
		String docName = null;
		String flagValue = candidateBean.getOcdFlagValue();

		String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
			if (candidateBean.getData1() != null && candidateBean.getData1().length() > 0) {
				UploadDocInS3sdkV2 uploadDocInS3sdkV2 = new UploadDocInS3sdkV2();
				uploadDocInS3sdkV2.uploadDocInCandFolderInS3(loggedInUser, candidateBean);
				saveDataHash = uploadDocInS3sdkV2.getDocMdPathFromCandFolderInS3(loggedInUser, candidateBean);
				if (saveDataHash != null && !"".equals(saveDataHash) && saveDataHash.equals(candidateBean.getData1Hash())) {
					boolean isUserExist = candidateUploadDocDao.isUserDocExists(loggedInUser, candidateBean);
					if (isUserExist) {
						String candidateDocPK = null;
						if (candidateBean.getCandidateDocPk() != null) {
							candidateDocPK = candidateBean.getCandidateDocPk();
						} else if (candidateBean.getCandidateDocPk1() != null) {
							candidateDocPK = candidateBean.getCandidateDocPk1();
						} else if (candidateBean.getCandidateDocPk2() != null) {
							candidateDocPK = candidateBean.getCandidateDocPk2();
						}
						// sending bytearray of document as null into db[start]
						try {
							recordCount = candidateUploadDocDao.updateCandidateDocuments(candidateDocPK, docId, candidateBean.getData1FileName(), null,
									candidateBean.getCheckCandidateAcceptance1(), loggedInUser);
						} catch (Exception e) {
							// Delete Doc File From Physical DocPath
							String candidateFolder = loggedInUser.getUsername();
							uploadDocInS3sdkV2.rmDocFromCandFolderInS3(loggedInUser, candidateBean);
							logger.error(e.getMessage());
							throw new LogicError("Error ocurred while saving Uploading Doc File details. For User ID :" + candidateFolder);
						}
					} else {
						try {
							recordCount = candidateUploadDocDao.insertCandidateDocuments(candidateBean.getData1FileName(), docId, null, candidateBean.getOcdFlagValue(),
									candidateBean.getCheckCandidateAcceptance1(), loggedInUser);
						} catch (Exception e) {
							// Delete Doc File From Physical DocPath
							String candidateFolder = loggedInUser.getUsername();
							uploadDocInS3sdkV2.rmDocFromCandFolderInS3(loggedInUser, candidateBean);
							logger.error(e.getMessage());
							throw new LogicError("Error ocurred while saving Uploading Doc File details. For User ID :" + candidateFolder);
						}
						// sending bytearray of document as null into db[end]
					}
					if (recordCount > 0) {
						ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(loggedInUser.getUsername() + ", " + request.getRemoteAddr()
								+ ", Uploaded Document , for " + flagValue + " " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));

						loggedInUser.setRemoteIp(request.getRemoteAddr());
						/*commonDao.insertCandidateAuditrail(loggedInUser, "Upload",
								"Candidate with User Id " + loggedInUser.getUsername() + " has uploaded Certificate for " + flagValue);*/

						// Successfuly Upload Doc
						return CandidateUploadDocConstants.SUCCESS;
					}

				} else if (saveDataHash != null && !saveDataHash.equals("") && saveDataHash.equals("ER2: File upload failed.Kindly re-upload the file.")) {

					// Delete Doc File From Physical DocPath
					String candidateFolder = loggedInUser.getUsername();
//					File documentFolder = new File(DocumentPath + "" + candidateFolder);
//					File upldDocfile = new File(documentFolder + "\\" + candidateBean.getOcdFlagValue() + "_" + docName);
//					boolean isdltDoc = false;
//					if (upldDocfile.exists()) {
//						FileUtils.copyFile(upldDocfile, new File(rmFldr + File.separator + upldDocfile.getName()));
//						isdltDoc = upldDocfile.delete();
//					}

					uploadDocInS3sdkV2.rmDocFromCandFolderInS3(loggedInUser, candidateBean);
					return CandidateUploadDocConstants.FILE_UPLOAD_FAILE;

				} else if (saveDataHash != null && !saveDataHash.equals("") && !saveDataHash.equals(candidateBean.getData1Hash())) {

					// Delete Doc File From Physical DocPath
					String candidateFolder = loggedInUser.getUsername();
//					File documentFolder = new File(DocumentPath + "" + candidateFolder);
//					File upldDocfile = new File(documentFolder + "\\" + candidateBean.getOcdFlagValue() + "_" + docName);
//					boolean isdltDoc = false;
//					if (upldDocfile.exists()) {
//						FileUtils.copyFile(upldDocfile, new File(rmFldr + File.separator + upldDocfile.getName()));
//						isdltDoc = upldDocfile.delete();
//					}
					uploadDocInS3sdkV2.rmDocFromCandFolderInS3(loggedInUser, candidateBean);
					return CandidateUploadDocConstants.UPLOAD_DOC_FAIL;

				}
			} else {
				return CandidateUploadDocConstants.UPLOAD_DOC_FAIL;
			}
			// Temp retn
			return CandidateUploadDocConstants.UPLOAD_DOC_FAIL;
		} else {
			// #################################################################################################################################
			// Saving Doc Logic Start
			if (candidateBean.getData1() != null && candidateBean.getData1().length() > 0) {
				byte[] byteArrFile = null;
				FileInputStream fi = null;
				ByteArrayOutputStream byteArrayOutputStream = null;

				try {
					File filePath = new File(candidateBean.getData1().getPath());
					fi = new FileInputStream(filePath);
					if (fi != null) {
						int bytesRead = 0;
						byte[] byteBuff = new byte[1000];
						byteArrayOutputStream = new ByteArrayOutputStream();
						while ((bytesRead = fi.read(byteBuff, 0, 1000)) != -1) {
							byteArrayOutputStream.write(byteBuff, 0, bytesRead);
						}
						byteArrFile = byteArrayOutputStream.toByteArray();
					}
				} finally {
					if (byteArrayOutputStream != null) {
						byteArrayOutputStream.close();
					}
					if (fi != null) {
						fi.close();
					}

				}
				docName = candidateBean.getData1FileName();
				StringBuffer sd = new StringBuffer();
				sd.append(docName + ",");
				candidateBean.setFile5(byteArrFile);

				// Delete Previous DocFile From Physical DOC Path
				// delete Exist file
				boolean dltFlag = false;
				File[] f = new File(DocumentPath + File.separator + loggedInUser.getUsername()).listFiles();
				// int fileList=f.length;
				int detDocCnt = 0;
				File rmFldr = new File(DocumentPath + File.separator + loggedInUser.getUsername() + File.separator + "RemoveDoc");
				rmFldr.mkdir();

				for (File f1 : f) {
					if (f1.getName().startsWith(loggedInUser.getUsername() + "_" + flagValue + "_")) {
						boolean fileFlag = new File(rmFldr + File.separator + f1.getName()).createNewFile();
						if (fileFlag) {
							FileUtils.copyFile(f1, new File(rmFldr + File.separator + f1.getName()));
						} else {
							if (new File(rmFldr + File.separator + f1.getName()).exists()) {
								FileUtils.copyFile(f1, new File(rmFldr + File.separator + f1.getName()));
							} else {
								FileUtils.copyFile(f1, new File(rmFldr + File.separator + f1.getName()));
							}
						}
						dltFlag = f1.delete();
						detDocCnt++;
						logger.info("For USER ID :" + loggedInUser.getUsername() + "  Delete PhysicalDoc File :" + f1.getName());
					}
				}

				// ------------------------------------------------------
				// Save Doc File in Physical DOC Path
				saveDataHash = saveFileonLocal(docName, candidateBean.getFile5(), candidateBean.getOcdFlagValue(), loggedInUser);

				String path = "";
				if (saveDataHash != null && !saveDataHash.equals("")) {
					path = saveDataHash;
				}
				if (path != null && !path.equals("") && path.equals(candidateBean.getData1Hash())) {
					boolean isUserExist = candidateUploadDocDao.isUserDocExists(loggedInUser, candidateBean);
					if (isUserExist) {
						String candidateDocPK = null;
						if (candidateBean.getCandidateDocPk() != null) {
							candidateDocPK = candidateBean.getCandidateDocPk();
						} else if (candidateBean.getCandidateDocPk1() != null) {
							candidateDocPK = candidateBean.getCandidateDocPk1();
						} else if (candidateBean.getCandidateDocPk2() != null) {
							candidateDocPK = candidateBean.getCandidateDocPk2();
						}
						// sending bytearray of document as null into db[start]
						try {

							recordCount = candidateUploadDocDao.updateCandidateDocuments(candidateDocPK, docId, candidateBean.getData1FileName(), null,
									candidateBean.getCheckCandidateAcceptance1(), loggedInUser);
						} catch (Exception e) {
							// Delete Doc File From Physical DocPath
							String candidateFolder = loggedInUser.getUsername();
							File documentFolder = new File(DocumentPath + "" + candidateFolder);
							File upldDocfile = new File(documentFolder + File.separator + candidateFolder + "_" + candidateBean.getOcdFlagValue() + "_" + docName);
							boolean isdltDoc = false;
							if (upldDocfile.exists()) {
								FileUtils.copyFile(upldDocfile, new File(rmFldr + File.separator + upldDocfile.getName()));
								isdltDoc = upldDocfile.delete();
							}
							logger.error(e.getMessage());
							throw new LogicError("Error ocurred while saving Uploading Doc File details. For User ID :" + candidateFolder);

						}
					} else {
						try {
							recordCount = candidateUploadDocDao.insertCandidateDocuments(candidateBean.getData1FileName(), docId, null, candidateBean.getOcdFlagValue(),
									candidateBean.getCheckCandidateAcceptance1(), loggedInUser);

						} catch (Exception e) {
							// Delete Doc File From Physical DocPath
							String candidateFolder = loggedInUser.getUsername();
							File documentFolder = new File(DocumentPath + "" + candidateFolder);
							File upldDocfile = new File(documentFolder + File.separator + candidateFolder + "_" + candidateBean.getOcdFlagValue() + "_" + docName);
							boolean isdltDoc = false;
							if (upldDocfile.exists()) {
								FileUtils.copyFile(upldDocfile, new File(rmFldr + File.separator + upldDocfile.getName()));
								isdltDoc = upldDocfile.delete();
							}
							logger.error(e.getMessage());
							throw new LogicError("Error ocurred while saving Uploading Doc File details. For User ID :" + candidateFolder);
						}
						// sending bytearray of document as null into db[end]
					}
					if (recordCount > 0) {
						

						loggedInUser.setRemoteIp(request.getRemoteAddr());

						commonDao.insertCandidateAuditrail(loggedInUser, "Document upload Page  - Save & Continue",
								"Candidate with User Id " + loggedInUser.getUsername() + " : All docments uploaded successfully");

						// Successfuly Upload Doc
						return CandidateUploadDocConstants.SUCCESS;
					}

				} else if (path != null && !path.equals("") && path.equals("ER2: File upload failed.Kindly re-upload the file.")) {

					// Delete Doc File From Physical DocPath
					String candidateFolder = loggedInUser.getUsername();
					File documentFolder = new File(DocumentPath + "" + candidateFolder);
					File upldDocfile = new File(documentFolder + File.separator + candidateFolder + "_" + candidateBean.getOcdFlagValue() + "_" + docName);
					boolean isdltDoc = false;
					if (upldDocfile.exists()) {
						FileUtils.copyFile(upldDocfile, new File(rmFldr + File.separator + upldDocfile.getName()));
						isdltDoc = upldDocfile.delete();
					}
					return CandidateUploadDocConstants.FILE_UPLOAD_FAILE;

				} else if (path != null && !path.equals("") && !path.equals(candidateBean.getData1Hash())) {
					// Delete Doc File From Physical DocPath
					String candidateFolder = loggedInUser.getUsername();
					File documentFolder = new File(DocumentPath + "" + candidateFolder);
					File upldDocfile = new File(documentFolder + File.separator + candidateFolder + "_" + candidateBean.getOcdFlagValue() + "_" + docName);
					boolean isdltDoc = false;
					if (upldDocfile.exists()) {
						FileUtils.copyFile(upldDocfile, new File(rmFldr + File.separator + upldDocfile.getName()));
						isdltDoc = upldDocfile.delete();
					}
					return CandidateUploadDocConstants.UPLOAD_DOC_FAIL;
				}
			} else {
				// For Upload DocData not found
				return CandidateUploadDocConstants.UPLOAD_DOC_FAIL;
			}

			// Temp retn
			return CandidateUploadDocConstants.UPLOAD_DOC_FAIL;
		}

	}

	public String saveFileonLocal(String docName, byte[] uploadFile, String flag, Users loggedInUser) throws Exception {
		// saving candidate documents in folder
		String path = "";
		String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
		File DocumentPathFile = new File(DocumentPath);
		if (!DocumentPathFile.exists()) {
			DocumentPathFile.mkdir();
		}
		if (DocumentPathFile.exists()) {
			String candidateFolder = loggedInUser.getUsername();
			File candidateFolderFile = new File(DocumentPath + "" + candidateFolder);
			if (!candidateFolderFile.exists()) {
				candidateFolderFile.mkdir();
			}
			if (candidateFolderFile.exists()) {

				File documentFolder = new File(DocumentPath + "" + candidateFolder);
				if (!documentFolder.exists()) {
					documentFolder.mkdir();
				}
				if (documentFolder.exists()) {
					if (docName != null) {
						int BUFFER_SIZE = 1024;
						int bytesRead = -1;
						byte[] imageInByte = null;
						imageInByte = uploadFile;
						if (imageInByte != null) {
							if (docName.toLowerCase().endsWith(".jpg")) {
								ByteArrayInputStream in = new ByteArrayInputStream(imageInByte);
								FileOutputStream out = null;
								try {
									out = new FileOutputStream(new File(documentFolder + File.separator + loggedInUser.getUsername() + "_" + flag + "_" + docName));
								} catch (FileNotFoundException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								try {
									IOUtils.copy(in, out);
								} catch (IOException e) {
									e.printStackTrace();
								}
								IOUtils.closeQuietly(in);
								IOUtils.closeQuietly(out);

								MessageDigest md = null;
								try {
									md = MessageDigest.getInstance("MD5");
								} catch (NoSuchAlgorithmException e) {
									e.printStackTrace();
								}
								FileInputStream fis = null;
								try {
									fis = new FileInputStream(documentFolder + File.separator + loggedInUser.getUsername() + "_" + flag + "_" + docName);
								} catch (FileNotFoundException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								byte[] dataBytes = new byte[1024];
								int nread = 0;
								try {
									if (fis != null) {
										while ((nread = fis.read(dataBytes)) != -1) {
											md.update(dataBytes, 0, nread);
										}
									}

								} catch (IOException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								;
								if (fis != null) {
									byte[] mdbytes = md.digest();
									StringBuffer sb = new StringBuffer();
									for (int i = 0; i < mdbytes.length; i++) {
										sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
									}
									path = sb.toString();
									try {
										fis.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}

							if (docName.toLowerCase().endsWith(".jpeg")) {
								ByteArrayInputStream in = new ByteArrayInputStream(imageInByte);
								FileOutputStream out = null;
								try {
									out = new FileOutputStream(new File(documentFolder + File.separator + loggedInUser.getUsername() + "_" + flag + "_" + docName));
								} catch (FileNotFoundException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								try {
									IOUtils.copy(in, out);
								} catch (IOException e) {
									e.printStackTrace();
								}
								IOUtils.closeQuietly(in);
								IOUtils.closeQuietly(out);

								MessageDigest md = null;
								try {
									md = MessageDigest.getInstance("MD5");
								} catch (NoSuchAlgorithmException e) {
									e.printStackTrace();
								}
								FileInputStream fis = null;
								try {
									fis = new FileInputStream(documentFolder + File.separator + loggedInUser.getUsername() + "_" + flag + "_" + docName);
								} catch (FileNotFoundException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								byte[] dataBytes = new byte[1024];
								int nread = 0;
								try {
									if (fis != null) {
										while ((nread = fis.read(dataBytes)) != -1) {
											md.update(dataBytes, 0, nread);
										}
									}
								} catch (IOException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								;
								if (fis != null) {
									byte[] mdbytes = md.digest();
									StringBuffer sb = new StringBuffer();
									for (int i = 0; i < mdbytes.length; i++) {
										sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
									}
									path = sb.toString();
									try {
										fis.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
							if (docName.toLowerCase().endsWith(".png")) {
								ByteArrayInputStream in = new ByteArrayInputStream(imageInByte);
								FileOutputStream out = null;
								try {
									out = new FileOutputStream(new File(documentFolder + File.separator + loggedInUser.getUsername() + "_" + flag + "_" + docName));
								} catch (FileNotFoundException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								try {
									IOUtils.copy(in, out);
								} catch (IOException e) {
									e.printStackTrace();
								}
								IOUtils.closeQuietly(in);
								IOUtils.closeQuietly(out);

								MessageDigest md = null;
								try {
									md = MessageDigest.getInstance("MD5");
								} catch (NoSuchAlgorithmException e) {
									e.printStackTrace();
								}
								FileInputStream fis = null;
								try {
									fis = new FileInputStream(documentFolder + File.separator + loggedInUser.getUsername() + "_" + flag + "_" + docName);
								} catch (FileNotFoundException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								byte[] dataBytes = new byte[1024];
								int nread = 0;
								try {
									if (fis != null) {
										while ((nread = fis.read(dataBytes)) != -1) {
											md.update(dataBytes, 0, nread);
										}
									}
								} catch (IOException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								;
								if (fis != null) {
									byte[] mdbytes = md.digest();
									StringBuffer sb = new StringBuffer();
									for (int i = 0; i < mdbytes.length; i++) {
										sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
									}
									path = sb.toString();
									try {
										fis.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
							if (docName.toLowerCase().endsWith(".pdf")) {
								InputStream in = new ByteArrayInputStream(imageInByte);
								java.io.OutputStream outputstream = null;
								try {
									outputstream = new FileOutputStream(documentFolder + File.separator + loggedInUser.getUsername() + "_" + flag + "_" + docName);
								} catch (FileNotFoundException e1) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								byte[] buffer = new byte[BUFFER_SIZE];
								try {
									while ((bytesRead = in.read(buffer)) != -1) {
										outputstream.write(buffer, 0, bytesRead);
									}
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								try {
									in.close();
									outputstream.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								MessageDigest md = null;
								try {
									md = MessageDigest.getInstance("MD5");
								} catch (NoSuchAlgorithmException e) {
									e.printStackTrace();
								}
								FileInputStream fis = null;
								try {
									fis = new FileInputStream(documentFolder + File.separator + loggedInUser.getUsername() + "_" + flag + "_" + docName);
								} catch (FileNotFoundException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								byte[] dataBytes = new byte[1024];
								int nread = 0;
								try {
									if (fis != null) {
										while ((nread = fis.read(dataBytes)) != -1) {
											md.update(dataBytes, 0, nread);
										}
									}
								} catch (IOException e) {
									path = "ER2: File upload failed.Kindly re-upload the file.";
								}
								;
								if (fis != null) {
									byte[] mdbytes = md.digest();
									StringBuffer sb = new StringBuffer();
									for (int i = 0; i < mdbytes.length; i++) {
										sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
									}
									path = sb.toString();
									try {
										fis.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}
		return path;
	}

	// TODO 1: Danger : below method is to check that is doc is present on physical path or not even if present in DB
	@Override
	public String getDocumentUploaded(Users loggedInUser) throws Exception {
		List<CandidateDocBean> candidateDocBeanList = candidateUploadDocDao.getDocumentUploaded(loggedInUser);
		for (CandidateDocBean candidateDocBean : candidateDocBeanList) {
			if (!isFilePresent(candidateDocBean, loggedInUser)) {
				int deleteSingleDoc = candidateUploadDocDao.deleteDocMissingOnPhysical(candidateDocBean.getOCD_FLAG(), loggedInUser);
			}
		}
		return "true";
	}

	private boolean isFilePresent(CandidateDocBean candidateDocBean, Users loggedInUser) {
		String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
			UploadDocInS3sdkV2 uploadDocInS3sdkV2 = new UploadDocInS3sdkV2();
			return uploadDocInS3sdkV2.isDocFilePresentInCandFolderOfS3(loggedInUser, candidateDocBean);
		} else {
			String documentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
			String candidateFolder = loggedInUser.getUsername();
			String documentPrefix = candidateDocBean.getOCD_FLAG();
			String documentSuffix = candidateDocBean.getOCD_DOC_FILE_NAME();
			File filePath = new File(documentPath + "" + candidateFolder + File.separator + loggedInUser.getUsername() + "_" + documentPrefix + "_" + documentSuffix);
			if (filePath.exists()) {
				return true;
			}
			return false;
		}
	}

}
