package com.influence.domain.post.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.influence.domain.post.dto.PostReviewDTO;
import com.influence.domain.post.service.PostReviewService;
import com.influence.global.utils.S3UploadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/postreview")
@RequiredArgsConstructor
public class PostReviewController {
	
    private final PostReviewService postReviweService;
    private final S3UploadService s3UploadService;
    
    
    // 소셜 모임 후기 게시글 작성 
    @PostMapping("/register")
    public  ResponseEntity<Boolean> createPost(@RequestPart PostReviewDTO dto, @RequestPart("file") MultipartFile file, 
    		Authentication authentication) throws IOException {
    	
        // 파일 업로드 로직 추가
        String uploadedFileName = s3UploadService.saveFile(file);

    	boolean result = postReviweService.createPost(dto, uploadedFileName, authentication);  	 
        
	    if (result) {
	        return ResponseEntity.ok(true);
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
	    }
    }
    
    
    // 소셜 모임 후기 게시글 전체 조회 
//    @GetMapping("/list")
//    public ResponseEntity<List<PostReviewDTO>> listPost() {
//    	
//        List<PostReviewDTO> response = postReviweService.getList();
//        
//        return ResponseEntity.ok(response);
//        
//    }
    @GetMapping("/list")
    public ResponseEntity<Page<PostReviewDTO>> listPost(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<PostReviewDTO> response = postReviweService.getList(page, size);
        return ResponseEntity.ok(response);
    }
     
    // 소셜 모임 후기 게시글 상세보기
   @GetMapping("/read/{prno}")
   public ResponseEntity<PostReviewDTO> readPost(@PathVariable Long prno) {
  	
	   PostReviewDTO response = postReviweService.getPost(prno);
      
	   return ResponseEntity.ok(response);
      
  }
    
    // 소셜 모임 후기 게시글 삭제 
    @DeleteMapping("/delete/{prno}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long prno) {
    	
        boolean result = postReviweService.deletePost(prno);
        
	    if (result) {
	        return ResponseEntity.ok(true);
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
	    }
}
    
}
    
    