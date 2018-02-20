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
@Table(name = "offer")
public class Offer implements Serializable {

	private static final long serialVersionUID = -1601953100176981040L;
	
	@Id
	@SequenceGenerator(name = "OFFER_ID_GENERATOR", allocationSize = 10)
	@GeneratedValue(generator = "OFFER_ID_GENERATOR")
	private Long id;
	
	@Column(name = "cancel_offer")
	private Boolean cancelOffer;
	
	@Column(name = "expenses")
	private Double expenses;
	
	@Column(name = "job_due_date")
	private Date jobDueDate;
	
	@Column(name = "accepted")
	private Boolean accepted;
	
	@ManyToOne
	private User initiator;
	
	@ManyToOne
	private PurchaseRequest purchaseRequest;
	
	public Offer() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getCancelOffer() {
		return cancelOffer;
	}

	public void setCancelOffer(Boolean cancelOffer) {
		this.cancelOffer = cancelOffer;
	}

	public Double getExpenses() {
		return expenses;
	}

	public void setExpenses(Double expenses) {
		this.expenses = expenses;
	}

	public Date getJobDueDate() {
		return jobDueDate;
	}

	public void setJobDueDate(Date jobDueDate) {
		this.jobDueDate = jobDueDate;
	}
	
	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public User getInitiator() {
		return initiator;
	}

	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}
	
}
