package com.influence.domain.orders.dto;

import java.sql.Timestamp;
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
public class OrdersDTO {
	
    private Long orderid;
	private int price;
	private Timestamp orderDate;
	private Long meetid;
    private String region;
    private String writer;
    private String title;
    private String content;
	
}

