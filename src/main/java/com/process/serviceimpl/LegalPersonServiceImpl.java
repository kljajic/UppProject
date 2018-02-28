package com.process.serviceimpl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.process.model.Location;
import com.process.model.User;
import com.process.repository.UserRepository;
import com.process.service.LegalPersonService;
import com.process.service.SecurityService;

@Service
@Transactional
public class LegalPersonServiceImpl implements LegalPersonService {
	
	
	private final UserRepository userRepository;
	private final SecurityService securityService;
	
	@Autowired
	public LegalPersonServiceImpl(UserRepository userRepository, SecurityService securityService) {
		this.userRepository = userRepository;
		this.securityService = securityService;
	}
	
	@Override
	public List<User> formCompanyListByCategory(String categoryName) {
		
		User user = securityService.getLoggedUser();
		Calendar tempCal = Calendar.getInstance();
		List<User> companyList = userRepository.findUserByJobCategoriesNameIgnoreCaseAndDateRoundRobinIsBeforeOrderByDateRoundRobinAsc(categoryName, tempCal.getTime());
		companyList.stream().filter(tempUser ->  tempUser.getDistance() > calculateDistance(user.getLocation(), tempUser.getLocation()));
		
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

	@Override
	public List<User> refreshCompanyListByCategory(String categoryName, List<User> companies) {
		
		
		User user = securityService.getLoggedUser();
		Calendar tempCal = Calendar.getInstance();
		List<User> companyList = userRepository.findUserByJobCategoriesNameIgnoreCaseAndDateRoundRobinIsBeforeOrderByDateRoundRobinAsc(categoryName, tempCal.getTime());
		companyList.stream().filter(tempUser ->  tempUser.getDistance() > calculateDistance(user.getLocation(), tempUser.getLocation()));
		
		for(User deniedCompany : companies) {
			for(User company : companyList) {
				if(company.getId() == (deniedCompany.getId())) {
					companyList.remove(company);
					break;
				}
			}
		}
		
		/*
		companies.forEach(deniedCompany -> {
			companyList.stream().filter(company ->  deniedCompany.getId() != company.getId());
		});*/
		
		companyList.stream().forEach(tempUser -> {
			tempUser.setDateRoundRobin(tempCal.getTime());
			userRepository.save(tempUser);
		});
		
		return companyList;
	}
	
}
