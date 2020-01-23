package com.jeeasy.engine.utils.business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationUtil {
	public static final String BEARER_AUTH_PREFIX = "Bearer";
	public static final String BASIC_AUTH_PREFIX = "Basic";
	
	public static String getAuthorizationFromHeader(String prefix, String authorizationHeader) {
		String authRegex = "^\\s*" + prefix + "\\s+((?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4}))$";
		
		Pattern pattern = Pattern.compile(authRegex);
		Matcher matcher = pattern.matcher(authorizationHeader);
		
		if (matcher.matches()) {
			return matcher.group(1);
		}
		
		return null;
	}
}
