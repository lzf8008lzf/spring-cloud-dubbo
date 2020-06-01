package com.enjoy;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-06-01 14:16
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class GeneratorCode {

    @Autowired
    private Environment environment;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Test
    public void generatorCode()
    {
        log.info(dataSourceProperties.getUrl());
        log.info(dataSourceProperties.getUsername());
        log.info(dataSourceProperties.getDriverClassName());
//        log.info(environment.getProperty("spring.datasource.url"));

        String packageName = "com.enjoy";
        generateByTables(packageName, "CMS_USER");
    }

    private void generateByTables(String packageName, String... tableNames) {
        // 全局配置
        GlobalConfig config = new GlobalConfig();
        AutoGenerator mpg = new AutoGenerator();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(environment.getProperty("spring.datasource.url"))
                .setUsername(environment.getProperty("spring.datasource.username"))
                .setPassword(environment.getProperty("spring.datasource.password"))
                .setDriverName(environment.getProperty("spring.datasource.driver-class-name"));
        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true) // 全局大写命名 ORACLE 注意
                .setEntityLombokModel(false) //实体 是否为lombok模型（默认 false）
                .setNaming(NamingStrategy.underline_to_camel) //表名生成策略
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组

        String projectPath = System.getProperty("user.dir");

        config.setActiveRecord(true) //是否 开启 ActiveRecord 模式
                .setAuthor("LiZhaofu")
                .setIdType(IdType.AUTO)
                .setOutputDir(projectPath + "/src/main/java")
                .setFileOverride(true)
                .setActiveRecord(true)
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(false);// XML columList

        config.setServiceName("%sService"); //自定义Service后戳,注意 %s 会自动填充表实体属性！

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };


        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        // 调整 xml 生成目录演示
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + ".xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // mpg.setTemplate(tc);

        //生成文件 配置
        mpg.setGlobalConfig(config)  //全局 相关配置
                .setDataSource(dataSourceConfig) //数据源配置
                .setStrategy(strategyConfig) //数据库表配置
                .setPackageInfo( //包 相关配置
                        new PackageConfig()  //跟包相关的配置项
                                .setParent(packageName)  //父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
                                .setController("controller") //Controller包名
                                .setEntity("entity") //entity包名
                )
                .execute();
    }

}
