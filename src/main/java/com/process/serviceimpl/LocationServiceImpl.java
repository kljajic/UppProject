package com.process.serviceimpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.process.model.Location;
import com.process.repository.LocationRepository;
import com.process.service.LocationService;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

	private final LocationRepository locationRepository;
	
	@Autowired
	public LocationServiceImpl(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}
	
	@Override
	public Location createLocation(String adresa, String mesto, String drzava) {
		System.out.println("LOKACIJA USAO!");
		Location location = new Location();
		final String apiKey = "AIzaSyDa0TStpnBWBQH3JLGwwhGs0MwhOgMiCDc";
		GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
		GeocodingResult[] results = null;
		try {
			results = GeocodingApi.geocode(context, adresa + " " + mesto + ", " + drzava).await();
			location.setLatitude(results[0].geometry.location.lat);
			location.setLongitude(results[0].geometry.location.lng);
			System.out.println(results[0].geometry.location.lat + " -- " + results[0].geometry.location.lng);
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
		List<Location> locations = this.locationRepository.findLocationsByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
		if(locations.size() != 0) {
			location = locations.get(0);
		}else {
			locationRepository.save(location);
		}
		return location;
	}

	@Override
	@Transactional(readOnly = true)
	public Location getLocation(Long userId) {
		return null;
	}

}
