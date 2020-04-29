package com.czy.core.annotation;


import com.czy.enums.AspectTypeEnum;

import java.lang.annotation.*;

/**
 * @author 陈志源 on 2020.04.03.
 * 切面注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Aspect {
    /*
    spring切面：execution(public * cn.lexiaotongvip.www.xuJie.control..*.*(..))
    我的切面：public * cn.lexiaotongvip.www.xuJie.control..*.*(..)
    对接口方法的切面：public.* com.czy.frame.test.dao..*.*(..)
    具体规则参考正则表达式语法。*零次或多次匹配前面的字符或子表达式 .匹配除“\n”之外的任何单个字符
    */
    int order();

    String[] pointcuts();
    AspectTypeEnum type();
}
