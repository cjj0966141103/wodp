package com.chinaunicom.wodp.service;

import java.util.List;

import com.chinaunicom.wodp.pojo.DataResource;
import com.chinaunicom.wodp.pojo.Resource;

public interface ResourceService {
	//根据商户表id查询资源
	public Resource selectResourceByTenantId(String tenant_id);
	//根据商户id查询数据资源
	public List<DataResource> selectDataResourceByTenantId(String resource_log_id);
	//资源申请
	public void resourceApplication(String json) throws Exception;
	//资源审核
	public void resourceChecked(String tenant_id,String resource_check,String check_desc);

}
