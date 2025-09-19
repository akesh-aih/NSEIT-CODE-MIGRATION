package com.nseit.users.dao;

import com.nseit.users.models.User;
import java.util.List;
import java.util.Map;

public interface UserDao {

    User findByUsername(String username);

    void logLogin(int userId, String loginId);

    void updateLoginStatus(String loginId);

    List<Map<String, Object>> getMenuPermissions(int userId);


    String saveUser(User user, int currentUserId);

    User findUserByLoginIdAndEmail(String loginId, String emailId);

    void updatePassword(String loginId, String newEncryptedPassword, String newEncryptedTrxnPassword, boolean changePwdOnNextLogin, boolean isSuspended);
}
