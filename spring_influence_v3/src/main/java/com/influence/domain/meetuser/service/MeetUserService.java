package com.influence.domain.meetuser.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.influence.domain.meet.dto.MeetDTO;
import com.influence.domain.meet.entity.Meet;
import com.influence.domain.meet.repository.MeetRepository;
import com.influence.domain.meetuser.dto.MeetUserDTO;
import com.influence.domain.meetuser.entity.MeetUser;
import com.influence.domain.meetuser.mapper.MeetUserMapper;
import com.influence.domain.meetuser.repository.MeetUserRepository;
import com.influence.domain.orders.entity.Orders;
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
public class MeetUserService {
	
	private final UserRepository userRepository;
	private final MeetRepository meetRepository;
	private final MeetUserRepository meetUserRepository;
	private final OrdersRepository ordersRepository;
	private final MeetUserMapper meetUserMapper;

	
	// 소셜 모임 참가 
	@Transactional
    public String registerMeet(Long id, MeetUserDTO dto, Authentication authentication) {
		
    	dto.setEmail(authentication.getName());
    	dto.setMeetid(id);
    	dto.setPrice(dto.getPrice());
    	
        User user = userRepository.findByEmail(dto.getEmail()).orElse(null);
//		.orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOTFOUND, "존재하지 않는 아이디 입니다."));
        Meet meet = meetRepository.findById(dto.getMeetid()).orElse(null);
//		.orElseThrow(() -> new CustomException(ErrorCode.MEET_NOTFOUND, "존재하지 않는 모임 입니다."));
        
        if (user == null || meet == null) {
            return "사용자 또는 모임을 찾을 수 없습니다.";
        }
        
        if(user.getAmount() < 10000) {
        	return "보유금이 부족합니다";
        }
        
        MeetUser meetUser = meetUserRepository.findByUserAndMeet(user, meet);
        
        if (meetUser != null) {
            return "이미 참가중입니다.";
        }
        
        // 신청 마감 처리 
        if (meet.getCurrentPlayers() >= meet.getMaxPlayers()) {
            return "이미 모임이 가득 찼습니다.";
        }
        
        MeetUser meetuser = MeetUser.builder()
        		.user(user)
        		.meet(meet)
        		.build();
        
        meetUserRepository.save(meetuser);
        		

        // 주문내역에 추가하고 보유금 차감해야함 
        Orders orders = Orders.builder()
        		.price(dto.getPrice())
        		.user(user)
        		.meet(meet)
        		.build();
        
        ordersRepository.save(orders);
        
    	
        user.setAmount(user.getAmount() - dto.getPrice());
        userRepository.save(user);
        
      
        // 로직 추가 
        // currentPlayers 값 증가
        meet.setCurrentPlayers(meet.getCurrentPlayers() + 1);

        // currentPlayers와 maxPlayers 비교하여 result 값 설정
        if (meet.getCurrentPlayers() >= meet.getMaxPlayers()) {
            meet.setResult("마감");
        } else {
            meet.setResult("신청가능");
        }

        meetRepository.save(meet); // 업데이트된 Meet 객체 저장

        
        return "모임을 참여했습니다.";
        
    }
	
	
		// 소셜 모임 참가 취소 
		@Transactional
	    public String deleteMeeting(Long id, MeetUserDTO dto,  Authentication authentication) {
			
	    	dto.setEmail(authentication.getName());
	    	dto.setMeetid(id);
	    	dto.setPrice(10000);
	    	
	        User user = userRepository.findByEmail(dto.getEmail()).orElse(null);
//			.orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOTFOUND, "존재하지 않는 아이디 입니다."));
	        Meet meet = meetRepository.findById(dto.getMeetid()).orElse(null);
//			.orElseThrow(() -> new CustomException(ErrorCode.MEET_NOTFOUND, "존재하지 않는 모임 입니다."));
	        
	        if (user == null || meet == null) {
	            return "사용자 또는 모임을 찾을 수 없습니다.";
	        }
	        
//	        if (!meetUserRepository.existsByUserAndMeet(user, meet)) {
//	            return "참가하고 있지 않은 모임입니다.";
//	        }
	        
	        MeetUser meetUser = meetUserRepository.findByUserAndMeet(user, meet);
//			.orElseThrow(() -> new CustomException(ErrorCode.MEETUSER_NOTFOUND, "해당 사용자는 이 모임에 참가하지 않았습니다."));

	        if (meetUser == null) {
	            return "해당 사용자는 이 모임에 참가하지 않았습니다.";
	        }
	        
	        meetUserRepository.delete(meetUser);
	        
	        // 주문 내역 삭제
	        ordersRepository.deleteByUserAndMeet(user, meet);
	        
	        user.setAmount(user.getAmount() + dto.getPrice());
	        userRepository.save(user);
	        
//	        // 로직 추가 
//	        // currentPlayers 값 감소
	        meet.setCurrentPlayers(meet.getCurrentPlayers() - 1);

	        meetRepository.save(meet); // 업데이트된 Meet 객체 저장
	        
	
	        return "모임을 취소했습니다.";
	    }


		public List<MeetDTO> listMeeting(Authentication authentication) {
			
	        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
//			.orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOTFOUND, "존재하지 않는 아이디 입니다."));
	        
	        List<MeetUser> meetUsers = meetUserRepository.findByUser(user);
	        
	        List<MeetDTO> dtoList = new ArrayList<>();
	        
		     for (MeetUser meetUser : meetUsers) {
			      dtoList.add(meetUserMapper.entityToDTO(meetUser));
			 }
	        
			return dtoList;
		}
	
    
    
}
