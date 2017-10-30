package com.chinaunicom.wodp.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

//工单 实体类
public class WorkBill {

	private Integer id; // 自增主键
	private String bus_id; // 商户ID
	private String product_id; // 商品id
	private String work_no; // 工单号
	private String max_num; // 最大输出行数,0表示全部输出,大于零整数表示
	private String serial_num; // 流水号
	private String times; // 时间段格式yyyy-mm-dd
	private String system_id; // 来源应用平台Id
	private Integer status = 0; // 工单状态默认未受理 0表示未受理 1表示处理成功 2表示处理失败
	private Date createTime;  //工单创建时间
	private String page_createTime;

	

	private String product_name; // 产品名称
	private String bus_name; // 商户名称

	// ftp信息
	private String ip; // 服务器ip地址
	private String port; // 端口号
	private String user; // ftp登录用户
	private String password; // ftp用户密码(明文)
	private String file_path; // 目录
	

	public String getPage_createTime() {
		return page_createTime;
	}

	public void setPage_createTime(String page_createTime) {
		this.page_createTime = page_createTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if(createTime != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			this.page_createTime = sdf.format(createTime);
		}
	}

	

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getBus_name() {
		return bus_name;
	}

	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getWork_no() {
		return work_no;
	}

	public void setWork_no(String work_no) {
		this.work_no = work_no;
	}

	public String getMax_num() {
		return max_num;
	}

	public void setMax_num(String max_num) {
		this.max_num = max_num;
	}

	public String getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

}
