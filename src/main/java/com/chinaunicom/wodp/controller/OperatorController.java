package com.chinaunicom.wodp.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.wodp.pojo.Operator;
import com.chinaunicom.wodp.pojo.QueryOperator;
import com.chinaunicom.wodp.service.OperatorService;
import com.google.gson.Gson;


 
@Controller
@RequestMapping("/operator")
public class OperatorController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OperatorService operatorService;
	
	
	
	//获取二级平台传递的操作员数据
	@ResponseBody
	@RequestMapping("/addOperatorLog")
	public String addOperatorLog(@RequestBody String json){
		logger.info("租户账户权限申请接口请求数据：{}",json);
		String rspJson = "{}";
		try{
			Map <String, String> rspJsonMap = new HashMap <String, String>();
			rspJsonMap.put("result_flag", "0");
	        rspJsonMap.put("message", "受理失败");
	        Gson gs = new Gson();
			logger.info("二级平台传递的操作员数据：" + json);
			operatorService.addOperatorLog(json);
			rspJsonMap.put("result_flag", "1");
	        rspJsonMap.put("message", "成功受理");
			rspJson = gs.toJson(rspJsonMap);
			//rspJson = ResponseUtils.mapToString(rspJsonMap);
			logger.info("租户账户权限申请接口响应数据：{}" , rspJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常:", e);
		}
		return rspJson;
	}
	
	
	//一级平台审核操作员
	@ResponseBody
	@RequestMapping("/checkOperator")
	public String checkOperator(String operator_check ,String id, String message){
		String rspJson = "{}";
		try {
			Map <String, String> rspJsonMap = new HashMap <String, String>();
			rspJsonMap.put("result_flag", "0");
	        rspJsonMap.put("message", "操作失败");
	        Gson gs = new Gson();
			logger.info("审核操作员传递的数据：" + "id：" + id + ",operator_check：" + operator_check + ",message：" + message);
			Operator operator = new Operator();
			operator.setId(Long.parseLong(id));
			operator.setOperator_check(operator_check);
			//审核操作员业务
			String rspMessage = operatorService.checkOperator(operator,message);
			rspJsonMap.put("result_flag", "1");
	        rspJsonMap.put("message", rspMessage);
			rspJson = gs.toJson(rspJsonMap);
			logger.info("审核操作员返回数据：" + rspJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常:", e);
		}
		return rspJson;
	
	}
	
	
	//一级平台根据条件查询操作员
	@ResponseBody
	@RequestMapping("/findOperatorLog")
	public String findOperatorLog(QueryOperator queryOperator){
		String rspJson = "{}";
		try {
			logger.info("前台传过来的数据：" + queryOperator.toString());
			Map<String, Object> rspJsonMap = operatorService.findOperatorLog(queryOperator);
			Gson gs = new Gson();
			rspJson = gs.toJson(rspJsonMap);
			logger.info("查询操作员返回数据：" + rspJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常:", e);
		}
		return rspJson;
	}
	
	
	//一级平台根据条件查询操作员详情页
	@ResponseBody
	@RequestMapping("/findOperatorLogDetails")
	public String findOperatorLogDetails(Long id){
		String rspJson ="{}";
		try {
			logger.info("前台传过来的数据：" + id);
			Operator operatorLogDetails = operatorService.findOperatorLogDetails(id);
			Gson gs = new Gson();
			rspJson = gs.toJson(operatorLogDetails);
			logger.info("商户下的操作员列表数据：" + rspJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常:", e);
		}
		return rspJson;
	}
	
	
	//一级平台根据条件查询操作员
	@ResponseBody
	@RequestMapping("/findOperator")
	public String findOperator(QueryOperator queryOperator){
		String rspJson ="{}";
		try {
			logger.info("前台传过来的数据：" + queryOperator.toString());
			Map<String, Object> rspJsonMap = operatorService.findOperator(queryOperator);
			Gson gs = new Gson();
			rspJson = gs.toJson(rspJsonMap);
			logger.info("查询操作员返回数据：" + rspJson);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常:", e);
		}
		return rspJson;
	}
	
	
}