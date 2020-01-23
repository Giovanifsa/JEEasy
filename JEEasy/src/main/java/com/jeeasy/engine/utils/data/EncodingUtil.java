package com.jeeasy.engine.utils.data;

import java.nio.charset.Charset;
import java.util.Base64;

public class EncodingUtil {
	public static byte[] applyBase64(byte[] data) {
		return Base64.getEncoder().encode(data);
	}
	
	public static String applyBase64EncodeToString(byte[] data, Charset charset) {
		return new String(applyBase64(data), charset);
	}
}
