package com.influence.domain.post.dto;

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
public class PostCommentDTO {
	
	private Long prno;
	
	private String title;
	private String content;
	
    private String writer;
    private String imageurl;
    
    private Long cno;
    
    private String ccontent;
    private String cwriter;
}
