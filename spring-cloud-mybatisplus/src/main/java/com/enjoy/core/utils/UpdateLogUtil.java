package com.enjoy.core.utils;

/**
 * @program: spring-cloud-dubbo
 * @description: 数据更新字段信息
 * @author: LiZhaofu
 * @create: 2021-04-12 14:02
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


public class UpdateLogUtil {

    private static Logger log = LoggerFactory.getLogger(UpdateLogUtil.class);


    public static String UpdateLog(Object oldObject,Object newObject) {
        Class oldClass = oldObject.getClass();
//        Class newClass = newObject.getClass();
        if(!(oldClass.isInstance(newObject))){
            String errorStr = "传入的两个java对象类型不一致！";
            log.error(errorStr);
            return errorStr;
        }
        Field[] fields = oldClass.getDeclaredFields();
        String remark = "";
        try {
            for (Field field : fields) {
                String name = field.getName();

                field.setAccessible(true); //设置些属性是可以访问的
                Object oldValue = field.get(oldObject);//得到此属性的修改前值
                Object newValue = field.get(newObject);//得到此属性的修改后值

                if(!String.valueOf(oldValue).equals(String.valueOf(newValue))){
                    //保存处理数据
                    log.debug("UpdateLogUtil.UpdateLog:"+name+"--"+oldValue+"----"+newValue);
                    if(remark.isEmpty()){
                        remark = name+"修改前：" + String.valueOf(oldValue)+",修改后："+String.valueOf(newValue);
                    }else {
                        remark +="；"+ name+"修改前：" + String.valueOf(oldValue)+"，修改后："+String.valueOf(newValue);
                    }

    //                //获取属性上的指定类型的注解
    //                Annotation annotation = field.getAnnotation(XmlElement.class);
    //                //有该类型的注解存在
    //                if (annotation!=null) {
    //                    //强制转化为相应的注解
    //                    XmlElement xmlElement = (XmlElement)annotation;
    //                }
                }
            }
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
            log.error("UpdateLog Error!",e);
        }

        return remark;
    }

}

