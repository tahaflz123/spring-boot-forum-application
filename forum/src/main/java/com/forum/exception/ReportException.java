package com.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReportException extends RuntimeException{

	public ReportException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
	
}
