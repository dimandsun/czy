package com.czy.question.model.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-06-22
 * 选项类型-英文字母、阿拉伯数字、判断对错、文本、其他填写
 * 选项类型-英文字母、阿拉伯数字、判断对错、文本、其他
 */
public enum OptionTypeEnum implements IEnum<Integer> {
    letter(1,"英文字母")
    ,integer(2,"阿拉伯数字")
    ,judge(3,"判断对错")
    ,textFill(2,"文本")
    ,other(3,"其他")
    ,;

    private Integer id;
    private String msg;
    OptionTypeEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    @Override
    public Integer getValue() {
        return id;
    }
}
