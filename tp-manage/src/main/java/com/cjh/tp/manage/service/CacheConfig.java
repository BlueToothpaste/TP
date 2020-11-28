package com.cjh.tp.manage.service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: tp
 * @description: 配置中心缓存，模拟缓存用
 * @author: chenjiehan
 * @create: 2020-11-27 13:19
 **/
public class CacheConfig {

    private CacheConfig() {

    }

    public static CacheConfig getInstance() {
        return LazyHolder.INSTANCE;
    }


    private static class LazyHolder {
        private static final CacheConfig INSTANCE = new CacheConfig();
    }

    static final ConcurrentHashMap<String, String> configCache = new ConcurrentHashMap<>();

    static {
        configCache.put("hello", "world");
    }


    public Boolean saveConfig(String key, String value) {
        configCache.put(key, value);
        return true;
    }

    public String getConfig(String key) {
        return configCache.get(key);
    }

    public Object removeConfig(String key) {
        return configCache.remove(key);
    }


}
