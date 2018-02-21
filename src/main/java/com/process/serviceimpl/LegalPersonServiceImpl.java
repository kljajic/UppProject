package com.process.serviceimpl;

import java.util.Calendar;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.process.model.Location;
import com.process.model.User;
import com.process.repository.JobCategoryRepository;
import com.process.repository.UserRepository;
import com.process.service.LegalPersonService;

@Service
@Transactional
public class LegalPersonServiceImpl implements LegalPersonService {
	
	
	private final UserRepository userRepository;
	private final JobCategoryRepository jobCategoryRepository;
	private final ProcessEngine processEngine;
	
	public LegalPersonServiceImpl(UserRepository userRepository, JobCategoryRepository jobCategoryRepository, ProcessEngine processEngine) {
		this.userRepository = userRepository;
		this.jobCategoryRepository = jobCategoryRepository;
		this.processEngine = processEngine;
	}
	
	@Override
	public List<User> formCompanyListByCategory(String categoryName) {
		
		
		//User taken from session or a token needs to be implemented for distance and location consideration
		//Dummy info for User
		User user = userRepository.findOne(2L);
		
		Calendar tempCal = Calendar.getInstance();
		
		List<User> companyList = userRepository.findUserByJobCategoriesNameIgnoreCaseAndDateRoundRobinIsBeforeOrderByDateRoundRobinAsc(categoryName, tempCal.getTime());
		
		companyList.stream().filter(tempUser ->  tempUser.getDistance() > calculateDistance(user.getLocation(), tempUser.getLocation()));
		
		//companyList = companyList.sublist(0, parameterWithNumberOfResultsWanted);
		
		companyList.stream().forEach(tempUser -> {
			tempUser.setDateRoundRobin(tempCal.getTime());
			userRepository.save(tempUser);
		});
		
		return companyList;
	}

	@Override
	public void formRangListOfOffers() {
	}

	@Override
	public Double calculateDistance(Location location1, Location location2) {
		
		Double earthRadius = 6371e3; 
		
		Double f1 = Math.toRadians(location1.getLatitude());
		Double f2 = Math.toRadians(location2.getLatitude());
		Double f1Diff = Math.toRadians(location2.getLatitude() - location1.getLatitude());
		
		Double lDiff = Math.toRadians(location2.getLongitude() - location1.getLongitude());

		Double a = Math.sin(f1Diff/2) * Math.sin(f1Diff/2) +
		        Math.cos(f1) * Math.cos(f2) *
		        Math.sin(lDiff/2) * Math.sin(lDiff/2);
		
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		Double distance = earthRadius * c;
		
		return distance;
	}
	
}
