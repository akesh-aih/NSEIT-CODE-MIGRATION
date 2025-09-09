package com.nseit.generic.util.validation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;

import com.nseit.generic.aws.util.UploadDocInS3sdkV2;
import com.nseit.generic.dao.CandidateDao;
import com.nseit.generic.dao.CandidateUploadDocDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;

import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import com.nseit.generic.util.LoggerHome;
import org.apache.logging.log4j.Logger;

@Slf4j
@Data
@ToString

public class DocumentBeanValidator {
	private CandidateUploadDocDao candidateUploadDocDao;
	private CandidateDao candidateDao;

	public void setCandidateUploadDocDao(CandidateUploadDocDao candidateUploadDocDao) {
		this.candidateUploadDocDao = candidateUploadDocDao;
	}

	public void setCandidateDao(CandidateDao candidateDAO) {
		this.candidateDao = candidateDAO;
	}

	private Logger logger = LoggerHome.getLogger(getClass());

	public String validateUploadedDocuments(CandidateBean candidateBean, String type, Users loggedInUser) {
		if ("form".equals(type)) {
			String errors = "";
			List<String> errorsList = new ArrayList<String>();
			File photoPath = null;
			String errorMessage = null;
			String regex = "^[a-zA-Z0-9_-]*$";
			String fname = "";

			if (candidateBean.getData1() != null) {
				photoPath = validateIsPathExist(candidateBean, errorsList, photoPath);
			} else {
				photoPath = new File("");
			}

			String documentType = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase();

			if (candidateBean.getOcdFlagValue() != null) {
				validateIsOcdFlagNull(candidateBean, errorsList, documentType);
			}

			validateFileSize(candidateBean, errorsList, photoPath, documentType);
			validateFileNameAndSize(candidateBean, errorsList, photoPath, regex, fname, documentType);
			errors = setErrorList(errors, errorsList);
			return errors;

		} else {

			List<String> errorsList = new ArrayList<String>();
			List<Integer> acdmSelection = new ArrayList<Integer>();
			List<EducationDetailsBean> educationDetaiBeanList = new ArrayList<EducationDetailsBean>();
			Users users = null;
			String ugDeg = "";
			String pgDeg = "";
			String pgDiploma = "";
			String pstmCerti = "";

			List<CandidateBean> uploadListDoc = candidateBean.getUploadList();
			String documentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);

			try {
				acdmSelection = candidateUploadDocDao.getEducationSelectionList(loggedInUser);
				educationDetaiBeanList = candidateUploadDocDao.getEducationBeanList(loggedInUser);// for education
				users = candidateDao.getBasicInfoForUploadDoc(loggedInUser);// for personal details
			} catch (Exception e) {
				logger.info(e, e);
			}

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

			String candidateFolder = loggedInUser.getUsername();

			/* Validation Code starts here */

			if ("6".equals(users.getExServiceMen())) {
				Optional<CandidateBean> isDocPresent = uploadListDoc.stream().filter(val -> val.getOcdFlagValue().equals("ExSM")).findFirst();
				isDocPresentAtBothPath(loggedInUser, errorsList, documentPath, candidateFolder, isDocPresent, "Ex-servicemen Certificate");
			}

			if (Arrays.asList("1", "2", "3", "4", "5", "6").contains(users.getCommunity())) {
				Optional<CandidateBean> isDocPresent = uploadListDoc.stream().filter(val -> val.getOcdFlagValue().equals("CC")).findFirst();
				isDocPresentAtBothPath(loggedInUser, errorsList, documentPath, candidateFolder, isDocPresent, "Community Certificate");
			}

			if (acdmSelection.contains(1)) {
				Optional<CandidateBean> isDocPresent = uploadListDoc.stream().filter(val -> val.getOcdFlagValue().equals("SSLC")).findFirst();
				isDocPresentAtBothPath(loggedInUser, errorsList, documentPath, candidateFolder, isDocPresent, "SSLC Certificate");
			}

			if (acdmSelection.contains(2)) {
				Optional<CandidateBean> isDocPresent = uploadListDoc.stream().filter(val -> val.getOcdFlagValue().equals("HSC")).findFirst();
				isDocPresentAtBothPath(loggedInUser, errorsList, documentPath, candidateFolder, isDocPresent, "HSC Certificate");
			}

			if (acdmSelection.contains(3)) {
				Optional<CandidateBean> isDocPresent = uploadListDoc.stream().filter(val -> val.getOcdFlagValue().equals("DIP")).findFirst();
				isDocPresentAtBothPath(loggedInUser, errorsList, documentPath, candidateFolder, isDocPresent, "Diploma Certificate");
			}

			if ("true".equals(ugDeg)) {
				Optional<CandidateBean> isDocPresent = uploadListDoc.stream().filter(val -> val.getOcdFlagValue().equals("UG")).findFirst();
				isDocPresentAtBothPath(loggedInUser, errorsList, documentPath, candidateFolder, isDocPresent, "UG Degree Certificate");
			}

			if ("true".equals(pgDeg)) {
				Optional<CandidateBean> isDocPresent = uploadListDoc.stream().filter(val -> val.getOcdFlagValue().equals("PGDEG")).findFirst();
				isDocPresentAtBothPath(loggedInUser, errorsList, documentPath, candidateFolder, isDocPresent, "PG Degree Certificate");
			}

			if ("true".equals(pgDiploma)) {
				Optional<CandidateBean> isDocPresent = uploadListDoc.stream().filter(val -> val.getOcdFlagValue().equals("PGDIP")).findFirst();
				isDocPresentAtBothPath(loggedInUser, errorsList, documentPath, candidateFolder, isDocPresent, "PG Diploma Certificate");
			}

			if ("true".equals(pstmCerti)) {
				Optional<CandidateBean> isDocPresent = uploadListDoc.stream().filter(val -> val.getOcdFlagValue().equals("PSTM")).findFirst();
				isDocPresentAtBothPath(loggedInUser, errorsList, documentPath, candidateFolder, isDocPresent, "PSTM Certificate (1st to 12th Standard & Diploma)");
			}

			/* Validation Code Ends Here */

			String errorMessage = ValidatorUtil.validationMessageFormatter(errorsList);
			return errorMessage;

		}
	}

	private void isDocPresentAtBothPath(Users loggedInUser, List<String> errorsList, String documentPath, String candidateFolder, Optional<CandidateBean> isDocPresent,
			String docName) {
		File filePath;
		if (isDocPresent.isPresent()) {
			CandidateBean document = isDocPresent.get();
			String documentPrefix = document.getOcdFlagValue();
			String documentSuffix = document.getDocumentFileName1();
			filePath = new File(documentPath + "" + candidateFolder + File.separator + loggedInUser.getUsername() + "_" + documentPrefix + "_" + documentSuffix);

			if (!filePath.exists()) {
				logger.info(documentPrefix + " Document is not present on Physical path for User : " + loggedInUser.getUsername());
				errorsList.add("<li>" + "Please click on Application Tab and go to Upload Documents and upload the missing <b> " + documentSuffix + " </b> document</li>");
			}
		} else {
			logger.info(docName + " Document is not present in Database for User :" + loggedInUser.getUsername());
			errorsList.add("<li>" + "Please click on Application Tab and go to Upload Documents and upload the missing <b> " + docName + " </b> document</li>");
		}
	}

	public boolean validateIsCatPresent(boolean categoryPresent, String category, CandidateBean candiBean, File filePath) {
		if (category != null && !category.equalsIgnoreCase("OC")) {
			if (candiBean.getDocLabel1().contains("Community Certificate")) {
				if (filePath.exists()) {
					categoryPresent = true;
				}
			}
		} else {
			categoryPresent = true;
		}
		return categoryPresent;
	}

	public boolean validateSscDocPathExist(boolean sscDocPresent, List<Integer> acdmUG, CandidateBean candiBean, File filePath) {
		if (acdmUG != null && acdmUG.contains(1)) {
			if (candiBean.getDocLabel1().contains("10th / SSLC Certificate")) {
				if (filePath.exists()) {
					sscDocPresent = true;
				}
			}
		}
		return sscDocPresent;
	}

	public boolean validateHscDocPathExist(boolean hscPresent, List<Integer> acdmUG, CandidateBean candiBean, File filePath) {
		if (acdmUG != null && acdmUG.contains(2)) {
			if (candiBean.getDocLabel1().contains("12th / HSC Certificate")) {
				if (filePath.exists()) {
					hscPresent = true;
				}
			}
		}
		return hscPresent;
	}

	public boolean validateIsDipCertPathExist(boolean dipPresent, List<Integer> acdmUG, CandidateBean candiBean, File filePath) {
		if (acdmUG != null && acdmUG.contains(3)) {
			if (candiBean.getDocLabel1().contains("Diploma Certificate")) {
				if (filePath.exists()) {
					dipPresent = true;
				}
			}
		}
		return dipPresent;
	}

	public boolean validateUgDocPathExist(boolean ugPresent, List<Integer> acdmUG, CandidateBean candiBean, File filePath) {
		if (acdmUG != null && acdmUG.contains(4)) {
			if (candiBean.getDocLabel1().contains("UG Degree Certificate")) {
				if (filePath.exists()) {
					ugPresent = true;
				}
			}
		}
		return ugPresent;
	}

	public boolean validatePgDocPathExist(boolean pgPresent, List<Integer> acdmUG, CandidateBean candiBean, File filePath) {
		if (acdmUG != null && acdmUG.contains(5)) {
			if (candiBean.getDocLabel1().contains("PG Degree Certificate")) {
				if (filePath.exists()) {
					pgPresent = true;
				}
			}
		}
		return pgPresent;
	}

	public boolean validateNativityPathExist(boolean ocertiPresent, CandidateBean candiBean, File filePath) {
		if (candiBean.getDocLabel1().contains("Nativity / Residence Certificate")) {
			if (filePath.exists()) {
				ocertiPresent = true;
			}
		}
		return ocertiPresent;
	}

	public void validateIsOcdFlagNull(CandidateBean candidateBean, List<String> errorsList, String documentType) {
		if (!Strings.isBlank(candidateBean.getData1().getPath())) {
			if (ValidatorUtil.isEmpty(candidateBean.getData1().getPath())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_DOCUMENT_REQD));
			}
		} else {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_DOCUMENT_REQD));
		}
		validateDocFormatIsValidOrNot(candidateBean, errorsList, documentType);
	}

	public void validateDocFormatIsValidOrNot(CandidateBean candidateBean, List<String> errorsList, String documentType) {
		if (!Strings.isBlank(candidateBean.getData1FileName())) {
			if (!documentType.contains(getFileExtension(candidateBean.getData1FileName().toLowerCase()))) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_DOCUMENT_FORMAT));
			}
		}
	}

	public File validateIsPathExist(CandidateBean candidateBean, List<String> errorsList, File photoPath) {
		photoPath = new File(candidateBean.getData1().getPath());
		if (!photoPath.exists()) {
			errorsList.add("Blank File cannot be uploaded.");
		}
		return photoPath;
	}

	public void validateFileNameAndSize(CandidateBean candidateBean, List<String> errorsList, File photoPath, String regex, String fname, String documentType) {
		if (!Strings.isBlank(candidateBean.getData1FileName())) {
			int mid = candidateBean.getData1FileName().lastIndexOf(".");
			if (mid > 0) {
				fname = candidateBean.getData1FileName().substring(0, mid);
			}
			if (candidateBean.getData1FileName().length() > 100) {
				errorsList.add("Please upload a file whose name along with extension is less than 100 characters.");
			} else if (fname != null && !fname.equals("") && !fname.matches(regex)) {
				errorsList.add("File " + candidateBean.getData1FileName()
						+ " is invalid. Kindly upload documents which have only characters A-Z or a-z or 0-9 or _ or - in its file name. File name should not have spaces.");
			}

		}
	}

	public String setErrorList(String errors, List<String> errorsList) {
		if (errorsList != null && !errorsList.isEmpty()) {
			errors = ValidatorUtil.validationMessageFormatterforDoc(errorsList);
			errors = errors.replace(",", "");
		}
		return errors;
	}

	public void validateFileSize(CandidateBean candidateBean, List<String> errorsList, File photoPath, String documentType) {
		Float value = (Float.valueOf(photoPath.length()) / 1024);
		int valMin = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB));
		int valMax = Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB));
		if (documentType.contains(getFileExtension(candidateBean.getData1FileName().toLowerCase()))) {
			validateFileMinSize(errorsList, value, valMin);
			validateFileMaxSize(errorsList, value, valMax);
		}
	}

	public void validateFileMaxSize(List<String> errorsList, Float value, int valMax) {
		String errorMessage;
		if (value > valMax) {
			errorMessage = "1," + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_DOCUMENT_SIZE_LIMIT_MESSAGE) + " "
					+ (Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB)) / 1024) + "MB";
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_DOCUMENT_SIZE_LIMIT_MESSAGE) + " "
					+ (Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB)) / 1024) + "MB");
		}
	}

	public void validateFileMinSize(List<String> errorsList, Float value, int valMin) {
		String errorMessage;
		if (value < valMin) {
			errorMessage = "1," + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_DOCUMENT_SIZE_LIMIT_MESSAGE_MIN) + " "
					+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB) + "KB";
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_DOCUMENT_SIZE_LIMIT_MESSAGE_MIN) + " "
					+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB) + "KB");
		}
	}

	private String getFileExtension(String fileName) {
		String ext = "";
		int mid = fileName.lastIndexOf(".");
		if (mid > 0) {
			ext = fileName.substring(mid + 1, fileName.length());
		} else {
			ext = "noext";
		}
		return ext;
	}
}
