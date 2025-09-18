package com.nseit.users.service;

import com.nseit.users.models.User;

public interface UserService {

    User authenticate(String username, String password);

}
