package com.czy.swing.entrance;

import com.czy.core.CoreContainer;
import com.czy.core.model.RouteModel;
import com.czy.core.enums.QuestEnum;
import com.czy.util.model.MyMap;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chenzy
 * @description
 * @since 2020-04-29
 */
public class Dispatch {
    private static MyMap<RouteModel> routeModelMap = null;
    private static Dispatch instance=new Dispatch();
    public static Dispatch getInstance() {
        return instance;
    }
    private Dispatch(){
        init();
    }
    private void init(){
        routeModelMap= CoreContainer.getInstance().getRouteMap();
    }

    public Object post(String url, ObjectNode dataJson){
        return exec(url, QuestEnum.Post,dataJson);
    }
    public Object get(String url,ObjectNode dataJson){
        return exec(url,QuestEnum.Get,dataJson);
    }
    public Object exec(String url, QuestEnum questEnum, ObjectNode dataJson){
        RouteModel routeModel= routeModelMap.get(questEnum.getMsg()+url);
        if (routeModel == null) {
            //再看看是否有可以接受任意请求方式的接口
            routeModel=routeModelMap.get("all"+url);
            if (routeModel == null) {
                return "未找到接口";
            }
        }
        Method modelMethod = routeModel.getMethod();
        /*执行业务方法*/
        Object result = null;
        try {
            result = modelMethod.invoke(routeModel.getBeanModel().getBean(),dataJson);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        /*输出结果*/
        return result;
    }
}
