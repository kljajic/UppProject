package com.process.service;

import java.io.Serializable;

public interface EmailService extends Serializable {

	void sendRegistrationMail(String to);
	void sendMail(String to, String body);
	
}
