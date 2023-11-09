package com.influence.domain.post.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.influence.domain.influencer.entity.Influencer;
import com.influence.domain.influencer.repository.InfluencerRepository;
import com.influence.domain.post.dto.PostRequestDTO;
import com.influence.domain.post.dto.PostResponseDTO;
import com.influence.domain.post.entity.Post;
import com.influence.domain.post.entity.PostInfluencer;
import com.influence.domain.post.mapper.PostMapper;
import com.influence.domain.post.repository.PostInfluencerRepository;
import com.influence.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostInfluencerService{

	private final PostRepository postRepository;
	private final InfluencerRepository influencerRepository;
	private final PostInfluencerRepository postInfluencerRepository;
	private final PostMapper postMaper;
	
	
	// 인플루언서 추천 게시글 작성 
	public Boolean createInfluencerPost(PostRequestDTO dto, Long pno) {
		
		dto.setPno(pno);
		
        Post post = postRepository.findById(dto.getPno()).orElseThrow(() -> new RuntimeException("Post not found"));
        Influencer influencer = influencerRepository.findById(dto.getIno()).orElseThrow(() -> new RuntimeException("Influencer not found"));
  
		PostInfluencer postInfluencer = postMaper.dtoToEntity(post, influencer);

		postInfluencerRepository.save(postInfluencer);
        
        return true;
    }

     
     // 인플루언서 추천 게시글 전체 조회
     public List<PostResponseDTO> getList() {
    	
        List<PostInfluencer> postInfluencers = postInfluencerRepository.findAllWithPostAndInfluencer();
        
        List<PostResponseDTO> dtoList = new ArrayList<>();
        
        for (PostInfluencer postInfluencer : postInfluencers) {
            dtoList.add(postMaper.entityToDTO(postInfluencer));
        }
        
        return dtoList;
    }
     
     
    // 인플루언서 추천 게시글 삭제 
    public boolean deleteInfluencerPost(Long ino, Long pno) {
   	 
   	 
	        Influencer influencer = influencerRepository.findById(ino).orElse(null);
	        Post post = postRepository.findById(pno).orElse(null);

   	 	PostInfluencer postInfluencers = postInfluencerRepository.findByInfluencerAndPost(influencer, post);

	        if (postInfluencers == null) {
	            return false;
	        }
	        
	        postInfluencerRepository.delete(postInfluencers);
        
		return true;
	}

    	
}
