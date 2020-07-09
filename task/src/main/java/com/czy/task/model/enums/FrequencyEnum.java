package com.czy.task.model.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-07-09
 * 任务循环频率:1每日/2每周/3每月/4每年/5工作日/6节假日/7非循环单次任务/8非循环任意任务/9其他
 */
public enum FrequencyEnum implements IEnum<Integer> {
    Daily(1,"每日"),Weekly(2,"每周"),Monthly(3,"每月"),Annually(4,"每年")
    ,WorkingDay(5,"工作日"),Holiday(6,"节假日"),Single(7,"非循环单次任务")
    ,Arbitrarily(8,"非循环任意任务"),Other(9,"其他");

    FrequencyEnum(Integer id, String msg) {
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
