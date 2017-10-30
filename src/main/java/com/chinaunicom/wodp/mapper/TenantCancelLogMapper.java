package com.chinaunicom.wodp.mapper;

import com.chinaunicom.wodp.pojo.TenantCancelLog;


public interface TenantCancelLogMapper {
	/**
	 * 保存商户注销记录
	 * @param tenantCancelLog
	 * @return
	 */
	public int save(TenantCancelLog tenantCancelLog);
}
