package com.influence.domain.comment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.influence.domain.post.entity.Post;
import com.influence.domain.post.entity.PostReview;
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
public class Comment {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;
    
    private String content;

    @ManyToOne
    @JoinColumn(name = "postid")
    private PostReview post;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;


}



