package com.influence.domain.post.mapper;

import org.springframework.stereotype.Component;

import com.influence.domain.post.dto.PostReviewDTO;
import com.influence.domain.post.entity.PostReview;
import com.influence.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PostReviewMapper {
	
    
	public PostReview dtoToEntity(PostReviewDTO dto, String uploadedFileName, User user) {
		  
		return PostReview.builder()
	            .title(dto.getTitle())
	            .content(dto.getContent())
	            .imageUrl(uploadedFileName)
	            .user(user)
	            .build();
        
	}
	
	
	public PostReviewDTO entityToDTO(PostReview postReview) {
		
		return PostReviewDTO.builder()
				.prno(postReview.getPrno())
				.title(postReview.getTitle())
				.content(postReview.getContent())
	            .imageurl(postReview.getImageUrl())
				.writer(postReview.getUser().getUsername())
            
		   .build();
		
	}

    
    
    

}