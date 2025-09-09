package com.nseit.generic.util.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
// @AllArgsConstructor
// @Slf4j
@NoArgsConstructor

public class PersonalBeanValidator {

	private Logger logger = LoggerHome.getLogger(getClass());

	private CandidateBean candidateBean;
	private String type;

	public String validatePersonalDetails(String type) {
		return formDbBasedValidation(candidateBean, type);
	}

	private String formDbBasedValidation(CandidateBean candidateBean, String type) {
		String errors = "";
		List<String> errorsList = new ArrayList<String>();
		ArrayList<String> errorField = new ArrayList<String>();
		if (candidateBean == null) {
			errorsList.add("<li>" + "Please fill the form" + "</li>");
			return errorsList.toString();
		} else {
			validateParentOrGuardianSelect(candidateBean, errorsList, errorField, type);
			validateMaritalStatusSelect(candidateBean, errorsList, errorField, type);
			validateNativitySelect(candidateBean, errorsList, errorField, type);
			validateGenderNullCheck(candidateBean, errorsList, errorField, type);
			validateReligionSelect(candidateBean, errorsList, errorField, type);
			validateGovtService(candidateBean, errorsList, errorField, type);
			
			if (Strings.isBlank(candidateBean.getPhotoIDProof1())) {
				errorsList.add("<li>Please select Photo ID Proof.</li>");
				errorField.add("photoIDProof1");
			} 
			if (Strings.isBlank(candidateBean.getPhotoIDProof1Val())) {
				errorsList.add("<li>Please enter Photo ID Proof Number.</li>");
				errorField.add("photoIDProof1Val");
			}else if (!Pattern.matches("^[a-zA-Z0-9 &.,\\-/\\\\]{1,50}$", candidateBean.getPhotoIDProof1Val().trim())) {
				errorsList.add("<li>"+"Please enter valid Photo ID Proof Number."+"</li>");
				errorField.add("photoIDProof1Val");
			}
			//validateAge(candidateBean, errorsList, errorField, type);
			validateAddress(candidateBean, errorsList, errorField, type);
			validateAltAddress(candidateBean, errorsList, errorField, type);
			validateMotherTongue(candidateBean, errorsList, errorField);
			if (errorsList != null && !errorsList.isEmpty()) {
				errors = ValidatorUtil.validationMessageFormatter(errorsList);
				candidateBean.setErrorField(errorField);
			}
			return errors;
		}
	}

