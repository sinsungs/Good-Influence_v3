package com.influence.domain.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.influence.domain.kakao.KakaoApproveResponse;
import com.influence.domain.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;
	
   
	// 카카오 페이 결제 요청 
    @GetMapping("/ready")
    public String readyToKakaoPay(Authentication authentication) {

        return paymentService.kakaoPayReady(authentication);
    }
    
    
    // 카카오페이 결제 성공 
    @GetMapping("/success")
    public ResponseEntity<String> afterPayRequest(@RequestParam("pg_token") String pgToken) {

        KakaoApproveResponse kakaoApprove = paymentService.ApproveResponse(pgToken);
        
          return ResponseEntity.ok("보유금이 충전되었습니다.");
    }
    
    
    // 카카오페이 결제 취소 
    @GetMapping("/cancel")
    public String cancel() {
    	
    	return "취소했습니다";
    	
    }

    
    // 카카오페이 결제 실패 
    @GetMapping("/fail")
    public String fail() {
    	
    	return "실패했습니다";
    }
    
    
}