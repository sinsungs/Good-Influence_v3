package com.influence.domain.post.dto;

import com.influence.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostReviewDTO {
	
	private Long prno;
	
	private String title;
	private String content;
	
    private String writer;
    private String imageurl;
}
