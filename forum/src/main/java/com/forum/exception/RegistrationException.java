package com.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistrationException extends RuntimeException{

	public RegistrationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
