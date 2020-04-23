package com.czy.frame.core.servlet;

import com.czy.frame.core.CoreContainer;
import com.czy.frame.db.config.DataSourceEnum;
import com.czy.frame.db.config.DataSourceHolder;
import com.czy.frame.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by czy on 2019/5/30.
 * 初始化log4j和容器 ，此时log4j没有创建，无法使用log日志
 */
public class InitServlet extends HttpServlet{
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
       /* System.out.println("------------------------------日志正在初始化------------------------------");
        String log4jLocation = config.getInitParameter("log4j-properties-location");
        if (log4jLocation == null) {
            System.err.println("*** 没有 log4j-properties-location 初始化的文件, 所以使用 BasicConfigurator初始化");
            BasicConfigurator.configure();
        } else {
            String classPath = config.getServletContext().getRealPath("/WEB-INF/classes");
            String log4jProp = classPath + log4jLocation.replace("/WEB-INF","");
            File logFile = new File(log4jProp);
            if (logFile.exists()) {
                System.out.println("使用: " + log4jProp+"初始化日志设置信息");
                PropertyConfigurator.configure(log4jProp);
            } else {
                System.err.println("*** " + log4jProp + " 文件没有找到， 所以使用 BasicConfigurator初始化");
                BasicConfigurator.configure();
            }
        }
        System.out.println("------------------------------日志初始化完成------------------------------");*/
        initProject(getRealContainer(),config);
    }
    protected <DataSourceKey>void createDataSourceHolder(Class<DataSourceKey> dataSourceKeyClass, DataSourceKey defaultDataSourceKey){
        DataSourceHolder.createInstance(dataSourceKeyClass,defaultDataSourceKey);
    }
    protected CoreContainer getRealContainer(){
        return CoreContainer.getInstance();
    }
    private void initProject(CoreContainer container,ServletConfig config){
        System.out.println("------------------------------容器正在初始化-----------------------------");
        /*多数据源*/
        createDataSourceHolder(DataSourceEnum.class,DataSourceEnum.DEFAULT);
        /*初始化用户的项目*/
        String projectGroupId =config.getInitParameter("projectGroupId");
        if (StringUtil.isNotBlank(projectGroupId)){
            container.addProjectGroupId(projectGroupId);
        }
        container.initProject();
        /*项目信息放入ServletContext*/
        ServletContext application=getServletContext();
//        项目名称
        application.setAttribute("projectName", container.getProjectInfo().getProjectName());
        /*存储路由与业务方法的map*/
        application.setAttribute("routeModelMap",container.getRouteMap());
        application.setAttribute("charEncoding","UTF-8");
        System.out.println("------------------------------容器初始化完成------------------------------");
    }
    protected void endInitProject(){

    }
}