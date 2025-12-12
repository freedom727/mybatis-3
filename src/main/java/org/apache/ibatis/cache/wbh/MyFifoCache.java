package org.apache.ibatis.cache.wbh;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyFifoCache<K, V> {

    private Map<K, V> cache;

    public MyFifoCache(int size) {
        cache = new LinkedHashMap<K, V>(size, 0.75f) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > size;
            }
        };
    }
}
