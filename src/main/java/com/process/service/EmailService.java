package com.process.service;

import java.io.Serializable;

import com.process.model.User;

public interface EmailService extends Serializable {

	void sendRegistrationMail(User user, String to);
	void sendSmallNumberOfCompanies(String to);
	void sendEmailToCompany(String to);
	void sendLowNumberOfOffersEmail(String to);
	void sentOfferAcceptanceEmail(String to);
	void sendMail(String to, String body, String subject);
	
}
