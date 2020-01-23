package com.jeeasy.engine.services;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.jeeasy.engine.database.eaos.UserAuthorizationEAO;
import com.jeeasy.engine.database.eaos.UserEAO;
import com.jeeasy.engine.database.entities.User;
import com.jeeasy.engine.database.entities.UserAuthorization;
import com.jeeasy.engine.exceptions.FailureRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumFailureExceptionCodes;
import com.jeeasy.engine.queries.vos.UserAuthorizationVO;
import com.jeeasy.engine.resources.dtos.beans.UserLoginBean;
import com.jeeasy.engine.services.validators.UserAuthorizationValidator;
import com.jeeasy.engine.settings.SystemCharsetSetting;
import com.jeeasy.engine.settings.UserAuthTokenExpSetting;
import com.jeeasy.engine.settings.UserAuthTokenRetriesSetting;
import com.jeeasy.engine.translations.EnumFailuresTranslations;
import com.jeeasy.engine.utils.business.AuthorizationUtil;
import com.jeeasy.engine.utils.data.EncodingUtil;
import com.jeeasy.engine.utils.data.HashingUtil;
import com.jeeasy.engine.utils.data.StringUtils;
import com.jeeasy.engine.utils.dependencies.IDependencyBean;

public class UserAuthorizationService extends AbstractService<UserAuthorization, UserAuthorizationVO, UserAuthorizationValidator, UserAuthorizationEAO> {	
	@Inject
	private UserEAO userEAO;
	
	@Inject
	private SystemCharsetSetting systemCharset;
	
	@Inject
	private UserAuthTokenRetriesSetting tokenGenRetries;
	
	@Inject
	private UserAuthTokenExpSetting userAuthTokenExp;
	
	public void cleanUserAuthorizations(UserLoginBean userToClean) {
		User user = userEAO.findByUserName(userToClean.getUserName());
		getValidator().validateUserCleanup(user);
		
		getEAO().deleteAllUserAuthorizationTokens(user);
	}
	
	public UserAuthorization login(UserLoginBean loginBean) {
		Charset usedCharset = systemCharset.getCharset();
		byte[] hashedPassword = HashingUtil.applySHA512(loginBean.getPassword().getBytes(usedCharset));
		
		String userName = loginBean.getUserName();
		String base64SHA512Password = EncodingUtil.applyBase64EncodeToString(hashedPassword, usedCharset);
		
		getValidator().validateLoginData(userName, base64SHA512Password);
		
		User user = userEAO.findByUserLogin(userName, base64SHA512Password);
		
		Date curTime = new Date();
		Date expDate = new Date(curTime.getTime() + userAuthTokenExp.getValue());
		
		UserAuthorization userAuth = new UserAuthorization();
		userAuth.setUser(user);
		userAuth.setExpirationDate(expDate);
		userAuth.setAuthorization(generateRandomUserAuthToken());
		
		return insert(userAuth);
	}
	
	public UserAuthorization renewAuthorization() {
		UserAuthorization userAuth = getContext().getAuthorization();
		
		userAuth.setExpirationDate(new Date(System.currentTimeMillis() + userAuthTokenExp.getValue()));
		userAuth.setAuthorization(generateRandomUserAuthToken());
		
		return update(userAuth);
	}
	
	public void logout() {
		delete(getContext().getAuthorization().getId());
	}
	
	public UserAuthorization processRawAuthorization(String authorizationHeader) {
		if (StringUtils.isNonBlankString(authorizationHeader)) {
			String auth = AuthorizationUtil.getAuthorizationFromHeader(AuthorizationUtil.BEARER_AUTH_PREFIX, authorizationHeader);
			return getEAO().findByAuthorization(auth);
		}
		
		return null;
	}
	
	private String generateRandomUserAuthToken() {
		Integer generationTries = tokenGenRetries.getValue();
		
		for (int x = 0; x < generationTries; x++) {
			String auth = StringUtils.generateRandomString(StringUtils.GeneratorAlphabet.BASE64, generationTries);
			
			if (getEAO().findByAuthorization(auth) == null) {
				return auth;
			}
		}
		
		throw new FailureRuntimeException(
				getTranslator().translateForContextLanguage(EnumFailuresTranslations.FAILED_TO_GENERATE_USER_AUTH_TOKEN, generationTries),
				EnumFailureExceptionCodes.FAILED_TO_GENERATE_UNIQUE_DATABASE_DATA);
	}
	
	public void cleanupExpiredAuthorizations() {
		getEAO().deleteExpiredAuthorizations();
	}
	
	@Override
	public List<Class<? extends IDependencyBean>> getDependencies() {
		return Arrays.asList(UserService.class);
	}
}
