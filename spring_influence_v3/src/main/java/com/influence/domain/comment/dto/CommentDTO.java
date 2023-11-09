package com.influence.domain.comment.dto;

import com.influence.domain.post.dto.PostRequestDTO;

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
public class CommentDTO {

	private Long cno;
	private String content;
    private String writer;
    
}

