package com.chinaunicom.wodp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinaunicom.wodp.pojo.Tenant;

public interface TenantMapper {
	//根据id查询商户表
	public Tenant select(String bus_id);
	//根据商户id查询表id
	public int selectId(String bus_id);
	//根据表id查询商户
	public Tenant selectById(String tenant_id);
	//商户表所有
	public List<Tenant> selectAll();
	//保存商户信息
	public int save(Tenant tenant);
	//多条件查询
	public List<Tenant> selectByCondition(@Param("bus_name")String bus_name,@Param("resource_check")String resource_check,
											@Param("begin")int begin,@Param("pageSize")int pageSize);
	//删除商户
	public void deletTenantByBus_id(String bus_id);
	
	//修改商户详细信息
	public int updateTenant(Tenant tenant);
	//修改商户的resource_log_Id
	public int updateResourceId(Tenant tenant);
	
	//根据条件查询总页数
	public int selectCountByCondition(@Param("bus_name")String bus_name,@Param("resource_check")String resource_check);
	
	
}
