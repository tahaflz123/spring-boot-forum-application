package com.forum.service;

import java.time.LocalDate;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.forum.dto.AuthDTO;
import com.forum.dto.TokenDTO;
import com.forum.entity.User;
import com.forum.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {

	
	private UserRepository appUserRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private User user;
	

	String parseToken;
	@Autowired
	public AuthService(UserRepository appUserRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.appUserRepository = appUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public TokenDTO authenticate(AuthDTO credentials){
		User dbUser = this.appUserRepository.findByUsername(credentials.getUsername());
		
		if(dbUser == null) {
			throw new UsernameNotFoundException("Kullanıcı adı yada şifreniz hatalı!");
		}
		
		boolean matches = this.bCryptPasswordEncoder.matches(credentials.getPassword(), dbUser.getPassword());
		if(!matches) {
			throw new UsernameNotFoundException("Kullanıcı adı yada şifreniz hatalı!");
		}
		String token = Jwts.builder().setSubject(dbUser.getUsername())
		.setIssuedAt(new Date())
		.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
		.signWith(SignatureAlgorithm.HS256,"securesecuresecure")
		.compact();
		this.setParseToken(token);
		TokenDTO tokenClass = new TokenDTO(token);
		/*AppUserVM userVM = new AppUserVM(dbUser);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setToken(token);
		authResponse.setAppUserVM(userVM);
		*/
		
		return tokenClass;
	}

	@Transactional
	public UserDetails getUserDetails(String token) {
        JwtParser parser= Jwts.parser().setSigningKey("securesecuresecure");
        parser.parse(token);
        Claims claims = parser.parseClaimsJws(token).getBody();
		String username = claims.getSubject();

		User user = this.appUserRepository.findByUsername(username);
		this.setUser(user);
		return user;
	}
	
	

	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getParseToken() {
		return parseToken;
	}

	public void setParseToken(String parseToken) {
		this.parseToken = parseToken;
	}
	
}
