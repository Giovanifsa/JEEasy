package com.jeeasy.engine.services.validators;

import javax.inject.Inject;

import com.jeeasy.engine.database.eaos.UserEAO;
import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.exceptions.ValidationRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumValidationExceptionCodes;
import com.jeeasy.engine.settings.UserPasswordMinLength;
import com.jeeasy.engine.translations.EnumPhrasesTranslations;
import com.jeeasy.engine.translations.EnumValidationsTranslations;
import com.jeeasy.engine.utils.data.BooleanUtils;

public class UserValidator extends AbstractCRUDValidator<User> {
	@Inject
	private UserEAO eao;
	
	@Inject
	private UserPasswordMinLength passwordMinLengthSetting;
	
	@Override
	public void validateRequiredFields(User entity) {
		checkField(entity.getUserName(), getTranslator().translateForContextLanguage(EnumPhrasesTranslations.USER_USERNAME));
		checkField(entity.getName(), getTranslator().translateForContextLanguage(EnumPhrasesTranslations.USER_NAME));
		checkField(entity.getBase64SHA512Password(), getTranslator().translateForContextLanguage(EnumPhrasesTranslations.USER_PASSWORD));
	}

	@Override
	public void validateInsert(User entity) {
		checkConflictingUsers(entity);
		checkPasswordLength(entity);
	}
	
	@Override
	public void validateUpdate(User entity) {
		checkConflictingUsers(entity);
		checkPasswordLength(entity);
	}
	
	@Override
	public void validateDelete(User entity) {
		checkSystemUser(entity);
	}
	
	public void checkConflictingUsers(User entity) {
		if (eao.findByUserName(entity.getUserName()) != null) {
			throw new ValidationRuntimeException(
					getTranslator().translateForContextLanguage(EnumValidationsTranslations.USER_ALREADY_EXISTS, entity.getUserName()), 
					EnumValidationExceptionCodes.USER_ALREADY_EXISTS);
		}
	}
	
	public void checkPasswordLength(User entity) {
		if (passwordMinLengthSetting.getValue() > entity.getBase64SHA512Password().length()) {
			throw new ValidationRuntimeException(
					getTranslator().translateForContextLanguage(EnumValidationsTranslations.FIELD_MISSING_LENGTH, 
						getTranslator().translateForContextLanguage(EnumPhrasesTranslations.USER_PASSWORD),
						passwordMinLengthSetting.getValue()), 
					EnumValidationExceptionCodes.USER_PASSWORD_LENGTH_TOO_SMALL);
		}
		
		else if (passwordMinLengthSetting.getValue() > passwordMinLengthSetting.getMaximumValue()) {
			throw new ValidationRuntimeException(
					getTranslator().translateForContextLanguage(EnumValidationsTranslations.FIELD_MAXIMUM_LENGTH, 
						getTranslator().translateForContextLanguage(EnumPhrasesTranslations.USER_PASSWORD),
						passwordMinLengthSetting.getValue()), 
					EnumValidationExceptionCodes.USER_PASSWORD_LENGTH_TOO_BIG);
		}
	}
	
	public void checkSystemUser(User entity) {
		if (BooleanUtils.isTrue(entity.getSystemUser())) {
			throw new ValidationRuntimeException(
					getTranslator().translateForContextLanguage(EnumValidationsTranslations.CANNOT_DELETE_SYSTEM_USER, entity.getUserName()), 
					EnumValidationExceptionCodes.CANNOT_DELETE_SYSTEM_USER);
		}
	}
}
