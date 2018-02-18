package com.process.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.process.service.EmailService;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
	
	private static final long serialVersionUID = -4635397942617717945L;
	
	private final JavaMailSender javaMailSender;
	
	@Autowired
	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	@Override
	public void sendRegistrationMail(String to) {
		String body = "Registration text with registartion link <a>Click to confirm registration</a>";
		this.sendMail(to, body);
	}

	@Override
	public void sendMail(String to, String body) {
		
		new Thread(new Runnable() {
		
			@Override
			public void run() {
				try {
		            SimpleMailMessage email = new SimpleMailMessage();
		            email.setFrom("uppprojekat@gmail.com");
		            email.setTo(to);
		            email.setSubject("Registration email");
		            email.setText(body);
		            javaMailSender.send(email);
		        } catch (Exception ex) {
		            System.out.println("Email is not sent." + ex.getMessage());
		        }
			}
			
		}).start();
	
	}

}
