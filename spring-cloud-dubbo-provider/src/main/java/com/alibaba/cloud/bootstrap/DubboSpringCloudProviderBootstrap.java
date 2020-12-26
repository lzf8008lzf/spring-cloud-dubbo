/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.bootstrap;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import javax.cache.CacheException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Dubbo Spring Cloud Provider Bootstrap.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DubboSpringCloudProviderBootstrap {

	private Config loadConfig(ClassLoader classLoader, String fileName) {
		InputStream is = classLoader.getResourceAsStream(fileName);
		if (is != null) {
			try {
				return Config.fromYAML(is);
			} catch (IOException e) {
				try {
					is = classLoader.getResourceAsStream(fileName);
					return Config.fromJSON(is);
				} catch (IOException e1) {
					throw new CacheException("Can't parse yaml config", e1);
				}
			}
		}
		return null;
	}

	@Bean
	public RedissonClient redissonClient(){
		RedissonClient redissonClient = null;

		Config config = loadConfig(DubboSpringCloudProviderBootstrap.class.getClassLoader(), "redisson.yaml");;
		redissonClient = Redisson.create(config);

		return redissonClient;
	}

	public static void main(String[] args) {
//		new SpringApplicationBuilder(DubboSpringCloudProviderBootstrap.class)
//				.properties("spring.profiles.active=nacos").web(WebApplicationType.NONE)
//				.run(args);
		SpringApplication.run(DubboSpringCloudProviderBootstrap.class, args);
	}

}
