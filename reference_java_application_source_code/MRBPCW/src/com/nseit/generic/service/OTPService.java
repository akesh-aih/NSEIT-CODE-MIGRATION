package com.nseit.generic.service;

public interface OTPService {
	public enum Result {
		E_SENT, E_RESENT, EMAIL_EXISTS, MOBILE_EXISTS, SUCCESS, FAIL, M_SENT, M_RESENT
	}

	// public Result checkOTP (RegistrationBean registrationBean,String
	// otpflag)throws LogicError, Exception;

}
