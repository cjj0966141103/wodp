package com.chinaunicom.wodp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.wodp.pojo.TenantCancelLog;
import com.chinaunicom.wodp.service.TenantCancelLogService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/tenant")
public class TenantCancelLogController {
	private Logger logger = LoggerFactory.getLogger(TenantCancelLogController.class);

	@Autowired
	private TenantCancelLogService tenantCancelLogService;

	@RequestMapping("/cancel")
	public @ResponseBody String saveProduct(@RequestBody String json) {
		logger.info("商户注销接口请求数据：{}",json);
		Gson gson = new Gson();
		//创建一个map封装返回结果信息
		Map<String,Object> map = new HashMap<String, Object>();
		logger.info("商户注销信息：" + json);
		// 把json数据转成TenantCancelLog对象
		TenantCancelLog tenantCancelLog = null;
		try {
			tenantCancelLog = gson.fromJson(json, TenantCancelLog.class);
			// 非空判断
			if(null == tenantCancelLog){
				map.put("result_flag", 0);
				map.put("message", "没有商户信息");
				String responseJson = gson.toJson(map);
				logger.info("商户注销失败："+responseJson);
				return responseJson;
			}
			//标记为是从二级平台传递过来的
			tenantCancelLog.setFlag(2);
			// 判断是否有商户id
			if (StringUtils.isBlank(tenantCancelLog.getBus_id())) {
				map.put("result_flag", 0);
				map.put("message", "没有商户id");
				//设置日志信息
				tenantCancelLog.setStatus(0);
				tenantCancelLog.setMessage("没有商户id");
				//保存日志
				tenantCancelLogService.save(tenantCancelLog);
				String responseJson = gson.toJson(map);
				logger.info("商户注销失败："+responseJson);
				return responseJson;
			}
			// 判断是否有电话号码
			if (StringUtils.isBlank(tenantCancelLog.getPhone_no())) {
				map.put("result_flag", 0);
				map.put("message", "没有电话号码");
				//设置日志信息
				tenantCancelLog.setStatus(0);
				tenantCancelLog.setMessage("没有电话号码");
				//保存日志
				tenantCancelLogService.save(tenantCancelLog);
				String responseJson = gson.toJson(map);
				logger.info("商户注销失败："+responseJson);
				return responseJson;
			}
			// 判断是否有来源平台id
			if (StringUtils.isBlank(tenantCancelLog.getSystem_id())) {
				map.put("result_flag", 0);
				map.put("message", "没有来源平台id");
				//设置日志信息
				tenantCancelLog.setStatus(0);
				tenantCancelLog.setMessage("没有来源平台id");
				//保存日志
				tenantCancelLogService.save(tenantCancelLog);
				String responseJson = gson.toJson(map);
				logger.info("商户注销失败："+responseJson);
				return responseJson;
			}
			// 判断是否有流水号
			if (StringUtils.isBlank(tenantCancelLog.getSerial_num())) {
				map.put("result_flag", 0);
				map.put("message", "没有流水号");
				//设置日志信息
				tenantCancelLog.setStatus(0);
				tenantCancelLog.setMessage("没有流水号");
				//保存日志
				tenantCancelLogService.save(tenantCancelLog);
				String responseJson = gson.toJson(map);
				logger.info("商户注销失败："+responseJson);
				return responseJson;
			}
			//调用service方法，进行商户注销
			map = tenantCancelLogService.cancel(tenantCancelLog);
			String responseJson = gson.toJson(map);
			logger.info("商户注销返回信息："+responseJson);
			return responseJson;
		} catch (Exception e) {
			map.put("result_flag", 0);
			map.put("message", "系统异常");
			if(null == tenantCancelLog){
				tenantCancelLog = new TenantCancelLog();
			}
			//设置日志信息
			tenantCancelLog.setFlag(2);
			tenantCancelLog.setStatus(0);
			tenantCancelLog.setMessage("系统异常:"+e.getMessage());
			//保存日志
			tenantCancelLogService.save(tenantCancelLog);
			String responseJson = gson.toJson(map);
			logger.error("商户注销失败："+responseJson,e);
			return responseJson;
		} 
	}
	
	@RequestMapping("/cancelByPage")
	public @ResponseBody String cancelByPage(String bus_id,HttpServletRequest request){
		//创建结果对象
		Map<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		TenantCancelLog tenantCancelLog = new TenantCancelLog();
		//设置是从页面的请求
		tenantCancelLog.setFlag(1);
		try {
			//获取用户信息
			String loginName = request.getRemoteUser();
			if(StringUtils.isBlank(loginName)){
				map.put("result_flag", 0);
				map.put("message", "用户没有登录，请先登录在操作");
				String responseJson = gson.toJson(map);
				logger.info(responseJson);
				return responseJson;
			}
			tenantCancelLog.setCreate_name(loginName);
			//判断商户id是否为空
			if(StringUtils.isBlank(bus_id)){
				map.put("result_flag", 0);
				map.put("message", "没有商户信息无法注销");
				String responseJson = gson.toJson(map);
				logger.info(responseJson);
				return responseJson;
			}
			tenantCancelLog.setBus_id(bus_id);
			//调用service方法，进行商户注销
			map = tenantCancelLogService.cancel(tenantCancelLog);
			String responseJson = gson.toJson(map);
			logger.info("商户注销返回信息："+responseJson);
			return responseJson;
		} catch (Exception e) {
			map.put("result_flag", 0);
			map.put("message", "系统异常，请稍后重试");
			//设置日志信息
			tenantCancelLog.setStatus(0);
			tenantCancelLog.setMessage("系统异常:"+e.getMessage());
			//保存日志
			tenantCancelLogService.save(tenantCancelLog);
			String responseJson = gson.toJson(map);
			logger.error(responseJson, e);
			return responseJson;
		}
	}
}
