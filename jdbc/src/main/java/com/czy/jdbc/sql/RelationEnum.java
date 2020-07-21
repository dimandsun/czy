package com.czy.jdbc.sql;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-07-21
 * 小于 小于等于 等于 不等于 大于 大于等于 likeAll likeLeft likeRight
 */
public enum RelationEnum implements IEnum<String> {
    LT("<? ", "{value} "),
    LE("<=?","{value} "),
    Equal("=?","{value} "),
    NotEqual("<=?","{value} "),
    GT(">?","{value} "),
    GE(">=?","{value} "),
    Like(" like ? ","%{value}% "),
    LikeLeft(" like ?","%{value} "),
    LikeRight(" like ?","{value}% "),

    ;
    private String code;
    private String msg;

    RelationEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String getValue() {
        return code;
    }
}
