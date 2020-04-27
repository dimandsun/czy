package com.czy.core.db;

import com.czy.core.annotation.Aspect;
import com.czy.core.annotation.Config;
import com.czy.core.enums.AspectTypeEnum;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author chenzy
 * @description
 * @since 2020-04-03
 */
@Config
public class DaoAspect$ {

    /**
     * 获取 mapper，执行对应sql
     * @param target
     * @param args
     * @return
     */
    @Aspect(pointcuts = "public.* com.czy.core\\.[(a-zA-Z)]*\\.dao..*.*(..)", order = 1, type = AspectTypeEnum.Around)
    public Object daoAspect$(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
       return DaoUtil.exeSql(target,method,args,methodProxy);
    }
}
