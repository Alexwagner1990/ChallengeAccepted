package com.skilldistillery.challengeaccepted.controllers;

import java.security.Principal;
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

import com.skilldistillery.challengeaccepted.entities.Message;
import com.skilldistillery.challengeaccepted.services.MessageService;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost:4200" })
public class MessageController {

	@Autowired
	private MessageService messageServ;

	@RequestMapping(path = "ping", method = RequestMethod.GET)
	public String ping() {
		return "pong";
	}

	// show single message
	@RequestMapping(path = "messages/{mid}", method = RequestMethod.GET)
	public Message show(HttpServletRequest req, HttpServletResponse res, @PathVariable int mid, Principal principal) {
		Message mes = messageServ.show(mid, principal.getName());
		if (mes != null) {
			res.setStatus(200);
			return mes;
		}
		res.setStatus(404);
		return null;
	}

	// show all messages in a single thread
	@RequestMapping(path = "messages/threads/{tid}", method = RequestMethod.GET)
	public Set<Message> indexThreadMessages(HttpServletRequest req, HttpServletResponse res, @PathVariable int tid, Principal principal) {
		Set<Message> sm = messageServ.indexThreadMessages(tid);
		if (sm != null) {
			res.setStatus(200);
			return sm;
		}
		res.setStatus(404);
		return null;
	}

	// show the first message of every thread for a user
	@RequestMapping(path = "messages/heads/{username}", method = RequestMethod.GET)
	public Set<Message> indexThreads(HttpServletRequest req, HttpServletResponse res, @PathVariable String username, Principal principal) {
		Set<Message> sm = messageServ.indexThreads(username);
		if (sm != null) {
			res.setStatus(200);
			return sm;
		}
		res.setStatus(404);
		return null;
	}

	// create a new message
	@RequestMapping(path = "messages", method = RequestMethod.POST)
	public Message create(HttpServletRequest req, HttpServletResponse res, @RequestBody Message message, Principal principal) {
		Message newMessage = messageServ.create(message);
		if (newMessage != null) {
			res.setStatus(201);
			return newMessage;
		}
		res.setStatus(404);
		return null;
	}

	// delete a message
	@RequestMapping(path = "messages/{mid}", method = RequestMethod.DELETE)
	public Boolean destroy(HttpServletRequest req, HttpServletResponse res, @PathVariable int mid, Principal principal) {
		
		return messageServ.destroy(principal.getName(), mid);
	}
	
}
