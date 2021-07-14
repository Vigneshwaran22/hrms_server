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

import com.hb.dao.OrganizationDao;
import com.hb.model.Organization;
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

	@Override
	public void filterOrganizations(String name, Pageable paging, Map<String, Object> response) {
		
		List<Organization> organizations = new ArrayList<Organization>();
		Page<Organization> pageTuts;
		if (name == null)
			pageTuts = organizationRepository.findAll(paging);
		else
			pageTuts = organizationRepository.findByNameContaining(name, paging);

		organizations = pageTuts.getContent();
		
		response.put("organizations", organizations);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		
	}
 	
}
