package com.skilldistillery.challengeaccepted.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.challengeaccepted.entities.Challenge;
import com.skilldistillery.challengeaccepted.entities.User;
import com.skilldistillery.challengeaccepted.entities.UserChallenge;
import com.skilldistillery.challengeaccepted.entities.UserChallengeDTO;
import com.skilldistillery.challengeaccepted.entities.UserSkill;
import com.skilldistillery.challengeaccepted.repositories.ChallengeRepository;
import com.skilldistillery.challengeaccepted.repositories.UserChallengeRepository;
import com.skilldistillery.challengeaccepted.repositories.UserRepository;
import com.skilldistillery.challengeaccepted.repositories.UserSkillRepository;

@Service
public class UserChallengeServiceImpl implements UserChallengeService {

	@Autowired
	private UserChallengeRepository userChallengeRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ChallengeRepository challengeRepository;
	@Autowired
	private UserSkillRepository userSkillRepo;
	@Autowired
	private UserSkillServiceImpl uSkillImpl;

	public UserChallenge create(UserChallengeDTO ucDTO, String username) {
		UserChallenge uc = new UserChallenge();
		User user = userRepo.findById(ucDTO.getAcceptorId()).get();
		Challenge challenge = challengeRepository.findById(ucDTO.getChallengeId()).get();
		uc.setAccepted(true);
		uc.setAcceptorWon(false);
		uc.setChallenge(challenge);
		uc.setUser(user);
		userChallengeRepo.saveAndFlush(uc);
		return uc;
	}

	public UserChallenge update(UserChallenge uc, String username) {
		UserChallenge managedUser = userChallengeRepo.findById(uc.getId()).get();
		managedUser.setAcceptorWon(uc.isAcceptorWon());
		managedUser.setAccepted(uc.isAccepted());
		userChallengeRepo.saveAndFlush(managedUser);

		return managedUser;
	}

	public Boolean delete(int id, String username) {
		try {
			userChallengeRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<UserChallenge> index() {
		return userChallengeRepo.findAll();
	}

	public UserChallenge show(int id, String username) {
		try {
			UserChallenge userChallenge = userChallengeRepo.findById(id).get();
			return userChallenge;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// gets all user challenge records for a single challenge
	public List<UserChallenge> getTheChallengeAcceptorsForAChallenge(int cid, String username) {
		return userChallengeRepo.findByChallengeId(cid);
	}
	
	// gets all user challenge records for a single challenge (that have accepted, used to tally winners and losers)
	public List<UserChallenge> getTheAcceptedChallengeAcceptorsForAChallenge(int cid) {
		return userChallengeRepo.findByChallengeIdAndAccepted(cid);
	}
	
	 // tally points for all user skill records for a challenge
	//also changing this back to uid, have the ID don't need the username
    public UserSkill tallyUserSkillPointsForChallenge(Challenge challenge, int uid) {
    	User u = userRepo.findById(uid).get();
    	System.out.println("Challenge: " + challenge.getId());
    	System.out.println("User: " + u.getId());
        UserChallenge managedUserChallenge = userChallengeRepo.findByUserIdAndChallengeId(u.getId(), challenge.getId());
        int newPoints = 0;
            if (managedUserChallenge.isAcceptorWon()) {
                newPoints = 5;    
            }
            else if (!managedUserChallenge.isAcceptorWon() && managedUserChallenge.isAccepted()){
                newPoints = 2;
            }
            else {
                newPoints = 0;
            }
            UserSkill uSkill = userSkillRepo.findBySkillIdAndUserId(challenge.getSkill().getId(), managedUserChallenge.getUser().getId());
            uSkillImpl.update(uSkill, newPoints);
        return uSkill;
    } 
//=======  			OLD ONE, WORKS.
//	// tally points for all user skill records for a challenge
//	public UserSkill tallyUserSkillPointsForChallenge(Challenge challenge, int uid) {
//		UserChallenge managedUserChallenge = userChallengeRepo.findByUserIdAndChallengeId(challenge.getId(), uid);
//		int newPoints = 0;
//			if (managedUserChallenge.isAcceptorWon()) {
//				newPoints = 5;	
//			}
//			else if (!managedUserChallenge.isAcceptorWon() && managedUserChallenge.isAccepted()){
//				newPoints = 2;
//			}
//			else {
//				newPoints = 0;
//			}
//			UserSkill uSkill = userSkillRepo.findBySkillIdAndUserId(challenge.getSkill().getId(), managedUserChallenge.getUser().getId());
//			uSkillImpl.update(uSkill, newPoints);
//		return uSkill;
//	}
//>>>>>>>

	public List<UserChallenge> challengesUserHasParticipatedIn(String username) {
		User u = userRepo.findByUsername(username);
		return userChallengeRepo.findByUserId(u.getId());
	}

	@Override
	//changed from taking a username to taking a user ID - the logged in user ID is previously grabbed from DB
	public UserChallenge checkIfUserHasAcceptedChallenge(int cid, int aid) {
		System.out.println(aid);
		User u = userRepo.findById(aid).get();
//		System.out.println(u);
		UserChallenge uc = userChallengeRepo.findByUserIdAndChallengeId(u.getId(), cid);
//		System.out.println(uc);
		return uc;
	}
	
	//Changing this back to a uid, I have the IDs from DB already
	public UserChallenge updateUCRecord(int cid, int uid) {
		User u = userRepo.findById(uid).get();
		UserChallenge uc = userChallengeRepo.findByUserIdAndChallengeId(u.getId(), cid);
		uc.setAcceptorWon(true);
		userChallengeRepo.saveAndFlush(uc);
		return uc;
	}

}
