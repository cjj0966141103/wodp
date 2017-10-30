package com.chinaunicom.wodp.mapper;

import java.util.List;

import com.chinaunicom.wodp.pojo.Product;

public interface ProductMapper {

	/**
	 * 保存产品信息
	 * @param product
	 * @return
	 */
	public int save(Product product);
	
	/**
	 * 根据产品id 查询产品
	 * @param product_id
	 * @return
	 */
	public List<Product> findByProductId(String product_id);
}
