package com.czy.question.server.model.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-06-22
 * 题型-填空、判断、单选、多选、不定项选择、阅读理解、完型填空、英译汉、汉译英、名词解释、简答、article
 */
public enum QuestionTypeEnum implements IEnum<Byte> {
    fillBank((byte) 1,"")
    ,judge((byte)2,"判断")
    ,singleChoice((byte)3,"单选")
    ,multipleChoice((byte)4,"多选")
    ,indefiniteChoice((byte)5,"不定项选择")
    ,readingComprehension((byte)6,"阅读理解")
    ,cloze((byte)7,"完型填空")
    ,E2C((byte)8,"英译汉")
    ,C2E((byte)9,"汉译英")
    ,Identification((byte)9,"名词解释")
    ,shortAnswer((byte)10,"简答")
    ,article((byte)11,"作文")
    ,;
    private byte id;
    private String msg;

    QuestionTypeEnum(byte id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    @Override
    public Byte getValue() {
        return id;
    }
}
