package com.czy.jdbc.sql.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public enum ReturnTypeEnum implements IEnum<String> {
    AffectedLines,//受影响行
    PrimaryKey,//返回主键，主要用于insert
    Cell,//返回单个值
    RecordOne,//一条记录
    RecordList,//记录集合
    ;

    @Override
    public String getValue() {
        return this.name();
    }
}
