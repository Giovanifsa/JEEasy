package com.jeeasy.engine.exceptions.codes;

public enum EnumValidationExceptionCodes implements IEnumExceptionCode {
	MISSING_REQUIRED_FIELD(0),
	
	USER_ALREADY_EXISTS(1),
	USER_PASSWORD_LENGTH_TOO_SMALL(2),
	USER_PASSWORD_LENGTH_TOO_BIG(3),
	CANNOT_DELETE_SYSTEM_USER(4),
	
	;

	private int code;
	
	private EnumValidationExceptionCodes(int code) {
		this.code = code;
	}
	
	@Override
	public int getCode() {
		return code;
	}
}
