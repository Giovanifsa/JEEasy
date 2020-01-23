package com.jeeasy.engine.database.entities.constants;

import java.util.ArrayList;
import java.util.List;

import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.database.entities.UserRolePermission;
import com.jeeasy.engine.utils.data.ListUtils;
import com.jeeasy.engine.utils.data.models.IOInterface;

public class UserRolePermissionConstants implements IEntityConstants {
	public static final UserRolePermission CREATE_USERS = new UserRolePermission(1L, "Create Users");
	
	@Override
	public List<AbstractEntity> getConstantEntities() {
		ArrayList<AbstractEntity> constants = new ArrayList<>();
		
		constants.add(CREATE_USERS);
		
		return constants;
	}
	
	public List<UserRolePermission> getTypedConstants() {
		return ListUtils.convertList(getConstantEntities(), new IOInterface<AbstractEntity, UserRolePermission>() {
			@Override
			public UserRolePermission output(AbstractEntity input) {
				return (UserRolePermission) input;
			}
		});
	}
}
