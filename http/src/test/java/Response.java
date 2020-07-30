import com.czy.http.enums.MIMEEnum;
import com.czy.http.model.MIME;
import com.czy.http.model.QuestScheme;
import com.czy.http.model.ServerInfo;
import com.czy.util.enums.QuestMethodEnum;
import com.czy.util.model.StringMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Response implements HttpServletResponse {
    public Response(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    /**********************************第一行*******************************************************/
    //GET
    private QuestMethodEnum questMethodEnum;
    //nelson
    private String path;
    //get请求取url的?号后值a=123
    private StringMap<String[]> parMap;
    //HTTP/1.1 new QuestScheme(QuestScheme.HTTP,1.1);
    private QuestScheme questScheme;
    /**********************************请求头信息列表，每一行都是key: value*******************************************************/
    private StringMap<String> headerMap;

    private ServerInfo serverInfo;
    /*Accept：浏览器可接受的MIME类型。*/
//    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
    private Map<MIMEEnum, MIME> mimeMap;

    /**********************************空行，用于与请求体分开*******************************************************/


    /**********************************请求体*******************************************************/
    private String body;


    /**
     * 将指定的cookie添加到响应中
     *
     * @param cookie
     */
    @Override
    public void addCookie(Cookie cookie) {

    }

    /**
     * 返回一个布尔值，指示命名的响应头是否已经设置。
     *
     * @param name
     * @return
     */
    @Override
    public boolean containsHeader(String name) {
        return false;
    }

    /**
     * 通过在其中包含会话ID来编码指定的URL，或者，如果不需要编码，则不加更改地返回URL
     *
     * @param url
     * @return
     */
    @Override
    public String encodeURL(String url) {
        return null;
    }

    /**
     * 编码指定的URL以供在sendRedirect方法中使用，或者，如果不需要编码，则不加更改地返回URL。
     *
     * @param url
     * @return
     */
    @Override
    public String encodeRedirectURL(String url) {
        return null;
    }

    /**
     * 由encodeURL替代
     *
     * @param url
     * @return
     */
    @Deprecated
    @Override
    public String encodeUrl(String url) {
        return null;
    }

    /**
     * 弃用。
     * 在2.1版本中，使用encodeRedirectURL(字符串url)代替
     *
     * @param url
     * @return
     */
    @Deprecated
    @Override
    public String encodeRedirectUrl(String url) {
        return null;
    }

    /**
     * 使用指定的状态码向客户端发送错误响应，并清除输出缓冲区。
     */
    @Override
    public void sendError(int sc, String msg) throws IOException {

    }

    /**
     * 使用指定的状态码向客户端发送一个错误响应，并清除缓冲区
     *
     * @param sc
     * @throws IOException
     */
    @Override
    public void sendError(int sc) throws IOException {

    }

    /**
     * 使用指定的重定向位置URL向客户端发送临时重定向响应。
     *
     * @param location
     * @throws IOException
     */
    @Override
    public void sendRedirect(String location) throws IOException {

    }

    /**
     * 使用给定的名称和日期值设置响应头
     *
     * @param name
     * @param date
     */
    @Override
    public void setDateHeader(String name, long date) {

    }

    /**
     * 添加具有给定名称和日期值的响应标头
     *
     * @param name
     * @param date
     */
    @Override
    public void addDateHeader(String name, long date) {

    }

    /**
     * 设置具有给定名称和值的响应标头。
     *
     * @param name
     * @param value
     */
    @Override
    public void setHeader(String name, String value) {

    }

    /**
     * 添加具有给定名称和值的响应头。
     *
     * @param name
     * @param value
     */
    @Override
    public void addHeader(String name, String value) {

    }

    /**
     * 使用给定的名称和整数值设置响应标头。
     *
     * @param name
     * @param value
     */
    @Override
    public void setIntHeader(String name, int value) {

    }

    /**
     * 添加具有给定名称和整数值的响应标头。
     *
     * @param name
     * @param value
     */
    @Override
    public void addIntHeader(String name, int value) {

    }

    /**
     * 设置此响应的状态码。
     *
     * @param sc
     */
    @Override
    public void setStatus(int sc) {

    }

    /**
     * 弃用。
     * 在2.1版本中，由于消息参数的含义不明确。使用setStatus(int)设置状态码，使用sendError(int, String)发送带有描述的错误。
     *
     * @param sc
     * @param sm
     */
    @Override
    @Deprecated
    public void setStatus(int sc, String sm) {

    }

    /**
     * 获取此响应的HTTP状态代码。
     *
     * @return
     */
    @Override
    public int getStatus() {
        return 0;
    }

    /**
     * 返回指定标头的值，如果未设置该标头，则返回null。
     *
     * @param name
     * @return
     */
    @Override
    public String getHeader(String name) {
        return null;
    }

    /**
     * 返回与指定标头名称关联的所有标头值的集合。
     *
     * @param name
     * @return
     */
    @Override
    public Collection<String> getHeaders(String name) {
        return null;
    }

    /**
     * 得到响应头名称集合
     *
     * @return
     */
    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return serverInfo.charSet().get();
    }

    /**
     * 返回响应体的MIME类型，如果类型未知则返回null。
     *
     * @return
     */
    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public void setCharacterEncoding(String charset) {
        serverInfo.charSet(charset);
    }

    /**
     * 设置响应体的长度(以字节为单位)
     *
     * @param len
     */
    @Override
    public void setContentLength(int len) {

    }

    /**
     * 设置响应体的MIME类型
     *
     * @param type
     */
    @Override
    public void setContentType(String type) {

    }

    /**
     * 设置响应主体的首选缓冲区大小。
     *
     * @param size
     */
    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    /**
     * 强制将缓冲区中的任何内容写入客户端。
     *
     * @throws IOException
     */
    @Override
    public void flushBuffer() throws IOException {

    }

    /**
     * 清除响应中底层缓冲区的内容，而不清除标头或状态代码。
     */
    @Override
    public void resetBuffer() {

    }

    /**
     * 返回一个布尔值，指示是否已提交响应。已提交的响应已经编写了状态码和头。
     *
     * @return
     */
    @Override
    public boolean isCommitted() {
        return false;
    }

    /**
     * 清除存在于缓冲区中的任何数据以及状态代码和标头。如果已提交响应，此方法将抛出IllegalStateException。
     */
    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
