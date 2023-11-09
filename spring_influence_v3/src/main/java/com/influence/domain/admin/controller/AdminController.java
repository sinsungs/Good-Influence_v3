package com.influence.domain.admin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.influence.domain.admin.service.AdminService;
import com.influence.domain.user.dto.UserDTO;
import com.influence.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminService adminService;
	private final UserService userService;


	// 모든 유저 조회
    @GetMapping("/user")
    public List<UserDTO> listUsers() {
    	
        return adminService.getAllUsers();
        
    }
    
    
	// 유저 회원 탈퇴 
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
        	
            adminService.deleteUserById(userId);
            
            return ResponseEntity.ok("삭제를 성공했습니다.");
            
        } catch (Exception e) {
        	
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting user: " + e.getMessage());
        }
    }
    
    
	// 유저 인플루언서 등록 
    @PatchMapping("/verify/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId) {
        try {
        	
            adminService.updateUserById(userId);
            
            return ResponseEntity.ok("업데이트 성공했습니다.");
            
        } catch (Exception e) {
        	
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting user: " + e.getMessage());
        }
    }
    
}
