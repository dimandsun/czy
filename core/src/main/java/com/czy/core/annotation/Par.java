package com.czy.core.annotation;

import java.lang.annotation.*;

/**
 * @author chenzy
 * @since 2020-04-09
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Par {
    String value();
}
