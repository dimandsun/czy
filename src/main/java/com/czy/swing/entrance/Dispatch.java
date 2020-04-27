package com.czy.swing.entrance;

import com.czy.core.CoreContainer;
import com.czy.core.annotation.Par;
import com.czy.core.enums.QuestEnum;
import com.czy.core.model.RouteModel;
import com.czy.util.ClassUtil;
import com.czy.util.enums.ResCodeEnum;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.MyMap;
import com.czy.util.model.OutPar;
import com.czy.util.model.ResultVO;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author chenzy
 * @description
 * @since 2020-04-23
 */
public class Dispatch {
    private static Logger logger = LoggerFactory.getLogger(Dispatch.class);
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

    public Object post(String url,ObjectNode dataJson){
        return exec(url,QuestEnum.Post,dataJson);
    }
    public Object get(String url,ObjectNode dataJson){
        return exec(url,QuestEnum.Get,dataJson);
    }
    private Object[] getPar(String url,Method method,ObjectNode dataJson,OutPar<Object> parVerifyResult){
        Parameter[] parameters = method.getParameters();
        if (parameters == null || parameters.length < 1) {
            return null;
        }
        Object[] resultPar = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class parClass = parameters[i].getType();
            /*非基础数据类型的参数，直接转换*/
            if (!ClassUtil.isBasicDataType(parClass)) {
                resultPar[i] = JsonUtil.model2Model(dataJson, parClass);
            } else {
                /*基础数据类型则根据注解值取数据*/
                for (Annotation annotation : parameters[i].getAnnotations()) {
                    if (annotation instanceof Par) {
                        String parName = ((Par) annotation).value();
                        resultPar[i] = dataJson.get(parName);
                    }
                    resultPar[i] = null;
                }
            }

        }
        return resultPar;
    }
    private Object exec(String url, QuestEnum questEnum, ObjectNode dataJson){
        RouteModel routeModel= routeModelMap.get(questEnum.getMsg()+url);
        if (routeModel == null) {
            //再看看是否有可以接受任意请求方式的接口
            routeModel=routeModelMap.get("all"+url);
            if (routeModel == null) {
                return "未找到接口";
            }
        }
        Method modelMethod = routeModel.getMethod();

        try {
            /*获取参数,子类实现getPar方法，即对参数可以做自定义的校验及解析*/
            OutPar<Object> parVerifyResult = new OutPar<>(null);
            Object[] pars = getPar(url,modelMethod, dataJson,parVerifyResult);
            Object parVerify = parVerifyResult.get();
            /*数据验证不通过，直接返回错误信息*/
            if (parVerify!=null){
                return parVerify;
            }
            /*执行业务方法*/
            Object result = null;
            if (pars == null||pars.length==0) {
                result = modelMethod.invoke(routeModel.getBeanModel().getBean());
            } else {
                result = modelMethod.invoke(routeModel.getBeanModel().getBean(), pars);
            }
            return result;
        } catch (IllegalAccessException e) {
            logger.error("访问异常：[url：" + url + "]", e);
            return new ResultVO<>(ResCodeEnum.BusInExce,"访问异常：[url：" + url + "]") ;
        } catch (InvocationTargetException e) {
            logger.error("访问异常：[url：" + url + "]", e);
            return new ResultVO<>(ResCodeEnum.BusInExce,"访问异常：[url：" + url + "]") ;
        }
    }
}
