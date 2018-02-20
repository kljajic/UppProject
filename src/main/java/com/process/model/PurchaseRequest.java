package com.process.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "purchase_request")
public class PurchaseRequest implements Serializable {

	private static final long serialVersionUID = -3925913701623826785L;

	@Id
	@SequenceGenerator(name = "PURCHASE_REQUEST_ID_GENERATOR", allocationSize = 10)
	@GeneratedValue(generator = "PURCHASE_REQUEST_ID_GENERATOR")
	private Long id;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "job_description")
	private String jobDescription;
	
	@Column(name = "max_value")
	private Double maxValue;
	
	@Column(name = "offer_due_date")
	private Date offerDueDate;
	
	@Column(name = "min_offers_num")
	private Long minNumberOfOffers;
	
	@Column(name = "job_due_date")
	private Date jobDueDate;
	
	@ManyToOne
	private User initiator;
	
	@OneToMany(mappedBy = "purchaseRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Offer> offers = new HashSet<>();
	
	public PurchaseRequest() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public Date getOfferDueDate() {
		return offerDueDate;
	}

	public void setOfferDueDate(Date offerDueDate) {
		this.offerDueDate = offerDueDate;
	}

	public Long getMinNumberOfOffers() {
		return minNumberOfOffers;
	}

	public void setMinNumberOfOffers(Long minNumberOfOffers) {
		this.minNumberOfOffers = minNumberOfOffers;
	}

	public Date getJobDueDate() {
		return jobDueDate;
	}

	public void setJobDueDate(Date jobDueDate) {
		this.jobDueDate = jobDueDate;
	}
	
	public User getInitiator() {
		return initiator;
	}

	public void setInitiator(User initiator) {
		this.initiator = initiator;
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
