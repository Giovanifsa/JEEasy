package com.jeeasy.engine.database.builders;

import java.util.List;

import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.database.entities.UserRole;
import com.jeeasy.engine.utils.data.ListUtils;
import com.jeeasy.engine.utils.data.models.Builder;

public class UserBuilder implements Builder<User> {
	private User building = new User();
	
	public UserBuilder setIdUser(Long idUser) {
		building.setIdUser(idUser);
		return this;
	}

	public UserBuilder setUserName(String userName) {
		building.setUserName(userName);
		return this;
	}

	public UserBuilder setBase64SHA512Password(String base64sha512Password) {
		building.setBase64SHA512Password(base64sha512Password);
		return this;
	}
	
	public UserBuilder setName(String name) {
		building.setName(name);
		return this;
	}
	
	public UserBuilder addUserRole(UserRole role) {
		List<UserRole> roles = ListUtils.newListOnNull(building.getUserRoles());
		roles.add(role);
		
		building.setUserRoles(roles);
		return this;
	}
	
	public UserBuilder setSystemUser(Boolean systemUser) {
		building.setSystemUser(systemUser);
		return this;
	}

	@Override
	public User build() {
		User builded = building;
		building = new User();
		
		return builded;
	}
}
