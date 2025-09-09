package com.nseit.generic.util.validation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.util.Strings;

import com.nseit.generic.aws.util.UploadPhotoSignInS3sdkV2;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
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
// @AllArgsConstructor
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBeanValidator {

	// for photo and sign
	// private String documentPath = "D:\\client\\";
	// //ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
	// private String documentPath ;
	public String getDocumentPath() {
		return ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
	}

	private CandidateBean candidateBean;
	// form, database
	private String type;
	private Users loggedInUser;
	private UploadPhotoSignInS3sdkV2 uploadPhotoSignInS3sdkV2;

	// public PhotoBeanValidator(CandidateBean candidateBean, String type, Users
	// loggedInUser) {
	// this.candidateBean = candidateBean;
	// this.type = type;
	// this.loggedInUser = loggedInUser;
	// }

	public String validateUploadedPhoto() {
		String error = "";
		return formDBBasedValidation(error);
	}

	/**
	 * @param error
	 *            {@code if ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3) is true fetch photo from cloud else from local server}
	 * @return
	 */
	public String formDBBasedValidation(String error) {
		List<String> errorList = new ArrayList<String>();
		String isS3Enable = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SAVE_IN_S3);
		try {
			if ((Strings.isNotBlank(isS3Enable) && "true".equals(isS3Enable))) {
				if ("form".equals(type)) {
					return validatePhotoWithForm(errorList);

				} else {
					String photo = "_photo.jpg";
					StringBuilder concat = new StringBuilder();
					boolean IsPhotoPresentInCloud = uploadPhotoSignInS3sdkV2.isPhotoSignaturePresentInS3(loggedInUser,
							photo);

					if (IsPhotoPresentInCloud) {
						getPhotoFromCloud(errorList, photo);

					} else {
						concat.append("<li>");
						concat.append(ResourceUtil
								.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_PHOTO_REQD));
						concat.append("</li>");
						errorList.add(concat.toString());
						if (errorList.size() > 0)
							error = ValidatorUtil.validationMessageFormatterForImages(errorList);
						
					}
				}

			} else {
				if ("form".equals(type)) {// if s3 is false
					return validatePhotoWithForm(errorList);

				} else {
					return validatePhotoWithDB(errorList);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return error;
	}

	private void getPhotoFromCloud(List<String> errorList, String photo) throws IOException {
		byte[] photoBytes = uploadPhotoSignInS3sdkV2.getByteArrayOfPhotoSignFromCandFolderInS3(loggedInUser,
				photo);
		if (photoBytes == null) {
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(
					ValidationMessageConstants.CANDICATE_PHOTO_CORRUPTED));
		} else {
			checkIfPhotoDataIsInvalid(errorList, photoBytes);
		}
	}

	private void checkIfPhotoDataIsInvalid(List<String> errorList, byte[] photoBytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(photoBytes);
		BufferedImage bufferImage = ImageIO.read(bis);
		if (bufferImage == null) {
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(
					ValidationMessageConstants.CANDIDATE_PHOTO_INVALID_DATA));
		}
	}

	private String validatePhotoWithDB(List<String> errorList) {
		List<String> errorsList = new ArrayList<String>();
		File photofilePath = new File(getDocumentPath() + File.separator + loggedInUser.getUsername() + File.separator
				+ loggedInUser.getUsername() + "_photo.jpg");
		if (photofilePath == null || !photofilePath.exists()) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("<li>");
			stringBuilder.append("Please click on Edit Photo button and upload the missing Photograph.");
			stringBuilder.append("</li>");
			errorsList.add(stringBuilder.toString());
		}
		String errorMessage = ValidatorUtil.validationMessageFormatter(errorsList);
		return errorMessage;
	}

	private String validatePhotoWithForm(List<String> errorList) throws IOException {
		File photoPath = null;
		String errorMessage = null;
		String fname = null;

		String imageType = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.IMAGE_TYPE)
				.toLowerCase();
		photoPath = new File(candidateBean.getAttachmentPhoto().getPath());
		// For Checking ImageFile conetents
		BufferedImage originalImage = null;
		originalImage = ImageIO.read(photoPath);
		if (originalImage == null) {
			errorList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_PHOTO_INVALID_DATA));
		}

		String ext = null;
		if (candidateBean.getAttachmentPhotoFileName() != null) {
			int mid = candidateBean.getAttachmentPhotoFileName().lastIndexOf(".");
			if (mid > 0) {
				fname = candidateBean.getAttachmentPhotoFileName().substring(0, mid);
				ext = candidateBean.getAttachmentPhotoFileName().substring(mid + 1,
						candidateBean.getAttachmentPhotoFileName().length());
				ext = ext.toLowerCase(); // For not case sensitive extension
											// format
			} else {
				ext = "noext";
			}
		}
		validateAttachedPhotoSize(fname, errorList, "^[a-zA-Z0-9_-]*$");
		validateAttachedPhoto(errorList);

		validateFormatForPhoto(candidateBean, errorList, imageType, ext);
		validateUploadImageSizeLimit(photoPath, errorMessage, errorList, imageType, ext);

		if (errorList.size() > 0)
			errorMessage = ValidatorUtil.validationMessageFormatterForImages(errorList);

		return errorMessage;
	}

	public String validateUploadImageSizeLimit(File photoPath, String errorMessage, List<String> errorList,
			String imageType, String ext) {
		Float value = null;
		Float value1 = null;
		value1 = (Float.valueOf(photoPath.length()) / 1024);
		if (candidateBean.getAttachmentPhotoFileName() != null) {
			if (imageType.contains(ext)) {
				if (value1 < Integer.parseInt(
						ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_IMAGE_SIZE_IN_KB))) {
					errorMessage = "1,"
							+ ResourceUtil.getValidationErrorMessageProperty(
									ValidationMessageConstants.UPLOAD_IMAGE_SIZE_LIMIT_MESSAGE_MIN)
							+ " "
							+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_IMAGE_SIZE_IN_KB)
							+ "KB";
					errorList.add("<li>"
							+ ResourceUtil.getValidationErrorMessageProperty(
									ValidationMessageConstants.UPLOAD_IMAGE_SIZE_LIMIT_MESSAGE_MIN)
							+ " "
							+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_IMAGE_SIZE_IN_KB)
							+ "KB" + "</li>");
				}
				value = (Float.valueOf(photoPath.length()) / 1024);
				if (value > Integer.parseInt(
						ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_IMAGE_SIZE_IN_KB))) {
					errorMessage = "1,"
							+ ResourceUtil.getValidationErrorMessageProperty(
									ValidationMessageConstants.UPLOAD_IMAGE_SIZE_LIMIT_MESSAGE)
							+ " "
							+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_IMAGE_SIZE_IN_KB)
							+ "KB";
					errorList.add("<li>"
							+ ResourceUtil.getValidationErrorMessageProperty(
									ValidationMessageConstants.UPLOAD_IMAGE_SIZE_LIMIT_MESSAGE)
							+ " "
							+ ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_IMAGE_SIZE_IN_KB)
							+ "KB" + "</li>");
				}
			}
		}
		if (photoPath.length() == 0)
			errorList.add("<li>"
					+ ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UPLOAD_IMAGE_REQUIRED)
					+ "</li>");
		return errorMessage;
	}

	public void validateAttachedPhotoSize(String fname, List<String> errorList, String regex) {
		if (candidateBean.getAttachmentPhotoFileName() != null) {
			if (candidateBean.getAttachmentPhotoFileName().length() > 100) {
				errorList.add("Please upload a file whose name along with extension is less than 100 characters.");
			} else if (fname != null && !fname.equals("") && !fname.matches(regex)) {
				errorList.add("File " + candidateBean.getAttachmentPhotoFileName()
						+ " is invalid. Kindly upload documents which have only characters A-Z or a-z or 0-9 or _ or - in its file name. File name should not have spaces.");
			}
		}
	}

	public void validateFormatForPhoto(CandidateBean candidateBean, List<String> errorList, String imageType,
			String ext) {
		if (candidateBean.getAttachmentPhotoFileName() != null) {
			if (!imageType.contains(ext)) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("<li>");
				stringBuilder.append(ResourceUtil
						.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_IMAGE_FORMAT));
				stringBuilder.append("</li>");
				errorList.add(stringBuilder.toString());
			}
		}
	}

	public void validateAttachedPhoto(List<String> errorList) {
		if (candidateBean.getAttachmentPhoto() != null) {
			if (ValidatorUtil.isEmpty(candidateBean.getAttachmentPhoto().getPath())) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("<li>");
				stringBuilder.append(ResourceUtil
						.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_PHOTO_REQD));
				stringBuilder.append("</li>");
				errorList.add(stringBuilder.toString());
			}
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("<li>");
			stringBuilder.append(
					ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_PHOTO_REQD));
			stringBuilder.append("</li>");
			errorList.add(stringBuilder.toString());
		}
	}

}
