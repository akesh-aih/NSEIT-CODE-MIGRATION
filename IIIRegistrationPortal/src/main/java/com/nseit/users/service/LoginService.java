package com.nseit.users.service;

import com.nseit.users.models.UserBean;

public interface LoginService {

    UserBean authenticate(String username, String password);
    void resetPassword(String loginId, String emailId);
    String changePassword(String loginId, String oldPassword, String newPassword);

}