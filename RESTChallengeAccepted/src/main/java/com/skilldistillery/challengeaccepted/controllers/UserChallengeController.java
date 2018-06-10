package com.skilldistillery.challengeaccepted.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.challengeaccepted.entities.Challenge;
import com.skilldistillery.challengeaccepted.entities.UserChallenge;
import com.skilldistillery.challengeaccepted.entities.UserChallengeDTO;
import com.skilldistillery.challengeaccepted.entities.UserSkill;
import com.skilldistillery.challengeaccepted.services.UserChallengeService;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost:4200" })
public class UserChallengeController {

	@Autowired
	private UserChallengeService userChallengeService;

	// users can view a single challenge they have accepted by challenge id - SHOW method.
	@RequestMapping(path = "user/challenges/accept/{cid}", method = RequestMethod.GET)
	public UserChallenge viewUserChallenge(HttpServletRequest req, HttpServletResponse res, @PathVariable int cid,
			Principal principal) {
		UserChallenge uc = userChallengeService.show(cid, principal.getName());
		if (uc != null) {
			res.setStatus(200);
			return uc;
		}
		res.setStatus(400);
		return null;
	}

	// check if a user has already accepted a challenge - return the object if user
	// has, else null                                   aid is acceptor ID, from an already-retrieved logged-in user object (whole thing, not jsut principle)
	@RequestMapping(path = "user/challenges/{cid}/user/{aid}/check", method = RequestMethod.GET)
	public UserChallenge checkIfUserHasAcceptedChallenge(HttpServletRequest req, HttpServletResponse res,
			@PathVariable int cid, @PathVariable int aid, Principal principal) {
		UserChallenge uc = userChallengeService.checkIfUserHasAcceptedChallenge(cid, aid);
//		System.out.println(username);
		if (uc != null) {
			res.setStatus(200);
			return uc;
		}
		res.setStatus(400);
		return null;
	}

	// returns all user_challenge records for one challenge
	@RequestMapping(path = "challenges/{cid}/accept/all", method = RequestMethod.GET)
	public List<UserChallenge> allAcceptsForChallenge(HttpServletRequest req, HttpServletResponse res,
			@PathVariable int cid, String username, Principal principal) {
		List<UserChallenge> luc = userChallengeService.getTheChallengeAcceptorsForAChallenge(cid, username);
		if (luc != null) {
			res.setStatus(200);
			return luc;
		}
		res.setStatus(400);
		return null;
	}
	
	// tally user skill points from challenge record
//	@RequestMapping(path="challenges/{cid}/userskills", method=RequestMethod.PATCH)
//	public List<UserSkill> tallyUserSkillPointsForChallenge(@RequestBody Challenge challenge, @PathVariable int cid) {
//		return userChallengeService.tallyUserSkillPointsForChallenge(challenge);
//	}

	// returns all challenges a user has accepted, active and inactive
	@RequestMapping(path = "user/{username}/challenges/accept/all", method = RequestMethod.GET)
	public List<UserChallenge> allAcceptsForUser(HttpServletRequest req, HttpServletResponse res,
			@PathVariable String username, Principal principal) {
		if (userChallengeService.challengesUserHasParticipatedIn(username) != null) {
			res.setStatus(200);
			return userChallengeService.challengesUserHasParticipatedIn(username);
		}
		res.setStatus(404);
		return null;
	}

	// creates a new user_challenge record when users accept a challenge by passing
	// a challenge id

	@RequestMapping(path = "challenges/{cid}/accept", method = RequestMethod.POST)
	public UserChallenge createUserChallenge(HttpServletRequest req, HttpServletResponse res,
			@RequestBody UserChallengeDTO ucDTO, @PathVariable int cid, Principal principal) {
		UserChallenge uc = userChallengeService.create(ucDTO, principal.getName());
		if (uc != null) {
			res.setStatus(201);
			return uc;
		}
		res.setStatus(400);
		return null;
	}

	// update an accepted user_challenge record w/ user_challenge id

	@RequestMapping(path = "challenges/accept/{ucid}", method = RequestMethod.PATCH)
	public UserChallenge updateUserChallenge(@RequestBody UserChallenge userChallenge, String username, @PathVariable int ucid, Principal principal) {
		return userChallengeService.update(userChallenge, username);

	}
	
	// update a user_challenge record based on user id and challenge id
	// Changing this back to a uid, the user id at this point has been retrieved from DB
	@RequestMapping(path="challenges/{cid}/user/{uid}", method=RequestMethod.PATCH)
	public UserChallenge updateUserChallengeRecord(HttpServletRequest req, HttpServletResponse res, @PathVariable int cid, 
			@PathVariable int uid, @RequestBody Challenge challenge, Principal principal) {
		return userChallengeService.updateUCRecord(cid, uid);
//		return userChallengeService.tallyUserSkillPointsForChallenge(challenge, uid);
	}


	// delete a user challenge record w/ the challenge id and user challenge id
//	As of right now, we're only hiding user challenge records; not needed for MVP
	@RequestMapping(path = "challenges/accept/{ucid}", method = RequestMethod.DELETE)
	public Boolean deleteUserChallenge(@PathVariable int cid, @PathVariable int ucid,
			String username, Principal principal) {
		
		return userChallengeService.delete(ucid, username);
	}
	
	//GET all the user_challenges of a challenge, so that each record can be updated after a win
	@RequestMapping(path = "challenges/{id}/allUserChallenges", method = RequestMethod.GET)
	public List<UserChallenge> getAllAcceptedUserChallenges(@PathVariable int id,
			String username, Principal principal) {
		return userChallengeService.getTheAcceptedChallengeAcceptorsForAChallenge(id);
	}
	

}