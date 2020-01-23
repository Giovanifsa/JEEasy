package com.jeeasy.engine.database.entities.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jeeasy.engine.database.builders.UserRoleBuilder;
import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.database.entities.UserRole;
import com.jeeasy.engine.utils.dependencies.IDependencyBean;

public class UserRoleConstants implements IEntityConstants {
	public static final UserRole ROOT = 
			new UserRoleBuilder()
			.setIdUserRole(1L)
			.setRoleName("Root")
			.setSystemRole(true)
			.setUserRolePermissions(new UserRolePermissionConstants().getTypedConstants())
			.build();
	
	@Override
	public List<AbstractEntity> getConstantEntities() {
		ArrayList<AbstractEntity> constants = new ArrayList<>();
		
		constants.add(ROOT);
		
		return constants;
	}
	
	@Override
	public List<Class<? extends IDependencyBean>> getDependencies() {
		return Arrays.asList(UserRolePermissionConstants.class);
	}
}
