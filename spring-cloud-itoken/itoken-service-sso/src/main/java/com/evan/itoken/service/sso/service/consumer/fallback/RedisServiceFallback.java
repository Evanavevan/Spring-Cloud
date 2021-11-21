package com.evan.itoken.service.sso.service.consumer.fallback;

import com.evan.itoken.common.hystrix.Fallback;
import com.evan.itoken.service.sso.service.consumer.RedisService;
import org.springframework.stereotype.Component;

@Component
public class RedisServiceFallback implements RedisService {

    @Override
    public String put(String key, String value, long seconds) {
        return null;
    }

    @Override
    public String get(String key) {
        return null;
    }
}
