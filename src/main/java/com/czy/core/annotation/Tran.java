package com.czy.core.annotation;

import java.lang.annotation.*;

/**
 * 事务注解
 * @author 陈志源 on 2019-01-08.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tran {
}
