package com.skilldistillery.challengeaccepted.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.challengeaccepted.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByIdAndUsername(int uid, String username);

	public User findByUsername(String username);
	@Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%',:username,'%')")
	public Set<User> searchByUsername(@Param("username")String username);

}
