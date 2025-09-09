package com.nseit.generic.service.impl;

import static com.nseit.generic.util.GenericConstants.GLOBAL_PLAIN_TEXT_MESSAGE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.nseit.generic.aws.util.UploadPhotoSignInS3sdkV2;
import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.dao.CandidatePhotoSignatureDao;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidatePhotoSignatureService;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.LogicError;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;

public class CandidatePhotoSignatureServiceImpl implements CandidatePhotoSignatureService {
	private CandidateDao candidateDao;
	private CandidatePhotoSignatureDao candidatePhotoSignatureDao;
	private CommonDao commonDao;
	private Logger logger = LoggerHome.getLogger(getClass());

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void setCandidatePhotoSignatureDao(CandidatePhotoSignatureDao candidatePhotoSignatureDao) {
		this.candidatePhotoSignatureDao = candidatePhotoSignatureDao;
	}

	public void setCandidateDao(CandidateDao candidateDao) {
		this.candidateDao = candidateDao;
	}

	@Override
	public CandidateSignatureServiceConstants getUploadedSignature(Users loggedInUser, CandidateBean candidateBean, HttpServletRequest request) throws Exception {
		Users users = candidateDao.getBasicCandidateInfo(loggedInUser);
		try {
			if(users != null) {
				candidateBean.getPersonalDetailsBean().setCandidateName(users.getFirstName());
				candidateBean.getPersonalDetailsBean().setDateOfBirth(users.getDate_Of_Birth());
				candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(users.getGender())));
			}else {
				logger.error("User got null for :"+ loggedInUser.getUsername());
			}
		}catch(Exception e) {
			logger.info("User not found or date of birth null for:" + loggedInUser.getUsername()+ e.getMessage());
		}
		candidateBean.setImageUploaded(checkIfSignatureUploaded(loggedInUser));

		// Added on 01/09/2012
		if (request.getAttribute("menuKey") != null) {
			String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
			if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
				candidateBean.setSignatureMandatory(true);
			}
		}
		return CandidateSignatureServiceConstants.UPLOAD_SIGNATURE;
	}

	@Override
	public CandidateSignatureServiceConstants getUploadedPhoto(Users loggedInUser, CandidateBean candidateBean, HttpServletRequest request) throws Exception {
		Users users = candidateDao.getBasicCandidateInfo(loggedInUser);
		try {
			if(users != null) {	
			candidateBean.getPersonalDetailsBean().setCandidateFirstName(users.getFirstName());
			candidateBean.getPersonalDetailsBean().setDateOfBirth(users.getDate_Of_Birth());
			candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(users.getGender())));
			}else {
				logger.error("User got null for :"+ loggedInUser.getUsername());
			}
		}catch(Exception e) {
			logger.info("User not found or date of birth null for:" + loggedInUser.getUsername()+ e.getMessage());
		}
		
		/*if(loggedInUser.getGender().equalsIgnoreCase("Male") || loggedInUser.getGender().equalsIgnoreCase("Female"))
		{
			candidateBean.setGenderValDesc(loggedInUser.getGender());
		}else
		candidateBean.setGenderValDesc(ConfigurationConstants.getInstance().getParameterValueForKey(Integer.parseInt(loggedInUser.getGender())));*/

		candidateBean.setImageUploaded(checkIfImageUploaded(loggedInUser));
		if (candidateBean.isImageUploaded()) {
			request.setAttribute("dataNotFound", "");
		}
		if (request.getAttribute("menuKey") != null) {
			String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
			if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
				candidateBean.setImageMandatory(true);
			}
		}
		return CandidateSignatureServiceConstants.UPLOAD_PHOTOGRAPH;
	}

	@Override
	public boolean checkIfImageUploaded(Users users) throws Exception {
		
		String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
			UploadPhotoSignInS3sdkV2 uploadPhotoSignInS3sdkV2 = new UploadPhotoSignInS3sdkV2();
			return uploadPhotoSignInS3sdkV2.isPhotoSignaturePresentInS3(users, "_photo.jpg");
		} else {
			return candidatePhotoSignatureDao.checkIfImageUploaded(users);
		}
	}

	public boolean checkIfSignatureUploaded(Users users) throws Exception {
		
		String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {
			UploadPhotoSignInS3sdkV2 uploadPhotoSignInS3sdkV2 = new UploadPhotoSignInS3sdkV2();
			return uploadPhotoSignInS3sdkV2.isPhotoSignaturePresentInS3(users, "_sign.jpg");
		}else {
			return candidatePhotoSignatureDao.checkIfSignatureUploaded(users);
		}
	}

	@Override
	public CandidateSignatureServiceConstants saveCandidateSignature(Users loggedInUser, CandidateBean candidateBean, Map<String, Object> requestAttributes,
			HttpServletRequest request) throws Exception {

		String pathToSaveDocument = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);// D:\Documents_and_Photo\
		String path = "";
		String message = "";
		int candidateImage = 0;
		String errorMessage = null;
		String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {

			UploadPhotoSignInS3sdkV2 uploadPhotoSignInS3sdkV2 = new UploadPhotoSignInS3sdkV2();
			uploadPhotoSignInS3sdkV2.uploadPhotoSignInCandFolderInS3(loggedInUser, candidateBean, "_sign.jpg");
			path = uploadPhotoSignInS3sdkV2.getPhotoSignMdPathFromCandFolderInS3(loggedInUser, candidateBean, "_sign.jpg");

			if (path != null && !Strings.isEmpty(path) && path.equals(candidateBean.getAttachmentSignatureHash())) {
				try {
					candidateImage = insertCandidateSignature(candidateBean, loggedInUser);
				} catch (Exception e) {
					// Delete Sign file from Physical documentFolder
					uploadPhotoSignInS3sdkV2.rmPhotoSignFromCandFolderInS3(loggedInUser, candidateBean, "_sign.jpg");
					logger.error(e.getLocalizedMessage());
					throw new LogicError("Error ocurred while saving Uploading Image details.");
				}
				if (candidateImage == 0) {
					message = "(message>1,Error Uploading Image.(/message)";
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, message);
				} else {
					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
							.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Upload Photo  , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					loggedInUser.setRemoteIp(request.getRemoteAddr());
					/*commonDao.insertCandidateAuditrail(loggedInUser, "Upload", "Candidate with User Id " + loggedInUser.getUsername() + " has uploaded photo.");*/
					message = "(message)" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.IMAGE_UPLOAD_SUCCESS) + "(/message)";
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, message);
				}
				if (loggedInUser.getCandidateStatusId() == 5 || loggedInUser.getCandidateStatusId() == 20 || loggedInUser.getCandidateStatusId() == 19
						|| loggedInUser.getCandidateStatusId() == 10 || loggedInUser.getCandidateStatusId() == 11) {
					loggedInUser.setCandidateStatusId(40);
					int updateStageAfterSubmit = commonDao.updateCandidateStatus(loggedInUser, 40);
				}
				return CandidateSignatureServiceConstants.SUCCESS;
			} else {
				if (request.getAttribute("menuKey") != null) {
					String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
					if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
						candidateBean.setImageMandatory(true);
					}
				}
				message = "(message)" + errorMessage + "(/message)";
				Users users = candidateDao.getBasicCandidateInfo(loggedInUser);
				candidateBean.getPersonalDetailsBean().setCandidateName(users.getFirstName());
				candidateBean.getPersonalDetailsBean().setDateOfBirth(users.getDate_Of_Birth());
				candidateBean.setGenderValDesc(users.getGender());
				candidateBean.setGenderVal(users.getGender());
				candidateBean.setImageUploaded(false);
