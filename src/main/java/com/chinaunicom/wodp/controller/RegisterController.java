package com.chinaunicom.wodp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.wodp.pojo.RegisterUser;
import com.chinaunicom.wodp.service.RegisterService;
import com.chinaunicom.wodp.utils.RandomUtils;

import net.sf.json.JSONObject;

@Controller
public class RegisterController {


	@Value("${rtw_url}")
	private String rtwurl;
	@Value("${pure_url}")
	private String pureurl;
	@Value("${reg_url}")
	private String regurl;

	@Autowired
	private RegisterService registerService;

	// 完成注册
	@RequestMapping(value = "/register")
	@ResponseBody
	public String register(RegisterUser user) throws Exception {

		// 获取一个六位随机数
		int randomNumber = RandomUtils.randomNumber();
		String ranNum = String.valueOf(randomNumber);
		user.setRanNum(ranNum);
		
		String trimName = user.getLoginName().trim();
		user.setLoginName(trimName);
		// 调用 service 保存 用户
		String responseJson = registerService.save(user);

		return responseJson;

	}

	// 检查用户名是否重复
	@RequestMapping("/checkUsername")
	@ResponseBody
	public String checkUsername(String loginName, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		if (loginName != null) {
			if (loginName.equals("")) {
				json.put("message", "请输入用户名");

			} else {
				int count = registerService.checkUsername(loginName);
				if (count != 0) {
					json.put("message", "用户名已存在");
				} else {
					json.put("message", "用户名可以使用");

				}
			}

		}
		return json.toString();
	}

}
