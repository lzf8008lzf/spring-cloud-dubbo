package com.enjoy.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * Md5加密方法
 *
 */
public class MD5Util
{
    private static final Logger log = LoggerFactory.getLogger(MD5Util.class);

    private static final String charseName ="UTF-8";

    private static byte[] md5(String s)
    {
        MessageDigest algorithm;
        try
        {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes(charseName));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        }
        catch (Exception e)
        {
            log.error("MD5 Error...", e);
        }
        return null;
    }

    private static final String toHex(byte hash[])
    {
        if (hash == null)
        {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++)
        {
            if ((hash[i] & 0xff) < 0x10)
            {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s)
    {
        try
        {
            return new String(toHex(md5(s)).getBytes(charseName), charseName);
        }
        catch (Exception e)
        {
            log.error("not supported charset...{}", e);
            return s;
        }
    }
}
