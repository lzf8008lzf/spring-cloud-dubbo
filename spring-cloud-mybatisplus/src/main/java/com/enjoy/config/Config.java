package com.enjoy.config;

/**
 * @author
 * @since 2019-04-17 12:04
 */
public class Config {

    /** 乐观锁字段名 */
    public static final String FIELD_VERSION_NAME = "version";

    /** 作者 */
    public static final String AUTHOR = "Jeff Lee";

    /** 生成文件的输出目录 */
    public static String projectPath = System.getProperty("user.dir");

    /** 输出目录 */
    public static final String outputDir = projectPath+"/src/main/java";

    /** 模板引擎。velocity / freemarker / beetl */
    public static final String TEMPLATE_ENGINE = "velocity";

}
