package com.chinaunicom.wodp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chinaunicom.wodp.utils.UploadFileUtils;
import com.google.gson.Gson;

@Controller
public class HomePageController {
	@Value("${filePath}")
	private String filePath;
	/**
	 * AJAX文件上传
	 * 测试上传到git  希望能成功
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/uploadfile")
	public void  uploadPic(@RequestParam(value="upload") MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uploadFileJson = UploadFileUtils.uploadFile(file, filePath);
		Gson gs = new Gson();
		Map <String, String> rspJsonMap = gs.fromJson(uploadFileJson, HashMap.class);
		if("1".equals(rspJsonMap.get("status"))){
			String newFileName = rspJsonMap.get("newFileName");
			rspJsonMap.put("filePath", filePath+newFileName);
			rspJsonMap.put("desc", "上传成功");
		}else{
			rspJsonMap.put("desc", "上传失败");
		}
		String rspJson = gs.toJson(rspJsonMap);
        response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        out.append(rspJson);
        out.flush();  
        out.close(); 
	}
	
	
}
