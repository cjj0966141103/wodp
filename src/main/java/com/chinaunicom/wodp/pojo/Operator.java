package com.chinaunicom.wodp.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Operator implements Serializable {

	private static final long serialVersionUID = 1L;
	//主键id
	private Long id;
	//商户id
	private String bus_id;
	//操作员id
	private String user_id;
	//操作员姓名
	private String user_name;
	//电话号码
	private String phone_no;
	//流水号
	private String serial_num;
	//邮箱地址
	private String email;
	//操作类型
	private String operate_type;
	//来源应用平台id
	private String system_id;
	//操作员审核状态
	private String operator_check;
	//创建时间
	private Date create_time;
	//审核时间
	private Date check_time;
	//用户状态
	private String status;
	
	//用户角色
	private String roles;
	
	//页面格式化时间
	//创建时间
	private String page_create_time;
	//审核时间
	private String page_check_time;
	
	
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBus_id() {
		return bus_id;
	}
	public void setBus_id(String bus_id) {
		this.bus_id = bus_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getSerial_num() {
		return serial_num;
	}
	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOperate_type() {
		return operate_type;
	}
	public void setOperate_type(String operate_type) {
		this.operate_type = operate_type;
	}
	public String getSystem_id() {
		return system_id;
	}
	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}
	public String getOperator_check() {
		return operator_check;
	}
	public void setOperator_check(String operator_check) {
		this.operator_check = operator_check;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
		if(create_time != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			this.page_create_time = sdf.format(create_time);
		}
	}
	public Date getCheck_time() {
		return check_time;
	}
	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
		if(check_time != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			this.page_check_time = sdf.format(check_time);
		}
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Operator [id=" + id + ", bus_id=" + bus_id + ", user_id=" + user_id + ", user_name=" + user_name
				+ ", phone_no=" + phone_no + ", serial_num=" + serial_num + ", email=" + email + ", operate_type="
				+ operate_type + ", system_id=" + system_id + ", operator_check=" + operator_check + ", create_time="
				+ create_time + ", check_time=" + check_time + ", status=" + status + ", roles=" + roles
				+ ", page_create_time=" + page_create_time + ", page_check_time=" + page_check_time + "]";
	}
	
	
	
	
}
