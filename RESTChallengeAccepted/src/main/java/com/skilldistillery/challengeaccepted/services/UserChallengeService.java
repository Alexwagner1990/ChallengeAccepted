package com.skilldistillery.challengeaccepted.services;

import java.util.List;

import com.skilldistillery.challengeaccepted.entities.Challenge;
import com.skilldistillery.challengeaccepted.entities.UserChallenge;
import com.skilldistillery.challengeaccepted.entities.UserChallengeDTO;
import com.skilldistillery.challengeaccepted.entities.UserSkill;

public interface UserChallengeService {

	public UserChallenge create(UserChallengeDTO ucDTO, String username);
	
	public UserChallenge update(UserChallenge uc, String username);
	
	public Boolean delete(int id, String username);
	
	public List<UserChallenge> index();
	
	public UserChallenge show(int id, String username);
	
	public List<UserChallenge> getTheChallengeAcceptorsForAChallenge(int cid, String username);
	
	public List<UserChallenge> challengesUserHasParticipatedIn(String username);
	
	// Need this method to take an acceptor id, the user object is previously retrieved
	public UserChallenge checkIfUserHasAcceptedChallenge(int cid, int aid);
	
	// Changing this back to uid as well, user objects are previously retrieved
	public UserChallenge updateUCRecord(int cid, int uid);
	
	// Changing back to uid, have the id already
	public UserSkill tallyUserSkillPointsForChallenge(Challenge challenge, int uid);
	
	// get the acceptors for a challenge
	public List<UserChallenge> getTheAcceptedChallengeAcceptorsForAChallenge(int cid);
}
