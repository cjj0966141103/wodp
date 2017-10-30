package com.chinaunicom.wodp.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinaunicom.wodp.mapper.OperatorMapper;
import com.chinaunicom.wodp.mapper.ResourceMapper;
import com.chinaunicom.wodp.mapper.TenantMapper;
import com.chinaunicom.wodp.pojo.PageBean;
import com.chinaunicom.wodp.pojo.Resource;
import com.chinaunicom.wodp.pojo.Tenant;
import com.chinaunicom.wodp.service.ResourceService;
import com.chinaunicom.wodp.service.TenantService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/tenant")
public class TenantController {
	private Logger logger = LoggerFactory.getLogger(TenantController.class);

	@Autowired
	private TenantService tenantService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private OperatorMapper operatorMapper;
	@Autowired
	private TenantMapper tenantMapper;
	@Autowired
	private ResourceMapper resourceMapper;
	/**
	 * 商户页面的查询
	 * 三张表的查询
	 */
	@RequestMapping("/select")
	public void selectTenant(PageBean pageBean,String bus_name,String resource_check ,HttpServletResponse response){
		try {
		Gson gson = new Gson();
		if(bus_name != null){
		if("".equals(bus_name)){
			bus_name=null;
		}
		}
		if(resource_check != null){
		if("3".equals(resource_check)){
			resource_check=null;
		}
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//创建一个结果list封装返回结果信息
		//map占用内存多，创建bean少点，此处为方便
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		//设置总记录数
		int totalCount = tenantMapper.selectCountByCondition(bus_name, resource_check);
		//封装pageBean
		pageBean.setTotalCount(totalCount);
		pageBean = tenantService.findPage(pageBean);
		//1.查询商户表
		List<Tenant> tenantlist = tenantService.selectByCondition(bus_name, resource_check,pageBean.getBegin(), pageBean.getPageSize());
		for (Tenant tenant : tenantlist) {
			
			HashMap<String,String> map = new HashMap<String, String>();
			map.put("bus_id", tenant.getBus_id());
			map.put("tenant_id", tenant.getId().toString());
			map.put("bus_name", tenant.getBus_name());
			map.put("create_time",sdf.format(tenant.getCreate_time()));
			map.put("status", tenant.getStatus());
			
			//2.查询资源日志表
			//用于资源申请修改操作，只有不是第一次资源申请的时候,resource_log_Id才存在,才用这个id去查找日志表的资源
			if(tenant.getResource_log_Id() != null){
				Resource resource = resourceMapper.selectResourceLogById(tenant.getResource_log_Id());
				if(resource != null){
					map.put("resource_check", resource.getResource_check());
					map.put("check_time", sdf.format(resource.getCheck_time()));
				}
			}else{
				Resource resource = resourceService.selectResourceByTenantId(tenant.getId().toString());
				if(resource != null){
					map.put("resource_check", resource.getResource_check());
					map.put("check_time", sdf.format(resource.getCheck_time()));
				}
			}
			
			//3.查询操作员操作表--操作员个数
			int operatornum = operatorMapper.findOperatorNumberByBusId(tenant.getBus_id());
			map.put("operatornum", String.valueOf(operatornum));
			//将map放入list集合中
			list.add(map);
		}
		pageBean.setListm(list);
		//将list转换为json
		String json = gson.toJson(pageBean);
		logger.info("商户页面表json串："+json);
		
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("页面显示出错",e);
		}
	}
	/**
	 * 保存用户
	 * @param tenant
	 * @param response
	 */
	@RequestMapping("/saveTenant")
	public void saveTenant(Tenant tenant,HttpServletResponse response){
		//保存用户
	}
	
	
}
