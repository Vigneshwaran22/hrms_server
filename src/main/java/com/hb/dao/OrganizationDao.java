package com.hb.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.hb.model.Organization;

public interface OrganizationDao {
	
	public List<Organization> findAllOrganizations();
	
	public Organization saveOrganization(Organization organization);
	
	public Optional<Organization> findById(Long id);
	
	public Optional<Organization> findByCode(String code);

	public void filterOrganizations(String title, Pageable paging, Map<String, Object> response);
	
}
	