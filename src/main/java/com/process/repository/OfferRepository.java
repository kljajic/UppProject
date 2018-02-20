package com.process.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.process.model.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

	List<Offer> findOffersByPurchaseRequestId(Long purchaseRequestId);//missing order by price and due date
	
	Integer countOffersByPurchaseRequestIdAndCancelOffer(Long purchaseRequestId, Boolean cancelOffer);
	
}
