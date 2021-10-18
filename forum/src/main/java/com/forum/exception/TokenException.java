package com.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TokenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	

}
