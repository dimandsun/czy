package com.czy.core.annotation.mapping;

import com.czy.core.enums.QuestEnum;

import java.lang.annotation.*;

/**
 * @author chenzy
 * @since 2020-05-19
 * @description 元注解，用于标注mapping注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface MappingAnnotation {
    QuestEnum value();
}
