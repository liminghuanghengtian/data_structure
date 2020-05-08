package com.liminghuang.thread.vl;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.thread.vl
 * Description: 1. volatile保证可见性  2. volatile禁止指令重排
 * <p>原理和实现：观察加入volatile关键字和没有加入volatile关键字时所生成的汇编代码发现，加入volatile关键字时，会多出一个lock前缀指令</p>
 * <p>lock前缀指令实际上相当于一个内存屏障（也成内存栅栏），内存屏障会提供3个功能：</p>
 * <p> 1）它确保指令重排序时不会把其后面的指令排到内存屏障之前的位置，也不会把前面的指令排到内存屏障的后面；即在执行到内存屏障这句指令时，在它前面的操作已经全部完成；</p>
 * <p> 2）它会强制将对缓存的修改操作立即写入主存；</p>
 * <p> 3）如果是写操作，它会导致其他CPU中对应的缓存行无效。</p>
 * <p>
 * CreateTime: 2020/5/8 11:13
 * Modifier: Adaministrator
 * ModifyTime: 2020/5/8 11:13
 * Comment:
 *
 * @author Adaministrator
 */
public class VolatileTest {
    public volatile int inc = 0;
    
    public void increase() {
        inc++;
    }
    
    public static void main(String[] args) {
        final VolatileTest test = new VolatileTest();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++)
                        test.increase();
                }
            };
            t.start();
            
            // // 每个线程按序执行
            // try {
            //     t.join();
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
        }
        
        int count;
        // 这个条件没用，总是返回2，无法测试
        while ((count = Thread.activeCount()) > 1) {//保证前面的线程都执行完
            System.out.println("count: " + count);
            Thread.yield();
        }
        
        System.out.println(test.inc);
    }
}
