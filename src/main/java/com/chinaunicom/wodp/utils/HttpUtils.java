package com.chinaunicom.wodp.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用HttpClient3 实现get、post及 ssl get、ssl post
 * 
 * @author Administrator
 * 
 */
public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	private static final String CONTACT_TYPE_DEFAULT = "text/plain;charset=UTF-8";

	public static String executeGet(String url) throws Exception {
		String content = null;

		byte[] responseBody = executeGetAsByte(url);
		// 处理内容
		content = new String(responseBody, "UTF-8");

		return content;

	}

	public static byte[] executeGetAsByte(String url) throws Exception {
		if (StringUtils.startsWith(url, "https://")) {
			return HttpUtils.executeSSLGetAsByte(url);
		}
		byte[] responseBody;
		// 定义HttpClient
		HttpClient client = new HttpClient();

		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

		// 实例化HTTP方法
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Content-Type", "text/html;charset=utf-8");

		// 使用系统提供的默认的恢复策略,此处HttpClient的恢复策略可以自定义（通过实现接口HttpMethodRetryHandler来实现）。
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		// 设置读数据超时时间(单位毫秒)
		client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		// 设置连接超时时间(单位毫秒)
		client.getHttpConnectionManager().getParams().setSoTimeout(5000);

		try {
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new Exception(getMethod.getStatusText());
			}
			// 读取内容
			responseBody = getMethod.getResponseBody();
			// 处理内容

		} catch (Exception e) {
			throw e;
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return responseBody;
	}

	public static byte[] executeSSLGetAsByte(String url) throws Exception {
		if (StringUtils.startsWith(url, "http://")) {
			return HttpUtils.executeGetAsByte(url);
		}
		byte[] responseBody = null;
		HttpsURLConnection connection = null;
		try {
			connection = (HttpsURLConnection) new URL(url).openConnection();
			connection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setConnectTimeout(5000);

			int code = connection.getResponseCode();
			logger.info("http code : {}", code);

			if (200 == code) {
				InputStream in = connection.getInputStream();
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] b = new byte[4096];
				for (int n; (n = in.read(b)) != -1;) {
					out.write(b, 0, n);
				}
				responseBody = out.toByteArray();
			}

		} catch (Exception e) {
			throw e;
		} finally {
			// 释放连接
			if (connection != null) {
				connection.disconnect();
			}
		}
		return responseBody;
	}

	public static String executePost(String url, String requestBody) throws Exception {

		String responseBody = "";
		PostMethod method = null;
		try {

			HttpClient client = new HttpClient();
			client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

			method = new PostMethod(url);
			method.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

			InputStream input = new ByteArrayInputStream(requestBody.getBytes("UTF-8"));
			method.setRequestEntity(new InputStreamRequestEntity(input));

			int statusCode = client.executeMethod(method);
			logger.info("http status = {}", statusCode);
			if (HttpStatus.SC_OK == statusCode) {
				byte[] bys = method.getResponseBody();
				responseBody = new String(bys, "UTF-8");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (method != null)
				method.releaseConnection();
		}
		return responseBody;
	}

	public static String executePostHttpConn(String url, String requestBody) throws Exception {
		HttpURLConnection connection = null;
		String response = "";
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			connection.getOutputStream().write(requestBody.getBytes("UTF-8"));
			connection.getOutputStream().flush();
			connection.getOutputStream().close();

			int code = connection.getResponseCode();
			logger.info("http code : {}", code);

			if (200 == code) {
				InputStream in = connection.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] b = new byte[4096];
				for (int n; (n = in.read(b)) != -1;) {
					out.write(b, 0, n);
				}
				response = out.toString("utf-8");
			}

		} catch (Exception e) {
			logger.error("http请求异常", e);
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}
	
	public static String executePostHttpConn(String url, String requestBody, String contentType) throws Exception {
		HttpURLConnection connection = null;
		String response = "";
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			if(StringUtils.isEmpty(contentType)){
				connection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
			}else{
				connection.setRequestProperty("Content-Type", contentType);
			}
			
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			connection.getOutputStream().write(requestBody.getBytes("UTF-8"));
			connection.getOutputStream().flush();
			connection.getOutputStream().close();
			
			int code = connection.getResponseCode();
			logger.info("http code : {}", code);
			
			if (200 == code) {
				InputStream in = connection.getInputStream();

				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] b = new byte[4096];
				for (int n; (n = in.read(b)) != -1;) {
					out.write(b, 0, n);
				}
				response = out.toString("utf-8");
			}
			
		} catch (Exception e) {
			logger.error("http请求异常", e);
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}
	
	/**
	 * 
	 * TODO:重载方法，加入参数request,并转发请求所有头信息
	 * @author WangCL
	 * 
	 */
	public static String executePostHttpConn(String url, String requestBody, HttpServletRequest request) throws Exception {
        HttpURLConnection connection = null;
        String response = "";
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            
            @SuppressWarnings("rawtypes")
            Enumeration headerNames = request.getHeaderNames();
            while(headerNames.hasMoreElements()){//将请求的所有头信息转发
                String headerName = (String) headerNames.nextElement();
                connection.setRequestProperty(headerName, request.getHeader(headerName));
            }
            
            if(StringUtils.isEmpty(request.getHeader("Content-Type"))){
                connection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
            }
            
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            connection.getOutputStream().write(requestBody.getBytes("UTF-8"));
            connection.getOutputStream().flush();
            connection.getOutputStream().close();
            
            int code = connection.getResponseCode();
            logger.info("http code : {}", code);
            
            if (200 == code) {
                InputStream in = connection.getInputStream();
                StringBuffer out = new StringBuffer();
                byte[] b = new byte[4096];
                for (int n; (n = in.read(b)) != -1;) {
                    out.append(new String(b, 0, n, "UTF-8"));
                }
                response = out.toString();
            }
            
        } catch (Exception e) {
            logger.error("http请求异常", e);
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

	public static String executeSSLGet(String url) throws Exception {
		if(StringUtils.startsWith(url, "http://")){
			return executeGet(url);
		}
		HttpsURLConnection connection = null;
		String response = "";
		try {

			connection = (HttpsURLConnection) new URL(url).openConnection();
			connection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setConnectTimeout(5000);

			int code = connection.getResponseCode();
			logger.info("http code : {}", code);

			if (200 == code) {
				InputStream in = connection.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] b = new byte[4096];
				for (int n; (n = in.read(b)) != -1;) {
					out.write(b, 0, n);
				}
				response = out.toString("utf-8");
			}

		} catch (Exception e) {
			logger.error("http请求异常", e);
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		logger.debug("响应报文：{}", response);

		return response;
	}

	public static String executeSSLPost(String url, String request) throws Exception {
		url = url.trim();
		if (StringUtils.startsWith(url, "http://")) {
			return HttpUtils.executePost(url, request);
		}
		logger.debug("请求报文：\n{}", request);
		HttpURLConnection connection = null;
		String response = "";
		try {

			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setConnectTimeout(5000);

			connection.getOutputStream().write(request.getBytes("UTF-8"));
			connection.getOutputStream().flush();
			connection.getOutputStream().close();

			int code = connection.getResponseCode();
			logger.info("http code : {}", code);

			if (200 == code) {

				InputStreamReader ins = new InputStreamReader(connection.getInputStream(), "UTF-8");

				StringWriter output = new StringWriter();
				int n = 0;
				char[] buffer = new char[1024 * 4];
				while (-1 != (n = ins.read(buffer))) {
					output.write(buffer, 0, n);
				}
				response = output.toString();
			}

		} catch (Exception e) {
			logger.error("http请求异常", e);
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		logger.debug("响应报文：{}", response);

		return response;
	}

	public static String executeSSLPostInputStream(String url, InputStream in) throws Exception {
		logger.debug("流文件ssl post提交URL=\n{}", url);

		HttpURLConnection connection = null;
		String response = "";
		try {

			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setConnectTimeout(5000);
			byte[] bs = new byte[1024];
			while (in.read(bs) > 0) {
				connection.getOutputStream().write(bs);
			}
			connection.getOutputStream().flush();
			connection.getOutputStream().close();

			int code = connection.getResponseCode();
			logger.info("http code : {}", code);

			if (200 == code) {

				InputStreamReader ins = new InputStreamReader(connection.getInputStream(), "UTF-8");

				StringWriter output = new StringWriter();
				int n = 0;
				char[] buffer = new char[1024 * 4];
				while (-1 != (n = ins.read(buffer))) {
					output.write(buffer, 0, n);
				}
				response = output.toString();
			}

		} catch (Exception e) {
			logger.error("http请求异常", e);
			throw e;
		} finally {
			if (connection != null) {
				in.close();
				connection.disconnect();
			}
		}

		logger.debug("响应报文：{}", response);

		return response;
	}






}
