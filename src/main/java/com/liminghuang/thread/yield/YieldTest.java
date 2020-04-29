package com.liminghuang.thread.yield;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.thread.yield
 * Description: yield会通知线程调度器放弃对处理器的占用，但调度器可以忽视这个通知。语义理解就是线程让出当前时间片给其他线程执行。实现两个线程交替执行
 * {@see <a href="https://blog.csdn.net/qq_15037231/article/details/85765322">Thread.yield()</a>}
 * <p>
 * CreateTime: 2020/4/29 16:48
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/29 16:48
 * Comment:
 *
 * @author Adaministrator
 */

public class YieldTest extends Thread {
    
    public YieldTest(String name) {
        super(name);
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 1000; i++) {
            System.out.println("" + this.getName() + "-----" + i);
            if (i == 30 || i == 100 || i == 500 || i == 700 || i == 800 || i == 900) {
                Thread.yield();
            }
        }
    }
    
    public static void main(String[] args) {
        YieldTest yt1 = new YieldTest("张三");
        YieldTest yt2 = new YieldTest("李四");
        yt1.start();
        yt2.start();
    }
}
