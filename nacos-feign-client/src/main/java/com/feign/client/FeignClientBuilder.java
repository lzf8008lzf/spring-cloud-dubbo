package com.feign.client;


import javax.net.ssl.SSLContext;

import feign.httpclient.ApacheHttpClient;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.util.ResourceUtils;


@Slf4j
public class FeignClientBuilder {

    private boolean enabled;

    private String keyPassword;
    private String keyStore;
    private String keyStorePassword;
    private String trustStore;
    private String trustStorePassword;

    private int maxConnTotal = 2048;

    private int maxConnPerRoute = 512;

    public FeignClientBuilder(boolean enabled, String keyPassword, String keyStore, String keyStorePassword, String trustStore, String trustStorePassword, int maxConnTotal, int maxConnPerRoute) {
        this.enabled = enabled;
        this.keyPassword = keyPassword;
        this.keyStore = keyStore;
        this.keyStorePassword = keyStorePassword;
        this.trustStore = trustStore;
        this.trustStorePassword = trustStorePassword;

        /**
         * maxConnTotal是同时间正在使用的最多的连接数
         */
        this.maxConnTotal = maxConnTotal;
        /**
         * maxConnPerRoute是针对一个域名同时间正在使用的最多的连接数
         */
        this.maxConnPerRoute = maxConnPerRoute;
    }

    public ApacheHttpClient apacheHttpClient() {

        CloseableHttpClient defaultHttpClient = HttpClients.custom()
                .setMaxConnTotal(maxConnTotal)
                .setMaxConnPerRoute(maxConnPerRoute)
                .build();
        ApacheHttpClient defaultApacheHttpClient = new ApacheHttpClient(defaultHttpClient);

        if (!enabled) {
            return defaultApacheHttpClient;
        }

        SSLContextBuilder sslContextBuilder = SSLContexts.custom();

        // 如果 服务端启用了 TLS 客户端验证，则需要指定 keyStore
        if (keyStore == null || keyStore.isEmpty()) {
            return new ApacheHttpClient();
        } else {
            try {
                sslContextBuilder
                        .loadKeyMaterial(
                                ResourceUtils.getFile(keyStore),
                                keyStorePassword.toCharArray(),
                                keyPassword.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 如果 https 使用自签名证书，则需要指定 trustStore
        if (trustStore == null || trustStore.isEmpty()) {
        } else {
            try {
                sslContextBuilder
//        .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                        .loadTrustMaterial(
                                ResourceUtils.getFile(trustStore),
                                trustStorePassword.toCharArray()
                        );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            SSLContext sslContext = sslContextBuilder.build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setMaxConnTotal(maxConnTotal)
                    .setMaxConnPerRoute(maxConnPerRoute)
                    .setSSLSocketFactory(sslsf)
                    .build();

            ApacheHttpClient apacheHttpClient = new ApacheHttpClient(httpClient);
            log.info("feign Client load with ssl.");
            return apacheHttpClient;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultApacheHttpClient;
    }


    public static FeignClientBuilderBuilder builder() {
        return new FeignClientBuilderBuilder();
    }

    public static class FeignClientBuilderBuilder {

        private boolean enabled;

        private String keyPassword;
        private String keyStore;
        private String keyStorePassword;
        private String trustStore;
        private String trustStorePassword;

        private int maxConnTotal = 2048;

        private int maxConnPerRoute = 512;

        public FeignClientBuilderBuilder enabled(boolean enabled) {
            this.enabled = enabled;

            return this;
        }

        public FeignClientBuilderBuilder keyPassword(String keyPassword) {
            this.keyPassword = keyPassword;

            return this;
        }
        public FeignClientBuilderBuilder keyStore(String keyStore) {
            this.keyStore = keyStore;

            return this;
        }
        public FeignClientBuilderBuilder keyStorePassword(String keyStorePassword) {
            this.keyStorePassword = keyStorePassword;

            return this;
        }
        public FeignClientBuilderBuilder trustStore(String trustStore) {
            this.trustStore = trustStore;

            return this;
        }
        public FeignClientBuilderBuilder trustStorePassword(String trustStorePassword) {
            this.trustStorePassword = trustStorePassword;

            return this;
        }

        public FeignClientBuilderBuilder maxConnTotal(int maxConnTotal) {
            this.maxConnTotal = maxConnTotal;

            return this;
        }
        public FeignClientBuilderBuilder maxConnPerRoute(int maxConnPerRoute) {
            this.maxConnPerRoute = maxConnPerRoute;

            return this;
        }

        public FeignClientBuilder build() {
            return new FeignClientBuilder(
                    this.enabled,
                    this.keyPassword,
                    this.keyStore,
                    this.keyStorePassword,
                    this.trustStore,
                    this.trustStorePassword,
                    this.maxConnTotal,
                    this.maxConnPerRoute
            );
        }

    }

}

