package com.chinaunicom.wodp.pojo;

import java.util.HashMap;
import java.util.List;

public class PageBean<T> {
	private Integer totalCount;    // 总条数
	private Integer currentPage=1;     // 当前页
	private Integer pageSize=15;     // 每页显示的条数
    private List<T> rows; // 查询的结果集
  	private Integer totalPage; //总页数
	
  	private Integer begin=0;
  	private List<HashMap<String,String>> listm;
    
    
    public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public List<HashMap<String, String>> getListm() {
		return listm;
	}

	public void setListm(List<HashMap<String, String>> listm) {
		this.listm = listm;
	}

	public PageBean() {
		super();
	}
    
	public PageBean(Integer totalCount, Integer currentPage) {
		if(currentPage == null){//没指定要看那一页,默认为第一页
			this.currentPage = 1;
		}else{
			if(currentPage<=0){ //如果当前页码小于0,当前页码为1
				
				this.currentPage = 1;
			}else{ 
				this.currentPage =currentPage;
			}
		}
		this.totalCount = totalCount;
        
		totalPage = (totalCount+pageSize-1)/pageSize;
		
		if(this.currentPage>totalPage){ // 如果当前页码大于总页码=>当前页码等于总页码
			this.currentPage = totalPage;
		}
	}

	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
    
    
	
	
}
