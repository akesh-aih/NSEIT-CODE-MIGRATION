package com.nseit.generic.service.impl;

import java.util.Random;

import com.nseit.generic.dao.OTPMasterDao;
import com.nseit.generic.dao.RegistraionDAO;
import com.nseit.generic.dao.UserDao;
import com.nseit.generic.service.OTPService;

public class OTPServiceImpl implements OTPService {
	private OTPMasterDao m_OTPMasterDao;
	private UserDao m_UserDao;
	public RegistraionDAO registraionDAO;

	public void setRegistraionDAO(RegistraionDAO registraionDAO) {
		this.registraionDAO = registraionDAO;
	}

	private static final int m_length = 6;

	public void setOTPMasterDao(OTPMasterDao otpMasterDao) {
		this.m_OTPMasterDao = otpMasterDao;
	}

	public void setUserDao(UserDao userDao) {
		this.m_UserDao = userDao;
	}

	static String generate(int len) {
		// System.out.println("Generating OTP using random() : ");
		// System.out.print("You OTP is : ");

		// Using numeric values
		String numbers = "0123456789";

		// Using random method
		Random rndm_method = new Random();

		char[] otp = new char[len];
		StringBuilder otp1 = new StringBuilder("");
		for (int i = 0; i < len; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
			otp1.append(numbers.charAt(rndm_method.nextInt(numbers.length())));
		}
		return otp1.toString();
	}

}
