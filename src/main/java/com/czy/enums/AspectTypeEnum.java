package com.czy.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @description
 * @since 2020-04-03
 */
public enum AspectTypeEnum implements IEnum<Integer> {
    Before(1,"前置切面"),After(2,"后置切面"),Around(3,"环绕");

    AspectTypeEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    private Integer id;
    private String msg;
    @Override
    public Integer getValue() {
        return id;
    }
}
