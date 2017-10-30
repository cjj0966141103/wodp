package com.chinaunicom.wodp.service;

import java.util.Map;

import com.chinaunicom.wodp.pojo.Operator;
import com.chinaunicom.wodp.pojo.QueryOperator;

public interface OperatorService {
	
	/**
	 * 添加操作员日志
	 * @param 操作员json串数据
	 */
	public void addOperatorLog(String json);
	
	/**
	 * 审核操作员
	 * @param operator
	 * @throws Exception 
	 * @return 审核后提示信息
	 */
	public String checkOperator(Operator operator,String message) throws Exception;

	/**
	 * 根据操作员条件（user_id | bus_id | id 等）注销操作员
	 * @param operator
	 */
	public void closeOperator(Operator operator);

	/**
	 * 根据条件（bus_name | user_name | operator_check 等）查询操作员日志列表（带分页）
	 * @return 带分页数据的操作员list集合map
	 */
	public Map<String,Object> findOperatorLog(QueryOperator queryOperator);
	
	/**
	 * 根据操作员日志id查询日志表操作员详情
	 * @return 操作员日志详情
	 */
	public Operator findOperatorLogDetails(Long id);
	
	/**
	 * 根据条件（ bus_id | operator_check |operate_type 等）查询操作员列表（带分页）
	 * @return 带分页数据的操作员list集合map
	 */
	public Map<String,Object> findOperator(QueryOperator queryOperator);
}
