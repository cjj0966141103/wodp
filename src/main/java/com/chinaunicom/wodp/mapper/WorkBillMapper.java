package com.chinaunicom.wodp.mapper;

import java.util.List;

import com.chinaunicom.wodp.pojo.WorkBill;
import com.chinaunicom.wodp.pojo.WorkBillAlter;
import com.chinaunicom.wodp.pojo.WorkBillQuery;

//本接口对 工单进行操作
public interface WorkBillMapper {

	public void saveWorkBill(WorkBill workBill);//保存 工单
	public void saveWorkBillSelective(WorkBill workBill);
	//根据条件查询工单
	public List<WorkBill> queryWorkBill(WorkBillQuery workBillQUery);
	//根据条件查询工单的总数
	public int queryWorkBillCount(WorkBillQuery workBillQUery);
	//修改 工单 
	public int alterWorkBill(WorkBillAlter workBillAlter);
	//判断工单号是否唯一 
	public int checkWorkNumber(String work_no);
	//根据工单号获取工单
	public WorkBillAlter getWorkBillAlter(String work_no);
}
