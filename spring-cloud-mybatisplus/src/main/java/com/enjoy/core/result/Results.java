package com.enjoy.core.result;

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
     * 错误代码
     */
    private String code;

    /**
     * 错误消息
     */
    private String msg;

    /**
     * 返回结果对象
     */
    private T data;

    public Results(Boolean ok) {
        this.setInnerData(ok,null);
    }

    public Results(Boolean ok, T data) {
        this.setInnerData(ok,data);
    }

    public Results(String code, String msg) {
        this.setError(code,msg);
    }

    private void setInnerData(boolean ok,T data) {

        this.ok = ok;
        this.data = data;

        if (ok) {
            this.code = CommConstants.RSP_ERROR_CODE.SUCCESS.value();
            this.msg = CommConstants.RSP_ERROR_CODE.SUCCESS.desc();
        } else {
            this.code = CommConstants.RSP_ERROR_CODE.ERROR.value();
            this.msg = CommConstants.RSP_ERROR_CODE.ERROR.desc();
        }
    }

    public void setError(String code, String msg){
        this.ok = CommConstants.ERROR;
        this.code = code;
        this.msg = msg;
    }

    public Boolean isOk() {
        return ok;
    }

}
