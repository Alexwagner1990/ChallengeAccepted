package com.skilldistillery.challengeaccepted.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.challengeaccepted.entities.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
	
	// find all challenges by status, passing status id
	public Set<Challenge> findByStatusId(int sid);
	
//	// find all challenges by status for a single user, passing status id and username
//	public Set<Challenge> findByStatusAndUserId(int sid, int uid);
	
	// example: select * from challenge c JOIN user_challenge uc ON uc.challenge_id = c.id 
//				JOIN status s ON c.status_id = s.id WHERE uc.invited_user_id = 1 
//				AND s.id = 1
	@Query("SELECT c FROM Challenge c JOIN UserChallenge uc ON uc.challenge.id = c.id JOIN Status s ON c.status.id = s.id WHERE uc.user.id = :uid AND s.id = :sid")
	public Set<Challenge> getChallengesByUserIdAndStatus(@Param("uid") int uid, @Param("sid") int sid); 
	
	public Set<Challenge> findByTagsId(int id);
}
