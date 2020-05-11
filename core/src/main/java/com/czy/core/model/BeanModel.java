package com.czy.core.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装bean实例及方法。在项目启动时加载
 * @author 陈志源 on 2019-01-03.
 */
public class BeanModel<Bean> {
    /*bean的原类，没有代理时bean.getClass()==primaryBeanClass才成立*/
    private Class<Bean> primaryBeanClass;
    /*bean的原类的接口*/
    private Class<?> primaryInterfaceClass;
    /*bean对象或者bean的代理对象。*/
    private Bean bean;
    private String beanName;
    /*等待自动注入的属性*/
    private Map<Field,Object> waitAutoFieldMap;

    public BeanModel(String beanName) {
        this.beanName = beanName;
    }

    public BeanModel(String beanName, Bean bean, Class primaryBeanClass) {
        this.beanName=beanName;
        this.bean = bean;
        this.primaryBeanClass =primaryBeanClass;
    }

    public Class<Bean> getPrimaryBeanClass() {
        return primaryBeanClass;
    }

    public Class<?> getPrimaryInterfaceClass() {
        return primaryInterfaceClass;
    }

    public void setPrimaryInterfaceClass(Class<?> primaryInterfaceClass) {
        this.primaryInterfaceClass = primaryInterfaceClass;
    }

    public void setPrimaryBeanClass(Class<Bean> primaryBeanClass) {
        this.primaryBeanClass = primaryBeanClass;
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

    public Map<Field, Object> getWaitAutoFieldMap() {
        return waitAutoFieldMap;
    }

    public void setWaitAutoFieldMap(Map<Field, Object> waitAutoFieldMap) {
        this.waitAutoFieldMap = waitAutoFieldMap;
    }

    public void addWaiteAutoField(Field field) {
        if (waitAutoFieldMap==null){
            waitAutoFieldMap=new HashMap<>();
        }
        waitAutoFieldMap.put(field,null);
    }

    /**
     * 有等待注入的属性返回true
     * @return
     */
    public boolean hasWaitAutoField() {
        return waitAutoFieldMap!=null&&waitAutoFieldMap.size()>0;
    }
}
