package com.whyuan.$6utils.cache;

import org.apache.hadoop.hbase.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisCache implements Cache<JedisPool> {

    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    @Override
    public void addCache(JedisPool redisPool, Map<String, Map<String, Object>> rows,
                         TimeUnit unit, long expire)
            throws CacheException {
        Jedis jedis = redisPool.getResource();
        Pipeline p = jedis.pipelined();

        rows.entrySet().forEach($en -> {
            String key = $en.getKey();
            Map<String, Object> map = $en.getValue();
            Map<String, String> hash = new HashMap<>();
            map.entrySet().forEach(en -> hash.put(en.getKey(), String.valueOf(en.getValue())));
            p.hmset(key, hash);
            p.expire(key, (int) unit.toMillis(expire));
        });

        p.sync();
        jedis.close();
    }

    @Override
    public Map<String, Map<String, Object>> getCache(JedisPool redisPool, List<String> keys, String fl)
            throws CacheException {

        Map<String, Map<String, Object>> result = new HashMap<>();

        Jedis jedis = redisPool.getResource();
        Pipeline p = jedis.pipelined();
        final Map<String, Response<Map<String, String>>> redisDocRes = new HashMap<>();
        keys.forEach(key -> redisDocRes.put(key, p.hgetAll(key)));
        p.sync();
        jedis.close();

        redisDocRes.entrySet().forEach($en -> {
            Map<String, Object> rs = new HashMap<>();
            String key = $en.getKey();
            Response<Map<String, String>> rMap = $en.getValue();
            if (rMap != null && !rMap.get().isEmpty()) {
                if (!Strings.isEmpty(fl)) { // process fl
                    String[] $$ = fl.split("\\s*,\\s*");
                    for (String $ : $$)
                        rs.put($, rMap.get().get($));
                } else {
                    Map<String, Object> rrs = new HashMap<>();
                    rMap.get().keySet().forEach($ -> rrs.put($, rMap.get().get($)));
                    rs = rrs;
                }
            }
            result.put(key, rs);
        });

        return result;
    }

    @Override
    public void delCache(JedisPool redisPool, List<String> keys)
            throws CacheException {
        Jedis jedis = redisPool.getResource();
        Pipeline p = jedis.pipelined();
        keys.forEach(key -> p.hdel(key));
        p.sync();
        jedis.close();

    }
}
