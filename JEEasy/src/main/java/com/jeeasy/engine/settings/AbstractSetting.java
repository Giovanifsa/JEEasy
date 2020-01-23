package com.jeeasy.engine.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.jeeasy.engine.translations.IEnumTranslations;
import com.jeeasy.engine.translations.Translator;

@Singleton
public abstract class AbstractSetting<T> {
	@Inject
	private Translator translator;
	
	protected T value;
	
	public abstract String getVariable();
	public abstract String getDescription();
	
	public abstract T getDefaultValue();
	public abstract T getMinimumValue();
	public abstract T getMaximumValue();
	protected abstract String _setValue(String newValue);
	
	public String setValue(String newValue) {
		try {
			return _setValue(newValue);
		} catch (Throwable ex) {
			value = getDefaultValue();
			throw ex;
		}
	}
	
	public T getValue() {
		return (value != null ? value : getDefaultValue());
	}
	
	public Translator getTranslator() {
		return translator;
	}
	
	public T getValueOrDefault() {
		if (getValue() == null) {
			return getDefaultValue();
		}
		
		return getValue();
	}
	
	protected String translateDescription(IEnumTranslations translationEnum, Object... params) {
		return getTranslator().translateForContextLanguage(translationEnum, params);
	}
}
