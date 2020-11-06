package com.enjoy.cores.result;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Results<T> implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 成功标识
     */
    private Boolean ok;

    /**
     * 返回代码
     */
    private String code;

    /**
     * 消息描述
     */
    private String msg;

    /**
     * 返回结果对象
     */
    private T data;

    /**
     * 有数据返回，默认为成功
     * @param data
     */
    public Results(T data) {
        this.ok = CommConstants.SUCCESS;
        this.code = CommConstants.RSP_ERROR_CODE.SUCCESS.value();
        this.msg = CommConstants.RSP_ERROR_CODE.SUCCESS.desc();
        this.data = data;
    }

    public Results(boolean ok, String code, String msg) {
        this.ok = ok;
        this.code = code;
        this.msg = msg;
    }

    public static Results ok() {
        return ok(null);
    }

    public static Results ok(String code, String msg){
        return new Results(CommConstants.SUCCESS,code,msg);
    }

    public static <T> Results<T> ok(T data) {
        return new Results(data);
    }

    public static Results error(String code, String msg) {
        return new Results(CommConstants.ERROR, code, msg);
    }

    public static Results error(String msg) {
        return error(CommConstants.RSP_ERROR_CODE.ERROR.value(), msg);
    }

    public static Results error() {
        return error(CommConstants.RSP_ERROR_CODE.ERROR.desc());
    }

    public Boolean isOk() {
        return ok;
    }

}