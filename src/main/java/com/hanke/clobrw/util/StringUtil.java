package com.hanke.clobrw.util;

public class StringUtil {
	
	public static boolean isBlank(String str) {
		return str == null || isEmpty(str);
	}

	public static boolean isEmpty(String str) {
		return "".equals(str.trim());
	}
	
}
