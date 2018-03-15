package com.whyuan.$6utils.cache;

import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HBaseCache implements Cache<Table> {

    private static final Logger logger = LoggerFactory.getLogger(HBaseCache.class);
    public static final byte[] FC = new byte[]{'c'};

    @Override
    public final void addCache(Table table, Map<String, Map<String, Object>> rows,
                               TimeUnit unit, long expire)
            throws CacheException {


        List<Put> puts = new ArrayList<>();
        rows.entrySet().forEach(row -> {
            Put put = row2put(row);
            puts.add(put);
        });
        try {
            table.put(puts);
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            close(table);
        }

    }

    public final void addCache(Table table, Map<String, Map<String, Object>> rows)
            throws CacheException {
        addCache(table, rows, null, 0L);
    }


    private Put row2put(Map.Entry<String, Map<String, Object>> row) {
        long current = System.currentTimeMillis();
        Put put = new Put(Bytes.toBytes(row.getKey()));
        Map<String, Object> map = row.getValue();
        map.forEach((k,v)->put.addColumn(FC, Bytes.toBytes(k), current, Bytes.toBytes(String.valueOf(v))));
//        map.entrySet().forEach(en -> put.addColumn(FC, Bytes.toBytes(en.getKey()), current, Bytes.toBytes(String.valueOf(en.getValue()))));
        return put;
    }

    @Override
    public Map<String, Map<String, Object>> getCache(Table table, List<String> keys, String fl)
            throws CacheException {
        Map<String, Map<String, Object>> result = null;

        List<Get> gets = new ArrayList<>();
        keys.forEach($key -> {
            Get get = new Get(Bytes.toBytes($key));
            if (!Strings.isEmpty(fl)) {
                String[] $$ = fl.split("\\s*,\\s*");
                for (String $ : $$) {
                    get.addColumn(FC, Bytes.toBytes($));
                }
            }
            gets.add(get);
        });

        try {
            Result[] results = table.get(gets);
            result = parse(results);
        } catch (IOException e) {
            logger.info("", e);
            throw new CacheException(e);
        } finally {
            close(table);
        }

        return result;
    }


    @Override
    public final void delCache(Table table, List<String> keys)
            throws CacheException {
        List<Delete> deletes = new ArrayList<>();

        keys.forEach($key -> {
            Delete delete = new Delete(Bytes.toBytes($key));
            deletes.add(delete);
        });
        try {
            table.delete(deletes);
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            close(table);
        }
    }

    private void close(Table table) {
        try {
            table.close();
        } catch (IOException e) {
            logger.info("", e);
        }
    }

    private Map<String, Map<String, Object>> parse(Result[] results) {
        Map<String, Map<String, Object>> rs = new HashMap<>();
        for (Result result : results) {
            final String key = Bytes.toString(result.getRow());
            Map<String, Object> hash = new HashMap<>();
            NavigableMap<byte[], byte[]> fMap = result.getFamilyMap(FC);
            if (fMap == null) continue;
            fMap.forEach((k,v)-> hash.put(Bytes.toString(k), Bytes.toString(v)));
            //fMap.entrySet().forEach($en -> hash.put(Bytes.toString($en.getKey()), Bytes.toString($en.getValue())));
            rs.put(key, hash);
        }
        return rs;
    }
}
