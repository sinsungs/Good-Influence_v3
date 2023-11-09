package com.influence.domain.orders.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.influence.domain.kakao.KakaoProfile;
import com.influence.domain.meet.dto.MeetDTO;
import com.influence.domain.meet.entity.Meet;
import com.influence.domain.orders.dto.OrdersDTO;
import com.influence.domain.orders.entity.Orders;
import com.influence.domain.user.RoleType;
import com.influence.domain.user.dto.UserDTO;
import com.influence.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OrdersMapper {
	
	private final BCryptPasswordEncoder encoder;

    //주문 내역 조회 
	public OrdersDTO entityToDTO(Orders orders) {
		
		return OrdersDTO.builder()
				.orderid(orders.getOrderid())
				.price(orders.getPrice())
				.orderDate(orders.getOrderDate())
				.region(orders.getMeet().getRegion())
				.title(orders.getMeet().getTitle())
				.content(orders.getMeet().getContent())
				.writer(orders.getMeet().getUser().getUsername())
				
				.build();
		
	}
	
}