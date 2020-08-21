package com.czy.http;

import com.czy.http.model.ServerInfo;
import com.czy.http.servlet.NoFoundServlet;
import com.czy.http.servlet.Servlet;
import com.czy.http.model.ServletInfo;
import com.czy.http.servlet.DefaultServlet;
import com.czy.http.servlet.HelloServlet;
import com.czy.util.io.FileUtil;
import com.czy.util.model.Par;
import com.czy.util.model.SettingFile;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
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

    public static ApplicationContext instance() {
        return instance;
    }

    private boolean isActivity;

    public void close() {
        servletMap = null;
    }

    public boolean isActivity() {
        return isActivity;
    }

    public void isActivity(boolean activity) {
        isActivity = activity;
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
        }
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
        servletInfo.addMaping(mapping);
        servletMap.add(mapping, servletInfo);
        return servletInfo;

    }

    public ServletInfo getServletInfo(String mapping) {
        var servletInfo = servletMap.get(mapping);
        if (servletInfo == null) {
            //mapping可能是正则表达式
            Par<ServletInfo> par = new Par();
            servletMap.forEach((key, value) -> {
                if (StringUtil.matcher(mapping, key)) {
                    par.set(value);
                    return;
                }
            });
            servletInfo = par.get();
        }
        if (servletInfo == null) {
            if (mapping.equals("/")) {
                servletInfo = servletMap.get("/hello");
            } else {
//                servletInfo = servletMap.get("/noFound");
                servletInfo = servletMap.get("/default");
            }
        }
        return servletInfo;
    }

    public Servlet getServlet(String mapping) {
        var servletInfo = getServletInfo(mapping);
        return servletInfo.getServlet();
    }

    /*初始化服务容器*/
    public void init() {
        if (!servletMap.containsKey("/hello")) {
            addServlet("/hello", "hello", HelloServlet.class);
        }
        if (!servletMap.containsKey("/noFound")) {
            addServlet("/noFound", "noFound", NoFoundServlet.class);
        }
        if (!servletMap.containsKey("/default")) {
            addServlet("/default", "default", DefaultServlet.class);
        }

        initServlet();
    }

    public void load() {
        load(new SettingFile(null, "server.yml"));
    }

    /*加载配置文件*/
    public void load(SettingFile settingFile) {
        var serverInfo = ServerInfo.instance();
        if (settingFile != null) {
            FileUtil.readYML(FileUtil.getResourceFile(settingFile.moduleDir(), settingFile.fileName())).ifPresent(map -> {
                Map<String, Object> temp = (Map<String, Object>) map.get("serverInfo");
                serverInfo.setPort(StringUtil.getInt(temp.get("port"), 8080));
                serverInfo.setCharset(Charset.forName(StringUtil.getStr(temp.get("charset"), "UTF-8")));
                serverInfo.setTimeout(StringUtil.getInt(temp.get("timeout"), 10000));
                try {
                    serverInfo.setAddress(InetAddress.getByName(StringUtil.getStr(temp.get("address"), "localhost")));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            });
            return;
        }
        if (serverInfo.getAddress() == null) {
            serverInfo.setPort(8080);
            serverInfo.setCharset(Charset.forName("UTF-8"));
            serverInfo.setTimeout(10000);
            try {
                serverInfo.setAddress(InetAddress.getByName("localhost"));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}