//				String candidateFolder = loggedInUser.getUsername();
//				File documentFolder = new File(pathToSaveDocument + "" + candidateFolder);
//				File f = new File(documentFolder + "\\" + loggedInUser.getUsername() + "_photo.jpg");
//				f.delete();
				uploadPhotoSignInS3sdkV2.rmPhotoSignFromCandFolderInS3(loggedInUser, candidateBean, "_sign.jpg");
				return CandidateSignatureServiceConstants.FILE_UPLOAD_FAILE;
			}

		} else {
			// saving candidate image in folder
			// String DocumentPath =
			// ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
			File DocumentPathFile = new File(pathToSaveDocument);
			if (!DocumentPathFile.exists()) {
				DocumentPathFile.mkdir();
			}
			if (DocumentPathFile.exists()) {
				String candidateFolder = loggedInUser.getUsername();
				File candidateFolderFile = new File(pathToSaveDocument + "" + candidateFolder);
				if (!candidateFolderFile.exists()) {
					candidateFolderFile.mkdir();
				}
				if (candidateFolderFile.exists()) {
					
					try {
						File oldFile = new File(candidateFolderFile + File.separator + loggedInUser.getUsername() + "_sign.jpg");
						if (oldFile.exists()) {
							LocalDateTime now = LocalDateTime.now();
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
							String formattedDateTime = now.format(formatter);
							File rmFldr = new File(candidateFolderFile + File.separator + "RemoveDoc");
							if (!rmFldr.exists()) {
								rmFldr.mkdir();
							}
							boolean fileFlag = new File(rmFldr + File.separator + loggedInUser.getUsername() + "_sign" + "_" + formattedDateTime + ".jpg").createNewFile();
							if (fileFlag) {
								FileUtils.copyFile(oldFile, new File(rmFldr + File.separator + loggedInUser.getUsername() + "_sign" + "_" + formattedDateTime + ".jpg"));
							} else {
								if (new File(rmFldr + File.separator + loggedInUser.getUsername() + "_sign" + "_" + formattedDateTime + ".jpg").exists()) {
									FileUtils.copyFile(oldFile, new File(rmFldr + File.separator + loggedInUser.getUsername() + "_sign" + "_" + formattedDateTime + "_1" + ".jpg"));
								} else {
									FileUtils.copyFile(oldFile, new File(rmFldr + File.separator + loggedInUser.getUsername() + "_sign" + "_" + formattedDateTime + ".jpg"));
								}
							}
							// oldFile.delete();
						}
					} catch (Exception e) {
						logger.error("Skipping moving signature to Remove Doc due to below Exception");
						logger.error(e, e);
					}

					File documentFolder = new File(pathToSaveDocument + "" + candidateFolder);
					if (!documentFolder.exists()) {
						documentFolder.mkdir();
					}
					if (documentFolder.exists()) {
						byte[] imageInByte = null;
						FileInputStream fi = null;
						File photoPath = null;

						photoPath = new File(candidateBean.getAttachmentSignature().getPath());
						ByteArrayOutputStream byteArrayOutputStream = null;
						try {
							fi = new FileInputStream(photoPath);
							if (fi != null) {
								int bytesRead = 0;

								byte[] byteBuff = new byte[1000];
								byteArrayOutputStream = new ByteArrayOutputStream();

								while ((bytesRead = fi.read(byteBuff, 0, 1000)) != -1) {
									byteArrayOutputStream.write(byteBuff, 0, bytesRead);
								}

								imageInByte = byteArrayOutputStream.toByteArray();
							}
						} finally {
							if (byteArrayOutputStream != null) {
								byteArrayOutputStream.close();
							}
							if (fi != null) {
								fi.close();
							}
						}

						ByteArrayInputStream in = new ByteArrayInputStream(imageInByte);

						FileOutputStream out = new FileOutputStream(new File(documentFolder +File.separator + loggedInUser.getUsername() + "_sign.jpg"));
						IOUtils.copy(in, out);
						IOUtils.closeQuietly(in);
						IOUtils.closeQuietly(out);

						FileInputStream fis = null;

						try {
							MessageDigest md = null;

							md = MessageDigest.getInstance("MD5");

							fis = new FileInputStream(documentFolder + File.separator+ loggedInUser.getUsername() + "_sign.jpg");
							// fis = new FileInputStream(photoPath);

							byte[] dataBytes = new byte[1024];
							int nread = 0;

							if (fis != null) {
								while ((nread = fis.read(dataBytes)) != -1) {
									md.update(dataBytes, 0, nread);
								}
							}
							;
							if (fis != null) {
								byte[] mdbytes = md.digest();
								StringBuffer sb = new StringBuffer();
								for (int i = 0; i < mdbytes.length; i++) {
									sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
								}
								path = sb.toString();
								fis.close();
							}
						} finally {
							if (fis != null) {
								fis.close();
							}
						}
					}
				}
			}

			if (path != null && !path.equals("") && path.equals(candidateBean.getAttachmentSignatureHash())) {
				try {
					candidateImage = insertCandidateSignature(candidateBean, loggedInUser);
				} catch (Exception e) {
					// Delete sign file from Physical documentFolder
					boolean deltSign = false;
					File signFile = new File(pathToSaveDocument +File.separator+ loggedInUser.getUsername() + File.separator + loggedInUser.getUsername() + "_sign.jpg");
					if (signFile.exists()) {
						deltSign = signFile.delete();
					}
					logger.error(e.getMessage());
					throw new LogicError("Error ocurred while saving Uploading Sign details.");
				}

				if (candidateImage == 0) {
					message = "(message>1,Error Uploading Image.(/message)";
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, message);
				} else {
					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger().info(
							loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Upload Signature , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					loggedInUser.setRemoteIp(request.getRemoteAddr());
			//		commonDao.insertCandidateAuditrail(loggedInUser, "Upload", "Candidate with User Id " + loggedInUser.getUsername() + " has uploaded signature.");
					message = "(message)" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.IMAGE_UPLOAD_SUCCESS) + "(/message)";
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, message);
				}
				if (loggedInUser.getCandidateStatusId() == 5 || loggedInUser.getCandidateStatusId() == 20 || loggedInUser.getCandidateStatusId() == 19
						|| loggedInUser.getCandidateStatusId() == 10 || loggedInUser.getCandidateStatusId() == 11) {
					loggedInUser.setCandidateStatusId(40);
					int updateStageAfterSubmit = 0;
					updateStageAfterSubmit = commonDao.updateCandidateStatus(loggedInUser, 40);

				}
				// return getUploadedSignature(loggedInUser,candidateBean,request);
				return CandidateSignatureServiceConstants.SUCCESS;
			} else {
				if (request.getAttribute("menuKey") != null) {
					String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
					if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
						candidateBean.setSignatureMandatory(true);
					}
				}
				// message = "(message)" + errorMessage + "(/message)";
				// addActionMessage("ER1: File upload failed.Kindly re-upload the
				// file.");
				Users users = candidateDao.getBasicCandidateInfo(loggedInUser);
				candidateBean.getPersonalDetailsBean().setCandidateName(users.getFirstName());
				candidateBean.getPersonalDetailsBean().setDateOfBirth(users.getDate_Of_Birth());
				candidateBean.setGenderValDesc(users.getGender());
				candidateBean.setImageUploaded(false);
				String candidateFolder = loggedInUser.getUsername();
				File documentFolder = new File(pathToSaveDocument + "" + candidateFolder);
				File f = new File(documentFolder + File.separator + loggedInUser.getUsername() + "_sign.jpg");
				f.delete();
				// result = "showUploadSignature";
				return CandidateSignatureServiceConstants.FILE_UPLOAD_FAILE;
			}
		}
	}

	@Override
	public CandidateSignatureServiceConstants saveCandidatePhoto(Users loggedInUser, CandidateBean candidateBean, Map<String, Object> requestAttributes, HttpServletRequest request)
			throws Exception {

		String pathToSaveDocument = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);// D:\Documents_and_Photo\
		String path = "";
		String message = "";
		int candidateImage = 0;
		String errorMessage = null;
		// ####################################################################################################
		String strSaveInS3 = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		if (strSaveInS3 != null && !Strings.isBlank(strSaveInS3) && "true".equals(strSaveInS3.toLowerCase())) {

			UploadPhotoSignInS3sdkV2 uploadPhotoSignInS3sdkV2 = new UploadPhotoSignInS3sdkV2();
			uploadPhotoSignInS3sdkV2.uploadPhotoSignInCandFolderInS3(loggedInUser, candidateBean, "_photo.jpg");
			path = uploadPhotoSignInS3sdkV2.getPhotoSignMdPathFromCandFolderInS3(loggedInUser, candidateBean, "_photo.jpg");

			if (path != null && !"".equals(path) && path.equals(candidateBean.getAttachementPhotoHash())) {
				try {
					candidateImage = insertCandidateImages(candidateBean, loggedInUser);
				} catch (Exception e) {
					// Delete Photo file from Physical documentFolder
//					boolean deltPhoto = false;
					uploadPhotoSignInS3sdkV2.rmPhotoSignFromCandFolderInS3(loggedInUser, candidateBean, "_photo.jpg");
//					File photoFile = new File(pathToSaveDocument + "\\" + loggedInUser.getUsername() + "_photo.jpg");
//					if (photoFile.exists()) {
//						deltPhoto = photoFile.delete();
//					}
					logger.error(e.getMessage());
					throw new LogicError("Error ocurred while saving Uploading Image details.");
				}
				if (candidateImage == 0) {
					message = "(message>1,Error Uploading Image.(/message)";
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, message);
				} else {
					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
							.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Upload Photo  , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					loggedInUser.setRemoteIp(request.getRemoteAddr());
					/*commonDao.insertCandidateAuditrail(loggedInUser, "Upload", "Candidate with User Id " + loggedInUser.getUsername() + " has uploaded photo.");*/
					message = "(message)" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.IMAGE_UPLOAD_SUCCESS) + "(/message)";
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, message);
				}
				if (loggedInUser.getCandidateStatusId() == 5 || loggedInUser.getCandidateStatusId() == 20 || loggedInUser.getCandidateStatusId() == 19
						|| loggedInUser.getCandidateStatusId() == 10 || loggedInUser.getCandidateStatusId() == 11) {
					loggedInUser.setCandidateStatusId(40);
					int updateStageAfterSubmit = commonDao.updateCandidateStatus(loggedInUser, 40);
				}
				return CandidateSignatureServiceConstants.SUCCESS;
			} else {
				if (request.getAttribute("menuKey") != null) {
					String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
					if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
						candidateBean.setImageMandatory(true);
					}
				}
				message = "(message)" + errorMessage + "(/message)";
				Users users = candidateDao.getBasicCandidateInfo(loggedInUser);
				candidateBean.getPersonalDetailsBean().setCandidateName(users.getFirstName());
				candidateBean.getPersonalDetailsBean().setDateOfBirth(users.getDate_Of_Birth());
				candidateBean.setGenderValDesc(users.getGender());
				candidateBean.setGenderVal(users.getGender());
				candidateBean.setImageUploaded(false);
