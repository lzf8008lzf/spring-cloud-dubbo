package com.enjoy.common.util;

import com.enjoy.common.log.RegexReplacement;

public class DesensitizeUtil {
    private static final RegexReplacement cardNumRegex = new RegexReplacement("(\\d{15})(\\d{4})(\\]|\\\"|'|)",
            "**** **** **** *** $2");

    private static final RegexReplacement mobileRegex =  new RegexReplacement("(1)([3-9]{2})(\\d{4})(\\d{4})(\\]|\\\"|'|)",
            "$1$2****$4");

    private static final RegexReplacement idCardRegex =  new RegexReplacement("(\\d{1})(\\d{16})([\\d|X|x]{1})(\\]|\\\"|)",
            "$1****************$3");


    public static String bankCard(String bankCardNo){
        return cardNumRegex.format(bankCardNo);
    }

    public static String mobilePhone(String moblie){
        return mobileRegex.format(moblie);
    }

    public static String idCardNum(String idCardNo){
        return idCardRegex.format(idCardNo);
    }
}
