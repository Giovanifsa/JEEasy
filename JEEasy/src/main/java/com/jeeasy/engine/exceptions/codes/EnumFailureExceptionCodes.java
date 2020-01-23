package com.jeeasy.engine.exceptions.codes;

public enum EnumFailureExceptionCodes implements IEnumExceptionCode {
	FAILED_TO_GENERATE_UNIQUE_DATABASE_DATA(0),
	FAILED_TO_VALIDATE_DATA(1),
	FAILED_TO_RUN_HASH_ALGORITHM(2),
	FAILED_TO_FIND_SYSTEM_USER(3),
	FAILED_TO_READ_SETTINGS_FILE(4),
	FAILED_TO_APPLY_MIGRATION(5),
	INFINITE_DEPENDENCY_TREE(6),
	;

	private int code;
	
	private EnumFailureExceptionCodes(int code) {
		this.code = code;
	}
	
	@Override
	public int getCode() {
		return code;
	}
}
