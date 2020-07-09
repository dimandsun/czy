package com.czy.task.model.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-07-09
 * 任务类型：1 紧急重要/2紧急不重要/3不紧急重要/4不紧急不重要
 */
public enum TaskTypeEnum implements IEnum<Integer> {
    UrgentImportant(1,"紧急重要"),UrgentUnimportant(2,"紧急不重要"),ImportantUnurgent(1,"重要不紧急")
    ,UnimportantUnurgent(1,"不重要不紧急");
    TaskTypeEnum(Integer id, String msg) {
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
