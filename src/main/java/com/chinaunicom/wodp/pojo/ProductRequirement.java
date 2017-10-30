package com.chinaunicom.wodp.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品需求类
 * @author Administrator
 *
 */
public class ProductRequirement implements Serializable{

	private Integer id;
	private String field_name; //字段名称
	private String field_desc; //字段中文描述
	private String field_type; //字段类型
	private Integer product_id; //产品ID
	private Date create_time; //创建时间

	public ProductRequirement() {
	}

	public ProductRequirement(Integer id, String field_name, String field_desc,
			String field_type, Integer product_id, Date create_time) {
		super();
		this.id = id;
		this.field_name = field_name;
		this.field_desc = field_desc;
		this.field_type = field_type;
		this.product_id = product_id;
		this.create_time = create_time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getField_desc() {
		return field_desc;
	}

	public void setField_desc(String field_desc) {
		this.field_desc = field_desc;
	}

	public String getField_type() {
		return field_type;
	}

	public void setField_type(String field_type) {
		this.field_type = field_type;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "ProductRequirement [id=" + id + ", field_name=" + field_name
				+ ", field_desc=" + field_desc + ", field_type=" + field_type
				+ ", product_id=" + product_id + ", create_time=" + create_time
				+ "]";
	}
}
