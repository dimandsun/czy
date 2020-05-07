package com.czy.sqltool;

import com.czy.sqltool.enums.ColumnTypeEnum;
import com.czy.sqltool.model.ColumnSqlInfo;
import com.czy.sqltool.model.TableSqlInfo;
import com.czy.util.DateUtil;
import com.czy.util.FileUtil;
import com.czy.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenzy
 * @description
 * @since 2020-05-07
 */
public class SQLUtil {
    /**
     * 根据sql建表语句生成javaBean，目前建表语句只匹配mysql建表语句。
     * 例：
     *   create table doc_mode(
     *      id int primary key auto_increment
     *      ,name nvarchar(200) comment '文档模块名称'
     *      ,parent_id int comment '父模块'
     *      ,level int comment '级别'
     *      ,server_uri nvarchar(200) comment '服务器地址.一级分类才有值，ip:端口'
     *   )COMMENT='文档模块';
     *   此sql将生成类DocMode
     * @param beanPackage
     * @param sqlPath
     * @param author
     */
   public static void generateBeanFile(String beanPackage, String sqlPath, String author) {
        beanPackage = "com.czy.a.model";
        sqlPath = "doc/sql.sql";
        author = "chenzy";
        String date = DateUtil.data2Str(new Date(), DateUtil.yyyy_MM_dd);
        /*创建bean目录*/
        String modelPath = null;
        try {
            String projectPath = new File("").getCanonicalPath();
            modelPath = projectPath + "/src/main/java" + File.separator + beanPackage.replace(".", File.separator);
            File modelDir = new File(modelPath);
            if (!modelDir.exists()) {
                modelDir.mkdirs();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*获取sql文本*/
        String sqlContents = FileUtil.readFile(FileUtil.getFile(sqlPath));
        if (!sqlContents.contains(";")) {
            return;
        }
        String[] sqlContentA = StringUtil.trimSpace(sqlContents.toLowerCase()).split(";");
        String sqlStart = "create table ";
        String tableDesStart = ")comment=";
        /*循环解析每个建表语句*/
        List<TableSqlInfo> tableSqlInfoList = new ArrayList<>(sqlContentA.length);
        for (String sqlContent : sqlContentA) {
            String tableName = sqlContent.substring(sqlStart.length(), sqlContent.indexOf("(")).trim();
            if (tableName.isBlank()) {
                continue;
            }
            sqlContent = sqlContent.substring(sqlContent.indexOf("(") + 1).trim();
            int tableDesIndex = sqlContent.indexOf(tableDesStart);
            String tableDes = null;
            if (tableDesIndex != -1) {
                tableDes = sqlContent.substring(tableDesIndex + tableDesStart.length()).replace("'", "").trim();
                sqlContent = sqlContent.substring(0, tableDesIndex);
            }
            TableSqlInfo tableSqlInfo = new TableSqlInfo(tableName, tableDes);
            /*循环解析每个字段*/
            String[] columnContentA = sqlContent.split(",");
            if (StringUtil.isEmpty(columnContentA)) {
                continue;
            }
            List<ColumnSqlInfo> columnSqlInfoList = new ArrayList<>(columnContentA.length);
            for (String columnContent : columnContentA) {
                String[] columnInfoA = columnContent.split(" ");
                if (StringUtil.isEmpty(columnInfoA)) {
                    continue;
                }
                if (columnInfoA.length < 2) {
                    continue;
                }
                String columnName = columnInfoA[0].trim();
                String columnType = columnInfoA[1].trim();
                String columnDes = null;
                if (columnInfoA.length >= 4 && columnInfoA[3].contains("'")) {
                    columnDes = columnInfoA[3].replace("'", "").trim();
                }
                columnSqlInfoList.add(new ColumnSqlInfo(columnName, columnType, columnDes));
            }
            tableSqlInfo.setColumnSqlInfoList(columnSqlInfoList);
            tableSqlInfoList.add(tableSqlInfo);
        }
        /*循环新建javaBean*/
        for (TableSqlInfo tableSqlInfo : tableSqlInfoList) {
            String tableDes = tableSqlInfo.getTableDes();
            String tableName = tableSqlInfo.getTableName();
            List<ColumnSqlInfo> columnSqlInfoList = tableSqlInfo.getColumnSqlInfoList();
            String className = StringUtil.upFirst(StringUtil.lineToHump(tableName));
            String fileName = modelPath + File.separator + className + ".java";
            FileWriter writer = null;
            try {
                writer = new FileWriter(fileName);
                /*写文件头信息：包名，导入类，注释信息，类注解，类名*/
                String packageContent = "package " + beanPackage + ";\n";
                String importContent = "import com.czy.core.annotation.Table;\n";
                String classDesContent = "/**\n * @author " + author + "\n * @since " + date + "\n * @description " + (tableDes == null ? "" : tableDes) + "\n */\n";
                String clssHeadContent = "@Table(\"" + tableName + "\")\npublic class " + className + " {\n";

                /*写字段：字段注释，字段类型，字段名
                  写方法: set方法，get方法
                */
                if (StringUtil.isEmpty(columnSqlInfoList)) {
                    continue;
                }
                String columnContent = "", columnMethodContent = "";
                for (ColumnSqlInfo columnSqlInfo : columnSqlInfoList) {
                    String columnDes = columnSqlInfo.getColumnDes();
                    String columnName = StringUtil.lineToHump(columnSqlInfo.getColumnName());
                    ColumnTypeEnum columnType = columnSqlInfo.getColumnType();
                    if (columnType.equals(ColumnTypeEnum.Date) && !importContent.contains("import java.util.Date;")) {
                        importContent += "import java.util.Date;\n";
                    }
                    columnContent += "\t/*" + (columnDes == null ? "" : columnDes) + "*/\n"
                            + "\tprivate " + columnType.getValue() + " " + columnName + ";\n";

                    columnMethodContent += "\tpublic " + columnType.getValue() + " get" + StringUtil.upFirst(columnName) + "(){\n"
                            + "\t\treturn " + columnName + ";\n\t}\n"
                            + "\tpublic void set" + StringUtil.upFirst(columnName) + "(" + columnType.getValue() + " " + columnName + "){\n"
                            + "\t\t this." + columnName + "=" + columnName + ";\n\t}\n";
                }
                writer.write(packageContent);
                writer.write(importContent);
                writer.write(classDesContent);
                writer.write(clssHeadContent);
                writer.write(columnContent + "\n");
                writer.write(columnMethodContent);
                writer.write("}");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
