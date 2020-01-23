package com.jeeasy.engine.services.validators;

import java.util.Date;

import javax.inject.Inject;

import com.jeeasy.engine.database.eaos.UserAuthorizationEAO;
import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.database.entities.UserAuthorization;
import com.jeeasy.engine.exceptions.MissingEntityRuntimeException;
import com.jeeasy.engine.exceptions.UnauthorizedRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumMissingEntityExceptionCodes;
import com.jeeasy.engine.exceptions.codes.EnumUnauthorizedExceptionCodes;
import com.jeeasy.engine.settings.UserAuthMaxSessionsSetting;
import com.jeeasy.engine.translations.EnumValidationsTranslations;
import com.jeeasy.engine.utils.data.StringUtils;

public class UserAuthorizationValidator extends AbstractCRUDValidator<UserAuthorization> {
	@Inject
	private UserAuthorizationEAO authEAO;
	
	@Inject
	private UserAuthMaxSessionsSetting userMaxSessions;
	
	@Override
	public void validateInsert(UserAuthorization entity) {
		validateLogin(entity.getUser());
		validateAuthorizationCountForSystemUser(entity.getUser());
	}
	
	public void validateUserCleanup(User user) {
		if (user == null) {
			throw new MissingEntityRuntimeException(
					getTranslator().translateForContextLanguage(EnumValidationsTranslations.USER_NOT_FOUND), 
					EnumMissingEntityExceptionCodes.USER_NOT_FOUND);
		}
	}
	
	public void validateLogin(User user) {
		if (user == null) {
			throw new UnauthorizedRuntimeException(
					getTranslator().translateForContextLanguage(EnumValidationsTranslations.USER_AUTHORIZATION_INVALID_CREDENTIALS),
					EnumUnauthorizedExceptionCodes.INVALID_USER_CREDENTIALS);
		}
	}
	
	public void validateLoginData(String userName, String base64SHA512Password) {
		if (!StringUtils.isNonBlankString(userName) || !StringUtils.isNonBlankString(base64SHA512Password)) {
			throw new UnauthorizedRuntimeException(
					getTranslator().translateForContextLanguage(EnumValidationsTranslations.USER_AUTHORIZATION_INVALID_CREDENTIALS),
					EnumUnauthorizedExceptionCodes.INVALID_USER_CREDENTIALS);
		}
	}
	
	public void validateAuthorizationCountForSystemUser(User user) {
		if (authEAO.countAuthorizationsForUser(user) > userMaxSessions.getValue()) {
			throw new UnauthorizedRuntimeException(
					getTranslator().translateForContextLanguage(EnumValidationsTranslations.USER_AUTHORIZATION_TOKENS_LIMIT_REACHED),
					EnumUnauthorizedExceptionCodes.MAX_SESSIONS_REACHED);
		}
	}
	
	public void validateFoundAuthorizationExpired(UserAuthorization auth) {
		if (auth.isExpired(new Date())) {
			throw new MissingEntityRuntimeException(
					getTranslator().translateForContextLanguage(EnumValidationsTranslations.USER_AUTHORIZATION_EXPIRED),
					EnumMissingEntityExceptionCodes.USER_AUTHORIZATION_EXPIRED);
		}
	}
}
