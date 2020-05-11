package com.czy.core;

import com.czy.core.annotation.Aspect;
import com.czy.core.model.AspectModel;
import com.czy.util.StringUtil;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author chenzy
 * @description
 * @since 2020-04-03
 */
public class AspectContainer {
    private static AspectContainer instance = new AspectContainer();

    private AspectContainer() {
    }
    public static AspectContainer getInstance() {
        return instance;
    }
    private Set<AspectModel> aspectModelList = new HashSet<>();

    public void add(Object aspectObject, Method aspectMethod) {
        if (aspectMethod.isAnnotationPresent(Aspect.class)) {
            Aspect aspect = aspectMethod.getAnnotation(Aspect.class);
            for (String pointcut:aspect.pointcuts()){
                aspectModelList.add(new AspectModel(pointcut,aspectMethod,aspect.order(),aspectObject,aspect.type()));
            }
        }
    }
    public void clear(){
        aspectModelList.clear();
    }
    public List<AspectModel> getAspectModelList(String targetMethod) {
        List<AspectModel> result = new ArrayList<>();
        for (AspectModel aspectModel:aspectModelList){
            if (StringUtil.matcher(targetMethod,aspectModel.getPointcut())){
                result.add(aspectModel);
            }
        }
        Collections.sort(result, AspectModel::compareTo);
        return result;
    }
}
