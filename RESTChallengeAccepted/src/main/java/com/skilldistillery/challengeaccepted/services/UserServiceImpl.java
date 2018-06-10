package com.skilldistillery.challengeaccepted.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilldistillery.challengeaccepted.entities.User;
import com.skilldistillery.challengeaccepted.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public User register(String json) {
		ObjectMapper om = new ObjectMapper();
		User user = null;

		try {
			user = om.readValue(json, User.class);

			String encodedPW = encoder.encode(user.getPassword());
			user.setPassword(encodedPW); // only persist encoded password

			// set other fields to default values

			userRepo.saveAndFlush(user);
		} catch (Exception e) {
			System.out.println(e);
		}		
		return user;
	}

	public User create(User u, String username) {
		userRepo.saveAndFlush(u);
		return u;
	}

	public User update(User u, String username) {
		User managedUser = userRepo.findByUsername(username);
		if(u.getChallenges() != null) {
			managedUser.setChallenges(u.getChallenges());
		}
		if(u.getEmail() != null && !u.getEmail().equals("")) {
			managedUser.setEmail(u.getEmail());
		}
		if(u.getLocation() != null && !u.getLocation().equals("")) {
			managedUser.setLocation(u.getLocation());
		}
		if(u.getSkills() != null) {
			managedUser.setSkills(u.getSkills());
		}
		if(u.getUserChallenges() != null) {
			managedUser.setUserChallenges(u.getUserChallenges());
		}
		if(u.getUserSkills() != null) {
			managedUser.setUserSkills(u.getUserSkills());
		}
		userRepo.saveAndFlush(managedUser);
		return managedUser;
	}
	
	public User show(String urlUserName, String principalUsername) {
		User user = userRepo.findByUsername(urlUserName);
		return userRepo.findByIdAndUsername(user.getId(), user.getUsername());
	}
	
	public Boolean delete(String username) {
		
		User u = userRepo.findByUsername(username);
		
		try {
			userRepo.deleteById(u.getId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User isUserValid(String username) {
		try {
			User u = userRepo.findByUsername(username);
			return u;
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
