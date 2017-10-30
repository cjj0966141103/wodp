package com.chinaunicom.wodp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinaunicom.wodp.mapper.ProductMapper;
import com.chinaunicom.wodp.mapper.ProductRequirementMapper;
import com.chinaunicom.wodp.pojo.Product;
import com.chinaunicom.wodp.pojo.ProductRequirement;
import com.chinaunicom.wodp.service.ProdcutService;

@Service
@Transactional
public class ProductServiceImpl implements ProdcutService {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductRequirementMapper productRequirementMapper;
	
	//保存产品和产品需求信息
	public void save(Product product) {
		Date date = new Date();
		//设置创建时间
		product.setCreate_time(date);
		//保存产品信息，并返回id
		productMapper.save(product);
		//获取产品需求集合
		List<ProductRequirement> requirements = product.getRequirements();
		//遍历需求信息
		for (ProductRequirement productRequirement : requirements) {
			//设置创建时间
			productRequirement.setCreate_time(date);
			//设置产品id
			productRequirement.setProduct_id(product.getId());
			//保存产品需求
			productRequirementMapper.save(productRequirement);
		}
	}

	//查询产品id是否存在
	public boolean checkProductId(String product_id) {
		List<Product> list = productMapper.findByProductId(product_id);
		//存在返回true 不存在返回false
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}

}
