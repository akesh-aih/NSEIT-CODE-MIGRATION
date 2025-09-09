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
import com.nseit.generic.models.CovidDutyCertDetailsBean;
import com.nseit.generic.models.CovidDutyCertificateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class CovidDutyCertificateValidator {
	private Logger logger = LoggerHome.getLogger(getClass());

	private CovidDutyCertificateBean covidDutyCertificateBean = new CovidDutyCertificateBean();
	
	//private CandidateService candidateService;
	
	public String validateCovidDutyCert(CandidateBean candidateBean,Users loggedInUser, CovidDutyCertificateBean covidDutyCertificateBean,String flag) {
		
		String errors = "";
		List<CovidDutyCertDetailsBean> covidDutyCertDetailsList = null;
		ArrayList<String> errorField = new ArrayList<String>();
		List<String> errorList = new ArrayList<String>();
		List<CovidDutyCertDetailsBean> covidDutyCertList = new ArrayList<CovidDutyCertDetailsBean>();
		int count = 1;
		
		try {
			if(flag.equals("form")) {
				covidDutyCertDetailsList = covidDutyCertificateBean.getCovidDutyCertDetailsList();
		}
			
			if(flag.equals("database")) {
				covidDutyCertDetailsList = candidateBean.getCovidDutyCertDtlsList();
				covidDutyCertificateBean = new CovidDutyCertificateBean();
				covidDutyCertificateBean.setYesNo(candidateBean.getYesOrNo());
			}
			
			covidDutyCertDetailsList.removeIf(Objects::isNull);
			
			if (Strings.isBlank(covidDutyCertificateBean.getYesNo())) {
				errorList.add("<li>Please select Have you worked in covid period?</li>");
			}else if (covidDutyCertificateBean.getYesNo().equals("6") || covidDutyCertificateBean.getYesNo().equalsIgnoreCase("yes")) {
			
				if (covidDutyCertDetailsList != null && !covidDutyCertDetailsList.isEmpty()) {
					for (CovidDutyCertDetailsBean covidDutyCertDetailsBean : covidDutyCertDetailsList) {
						if (covidDutyCertDetailsBean != null) {
							
							//Institution Type
							if (StringUtils.isBlank(covidDutyCertDetailsBean.getInstitutionType())) {
								errorField.add("institutionType" + count);
								errorList.add("<li>Please select Institution Type in row " + count + ".</li>");
							}
							
							//Name of the Medical Institution
							if (StringUtils.isBlank(covidDutyCertDetailsBean.getNameOfMedInstitution())) {
								errorField.add("nameOfMedInstitution" + count);
								errorList.add("<li>Please enter Name of the Medical Institution in row " + count + ".</li>");
							} else if (!Pattern.matches("[a-zA-Z0-9 .]{1,100}", covidDutyCertDetailsBean.getNameOfMedInstitution())) {
								errorField.add("nameOfMedInstitution" + count);
								errorList.add("<li>Please enter valid Name of the Medical Institution in row " + count + ".</li>");
							}
							else if (Strings.isNotBlank(covidDutyCertDetailsBean.getNameOfMedInstitution()) && Pattern.matches("[0-9.]+", covidDutyCertDetailsBean.getNameOfMedInstitution().trim())) {
								errorList.add("<li>Please enter valid Name of the Medical Institution in row " + count + ".</li>");
								errorField.add("nameOfMedInstitution" + count);
							}else if (Strings.isNotBlank(covidDutyCertDetailsBean.getNameOfMedInstitution()) && Pattern.matches("\\d+", covidDutyCertDetailsBean.getNameOfMedInstitution().trim())) {
								errorList.add("<li>Please enter valid Name of the Medical Institution in row " + count + ".</li>");
								errorField.add("nameOfMedInstitution" + count);
							}
							
							//District
							if (StringUtils.isBlank(covidDutyCertDetailsBean.getDistrictVal())) {
								errorField.add("districtList" + count);
								errorList.add("<li>Please select District in row " + count + ".</li>");
							}
							
							//Address of the Institution
							if (StringUtils.isBlank(covidDutyCertDetailsBean.getAddressOfInstitute())) {
								errorField.add("addressOfInstitute" + count);
								errorList.add("<li>Please enter Address of the Institution in row " + count + ".</li>");
							} else if (!Pattern.matches("[a-zA-Z0-9 ,&.\\\\/]{1,100}", covidDutyCertDetailsBean.getAddressOfInstitute())) {
								errorField.add("addressOfInstitute" + count);
								errorList.add("<li>Please enter valid Address of the Institution in row " + count + ".</li>");
							}
							
							//Period of Work-From
							if (StringUtils.isBlank(covidDutyCertDetailsBean.getFromYear())) {
								errorList.add("<li>" + "Please select Period of Work-From in row " + count + ".</li>");
								errorField.add("fromYear_" + count);
							} else if (covidDutyCertDetailsBean.getFromYear().toLowerCase().contains("nan")
									|| covidDutyCertDetailsBean.getFromYear().toLowerCase().contains("undefined")) {
								errorList.add("<li>" + "Invalid Period of Work-From in row " + count + ".Please try selecting Period of Work-From again.</li>");
								errorField.add("fromYear_" + count);
							} else {
								Date workFrom = new SimpleDateFormat("dd-MMM-yyyy").parse(covidDutyCertDetailsBean.getFromYear());
								Date maxDate = new SimpleDateFormat("dd-MMM-yyyy").parse("05-May-2023");
								Date minDate = new SimpleDateFormat("dd-MMM-yyyy").parse("01-Mar-2020");
								if (workFrom.after(maxDate) || workFrom.before(minDate)) {
									errorList.add("<li>Period of Work-From in row " + count + "should be between 01-Mar-2020 and 05-May-2023.</li>");
									errorField.add("fromYear_" + count);
								}
							}
							
							//Period of Work-To
							if (StringUtils.isBlank(covidDutyCertDetailsBean.getToYear())) {
								errorField.add("toYear_" + count);
								errorList.add("<li>Please select Period of Work-To in row " + count + ".</li>");
							}else if (covidDutyCertDetailsBean.getToYear().toLowerCase().contains("nan")
									|| covidDutyCertDetailsBean.getToYear().toLowerCase().contains("undefined")) {
								errorList.add("<li>" + "Invalid Period of Work-To in row " + count + ".Please try selecting Period of Work-To again.</li>");
								errorField.add("toYear_" + count);
							} else {
								Date workTo = new SimpleDateFormat("dd-MMM-yyyy").parse(covidDutyCertDetailsBean.getToYear());
								Date maxDate = new SimpleDateFormat("dd-MMM-yyyy").parse("05-May-2023");
								Date minDate = new SimpleDateFormat("dd-MMM-yyyy").parse("01-Mar-2020");
								if (workTo.after(maxDate) || workTo.before(minDate)) {
									errorList.add("<li>Period of Work-To in row " + count + "should be between 01-Mar-2020 and 05-May-2023.</li>");
									errorField.add("toYear_" + count);
								}
							}
							
							//Period of Work-From and Period of Work-To comparison
							if (StringUtils.isNotBlank(covidDutyCertDetailsBean.getFromYear()) && !covidDutyCertDetailsBean.getFromYear().toLowerCase().contains("nan")
									&& !covidDutyCertDetailsBean.getFromYear().toLowerCase().contains("undefined") && StringUtils.isNotBlank(covidDutyCertDetailsBean.getToYear())
									&& !covidDutyCertDetailsBean.getToYear().toLowerCase().contains("nan")
									&& !covidDutyCertDetailsBean.getToYear().toLowerCase().contains("undefined")) {

								Date workFrom = new SimpleDateFormat("dd-MMM-yyyy").parse(covidDutyCertDetailsBean.getFromYear());
								Date workTo = new SimpleDateFormat("dd-MMM-yyyy").parse(covidDutyCertDetailsBean.getToYear());

								if (workFrom.after(workTo)) { 
									errorList.add("<li>" + "Period of Work-To in row " + count + "should be greater than or equal to Period of Work-From in row " + count + ".</li>");
									errorField.add("fromYear_" + count);
									errorField.add("toYear_" + count);
								}
								
								//Duration of Covid Service
								if (flag.equals("form")) {
									if (StringUtils.isBlank(covidDutyCertDetailsBean.getDurationOfCovidService())) {
										errorList.add("<li>" + "Duration of Covid Service cannont be blank in row " + count + ".Please try selecting Period of Work-From and Period of Work-To again.</li>");
										errorField.add("durationOfCovidService_" + count);
									}
								} else if (flag.equals("database")) {
									if (StringUtils.isBlank(covidDutyCertDetailsBean.getDurationOfCovidService())) {
										errorList.add("<li>" + "Duration of Covid Service cannont be blank in row " + count + ".Please try selecting Period of Work-From and Period of Work-To again.</li>");
										errorField.add("durationOfCovidService_" + count);
									} else if (covidDutyCertDetailsBean.getDurationOfCovidService().toLowerCase().contains("nan") || covidDutyCertDetailsBean.getDurationOfCovidService().toLowerCase().contains("undefined")) {
										errorList.add("<li>" + "Invalid Duration of Covid Service in row " + count + ".Please try selecting Period of Work-From and Period of Work-To again.</li>");
										errorField.add("durationOfCovidService_" + count);
									}
								}
							}
							
							// check date overlapping
							
							Date row1FromDate = null;
							Date row1ToDate = null;
							SimpleDateFormat sdfExp = new SimpleDateFormat("dd-MMM-yyyy");
							
							if (StringUtils.isNotBlank(covidDutyCertDetailsBean.getFromYear())
									&& (StringUtils.isNotBlank(covidDutyCertDetailsBean.getToYear()))) {
								row1FromDate = sdfExp.parse(covidDutyCertDetailsBean.getFromYear());
								row1ToDate = sdfExp.parse(covidDutyCertDetailsBean.getToYear());
							}

							if (row1FromDate != null && row1ToDate != null) {
								for (int i = count; i < covidDutyCertDetailsList.size(); i++) {

									CovidDutyCertDetailsBean covidDetailsBean = covidDutyCertDetailsList.get(i);

									Date row2FromDate = null;
									Date row2ToDate = null;
									//Date fromDate = null;
									if (covidDetailsBean.getFromYear() != null && !covidDetailsBean.getFromYear().equals("")) {
										sdfExp.parse(covidDetailsBean.getFromYear());
									}
									if (StringUtils.isNotBlank(covidDetailsBean.getFromYear())
											&& (StringUtils.isNotBlank(covidDetailsBean.getToYear()))) {
										row2FromDate = sdfExp.parse(covidDetailsBean.getFromYear());
										row2ToDate = sdfExp.parse(covidDetailsBean.getToYear());
									}
									if (row2FromDate != null && row2ToDate != null) {
										if ((row2FromDate.after(row1FromDate) && row2FromDate.before(row1ToDate))
												|| (row2ToDate.after(row1FromDate) && row2ToDate.before(row1ToDate))) {
											errorList.add("<li>Covid Duty Certificate date entered in row " + count + " is overlapping with row " + (i + 1) + ".</li>");
										} else if (row2FromDate.equals(row1FromDate) || row2FromDate.equals(row1ToDate) || row2ToDate.equals(row1FromDate)
												|| row2ToDate.equals(row1ToDate)) {
											errorList.add("<li>Covid Duty Certificate date entered in row " + count + " is overlapping with row " + (i + 1) + ".</li>");
										} else if ((row1FromDate.after(row2FromDate) && row1FromDate.before(row2ToDate))
												|| (row1ToDate.after(row2FromDate) && row1ToDate.before(row2ToDate))) {
											errorList.add("<li>Covid Duty Certificate date entered in row " + count + " is overlapping with row " + (i + 1) + ".</li>");
										}
									}
								}
							}
							
							if (StringUtils.isNotBlank(covidDutyCertDetailsBean.getInstitutionType())) {
								// Certificate Signed By
								if (StringUtils.isBlank(covidDutyCertDetailsBean.getCertificateSignedBy())) {
									errorList.add("<li>" + "Certificate Signed by cannot be blank. Please try selecting Institution Type again." + "</li>");
									errorField.add("certificateSignedBy" + count);
								}
								// Certificate Counter Signed By
								if (StringUtils.isBlank(covidDutyCertDetailsBean.getCertiCounterSignedBy())) {
									errorList.add("<li>" + "Certificate Counter Signed by cannot be blank. Please try selecting Institution Type again." + "</li>");
									errorField.add("certiCounterSignedBy" + count);
								}
							}
							covidDutyCertList.add(covidDutyCertDetailsBean);
						}else {
							errorList.add("<li>Please enter Covid Duty Certificate Details.</li>");
						}
						count++;
					}
					// for loop closed
					
					if (covidDutyCertList.isEmpty()) { 
						errorList.add("<li>Please enter Covid Duty Certificate Details.</li>");
					}
					
					//Total Duration of Covid Service
					if (flag.equals("form")) {
						if (StringUtils.isBlank(covidDutyCertificateBean.getYearOfTotalService()) || StringUtils.isBlank(covidDutyCertificateBean.getMonthOfTotalService())
								|| StringUtils.isBlank(covidDutyCertificateBean.getDayOfTotalService())) {
							errorList.add("<li>" + "Total Duration of Covid Service cannont be blank. Please try selecting Period of Work-From and Period of Work-To again." + "</li>");
							errorField.add("yearOfTotalService");
							errorField.add("monthOfTotalService");
							errorField.add("dayOfTotalService");
						} else if (!ValidatorUtil.isNumeric(covidDutyCertificateBean.getYearOfTotalService()) || covidDutyCertificateBean.getYearOfTotalService().toLowerCase().contains("nan")
								|| covidDutyCertificateBean.getYearOfTotalService().toLowerCase().contains("undefined") || !ValidatorUtil.isNumeric(covidDutyCertificateBean.getMonthOfTotalService())
								|| covidDutyCertificateBean.getMonthOfTotalService().toLowerCase().contains("nan") || covidDutyCertificateBean.getMonthOfTotalService().toLowerCase().contains("undefined")
								|| !ValidatorUtil.isNumeric(covidDutyCertificateBean.getDayOfTotalService()) || covidDutyCertificateBean.getDayOfTotalService().toLowerCase().contains("nan")
								|| covidDutyCertificateBean.getDayOfTotalService().toLowerCase().contains("undefined")) {
							errorList.add("<li>" + "Invalid Total Duration of Covid Service. Please try selecting Period of Work-From and Period of Work-To again." + "</li>");
							errorField.add("yearOfTotalService");
							errorField.add("monthOfTotalService");
							errorField.add("dayOfTotalService");
						}
					} else if (flag.equals("database")) {
						if (StringUtils.isBlank(candidateBean.getTotalDurationOfService()) || candidateBean.getTotalDurationOfService().toLowerCase().contains("nan")
								|| candidateBean.getTotalDurationOfService().toLowerCase().contains("undefined")) {
							errorList.add("<li>" + "Invalid Total Duration of Covid Service. Please try selecting Period of Work-From and Period of Work-To again." + "</li>");
							errorField.add("yearOfTotalService");
							errorField.add("monthOfTotalService");
							errorField.add("dayOfTotalService");
						} else {
							String totalExp = candidateBean.getTotalDurationOfService();
							totalExp = totalExp.replaceAll(" years | months | days", "");
							
							if (totalExp.equals("000") || StringUtils.isBlank(totalExp)) { // 0 years 0 months 0 days
								errorList.add("<li>" + "Invalid Total Duration of Covid Service. Please try selecting Period of Work-From and Period of Work-To again." + "</li>");
							}
						}
					} // End Total Duration of Covid Service
					
					covidDutyCertificateBean.setCovidDutyCertSaveList(covidDutyCertList);
					
				}  // end of line 65
				else {
					errorList.add("<li>Please enter Covid Duty Certificate Details.</li>");
				}
			} // end of line 63
			
			if (errorList != null && !errorList.isEmpty()) {
				covidDutyCertificateBean.setErrorField(errorField);
				errors = ValidatorUtil.validationMessageFormatter(errorList);
			}
		}catch (Exception e) {
			errors = "<li>Some internal error occured, Please try again..!!</li>";
			logger.fatal(e, e);
		}
		return errors;
	}

}
