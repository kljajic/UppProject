package com.process.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="rating")
public class Rating implements Serializable{

	private static final long serialVersionUID = -199651029137922155L;

	@Id
	@SequenceGenerator(name = "RATING_ID_GENERATOR", allocationSize = 10)
	@GeneratedValue(generator = "RATING_ID_GENERATOR")
	private Long id;
	
	@Column
	private int rating;
	
	@Column
	private Date date;
	
	@ManyToOne
	private User userRating;
	
	@ManyToOne
	private User ratedUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUserRating() {
		return userRating;
	}

	public void setUserRating(User userRating) {
		this.userRating = userRating;
	}

	public User getRatedUser() {
		return ratedUser;
	}

	public void setRatedUser(User ratedUser) {
		this.ratedUser = ratedUser;
	}
	
}
