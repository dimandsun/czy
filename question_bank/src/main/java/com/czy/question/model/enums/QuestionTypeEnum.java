package com.czy.question.model.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-06-22
 * 题型-填空、判断、单选、多选、不定项选择、阅读理解、完型填空、英译汉、汉译英、名词解释、简答、article
 */
public enum QuestionTypeEnum implements IEnum<Integer> {
    fillBank(1,"填空")
    ,judge(2,"判断")
    ,singleChoice(3,"单选")
    ,multipleChoice(4,"多选")
    ,indefiniteChoice(5,"不定项选择")
    ,Identification(9,"名词解释")
    ,shortAnswer(10,"简答")
    ,readingComprehension(6,"阅读理解")
    ,cloze(7,"完型填空")
    ,E2C(8,"英译汉")
    ,C2E(9,"汉译英")
    ,article(11,"作文")
    ,;
    private Integer id;
    private String msg;

    QuestionTypeEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    @Override
    public Integer getValue() {
        return id;
    }
}
