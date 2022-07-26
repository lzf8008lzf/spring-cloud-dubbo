package com.feign.client;

import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static feign.Util.decodeOrDefault;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class FeignLogger extends feign.Logger {
    static ThreadLocal<Map<String, String>> logContext = new ThreadLocal();
    static String PATH = "path";
    static String METHOD = "method";
    static String REQUEST_BODY = "body";
    static String ELAPSED_TIME = "耗时";
    static String ELAPSED_TIME_UNIT = "毫秒";
    static String FEIGN_INVOKE_LOGGER = "feign 接口调用";
    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        Map<String, String> logMap = new HashMap<>(3);
        logMap.put(PATH, request.url());
        logMap.put(METHOD, request.httpMethod().name());
        logMap.put(REQUEST_BODY, request.body() == null ? null :
                request.charset() == null ? null : new String(request.body(), request.charset()));
        logContext.set(logMap);
    }
    @Override
    protected Response logAndRebufferResponse(
            String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        Map<String, String> requetParam = logContext.get();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(FEIGN_INVOKE_LOGGER).append(" ")
                .append(requetParam.get(METHOD)).append(" ")
                .append(response.status()).append(" ")
                .append(requetParam.get(PATH)).append(" ")
                .append(ELAPSED_TIME).append(elapsedTime).append(ELAPSED_TIME_UNIT);
        if (requetParam.get(REQUEST_BODY) != null) {
            stringBuilder.append(" 请求入参:").append(requetParam.get(REQUEST_BODY));
        }
        logContext.remove();
        // 返回参数
        if (response.body() != null && !(response.status() == 204 || response.status() == 205)) {
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
            if (bodyData.length > 0) {
                String responseBody = decodeOrDefault(bodyData, UTF_8, "Binary data");
                stringBuilder
                        .append(" 返回值:")
                        .append(responseBody.replaceAll("\\s*|\t|\r|\n", ""));
            }
            log.info(stringBuilder.toString());
            return response.toBuilder().body(bodyData).build();
        }
        log.info(stringBuilder.toString());
        return response;
    }
    protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {
        Map<String, String> requetParam = logContext.get();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(FEIGN_INVOKE_LOGGER).append(" ")
                .append(requetParam.get(METHOD)).append(" ")
                .append(ioe.getClass().getSimpleName()).append(" ")
                .append(requetParam.get(PATH)).append(" ")
                .append(ELAPSED_TIME).append(elapsedTime).append(ELAPSED_TIME_UNIT);;
        if (requetParam.get(REQUEST_BODY) != null) {
            stringBuilder.append(" 请求入参:").append(requetParam.get(REQUEST_BODY));
        }
        log.warn(stringBuilder.toString());
        logContext.remove();
        return ioe;
    }
    @Override
    protected void log(String configKey, String format, Object... args) {
        if (log.isInfoEnabled()) {
            log.info(String.format(methodTag(configKey) + format, args));
        }
    }
}

