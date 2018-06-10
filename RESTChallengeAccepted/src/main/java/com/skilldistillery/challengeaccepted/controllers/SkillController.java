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

import com.skilldistillery.challengeaccepted.entities.Skill;
import com.skilldistillery.challengeaccepted.entities.UserChallenge;
import com.skilldistillery.challengeaccepted.services.SkillService;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost:4200" })
public class SkillController {

	@Autowired
	private SkillService skillzServ;

	@RequestMapping(path = "skills", method = RequestMethod.GET)
	public List<Skill> allSkills(HttpServletRequest req, HttpServletResponse res, Principal principal) {
		List<Skill> ls = skillzServ.index();
		if (ls != null) {
			res.setStatus(200);
			return ls;

		}
		res.setStatus(404);
		return null;
	}

	@RequestMapping(path = "skills/{id}", method = RequestMethod.GET)
	public Skill skillById(HttpServletRequest req, HttpServletResponse res, @PathVariable int id, Principal principal) {
		Skill skill = skillzServ.oneSkill(id);
		if (skill != null) {
			res.setStatus(200);
			return skill;
		}
		res.setStatus(400);
		return null;
	}
	


}
