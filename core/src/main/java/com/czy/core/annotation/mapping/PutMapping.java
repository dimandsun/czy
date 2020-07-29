package com.czy.core.annotation.mapping;

import com.czy.util.enums.QuestMethodEnum;

import java.lang.annotation.*;

/**
 * @author 陈志源 on 2019-01-02.
 * 路由,controller的方法上的注解 restful风格中的更新接口 对应QuestEnum.Put
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MappingAnnotation(QuestMethodEnum.Put)
public @interface PutMapping {
    String value() default "";
}
