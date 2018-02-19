package com.process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.process.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;	
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/register")
	public void startRegisterProcess() {
		this.userService.startRegisterProcess();
	}
	
	@GetMapping("/confirmRegistration")
	public void confirmRegistration(@RequestParam(value = "registrationLink", required = true) String registrationLink,
									@RequestParam(value = "userId", required = true) Long userId) {
		
		if(this.userService.confirmRegistration(registrationLink, userId)) {
			System.out.println("Successfully confirmed => " + userId);
			return;
		} 
		System.out.println("Unsuccessful confirmation (already activated or invalid registration link) => " + userId);
	}
}
