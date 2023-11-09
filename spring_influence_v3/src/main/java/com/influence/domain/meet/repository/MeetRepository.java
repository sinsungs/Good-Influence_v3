package com.influence.domain.meet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.influence.domain.meet.entity.Meet;


public interface MeetRepository extends JpaRepository<Meet, Long> {

    @Query("SELECT COUNT(m) FROM Meet m")
	int countMeet();

    @Query("SELECT COUNT(mu) FROM MeetUser mu")
	int meetRank();
    
    @EntityGraph(attributePaths = {"user"}) // Fetch Join 설정
    List<Meet> findAll();
    
}
