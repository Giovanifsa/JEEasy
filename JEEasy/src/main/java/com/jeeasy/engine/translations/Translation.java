package com.jeeasy.engine.translations;

import com.jeeasy.engine.utils.data.StringUtils;

public class Translation {
	private IEnumTranslations translationEnum;
	private String translatedText;
	
	public Translation(IEnumTranslations translationEnum) {
		
	}
	
	public Translation(IEnumTranslations translationEnum, String translatedText) {
		
	}
	
	public String getTranslatedText() {
		if (StringUtils.isNonBlankString(translatedText)) {
			return translatedText;
		}
		
		return translationEnum.getDefaultTranslation();
	}
	
	public IEnumTranslations getTranslationEnum() {
		return translationEnum;
	}
	
	public String getTranslationUnit() {
		return translationEnum.getTranslationUnit();
	}
}
