package com.process.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {
	
	private final Environment  environment;
	
	public EmailConfiguration(Environment environment) {
		this.environment = environment;
	}
	
	@Bean
	public JavaMailSender getGoogleJavaMailSender() {
	    
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
	    mailSenderImpl.setHost(environment.getProperty("spring.gmail.host"));
	    mailSenderImpl.setPort(Integer.valueOf(environment.getProperty("spring.gmail.port")).intValue());
	     
	    mailSenderImpl.setUsername(environment.getProperty("spring.gmail.username"));
	    mailSenderImpl.setPassword(environment.getProperty("spring.gmail.password"));
	    
	    Properties props = mailSenderImpl.getJavaMailProperties();
	    props.put("mail.transport.protocol", environment.getProperty("spring.gmail.transport.protocol"));
	    props.put("mail.smtp.auth", environment.getProperty("spring.gmail.smtp.auth"));
	    props.put("mail.smtp.starttls.enable", environment.getProperty("spring.gmail.starttls.enable"));
	    props.put("mail.debug", environment.getProperty("spring.gmail.debug"));
	     
	    JavaMailSender mailSender = mailSenderImpl;
	    
	    return mailSender;
	}
}
