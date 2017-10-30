package com.chinaunicom.wodp.pojo;

import java.io.Serializable;
import java.util.Date;

public class Tenant implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;//表id
	private String bus_id; //商户id
	private String bus_name; //商户名称
	private String phone_no; //电话号码
	private String status; //商户是否可用（1:可用 0:不可用）
	private Date create_time;//创建时间
	private String darwin_orgId;//组织机构id
	private String gateway_orgId;//组织机构id
	private String resource_log_Id;//资源日志表id
	


	public Tenant() {
		super();
	}

	public String getResource_log_Id() {
		return resource_log_Id;
	}


	public void setResource_log_Id(String resource_log_Id) {
		this.resource_log_Id = resource_log_Id;
	}

	public String getDarwin_orgId() {
		return darwin_orgId;
	}


	public void setDarwin_orgId(String darwin_orgId) {
		this.darwin_orgId = darwin_orgId;
	}


	public String getGateway_orgId() {
		return gateway_orgId;
	}


	public void setGateway_orgId(String gateway_orgId) {
		this.gateway_orgId = gateway_orgId;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBus_id() {
		return bus_id;
	}
	public void setBus_id(String bus_id) {
		this.bus_id = bus_id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	
}
