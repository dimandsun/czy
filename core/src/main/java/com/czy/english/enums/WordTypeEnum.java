package com.czy.english.enums;


import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @since 2020-04-28
 * @description 单词类型：动词、名词、形容词、副词
 */
public enum WordTypeEnum implements IEnum<Integer> {
    Verb(1,"动词"),Noun(2,"名词"),Adjective(3,"形容词"),Adverb(4,"副词");

    WordTypeEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    private Integer id;
    private String msg;

    @Override
    public Integer getValue() {
        return this.id;
    }
}
