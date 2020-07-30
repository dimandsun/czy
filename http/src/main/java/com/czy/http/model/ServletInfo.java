package com.czy.http.model;

import com.czy.util.model.StringMap;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-30
 */
public class ServletInfo {
    private String servletName;
    private Servlet servlet;
    private int loadOrder;
    private List<String> mappings=new ArrayList<>();
    private StringMap initPar;

    public void addMaping(String mapping) {
        mappings.add(mapping);
    }

    public List<String> getMappings() {
        return mappings;
    }
    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public Servlet getServlet() {
        return servlet;
    }

    public void setServlet(Servlet servlet) {
        this.servlet = servlet;
    }

    public int getLoadOrder() {
        return loadOrder;
    }

    public void setLoadOrder(int loadOrder) {
        this.loadOrder = loadOrder;
    }



    public StringMap getInitPar() {
        return initPar;
    }
    public void setInitPar(StringMap initPar) {
        this.initPar = initPar;
    }


}
