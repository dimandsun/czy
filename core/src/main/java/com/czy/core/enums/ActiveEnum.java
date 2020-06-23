package com.czy.core.enums;

import com.czy.util.text.StringUtil;
import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @since 2020-05-11
 *  项目环境：开发、生产、测试、默认
 */
public enum ActiveEnum implements IEnum<Integer> {

    Dev(1,"dev"),Pro(2,"pro"),Test(3,"javafx.test"),Default(4,"");

    ActiveEnum(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    private Integer id;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public static ActiveEnum getEnum(String active) {
        if (StringUtil.isBlank(active)){
            return null;
        }
        for (ActiveEnum activeEnum : ActiveEnum.values()) {
            if (active.equals(activeEnum.msg)){
                return activeEnum;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return id;
    }
}
