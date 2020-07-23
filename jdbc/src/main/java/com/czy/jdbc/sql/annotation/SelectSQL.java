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
@SQLAnnotation(SQLTypeEnum.Select)
public @interface SelectSQL {
    String value();
    ResultTypeEnum returnType() default ResultTypeEnum.RecordList;
}