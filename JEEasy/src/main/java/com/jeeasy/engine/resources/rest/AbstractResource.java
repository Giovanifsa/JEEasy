package com.jeeasy.engine.resources.rest;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.jeeasy.engine.context.ArchitectureContext;
import com.jeeasy.engine.database.eaos.UserAuthorizationEAO;
import com.jeeasy.engine.translations.Translator;

public abstract class AbstractResource {
	@Context
	private HttpHeaders httpHeaders;
	
	@Context
	private UriInfo uriInfo;
	
	@Context
	private Request request;

	@Context
	private HttpServletRequest httpServletRequest;

	@Context
	private HttpServletResponse httpServletResponse;

	@Context
	private ServletConfig servletConfig;

	@Context
	private ServletContext servletContext;

	@Context
	private SecurityContext securityContext;
	
	@Inject
	private UserAuthorizationEAO authorizationEAO;
	
	@Inject
	private ArchitectureContext context;
	
	@Inject
	private Translator translator;
	
	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}

	public UriInfo getUriInfo() {
		return uriInfo;
	}

	public Request getRequest() {
		return request;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}

	public ServletConfig getServletConfig() {
		return servletConfig;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	public UserAuthorizationEAO getAuthorizationEAO() {
		return authorizationEAO;
	}

	public ArchitectureContext getContext() {
		return context;
	}

	public Translator getTranslator() {
		return translator;
	}
}
