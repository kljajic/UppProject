package com.process.service;

import com.process.model.PurchaseRequest;

public interface PurchaseRequestService {

	PurchaseRequest createPurchaseRequest(String categoryName, String jobDescription, Double maxPrice, 
										  String offerDueDate, Long minimumNumberOfOffers, String jobDueDate);
	
}
