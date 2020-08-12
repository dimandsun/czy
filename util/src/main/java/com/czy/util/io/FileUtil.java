package com.czy.util.io;

import com.czy.util.model.StringMap;
import com.czy.util.text.Line;
import com.czy.util.text.StringUtil;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.Yaml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author chenzy
 * @date 2020-08-05
 * NIO操作,也包括部分传统io操作
 */
public class FileUtil {
    private FileUtil() {
    }

    /**
     * 获取资源文件
     * 没有则创建
     *
     * @param fileName
     * @return
     */
    public static File getResourceFile(String moduleDir, String fileName) {
        return getResourceFile(moduleDir, fileName, true);
    }

    /**
     * 获取资源文件
     */
    public static File getResourceFile(String fileName) {
        return getResourceFile(null, fileName, false);
    }

    public static File getResourceFile(String moduleDir, String fileName, boolean isCreateFiel) {
//        FileUtil.class.getClassLoader().getResource("doc/sql.sql")
        //当前模块，直接返回url
        if (StringUtil.isBlank(moduleDir)) {
            URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
            if (url != null) {
                /*资源文件存在时，直接返回文件*/
                return new File(url.getPath());
            }
        }
        /*非当前模块，资源文件不存在时，也会返回file对象*/
        String projectPath = null;
        try {
            projectPath = new File(StringUtil.isBlank(moduleDir) ? "" : moduleDir).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        var file = new File(projectPath + "/src/main/resource/" + fileName);
        if (isCreateFiel) {
            FileUtil.createFile(file);
        }
        return file;
    }

    /**
     * 一行行读取文件
     *
     * @param file
     * @return
     */
    public static List<String> readByLine(File file) {
        if (file == null) {
            return null;
        }
        var result = new ArrayList<String>();
        try (var reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*指定编码格式读取文件*/
    public static String read(File file, Charset charset) {
        if (file == null || file.exists() || !file.isFile()) {
            return "";
        }
        try (var fileChannel = new FileInputStream(file).getChannel()) {
            var buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            buffer.flip();
            var bytes = new byte[buffer.limit()];
            buffer.get(bytes);
            if (charset == null) {
                return new String(bytes);
            }
            return new String(bytes, charset);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> StringMap<T> readProperty(String filePath) {
        try {
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream(FileUtil.getResourceFile(filePath));
            prop.load(in);
            StringMap<T> proMap = new StringMap();
            for (String key : prop.stringPropertyNames()) {
                proMap.put(key, (T) prop.getProperty(key));
            }
            in.close();
            return proMap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Optional<List<StringMap>> readXML(File file) {
        if (file == null || file.exists()) {
            return Optional.empty();
        }
        try {
            var factory = DocumentBuilderFactory.newInstance();
            var document = factory.newDocumentBuilder().parse(file);
            var nodeList = document.getChildNodes();
            if (nodeList == null || nodeList.getLength() < 1) {
                return Optional.empty();
            }
            return Optional.of(nodeList2MapList(nodeList));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (SAXException e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /*此方法还可以优化*/
    private static ArrayList<StringMap> nodeList2MapList(NodeList nodeList) {
        var list = new ArrayList<StringMap>(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            var node = nodeList.item(i);
            var nodeValue = node.getNodeValue();
            if (node.hasChildNodes()) {
                var childNodeList = node.getChildNodes();
                if (childNodeList.getLength() == 1) {
                    var temp = childNodeList.item(0);
                    /*文本作为一个#text节点，没有必要把#text作为map的key返回*/
                    if (temp.getNodeName().equals("#text") && StringUtil.isNotBlank(nodeValue)) {
                        list.add(new StringMap(1, node.getNodeName(), temp.getNodeValue()));
                    }
                } else {
                    /*递归*/
                    list.add(new StringMap(1, node.getNodeName(), nodeList2MapList(node.getChildNodes())));
                }
                continue;
            }
            if (StringUtil.isNotBlank(nodeValue)) {
                list.add(new StringMap(1, node.getNodeName(), nodeValue));
            }
        }
        return list;
    }

    public static <T> Optional<StringMap<T>> readYML(File file) {
        if (file == null) {
            return Optional.empty();
        }
        if (!file.exists()) {
            //文件未找到
            return Optional.empty();
        }
        try (InputStream in = new FileInputStream(file)) {
            StringMap<T> proMap = new Yaml().loadAs(in, StringMap.class);
            return Optional.ofNullable(proMap);
        } catch (IOException e) {
            //加载配置文件失败 file.getPath()
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static <T> Optional<StringMap<T>> readYML(File file, String... keys) {
        Optional<StringMap<T>> map = readYML(file);
        if (keys == null || keys.length == 0||map.isEmpty()) {
            return map;
        }
        StringMap<T> result = new StringMap<>(keys.length);
        for (String key : keys) {
            result.add(key, map.get().get(key));
        }
        return Optional.of(result);
    }

    /**
     * 读取文件
     *
     * @param file
     * @return
     */
    public static String read(File file) {
        return read(file, null);
    }

    /*追加写*/
    public static void append(File file, String... contents) {
        write(file, true, false, contents);
    }

    /*追加写:换行*/
    public static void appendLine(File file, String... contents) {
        write(file, true, true, contents);
    }

    /**
     * 文件中写入指定内容:覆盖写
     */
    public static void write(File file, String... contents) {
        write(file, false, false, contents);
    }

    public static void write(File file, boolean append, boolean appendLine, String... contents) {
        if (file == null || contents == null || contents.length < 1) {
            return;
        }
        //文件不存在时创建，存在则不做任何操作
        createFile(file);
        try (var fileChannel = new FileOutputStream(file, append).getChannel()) {
            fileChannel.write(ByteBuffer.wrap(String.join(appendLine ? Line.separator : "", contents).getBytes()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件不存在时创建，存在则不做任何操作
     * @param file
     */
    public static Boolean createFile(File file) {
        if (file.exists()) {
            return false;
        }
        File pathFile = file.getParentFile();
        /*创建目录*/
        if (pathFile != null && !pathFile.exists()) {
            pathFile.mkdirs();
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * from文件数据拷贝到to文件
     * 此方法只支持复制utf-8格式编码文件
     *
     * @param from
     * @param to
     */
    public static void copy(File from, File to) {
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(from)));
             var writer = new PrintWriter(to)) {
            reader.transferTo(writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    xml属性写入yml文件，用不上且有优化空间，先注释掉，后面要用再改。
    public static void writeConfigFileByXML2YML(String xmlFilePath, String ymlFilePath) {
        List<StringMap> dataList = FileUtil.readXML(FileUtil.getResourceFile(xmlFilePath)).get();
        File ymlFile = FileUtil.getResourceFile(ymlFilePath);
        FileUtil.createFile(ymlFile);
        try {
            org.ho.yaml.Yaml.dump(dataList, ymlFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/
    /**
     * 批量重命名
     * 待完善
     * @param filePath
     * @param newName
     */
    public static void reNameJavaList(String filePath, String newName) {
        String oldName = null;
        try {
            File[] childFiles = new File(filePath).listFiles();
            if (childFiles != null) {
                for (File childFile : childFiles) {
                    if (childFile.isDirectory()) {
                        reNameJavaList(childFile.getPath(), newName);
                    } else {
                        oldName = childFile.getName();
                        String newFileName = newName.replace("oldName"
                                , oldName.substring(0, oldName.indexOf(".java"))) + ".java";
                        childFile.renameTo(new File(childFile.getParentFile() + "/" + newFileName));
                    }
                }
            }
        } catch (Exception e) {
            //重命名错误 oldName
            e.printStackTrace();
        }
    }
    /**
     * 根据绝对路径(可能是文件夹，可能是文件)，得到当前路径下所有指定后缀文件
     *
     * @param filePath
     * @return
     */
    public static List<File> getFileList(String filePath, String suffix) {
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        List<File> files = new ArrayList();
        if (childFiles != null) {
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    files.addAll(getFileList(childFile.getPath(), suffix));
                } else {
                    String childFilePath = childFile.getPath();
                    String fileSuffix = childFilePath.substring(childFilePath.lastIndexOf(".") + 1);
                    if (suffix == null || fileSuffix.equals(suffix)) {
                        files.add(childFile);
                    }
                }
            }
        }
        return files;
    }

    /*获取class所在目录*/
    public static File getClassFile(String moduleDir, String filePath) {
        Thread.currentThread().getContextClassLoader().getResource("com/czy/frame/cash/service");
        if (StringUtil.isBlank(moduleDir) && StringUtil.isBlank(filePath)) {
            return null;
        }
        String path = StringUtil.isBlank(moduleDir) ? "" : moduleDir;
        if (!path.endsWith(File.separator) && path.length() > 0) {
            path += File.separator;
        }
        if (filePath.startsWith(File.separator)) {
            path += filePath.substring(1);
        } else {
            path += filePath;
        }
        path = path.replace(".", File.separator);
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url != null) {
            return new File(url.getPath());
        } else {
            return null;
        }
    }

    /**
     * @param moduleDir
     * @param groupId
     * @return
     */
    public static List<Class> getClassList(String moduleDir, String groupId) {
        List<Class> classList = new ArrayList();
        String filePath = null;
        String modulePath = getPath(moduleDir) + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        try {
            File[] childFiles = getCodeFile(moduleDir, groupId).listFiles();
            if (childFiles != null) {
                for (File childFile : childFiles) {
                    filePath = childFile.getPath();
                    filePath = filePath.substring(filePath.indexOf(modulePath) + modulePath.length());
                    if (childFile.isDirectory()) {
                        classList.addAll(getClassList(moduleDir, filePath));
                    } else {
                        if (filePath.endsWith(".java")) {
                            var className = filePath.substring(0, filePath.lastIndexOf(".java")).replace(File.separator, ".");
                            classList.add(Class.forName(className));
                        }
                    }
                }
            }
        } catch (Throwable e) {
            //加载类 filePath
            e.printStackTrace();
        }
        return classList;
    }

    /**
     * 获取原代码文件或者目录
     *
     * @param moduleDir module目录，可以不传此参数
     * @param beanName
     * @return
     */
    public static File getCodeFile(String moduleDir, String beanName) {
        String projectPath = null;
        try {
            projectPath = new File(StringUtil.isBlank(moduleDir) ? "" : moduleDir).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = projectPath + "/src/main/java" + File.separator + getPath(beanName);
        return new File(path);
    }

    /*返回相对目录，以File.separator分割，参数1可能存在以File.separator或者.做目录分割*/
    private static String getPath(String path) {
        String result = null;
        if (StringUtil.isNotBlank(path)) {
            result = path;
            var isFile = false;
            if (result.endsWith(".java")) {
                result = result.substring(0, result.lastIndexOf(".java"));
                result = result.replace(".", File.separator);
                result += ".java";
                isFile = true;
            } else if (result.endsWith(".fxml")) {
                result = result.substring(0, result.lastIndexOf(".fxml"));
                result = result.replace(".", File.separator);
                result += ".fxml";
                isFile = true;
            } else {
                result = result.replace(".", File.separator);
            }
            if (!isFile && !result.endsWith(File.separator)) {
                result += File.separator;
            }
            return result;
        } else {
            return "";
        }
    }
    /**
     * 返回当前所在盘符，两个字符。例 A:
     * @return
     */
    public static String getRoot() {
        var systemName=System.getProperty("os.name");
        if (systemName.contains("Linux")){
            /*linux系统根目录是/*/
            return File.separator;
        }
        try {
            /*除linux系统外都按照widow系统的根目录，以盘符开始*/
            return new File("").getCanonicalPath().substring(0,3);
        } catch (IOException e) {
            e.printStackTrace();
            return "C:"+File.separator;//默认
        }
    }
}
