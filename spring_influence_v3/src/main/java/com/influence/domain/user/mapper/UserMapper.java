package com.influence.domain.user.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.influence.domain.kakao.KakaoProfile;
import com.influence.domain.user.RoleType;
import com.influence.domain.user.dto.UserDTO;
import com.influence.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserMapper {
	
	private final BCryptPasswordEncoder encoder;
	

	// 회원가입
    public User ToEntity(UserDTO dto) {

    	return User.builder()
    			.username(dto.getUsername())
    			.password(encoder.encode(dto.getPassword()))
    			.email(dto.getEmail())
    			.role(RoleType.USER)
    			.oauth("local")
    			.amount(100000)
    			.experience(0)
    			.sns("유저")
    			.imageUrl("https://sinsung-s3.s3.ap-northeast-2.amazonaws.com/%EC%B9%B4%EC%B9%B4%EC%98%A4%ED%86%A1%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84.jpeg")
    			.build();
    }
    
    // 카카오 회원가입 
    public User ToEntity(KakaoProfile dto) {

    	return User.builder()
				.username(dto.getKakao_account().getEmail()+"_"+ dto.getId())
				.email(dto.getKakao_account().getEmail())
				.password(encoder.encode("examplePassword"))
				.role(RoleType.USER)
				.oauth("kakao")
				.amount(100000)
				.experience(0)
				.sns("유저")
				.imageUrl("https://sinsung-s3.s3.ap-northeast-2.amazonaws.com/%EC%B9%B4%EC%B9%B4%EC%98%A4%ED%86%A1%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84.jpeg")
				.build();
		
    }
    
    
    

}