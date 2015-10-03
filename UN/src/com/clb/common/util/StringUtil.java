package com.clb.common.util;

public class StringUtil {

	public static boolean isEmpty(String str){
		return null==str || str.length()==0;
	}
	
	public static boolean isEmpty(StringBuilder str){
		return null==str || str.length()==0;
	}
	
	public static boolean isEmptyJson(String str){
		return null==str || str.length()==0 || "{}".equals(str) || "[]".equals(str);
	}
	
	public static int getInt(String str, int defaultValue){
		if(isEmpty(str)){
			return defaultValue;
		}
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}
}
