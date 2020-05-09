package com.liminghuang.cache;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.cache
 * Description: 缓存抽象
 * <p>
 * CreateTime: 2017/7/25 20:59
 * Modifier: Adaministrator
 * ModifyTime: 2017/7/25 20:59
 * Comment:
 *
 * @author Adaministrator
 */
public interface ICache<T> {
    
    void put(Object key, T t);
    
    T get(Object key);
    
    T remove(Object key);
}
