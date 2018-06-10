package com.skilldistillery.challengeaccepted.controllers;


import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.challengeaccepted.entities.User;
import com.skilldistillery.challengeaccepted.services.UserService;

@RestController
@RequestMapping("api")
@CrossOrigin({"*", "http://localhost:4200"})
public class UserController {
	
	@Autowired
	private UserService userServ;
	
	
	
	// finds user by user id
	@RequestMapping(path="users/{username}", method=RequestMethod.GET)
	public User viewUserProfile(HttpServletRequest req, HttpServletResponse res, @PathVariable String username, Principal principal) {
		User user = userServ.show(username, principal.getName());
		if ( user != null) {
			res.setStatus(200);
			return userServ.show(username, principal.getName());
		}
		res.setStatus(404);
		return null;
	}
	
	// update existing user w/ user id
	@RequestMapping(path="users", method=RequestMethod.PATCH)
	public User updateUser(@RequestBody User user, Principal principal) {
		return userServ.update(user, principal.getName());
	}
	
	// delete user by user id
//	@RequestMapping(path="users", method=RequestMethod.DELETE)
//	public Boolean deleteUser(Principal principal) {
//		System.out.println("*******************************");
//		return userServ.delete(principal.getName());
//	}
	
	@RequestMapping(path="users", method=RequestMethod.DELETE)
	public Boolean deleteUser(Principal principal) {
		System.out.println("*******************************");
		return userServ.delete(principal.getName());
	}
	
	//checks that a user is valid
	@RequestMapping(path="users/{username}/valid", method=RequestMethod.GET)
	public User isUserValid(@PathVariable String username, Principal principal) {
		return userServ.isUserValid(username);
	}
	
}
