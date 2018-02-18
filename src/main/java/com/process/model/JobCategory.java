package com.process.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "job_category")
public class JobCategory {

	@Id
	@SequenceGenerator(name = "JOB_CETEGORY_ID_GENERATOR", allocationSize = 10)
	@GeneratedValue(generator = "JOB_CETEGORY_ID_GENERATOR")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToMany(targetEntity = com.process.model.User.class, mappedBy = "jobCategories")
	private Set<User> users = new HashSet<>();
	
	public JobCategory() {
	}
	
	public JobCategory(String name) {
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}

	@JsonProperty
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
