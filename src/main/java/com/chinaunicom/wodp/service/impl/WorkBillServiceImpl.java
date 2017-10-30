package com.chinaunicom.wodp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinaunicom.wodp.mapper.WorkBillMapper;
import com.chinaunicom.wodp.pojo.PageBean;
import com.chinaunicom.wodp.pojo.WorkBill;
import com.chinaunicom.wodp.pojo.WorkBillAlter;
import com.chinaunicom.wodp.pojo.WorkBillQuery;
import com.chinaunicom.wodp.service.WorkBillService;

@Service
@Transactional
public class WorkBillServiceImpl implements WorkBillService {

	@Autowired
	private WorkBillMapper workBillMapper;

	//保存工单
	public void saveWOrkBill(WorkBill workBill) {
		workBillMapper.saveWorkBillSelective(workBill);
	}

	//根据条件查询工单
	public PageBean<WorkBill> workBillQuery(WorkBillQuery workBillQUery) {
		//获取查询的当前页
		Integer currentPage = workBillQUery.getCurrentPage();
		//获取查询的workbill的总数量
		int totalCount = workBillMapper.queryWorkBillCount(workBillQUery);
		//获取每页显示的数量 
		Integer pageSize = workBillQUery.getPageSize();
		//计算总页数
		int totalPage ;
		if (totalCount%pageSize==0) {
			totalPage= totalCount/pageSize;
		}else if(totalCount<=pageSize){
			totalPage=1;
		}else{
			totalPage= totalCount/pageSize+1;
		}
		
		
		PageBean<WorkBill> pageBean = new PageBean<WorkBill>(totalCount, currentPage);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		List<WorkBill> list = workBillMapper.queryWorkBill(workBillQUery);
		pageBean.setRows(list);
		
		return pageBean;
		 
	}

	//修改工单 
	public int workBillAlter(WorkBillAlter workBillAlter) {
		int change = workBillMapper.alterWorkBill(workBillAlter);
		return change;
	}

	//查询工单号 是否已经存在 , 已经存在 不允许保存
	public int checkWorkNum(String work_no) {
		int count = workBillMapper.checkWorkNumber(work_no);
		return count;
	}
	
	//根据工单号 获取工单
	public WorkBillAlter getWorkBillByWork_no(String work_no) {
		WorkBillAlter workbill = workBillMapper.getWorkBillAlter(work_no);
		return workbill;
	}

	
}
