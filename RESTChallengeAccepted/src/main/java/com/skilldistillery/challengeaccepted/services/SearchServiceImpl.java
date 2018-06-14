package com.skilldistillery.challengeaccepted.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.challengeaccepted.entities.Challenge;
import com.skilldistillery.challengeaccepted.entities.Tag;
import com.skilldistillery.challengeaccepted.entities.User;
import com.skilldistillery.challengeaccepted.repositories.ChallengeRepository;
import com.skilldistillery.challengeaccepted.repositories.TagRepository;
import com.skilldistillery.challengeaccepted.repositories.UserRepository;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ChallengeRepository challengeRepo;
	@Autowired
	private TagRepository tagRepo;
	@Autowired
	private ChallengeService challengeService;
	@Autowired
	private UserRepository userRepo;


	@Override
	public Set<Challenge> getChallengesByTags(int[] tagIds) {
		Set<Challenge> allPendingChallenges = new HashSet<>();
		Set<Challenge> whatIWillReturn = new HashSet<>();
		List<Tag> tagList = new ArrayList<>();
		
		for (int id : tagIds) {
			Tag tag = tagRepo.findById(id).get();
			tagList.add(tag);
		}
		allPendingChallenges = challengeService.indexStatusChallenges(1);
		for (Challenge challenge : allPendingChallenges) {
//			for (Tag tag : tagList) {
//				if (challenge.getTags().contains(tag)) {
//					whatIWillReturn.add(challenge);
//				}
			if(challenge.getTags().containsAll(tagList)) {
				whatIWillReturn.add(challenge);
			}
		}
//		System.out.println("IN SEARCH SERVICE" + whatIWillReturn);
		return whatIWillReturn;
	}


	@Override
	public Set<User> getUsersByUsername(String username) {
		Set<User> users = userRepo.searchByUsername(username);
		System.out.println(users);
		return users;
	}

}
