package com.czy.frame.cash.service.impl;

import com.czy.frame.cash.service.IRedisService;
import com.czy.frame.core.annotation.Auto;
import com.czy.frame.core.annotation.Service;
import com.czy.frame.util.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author chenzy
 * @description
 * @since 2020-04-02
 */
@Service
public class RedisServiceImpl implements IRedisService {
    private Logger logger =LoggerFactory.getLogger(RedisServiceImpl.class);
    @Auto
    private JedisPool jedisPool;

    /**
     * 将数据写入内存
     * @param key
     * @param val
     */
    @Override
    public Boolean set(String key, String val) {
        Jedis jedis = getJedis();
        jedis.set(key, val);
        jedis.close();
        return true;
    }

    @Override
    public Boolean set(String key, Object val) {
        return set(key,val.toString());
    }
    @Override
    public Boolean set(String key, String val, Integer timeout) {
        Jedis jedis = getJedis();
        jedis.set(key, val);
        //设置过期时间，单位是秒
        jedis.expire(key,timeout);
        jedis.close();
        return true;
    }

    @Override
    public Boolean set(String key, Object val, Integer timeout) {
        return set(key,val.toString(),timeout);
    }

    /**
     * 获取数据
     *
     * @param key
     */
    @Override
    public String get(String key) {
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }
    /**
     * 获取数据
     *
     * @param key
     */
    @Override
    public <T> T get(String key, Class<T> tClass) {
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        jedis.close();
        return JsonUtil.str2Model(value, tClass);
    }

    /**
     * 删除key，删除内存中的数据
     *
     * @param key
     */
    @Override
    public Boolean del(String key) {
        Jedis jedis = getJedis();
        jedis.del(key);
        jedis.close();
        return true;
    }


    /**
     * 将内存数据写入磁盘，redis服务恢复时或者重启自动加载。应用程序暂时没有必要调用
     */
    private void writeToDB() {
        Jedis jedis = getJedis();
        jedis.save();
        jedis.close();
    }

    private Jedis getJedis(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (!jedis.isConnected()){
                jedis.connect();
            }
            return jedis;
        }catch (JedisConnectionException e){
            logger.error("获取redis连接失败，继续获取",e);
            if (jedis!=null){
                jedis.close();
            }
            return getJedis();
        }
    }
}
