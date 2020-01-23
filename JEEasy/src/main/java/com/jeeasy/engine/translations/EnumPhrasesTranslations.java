package com.jeeasy.engine.translations;

public enum EnumPhrasesTranslations implements IEnumTranslations {
	USER_USERNAME("USER_USERNAME", "User name"),
	USER_NAME("USER_NAME", "User alias"),
	USER_PASSWORD("USER_PASSWORD", "User password"),
	;
	
	private String translationUnit;
	private String englishTranslation;

	private EnumPhrasesTranslations(String translationUnit, String englishTranslation) {
		this.translationUnit = translationUnit;
		this.englishTranslation = englishTranslation;
	}

	@Override
	public String getTranslationUnit() {
		return "#PHRASE_" + translationUnit;
	}

	@Override
	public String getDefaultTranslation() {
		return englishTranslation;
	}
}
