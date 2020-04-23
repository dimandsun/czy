package com.czy.core.annotation;

import java.lang.annotation.*;

/**
 * 在三层架构中自动注入对象
 * @author 陈志源 on 2019-01-08.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auto {
    String value() default "";
}
