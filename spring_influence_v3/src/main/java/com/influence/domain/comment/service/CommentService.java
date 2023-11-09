package com.influence.domain.comment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.influence.domain.comment.dto.CommentDTO;
import com.influence.domain.comment.entity.Comment;
import com.influence.domain.comment.mapper.CommentMapper;
import com.influence.domain.comment.repository.CommentRepository;
import com.influence.domain.post.entity.PostReview;
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
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final PostReviewRepository postReviewRepository;
	private final CommentMapper commentMapper;

	
	// 댓글 생성 
	public boolean createComment(Long prno, CommentDTO dto, Authentication authentication) {
		
		System.out.println("content : "+dto.getContent());
		
		dto.setWriter(authentication.getName());
		
		User user = userRepository.findByEmail(dto.getWriter())
   			 .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "로그인 정보를 찾지 못했습니다."));
		
		PostReview post = postReviewRepository.findById(prno).orElse(null);
		
		Comment comment = commentMapper.dtoToEntity(dto, user, post);
		
		commentRepository.save(comment);
		
		return true;
	}
	
	
	// 게시글에 해당하는 댓글 전체 조회
	public List<CommentDTO> getList(Long prno) {
		

//	    List<Comment> comments = commentRepository.findAll();
		List<Comment> comments = commentRepository.findByPostPrno(prno);
  
	    List<CommentDTO> dtoList = new ArrayList<>();

	    for (Comment comment : comments) {
	        dtoList.add(commentMapper.entityToDTO(comment));
	    }

	    return dtoList;
	}

	
	// 댓글 삭제 
	public boolean deleteComment(Long cno) {
		
		commentRepository.deleteById(cno);
		
		return true;
	}

	
}
