package com.chinaunicom.wodp.mapper;

import java.util.List;

import com.chinaunicom.wodp.pojo.AlterPwd;
import com.chinaunicom.wodp.pojo.RegisterUser;

public interface RegisterMapper {

	//保存用户
	public void save(RegisterUser user);
	public void saveSelective(RegisterUser user);
	//检查用户名是否重复
	public int checkName(String loginName);
	//删除 用户
	public int  deleteUser(String loginName);
	//判断 此登录用户是否是管理员
	public int getIsAdminByUsername(String username);
	//获取当前登录用户的需要显示的页面
	public List<String> getUsernamePrivilege(int isAdmin);
	//根据登录名获取 密码
	public String getPasswordByUsername(String username);
	//修改密码
	public void alterPassWord(AlterPwd alterPwd);
	
}

