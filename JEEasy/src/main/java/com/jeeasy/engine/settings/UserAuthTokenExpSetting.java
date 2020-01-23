package com.jeeasy.engine.settings;

import com.jeeasy.engine.translations.EnumSettingsTranslations;

public class UserAuthTokenExpSetting extends AbstractSetting<Long> {
	@Override
	public String getVariable() {
		return "user.auth.token_exp";
	}

	@Override
	public String getDescription() {
		return getTranslator().translateForContextLanguage(EnumSettingsTranslations.USER_AUTH_TOKEN_EXP);
	}

	@Override
	public Long getDefaultValue() {
		return 1296000L;
	}

	@Override
	public Long getMinimumValue() {
		return 300L;
	}

	@Override
	public Long getMaximumValue() {
		return null;
	}

	@Override
	protected String _setValue(String newValue) {
		Long newLongValue = Long.valueOf(newValue);
		String oldValue = String.valueOf(value);
		
		if (newLongValue.compareTo(getMinimumValue()) < 0) {
			value = getMinimumValue();
		}
		
		else if (newLongValue.compareTo(getMaximumValue()) > 0) {
			value = getMaximumValue();
		}
		
		else {
			value = newLongValue;
		}
		
		return oldValue;
	}

}
