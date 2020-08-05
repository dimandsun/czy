package com.czy.core;

import com.czy.core.annotation.Aspect;
import com.czy.core.annotation.Auto;
import com.czy.core.annotation.bean.*;
import com.czy.core.annotation.mapping.Mapping;
import com.czy.core.annotation.mapping.MappingAnnotation;
import com.czy.core.model.BeanModel;
import com.czy.core.model.ProjectInfo;
import com.czy.core.model.RouteModel;
import com.czy.jdbc.pool.DataSourceFactory;
import com.czy.log.Log;
import com.czy.log.LogFactory;
import com.czy.util.enums.QuestMethodEnum;
import com.czy.util.list.ListUtil;
import com.czy.util.text.StringUtil;
import com.czy.util.io.FileUtilOld;
import com.czy.util.model.OutPar;
import com.czy.util.model.StringMap;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Closeable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author chenzy
 * 应用容器，维护应用下的属性和bean对象
 * @since 2020-03-31
 */
public class ProjectContainer implements Closeable {
    private static Log logger = LogFactory.getLog(ProjectContainer.class);
    private static ProjectContainer instance = new ProjectContainer();
    /*key是 请求方法+url,value是RouteModel*/
    private StringMap<BeanModel> beanMap = new StringMap();
    private StringMap<RouteModel> routeMap = new StringMap();


    public static void setInstance(ProjectContainer instance) {
        ProjectContainer.instance = instance;
    }

    public static ProjectContainer getInstance() {
        return instance;
    }


    protected ProjectContainer() {
    }
    /*初始化项目*/
    public void initProject() {
        System.out.println("*******************************容器正在初始化**************************");
        try {
            /*读取并设置配置文件application-xx.xml中的数据*/
            reloadBasicCofig();
            /*注入bean：把代码中的bean信息放入beanMap和routeMap*/
            setJavaBeanMap();
            /*注入bean:把代码中的config注解类中的注解在方法上的bean和切面放入容器*/
            setConfigClassInner();
            /*处理等待自动注入的属性*/
            doWaitAutoFieldMap();
            classList = null;
            System.out.println("*******************************容器初始化完成**************************");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("*******************************容器初始化失败，退出系统**************************");
            System.exit(-1);
        }
    }
    /**
     * 获取主配置文件application-xx.yml信息
     */
    public Map<String, Object> getBasicConfigMap() {
        var projectInfo=ProjectInfo.getInstance();
        if (projectInfo.getSettingFile()==null){
            ProjectInfo.init(FileUtilOld.getResourceFile("application.yml"));
        }
        return FileUtilOld.readConfigFileByYML(projectInfo.getSettingFile()).get();
    }
    public void reloadBasicCofig() {
        /*读取配置文件application-xx.xml中的数据*/
        Map<String, Object> basicConfigMap = getBasicConfigMap();
        // 注入bean: 配置文件中bean
        setProBean(basicConfigMap);
    }
    public void setProBean(Map<String, Object> proMap) {
        try {
            if (proMap==null){
                return;
            }
            /*1、注入数据源*/
            DataSourceFactory.init(Optional.ofNullable(StringMap.translate(proMap)));
            /*2、注入redis*/
            setBeanRedis(proMap);
            /*3-注入memcache*/
            setBeanMemcah(proMap);
        } catch (Exception e) {
            logger.error("启动异常：加载配置文件失败");
            e.printStackTrace();
        }
    }
    /**
     * 启动的时候使用，启动完毕清除
     */
    private List<Class> classList;

    private void setClassList() {
        if (classList == null) {
            classList = new ArrayList<>();
        }
        String projectGroupId = ProjectInfo.getInstance().getProjectGroupId();
//        String classPath = this.getClass().getResource("/").getPath() + projectGroupId.replace(".", File.separator);
        classList.addAll(FileUtilOld.getClassList(ProjectInfo.getInstance().getModuleDir(), projectGroupId));
    }

