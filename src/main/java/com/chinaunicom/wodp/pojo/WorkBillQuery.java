package com.chinaunicom.wodp.pojo;

import java.util.Date;

//将查询条件封装为一个对象
public class WorkBillQuery {

	private String  product_name;  //产品名称
	private String  bus_name;      //商户名称
	private Integer status;        //工单状态 
	private String begin_time;     //前台传过来的查询的起始时间
	private String end_time;       //前台传过来的查询的结束时间
	
	private Date beginTime;   //转换为date类型的日期
	private Date endTime;     //转换为date类型的结束日期
	
	private Integer startRow; //起始行
	private Integer pageSize=15; //每页显示的条数 
	private Integer currentPage=1; //当前页码
	
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

	
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.startRow = (currentPage - 1) * pageSize; // 每页记录数发生改变需要改变起始行
		this.pageSize = pageSize;
	}
	
	public Integer getCurrentPage() {
		this.startRow = (currentPage - 1) * pageSize; // 页码发生改变需要改变起始行
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
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

	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	
	
	
}
