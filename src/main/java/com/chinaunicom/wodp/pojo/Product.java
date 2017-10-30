package com.chinaunicom.wodp.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 产品类
 * @author Administrator
 *
 */
public class Product implements Serializable {

	private Integer id;
	private String product_id; //产品ID
	private String product_name; //产品名称
	private String product_type; //产品类型（1：商户结果数据，2：联通原始数据，3：应用数据）
	private String bus_id; //商户ID（二级平台）
	private String serial_num; //流水号
	private String other_requirement; //其他需求
	private Date create_time;
	private String data_resources; //产品需求JASON对象
	private List<ProductRequirement> requirements;
	
	public Product() {
	}

	public Product(Integer id, String product_id, String product_name,
			String product_type, String bus_id, String serial_num,
			String other_requirement, Date create_time, String data_resources,
			List<ProductRequirement> requirements) {
		super();
		this.id = id;
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_type = product_type;
		this.bus_id = bus_id;
		this.serial_num = serial_num;
		this.other_requirement = other_requirement;
		this.create_time = create_time;
		this.data_resources = data_resources;
		this.requirements = requirements;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getBus_id() {
		return bus_id;
	}

	public void setBus_id(String bus_id) {
		this.bus_id = bus_id;
	}

	public String getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public String getOther_requirement() {
		return other_requirement;
	}

	public void setOther_requirement(String other_requirement) {
		this.other_requirement = other_requirement;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getData_resources() {
		return data_resources;
	}

	public void setData_resources(String data_resources) {
		this.data_resources = data_resources;
	}

	public List<ProductRequirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<ProductRequirement> requirements) {
		this.requirements = requirements;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", product_id=" + product_id
				+ ", product_name=" + product_name + ", product_type="
				+ product_type + ", bus_id=" + bus_id + ", serial_num="
				+ serial_num + ", other_requirement=" + other_requirement
				+ ", create_time=" + create_time + ", data_resources="
				+ data_resources + ", requirements=" + requirements + "]";
	}

	

}
