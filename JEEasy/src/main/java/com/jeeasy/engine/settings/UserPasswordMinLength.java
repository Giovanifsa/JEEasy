package com.jeeasy.engine.settings;

import com.jeeasy.engine.translations.EnumSettingsTranslations;

public class UserPasswordMinLength extends AbstractSetting<Integer> {

	@Override
	public String getVariable() {
		return "user.password.min_length";
	}

	@Override
	public String getDescription() {
		return getTranslator().translateForContextLanguage(EnumSettingsTranslations.USER_PASSWORD_MIN_LENGTH);
	}

	@Override
	public Integer getDefaultValue() {
		return 12;
	}

	@Override
	public Integer getMinimumValue() {
		return 4;
	}

	@Override
	public Integer getMaximumValue() {
		return 64;
	}

	@Override
	protected String _setValue(String newValue) {
		Integer newIntValue = Integer.valueOf(newValue);
		String oldValue = String.valueOf(value);
		
		if (newIntValue.compareTo(getMinimumValue()) < 0) {
			value = getMinimumValue();
		}
		
		else if (newIntValue.compareTo(getMaximumValue()) > 0) {
			value = getMaximumValue();
		}
		
		else {
			value = newIntValue;
		}
		
		return oldValue;
	}

}
