package com.czy.util.sqltool.model;

import java.util.List;

/**
 * @author chenzy
 * @since 2020-05-07
 *  解析建表语句得到信息，然后把信息封装成model
 */
public class TableSqlInfo {
    private String tableName;
    private String tableDes;
    private List<ColumnSqlInfo> columnSqlInfoList;

    public TableSqlInfo() {
    }

    public TableSqlInfo(String tableName, String tableDes) {
        this.tableName = tableName;
        this.tableDes = tableDes;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDes() {
        return tableDes;
    }

    public void setTableDes(String tableDes) {
        this.tableDes = tableDes;
    }

    public List<ColumnSqlInfo> getColumnSqlInfoList() {
        return columnSqlInfoList;
    }

    public void setColumnSqlInfoList(List<ColumnSqlInfo> columnSqlInfoList) {
        this.columnSqlInfoList = columnSqlInfoList;
    }
}
