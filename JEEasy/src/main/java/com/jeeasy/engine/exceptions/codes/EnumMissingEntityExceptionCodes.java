package com.jeeasy.engine.exceptions.codes;

public enum EnumMissingEntityExceptionCodes implements IEnumExceptionCode {
	ENTITY_NOT_FOUND(0),
	USER_NOT_FOUND(1),
	USER_AUTHORIZATION_NOT_FOUND(2),
	USER_AUTHORIZATION_EXPIRED(3),
	;

	private int code;
	
	private EnumMissingEntityExceptionCodes(int code) {
		this.code = code;
	}
	
	@Override
	public int getCode() {
		return code;
	}

}
