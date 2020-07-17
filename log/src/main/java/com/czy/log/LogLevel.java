package com.czy.log;

import com.czy.util.enums.IEnum;

import java.util.logging.Level;

/**
 * @author chenzy
 * @date 2020-07-17
 */
public enum LogLevel implements IEnum<String> {
    ALL("all",Level.ALL),DEBUG("debug",Level.CONFIG),INFO("info",Level.INFO),WARN("warn",Level.WARNING),ERROR("error",Level.SEVERE),;
    LogLevel(String code, Level level) {
        this.code = code;
        this.level = level;
    }

    private String code;
    private Level level;

    public static LogLevel getLevel(String code) {
        for (var logLevel: LogLevel.values()) {
            if (logLevel.code.equals(code)){
                return logLevel;
            }
        }
        return ALL;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public String getValue() {
        return code;
    }
}
