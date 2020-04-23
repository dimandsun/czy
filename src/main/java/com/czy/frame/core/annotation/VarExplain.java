package com.czy.frame.core.annotation;

import java.lang.annotation.*;

/**
 * @author chenzy
 * @since 2020.02.28
 * 变量上的注解，对变量做说明
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VarExplain {
    String value();
    int minLength() default 0;
    int maxLength() default Integer.MAX_VALUE;

    int min()  default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;

    boolean PositiveInt() default false;
}
