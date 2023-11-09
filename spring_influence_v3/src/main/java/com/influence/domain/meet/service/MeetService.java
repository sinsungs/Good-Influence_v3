package com.influence.domain.meet.service;

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
import com.influence.domain.user.entity.User;
import com.influence.domain.user.repository.UserRepository;
import com.influence.global.exception.CustomException;
import com.influence.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MeetService {
	
	private final UserRepository userRepository;
	private final MeetRepository meetRepository;
	private final MeetMapper meetMapper;
	
	
    // 소셜 모임 생성
	@Transactional
    public Boolean createMeet(MeetDTO dto, Authentication authentication) {
    	
    	dto.setWriter(authentication.getName());
		
    	User user = userRepository.findByEmail(dto.getWriter())
    			 .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "로그인 정보를 찾지 못했습니다."));
    	
    	Meet meet = meetMapper.dtoToEntity(dto, user);
    	
    	meetRepository.save(meet);

        return true;
    }

	
    // 소셜 모임 전체 조회
    public List<MeetDTO> listMeet() {
    	
        List<Meet> meets = meetRepository.findAll();
        
        List<MeetDTO> dtoList = new ArrayList<>();
        
	     for (Meet meet : meets) {
	      dtoList.add(meetMapper.entityToDTO(meet));
	     }
	  
	     return dtoList;
    }
    
    
    // 소셜 모임 정보 수정 
	@Transactional
    public Boolean updateMeet(Long meetId, MeetDTO dto) {
		
        Meet meet = meetRepository.findById(meetId)
        		.orElseThrow(() -> new CustomException(ErrorCode.MEET_NOTFOUND, "존재하지 않는 모임 입니다."));

        	
            meet = meetMapper.putToEntity(dto);

            meetRepository.save(meet);
            
            return true;
    }
	

	// 소셜 모임 삭제 
	@Transactional
    public boolean deleteMeet(Long meetId) {
    	
        Optional<Meet> optionalMeet = meetRepository.findById(meetId);
           
        if (optionalMeet.isPresent()) {
            Meet meet = optionalMeet.get();
            
            // Meet에 참여한 사용자들 가져오기
            List<MeetUser> meetUsers = meet.getMeetUsers();
            
            // 보유금을 돌려주는 로직 구현
            for (MeetUser meetUser : meetUsers) {
                User user = meetUser.getUser();
                
    	        user.setAmount(user.getAmount() + 10000);
    	        userRepository.save(user);
            }
            
            meetRepository.delete(meet);
            return true;
        }
        
        return false; 
    }
    
  
}
