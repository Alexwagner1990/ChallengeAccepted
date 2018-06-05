package com.skilldistillery.challengeaccepted.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String username;
	private String password;
	private String email;
	private String location;
	private String role;
	
	@ManyToMany
	@JoinTable(name="user_challenge",
	joinColumns =  @JoinColumn(name="invited_user_id"),
	inverseJoinColumns=@JoinColumn(name="challenge_id"))
	@JsonIgnore
	private List <Challenge> challenges;

	@ManyToMany
	@JsonIgnore
	@JoinTable(name="user_skill",
	joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns= @JoinColumn(name="skill_id"))
	private List <Skill> skills;

	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List <UserChallenge> userChallenges;
	
	@OneToMany(mappedBy="user")
	private List <UserSkill> userSkills;
	
	// constructor
	public User() {
	}
	
	
	//gets/sets
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}
	
	public List<Challenge> getChallenges() {
		return challenges;
	}


	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}


	public List<Skill> getSkills() {
		return skills;
	}


	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}


	public List<UserChallenge> getUserChallenges() {
		return userChallenges;
	}


	public void setUserChallenges(List<UserChallenge> userChallenges) {
		this.userChallenges = userChallenges;
	}


	public List<UserSkill> getUserSkills() {
		return userSkills;
	}


	public void setUserSkills(List<UserSkill> userSkills) {
		this.userSkills = userSkills;
	}


	//toString
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);
		builder.append(", location=");
		builder.append(location);
		builder.append(", role=");
		builder.append(role);
		builder.append("]");
		return builder.toString();
	}
	
}
