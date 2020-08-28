package com.czy.util.sqltool;

import com.czy.util.io.FileUtil;
import com.czy.util.text.Line;
import com.czy.util.time.DateUtil;
import com.czy.util.list.ListUtil;
import com.czy.util.text.StringUtil;
import com.czy.util.sqltool.enums.ColumnTypeEnum;
import com.czy.util.sqltool.model.ColumnSqlInfo;
import com.czy.util.sqltool.model.TableSqlInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenzy
 * 
 * @since 2020-05-07
 */
public class SQLUtil {
    /**
     * 根据sql建表语句生成javaBean，目前建表语句只匹配mysql建表语句。
     * 例：
     * create table doc_mode(
     * id int primary key auto_increment
     * ,name nvarchar(200) comment '文档模块名称'
     * ,parent_id int comment '父模块'
     * ,level int comment '级别'
     * ,server_uri nvarchar(200) comment '服务器地址.一级分类才有值，ip:端口'
     * )COMMENT='文档模块';
     * 此sql将生成类DocMode
     *
     * @param beanPackage
     * @param sqlPath
     * @param author
     */
    public static void generateBeanFile(String beanPackage, String sqlPath, String author) {
//        beanPackage = "com.czy.a.model";
//        sqlPath = "doc/sql.sql";
//        author = "chenzy";
        String date = DateUtil.data2Str(new Date(), DateUtil.yyyy_MM_dd);
        /*创建bean目录*/
        String modelPath ="src/main/java/"+beanPackage.replace(".",File.separator);
        File modelDir = FileUtil.getCodeFile(null,beanPackage);
        if (!modelDir.exists()) {
            modelDir.mkdirs();
        }
        /*获取sql文本*/
        String sqlContents = FileUtil.read(FileUtil.getResourceFile(sqlPath));
        if (!sqlContents.contains(";")) {
            return;
        }
        String[] sqlContentA = StringUtil.eraseSpace(sqlContents.toLowerCase()).split(";");
        String sqlStart = "create table ";
        String tableDesStart = ")comment=";
        /*循环解析每个建表语句*/
        List<TableSqlInfo> tableSqlInfoList = new ArrayList<>(sqlContentA.length);
        for (String sqlContent : sqlContentA) {
            String tableName = sqlContent.substring(sqlStart.length(), sqlContent.indexOf("(")).strip();
            if (tableName.isBlank()) {
                continue;
            }
            if (tableName.startsWith("[")&&tableName.endsWith("]")){
               tableName = tableName.substring(1,tableName.length()-1);
            }
            sqlContent = sqlContent.substring(sqlContent.indexOf("(") + 1).strip();
            int tableDesIndex = sqlContent.indexOf(tableDesStart);
            String tableDes = null;
            if (tableDesIndex != -1) {
                tableDes = sqlContent.substring(tableDesIndex + tableDesStart.length()).replace("'", "").strip();
                sqlContent = sqlContent.substring(0, tableDesIndex);
            }
            TableSqlInfo tableSqlInfo = new TableSqlInfo(tableName, tableDes);

            /*循环解析每个字段：以逗号分割，要对类似decimal(10,4)类型作处理*/
            sqlContent= sqlContent.replaceAll("(decimal\\(([0-9]*),([0-9]*)\\))","decimal");
            String[] columnContentA = sqlContent.split(",");
            if (ListUtil.isEmpty(columnContentA)) {
                continue;
            }
            List<ColumnSqlInfo> columnSqlInfoList = new ArrayList<>(columnContentA.length);
            for (String columnContent : columnContentA) {
                String[] columnInfoA = columnContent.split(" ");
                if (ListUtil.isEmpty(columnInfoA)) {
                    continue;
                }
                if (columnInfoA.length < 2) {
                    continue;
                }
                String columnName = columnInfoA[0].strip();
                String columnType = columnInfoA[1].strip();
                String columnDes = null;
                if (columnInfoA.length >= 4 && columnInfoA[3].contains("'")) {
                    columnDes = columnInfoA[3].replace("'", "").strip();
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

            /*写文件头信息：包名，导入类，注释信息，类注解，类名*/
            String packageContent = "package " + beanPackage + ";\n";
            String importContent = "import com.czy.core.annotation.db.Table;\n";
            String classDesContent = "/**\n * @author " + author + "\n * @date " + date + "\n *  " + (tableDes == null ? "" : tableDes) + "\n */\n";
            String clssHeadContent = "@Table(\"" + tableName + "\")\npublic class " + className + " {\n";

                /*写字段：字段注释，字段类型，字段名
                  写方法: set方法，get方法
                */
            if (ListUtil.isEmpty(columnSqlInfoList)) {
                continue;
            }
            String columnContent = "", columnMethodContent = "";
            for (ColumnSqlInfo columnSqlInfo : columnSqlInfoList) {
                String columnDes = columnSqlInfo.getColumnDes();
                String columnName = StringUtil.lineToHump(columnSqlInfo.getColumnName());
                ColumnTypeEnum columnType = columnSqlInfo.getColumnType();
                if (columnType.equals(ColumnTypeEnum.Date) && !importContent.contains("import java.util.Date;")) {
                    importContent += "import java.util.Date;\n";
                }else if (columnType.equals(ColumnTypeEnum.BigDecimal) && !importContent.contains("import java.math.BigDecimal;")) {
                    importContent += "import java.math.BigDecimal;\n";
                }
                //字段加注解，
                var jsonColumn = columnName.equals(columnSqlInfo.getColumnName())?"":"\t@JsonProperty(\""+columnSqlInfo.getColumnName()+"\")\n" ;
                if (jsonColumn.length()>0&&!importContent.contains("import com.fasterxml.jackson.annotation.JsonProperty;")){
                    importContent += "import com.fasterxml.jackson.annotation.JsonProperty;\n";
                }
                columnContent += "\t/*" + (columnDes == null ? "" : columnDes) + "*/\n"+jsonColumn
                        + "\tprivate " + columnType.getValue() + " " + columnName + ";\n";

                columnMethodContent += "\tpublic " + columnType.getValue() + " get" + StringUtil.upFirst(columnName) + "(){\n"
                        + "\t\treturn " + columnName + ";\n\t}\n"
                        + "\tpublic void set" + StringUtil.upFirst(columnName) + "(" + columnType.getValue() + " " + columnName + "){\n"
                        + "\t\t this." + columnName + "=" + columnName + ";\n\t}\n";
            }
            FileUtil.write(new File(fileName),
                    packageContent,importContent,classDesContent,clssHeadContent,columnContent, Line.separator
                            , columnMethodContent , "}");
        }
    }
}
