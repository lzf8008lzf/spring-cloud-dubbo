package com.enjoy.core.exception;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-12-17 14:16
 **/

public class BusinessException extends RuntimeException{
    private int errorCode;

    public BusinessException(){
        super();
    }

    public BusinessException(String message){
        super(message);
    }

    public BusinessException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getCode() {
        return errorCode;
    }
}