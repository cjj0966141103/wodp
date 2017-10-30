package com.chinaunicom.wodp.pojo;

import java.io.Serializable;
import java.util.Date;
/**
 * 商户注销日志类
 * @author Administrator
 *
 */
public class TenantCancelLog implements Serializable {
	private Integer id;
	private String bus_id; //商户ID(二级平台)
	private String phone_no; //电话号码
	private String system_id; //来源应用平台ID
	private String serial_num; //流水号
	private Date create_time; //创建的时间
	private Integer status; //用户注销的状态：0失败，1成功
	private String message; //处理信息
	private Integer flag; // 标记
	private String create_name;//操作人
	
	public TenantCancelLog() {
		super();
	}
	
	public TenantCancelLog(Integer id, String bus_id, String phone_no,
			String system_id, String serial_num, Date create_time,
			Integer status, String message, Integer flag, String create_name) {
		super();
		this.id = id;
		this.bus_id = bus_id;
		this.phone_no = phone_no;
		this.system_id = system_id;
		this.serial_num = serial_num;
		this.create_time = create_time;
		this.status = status;
		this.message = message;
		this.flag = flag;
		this.create_name = create_name;
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

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getSystem_id() {
		return system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	public String getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}



	public Integer getFlag() {
		return flag;
	}



	public void setFlag(Integer flag) {
		this.flag = flag;
	}



	public String getCreate_name() {
		return create_name;
	}



	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}



	@Override
	public String toString() {
		return "TenantCancelLog [id=" + id + ", bus_id=" + bus_id
				+ ", phone_no=" + phone_no + ", system_id=" + system_id
				+ ", serial_num=" + serial_num + ", create_time=" + create_time
				+ ", status=" + status + ", message=" + message + ", flag="
				+ flag + ", create_name=" + create_name + "]";
	}
	
	
}
