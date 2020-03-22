package com.jeeasy.engine.utils.business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationUtil {
	public static final String BEARER_AUTH_PREFIX = "Bearer";
	public static final String BASIC_AUTH_PREFIX = "Basic";
	
	public static String extractAuthorizationFromString(String prefix, String authString) {
		String authRegex = "^\\s{0}" + prefix + "\\s{1}(\\S+)$";
		
		Pattern pattern = Pattern.compile(authRegex);
		Matcher matcher = pattern.matcher(authString);
		
		if (matcher.matches()) {
			return matcher.group(1);
		}
		
		return null;
	}
}
