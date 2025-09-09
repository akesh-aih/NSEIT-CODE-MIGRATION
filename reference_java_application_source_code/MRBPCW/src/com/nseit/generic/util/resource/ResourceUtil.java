package com.nseit.generic.util.resource;

import java.util.ResourceBundle;

public class ResourceUtil
{
	private static ResourceBundle validationErrorMessageBundle;
	private static String VALIDATION_ERROR_MESSAGE_PROPERTIES = "com.nseit.generic.util.resource.ValidationMessage";
	
	static
	{
		validationErrorMessageBundle = ResourceBundle.getBundle(VALIDATION_ERROR_MESSAGE_PROPERTIES);
	}

	public static String getValidationErrorMessageProperty(String key)
	{
		return validationErrorMessageBundle.getString(key);
	}
	
}