package com.zemasterkrom.vglloadbalancer.configuration;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CacheInstance {

    /**
     * Caffeine cache
     */
    private final Cache<Object,Object> cache;

    public CacheInstance(Long expireAfterAccess, Long expireAfterWrite, Long maximumSize) {
        this.cache = Caffeine.newBuilder().
                expireAfterAccess(expireAfterAccess > 0 ? expireAfterAccess : 1440, TimeUnit.SECONDS).
                expireAfterWrite(expireAfterWrite > 0 ? expireAfterWrite : 1440, TimeUnit.SECONDS).
                maximumSize(maximumSize > 0 ? maximumSize : 60).build();
    }

    public void put(Object key, Object value) {
        this.cache.put(key, value);
    }

    public Object get(Object key) {
        return this.cache.getIfPresent(key);
    }
}
