package com.skilldistillery.challengeaccepted.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.challengeaccepted.entities.UserChallenge;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Integer> {


	@Query("SELECT uc FROM UserChallenge uc WHERE uc.challenge.id = :challId AND uc.accepted = true")	
	public List<UserChallenge> findByChallengeIdAndAccepted(@Param("challId") int challId);
	public List<UserChallenge> findByChallengeId(int userId);
	public List<UserChallenge> findByUserId(int userId);
	@Query("SELECT uc FROM UserChallenge uc WHERE uc.challenge.id = :challId AND uc.user.id = :userId")
	public UserChallenge findByUserIdAndChallengeId(@Param("userId") int userId, @Param("challId") int challId);
}
