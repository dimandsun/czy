package com.czy.util;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequestUtil {
    static boolean proxySet = false;
    static String proxyHost = "127.0.0.1";
    static int proxyPort = 8087;



    private static URLConnection getConnection(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(30 * 1000);
        connection.setReadTimeout(30 * 1000);
        return connection;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            if (param == null || param.equals(""))
                urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = getConnection(realUrl);
            // 设置通用的请求属性
            connection.setRequestProperty("Charsert", "UTF-8"); //设置请求编码
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                // System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += new String(line.getBytes());
            }

        } catch (Exception e) {
            return null;
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                return null;
            }

        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url     发送请求的 URL
     * @param param   请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param isproxy 是否使用代理模式
     * @return 所代表远程资源的响应结果
     * @author xujie
     */
    public static String sendPost(String url, String param, boolean isproxy) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = getConnection(realUrl);
            if (isproxy) {//使用代理模式
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                conn = (URLConnection) realUrl.openConnection(proxy);
            }
            // 打开和URL之间的连接

            conn.setRequestProperty("Charsert", "UTF-8"); //设置请求编码
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            result = null;
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {

            }
        }
        return result;
    }


    public static void main(String[] args) {
        //demo:代理访问
        String url = "http://machinemanager.lxt6.cn:10085/zk_usermanager_api/student/GetStudentInfo";
        String para = "?code=RERGMDMyOTcwMkE3OUM4QTYyNjk0NEJBMDVCRkRGQkVBNzQ0NDNEMkJGODdDNERBMUYzMkI1OTFDRUFCREFGRUMyMDI2OTI2RDQyQ0Y4OTQyNUJGQkQxRDIwQzk1QThEOTNENEQ2RjExMzJDQzYwRjY2QTkzMUQ0MDdFOTcwRTU=&sign=b5778c21741364ecabcabb9b0fdb04ec&sid=140000000000000001&requestType=2";
        //String para = "";
        String sr = HttpRequestUtil.sendGet(url, para);
        System.out.println(sr);
    }
}
