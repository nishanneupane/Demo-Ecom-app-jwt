package com.nishan.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nishan.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
	List<User> findByAddress(String address);
	List<User> findByAge(Integer age);
	List<User> findByName(String name);
	
	@Query("select u from User u where u.name Like %:query% or u.email Like %:query%")
	public List<User> searchUser(String query);

}
