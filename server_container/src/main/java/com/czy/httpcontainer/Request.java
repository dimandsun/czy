package com.czy.httpcontainer;

import com.czy.util.enums.QuestMethodEnum;
import com.czy.util.http.QuestScheme;
import com.czy.util.list.EnumerationFactory;
import com.czy.util.model.StringMap;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Request implements HttpServletRequest {
    private String authType;
    private String contextPath;
    private Cookie[] cookies;
    private long dateHeader;
    private String header;
    private StringMap headers;
    private StringMap attributes;
    private QuestMethodEnum questMethodEnum;
    private String pathInfo;
    private String charSet;
    private StringMap<String[]> par;
    private String protocol;
    private QuestScheme questScheme;
    private Locale locale;

    /**
     * 返回用于保护servlet的认证方案的名称。
     */
    @Override
    public String getAuthType() {
        return authType;
    }

    /**
     * 返回请求URI中指示请求上下文的部分。
     *
     * @return
     */
    @Override
    public String getContextPath() {
        return contextPath;
    }

    /**
     * 返回一个数组，其中包含Cookie 客户端与此请求一起发送的所有对象。
     *
     * @return
     */
    @Override
    public Cookie[] getCookies() {
        return cookies;
    }

    /**
     * 返回指定请求标头的long值作为代表Date对象的值 。
     *
     * @param name
     * @return
     */
    @Override
    public long getDateHeader(String name) {
        return (long) headers.get(name);
    }

    @Override
    public String getHeader(String name) {
        return (String) headers.get(name);
    }

    /**
     * 以形式返回指定请求标头的值String。
     *
     * @param name
     * @return
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        return null;
    }

    /**
     * 返回此请求包含的所有标头名称的枚举。
     *
     * @return
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        var vector = new Vector<String>();
        headers.keySet().forEach(key -> vector.addElement((String) key));
        return vector.elements();
    }

    /**
     * 以形式返回指定请求标头的值int。
     *
     * @param name
     * @return
     */
    @Override
    public int getIntHeader(String name) {
        return (int) headers.get(name);
    }

    /**
     * 返回发出此请求的HTTP方法的名称，例如GET，POST或PUT。
     *
     * @return
     */
    @Override
    public String getMethod() {
        return questMethodEnum.getMsg();
    }

    /**
     * 返回与客户端发出此请求时发送的URL关联的所有其他路径信息。
     *
     * @return
     */
    @Override
    public String getPathInfo() {
        return pathInfo;
    }

    /**
     * 返回servlet名称之后但查询字符串之前的所有其他路径信息，并将其转换为真实路径。
     *
     * @return
     */
    @Override
    public String getPathTranslated() {
        return null;
    }

    /**
     * 返回路径后面的请求URL中包含的查询字符串。
     *
     * @return
     */
    @Override
    public String getQueryString() {
        return null;
    }

    /**
     * 如果用户已通过身份验证，或者null用户未通过身份验证，则返回发出此请求的用户的登录名。
     *
     * @return
     */
    @Override
    public String getRemoteUser() {
        return null;
    }

    /**
     * 返回客户端指定的会话ID。
     *
     * @return
     */
    @Override
    public String getRequestedSessionId() {
        return getSession().getId();
    }

    /**
     * 返回此请求的URL的一部分，从协议名称到HTTP请求第一行中的查询字符串。
     *
     * @return
     */
    @Override
    public String getRequestURI() {
        return null;
    }

    /**
     * 重构客户端用于发出请求的URL。
     *
     * @return
     */
    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    /**
     * 返回此请求的URL中调用servlet的部分。
     *
     * @return
     */
    @Override
    public String getServletPath() {
        return null;
    }

    /**
     * 返回与此请求关联的当前会话，或者如果该请求没有会话，则创建一个会话。
     *
     * @return
     */
    @Override
    public HttpSession getSession() {
        return null;
    }

    /**
     * 返回HttpSession 与此请求相关联的当前会话；如果没有当前会话且create为true，则返回一个新会话。
     *
     * @param create
     * @return
     */
    @Override
    public HttpSession getSession(boolean create) {
        return null;
    }

    /**
     * 返回一个java.security.Principal对象，其中包含当前已认证用户的名称。
     *
     * @return
     */
    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    /**
     * 检查请求的会话ID是否作为cookie进入。
     *
     * @return
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    /**
     * 不推荐使用。 从Java Servlet API的2.1版本开始，请isRequestedSessionIdFromURL() 改用。
     *
     * @return
     */
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    /**
     * 检查请求的会话ID是否作为请求URL的一部分进入。
     *
     * @return
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    /**
     * 检查请求的会话ID是否仍然有效。
     *
     * @return
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    /**
     * 返回一个布尔值，指示已认证的用户是否包含在指定的逻辑“角色”中。
     *
     * @param role
     * @return
     */
    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    /**
     * 如果请求是针对受安全约束保护的资源，则触发相同的身份验证过程
     *
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    /**
     * 验证提供的用户名和密码，然后将经过验证的用户与请求关联起来
     *
     * @param username
     * @param password
     * @throws ServletException
     */
    @Override
    public void login(String username, String password) throws ServletException {

    }

    /**
     * 从请求中移除任何经过身份验证的用户
     *
     * @throws ServletException
     */
    @Override
    public void logout() throws ServletException {

    }

    /**
     * 返回所有上传部件的集合
     *
     * @return
     */
    @Override
    public Collection<Part> getParts() throws IOException, IllegalStateException, ServletException {
        return null;
    }

    /**
     * 获取命名的部件，如果该部件不存在，返回空。
     *
     * @param name
     * @return
     */
    @Override
    public Part getPart(String name) throws IOException, IllegalStateException, ServletException {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return EnumerationFactory.createEnumerationKey(attributes);
    }

    /**
     * 返回此请求主体中使用的字符编码的名称。
     *
     * @return
     */
    @Override
    public String getCharacterEncoding() {
        return charSet;
    }

    @Override
    public void setCharacterEncoding(String charSet) {
        this.charSet = charSet;
    }

    /**
     * 返回输入流可用的请求体的长度(以字节为单位)，如果长度未知则返回-1
     *
     * @return
     */
    @Override
    public int getContentLength() {
        return -1;
    }

    /**
     * 返回请求体的MIME类型，如果类型未知则返回null。
     *
     * @return
     */
    @Override
    public String getContentType() {
        return null;
    }

    /**
     * 使用ServletInputStream以二进制数据的形式检索请求主体。
     *
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    /**
     * 以字符串的形式返回请求参数的值，如果该参数不存在，则返回null。
     *
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        var temp = par.get(name);
        return temp == null ? null : temp[0];
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return EnumerationFactory.createEnumerationKey(par);
    }

    @Override
    public String[] getParameterValues(String name) {
        return par.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return par;
    }

    /**
     * 返回请求在协议/majorVersion表单中使用的协议的名称和版本。minorVersion，例如HTTP/1.1。
     *
     * @return
     */
    @Override
    public String getProtocol() {
        return protocol;
    }

    /**
     * 返回用于发出此请求的方案的名称，例如http、https或ftp。
     *
     * @return
     */
    @Override
    public String getScheme() {
        return questScheme.getValue();
    }

    /**
     * 返回发送请求到的服务器的主机名。
     *
     * @return
     */
    @Override
    public String getServerName() {
        return null;
    }

    /**
     * 返回发送请求的端口号
     *
     * @return
     */
    @Override
    public int getServerPort() {
        return 0;
    }

    /**
     * 使用BufferedReader以字符数据的形式检索请求主体
     *
     * @return
     * @throws IOException
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    /**
     * 返回发送请求的客户端或最后一个代理的Internet协议(IP)地址
     *
     * @return
     */
    @Override
    public String getRemoteAddr() {
        return null;
    }

    /**
     * 返回发送请求的客户端或最后一个代理的完全限定名。
     *
     * @return
     */
    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String name, Object o) {
        attributes.add(name, o);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    /**
     * 返回客户端将根据接受语言头接受内容的首选语言环境。
     * @return
     */
    @Override
    public Locale getLocale() {
        return locale;
    }

    /**
     * 返回语言环境对象的枚举，以首选语言环境开始的降序顺序指示客户端基于接受语言头可接受的语言环境。
     * @return
     */
    @Override
    public Enumeration<Locale> getLocales() {
        return EnumerationFactory.createEnumeration(Locale.getAvailableLocales());
    }

    /**
     * 此请求是否使用安全通道(如HTTPS)发出。
     * @return
     */
    @Override
    public boolean isSecure() {
        return QuestScheme.HTTPS.equals(questScheme);
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    /**
     * 在Java Servlet API的2.1版本中，使用ServletContext.getRealPath(Java .lang. string)代替。
     * @param path
     * @return
     */
    @Override public String getRealPath(String path) {
        return getServletContext().getRealPath(path);
    }

    /**
     * 返回发送请求的客户端或最后一个代理的Internet协议(IP)源端口。
     * @return
     */
    @Override
    public int getRemotePort() {
        return 0;
    }

    /**
     * 返回接收请求的Internet协议(IP)接口的主机名。
     * @return
     */
    @Override
    public String getLocalName() {
        return null;
    }

    /**
     * 返回接收请求的接口的Internet协议(IP)地址。
     * @return
     */
    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    /**
     * 获取当前的AsyncContext
     * @return
     */
    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }
}
