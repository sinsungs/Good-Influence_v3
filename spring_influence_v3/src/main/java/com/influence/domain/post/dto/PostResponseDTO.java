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
public class PostResponseDTO {
	
	private Long pno;
	private String title;
	private String content;

    private Long ino;
    private String name;

    private String writer;
    
    private String imageurl;
    private String influencerimage;
}
