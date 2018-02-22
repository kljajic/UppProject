package com.process.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.process.model.User;
import com.process.repository.UserRepository;
import com.process.service.UserDetailsCustomService;

@Service
public class UserDetailsCustomServiceImpl implements UserDetailsCustomService {
	
	private final UserRepository userRepository;
	
	public UserDetailsCustomServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetails userDetails;
		User user = userRepository.findUserByUsername(username);
		
		if(user != null) {
			Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
	        grantedAuthorities.add(new SimpleGrantedAuthority("user"));
			userDetails =  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
			return userDetails;
		}
		throw new UsernameNotFoundException("No user found. Username tried: " + username);
	}
	
}
