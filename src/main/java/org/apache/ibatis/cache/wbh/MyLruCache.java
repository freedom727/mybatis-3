package org.apache.ibatis.cache.wbh;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyLruCache {
    private Map<Object, Object> cache;

    public MyLruCache(int size) {
        // 向上取整，防止扩容
        int capacity = (int) Math.ceil(size / 0.75f) + 1;
        cache = new LinkedHashMap<Object, Object>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
                return size() > size;
            }
        };
    }

    public Object get(Object key) {
        return cache.get(key);
    }

    public void remove(Object key) {
        cache.remove(key);
    }

    public void put(Object key, Object value) {
        cache.put(key, value);
    }
}
