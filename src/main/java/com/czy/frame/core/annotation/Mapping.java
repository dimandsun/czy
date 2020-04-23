package com.czy.frame.core.annotation;

import java.lang.annotation.*;

/**
 * @author 陈志源 on 2019-01-02.
 * 路由,controller的方法上的注解 对应QuestEnum.Default
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapping {
    String value();
}
