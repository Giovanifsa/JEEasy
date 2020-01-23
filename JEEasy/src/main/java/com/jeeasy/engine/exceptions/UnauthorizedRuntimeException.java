package com.jeeasy.engine.exceptions;

import com.jeeasy.engine.exceptions.codes.EnumExceptionKindCode;
import com.jeeasy.engine.exceptions.codes.EnumUnauthorizedExceptionCodes;

public class UnauthorizedRuntimeException extends ArchitectureRuntimeException {
	private static final long serialVersionUID = 1L;

	public UnauthorizedRuntimeException(String message, EnumUnauthorizedExceptionCodes exceptionCode) {
		super(message, EnumExceptionKindCode.UNAUTHORIZED, exceptionCode);
	}

	public UnauthorizedRuntimeException(String message, int exceptionCode) {
		super(message, EnumExceptionKindCode.UNAUTHORIZED.getCode(), exceptionCode);
	}

	public UnauthorizedRuntimeException(String message, Throwable cause, EnumUnauthorizedExceptionCodes exceptionCode) {
		super(message, cause, EnumExceptionKindCode.UNAUTHORIZED, exceptionCode);
	}

	public UnauthorizedRuntimeException(String message, Throwable cause, int exceptionCode) {
		super(message, cause, EnumExceptionKindCode.UNAUTHORIZED.getCode(), exceptionCode);
	}
}
