package com.evan.itoken.service.redis.service;

public interface RedisService {
    void set(String key, Object value, long seconds);
    Object get(String key);
}
