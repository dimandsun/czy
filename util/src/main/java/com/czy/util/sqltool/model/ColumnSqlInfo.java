package com.czy.util.sqltool.model;

import com.czy.util.sqltool.enums.ColumnTypeEnum;

/**
 * @author chenzy
 * @since 2020-05-07
 *  解析建表语句得到信息，然后把信息封装成model.
 */
public class ColumnSqlInfo {
    /**/
    private String columnName;
    private ColumnTypeEnum columnType;
    private String columnDes;

    public ColumnSqlInfo(String columnName, String columnType, String columnDes) {
        this.columnName=columnName;
        this.columnDes=columnDes;
        this.columnType=ColumnTypeEnum.getEnum(columnType);
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public ColumnTypeEnum getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnTypeEnum columnType) {
        this.columnType = columnType;
    }

    public String getColumnDes() {
        return columnDes;
    }

    public void setColumnDes(String columnDes) {
        this.columnDes = columnDes;
    }
}
