package redis.service.impl;

import com.czy.core.annotation.Auto;
import com.czy.core.annotation.bean.Service;
import redis.service.IRedisService;
import com.czy.util.text.StringUtil;
import com.czy.util.json.JsonUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author chenzy
 * @since 2020-06-09
 */
@Service
public class RedisServiceImpl implements IRedisService {
    @Auto
    private JedisPool jedisPool;

    @Override
    public Boolean set(String key, String val) {
        return set(key,JsonUtil.model2Str(val));
    }

    @Override
    public Boolean set(String key, Object val) {
        return set(key,JsonUtil.model2Str(val),null);
    }

    @Override
    public Boolean set(String key, String val, Integer timeout) {
        Jedis jedis = getJedis(1);
        if (jedis==null){
            return false;
        }
        jedis.set(key, val);
        //设置过期时间，单位是秒
        if (timeout!=null){
            jedis.expire(key,timeout);
        }
        jedis.close();
        return true;
    }

    @Override
    public Boolean set(String key, Object val, Integer timeout) {
            return set(key,JsonUtil.model2Str(val),timeout);
    }

    /**
     * 将数据以jdk序列化后写入内存
     * @param key
     * @param val
     */
    private Boolean setJDKSerialize(String key, String val, Integer timeout) {
        Jedis jedis = getJedis(1);
        if (jedis==null){
            return false;
        }
        byte[] bv =StringUtil.serializeJDK(val);
        jedis.set(key.getBytes(), bv);
        //设置过期时间，单位是秒
        if (timeout!=null){
            jedis.expire(key,timeout);
        }
        jedis.close();
        return true;
    }
    /**
     * 获取数据
     *
     * @param key
     */
    @Override
    public String get(String key) {
        if (StringUtil.isBlank(key)){
            return null;
        }
        Jedis jedis = getJedis(1);
        if (jedis==null){
            return null;
        }
        String result =StringUtil.derializerJDK(jedis.get(key.getBytes()));
        jedis.close();
        return result;
    }

    /**
     * 获取数据
     *
     * @param key
     */
    @Override
    public <T> T get(String key, Class<T> tClass) {
        String value = get(key);
        return JsonUtil.str2Model(value, tClass);
    }

    /**
     * 删除key，删除内存中的数据
     * @param key
     * @return
     */
    @Override
    public Boolean del(String key) {
        Jedis jedis = getJedis(1);
        if (jedis==null){
            return null;
        }
        jedis.del(key);
        jedis.close();
        return true;
    }
    /**
     * 将内存数据写入磁盘，redis服务恢复时或者重启自动加载。应用程序暂时没有必要调用
     */
    private void writeToDB() {
        Jedis jedis = getJedis(1);
        if (jedis==null){
            return ;
        }
        jedis.save();
        jedis.close();
    }
    /**
     * 获取Jedis，会多次尝试，若获取不到Jedis，则返回null。后续代码会报空指针异常
     * @return
     */
    private Jedis getJedis(Integer i){
        if (i>10){
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis!=null&&!jedis.isConnected()){
                jedis.connect();
            }
            return jedis;
        }catch (JedisConnectionException e){
//            jedisPool.error("第"+i+"次获取redis连接失败，继续获取",e.getMessage());
            System.out.println("第"+i+"次获取redis连接失败，继续获取");
            e.printStackTrace();
            if (jedis!=null){
                jedis.close();
            }
            return getJedis(i++);
        }
    }
}
