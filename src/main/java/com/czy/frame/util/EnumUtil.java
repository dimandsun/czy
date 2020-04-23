package com.czy.frame.util;


import com.czy.frame.core.enums.IEnum;

public class EnumUtil {
    private EnumUtil(){}

    public static  <EN extends IEnum> EN getIEnum(Class<EN> targerType, String source) {
        for (EN enumObj : targerType.getEnumConstants()) {
            if (source.equals(enumObj.getValue().toString())) {
                return enumObj;
            }
        }
        return null;
    }
}