//				String candidateFolder = loggedInUser.getUsername();
//				File documentFolder = new File(pathToSaveDocument + "" + candidateFolder);
//				File f = new File(documentFolder + "\\" + loggedInUser.getUsername() + "_photo.jpg");
//				f.delete();
				uploadPhotoSignInS3sdkV2.rmPhotoSignFromCandFolderInS3(loggedInUser, candidateBean, "_photo.jpg");
				return CandidateSignatureServiceConstants.FILE_UPLOAD_FAILE;
			}
		} else {

			// ##########################################################
			File rootDirToSaveDocs = new File(pathToSaveDocument);
			if (!rootDirToSaveDocs.exists()) {
				rootDirToSaveDocs.mkdir();
			}
			if (rootDirToSaveDocs.exists()) {
				String username = loggedInUser.getUsername();
				File candDirToSaveDocs = new File(pathToSaveDocument + "" + username);
				if (!candDirToSaveDocs.exists()) {
					candDirToSaveDocs.mkdir();
				}
				if (candDirToSaveDocs.exists()) {
					
					// below code to move existing photo to remove doc
					try {
						File oldFile = new File(candDirToSaveDocs + File.separator + loggedInUser.getUsername() + "_photo.jpg");
						if (oldFile.exists()) {
							LocalDateTime now = LocalDateTime.now();
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
							String formattedDateTime = now.format(formatter);
							File rmFldr = new File(candDirToSaveDocs + File.separator + "RemoveDoc");
							if (!rmFldr.exists()) {
								rmFldr.mkdir();
							}
							boolean fileFlag = new File(rmFldr + File.separator + loggedInUser.getUsername() + "_photo" + "_" + formattedDateTime + ".jpg").createNewFile();
							if (fileFlag) {
								FileUtils.copyFile(oldFile, new File(rmFldr + File.separator + loggedInUser.getUsername() + "_photo" + "_" + formattedDateTime + ".jpg"));
							} else {
								if (new File(rmFldr + File.separator + loggedInUser.getUsername() + "_photo" + "_" + formattedDateTime + ".jpg").exists()) {
									FileUtils.copyFile(oldFile,
											new File(rmFldr + File.separator + loggedInUser.getUsername() + "_photo" + "_" + formattedDateTime + "_1" + ".jpg"));
								} else {
									FileUtils.copyFile(oldFile, new File(rmFldr + File.separator + loggedInUser.getUsername() + "_photo" + "_" + formattedDateTime + ".jpg"));
								}
							}
							// oldFile.delete();
						}
					} catch (Exception e) {
						logger.error("Skipping moving photo to Remove Doc due to below Exception");
						logger.error(e, e);
					}
					
					byte[] imageInByte = null;
					FileInputStream fileInputStream = null;
					ByteArrayOutputStream byteArrayOutputStream = null;
					try {
						File photoPath = new File(candidateBean.getAttachmentPhoto().getPath());
						fileInputStream = new FileInputStream(photoPath);
						if (fileInputStream != null) {
							int bytesRead = 0;
							byte[] byteBuff = new byte[1000];
							byteArrayOutputStream = new ByteArrayOutputStream();
							while ((bytesRead = fileInputStream.read(byteBuff, 0, 1000)) != -1) {
								byteArrayOutputStream.write(byteBuff, 0, bytesRead);
							}
							imageInByte = byteArrayOutputStream.toByteArray();
						}
					} finally {
						if (byteArrayOutputStream != null) {
							byteArrayOutputStream.close();
						}
						if (fileInputStream != null) {
							fileInputStream.close();
						}
					}

					ByteArrayInputStream in = new ByteArrayInputStream(imageInByte);
					FileOutputStream out = new FileOutputStream(new File(candDirToSaveDocs + File.separator + loggedInUser.getUsername() + "_photo.jpg"));
					IOUtils.copy(in, out); // Save file in physical path
					IOUtils.closeQuietly(in);
					IOUtils.closeQuietly(out);

					FileInputStream fis = null;
					try {

						MessageDigest md = MessageDigest.getInstance("MD5");
						fis = new FileInputStream(candDirToSaveDocs + File.separator + loggedInUser.getUsername() + "_photo.jpg");
						byte[] dataBytes = new byte[1024];
						int nread = 0;
						if (fis != null) {
							while ((nread = fis.read(dataBytes)) != -1) {
								md.update(dataBytes, 0, nread);
							}
						}
						if (fis != null) {
							byte[] mdbytes = md.digest();
							StringBuffer sb = new StringBuffer();
							for (int i = 0; i < mdbytes.length; i++) {
								sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
							}
							path = sb.toString();
						}
					} finally {
						if (fis != null) {
							fis.close();
						}
					}
				}
			}

			if (path != null && !"".equals(path) && path.equals(candidateBean.getAttachementPhotoHash())) {
				try {
					candidateImage = insertCandidateImages(candidateBean, loggedInUser);
				} catch (Exception e) {
					// Delete Photo file from Physical documentFolder
					boolean deltPhoto = false;
					File photoFile = new File(pathToSaveDocument + File.separator + loggedInUser.getUsername() + "_photo.jpg");
					if (photoFile.exists()) {
						deltPhoto = photoFile.delete();
					}
					logger.error(e.getMessage());
					throw new LogicError("Error ocurred while saving Uploading Image details.");
				}
				if (candidateImage == 0) {
					message = "(message>1,Error Uploading Image.(/message)";
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, message);
				} else {
					ConfigurationConstants.getInstance().getCandidateActivityAuditTrailLogger()
							.info(loggedInUser.getUsername() + ", " + request.getRemoteAddr() + ", Upload Photo  , " + CommonUtil.formatDate(new Date(), "dd-MMM-yyyy HH:mm:ss"));
					loggedInUser.setRemoteIp(request.getRemoteAddr());
					/*commonDao.insertCandidateAuditrail(loggedInUser, "Upload", "Candidate with User Id " + loggedInUser.getUsername() + " has uploaded photo.");*/
					message = "(message)" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.IMAGE_UPLOAD_SUCCESS) + "(/message)";
					request.setAttribute(GLOBAL_PLAIN_TEXT_MESSAGE, message);
				}
				if (loggedInUser.getCandidateStatusId() == 5 || loggedInUser.getCandidateStatusId() == 20 || loggedInUser.getCandidateStatusId() == 19
						|| loggedInUser.getCandidateStatusId() == 10 || loggedInUser.getCandidateStatusId() == 11) {
					loggedInUser.setCandidateStatusId(40);
					int updateStageAfterSubmit = commonDao.updateCandidateStatus(loggedInUser, 40);
				}
				return CandidateSignatureServiceConstants.SUCCESS;
			} else {
				if (request.getAttribute("menuKey") != null) {
					String mandatory = ConfigurationConstants.getInstance().getMenuKeyMandatory(request.getAttribute("menuKey").toString());
					if (mandatory != null && mandatory.equals(GenericConstants.MANDATORY)) {
						candidateBean.setImageMandatory(true);
					}
				}

				message = "(message)" + errorMessage + "(/message)";
				Users users = candidateDao.getBasicCandidateInfo(loggedInUser);
				candidateBean.getPersonalDetailsBean().setCandidateName(users.getFirstName());
				candidateBean.getPersonalDetailsBean().setDateOfBirth(users.getDate_Of_Birth());
				candidateBean.setGenderValDesc(users.getGender());
				candidateBean.setGenderVal(users.getGender());
				candidateBean.setImageUploaded(false);
				String candidateFolder = loggedInUser.getUsername();
				File documentFolder = new File(pathToSaveDocument + "" + candidateFolder);
				File f = new File(documentFolder + File.separator + loggedInUser.getUsername() + "_photo.jpg");
				f.delete();
				return CandidateSignatureServiceConstants.FILE_UPLOAD_FAILE;
			}
		}
	}

	public int insertCandidateSignature(CandidateBean candidateBean, Users loggedInUser) throws Exception {
		int insertCandidateImage = candidatePhotoSignatureDao.insertCandidateSignature(candidateBean, loggedInUser);
		return insertCandidateImage;

	}

	public int insertCandidateImages(CandidateBean candidateBean, Users loggedInUser) throws Exception {
		int insertCandidateImage = candidatePhotoSignatureDao.insertCandidateImages(candidateBean, loggedInUser);
		return insertCandidateImage;
	}

	public int updateCandidatePhotoStatus(CandidateBean candidateBean, Users loggedInUser, String flag) throws Exception {
		int updateuploadstatus = candidatePhotoSignatureDao.updateCandidatePhotoStatus(candidateBean, loggedInUser, flag);
		return updateuploadstatus;
	}
}
