package com.nseit.generic.util.validation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.models.WorkExperienceDetailsBean;
import com.nseit.generic.service.CandidateService;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class WorkExperienceValidator {
	private Logger logger = LoggerHome.getLogger(getClass());

	private WorkExperienceBean workExperienceBean = new WorkExperienceBean();
	
	private CandidateService candidateService;
	
	public String validateWorkExp(CandidateBean candidateBean,Users loggedInUser, WorkExperienceBean workExperienceBean,String flag) {

		String errors = "";
		List<WorkExperienceDetailsBean> workExperienceDetailsList = null;
		ArrayList<String> errorField = new ArrayList<String>();
		List<String> errorList = new ArrayList<String>();
		List<WorkExperienceDetailsBean> workExperienceList = new ArrayList<WorkExperienceDetailsBean>();
		int count = 1;
		try {
			
			if(flag.equals("form")) {
				candidateBean.setYesNoExp(workExperienceBean.getYesNo());
			workExperienceDetailsList = workExperienceBean.getWorkExperienceDetailsList();
		}
			
			if(flag.equals("database")) {
				workExperienceDetailsList = candidateBean.getWkExperienceDtlsList();
				workExperienceBean = new WorkExperienceBean();
			}
				
			workExperienceDetailsList.removeIf(Objects::isNull);

			String dateofpassing = null;
			
			List<EducationDetailsBean> candidateEducationalDetails = null;
			candidateEducationalDetails = candidateService.getCandiateAcademicDetails(candidateBean,loggedInUser);
			
			if (!candidateEducationalDetails.isEmpty()) {
					dateofpassing = candidateEducationalDetails.get(0).getYearOfPassing();
			}
			if (dateofpassing != null) {	
				workExperienceBean.setEduDate(dateofpassing);
			}

			SimpleDateFormat sdfExp = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat sdfMnY = new SimpleDateFormat("MMMM yyyy");
			Date eduPass = null;
			
			
			if(workExperienceBean.getEduDate() != null)
			 eduPass = sdfMnY.parse(workExperienceBean.getEduDate());

				int counter = 0;
				if (workExperienceDetailsList != null && !workExperienceDetailsList.isEmpty()) {
					for (WorkExperienceDetailsBean workExperienceDetailsBean : workExperienceDetailsList) {
						if (workExperienceDetailsBean != null) {

							if (workExperienceDetailsBean.getYesNoA() != null || !workExperienceDetailsBean.getYesNoA().equals("")) {
								if (workExperienceDetailsBean.getYesNoA().equals("6")) {
									counter++;
								}
							}
							if (counter > 1) {
								errorList.add("<li>Current organisation cannot be more than one.</li>");
							}

							if (workExperienceDetailsBean.getYesNoA() != null && workExperienceDetailsBean.getYesNoA().equals("6")) {
								workExperienceDetailsBean.setToYear("20-Jun-2023");
							}
							workExperienceList.add(workExperienceDetailsBean);

							if (workExperienceDetailsBean.getArea() == null || workExperienceDetailsBean.getArea().trim().equals("")) {
								errorField.add("areaArpSop" + count);
								errorList.add("<li>Please enter Organization Name in row " + count + ".</li>");
							} else if (!Pattern.matches("[a-zA-Z0-9 ]{1,100}", workExperienceDetailsBean.getArea().trim())) {
								errorField.add("areaArpSop" + count);
								errorList.add("<li>Please enter valid Organization Name in row " + count + ".</li>");
							}

							if (workExperienceDetailsBean.getOrganizationOthers() == null
									|| workExperienceDetailsBean.getOrganizationOthers().trim().equals("")) {
								errorField.add("organizationOtherss" + count);
								errorList.add("<li>Please enter Designation in row " + count + ".</li>");
							} else if (!Pattern.matches("[a-zA-Z0-9 ]{1,50}", workExperienceDetailsBean.getOrganizationOthers().trim())) {
								errorField.add("organizationOtherss" + count);
								errorList.add("<li>Please enter valid Designation in row " + count + ".</li>");
							}
							
							if (workExperienceDetailsBean.getJobRole() == null || workExperienceDetailsBean.getJobRole().trim().equals("")) {
								errorField.add("jobRole" + count);
								errorList.add("<li>Please enter Roles and Responsibility in row " + count + ".</li>");
							} else if (!Pattern.matches("[a-zA-Z0-9 ]{1,300}", workExperienceDetailsBean.getJobRole().trim())) {
								errorField.add("jobRole" + count);
								errorList.add("<li>Please enter valid Roles and Responsibility in row " + count + ".</li>");
							}

							if (workExperienceDetailsBean.getFromYear() == null || workExperienceDetailsBean.getFromYear().equals("")) {
								errorField.add("fromYear_" + count);
								errorList.add("<li>Please select Date of Joining in row " + count + ".</li>");
							}
							if (workExperienceDetailsBean.getYesNoA() == null || workExperienceDetailsBean.getYesNoA().equals("")) {
								errorField.add("yesNoA" + count);
								errorList.add("<li>Please select Is this your current organization in row " + count + ".</li>");
							}
							if (workExperienceDetailsBean.getYesNoA() != null && workExperienceDetailsBean.getYesNoA().equals("7")) {
								if (workExperienceDetailsBean.getToYear() == null || workExperienceDetailsBean.getToYear().equals("")) {
									errorField.add("toYear_" + count);
									errorList.add("<li>Please select Date of Leaving  in row " + count + ".</li>");
								}
							}
							if (workExperienceDetailsBean.getFromYear() != null && !workExperienceDetailsBean.getFromYear().equals("")
									&& workExperienceDetailsBean.getToYear() != null && !workExperienceDetailsBean.getToYear().equals("")
									&& workExperienceDetailsBean.getFromYear().equals(workExperienceDetailsBean.getToYear())) {
								errorList.add("<li>Date of Joining and Date of Leaving cannot be same. Please enter correct Date of Joining and Date of Leaving in row " + count
										+ ".</li>");
							}

							Date row1FromDate = null;
							Date row1ToDate = null;

							if (workExperienceDetailsBean.getFromYear() != null && !workExperienceDetailsBean.getFromYear().equals("")
									&& workExperienceDetailsBean.getToYear() != null && !workExperienceDetailsBean.getToYear().equals("")) {
								row1FromDate = sdfExp.parse(workExperienceDetailsBean.getFromYear());
								row1ToDate = sdfExp.parse(workExperienceDetailsBean.getToYear());
							}

							if (row1FromDate != null && row1ToDate != null) {
								for (int i = count; i < workExperienceDetailsList.size(); i++) {

									WorkExperienceDetailsBean workExpDetailsBean = workExperienceDetailsList.get(i);

									Date row2FromDate = null;
									Date row2ToDate = null;
									Date fromDate = null;
									Date cutOffDate = sdfExp.parse("20-Jun-2023");
									if (workExpDetailsBean.getFromYear() != null && !workExpDetailsBean.getFromYear().equals("")) {
										fromDate = sdfExp.parse(workExpDetailsBean.getFromYear());
									}
									if (!workExpDetailsBean.getYesNoA().equals("") && workExpDetailsBean.getYesNoA().equals("6")) {
										if (fromDate != null && fromDate.before(cutOffDate)) {
											workExpDetailsBean.setToYear("20-Jun-2023");
										} else {
											Date today = new Date();
											String todaysDate = sdfExp.format(today);
											workExpDetailsBean.setToYear(todaysDate);
										}
									}
									if (workExpDetailsBean.getFromYear() != null && !workExpDetailsBean.getFromYear().equals("") && workExpDetailsBean.getToYear() != null
											&& !workExpDetailsBean.getToYear().equals("")) {
										row2FromDate = sdfExp.parse(workExpDetailsBean.getFromYear());
										row2ToDate = sdfExp.parse(workExpDetailsBean.getToYear());
									}
									if (row2FromDate != null && row2ToDate != null) {
										if ((row2FromDate.after(row1FromDate) && row2FromDate.before(row1ToDate))
												|| (row2ToDate.after(row1FromDate) && row2ToDate.before(row1ToDate))) {
											errorList.add("<li>Work Experience date entered in row " + count + " is overlapping with row " + (i + 1) + ".</li>");
										} else if (row2FromDate.equals(row1FromDate) || row2FromDate.equals(row1ToDate) || row2ToDate.equals(row1FromDate)
												|| row2ToDate.equals(row1ToDate)) {
											errorList.add("<li>Work Experience date entered in row " + count + " is overlapping with row " + (i + 1) + ".</li>");
										} else if ((row1FromDate.after(row2FromDate) && row1FromDate.before(row2ToDate))
												|| (row1ToDate.after(row2FromDate) && row1ToDate.before(row2ToDate))) {
											errorList.add("<li>Work Experience date entered in row " + count + " is overlapping with row " + (i + 1) + ".</li>");
										}
									}
								}
							}
							// add here for education passing year and work
							// experience gap
							if (eduPass != null && !eduPass.equals("") && workExperienceDetailsBean.getFromYear() != null && !workExperienceDetailsBean.getFromYear().equals("")) {
								Date fromYear = sdfExp.parse(workExperienceDetailsBean.getFromYear());

								if (eduPass.after(fromYear) || eduPass.equals(fromYear)) {
									errorField.add("fromYear_" + count);
									errorList.add("<li>" + "Date of Period of Experience From in row " + count + " should be greater than Graduation/Equivalent - Date of Passing."
											+ "</li>");
								}
							}
							if(workExperienceDetailsBean.getMaxPayLakh()==null || workExperienceDetailsBean.getMaxPayLakh().equals("") ||
		    						workExperienceDetailsBean.getMaxPayThousnd()==null || workExperienceDetailsBean.getMaxPayThousnd().equals(""))
		        			{
		        				errorList.add("<li>Please enter Annual CTC in row "+count+".</li>");
		        				errorField.add("maxPayLakh" + count);
		        				errorField.add("maxPayThousnd" + count);
		        			}
		    				else if(Integer.parseInt(workExperienceDetailsBean.getMaxPayLakh())<0 ||
		    						Integer.parseInt(workExperienceDetailsBean.getMaxPayLakh())>99
		    						 || Integer.parseInt(workExperienceDetailsBean.getMaxPayThousnd())<0 || 
		    						 Integer.parseInt(workExperienceDetailsBean.getMaxPayThousnd())>99) {
		    					errorList.add("<li>Please enter Valid Annual CTC in row "+count+".</li>");
		    				}
		    				else if(Integer.parseInt(workExperienceDetailsBean.getMaxPayLakh())==0 &&
		    						Integer.parseInt(workExperienceDetailsBean.getMaxPayThousnd())==0) {
		    					errorList.add("<li>Valid Annual CTC cannot be zero in row "+count+".</li>");
		    				}
						}
						count++;
					} // for loop closed

					if (workExperienceList.isEmpty()) {
						errorList.add("<li>Please enter Work Experience Details.</li>");
					}
					
					if(flag.equals("form")) {
					if (workExperienceBean.getMonthOfExperience() == null
							|| "".equals(workExperienceBean.getMonthOfExperience()) || workExperienceBean.getDayOfExperience() == null
							|| "".equals(workExperienceBean.getDayOfExperience())) {
						errorList.add("<li>Total Work Experience should not be Blank.</li>");
					} else if(Integer.parseInt(workExperienceBean.getMonthOfExperience()) < 24) {
						errorList.add("<li>Minimum Work Experience Requirement is 24 months.</li>");
					} else if ((workExperienceBean.getMonthOfExperienceHidden() != null && !workExperienceBean.getMonthOfExperienceHidden().equals("")
									&& !workExperienceBean.getMonthOfExperienceHidden().equals(workExperienceBean.getMonthOfExperience()))
							|| (workExperienceBean.getDayOfExperienceHidden() != null && !workExperienceBean.getDayOfExperienceHidden().equals("")
									&& !workExperienceBean.getDayOfExperienceHidden().equals(workExperienceBean.getDayOfExperience()))) {
						errorList.add("<li>Total Work Experience does not match with entered work experience dates. Kindly do not change Total Work Experience manually.</li>");
					}
					}
					if(flag.equals("database")) {
					if (candidateBean.getTotalYearExp() == null || "".equals(candidateBean.getTotalYearExp())) {
						errorList.add("<li>Total Work Experience should not be Blank.</li>");
					}
					}
					workExperienceBean.setWorkExperienceSaveList(workExperienceList);
				}

			if (errorList != null && !errorList.isEmpty()) {
				workExperienceBean.setErrorField(errorField);
				errors = ValidatorUtil.validationMessageFormatter(errorList);
			}
		} catch (Exception e) {
			errors = "<li>Some internal error occured, Please try again..!!</li>";
			logger.fatal(e, e);
		}
		return errors;
	}
}