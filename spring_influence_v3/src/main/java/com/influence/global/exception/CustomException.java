package com.influence.global.exception;

import com.influence.global.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
	
	// 에러코드에 상태와 메세지가 모두 담겨있다.
	private ErrorCode errorCode;

	private String message;
	
	
}