package com.influence.domain.post.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.influence.domain.post.dto.PostRequestDTO;
import com.influence.domain.post.dto.PostResponseDTO;
import com.influence.domain.post.service.PostInfluencerService;
import com.influence.domain.post.service.PostService;
import com.influence.global.utils.S3UploadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostInfluencerController {
	
	private final PostService postService;
    private final PostInfluencerService postInfluencerService;
    private final S3UploadService s3UploadService;
    
    
    // 인플루언서 추천 게시글 작성 
    @PostMapping("/register")
    public  ResponseEntity<Boolean> createPost(@RequestPart PostRequestDTO dto, @RequestPart("file") MultipartFile file, 
    		Authentication authentication) throws IOException {
    	
        // 파일 업로드 로직 추가
        String uploadedFileName = s3UploadService.saveFile(file);
        
	  	Long pno = postService.createPost(dto, uploadedFileName, authentication);
	  	
    	Boolean result = postInfluencerService.createInfluencerPost(dto, pno);

	    return ResponseEntity.ok(result);
    }

    
    // 인플루언서 추천 게시글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<PostResponseDTO>> listPost() {
    	
        List<PostResponseDTO> response = postInfluencerService.getList();
        
        return ResponseEntity.ok(response);
    }
    
    
    
    // 인플루언서 추천 게시글 삭제
    @DeleteMapping("/delete/{ino}/{pno}")
    public  ResponseEntity<Boolean> deletePost(@PathVariable Long ino, @PathVariable Long pno) {
    	
        boolean result = postInfluencerService.deleteInfluencerPost(ino, pno);
      
	        return ResponseEntity.ok(result);
    }
    
    
}
    
    