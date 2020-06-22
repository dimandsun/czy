package com.czy.core.util;
import com.czy.core.ProjectContainer;
import com.czy.core.annotation.bean.Dao;
import com.czy.core.db.config.DataSourceHolder;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chenzy
 *
 * @since 2020-04-08
 */
public class DaoUtil {
    private DaoUtil() {
    }
    public static Object exeSql(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Class targetClass = method.getDeclaringClass();
        if (!targetClass.isAnnotationPresent(Dao.class)) {
            return null;
        }
        try (var sqlSession =ProjectContainer.getInstance().getDataFactoryMap()
                .get(DataSourceHolder.getInstance().get()).openSession()){
                Object mapper = sqlSession.getMapper(targetClass);
                Method mapperMethod = mapper.getClass().getMethod(method.getName(), method.getParameterTypes());
                Object result = mapperMethod.invoke(mapper, args);
                sqlSession.commit();
                return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw e.getTargetException().getCause();
        }
        return null;
    }
}
