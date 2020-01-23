package com.jeeasy.engine.resources.rest.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeeasy.engine.exceptions.ArchitectureRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumExceptionKindCode;

@Provider
public class ArchitectureRuntimeExceptionMapper implements ExceptionMapper<ArchitectureRuntimeException> {
	private Logger logger = LogManager.getLogger(ArchitectureRuntimeExceptionMapper.class);
	
	@Override
	public Response toResponse(ArchitectureRuntimeException exception) {
		ArchitectureExceptionInformation information = new ArchitectureExceptionInformation(exception);
		
		if (information.getExceptionCode() == EnumExceptionKindCode.FAILURE.getCode()) {
			logger.error("Runtime failure reported:\n{}\nException Kind:{}\nException Code:{}\nMessage:{}\nException ClassName:{}\nStack Trace:\n{}", 
					information.getExceptionKindCode(), information.getExceptionCode(), information.getMessage(), information.getExceptionClassName(), information.getStackTrace());
		}
		
		return Response
				.status(information.getExceptionKindCode())
				.entity(information)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}
