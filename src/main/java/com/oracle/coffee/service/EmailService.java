package com.oracle.coffee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	private final String fromMail = "team704gpt@gmail.com";
	
	public void sendEmail(String targetMail, String title, String contents) throws MessagingException {
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
	
		helper.setFrom(fromMail);
		helper.setTo(targetMail);
		helper.setSubject(title);
		helper.setText(contents);
		
		mailSender.send(msg);
	}
}
