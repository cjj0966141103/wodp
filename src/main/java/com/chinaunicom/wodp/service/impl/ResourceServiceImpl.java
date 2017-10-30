package com.chinaunicom.wodp.service.impl;



import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinaunicom.wodp.mapper.ResourceMapper;
import com.chinaunicom.wodp.mapper.TenantMapper;
import com.chinaunicom.wodp.pojo.DataResource;
import com.chinaunicom.wodp.pojo.Resource;
import com.chinaunicom.wodp.pojo.Tenant;
import com.chinaunicom.wodp.service.ResourceService;
import com.chinaunicom.wodp.utils.ErjiHttpUtil;
import com.chinaunicom.wodp.utils.HttpUtils;
import com.chinaunicom.wodp.utils.MD5;
import com.chinaunicom.wodp.utils.PropertiesUtils;
import com.chinaunicom.wodp.utils.RandomUtils;
import com.chinaunicom.wodp.utils.ResponseUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private TenantMapper tenantMapper;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//查询资源
	public Resource selectResourceByTenantId(String tenant_id) {
		return resourceMapper.selectResourceLog(tenant_id);
	}
	//查询数据资源
	public List<DataResource> selectDataResourceByTenantId(String resource_log_id) {
		return resourceMapper.selectDataResourceLog(resource_log_id);
	}
	
	/**
	 * 资源申请
	 * @throws Exception 
	 */
	public void resourceApplication(String json) throws Exception {
		PropertiesUtils.setProperties("/app.properties");
		Gson gs = new Gson();
		//封装基础数据
		Resource resource = gs.fromJson(json, Resource.class);
		Tenant tenant = gs.fromJson(json, Tenant.class);
		//得到service_name集合
		List<DataResource> serviceNameList = dataFromJson(resource.getData_resource());
		//1.判断用户是否存在
		Tenant t1 = tenantMapper.select(resource.getBus_id());
		int tenantId = 0;
		
		//用户不存在
		if(t1==null){
			logger.info("商户id为:"+resource.getBus_id()+"的用户不存在,正在执行添加...");
			//2.operate_type是新增,用户表进行增加，增加两个日志表
			//1:新增2:修改
			if("1".equals(resource.getOperate_type())){
				
				//调用gateway的注册接口，创建一个机构用户。调用达尔文接口，创建一个机构
				Map <String, String> orgMap = new HashMap <String, String>();
				orgMap.put("bus_name", resource.getBus_name());
				orgMap.put("bus_id", resource.getBus_id());
				String gatewayURL = PropertiesUtils.getPropertiesValue("gatewayURL");
				String daerwenURL = PropertiesUtils.getPropertiesValue("daerwenURL");
				String orgJson = gs.toJson(orgMap);
				logger.info("传递于达尔文和gateway的参数：" + orgJson);
				//调用二级接口,封装orgid
				String gateResponseBodyOrg;
				String daerwenResponseBodyOrg;
				gateResponseBodyOrg = HttpUtils.executePost(gatewayURL, orgJson);
				logger.info("gateway接口返回参数：" + gateResponseBodyOrg);
				daerwenResponseBodyOrg = HttpUtils.executePost(daerwenURL, orgJson);
				logger.info("达尔文接口返回参数：" + daerwenResponseBodyOrg);
				String darwin_orgId = toMap(daerwenResponseBodyOrg).get("darwin_orgId");
				String gateway_orgId = toMap(gateResponseBodyOrg).get("orgId");
				tenant.setDarwin_orgId(darwin_orgId);
				tenant.setGateway_orgId(gateway_orgId);
				
				//保存三张表
				//资源日志表
				resourceMapper.saveResourceLog(resource);
				//商户表
				tenant.setResource_log_Id(String.valueOf(resource.getId()));
				tenantMapper.save(tenant);
				tenantId = tenantMapper.selectId(resource.getBus_id());
				resource.setTenant_id(String.valueOf(tenantId));
				
				//遍历插入DataResourceLog表
				for (DataResource dR : serviceNameList) {
					dR.setTenant_id(String.valueOf(tenantId));
					dR.setService_name(dR.getData_service_name());
					dR.setResource_log_id(resource.getId());
					resourceMapper.saveDataResourceLog(dR);
				}
				
		}else{
			logger.info("用户不存在，无法修改，Operate_type传入错误！");
		}
		}//用户已存在
		else{
			tenantId = tenantMapper.selectId(resource.getBus_id());
			resource.setTenant_id(String.valueOf(tenantId));
			//修改
			if("2".equals(resource.getOperate_type())){
				
			    //resourceMapper.deleteResourceLogByTenantId(String.valueOf(tenantId));
				//添加resource_log表
				resourceMapper.saveResourceLog(resource);
				//得到新增资源日志数据id
				tenant.setResource_log_Id(String.valueOf(resource.getId()));
				//更改tenant表resource_log_Id字段
				tenantMapper.updateResourceId(tenant);
				//删除原有的资源对照表
//				resourceMapper.deleteDataResourceLogByTenantId(String.valueOf(tenantId));
				//遍历插入DataResourceLog
				for (DataResource dR : serviceNameList) {
					dR.setTenant_id(String.valueOf(tenantId));
					dR.setService_name(dR.getData_service_name());
					dR.setResource_log_id(resource.getId());
					resourceMapper.saveDataResourceLog(dR);
				}
				

			}else{
				logger.info("用户已经存在，Operate_type传入错误！");
			}
		}
	}
	//资源审核
	public void resourceChecked(String tenant_id, String resource_check,String check_desc) {
		
		
		Tenant tenant = tenantMapper.selectById(tenant_id);
		List<DataResource> dataResourceList = resourceMapper.selectDataResourceLog(tenant.getResource_log_Id());
		Resource resource;
		//新增
		
			resource = resourceMapper.selectResourceLogById(tenant.getResource_log_Id());
			if (resource !=null) {
			    if (resource_check!=null) {
	                resource.setResource_check(resource_check);
	                
	            }
	            if (tenant_id!=null) {
	                resource.setTenant_id(tenant_id);
	            }
	            if (check_desc != null) {
	                resource.setCheck_desc(check_desc);
	            }
	        
	        
	        //新增:审核通过后在资源表和数据权限表里增加记录
	        if("1".equals(resource.getOperate_type())){
	            //审核通过
	            if("1".equals(resource_check)){
	                
	                resourceMapper.updateResourceLog(resource);
	                resourceMapper.saveResource(resource);
	                //遍历保存DataResource
	                for (DataResource dR : dataResourceList) {
	                    resourceMapper.saveDataResource(dR);
	                }
	                //调用数据平台接口
	                send_LESSEE_DATA_AUTH_NOTICE_SERVICE(resource);
	            }else{
	                logger.info("资源审核resource_check传入错误！");
	                //调用数据平台接口
	                if(resource != null){
	                    send_LESSEE_DATA_AUTH_NOTICE_SERVICE(resource);
	                }
	            }
	        }//修改:审核通过后先删除原有资源表和数据权限表里的记录，再新增记录。
	        else if("2".equals(resource.getOperate_type())){
	            //审核通过
	            if("1".equals(resource_check)){
	                resourceMapper.updateResourceLog(resource);
	                resourceMapper.deleteResourceByTenantId(tenant_id);
	                resourceMapper.saveResource(resource);
	                resourceMapper.deleteDataResourceByTenantId(tenant_id);
	                //遍历保存DataResource
	                for (DataResource dR : dataResourceList) {
	                    resourceMapper.saveDataResource(dR);
	                }
	                //调用数据平台接口
	                if(resource != null){
	                    send_LESSEE_DATA_AUTH_NOTICE_SERVICE(resource);
	                }
	            }else{
	                logger.info("资源审核resource_check传入错误！");
	                //调用数据平台接口
	                send_LESSEE_DATA_AUTH_NOTICE_SERVICE(resource);
	            }
	        }else{
	            logger.info("操作类型  新增/删除: 1/2传入错误！");
	            //调用数据平台接口
	            if(resource != null){
	                send_LESSEE_DATA_AUTH_NOTICE_SERVICE(resource);
	            }
	        }
            }
	}
	
	/**
	 * 将json转换为list
	 * @param json
	 * @return
	 */
	public List<DataResource> dataFromJson(String json) {
        Type listType = new TypeToken<List<DataResource>>() {
        }.getType();
        Gson gson = new Gson();
        List<DataResource> list = gson.fromJson(json, listType);
        return list;
    }
	/**
	 * json转换为map
	 * @param json
	 * @return
	 */
	public Map<String, String> toMap(String json){
	       GsonBuilder gb = new GsonBuilder();
	       Gson g = gb.create();
	       Map<String, String> map = g.fromJson(json, new TypeToken<Map<String, String>>() {}.getType());
	       return map;
	   }
	
	/**
	 * 租户数据资源申请结果接口
	 * @param resource
	 */
	public void send_LESSEE_DATA_AUTH_NOTICE_SERVICE(Resource resource){
		//1.封装调用接口参数
		//传递的参数
		PropertiesUtils.setProperties("/app.properties");
		//服务名称
		String service_name = "LESSEE_DATA_AUTH_NOTICE_SERVICE";
		//流水号为六位随机数
		int randomNumber = RandomUtils.randomNumber();
		String session_id = String.valueOf(randomNumber);
		//编码格式
		String input_charset = "utf-8";
		//签名钥匙
		String sign_key = PropertiesUtils.getPropertiesValue("sign_key");
		//操作人员ID
		String tenant_id = resource.getTenant_id();
		Tenant tenant = tenantMapper.selectById(tenant_id);
		String user_id = tenant.getBus_id();

		Gson gs = new Gson();
		//数据权限申请结果JASON对象
		List<DataResource> dataResourceList = resourceMapper.selectDataResourceLog(Integer.toString(resource.getId()));
		//审核状态
		String result_flag = resource.getResource_check().equals("0")?"2":"1";
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		String data_service_name="";
		for (DataResource dR : dataResourceList) {
			data_service_name = dR.getService_name();
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("data_service_name", data_service_name);
			map.put("result_flag", result_flag);
			list.add(map);
		}
		String pem_info =gs.toJson(list);
		logger.info("pem_info:"+pem_info);
		//资源名称
		//来源应用平台ID/
		String system_id = PropertiesUtils.getPropertiesValue("system_id");
		//封装调用接口的json
		Map <String, String> respMap = new HashMap <String, String>();
		respMap.put("service_name", service_name);
		respMap.put("session_id", session_id);
		respMap.put("input_charset", input_charset);
		respMap.put("user_id", user_id);
		respMap.put("result_flag", result_flag);
	//	respMap.put("message", resource.getCheck_desc());
		respMap.put("pem_info", pem_info);
		respMap.put("system_id", system_id);
		
		//字符串拼接
		String respJson = ResponseUtils.mapToString(respMap);
		//String respJson = gs.toJson(respMap);
		String sign = MD5.sign(respJson, sign_key, "utf-8");
		respMap.put("sign_key", sign);	
		logger.info("调用二级平台接口传递参数：" + respJson);
		String regUrl = PropertiesUtils.getPropertiesValue("erjiUrl");
		//2.调用二级接口
		try {
			//String responseBody = HttpUtils.executePost(regUrl, respJson);
			String rspErji = ErjiHttpUtil.readStream(ErjiHttpUtil.doPostForStream(regUrl, respMap),"utf-8");
			logger.info("二级平台接口返回参数：" + rspErji);
		} catch (Exception e) {
			logger.error("异常:", e);
		}
		
	}
}
