package com.hb.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.hb.dao.UserDao;
import com.hb.model.User;
import com.hb.repository.UserRepository;

@Repository
public class UserImpl implements UserDao, Serializable {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public Optional<User> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void filterUsers(String name, Pageable paging, Map<String, Object> response) {
		
		List<User> users = new ArrayList<User>();
		Page<User> pageTuts;
		if (name == null)
			pageTuts = userRepository.findAll(paging);
		else
			pageTuts = userRepository.findByNameContaining(name, paging);

		users = pageTuts.getContent();
		
		response.put("datas", users);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		
	}

}
