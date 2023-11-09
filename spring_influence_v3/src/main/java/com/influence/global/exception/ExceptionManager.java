package com.influence.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 모든 Exception 발생 시 
@RestControllerAdvice
public class ExceptionManager {
	
	// custom 시 
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> CustomExceptionHandler(CustomException e) {
		
		return ResponseEntity.status(e.getErrorCode().getStatus())
				.body(e.getErrorCode().name() + " " + e.getMessage());
		
	}

	// runtime 시 
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
		
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(e.getMessage()+"어드바이스");
		
	}
	
}
