package com.dk.mp.gzbx.util;

public class StringUtil {
	
	/**
	 * string字符串强制换行
	 * @param name
	 * @return
	 */
	public static String dealString(String name){
		StringBuilder sb=new StringBuilder();
		if(name.length() ==2 || name.length() ==3){
			sb.append(name).insert(1, "\n"); 
		}else if(name.length()>=4){
			sb.append(name).insert(2, "\n"); 
		}else{
			sb.append(name);
		}
		return sb.toString();
	}
}
