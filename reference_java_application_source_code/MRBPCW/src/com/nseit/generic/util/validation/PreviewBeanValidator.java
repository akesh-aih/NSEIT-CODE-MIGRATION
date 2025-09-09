package com.nseit.generic.util.validation;

import org.apache.logging.log4j.Logger;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CovidDutyCertificateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.service.CommonService;
import com.nseit.generic.util.LoggerHome;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@ToString
@Slf4j
@NoArgsConstructor

public class PreviewBeanValidator {

	private Logger logger = LoggerHome.getLogger(getClass());
	private CandidateBean candidateBean;
	private WorkExperienceBean workExperienceBean;
	private Users loggedInUser;
	private CommonService commonService;
	private PersonalBeanValidator personalBeanValidator;
	private EducationBeanValidator educationBeanValidator;
	private PhotoBeanValidator photoBeanValidator;
	private SignatureBeanValidator signatureBeanValidator;
	private DocumentBeanValidator documentBeanValidator;
	private AdditionalBeanValidator additionalBeanValidator;
	private WorkExperienceValidator workExperienceValidator;
	private CovidDutyCertificateValidator covidDutyCertificateValidator;
	private CovidDutyCertificateBean covidDutyCertificateBean;
	
	public String previewValidation(CandidateBean candidateBean) {
		String errors = "";
		
		try {
			personalBeanValidator.setCandidateBean(candidateBean);
			loggedInUser= candidateBean.getLoggedInUser();
			
			errors = errors.concat(personalBeanValidator.validatePersonalDetails("database"));
			errors = errors.concat(educationBeanValidator.validateEducationalDetails(candidateBean, loggedInUser, "database"));
			
			photoBeanValidator.setCandidateBean(candidateBean);
			photoBeanValidator.setType("database");
			photoBeanValidator.setLoggedInUser(loggedInUser);
			errors = errors.concat(photoBeanValidator.validateUploadedPhoto());
			
			signatureBeanValidator.setCandidateBean(candidateBean);
			signatureBeanValidator.setLoggedInUser(loggedInUser);
			signatureBeanValidator.setType("database");
			errors = errors.concat(signatureBeanValidator.validateUploadedSignature());
			errors = errors.concat(documentBeanValidator.validateUploadedDocuments(candidateBean,"database", loggedInUser));
			if (errors.length() > 0) {
				commonService.insertCandidateAuditrail(loggedInUser, "Preview Page - Exception or Error", "Candidate with User Id " + loggedInUser.getUsername() + " : " + errors.toString());
			}

			if (errors.length() == 0) {
				commonService.insertCandidateAuditrail(loggedInUser, "Preview Page - Serverside validation",
						"Candidate with User Id " + loggedInUser.getUsername() + " : No issue found in server side validation");
			}
		} catch (Exception e) {
			errors = "Error while submitting page, Please contact System administrator.";
			e.printStackTrace();
			log.error("", e.getLocalizedMessage());
		}

		return errors;
	}
}