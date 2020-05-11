package com.czy.util.enums;

/**
 * 响应码级别
 *
 * @author chenzy
 * @date 2019.12.16
 */
public enum ResCodeLevelEnum implements IEnum<Integer> {
    Lev1(1, "一级故障"), Lev2(2, "二级故障"), Lev3(3, "三级故障"), SysProtect(4, "系统保护"), Debug(5, "开发时异常"), Warn(6, "提醒级"), Info(7, "提示")
    ,Process(8,"流程"), Normal(9, "正常"),
    ;

    private Integer id;
    private String msg;

    ResCodeLevelEnum(Integer level, String msg) {
        this.id = level;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

    @Override
    public Integer getValue() {
        return this.id;
    }
}
