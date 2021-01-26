package com.enjoy.core.utils;

import java.text.DecimalFormat;

/**
 * @program: spring-cloud-dubbo
 * @description: 数字处理工具类
 * @author: LiZhaofu
 * @create: 2021-01-18 16:38
 **/

public class NumberUtil {

    /**
     * 计算数字的各位数字之和
     * @param num
     * @return
     */
    public static int sum(int num){
        int tmp = num;
        int sum = 0;   // 数字之和
        while( tmp != 0 ){
            sum += tmp%10;
            tmp /= 10;
        }

        return sum;
    }

    public static String format3D(int num){
        String fmtStr = new DecimalFormat("000").format(num);
        return fmtStr;
    }

    public static boolean isOdd(int num){
        return num % 2 != 0;
    }

    public static boolean isPrime(int num) {
        if(num < 2) {
            return false;
        }
        for(int i = 2; i <= Math.sqrt(num); i++ ) {
            if(num%i == 0) {
                return false;
            }
        }
        return true;
    }

    public static String oddEven(String num){
        StringBuilder sb = new StringBuilder();
        // 遍历所有字符
        for (int i = 0; i < num.length(); i++){
            char item = num.charAt(i);
            if (Character.isDigit(item)){
                int tmp = Integer.parseInt(String.valueOf(item));
                if(isOdd(tmp)){
                    sb.append("1");
                }else {
                    sb.append("0");
                }
            }
        }

        return sb.toString();
    }
}
