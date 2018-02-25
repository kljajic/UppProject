package com.process.service;

import java.util.List;

import com.process.model.Offer;
import com.process.model.PurchaseRequest;

public interface OfferService {

	Offer createOffer(Boolean cancelOffer, Double price, String dueDate, PurchaseRequest purchaseRequest, String username, String email);
	Offer getOffer(Long offerId);
	void startAuctionProcess();
	List<Offer> formOffersRangListForPurchaseRequest(Long purchaseRequestId);
	Integer getNumberOfOffersForPurchaseRequest(Long purchaseRequestId);
	Offer acceptOfferAndSendMailToCompany(Long offerId);
	
}
