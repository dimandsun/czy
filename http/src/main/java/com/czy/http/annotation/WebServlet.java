package com.czy.http.annotation;

import java.lang.annotation.*;

/**
 * @author chenzy
 * @date 2020-08-12
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServlet {
    String[] value();
    String name();
}
