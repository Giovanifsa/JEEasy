package com.jeeasy.engine.utils.data;

import java.security.SecureRandom;

public class StringUtils {
	public static enum GeneratorAlphabet {
		BASE16("0123456789ABCDEF"),
		BASE32("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567"),
		BASE64("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
		
		private final String alphabet;
		
		private GeneratorAlphabet(String alphabet) {
			this.alphabet = alphabet;
		}
		
		public String getAlphabet() {
			return alphabet;
		}
	};
	
	public static boolean isNonBlankString(String str) {
		return (str != null && !str.isEmpty() && !str.isBlank());
	}
	
	public static String generateRandomString(GeneratorAlphabet alphabet, int length) {
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder builder = new StringBuilder();
		
		for (int x = 0; x < length; x++) {
			builder.append(alphabet.getAlphabet().charAt(secureRandom.nextInt(alphabet.getAlphabet().length())));
		}
		
		return builder.toString();
	}
}
