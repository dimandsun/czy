package com.czy.util.enums;

/**
 * @author chenzy
 * @since 2020-04-01
 *  请求类型 Put 更新 Get 查询 Post 新增 Delete 删除
 */
public enum QuestMethodEnum implements IEnum<String> {
    POST, GET, DELETE, PUT, DEFAULT, ALL;

    @Override
    public String getValue() {
        return name();
    }
    public String toString() {
        return getValue();
    }
}
