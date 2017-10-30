package com.chinaunicom.wodp.pojo;

import java.io.Serializable;
import java.util.Date;

public class OperatorRole implements Serializable {

	private static final long serialVersionUID = 1L;
	//主键id
	private Long id;
	//操作员id
	private Long opId;
	//角色信息
	private String roles;
	//创建时间
	private Date create_time;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "OperatorRole [id=" + id + ", opId=" + opId + ", roles=" + roles + ", create_time=" + create_time + "]";
	}
	
	
	
	
}
