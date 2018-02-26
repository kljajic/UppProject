package com.process.service;

import com.process.model.User;

public interface SecurityService {
	
	User loginUser(User user);
	void logoutUser();
	User getLoggedUser();

}
