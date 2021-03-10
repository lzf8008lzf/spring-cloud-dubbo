package com.enjoy.utils;

import java.io.File;
import java.io.InputStream;

/**
 * @program: spring-cloud-dubbo
 * @description: 文件操作工具类
 * @author: LiZhaofu
 * @create: 2021-03-10 17:26
 **/

public class FileUtil {

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static String getPath() {
        return FileUtil.class.getResource("/").getPath();
    }

    public static File createNewFile(String pathName) {
        File file = new File(getPath() + pathName);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    public static File readFile(String pathName) {
        return new File(getPath() + pathName);
    }

    public static File readUserHomeFile(String pathName) {
        return new File(System.getProperty("user.home") + File.separator + pathName);
    }

    public static void removePath(String path){
        File file = new File(path);
        if (file.exists()) {
            removeDir(file);
        }
    }

    private static void removeDir(File dir) {
        File[] files=dir.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                removeDir(file);
            }else{
                file.delete();
            }
        }
        dir.delete();
    }
}
