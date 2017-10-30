package com.chinaunicom.wodp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinaunicom.wodp.mapper.TenantMapper;
import com.chinaunicom.wodp.pojo.PageBean;
import com.chinaunicom.wodp.pojo.Tenant;
import com.chinaunicom.wodp.service.TenantService;

@Service
@Transactional
public class TenantServiceImpl implements TenantService {

	@Autowired
	private TenantMapper tenantMapper;

		//保存
		public void save(Tenant tenant) {
			tenantMapper.save(tenant);
		}
		
		//查询所有用户
		public List<Tenant> selectAll() {
			return tenantMapper.selectAll();
		}
	
		//分页查询
		public PageBean findPage(PageBean pageBean) {
			//当前页
			int currentPage = pageBean.getCurrentPage();
			//每页记录数
			int pageSize = 15;
			pageBean.setPageSize(pageSize);
			//总记录数
			int totalCount = pageBean.getTotalCount();
			//总页数
//			如果总记录数可以整除每页条数，总页数相除的结果
//			如果总记录数不能整除每页条数，总页数相除的结果+1
			int totalPage = 0 ;
			if(totalCount%pageSize==0) {//整除
				totalPage = totalCount/pageSize;
			} else {//不能整除
				totalPage = totalCount/pageSize+1;
			}
			pageBean.setTotalPage(totalPage);
			//开始位置 （当前页-1）*每页记录数
			int begin = (currentPage-1)*pageSize;
			pageBean.setBegin(begin);
			
			return pageBean;
		}

		//根据bus_id进行查询
		public Tenant selectByBus_id(String bus_id) {
			return tenantMapper.select(bus_id);
		}
		//多条件查询
		public List<Tenant> selectByCondition(String bus_name, String resource_check, int begin, int pageSize) {			
			return tenantMapper.selectByCondition(bus_name, resource_check, begin, pageSize);
		}
		//删除商户
		public void deletTenantByBus_id(String bus_id) {
			tenantMapper.deletTenantByBus_id(bus_id);
		}

}
