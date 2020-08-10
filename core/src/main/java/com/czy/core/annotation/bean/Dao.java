package com.czy.core.annotation.bean;

import com.czy.core.enums.BeanTypeEnum;

import java.lang.annotation.*;

/**
 * dao层注解
 * @author 陈志源 on 2019-01-08.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BeanAnnotation(BeanTypeEnum.Dao)
public @interface Dao {
    String value() default "";
}
