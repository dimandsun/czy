package com.czy.frame.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author czy
 * @date 2019/7/8
 * @description
 */
public class TaskListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        new TimerManager();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
