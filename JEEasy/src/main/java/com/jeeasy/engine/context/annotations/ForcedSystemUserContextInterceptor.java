package com.jeeasy.engine.context.annotations;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.jeeasy.engine.context.ArchitectureContext;
import com.jeeasy.engine.context.ArchitectureContextManager;
import com.jeeasy.engine.database.eaos.UserEAO;
import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.exceptions.FailureRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumFailureExceptionCodes;
import com.jeeasy.engine.translations.EnumFailuresTranslations;
import com.jeeasy.engine.translations.Translator;
import com.jeeasy.engine.utils.cdi.CDIUtils;

@Interceptor
@ForcedSystemUserContext
public class ForcedSystemUserContextInterceptor {
	@Inject
	private UserEAO userEAO;
	
	@Inject
	private Translator translator;
	
	@AroundInvoke
	public Object forceSystemUserContext(InvocationContext invocationContext) throws Exception {
		ArchitectureContext lastContext = CDIUtils.inject(ArchitectureContext.class);
		ArchitectureContextManager.destroyForThread();
		
		ArchitectureContext newContext = CDIUtils.inject(ArchitectureContext.class);
		User systemUser = userEAO.findByUserName("system");
		
		if (systemUser == null) {
			rollbackContext(lastContext);
			
			throw new FailureRuntimeException(translator.translateForContextLanguage(
					EnumFailuresTranslations.FAILED_TO_FIND_SYSTEM_USER),
					EnumFailureExceptionCodes.FAILED_TO_FIND_SYSTEM_USER);
		}
		
		newContext.setUser(systemUser);
		
		Object invocationReturn = invocationContext.proceed();
		
		rollbackContext(lastContext);
		return invocationReturn;
	}
	
	private void rollbackContext(ArchitectureContext lastContext) {
		ArchitectureContextManager.destroyForThread();
		ArchitectureContextManager.setForThread(lastContext);
	}
}
