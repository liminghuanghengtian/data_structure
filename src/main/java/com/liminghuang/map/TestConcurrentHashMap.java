package com.liminghuang.map;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.map
 * Description:
 * <p>
 * CreateTime: 2020/4/29 13:53
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/29 13:53
 * Comment:
 *
 * @author Adaministrator
 */
public class TestConcurrentHashMap {
    public static void main(String[] args) {
        testHash();
    }
    
    public static void test1() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(); //初始化ConcurrentHashMap
        //新增个人信息
        map.put("id", "1");
        map.put("name", "andy");
        map.put("sex", "男");
        //获取姓名
        String name = map.get("name");
        System.out.println(Objects.equals(name, "andy"));
        //计算大小
        int size = map.size();
        System.out.println(Objects.equals(size, 3));
    }
    
    public static void testHash() {
        int hash = spread("12345".hashCode());
        System.out.println("hash -> ox" + Integer.toHexString(hash));
        System.out.println("hash -> " + Integer.toBinaryString(hash));
    }
    
    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
    
    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }
}
