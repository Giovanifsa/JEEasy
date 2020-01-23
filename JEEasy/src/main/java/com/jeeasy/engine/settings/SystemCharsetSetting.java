package com.jeeasy.engine.settings;

import java.nio.charset.Charset;

import com.jeeasy.engine.translations.EnumSettingsTranslations;

public class SystemCharsetSetting extends AbstractSetting<String> {
	private String value;

	@Override
	public String getVariable() {
		return "system.charset";
	}

	@Override
	public String getDescription() {
		return getTranslator().translateForContextLanguage(EnumSettingsTranslations.SYSTEM_CHARSET);
	}

	@Override
	public String getDefaultValue() {
		return "UTF-8";
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
		Charset.forName(newValue);
		
		String oldValue = value;
		value = newValue;
		
		return oldValue;
	}

	public Charset getCharset() {
		if (value != null) {
			return Charset.forName(value);
		}
		
		return Charset.forName(getDefaultValue());
	}
}
