package com.nseit.users.dao;

import com.nseit.users.models.UserBean;

public interface LoginDao {

    UserBean findByUsername(String username);
    void logLogin(int userId, String loginId);
    void updateLoginStatus(String loginId);
    UserBean findUserByLoginIdAndEmail(String loginId, String emailId);
    void updatePassword(String loginId, String newEncryptedPassword, String newEncryptedTrxnPassword, boolean changePwdOnNextLogin, boolean isSuspended);

}
