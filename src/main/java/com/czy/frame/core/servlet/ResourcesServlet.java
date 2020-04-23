package com.czy.frame.core.servlet;

import com.czy.frame.core.CoreContainer;
import com.czy.frame.util.StringUtil;
import org.apache.catalina.servlets.DefaultServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenzy
 * @description 处理静态资源
 * @since 2020-03-30
 */
public class ResourcesServlet extends DefaultServlet {
    private static Logger logger = LoggerFactory.getLogger(ResourcesServlet.class);
    private static String projectName = null;

    @Override
    public void init() throws ServletException {
        super.init();
        /*得到项目信息*/
        projectName = CoreContainer.getInstance().getProjectInfo().getProjectName();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        try {
            Boolean hasStaticPath=StringUtil.isNotBlank(projectName)?uri.startsWith("/" + projectName + "/static"):uri.startsWith("/static");
            Boolean isStaticResource = uri.endsWith(".html")||uri.endsWith(".js")||uri.endsWith(".css") || uri.endsWith(".jpg");
            /*静态资源且不以static开始，则请求转发*/
            if (isStaticResource&&!hasStaticPath){
                uri="/static" + uri;
                req.getRequestDispatcher(uri).forward(req, resp);
            }else {
                super.serveResource(req, resp, true, this.fileEncoding);
            }
        } catch (ServletException e) {
            logger.error("静态资源转发异常，uri:{}",uri);
        } catch (IOException e) {
            logger.error("静态资源转发-IO流异常，uri:{}",uri);
        }
    }


}
