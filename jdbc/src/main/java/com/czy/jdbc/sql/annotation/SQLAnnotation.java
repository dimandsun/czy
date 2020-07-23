package com.czy.jdbc.sql.annotation;

import com.czy.jdbc.sql.enums.SQLTypeEnum;

import java.lang.annotation.*;

/**
 * @author chenzy
 * @date 2020-07-21
 * 元注解，用于标注sql注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface SQLAnnotation {
    SQLTypeEnum value();
}
