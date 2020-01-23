package com.jeeasy.engine.utils.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.jeeasy.engine.exceptions.FailureRuntimeException;
import com.jeeasy.engine.exceptions.codes.EnumFailureExceptionCodes;
import com.jeeasy.engine.translations.EnumFailuresTranslations;
import com.jeeasy.engine.translations.Translator;
import com.jeeasy.engine.utils.cdi.CDIUtils;

public class HashingUtil {
	public static byte[] applySHA512(byte[] data) {
		return applyHash("SHA-512", data);
	}
	
	public static byte[] applyHash(String algorithm, byte[] data) {
		try {
			return MessageDigest.getInstance(algorithm).digest(data);
		}catch (NoSuchAlgorithmException e) {
			Translator translator = CDIUtils.inject(Translator.class);
			
			throw new FailureRuntimeException(
					translator.translateForContextLanguage(EnumFailuresTranslations.FAILED_TO_HASH_DATA, algorithm),
					EnumFailureExceptionCodes.FAILED_TO_RUN_HASH_ALGORITHM);
		}
		
	}
}
