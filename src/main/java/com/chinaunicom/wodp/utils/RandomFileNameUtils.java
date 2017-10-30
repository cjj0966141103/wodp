package com.chinaunicom.wodp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomFileNameUtils {

	 public static String getRandomFileName() {  
		  
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
        String time = simpleDateFormat.format(new Date());  
        Random random = new Random();  
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
        return time + rannum ; 
	 }  
}
