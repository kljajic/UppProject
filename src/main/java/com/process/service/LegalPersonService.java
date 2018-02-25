package com.process.service;

import java.util.List;

import com.process.model.Location;
import com.process.model.User;

public interface LegalPersonService {
	
	List<User> formCompanyListByCategory(String categoryName);
	void formRangListOfOffers();	
	Double calculateDistance(Location location1, Location location2);
	List<User> refreshCompanyListByCategory(String categoryName, List<User> companies);
}
