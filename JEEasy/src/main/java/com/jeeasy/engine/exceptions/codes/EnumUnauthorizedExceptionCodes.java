package com.jeeasy.engine.exceptions.codes;

public enum EnumUnauthorizedExceptionCodes implements IEnumExceptionCode {
	LOGIN_REQUIRED(0),
	INVALID_USER_CREDENTIALS(1),
	MAX_SESSIONS_REACHED(2),
	;

	private int code;
	
	private EnumUnauthorizedExceptionCodes(int code) {
		this.code = code;
	}
	
	@Override
	public int getCode() {
		return code;
	}
}
