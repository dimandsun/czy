package com.czy.core.annotation;

import java.lang.annotation.*;

/**
 * @author 陈志源 on 2019-01-08.
 * 表实体类上的注解，value表示表名
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    String value();
}
