package com.nseit.generic.util.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.nseit.generic.models.AdditionalDetailsBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.EmailValidator;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Data
@ToString
//@AllArgsConstructor
@Slf4j
@NoArgsConstructor

public class AdditionalBeanValidator {
private Logger logger = LoggerHome.getLogger(getClass());

	private CandidateBean candidateBean;
	
	public String validateAdditionalDetails(CandidateBean candidateBean, Users loggedInUser) {
		return formDbBasedValidation(candidateBean,loggedInUser);
	}
	
	@SuppressWarnings("unused")
	private String formDbBasedValidation(CandidateBean candidateBean, Users loggedInUser) {
		String errors = "";
		List<String> errorsList = new ArrayList<String>();
		ArrayList<String> errorField = new ArrayList<String>();
		
		AdditionalDetailsBean additionalDetailsBean = candidateBean
		        .getAdditionalDetailsBean();
		if (additionalDetailsBean == null) {
			errorsList.add("<li>" + "" + "Please fill the form" + "</li>");
			return errorsList.toString();
		} else {
			// validation methods to come here
			validateAdvertisement(errorsList, errorField, additionalDetailsBean);
			validateAppliedInPast(errorsList, errorField, additionalDetailsBean);
			 if(Arrays.asList("6", "Yes").contains(additionalDetailsBean.getAppliedInPast())) {
				    validateYearOfApply(errorsList, errorField, additionalDetailsBean);
				    validateReasonForNotJoining(errorsList, errorField, additionalDetailsBean);
				    }
			 
			//ref1 start
			    if("1".equals(loggedInUser.getDisciplineID())) {
			    validateRef1Name(errorsList, errorField, additionalDetailsBean);
			    validateRef1Desig(errorsList, errorField, additionalDetailsBean);
			    validateRef1IsAcademician(errorsList, errorField, additionalDetailsBean);
			    validateRef1Add1(errorsList, errorField, additionalDetailsBean);
			    validateRef1State(errorsList, errorField, additionalDetailsBean);
			    validateRef1District(errorsList, errorField, additionalDetailsBean);
			    validateRef1City(errorsList, errorField, additionalDetailsBean);
			    validateRef1PinCode(errorsList, errorField, additionalDetailsBean);
			   validateRef1EmailAddress(errorsList, errorField, additionalDetailsBean);
			    validateRef1contact(errorsList, errorField, additionalDetailsBean);
			    //ref1 end
			    
			  //ref2 start
			    validateRef2Name(errorsList, errorField, additionalDetailsBean);
			    validateRef2Desig(errorsList, errorField, additionalDetailsBean);
			    validateRef2Academician(errorsList, errorField, additionalDetailsBean);
			    validateRef2Add1(errorsList, errorField, additionalDetailsBean);
			    validateRef2State(errorsList, errorField, additionalDetailsBean);
			    validateRef2District(errorsList, errorField, additionalDetailsBean);
			    validateRef2City(errorsList, errorField, additionalDetailsBean);
			    validateRef2PinCode(errorsList, errorField, additionalDetailsBean);
			   validateRef2EmailAddress(errorsList, errorField,additionalDetailsBean);
			    validateRef2Contact(errorsList, errorField, additionalDetailsBean);
			    //ref2 end
			    
			    validateSmtpOfPurpose(errorsList, errorField, additionalDetailsBean);
			    validateRefIsYesNo(errorsList, errorField, additionalDetailsBean);
			    }
		}
		
		    if (errorsList != null && !errorsList.isEmpty()) {
		        errors = ValidatorUtil.validationMessageFormatter(errorsList);
                candidateBean.setErrorField(errorField);
		      }
		    return errors;
		      }

	private void validateRefIsYesNo(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		
		
		if(Arrays.asList("7", "No").contains(additionalDetailsBean.getRef2IsAcademician()) && Arrays.asList("7", "No").contains(additionalDetailsBean.getRef1IsAcademician())) {
			errorsList.add("<li>" + "Both the fields Is he/she an academician for Referee 1 and Is he/she an academician for Referee 2 cannot be No"+"</li>");
			errorField.add("ref1IsAcademician");
			errorField.add("ref2IsAcademician");
		}
	}

