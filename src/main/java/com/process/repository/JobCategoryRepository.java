package com.process.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.process.model.JobCategory;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

	@Query("select jobCategory from JobCategory jobCategory where upper(jobCategory.name) = ?1")
	JobCategory findJobCategoryByName(String name);
	
}
