package com.process.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.EnumFormType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.process.model.TaskProperty;
import com.process.model.UserTask;
import com.process.service.UserTaskService;

@Service
@Transactional
public class UserTaskServiceImpl implements UserTaskService{

	private final ProcessEngine processEngine;

	@Autowired
	public UserTaskServiceImpl(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	@Override
	public List<UserTask> getTasksForUser(String assignee) {
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
		List<UserTask> userTasks = new ArrayList<>();
		tasks.forEach( (task) -> {
			UserTask userTask = mapTaskToUserTask(task);
			TaskFormData taskFormData = processEngine.getFormService().getTaskFormData(task.getId());
			taskFormData.getFormProperties().forEach( (formProperty) -> {
				TaskProperty taskProperty = mapFormPropertyToTaskProperty(formProperty);
				userTask.getProperties().add(taskProperty);
			});
			userTasks.add(userTask);
		});
		return userTasks;
	}
	
	@Override
	public void completeTask(UserTask userTask) {
		System.out.println("Pera odobrava task:" + userTask.getId() + ", " + userTask.getId());
		TaskService taskService = processEngine.getTaskService();
		Map<String, Object> taskVariables = mapTaskValues(userTask);
		taskService.complete(userTask.getId(), taskVariables);
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("pera").list();
		System.out.println("Nakon odobravanja taska pera ima: " + tasks.size() + " taskova");
		System.out.println("Trenutno imam aktivnih procesa: " + processEngine.getRuntimeService().createProcessInstanceQuery().count());
		System.out.println("Trenutno imam aktivnih procesa nakon izvrsenja perinog taska: " + processEngine.getRuntimeService().createProcessInstanceQuery().count());
	}
	
	private UserTask mapTaskToUserTask(Task task) {
		UserTask userTask = new UserTask();
		userTask.setId(task.getId());
		userTask.setName(task.getName());
		userTask.setOwner(task.getOwner());
		userTask.setAssignee(task.getAssignee());
		userTask.setCategory(task.getCategory());
		userTask.setDescription(task.getDescription());
		userTask.setDueDate(task.getDueDate());
		userTask.setCreationTime(task.getCreateTime());
		userTask.setProcessInstanceId(task.getProcessInstanceId());
		return userTask;
	}
	
	private TaskProperty mapFormPropertyToTaskProperty(FormProperty formProperty) {
		TaskProperty taskProperty = new TaskProperty();
		taskProperty.setId(formProperty.getId());
		taskProperty.setName(formProperty.getName());
		taskProperty.setValue(formProperty.getValue());
		taskProperty.setReadable(formProperty.isReadable());
		taskProperty.setRequired(formProperty.isRequired());
		taskProperty.setWritable(formProperty.isWritable());
		taskProperty.setType(getFormPropertyType(formProperty));
		taskProperty.setValues(getFormPropertyValues(formProperty));
		
		return taskProperty;
	}
	
	private String getFormPropertyType(FormProperty formProperty) {
		if(formProperty.getType() != null) {
			return formProperty.getType().getName();
		}
		return "string";
	}
	
	private Map<String, String> getFormPropertyValues(FormProperty formProperty) {
		try {
			if(formProperty.getType() instanceof EnumFormType) {
				Object information = formProperty.getType().getInformation("values");
				if(information instanceof Map<?, ?>) {
					@SuppressWarnings("unchecked")
					Map<String, String> map = (Map<String, String>) information;
					return map;
				} else {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", formProperty.getValue());
					return map;
			} 
			} else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("value", formProperty.getValue());
				return map;
			} 
		} catch(NullPointerException exception) {
			System.out.println(getFormPropertyType(formProperty) + " : " + formProperty.getName() + ": Null value");
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", "null");
			return map;
		}
	}
	
	private Map<String, Object> mapTaskValues(UserTask userTask) {
		Map<String, Object> taskValues = new HashMap<>();
		userTask.getProperties().forEach( (taskProperty) -> {
			taskValues.put(taskProperty.getId(), taskProperty.getValue());
		});
		return taskValues;
	}
}
