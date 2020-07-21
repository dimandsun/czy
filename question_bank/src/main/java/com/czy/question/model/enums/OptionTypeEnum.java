package com.czy.question.model.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-06-22
 * 选项类型-英文字母、阿拉伯数字、判断对错、文本、其他填写
 */
public enum OptionTypeEnum implements IEnum<Byte> {
    letter((byte)1,"英文字母")
    ,integer((byte)2,"阿拉伯数字")
    ,judge((byte)3,"判断对错")
    ,textFill((byte)2,"文本")
    ,other((byte)3,"其他")
    ,;

    private byte id;
    private String msg;
    OptionTypeEnum(byte id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    @Override
    public Byte getValue() {
        return id;
    }
}
