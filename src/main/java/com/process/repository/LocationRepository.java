package com.process.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.process.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	
	List<Location> findLocationsByLatitudeAndLongitude(Double latitude, Double longitude);

}
