package com.jeeasy.engine.context.annotations;

import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.jeeasy.engine.context.ArchitectureContext;
import com.jeeasy.engine.exceptions.UnauthorizedRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumUnauthorizedExceptionCodes;
import com.jeeasy.engine.translations.EnumValidationsTranslations;
import com.jeeasy.engine.translations.Translator;

@Interceptor
@RequiresAuthorization
public class RequiresAuthorizationInterceptor {
	@Inject
	private ArchitectureContext context;
	
	@Inject
	private Translator translator;
	
	public Object requireAuthorization(InvocationContext invocationContext) throws Exception {
		if (!context.isRequestUserAuthorized()) {
			throw new UnauthorizedRuntimeException(
					translator.translateForContextLanguage(EnumValidationsTranslations.LOGIN_REQUIRED),
					EnumUnauthorizedExceptionCodes.LOGIN_REQUIRED);
		}
		
		return invocationContext.proceed();
	}
}
