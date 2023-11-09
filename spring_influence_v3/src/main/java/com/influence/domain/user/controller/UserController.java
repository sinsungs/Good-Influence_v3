package com.influence.domain.user.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.influence.domain.kakao.KakaoProfile;
import com.influence.domain.oauth.OAuthToken;
import com.influence.domain.user.dto.LoginRequest;
import com.influence.domain.user.dto.UserDTO;
import com.influence.domain.user.entity.User;
import com.influence.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	// 회원 가입
	@PostMapping("/user/join")
	public ResponseEntity<String> joinUser(@Valid @RequestBody UserDTO dto, BindingResult br) {
		
		if(br.hasErrors()) {
			// DTO에서 유효성 검사를 통과하지 못한 에러들 매핑 
	        for (FieldError error : br.getFieldErrors()) {
	        		// 에러의 message를 반환 
	                return ResponseEntity.ok().body(error.getDefaultMessage());
	        }
		}
		
		String result = userService.Join(dto);
		
		return ResponseEntity.ok(result);
	}
	
	
	// JWT 로그인 
	@PostMapping("/user/jwtlogin")
	public ResponseEntity<String> jwtLogin(@Valid @RequestBody LoginRequest dto, BindingResult br) {
		
		if(br.hasErrors()) { 
	        for (FieldError error : br.getFieldErrors()) {
	                return ResponseEntity.ok().body(error.getDefaultMessage());
	        }
		}

		return ResponseEntity.ok().body(userService.jwtLogin(dto.getEmail(), dto.getPassword()));
	}
	
	
	// 일반 로그인 
	@PostMapping("/user/login")
	public ResponseEntity<String> Login(@RequestBody User user) {
		
		String result = userService.Login(user);
		
		return ResponseEntity.ok(result);
	}
	
	// 프로필 정보 
	@PostMapping("/user/profile")
	public ResponseEntity<User> Profile(Authentication authentication){
		
    	User user = userService.findUser(authentication.getName());
        
        return ResponseEntity.ok(user);
	}
	
	// 마이페이지 
	@PostMapping("/user/mypage")
	public ResponseEntity<?> Mypage(Authentication authentication){
    	
    	User user = userService.findUser(authentication.getName());
        
        return ResponseEntity.ok(user);
	}
	
	// 카카오 로그인 
	@GetMapping("/auth/kakao/callback")
    public String processKakaoLogin(String code) {
		
        // 카카오 토큰 요청 및 처리
        OAuthToken oauthToken = userService.getKakaoToken(code);

        // 카카오 프로필 정보 요청 및 처리
        KakaoProfile kakaoProfile = userService.getKakaoProfile(oauthToken);

        // 유저 조회
        User originUser = userService.findUser(kakaoProfile.getKakao_account().getEmail());
        
        // 유저가 비회원이면 
        if(originUser.getEmail()==null) {
            // 회원가입 후 로그인 처리
    		User user = userService.KakaoTest(kakaoProfile);
    		
            String jwt = userService.jwtLogin(user.getEmail(), "examplePassword");
            
    		return jwt ;
        }
        
        // 유저가 기존 회원이면 로그인 처리 
        String jwt = userService.jwtLogin(originUser.getEmail(), "examplePassword");
        
		return jwt ;
        
	}
	
}
