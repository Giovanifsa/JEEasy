package com.jeeasy.engine.database.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class UserRolePermission extends AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUserRolePermission;
	
	@Column(name = "permissionName", nullable = false, length = 64)
	private String permissionName;
	
	@ManyToMany(mappedBy = "userRolePermissions")
	private List<UserRole> userRolesThatHaveThisPermission;
	
	public UserRolePermission() {
		
	}
	
	public UserRolePermission(Long idUserRolePermission, String permissionName) {
		this.idUserRolePermission = idUserRolePermission;
		this.permissionName = permissionName;
	}

	public Long getIdUserRolePermission() {
		return idUserRolePermission;
	}

	public void setIdUserRolePermission(Long idUserRolePermission) {
		this.idUserRolePermission = idUserRolePermission;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	
	public List<UserRole> getUserRolesThatHaveThisPermission() {
		return userRolesThatHaveThisPermission;
	}

	public void setUserRolesThatHaveThisPermission(List<UserRole> userRolesThatHaveThisPermission) {
		this.userRolesThatHaveThisPermission = userRolesThatHaveThisPermission;
	}

	@Override
	public Long getId() {
		return getIdUserRolePermission();
	}
}
