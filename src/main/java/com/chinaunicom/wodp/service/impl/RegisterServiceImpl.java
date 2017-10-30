package com.chinaunicom.wodp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinaunicom.wodp.mapper.RegisterMapper;
import com.chinaunicom.wodp.pojo.AlterPwd;
import com.chinaunicom.wodp.pojo.RegisterUser;
import com.chinaunicom.wodp.service.RegisterService;
import com.chinaunicom.wodp.utils.HttpUtils;
import com.chinaunicom.wodp.utils.MD5Utils;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RegisterMapper registerMapper;
	@Value("${rtw_url}")
	private String rtwurl;
	@Value("${pure_url}")
	private String pureurl;
	@Value("${reg_url}")
	private String regurl;

	// 保存 注册用户
	public String save(RegisterUser user) {

		// 当选择系统管理员 默认 给 达尔文 和 数据挖掘 赋值
		Integer isadmin = user.getIsAdmin();
		if (isadmin == 1) {
			user.setRtw_roleId("1");
			user.setAdmin(1);
			user.setRoleIds("root,m02,ccf01");
		}
		if (isadmin != 1) {
			user.setAdmin(0);
		}

		// 获取前台的注册信息 判断哪个注册那个不注册 dewStatu gaStatu wjStatu 值为1就是注册
		String dewStatu = user.getRegisterDEW();
		String gaStatu = user.getRegisterGA();
		String wjStatu = user.getRegisterWJ();
		// 创建一个json给前台返回信息
		JSONObject respJson = new JSONObject();

		try {
			if (dewStatu.equals("1") && gaStatu.equals("0") && wjStatu.equals("0")) {// 只注册数据作业
				int rtwStatu = registDaerWen(user);
				if (rtwStatu == 1) {// &&
					// rtwStatu==1
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据作业注册成功");
				} else {
					respJson.put("success", "数据作业注册失败");
				}

			} else if (dewStatu.equals("0") && gaStatu.equals("1") && wjStatu.equals("0")) {// 只注册数据审计
				int regStatu = registShenJi(user);
				if (regStatu == 1) {// &&
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据审计注册成功");
				} else {
					respJson.put("success", "数据审计注册失败");
				}

			} else if (dewStatu.equals("0") && gaStatu.equals("0") && wjStatu.equals("1")) {// 只注册数据挖掘
				int pureStatu = registWaJue(user);
				if (pureStatu == 1) {// &&
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据挖掘注册成功");
				} else {
					respJson.put("success", "数据挖掘注册失败");
				}

			} else if (dewStatu.equals("1") && gaStatu.equals("1") && wjStatu.equals("0")) {// 注册数据作业和数据审计
				int regStatu = registShenJi(user);
				int rtwStatu = registDaerWen(user);

				if (regStatu == 1 && rtwStatu == 1) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据作业和数据审计注册成功");
				} else if (regStatu == 0 && rtwStatu == 1) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据审计注册失败,数据作业注册成功");
				} else if (regStatu == 1 && rtwStatu == 0) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据作业注册失败,数据审计注册成功");
				} else {
					respJson.put("success", "数据作业注册失败,数据审计注册失败");
				}

			} else if (dewStatu.equals("1") && gaStatu.equals("0") && wjStatu.equals("1")) {// 注册数据作业和数据挖掘
				int rtwStatu = registDaerWen(user);
				int pureStatu = registWaJue(user);
				if (pureStatu == 1 && rtwStatu == 1) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据作业和数据挖掘注册成功");
				} else if (pureStatu == 0 && rtwStatu == 1) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据挖掘注册失败,数据作业注册成功");
				} else if (pureStatu == 1 && rtwStatu == 0) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据作业注册失败,数据挖掘注册成功");
				} else {
					respJson.put("success", "数据作业注册失败,数据挖掘注册失败;");
				}

			} else if (dewStatu.equals("0") && gaStatu.equals("1") && wjStatu.equals("1")) {// 注册数据审计和数据挖掘
				int pureStatu = registWaJue(user);
				int regStatu = registShenJi(user);
				if (pureStatu == 1 && regStatu == 1) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据挖掘和数据审计注册成功");
				} else if (pureStatu == 0 && regStatu == 1) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据挖掘注册失败,数据审计注册成功");
				} else if (pureStatu == 1 && regStatu == 0) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据审计注册失败,数据挖掘注册成功");
				} else {
					respJson.put("success", "数据审计注册失败,数据挖掘注册失败");
				}

			} else if (dewStatu.equals("1") && gaStatu.equals("1") && wjStatu.equals("1")) {
				int regStatu = registShenJi(user);
				int rtwStatu = registDaerWen(user);
				int pureStatu = registWaJue(user);

				if (pureStatu == 1 && regStatu == 1 && rtwStatu == 1) {// &&
					// rtwStatu==1
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "全部成功");
				}
				if (pureStatu == 0 && regStatu == 1 && rtwStatu == 1) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据挖掘注册失败,数据作业和数据审计成功");
				}
				if (pureStatu == 0 && regStatu == 0 && rtwStatu == 1) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据挖掘注册失败,数据审计注册失败,数据作业成功");
				}
				if (pureStatu == 0 && regStatu == 1 && rtwStatu == 0) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据作业注册失败,数据挖掘注册失败,数据审计成功");
				}
				if (pureStatu == 1 && regStatu == 0 && rtwStatu == 1) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据审计注册失败,数据作业和数据挖掘注册成功");
				}
				if (pureStatu == 1 && regStatu == 0 && rtwStatu == 0) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据作业注册失败,数据审计注册失败,数据挖掘成功");
				}
				if (pureStatu == 1 && regStatu == 1 && rtwStatu == 0) {
					// 将密码使用MD5加密
					String pwd = user.getLoginPwd();
					if (pwd != null) {
						String getMD5Code = MD5Utils.GetMD5Code(pwd);
						user.setLoginPwd(getMD5Code);
					}
					registerMapper.save(user);
					respJson.put("success", "数据作业失败,数据挖掘和数据审计注册成功");
				}
				if (pureStatu == 0 && regStatu == 0 && rtwStatu == 0) {
					respJson.put("success", "数据作业注册失败,数据审计注册失败,数据挖掘注册失败");
				}

			} else if (dewStatu.equals("0") && gaStatu.equals("0") && wjStatu.equals("0")) {
				respJson.put("success", "数据作业,数据挖掘和gateaway至少注册一个");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("注册用户异常异常:", e);
		}
		return respJson.toString();

	}

	// 调用数据作业接口 保存 数据,返回值为 是否注册成功 1为成功 0为失败
	public int registDaerWen(RegisterUser user) {
		// 数据作业
		JSONObject rtwJson = new JSONObject();
		rtwJson.put("version", "1.0");
		rtwJson.put("id", user.getRanNum());
		rtwJson.put("type", "rtw_register");
		rtwJson.put("action", "request");
		rtwJson.put("username", user.getLoginName());
		rtwJson.put("rtw_roleId", user.getRtw_roleId());
		rtwJson.put("rtw_orgName", user.getRtw_orgName());
		rtwJson.put("rtw_orgpid", user.getRtw_orgpid());
		rtwJson.put("rtw_orgId", user.getRtw_orgId());
		rtwJson.put("totalSpace", user.getTotalSpace());
		rtwJson.put("folderName", user.getFolderName());
		rtwJson.put("email", user.getEmail());
		rtwJson.put("nickname", user.getNickname());

		logger.info("数据作业注册请求:{}", rtwJson.toString());
		String responseBodyRtw = null;
		try {
			responseBodyRtw = HttpUtils.executePost(rtwurl, rtwJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用数据作业注册接口异常:", e);
		}
		logger.info("数据作业注册响应:{}", responseBodyRtw); // 将字符串 转换为json
		JSONObject resJsonRtw = JSONObject.fromObject(responseBodyRtw);
		String rtwStatus = (String) resJsonRtw.get("status");
		int rtwStatu = Integer.parseInt(rtwStatus);

		return rtwStatu;
	}

	// 调用 数据挖掘 注册接口 保存数据 返回值为 是否注册成功 1为成功 0为失败
	public int registWaJue(RegisterUser user) {
		// 数据挖掘
		JSONObject pureJson = new JSONObject();
		pureJson.put("version", "1.0");
		pureJson.put("id", user.getRanNum());
		pureJson.put("type", "mamp_register");
		pureJson.put("action", "request");
		pureJson.put("username", user.getUsername());
		pureJson.put("loginName", user.getLoginName());
		pureJson.put("admin", user.getAdmin());
		pureJson.put("sex", user.getSex());
		String roleIds = user.getRoleIds();
		String[] split = null;
		if (roleIds != null) {
			split = roleIds.split(",");
		}
		pureJson.put("roleIds", split);
		pureJson.put("email", user.getEmail());
		pureJson.put("qq", user.getQq());
		pureJson.put("mobile", user.getMobile());
		pureJson.put("telephone", user.getTelephone());
		pureJson.put("memo", user.getMemo());
		pureJson.put("pureOrgId", user.getPureOrgId());

		logger.info("数据挖掘注册请求:{}", pureJson.toString());
		String responseBodyPure = null;
		try {
			responseBodyPure = HttpUtils.executePost(pureurl, pureJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用数据挖掘注册接口异常:", e);
		}
		logger.info("数据挖掘注册响应:{}", responseBodyPure);
		// 将字符串 转换为json
		JSONObject resJsonPure = JSONObject.fromObject(responseBodyPure);
		String pureStatus = (String) resJsonPure.get("status");
		Integer pureStatu = Integer.parseInt(pureStatus);

		return pureStatu;
	}

	// 调用数据审计 注册接口,返回值为 是否注册成功 1为成功 0为失败
	public int registShenJi(RegisterUser user) {
		// 数据审计
		Gson gson = new Gson();
		String json = gson.toJson(user);

		logger.info("数据审计注册请求:{}", json);
		String responseBodyReg = null;
		try {
			responseBodyReg = HttpUtils.executePost(regurl, json);
		} catch (Exception e) {
			logger.error("调用数据审计注册接口异常:", e);
			e.printStackTrace();
		}
		logger.info("数据审计注册响应:{}", responseBodyReg);
		// 将字符串 转换为json && rtwStatu==1 pureStatu==1 && pureStatu==1 &&
		JSONObject resJsonReg = JSONObject.fromObject(responseBodyReg);
		Integer regStatu = (Integer) resJsonReg.get("status");
		return regStatu;
	}

	// 检查注册名是否重复
	public int checkUsername(String loginName) {
		int count = registerMapper.checkName(loginName);
		return count;
	}

	// 删除用户
	public int deleteUser(String loginName) {
		int nameCount = registerMapper.checkName(loginName);
		if (nameCount == 1) {// 用户名存在,判断删除成功还是失败
			int deleteUser = registerMapper.deleteUser(loginName);
			if (deleteUser == 1) {
				return 1;
			} else {
				return 0;
			}
		} else {// 用户名不存在 返回删除成功
			return 1;
		}

	}

	// 根据用户名 判断是否是管理员
	public int getIsAdminByUsername(String username) {
		int i = registerMapper.getIsAdminByUsername(username);
		return i;
	}

	// 是管理员的话,获得管理员的权限
	public List<String> getNamePrivilege(int isAdmin) {
		List<String> list = registerMapper.getUsernamePrivilege(isAdmin);
		return list;
	}

	// 修改密码
	public String alterPassword(AlterPwd alterPwd) {
		String loginname = alterPwd.getLoginName();
		String newPassword = alterPwd.getNewPassword();
		String oldPassword = alterPwd.getOldPassword();
		// 创建一个 json 返回数据
		JSONObject responseJson = new JSONObject();
		if (oldPassword == null || oldPassword.trim().equals("")||
				newPassword == null || newPassword.trim().equals("")) {
			return responseJson.toString();
		}
		// 将原密码和新密码都使用md5加密
		oldPassword = MD5Utils.GetMD5Code(oldPassword);
		newPassword = MD5Utils.GetMD5Code(newPassword);
		// 根据用户名 获取当前密码
		String old_pwd = registerMapper.getPasswordByUsername(loginname);
		if (!old_pwd.equals(oldPassword)) {
			responseJson.put("message", "原始密码不正确");
			return responseJson.toString();
		}
		//判断新输入密码与原密码是否一致
		if (old_pwd.equals(newPassword)) {
			responseJson.put("message", "新旧密码一致,不建议如此!");
			return responseJson.toString();
		}

		alterPwd.setNewPassword(newPassword);

		if (old_pwd.equals(oldPassword)) {
			registerMapper.alterPassWord(alterPwd);
			responseJson.put("message", "密码修改成功");
			return responseJson.toString();
		}

		return null;
	}

}
