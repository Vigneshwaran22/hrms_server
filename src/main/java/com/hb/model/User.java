package com.hb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name= "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Name must be required...")
	private String name;
	
	@Column(unique = true)
	@NotNull(message = "User Name must be required...")
	private String userName;
	
	@Column(unique = true)
	@NotNull(message = "Email must be required...")
	private String email;
	
	@NotNull(message = "Organization must be required...")
	private Long organizationId;
	
	private String password;
	
	private boolean activeStatus = true;
	
	public User() {}

	public User(Long id, @NotNull(message = "Name must be required...") String name,
			@NotNull(message = "User Name must be required...") String userName,
			@NotNull(message = "Email must be required...") String email,
			@NotNull(message = "Organization must be required...") Long organizationId, String password,
			boolean activeStatus) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.organizationId = organizationId;
		this.password = password;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", userName=" + userName + ", email=" + email + ", organizationId="
				+ organizationId + ", password=" + password + ", activeStatus=" + activeStatus + "]";
	}

}
