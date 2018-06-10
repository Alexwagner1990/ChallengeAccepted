package com.skilldistillery.challengeaccepted.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.challengeaccepted.entities.UserChallengeUserDTO;
import com.skilldistillery.challengeaccepted.entities.UserSkill;
import com.skilldistillery.challengeaccepted.services.UserSkillService;

@RestController
@RequestMapping("api")
@CrossOrigin({"*", "http://localhost:4200"})
public class UserSkillController {
	
	@Autowired
	private UserSkillService userSkillSvc;
	
	@RequestMapping(path= "userskills", method = RequestMethod.GET)
	public List<UserSkill> index() {
		return userSkillSvc.index();
	}
	
	// returns list of all user_skill records based on user id
	@RequestMapping(path="userskills/{username}", method=RequestMethod.GET)
	public Set<UserSkill> getUserSkillsByUserId(HttpServletRequest req, HttpServletResponse res, @PathVariable String username, Principal principal) {
		Set<UserSkill> userSkills = userSkillSvc.getUserSkillsByUsername(username);
		if (userSkills != null) {
			res.setStatus(200);
			return userSkills;
		}
		res.setStatus(404);
		return null;
	}
	
	@RequestMapping(path="userskills/indexByPoints", method=RequestMethod.GET)
	public List<UserSkill> getUserSkillsByPoints(HttpServletRequest req, HttpServletResponse res, Principal principal) {
		List<UserSkill> userSkills = userSkillSvc.getUserSkillsByPoints();
		if (userSkills != null) {
			res.setStatus(200);
			return userSkills;
		}
		res.setStatus(404);
		return null;
	}
	
	// create a new UserSkill record
	@RequestMapping(path="userskills", method=RequestMethod.POST)
	public UserSkill create(HttpServletRequest req, HttpServletResponse res, @RequestBody UserSkill us, Principal principal) {
		UserSkill newUS = userSkillSvc.create(us); 
		if (newUS != null) {
			res.setStatus(201);
			return newUS;
		}
		res.setStatus(404);
		return null;
	}
	
	// update points on an existing UserSkill record (usid = userskill id)
	@RequestMapping(path="userskills/{usid}/{newPoints}", method=RequestMethod.PATCH)
	public UserSkill update(HttpServletRequest req, HttpServletResponse res, @RequestBody UserSkill us, @PathVariable int usid, 
			@PathVariable int newPoints, Principal principal) {
		return userSkillSvc.update(us, newPoints); 
	}
	
	// get a particular userskill
	@RequestMapping(path="userskill/one/{sid}/{uid}", method=RequestMethod.GET)
	public UserSkill getUserSkill(HttpServletRequest req, HttpServletResponse res, @PathVariable int sid, 
			@PathVariable int uid, Principal principal) {
		return userSkillSvc.getOneUserSkill(sid, uid); 
	}
	
	// delete a userskill record by userskill id
	@RequestMapping(path="userskills/{usid}", method=RequestMethod.DELETE)
	public Boolean destory(HttpServletRequest req, HttpServletResponse res, @PathVariable int usid, Principal principal) {
		return userSkillSvc.destroy(usid);
	}
	
	// Update the points in the user-skill table of users who won or lost a challenge
	@RequestMapping(path = "skills/{skillid}/tallyResults", method = RequestMethod.PATCH)
	public Boolean tallyResultsOfChallenge(@PathVariable int skillid, @RequestBody List<UserChallengeUserDTO>userChallengesDTO, 
			String username, Principal principal) {
		return userSkillSvc.tallyResultsOfSkills(skillid, userChallengesDTO);
	}
}
