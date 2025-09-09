package com.nseit.generic.dao;

public interface OTPMasterDao {
	public enum State {
		NONE, SENT, VERIFIED
	}

	// public State getEmailOTPState(String email) throws LogicError;

}
