package com.influence.domain.meet.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.influence.domain.kakao.KakaoProfile;
import com.influence.domain.meet.dto.MeetDTO;
import com.influence.domain.meet.entity.Meet;
import com.influence.domain.user.RoleType;
import com.influence.domain.user.dto.UserDTO;
import com.influence.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MeetMapper {
	
	private final BCryptPasswordEncoder encoder;
	

	// 소셜 모임 생성 매퍼 
    public Meet dtoToEntity(MeetDTO dto, User user) {

    	return Meet.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .maxPlayers(dto.getMaxplayers())
                .currentPlayers(dto.getCurrentPlayers())
                .region(dto.getRegion())
                .result(dto.getResult())
                .meettime(dto.getMeettime())
                .result("신청가능")
                .user(user)
                .build();
    }
    
    
    //소셜 모임 전체 조회 
	public MeetDTO entityToDTO(Meet meet) {
		
		return MeetDTO.builder()
				.meetid(meet.getMeetid())
	            .title(meet.getTitle())
                .content(meet.getContent())
                .maxplayers(meet.getMaxPlayers())
                .currentPlayers(meet.getCurrentPlayers())
                .region(meet.getRegion())
                .result(meet.getResult())
                .meettime(meet.getMeettime())
                .writer(meet.getUser().getUsername())
				.build();
		
	}
	
	
	// 소셜 모임 수정 매퍼 
    public Meet putToEntity(MeetDTO dto) {

    	return Meet.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .maxPlayers(dto.getMaxplayers())
//                .currentPlayers(dto.getCurrentPlayers())
                .region(dto.getRegion())
//                .result(dto.getResult())
                .meettime(dto.getMeettime())
//                .result("신청가능")
//                .user(user)
                .build();
    }
   

}