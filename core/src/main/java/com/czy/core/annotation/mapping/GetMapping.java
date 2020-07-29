package com.czy.core.annotation.mapping;

import com.czy.util.enums.QuestMethodEnum;

import java.lang.annotation.*;

/**
 * @author 陈志源 on 2019-01-02.
 * 路由,controller的方法上的注解，restful风格中的查询接口 对应QuestEnum.Get
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MappingAnnotation(QuestMethodEnum.Get)
public @interface GetMapping {
    String value() default "";
}
