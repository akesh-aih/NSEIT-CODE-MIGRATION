package com.nseit.generic.util.validation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.EducationDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.generic.util.resource.ResourceUtil;
import com.nseit.generic.util.resource.ValidationMessageConstants;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@ToString
@Slf4j
public class EducationBeanValidator {

	List<String> otherBoard = Arrays.asList("230", "1206");

	public String validateEducationalDetails(CandidateBean candidateBean, Users loggedInUser, String type) {
		String errors = "";
		List<EducationDetailsBean> educationalDetailsList = null;
		List<String> errorList = new ArrayList<String>();
		int eduList_index = 0;

		List<EducationDetailsBean> saveEducationalDetailsList = new ArrayList<EducationDetailsBean>();
		ArrayList<String> errorField = new ArrayList<String>();

		String dateofbirthvalue = null;

		try {
			educationalDetailsList = candidateBean.getEducationDtlsList();

			if (educationalDetailsList != null && !educationalDetailsList.isEmpty()) {
				boolean sscTamilMedium = false;
				boolean hscTamilMedium = false;
				boolean dipTamilMedium = false;
				for (EducationDetailsBean educationDetailsBean : educationalDetailsList) {
					if (educationDetailsBean != null) {

						boolean sscExamination = educationDetailsBean.getExamination().equals("10th / SSLC");
						boolean hscExamination = educationDetailsBean.getExamination().equals("12th / HSC");
						boolean diplomaExamination = educationDetailsBean.getExamination().equals("Diploma");
						boolean graduateExamination = educationDetailsBean.getExamination().equals("Under Graduate");
						boolean pgExamination = educationDetailsBean.getExamination().equals("Post Graduate");
						boolean pstmMandatory = educationDetailsBean.getExamination().equals("PSTM");

						// Name of Board and other board for 10th, 12th and Diploma name for Diploma
						if (sscExamination || hscExamination || diplomaExamination) {

							if (StringUtils.isBlank(educationDetailsBean.getUniversity())) {
								if (diplomaExamination)
									errorList.add("<li>" + "Please select Diploma Name for " + educationDetailsBean.getExamination() + "</li>");
								else if (hscExamination || sscExamination)
									errorList.add("<li>" + "Please select Name of Board for " + educationDetailsBean.getExamination() + "</li>");

								errorField.add("university" + eduList_index);

							} else if (Arrays.asList("7", "11", "16").contains(educationDetailsBean.getUniversity())
									|| Arrays.asList("Other", "Other Equivalent").contains(educationDetailsBean.getUniversity())) {

								if (StringUtils.isBlank(educationDetailsBean.getUniversityOth())) {
									if (diplomaExamination)
										errorList.add("<li>" + "Please enter Other Equivalent Diploma for " + educationDetailsBean.getExamination() + "</li>");
									else
										errorList.add("<li>" + "Please enter Other Board for " + educationDetailsBean.getExamination() + "</li>");

									errorField.add("universityOth" + eduList_index);

								} else if (!educationDetailsBean.getUniversityOth().matches("^[A-Za-z0-9 .,]{1,100}$")) {
									if (diplomaExamination)
										errorList.add("<li>" + "Please enter valid Other Equivalent Diploma for " + educationDetailsBean.getExamination() + "</li>");
									else
										errorList.add("<li>" + "Please enter valid Other Board for " + educationDetailsBean.getExamination() + "</li>");

									errorField.add("universityOth" + eduList_index);
								}
							}
						}

						if (diplomaExamination) {

							if (StringUtils.isBlank(educationDetailsBean.getPrdOfStudyFrm())) {
								errorList.add("<li>" + "Please select Period of Study from for " + educationDetailsBean.getExamination() + "</li>");
								errorField.add("prdOfStudyFrm_" + eduList_index);

							} else if (educationDetailsBean.getPrdOfStudyFrm().toLowerCase().contains("nan")
									|| educationDetailsBean.getPrdOfStudyFrm().toLowerCase().contains("undefined")) {

								errorList.add("<li>" + "Invalid Period of Study From for " + educationDetailsBean.getExamination() + ". Please try selecting it again.</li>");
								errorField.add("prdOfStudyFrm_" + eduList_index);
							}

							if (StringUtils.isBlank(educationDetailsBean.getPrdOfStudyTo())) {
								errorList.add("<li>" + "Please select Period of Study to for " + educationDetailsBean.getExamination() + "</li>");
								errorField.add("prdOfStudyTo_" + eduList_index);

							} else if (educationDetailsBean.getPrdOfStudyTo().toLowerCase().contains("nan")
									|| educationDetailsBean.getPrdOfStudyTo().toLowerCase().contains("undefined")) {

								errorList.add("<li>" + "Invalid Period of Study To for " + educationDetailsBean.getExamination() + ". Please try selecting it again.</li>");
								errorField.add("prdOfStudyTo_" + eduList_index);
							}

							if (StringUtils.isBlank(educationDetailsBean.getDuration())) {
								errorList.add("<li>" + "Invalid Duration of Study in " + educationDetailsBean.getExamination()
										+ ". Please try selecting Period of Study From and Period of Study To again.</li>");
								errorField.add("duration" + eduList_index);

							} else {
								// check duration as per condition
								int checkDuration = Integer.parseInt(educationDetailsBean.getDuration());
								int calDuration = 0;

								if (StringUtils.isNotBlank(educationDetailsBean.getPrdOfStudyTo()) && StringUtils.isNotBlank(educationDetailsBean.getPrdOfStudyFrm())) {
									calDuration = Integer.parseInt(educationDetailsBean.getPrdOfStudyTo()) - Integer.parseInt(educationDetailsBean.getPrdOfStudyFrm());
								}

								if (checkDuration < 0 || (calDuration != checkDuration)) {
									errorList.add("<li>" + "Invalid Duration of Study for " + educationDetailsBean.getExamination()
											+ ". Please try selecting Period of Study From and Period of Study To again.</li>");
									errorField.add("duration" + eduList_index);

								} else {
									if (checkDuration < 2) {
										errorList.add("<li>" + "Duration of Study cannot be less than 2 years for " + educationDetailsBean.getExamination() + "</li>");
										errorField.add("duration" + eduList_index);
									}
								}
							}

							if (StringUtils.isBlank(educationDetailsBean.getInstitution())) {
								errorList.add("<li>" + "Please enter Name of Institution for " + educationDetailsBean.getExamination() + "</li>");
								errorField.add("institution" + eduList_index);

							} else if (!educationDetailsBean.getInstitution().matches("^[A-Za-z0-9 .,]{1,100}$")) {
								errorList.add("<li>" + "Please enter valid Name of Institution for " + educationDetailsBean.getExamination() + "</li>");
								errorField.add("institution" + eduList_index);
							}
						}

						// Month & Year of Passing
						if (hscExamination || sscExamination || diplomaExamination || (graduateExamination && Arrays.asList("6", "Yes").contains(educationDetailsBean.getUgYesNo()))
								|| (pgExamination && Arrays.asList("6", "Yes").contains(educationDetailsBean.getPgYesNo()))) {

							if (StringUtils.isBlank(educationDetailsBean.getDateOfPassing())) {
								errorList.add("<li>" + "Please select Month & Year of Passing for " + educationDetailsBean.getExamination() + "</li>");
								errorField.add("dateOfPassing" + eduList_index);

							} else if (StringUtils.isNotBlank(educationDetailsBean.getDateOfPassing()) && (educationDetailsBean.getDateOfPassing().toLowerCase().contains("nan")
									|| educationDetailsBean.getDateOfPassing().toLowerCase().contains("undefined"))) {
								errorList.add("<li>Invalid Month & Year of Passing for " + educationDetailsBean.getExamination()
										+ " Please try selecting Month & Year of Passing again.");
								errorField.add("dateOfPassing" + eduList_index);
							}
						}

						if (sscExamination || hscExamination) {

							if (StringUtils.isBlank(educationDetailsBean.getPercentage())) {
								errorList.add("<li>" + "Please enter Percentage of Marks for " + educationDetailsBean.getExamination() + "</li>");
								errorField.add("percentage" + eduList_index);

							} else if (!educationDetailsBean.getPercentage().matches("^([3-9]?\\d(\\.\\d{1,2})?|100(\\.00?)?)$")) {
								errorList.add("<li>" + "Please enter Percentage between 30 to 100 for " + educationDetailsBean.getExamination() + "</li>");
								errorField.add("percentage" + eduList_index);

							} else {
								double percentage = Double.parseDouble(educationDetailsBean.getPercentage());
								if (percentage < 30 || percentage > 100) {
									//errorList.add("<li>" + "Percentage of Marks must be between 30 and 100 for " + educationDetailsBean.getExamination() + "</li>");
									//errorField.add("percentage" + eduList_index);
								}
							}
						}

						if (sscExamination || hscExamination || diplomaExamination) {

							if(diplomaExamination)
							{
								if (StringUtils.isBlank(educationDetailsBean.getDipMarksYesNo())) {
									errorList.add("<li>" + "Please select Do you have marks for the Diploma Course? for " + educationDetailsBean.getExamination() + "</li>");
									errorField.add("dipMarksYesNo" + eduList_index);
								}
							}
							 if (Arrays.asList("6", "Yes").contains(educationDetailsBean.getDipMarksYesNo()) || (sscExamination || hscExamination)) {

								// Total Maximum Marks
								if (StringUtils.isBlank(educationDetailsBean.getTotalMarks())) {
									errorList.add("<li>" + "Please enter Total Maximum Marks for " + educationDetailsBean.getExamination() + "</li>");
									errorField.add("totalMarks" + eduList_index);

								} else if (!educationDetailsBean.getTotalMarks().matches("^(\\d{1,4}(\\.\\d{1,4})?|10000(\\.0000?)?)$")) {
									errorList.add("<li>" + "Please enter valid Total Maximum Marks. Should not be greater than 10000 and upto four decimals for " + educationDetailsBean.getExamination() + "</li>");
									errorField.add("totalMarks" + eduList_index);

								} else {
									double totalMarks = Double.parseDouble(educationDetailsBean.getTotalMarks());
									if (totalMarks < 0 || totalMarks > 10000) {
										errorList.add("<li>" + "Total Maximum Marks must be between 0 and 10000 for " + educationDetailsBean.getExamination() + "</li>");
										errorField.add("totalMarks" + eduList_index);
									}
								}

								// Total Obtained Marks
								if (StringUtils.isBlank(educationDetailsBean.getObtainedMarks())) {
									errorList.add("<li>" + "Please enter Total Obtained Marks for " + educationDetailsBean.getExamination() + "</li>");
									errorField.add("obtainedMarks" + eduList_index);

								} else if (!educationDetailsBean.getObtainedMarks().matches("^(\\d{1,4}(\\.\\d{1,4})?|10000(\\.0000?)?)$")) {
									errorList.add("<li>" + "Please enter valid Total Obtained Marks. Should not be greater than 10000 and upto four decimals for " + educationDetailsBean.getExamination() + ".</li>");
									errorField.add("obtainedMarks" + eduList_index);

								} else {
									double obtainedMarks = Double.parseDouble(educationDetailsBean.getObtainedMarks());
									if (obtainedMarks < 0 || obtainedMarks > 10000) {
										errorList.add("<li>" + "Total Obtained Marks must be between 0 and 10000 for " + educationDetailsBean.getExamination() + ".</li>");
										errorField.add("obtainedMarks" + eduList_index);
									}
								}

								// Percentage of Marks
								if (StringUtils.isBlank(educationDetailsBean.getPercentage())) {
									errorList.add("<li>" + "Percentage of Marks for " + educationDetailsBean.getExamination()
											+ " cannot be blank. Please try entering Total Maximum Marks and Total Obtained Marks again.</li>");
									errorField.add("percentage" + eduList_index);

								} else if (!educationDetailsBean.getPercentage().matches("^([3-9]?\\d(\\.\\d{1,2})?|100(\\.00?)?)$")) {
									errorList.add("<li>" + "Invalid Percentage of Marks for " + educationDetailsBean.getExamination()
											+ ". Please try entering Total Maximum Marks and Total Obtained Marks again.</li>");
									errorField.add("percentage" + eduList_index);

								} else {
									double percentage = Double.parseDouble(educationDetailsBean.getPercentage());
									if (percentage < 30 || percentage > 100) {
										errorList.add("<li>" + "Percentage of Marks must be between 30 and 100 for " + educationDetailsBean.getExamination() + ".</li>");
										errorField.add("percentage" + eduList_index);
									}
								}
							}
						}

						// Medium of Instruction
						if (sscExamination || hscExamination || diplomaExamination) {

							if (StringUtils.isBlank(educationDetailsBean.getMedOfInstruction())) {
								errorList.add("<li>" + "Please select Medium of Instruction for " + educationDetailsBean.getExamination() + "</li>");
								errorField.add("medOfInstruction" + eduList_index);

							} else if ("161".equals(educationDetailsBean.getMedOfInstruction())) {
								if (sscExamination) {
									sscTamilMedium = true;
								} else if (hscExamination) {
									hscTamilMedium = true;
								} else if (diplomaExamination) {
									dipTamilMedium = true;
								}
							}
						}

						// Tamil as one of the language part 1
						if (sscExamination || hscExamination || diplomaExamination) {
							if (StringUtils.isBlank(educationDetailsBean.getTamilLang())) {
								errorList.add(
										"<li>" + "Please select Have you studied Tamil as one of the language (Part-1) for " + educationDetailsBean.getExamination() + "</li>");
								errorField.add("tamilLang" + eduList_index);
							}
						}

						if (graduateExamination) {

							if (StringUtils.isBlank(educationDetailsBean.getUgYesNo())) {
								errorList.add("<li>" + "Please select Do you have UG Degree?</li>");
								errorField.add("ugYesNo" + eduList_index);

							} else if (Arrays.asList("6", "Yes").contains(educationDetailsBean.getUgYesNo())) {

								if (StringUtils.isBlank(educationDetailsBean.getSpecialization())) {
									errorList.add("<li>" + "Please enter Specialization for " + educationDetailsBean.getExamination() + "</li>");
									errorField.add("specialization" + eduList_index);

								} else if (!educationDetailsBean.getSpecialization().matches("^[A-Za-z0-9 .,]{1,100}$")) {
									errorList.add("<li>" + "Please enter valid Specialization for " + educationDetailsBean.getExamination() + "</li>");
									errorField.add("specialization" + eduList_index);
								}
							}
						}

						if (pgExamination) {
							if (StringUtils.isBlank(educationDetailsBean.getPgYesNo())) {
								errorList.add("<li>" + "Please select Do you have PG Degree?</li>");
								errorField.add("pgYesNo" + eduList_index);

							} else if (Arrays.asList("6", "Yes").contains(educationDetailsBean.getPgYesNo())) {

								if (StringUtils.isBlank(educationDetailsBean.getSpecialization())) {
									errorList.add("<li>" + "Please enter Specialization for PG Degree.</li>");
									errorField.add("specialization" + eduList_index);

								} else if (!educationDetailsBean.getSpecialization().matches("^[A-Za-z0-9 .,]{1,100}$")) {
									errorList.add("<li>" + "Please enter valid Specialization for PG Degree.</li>");
									errorField.add("specialization" + eduList_index);
								}
							}

							if (StringUtils.isBlank(educationDetailsBean.getPgDipYesNo())) {
								errorList.add("<li> Please select Do you have PG Diploma?</li>");
								errorField.add("pgDipYesNo" + eduList_index);

							} else if (Arrays.asList("6", "Yes").contains(educationDetailsBean.getPgDipYesNo())) {

								if (StringUtils.isBlank(educationDetailsBean.getPgDipSpecialization())) {
									errorList.add("<li>" + "Please enter Specialization for PG Diploma.</li>");
									errorField.add("pgDipSpecialization" + eduList_index);
								} else if (!educationDetailsBean.getPgDipSpecialization().matches("^[A-Za-z0-9 .,]{1,100}$")) {
									errorList.add("<li>" + "Please enter valid Specialization for PG Diploma.</li>");
									errorField.add("pgDipSpecialization" + eduList_index);
								}

								if (StringUtils.isBlank(educationDetailsBean.getPgDipDateofpassing())) {
									errorList.add("<li>" + "Please select Month & Year of Passing for PG Diploma.</li>");
									errorField.add("pgDipDateofpassing" + eduList_index);
								} else if (StringUtils.isNotBlank(educationDetailsBean.getPgDipDateofpassing())
										&& (educationDetailsBean.getPgDipDateofpassing().toLowerCase().contains("nan")
												|| educationDetailsBean.getPgDipDateofpassing().toLowerCase().contains("undefined"))) {
									errorList.add("<li>Invalid Month & Year of Passing for PG Diploma. Please try selecting Month & Year of Passing again.");
									errorField.add("pgDipDateofpassing" + eduList_index);
								}
							}
						}

						// PSTM
						if (sscTamilMedium && hscTamilMedium && dipTamilMedium && pstmMandatory) {
							if (StringUtils.isBlank(educationDetailsBean.getPstmPreference())) {
								errorList.add("<li>" + "Please select Are you eligible to avail PSTM preference?" + "</li>");
								errorField.add("pstmPreference");
							}
							if (Arrays.asList("6", "Yes").contains(educationDetailsBean.getPstmPreference())) {

								if (StringUtils.isBlank(educationDetailsBean.getTamilMedium())) {
									errorList.add("<li>" + "Please select Have you studied in Tamil medium from 1st standard to 12th standard? " + "</li>");
									errorField.add("tamilMedium");
								}

								if (StringUtils.isBlank(educationDetailsBean.getUgTamilMedium())) {
									errorList.add("<li>" + "Please select Have you studied your Diploma in Tamil medium? " + "</li>");
									errorField.add("ugTamilMedium");
								}

								if (Arrays.asList("7", "No").contains(educationDetailsBean.getTamilMedium())
										|| Arrays.asList("7", "No").contains(educationDetailsBean.getUgTamilMedium())) {
									errorList.add("<li>" + "You are not eligible to claim PSTM.</li>");
								}
							}
						}

						if (type.equals("form")) {

							if (StringUtils.isNotBlank(loggedInUser.getDate_Of_Birth())) {
								dateofbirthvalue = loggedInUser.getDate_Of_Birth();
							}

							Date currentDate = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
							SimpleDateFormat birthDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

							if (sscExamination) {

								// SSC
								if (StringUtils.isNotBlank(educationalDetailsList.get(0).getDateOfPassing())
										&& !educationalDetailsList.get(0).getDateOfPassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(0).getDateOfPassing().toLowerCase().contains("undefined")) {

									if (StringUtils.isNotBlank(dateofbirthvalue)) {
										Date birthDate = birthDateFormat.parse(dateofbirthvalue);
										Date sscDop = sdf.parse(educationalDetailsList.get(0).getDateOfPassing());

										Calendar calendar = Calendar.getInstance();
										calendar.setTime(birthDate);
										calendar.add(Calendar.MONTH, 144);
										birthDate = calendar.getTime();
										// 12 years are added to dob and then compared with 10th mnyop

										if (birthDate.after(sscDop)) {
											errorList.add("<li>" + educationDetailsBean.getExamination() + " Month & Year of Passing should be "
													+ "at least 12 years after Date of Birth.</li>");
											errorField.add("dateOfPassing0");

										} else if (sscDop.before(sdf.parse("Jul 1978")) || sscDop.after(currentDate)) {
											errorList.add("<li>" + educationDetailsBean.getExamination()
													+ " Month & Year of Passing should be between July 1978 and Current Month & Year.</li>");
											errorField.add("dateOfPassing0");
										}

									} else {
										errorList.add("<li>Invalid Date of Birth. Please try selecting Date of Birth again.</li>");
									}
								}

							} else if (hscExamination) {

								// HSC
								if (StringUtils.isNotBlank(educationalDetailsList.get(1).getDateOfPassing())
										&& !educationalDetailsList.get(1).getDateOfPassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(1).getDateOfPassing().toLowerCase().contains("undefined")
										&& StringUtils.isNotBlank(educationalDetailsList.get(0).getDateOfPassing())
										&& !educationalDetailsList.get(0).getDateOfPassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(0).getDateOfPassing().toLowerCase().contains("undefined")) {

									Date hscDop = sdf.parse(educationalDetailsList.get(1).getDateOfPassing());
									Date sscDop = sdf.parse(educationalDetailsList.get(0).getDateOfPassing());

									if (sscDop.after(hscDop) || sscDop.equals(hscDop)) {
										errorList.add("<li>" + educationDetailsBean.getExamination() + " Month & Year of Passing should be greater than "
												+ educationalDetailsList.get(0).getExamination() + " Month & Year of Passing.</li>");
										errorField.add("dateOfPassing" + eduList_index);

									} else if (hscDop.before(sdf.parse("Jul 1978")) || hscDop.after(currentDate)) {
										errorList.add("<li>" + educationDetailsBean.getExamination()
												+ " Month & Year of Passing should be between July 1978 and Current Month & Year.</li>");
										errorField.add("dateOfPassing" + eduList_index);
									}

								}

							} else if (diplomaExamination) {

								// Diploma
								if (StringUtils.isNotBlank(educationalDetailsList.get(2).getDateOfPassing())
										&& !educationalDetailsList.get(2).getDateOfPassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(2).getDateOfPassing().toLowerCase().contains("undefined")
										&& StringUtils.isNotBlank(educationalDetailsList.get(1).getDateOfPassing())
										&& !educationalDetailsList.get(1).getDateOfPassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(1).getDateOfPassing().toLowerCase().contains("undefined")) {

									/*
									 * && StringUtils.isNotBlank(educationalDetailsList.get(0).getDateOfPassing()) &&
									 * !educationalDetailsList.get(0).getDateOfPassing().toLowerCase().contains("nan") &&
									 * !educationalDetailsList.get(0).getDateOfPassing().toLowerCase().contains("undefined")
									 */

									Date dipDop = sdf.parse(educationalDetailsList.get(2).getDateOfPassing());
									Date hscDop = sdf.parse(educationalDetailsList.get(1).getDateOfPassing());
									/* Date sscDop = sdf.parse(educationalDetailsList.get(0).getDateOfPassing()); */
									Date periodOfStudyTo = new Date();

									if (StringUtils.isNotBlank(educationalDetailsList.get(2).getPrdOfStudyTo())
											&& !educationalDetailsList.get(2).getPrdOfStudyTo().toLowerCase().contains("nan")
											&& !educationalDetailsList.get(2).getPrdOfStudyTo().toLowerCase().contains("undefined")) {
										periodOfStudyTo = new SimpleDateFormat("yyyy").parse(educationDetailsBean.getPrdOfStudyTo());
									}

									if (hscDop.after(dipDop) || hscDop.equals(dipDop)) { // sscDop.after(dipDop) || sscDop.equals(dipDop) ||
										errorList.add("<li>" + educationDetailsBean.getExamination() + " Month & Year of Passing should be greater than both "
												+ educationalDetailsList.get(0).getExamination() + " and " + educationalDetailsList.get(1).getExamination()
												+ " Month & Year of Passing.</li>");
										errorField.add("dateOfPassing" + eduList_index);

									} else if (dipDop.before(periodOfStudyTo)) {// Diploma Year must not be less than Period of Study To
										errorList.add("<li>" + educationDetailsBean.getExamination()
												+ " Month & Year of Passing must not be less than the Period of Study To for Diploma.</li>");
										errorField.add("dateOfPassing" + eduList_index);

									} else if (dipDop.before(sdf.parse("Jul 1978")) || dipDop.after(currentDate)) {
										errorList.add("<li>" + educationDetailsBean.getExamination()
												+ " Month & Year of Passing should be between July 1978 and Current Month & Year.</li>");
										errorField.add("dateOfPassing" + eduList_index);
									}

								}

							} else if (graduateExamination) {

								// UG
								if (StringUtils.isNotBlank(educationalDetailsList.get(3).getDateOfPassing())
										&& !educationalDetailsList.get(3).getDateOfPassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(3).getDateOfPassing().toLowerCase().contains("undefined")
										&& StringUtils.isNotBlank(educationalDetailsList.get(2).getDateOfPassing())
										&& !educationalDetailsList.get(2).getDateOfPassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(2).getDateOfPassing().toLowerCase().contains("undefined")) {

									/*
									 * && StringUtils.isNotBlank(educationalDetailsList.get(1).getDateOfPassing()) &&
									 * !educationalDetailsList.get(1).getDateOfPassing().toLowerCase().contains("nan") &&
									 * !educationalDetailsList.get(1).getDateOfPassing().toLowerCase().contains("undefined") &&
									 * StringUtils.isNotBlank(educationalDetailsList.get(0).getDateOfPassing()) &&
									 * !educationalDetailsList.get(0).getDateOfPassing().toLowerCase().contains("nan") &&
									 * !educationalDetailsList.get(0).getDateOfPassing().toLowerCase().contains("undefined")
									 */

									Date ugDop = sdf.parse(educationalDetailsList.get(3).getDateOfPassing());
									Date dipDop = sdf.parse(educationalDetailsList.get(2).getDateOfPassing());
									/*
									 * Date hscDop = sdf.parse(educationalDetailsList.get(1).getDateOfPassing()); Date sscDop =
									 * sdf.parse(educationalDetailsList.get(0).getDateOfPassing());
									 */

									if (dipDop.after(ugDop) || dipDop.equals(ugDop)) {
										errorList.add("<li>" + educationDetailsBean.getExamination() + " Month & Year of Passing should be greater than "
												+ educationalDetailsList.get(0).getExamination() + ", " + educationalDetailsList.get(1).getExamination() + " and "
												+ educationalDetailsList.get(2).getExamination() + " Month & Year of Passing.</li>");
										errorField.add("dateOfPassing" + eduList_index);

									} else if (ugDop.before(sdf.parse("Jul 1978")) || ugDop.after(currentDate)) {
										errorList.add("<li>" + educationDetailsBean.getExamination()
												+ " Month & Year of Passing should be between July 1978 and Current Month & Year.</li>");
										errorField.add("dateOfPassing" + eduList_index);
									}

								}

							} else if (pgExamination) {

								// PG
								if (StringUtils.isNotBlank(educationalDetailsList.get(4).getDateOfPassing())
										&& !educationalDetailsList.get(4).getDateOfPassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(4).getDateOfPassing().toLowerCase().contains("undefined")
										&& StringUtils.isNotBlank(educationalDetailsList.get(3).getDateOfPassing())
										&& !educationalDetailsList.get(3).getDateOfPassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(3).getDateOfPassing().toLowerCase().contains("undefined")) {

									Date pgDop = sdf.parse(educationalDetailsList.get(4).getDateOfPassing());
									Date ugDop = sdf.parse(educationalDetailsList.get(3).getDateOfPassing());

									if (pgDop.before(ugDop) || pgDop.equals(ugDop)) {
										errorList.add("<li> " + educationalDetailsList.get(4).getExamination() + " Month & Year of Passing should be greater than "
												+ educationalDetailsList.get(3).getExamination() + " Month & Year of Passing.</li>");
										errorField.add("dateOfPassing" + eduList_index);

									} else if (pgDop.before(sdf.parse("Jul 1978")) || pgDop.after(currentDate)) {
										errorList.add("<li>" + educationDetailsBean.getExamination()
												+ " Month & Year of Passing should be between July 1978 and Current Month & Year.</li>");
										errorField.add("dateOfPassing" + eduList_index);
									}
								}

								// PG-Diploma
								if (StringUtils.isNotBlank(educationalDetailsList.get(4).getPgDipDateofpassing())
										&& !educationalDetailsList.get(4).getPgDipDateofpassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(4).getPgDipDateofpassing().toLowerCase().contains("undefined")
										&& StringUtils.isNotBlank(educationalDetailsList.get(3).getDateOfPassing())
										&& !educationalDetailsList.get(3).getDateOfPassing().toLowerCase().contains("nan")
										&& !educationalDetailsList.get(3).getDateOfPassing().toLowerCase().contains("undefined")) {

									Date pgDip = sdf.parse(educationalDetailsList.get(4).getPgDipDateofpassing());
									Date ugDop = sdf.parse(educationalDetailsList.get(3).getDateOfPassing());

									if (pgDip.before(ugDop) || pgDip.equals(ugDop)) {
										errorList.add("<li> PG-Diploma Month & Year of Passing should be greater than " + educationalDetailsList.get(3).getExamination()
												+ " Month & Year of Passing.</li>");
										errorField.add("pgDipDateofpassing" + eduList_index);

									} else if (pgDip.before(sdf.parse("Jul 1978")) || pgDip.after(currentDate)) {
										errorList.add("<li>" + educationDetailsBean.getExamination()
												+ " Month & Year of Passing should be between July 1978 and Current Month & Year.</li>");
										errorField.add("dateOfPassing" + eduList_index);
									}
								}
							}

						}
						saveEducationalDetailsList.add(educationDetailsBean);
					}
					eduList_index++;
				}
			} else {
				errorList.add("<li>Please fill all mandatory details.</li>");
			}

			if (errorList != null && !errorList.isEmpty()) {
				candidateBean.setSaveEducationDtlsList(educationalDetailsList);
			} else {
				candidateBean.setSaveEducationDtlsList(saveEducationalDetailsList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorList.add("<li>" + "Some error occured while saving academic details.</li>");
		}
		if (errorList != null && !errorList.isEmpty()) {
			errors = ValidatorUtil.validationMessageFormatter(errorList);
			candidateBean.setErrorField(errorField);
		}
		return errors;
	}

	public boolean checkvalidDecimal(String Percentage) {
		int occurance = StringUtils.countMatches(Percentage, ".");
		if (occurance > 1)
			return true;
		else
			return false;
	}

	public String setErrorMessage(CandidateBean candidateBean, List<String> errorList, ArrayList<String> errorField, String result) {
		if (errorList != null && !errorList.isEmpty()) {
			result = ValidatorUtil.validationMessageFormatter(errorList);
		}
		return result;
	}

	public void setErrorField(CandidateBean candidateBean, ArrayList<String> errorField) {
		candidateBean.setErrorField(errorField);
	}

	public void setCalenderDateValue(CandidateBean candidateBean, ArrayList<String> errorField) {
		if (StringUtils.isNotBlank(candidateBean.getDateOfBirth())) {
			String d[] = candidateBean.getDateOfBirth().split("-");
			int dobYr = Integer.parseInt(d[2]);
			candidateBean.setFirstDate(dobYr + 12);
			candidateBean.setHscDate(dobYr + 14);
		}
	}

	public void validateIsFieldBlank(String field, List<String> errorList, ArrayList<String> errorField, String message, String fieldName) {
		if (StringUtils.isBlank(field)) {
			errorList.add(message);
			errorField.add(fieldName);
		}
	}

	public Boolean validateFieldLength(String field, int length, List<String> errorList, String message, Boolean isMin, List<String> errorField, String fieldName) {
		Boolean flag = Boolean.FALSE;
		if (isMin) {
			if (field.length() < length) {
				errorList.add(message);
				errorField.add(fieldName);
				flag = Boolean.TRUE;
			}
		} else {
			if (field.length() > length) {
				errorList.add(message);
				errorField.add(fieldName);
				flag = Boolean.TRUE;
			}
		}
		return flag;
	}

	public void validateSscBoard(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (index.get() == 0) {
			if (StringUtils.isBlank(educationDetailsBean.getNameOfUniv())) {
				errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.BOARD_NAME_REQ) + educationDetailsBean.getExamination() + "</li>");
				errorField.add("nameOfUniv" + index);
			} else if (StringUtils.isNotBlank(educationDetailsBean.getNameOfUniv()) && (otherBoard.contains(educationDetailsBean.getNameOfUniv()))) {
				validateSscOtherBoard(educationDetailsBean, errorField, errorList, index);
			}
		}
	}

