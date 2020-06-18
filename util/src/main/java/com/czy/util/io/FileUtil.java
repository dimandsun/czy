package com.czy.util.io;


import com.czy.util.ListUtil;
import com.czy.util.StringUtil;
import com.czy.util.model.MyMap;
import com.czy.util.model.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.Yaml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by com.czy on 2019/5/15.
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static FileUtil fileUtil = new FileUtil();

    public static FileUtil getInstance() {
        return fileUtil;
    }

    private FileUtil() {
    }

    /**
     * 一行行读取文件
     * @param file
     * @return
     */
    public static List<String> readFileLine(File file) {
        if (file == null) {
            return null;
        }
        var result = new ArrayList<String>();
        try (var reader = new BufferedReader(new FileReader(file))){
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

    public static String readFile(File file) {
        if (file == null) {
            return null;
        }
        BufferedReader reader = null;
        var sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    private static void copy(File file) {
        String url2 = "C:\\Users\\Administrator\\Desktop\\新建文件夹2\\";
        // 获取源文件夹当前下的文件或目录
        File[] files = (file).listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                // 复制文件
                System.out.println("复制:" + files[i].getName());
                copyFile(files[i], new File(url2 + files[i].getName()));
            } else {
                copy(files[i]);
            }
        }
    }

    /**
     * 复制文件
     *
     * @param sourceFile
     * @param targetFile
     */
    public static void copyFile(File sourceFile, File targetFile) {
        // 新建文件输入流并对它进行缓冲
        try {
            FileInputStream input = new FileInputStream(sourceFile);
            BufferedInputStream inBuff = new BufferedInputStream(input);
            // 新建文件输出流并对它进行缓冲
            FileOutputStream output = new FileOutputStream(targetFile);
            BufferedOutputStream outBuff = new BufferedOutputStream(output);
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
            //关闭流
            inBuff.close();
            outBuff.close();
            output.close();
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用文件通道的方式来进行文件复制
     *
     * @param sourceFile 原文件
     * @param targetFile 新文件
     */
    public static void fileChannelCopy(File sourceFile, File targetFile) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(sourceFile);
            fo = new FileOutputStream(targetFile);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    /**
     * @param moduleDir
     * @param groupId
     * @return
     */
    public static List<Class> getClassList(String moduleDir, String groupId) {
        List<Class> classList = new ArrayList();
        String filePath = null;
        String modulePath=getPath(moduleDir)+"src"+File.separator +"main"+File.separator+"java"+File.separator;
        try {
            File[] childFiles = getCodeFile(moduleDir, groupId).listFiles();
            if (childFiles != null) {
                for (File childFile : childFiles) {
                    filePath = childFile.getPath();
                    filePath=filePath.substring(filePath.indexOf(modulePath) + modulePath.length());
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
        } catch (Exception e) {
            logger.error("加载类{}错误", filePath);
        }
        return classList;
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
        }else {
            return "";
        }
    }

    /**
     * 批量重命名
     *
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
//                        newFileName=newFileName.substring(0,newFileName.indexOf(".java"))+".java";
                        childFile.renameTo(new File(childFile.getParentFile() + "/" + newFileName));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("重命名{}错误", oldName);
        }
    }
    /**
     * 获取资源文件
     * 没有则创建
     * @param fileName
     * @return
     */
    public static File getResourceFile(String fileName,boolean isCreateFiel) {
        return getResourceFile(null, fileName,isCreateFiel);
    }
    /**
     * 获取资源文件
     *
     * @param fileName
     * @return
     */
    public static File getResourceFile(String fileName) {
        return getResourceFile(null, fileName,false);
    }

    public static File getResourceFile(String moduleDir, String fileName,boolean isCreateFiel) {
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
        var file= new File(projectPath + "/src/main/resource/" + fileName);
        if (isCreateFiel){
            createFile(file);
        }
        return file;
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
        String path = projectPath + "/src/main/java" + File.separator +  getPath(beanName);
        return new File(path);
    }

    public static void writeConfigFileByXML2YML(String xmlFilePath, String ymlFilePath) {
        List<MyMap> dataList = readConfigFileByXML(xmlFilePath);
        File ymlFile = FileUtil.getResourceFile(ymlFilePath);
        createFile(ymlFile);
        try {
            org.ho.yaml.Yaml.dump(dataList, ymlFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件不存在时创建，存在则不做任何操作
     *
     * @param file
     */
    public static void createFile(File file) {
        if (!file.exists()) {
            /*如果参数是目录，直接创建目录。这里用file.isDirectory()无法区分file是否是目录，需要根据文件名是否有后缀来判断*/
            if (file.getName().indexOf(".") == -1) {
                file.mkdirs();
                return;
            }

            /*若参数是文件，要先判断文件所在目录是否存在，若不存在，则需要创建*/
            File pathFile = file.getParentFile();
            if (pathFile!=null&&!pathFile.exists()) {
                pathFile.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file.getPath();
    }

    /*追加写*/
    public static void append(File file, String... contents) {
        write(file,true,false,contents);
    }
    /*追加写:换行*/
    public static void appendLine(File file, String... contents) {
        write(file,true,true,contents);
    }
    /**
     * 文件中写入指定内容
     *
     * @param file
     * @param contents
     */
    public static void write(File file, String... contents) {
        write(file,false,false,contents);
    }
    /**
     *  文件中写入指定内容
     * @param file
     * @param append 是否追加写。否会覆盖原内容。值为null则默认覆盖
     * @param appendLine 是否换行。值为null则默认不换行
     * @param contents
     */
    public static void write(File file,Boolean append,Boolean appendLine, String... contents) {
        if (file == null || ListUtil.isEmpty(contents)) {
            return;
        }
        if(append==null){
            append=false;
        }
        if (appendLine==null){
            appendLine=false;
        }
        createFile(file);
        try(var writer = new FileWriter(file,append)) {
            for (String content : contents) {
                if (StringUtil.isBlank(content)){
                    continue;
                }
                writer.write(content);
                if (appendLine){
                    writer.write(File.separator);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static List<MyMap> readConfigFileByXML(String filePath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(FileUtil.getResourceFile(filePath));
            NodeList nodeList = document.getChildNodes();
            List<MyMap> list = nodeList2MapList(nodeList);
            return list;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            logger.error("加载配置文件{}失败。", filePath);
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            logger.error("加载配置文件{}失败。", filePath);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("加载配置文件{}失败。", filePath);
            return null;
        }

    }

    private static List<MyMap> nodeList2MapList(NodeList nodeList) {
        List<MyMap> list = new ArrayList<>(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String nodeValue = node.getNodeValue();
            if (!node.hasChildNodes()) {
                if (StringUtil.isBlank(nodeValue)) {
                    /*不读取空文本*/
                    continue;
                } else {
                    list.add(new MyMap(1, node.getNodeName(), nodeValue));
                }
            } else {
                NodeList childNodeList = node.getChildNodes();
                /*文本作为一个#text节点，没有必要把#text作为map的key返回*/
                if (childNodeList.getLength() == 1 && childNodeList.item(0).getNodeName().equals("#text")) {
                    list.add(new MyMap(1, node.getNodeName(), childNodeList.item(0).getNodeValue()));
                } else {
                    list.add(new MyMap(1, node.getNodeName(), nodeList2MapList(node.getChildNodes())));
                }
            }
        }
        return list;
    }

    public static <T> StringMap<T> readConfigFileByProperty(String filePath) {
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
            logger.error("加载配置文件{}失败。", filePath);
            return null;
        }
    }

    public static <T> StringMap<T> readConfigFileByYML(File file) {
        if (file == null) {
            return null;
        }
        if (!file.exists()) {
            logger.error("文件{}未找到", file.getPath());
            return null;
        }
        try (InputStream in = new FileInputStream(file)) {
            StringMap<T> proMap = new Yaml().loadAs(in, StringMap.class);
            return proMap;
        } catch (IOException e) {
            logger.error("加载配置文件{}失败。", file.getPath());
            return null;
        }
    }

    public static void main(String[] args) {
//        createFile(getResourceFile("a/b.txt"));
        getClassFile("core", "com.czy");
    }



}
