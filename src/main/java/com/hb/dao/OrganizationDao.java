package com.hb.dao;

import java.util.List;
import java.util.Optional;

import com.hb.model.Organization;

public interface OrganizationDao {
	
	public List<Organization> findAllOrganizations();
	
	public Organization saveOrganization(Organization organization);
	
	public Optional<Organization> findById(Long id);
	
	public Optional<Organization> findByCode(String code);
	
}
	