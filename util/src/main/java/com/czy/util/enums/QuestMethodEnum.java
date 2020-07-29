package com.czy.util.enums;

/**
 * @author chenzy
 * @since 2020-04-01
 *  请求类型 Put 更新 Get 查询 Post 新增 Delete 删除
 */
public enum QuestMethodEnum implements IEnum<Integer> {
    Post(1,"post"),Get(2,"get"),Delete(3,"delete"),Put(4,"put"),Default(5,"post"),All(6,"all");

    private Integer id;
    private String msg;

    QuestMethodEnum(Integer id, String msg) {
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
