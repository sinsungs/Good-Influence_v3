package com.influence.domain.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.influence.domain.post.entity.PostReview;

public interface PostReviewRepository extends JpaRepository<PostReview, Long> {
	
  @EntityGraph(attributePaths = {"user"}) // Fetch Join 설정
  Page<PostReview> findAll(Pageable pageable);
	
}
