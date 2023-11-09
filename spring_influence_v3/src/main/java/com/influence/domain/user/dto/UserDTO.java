package com.influence.domain.user.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private Long id;
	
	@NotBlank(message = "이메일을 입력해 주세요")
	private String email;
	
	@NotBlank(message = "비밀번호를 입력해 주세요")
	private String password;
		
	@NotBlank(message = "닉네임을 입력해 주세요")
	private String username;
	
	private LocalDateTime regdate;
	
	private String sns;
		
}
