package com.influence.domain.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {
	
	@NotBlank(message = "이메일을 입력해 주세요")
	private String email;
	
	@NotBlank(message = "비밀번호를 입력해 주세요")
	private String password;
	
}
