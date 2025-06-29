package com.nit.MailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailSenderConfig {
	
	@Autowired
	public JavaMailSender sender;
	
	public boolean processMail(String to, String[] cc, String[] bcc, String subject,String text,MultipartFile file) {
		
		  MimeMessage mimeMessage = sender.createMimeMessage();
		  try {
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage, file!=null);
			helper.setTo(to);
			if (cc != null && cc.length > 0) helper.setCc(cc);
            if (bcc != null && bcc.length > 0) helper.setBcc(bcc);
            helper.setSubject(subject);
            helper.setText(text);
            if (file != null && !file.isEmpty()) {
                helper.addAttachment(file.getOriginalFilename(), file);
            }
            sender.send(mimeMessage);
            return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean send(String to, String[] cc, String[] bcc, String subject,String text) {
		return processMail(to, cc, bcc, subject, text, null);
	}
	
	public boolean send(String to, String subject,String text) {
		System.out.print("--------------------->"+text);
		return processMail(to, null, null, subject, text, null);
	}

}
