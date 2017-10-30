package com.chinaunicom.wodp.mapper;

import java.util.List;

import com.chinaunicom.wodp.pojo.Operator;
import com.chinaunicom.wodp.pojo.OperatorRole;
import com.chinaunicom.wodp.pojo.QueryOperator;

public interface OperatorMapper {

	/**
	 * 添加操作员日志
	 * @param operator
	 * @return 数据库影响的行数
	 */
	public int addOperatorLog(Operator operator);
	
	/**
	 * 添加操作员角色日志
	 * @param operatorRole
	 * @return 数据库影响的行数
	 */
	public int addOperatorLogRole(OperatorRole operatorRole);
	
	/**
	 * 更新操作员日志
	 * @param operator
	 * @return 数据库影响的行数
	 */
	public int updateOperatorLog(Operator operator);
	
	/**
	 * 根据操作员日志主键id查询日志中的操作员数据
	 * @param id
	 * @return Operator
	 */
	public Operator findOperatorLogById(Long id);
	
	
	/**
	 * 根据操作员日志主键id查询日志中的操作员角色数据
	 * @param operatorId
	 * @return OperatorRole
	 */
	public OperatorRole findOperatorLogRoleByOperatorId(Long operatorId);
	
	
	/**
	 * 根据条件（bus_name | user_name | bus_id | operator_check 等）查询操作员日志表的数量
	 * @param queryOperator
	 * @return 操作员数量
	 */
	public int findOperatorLogNumber(QueryOperator queryOperator);
	
	/**
	 * 根据条件（bus_name | user_name | operator_check 等）查询操作员日志表集合
	 * @param queryOperator
	 * @return List
	 */
	public List<QueryOperator> findOperatorLog(QueryOperator queryOperator);
	
	/**
	 * 根据操作员user_id查询添加操作员且审核通过的操作员日志数据
	 * @param user_id
	 * @return Operator
	 */
	public Operator findCreateOperatorLogByUserId(String user_id);
	
	/*=================================================================================================*/
	
	/**
	 * 添加操作员
	 * @param operator
	 * @return 数据库影响的行数
	 */
	public int addOperator(Operator operator);
	
	/**
	 * 添加操作员角色
	 * @param operatorRole
	 * @return 数据库影响的行数
	 */
	public int addOperatorRole(OperatorRole operatorRole);
	
	/**
	 * 根据操作员id更新操作员
	 * @param operator
	 * @return 数据库影响的行数
	 */
	public int updateOperatorByUserId(Operator operator);
	
	/**
	 * 根据操作员id更新操作员角色
	 * @param operatorRole
	 * @return 数据库影响的行数
	 */
	public int updateOperatorRoleByOpId(OperatorRole operatorRole);
	
	/**
	 * 根据操作员id查询操作员数据
	 * @param operatorRole
	 * @return Operator
	 */
	public Operator findOperatorByUserId(String user_id);
	
	
	/**
	 * 根据操作员外键id查询操作员角色数据
	 * @param operatorId
	 * @return OperatorRole
	 */
	public OperatorRole findOperatorRoleByOperatorId(Long operatorId);
	

	/**
	 * 根据操作员条件（user_id | bus_id | id 等）更新操作员角色
	 * @param operator
	 * @return 数据库影响的行数
	 */
	public int updateOperator(Operator operator);
	
	/**
	 * 根据商户id查询操作员的数量
	 * @param bus_id
	 * @return 操作员数量
	 */
	public int findOperatorNumberByBusId(String bus_id);
	
	
	/**
	 * 根据条件（ bus_id | operator_check | operate_type 等）查询操作员日志表集合
	 * @param queryOperator
	 * @return List
	 */
	public List<QueryOperator> findOperator(QueryOperator queryOperator);
	
}
