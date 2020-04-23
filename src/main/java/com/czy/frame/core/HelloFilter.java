package com.czy.frame.core;

import javax.servlet.*;
import java.io.IOException;

/**
 * 暂时不用
 * @author 陈志源 on 2019-01-04.
 */
@Deprecated
public class HelloFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*   String uri = req.getRequestURI();
        HelloAction helloAction = actionMap.get(uri);
        if(helloAction!=null){
            try {
                //反射实现具体业务
                helloAction.getMethodName().invoke(helloAction.getAction(),req,resp);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }*/
        filterChain.doFilter(req,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
