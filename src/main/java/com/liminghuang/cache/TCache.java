package com.liminghuang.cache;

import sun.misc.LRUCache;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.cache
 * Description: 基于LRU算法的缓存实现
 * <p>
 * CreateTime: 2017/10/22 14:18
 * Modifier: Adaministrator
 * ModifyTime: 2017/10/22 14:18
 * Comment:
 *
 * @author Adaministrator
 */
public class TCache<T> implements ICache<T> {
    transient LRUCache<String, Object> cache;
    
    public TCache() {
        cache = new MyLRUCache<>(16);
    }
    
    @Override
    public void put(String key, T t) {
    }
    
    @Override
    public T get(String key) {
        return null;
    }
    
    @Override
    public T remove(String key) {
        return null;
        
    }
    
    static class MyLRUCache<N, V> extends LRUCache<N, V> {
        
        MyLRUCache(int size) {
            super(size);
        }
        
        @Override
        protected V create(N n) {
            return forName(n);
        }
        
        @Override
        protected boolean hasName(V v, N n) {
            return false;
        }
    }
}
