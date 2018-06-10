package com.skilldistillery.challengeaccepted.services;

import java.util.List;
import java.util.Set;

import com.skilldistillery.challengeaccepted.entities.User;
import com.skilldistillery.challengeaccepted.entities.UserChallenge;
import com.skilldistillery.challengeaccepted.entities.UserChallengeUserDTO;
import com.skilldistillery.challengeaccepted.entities.UserSkill;

public interface UserSkillService {
	
	public List<UserSkill> index();
	
	public UserSkill create(UserSkill us);
	
	public UserSkill update(UserSkill us, int newPoints);

	public Boolean destroy(int usid);
	
	public Set<UserSkill> getUserSkillsByUsername(String username);
	
	public UserSkill getOneUserSkill(int sid, int uid);
	
	public List<UserSkill> getUserSkillsByPoints();
	
	public Boolean tallyResultsOfSkills(int skillId, List<UserChallengeUserDTO> userChallengesDTO);
	
}
