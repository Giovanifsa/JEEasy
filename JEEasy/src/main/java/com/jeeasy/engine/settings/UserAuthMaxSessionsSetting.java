package com.jeeasy.engine.settings;

import com.jeeasy.engine.translations.EnumSettingsTranslations;

public class UserAuthMaxSessionsSetting extends AbstractSetting<Integer> {
	@Override
	public String getVariable() {
		return "user.auth.max_sessions";
	}

	@Override
	public String getDescription() {
		return translateDescription(EnumSettingsTranslations.USER_AUTH_MAX_SESSIONS);
	}

	@Override
	public Integer getDefaultValue() {
		return 25;
	}

	@Override
	public Integer getMinimumValue() {
		return 1;
	}

	@Override
	public Integer getMaximumValue() {
		return null;
	}

	@Override
	protected String _setValue(String newValue) {
		Integer tempValue = Integer.valueOf(newValue);
		String oldValue = String.valueOf(value);
		
		if (tempValue.compareTo(getMinimumValue()) < 0) {
			value = getMinimumValue();
		}
		
		else if (tempValue.compareTo(getMaximumValue()) > 0) {
			value = getMaximumValue();
		}
		
		else {
			value = tempValue;
		}
		
		return oldValue;
	}

}
