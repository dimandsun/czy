package com.czy.core.annotation.bean;

import java.lang.annotation.*;

/**
 * @author chenzy
 * @since 2020-05-19
 * @description 元注解，用于标注bean注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface BeanAnnotation {
    String value() default "";
}
