package com.influence.domain.post.dto;

import java.time.LocalDateTime;

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
public class PostRequestDTO {
	
	private Long pno;
	private String title;
	private String content;

    private Long ino;

    private String writer;
}
