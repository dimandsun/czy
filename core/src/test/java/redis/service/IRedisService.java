package redis.service;

/**
 * @author chenzy
 * @since 2020-06-09
 */
public interface IRedisService {
    Boolean set(String key, String val);
    Boolean set(String key, Object val);
    Boolean set(String key, String val, Integer timeout);
    Boolean set(String key, Object val, Integer timeout);
    String get(String key);
    <T> T get(String key, Class<T> tClass);
    Boolean del(String key);


}
