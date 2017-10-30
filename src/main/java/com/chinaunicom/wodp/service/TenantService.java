package com.chinaunicom.wodp.service;


import java.util.List;

import com.chinaunicom.wodp.pojo.PageBean;
import com.chinaunicom.wodp.pojo.Tenant;

public interface TenantService {
	//保存商户
	public void save(Tenant tenant);
	//查询商户
	public List<Tenant> selectAll();
	//分页
	public PageBean findPage(PageBean pageBean);
	//根据商户id查询
	public Tenant selectByBus_id(String bus_id);
	//条件查询
	public List<Tenant> selectByCondition(String bus_name,String resource_check,int begin,int pageSize);
	//删除商户
	public void deletTenantByBus_id(String bus_id);

}
