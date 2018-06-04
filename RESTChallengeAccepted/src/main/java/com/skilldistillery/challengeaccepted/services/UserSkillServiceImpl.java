package com.skilldistillery.challengeaccepted.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.challengeaccepted.entities.Skill;
import com.skilldistillery.challengeaccepted.entities.User;
import com.skilldistillery.challengeaccepted.entities.UserSkill;
import com.skilldistillery.challengeaccepted.repositories.SkillRepository;
import com.skilldistillery.challengeaccepted.repositories.UserRepository;
import com.skilldistillery.challengeaccepted.repositories.UserSkillRepository;

@Service
public class UserSkillServiceImpl implements UserSkillService {
	
	@Autowired
	private UserSkillRepository userSkillRepo; 
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private SkillRepository skillzRepo;
	
	public Set<UserSkill> getUserSkillsByUserId(int uid) {
		return userSkillRepo.findByUserId(uid); 
	}
	
	public UserSkill create(UserSkill us) {
		User managedUser = userRepo.findByUsername(us.getUser().getUsername());
		us.setUser(managedUser);
		Skill managedSkill = skillzRepo.findById(us.getSkill().getId()); 
		us.setSkill(managedSkill);
		
		return userSkillRepo.saveAndFlush(us); 
	}
}