	private void validateSmtpOfPurpose(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getStmtOfPurpose())) {
			errorsList.add("<li>" + "Please enter Write your \"Statement of Purpose\" in not more than 200 words indicating why you want to join the Ph.D Programme at the NCFE and what are your future research plans"+"</li>");
		    errorField.add("stmtOfPurpose");
		}
	}

	private void validateRef2Contact(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
			if(Strings.isBlank(additionalDetailsBean.getRef2Contact())) {
			errorsList.add("<li>" + "Please enter Contact for Referee 2"+"</li>");
		    errorField.add("ref2Contact");
		}
	}

	private void validateRef2EmailAddress(List<String> errorsList, ArrayList<String> errorField, AdditionalDetailsBean additionalDetailsBean) {
		if (additionalDetailsBean.getRef2EmailAddress() == null || ValidatorUtil.isEmpty(additionalDetailsBean.getRef2EmailAddress().trim())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.EMAIL_REQDREF2));
			errorField.add("ref2EmailAddress");
		} else if (!EmailValidator.validate(additionalDetailsBean.getRef2EmailAddress().trim())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_EMAILREF2));
			errorField.add("ref2EmailAddress");
		}
	}

	private void validateRef2PinCode(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef2Pincode())) {
			errorsList.add("<li>Please enter Pincode for Referee 2"+"</li>");
		    errorField.add("pinCode");
		}
		else {
			validateRef2PinCodeForZero(errorsList, errorField,additionalDetailsBean);
		}
	}
	//new code
	public void validateRef2PinCodeForZero(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if (additionalDetailsBean.getRef2Pincode().length() > 6 || additionalDetailsBean.getRef2Pincode().length() < 6) {
			errorsList.add("<li>" + "Referee 2 - Pincode must be 6 digits." + "</li>");
			errorField.add("pinCode2");
		} else {
			int index = additionalDetailsBean.getRef2Pincode().indexOf("0");
			int counter = 0;
			while (index >= 0) {

				index = additionalDetailsBean.getRef2Pincode().indexOf("0", index + 1);
				counter++;
			}
			if (counter == additionalDetailsBean.getRef2Pincode().length()) {
				errorsList.add("<li>" + "Referee 2 - Pincode cannot be 0." + "</li>");
				errorField.add("pinCode2");
			}
		}
	}

	private void validateRef2City(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef2City())) {
			errorsList.add("<li>" + "Please enter City / Village / Town for Referee 2"+"</li>");
		    errorField.add("cityNameother2");
		}
	}

	private void validateRef2District(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
			if(Strings.isBlank(additionalDetailsBean.getRef2District())) {
			errorsList.add("<li>" + "Please select District for Referee 2"+"</li>");
		    errorField.add("ref2DistrictList");
		}
	}

	private void validateRef2State(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
			if(Strings.isBlank(additionalDetailsBean.getRef2State())) {
			errorsList.add("<li>" + "Please select State / Union Territory 1 for Referee 2"+"</li>");
		    errorField.add("ref2StateList");
		}
	}

	private void validateRef2Add1(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef2Add1())) {
			errorsList.add("<li>" + "Please enter Address Line 1 for Referee 2"+"</li>");
		    errorField.add("ref2Add1");
		}
	}

	private void validateRef2Academician(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
			if(Strings.isBlank(additionalDetailsBean.getRef2IsAcademician())) {
			errorsList.add("<li>" + "Please select Is he/she an academician for Referee 2"+"</li>");
		    errorField.add("ref2IsAcademician");
		}
	}

	private void validateRef2Desig(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
			if(Strings.isBlank(additionalDetailsBean.getRef2Desig())) {
			errorsList.add("<li>" + "Please enter Referee 2 Designation");
		    errorField.add("ref2Desig");
		}
	}

	private void validateRef2Name(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef2Name())) {
			errorsList.add("<li>" + "Please enter Referee 2 Name"+"</li>");
		    errorField.add("ref2Name");
		}
	}

	private void validateRef1EmailAddress(List<String> errorsList, ArrayList<String> errorField,AdditionalDetailsBean additionalDetailsBean) {
		if (additionalDetailsBean.getRef1EmailAddress() == null || ValidatorUtil.isEmpty(additionalDetailsBean.getRef1EmailAddress().trim())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.EMAIL_REQDREF1));
			errorField.add("ref1EmailAddress");
		} else if (!EmailValidator.validate(additionalDetailsBean.getRef1EmailAddress().trim())) {
			errorsList.add(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_EMAILREF1));
			errorField.add("ref1EmailAddress");
		}
	}

	private void validateRef1contact(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef1Contact())) {
			errorsList.add("<li>" + "Please enter Contact for Referee 1"+"</li>");
		    errorField.add("ref1Contact");
		}
	}

	private void validateRef1PinCode(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
			if(Strings.isBlank(additionalDetailsBean.getRef1Pincode())) {
			errorsList.add("<li>" + "Please enter Pincode for Referee 1"+"</li>");
		    errorField.add("pinCode");
		}
			else {
				validateRef1PinCodeForZero(errorsList, errorField,additionalDetailsBean);
			}
	}
	
	//new code from line no.232 to 250
	public void validateRef1PinCodeForZero(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if (additionalDetailsBean.getRef1Pincode().length() > 6 || additionalDetailsBean.getRef1Pincode().length() < 6) {
			errorsList.add("<li>" + "Referee 1 - Pincode must be 6 digits." + "</li>");
			errorField.add("pinCode");
		} else {
			int index = additionalDetailsBean.getRef1Pincode().indexOf("0");
			int counter = 0;
			while (index >= 0) {

				index = additionalDetailsBean.getRef1Pincode().indexOf("0", index + 1);
				counter++;
			}
			if (counter == additionalDetailsBean.getRef1Pincode().length()) {
				errorsList.add("<li>" + "Referee 1 - Pincode cannot be 0." + "</li>");
				errorField.add("pinCode");
			}
		}
	}

	private void validateRef1City(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef1City())) {
			errorsList.add("<li>" + "Please enter City / Village / Town for Referee 1");
		    errorField.add("cityNameother1");
		}
	}

	private void validateRef1District(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef1District())) {
			errorsList.add("<li>" + "Please select District for Referee 1"+"</li>");
		    errorField.add("ref1DistrictList");
		}
	}

	private void validateRef1State(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef1State())) {
			errorsList.add("<li>" + "Please select State / Union Territory 1 for Referee 1"+"</li>");
		    errorField.add("ref1StateList");
		}
	}

	private void validateRef1Add1(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
			if(Strings.isBlank(additionalDetailsBean.getRef1Add1())) {
			errorsList.add("<li>" + "Please enter Address Line 1 for Referee 1"+"</li>");
		    errorField.add("ref1Add1");
		}
	}

	private void validateRef1IsAcademician(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef1IsAcademician())) {
			errorsList.add("<li>" + "Please select Is he/she an academician for Referee 1"+"</li>");
		    errorField.add("ref1IsAcademician");
		}
	}

	private void validateRef1Desig(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef1Desig())) {
			errorsList.add("<li>" + "Please enter Referee 1 Designation"+"</li>");
		    errorField.add("ref1Desig");
		}
	}

	private void validateRef1Name(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getRef1Name())) {
			errorsList.add("<li>" + "Please enter Referee 1 Name"+"</li>");
		    errorField.add("ref1Name");
		}
	}

	private void validateReasonForNotJoining(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getReasonForNotJoining())) {
			errorsList.add("<li>" + "Please enter If you were offered admission, give brief reasons for not joining the programme"+"</li>");
		    errorField.add("reasonForNotJoining");
		}
	}

	private void validateYearOfApply(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getYearsOfApply())) {
			errorsList.add("<li>" + "Please select Year(s) of application"+"</li>");
		    errorField.add("yearsOfApply");
		}
	}

	private void validateAppliedInPast(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getAppliedInPast())) {
			errorsList.add("<li>" + "Please select Have you applied for this programme in the past?"+"</li>");
		    errorField.add("appliedInPast");
		}
	}

	private void validateAdvertisement(List<String> errorsList, ArrayList<String> errorField,
			AdditionalDetailsBean additionalDetailsBean) {
		if(Strings.isBlank(additionalDetailsBean.getAdvertisement())) {
			errorsList.add("<li>" + "Please select Where did you see the advertisement?"+"</li>");
			 errorField.add("advertisement");
		   
		}
	}
	
	}
	

