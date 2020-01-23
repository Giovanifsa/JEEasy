package com.jeeasy.engine.translations;

public enum EnumValidationsTranslations implements IEnumTranslations {
	LOGIN_REQUIRED("LOGIN_REQUIRED", "You need to sign-in to use this service."),
	
	MISSING_REQUIRED_FIELD("MISSING_REQUIRED_FIELD", "The field {0} is obligatory."),
	FIELD_MISSING_LENGTH("FIELD_MISSING_LENGTH", "The field \"{0}\" requires the minimum size of {1} characters."),
	FIELD_MAXIMUM_LENGTH("FIELD_MAXIMUM_LENGTH", "The field \"{0}\" shouldn't be bigger than {1} characters."),
	
	USER_NOT_FOUND("USER_NOT_FOUND", "User not found."),
	USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "The User {0} already exists."),
	
	USER_AUTHORIZATION_INVALID_CREDENTIALS("USER_AUTHORIZATION_INVALID_CREDENTIALS", "Provided credentials is invalid."),
	USER_AUTHORIZATION_TOKENS_LIMIT_REACHED("MAX_USER_AUTH_TOKENS_LIMITE_REACHED", "Maximum user login limit reached."),
	USER_AUTHORIZATION_EXPIRED("USER_AUTH_EXPIRED", "The authorization token has expired."),
	USER_AUTHORIZATION_NOT_FOUND("USER_AUTH_NOT_FOUND", "Provided authorization token not found."),
	;
	
	private String translationUnit;
	private String englishTranslation;

	private EnumValidationsTranslations(String translationUnit, String englishTranslation) {
		this.translationUnit = translationUnit;
		this.englishTranslation = englishTranslation;
	}
	
	@Override
	public String getDefaultTranslation() {
		return englishTranslation;
	}

	@Override
	public String getTranslationUnit() {
		return "#VALIDATION_" + translationUnit;
	}

}
