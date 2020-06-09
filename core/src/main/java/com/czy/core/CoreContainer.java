package com.czy.core;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.czy.core.annotation.*;
import com.czy.core.annotation.bean.*;
import com.czy.core.annotation.mapping.*;
import com.czy.core.db.DaoAspect$;
import com.czy.core.db.config.DataSourceEnum;
import com.czy.core.db.model.MybatisInfo;
import com.czy.core.db.model.TypeAliases;
import com.czy.core.model.BeanModel;
import com.czy.core.model.CoreProject;
import com.czy.core.model.ProjectInfo;
import com.czy.core.model.RouteModel;
import com.czy.core.enums.QuestEnum;
import com.czy.util.FileUtil;
import com.czy.util.ListUtil;
import com.czy.util.StringUtil;
import com.czy.util.model.OutPar;
import com.czy.util.model.StringMap;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author chenzy
 * @description 应用容器，维护应用下的属性和bean对象
 * @since 2020-03-31
 */
public class CoreContainer {
    private static Logger logger = LoggerFactory.getLogger(CoreContainer.class);
    private static CoreContainer instance = new CoreContainer();
    private Set<String> projectGroupIdList = new HashSet<>();
    /*key是 请求方法+url,value是RouteModel*/
    private StringMap<BeanModel> beanMap = new StringMap();
    private StringMap<RouteModel> routeMap = new StringMap();
    private CoreProject coreProject = null;

    public static void setInstance(CoreContainer instance) {
        CoreContainer.instance = instance;
    }

    public static CoreContainer getInstance() {
        return instance;
    }

    public CoreProject getCoreProject() {
        return coreProject;
    }

    public void setCoreProject(CoreProject coreProject) {
        this.coreProject = coreProject;
    }

    public void setProjectInfo() {
        this.coreProject = CoreProject.getInstance();
    }

    /*数据源集合*/
    private Map<DataSourceEnum, SqlSessionFactory> dataSourceMap = new HashMap<>();

    protected CoreContainer() {
    }

