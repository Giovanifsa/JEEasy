 package com.jeeasy.engine.translations;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.jeeasy.engine.context.ArchitectureContext;

@Singleton
public class Translator {
	private HashMap<IEnumTranslations, HashMap<Locale, ArrayList<Translation>>> translationList = new HashMap<>();
	
	@Inject
	private ArchitectureContext context;
	
	public String translateForContextLanguage(IEnumTranslations translation, Object... params) {
		return translateForLanguage(translation, context.getContextLanguage(), params);
	}
	
	public String translateForLanguage(IEnumTranslations translation, Locale language, Object... params) {
		String translationText = findTranslationForLanguage(translation, language);
		
		return MessageFormat.format(translationText, params);
	}
	
	public String findTranslationForLanguage(IEnumTranslations translation, Locale language) {
		if (translationList.containsKey(translation)) {
			HashMap<Locale, ArrayList<Translation>> hashMap = translationList.get(translation);
			
			for (Entry<Locale, ArrayList<Translation>> entry : hashMap.entrySet()) {
				if (entry.getKey().equals(language)) {
					for (Translation trsl : entry.getValue()) {
						if (trsl.getTranslationEnum() == translation) {
							return trsl.getTranslatedText();
						}
					}
				}
			}
		}
		
		return translation.getDefaultTranslation();
	}
}