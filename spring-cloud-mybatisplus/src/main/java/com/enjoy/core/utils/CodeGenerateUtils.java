package com.enjoy.core.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.enjoy.config.Config;

import java.util.Collections;

/**
 *
 */
public class CodeGenerateUtils {

    /**
     * 数据连接信息
     * @param dbUrl 连接地址
     * @param username 用户名
     * @param password 密码
     * @return DataSourceConfig
     */
    private static DataSourceConfig dataSourceConfig(String dbUrl, String username, String password) {
        DataSourceConfig dataSourceConfig;
        dataSourceConfig = new DataSourceConfig.Builder(dbUrl,username,password).build();

        return dataSourceConfig;
    }

    // 配置
    private static GlobalConfig globalConfig() {

        GlobalConfig globalConfig = GeneratorBuilder.globalConfigBuilder().author(Config.AUTHOR)
                .enableSwagger().dateType(DateType.SQL_PACK).openDir(true).outputDir(Config.outputDir).build();

        return globalConfig;
    }


    private static StrategyConfig strategyConfig(String [] tablePrefixes, String [] tableNames, String [] fieldPrefixes) {
        StrategyConfig strategyConfig = GeneratorBuilder.strategyConfigBuilder()
                .addTablePrefix(tablePrefixes)
                .addInclude(tableNames)
                .addFieldPrefix(fieldPrefixes)
                .enableSkipView()
                .entityBuilder().enableChainModel().enableLombok().enableSerialVersionUID().naming(NamingStrategy.underline_to_camel)
                .controllerBuilder().enableHyphenStyle().enableRestStyle()
                .mapperBuilder().build();

        return strategyConfig;
    }

    // 包信息配置
    private static PackageConfig packageConfig(String packageName) {
        PackageConfig packageConfig =GeneratorBuilder.packageConfigBuilder().parent("com.enjoy")
                .build();
        return packageConfig;
    }


    /**
     * 获取模板引擎
     * @return 模板引擎 {@link AbstractTemplateEngine}
     */
    private static AbstractTemplateEngine getTemplateEngine() {
        String templateEngine = Config.TEMPLATE_ENGINE;
        switch (templateEngine) {
            case "velocity":
                return new VelocityTemplateEngine();
            case "freemarker":
                return new FreemarkerTemplateEngine();
            case "beetl":
                return new BeetlTemplateEngine();
        }
        return new VelocityTemplateEngine();
    }

    /**
     * 执行器
     * @param dbUrl
     * @param username
     * @param password
     * @param tablePrefixes
     * @param tableNames
     * @param packageName
     */
    public static void execute(String dbUrl, String username, String password,
                               String [] tablePrefixes, String [] tableNames, String packageName, String [] fieldPrefixes) {
        GlobalConfig globalConfig = globalConfig();
        DataSourceConfig dataSourceConfig = dataSourceConfig(dbUrl, username, password);
        StrategyConfig strategyConfig = strategyConfig(tablePrefixes, tableNames, fieldPrefixes);
        PackageConfig packageConfig = packageConfig(packageName);
        AbstractTemplateEngine templateEngine = getTemplateEngine();

        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig).global(globalConfig)
                .strategy(strategyConfig)
                .packageInfo(packageConfig);

        autoGenerator.execute(templateEngine);
    }

}
