package com.jeeasy.engine.resources.dtos;

import java.util.Date;

import com.jeeasy.engine.database.builders.UserAuthorizationBuilder;
import com.jeeasy.engine.database.entities.UserAuthorization;

public class UserAuthorizationDTO extends AbstractDTO<UserAuthorization> {
	private Long idUserAuthorization;
	private UserDTO user;
	private String authorization;
	private Date expirationDate;
	
	@Override
	public void readFromEntity(UserAuthorization entity) {
		if (entity != null) {
			idUserAuthorization = entity.getIdUserAuthorization();
			
			user = new UserDTO();
			user.readFromEntity(entity.getUser());
			
			authorization = entity.getAuthorization();
			expirationDate = entity.getExpirationDate();
		}
	}

	@Override
	public UserAuthorization convertDTOtoEntity() {
		return new UserAuthorizationBuilder()
				.setIdUserAuthorization(idUserAuthorization)
				.setUser(user != null ? user.convertDTOtoEntity() : null)
				.setExpirationDate(expirationDate)
				.setAuthorization(authorization)
				.build();
	}

	@Override
	public Long getId() {
		return idUserAuthorization;
	}

	public Long getIdUserAuthorization() {
		return idUserAuthorization;
	}

	public void setIdUserAuthorization(Long idUserAuthorization) {
		this.idUserAuthorization = idUserAuthorization;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}
