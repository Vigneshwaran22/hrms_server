package com.hb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hb.model.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, Long>{
	
	List<Organization> findAll();

	Optional<Organization> findByCode(String code);
}
