package com.chinaunicom.wodp.pojo;

import java.io.Serializable;
import java.util.Date;

public class DataResource implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;//表id
	private String tenant_id; //商户表id
	private String service_name; //商户名称
	private Date create_time;//创建时间
	private int resource_log_id;
	public int getResource_log_id() {
		return resource_log_id;
	}


	public void setResource_log_id(int resource_log_id) {
		this.resource_log_id = resource_log_id;
	}
	//接口修改后的名称
	private String data_service_name; //商户名称
	public DataResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public String getData_service_name() {
		return data_service_name;
	}



	public void setData_service_name(String data_service_name) {
		this.data_service_name = data_service_name;
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
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	
	
	
}
