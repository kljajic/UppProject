package com.process.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.process.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, Serializable{

	User findUsersByEmailAndUsername(String email, String username);
	List<User> findUserByJobCategoriesNameIgnoreCaseAndDateRoundRobinIsBeforeOrderByDateRoundRobinAsc(String jobCategory, Date dateRoundRobin);
	User findUserByUsername(String username);
}
