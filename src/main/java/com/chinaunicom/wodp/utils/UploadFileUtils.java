package com.chinaunicom.wodp.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
 
public class UploadFileUtils {

	public static String uploadFile(MultipartFile file , String filePath){
		Map <String, String> rspJsonMap = new HashMap <String, String>();
		try {
			String fileName = file.getOriginalFilename();
			int i=fileName.lastIndexOf(".");
			String randomFileName = RandomFileNameUtils.getRandomFileName();
			String newFileName = randomFileName + "." + fileName.substring(i+1);
			File newFile = new File(filePath+newFileName);
			BufferedOutputStream stream;
			if(!newFile.getParentFile().exists()){
				newFile.getParentFile().mkdirs();
			}
			stream = new BufferedOutputStream(new FileOutputStream(newFile));
			stream.write(file.getBytes());
			stream.close();
			rspJsonMap.put("realName", fileName);
			rspJsonMap.put("newFileName", newFileName);
			rspJsonMap.put("status", "1");
		} catch (Exception e) {
			e.printStackTrace();
			rspJsonMap.put("status", "0");
		}
		Gson gs = new Gson();
		String rspJson = gs.toJson(rspJsonMap);
		return rspJson;
	};
}
