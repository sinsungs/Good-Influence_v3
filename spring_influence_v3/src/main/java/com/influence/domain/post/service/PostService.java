package com.influence.domain.post.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.influence.domain.post.dto.PostRequestDTO;
import com.influence.domain.post.entity.Post;
import com.influence.domain.post.repository.PostRepository;
import com.influence.domain.user.entity.User;
import com.influence.domain.user.repository.UserRepository;
import com.influence.global.exception.CustomException;
import com.influence.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;

    

		public Long createPost(PostRequestDTO dto, String uploadedFileName, Authentication authentication) {
		
		dto.setWriter(authentication.getName());
	
		User user = userRepository.findByEmail(dto.getWriter())
   			 .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "로그인 정보를 찾지 못했습니다."));
		
	    Post post = Post.builder()
	            .title(dto.getTitle())
	            .content(dto.getContent())
	            .imageUrl(uploadedFileName)
	            .user(user)
	            .build();
	    
	    postRepository.save(post);
	    
	    Long postId = post.getPno();
	    
	    return postId;
	}
    
}