package com.whyuan.$6utils.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface Cache<T> {

    void addCache(T t, Map<String, Map<String, Object>> rows, TimeUnit unit, long expire) throws CacheException;

    Map<String, Map<String, Object>> getCache(T t, List<String> keys, String fl) throws CacheException;

    void delCache(T t, List<String> keys) throws CacheException;

}
