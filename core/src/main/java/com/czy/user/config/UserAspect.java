package com.czy.user.config;

import com.czy.core.annotation.Aspect;
import com.czy.core.annotation.bean.Config;
import com.czy.core.enums.AspectTypeEnum;
import com.czy.core.util.DaoUtil;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author chenzy
 *
 * @since 2020-04-03
 */
@Config
public class UserAspect {

    /**
     * 获取 mapper，执行对应sql
     * @param target
     * @param args
     * @return
     */
    @Aspect(pointcuts = "public.* com.czy.user.dao..*.*(..)", order = 1, type = AspectTypeEnum.Around)
    public Object daoAspect$(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
       return DaoUtil.exeSql(target,method,args,methodProxy);
    }
}
