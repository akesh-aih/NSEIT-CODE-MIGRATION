package com.nseit.generic.action;

import org.apache.commons.text.StringEscapeUtils;

public class Convertor {
	
	public void convertFont() {
		 	String str = "हिन्दी में टाइप करें) in 2023";
	        String response = StringEscapeUtils.escapeJava(str);
	        System.out.println(response);
	}
	 public static void main(String[] args)
	    {
	       Convertor con = new Convertor();
	       con.convertFont();
	    }
}
