package com.czy.core.dispatch;

import com.czy.core.ProjectContainer;
import com.czy.core.annotation.Par;
import com.czy.core.enums.QuestEnum;
import com.czy.core.model.RouteModel;
import com.czy.util.ClassUtil;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author chenzy
 * @since 2020-04-29
 */
public class Dispatch {
    private static Logger logger= LoggerFactory.getLogger("dispatch_log");
    private static StringMap<RouteModel> routeModelMap = null;
    private static Dispatch instance=new Dispatch();
    public static Dispatch getInstance() {
        return instance;
    }
    private Dispatch(){
        init();
    }
    private void init(){
        routeModelMap= ProjectContainer.getInstance().getRouteMap();
    }

    public <T> T post(String url, StringMap dataJson){
        return exec(new Quest(QuestEnum.Post,url,dataJson));
    }
    public <T> T get(String url,StringMap dataJson){
        return exec(new Quest(QuestEnum.Get,url,dataJson));
    }
    public <T> T exec(Quest quest){
        var url=quest.getUrl();
        if (!url.startsWith("/")){
            url="/"+url;
        }
        var dataJson=quest.getData();
        RouteModel routeModel= routeModelMap.get(quest.getQuestEnum().getMsg()+url);
        if (routeModel == null) {
            //再看看是否有可以接受任意请求方式的接口
            routeModel=routeModelMap.get("all"+url);
            if (routeModel == null) {
                routeModel = routeModelMap.get("all/error_404");
            }
        }
        Method modelMethod = routeModel.getMethod();
        Object[] pars =getPar(url,modelMethod,dataJson);
        logger.info("[{}]接收数据:{}", modelMethod.getName(), dataJson);
        /*执行业务方法*/
        Object result = null;
        try {
          result =pars == null||pars.length==0?modelMethod.invoke(routeModel.getBeanModel().getBean())
                :modelMethod.invoke(routeModel.getBeanModel().getBean(), pars);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        logger.info("[{}]返回数据:{}", modelMethod.getName(), result);
        /*输出结果*/
        return (T) result;
    }
    protected Object[] getPar(String url,Method method,StringMap dataJson){
        Parameter[] parameters = method.getParameters();
        if (parameters == null || parameters.length < 1) {
            return null;
        }
        Object[] resultPar = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class parClass = parameters[i].getType();
            /*非基础数据类型的参数，直接转换*/
            if (!ClassUtil.isBasicDataType(parClass)) {
                resultPar[i] = JsonUtil.map2Model(dataJson, parClass);
            } else {
                /*基础数据类型则根据注解值取数据*/
                for (Annotation annotation : parameters[i].getAnnotations()) {
                    resultPar[i] = annotation instanceof Par?dataJson.get(((Par) annotation).value()):null;
                }
            }
        }
        return resultPar;
    }

}
