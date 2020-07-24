package com.czy.core.util;

import com.czy.core.annotation.bean.Dao;
import com.czy.jdbc.pool.DataSourceHolder;
import com.czy.jdbc.sql.SQLBuilder;
import com.czy.jdbc.sql.SQLFactory;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * @author chenzy
 * @since 2020-04-08
 * netty
 */
public class DaoUtil {
    private DaoUtil() {
    }
    public static Object exeSql(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Class targetClass = method.getDeclaringClass();
        if (!targetClass.isAnnotationPresent(Dao.class)) {
            return null;
        }
        try {
            SQLBuilder sqlBuilder = SQLFactory.createSQL(method, args);
            if (sqlBuilder == null) {
                throw new SQLException("没有获取到sql信息!");
            }
            Object result = sqlBuilder.exec();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            //防止内存泄漏
            DataSourceHolder.getInstance().clear();
        }
    }
}
