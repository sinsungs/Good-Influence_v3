package com.influence.domain.orders.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.influence.domain.meet.dto.MeetDTO;
import com.influence.domain.meet.entity.Meet;
import com.influence.domain.meet.mapper.MeetMapper;
import com.influence.domain.meet.repository.MeetRepository;
import com.influence.domain.meetuser.entity.MeetUser;
import com.influence.domain.orders.dto.OrdersDTO;
import com.influence.domain.orders.entity.Orders;
import com.influence.domain.orders.mapper.OrdersMapper;
import com.influence.domain.orders.repository.OrdersRepository;
import com.influence.domain.user.entity.User;
import com.influence.domain.user.repository.UserRepository;
import com.influence.global.exception.CustomException;
import com.influence.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrdersService {
	
	private final UserRepository userRepository;
	private final OrdersRepository ordersRepository;
	private final OrdersMapper ordersMapper;
	
	
    // 소셜 모임 전체 조회
    public List<OrdersDTO> listMeet(Authentication authentication) {
		
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        
        List<Orders> Order = ordersRepository.findByUser(user);
        
        List<OrdersDTO> dtoList = new ArrayList<>();
        
	     for (Orders orders : Order) {
		      dtoList.add(ordersMapper.entityToDTO(orders));
		 }
	     return dtoList;
    }
    
  
}
