package com.enjoy.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @program: spring-cloud-dubbo
 * @description: 比较两个对象的属性变化
 * @author: LiZhaofu
 * @create: 2021-04-12 17:04
 **/

public class CompareObejct<T>{
    private T original;

    private T current;

    /**
     *
     * @param cls
     * @return
     */

    public String contrastObj(Class cls){
        StringBuilder sb = new StringBuilder();

        try{
            Field[] fields = cls.getDeclaredFields();

            for(Field field: fields){
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(),cls);

                Method getMethod = pd.getReadMethod();

                String type = field.getType().getName();

                if(!"java.util.Set".equals(type)){
                    Object o1 = getMethod.invoke(this.original);
                    Object o2 = getMethod.invoke(this.current);

                    if(null != o2){
                        String s1 = o1 == null ? "" :o1.toString();

                        String s2 = o2 == null ? "" :o2.toString();

                        if(!s1.equals(s2)){
//System.out.println("不一样的属性：" + field.getName() + " 属性值：[" + s1 + "," + s2 + "]");
                            sb.append(field.getName() + ":" + "[" + s1 + "," + s2 + "];");
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return sb.toString();
    }

    public T getOriginal() {
        return original;
    }

    public void setOriginal(T original) {
        this.original = original;
    }

    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
    }
}

