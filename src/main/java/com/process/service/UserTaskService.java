package com.process.service;

import java.util.List;
import com.process.model.UserTask;

public interface UserTaskService {

	List<UserTask> getTasksForUser(String assignee);
	void completeTask(UserTask userTask);
	
}
