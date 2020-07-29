package com.czy.core.annotation.mapping;

import com.czy.util.enums.QuestMethodEnum;

import java.lang.annotation.*;

/**
 * @author 陈志源 on 2019-01-02.
 * 路由,controller的方法上的注解 对应QuestEnum.Default
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MappingAnnotation(QuestMethodEnum.All)
public @interface Mapping {
    String value() default "";
}
