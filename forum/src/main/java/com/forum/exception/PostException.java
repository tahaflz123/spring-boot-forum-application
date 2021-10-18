package com.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostException extends RuntimeException{
	
	public PostException(String message) {
		super(message);
	}

	
}