	public void validateName(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getPersonalDetailsBean().getCandidateFirstName())) {
			errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.FIRST_NAME_REQD) + "</li>");
			errorField.add("candidateFirstName");
		} else
			validateValidPatternForFirstName(candidateBean, errorsList, errorField);
	}

	public void validateValidPatternForFirstName(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (!Pattern.matches("[a-zA-Z ]{1,150}", candidateBean.getPersonalDetailsBean().getCandidateFirstName().trim())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VALID_FIRST_NAME_REQD));
			errorField.add("candidateFirstName");
		}
	}

	public void validateDobNullCheck(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (Strings.isBlank(candidateBean.getDateOfBirth())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_DOB));
			errorField.add("dateOfBirth");
		} else {
			dateFieldNanValidation(candidateBean.getDateOfBirth(), errorsList, errorField,
					ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_INVALID_DOB), "dateOfBirth", type);
		}
	}

	public void dateFieldNanValidation(String date, List<String> errorsList, ArrayList<String> errorField, String message, String fieldName, String type) {
		if (date.toLowerCase().contains("nan") || date.toLowerCase().contains("undefined")) {
			errorsList.add(message);
			errorField.add(fieldName);
		} else {
			dateRangeValidation(candidateBean.getDateOfBirth(), errorsList, errorField,
					ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_INVALID_DOB), type);
		}
	}
	
	public void dateRangeValidation(String date, List<String> errorsList, ArrayList<String> errorField, String message, String type) {
		try {
			Date dateofbirth = new SimpleDateFormat("dd-MM-yyyy").parse(candidateBean.getDateOfBirth());
			Date maxDate = new SimpleDateFormat("dd-MMM-yyyy").parse("01-Jul-2006");
			Date minDate = new SimpleDateFormat("dd-MMM-yyyy").parse("01-Jul-1965");

			if (dateofbirth.after(maxDate) || dateofbirth.before(minDate)) {
				errorsList.add("<li>" + "Date Of Birth should be between 01-Jul-1965 and 01-Jul-2006." + "</li>");
				errorField.add("dateOfBirth");
			} else {
				validateAge(candidateBean, errorsList, errorField, type);
			}
		} catch (ParseException e) {
			logger.fatal("ParseException in dateRangeValidation" + e);
		}
	}

	public void validateAge(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (type.equals("form")) {
			if (Strings.isBlank(candidateBean.getAgeInYears()) || candidateBean.getAgeInYears().toLowerCase().contains("nan")
					|| candidateBean.getAgeInYears().toLowerCase().contains("undefined") || Strings.isBlank(candidateBean.getAgeInMonths())
					|| candidateBean.getAgeInMonths().toLowerCase().contains("nan") || candidateBean.getAgeInMonths().toLowerCase().contains("undefined")
					|| Strings.isBlank(candidateBean.getAgeInDays()) || candidateBean.getAgeInDays().toLowerCase().contains("nan")
					|| candidateBean.getAgeInDays().contains("undefined")) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.AGE_REQUIRED));
				errorField.add("ageInYears");
				errorField.add("ageInMonths");
				errorField.add("ageInDays");
			} else if (!ValidatorUtil.isNumeric(candidateBean.getAgeInYears()) || !ValidatorUtil.isNumeric(candidateBean.getAgeInMonths())
					|| !ValidatorUtil.isNumeric(candidateBean.getAgeInDays())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_AGE));
				errorField.add("ageInYears");
				errorField.add("ageInMonths");
				errorField.add("ageInDays");
			}
		} else {
			if (Strings.isBlank(candidateBean.getPersonalDetailsBean().getAge())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.AGE_REQUIRED));
			}
		}
	}

	public void validateGenderNullCheck(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (type.equals("form")) {
			if (Strings.isBlank(candidateBean.getGenderValDesc())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.GENDER_REQD));
				errorField.add("gender");
			}
		} else {
			if (Strings.isBlank(candidateBean.getGenderVal())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.GENDER_REQD));
				errorField.add("gender");
			}
		}
	}

	public void validateMaritalStatusSelect(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (Strings.isBlank(candidateBean.getMariatalStatus())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_MARITAL_STATUS_REQD));
			errorField.add("mariatalStatus");
		}else if (candidateBean.getMariatalStatus().equals(type.equals("form") ? "6" : "Married") && Strings.isBlank(candidateBean.getSpouseName())) {
			errorsList.add("<li>"+"Please enter Name of Spouse."+"</li>");
			errorField.add("spouseName");
		}else if (Strings.isNotBlank(candidateBean.getSpouseName()) && !Pattern.matches("[a-zA-Z.' ]{1,75}", candidateBean.getSpouseName().trim())) {
			errorsList.add("<li>"+"Please enter valid Name of Spouse."+"</li>");
			errorField.add("spouseName");
		}
		else if (Strings.isNotBlank(candidateBean.getSpouseName()) && Pattern.matches("\\.+", candidateBean.getSpouseName().trim())) {
			errorsList.add("<li>"+"Please enter valid Name of Spouse."+"</li>");
			errorField.add("spouseName");
		}
	}
	
	public void validateParentOrGuardianSelect(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (type.equals("form")) {
			if (candidateBean.getParentAndGuardian() != 0 && Optional.ofNullable(candidateBean.getParentAndGuardian()).isPresent()) {
				if (candidateBean.getParentAndGuardian() == 220) {
					validateFathersName(candidateBean, errorsList, errorField);
					validateMothersName(candidateBean, errorsList, errorField);
				}
				if (candidateBean.getParentAndGuardian() == 221) {
					validateGuardianName(candidateBean, errorsList, errorField);
				}
			}else {
				errorsList.add("<li>"+"Please select Would you like to give Father and Mother name or Guardian name."+"</li>");
				errorField.add("parentAndGuardian");
			}
		} else {
			if (Strings.isNotBlank(candidateBean.getParentAndGuardianVal())) {
				if (candidateBean.getParentAndGuardianVal() == "220") {
					validateFathersName(candidateBean, errorsList, errorField);
					validateMothersName(candidateBean, errorsList, errorField);
				}
				if (candidateBean.getParentAndGuardianVal() == "221") {
					validateGuardianName(candidateBean, errorsList, errorField);
				}
			}else {
				errorsList.add("<li>"+"Please select Would you like to give Father and Mother name or Guardian name."+"</li>");
				errorField.add("parentAndGuardian");
			}
		}
	}

	public void validateFathersName(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getFathersName())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.FATHER_FIRST_NAME_REQD));
			errorField.add("fathersName");
		} else if (!Pattern.matches("[a-zA-Z.' ]{1,75}", candidateBean.getFathersName().trim())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_FATHER_NAME));
			errorField.add("fathersName");
		}
	}

	public void validateFathersInitial(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isNotBlank(candidateBean.getFathersInitial())) {
			if (!Pattern.matches("[a-zA-Z ]{1,6}", candidateBean.getFathersInitial().trim())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.FATHER_LAST_NAME_REQD));
				errorField.add("fathersInitial");
			}
		}
	}

	public void validateMothersName(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getMothersName())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.MOTHER_FIRST_NAME_REQD));
			errorField.add("mothersName");
		} else if (!Pattern.matches("[a-zA-Z.' ]{1,75}", candidateBean.getMothersName().trim())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ENROLL_MOTHER_NAME));
			errorField.add("mothersName");
		}
	}
	
	public void validateMothersInitial(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isNotBlank(candidateBean.getMothersInitial())) {
			if (!Pattern.matches("[a-zA-Z ]{1,6}", candidateBean.getMothersInitial().trim())) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.MOTHER_LAST_NAME_REQD));
				errorField.add("mothersInitial");
			}
		}
	}
	
	public void validateGuardianName(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getGuardianName())) {
			errorsList.add("<li>"+"Please enter Guardian’s Name."+"</li>");
			errorField.add("guardianName");
		} else if (!Pattern.matches("[a-zA-Z.' ]{1,75}", candidateBean.getGuardianName().trim())) {
			errorsList.add("<li>"+"Please enter valid Guardian’s Name."+"</li>");
			errorField.add("guardianName");
		}
	}

	public void validateReligionSelect(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (Strings.isBlank(candidateBean.getReligionBelief())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_RELIGION_REQD));
			errorField.add("religionBelief");
		} else if (candidateBean.getReligionBelief().equals(type.equals("form") ? "153" : "Other")) {
			validateOtherReligion(candidateBean, errorsList, errorField);
		}
	}
	
	
	public void validateNativitySelect(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (Strings.isBlank(candidateBean.getNativity())) {
			errorsList.add("<li>"+"Please select Nativity."+"</li>");
			errorField.add("nativity");
		} else if (candidateBean.getNativity().equals(type.equals("form") ? "39" : "Other")) {
			validateOtherNativity(candidateBean, errorsList, errorField);
		}
	}
	
	public void validateOtherNativity(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getOtherNativity())) {
			errorsList.add("<li>"+"Please select Other Native."+"</li>");
			errorField.add("otherNativity");
		}
	}
	
	public void validateGovtService(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (Strings.isBlank(candidateBean.getIsGovtService())) {
			errorsList.add("<li>"+"Please select Are you already in Government Service?"+"</li>");
			errorField.add("isGovtService");
		} else if (candidateBean.getIsGovtService().equals(type.equals("form") ? "6" : "Yes")) {
			validateChkBox(candidateBean, errorsList, errorField);
			validateOtherGovtService(candidateBean, errorsList, errorField);
		}
	}
	
	public void validateChkBox(CandidateBean candidateBean2, List<String> errorsList, ArrayList<String> errorField) {
		if (!candidateBean.isGovtServChkBox()) {
			errorsList.add("<li>"
					+ "Please check 'I agree to provide No Objection Certificate from the concerned Government Department at the time of Certificate Verification' declaration checkbox."
					+ "</li>");
			errorField.add("govtServChkBox");
		}
	}

	public void validateOtherGovtService(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getOrgName())) {
			errorsList.add("<li>"+"Please enter Department Name."+"</li>");
			errorField.add("orgName");
		}else if (!Pattern.matches("[a-zA-Z0-9 .]{1,50}", candidateBean.getOrgName().trim())) {
			errorsList.add("<li>"+"Please enter valid Department Name."+"</li>");
			errorField.add("orgName");
		}
		else if (Strings.isNotBlank(candidateBean.getOrgName()) && Pattern.matches("[0-9.]+", candidateBean.getOrgName().trim())) {
			errorsList.add("<li>"+"Please enter valid Department Name."+"</li>");
			errorField.add("orgName");
		}else if (Strings.isNotBlank(candidateBean.getOrgName()) && Pattern.matches("\\d+", candidateBean.getOrgName().trim())) {
			errorsList.add("<li>"+"Please enter valid Department Name."+"</li>");
			errorField.add("orgName");
		}
		if (Strings.isBlank(candidateBean.getCurrentDesig())) {
			errorsList.add("<li>"+"Please enter Current Designation."+"</li>");
			errorField.add("currentDesig");
		}else if (!Pattern.matches("[a-zA-Z0-9 .]{1,50}", candidateBean.getCurrentDesig().trim())) {
			errorsList.add("<li>"+"Please enter valid Current Designation."+"</li>");
			errorField.add("currentDesig");
		}
		else if (Strings.isNotBlank(candidateBean.getCurrentDesig()) && Pattern.matches("[0-9.]+", candidateBean.getCurrentDesig().trim())) {
			errorsList.add("<li>"+"Please enter valid Current Designation."+"</li>");
			errorField.add("currentDesig");
		}else if (Strings.isNotBlank(candidateBean.getCurrentDesig()) && Pattern.matches("\\d+", candidateBean.getCurrentDesig().trim())) {
			errorsList.add("<li>"+"Please enter valid Current Designation."+"</li>");
			errorField.add("currentDesig");
		}
		if (Strings.isBlank(candidateBean.getPlaceOfWork())) {
			errorsList.add("<li>"+"Please enter Place of Work."+"</li>");
			errorField.add("placeOfWork");
		}else if (!Pattern.matches("[a-zA-Z0-9 .]{1,50}", candidateBean.getPlaceOfWork().trim())) {
			errorsList.add("<li>"+"Please enter valid Place of Work."+"</li>");
			errorField.add("placeOfWork");
		}
		else if (Strings.isNotBlank(candidateBean.getPlaceOfWork()) && Pattern.matches("[0-9.]+", candidateBean.getPlaceOfWork().trim())) {
			errorsList.add("<li>"+"Please enter valid Place of Work."+"</li>");
			errorField.add("placeOfWork");
		}else if (Strings.isNotBlank(candidateBean.getPlaceOfWork()) && Pattern.matches("\\d+", candidateBean.getPlaceOfWork().trim())) {
			errorsList.add("<li>"+"Please enter valid Place of Work."+"</li>");
			errorField.add("placeOfWork");
		}
		if(StringUtils.isBlank(candidateBean.getGovtDate()))
		{
			errorsList.add("<li>"+"Please select Date of Joining in the Service."+"</li>");
			errorField.add("govtDate");
		}
	}

	public void validateOtherReligion(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getReligionBeliefOthers())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_OTHER_RELIGION_REQD));
			errorField.add("religionBeliefOthers");
		} else if (!Pattern.matches("[a-zA-Z ]{1,20}", candidateBean.getReligionBeliefOthers().trim())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_OTHER_RELIGION_INVALID));
			errorField.add("religionBeliefOthers");
		}
	}

	public void validateCategoryForReligionAndCert(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (candidateBean.getCategorycertificate().equals(type.equals("form") ? "6" : "Yes")) {
			if (candidateBean.getReligionBelief().equals(type.equals("form") ? "151" : "Muslim")) {
				if (!candidateBean.getCategoryValDesc().equals(type.equals("form") ? "2" : "BC-Muslim")) {
					errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_RELIGION_COMMUNITY_MISMATCH));
					errorField.add("categoryValDesc");
				}
			} else {
				if (candidateBean.getCategoryValDesc().equals(type.equals("form") ? "2" : "BC-Muslim")) {
					errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_RELIGION_COMMUNITY_MISMATCH));
					errorField.add("categoryValDesc");
				}
			}
		} else {
			if (!candidateBean.getCategoryValDesc().equals(type.equals("form") ? "7" : "OC")) {
				errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_CERT_COMMUNITY_MISMATCH));
				errorField.add("categoryValDesc");
			}
		}
	}

	public void validatePhDisability(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (!Strings.isBlank(candidateBean.getBenchmarkDisability()) && Arrays.asList("6", "Yes").contains(candidateBean.getBenchmarkDisability())) {
			validateDisabilityType(candidateBean, errorsList, errorField);
		} else {
			validateDisability(candidateBean, errorsList, errorField);
		}
	}

	public void validateDisabilityType(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getDisabilityType())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_DISABILITY_TYPE));
			errorField.add("disabilityType");
		}
	}

	public void validateDisability(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getBenchmarkDisability())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_DISABILITY));
			errorField.add("benchmarkDisability");
		}
	}

	public void validateNationality(CandidateBean candidateBean2, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isNotBlank(candidateBean.getNationality()) && !Arrays.asList("4", "Indian").contains(candidateBean.getNationality())) {
			errorsList.add("<li>" + "Other Nation Citizens are not allowed." + "</li>");
			errorField.add("nationality");
		} else
			validateNationalityNullCheck(errorsList, errorField);
	}

	public void validateNationalityNullCheck(List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getNationality())) {
			errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.CANDIDATE_NATIONALITY_REQD) + "</li>");
			errorField.add("nationality");
		}
	}

	public void validateAddress(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (candidateBean.isAddressChkBox()) {
			candidateBean.copyPermenantToComm();
		}
		validateAddressField1(candidateBean, errorsList, errorField);
		validateStateNull(candidateBean, errorsList, errorField);
		validateDistrictNull(candidateBean, errorsList, errorField);
		validateCityName(candidateBean, errorsList, errorField);
		validatePincodeNullCheck(candidateBean, errorsList, errorField);
	}

	public void validateAddressField1(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getAddressBean().getAddressFiled1())) {
			errorsList.add("<li>" + "Please enter Permanent Address." + "</li>");
			errorField.add("addressFiled1");
		} else {
			validateValidAddressField1(candidateBean, errorsList, errorField);
		}
	}

	public void validateValidAddressField1(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (!ValidatorUtil.validatePatternWithMinMax("[a-zA-Z0-9 &.,\\-/\\\\]", candidateBean.getAddressBean().getAddressFiled1(), "1", "250")) {
			errorsList.add("<li>" + "Please enter a valid Permanent Address. Only comma (,), ampersand (&), hyphen (-), "
					+ "back and forward slash (\\ /), and dot (.) special characters are allowed." + "</li>");
			errorField.add("addressFiled1");
		}
	}

	public void validateAddressField2(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isNotBlank(candidateBean.getAddressBean().getAddressFiled2())) {
			if (!ValidatorUtil.validatePatternWithMinMax("[a-zA-Z0-9 &.,\\\\/]", candidateBean.getAddressBean().getAddressFiled2(), "1", "255")) {
				errorsList.add("<li>"
						+ "Please enter valid Permanent Address Line 2. Only comma (,), ampersand(&), back and forward slash(/ \\) and dot (.) special characters are allowed."
						+ "</li>");
				errorField.add("addressFiled2");
			}
		}
	}

	public void validateCityName(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getAddressBean().getCityName())) {
			errorsList.add("<li>" + "Please enter Permanent Address - City / Village." + "</li>");
			errorField.add("cityName");
		} else {
			validateCityNameValidity(candidateBean, errorsList, errorField);
		}
	}

	public void validateCityNameValidity(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (!ValidatorUtil.validatePatternWithMinMax("[a-zA-Z0-9 ,._-]", candidateBean.getAddressBean().getCityName(), "1", "50")) {
			errorsList.add("<li>" + "Please enter valid Permanent Address - City / Village. Only comma (,), dot (.), underscore (_) and hyphen (-) special characters are allowed." + "</li>");
			errorField.add("cityName");
		}
	}

	public void validateStateNull(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getStateVal())) {
			errorsList.add("<li>" + "Please select Permanent Address - State." + "</li>");
			errorField.add("stateList");
		}
	}

	public void validateDistrictNull(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if(candidateBean.getStateVal().equals("1") || candidateBean.getStateVal().equals("Tamil Nadu"))
		{
			if (Strings.isBlank(candidateBean.getDistrictVal())) {
				errorsList.add("<li>" + "Please select Permanent Address - District." + "</li>");
				errorField.add("districtList");
			}
			candidateBean.setDistrictValother("");
		} else
			if(!candidateBean.getStateVal().equals("1") && !candidateBean.getStateVal().equals("Tamil Nadu"))
			{
				if (Strings.isBlank(candidateBean.getDistrictValother())) {
					errorsList.add("<li>" + "Please enter Permanent Address - District." + "</li>");
					errorField.add("districtValother");
				}else if (!ValidatorUtil.validatePatternWithMinMax("[a-zA-Z ]", candidateBean.getDistrictValother(), "1", "50")) {
					errorsList.add("<li>" + "Please enter valid Permanent Address - District." + "</li>");
					errorField.add("districtValother");
				}
				candidateBean.setDistrictVal("");
			}
	}
	
	public void validatePincodeNullCheck(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getAddressBean().getPinCode())) {
			errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.PINCODE_REQD) + "</li>");
			errorField.add("pinCode");
		} else {
			validatePinCode(candidateBean, errorsList, errorField);
		}
	}

	public void validatePinCode(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (candidateBean.getAddressBean().getPinCode().length() > 6 || candidateBean.getAddressBean().getPinCode().length() < 6) {
			errorsList.add("<li>" + "Permanent Address - Pincode must be 6 digits." + "</li>");
			errorField.add("pinCode");
		} else {
			int index = candidateBean.getAddressBean().getPinCode().indexOf("0");
			int counter = 0;
			while (index >= 0) {

				index = candidateBean.getAddressBean().getPinCode().indexOf("0", index + 1);
				counter++;
			}
			if (counter == candidateBean.getAddressBean().getPinCode().length()) {
				errorsList.add("<li>" + "Permanent Address - Pincode cannot be 0." + "</li>");
				errorField.add("pinCode");
			}
		}
	}
	
	public void validateTelephoneNo(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		// if(type.equals("form")) {
		if (Strings.isNotBlank(candidateBean.getAddressBean().getTelephoneNo1())) {
			validateTelephone1(candidateBean, errorsList, errorField);
			if (Strings.isBlank(candidateBean.getAddressBean().getTelephoneNo2())) {
				errorsList.add("<li>" + "Please enter Permanent Address - Telephone Number Minimum 1 digit and maximum 8 digits required in second field. " + "</li>");
				errorField.add("telephoneNo2");
			}
		}

		if (Strings.isNotBlank(candidateBean.getAddressBean().getTelephoneNo2())) {
			validateTelephone2(candidateBean, errorsList, errorField);
			if (Strings.isBlank(candidateBean.getAddressBean().getTelephoneNo1())) {
				errorsList.add("<li>" + "Please enter Permanent Address - Telephone Number Minimum 1 digit and maximum 4 digits required in first field. " + "</li>");
				errorField.add("telephoneNo1");
			}
		}
	}

	public void validateTelephone1(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (candidateBean.getAddressBean().getTelephoneNo1().length() > 4 || candidateBean.getAddressBean().getTelephoneNo1().length() < 1) {
			errorsList.add("<li>" + "Please enter valid Permanent Address - Telephone Number Minimum 1 digit and maximum 4 digits required in first field. " + "</li>");
			errorField.add("telephoneNo1");
			errorField.add("telephoneNo2");
		}
	}

	public void validateTelephone2(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (candidateBean.getAddressBean().getTelephoneNo2().length() > 8 || candidateBean.getAddressBean().getTelephoneNo2().length() < 1) {
			errorsList.add("<li>" + "Please enter valid Permanent Address - Telephone Number Minimum 1 digit and maximum 8 digits required in second field. " + "</li>");
			errorField.add("telephoneNo1");
			errorField.add("telephoneNo2");

		}
	}

	public void validateAllZeroInTele(CandidateBean candidateBean2, List<String> errorsList, ArrayList<String> errorField) {

		boolean falg = true;

		if (Arrays.asList("0", "00", "000", "0000").contains(candidateBean.getAddressBean().getTelephoneNo1())) {
			errorsList.add("<li>" + "Permanent Address - Telephone Number is invalid" + "</li>");
			errorField.add("telephoneNo1");
			falg = false;
		}

		if (Arrays.asList("0", "00", "000", "0000", "00000", "000000", "0000000", "00000000").contains(candidateBean.getAddressBean().getTelephoneNo2())) {
			if (falg)
				errorsList.add("<li>" + "Permanent Address - Telephone Number is invalid" + "</li>");
			errorField.add("telephoneNo2");
		}
	}

	public void validateAltAddress(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		if (!candidateBean.isAddressChkBox()) {
			validateAlternateAddressField1NullCheck(candidateBean, errorsList, errorField);
			validateAltStateValNullCheck(candidateBean, errorsList, errorField);
			validateAltDistrictNull(candidateBean, errorsList, errorField);
			validateAlternateCityNameOther(candidateBean, errorsList, errorField);
			validateAltPinCodeNullCheck(candidateBean, errorsList, errorField);
		}
	}

	public void validateAlternateAddressField1NullCheck(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getAddressBean().getAlternateAddressFiled1())) {
			errorsList.add("<li>" + "Please enter Correspondence Address." + "</li>");
			errorField.add("alternateAddressFiled1");
		} else {
			validateValidAltAddressField1(candidateBean, errorsList, errorField);
		}
	}

	public void validateValidAltAddressField1(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (!ValidatorUtil.validatePatternWithMinMax("[a-zA-Z0-9 &.,\\-/\\\\]", candidateBean.getAddressBean().getAlternateAddressFiled1(), "1", "250")) {
			errorsList.add("<li>" + "Please enter a valid Correspondence Address. Only comma (,), ampersand (&), hyphen (-), "
					+ "back and forward slash (\\ /), and dot (.) special characters are allowed." + "</li>");
			errorField.add("alternateAddressFiled1");
		}
	}

	public void validateAlternateAddressField2(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isNotBlank(candidateBean.getAddressBean().getAlternateAddressFiled2())) {
			if (!ValidatorUtil.validatePatternWithMinMax("[a-zA-Z0-9 &.,\\\\/]", candidateBean.getAddressBean().getAlternateAddressFiled2(), "1", "255")) {
				errorsList.add("<li>"
						+ "Please enter valid Correspondence Address Line 2. Only comma (,), ampersand(&), back and forward slash(/ \\) and dot (.) special characters are allowed."
						+ "</li>");
				errorField.add("alternateAddressFiled2");
			}
		}
	}

	public void validateAlternateCityNameOther(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getAddressBean().getAlternateCity())) {
			errorsList.add("<li>" + "Please enter Correspondence Address - City / Village." + "</li>");
			errorField.add("alternateCityother");
		} else {
			validateAltCityNameValidity(candidateBean, errorsList, errorField);
		}
	}

	public void validateAltCityNameValidity(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (!ValidatorUtil.validatePatternWithMinMax("[a-zA-Z0-9 ,._-]", candidateBean.getAddressBean().getAlternateCity(), "1", "50")) {
			errorsList.add("<li>" + "Please enter valid Correspondence Address - City / Village. Only comma (,), dot (.), underscore (_) and hyphen (-) special characters are allowed." + "</li>");
			errorField.add("alternateCityother");
		}
	}

	public void validateAltStateValNullCheck(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getAltStateVal())) {
			errorsList.add("<li>" + "Please select Correspondence Address - State." + "</li>");
			errorField.add("altStateList");
		}
	}

	public void validateAltDistrictNull(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if(candidateBean.getAltStateVal().equals("1") || candidateBean.getAltStateVal().equals("Tamil Nadu"))
		{
			if (Strings.isBlank(candidateBean.getAltDistrictVal())) {
				errorsList.add("<li>" + "Please select Correspondence Address - District." + "</li>");
				errorField.add("altDistrictList");
			}
			candidateBean.setAltDistrictValOthers("");
		} else
			if(!candidateBean.getAltStateVal().equals("1") && !candidateBean.getAltStateVal().equals("Tamil Nadu"))
			{
				if (Strings.isBlank(candidateBean.getAltDistrictValOthers())) {
					errorsList.add("<li>" + "Please enter Correspondence Address - District." + "</li>");
					errorField.add("altDistrictValOthers");
				}else if (!ValidatorUtil.validatePatternWithMinMax("[a-zA-Z ]", candidateBean.getAltDistrictValOthers(), "1", "50")) {
					errorsList.add("<li>" + "Please enter valid Correspondence Address - District." + "</li>");
					errorField.add("altDistrictValOthers");
				}
				candidateBean.setAltDistrictVal("");
			}
	}
	
	public void validateAltPinCodeNullCheck(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getAddressBean().getAlternatePinCode())) {
			errorsList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ALTERNATE_PINCODE_REQD) + "</li>");
			errorField.add("alternatePinCode");
		} else {
			validateAltPinCode(candidateBean, errorsList, errorField);
		}
	}

	public void validateAltPinCode(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (candidateBean.getAddressBean().getAlternatePinCode().length() > 6 || candidateBean.getAddressBean().getAlternatePinCode().length() < 6) {
			errorsList.add("<li>" + "Correspondence Address - Pincode must be 6 digits." + "</li>");
			errorField.add("alternatePinCode");
		} else {
			int index = candidateBean.getAddressBean().getAlternatePinCode().indexOf("0");
			int counter = 0;
			while (index >= 0) {

				index = candidateBean.getAddressBean().getAlternatePinCode().indexOf("0", index + 1);
				counter++;
			}
			if (counter == candidateBean.getAddressBean().getAlternatePinCode().length()) {
				errorsList.add("<li>" + "Correspondence Address - Pincode cannot be 0." + "</li>");
				errorField.add("alternatePinCode");
			}
		}
	}
	
	public void validateMotherTongue(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (Strings.isBlank(candidateBean.getMotherTongue())) {
			errorsList.add("<li>" + "Please select Mother Tongue." + "</li>");
			errorField.add("motherTongue");
		} 
	}

	public void validateAltTelephoneNo(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField, String type) {
		// if(type.equals("form")) {
		if (Strings.isNotBlank(candidateBean.getAddressBean().getAltTelephoneNo1())) {
			validateAltTelephone1(candidateBean, errorsList, errorField);
			if (Strings.isBlank(candidateBean.getAddressBean().getAltTelephoneNo2())) {
				errorsList.add("<li>" + "Please enter Communication Address - Telephone Number Minimum 1 digit and maximum 8 digits required in second field. " + "</li>");
				errorField.add("altTelephoneNo2");
			}
		}
		if (Strings.isNotBlank(candidateBean.getAddressBean().getAltTelephoneNo2())) {
			validateAltTelephone2(candidateBean, errorsList, errorField);
			if (Strings.isBlank(candidateBean.getAddressBean().getAltTelephoneNo1())) {
				errorsList.add("<li>" + "Please enter Communication Address - Telephone Number Minimum 1 digit and maximum 4 digits required in first field. " + "</li>");
				errorField.add("altTelephoneNo2");
			}
		}
		// }else{
		// if (Strings.isBlank(candidateBean.getAddressBean().getAltTelephoneNo())) {
		// errorsList.add("<li>"
		// + "Please enter Communication Address - Telephone No."
		// + "</li>");
		// }
		// }
	}

	public void validateAltTelephone2(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (candidateBean.getAddressBean().getAltTelephoneNo2().length() > 8 || candidateBean.getAddressBean().getAltTelephoneNo2().length() < 1) {
			errorsList.add("<li>" + "Please enter valid Communication Address - Telephone Number Minimum 1 digit and maximum 8 digits required in second field. " + "</li>");
			errorField.add("altTelephoneNo1");
			errorField.add("altTelephoneNo2");
		}
	}

	public void validateAltTelephone1(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (candidateBean.getAddressBean().getAltTelephoneNo1().length() > 4 || candidateBean.getAddressBean().getAltTelephoneNo1().length() < 1) {
			errorsList.add("<li>" + "Please enter valid Communication Address - Telephone Number Minimum 1 digit and maximum 4 digits required in first field. " + "</li>");
			errorField.add("altTelephoneNo1");
			errorField.add("altTelephoneNo2");

		}
	}

	public void validateAllZeroInAltTele(CandidateBean candidateBean2, List<String> errorsList, ArrayList<String> errorField) {

		boolean falg = true;

		if (Arrays.asList("0", "00", "000", "0000").contains(candidateBean.getAddressBean().getAltTelephoneNo1())) {
			errorsList.add("<li>" + "Communication Address - Telephone Number is invalid" + "</li>");
			errorField.add("altTelephoneNo1");
			falg = false;
		}

		if (Arrays.asList("0", "00", "000", "0000", "00000", "000000", "0000000", "00000000").contains(candidateBean.getAddressBean().getAltTelephoneNo2())) {
			if (falg)
				errorsList.add("<li>" + "Communication Address - Telephone Number is invalid" + "</li>");
			errorField.add("altTelephoneNo2");
		}
	}

	public void validateTestPref3(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (candidateBean.getTestCenter3() == null || (candidateBean.getTestCenter3() != null && candidateBean.getTestCenter3().trim().equals(""))) {
			errorsList.add("<li>Please select Preferred Exam Centre City -3.</li>");
			errorField.add("testCenter3");
		}
	}

	public void validateTestPref2(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (candidateBean.getTestCenter2() == null || (candidateBean.getTestCenter2() != null && candidateBean.getTestCenter2().trim().equals(""))) {
			errorsList.add("<li>Please select Preferred Exam Centre City -2.</li>");
			errorField.add("testCenter2");
		}
	}

	public void validateTestPref1(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if (candidateBean.getTestCenter1() == null || (candidateBean.getTestCenter1() != null && candidateBean.getTestCenter1().trim().equals(""))) {
			errorsList.add("<li>Please select Preferred Exam Centre City -1.</li>");
			errorField.add("testCenter1");
		}
	}

	public void validateAlreadySelectTestPref(CandidateBean candidateBean, List<String> errorsList, ArrayList<String> errorField) {
		if ((candidateBean.getTestCenter1() != null && !candidateBean.getTestCenter1().trim().equals("")) && candidateBean.getTestCenter2() != null
				&& !candidateBean.getTestCenter2().trim().equals("") && candidateBean.getTestCenter3() != null && !candidateBean.getTestCenter3().trim().equals("")) {
			if (candidateBean.getTestCenter1().equals(candidateBean.getTestCenter2())) {
				errorsList.add("<li>Preferred Exam Centre City -1 and Preferred Exam Centre City -2 cannot be same.</li>");
				errorField.add("testCenter1");
				errorField.add("testCenter2");
			}
			if (candidateBean.getTestCenter1().equals(candidateBean.getTestCenter3())) {
				errorsList.add("<li>Preferred Exam Centre City -1 and Preferred Exam Centre City -3 cannot be same.</li>");
				errorField.add("testCenter1");
				errorField.add("testCenter3");
			}

			if (candidateBean.getTestCenter2().equals(candidateBean.getTestCenter3())) {
				errorsList.add("<li>Preferred Exam Centre City -2 and Preferred Exam Centre City -3 cannot be same.</li>");
				errorField.add("testCenter2");
				errorField.add("testCenter3");
			}
		}
	}

}
