package com.process.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.process.config.EmailConfiguration;
import com.process.model.User;
import com.process.service.EmailService;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
	
	private static final long serialVersionUID = -4635397942617717945L;
	
	private final EmailConfiguration emailConfiguration;
	
	@Autowired
	public EmailServiceImpl(EmailConfiguration emailConfiguration) {
		this.emailConfiguration = emailConfiguration;
	}
	
	@Override
	public void sendRegistrationMail(User user, String to) {
		
		System.out.println("Send Registration Mail => " + user.getEmail());
		
		StringWriter writer = new StringWriter();
		
		File registrationEmail;
		try {
			registrationEmail = new ClassPathResource("/html/registrationEmail.html").getFile();
			FileInputStream inputStream = new FileInputStream(registrationEmail) ;
	        IOUtils.copy(inputStream, writer, "utf-8");
		} catch (IOException e) {
			writer.write("Dovrsite registraciju klikom na aktivacioni link: <a href=\"registrationLinkId\">Aktiviranje naloga</a>");
		}
        
        String body = writer.toString();
        String registrationLink = "http://localhost:8100/users/confirmRegistration?registrationLink=" + user.getRegistrationLink() + "&userId=" + user.getId();
        
        body = body.replaceAll("registrationLinkId", registrationLink);
		
        String subject = "Registracija - Reverse auction app";
        
		this.sendMail(to, body, subject);
	}

	@Override
	public void sendMail(String to, String body, String subject) {
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) emailConfiguration.getGoogleJavaMailSender();
		MimeMessage message = emailConfiguration.getGoogleJavaMailSender().createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(mailSender.getUsername());
			helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(body, true);
	        mailSender.send(message);
		} catch (Exception e) {
	        try {
			helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setFrom(mailSender.getUsername());
	        helper.setSubject(subject);
			helper.setText(body, true);
			mailSender.send(message);
			} catch (MessagingException me) {
				me.printStackTrace();
			}
		} 
	}
}
