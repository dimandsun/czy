package com.czy.util.sqltool.enums;

import com.czy.util.text.StringUtil;
import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @since 2020-05-07
 *
 */
public enum ColumnTypeEnum implements IEnum<String> {
    String("String","(char\\((([0-9]*)|(max))\\))|(varchar\\((([0-9]*)|(max))\\))|(nvarchar\\((([0-9]*)|(max))\\))|(text)|(enum)")
    /*Boolean和Byte不能调整顺序，不然无法将tinyint(1)转成Boolean*/
    ,Boolean("Boolean","tinyint\\(1\\)")
    ,Byte("Byte","tinyint(\\([0-9]*\\)|())")
    ,Integer("Integer","(int(\\([0-9]*\\)|()))|(tinyint(\\([0-9]*\\)|()))")
    ,Long("Long","bigint\\([0-9]*\\)")
    ,Date("Date","(date)|(time)|(datetime)")
    ,Float("Float","float")
    ,Double("Double","double")
    ,BigDecimal("BigDecimal","(decimal)|(decimal\\(([0-9]*),([0-9]*)\\))|(decimal\\(([0-9]*)\\))")
    ,Object("Object","other")
    ;
    private String sqlType;//正则表达式
    private String javaType;

    ColumnTypeEnum(String javaType,String sqlType) {
        this.javaType=javaType;
        this.sqlType = sqlType;
    }
    @Override
    public String getValue() {
        return javaType;
    }

    public static ColumnTypeEnum getEnum(String sqlType){
        for (ColumnTypeEnum typeEnum:ColumnTypeEnum.values()){
            if (StringUtil.matcher(sqlType,typeEnum.sqlType)){
                return typeEnum;
            }
        }
        return Object;
    }
    public static void main(String[] args) {
//        ColumnTypeEnum columnTypeEnum =getEnum("decimal(10,4)");
//        System.out.println(columnTypeEnum);
        String sqlContent="decimal(10,4)";
        sqlContent=sqlContent.replaceAll("(\\(([0-9]*),([0-9]*)\\))","\\(([0-9]*)-([0-9]*)\\)");
        System.out.println(sqlContent);
        {
            var a= getEnum("tinyint");
            System.out.println(a);
        }
    }
}
