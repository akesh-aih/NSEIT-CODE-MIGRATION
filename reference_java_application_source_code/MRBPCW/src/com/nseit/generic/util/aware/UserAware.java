package com.nseit.generic.util.aware;

import com.nseit.generic.models.Users;

public interface UserAware
{
	public void setLoggedInUser(Users users);
}
