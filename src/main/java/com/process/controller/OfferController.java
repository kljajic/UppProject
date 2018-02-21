package com.process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.process.service.OfferService;

@RestController
@RequestMapping("/offers")
public class OfferController {

	private final OfferService offerService;
	
	@Autowired
	public OfferController(OfferService offerService) {
		this.offerService = offerService;
	}
	
	@GetMapping("/startAuction")
	public void startAuctionProcess() {
		this.offerService.startAuctionProcess();
	}
		
}
