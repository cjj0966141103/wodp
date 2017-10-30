package com.chinaunicom.wodp.service;

import com.chinaunicom.wodp.pojo.PageBean;
import com.chinaunicom.wodp.pojo.WorkBill;
import com.chinaunicom.wodp.pojo.WorkBillAlter;
import com.chinaunicom.wodp.pojo.WorkBillQuery;

public interface WorkBillService {

	//保存工单
	public void saveWOrkBill(WorkBill workBill);
	//根据条件查询工单
	public PageBean<WorkBill> workBillQuery(WorkBillQuery workBillQUery);
	//修改工单
	public int workBillAlter(WorkBillAlter workBillAlter);
	//根据工单号  判断工单是否存在
	public int checkWorkNum(String work_no);
	//根据工单号 获取 工单 
	public WorkBillAlter getWorkBillByWork_no(String work_no);
}
