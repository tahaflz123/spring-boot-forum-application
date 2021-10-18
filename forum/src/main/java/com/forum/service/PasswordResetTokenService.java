package com.forum.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.forum.entity.PasswordResetToken;
import com.forum.entity.User;
import com.forum.exception.AuthException;
import com.forum.exception.TokenException;
import com.forum.repository.PasswordResetTokenRepository;
import com.forum.repository.UserRepository;

@Service
public class PasswordResetTokenService {
	
	
	private static int expiryDate = 24 * 60 * 60;
	
	private UserRepository userRepository;
	private PasswordResetTokenRepository passwordResetTokenRepository;
	private JavaMailSender mailSender;
	
	@Autowired
	public PasswordResetTokenService(UserRepository userRepository,
			PasswordResetTokenRepository passwordResetTokenRepository,JavaMailSender mailSender) {
		this.userRepository = userRepository;
		this.mailSender = mailSender;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
	}





	public String forgotPassword(String email) {
		User user = this.userRepository.findByEmail(email);
		if(user != null) {
			try {
				PasswordResetToken passwordResetToken = new PasswordResetToken();
				passwordResetToken.setUser(user);
				passwordResetToken.setToken(UUID.randomUUID().toString());
				Date date = Date.from(Instant.now().plusSeconds(expiryDate));
				passwordResetToken.setExpiryDate(date);
				this.passwordResetTokenRepository.save(passwordResetToken);
				MimeMessage mimeMessage = this.mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,"utf-8");
				messageHelper.setTo(email);
				messageHelper.setSubject("-----------------");
				messageHelper.setFrom("--------");
				messageHelper.setText("----------");
				this.mailSender.send(mimeMessage);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return "E-mail sended...";
			
		}
		throw new AuthException();
	}
	
	public String resetPassword(String token) {
		PasswordResetToken dbToken = this.passwordResetTokenRepository.findByToken(token);
	
		if(dbToken == null) {
			throw new TokenException("This link is not working...");
		}else if(dbToken.getExpiryDate().before(new Date())) {
			this.passwordResetTokenRepository.delete(dbToken);
			throw new TokenException("This link is expired...");
		}
		return null;
	}
	
	public String resetPassword(String token,String password) {
		PasswordResetToken dbToken = this.passwordResetTokenRepository.findByToken(token);
		if(dbToken != null) {
			User user = dbToken.getUser();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(password));
			this.userRepository.save(user);
			this.passwordResetTokenRepository.delete(dbToken);
			return "Password changed...";
		}
		
		
		throw new TokenException("This link is not working...");
	}

}
