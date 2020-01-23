package com.jeeasy.engine.context;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.jeeasy.engine.database.entities.UserAuthorization;

public class ResourceContext {
	private UserAuthorization authorization;
	
	private String authorizationHeader;
	
	private List<Locale> acceptableLanguages;
	
	private Map<String, Cookie> cookies;
	
	private MultivaluedMap<String, String> headers;
	
	private String requestMethod;
	
	private Request request;
	
	private UriInfo uriInfo;
	
	private SecurityContext securityContext;
	
	public UserAuthorization getAuthorization() {
		return authorization;
	}
	
	public boolean isAuthorized() {
		return authorization != null;
	}
	
	public void setAuthorization(UserAuthorization authorization) {
		this.authorization = authorization;
	}
	
	public List<Locale> getAcceptableLanguages() {
		return acceptableLanguages;
	}
	
	public void setAcceptableLanguages(List<Locale> acceptableLanguages) {
		this.acceptableLanguages = acceptableLanguages;
	}
	
	public Map<String, Cookie> getCookies() {
		return cookies;
	}
	
	public void setCookies(Map<String, Cookie> cookies) {
		this.cookies = cookies;
	}
	
	public MultivaluedMap<String, String> getHeaders() {
		return headers;
	}
	
	public void setHeaders(MultivaluedMap<String, String> headers) {
		this.headers = headers;
	}
	
	public String getRequestMethod() {
		return requestMethod;
	}
	
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	public Request getRequest() {
		return request;
	}
	
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public UriInfo getUriInfo() {
		return uriInfo;
	}
	
	public void setUriInfo(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	}
	
	public SecurityContext getSecurityContext() {
		return securityContext;
	}
	
	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public String getAuthorizationHeader() {
		return authorizationHeader;
	}

	public void setAuthorizationHeader(String authorizationHeader) {
		this.authorizationHeader = authorizationHeader;
	}
	
	public Locale getPreferredLanguage() {
		return acceptableLanguages.get(0);
	}
}
