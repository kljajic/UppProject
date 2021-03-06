package com.process.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.process.model.User;
import com.process.model.UserTask;
import com.process.service.SecurityService;
import com.process.service.UserTaskService;

@RestController
@RequestMapping("/api/tasks")
public class UserTaskController {

	private final UserTaskService userTaskService;
	
	@Autowired 
	private SecurityService userDetailsCustomService;
	
	@Autowired
	public UserTaskController(UserTaskService userTaskService) {
		this.userTaskService = userTaskService;
	}
	
	@GetMapping
	@ResponseBody
	public List<UserTask> getUserTasks() {
		User user = userDetailsCustomService.getLoggedUser();
		if(user == null)
			return null;
		return this.userTaskService.getTasksForUser(user.getUsername());
	}
	
	@PostMapping
	public void completeTask(@RequestBody UserTask userTask) {
		this.userTaskService.completeTask(userTask);
	}
	
}
