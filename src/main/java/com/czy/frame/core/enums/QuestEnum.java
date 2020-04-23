package com.czy.frame.core.enums;

/**
 * @author chenzy
 * @since 2020-04-01
 * @description 请求类型 Put 更新 Get 查询 Post 新增 Delete 删除
 */
public enum QuestEnum implements IEnum<Integer>{
    Post(1,"post"),Get(2,"get"),Delete(3,"delete"),Put(4,"put"),Default(5,"post"),All(6,"all");

    private Integer id;
    private String msg;

    QuestEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }
    @Override
    public Integer getValue() {
        return id;
    }

    public String getMsg() {
        return msg;
    }
}
