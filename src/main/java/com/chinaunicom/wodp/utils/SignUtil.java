package com.chinaunicom.wodp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* *
 *类名：AlipayFunction
 *功能：支付宝接口公用函数类
 *详细：该类是请求、通知返回两个文件所调用的公用函数核心处理文件，不需要修改
 *版本：3.3
 *日期：2012-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class SignUtil {

    public static final String INPUT_CHARSET_KEY = "_input_charset";
    public static final String SIGN_KEY = "sign";

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> filteParam(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 验证是否已签名
     * @param params
     * @param key
     * @return
     */
    public static boolean getSignVeryfy(Map<String, String> params, String key){

        String sign = params.get(SIGN_KEY);
        return getSignVeryfy(params, sign, key);
    }

    public static boolean getSignVeryfy(Map<String, String> params, String sign, String key) {
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = filteParam(params);

        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew);
        //获得签名验证结果
        boolean isSign = false;
//        if(MerchantConfig.sign_type.equals("MD5") ) {
//            isSign = MD5.verify(preSignStr, sign, MerchantConfig.key, MerchantConfig.input_charset);
//        }
        if(params.containsKey(INPUT_CHARSET_KEY)){
            isSign = MD5.verify(preSignStr, sign, key, params.get(INPUT_CHARSET_KEY));
        }

        return isSign;
    }

}
