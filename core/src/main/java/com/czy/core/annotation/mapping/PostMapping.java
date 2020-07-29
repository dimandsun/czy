package com.czy.core.annotation.mapping;

import com.czy.util.enums.QuestMethodEnum;

import java.lang.annotation.*;

/**
 * @author 陈志源 on 2019-01-02.
 * 路由,controller的方法上的注解 restful风格中的新增接口 对应QuestEnum.Post
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MappingAnnotation(QuestMethodEnum.Post)
public @interface PostMapping {
    String value() default "";
}
