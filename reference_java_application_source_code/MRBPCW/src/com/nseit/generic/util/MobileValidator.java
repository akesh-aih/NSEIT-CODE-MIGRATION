package com.nseit.generic.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileValidator {

	public static boolean isValidMsisdn(String str){
		boolean isNumber = isNumeric(str);
		if(isNumber){
			Pattern pattern = Pattern.compile("[0-9]{10}");
			Matcher matcher = pattern.matcher(str);
			if (matcher.matches()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
	
	public static boolean isNumeric(String str){  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

}
