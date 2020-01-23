package com.jeeasy.engine.resources.rest.filters;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import com.jeeasy.engine.context.ArchitectureContext;
import com.jeeasy.engine.context.ResourceContext;
import com.jeeasy.engine.database.entities.UserAuthorization;
import com.jeeasy.engine.services.UserAuthorizationService;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class ResourceContextFilter implements ContainerRequestFilter {
	@Inject
	private ArchitectureContext context;
	
	@Inject
	private UserAuthorizationService authService;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		loadRequestData(requestContext);
	}
	
	private void loadRequestData(ContainerRequestContext requestContext) {
		ResourceContext resourceContext = new ResourceContext();
		resourceContext.setAcceptableLanguages(requestContext.getAcceptableLanguages());
		resourceContext.setAuthorizationHeader(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION));
		resourceContext.setCookies(requestContext.getCookies());
		resourceContext.setHeaders(requestContext.getHeaders());
		resourceContext.setRequest(requestContext.getRequest());
		resourceContext.setRequestMethod(requestContext.getMethod());
		resourceContext.setSecurityContext(requestContext.getSecurityContext());
		resourceContext.setUriInfo(requestContext.getUriInfo());
		
		UserAuthorization auth = authService.processRawAuthorization(resourceContext.getAuthorizationHeader());
		resourceContext.setAuthorization(auth);
		
		context.setResourceContext(resourceContext);
	}
}
