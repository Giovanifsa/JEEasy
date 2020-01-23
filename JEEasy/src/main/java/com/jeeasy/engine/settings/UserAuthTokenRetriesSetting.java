package com.jeeasy.engine.settings;

import com.jeeasy.engine.translations.EnumSettingsTranslations;

public class UserAuthTokenRetriesSetting extends AbstractSetting<Integer> {
	private Integer value = null;

	@Override
	public String getVariable() {
		return "user.auth.token_retries";
	}

	@Override
	public String getDescription() {
		return translateDescription(EnumSettingsTranslations.USER_AUTH_TOKEN_RETRIES_DESCRIPTION);
	}

	@Override
	public Integer getDefaultValue() {
		return 5;
	}

	@Override
	public Integer getMinimumValue() {
		return 1;
	}

	@Override
	public Integer getMaximumValue() {
		return 100;
	}

	@Override
	protected String _setValue(String newValue) {
		Integer intNewValue = Integer.valueOf(newValue);
		Integer oldValue = value;
		
		if (intNewValue != null) {
			if (intNewValue.compareTo(getMinimumValue()) < 0) {
				value = getMinimumValue();
			}
			
			else if (intNewValue.compareTo(getMaximumValue()) > 0) {
				value = getMaximumValue();
			}
		}
		
		else {
			value = intNewValue;
		}
		
		return String.valueOf(oldValue);
	}
}
