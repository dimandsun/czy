package com.czy.core.db.service.impl;

import com.czy.core.annotation.Auto;
import com.czy.core.annotation.Service;
import com.czy.core.annotation.Table;
import com.czy.core.db.dao.ICommonDao;
import com.czy.core.db.service.ICommonService;
import com.czy.util.StringUtil;
import com.czy.util.json.JsonUtil;

import java.util.Map;

/**
 * @author chenzy
 * @description
 * @since 2020-04-24
 */
@Service
public class CommonServiceImpl implements ICommonService {
    @Auto
    private ICommonDao commonDao;

    @Override
    public <Bean> Integer insert(Bean bean) {
        Map<String, Object> par = JsonUtil.model2Model(bean, Map.class);
        Class beanClass = bean.getClass();
        String tableName = beanClass.isAnnotationPresent(Table.class) ? ((Table) beanClass.getAnnotation(Table.class)).value() :
                StringUtil.lowFirst(beanClass.getSimpleName().replace(".class", ""));
        Integer id = commonDao.insert(par, tableName);
        return id;
    }
}
