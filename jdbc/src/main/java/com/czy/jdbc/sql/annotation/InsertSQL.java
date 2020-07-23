package com.czy.jdbc.sql.annotation;

import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.jdbc.sql.enums.SQLTypeEnum;

import java.lang.annotation.*;

/**
 * @author chenzy
 * @date 2020-07-21
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SQLAnnotation(SQLTypeEnum.Insert)
public @interface InsertSQL {
    String value() default "";
    ResultTypeEnum returnType() default ResultTypeEnum.PrimaryKey;
}