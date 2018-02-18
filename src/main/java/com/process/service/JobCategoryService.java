package com.process.service;

import java.util.List;

import com.process.model.JobCategory;

public interface JobCategoryService {

	JobCategory createJobCategory(JobCategory jobCategory);
	JobCategory findJobCategoryByName(String name);
	JobCategory getJobCategory(Long jobCategoryId);
	List<JobCategory> getAllJobCategories();
	JobCategory updateJobCategory(JobCategory jobCategory);
	void deleteJobCategory(Long jobCategoryId);
	
}
