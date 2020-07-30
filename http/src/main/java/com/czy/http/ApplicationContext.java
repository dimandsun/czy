package com.czy.http;

import com.czy.http.model.Servlet;
import com.czy.http.model.ServletInfo;
import com.czy.util.model.StringMap;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author chenzy
 * @date 2020-07-30
 */
public class ApplicationContext {
    private static ApplicationContext instance = new ApplicationContext();

    private ApplicationContext() {
        servletMap = new StringMap<>();
    }

    public static ApplicationContext getInstance() {
        return instance;
    }
    private StringMap<ServletInfo> servletMap;
    public void initServlet() {
        //排序后初始化
        servletMap.values().stream().sorted(Comparator.comparingInt(ServletInfo::getLoadOrder)).forEach(servletInfo -> {
            servletInfo.getServlet().init(servletInfo);
        });
    }
    public ServletInfo addServlet(String mapping, String servletName, Class<? extends Servlet> servletClass) {
        Objects.requireNonNull(mapping);

        var servletInfo = servletMap.get(mapping);
        if (servletInfo == null) {
            servletInfo = new ServletInfo();
            servletInfo.setServletName(servletName);
            try {
                servletInfo.setServlet(servletClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            servletMap.add(mapping, servletInfo);
        }
        servletInfo.addMaping(mapping);
        return servletInfo;

    }
    public Servlet getServlet(String mapping){
        var servletInfo=servletMap.get(mapping);
        Objects.requireNonNull(servletInfo,"未找到指定servlet");
        return servletInfo.getServlet();
    }

}
