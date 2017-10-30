package com.chinaunicom.wodp.mapper;

import java.util.List;

import com.chinaunicom.wodp.pojo.DataResource;
import com.chinaunicom.wodp.pojo.Resource;

public interface ResourceMapper {
	//增加
	public void saveResourceLog(Resource resource);
	public int saveResource(Resource resource);
	public void saveDataResourceLog(DataResource dataResource);
	public void saveDataResource(DataResource dataResource);
	//查询
	public Resource selectResourceLog(String tenant_id);
	public List<DataResource> selectDataResourceLog(String tenant_id);
	public Resource selectResource(String tenant_id);
	public List<DataResource> selectDataResource(String tenant_id);
	public Resource selectResourceLogById(String id);
	//删除
	public void deleteResourceLogByTenantId(String tenant_id);
	public void deleteResourceByTenantId(String tenant_id);
	public void deleteDataResourceLogByTenantId(String tenant_id);
	public void deleteDataResourceByTenantId(String tenant_id);
	//修改
	public int updateResource(Resource resource);
	public int updateResourceLog(Resource resource);
	public int updateDataResource(DataResource dataResource);
	public int updateDataResourceLog(DataResource dataResource);
}
