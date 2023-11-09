package com.influence.domain.comment.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.influence.domain.comment.dto.CommentDTO;
import com.influence.domain.comment.service.CommentService;
import com.influence.domain.post.dto.PostReviewDTO;
import com.influence.domain.post.service.PostReviewService;
import com.influence.global.utils.S3UploadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
	
    private final CommentService commentService;
    private final S3UploadService s3UploadService;
    
    
    // 댓글 작성 
    @PostMapping("/register/{prno}")
    public  ResponseEntity<Boolean> createComment(@PathVariable Long prno, @RequestBody CommentDTO dto, Authentication authentication) throws IOException {

    	boolean result = commentService.createComment(prno, dto, authentication);  	 
        
	    if (result) {
	        return ResponseEntity.ok(true);
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
	    }
    }
    
    
    // 댓글 조회 
    @GetMapping("/list/{prno}")
    public ResponseEntity<List<CommentDTO>> listComment(@PathVariable Long prno, CommentDTO dto) {
    	
        List<CommentDTO> response = commentService.getList(prno);
        
        return ResponseEntity.ok(response);
        
    }
     
    
    // 댓글 삭제 
    @DeleteMapping("/delete/{cno}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long cno) {
    	
        boolean result = commentService.deleteComment(cno);
        
	    if (result) {
	        return ResponseEntity.ok(true);
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
	    }
}
    
//    @DeleteMapping("/delete/{cno}")
//    public ResponseEntity<Boolean> deletePost(@PathVariable Long cno) {
//    	
//        boolean result = commentService.deleteComment(cno);
//        
//	    if (result) {
//	        return ResponseEntity.ok(true);
//	    } else {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
//	    }
//}
    
    
}
    
    