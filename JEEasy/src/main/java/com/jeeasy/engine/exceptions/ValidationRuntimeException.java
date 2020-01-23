package com.jeeasy.engine.exceptions;

import com.jeeasy.engine.exceptions.codes.EnumExceptionKindCode;
import com.jeeasy.engine.exceptions.codes.EnumValidationExceptionCodes;

public class ValidationRuntimeException extends ArchitectureRuntimeException {
	private static final long serialVersionUID = 1L;

	public ValidationRuntimeException(String message, EnumValidationExceptionCodes exceptionCode) {
		super(message, EnumExceptionKindCode.VALIDATION, exceptionCode);
	}

	public ValidationRuntimeException(String message, int exceptionCode) {
		super(message, EnumExceptionKindCode.VALIDATION.getCode(), exceptionCode);
	}

	public ValidationRuntimeException(String message, Throwable cause, EnumValidationExceptionCodes exceptionCode) {
		super(message, cause, EnumExceptionKindCode.VALIDATION, exceptionCode);
	}

	public ValidationRuntimeException(String message, Throwable cause, int exceptionCode) {
		super(message, cause, EnumExceptionKindCode.VALIDATION.getCode(), exceptionCode);
	}
}
