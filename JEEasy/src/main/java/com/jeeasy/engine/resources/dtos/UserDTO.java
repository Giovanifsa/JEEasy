package com.jeeasy.engine.resources.dtos;

import com.jeeasy.engine.database.builders.UserBuilder;
import com.jeeasy.engine.database.entities.User;

public class UserDTO extends AbstractDTO<User> {
	private Long idUser;
	private String userName;
	private String name;
	private Boolean systemUser;
	
	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(Boolean systemUser) {
		this.systemUser = systemUser;
	}

	@Override
	public void readFromEntity(User entity) {
		if (entity != null) {
			setIdUser(entity.getIdUser());
			setUserName(entity.getUserName());
			setName(entity.getName());
			setSystemUser(entity.getSystemUser());
		}
	}

	@Override
	public User convertDTOtoEntity() {
		return new UserBuilder()
				.setUserName(getUserName())
				.setIdUser(getIdUser())
				.setName(getName())
				.setSystemUser(getSystemUser())
				.build();
	}

	@Override
	public Long getId() {
		return getIdUser();
	}
}
