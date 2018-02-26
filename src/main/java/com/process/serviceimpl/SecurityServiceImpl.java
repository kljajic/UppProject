package com.process.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.process.config.WebSecurityConfiguration;
import com.process.model.User;
import com.process.repository.UserRepository;
import com.process.service.SecurityService;
import com.process.service.UserDetailsCustomService;

@Service
public class SecurityServiceImpl implements SecurityService {
	
	private final UserRepository userRepository;
	private final UserDetailsCustomService userDetailsCustomService;
	
	@Autowired
	private WebSecurityConfiguration webSecurityConfiguration;
	
	public SecurityServiceImpl(UserRepository userRepository, UserDetailsCustomService userDetailsCustomService) {
		this.userRepository = userRepository;
		this.userDetailsCustomService = userDetailsCustomService;
	}

	@Override
	public User loginUser(User user) {
		UserDetails userDetails = userDetailsCustomService.loadUserByUsername(user.getUsername());
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, user.getPassword(), null);
		try {
			webSecurityConfiguration.authenticationManagerBean().authenticate(authenticationToken);
		} catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);
			return null;
		}
		
		if (authenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			User loggedInUser =  getLoggedUser();
			return loggedInUser;
		}
		return null;
	}
	
	@Override
	public void logoutUser() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	@Override
	public User getLoggedUser() {
		if(SecurityContextHolder.getContext().getAuthentication() != null){
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return userRepository.findUserByUsername(username);
		} 
		return null;
	}

}
