package com.process.serviceimpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.process.model.JobCategory;
import com.process.model.Location;
import com.process.model.User;
import com.process.model.UserType;
import com.process.repository.UserRepository;
import com.process.service.JobCategoryService;
import com.process.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final long serialVersionUID = 3791634592430195341L;
	
	private final UserRepository userRepository;
	private final JobCategoryService jobCategoryService;
	private final ProcessEngine processEngine;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,
						   JobCategoryService jobCategoryService,
						   ProcessEngine processEngine) {
		this.userRepository = userRepository;
		this.jobCategoryService = jobCategoryService;
		this.processEngine = processEngine;
	}
	
	@Override
	public User createUser(String name, String email, String username, String password,
						   String address, String city, String country, String zipCode, 
						   String userType, String jobCategories, String distance, Location location) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);
		user.setAddress(address);
		user.setCity(city);
		user.setCountry(country);
		user.setZipCode(zipCode);
		user.setType(UserType.valueOf(userType.toUpperCase()));
		if(user.getType() == UserType.PRAVNO) {
			user.setJobCategories(parseJobCategoriesFromProcess(jobCategories));
			user.setDistance(Double.valueOf(distance));
		}
		user.setLocation(location);
		return this.userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUser(Long userId) {
		return this.userRepository.getOne(userId);
	}

	@Override
	public void deleteUser(String username, String email) {
		Long userId = this.userRepository.findUsersByEmailAndUsername(email, username).getId();
		this.userRepository.delete(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean checkUnique(String username, String email) {
		return this.userRepository.findUsersByEmailAndUsername(email, username) == null ? true : false;
	}
	
	@Override
	public void startRegisterProcess() {
		Map<String, Object> registerProcessVariables = new HashMap<String, Object>();
		TaskService taskService = processEngine.getTaskService();/////
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("pera").list();/////
		System.out.println("Pera pre startovanja procesa ima: " + tasks.size() + " taskova");/////
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("registerProcess", registerProcessVariables);
		processInstance.getProcessVariables().put("processInstanceId", processInstance.getId());
		System.out.println("ID PROCESA: " + processInstance.getId());
		tasks = processEngine.getTaskService().createTaskQuery().taskAssignee("pera").list();/////
		System.out.println("Pera nakon startovanja procesa ima: " + tasks.size() + " taskova");
	}

	private Set<JobCategory> parseJobCategoriesFromProcess(String inputJobCategories) {
		String[] categories = inputJobCategories.split(",");
		Set<JobCategory> jobCategories = new HashSet<>();
		for(String category: categories) {
			JobCategory jobCategory = new JobCategory(category.trim());
			jobCategory = jobCategoryService.createJobCategory(jobCategory);
			jobCategories.add(jobCategory);
		}
		return jobCategories;
	}
	
}
