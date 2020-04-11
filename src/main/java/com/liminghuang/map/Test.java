package com.liminghuang.map;

import java.util.HashMap;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.map
 * Description: HashMap并发问题（扩容并进行rehash的时候，在数组的某个位置上形成了环形列表，刚好取值在这个位置时造成无限循环）
 * <p>
 * CreateTime: 2020/3/21 21:08
 * Modifier: Adaministrator
 * ModifyTime: 2020/3/21 21:08
 * Comment:
 *
 * @author Adaministrator
 */
public class Test {
    
    public static void main(String[] args) throws InterruptedException {
        HashMap<Integer, String> data = new HashMap<Integer, String>(8);
        for (int j = 0; j < 100; j++) {
            int finalJ = j;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    data.put(finalJ, "data-" + finalJ);
                }
            }, "Thread-" + j).start();
        }
        
        System.out.println(data.size());
        System.out.println(data.keySet());// ConcurrentModificationException异常
        System.out.println(data.values());
        Thread.sleep(3000);
        System.out.println(data.size());
        System.out.println(data.keySet());
        System.out.println(data.values());
    }
}
