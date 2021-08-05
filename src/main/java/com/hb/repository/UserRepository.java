package com.hb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.hb.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findAll();
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUserName(String userName);

	Page<User> findAll(Pageable paging);

	Page<User> findByNameContaining(String name, Pageable paging);
}
