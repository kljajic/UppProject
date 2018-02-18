package com.process.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.process.model.JobCategory;
import com.process.repository.JobCategoryRepository;
import com.process.service.JobCategoryService;

@Service
@Transactional
public class JobCategoryServiceImpl implements JobCategoryService {

	private final JobCategoryRepository jobCategoryRepository;
	
	@Autowired
	public JobCategoryServiceImpl(JobCategoryRepository jobCategoryRepository) {
		this.jobCategoryRepository = jobCategoryRepository;
	}
	
	@Override
	public JobCategory createJobCategory(JobCategory jobCategory) {
		JobCategory existingJobCategory = jobCategoryRepository.findJobCategoryByName(jobCategory.getName().toUpperCase());
		if(existingJobCategory == null) {
			return jobCategoryRepository.save(jobCategory);
		}
		return existingJobCategory;
	}

	@Override
	@Transactional(readOnly = true)
	public JobCategory findJobCategoryByName(String name) {
		return jobCategoryRepository.findJobCategoryByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public JobCategory getJobCategory(Long jobCategoryId) {
		return jobCategoryRepository.getOne(jobCategoryId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<JobCategory> getAllJobCategories() {
		return jobCategoryRepository.findAll();
	}

	@Override
	public JobCategory updateJobCategory(JobCategory jobCategory) {
		JobCategory updateJobCategory = jobCategoryRepository.getOne(jobCategory.getId());
		updateJobCategory.setName(jobCategory.getName());
		return this.createJobCategory(jobCategory);
	}

	@Override
	public void deleteJobCategory(Long jobCategoryId) {
		jobCategoryRepository.delete(jobCategoryId);
	}

}
