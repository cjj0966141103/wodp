package com.chinaunicom.wodp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResponseUtils {
	 public static String mapToString(Map<String, String> params) {

	        List<String> keys = new ArrayList<String>(params.keySet());
	        Collections.sort(keys);

	        String prestr = "";

	        for (int i = 0; i < keys.size(); i++) {
	            String key = keys.get(i);
	            String value = params.get(key);

	            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
	                prestr = prestr + key + "=" + value;
	            } else {
	                prestr = prestr + key + "=" + value + "&";
	            }
	        }

	        return prestr;
	    }

	 
	 /*	 public static String mapToString(Map map) {
		 StringBuffer sb = new StringBuffer();
		 Set<String> keySet = map.keySet();
		 for (String key : keySet) {
			 Object value = map.get(key);
			 if (null != value) {
				 sb.append(key);
				 sb.append("=");
				 sb.append(value);
				 sb.append("&");
			 }
		 }
		 return sb.toString().substring(0, sb.length() - 1);
	 }
*/}
