package com.process.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "user")
public class User implements Serializable{

	private static final long serialVersionUID = 7537973865159120549L;

	@Id
	@SequenceGenerator(name = "USER_ID_GENERATOR", allocationSize = 10)
	@GeneratedValue(generator = "USER_ID_GENERATOR")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "zip_code")
	private String zipCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "user_type")
	private UserType type;
	
	@Column(name = "activated")
	private Boolean isActivated;
	
	@Column(name = "distance")
	private Double distance;
	
	@Column(name = "registration_link")
	private String registrationLink;
	
	@Column(name = "date_round_robin")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
	private Date dateRoundRobin;
	
	@ManyToOne
	private Location location;
	
	@ManyToMany(targetEntity = com.process.model.JobCategory.class)
	private Set<JobCategory> jobCategories = new HashSet<>();
	
	@OneToMany(mappedBy = "initiator", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PurchaseRequest> purchaseRequests = new HashSet<>();
	
	@OneToMany(mappedBy = "initiator", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Offer> offers = new HashSet<>();

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public Boolean getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean isActivated) {
		this.isActivated = isActivated;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	@JsonIgnore
	public Set<JobCategory> getJobCategories() {
		return jobCategories;
	}

	@JsonProperty
	public void setJobCategories(Set<JobCategory> jobCategories) {
		this.jobCategories = jobCategories;
	}

	public String getRegistrationLink() {
		return registrationLink;
	}

	public Date getDateRoundRobin() {
		return dateRoundRobin;
	}

	public void setDateRoundRobin(Date dateRoundRobin) {
		this.dateRoundRobin = dateRoundRobin;
	}

	public void setRegistrationLink(String registrationLink) {
		this.registrationLink = registrationLink;
	}
	
	@JsonIgnore
	public Set<PurchaseRequest> getPurchaseRequests() {
		return purchaseRequests;
	}

	@JsonProperty
	public void setPurchaseRequests(Set<PurchaseRequest> purchaseRequests) {
		this.purchaseRequests = purchaseRequests;
	}
	
	@JsonIgnore
	public Set<Offer> getOffers() {
		return offers;
	}

	@JsonProperty
	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

}
