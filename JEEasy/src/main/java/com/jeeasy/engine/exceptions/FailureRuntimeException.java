package com.jeeasy.engine.exceptions;

import com.jeeasy.engine.exceptions.codes.EnumExceptionKindCode;
import com.jeeasy.engine.exceptions.codes.EnumFailureExceptionCodes;

public class FailureRuntimeException extends ArchitectureRuntimeException {
	private static final long serialVersionUID = 1L;

	public FailureRuntimeException(String message, Throwable cause, EnumFailureExceptionCodes exceptionCode) {
		super(message, cause, EnumExceptionKindCode.FAILURE, exceptionCode);
	}

	public FailureRuntimeException(String message, EnumFailureExceptionCodes exceptionCode) {
		super(message, EnumExceptionKindCode.FAILURE, exceptionCode);
	}
	
	public FailureRuntimeException(String message, Throwable cause, int exceptionCode) {
		super(message, cause, EnumExceptionKindCode.FAILURE.getCode(), exceptionCode);
	}

	public FailureRuntimeException(String message, int exceptionCode) {
		super(message, EnumExceptionKindCode.FAILURE.getCode(), exceptionCode);
	}
}
