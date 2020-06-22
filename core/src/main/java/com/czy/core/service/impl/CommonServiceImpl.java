package com.czy.core.service.impl;

import com.czy.core.annotation.Auto;
import com.czy.core.annotation.bean.Service;
import com.czy.core.db.dao.ICommonDao;
import com.czy.core.service.ICommonService;
import com.czy.core.util.TableUtil;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.ResultVO;

import java.util.List;
import java.util.Map;

/**
 * @author chenzy
 * 
 * @since 2020-04-24
 */
@Service
public class CommonServiceImpl implements ICommonService {
    @Auto
    private ICommonDao commonDao;

    @Override
    public ResultVO insert(Map<String, Object> par) {
        String tableName= (String) par.get("tableName");
        par.remove("tableName");
        Integer id = commonDao.insert(par, tableName);
        return new ResultVO(id);
    }

    @Override
    public ResultVO update(Map<String, Object> par) {
        Integer count = commonDao.update((Map<String, Object>)par.get("setPar"),(Map<String, Object>)par.get("wherePar"), (String) par.get("tableName"));
        return new ResultVO(count);
    }

    @Override
    public ResultVO delete(Map<String, Object> par) {
        String tableName= (String) par.get("tableName");
        par.remove("tableName");
        Integer count = commonDao.delete(par, tableName);
        return new ResultVO(count);
    }

    @Override
    public ResultVO get(Map<String, Object> par) {
        String tableName= (String) par.get("tableName");
        par.remove("tableName");
        List<Map<String, Object>> mapList= commonDao.getList(par, tableName);
        return new ResultVO(mapList);
    }

    @Override
    public <Bean> Integer insert(Bean bean) {
        Map<String, Object> par = JsonUtil.model2Model(bean, Map.class);
        Integer id = commonDao.insert(par, TableUtil.getTableName(bean.getClass()));
        return id;
    }

    @Override
    public <Bean> Boolean update(Bean setBean, Bean whereBean) {
        Map<String, Object> wherePar = JsonUtil.model2Model(whereBean, Map.class);
        Map<String, Object> setPar = JsonUtil.model2Model(setBean, Map.class);
        Integer count = commonDao.update(setPar,wherePar,TableUtil.getTableName(setBean.getClass()));
        if (count==null||count.equals(0)){
            return false;
        }
        return true;
    }
    @Override
    public <Bean> Bean getOne(Bean whereBean) {
        return getOnePart(whereBean);
    }
    @Override
    public Map<String, Object> getOneMap(Map<String, Object> wherePar, String tableName) {
        return getOnePartMap(wherePar,tableName);
    }
    @Override
    public <Bean> Bean getOnePart(Bean whereBean, String... columns) {
        Class<Bean> beanClass = (Class<Bean>) whereBean.getClass();
        Map<String, Object> wherePar = JsonUtil.model2Model(whereBean, Map.class);
        String tableName =TableUtil.getTableName(beanClass);
        Map<String, Object> beanMap =columns==null||columns.length==0?commonDao.getOne(wherePar,tableName)
                :commonDao.getOnePart(columns,wherePar,tableName);
        if (beanMap==null||beanMap.isEmpty()){
            return null;
        }
        return JsonUtil.map2Model(beanMap,beanClass);
    }
    @Override
    public Map<String, Object> getOnePartMap(Map<String, Object> wherePar, String tableName, String... columns) {
        return columns==null||columns.length==0?commonDao.getOne(wherePar,tableName):
                commonDao.getOnePart(columns,wherePar,tableName);
    }
    @Override
    public <Bean> List<Bean> getListBean(Bean whereBean, String... columns) {
        Class<Bean> beanClass = (Class<Bean>) whereBean.getClass();
        Map<String, Object> wherePar = JsonUtil.model2Model(whereBean, Map.class);
        String tableName =TableUtil.getTableName(beanClass);
        List<Map<String, Object>> beanMapList =columns==null||columns.length==0?commonDao.getList(wherePar,tableName)
                :commonDao.getListPart(columns,wherePar,tableName);
        if (beanMapList==null||beanMapList.isEmpty()){
            return null;
        }
        return JsonUtil.model2Model(beanMapList,List.class,beanClass);
    }
    @Override
    public List<Map<String, Object>> getListMap(Map<String, Object> wherePar, String tableName, String... columns) {
        return columns==null||columns.length==0?commonDao.getList(wherePar,tableName)
                :commonDao.getListPart(columns,wherePar,tableName);
    }
    @Override
    public <Bean> Boolean delete(Bean bean) {
        Map<String, Object> wherePar = JsonUtil.model2Model(bean, Map.class);
        Integer count = commonDao.delete(wherePar, TableUtil.getTableName(bean.getClass()));
        if (count==null||count.equals(0)){
            return false;
        }
        return true;
    }
    @Override
    public <Bean> Boolean delete(Map<String, Object> wherePar, String tableName) {
        Integer count = commonDao.delete(wherePar,tableName);
        if (count==null||count.equals(0)){
            return false;
        }
        return true;
    }
}
