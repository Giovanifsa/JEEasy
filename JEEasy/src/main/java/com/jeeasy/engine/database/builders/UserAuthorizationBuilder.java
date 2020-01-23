package com.jeeasy.engine.database.builders;

import java.util.Date;

import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.database.entities.UserAuthorization;
import com.jeeasy.engine.utils.data.models.Builder;

public class UserAuthorizationBuilder implements Builder<UserAuthorization> {
	UserAuthorization userAuthorization = new UserAuthorization();

	public UserAuthorizationBuilder setIdUserAuthorization(Long idUserAuthorization) {
		userAuthorization.setIdUserAuthorization(idUserAuthorization);
		return this;
	}

	public UserAuthorizationBuilder setUser(User user) {
		userAuthorization.setUser(user);
		return this;
	}

	public UserAuthorizationBuilder setAuthorization(String authorization) {
		userAuthorization.setAuthorization(authorization);
		return this;
	}

	public UserAuthorizationBuilder setExpirationDate(Date expirationDate) {
		userAuthorization.setExpirationDate(expirationDate);
		return this;
	}

	@Override
	public UserAuthorization build() {
		return userAuthorization;
	}
}
