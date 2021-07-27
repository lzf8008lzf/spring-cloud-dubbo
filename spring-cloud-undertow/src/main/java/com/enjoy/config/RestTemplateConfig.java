//package com.enjoy.config;
//
///**
// * @program: spring-cloud-dubbo
// * @description:
// * @author: LiZhaofu
// * @create: 2021-05-14 19:15
// **/
//
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HeaderElement;
//import org.apache.http.HeaderElementIterator;
//import org.apache.http.HttpHost;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.ConnectionKeepAliveStrategy;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicHeaderElementIterator;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.ssl.SSLContexts;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.*;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.ResponseErrorHandler;
//import org.springframework.web.client.RestTemplate;
//
//import javax.net.ssl.SSLContext;
//import java.io.*;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.X509Certificate;
//import java.util.*;
//
///**
// * @version v1.0
// * @description: RestTemplate配置类
// * @author: 47
// */
//@Configuration
//public class RestTemplateConfig {
//
//    private final static Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);
//
//    //==============Resttemplate相关参数====================
//    @Value("${spring.restTemplate.readTimeout}")
//    private int readTimeout;
//    @Value("${spring.restTemplate.connectTimeout}")
//    private int connectTimeout;
//    @Value("${spring.restTemplate.connectionRequestTimeout}")
//    private int connectionRequestTimeout;
//
//    //==============Httpclient连接池相关参数====================
//    @Value("${spring.restTemplate.httpclient.maxTotal}")
//    private int maxTotal;
//    @Value("${spring.restTemplate.httpclient.maxConnectPerRoute}")
//    private int maxConnectPerRoute;
//    @Value("${spring.restTemplate.httpclient.enableRetry}")
//    private boolean enableRetry;
//    @Value("${spring.restTemplate.httpclient.retryTimes}")
//    private int retryTimes;
//    @Value("#{${spring.restTemplate.httpclient.keepAliveTargetHosts}}")
//    private Map<String, Long> keepAliveTargetHosts;
//    @Value("${spring.restTemplate.httpclient.keepAliveTime}")
//    private long keepAliveTime;
//
//
//    /**
//     * 配置RestTemplate
//     *
//     * @param clientHttpRequestFactory
//     * @return
//     */
//    @Bean
//    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
//        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
//        //默认使用ISO-8859-1编码，此处修改为UTF-8
//        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
//        while (iterator.hasNext()) {
//            HttpMessageConverter<?> converter = iterator.next();
//            if (converter instanceof StringHttpMessageConverter) {
//                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("UTF-8"));
//            }
//        }
//        //设置自定义异常处理
//        restTemplate.setErrorHandler(new MyErrorHandler());
//        //设置日志拦截器
//        restTemplate.getInterceptors().add(new LoggingInterceptor());
//        return restTemplate;
//    }
//
//    /**
//     * 配置httpclient工厂
//     *
//     * @param httpClient
//     * @return
//     */
//    @Bean
//    public ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient) {
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        factory.setHttpClient(httpClient);
//        //客户端和服务端建立连接的超时时间,这里最大只能是20s,因为linux操作系统的tcp进行三次握手的默认超时时间是20s,,即便设置100s也是在20s后报错
//        factory.setConnectTimeout(connectTimeout); //毫秒
//        //即socketTime,数据响应读取超时时间，指的是两个相邻的数据包的间隔超时时间
//        factory.setReadTimeout(readTimeout); //毫秒
//        //连接不够用时从连接池获取连接的等待时间，必须设置。不宜过长，连接不够用时，等待时间过长将是灾难性的
//        factory.setConnectionRequestTimeout(connectionRequestTimeout); //毫秒
//        //必须使用BufferingClientHttpRequestFactory这个包装类，否则默认实现只允许读取一次响应流，日志输出那里读取之后，请求调用处再读取时会报错
//        BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(factory);
//        return bufferingClientHttpRequestFactory;
//    }
//
//    /**
//     * 配置httpclient
//     *
//     * @return
//     */
//    @Bean
//    public HttpClient httpClient(ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        SSLContext sslContext = null;
//        try {
//            sslContext = SSLContexts.custom().loadTrustMaterial(null, (X509Certificate[] var1, String var2) -> true).build();
//        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
//            logger.error(e.getMessage());
//        }
////        httpClientBuilder.setSSLContext(sslContext);
//        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
//        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//                // 注册http和https请求
//                .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                .register("https", sslConnectionSocketFactory)
//                .build();
//        //使用Httpclient连接池的方式配置(推荐)，同时支持netty，okHttp以及其他http框架
//        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//        // 最大tcp连接数
//        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
//        // 同路由最大tcp连接数
//        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxConnectPerRoute);
//        //配置连接池
//        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
//        // 是否允许重试和重试次数
//        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(retryTimes, enableRetry));
//
//        //设置默认请求头
////        List<Header> headers = getDefaultHeaders();
////        httpClientBuilder.setDefaultHeaders(headers);
//
//        //设置长连接保持策略
//        httpClientBuilder.setKeepAliveStrategy(connectionKeepAliveStrategy);
//
//        return httpClientBuilder.build();
//    }
//
//    /**
//     * 配置长连接策略
//     *
//     * @return
//     */
//    @Bean
//    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
//        return (response, context) -> {
//            // Honor 'keep-alive' header
//            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
//            logger.debug("HeaderElement:{}", it);
//            while (it.hasNext()) {
//                HeaderElement he = it.nextElement();
//                String param = he.getName();
//                String value = he.getValue();
//                if (value != null && "timeout".equalsIgnoreCase(param)) {
//                    try {
//                        //1.服务器有时候会告诉客户端长连接的超时时间，如果有则设置为服务器的返回值
//                        return Long.parseLong(value) * 1000;
//                    } catch (NumberFormatException ignore) {
//                        logger.error("解析长连接过期时间异常", ignore);
//                    }
//                }
//            }
//            //2.如果服务器没有返回超时时间则采用配置的时间
//            //a.如果请求目标地址,单独配置了长连接保持时间,使用该配置 b.否则使用配置的的默认长连接保持时间keepAliveTime
//            HttpHost target = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
//            Optional<Map.Entry<String, Long>> any = Optional.ofNullable(keepAliveTargetHosts).orElseGet(HashMap::new)
//                    .entrySet().stream().filter(e -> e.getKey().equalsIgnoreCase(target.getHostName())).findAny();
//            return any.map(e -> e.getValue()).orElse(keepAliveTime);
//        };
//    }
//
//    /**
//     * 自定义异常处理
//     */
//    private class MyErrorHandler implements ResponseErrorHandler {
//
//        @Override
//        public boolean hasError(ClientHttpResponse response) throws IOException {
//            return !response.getStatusCode().is2xxSuccessful();
//        }
//
//        @Override
//        public void handleError(ClientHttpResponse response) throws IOException {
//            //响应状态码错误时不抛出异常
//            throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText(), response.getHeaders(), IOUtils.toByteArray(response.getBody()), Charset.forName("UTF-8"));
//        }
//    }
//
//    /**
//     * 日志拦截器
//     */
//    private class LoggingInterceptor implements ClientHttpRequestInterceptor {
//
//        @Override
//        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//            traceRequest(request, body);
//            long start = System.currentTimeMillis();
//            ClientHttpResponse response = execution.execute(request, body);
//            long end = System.currentTimeMillis();
//            traceResponse(body, request, response, (end - start) / 1000.0f);
//            return response;
//        }
//
//        private void traceRequest(HttpRequest request, byte[] body) {
//            StringBuilder log = new StringBuilder();
//            log.append("\n===========================request begin===========================\n")
//                    .append("URI         : ").append(request.getURI()).append("\n")
//                    .append("Method      : ").append(request.getMethod()).append("\n")
//                    .append("Headers     : ").append(request.getHeaders()).append("\n")
//                    .append("Request body: ").append(new String(body, Charset.forName("UTF-8"))).append("\n")
//                    .append("===========================request end==============================");
//            logger.debug(log.toString());
//        }
//
//        private void traceResponse(byte[] body, HttpRequest request, ClientHttpResponse response, float time) throws IOException {
//            StringBuilder inputStringBuilder = new StringBuilder();
//            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"))) {
//                String line = bufferedReader.readLine();
//                while (line != null) {
//                    inputStringBuilder.append(line);
//                    line = bufferedReader.readLine();
//                }
//            }
//            StringBuilder log = new StringBuilder();
//            if (response.getStatusCode().is2xxSuccessful()) {
//                log.append("\n===========================response begin===========================\n")
//                        .append("TIME         : ").append(time).append("s").append("\n")
//                        .append("URI          : ").append(request.getURI()).append("\n")
//                        .append("Method       : ").append(request.getMethod()).append("\n")
//                        .append("Status code  : ").append(response.getStatusCode()).append("\n")
//                        .append("Headers      : ").append(response.getHeaders()).append("\n")
//                        .append("Response body: ").append(inputStringBuilder.toString()).append("\n")
//                        .append("===========================response end==============================");
//                logger.debug(log.toString());
//            } else {
//                log.append("\n===========================response begin===========================\n")
//                        .append("TIME         : ").append(time).append("s").append("\n")
//                        .append("URI          : ").append(request.getURI()).append("\n")
//                        .append("Method       : ").append(request.getMethod()).append("\n")
//                        .append("Headers      : ").append(request.getHeaders()).append("\n")
//                        .append("Request body : ").append(new String(body, Charset.forName("UTF-8"))).append("\n")
//                        .append("Status code  : ").append(response.getStatusCode()).append("\n")
//                        .append("Headers      : ").append(response.getHeaders()).append("\n")
//                        .append("Response body: ").append(inputStringBuilder.toString()).append("\n")
//                        .append("===========================response end==============================");
//                logger.error(log.toString());
//            }
//        }
//
//
//        private String getLogRules() {
//            String rule = "";
//            String path = System.getProperty("user.dir");
//            StringBuffer sb = new StringBuffer();
//            String logRulesPath = sb.append(path)
//                    .append(File.separator)
//                    .append("config")
//                    .append(File.separator)
//                    .append("log-rules")
//                    .append(File.separator).append("log-print-rules.json").toString();
//            FileInputStream fileInputStream = null;
//            try {
//                fileInputStream = new FileInputStream(new File(logRulesPath));
//                rule = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8.name());
//            } catch (IOException e) {
//                logger.error("Load rules file error.", e);
//            } finally {
//                if (fileInputStream != null) {
//                    try {
//                        fileInputStream.close();
//                    } catch (IOException e) {
//                        logger.error("Closing stream error.", e);
//                    }
//                }
//            }
//            return rule;
//        }
//
//    }
//}
