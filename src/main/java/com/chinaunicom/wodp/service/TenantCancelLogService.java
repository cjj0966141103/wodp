package com.chinaunicom.wodp.service;

import java.util.Map;

import com.chinaunicom.wodp.pojo.TenantCancelLog;

/**
 * 商户注销的service类
 * @author wuchaung
 *
 */
public interface TenantCancelLogService {
	/**
	 * 商户注销
	 * @param tenantCancelLog
	 */
	public Map<String,Object> cancel(TenantCancelLog tenantCancelLog);
	
	/**
	 * 保存商户注销日志
	 */
	public void save(TenantCancelLog tenantCancelLog);
}
