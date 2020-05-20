package com.czy.core.annotation.bean;

import java.lang.annotation.*;

/**
 * bean注解,类上有此注解，表示应用启动时会生成类的实例，可以用Auto调用生成的实例。bean的名字是类名(首字母小写)
 * 方法上有此注解，表示会将返回对象作为bean放在应用容器中，bean的名字是方法名
 * @author 陈志源 on 2019-01-08.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BeanAnnotation
public @interface Bean {
}
