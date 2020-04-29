package com.czy.core;

import com.czy.core.model.AspectModel;
import com.czy.enums.AspectTypeEnum;
import com.czy.util.enums.ResCodeEnum;
import com.czy.util.model.ResultVO;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by 陈志源 on 2020.04.02
 * 实现切面，切面通过此代理类分配执行
 * controller、service、dao接口实现此代理，其他bean，包括config注解、通过配置文件生成的bean不应该实现此代理。
 */
public class CGLIB implements MethodInterceptor {
    private static Logger logger = LoggerFactory.getLogger(CGLIB.class);
    private Object target;

    public CGLIB() {

    }
    private static CGLIB instance = new CGLIB();
    public static CGLIB getInstance() {
        return instance;
    }

    //相当于JDK动态代理中的绑定
    public Object getInstance(Class<?> targetClass) {
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(targetClass);
        //3.设置回调函数
        en.setCallback(this);
        //4.创建子类(代理对象)
        return en.create();
    }
    public Object getInstance(Object target) {
       this.target=target;
       return getInstance(target.getClass());
    }


    /**
     * @param targetProxy
     * @param method
     * @param args
     * @param methodProxy
     * @return
     */
    @Override
    public Object intercept(Object targetProxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        try {
            List<AspectModel> aspectModelList = AspectContainer.getInstance().getAspectModelList(method.toString());
            /*没有切面执行原方法*/
            if (aspectModelList.isEmpty()) {
                return methodProxy.invokeSuper(targetProxy, args);
            }
            Object result = null;
            for (AspectModel aspectModel: aspectModelList){
                Object aspectObject =aspectModel.getObject();
                AspectTypeEnum aspectTypeEnum=aspectModel.getAspectTypeEnum();
                Method aspectMethod =aspectModel.getMethod();
                Object[] aspectArgs={targetProxy,method,args,methodProxy};
                if (aspectTypeEnum.equals(AspectTypeEnum.Around)) {
                    /*环绕切面,只执行第一个*/
                    return aspectMethod.invoke(aspectObject, aspectArgs);
                } else if (aspectTypeEnum.equals(AspectTypeEnum.Before)) {
                    /*前置切面*/
                    Object respectResult = aspectMethod.invoke(aspectObject, aspectArgs);
                } else if (aspectTypeEnum.equals(AspectTypeEnum.After)) {
                    /*后置切面*/
                    if (result==null){
                        result = methodProxy.invokeSuper(targetProxy, args);
                    }
                    aspectArgs= new Object[]{methodProxy, targetProxy, result, args};
                    aspectMethod.invoke(aspectObject, aspectArgs);
                } else {
                    logger.error("切面异常,未知切面类型");
                }
            }
            /*后置切面时result不为空*/
            if (result!=null){
                return result;
            }
            return methodProxy.invokeSuper(target, args);
        }catch (IllegalAccessException e) {
            logger.error("切面异常!", e);
            return new ResultVO<>(ResCodeEnum.BusInExce,"切面异常");
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Throwable throwable) {
            throw throwable;
        }
    }
}