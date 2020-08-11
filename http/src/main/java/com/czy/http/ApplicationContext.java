package com.czy.http;

import com.czy.http.model.ServerInfo;
import com.czy.http.model.Servlet;
import com.czy.http.model.ServletInfo;
import com.czy.util.model.Par;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

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
        isActivity(true);
    }

    public static ApplicationContext getInstance() {
        return instance;
    }
    private ServerInfo serverInfo;
    private boolean isActivity;

    public boolean isActivity() {
        return isActivity;
    }
    public void isActivity(boolean activity) {
        isActivity = activity;
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    private StringMap<ServletInfo> servletMap;
    public void initServlet() {
        //排序后初始化
        servletMap.values().stream().sorted(Comparator.comparingInt(ServletInfo::getLoadOrder)).forEach(servletInfo -> {
            servletInfo.getServlet().init(servletInfo);
        });
    }
    public ServerInfo getServerInfo() {
        return serverInfo;
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
    public ServletInfo getServletInfo(String mapping){
        var servletInfo=servletMap.get(mapping);
        if (servletInfo==null){
            //mapping可能是正则表达式
            Par<ServletInfo> par=new Par();
            servletMap.forEach((key,value)->{
                if (StringUtil.matcher(mapping,key)){
                    par.set(value);
                    return;
                }
            });
            servletInfo=par.get();
        }
        if (servletInfo==null){
            servletInfo=servletMap.get("/default");
        }
        return servletInfo;
    }
    public Servlet getServlet(String mapping){
        var servletInfo=getServletInfo(mapping);
        return servletInfo.getServlet();
    }

}
