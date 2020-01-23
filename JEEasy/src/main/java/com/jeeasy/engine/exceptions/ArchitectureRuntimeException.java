package com.jeeasy.engine.exceptions;

import com.jeeasy.engine.exceptions.codes.IEnumExceptionCode;

public abstract class ArchitectureRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private int exceptionKindCode;
	private int exceptionCode;

	public ArchitectureRuntimeException(String message, Throwable cause, IEnumExceptionCode exceptionKindCode, IEnumExceptionCode exceptionCode) {
		super(message, cause);
		this.exceptionKindCode = exceptionKindCode.getCode();
		this.exceptionCode = exceptionCode.getCode();
	}
	
	public ArchitectureRuntimeException(String message, Throwable cause, int exceptionKindCode, int exceptionCode) {
		super(message, cause);
		this.exceptionKindCode = exceptionKindCode;
		this.exceptionCode = exceptionCode;
	}

	public ArchitectureRuntimeException(String message, IEnumExceptionCode exceptionKindCode, IEnumExceptionCode exceptionCode) {
		super(message);
		this.exceptionKindCode = exceptionKindCode.getCode();
		this.exceptionCode = exceptionCode.getCode();
	}
	
	public ArchitectureRuntimeException(String message, int exceptionKindCode, int exceptionCode) {
		super(message);
		this.exceptionKindCode = exceptionKindCode;
		this.exceptionCode = exceptionCode;
	}

	public int getExceptionKindCode() {
		return exceptionKindCode;
	}

	public int getExceptionCode() {
		return exceptionCode;
	}
}
