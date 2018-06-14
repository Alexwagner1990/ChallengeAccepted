package com.skilldistillery.challengeaccepted.services;

import java.util.Set;

import com.skilldistillery.challengeaccepted.entities.Challenge;
import com.skilldistillery.challengeaccepted.entities.User;

public interface SearchService {

	public Set<Challenge> getChallengesByTags(int[] tagIds);
	public Set<User> getUsersByUsername(String username);
}
