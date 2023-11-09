package com.influence.domain.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.influence.domain.kakao.KakaoProfile;
import com.influence.domain.oauth.OAuthToken;
import com.influence.domain.user.RoleType;
import com.influence.domain.user.dto.UserDTO;
import com.influence.domain.user.entity.User;
import com.influence.domain.user.mapper.UserMapper;
import com.influence.domain.user.repository.UserRepository;
import com.influence.global.exception.CustomException;
import com.influence.global.exception.ErrorCode;
import com.influence.global.utils.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;
	private final UserMapper userMapper;
	
	
	// jwt 로그인 
	// 로컬 로그인 예외 똑같이 들어가야함
	@Value("${jwt.secret}")
	private String secretKey;
	// 시크릿키를 이렇게 등록하지 않으면 git에서 전세계 사람들이 다 볼 수 있음
	
	private Long expiredMs = 1000 * 60 * 60l;
	// 1시간으로 만들어주기 , 밀리세컨드 , long타입이라 l 붙이기
	
	@Transactional(readOnly = true)
	public String jwtLogin(String email, String password) {
		
		// email 없음
		User selectedUser = userRepository.findByEmail(email).orElse(null);
//		.orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOTFOUND, "존재하지 않는 아이디 입니다."));
	
			if(selectedUser == null) {
				return "존재하지 않는 아이디 입니다.";
			}
//				
						
		// password 틀림 
		if (!encoder.matches( password, selectedUser.getPassword())) {
//			throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력 했습니다.");
			return "패스워드를 잘못 입력 했습니다.";
		} 
		
		return JwtUtil.createJwt(email, secretKey, expiredMs);
	}
	
	// 로컬 로그인 
	@Transactional(readOnly = true) 
	public String Login(User user) {
		
		// email 없음
		User selectedUser = userRepository.findByEmail(user.getEmail())
				.orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOTFOUND, "존재하지 않는 아이디 입니다."));
				
		// password 틀림 
		if (!encoder.matches( user.getPassword(), selectedUser.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력 했습니다.");
		} 
		
		return "성공";
//		return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
	
	}
	
	
	
	// 로컬 회원가입 
	@Transactional
	public String Join(UserDTO dto) {

		// email 공백 check 
		if(dto.getEmail() == "") {
			return "이메일을 입력해 주세요.";
		}
		
		// password 공백 check
		if(dto.getPassword() == "") {
			return "비밀번호를 입력해 주세요.";
		} else if (dto.getPassword().length() < 6) {
		    return "비밀번혼는 6자리 이상이어야 합니다.";
		}
		
		// username 공백 및 글자 수 check
		if (dto.getUsername() == "") {
		    return "닉네임을 입력해 주세요.";
		} else if (dto.getUsername().length() == 1) {
		    return "닉네임은 최소 2글자 이상이어야 합니다.";
		}
		
		// email 중복 check 
		User userID = userRepository.findByEmail(dto.getEmail()).orElse(null);
		
		if(userID != null) {
			return "이미 사용중인 아이디입니다.";
		}
		
		// email 중복 check Exception 
//		userRepository.findByEmail(dto.getEmail())
//		.ifPresent(user -> {
//			throw new AppException(ErrorCode.EMAIL_DUPLICATED, "사용중인 이메일 입니다.");
//	});
		
					
		// username 중복 check
		User userName = userRepository.findByUsername(dto.getUsername()).orElse(null);
		
		if(userName != null) {
			return "닉네임 '" + userName.getUsername() + "' 사용중입니다.";
		}
				
		// username 중복 check Exception
//		userRepository.findByUsername(dto.getUsername())
//			.ifPresent(user -> {
////				throw new RuntimeException("사용중인 닉네임 입니다.");
//				throw new AppException(ErrorCode.USERNAME_DUPLICATED, "사용중인 닉네임 입니다.");
//		});
				
		
		// Mapper
		User user = userMapper.ToEntity(dto);
		
		// Repository
        userRepository.save(user);
        
        String result = "회원가입에 성공하셨습니다.";
        
        return result;
        
	}

	
	// 카카오 로그인
	@Transactional
	public User KakaoTest(KakaoProfile dto) {
		
		// Mapper
		User user = userMapper.ToEntity(dto);
		
		// Repository
		return userRepository.save(user);
		
	}
	
	// 유저 찾기 
	@Transactional
	public User findUser(String email) {
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOTFOUND, "존재하지 않는 아이디 입니다."));
		
		return user;
	}

	
	public OAuthToken getKakaoToken(String code) {
		
		log.info(code);
		
		// 토큰을 받아오기위해 POST 요청 
		// POST 방식으로 key=value 데이터 요청 
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "b58919f7c93ec635d5c0b3697d4aac6b");
		params.add("redirect_uri", "https://goodinfluence.shop/login/oauth2/callback/kakao");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = 
				new HttpEntity<>(params,headers);
		
		// Http 요청하기 - post방식으로 - 그리고 response 응답받기
		ResponseEntity<String> response = rt.exchange(
			"https://kauth.kakao.com/oauth/token",
			HttpMethod.POST,
			kakaoTokenRequest,
			String.class
		);
		
		// 카카오 Response 값 매핑 ( json ) 
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return oauthToken;
	}
	
	
	
	public KakaoProfile getKakaoProfile(OAuthToken oauthToken) {
		// 토큰을 이용한 POST 요청 사용자 정보 조회 
		// POST 방식으로 key=value 데이터 요청 
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		// Http 요청하기 - post방식으로 - 그리고 response 응답받기
		ResponseEntity<String> response2 = rt2.exchange(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.POST,
			kakaoProfileRequest2,
			String.class
		);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(kakaoProfile);
//		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        log.info("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        log.info("카카오 유저네임 : " + kakaoProfile.getKakao_account().getEmail()+"_"+ kakaoProfile.getId());
		
        return kakaoProfile;
	}
	





	

}
