package com.enjoy.core.result;

/**
 * @program: service-monitor
 * @description:
 * @author: LiZhaofu
 * @create: 2020-07-16 18:46
 **/

public class ResultsUtil {

    public static Results ok() {
        return new Results(CommConstants.SUCCESS);
    }

    public static <T> Results<T> ok(T data) {
        return new Results(CommConstants.SUCCESS, data);
    }

    public static Results error() {
        return new Results(CommConstants.RSP_ERROR_CODE.ERROR.value(), CommConstants.RSP_ERROR_CODE.ERROR.desc());
    }

    public static Results error(String code, String msg) {
        return new Results(code, msg);
    }
}
