package com.czy.sqltool.enums;

import com.czy.util.StringUtil;
import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @since 2020-05-07
 * @description
 */
public enum ColumnTypeEnum implements IEnum<String> {
    String("String","(char\\((([0-9]*)|(max))\\))|(varchar\\((([0-9]*)|(max))\\))|(nvarchar\\((([0-9]*)|(max))\\))|(text)|(enum)")
    /*Boolean和Integer不能调整顺序，不然无法将tinyint(1)转成Boolean*/
    ,Boolean("Boolean","tinyint\\(1\\)")
    ,Integer("Integer","(int(\\([0-9]*\\)|()))|(tinyint(\\([0-9]*\\)|()))")
    ,Long("Long","bigint\\([0-9]*\\)")
    ,Date("Date","(date)|(time)|(datetime)")
    ,Float("Float","float")
    ,Double("Double","double")
    ,BigDecimal("BigDecimal","decimal")
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
        ColumnTypeEnum columnTypeEnum =getEnum("tinyint(1)");
        System.out.println(columnTypeEnum);
    }
}
