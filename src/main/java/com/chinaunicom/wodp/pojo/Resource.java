package com.chinaunicom.wodp.pojo;

import java.io.Serializable;
import java.util.Date;

public class Resource implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;//表id
	private String tenant_id; //商户表id
	private String disk; //磁盘空间
	private String serial_num; //流水号
	private String system_id; //来源平台id
	private String operate_type; //操作类型（新增/修改）
	private Date create_time;//创建时间
	private Date check_time;//审核时间（新增的时候是创建的时间一样，提交审核时，修改该字段）
	private String resource_check;//1:审核通过0:审核不通过2:待审核
	private String check_desc;//审核描述
	//大数据平台传入的字段
	
	private String bus_name;
	private String bus_id;
	private String phone_no;
	private String data_resource;//Json串：[{“data_service_name”:“***********” },{“data_service_name”:“***********” }]
	
	public String getData_resource() {
		return data_resource;
	}



	public void setData_resource(String data_resource) {
		this.data_resource = data_resource;
	}



	public Resource() {
		super();
	}



	public String getBus_id() {
		return bus_id;
	}


	public void setBus_id(String bus_id) {
		this.bus_id = bus_id;
	}


	public String getCheck_desc() {
		return check_desc;
	}


	public void setCheck_desc(String check_desc) {
		this.check_desc = check_desc;
	}


	public String getBus_name() {
		return bus_name;
	}


	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}


	public String getPhone_no() {
		return phone_no;
	}


	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}


	public Date getCheck_time() {
		return check_time;
	}


	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	public String getDisk() {
		return disk;
	}
	public void setDisk(String disk) {
		this.disk = disk;
	}
	public String getSerial_num() {
		return serial_num;
	}
	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}
	public String getSystem_id() {
		return system_id;
	}
	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}
	public String getOperate_type() {
		return operate_type;
	}
	public void setOperate_type(String operate_type) {
		this.operate_type = operate_type;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getResource_check() {
		return resource_check;
	}
	public void setResource_check(String resource_check) {
		this.resource_check = resource_check;
	}
	
}
