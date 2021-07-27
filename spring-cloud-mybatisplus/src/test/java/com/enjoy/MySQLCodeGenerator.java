package com.enjoy;

import com.baomidou.mybatisplus.annotation.DbType;
import com.enjoy.core.utils.CodeGenerateUtils;
import org.springframework.util.DigestUtils;

/**
 * MySQL 数据库代码生成类
 * @author Erwin Feng
 * @since 2019-04-17 10:33
 */
public class MySQLCodeGenerator {

    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/yuexiang?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=round&&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "admin";
        // 表前缀，生成的实体类，不含前缀
        String [] tablePrefixes = {};
        // 表名，为空，生成所有的表
        String [] tableNames = {"access"};
        // 字段前缀
        String [] fieldPrefixes = {};
        // 基础包名
        String packageName = "com.enjoy";
        CodeGenerateUtils.execute( dbUrl, username, password, tablePrefixes, tableNames, packageName, fieldPrefixes);

//        System.out.println(DigestUtils.md5DigestAsHex("08affcd1d256a4b2".getBytes()));
    }

}
