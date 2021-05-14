package com.enjoy.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 签名工具类
 *
 **/
public class SignUtil {

    private static final Logger log = LoggerFactory.getLogger(SignUtil.class);

    public static String packageParmeter(Map<String, Object> param){
        // 由于map是无序的，这里主要是对key进行排序（字典序）
        Set<String> keySet = param.keySet();
        String[] keyArr = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArr);
        StringBuilder sb = new StringBuilder();
        for (String key : keyArr) {
            sb.append(key).append("=").append(param.get(key)).append("&");
        }

        sb.deleteCharAt(sb.lastIndexOf("&"));

        return sb.toString();
    }

    /**
     * 获取签名信息
     * @param param
     * @param secret
     * @param appendStr
     * @return
     */
    public static String createSign(Map<String, Object> param, String secret,String appendStr) {

        String paramStr = packageParmeter(param);

        String md5Str = DigestUtils.md5DigestAsHex(paramStr.getBytes())+appendStr;
        String sha256Str = CodecUtil.sha256_HMAC(md5Str,secret).toUpperCase();

        log.info("param:{},md5:{},sha256:{}",paramStr,md5Str,sha256Str);
        return sha256Str;
    }
    /**
     * 获取签名信息
     * @param param
     * @param secret
     * @return
     */
    public static String createSign(Map<String, Object> param, String secret) {
        String paramStr = packageParmeter(param);

        String md5Str = DigestUtils.md5DigestAsHex(paramStr.getBytes());
        String sha256Str = CodecUtil.sha256_HMAC(md5Str,secret).toUpperCase();

        log.info("param:{},md5:{},sha256:{}",paramStr,md5Str,sha256Str);
        return sha256Str;
    }

    public static boolean checkSign(Map<String, Object> data, String secret,String sign){
        String genSign = createSign(data,secret);
        return genSign.equals(sign);
    }

}
