package com.process.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.process.model.PurchaseRequest;
import com.process.repository.PurchaseRequestRepository;
import com.process.service.PurchaseRequestService;
import com.process.service.SecurityService;

@Service
@Transactional
public class PurchaseRequestServiceImpl implements PurchaseRequestService {

	private final PurchaseRequestRepository purchaseRequestRepository;
	private final SecurityService securityService;
	
	@Autowired
	public PurchaseRequestServiceImpl(PurchaseRequestRepository purchaseRequestRepository, SecurityService securityService) {
		this.purchaseRequestRepository = purchaseRequestRepository;
		this.securityService = securityService;
	}
	
	@Override
	public PurchaseRequest createPurchaseRequest(String categoryName, String jobDescription, Double maxPrice,
			String offerDueDateS, Long minimumNumberOfOffers, String jobDueDateS) {
		PurchaseRequest purchaseRequest = new PurchaseRequest();
		purchaseRequest.setCategory(categoryName);
		purchaseRequest.setJobDescription(jobDescription);
		purchaseRequest.setMaxValue(maxPrice);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date offerDueDate = null;
		Date jobDueDate = null;
		try {
			offerDueDate = sdf.parse(offerDueDateS);
			jobDueDate = sdf.parse(jobDueDateS);
		} catch (ParseException e) {
			System.out.println("Njet datum parsiranje");
		}
		purchaseRequest.setJobDueDate(jobDueDate);
		purchaseRequest.setOfferDueDate(offerDueDate);
		purchaseRequest.setMinNumberOfOffers(minimumNumberOfOffers);
		purchaseRequest.setInitiator(securityService.getLoggedUser());
		return purchaseRequestRepository.save(purchaseRequest);
	}

}
