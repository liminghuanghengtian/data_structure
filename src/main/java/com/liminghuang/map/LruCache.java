package com.liminghuang.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.map
 * Description: 基于LinkedHashMap实现LruCache算法. 主要涉及以下两点修改：
 * <p>1. accessOrder需要设置为true，开启访问排序功能.</p>
 * <p>2. 指定缓存容量，并覆写removeEldestEntry方法，在容量满足时返回true，开启移除eldestNode的功能.</p>
 * <p>
 * CreateTime: 2020/4/17 16:25
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/17 16:25
 * Comment:
 *
 * @author Adaministrator
 */
public class LruCache {
    private static final int MAX_SIZE = 4;
    private LruLinkedHashMap<String, String> mInternalCache = new LruLinkedHashMap<String, String>(8, 0.75f);
    
    private static class LruLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
        LruLinkedHashMap(int initialCapacity,
                         float loadFactor) {
            // 这里是第一点
            super(initialCapacity, loadFactor, true);
        }
        
        /**
         * 这里是第二点
         *
         * @param eldest
         * @return
         */
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            boolean ret = size() > MAX_SIZE;
            if (ret) {
                System.out.println("removeEldestEntry: " + eldest.getKey() + "-" + eldest.getValue());
            }
            return ret;
        }
    }
    
    public void print(String action) {
        System.out.println("----start----");
        System.out.println(action);
        // hasNext一开始next是执行头节点的，调用了next的方法后把这个头节点取出，然后要指向下一个节点
        // for (Iterator<Map.Entry<String, String>> iterator = mInternalCache.entrySet().iterator(); iterator.hasNext
        //         (); ) {
        //     Map.Entry<String, String> entry = iterator.next();
        //     System.out.println("entry: " + entry.getKey() + "=" + entry.getValue());
        // }
        mInternalCache.forEach((key, value) -> {
            System.out.println("entry: " + key + "=" + value);
        });
        System.out.println("----end----\n");
    }
    
    public void printBySet() {
        for (Map.Entry<String, String> entry : mInternalCache.entrySet()) {
            System.out.println("entry: " + entry.getKey() + "=" + entry.getValue());
        }
        System.out.println("----end----\n");
    }
    
    public String get(String key) {
        return mInternalCache.getOrDefault(key, "");
    }
    
    public void put(String key, String value) {
        mInternalCache.put(key, value);
    }
    
    public Map<String, String> getCache() {
        return mInternalCache;
    }
    
    public static void main(String[] params) {
        LruCache cache = new LruCache();
        cache.put("1", "语文");
        cache.put("2", "数学");
        cache.put("3", "科学");
        cache.print("输出");
        
        cache.put("4", "英语");
        cache.print("新增4-英语");
        
        // get一下
        cache.get("3");
        cache.print("查找3");
        
        cache.put("2", "新课（替换数学）");
        cache.print("重置2");
        
        cache.put("4", "新课（替换英语）");
        cache.print("重置4");
        
        // 再get一下
        cache.get("2");
        cache.print("查找2");
        
        // 再get一下
        cache.get("3");
        cache.print("查找3");
        
        cache.put("5", "体育");
        cache.print("新增5-体育");
        
        
        // cache.getCache().put("4", "英语");
        // cache.print();
        //
        // cache.getCache().put("5", "体育");
        // cache.print();
        //
        // cache.getCache().put("6", "思想");
        // cache.print();
        //
        // cache.getCache().put("7", "政治");
        // cache.print();
        //
        // cache.getCache().put("8", "化学");
        // cache.print();
        //
        // cache.getCache().put("9", "物理");
        // cache.print();
        
    }
}
