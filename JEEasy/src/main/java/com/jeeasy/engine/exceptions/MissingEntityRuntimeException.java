package com.jeeasy.engine.exceptions;

import com.jeeasy.engine.exceptions.codes.EnumExceptionKindCode;
import com.jeeasy.engine.exceptions.codes.EnumMissingEntityExceptionCodes;

public class MissingEntityRuntimeException extends ArchitectureRuntimeException {
	private static final long serialVersionUID = 1L;

	public MissingEntityRuntimeException(String message, EnumMissingEntityExceptionCodes exceptionCode) {
		super(message, EnumExceptionKindCode.MISSING_ENTITY, exceptionCode);
	}

	public MissingEntityRuntimeException(String message, int exceptionCode) {
		super(message, EnumExceptionKindCode.MISSING_ENTITY.getCode(), exceptionCode);
	}

	public MissingEntityRuntimeException(String message, Throwable cause, EnumMissingEntityExceptionCodes exceptionCode) {
		super(message, cause, EnumExceptionKindCode.MISSING_ENTITY, exceptionCode);
	}

	public MissingEntityRuntimeException(String message, Throwable cause, int exceptionCode) {
		super(message, cause, EnumExceptionKindCode.MISSING_ENTITY.getCode(), exceptionCode);
	}
}
