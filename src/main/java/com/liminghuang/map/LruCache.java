package com.liminghuang.map;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.map
 * Description: 基于LinkedHashMap实现LruCache算法
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
            super(initialCapacity, loadFactor, true);
        }
        
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            boolean ret = size() == MAX_SIZE;
            if (ret) {
                System.out.println("removeEldestEntry: " + eldest.getKey() + "-" + eldest.getValue());
            }
            return ret;
        }
    }
    
    public void print(String action) {
        System.out.println("----start----");
        System.out.println(action);
        for (Iterator<Map.Entry<String, String>> iterator = mInternalCache.entrySet().iterator(); iterator.hasNext
                (); ) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("entry: " + entry.getKey() + "=" + entry.getValue());
        }
        System.out.println("----end----\n");
    }
    
    public void printBySet() {
        for (Map.Entry<String, String> entry : mInternalCache.entrySet()) {
            System.out.println("entry: " + entry.getKey() + "=" + entry.getValue());
        }
        System.out.println("----end----\n");
    }
    
    public Map<String, String> getCache() {
        return mInternalCache;
    }
    
    public static void main(String[] params) {
        LruCache cache = new LruCache();
        cache.getCache().put("1", "语文");
        cache.getCache().put("2", "数学");
        cache.getCache().put("3", "科学");
        cache.print("输出");
        
        cache.getCache().put("4", "英语");
        cache.print("新增4-英语");
        
        // get一下
        cache.getCache().get("3");
        cache.print("查找3");
        
        cache.getCache().put("2", "新课（替换数学）");
        cache.print("重置2");
    
        cache.getCache().put("4", "新课（替换英语）");
        cache.print("重置4");
        
        // 再get一下
        cache.getCache().get("2");
        cache.print("查找2");
        
        cache.getCache().put("5", "体育");
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
