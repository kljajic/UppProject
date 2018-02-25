package com.process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.process.model.User;
import com.process.service.SecurityService;
import com.process.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final SecurityService securityService;
	
	@Autowired
	public UserController(UserService userService, SecurityService securityService) {
		this.userService = userService;
		this.securityService = securityService;
	}
	
	@GetMapping("/register")
	public void startRegisterProcess() {
		this.userService.startRegisterProcess();
	}
	
	@GetMapping("/registerStop")
	public void stopRegisterProcess(@RequestParam(required=true, name="processInstanceId") String processInstanceId) {
		this.userService.stopRegisterProcess(processInstanceId);
		System.out.println("Terminiran proces registracije => ID = " + processInstanceId);
	}
	
	@PostMapping("/login")
	public void login(@RequestBody User user) {
		securityService.loginUser(user);
	}
	
	@GetMapping("/logout")
	public void logout() {
		securityService.logoutUser();
	}
}
