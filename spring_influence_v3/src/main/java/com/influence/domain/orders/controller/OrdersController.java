package com.influence.domain.orders.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.influence.domain.meet.dto.MeetDTO;
import com.influence.domain.meet.entity.Meet;
import com.influence.domain.meet.service.MeetService;
import com.influence.domain.orders.dto.OrdersDTO;
import com.influence.domain.orders.service.OrdersService;
import com.influence.global.utils.S3UploadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;
	

    @GetMapping("/list")
    public ResponseEntity<List<OrdersDTO>> listMeet(Authentication authentication) {
    	
        List<OrdersDTO> list = ordersService.listMeet(authentication);
        
		if(!list.isEmpty()) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
    
}
