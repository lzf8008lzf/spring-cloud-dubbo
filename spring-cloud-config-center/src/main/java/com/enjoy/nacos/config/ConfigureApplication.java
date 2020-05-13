package com.enjoy.nacos.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @program: spring-cloud-dubbo
 * @description: 配置中心启动类
 * @author: LiZhaofu
 * @create: 2020-05-13 13:07
 **/

@SpringBootApplication
public class ConfigureApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigureApplication.class, args);
    }
    @Bean
    public UserConfig userConfig() {
        return new UserConfig();
    }

}

@ConfigurationProperties(prefix = "user")
class UserConfig {

    private int age;

    private String name;

    private Map<String, Object> map;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "UserConfig{" + "age=" + age + ", name='" + name + '\'' + ", map=" + map
                + '}';
    }

}

@Component
class SampleRunner implements ApplicationRunner {

    @Value("${user.name}")
    String userName;

    @Value("${user.age:25}")
    int userAge;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(
                String.format("Initial username=%s, userAge=%d", userName, userAge));

        nacosConfigManager.getConfigService().addListener(
                "nacos-config-center.properties", "DEFAULT_GROUP", new Listener() {

                    /**
                     * Callback with latest config data.
                     *
                     * For example, config data in Nacos is:
                     *
                     * user.name=Nacos user.age=25
                     * @param configInfo latest config data for specific dataId in Nacos
                     * server
                     */
                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        Properties properties = new Properties();
                        try {
                            properties.load(new StringReader(configInfo));
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("config changed: " + properties);
                    }

                    @Override
                    public Executor getExecutor() {
                        return null;
                    }
                });
    }

}

@RestController
@RefreshScope
class SampleController {

    @Autowired
    UserConfig userConfig;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Value("${user.name}")
    String userName;

    @Value("${user.age:25}")
    Integer age;

    @RequestMapping("/user")
    public String simple() {
        return "Hello Nacos Config!" + "Hello " + userName + " " + age + " [UserConfig]: "
                + userConfig + "!" + nacosConfigManager.getConfigService();
    }

    @RequestMapping("/bool")
    public boolean bool() {
        return (Boolean) (userConfig.getMap().get("2"));
    }

}
