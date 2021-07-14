package com.hb.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hb.model.Organization;
import com.hb.dao.OrganizationDao;
import com.hb.repository.OrganizationRepository;

@Repository
public class OrganizationImpl implements OrganizationDao, Serializable {

	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Override
	public List<Organization> findAllOrganizations() {
		return organizationRepository.findAll();
	}
	
	@Override
	public Organization saveOrganization(Organization organization) {
		return organizationRepository.save(organization);
	}
	
	@Override
	public Optional<Organization> findById(Long id) {
		return organizationRepository.findById(id);
	}
	
	@Override
	public Optional<Organization> findByCode(String code) {
		return organizationRepository.findByCode(code);
	}
 	
}