	public void validateSscOtherBoard(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (index.get() == 0) {
			if (StringUtils.isBlank(educationDetailsBean.getUniversityOth())) {
				errorList.add(
						"<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.OTHER_BOARD_NAME_REQ) + educationDetailsBean.getExamination() + "</li>");
				errorField.add("universityOTH" + index);
			} else {
				validatePatternForSscOthBoard(educationDetailsBean, errorField, errorList, index);
			}
		}
	}

	public void validatePatternForSscOthBoard(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (educationDetailsBean.getUniversityOth().equals(otherBoard)) {
			if (!(validateFieldLength(educationDetailsBean.getUniversityOth(), 1, errorField,
					"minimum 1 character required for " + educationDetailsBean.getExamination() + "Other Board", Boolean.TRUE, errorList, "universityOTH")
					&& validateFieldLength(educationDetailsBean.getUniversityOth(), 100, errorField,
							"maximum 100 character required for " + educationDetailsBean.getExamination() + "Other Board", Boolean.FALSE, errorList, "universityOTH"))) {
				if (!ValidatorUtil.validatePatternWithMinMax(ValidatorUtil.alphaSpace, educationDetailsBean.getCertiSerialNum(), "1", "100")) {
					errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_OTHER_BOARD_NAME)
							+ educationDetailsBean.getExamination() + "</li>");
					errorField.add("universityOTH" + index);
				}
			}
		}
	}

	public void validateDipUiversity(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (index.get() == 1) {
			if (StringUtils.isBlank(educationDetailsBean.getNameOfUniv())) {
				errorList.add(
						"<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.UNIVERSITY_NAME_REQ) + educationDetailsBean.getExamination() + "</li>");
				errorField.add("nameOfUniv" + index);
			} else if (StringUtils.isNotBlank(educationDetailsBean.getNameOfUniv()) && (otherBoard.contains(educationDetailsBean.getNameOfUniv()))) {
				validateDipOtherUniv(educationDetailsBean, errorField, errorList, index);
			}
		}
	}

	public void validateDipOtherUniv(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (index.get() == 1) {
			if (StringUtils.isBlank(educationDetailsBean.getUniversityOth())) {
				errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.OTHER_UNIVERSITY_NAME_REQ) + educationDetailsBean.getExamination()
						+ "</li>");
				errorField.add("universityOTH" + index);
			} else {
				validatePatternDipOtherUniv(educationDetailsBean, errorField, errorList, index);
			}
		}
	}

	public void validatePatternDipOtherUniv(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (educationDetailsBean.getUniversityOth().equals(otherBoard)) {
			if (!(validateFieldLength(educationDetailsBean.getUniversityOth(), 1, errorField,
					"minimum 1 character required for " + educationDetailsBean.getExamination() + "Other University", Boolean.TRUE, errorList, "universityOTH")
					&& validateFieldLength(educationDetailsBean.getUniversityOth(), 100, errorField,
							"maximum 100 character required for " + educationDetailsBean.getExamination() + "Other Board", Boolean.FALSE, errorList, "universityOTH"))) {
				if (!ValidatorUtil.validatePatternWithMinMax(ValidatorUtil.alphaSpace, educationDetailsBean.getCertiSerialNum(), "1", "100")) {
					errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_OTHER_UNIVERSITY_NAME)
							+ educationDetailsBean.getExamination() + "</li>");
					errorField.add("universityOTH" + index);
				}
			}
		}
	}

	public void validateDegreeSubject(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (index.get() == 1) {
			validateIsFieldBlank(educationDetailsBean.getDegreeSubject(), errorList, errorField,
					"<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.TRADE_NAME_REQ) + educationDetailsBean.getExamination() + "</li>",
					"degreeSubject" + index);
		}
	}

	public void validateRegistrationNo(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (index.get() == 0 || index.get() == 1) {
			if (StringUtils.isBlank(educationDetailsBean.getRegistrationNo())) {
				errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.ROLL_NO_REQ) + educationDetailsBean.getExamination() + "</li>");
				errorField.add("registrationNo" + index);
			} else {
				validatePatternForRegNo(educationDetailsBean, errorField, errorList, index);
			}
		}
	}

	public void validatePatternForRegNo(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (!(validateFieldLength(educationDetailsBean.getRegistrationNo(), 1, errorList, "minimum 1 character required for Roll Number of " + educationDetailsBean.getExamination(),
				Boolean.TRUE, errorField, "registrationNo")
				&& validateFieldLength(educationDetailsBean.getRegistrationNo(), 25, errorList,
						"miaximum 25 character required for Roll Number of " + educationDetailsBean.getExamination(), Boolean.FALSE, errorField, "registrationNo"))) {
			if (!ValidatorUtil.validatePatternWithMinMax(ValidatorUtil.rollNo, educationDetailsBean.getRegistrationNo(), "1", "25")) {
				errorList
						.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_ROLL_NO) + educationDetailsBean.getExamination() + "</li>");
				errorField.add("registrationNo" + index);
			}
		}
	}

	public void validateCertSerialNo(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (index.get() == 0 || index.get() == 1) {
			if (!StringUtils.isBlank(educationDetailsBean.getCertiSerialNum())) {
				validateCertSrNoTextField(educationDetailsBean, errorField, errorList, index);
			}
		}
	}

	public void validateCertSrNoTextField(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		if (!(validateFieldLength(educationDetailsBean.getCertiSerialNum(), 1, errorField,
				"minimum 1 character required for Certificate/Marksheet Serial Number" + educationDetailsBean.getExamination(), Boolean.TRUE, errorList, "certiSerialNum")
				&& validateFieldLength(educationDetailsBean.getCertiSerialNum(), 25, errorField,
						"maximum 25 character required for Certificate/Marksheet Serial Number" + educationDetailsBean.getExamination(), Boolean.FALSE, errorList, "certiSerialNum"))) {
			if (!ValidatorUtil.validatePatternWithMinMax(ValidatorUtil.alphaNumSpace, educationDetailsBean.getCertiSerialNum(), "1", "25")) {
				errorList.add("<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.INVALID_CERTIFICATE_SR_NO) + educationDetailsBean.getExamination()
						+ "</li>");
				errorField.add("certiSerialNum" + index);
			}
		}
	}

	public void validateDoeacc(CandidateBean candidateBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		validateIsFieldBlank(candidateBean.getDoeacc(), errorList, errorField,
				"<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.O_LEVEL_CERTIFICATE_REQ) + "</li>", "doeacc");
	}

	public void validateTeriArmy(CandidateBean candidateBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		validateIsFieldBlank(candidateBean.getTerriArmy(), errorList, errorField,
				"<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.TERRITORIAL_ARMY) + "</li>", "terriArmy");
	}

	public void validateCertiB(CandidateBean candidateBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index) {
		validateIsFieldBlank(candidateBean.getCertiB(), errorList, errorField,
				"<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.OBTAINED_B_CERTI) + "</li>", "certiB");
	}

	public void validateSscYrOfPassing(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index,
			String dateofbirthvalue, String yearOfPassing, String examination) {
		if (index.get() == 0) {
			validateIsFieldBlank(educationDetailsBean.getDateOfPassing(), errorList, errorField,
					"<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.DATE_OF_PASSING_REQ) + educationDetailsBean.getExamination() + "</li>",
					"dateOfPassing0");
			validateSscPassingDtCompareWitihDob(errorField, errorList, index, educationDetailsBean, dateofbirthvalue, yearOfPassing, examination);
		}
	}

	public void validateDipYrOfPassing(EducationDetailsBean educationDetailsBean, ArrayList<String> errorField, List<String> errorList, AtomicInteger index,
			String dateofbirthvalue, String yearOfPassing, String examination) {
		if (index.get() == 1) {
			validateIsFieldBlank(educationDetailsBean.getDateOfPassing(), errorList, errorField,
					"<li>" + ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.DATE_OF_PASSING_REQ) + educationDetailsBean.getExamination() + "</li>",
					"dateOfPassing1");
			validateDipPassYrDtCompareWitihSscPassYr(errorField, errorList, index, educationDetailsBean, dateofbirthvalue, yearOfPassing, examination);
		}
	}

	public void validateSscPassingDtCompareWitihDob(ArrayList<String> errorField, List<String> errorList, AtomicInteger index, EducationDetailsBean educationDetailsBean,
			String dateofbirthvalue, String yearOfPassing, String examination) {
		if (index.get() == 0) {
			if ((StringUtils.isNotBlank(yearOfPassing) && StringUtils.isNotBlank(dateofbirthvalue) && StringUtils.isNotBlank(educationDetailsBean.getExamination())
					&& !yearOfPassing.toLowerCase().contains("nan"))) {
				try {
					int datediff = Integer.parseInt(yearOfPassing.split(" ")[1]) - Integer.parseInt(dateofbirthvalue.split("-")[2]);
					if (datediff < 12) {
						errorList.add(
								"<li>Month & Year of Passing of " + educationDetailsBean.getExamination() + " should be atleast 12 years " + "greater than Date of Birth</li>");
						errorField.add("dateOfPassing0");
					}
				} catch (Exception e) {
					log.error("error message {} ", e.getMessage());
					log.error("error LocalizedMessage {} ", e.getLocalizedMessage());
					errorList.add("<li>Some internal error occured, Please try again..!!</li>");
				}
			}
		}
	}

	public void validateDipPassYrDtCompareWitihSscPassYr(ArrayList<String> errorField, List<String> errorList, AtomicInteger index, EducationDetailsBean educationDetailsBean,
			String dateofbirthvalue, String yearOfPassing, String examination) {
		if (index.get() == 1) {
			if ((StringUtils.isNotBlank(educationDetailsBean.getDateOfPassing()) && StringUtils.isNotBlank(yearOfPassing)
					&& StringUtils.isNotBlank(educationDetailsBean.getExamination()) && !educationDetailsBean.getDateOfPassing().toLowerCase().contains("nan"))) {
				try {
					int datediff = Integer.parseInt(educationDetailsBean.getDateOfPassing().split(" ")[1]) - Integer.parseInt(yearOfPassing.split(" ")[1]);
					if (datediff < 3) {
						errorList.add("<li>Month & Year of Passing of " + educationDetailsBean.getExamination() + " should be atleast 3 years "
								+ "greater than Month & Year of Passing of SSC</li>");
						errorField.add("dateOfPassing1");
						errorField.add("dateOfPassing0");
					}
				} catch (Exception e) {
					errorList.add("<li>Some internal error occured, Please try again..!!</li>");
				}
			}
		}
	}

}
