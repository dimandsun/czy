package com.czy.core.annotation.bean;

import com.czy.core.enums.BeanTypeEnum;

import java.lang.annotation.*;

/**
 * service层注解
 * @author 陈志源 on 2019-01-08.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BeanAnnotation(BeanTypeEnum.Service)
public @interface Service {
    String value() default "";
}
