package com.chinaunicom.wodp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinaunicom.wodp.mapper.TenantCancelLogMapper;
import com.chinaunicom.wodp.pojo.Operator;
import com.chinaunicom.wodp.pojo.Tenant;
import com.chinaunicom.wodp.pojo.TenantCancelLog;
import com.chinaunicom.wodp.service.OperatorService;
import com.chinaunicom.wodp.service.TenantCancelLogService;
import com.chinaunicom.wodp.service.TenantService;

@Service
@Transactional
public class TenantCancelLogServiceImpl implements TenantCancelLogService {
	
	@Autowired
	private TenantService tenantService;

	@Autowired
	private OperatorService operatorService;
	
	@Autowired
	private TenantCancelLogMapper tenantCancelLogMapper;

	public Map<String,Object> cancel(TenantCancelLog tenantCancelLog) {
		//创建结果封装对象
		Map<String,Object> map = new HashMap<String, Object>();
		//获取二级平台商户id
		String bus_id = tenantCancelLog.getBus_id();
		//根据二级平台商户id查询商户
		Tenant tenant = tenantService.selectByBus_id(bus_id);
		//判断商户是否存在，不存在返回
		if(null == tenant){
			map.put("result_flag", 0);
			map.put("message", "商户不存在");
			//设置日志信息
			tenantCancelLog.setStatus(0);
			tenantCancelLog.setMessage("商户不存在");
			//保存日志
			this.save(tenantCancelLog);
			return map;
		}
		//调用tenantService的商户注销方法
		tenantService.deletTenantByBus_id(bus_id);
		//调用operatorService的注销方法
		Operator operator = new Operator();
		operator.setBus_id(bus_id);
		operatorService.closeOperator(operator);
		//根据返回结果设置
		map.put("result_flag", 1);
		map.put("message", "商户注销成功");
		//设置日志信息
		tenantCancelLog.setStatus(1);
		tenantCancelLog.setMessage("商户注销成功");
		//保存日志
		this.save(tenantCancelLog);
		return map;
	}

	//保存商户注销日志
	public void save(TenantCancelLog tenantCancelLog) {
		//设置创建时间
		tenantCancelLog.setCreate_time(new Date());
		//保存
		tenantCancelLogMapper.save(tenantCancelLog);
	}
}
