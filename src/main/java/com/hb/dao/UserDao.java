package com.hb.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.hb.model.User;

public interface UserDao {
	
	public List<User> findAllUsers();
	
	public User saveUser(User user);
	
	public Optional<User> findById(Long id);
	
	public Optional<User> findByUserName(String userName);
	
	public Optional<User> findByEmail(String email);
	
	public void filterUsers(String title, Pageable paging, Map<String, Object> response);
	

}
