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

    /**
     * 获取签名信息
     * @param data
     * @param secret
     * @return
     */
    public static String createSign(Map<String, Object> data, String secret) {
        // 由于map是无序的，这里主要是对key进行排序（字典序）
        Set<String> keySet = data.keySet();
        String[] keyArr = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArr);
        StringBuilder sb = new StringBuilder();
        for (String key : keyArr) {
            sb.append(key).append("=").append(data.get(key)).append("&");
        }
        // secret最后拼接
//        sb.append("secret=").append(secret);

        sb.deleteCharAt(sb.lastIndexOf("&"));

        String md5Str = DigestUtils.md5DigestAsHex(sb.toString().getBytes());
        String sha256Str = CodecUtil.sha256_HMAC(md5Str,secret).toUpperCase();

        log.info("param:{},md5:{},sha256:{}",sb.toString(),md5Str,sha256Str);
        return sha256Str;
    }

    public static boolean checkSign(Map<String, Object> data, String secret,String sign){
        String genSign = createSign(data,secret);
        return genSign.equals(sign);
    }

}
