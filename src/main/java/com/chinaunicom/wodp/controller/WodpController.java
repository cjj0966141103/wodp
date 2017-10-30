package com.chinaunicom.wodp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.wodp.pojo.AlterPwd;
import com.chinaunicom.wodp.service.RegisterService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/unicom")
public class WodpController {
	
	@Autowired
	private RegisterService registerService;
	
	
	
	//修改密码 
	@RequestMapping("/alterPassword")
	@ResponseBody
	public String alterPassword(AlterPwd alterPwd,HttpServletRequest request){
		//获取sso服务器传过来的用户名
		 String username = request.getRemoteUser();
		 alterPwd.setLoginName(username);
		 //调用service 修改密码
		 String response = registerService.alterPassword(alterPwd);
		 return response;
	}

	
	//获取登录名 用于 页面显示 欢迎 xxx
	@RequestMapping("/loginUser")
	@ResponseBody
	public String loginUser(HttpServletRequest request){
		//获取sso服务器传过来的用户名
		 String username = request.getRemoteUser();
		 JSONObject jsonObject = new JSONObject();
		 jsonObject.put("loginuser", username);
		 return jsonObject.toString();
	
	}
	
	//删除用户
	@RequestMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(String loginName,HttpServletRequest request){
		//调用service删除用户
		int i  = registerService.deleteUser(loginName);
		//创建json返回状态
		JSONObject jsonObject = new JSONObject();
		if (i==1) {
			jsonObject.put("message", "删除成功");
			return jsonObject.toString();
		}else{
			jsonObject.put("message", "删除失败,联系管理员");
			return jsonObject.toString();
		}
	}
	
	//获取登录名, 判断登录名是否是管理员  在确定具体权限 显示页面
	@RequestMapping("/checkUsernameIsAdmin")
	public @ResponseBody String checkUsernameIsAdmin(HttpServletRequest request){
		//获取登陆的用户名
		String username = request.getRemoteUser();
		int isAdmin  = registerService.getIsAdminByUsername(username);
		//isAdmin=1 是管理员   isAdmin=0 是普通人员
		if (isAdmin==1){       
			List<String> nameList = registerService.getNamePrivilege(isAdmin);
			return JSONArray.fromObject(nameList).toString();
		}else if (isAdmin==0){       
			List<String> nameList = registerService.getNamePrivilege(isAdmin);
			return JSONArray.fromObject(nameList).toString();
		}
		return "";
	}
	
	
	
}