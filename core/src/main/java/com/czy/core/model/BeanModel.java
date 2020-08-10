package com.czy.core.model;

import com.czy.core.enums.BeanTypeEnum;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装bean实例及方法。在项目启动时加载
 * @author 陈志源 on 2019-01-03.
 */
public class BeanModel<Bean> {
    /*bean的原类，没有代理时bean.getClass()==beanClass才成立*/
    private Class<Bean> beanClass;
    /*bean的原类的接口*/
    private Class<?> beanInterface;
    /*bean对象或者bean的代理对象。*/
    private Bean bean;
    private String beanName;
    private BeanTypeEnum beanType;
    /*等待自动注入的属性*/
    private List<Field> waitAutoFieldList=new ArrayList<>();

    public BeanModel(String beanName) {
        setBeanName(beanName);
    }
    public BeanModel(String beanName, Bean bean, Class beanClass,BeanTypeEnum beanType) {
       setBeanName(beanName);
       setBean(bean);
       setBeanClass(beanClass);
       setBeanType(beanType);
    }

    public BeanTypeEnum getBeanType() {
        return beanType;
    }

    public void setBeanType(BeanTypeEnum beanType) {
        this.beanType = beanType;
    }

    public Class<Bean> getBeanClass() {
        return beanClass;
    }

    public Class<?> getBeanInterface() {
        return beanInterface;
    }

    public void setBeanInterface(Class<?> beanInterface) {
        this.beanInterface = beanInterface;
    }

    public void setBeanClass(Class<Bean> beanClass) {
        this.beanClass = beanClass;
    }

    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public List<Field> getWaitAutoFieldList() {
        return waitAutoFieldList;
    }
    public void addWaiteAutoField(Field field) {
        waitAutoFieldList.add(field);
    }
    /**
     * 清除加载时用的waitAutoFieldMap
     * 加载完后调用，否则异常
     */
    public void clear() {
        waitAutoFieldList=null;
    }
}
