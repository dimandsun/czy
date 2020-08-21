package com.czy.core.model;


import com.czy.util.enums.QuestMethodEnum;

import java.lang.reflect.Method;

/**
 * @author chenzy
 * @since 2020-04-01
 *  路由，每一个供外部调用的接口对应一个路由实例
 */
public class RouteModel<Bean> {
    private BeanModel<Bean> beanModel;
    private Method method;
    private QuestMethodEnum questMethodEnum;
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

    public QuestMethodEnum getQuestMethodEnum() {
        return questMethodEnum;
    }

    public void setQuestMethodEnum(QuestMethodEnum questMethodEnum) {
        this.questMethodEnum = questMethodEnum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRouteKey() {
        return questMethodEnum.toString()+url;
    }
}
