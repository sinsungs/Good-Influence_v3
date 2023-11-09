package com.influence.domain.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.influence.domain.comment.dto.CommentDTO;
import com.influence.domain.comment.entity.Comment;
import com.influence.domain.meet.entity.Meet;
import com.influence.domain.meetuser.entity.MeetUser;
import com.influence.domain.post.dto.PostResponseDTO;
import com.influence.domain.post.dto.PostReviewDTO;
import com.influence.domain.post.entity.PostInfluencer;
import com.influence.domain.post.entity.PostReview;
import com.influence.domain.post.mapper.PostReviewMapper;
import com.influence.domain.post.repository.PostReviewRepository;
import com.influence.domain.user.entity.User;
import com.influence.domain.user.repository.UserRepository;
import com.influence.global.exception.CustomException;
import com.influence.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@RequiredArgsConstructor
@Log4j2
public class PostReviewService{
	
	private final PostReviewRepository postReviewRepository;
	private final UserRepository userRepository;
	private final PostReviewMapper postReviewMaper;
	
	
    // 소셜 모임 후기 게시글 작성 
	public Boolean createPost(PostReviewDTO dto, String uploadedFileName, Authentication authentication) {
		
		dto.setWriter(authentication.getName());
	
		User user = userRepository.findByEmail(dto.getWriter())
   			 .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "로그인 정보를 찾지 못했습니다."));
		
		PostReview postReview = postReviewMaper.dtoToEntity(dto, uploadedFileName, user);
	    
	    postReviewRepository.save(postReview);
	    
	    return true;
}


    // 소셜 모임 후기 게시글 전체 조회
//    public List<PostReviewDTO> getList() {
//   	
//       List<PostReview> PostReviews = postReviewRepository.findAll();
//       
//       List<PostReviewDTO> dtoList = new ArrayList<>();
//       
//       for (PostReview postReview : PostReviews) {
//           dtoList.add(postReviewMaper.entityToDTO(postReview));
//       }
//       
//       return dtoList;
//   }
	public Page<PostReviewDTO> getList(int page, int size) {
		
	    Pageable pageable = PageRequest.of(page, size);
	    
	    Page<PostReview> postReviews = postReviewRepository.findAll(pageable);
	    
	    return postReviews.map(postReviewMaper::entityToDTO);
	}
	

	// 소셜 모임 후기 게시글 삭제 
	public boolean deletePost(Long prno) {
		
		PostReview postReview = postReviewRepository.findById(prno).orElse(null);
		
		postReviewRepository.delete(postReview);
		
		return true;
	}


	public PostReviewDTO getPost(Long prno) {
		
		PostReview postReview = postReviewRepository.findById(prno).orElse(null);
		
		PostReviewDTO dto = postReviewMaper.entityToDTO(postReview);
		
		return dto;
	}


}
