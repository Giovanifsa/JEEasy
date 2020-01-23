package com.jeeasy.engine.database.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class UserRole extends AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUserRole", nullable = false, unique = true)
	private Long idUserRole;
	
	@Column(name = "roleName", nullable = false, length = 45)
	private String roleName;
	
	@Column(name = "systemRole")
	private Boolean systemRole;
	
	@ManyToMany(mappedBy = User.ATTRIBUTE_USERROLES)
	private List<User> usersThatHaveThisRole;
	
	@ManyToMany
	@JoinTable(
			name = "UserRoleMMUserRolePermission",
			joinColumns = {
					@JoinColumn(name = "idUserRole")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "idUserRolePermission")
			}
	)
	private List<UserRolePermission> userRolePermissions;
	
	public UserRole() {
		
	}
	
	public UserRole(Long idUserRole, String roleName, Boolean systemRole) {
		setIdUserRole(idUserRole);
		setRoleName(roleName);
		setSystemRole(systemRole);
	}
	
	public Long getIdUserRole() {
		return idUserRole;
	}

	public void setIdUserRole(Long idUserRole) {
		this.idUserRole = idUserRole;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getSystemRole() {
		return systemRole;
	}

	public void setSystemRole(Boolean systemRole) {
		this.systemRole = systemRole;
	}
	
	public List<User> getUsersThatHaveThisRole() {
		return usersThatHaveThisRole;
	}

	public List<UserRolePermission> getUserRolePermissions() {
		return userRolePermissions;
	}

	public void setUserRolePermissions(List<UserRolePermission> userRolePermissions) {
		this.userRolePermissions = userRolePermissions;
	}

	@Override
	public Long getId() {
		return idUserRole;
	}
}
