package com.chinaunicom.wodp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaunicom.wodp.pojo.PageBean;
import com.chinaunicom.wodp.pojo.WorkBill;
import com.chinaunicom.wodp.pojo.WorkBillAlter;
import com.chinaunicom.wodp.pojo.WorkBillQuery;
import com.chinaunicom.wodp.service.WorkBillService;
import com.chinaunicom.wodp.utils.ErjiHttpUtil;
import com.chinaunicom.wodp.utils.MD5;
import com.chinaunicom.wodp.utils.ResponseUtils;

import net.sf.json.JSONObject;


@Controller
public class WorkBillController<V> {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WorkBillService workBillService;
	@Value("${sign_key}")
	private String sign_key;
	@Value("${erjiUrl}")
	private String erjiUrl;
	
	//根据二级平台传递过来的请求 ,将工单 保存`
	@RequestMapping("/saveWorkBill")
	@ResponseBody
	public String saveWorkBill(@RequestBody String json){
		
		JSONObject jsonObject = new JSONObject();
			try {
				logger.info("申请工单信息：" + json);
				JSONObject reqJson = JSONObject.fromObject(json);
				WorkBill workBill = (WorkBill) JSONObject.toBean(reqJson, WorkBill.class);
				String max_num = workBill.getMax_num();
				if (StringUtils.isBlank(max_num) ) {
					jsonObject.put("result_flag", "0");
					jsonObject.put("message", "最大输出行数不能为空");
					return jsonObject.toString();
				}else if (StringUtils.isBlank(workBill.getSerial_num())) {
					jsonObject.put("result_flag", "0");
					jsonObject.put("message", "流水号不能为空");
					return jsonObject.toString();
				}else if (StringUtils.isBlank(workBill.getSystem_id())) {
					jsonObject.put("result_flag", "0");
					jsonObject.put("message", "来源应用平台ID不能为空");
					return jsonObject.toString();
				}else if (StringUtils.isBlank(workBill.getBus_id())) {
					jsonObject.put("result_flag", "0");
					jsonObject.put("message", "商户ID不能为空");
					return jsonObject.toString();
				}else if (StringUtils.isBlank(workBill.getProduct_id())) {
					jsonObject.put("result_flag", "0");
					jsonObject.put("message", "产品ID不能为空");
					return jsonObject.toString();
				}else if (StringUtils.isBlank(workBill.getWork_no())) {
					jsonObject.put("result_flag", "0");
					jsonObject.put("message", "工单号不能为空");
					return jsonObject.toString();
				}else{
					//判断工单号是否已经存在,存在的话不允许保存
					int count  = workBillService.checkWorkNum(workBill.getWork_no());
					if (count==1) {
						jsonObject.put("result_flag", "0");
						jsonObject.put("message", "工单号已经存在,保存失败");
						return jsonObject.toString();
					}else{
						//调用 service 保存工单   
						Date date = new Date();
						//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						workBill.setCreateTime(date);
						workBillService.saveWOrkBill(workBill);
						
						jsonObject.put("result_flag", "1");
						jsonObject.put("message", "工单保存成功");
						return jsonObject.toString();
					}
				}
			} catch (Exception e) {
				jsonObject.put("result_flag", "0");
				jsonObject.put("message", "系统异常");
				e.printStackTrace();
				logger.error("工单操作异常:", e);
				return jsonObject.toString();
			}
		
	}
	
	//根据前台传递的条件 查询 工单
	@RequestMapping("/workBillQuery")
	@ResponseBody
	public String workBillQuery(WorkBillQuery workBillQUery,HttpServletResponse response) throws ParseException{
		String begin_time = workBillQUery.getBegin_time();
		String end_time = workBillQUery.getEnd_time();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if (StringUtils.isNotBlank(begin_time)) {
			Date beginTime = sdf.parse(begin_time);
			workBillQUery.setBeginTime(beginTime);
		}
		if (StringUtils.isNotBlank(end_time)) {
			Date endTime = sdf.parse(end_time);
			workBillQUery.setEndTime(endTime);
		}
		
		PageBean<WorkBill> pageBean = workBillService.workBillQuery(workBillQUery);
		JSONObject pageBeanJson = JSONObject.fromObject(pageBean);
		return pageBeanJson.toString();
	}
	
	//调用 二级平台接口  工单结果数据已生成 通知接口
	@RequestMapping("/workBillNotice")
	@ResponseBody
	public String workBillNotice(WorkBillAlter workBillAlter){
		//创建一个json用于封装 ftp信息
		JSONObject ftpJson = new JSONObject();                                              
		ftpJson.put("ip", workBillAlter.getIp());                         //服务器ip地址         
		ftpJson.put("port", workBillAlter.getPort());                     //端口号             
		ftpJson.put("user", workBillAlter.getUser());                     //ftp登录用户         
		ftpJson.put("password", workBillAlter.getPassword());             //ftp用户密码(明文)     
		ftpJson.put("file_path", workBillAlter.getFile_path());           //目录              
		
		
		//创建一个 json用于封装 数据  发送到二级平台
		
		Map<String, String> erjiMap = new HashMap<String, String>();
		erjiMap.put("service_name", "ORDER_RESULT_NOTICE_SERVICE");        //服务名称
		erjiMap.put("session_id", workBillAlter.getSerial_num());           //流水号
		erjiMap.put("input_charset", "utf-8");                              //默认编码格式
		//erjiMap.put("sign_key", sign_key);                                    //签名秘钥
		erjiMap.put("work_no", workBillAlter.getWork_no());                 //工单号
		erjiMap.put("execute_status", workBillAlter.getExecute_status());   //工单执行状态
		erjiMap.put("ftp_info", ftpJson.toString());                                     //ftp服务器信息,jason对象
		erjiMap.put("file_name", workBillAlter.getFile_name());             //已上传文件名称
		erjiMap.put("remark", workBillAlter.getRemark());                   //备注 受理失败原因
		
		//将map转换为字符串
		String regJson = ResponseUtils.mapToString(erjiMap);
		
		//加密
		String sign = MD5.sign(regJson, sign_key, "utf-8");
		erjiMap.put("sign_key", sign);
		logger.info("调用二级平台工单操作结果通知接口传递参数：" + regJson);
		//调用二级接口
		JSONObject respJson = new JSONObject();
		String rspErji;
		try {
			rspErji = ErjiHttpUtil.readStream(ErjiHttpUtil.doPostForStream(erjiUrl, erjiMap),"utf-8");
			logger.info("二级平台接口返回参数：" + rspErji);
			//创建一个json给前台返回信息
			JSONObject resJsonNotice = JSONObject.fromObject(rspErji);
			String noticeStatus = (String) resJsonNotice.get("result_flag");
			int noticeStatu = Integer.parseInt(noticeStatus);
			if (noticeStatu == 1) {// &&
				
				workBillService.workBillAlter(workBillAlter);
				respJson.put("message", "状态修改成功");
			} else {
				respJson.put("message", resJsonNotice.get("message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("工单状态修改异常:", e);
		}
			return respJson.toString();
		
	}
	
	//用于回显 工单详情  返回所需要的数据
	@RequestMapping("/huiXianMessage")
	@ResponseBody
	public String huiXianMessage(String work_no){
		//根据工单号 调用service 将信息返回前台
		WorkBillAlter workBillAlter = workBillService.getWorkBillByWork_no(work_no);
		JSONObject workBillJson = JSONObject.fromObject(workBillAlter);
		String string = workBillJson.toString();
		return string;
	}
	
	
	
	
	
	
	
	
	
	
	
}