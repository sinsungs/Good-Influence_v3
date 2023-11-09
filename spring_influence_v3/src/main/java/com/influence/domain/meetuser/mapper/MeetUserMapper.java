package com.influence.domain.meetuser.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.influence.domain.kakao.KakaoProfile;
import com.influence.domain.meet.dto.MeetDTO;
import com.influence.domain.meet.entity.Meet;
import com.influence.domain.meetuser.dto.MeetUserDTO;
import com.influence.domain.meetuser.entity.MeetUser;
import com.influence.domain.user.RoleType;
import com.influence.domain.user.dto.UserDTO;
import com.influence.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MeetUserMapper {
	  
    // 참가중인 소셜 모임 전체 조회 
	public MeetDTO entityToDTO(MeetUser meetuser) {
		
		return MeetDTO.builder()
				.meetid(meetuser.getMeet().getMeetid())
				.title(meetuser.getMeet().getTitle())
				.content(meetuser.getMeet().getContent())
	            .maxplayers(meetuser.getMeet().getMaxPlayers())
	            .currentPlayers(meetuser.getMeet().getCurrentPlayers())
	            .region(meetuser.getMeet().getRegion())
	            .result(meetuser.getMeet().getResult())
	            .meettime(meetuser.getMeet().getMeettime())
	            .writer(meetuser.getMeet().getUser().getUsername())
				.build();
		
	}
   

}