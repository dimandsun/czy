package com.czy.core.model;


import com.czy.enums.QuestEnum;

import java.lang.reflect.Method;

/**
 * @author chenzy
 * @since 2020-04-01
 * @description 路由，每一个供外部调用的接口对应一个路由实例
 */
public class RouteModel<Bean> {
    private BeanModel<Bean> beanModel;
    private Method method;
    private QuestEnum questEnum;
    private String url;

    public BeanModel<Bean> getBeanModel() {
        return beanModel;
    }

    public void setBeanModel(BeanModel<Bean> beanModel) {
        this.beanModel = beanModel;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public QuestEnum getQuestEnum() {
        return questEnum;
    }

    public void setQuestEnum(QuestEnum questEnum) {
        this.questEnum = questEnum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRouteKey() {
        return questEnum.getMsg()+url;
    }
}
