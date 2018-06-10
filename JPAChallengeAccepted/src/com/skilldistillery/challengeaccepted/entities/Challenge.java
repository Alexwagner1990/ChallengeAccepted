package com.skilldistillery.challengeaccepted.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Challenge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String location;
	private String name;
	private String description;
	private Integer wager;
//	private Boolean visibleToPublic;
	
	@Column(name="min_number_of_challengers")
	private int minNumberOfChallengers;
	
	@Column(name="max_number_of_challengers")
	private int maxNumberOfChallengers;
	
	@ManyToOne
	@JoinColumn(name="status_id")
	private Status status;
	
	@ManyToMany(mappedBy="challenges")
	private List <User> acceptors;
	
	@ManyToMany
	@JoinTable(name="challenge_tag",
	joinColumns = @JoinColumn(name="challenge_id"),
	inverseJoinColumns= @JoinColumn(name="tag_id"))
	private List<Tag> tags;
	
	@ManyToOne
	@JoinColumn(name="creator_id")
//	@JsonIgnore
	private User creator;
	
	@ManyToOne
	@JoinColumn(name="skill_id")
	private Skill skill;
	
	@JsonIgnore
	@OneToMany(mappedBy="challenge")
	private List <UserChallenge> userChallenges;
	
	@CreationTimestamp
	@Column(name= "time_created")
	private Date timeCreated;
	
	public Challenge() {
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWager() {
		return wager;
	}

	public void setWager(int wager) {
		this.wager = wager;
	}

	public List<User> getUsers() {
		return acceptors;
	}

	public void setUsers(List<User> acceptors) {
		this.acceptors = acceptors;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}


	public List<UserChallenge> getUserChallenges() {
		return userChallenges;
	}

	public void setUserChallenges(List<UserChallenge> userChallenges) {
		this.userChallenges = userChallenges;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	public int getMinNumberOfChallengers() {
		return minNumberOfChallengers;
	}

	public void setMinNumberOfChallengers(int minNumberOfChallengers) {
		this.minNumberOfChallengers = minNumberOfChallengers;
	}

	public int getMaxNumberOfChallengers() {
		return maxNumberOfChallengers;
	}

	public void setMaxNumberOfChallengers(int maxNumberOfChallengers) {
		this.maxNumberOfChallengers = maxNumberOfChallengers;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

//	public Boolean getVisibleToPublic() {
//		return visibleToPublic;
//	}
//
//
//	public void setVisibleToPublic(Boolean visibleToPublic) {
//		this.visibleToPublic = visibleToPublic;
//	}


	public List<User> getAcceptors() {
		return acceptors;
	}


	public void setAcceptors(List<User> acceptors) {
		this.acceptors = acceptors;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setWager(Integer wager) {
		this.wager = wager;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Challenge [id=");
		builder.append(id);
		builder.append(", location=");
		builder.append(location);
		builder.append(", name=");
		builder.append(name);
		builder.append(", wager=");
		builder.append(wager);
		builder.append(", users=");
		builder.append(acceptors);
		builder.append(", status=");
		builder.append(status);
		builder.append(", timeCreated=");
		builder.append(timeCreated);
		builder.append("]");
		return builder.toString();
	}
}
