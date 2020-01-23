package com.jeeasy.engine.services.validators;

import javax.inject.Inject;

import com.jeeasy.engine.context.ArchitectureContext;
import com.jeeasy.engine.database.entities.AbstractEntity;
import com.jeeasy.engine.exceptions.UnauthorizedRuntimeException;
import com.jeeasy.engine.exceptions.ValidationRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumUnauthorizedExceptionCodes;
import com.jeeasy.engine.exceptions.codes.EnumValidationExceptionCodes;
import com.jeeasy.engine.queries.QuerySettings;
import com.jeeasy.engine.translations.EnumValidationsTranslations;
import com.jeeasy.engine.translations.Translator;
import com.jeeasy.engine.utils.data.StringUtils;

public abstract class AbstractCRUDValidator<Entity extends AbstractEntity> {
	@Inject
	private Translator translator;
	
	@Inject
	private ArchitectureContext context;
	
	public void validateRequiredFields(Entity entity) {};
	
	public void checkField(Object value, String fieldName) {
		if (value == null || (value instanceof String && StringUtils.isNonBlankString(((String) value)))) {
			throw new ValidationRuntimeException(
					translator.translateForContextLanguage(EnumValidationsTranslations.MISSING_REQUIRED_FIELD, fieldName),
					EnumValidationExceptionCodes.MISSING_REQUIRED_FIELD);
		}
	}
	
	public void checkUserLogin() {
		if (!context.hasUser()) {
			throw new UnauthorizedRuntimeException(
					translator.translateForContextLanguage(EnumValidationsTranslations.LOGIN_REQUIRED),
					EnumUnauthorizedExceptionCodes.LOGIN_REQUIRED);
		}
	}
	
	public void validateInsert(Entity entity) {
		checkUserLogin();
	}
	
	public void validateUpdate(Entity entity) {
		checkUserLogin();
	}
	
	public void validateDelete(Entity entity) {
		checkUserLogin();
	}
	
	public void validateFind(Long id) {
		checkUserLogin();
	}
	
	public void validatePagedSearch(QuerySettings querySettings) {
		checkUserLogin();
	}
	
	public Translator getTranslator() {
		return translator;
	}
	
	public ArchitectureContext getContext() {
		return context;
	}
}
