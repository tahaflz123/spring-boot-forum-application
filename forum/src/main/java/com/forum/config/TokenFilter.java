package com.forum.config;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import com.forum.entity.User;
import com.forum.service.AuthService;


@CrossOrigin
public class TokenFilter extends OncePerRequestFilter{

	@Autowired
	private AuthService authService;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		System.err.println(authorization);
		if(authorization != null) {
			String token = authorization.substring(7);
			User user = (User) authService.getUserDetails(token); 
		    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
		// TODO Auto-generated method stub
		
		filterChain.doFilter(request, response);
		
	}

}
