package com.nseit.generic.util.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.service.RegistrationService;
import com.nseit.generic.util.EmailValidator;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.utils.StringUtils;

@Data
@ToString
@AllArgsConstructor
@Slf4j
public class RegistrationBeanValidator {

	private RegistrationService registrationService;

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	public RegistrationBeanValidator() {
		super();
	}

	public String validateRegisterCandidateBean(RegistrationBean registrationBean) {
		String errors = "";
		StringBuilder stringBuilder = new StringBuilder();
		List<String> errorsList = new ArrayList<String>();
		Logger logger = LoggerHome.getLogger(getClass());
		ArrayList<String> errorField = new ArrayList<String>();
		try {
			validateCandidateFirstName(registrationBean, errorsList, errorField);
			validateNationality(registrationBean, errorsList, errorField);
			validateGenderNullCheck(registrationBean, errorsList, errorField);
			validateExServiceMen(registrationBean, errorsList, errorField);

			if (Strings.isBlank(registrationBean.getCommCertYesNo())) {
				errorsList.add("<li>" + "Please select Do you have community certificate issued by Tamil Nadu Government?" + ".</li>");
				errorField.add("commCertYesNo");
			} else if (registrationBean.getCommCertYesNo().equals("6")) {
				if (Strings.isBlank(registrationBean.getCommunity())) {
					errorsList.add("<li>" + "Please select Community." + "</li>");
					errorField.add("community");
				} else if (!registrationBean.getCommunity().equals("7")) {
					if (StringUtils.isBlank(registrationBean.getSubCaste())) {
						errorsList.add("<li>" + "Please enter Sub Caste." + "</li>");
						errorField.add("subCaste");
					} else if (!Pattern.matches("[a-zA-Z ]{1,50}", registrationBean.getSubCaste())) {
						errorsList.add("<li>" + "Please enter valid Sub Caste." + "</li>");
						errorField.add("subCaste");
					}
					if (StringUtils.isBlank(registrationBean.getIssueAuthCommCert())) {
						errorsList.add("<li>" + "Please enter Issuing Authority of Community Certificate." + "</li>");
						errorField.add("issueAuthCommCert");
					} else if (!Pattern.matches("[a-zA-Z0-9 &-.,\\\\/]{1,100}", registrationBean.getIssueAuthCommCert())) {
						errorsList.add("<li>" + "Please enter valid Issuing Authority of Community Certificate" + ".</li>");
						errorField.add("issueAuthCommCert");
					}
					if (StringUtils.isBlank(registrationBean.getCommCertNo())) {
						errorsList.add("<li>" + "Please enter Community Certificate Number." + "</li>");
						errorField.add("commCertNo");
					} else if (!Pattern.matches("[a-zA-Z0-9 &-.,\\\\/]{1,20}", registrationBean.getCommCertNo())) {
						errorsList.add("<li>" + "Please enter valid Community Certificate Number" + ".</li>");
						errorField.add("commCertNo");
					}
					if (StringUtils.isBlank(registrationBean.getCommCertPlace())) {
						errorsList.add("<li>" + "Please enter Community Certificate Place of Issue." + "</li>");
						errorField.add("commCertPlace");
					} else if (!Pattern.matches("[a-zA-Z0-9 &-.,\\\\/]{1,50}", registrationBean.getCommCertPlace())) {
						errorsList.add("<li>" + "Please enter valid Community Certificate Place of Issue" + ".</li>");
						errorField.add("commCertPlace");
					}
					if (StringUtils.isBlank(registrationBean.getCommIssueDate())) {
						errorsList.add("<li>" + "Please select Community Certificate Issuing Date" + ".</li>");
						errorField.add("commIssueDate");
					} else {
						if (StringUtils.isNotBlank(registrationBean.getDateOfBirth()) && !registrationBean.getDateOfBirth().toLowerCase().contains("nan")
								&& !registrationBean.getDateOfBirth().toLowerCase().contains("undefined")) {
							try {
								DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");

								String dob = registrationBean.getDateOfBirth();
								Date dob1 = df1.parse(dob);

								String catDate = registrationBean.getCommIssueDate();
								Date catDate1 = df1.parse(catDate);

								if (catDate1.before(dob1)) {
									errorsList.add("<li>" + "Community Certificate Issuing date should be on or after Date of Birth." + "</li>");
									errorField.add("commIssueDate");
								}
							} catch (Exception e) {
								logger.fatal(e, e);
							}
						}
					}
				}
			}

			if (StringUtils.isBlank(registrationBean.getDisableYesNo())) {
				errorsList.add("<li>" + "Please select Are you differently abled?" + "</li>");
				errorField.add("disableYesNo");
			} else if (registrationBean.getDisableYesNo().equals("6")) {
				if (!registrationBean.isDiffAbledChkBox()) {
					errorsList.add("<li>" + "Please check 'I agree to provide Differently Abled Person Certificate at the time of Certificate Verification' declaration checkbox."
							+ "</li>");
					errorField.add("diffAbledChkBox");
				}

				if (StringUtils.isBlank(registrationBean.getDisableType())) {
					errorsList.add("<li>" + "Please select Differently Abled Category." + "</li>");
					errorField.add("disableType");
				}
				if (StringUtils.isNotBlank(registrationBean.getDisablityPercent())) {
					if (Integer.parseInt(registrationBean.getDisablityPercent()) < 40 || Integer.parseInt(registrationBean.getDisablityPercent()) > 70) {
						errorsList.add("<li>" + "Percentage of Disability for " + registrationBean.getDisableType() + "should be between 40 to 70" + ".</li>");
						errorField.add("disablityPercent");
					}
				} else {
					errorsList.add("<li>" + "Please enter Percentage of Disability." + "</li>");
					errorField.add("disablityPercent");
				}
			}
			
			validateWidowYesNo(registrationBean, errorsList, errorField);

			if (Strings.isBlank(registrationBean.getDateOfBirth())) {
				errorsList.add("<li>" + "Please select Date of Birth (as per SSLC mark Sheet)" + ".</li>");
				errorField.add("dateOfBirth");
			}

			// TODO add validateMobileNumber 
			validateEmailAddress(registrationBean, errorsList, errorField);
			// Checking email or mobile is verified in MOB_OTP_MASTER table
			checkSmsOrEmailIsVerified(registrationBean, errorsList, logger);
			// Checking email or mobile is already present in User Master Table or Not
			checkEmailOrMobIsAlreadyPresent(registrationBean, errorsList, logger, errorField);
			validatePassword(registrationBean, errorsList, errorField);
			validateConfirmPassword(registrationBean, errorsList, errorField);
			validateCaptcha(registrationBean, errorsList, errorField);


		} catch (Exception e) {
			stringBuilder.append("<li>" + "Some internal error occured, try again." + "</li>");
			errorsList.add(stringBuilder.toString());
		}
		if (errorsList != null && !errorsList.isEmpty()) {
			errors = ValidatorUtil.validationMessageFormatter(errorsList);
			registrationBean.setErrorField(errorField);
		}
		return errors;
	}

