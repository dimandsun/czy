package com.czy.log;

import com.czy.util.enums.IEnum;
/**
 * @author chenzy
 * @date 2020-07-17
 */
public enum LogLevel implements IEnum<String> {
    ALL,DEBUG,INFO,WARN,ERROR,;
    @Override
    public String getValue() {
        return name();
    }
}
