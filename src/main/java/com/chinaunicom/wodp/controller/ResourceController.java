package com.chinaunicom.wodp.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.wodp.mapper.ResourceMapper;
import com.chinaunicom.wodp.mapper.TenantMapper;
import com.chinaunicom.wodp.pojo.DataResource;
import com.chinaunicom.wodp.pojo.Resource;
import com.chinaunicom.wodp.pojo.Tenant;
import com.chinaunicom.wodp.service.ResourceService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	private Logger logger = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private TenantMapper tenantMapper;
	@Autowired
	private ResourceMapper resourceMapper;
	/**
	 * 资源审核页面
	 * @param tenant_id
	 */
	@ResponseBody
	@RequestMapping("/checkedPage")
	public String checkedPage(String tenant_id){
		
			//创建一个list封装返回信息
			ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			Tenant tenant = tenantMapper.selectById(tenant_id);
			Resource resource;
			if(tenant.getResource_log_Id() == null){
				resource = resourceMapper.selectResourceLog(tenant_id);
			}else{
				resource = resourceMapper.selectResourceLogById(tenant.getResource_log_Id());
			}
			
			List<DataResource> datalist = resourceService.selectDataResourceByTenantId(tenant.getResource_log_Id());
			//一行一个资源
			String servicename = "";
//			System.out.println(datalist.size());
			for (DataResource dataResource : datalist) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("disk", resource.getDisk());//磁盘空间
				map.put("resource_check", resource.getResource_check());//审核状态
				servicename =dataResource.getService_name();
				map.put("service_name", servicename);
				list.add(map);
			}
			
			//转化为json数据
			Gson gson = new Gson();
			String json = gson.toJson(list);
			logger.info("资源数据日志表json："+json);
			
			return json;
	}
	/**
	 * 资源审核
	 * @param tenant_id
	 * @param resource_check
	 * @param check_desc
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checked")
	public String resourceChecked(String tenant_id,String resource_check,String check_desc,HttpServletRequest request){
		//封装
		Map <String, String> rspJsonMap = new HashMap <String, String>();
		rspJsonMap.put("result_flag", "0");
        rspJsonMap.put("message", "系统异常");
        Gson gs = new Gson();
		
        try {
			resourceService.resourceChecked(tenant_id, resource_check,check_desc);
			rspJsonMap.put("result_flag", "1");
	        rspJsonMap.put("message", "提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常:", e);
		}
        
		String rspJson = gs.toJson(rspJsonMap);
		logger.info("提交返回数据：" + rspJson);
		
		return rspJson;
	}
	/**
	 * 资源申请
	 * @param json
	 */
	@ResponseBody
	@RequestMapping("/resourceApplication")
	public String resourceApplication(@RequestBody String json){
		logger.info("租户数据资源申请接口请求数据：{}",json);
		//封装
		Map <String, String> rspJsonMap = new HashMap <String, String>();
		rspJsonMap.put("result_flag", "0");
        rspJsonMap.put("message", "系统异常");
        Gson gs = new Gson();
		try {	
			resourceService.resourceApplication(json);
			rspJsonMap.put("result_flag", "1");
	        rspJsonMap.put("message", "提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("资源申请异常:", e);
		}
		String rspJson = gs.toJson(rspJsonMap);
		logger.info("租户数据资源申请接口响应数据：{}" + rspJson);
		return rspJson;
	}
	
}
