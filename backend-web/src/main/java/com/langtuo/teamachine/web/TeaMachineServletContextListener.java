package com.langtuo.teamachine.web;

import com.langtuo.teamachine.biz.aync.threadpool.AsyncExeService;
import com.langtuo.teamachine.mqtt.threadpool.ConsumeExeService;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author Jiaqing
 */
public class TeaMachineServletContextListener implements ServletContextListener {
    /**
     * 等待销毁的时间
     */
    private static final int WAITTING_DESTROY_TIME = 1000 * 5;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 应用启动时的操作
        System.out.println("teaMachineServletContextListener|contextInitialized|entering");
        System.out.println("teaMachineServletContextListener|contextInitialized|exiting");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 应用停止时的操作
        // 例如: 关闭数据库连接，停止后台线程等
        System.out.println("## teaMachineServletContextListener|contextDestroyed|entering");

        // 销毁线程池
        AsyncExeService.destroy();
        ConsumeExeService.destroy();

        // 手动解除驱动注册
        Enumeration<Driver> enumeration = DriverManager.getDrivers();
        while (enumeration.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(enumeration.nextElement());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            AbandonedConnectionCleanupThread.uncheckedShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 等待清理完成
        try {
            Thread.sleep(WAITTING_DESTROY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("## teaMachineServletContextListener|contextDestroyed|exiting");
    }
}
