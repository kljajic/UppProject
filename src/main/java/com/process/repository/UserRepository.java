package com.process.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.process.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, Serializable{

	User findUsersByEmailAndUsername(String email, String username);
	
}
