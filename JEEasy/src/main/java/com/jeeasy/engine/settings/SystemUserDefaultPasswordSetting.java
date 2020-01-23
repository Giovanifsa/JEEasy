package com.jeeasy.engine.settings;

import com.jeeasy.engine.translations.EnumSettingsTranslations;

public class SystemUserDefaultPasswordSetting extends AbstractSetting<String> {
	private String value;
	
	@Override
	public String getVariable() {
		return "user.systemuser_default_password";
	}

	@Override
	public String getDescription() {
		return translateDescription(EnumSettingsTranslations.USER_SYSTEMUSER_DEFAULT_PASSWORD);
	}

	@Override
	public String getDefaultValue() {
		return "root";
	}

	@Override
	public String getMinimumValue() {
		return null;
	}

	@Override
	public String getMaximumValue() {
		return null;
	}

	@Override
	protected String _setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		
		return oldValue;
	}
}
