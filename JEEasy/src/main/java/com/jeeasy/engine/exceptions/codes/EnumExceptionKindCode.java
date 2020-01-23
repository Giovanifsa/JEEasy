package com.jeeasy.engine.exceptions.codes;

public enum EnumExceptionKindCode implements IEnumExceptionCode {
	//HTTP Status codes
	FAILURE(500),
	MISSING_ENTITY(410),
	UNAUTHORIZED(401),
	VALIDATION(400),
	;
	
	private int code;
	
	private EnumExceptionKindCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
