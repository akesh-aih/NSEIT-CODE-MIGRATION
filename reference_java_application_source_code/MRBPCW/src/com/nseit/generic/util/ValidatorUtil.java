package com.nseit.generic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public abstract class ValidatorUtil
{
	private static String numericRegularExpression = "^\\-1|[0-9]+$";
	private static String decimalRegularExpression = "[0-9]+(\\.[0-9][0-9]?)?";

	private static String alphaNumericRegularExpression = "^\\-1|[a-zA-Z0-9]+$";
	private static String pwdRegularExpression = "^\\-1|[a-zA-Z0-9_-`~!@#$%&*=+'<>?]{8,}+$";
	
	private static Pattern numericPattern = Pattern.compile(numericRegularExpression);
	private static Pattern decimalPattern = Pattern.compile(decimalRegularExpression);
	private static Pattern alphaNumericPattern = Pattern.compile(alphaNumericRegularExpression);
	private static Pattern pwdPattern = Pattern.compile(pwdRegularExpression);
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	
	private static String whiteListRegularExpression = "^[a-zA-Z0-9\\-\\{\\}\\.@_!\\ #\\$^\\&\\(\\)=\\+/,~`:\\?|\\\\]*$";
	private static Pattern pattern = Pattern.compile(whiteListRegularExpression);
	
	public static String alphaNumSpace = "[a-zA-Z0-9]+$";
	public static String alphaSpace = "[a-zA-Z ]";
	public static String rollNo = "[a-zA-Z0-9 -/\\\\]+$";
	
	private static DateTimeFormatter dateFormatter;
    
    public void DateValidatorUsingLocalDate(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }
	
	public static boolean isEmpty(String value)
	{
		return CommonUtil.getParameter(value).compareTo("")==0;
	}
	
	public static boolean isNumeric(String value)
	{
		return numericPattern.matcher(value).matches();
	}
	
	public static boolean isDecimal(String value)
	{
		return decimalPattern.matcher(value).matches();
	}
	
	public static boolean isalphaNumeric(String value)
	{
		return alphaNumericPattern.matcher(value).matches();
	}
	
	public static boolean isValidPaasword(String value)
	{
		//boolean isWhitespace = value.matches("^\\s*$");
		if(isEmpty(value) || !pwdPattern.matcher(value).matches())
			return false;
		return true;

	}
	
	public static boolean isValidDate(String date)
	{
		if("".equalsIgnoreCase(date))
		{
			return false;
		}
		try
		{
			Date date1 = sdf.parse(sdf.format(new Date(date)));
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	public static boolean validateRequestParameters(HttpServletRequest request)
	{
		StringBuilder allParameterValues = new StringBuilder(request.getParameterMap().size()*4);
		Enumeration<String> parametersNamesEnum = request.getParameterNames();
		
		while(parametersNamesEnum.hasMoreElements())
		{
			String[] parameterValues = request.getParameterValues(parametersNamesEnum.nextElement());
			
			for(String parameterValue : parameterValues)
			{
				allParameterValues.append(parameterValue==null ? "" : parameterValue);
			}
		}
		
		//boolean isValid = pattern.matcher(allParameterValues.toString()).matches();
		boolean isValid = true;
		
		return isValid;
	}
	
	/**
	 * Format Server side validations
	 * @author Pankaj
	 */
	public static String validationMessageFormatter(List<String> errorList){
		StringBuilder formaatedErrorMessages = new StringBuilder();
		
		//formaatedErrorMessages.append("").append(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_START)).append("<br/><br/>");
		for (int i = 0; i < errorList.size(); i++)
		{
			formaatedErrorMessages.append("");
			formaatedErrorMessages.append(errorList.get(i));
			if (i!=errorList.size()-1){
				formaatedErrorMessages.append(" <br/>");
			}
			formaatedErrorMessages.append("");
		}
		//formaatedErrorMessages = formaatedErrorMessages .append("<br/>").append( ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_END)); 
		formaatedErrorMessages.append("");
		
		return formaatedErrorMessages.toString();
	}
	
	public static String validationMessageFormatterForImages(List<String> errorList){
		StringBuilder formaatedErrorMessages = new StringBuilder();
		
		//formaatedErrorMessages.append("").append(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_START)).append("<br/><br/>");
		for (int i = 0; i < errorList.size(); i++)
		{
			formaatedErrorMessages.append("");
			formaatedErrorMessages.append(errorList.get(i));
			if (i!=errorList.size()-1){
				formaatedErrorMessages.append(" \n");
			}
			formaatedErrorMessages.append("");
		}
		//formaatedErrorMessages = formaatedErrorMessages .append("<br/>").append( ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_END)); 
		formaatedErrorMessages.append("");
		
		return formaatedErrorMessages.toString();
	}
	
	/**
	 * Format Server side validations
	 * @author Pankaj
	 */
	public static String validationMessageFormatterforDoc(List<String> errorList){
		StringBuilder formaatedErrorMessages = new StringBuilder();
		
		//formaatedErrorMessages.append("").append(ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_START)).append("<br/><br/>");
		for (int i = 0; i < errorList.size(); i++)
		{
			formaatedErrorMessages.append("");
			formaatedErrorMessages.append(errorList.get(i)).append(",");
			formaatedErrorMessages.append("");
		}
		//formaatedErrorMessages = formaatedErrorMessages .append("<br/>").append( ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.SS_COMMON_WARNING_END)); 
		formaatedErrorMessages.append("");
		
		return formaatedErrorMessages.toString();
	}

	
	//For New NCFE Code
	
	public static Boolean validatePattern(String value,String initLength, String maxLength) {
		return Pattern.matches("[a-zA-Z0-9 -/\\\\.&]+${"+initLength+ ","+maxLength+"}",value.trim());
		
	}
	
	public static Boolean validatePatternWithMinMax(String pattern ,String value,String initLength, String maxLength) {
		boolean matches = Pattern.matches(""+pattern+"{"+initLength+ ","+maxLength+"}",value.trim());
		return matches;
		
	}

	public static Date getStandardFormattedDate(String dateOfBirth) throws ParseException {
		return new SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirth);
	}
	
	public static boolean isValid(String dateStr) {
        try {
            LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }	

}
