package com.jeeasy.engine.settings;

import com.jeeasy.engine.translations.EnumSettingsTranslations;

public class UserAuthTokenLengthSetting extends AbstractSetting<Integer>{

	@Override
	public String getVariable() {
		return "user.auth.token_exp";
	}

	@Override
	public String getDescription() {
		return getTranslator().translateForContextLanguage(EnumSettingsTranslations.USER_AUTH_TOKEN_EXP);
	}

	@Override
	public Integer getDefaultValue() {
		return 40;
	}

	@Override
	public Integer getMinimumValue() {
		return 20;
	}

	@Override
	public Integer getMaximumValue() {
		return null;
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
