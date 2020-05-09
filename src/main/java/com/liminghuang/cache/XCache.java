package com.liminghuang.cache;

import java.util.LinkedHashMap;

import sun.misc.LRUCache;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.cache
 * Description: Cache的简单实现
 * <p>
 * CreateTime: 2017/7/25 21:01
 * Modifier: Adaministrator
 * ModifyTime: 2017/7/25 21:01
 * Comment:
 *
 * @author Adaministrator
 */
public class XCache<T> implements ICache<T> {
    
    transient LinkedHashMap<Object, T> cacheMap;
    
    public XCache() {
        cacheMap = new LinkedHashMap<>();
    }
    
    public void put(Object key, T t) {
        if (key != null) {
            cacheMap.put(key, t);
        }
    }
    
    public T get(Object key) {
        T t;
        if (key != null) {
            return (t = cacheMap.get(key)) == null ? null : t;
        }
        return null;
    }
    
    public T remove(Object key) {
        T t;
        if (key != null) {
            return (t = cacheMap.remove(key)) == null ? null : t;
        }
        return null;
    }
}
