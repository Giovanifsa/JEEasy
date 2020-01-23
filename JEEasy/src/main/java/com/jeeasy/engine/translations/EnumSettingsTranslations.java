package com.jeeasy.engine.translations;

public enum EnumSettingsTranslations implements IEnumTranslations {
	USER_AUTH_TOKEN_RETRIES_DESCRIPTION("USER_AUTH_TOKEN_RETRIES_DESCRIPTION", "Maximum tries to generate a non-conflicting user authorization token."),
	USER_SYSTEMUSER_DEFAULT_PASSWORD("USER_SYSTEMUSER_DEFAULT_PASSWORD", "Default first password for the SYSTEM user."),
	SYSTEM_CHARSET("SYSTEM_CHARSET", "Default Charset used by the application."),
	USER_AUTH_TOKEN_EXP("USER_AUTH_TOKEN_EXP", "User's relative expiration date in miliseconds (today + this setting value = token expiration date)."),
	USER_AUTH_MAX_SESSIONS("USER_AUTH_MAX_SESSIONS", "Maximum sessions allowed for each user."),
	USER_PASSWORD_MIN_LENGTH("USER_PASSWORD_MIN_LENGTH", "Minimum user password length allowed."),
	;
	
	private String translationUnit;
	private String englishTranslation;

	private EnumSettingsTranslations(String translationUnit, String englishTranslation) {
		this.translationUnit = translationUnit;
		this.englishTranslation = englishTranslation;
	}

	@Override
	public String getTranslationUnit() {
		return "#SETTING_" + translationUnit;
	}

	@Override
	public String getDefaultTranslation() {
		return englishTranslation;
	}
}
