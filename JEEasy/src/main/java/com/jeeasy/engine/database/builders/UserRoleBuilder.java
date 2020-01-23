package com.jeeasy.engine.database.builders;

import java.util.List;

import com.jeeasy.engine.database.entities.UserRole;
import com.jeeasy.engine.database.entities.UserRolePermission;
import com.jeeasy.engine.utils.data.ListUtils;
import com.jeeasy.engine.utils.data.models.Builder;

public class UserRoleBuilder implements Builder<UserRole> {
	private UserRole building = new UserRole();
	
	public UserRoleBuilder setIdUserRole(Long idUserRole) {
		building.setIdUserRole(idUserRole);
		return this;
	}

	public UserRoleBuilder setRoleName(String roleName) {
		building.setRoleName(roleName);
		return this;
	}

	public UserRoleBuilder setSystemRole(Boolean systemRole) {
		building.setSystemRole(systemRole);
		return this;
	}
	
	public UserRoleBuilder addUserRolePermission(UserRolePermission userRolePermission) {
		List<UserRolePermission> permissions = ListUtils.newListOnNull(building.getUserRolePermissions());
		permissions.add(userRolePermission);
		
		building.setUserRolePermissions(permissions);
		return this;
	}
	
	public UserRoleBuilder setUserRolePermissions(List<UserRolePermission> userRolePermissions) {
		building.setUserRolePermissions(userRolePermissions);
		return this;
	}

	@Override
	public UserRole build() {
		return building;
	}
}