    /*初始化项目*/
    public void initProject() {
        try {
            if (projectGroupIdList.size() < 1) {
                return;
            }
            /*注入bean：读取配置文件中的bean*/
            setProBean();
            /*注入bean：把代码中的bean信息放入beanMap和routeMap*/
            setJavaBeanMap();
            /*注入bean:把代码中的config注解类中的注解在方法上的bean和切面放入容器*/
            setConfigClassInner();
            /*处理等待自动注入的属性*/
            doWaitAutoFieldMap();
            /*mybatis配置多数据源*/
            String packageName = null;
            if (beanMap.get("mybatisInfo") != null) {
                packageName = ((MybatisInfo) beanMap.get("mybatisInfo").getBean()).getTypeAliases().getPackageName();
            }
            for (DataSourceEnum dataSourceEnum : DataSourceEnum.values()) {
                if (dataSourceEnum.getDataSource() == null) continue;
                Configuration configuration = new Configuration(
                        new Environment("development", new JdbcTransactionFactory(), dataSourceEnum.getDataSource()));
                /*mybatis设置对象别名*/
                if (packageName != null) {
                    TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
                    for (Class c : getClassList(packageName)) {
                        typeAliasRegistry.registerAlias(c);
                    }
                }
                /*设值mapper*/
                setMapper(configuration);
                configuration.addLoadedResource("mybatis-config.xml");
                dataSourceMap.put(dataSourceEnum, new SqlSessionFactoryBuilder().build(configuration));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private List<Class> getClassList(String packageName) {
        List<Class> classList = new ArrayList<>();
        String classPathP = this.getClass().getResource("").getPath();
        classPathP = classPathP.substring(0, classPathP.indexOf(CoreProject.getInstance().getGroupId().replace(".", "/")));
        for (String projectGroupId : projectGroupIdList) {
            String classPath = classPathP + projectGroupId.replace(".", File.separator);
            classList.addAll(FileUtil.getClassList(classPath, projectGroupId, packageName));
        }
        return classList;
    }

    private List<Class> getClassList(Class<? extends Annotation> annotationClass) {
        List<Class> classList = new ArrayList<>();
        String classPathP = this.getClass().getResource("").getPath();
        classPathP = classPathP.substring(0, classPathP.indexOf(CoreProject.getInstance().getGroupId().replace(".", "/")));
        for (String projectGroupId : projectGroupIdList) {
            String classPath = classPathP + projectGroupId.replace(".", File.separator);
            classList.addAll(FileUtil.getClassList(classPath, projectGroupId, annotationClass));
        }
        return classList;
    }

    /**
     * 把代码中的config注解类中的bean和切面放入容器。
     * 注意config注解的类也是个bean，在setBeanMap()中注入了
     */
    public void setConfigClassInner() {
        Class<? extends Annotation> annotationClass = null;
        List<Class> classList = getClassList(annotationClass);
        for (Class c : classList) {
            if (!c.isAnnotationPresent(Config.class)) {
                continue;
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
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /*重新加载容器*/
    public void reLoadContainer() {
        /*初始化框架*/
        initProject();
    }

    private void setMapper(Configuration configuration) {
        for (Class c : getClassList(Dao.class)) {
            if (!configuration.hasMapper(c)) {
                configuration.addMapper(c);
            }
        }
    }

    /**
     * 获取主配置文件application.yml信息
     */
    public Map<String, Object> getProMap() {
        String proFileName = "application.yml";
        proFileName = "application-" + ProjectInfo.getInstance().getActive().getMsg() + ".yml";
        Map<String, Object> resultMap = FileUtil.readConfigFileByYML(proFileName);
        return resultMap;
    }

    public void setProBean() {
        try {

            Map<String, Object> proMap = getProMap();
            if (proMap == null) {
                return;
            }
            /*1、注入数据源*/
            Map<String, Map<String, Object>> datasourceMap = (Map<String, Map<String, Object>>) proMap.get("datasource");
            if (datasourceMap != null) {
                for (Map.Entry<String, Map<String, Object>> entry : datasourceMap.entrySet()) {
                    /*数据源直接放入枚举中*/
                    DataSourceEnum.setDataSource(entry.getKey(), DruidDataSourceFactory.createDataSource(entry.getValue()));
                }
            }
            /*写入mybatis配置信息*/
            String packageName = Optional.ofNullable((Map<String, Map<String, String>>) proMap.get("mybatis"))
                    .map(projectMap -> projectMap.get("typeAliases")).map(projectMap -> projectMap.get("packageName")).orElse("");
            if (packageName != "") {
                MybatisInfo mybatisInfo = new MybatisInfo(new TypeAliases(packageName));
                beanMap.add("mybatisInfo", new BeanModel("mybatisInfo", mybatisInfo, MybatisInfo.class));
            }
            /*2、注入redis*/
            setBeanRedis(proMap);
            /*3-注入memcache*/
            setBeanMemcah(proMap);
            /*4-注入项目信息*/
            setProjectInfo();
            setProjectInfo((Map<String, Object>) proMap.get("project"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProjectInfo(Map<String, Object> projectMap) {
        if (projectMap != null) {
            Object projectName = projectMap.get("name");
            if (projectName != null) {
//                coreProject.setProjectName(projectName.toString());
            }
        }
    }

    private void setBeanRedis(Map<String, Object> proMap) {
        Map<String, Object> redisProMap = (Map<String, Object>) proMap.get("redis");
        /*2.1、注入jedisPoolConfig*/
        Map<String, Object> poolProMap = Optional.ofNullable((Map<String, Object>) redisProMap.get("lettuce"))
                .map(lettuceMap -> (Map<String, Object>) lettuceMap.get("pool")).orElse(new HashMap<>(0));
        Integer maxIdle = StringUtil.getInt(poolProMap.get("max-idle"), 300);
        Integer minIdle = StringUtil.getInt(poolProMap.get("min-idle"), 0);
        Integer maxActive = StringUtil.getInt(poolProMap.get("max-active"), -1);
        Long maxWait = StringUtil.getLongByMS(poolProMap.get("max-wait"), 1000L);
        String configBeanName = StringUtil.getStr(poolProMap.get("config-name"),"jedisPoolConfig");
        String poolBeanName = StringUtil.getStr(poolProMap.get("pool-name"),"jedisPool");
        Boolean testOnBorrow = StringUtil.getBoolean(poolProMap.get("test-on-borrow"));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
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
       /* SockIOPool sockIOPool = SockIOPool.getInstance(poolName);
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

    public Map<DataSourceEnum, SqlSessionFactory> getDataSourceMap() {
        return dataSourceMap;
    }

    public Set<String> getProjectGroupIdList() {
        return projectGroupIdList;
    }

    public void addProjectGroupId(String projectGroupId) {
        this.projectGroupIdList.add(projectGroupId);
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
        Class<? extends Annotation> annotationClass = null;
        for (Class c : getClassList(annotationClass)) {
            /*1、获取beanName*/
            OutPar<Class> primaryInterfaceClassPar = new OutPar();
            String beanName = getBeanName(c, primaryInterfaceClassPar);
            if (StringUtil.isBlank(beanName)) {
                continue;
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
        }
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
            var questEnumOutPar=new OutPar<QuestEnum>();
            String urlSuffix = getMapping(method,questEnumOutPar);
            if (questEnumOutPar.get()==null){
                continue;
            }
            RouteModel routeModel = new RouteModel();
            routeModel.setUrl(getUrl(urlPrefix,urlSuffix));
            routeModel.setQuestEnum(questEnumOutPar.get());
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
        String url="";
        urlPrefix =trim(urlPrefix);
        if (StringUtil.isNotBlank(urlPrefix)){
            url=urlPrefix;
        }
        urlSuffix =trim(urlSuffix);
        if (StringUtil.isNotBlank(urlSuffix)){
            url+=urlSuffix;
        }
        return url;
    }

    private String getMapping(Method method, OutPar<QuestEnum> questEnumOutPar) {
        Annotation[] annotations = method.getAnnotations();
        if (ListUtil.isEmpty(annotations)) {
            return null;
        }
        for (Annotation annotation:annotations){
            Class annotationClass = annotation.annotationType();
            if (!annotationClass.isAnnotationPresent(MappingAnnotation.class)) {
                continue;
            }
            MappingAnnotation mappingAnnotation= (MappingAnnotation) annotationClass.getAnnotation(MappingAnnotation.class);
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
        String inteSimpleName = interfaceClass.getSimpleName();
        if (inteSimpleName.startsWith("I")) {
            inteSimpleName = inteSimpleName.substring(1);
        }
        for (String projectGroupId : projectGroupIdList) {
            if (interfaceClass.getName().contains(projectGroupId)) {
                return StringUtil.lowFirst(inteSimpleName.replace(".class", ""));
            }
        }
        return null;
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
        CoreContainer instance = CoreContainer.getInstance();
        instance.initProject();

    }

}
