package com.czy.frame.core.servlet;

import com.czy.frame.core.model.RouteModel;
import com.czy.frame.model.MyMap;
import com.czy.frame.model.OutPar;
import com.czy.frame.util.StringUtil;
import com.czy.frame.util.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有请求入口(除静态资源请求),把请求分配给不同的controller
 *
 * @author 陈志源 on 2019-01-01.
 */
public class DispatchServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(DispatchServlet.class);
    protected static MyMap<RouteModel> routeModelMap = null;
    protected static String projectName = null;
    protected static String charEncoding = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        routeModelMap = (MyMap<RouteModel>) getServletContext().getAttribute("routeModelMap");
        projectName = getServletContext().getAttribute("projectName").toString();
        charEncoding = getServletContext().getAttribute("charEncoding").toString();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.exec(req, resp, "get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        this.exec(req, resp, "post");
    }
    private RouteModel getRouteModel(HttpServletRequest req, HttpServletResponse resp, String questMethd, String url) {
        String noFoundURL = "/static/404.html";
        try {
            req.setCharacterEncoding(charEncoding);
            resp.setCharacterEncoding(charEncoding);
            resp.setHeader("Content-type", "text/html;" + charEncoding);
            String method = req.getParameter("_method");
            if (StringUtil.isBlank(method)) {
                method = questMethd;
            } else {
                method = method.trim().toLowerCase();
            }
            RouteModel routeModel = routeModelMap.get(method + url);
            if (routeModel == null) {
                //再看看是否有可以接受任意请求方式的接口
                routeModel=routeModelMap.get("all"+url);
                if (routeModel == null) {
                    req.getRequestDispatcher(noFoundURL).forward(req, resp);
                    return null;
                }
            }
            return routeModel;
        } catch (UnsupportedEncodingException e) {
            logger.error("编码转换异常，不支持编码:{}", charEncoding);
        } catch (ServletException e) {
            logger.error("请求转发异常,转发地址:{}", noFoundURL);
        } catch (IOException e) {
            logger.error("请求转发IO异常,转发地址:{}", noFoundURL);
        }
        return null;
    }

    /**
     * 处理结果,以josn形式输出
     */
    private void returnJson(Object result, HttpServletResponse resp) {
        String resultJosn = JsonUtil.model2Str(result);
        try {
            OutputStream outputStream = resp.getOutputStream();
            //将字符转换成字节数组，指定以UTF-8编码进行转换
            byte[] dataByteArr = resultJosn.getBytes(charEncoding);
            //使用OutputStream流向客户端输出字节数组
            outputStream.write(dataByteArr);
        } catch (IOException e) {
            logger.error("以json形式返回请求结果时IO异常，返回数据：{}",resultJosn);
        }
    }

    private void exec(HttpServletRequest req, HttpServletResponse resp, String questMethd) {
        OutPar<Object> urlVerifyResult = new OutPar<>();
        String url = getURL(req,urlVerifyResult);
        {
            Object parVerify = urlVerifyResult.get();
            /*url验证不通过，直接返回错误信息*/
            if (parVerify!=null){
                returnJson(parVerify, resp);
                return;
            }
        }
        RouteModel routeModel = getRouteModel(req, resp, questMethd, url);
        if (routeModel == null) {
            return;
        }
        try {
            Method modelMethod = routeModel.getMethod();
            /*获取参数,子类实现getPar方法，即对参数可以做自定义的校验及解析*/
            OutPar<Object> parVerifyResult = new OutPar<>(null);
            Object[] pars = getPar(modelMethod, req, resp,parVerifyResult);
            Object parVerify = parVerifyResult.get();
            /*数据验证不通过，直接返回错误信息*/
            if (parVerify!=null){
                returnJson(parVerify, resp);
                return;
            }
            /*执行业务方法*/
            Object result = null;
            if (pars == null||pars.length==0) {
                result = modelMethod.invoke(routeModel.getBeanModel().getBean());
            } else {
                result = modelMethod.invoke(routeModel.getBeanModel().getBean(), pars);
            }
            /*输出结果*/
            returnJson(result, resp);
        } catch (Exception e) {
            logger.error("访问异常：[url：" + url + "]", e);
        }


    }
    protected String getURL(HttpServletRequest req,OutPar<Object> urlVerifyResult) {
        String url = req.getRequestURI();
        if (StringUtil.isNotBlank(projectName)) {
            url = url.replace("/" + projectName, "");
        }
        return url;
    }
    /**
     * 映射参数，返回json格式参数。
     * 子类实现此方法，即对参数可以做自定义的校验及解析
     * @param method
     * @param req
     * @return
     */
    protected Object[] getPar(Method method, HttpServletRequest req, HttpServletResponse resp,OutPar<Object> parVerifyResult) {
        Class[] parClasss = method.getParameterTypes();
        /*业务方法没有参数*/
        if (parClasss == null||parClasss.length==0) {
            return null;
        }
        return new Object[]{getJson(method,req,resp)};
    }
    private MyMap getJson(Method method, HttpServletRequest req, HttpServletResponse resp) {
        Enumeration paramNames = req.getParameterNames();
//        ObjectNode objectNode = JsonUtil.createJsonNode();
        MyMap<Object> par = new MyMap<>();
        while (paramNames.hasMoreElements()) {
            String key = paramNames.nextElement().toString().trim();
            if ("_method".equals(key)) {
                continue;
            }
            StringBuilder value = new StringBuilder();
            for (String v : req.getParameterValues(key)) {
                value.append(v);
            }
            par.put(key, value.toString());
        }
        return par;
    }
/*    private Map<String, String> getParMap(HttpServletRequest req) {
        Map<String, String[]> map = req.getParameterMap();
        Map<String, String> result = new HashMap<>(map.size());
        String key = null, val = null;
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            key = entry.getKey();
            String[] vals = entry.getValue();
            if (vals != null) {
                val = StringUtils.join(vals, ",");
            }
            result.put(key, val);
        }
        // GET
        if (req.getMethodName().equals("GET")) {
//            return new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");
            // POST
        } else {
            result.putAll(getRequestPostStr(req));
        }
        return result;
    }*/

    private Map<String, String> getRequestPostStr(HttpServletRequest req) {
        Map<String, String> result = new HashMap<>();
        int contentLength = req.getContentLength();
        if (contentLength < 0) {
            return result;
        }
        byte buffer[] = new byte[contentLength];
        try {
            for (int i = 0; i < contentLength; ) {
                int readlen = 0;
                readlen = req.getInputStream().read(buffer, i, contentLength - i);
                if (readlen == -1) {
                    break;
                }
                i += readlen;
            }
            String json = new String(buffer, charEncoding);
            if (json.length() < 1) {
                return result;
            }
//            result = JSON.parseObject(json, HashMap.class);
            return result;
        } catch (IOException e) {
            logger.error("", e);
        }
        return result;
    }
}
