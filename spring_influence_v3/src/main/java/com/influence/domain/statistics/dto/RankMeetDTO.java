package com.influence.domain.statistics.dto;

import java.util.List;

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
public class RankMeetDTO {
	
	private Long id;
	private String username;
	private String sns;
	private int experience;
	private int meetcount;

    
}