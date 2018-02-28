package com.process.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.process.model.User;
import com.process.model.UserTask;
import com.process.service.SecurityService;
import com.process.service.UserService;
import com.process.service.UserTaskService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final SecurityService securityService;
	private final UserTaskService userTaskService;
	
	@Autowired
	public UserController(UserService userService, SecurityService securityService, UserTaskService userTaskService) {
		this.userService = userService;
		this.securityService = securityService;
		this.userTaskService = userTaskService;
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
	public User login(@RequestBody User user) {
		return securityService.loginUser(user);
	}
	
	@GetMapping("/logout")
	public void logout() {
		securityService.logoutUser();
	}
	
	@PostMapping("/complete")
	public void completeTask(@RequestBody UserTask userTask) {
		this.userTaskService.completeTask(userTask);
	}
	
	@GetMapping("/getTasks")
	@ResponseBody
	public List<UserTask> getUserTasks() {
		return this.userTaskService.getTasksForUser("pera");
	}

}


