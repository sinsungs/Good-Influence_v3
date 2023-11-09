package com.influence.domain.influencer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.influence.domain.influencer.entity.Influencer;
import com.influence.domain.meet.entity.Meet;
import com.influence.domain.user.entity.User;



public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
	
	List<Influencer> findByNameLike(String query);

    @Query("SELECT COUNT(i) FROM Influencer i")
    int countAllInfluencers();
    
    @Query("SELECT COUNT(i) FROM Influencer i WHERE i.category = '먹방'")
    int countInfluencersByCategory();

    @Query("SELECT COUNT(m) FROM Meet m")
	int countMeet();

	Influencer findByUser(User user);
	
    @EntityGraph(attributePaths = {"user"}) // Fetch Join 설정
    List<Influencer> findAll();
    
    
}
