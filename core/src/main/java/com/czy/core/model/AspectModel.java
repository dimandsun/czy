package com.czy.core.model;


import com.czy.core.enums.AspectTypeEnum;

import java.lang.reflect.Method;

/**
 * @author chenzy
 * @since 2020-04-03
 *  切面数据的封装
 */
public class AspectModel implements Comparable {
    private String pointcut;
    private Method method = null;
    private Integer order = null;
    private Object object = null;
    private AspectTypeEnum aspectTypeEnum = null;

    public AspectModel(String pointcut, Method method, Integer order, Object object, AspectTypeEnum aspectTypeEnum) {
        this.pointcut = pointcut;
        this.method = method;
        this.order = order;
        this.object = object;
        this.aspectTypeEnum = aspectTypeEnum;
    }

    /**
     * 必须重写equals和hashCode
     * @return
     */
    @Override
    public int hashCode() {
        return this.pointcut.hashCode()+this.order.hashCode()+this.method.toString().hashCode();
    }

    /**
     * 重写equals，便于set集合去重。
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AspectModel){
            AspectModel o = (AspectModel) obj;
            return this.pointcut.equals(o.pointcut)&&this.order.equals(o.order)
                    &&this.method.toString().equals(o.method.toString());
        }
        return super.equals(obj);
    }

    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public AspectTypeEnum getAspectTypeEnum() {
        return aspectTypeEnum;
    }

    public void setAspectTypeEnum(AspectTypeEnum aspectTypeEnum) {
        this.aspectTypeEnum = aspectTypeEnum;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof AspectModel){
            return this.getOrder()-((AspectModel)o).getOrder();
        }
        return 0;
    }

}
