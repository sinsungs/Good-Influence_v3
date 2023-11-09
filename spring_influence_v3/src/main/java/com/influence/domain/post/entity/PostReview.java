package com.influence.domain.post.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.influence.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prno;
    
	private String title;
	private String content;	
	
	private String imageUrl; // 이미지 파일의 경로나 URL을 저장할 필드

    @ManyToOne 
    @JoinColumn(name = "user_id")
    private User user; 


}