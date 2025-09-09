package com.nseit.generic.util.validation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.nseit.generic.aws.util.UploadPhotoSignInS3sdkV2;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
@Data
@ToString
@AllArgsConstructor
@Slf4j
@NoArgsConstructor
public class SignatureBeanValidator {

//	private Logger logger = LoggerHome.getLogger(getClass());
	
	// for photo and sign
//	private String documentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
//	private String documentPath = "D:\\Documents_and_Photo\\";
	private CandidateBean candidateBean;
	// form, database
	private String type;
	private Users loggedInUser;
	private UploadPhotoSignInS3sdkV2 uploadPhotoSignInS3sdkV2;

//	public SignatureBeanValidator(CandidateBean candidateBean, String type, Users loggedInUser) {
//		this.candidateBean = candidateBean;
//		this.type = type;
//		this.loggedInUser = loggedInUser;
//	}
	public String getDocumentPath() {
		return ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
	 }

	public String validateUploadedSignature() {
		String error = "";
		return formDBBasedValidation(error);
	}

	private String formDBBasedValidation(String error) {
		List<String> errorList = new ArrayList<String>();
		String isS3Enable = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		try {
			if ((Strings.isNotBlank(isS3Enable) && "true".equals(isS3Enable))) {
				if ("form".equals(type)) {
					return validateSignatureWithForm(errorList);

				} else {
					String photoSign = "_sign.jpg";
					StringBuilder concat = new StringBuilder();
					boolean IsPhotoPresentInCloud = uploadPhotoSignInS3sdkV2.isPhotoSignaturePresentInS3(loggedInUser,
							photoSign);

					if (IsPhotoPresentInCloud) {
						getSignatureFromCloud(errorList, photoSign);

					} else {
						concat.append("<li>");
						concat.append(ResourceUtil.getValidationErrorMessageProperty(
								ValidationMessageConstants.UPLOAD_SIGNATURE_REQUIRED));
						concat.append("</li>");
						errorList.add(concat.toString());
						if (errorList.size() > 0)
							error = ValidatorUtil.validationMessageFormatterForImages(errorList);
					}
				}

			} else {
				if ("form".equals(type)) {
					return validateSignatureWithForm(errorList);
				} else {
					return validateSignatureWithDB(errorList);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return error;
	}

private void getSignatureFromCloud(List<String> errorList, String photoSign) throws IOException {
	byte[] photoBytes = uploadPhotoSignInS3sdkV2.getByteArrayOfPhotoSignFromCandFolderInS3(loggedInUser,
			photoSign);
	if (photoBytes == null) {
		errorList.add(ResourceUtil.getValidationErrorMessageProperty(
				ValidationMessageConstants.CANDICATE_SIGNATURE_CORRUPTED));
	} else {
		checkIfSignatureDataIsInvalid(errorList, photoBytes);
	}
}

private void checkIfSignatureDataIsInvalid(List<String> errorList, byte[] photoBytes) throws IOException {
	ByteArrayInputStream bis = new ByteArrayInputStream(photoBytes);
	BufferedImage bufferImage = ImageIO.read(bis);
	if (bufferImage == null) {
		errorList.add(ResourceUtil.getValidationErrorMessageProperty(
				ValidationMessageConstants.CANDIDATE_SIGNATURE_INVALID_DATA));
	}
}

private String validateSignatureWithDB(List<String> errorsList) {
	
	File signfilePath = new File(getDocumentPath() + File.separator + loggedInUser.getUsername() + File.separator + loggedInUser.getUsername() + "_sign.jpg");
	if (signfilePath == null || !signfilePath.exists()) {
		errorsList.add("<li>" + "Please click on Edit Signature button and upload the missing Signature." + "</li>");
	}
	String errorMessage = ValidatorUtil.validationMessageFormatter(errorsList);
	return errorMessage;
}

private String validateSignatureWithForm(List<String> errorsList) throws IOException {
	File photoPath = null;
	String errorMessage = null;
	String fname = "";
	String signatureType = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SIGNATURE_TYPE).toLowerCase();
	Float value = null;
	Float value1 = null;
	photoPath = new File(candidateBean.getAttachmentSignature().getPath());
	String ext = null;
	String regex = "^[a-zA-Z0-9_-]*$";
	BufferedImage originalImage = null;
		originalImage = ImageIO.read(photoPath);
	if (originalImage == null) {
		errorsList.add("<li>" + "Please upload a valid Image file." + "</li>");
	}

	if (candidateBean.getAttachmentSignatureFileName() != null) {
		int mid = candidateBean.getAttachmentSignatureFileName().lastIndexOf(".");
		if (mid > 0) {
			fname = candidateBean.getAttachmentSignatureFileName().substring(0, mid);
			ext = candidateBean.getAttachmentSignatureFileName().substring(mid + 1, candidateBean.getAttachmentSignatureFileName().length());
			ext = ext.toLowerCase(); // For not case sensitive extension
										// format
		} else {
			ext = "noext";
		}
	}
	validateAttachedSignSize(fname, errorsList, regex);
	
	validateAttachmentSign(errorsList);
	
	validateFormatForSign(errorsList, signatureType, ext);
	  validateUploadSignSizeLimit(photoPath, errorMessage, errorsList, signatureType, ext);

	if (errorsList.size() > 0)
		errorMessage = ValidatorUtil.validationMessageFormatterForImages(errorsList);

	return errorMessage;
}

public String validateUploadSignSizeLimit(File photoPath, String errorMessage, List<String> errorList, String signatureType,
		String ext) {
	Float value = null;
	Float value1 = null;
	if (candidateBean.getAttachmentSignatureFileName() != null) {
		if (signatureType.contains(ext)) {
			value1 = (Float.valueOf(photoPath.length()) / 1024);
			if (value1 < Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_SIGN_SIZE_IN_KB))) {
				errorMessage = "1," + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_SIGNATURE_SIZE_LIMIT_MESSAGE_MIN) + " "
						+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_SIGN_SIZE_IN_KB) + "KB";
				errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_SIGNATURE_SIZE_LIMIT_MESSAGE_MIN) + " "
						+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_SIGN_SIZE_IN_KB) + "KB" + "</li>");
			}
			value = (Float.valueOf(photoPath.length()) / 1024);
			if (value > Integer.parseInt(ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_SIGNATURE_SIZE_IN_KB))) {
				errorMessage = "1," + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_SIGNATURE_SIZE_LIMIT_MESSAGE) + " "
						+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_SIGNATURE_SIZE_IN_KB) + "KB";
				errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_SIGNATURE_SIZE_LIMIT_MESSAGE) + " "
						+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_SIGNATURE_SIZE_IN_KB) + "KB" + "</li>");
			}
		}
	}
	if (photoPath.length() == 0)
		errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_SIGNATURE_REQUIRED) + "</li>");
	return errorMessage;
}


public void validateAttachedSignSize(String fname, List<String> errorList, String regex) {
	if (candidateBean.getAttachmentSignatureFileName() != null) {
		if (candidateBean.getAttachmentSignatureFileName().length() > 100) {
			errorList.add("Please upload a file whose name along with extension is less than 100 characters.");
		} else if (fname != null && !fname.equals("") && !fname.matches(regex)) {
			errorList.add("File " + candidateBean.getAttachmentSignatureFileName()
					+ " is invalid. Kindly upload documents which have only characters A-Z or a-z or 0-9 or _ or - in its file name. File name should not have spaces.");
		}
	}
}

public void validateFormatForSign(List<String> errorList, String signatureType, String ext) {
	if (candidateBean.getAttachmentSignatureFileName() != null) {
		if (signatureType.contains(ext)) {
		} else {
			errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_SIGNATURE_FORMAT) + "</li>");
		}
	}
}

public void validateAttachmentSign(List<String> errorList) {
	if (candidateBean.getAttachmentSignature() != null) {
		if (ValidatorUtil.isEmpty(candidateBean.getAttachmentSignature().getPath())) {
			errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_SIGN_REQD) + "</li>");
		}
	} else {
		errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_SIGN_REQD) + "</li>");
	}
}
}
