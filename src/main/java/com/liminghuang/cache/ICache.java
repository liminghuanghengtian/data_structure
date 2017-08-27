package com.liminghuang.cache;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.cache
 * Description:
 * <p>
 * CreateTime: 2017/7/25 20:59
 * Modifier: Adaministrator
 * ModifyTime: 2017/7/25 20:59
 * Comment:
 *
 * @author Adaministrator
 */
public interface ICache<T> {
    
    void put(String key,T t);
    
    T get(String key);
    
    T remove(String key);
}
