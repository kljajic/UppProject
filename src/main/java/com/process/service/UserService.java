package com.process.service;

import java.io.Serializable;

import com.process.model.Location;
import com.process.model.User;

public interface UserService extends Serializable {

	User createUser(String name, String email, String username, String password,
					String address, String city, String country, String zipCode, 
					String userType, Location location, String jobCategories, String distance);
	User getUser(Long userId);
	void deleteUser(String username, String email);
	boolean checkUnique(String username,String email);
	void startRegisterProcess();
	void activateAcount(String email, String username, String link);
	String generateRegistrationLink(String username);
	boolean confirmRegistration(String registrationLink, User user);
}
