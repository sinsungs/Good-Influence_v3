package com.influence.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.influence.domain.user.RoleType;
import com.influence.global.utils.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.influence.domain.meet.entity.Meet;
import com.influence.domain.meetuser.entity.MeetUser;
import com.influence.domain.orders.entity.Orders;
import com.influence.domain.payment.entity.Payment;
import com.influence.domain.influencer.entity.Influencer;
import com.influence.domain.post.entity.Post;
import com.influence.domain.post.entity.PostReview;
import com.influence.domain.comment.entity.Comment;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 테이블 생성 효과
public class User extends BaseEntity {

	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement 
	private Long id;
	
	@Column(nullable=false, length=100)
	private String username;
	
	@Column(nullable=false, length=100) // 해쉬를 넣기에 100으로 설정
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	
//	@ColumnDefault("'user'")
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다.
	
	private String oauth;
	
	private int amount;
	
//	@CreationTimestamp // 시간이 자동 입력
//	private Timestamp createDate;
	
	private int experience;
	
	private String sns;
	
	private String imageUrl;
	


}
