package com.chinaunicom.wodp.pojo;
//工单修改 的pojo  主要添加文件名  和  备注 字段
public class WorkBillAlter extends WorkBill {

	private String file_name;   //文件名称
	private String remark;      //备注
	private String execute_status; //执行状态
	
	private String bus_name;
	private String product_name;
	

	public String getBus_name() {
		return bus_name;
	}
	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getExecute_status() {
		return execute_status;
	}
	public void setExecute_status(String execute_status) {
		this.execute_status = execute_status;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
