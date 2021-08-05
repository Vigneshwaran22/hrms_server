package com.hb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name= "organizations")
public class Organization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Name must be required...")
	private String name;
	
	@Column(unique = true)
	@NotNull(message = "Code must be required...")
	private String code;
	
	private boolean activeStatus = true;
	
	public Organization() {	}
	
	public Organization(Long id, String name, String code, boolean activeStatus) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.activeStatus = activeStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public String toString() {
		return "Organization [name=" + name + "]";
	}
	
}