	public void validateGenderNullCheck(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(registrationBean.getGender())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.GENDER_REQD));
			errorField.add("gender");
		}
	}

	private void validateWidowYesNo(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (StringUtils.isNotBlank(registrationBean.getGender()) && registrationBean.getGender().equals("2")) {
			if (StringUtils.isBlank(registrationBean.getWidowYesNo())) {
				errorsList.add("<li>" + "Please select Are you a Destitute Widow?" + "</li>");
				errorField.add("widowYesNo");
			} else if (registrationBean.getWidowYesNo().equals("6")) {
				/*if (!registrationBean.isWidowCheckbox()) {
					errorsList
							.add("<li>" + "Please check 'I agree to provide Destitute Widow Certificate at the time of Certificate Verification' declaration checkbox." + "</li>");
					errorField.add("widowChkBox");
				}*/
				if (StringUtils.isBlank(registrationBean.getDesWidowCertNo())) {
					errorsList.add("<li>" + "Please enter Destitute Widow Certificate Number." + ".</li>");
					errorField.add("widowYesNo");
				} else if (!Pattern.matches("[a-zA-Z0-9 &-.,\\\\/]{1,50}", registrationBean.getDesWidowCertNo())) {
					errorsList.add("<li>" + "Please enter valid Destitute Widow Certificate Number" + ".</li>");
					errorField.add("widowYesNo");
				}

				if (StringUtils.isBlank(registrationBean.getWidowIssueDate())) {
					errorsList.add("<li>" + "Please select Destitute Widow Certificate Issuing Date" + ".</li>");
					errorField.add("widowIssueDate");
				}
				if (StringUtils.isBlank(registrationBean.getWidowIssueAuth())) {
					errorsList.add("<li>" + "Please select Issuing Authority of Destitute Widow Certificate" + ".</li>");
					errorField.add("widowIssueAuth");
				} else if (!Pattern.matches("[a-zA-Z0-9 &-.,\\\\/]{1,50}", registrationBean.getWidowIssueAuth())) {
					errorsList.add("<li>" + "Please enter valid Issuing Authority of Destitute Widow Certificate" + ".</li>");
					errorField.add("widowIssueAuth");
				}

				if (StringUtils.isBlank(registrationBean.getWidowDistrict())) {
					errorsList.add("<li>" + "Please select Destitute Widow Certificate Issued District" + ".</li>");
					errorField.add("widowDistrict");
				} else if (registrationBean.getWidowOtherDistrict().equals("585")) {
					if (StringUtils.isBlank(registrationBean.getWidowOtherDistrict())) {
						errorsList.add("<li>" + "Please enter Destitute Widow Certificate Issued Other District" + ".</li>");
						errorField.add("widowOtherDistrict");
					} else if (!Pattern.matches("[a-zA-Z0-9 &-.,\\\\/]{1,50}", registrationBean.getWidowOtherDistrict())) {
						errorsList.add("<li>" + "Please enter valid Destitute Widow Certificate Issued Other District" + ".</li>");
						errorField.add("widowOtherDistrict");
					}
				}
				if (StringUtils.isBlank(registrationBean.getWidowSubDivision())) {
					errorsList.add("<li>" + "Please enter Destitute Widow Certificate Issued Sub Division" + ".</li>");
					errorField.add("widowSubDivision");
				} else if (!Pattern.matches("[a-zA-Z0-9 &-.,\\\\/]{1,50}", registrationBean.getWidowSubDivision())) {
					errorsList.add("<li>" + "Please enter valid Destitute Widow Certificate Issued Sub Division" + ".</li>");
					errorField.add("widowSubDivision");
				}
			}
		}
	}

	private void checkEmailOrMobIsAlreadyPresent(RegistrationBean registrationBean, List<String> errorsList, Logger logger, ArrayList<String> errorField) {
		String userDetails = "";
		String emailFound = "";
		String mobileFound = "";
		userDetails = registrationService.checkIfUserDetailsExist(registrationBean);

		if (StringUtils.isNotBlank(userDetails)) {
			if (userDetails.contains(",")) {
				String[] output = userDetails.split(",");
				for (int i = 0; i < output.length; i++) {
					if ("E".equals(output[i]))
						emailFound = "true";
					if ("M".equals(output[i]))
						mobileFound = "true";
				}
			} else {
				if ("E".equals(userDetails))
					emailFound = "true";
				if ("M".equals(userDetails))
					mobileFound = "true";
			}
		}

		if ("true".equals(emailFound)) {
			errorsList.add("<li>" + " Please try again with a different email address" + ".</li>");
			errorField.add("emailAddress");
			registrationBean.setVerifyemailOTPFlag("false");
		}
		if ("true".equals(mobileFound)) {
			errorsList.add("<li>" + "Please try again with a different mobile number" + ".</li>");
			errorField.add("mobileNo");
			registrationBean.setVerifyMobileOTPFlag("false");
		}
	}

	private void checkSmsOrEmailIsVerified(RegistrationBean registrationBean, List<String> errorsList, Logger logger) {
		if (Strings.isNotBlank(registrationBean.getMobileNo()) || Strings.isNotBlank(registrationBean.getEmailAddress())) {
			int checkIfMobOrEmailIsAlreadyVerified = registrationService.checkIfMobOrEmailIsAlreadyVerified(registrationBean.getMobileNo(), registrationBean.getEmailAddress());

			if (checkIfMobOrEmailIsAlreadyVerified > 0) {
				logger.info("Email or Mobile is already Verified in MOB_OTP_MASTER for email : " + registrationBean.getEmailAddress() + " and Mobile : "
						+ registrationBean.getMobileNo());
				// errorsList.add("<li>" + " Email address or Mobile number is already exist" + ".</li>");
			} else {
				logger.info("Email or Mobile is not found Verified in MOB_OTP_MASTER for email : " + registrationBean.getEmailAddress() + " and Mobile : "
						+ registrationBean.getMobileNo());
			}

		}
	}

	public void validateCandidateFirstName(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (StringUtils.isBlank(registrationBean.getCandidateName())) {
			errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.FIRST_NAME_REQD) + "</li>");
			errorField.add("candidateName");
		} else {
			validateValidPatternForFirstName(registrationBean, errorsList, errorField);
		}
	}

	public void validateValidPatternForFirstName(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (!Pattern.matches("^(?=.*[A-Za-z])[A-Za-z .']{1,100}$", registrationBean.getCandidateName())) {
			errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VALID_FIRST_NAME_REQD) + "</li>");
			errorField.add("candidateName");
		}
	}

	public void validateInitials(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (StringUtils.isNotBlank(registrationBean.getInitials())) {
			if (!Pattern.matches("[a-zA-Z ]{1,6}", registrationBean.getInitials())) {
				errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VALID_FIRST_NAME_REQD) + "</li>");
				errorField.add("initials");
			}
		}
	}

	public void validateNationality(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (StringUtils.isBlank(registrationBean.getNationVal())) {
			errorsList.add("<li>" + "Please select Nationality." + "</li>");
			errorField.add("nationality");
		} else if (Arrays.asList("219", "Other").contains(registrationBean.getNationVal())) {
			errorsList.add("<li>" + "Only Indian, Nepal, Bhutan and Person of Indian Origin Nationals are allowed." + "</li>");
			errorField.add("nationality");
		}
	}

	public void validateExServiceMen(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) throws ParseException {
		if (Strings.isBlank(registrationBean.getExServiceMen())) {
			errorsList.add("<li>" + "Please select Are you an Ex-Servicemen." + "</li>");
			errorField.add("exServiceMen");
		} else if (Arrays.asList("6", "Yes").contains(registrationBean.getExServiceMen())) {
			if (Strings.isBlank(registrationBean.getDischargeDate())) {
				errorsList.add("<li>" + "Please select Date of Discharge / Probable Discharge" + "</li>");
				errorField.add("dischargeDate");
			} else {
				if (Strings.isNotBlank(registrationBean.getDateOfBirth()) && !registrationBean.getDateOfBirth().toLowerCase().contains("nan")
						&& !registrationBean.getDateOfBirth().toLowerCase().contains("undefined")) {

					DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");

					String dob = registrationBean.getDateOfBirth();
					Date dob1 = df1.parse(dob);

					String dischargeDate = registrationBean.getDischargeDate();
					Date dischargeDate1 = df1.parse(dischargeDate);
					Calendar c = Calendar.getInstance();
					c.setTime(dob1);
					c.add(Calendar.YEAR, 14);
					Date newDate = c.getTime();
					if (dischargeDate1.before(newDate)) {
						errorsList.add("<li>" + "Date of Discharge / Probable Discharge Date should be minimum 14 years greater than Date of Birth" + ".</li>");
						errorField.add("dischargeDate");
					}
				}
			}
			if (Strings.isBlank(registrationBean.getPpoNumber())) {
				errorsList.add("<li>" + "Please enter PPO Number" + ".</li>");
				errorField.add("ppoNumber");
			} else if (!Pattern.matches("[a-zA-Z0-9 &-.,\\\\/]{1,50}", registrationBean.getPpoNumber())) {
				errorsList.add("<li>" + "Please enter valid PPO Number" + ".</li>");
				errorField.add("ppoNumber");
			}
		}
	}

	public void validatePassword(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(registrationBean.getNewPassword())) {
			errorsList.add("<li>" + "Please enter Password" + ".</li>");
			errorField.add("newPassword");
		} else {
			if (registrationBean.getNewPassword().trim().length() < 8 && registrationBean.getNewPassword().trim().length() > 12) {
				errorsList.add("<li>" + "Password must be minimum 8 characters and maximum 12 characters" + ".</li>");
				errorField.add("newPassword");
			} else if (!Pattern.matches("^(?=.{8,12}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&])(?=.*[0-9]).*$", registrationBean.getNewPassword())) { /* if(!ValidatorUtil.isValidPaasword(registrationBean.getNewPassword())) { */
				errorsList.add("<li>"
						+ "Please enter valid Password. Password must contain at least one Upper case character (A-Z), one Lower case character (a-z), one Number (0-9) "
						+ "and one Special character (!@#&)."
						+ "</li>");
				errorField.add("newPassword");
			}
		}
	}

	public void validateConfirmPassword(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(registrationBean.getConfirmPassword())) {
			errorsList.add("<li>" + "Please enter Confirm Password" + ".</li>");
			errorField.add("confirmPassword");
		} else {
			if (registrationBean.getConfirmPassword().trim().length() < 8 && registrationBean.getConfirmPassword().trim().length() > 12) {
				errorsList.add("<li>" + "Confirm Password must be minimum 8 characters and maximum 12 characters." + ".</li>");
				errorField.add("confirmPassword");
			} else {
				if (registrationBean.getConfirmPassword() != null && registrationBean.getNewPassword() != null
						&& !registrationBean.getConfirmPassword().trim().equals(registrationBean.getNewPassword().trim())) {
					errorsList.add("<li>" + "Password and Confirm Password must be same" + ".</li>");
					errorField.add("confirmPassword");
				}
				if (!Pattern.matches("^(?=.{8,12}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&])(?=.*[0-9]).*$", registrationBean.getConfirmPassword())) { // if(!ValidatorUtil. isValidPaasword( registrationBean. getConfirmPassword())) {
					errorsList.add("<li>"
							+ "Please enter valid Confirm Password. Password must contain at least one Upper case character (A-Z), one Lower case character (a-z), one Number (0-9) "
							+ "and one Special character (!@#&)."
							+ "</li>");
					errorField.add("confirmPassword");
				}
			}
		}
	}

	public void validateCaptcha(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isNotBlank(registrationBean.getCaptcha())) {
			if (ValidatorUtil.isEmpty(registrationBean.getCaptcha().trim())) {
				errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CAPTCHA_REQD) + ".</li>");
				errorField.add("registerCaptcha");
			}
		} else {
			errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CAPTCHA_REQD) + ".</li>");
			errorField.add("registerCaptcha");
		}
	}

	public void validateBothEmails(RegistrationBean registrationBean, List<String> errorsList) {
		if (Strings.isNotBlank(registrationBean.getEmailAddress()) && Strings.isNotBlank(registrationBean.getConfirmEmailAddress())) {
			if (!registrationBean.getEmailAddress().toLowerCase().equals(registrationBean.getConfirmEmailAddress().toLowerCase())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CONFIRMEMAIL_EMAIL_REQD));
			}
		}
	}

	public void validateConfirmEmailAddress(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isNotBlank(registrationBean.getConfirmEmailAddress())) {
			validateValidConfirmEmailAddress(registrationBean, errorsList, errorField);
		} else {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CONFIRM_EMAIL_REQD));
		}
	}

	public void validateValidConfirmEmailAddress(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isNotBlank(registrationBean.getConfirmEmailAddress())) {
			if (EmailValidator.validate(registrationBean.getConfirmEmailAddress())) {
			} else {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_CONFIRMEMAIL));
			}
		}
	}

	public void validateEmailAddress(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isNotBlank(registrationBean.getEmailAddress())) {
			validateValidEmailAddress(registrationBean, errorsList, errorField);
		} else {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.EMAIL_REQD));
		}

	}

	public void validateValidEmailAddress(RegistrationBean registrationBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isNotBlank(registrationBean.getEmailAddress())) {
			if (!EmailValidator.validate(registrationBean.getEmailAddress())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_EMAIL));
			}
		}
	}

	public void validateCandidateLastName(RegistrationBean registrationBean, List<String> errorsList) {
		if (!Strings.isBlank(registrationBean.getCandidateLastName())) {
			if (!Pattern.matches("[a-zA-Z ]{1,50}", registrationBean.getCandidateLastName()))// for
			{
				errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VALID_LAST_NAME_REQD) + "</li>");
			}
		}
	}

	public void validateCandidateMiddleName(RegistrationBean registrationBean, List<String> errorsList) {
		if (!Strings.isBlank(registrationBean.getCandidateMiddleName())) {
			if (!Pattern.matches("[a-zA-Z ]{1,50}", registrationBean.getCandidateMiddleName()))// for
			{
				errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VALID_MIDDLE_NAME_REQD) + "</li>");
			}
		}
	}

	public void validateCategory(RegistrationBean registrationBean, List<String> errorsList) {
		if (Strings.isBlank(registrationBean.getCategoryValDesc())) {
			errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CATEGORY_REQD) + "</li>");
		}

	}

}
