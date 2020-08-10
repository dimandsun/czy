package com.czy.core.annotation.bean;

import com.czy.core.enums.BeanTypeEnum;

import java.lang.annotation.*;

/**
 * @author 陈志源 on 2019-01-02.
 * bean的一种，controller类使用。
 * 注意value值表示的不是bean的名字，而是路由。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BeanAnnotation(BeanTypeEnum.Controller)
public @interface Controller {
    String value() default "";
}
