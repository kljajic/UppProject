package com.process.service;

import com.process.model.Location;

public interface LocationService {

	Location createLocation(String adresa, String mesto, String drzava);
	Location getLocation(Long userId);
	
}
