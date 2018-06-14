package com.skilldistillery.challengeaccepted.controllers;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.challengeaccepted.entities.Challenge;
import com.skilldistillery.challengeaccepted.entities.User;
import com.skilldistillery.challengeaccepted.services.SearchService;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost:4200" })
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(path="/search/challenges/{tagIds}", method=RequestMethod.GET)
	public Set<Challenge> searchChallengesByTag(HttpServletRequest req, HttpServletResponse res, @PathVariable int[] tagIds, Principal principal){
		return searchService.getChallengesByTags(tagIds);
	}
	
	@RequestMapping(path="/search/users/{username}", method=RequestMethod.GET)
	public Set<User> searchUsersByUsername(HttpServletRequest req, HttpServletResponse res, @PathVariable String username, Principal principal){
		return searchService.getUsersByUsername(username);
	}

}
