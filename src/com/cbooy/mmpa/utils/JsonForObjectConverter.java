package com.cbooy.mmpa.utils;

import com.google.gson.Gson;


/**
 * json���ַ���֮����໥ת��
 * @author chenhao24
 *
 */
public class JsonForObjectConverter {
	
	public static<T> T StringToObject(String str,Class<T> clazz){
		
		Gson gson = new Gson();
		
		return gson.fromJson(str, clazz);
	}
}
