package com.jeeasy.engine.context;

import java.util.Locale;

import javax.annotation.PreDestroy;
import javax.enterprise.inject.Alternative;

import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.database.entities.UserAuthorization;

@Alternative
public class ArchitectureContext {
	private User user;
	private ResourceContext resourceContext;
	private Locale contextLanguage;
	
	public User getUser() {
		if (isRequestUserAuthorized()) {
			return getAuthorization().getUser();
		}
		
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ResourceContext getResourceContext() {
		return resourceContext;
	}

	public void setResourceContext(ResourceContext resourceContext) {
		this.resourceContext = resourceContext;
	}
	
	public boolean isRequestContext() {
		return resourceContext != null;
	}
	
	public boolean hasUser() {
		if (isRequestUserAuthorized() || user != null) {
			return true;
		}
		
		return false;
	}
	
	public boolean isRequestUserAuthorized() {
		if (isRequestContext()) {
			return resourceContext.isAuthorized();
		}
		
		return false;
	}
	
	public UserAuthorization getAuthorization() {
		if (isRequestContext()) {
			return resourceContext.getAuthorization();
		}
		
		return null;
	}
	
	@PreDestroy
	private void destroyForThread() {
		ArchitectureContextManager.destroyForThread(this);
	}

	public Locale getContextLanguage() {
		if (isRequestContext()) {
			return resourceContext.getPreferredLanguage();
		}
		
		else if (contextLanguage == null) {
			return Locale.ENGLISH;
		}
		
		return contextLanguage;
	}

	public void setContextLanguage(Locale contextLanguage) {
		this.contextLanguage = contextLanguage;
	}
}
