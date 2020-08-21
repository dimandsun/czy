package com.czy.core.enums;

import com.czy.util.text.StringUtil;
import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @since 2020-05-11
 *  项目环境：开发、生产、测试、默认
 */
public enum ActiveEnum implements IEnum<String> {
    dev,pro,test,other;
    public static ActiveEnum getEnum(String name) {
        if (StringUtil.isBlank(name)){
            return null;
        }
        return ActiveEnum.valueOf(name.toLowerCase());
    }
    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public String getValue() {
        return name();
    }
}
