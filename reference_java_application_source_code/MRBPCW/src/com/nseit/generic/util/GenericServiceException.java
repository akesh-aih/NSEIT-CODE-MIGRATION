package com.nseit.generic.util;

public class GenericServiceException extends GenericException {

	public GenericServiceException(Throwable ex) {
		super(ex);
	}

	public GenericServiceException(String errDescription) {
		super(errDescription);
	}

}
