package com.er.paiyipai.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CacheService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final int EXPIRE_TIME = 1;
    private final TimeUnit EXPIRE_TIME_TYPE = TimeUnit.DAYS;

    /**
     * 数据缓存至redis
     *
     * @param key
     * @param value
     * @return
     */
    public <V> void add(String key, V value) {

        try {
            if (value != null) {
                redisTemplate
                        .opsForValue()
                        .set( key, JSON.toJSONString(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据缓存至redis失败");
        }
    }

    /**
     * 数据缓存至redis并设置过期时间
     *
     * @param key
     * @param value
     * @return
     */
    public <V> void add(String key, V value, long timeout, TimeUnit unit) {
        try {
            if (value != null) {
                redisTemplate
                        .opsForValue()
                        .set(key, JSON.toJSONString(value), timeout, unit);
            }
        } catch (Exception e) {

            throw new RuntimeException("数据缓存至redis失败");
        }
    }

    /**
     * 从redis中获取缓存数据，转成对象
     *
     * @param key   must not be {@literal null}.
     * @param clazz 对象类型
     * @return
     */
    public <V> V getObject(String key, Class<V> clazz) {
        String value = this.get(key);
        V result = null;
        if (!StringUtils.isEmpty(value)) {
            result = JSONObject.parseObject(value, clazz);
        }
        return result;
    }

    /**
     * 从redis中获取缓存数据，转成list
     *
     * @param key   must not be {@literal null}.
     * @param clazz 对象类型
     * @return
     */
    public <V> List<V> getList(String key, Class<V> clazz) {
        String value = this.get(key);
        List<V> result = Collections.emptyList();
        if (!StringUtils.isEmpty(value)) {
            result = JSONArray.parseArray(value, clazz);
        }
        return result;
    }

    /**
     * 功能描述：Get the value of {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return java.lang.String
     * @date 2021/9/19
     **/
    public  String get(String key) {
        String value;
        try {
            value = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {

            throw new RuntimeException("从redis缓存中获取缓存数据失败");
        }
        return value;
    }


    /**
     * 写入 hash-set,已经是key-value的键值，不能再写入为hash-set
     *
     * @param key    must not be {@literal null}.
     * @param subKey must not be {@literal null}.
     * @param value  写入的值
     */
    public < SK, V> void addHashCache(String key, SK subKey, V value) {
        redisTemplate.opsForHash().put(key, subKey, value);
    }

    /**
     * 写入 hash-set,并设置过期时间
     *
     * @param key    must not be {@literal null}.
     * @param subKey must not be {@literal null}.
     * @param value  写入的值
     */
    public <SK, V> void addHashCache(String key, SK subKey, V value, long timeout, TimeUnit unit) {
        redisTemplate.opsForHash().put( key, subKey, value);
        redisTemplate.expire( key, timeout, unit);
    }

    /**
     * 获取 hash-setvalue
     *
     * @param key    must not be {@literal null}.
     * @param subKey must not be {@literal null}.
     */
    public <SK> Object getHashCache(String key, SK subKey) {
        return  redisTemplate.opsForHash().get(  key, subKey);
    }
}