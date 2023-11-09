package com.influence.domain.comment.mapper;

import org.springframework.stereotype.Component;

import com.influence.domain.comment.dto.CommentDTO;
import com.influence.domain.comment.entity.Comment;
import com.influence.domain.post.dto.PostReviewDTO;
import com.influence.domain.post.entity.PostReview;
import com.influence.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CommentMapper {
	
    
	public Comment dtoToEntity(CommentDTO dto, User user, PostReview post) {
		  
		return Comment.builder()
	            .content(dto.getContent())
	            .user(user)
	            .post(post)
	            .build();
        
	}

	
	public CommentDTO entityToDTO(Comment comment) {
		
		return CommentDTO.builder()
				.cno(comment.getCno())
				.content(comment.getContent())
				.writer(comment.getUser().getUsername())
            
		   .build();
		
	}

   
}
    