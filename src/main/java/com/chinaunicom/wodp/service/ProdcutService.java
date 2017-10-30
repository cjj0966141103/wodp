package com.chinaunicom.wodp.service;

import com.chinaunicom.wodp.pojo.Product;
/**
 * 产品信息的service类
 * @author wuchaung
 *
 */
public interface ProdcutService {
	/**
	 * 保存产品信息
	 * @param product
	 * @param requirements
	 */
	public void save(Product product);
	
	/**
	 * 查询产品id是否存在
	 * @param product_id
	 * @return 存在返回true 不存在返回false
	 */
	public boolean checkProductId(String product_id);
}
