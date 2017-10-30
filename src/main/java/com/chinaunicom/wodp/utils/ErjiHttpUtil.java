package com.chinaunicom.wodp.utils;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;


/**
 * 
 * 
 * @author Administrator
 * 
 */
public class ErjiHttpUtil {

	
	public static InputStream doPostForStream(String url, Map<String, String> map) throws Exception {
		HttpResponse response = doPost(url, map);
		return response!=null ? response.getEntity().getContent() : null;
	}

	public static  HttpResponse doPost(String url, Map<String, String> map) throws Exception{
		HttpPost pm = new HttpPost();
		URIBuilder builder = new URIBuilder(url);
		//填入参数
		if (map != null && !map.isEmpty()){
			List<BasicNameValuePair> nvps = new LinkedList<BasicNameValuePair>();
			Set<Entry<String, String>> paramsSet= map.entrySet();
			for (Entry<String, String> paramEntry : paramsSet) {
				nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
			}
			pm.setURI(builder.build());
			pm.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
		}
		pm.setURI(builder.build());
		CloseableHttpClient client = HttpClients.createDefault(); 
		return client.execute(pm);
	}


	public static String readStream(InputStream in, String encoding) throws Exception{
		if (in == null){
			return null;
		}
		InputStreamReader inReader= null;
		if (encoding == null){
			inReader= new InputStreamReader(in, "utf-8");
		}else{
			inReader= new InputStreamReader(in, encoding);
		}
		char[] buffer= new char[1024];
		int readLen= 0;
		StringBuffer sb= new StringBuffer();
		while((readLen= inReader.read(buffer))!=-1){
			sb.append(buffer, 0, readLen);
		}
		inReader.close();
		return sb.toString();
	}




}
