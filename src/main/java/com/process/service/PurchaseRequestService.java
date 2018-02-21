package com.process.service;

import java.util.Date;

import com.process.model.PurchaseRequest;

public interface PurchaseRequestService {

	PurchaseRequest createPurchaseRequest(String categoryName, String jobDescription, Double maxPrice, 
										  String offerDueDate, Long minimumNumberOfOffers, String jobDueDate);
	
}
