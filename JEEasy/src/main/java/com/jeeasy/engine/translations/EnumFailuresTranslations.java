package com.jeeasy.engine.translations;

public enum EnumFailuresTranslations implements IEnumTranslations {
	FAILED_TO_ENFORCE_UPDATEABLE_FIELD("SERVICE_FAILED_TO_ENFORCE_UPDATEABLE_FIELD", "Failed to enforce updateable field(s) for {0}."),
	FAILED_TO_HASH_DATA("FAILED_TO_HASH_DATA", "Failed to generate hash using {0} as the algorithm."),
	FAILED_TO_GENERATE_USER_AUTH_TOKEN("FAILED_TO_GENERATE_USER_AUTH_TOKEN", "Failed to generate user's authorization token after {0} tries."),
	FAILED_TO_FIND_SYSTEM_USER("FAILED_TO_FIND_SYSTEM_USER", "System user not found. Cannot operate."),
	FAILED_TO_READ_SETTINGS_DATA("FAILED_TO_READ_SETTINGS_DATA", "Failed to read settings file data ({0})."),
	FAILED_TO_APPLY_MIGRATION("FAILED_TO_APPLY_MIGRATION", "Failed to migrate database: {0}"),
	FAILED_TO_APPLY_MIGRATION_DATASOURCE_NOT_FOUND("FAILED_TO_APPLY_MIGRATION_DATASOURCE_NOT_FOUND", "No DataSource found to run the database migration."),
	;
	
	private String translationUnit;
	private String englishTranslation;

	private EnumFailuresTranslations(String translationUnit, String englishTranslation) {
		this.translationUnit = translationUnit;
		this.englishTranslation = englishTranslation;
	}
	
	@Override
	public String getDefaultTranslation() {
		return englishTranslation;
	}

	@Override
	public String getTranslationUnit() {
		return "#FAILURE_" + translationUnit;
	}
}
