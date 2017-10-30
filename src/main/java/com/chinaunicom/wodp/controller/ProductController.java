package com.chinaunicom.wodp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.wodp.pojo.Product;
import com.chinaunicom.wodp.pojo.ProductRequirement;
import com.chinaunicom.wodp.service.ProdcutService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("product")
public class ProductController {
	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProdcutService prodcutService;

	@RequestMapping("/addProduct")
	public @ResponseBody String saveProduct(@RequestBody String json) {
		Gson gson = new Gson();
		//创建一个map封装返回结果信息
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			logger.info("产品申请信息：" + json);
			// 把json数据转成product对象
			Product product = gson.fromJson(json, Product.class);
			// 非空判断
			if(null == product){
				map.put("result_flag", 0);
				map.put("message", "没有产品信息");
				String responseJson = gson.toJson(map);
				logger.info("产品申请失败："+responseJson);
				return responseJson;
			}
			// 判断是否有产品id
			if (StringUtils.isBlank(product.getProduct_id())) {
				map.put("result_flag", 0);
				map.put("message", "没有产品id");
				String responseJson = gson.toJson(map);
				logger.info("产品申请失败："+responseJson);
				return responseJson;
			}
			//判断该产品是否已经注册
			if(prodcutService.checkProductId(product.getProduct_id())){
				map.put("result_flag", 0);
				map.put("message", "产品id已经注册");
				String responseJson = gson.toJson(map);
				logger.info("产品申请失败："+responseJson);
				return responseJson;
			}
			// 判断是否有产品类型
			if (StringUtils.isBlank(product.getProduct_type())) {
				map.put("result_flag", 0);
				map.put("message", "没有产品类型");
				String responseJson = gson.toJson(map);
				logger.info("产品申请失败："+responseJson);
				return responseJson;
			}
			// 判断是否有商户id
			if (StringUtils.isBlank(product.getBus_id())) {
				map.put("result_flag", 0);
				map.put("message", "没有商户id");
				String responseJson = gson.toJson(map);
				logger.info("产品申请失败："+responseJson);
				return responseJson;
			}
			// 判断是否有流水号
			if (StringUtils.isBlank(product.getSerial_num())) {
				map.put("result_flag", 0);
				map.put("message", "没有流水号");
				String responseJson = gson.toJson(map);
				logger.info("产品申请失败："+responseJson);
				return responseJson;
			}
			// 判断是否有产品需求
			String data_resources= product.getData_resources();
			if (StringUtils.isBlank(data_resources)) {
				map.put("result_flag", 0);
				map.put("message", "没有产品需求");
				String responseJson = gson.toJson(map);
				logger.info("产品申请失败："+responseJson);
				return responseJson;
			}
			List<ProductRequirement> list = gson.fromJson(data_resources, new TypeToken<List<ProductRequirement>>() {  
            }.getType());
			if (null == list || list.size()<1) {
				map.put("result_flag", 0);
				map.put("message", "没有产品需求");
				String responseJson = gson.toJson(map);
				logger.info("产品申请失败："+responseJson);
				return responseJson;
			}
			product.setRequirements(list);
			//调用service方法，保存产品信息
			prodcutService.save(product);
			map.put("result_flag", 1);
			map.put("message", "产品申请已受理");
			String responseJson = gson.toJson(map);
			logger.info("产品申请成功："+responseJson);
			return responseJson;
		} catch (Exception e) {
			map.put("result_flag", 0);
			map.put("message", "系统异常");
			String responseJson = gson.toJson(map);
			logger.error("产品申请失败："+responseJson,e);
			return responseJson;
		} 
	}
}
