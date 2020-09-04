package com.alibaba.cloud;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * @program: yuexiang
 * @description:
 * @author: LiZhaofu
 * @create: 2020-09-03 17:52
 **/

public class InitServletContextListener implements ServletContextListener {

    private static WebApplicationContext springContext;


    public void contextInitialized(ServletContextEvent sce) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        System.out.println("------------------------------------------------------------------contextInitialized");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("-------------------------------------------------------------------contextDestroyed");
    }

    public static ApplicationContext getApplicationContext() {
        return springContext;
    }

}