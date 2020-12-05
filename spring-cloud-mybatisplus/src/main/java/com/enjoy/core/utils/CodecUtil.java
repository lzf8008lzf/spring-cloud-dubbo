package com.enjoy.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-12-05 13:37
 **/

public class CodecUtil {

    private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);
    /**
     * 统一字符集
     */
    private static final String CHARSET = "utf-8";
    /**
     * key摘要算法
     */
    private static final String ALGORITHM = "SHA-256";
    /**
     * 统一缓存头
     */
    private static final String CACHE_NAME = "Hibernate:";
    /**
     * 信息摘要
     */
    private static volatile MessageDigest messageDigest;

    /**
     * 计算出key的摘要
     * @param cacheKey CacheKey
     * @return 字符串key
     */
    public static String getKey(Object cacheKey) {
        String cacheKeyStr = cacheKey.toString();
        logger.debug("count hash key, cache key origin string:{}", cacheKeyStr);
        String strKey = byte2hex(getSHADigest(cacheKeyStr));
        logger.debug("hash key:{}", strKey);
        String key = CACHE_NAME + strKey;
        return key;
    }

    /**
     * 获取信息摘要
     * @param data 待计算字符串
     * @return 字节数组
     */
    public static byte[] getSHADigest(String data) {
        try {
            if (messageDigest == null) {
                synchronized (MessageDigest.class) {
                    if (messageDigest == null) {
                        messageDigest = MessageDigest.getInstance(ALGORITHM);
                    }
                }
            }
            return messageDigest.digest(data.getBytes(CHARSET));
        } catch (Exception e) {
            logger.error("SHA-256 digest error: ", e);
        }

        return null;
    }

    /**
     * 字节数组转16进制字符串
     * @param bytes 待转换数组
     * @return 16进制字符串
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
