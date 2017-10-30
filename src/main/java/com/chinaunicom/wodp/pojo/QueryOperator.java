package com.chinaunicom.wodp.pojo;

import java.io.Serializable;


public class QueryOperator extends Operator implements Serializable {

	private static final long serialVersionUID = 1L;

	//商户名称
	private String bus_name;
	//前台传递的当前页数
	private Integer currentPage; 
	//数据库查询的起始页
	private Integer begin;
	//每页显示的条数
	private Integer pageSize = 15;     
	//总的页数
	private Integer totalPage;
	//总条数
	private Integer totalCount;
	
	public String getBus_name() {
		return bus_name;
	}
	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getBegin() {
		return begin;
	}
	public void setBegin(Integer begin) {
		this.begin = begin;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	@Override
	public String toString() {
		return "QueryOperator [bus_name=" + bus_name + ", currentPage=" + currentPage + ", begin="
				+ begin + ", pageSize=" + pageSize + ", totalPage=" + totalPage + ", totalCount=" + totalCount
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
	

}