    private List<Class> getClassList() {
        if (classList == null || classList.isEmpty()) {
            setClassList();
        }
        return classList;

    }

    private List<Class> getClassList(String packageName) {
        List<Class> result = new ArrayList<>();
        getClassList().forEach(clas -> {
            if (StringUtil.matcher(clas.getName(), packageName)) {
                result.add(clas);
            }
        });
        return result;
    }

    private List<Class> getClassList(Class<? extends Annotation> annotationClass) {
        List<Class> result = new ArrayList<>();
        getClassList().forEach(clas -> {
            if (clas.isAnnotationPresent(annotationClass)) {
                result.add(clas);
            }
        });
        return result;
    }

    /**
     * 把代码中的config注解类中的bean和切面放入容器。
     * 注意config注解的类也是个bean，在setBeanMap()中注入了
     */
    public void setConfigClassInner() {
        getClassList().forEach(c -> {
            if (!c.isAnnotationPresent(Config.class)) {
                return;//跳过本次循环，继续下一个。
            }
            try {
                String configBeanName = StringUtil.lowFirst(c.getSimpleName().replace(".class", ""));
                Object o = beanMap.get(configBeanName).getBean();
                for (Method method : c.getMethods()) {
                    if (method.isAnnotationPresent(Bean.class)) {
                        BeanModel beanModel = new BeanModel(method.getName(), method.invoke(o), method.getReturnType());
                        beanMap.add(method.getName(), beanModel);
                    } else if (method.isAnnotationPresent(Aspect.class)) {
                        AspectContainer.getInstance().add(o, method);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /*重新加载容器*/
    public void reLoadContainer() {
        /*初始化框架*/
        initProject();
    }



    private void setBeanRedis(Map<String, Object> proMap) {
        var redisProMap = (Map<String, Object>) proMap.get("redis");
        /*2.1、注入jedisPoolConfig*/
        var poolProMap = Optional.ofNullable((Map<String, Object>) redisProMap.get("lettuce"))
                .map(lettuceMap -> (Map<String, Object>) lettuceMap.get("pool")).orElse(new HashMap<>(0));
        Integer maxIdle = StringUtil.getInt(poolProMap.get("max-idle"), 300);
        Integer minIdle = StringUtil.getInt(poolProMap.get("min-idle"), 0);
        Integer maxActive = StringUtil.getInt(poolProMap.get("max-active"), -1);
        Long maxWait = StringUtil.getLongByMS(poolProMap.get("max-wait"), 1000L);
        String configBeanName = StringUtil.getStr(poolProMap.get("config-name"), "jedisPoolConfig");
        String poolBeanName = StringUtil.getStr(poolProMap.get("pool-name"), "jedisPool");
        Boolean testOnBorrow = StringUtil.getBoolean(poolProMap.get("javafx.test-on-borrow"));
        var jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        beanMap.add(configBeanName, new BeanModel(configBeanName, jedisPoolConfig, JedisPoolConfig.class));
        /*2.2-注入JedisPool*/
        String host = StringUtil.getStr(redisProMap.get("host"), "127.0.0.1");
        Integer port = StringUtil.getInt(redisProMap.get("port"), 6379);
        String password = StringUtil.getStr(redisProMap.get("password"), null);
        Integer database = StringUtil.getInt(redisProMap.get("database"), 0);
        Integer timeout = StringUtil.getLongByMS(redisProMap.get("timeout"), 60L).intValue();
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
        beanMap.add(poolBeanName, new BeanModel(poolBeanName, jedisPool, JedisPool.class));
    }

    private void setBeanMemcah(Map<String, Object> proMap) {
       /*
        Map<String, Object> memcacheProMap = Optional.ofNullable((Map<String, Object>) proMap.get("memcache")).orElse(new HashMap<>(0));
        String[] servers = StringUtil.getStrlist(memcacheProMap.get("servers"), new String[]{""});
        Boolean failover = StringUtil.getBoolean(memcacheProMap.get("failover"), true);
        Integer initConn = StringUtil.getInt(memcacheProMap.get("initConn"), 20);
        Integer minConn = StringUtil.getInt(memcacheProMap.get("minConn"), 10);
        Integer maxConn = StringUtil.getInt(memcacheProMap.get("maxConn"), 200);
        Integer maintSleep = StringUtil.getInt(memcacheProMap.get("maintSleep"), 3000);
        Boolean nagel = StringUtil.getBoolean(memcacheProMap.get("nagel"), false);
        Integer socketTO = StringUtil.getInt(memcacheProMap.get("socketTO"), 3000);
        Boolean aliveCheck = StringUtil.getBoolean(memcacheProMap.get("aliveCheck"), true);
        String poolName = StringUtil.getStr(memcacheProMap.get("poolName"), "memcachedPool");
       SockIOPool sockIOPool = SockIOPool.getInstance(poolName);
        sockIOPool.setServers(servers);
        sockIOPool.setFailover(failover);
        sockIOPool.setInitConn(initConn);
        sockIOPool.setMinConn(minConn);
        sockIOPool.setMaxConn(maxConn);
        sockIOPool.setMaintSleep(maintSleep);
        sockIOPool.setNagle(nagel);
        sockIOPool.setSocketTO(socketTO);
        sockIOPool.setAliveCheck(aliveCheck);
        sockIOPool.initialize();
        beanMap.add("sockIOPool", new BeanModel("sockIOPool", sockIOPool, SockIOPool.class));
        beanMap.add("memCachedClient", new BeanModel("memCachedClient", new MemCachedClient(poolName), MemCachedClient.class));*/
    }

    public StringMap<RouteModel> getRouteMap() {
        return routeMap;
    }

    public StringMap<BeanModel> getBeanMap() {
        return beanMap;
    }


    /**
     * java代码配置的bean放入容器中
     */
    public void setJavaBeanMap() {
        getClassList().forEach(c -> {
            /*1、获取beanName*/
            OutPar<Class> primaryInterfaceClassPar = new OutPar();
            String beanName = getBeanName(c, primaryInterfaceClassPar);
            if (StringUtil.isBlank(beanName)) {
                return;//等价于普通for循环的continue
            }
            BeanModel beanModel = new BeanModel(beanName);
            if (primaryInterfaceClassPar.get() != null) {
                beanModel.setPrimaryInterfaceClass(primaryInterfaceClassPar.get());
            }
            /*2、获取bean对象*/
            Object bean = null;
            if (c.isAnnotationPresent(Dao.class)) {
                /*Dao、Controller、Service 动态代理，用于切面编程。
                    注意：dao只是个接口，不能生成bean对象，只能生成代理对象，*/
                bean = CGLIB.getInstance().getInstance(c);
            } else if (c.isAnnotationPresent(Service.class) || c.isAnnotationPresent(Controller.class)) {
                bean = CGLIB.getInstance().getInstance(getBean(c));
            } else {
                /*其他bean不实现动态代理*/
                bean = getBean(c);
            }
            beanModel.setBean(bean);
            /*3、bean的class*/
            beanModel.setPrimaryBeanClass(c);
            /*4、添加自动注入字段信息*/
            addWaitAutoFieldMap(beanModel);
            /*5、bean放入beanMap*/
            beanMap.add(beanModel.getBeanName(), beanModel);
            /*6、controller层的接口路由注入*/
            if (c.isAnnotationPresent(Controller.class)) {
                setRouteMap(beanModel);
            }
        });
    }

    private Object getBean(Class beanClass) {
        try {
            return beanClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理等待自动注入的属性
     */
    private void doWaitAutoFieldMap() {
        try {
            for (BeanModel beanModel : beanMap.values()) {
                if (beanModel.hasWaitAutoField()) {
                    Map<Field, Object> waitAutoFieldMap = beanModel.getWaitAutoFieldMap();
                    for (Field field : waitAutoFieldMap.keySet()) {
                        BeanModel fieldBeanModel = beanMap.get(field.getName());
                        if (fieldBeanModel == null) {
                            logger.error("auto自动注入失败，类：{},属性：{}", beanModel.getPrimaryBeanClass(), field.getType());
                        } else {
                            field.setAccessible(true);
                            field.set(beanModel.getBean(), fieldBeanModel.getBean());
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setRouteMap(BeanModel controllerBeanModel) {
        Class c = controllerBeanModel.getPrimaryBeanClass();
        //路由前缀，在Controller类上
        String urlPrefix = null;
        if (c.isAnnotationPresent(Mapping.class)) {
            Mapping mappingAnnotation = (Mapping) c.getAnnotation(Mapping.class);
            urlPrefix = mappingAnnotation.value();
        }
        for (Method method : c.getMethods()) {
            /*urlSuffix:路由后缀，在方法的Mapping注解上
              questEnumOutPar:请求类型
            */
            var questEnumOutPar = new OutPar<QuestMethodEnum>();
            String urlSuffix = getMapping(method, questEnumOutPar);
            if (questEnumOutPar.get() == null) {
                continue;
            }
            RouteModel routeModel = new RouteModel();
            routeModel.setUrl(getUrl(urlPrefix, urlSuffix));
            routeModel.setQuestMethodEnum(questEnumOutPar.get());
            routeModel.setBeanModel(controllerBeanModel);
            routeModel.setMethod(method);
            routeMap.add(routeModel.getRouteKey(), routeModel);
        }

    }

    /**
     * @param urlPrefix 前缀
     * @param urlSuffix 后缀
     * @return
     */
    private String getUrl(String urlPrefix, String urlSuffix) {
        String url = "";
        urlPrefix = trim(urlPrefix);
        if (StringUtil.isNotBlank(urlPrefix)) {
            url = urlPrefix;
        }
        urlSuffix = trim(urlSuffix);
        if (StringUtil.isNotBlank(urlSuffix)) {
            url += urlSuffix;
        }
        return url;
    }

    private String getMapping(Method method, OutPar<QuestMethodEnum> questEnumOutPar) {
        Annotation[] annotations = method.getAnnotations();
        if (ListUtil.isEmpty(annotations)) {
            return null;
        }
        for (Annotation annotation : annotations) {
            Class annotationClass = annotation.annotationType();
            if (!annotationClass.isAnnotationPresent(MappingAnnotation.class)) {
                continue;
            }
            MappingAnnotation mappingAnnotation = (MappingAnnotation) annotationClass.getAnnotation(MappingAnnotation.class);
            questEnumOutPar.set(mappingAnnotation.value());
            try {
                Method annotationMethod = annotationClass.getMethod("value");
                Object temp = annotationMethod.invoke(annotation);
                if (temp != null) {
                    return temp.toString();
                }
            } catch (NoSuchMethodException e) {
                return null;
            } catch (IllegalAccessException e) {
                return null;
            } catch (InvocationTargetException e) {
                return null;
            }
        }
        return null;

    }

    private String trim(String url) {
        if (StringUtil.isBlank(url)) {
            return url;
        }
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    /**
     * 获取beanName及primaryInterfaceClass
     * 注意objectClass本身是接口时，不返还class实现的接口。
     * <p>
     * 从注解值中获取beanName
     * 从接口中获取beanName
     * 从类中获取beanName
     *
     * @param objectClass
     * @return
     */
    public String getBeanName(Class objectClass, OutPar<Class> primaryInterfaceClassPar) {
        Annotation[] annotations = objectClass.getAnnotations();
        if (annotations == null || annotations.length < 1) {
            return null;
        }
        String beanName = null;
        /*1、从注解值中获取beanName*/
        Boolean isBean = false;
        for (Annotation annotation : annotations) {
            Class annotationClass = annotation.annotationType();
            if (!annotationClass.isAnnotationPresent(BeanAnnotation.class)) {
                continue;
            }
            isBean = true;
            try {
                Method method = annotationClass.getMethod("value");
                Object temp = method.invoke(annotation);
                if (temp != null) {
                    beanName = temp.toString();
                }
            } catch (NoSuchMethodException e) {
                return null;
            } catch (IllegalAccessException e) {
                return null;
            } catch (InvocationTargetException e) {
                return null;
            }
        }
        if (!isBean) {
            return null;
        }
        if (StringUtil.isNotBlank(beanName)) {
            return beanName;
        }
        if (objectClass.isInterface()) {
            return getBeanNameByInterface(objectClass);
        }
        /*2、从接口中获取beanName*/
        Class primaryInterfaceClass = null;
        for (Class interfaceClass : objectClass.getInterfaces()) {
            beanName = getBeanNameByInterface(interfaceClass);
            if (beanName != null) {
                primaryInterfaceClass = interfaceClass;
                break;
            }
        }
        /*3、从类中获取beanName*/
        if (StringUtil.isBlank(beanName)) {
            beanName = StringUtil.lowFirst(objectClass.getSimpleName().replace(".class", ""));
        }
        if (beanMap.containsKey(beanName)) {
            logger.error("bean名称重复：{}", beanName);
            return null;
        }
        if (primaryInterfaceClass != null) {
            primaryInterfaceClassPar.set(primaryInterfaceClass);
        }
        return beanName;
    }

    private String getBeanNameByInterface(Class interfaceClass) {
        if (isServiceInterface(interfaceClass)) {
            String temp = interfaceClass.getSimpleName();
            if (temp.startsWith("I")) {
                temp = temp.substring(1);
            }
            return StringUtil.lowFirst(temp.replace(".class", ""));
        }
        return null;
    }
    private Boolean isServiceInterface(Class interfaceClass){
        String projectGroupId = ProjectInfo.getInstance().getProjectGroupId();
        return(interfaceClass.getName().contains(projectGroupId));
    }
    /**
     * bean已经在beanMap中时，直接赋值
     * bean没有在beanMap中时，暂时放到noAutoFieldMap中
     *
     * @param beanModel
     */
    private void addWaitAutoFieldMap(BeanModel beanModel) {
        Class primaryBeanClass = beanModel.getPrimaryBeanClass();
        if (primaryBeanClass == null) {
            return;
        }
        try {
            for (Field field : primaryBeanClass.getDeclaredFields()) {
                /*字段存在自动注入的注解，需要给字段赋值*/
                if (field.isAnnotationPresent(Auto.class)) {
                    BeanModel fieldBeanModel = beanMap.get(field.getName());
                    if (fieldBeanModel != null) {
                        if (notClassType(field.getType(), fieldBeanModel.getPrimaryBeanClass(), fieldBeanModel.getPrimaryInterfaceClass())) {
                            logger.error("要注入的bean类型错误，请检查：{}.{}", primaryBeanClass.getName(), field.getName());
                            continue;
                        }
                        /*bean已经在beanMap中时，直接赋值*/
                        field.setAccessible(true);
                        field.set(beanModel.getBean(), fieldBeanModel.getBean());
                    } else {
                        /*bean没有在beanMap中时，暂时放到noAutoFieldMap中*/
                        beanModel.addWaiteAutoField(field);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        beanMap.values().forEach(beanModel -> {
            Optional.ofNullable(beanModel).map(temp -> temp.getBean()).ifPresent(bean -> {
                if (bean instanceof Closeable closeable) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    /**
     * 判断参数1是否是参数2或者参数3的类型。是返回true
     * 参数3可为空
     *
     * @param c
     * @param c1
     * @param c2
     * @return
     */
    private Boolean notClassType(Class c, Class c1, Class c2) {
        return c != c1 && (c2 == null || c != c2);
    }

    public static void main(String[] args) {
        ProjectContainer instance = ProjectContainer.getInstance();
        instance.initProject();
        System.out.println(123);
    }


}
