package com.chinaunicom.wodp.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinaunicom.wodp.mapper.OperatorMapper;
import com.chinaunicom.wodp.mapper.TenantMapper;
import com.chinaunicom.wodp.pojo.Operator;
import com.chinaunicom.wodp.pojo.OperatorRole;
import com.chinaunicom.wodp.pojo.QueryOperator;
import com.chinaunicom.wodp.pojo.RegisterUser;
import com.chinaunicom.wodp.pojo.Tenant;
import com.chinaunicom.wodp.service.OperatorService;
import com.chinaunicom.wodp.service.RegisterService;
import com.chinaunicom.wodp.utils.ErjiHttpUtil;
import com.chinaunicom.wodp.utils.HttpUtils;
import com.chinaunicom.wodp.utils.MD5;
import com.chinaunicom.wodp.utils.PinyinUtils;
import com.chinaunicom.wodp.utils.PropertiesUtils;
import com.chinaunicom.wodp.utils.RandomUtils;
import com.chinaunicom.wodp.utils.ResponseUtils;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Service
@Transactional
public class OperatorServiceImpl implements OperatorService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OperatorMapper operatorMapper;
	@Autowired
	private TenantMapper tenantMapper;
	@Autowired
	private RegisterService registerService;
	
 
	public void addOperatorLog(String json) {
		Gson gs = new Gson();
		Operator operator = gs.fromJson(json, Operator.class);
		OperatorRole operatorRole = gs.fromJson(json, OperatorRole.class);
		operatorMapper.addOperatorLog(operator);
		Long operatorLogId = operator.getId();
		operatorRole.setOpId(operatorLogId);
		String roles = operatorRole.getRoles();
		if(roles == null){
			String user_id = operator.getUser_id();
			Operator operator2 = operatorMapper.findOperatorByUserId(user_id);
			operatorRole.setRoles(operator2.getRoles());
		}
		operatorMapper.addOperatorLogRole(operatorRole);
	}

	
	
	public String checkOperator(Operator operator,String message) throws Exception {
		Long id = operator.getId();
		String operator_check = operator.getOperator_check();
		String rspMessage = "操作成功";
		if(StringUtils.isNotBlank(operator_check) && id != null){
			//更新日志表
			operatorMapper.updateOperatorLog(operator);
			//查询操作员日志表数据
			Operator findOperatorLog = operatorMapper.findOperatorLogById(id);
			String operate_type = findOperatorLog.getOperate_type();
			//查询操作员角色日志表数据
			OperatorRole operatorRole = operatorMapper.findOperatorLogRoleByOperatorId(id);
			//再判断操作操作员的表(1表示通过)
			if("1".equals(operator_check)){
				//1.新增  2.修改 3.删除 4.信息同步 
				if("1".equals(operate_type)){
					//findOperatorLog.setId(null);
					//添加操作员
					operatorMapper.addOperator(findOperatorLog);
					//返回操作员表添加的主键
					Long opId = findOperatorLog.getId();
					//查询操作员角色日志表数据
					//OperatorRole operatorRole = operatorMapper.findOperatorLogRoleByOperatorId(id);
					//设置角色表外键
					operatorRole.setOpId(opId);
					//添加操作员角色
					operatorMapper.addOperatorRole(operatorRole);
					//注册用户
					rspMessage = addUser(findOperatorLog);
					//调用二级接口
					send_LESSEE_ACCOUNT_AUTH_NOICE_SERVICE(findOperatorLog,operatorRole,message);
				}
				if("2".equals(operate_type)){
					//更新操作员
					operatorMapper.updateOperatorByUserId(findOperatorLog);
					//得到操作员id(即角色外键值)
					String user_id = findOperatorLog.getUser_id();
					Operator operator2 = operatorMapper.findOperatorByUserId(user_id);
					Long opId = operator2.getId();
					//查询操作员角色日志表数据
					//OperatorRole operatorRole = operatorMapper.findOperatorLogRoleByOperatorId(id);
					//设置操作员外键id
					operatorRole.setOpId(opId);
					//更新操作员角色
					operatorMapper.updateOperatorRoleByOpId(operatorRole);
					//将Operator表中的id值赋值到findOperatorLog
					findOperatorLog.setId(operator2.getId());
					//更新之前的操作员角色
					String oldRoles = operator2.getRoles();
					//修改用户
					rspMessage = updateUser(findOperatorLog,oldRoles);
					//调用二级接口
					send_LESSEE_ACCOUNT_AUTH_NOICE_SERVICE(findOperatorLog,operatorRole,message);
				}
				if("3".equals(operate_type)){
					//得到操作员
					String user_id = findOperatorLog.getUser_id();
					Operator operator2 = operatorMapper.findOperatorByUserId(user_id);
					operator2.setStatus("0");
					//更新状态
					operatorMapper.updateOperatorByUserId(operator2);
					//查询操作员角色日志表数据
					//OperatorRole operatorRole = operatorMapper.findOperatorLogRoleByOperatorId(id);
					//将Operator表中的id值赋值到findOperatorLog
					findOperatorLog.setId(operator2.getId());
					//当前操作员的角色
					String roles = operator2.getRoles();
					//删除用户
					rspMessage = deletUser(findOperatorLog,roles);
					//调用二级接口
					send_LESSEE_ACCOUNT_AUTH_NOICE_SERVICE(findOperatorLog,operatorRole,message);
				}
				if("4".equals(operate_type)){
					//更新操作员
					operatorMapper.updateOperatorByUserId(findOperatorLog);				
				}
			}else{
				//调用二级接口
				send_LESSEE_ACCOUNT_AUTH_NOICE_SERVICE(findOperatorLog,operatorRole,message);
			}
		}
		return rspMessage;
	}
	
	//四个平台注册用户
	public String addUser(Operator operator) throws Exception{
		String id = String.valueOf(operator.getId());
		String userName = operator.getUser_name();
		String loginName = PinyinUtils.chineseToPinYinF(userName)+id;
		String mobile = operator.getPhone_no();
		String email = operator.getEmail();
		String busId = operator.getBus_id();
		String roles = operator.getRoles();
		//注册用户信息
		RegisterUser user = new RegisterUser();
		user.setUsername(userName);
		user.setLoginName(loginName);
		user.setMobile(mobile);
		user.setEmail(email);
		user.setLoginPwd("123456");
		user.setIsAdmin(0);
		user.setRegisterDEW("0");
		user.setRegisterWJ("0");
		user.setRegisterGA("0");
		Tenant tenant = tenantMapper.select(busId);
		String rtw_orgId = tenant.getDarwin_orgId();
		String gateway_orgId = tenant.getGateway_orgId();
		//1：数据加工角色(达尔文)；2：数据建模角色(数据挖掘)；3：数据审核角色(gataway)；
		if(StringUtils.isNotBlank(roles)){
			String[] roleArray = StringUtils.split(roles.trim(), ",");
			for (String role : roleArray) {
				if("1".equals(role)){
					//达尔文注册
					String rtw_orgName = tenant.getBus_name();
					user.setRtw_orgId(rtw_orgId);
					user.setRtw_orgName(rtw_orgName);
					user.setTotalSpace("1");
					user.setRtw_roleId("2");
					user.setRegisterDEW("1");
					continue;
				}
				if("2".equals(role)){
					//数据挖掘注册
					user.setAdmin(0);
					user.setSex(1);
					user.setRoleIds("m02");
					user.setQq(RandomUtils.genRandomNum(10));
					user.setRegisterWJ("1");
					continue;
				}
				if("3".equals(role)){
					//gataway注册
					user.setUserType("dataUser");
					user.setCertNo(RandomUtils.genRandomNum(18));
					user.setFilePath("operatorRegister");
					user.setOrgId(gateway_orgId);
					user.setRegisterGA("1");
					continue;
				}
			}
		}
		String message = registerService.save(user);
		return message;
	};

	//四个平台更新用户
	public String updateUser(Operator operator,String oldRoles) throws Exception{
		//三个平台的删除用户url
		PropertiesUtils.setProperties("/app.properties");
		String deletDrwUrl = PropertiesUtils.getPropertiesValue("deletDrwUrl");//达尔文
		String deletWjUrl = PropertiesUtils.getPropertiesValue("deletWjUrl");//数据挖掘
		String deletGtwUrl = PropertiesUtils.getPropertiesValue("deletGtwUrl");//数据审计
		//获取值
		String id = String.valueOf(operator.getId());
		String userName = operator.getUser_name();
		String user_id = operator.getUser_id();
		Operator oldCreateOperator = operatorMapper.findCreateOperatorLogByUserId(user_id);
		String loginName = PinyinUtils.chineseToPinYinF(oldCreateOperator.getUser_name())+id;
		String mobile = operator.getPhone_no();
		String email = operator.getEmail();
		String busId = operator.getBus_id();
		String roles = operator.getRoles();
		//注册用户信息
		RegisterUser user = new RegisterUser();
		user.setUsername(userName);
		user.setLoginName(loginName);
		user.setMobile(mobile);
		user.setEmail(email);
		user.setLoginPwd("123456");
		user.setIsAdmin(0);
		Tenant tenant = tenantMapper.select(busId);
		String rtw_orgId = tenant.getDarwin_orgId();
		String gateway_orgId = tenant.getGateway_orgId();
		StringBuffer message = new StringBuffer();
		message.append("操作成功！");
		//1：数据加工角色(达尔文)；2：数据建模角色(数据挖掘)；3：数据审核角色(gataway)；
		if(StringUtils.isBlank(roles)){
			String[] oldRoleArray = StringUtils.split(oldRoles.trim(), ",");
			for (String oldRole : oldRoleArray) {
				if("1".equals(oldRole)){
					//调用达尔文删除
					String json = HttpUtils.executePost(deletDrwUrl, loginName);
					JSONObject jsonObject = JSONObject.fromObject(json);
					String status = jsonObject.get("status").toString().trim();
					if("0".equals(status)){
						message.append("数据作业角色去除失败！");
					}
					continue;
				}
				if("2".equals(oldRole)){
					//调用数据挖掘删除
					String json = HttpUtils.executePost(deletWjUrl, loginName);
					JSONObject jsonObject = JSONObject.fromObject(json);
					String status = jsonObject.get("status").toString().trim();
					if("0".equals(status)){
						message.append("数据挖掘角色去除失败！");
					}
					continue;
				}
				if("3".equals(oldRole)){
					//调用gataway删除
					String json = HttpUtils.executePost(deletGtwUrl, loginName);
					JSONObject jsonObject = JSONObject.fromObject(json);
					String status = jsonObject.get("status").toString().trim();
					if("0".equals(status)){
						message.append("数据审计角色去除失败！");
					}
					continue;
				}
			}
		}else{
			//更新删除操作
			if(oldRoles.contains("1") && !roles.contains("1")){
				//调用达尔文删除
				String json = HttpUtils.executePost(deletDrwUrl, loginName);
				JSONObject jsonObject = JSONObject.fromObject(json);
				String status = jsonObject.get("status").toString().trim();
				if("0".equals(status)){
					message.append("数据作业角色去除失败！");
				}
			}
			if(oldRoles.contains("2") && !roles.contains("2")){
				//调用数据挖掘删除
				String json = HttpUtils.executePost(deletWjUrl, loginName);
				JSONObject jsonObject = JSONObject.fromObject(json);
				String status = jsonObject.get("status").toString().trim();
				if("0".equals(status)){
					message.append("数据挖掘角色去除失败！");
				}
			}
			if(oldRoles.contains("3") && !roles.contains("3")){
				//调用gataway删除
				String json = HttpUtils.executePost(deletGtwUrl, loginName);
				JSONObject jsonObject = JSONObject.fromObject(json);
				String status = jsonObject.get("status").toString().trim();
				if("0".equals(status)){
					message.append("数据审计角色去除失败！");
				}
			}
			//更新添加操作
			String[] roleArray = StringUtils.split(roles.trim(), ",");
			for (String role : roleArray) {
				if("1".equals(role)){
					if(!oldRoles.contains("1")){
						//达尔文注册
						String rtw_orgName = tenant.getBus_name();
						user.setRtw_orgId(rtw_orgId);
						user.setRtw_orgName(rtw_orgName);
						user.setTotalSpace("1");
						user.setRtw_roleId("2");
						int status = registerService.registDaerWen(user);
						if(status == 0){
							message.append("数据作业角色添加失败！");
						}
					}
					continue;
				}
				if("2".equals(role)){
					if(!oldRoles.contains("2")){
						//数据挖掘注册
						user.setAdmin(0);
						user.setSex(1);
						user.setRoleIds("m02");
						user.setQq(RandomUtils.genRandomNum(10));
						int status = registerService.registWaJue(user);
						if(status == 0){
							message.append("数据挖掘角色添加失败！");
						}
					}
					continue;
				}
				if("3".equals(role)){
					if(!oldRoles.contains("3")){
						//gataway注册
						user.setUserType("dataUser");
						user.setCertNo(RandomUtils.genRandomNum(18));
						user.setFilePath("operatorRegister");
						user.setOrgId(gateway_orgId);
						int status = registerService.registShenJi(user);
						if(status == 0){
							message.append("数据审计角色添加失败！");
						}
					}
					continue;
				}
			}
		}
		return message.toString();
	};

	//四个平台删除用户
	public String deletUser(Operator operator,String roles) throws Exception{
		//三个平台的删除用户url
		PropertiesUtils.setProperties("/app.properties");
		String deletDrwUrl = PropertiesUtils.getPropertiesValue("deletDrwUrl");//达尔文
		String deletWjUrl = PropertiesUtils.getPropertiesValue("deletWjUrl");//数据挖掘
		String deletGtwUrl = PropertiesUtils.getPropertiesValue("deletGtwUrl");//数据审计
		//获取值
		String id = String.valueOf(operator.getId());
		String user_id = operator.getUser_id();
		Operator oldCreateOperator = operatorMapper.findCreateOperatorLogByUserId(user_id);
		String loginName = PinyinUtils.chineseToPinYinF(oldCreateOperator.getUser_name())+id;
		StringBuffer message = new StringBuffer();
		message.append("操作成功！");
		int deleteUser = registerService.deleteUser(loginName);
		if(deleteUser == 0){
			message.append("一级平台删除用户失败！");
		}
		if(StringUtils.isNotBlank(roles)){
			String[] oldRoleArray = StringUtils.split(roles.trim(), ",");
			for (String oldRole : oldRoleArray) {
				if("1".equals(oldRole)){
					//调用达尔文删除
					String json = HttpUtils.executePost(deletDrwUrl, loginName);
					JSONObject jsonObject = JSONObject.fromObject(json);
					String status = jsonObject.get("status").toString().trim();
					if("0".equals(status)){
						message.append("数据作业删除用户失败！");
					}
					continue;
				}
				if("2".equals(oldRole)){
					//调用数据挖掘删除
					String json = HttpUtils.executePost(deletWjUrl, loginName);
					JSONObject jsonObject = JSONObject.fromObject(json);
					String status = jsonObject.get("status").toString().trim();
					if("0".equals(status)){
						message.append("数据挖掘删除用户失败！");
					}
					continue;
				}
				if("3".equals(oldRole)){
					//调用gataway删除
					String json = HttpUtils.executePost(deletGtwUrl, loginName);
					JSONObject jsonObject = JSONObject.fromObject(json);
					String status = jsonObject.get("status").toString().trim();
					if("0".equals(status)){
						message.append("数据审计删除用户失败！");
					}
					continue;
				}
			}
		}
		return message.toString();
	}
	
	
	public void send_LESSEE_ACCOUNT_AUTH_NOICE_SERVICE(Operator operator,OperatorRole operatorRole,String message) throws Exception{
		//传递的参数
		PropertiesUtils.setProperties("/app.properties");
		//服务名称
		String service_name = "LESSEE_ACCOUNT_AUTH_NOICE_SERVICE";
		//流水号为六位随机数
		//String session_id = RandomUtils.genRandomNum(6);
		String session_id = operator.getSerial_num();
		//编码格式
		String input_charset = "utf-8";
		//签名钥匙
		String sign_key = PropertiesUtils.getPropertiesValue("sign_key");
		//操作人员ID
		String user_id = operator.getUser_id();
		//审核状态
		String result_flag = operator.getOperator_check();
		//来源应用平台ID
		//String system_id = PropertiesUtils.getPropertiesValue("system_id");
		String system_id = operator.getSystem_id();
		//权限账号信息JASON对象
		//操作员账号
		String account = PinyinUtils.chineseToPinYinF(operator.getUser_name())+operator.getId();
		String result = "[]";
		Gson gs = new Gson();
		if("1".equals(result_flag)){
			String roles = operatorRole.getRoles();
			if(StringUtils.isNotBlank(roles)){
				String url = PropertiesUtils.getPropertiesValue("yijiUrl");
				List <Map<String,String>> rolesList = new ArrayList<Map<String,String>>();
				String[] roleArray = roles.split(",");
				for (String role : roleArray) {
					Map <String, String> roleMap = new HashMap <String, String>();
					roleMap.put("role", role);
					roleMap.put("account", account);
					roleMap.put("url", url);
					rolesList.add(roleMap);
				}
				result = gs.toJson(rolesList);
			}
		}
		Map <String, String> erjiMap = new HashMap <String, String>();
		erjiMap.put("service_name", service_name);
		erjiMap.put("session_id", session_id);
		erjiMap.put("input_charset", input_charset);
		//regMap.put("sign_key", "1234567890");
		erjiMap.put("user_id", user_id);
		erjiMap.put("result_flag", result_flag);
		erjiMap.put("system_id", system_id);
		if("1".equals(result_flag)){
			if(!"3".equals(operator.getOperate_type())){
				erjiMap.put("result", result);
			}
		}else{
			erjiMap.put("message", message);
		}
		//String regJson = gs.toJson(regMap);
		String regJson = ResponseUtils.mapToString(erjiMap);
		//加密
		String sign = MD5.sign(regJson, sign_key, "utf-8");
		erjiMap.put("sign_key", sign);
		logger.info("调用二级平台接口传递参数：" + regJson);
		//调用二级接口
		String regUrl = PropertiesUtils.getPropertiesValue("erjiUrl");
		String rspErji = ErjiHttpUtil.readStream(ErjiHttpUtil.doPostForStream(regUrl, erjiMap),"utf-8");
		logger.info("二级平台接口返回参数：" + rspErji);
		
	}


	public void closeOperator(Operator operator) {
		operator.setStatus("0");
		operatorMapper.updateOperator(operator);
	}

	public Map<String, Object> findOperatorLog(QueryOperator queryOperator) {
		//总条数
		int totalCount = operatorMapper.findOperatorLogNumber(queryOperator);
		queryOperator.setTotalCount(totalCount);
		QueryOperator fenYeQueryOperator = fenYe(queryOperator);
		Map<String,Object> map = new HashMap<String,Object>();
		//当前页
		map.put("currentPage", fenYeQueryOperator.getCurrentPage());
		//总页数
		map.put("totalPage", fenYeQueryOperator.getTotalPage());
		//总条数
		map.put("totalCount", totalCount);
		//去空操作
		String bus_name = fenYeQueryOperator.getBus_name();
		String user_name = fenYeQueryOperator.getUser_name();
		if(bus_name != null){
			fenYeQueryOperator.setBus_name(bus_name.trim());
		}
		if(user_name != null){
			fenYeQueryOperator.setUser_name(user_name.trim());
		}
		List<QueryOperator> queryOperatorList = operatorMapper.findOperatorLog(fenYeQueryOperator);
		map.put("data", queryOperatorList);
		return map;
	}
	
	
	public Operator findOperatorLogDetails(Long id) {
		Operator operatorLogDetails = operatorMapper.findOperatorLogById(id);
		return operatorLogDetails;
	}
	
	
	public Map<String, Object> findOperator(QueryOperator queryOperator) {
		String bus_id = queryOperator.getBus_id();
		//总条数
		int totalCount = operatorMapper.findOperatorNumberByBusId(bus_id);
		queryOperator.setTotalCount(totalCount);
		QueryOperator fenYeQueryOperator = fenYe(queryOperator);
		Map<String,Object> map = new HashMap<String,Object>();
		//当前页
		map.put("currentPage", fenYeQueryOperator.getCurrentPage());
		//总页数
		map.put("totalPage", fenYeQueryOperator.getTotalPage());
		//总条数
		map.put("totalCount", totalCount);
		List<QueryOperator> queryOperatorList = operatorMapper.findOperator(fenYeQueryOperator);
		map.put("data", queryOperatorList);
		return map;
	}
	
	
	
	
	
	public QueryOperator fenYe(QueryOperator queryOperator){
		//总条数
		Integer totalCount = queryOperator.getTotalCount();
		
		//设置每页条数
		Integer pageSize = queryOperator.getPageSize();
		if(pageSize == null || pageSize < 1){
			pageSize = 10;
			queryOperator.setPageSize(pageSize);
		}
		
		//总页数
		Integer totalPage = (int)(Math.ceil((double)totalCount/(double)pageSize));
		queryOperator.setTotalPage(totalPage);
		
		//当前页
		Integer currentPage = queryOperator.getCurrentPage();
		if(currentPage == null || currentPage < 1){
			currentPage = 1;
			queryOperator.setCurrentPage(currentPage);
		}
		
		//数据库查询起始位置
		Integer begin = (currentPage - 1) * pageSize;
		queryOperator.setBegin(begin);
		
		return queryOperator;
	}


}
