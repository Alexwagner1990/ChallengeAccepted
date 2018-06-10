package com.skilldistillery.challengeaccepted.services;

import com.skilldistillery.challengeaccepted.entities.User;

public interface UserService {
	
	public User create(User u, String username);
	
	public User update(User u, String username);
	
	public User show(String urlUserName, String principalUsername);
	
	public Boolean delete(String username);

	User register(String json);
	
	public User isUserValid(String username);
	
}
