package com.process.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.ProcessEngine;
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
	
	private final ProcessEngine processEngine;
	
	@Autowired
	public EmailServiceImpl(EmailConfiguration emailConfiguration, ProcessEngine processEngine) {
		this.emailConfiguration = emailConfiguration;
		this.processEngine = processEngine;
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
	public void sendSmallNumberOfCompanies(String initiator) {
		
		String to = processEngine.getIdentityService().getUserInfo(initiator, "email");
		
		System.out.println("Send Small Number Of Companies Mail => " + to);
		
		StringWriter writer = new StringWriter();
		
		File genericLinkEmail;
		try {
			genericLinkEmail = new ClassPathResource("/html/genericLinkEmail.html").getFile();
			FileInputStream inputStream = new FileInputStream(genericLinkEmail) ;
	        IOUtils.copy(inputStream, writer, "utf-8");
		} catch (IOException e) {
			writer.write("Postovani, za Vasu aukciju postoji suvise mali broj kompanija koje zadovoljavaju Vase kriterijume. Ukoliko zelite da svejedno nastavite kliknite na sledeci link"
					+ ": <a href=\"linkHref\">Potvrda aukcije</a>");
		}
        
        String body = writer.toString();
        //String registrationLink = "http://localhost:8100/users/confirmRegistration?registrationLink=" + user.getRegistrationLink() + "&userId=" + user.getId();
        
		body = body.replaceAll("<br class=\"information\"/>", "za Vasu aukciju postoji suvise mali broj kompanija koje zadovoljavaju Vase kriterijume. Ukoliko zelite da svejedno nastavite sa aukcijom pratite link za potvrdu.");
        body = body.replaceAll("<br class=\"linkName\"/>", "Potvrda aukcije");
		body = body.replaceAll("linkHref", "http://localhost:8100/api/users/tasks");
				
        String subject = "Potvrda aukcije - Mali broj kompanija";
        
		this.sendMail(to, body, subject);
	}

	@Override
	public void sendEmailToCompany(String to) {
		System.out.println("Send Email To Companies Mail => " + to);
		
		StringWriter writer = new StringWriter();
		
		File genericLinkEmail;
		try {
			genericLinkEmail = new ClassPathResource("/html/genericLinkEmail.html").getFile();
			FileInputStream inputStream = new FileInputStream(genericLinkEmail) ;
	        IOUtils.copy(inputStream, writer, "utf-8");
		} catch (IOException e) {
			writer.write("Postovani, u skladu sa Vasim profilom pojavila se aukcija. Ukoliko zelite da pogledate aukciju klinkite na link"
					+ ": <a href=\"linkHref\">Ponuda</a>");
		}
        
        String body = writer.toString();
        //String registrationLink = "http://localhost:8100/users/confirmRegistration?registrationLink=" + user.getRegistrationLink() + "&userId=" + user.getId();
        
		body = body.replaceAll("<br class=\"information\"/>", "u skladu sa Vasim profilom pojavila se ponuda. Ukoliko zelite da pogledate ponudu klinkite na link.");
        body = body.replaceAll("<br class=\"linkName\"/>", "Ponuda aukcije");
		body = body.replaceAll("linkHref", "http://localhost:8100/api/users/tasks");
				
        String subject = "Nova aukcija";
        
		this.sendMail(to, body, subject);
		
	}

	@Override
	public void sendLowNumberOfOffersEmail(String initiator) {
		String to = processEngine.getIdentityService().getUserInfo(initiator, "email");
		
		System.out.println("Send Email To Companies Mail => " + to);
		
		StringWriter writer = new StringWriter();
		
		File genericLinkEmail;
		try {
			genericLinkEmail = new ClassPathResource("/html/genericLinkEmail.html").getFile();
			FileInputStream inputStream = new FileInputStream(genericLinkEmail) ;
	        IOUtils.copy(inputStream, writer, "utf-8");
		} catch (IOException e) {
			writer.write("Postovani, za Vasu aukciju postoji suvise mali broj ponuda. Ukoliko zelite da produzite rok za ponude ili da donesete odluku na osnovu postojecih ponuda pratite link"
					+ ": <a href=\"linkHref\">Potvrda aukcije</a>");
		}
        
        String body = writer.toString();
        //String registrationLink = "http://localhost:8100/users/confirmRegistration?registrationLink=" + user.getRegistrationLink() + "&userId=" + user.getId();
        
		body = body.replaceAll("<br class=\"information\"/>", "za Vasu aukciju postoji suvise mali broj ponuda. Ukoliko zelite da produzite rok za ponude ili da donesete odluku na osnovu postojecih ponuda pratite link.");
        body = body.replaceAll("<br class=\"linkName\"/>", "Potvrda aukcije");
		body = body.replaceAll("linkHref", "http://localhost:8100/users/reviewAuction");
				
        String subject = "Potvrda aukcije - Prihvacen zahtev - Odredjivanje termina pocetka";
        
		this.sendMail(to, body, subject);
		
	}

	@Override
	public void sentOfferAcceptanceEmail(String to) {
		System.out.println("Send Email To Companies Mail => " + to);
		
		StringWriter writer = new StringWriter();
		
		File genericLinkEmail;
		try {
			genericLinkEmail = new ClassPathResource("/html/genericLinkEmail.html").getFile();
			FileInputStream inputStream = new FileInputStream(genericLinkEmail) ;
	        IOUtils.copy(inputStream, writer, "utf-8");
		} catch (IOException e) {
			writer.write("Postovatni, Vasa ponuda za aukciju je prihvacena, za utvrdjivanje termina pocetka izvrsavanja zahteva ispratite link"
					+ ": <a href=\"linkHref\">Potvrda aukcije</a>");
		}
        
        String body = writer.toString();
        //String registrationLink = "http://localhost:8100/users/confirmRegistration?registrationLink=" + user.getRegistrationLink() + "&userId=" + user.getId();
        
		body = body.replaceAll("<br class=\"information\"/>", " Vasa ponuda za aukciju je prihvacena, za utvrdjivanje termina pocetka izvrsavanja zahteva ispratite link.");
        body = body.replaceAll("<br class=\"linkName\"/>", "Potvrda aukcije");
		body = body.replaceAll("linkHref", "http://localhost:8100/api/users/tasks");
				
        String subject = "Potvrda aukcije - Prihvacen zahtev - Odredjivanje termina pocetka";
        
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
