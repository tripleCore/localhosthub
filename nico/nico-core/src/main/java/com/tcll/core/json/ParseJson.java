package com.tcll.core.json;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * json 与 对象之间相互转换
 * 
 * @author only3c
 */
public class ParseJson {

	/**
	 * json转对象
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return T
	 */
	public static <T> T JSON2Object(String jsonStr, Class<T> clazz) {
		// 接收{}对象，此处接收数组对象会有异常
		if (jsonStr.indexOf("[") != -1) {
			jsonStr = jsonStr.replace("[", "");
		}
		if (jsonStr.indexOf("]") != -1) {
			jsonStr = jsonStr.replace("]", "");
		}
		JSONObject obj = JSONObject.parseObject(jsonStr);
		T t = JSON.toJavaObject(obj, clazz);
		return t;// 返回一个Person对象
	}

	/**
	 * 对象转json
	 * 
	 * @param object
	 * @return String
	 */
	public static String Object2JSON(Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * json 转 list
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return List<T>
	 */
	public static <T> List<T> JSON2ObjectList(String jsonStr, Class<T> clazz) {
		List<T> t = JSON.parseArray(jsonStr, clazz);
		return t;
	}
}