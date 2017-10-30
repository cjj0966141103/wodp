package com.chinaunicom.wodp.mapper;

import com.chinaunicom.wodp.pojo.ProductRequirement;

public interface ProductRequirementMapper {
	/**
	 * 保存产品需求信息
	 * @param product
	 * @return
	 */
	public int save(ProductRequirement productRequirement);
}
