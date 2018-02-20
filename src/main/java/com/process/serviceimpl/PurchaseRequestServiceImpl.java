package com.process.serviceimpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.process.model.PurchaseRequest;
import com.process.repository.PurchaseRequestRepository;
import com.process.service.PurchaseRequestService;

@Service
@Transactional
public class PurchaseRequestServiceImpl implements PurchaseRequestService {

	private final PurchaseRequestRepository purchaseRequestRepository;
	
	@Autowired
	public PurchaseRequestServiceImpl(PurchaseRequestRepository purchaseRequestRepository) {
		this.purchaseRequestRepository = purchaseRequestRepository;
	}
	
	@Override
	public PurchaseRequest createPurchaseRequest(String categoryName, String jobDescription, Double maxPrice,
			Date offerDueDate, Long minimumNumberOfOffers, Date jobDueDate) {
		PurchaseRequest purchaseRequest = new PurchaseRequest();
		purchaseRequest.setCategory(categoryName);
		purchaseRequest.setJobDescription(jobDescription);
		purchaseRequest.setMaxValue(maxPrice);
		purchaseRequest.setOfferDueDate(offerDueDate);
		purchaseRequest.setMinNumberOfOffers(minimumNumberOfOffers);
		purchaseRequest.setJobDueDate(jobDueDate);
		return purchaseRequestRepository.save(purchaseRequest);
	}

}
