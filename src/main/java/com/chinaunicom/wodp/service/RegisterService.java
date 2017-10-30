package com.chinaunicom.wodp.service;

import java.util.List;

import com.chinaunicom.wodp.pojo.AlterPwd;
import com.chinaunicom.wodp.pojo.RegisterUser;

public interface RegisterService {

	//注册  插入数据
	public String save(RegisterUser user);

	public int checkUsername(String loginName);
	
	//删除用户
	public int deleteUser(String loginName);

	//根据用户名判断 此是否是管理员
	public int getIsAdminByUsername(String username);

	//获取此用户的权限  也就是显示的页面
	public List<String> getNamePrivilege(int isAdmin);
	
	//修改密码
	public String alterPassword(AlterPwd alterPwd);

	// 调用数据作业接口 保存 数据,返回值为 是否注册成功  1为成功 0为失败
	public int registDaerWen(RegisterUser user);
	
	// 调用 数据挖掘 注册接口 保存数据 返回值为 是否注册成功  1为成功 0为失败
    public int registWaJue(RegisterUser user);
    
    // 调用数据审计 注册接口,返回值为 是否注册成功  1为成功 0为失败
 	public int registShenJi(RegisterUser user);
}
