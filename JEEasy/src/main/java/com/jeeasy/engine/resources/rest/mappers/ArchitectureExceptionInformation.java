package com.jeeasy.engine.resources.rest.mappers;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.jeeasy.engine.exceptions.ArchitectureRuntimeException;

public class ArchitectureExceptionInformation {
	private int exceptionKindCode;
	private int exceptionCode;
	
	private String message;
	private String exceptionClassName;
	private String stackTrace;
	
	public ArchitectureExceptionInformation(ArchitectureRuntimeException ex) {
		exceptionKindCode = ex.getExceptionKindCode();
		exceptionCode = ex.getExceptionCode();
		message = ex.getMessage();
		exceptionClassName = ex.getClass().getName();
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		
		stackTrace = sw.toString();
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getStackTrace() {
		return stackTrace;
	}

	public int getExceptionKindCode() {
		return exceptionKindCode;
	}

	public int getExceptionCode() {
		return exceptionCode;
	}

	public String getExceptionClassName() {
		return exceptionClassName;
	}
}
