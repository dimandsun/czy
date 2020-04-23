package com.czy.core.db;

import com.czy.core.CoreContainer;
import com.czy.core.annotation.Dao;
import com.czy.core.db.config.DataSourceHolder;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chenzy
 * @description
 * @since 2020-04-08
 */
public class DaoUtil {
    private DaoUtil() {
    }
    public static Object exeSql(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Class targetClass = method.getDeclaringClass();
        SqlSession sqlSession = null;
        try {
            if (targetClass.isAnnotationPresent(Dao.class)) {
                sqlSession = CoreContainer.getInstance().getDataSourceMap().get(DataSourceHolder.getInstance().get())
                        .openSession();
                Object mapper = sqlSession.getMapper(targetClass);
                Method mapperMethod = mapper.getClass().getMethod(method.getName(), method.getParameterTypes());
                Object result = mapperMethod.invoke(mapper, args);
                sqlSession.commit();
                return result;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw e.getTargetException().getCause();
        }finally {
            if (sqlSession!=null){
                sqlSession.close();
            }
        }
        return null;
    }
}
