package com.nseit.users.dao;

import com.nseit.users.models.UserBean;
import java.util.List;
import java.util.Map;

public interface UserManagementDao {

    List<Map<String, Object>> getMenuPermissions(int userId);


    String saveUser(UserBean user, int currentUserId);

}
