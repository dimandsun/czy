package com.czy.core;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.czy.core.annotation.*;
import com.czy.core.db.config.DataSourceEnum;
import com.czy.core.db.model.MybatisInfo;
import com.czy.core.db.model.TypeAliases;
import com.czy.core.model.BeanModel;
import com.czy.core.model.CoreProject;
import com.czy.core.model.RouteModel;
import com.czy.core.enums.QuestEnum;
import com.czy.util.FileUtil;
import com.czy.util.StringUtil;
import com.czy.util.model.MyMap;
import com.czy.util.model.OutPar;
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
    private MyMap<BeanModel> beanMap = new MyMap();
    private MyMap<RouteModel> routeMap = new MyMap();
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
        for (String projectGroupId : projectGroupIdList) {
            String classPath = this.getClass().getResource("/").getPath() + projectGroupId.replace(".", File.separator);
            classList.addAll(FileUtil.getClassList(classPath, projectGroupId, packageName));
        }
        return classList;
    }

    private List<Class> getClassList(Class<? extends Annotation> annotationClass) {
        List<Class> classList = new ArrayList<>();
        for (String projectGroupId : projectGroupIdList) {
            String classPath = this.getClass().getResource("/").getPath() + projectGroupId.replace(".", File.separator);
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
        Map<String, Map<String, Object>> proMap = FileUtil.readConfigFileByYML(proFileName);
        if (proMap==null){
            return null;
        }
        String active = proMap.get("profiles").get("active").toString();
        proFileName = "application-" + active + ".yml";
        Map<String, Object> resultMap = FileUtil.readConfigFileByYML(proFileName);
        return resultMap;
    }

    public void setProBean() {
        try {

            Map<String, Object> proMap = getProMap();
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
        Boolean testOnBorrow = StringUtil.getBoolean(poolProMap.get("test-on-borrow"));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        beanMap.add("jedisPoolConfig", new BeanModel("jedisPoolConfig", jedisPoolConfig, JedisPoolConfig.class));
        /*2.2-注入JedisPool*/
        String host = StringUtil.getStr(redisProMap.get("host"), "127.0.0.1");
        Integer port = StringUtil.getInt(redisProMap.get("port"), 6379);
        String password = StringUtil.getStr(redisProMap.get("password"), null);
        Integer database = StringUtil.getInt(redisProMap.get("database"), 0);
        Integer timeout = StringUtil.getLongByMS(redisProMap.get("timeout"), 60L).intValue();
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
        beanMap.add("jedisPool", new BeanModel("jedisPool", jedisPool, JedisPool.class));
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

    public MyMap<RouteModel> getRouteMap() {
        return routeMap;
    }

    public MyMap<BeanModel> getBeanMap() {
        return beanMap;
    }


    /**
     * java代码配置的bean放入容器中
     */
    public void setJavaBeanMap() {
        Class<? extends Annotation> annotationClass = null;
        for (Class c : getClassList(annotationClass)) {
            BeanModel beanModel = null;
            /*1、获取beanName*/
            if (c.isAnnotationPresent(Service.class) || c.isAnnotationPresent(Controller.class)
                    || c.isAnnotationPresent(Dao.class) || c.isAnnotationPresent(Config.class) || c.isAnnotationPresent(Bean.class)) {
                OutPar<Class> primaryInterfaceClassPar = new OutPar();
                String beanName = getBeanName(c, primaryInterfaceClassPar);
                if (StringUtil.isBlank(beanName)) {
                    continue;
                }
                beanModel = new BeanModel(beanName);
                if (primaryInterfaceClassPar.get() != null) {
                    beanModel.setPrimaryInterfaceClass(primaryInterfaceClassPar.get());
                }
            } else {
                continue;
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
        Controller controllerAnnotation = (Controller) c.getAnnotation(Controller.class);
        String urlPrefix = controllerAnnotation.value();
        if (!urlPrefix.isBlank()&&!urlPrefix.startsWith("/")) {
            urlPrefix="/"+urlPrefix;
        }
        QuestEnum questEnum = null;
        String url = null;
        for (Method method : c.getMethods()) {
            if (method.isAnnotationPresent(Mapping.class)) {
                questEnum = QuestEnum.All;
                url = getURL(method.getAnnotation(Mapping.class).value(),urlPrefix);
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                questEnum = QuestEnum.Post;
                url = getURL(method.getAnnotation(PostMapping.class).value(),urlPrefix);
            } else if (method.isAnnotationPresent(PutMapping.class)) {
                questEnum = QuestEnum.Put;
                url = getURL(method.getAnnotation(PutMapping.class).value(),urlPrefix);
            } else if (method.isAnnotationPresent(GetMapping.class)) {
                questEnum = QuestEnum.Get;
                url =getURL(method.getAnnotation(GetMapping.class).value(),urlPrefix);
            } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                questEnum = QuestEnum.Delete;
                url =getURL(method.getAnnotation(DeleteMapping.class).value(),urlPrefix);
            } else {
                continue;
            }
            RouteModel routeModel = new RouteModel();
            routeModel.setUrl(url);
            routeModel.setQuestEnum(questEnum);
            routeModel.setBeanModel(controllerBeanModel);
            routeModel.setMethod(method);
            routeMap.add(routeModel.getRouteKey(), routeModel);
        }

    }
    private String getURL(String url2,String urlPrefix){
        if (!url2.isBlank()&&!url2.startsWith("/")) {
            url2="/"+url2;
        }
        return urlPrefix + url2;
    }
    private String getBeanName(Class interfaceClass) {
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
     * 根据class名获取beanName，若有实现接口，则根据接口class名获取beanName，同时得到Class所实现的接口。
     * 注意calss本身是接口时，不返还class实现的接口。
     *
     * @return
     */
    private String getBeanName(Class c, OutPar<Class> primaryInterfaceClassPar) {
        if (c.isInterface()) {
            return getBeanName(c);
        }
        String beanName = null;
        /*如果有bean接口，beanName取接口名*/
        Class primaryInterfaceClass = null;
        for (Class interfaceClass : c.getInterfaces()) {
            beanName = getBeanName(interfaceClass);
            if (beanName != null) {
                primaryInterfaceClass = interfaceClass;
                break;
            }
        }
        /*如果bean没有接口，beanName取类名*/
        if (beanName == null) {
            beanName = StringUtil.lowFirst(c.getSimpleName().replace(".class", ""));
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
