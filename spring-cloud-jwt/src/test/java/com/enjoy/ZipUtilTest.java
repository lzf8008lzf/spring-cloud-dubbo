package com.enjoy;

import cn.hutool.core.util.ZipUtil;
import com.enjoy.utils.FileUtil;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-03-10 14:23
 **/

public class ZipUtilTest {


    public static void main(String[] args){
        FileUtil.removePath("D:\\拳皇97风云再起");
//        testZip();
    }

    public static void testZip() {
        String targetPath = "D:\\拳皇97";
//        ZipUtil.zip(targetPath);

        ZipUtil.unzip("D:\\拳皇97风云再起.zip");
    }
}
