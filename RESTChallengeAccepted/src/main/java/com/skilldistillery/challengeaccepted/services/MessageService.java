package com.skilldistillery.challengeaccepted.services;

import java.util.Set;

import com.skilldistillery.challengeaccepted.entities.Message;

public interface MessageService {
	
	public Set<Message> index(String username);
	public Message show(String username, int mid);
	public Message create(Message message);
//	public Message update(String username, int mid, Message message);
	public void destroy (String username, int mid);

}
