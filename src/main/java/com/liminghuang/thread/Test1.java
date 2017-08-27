package com.liminghuang.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.thread
 * Description:
 * <p>
 * CreateTime: 2017/7/9 18:24
 * Modifier: Adaministrator
 * ModifyTime: 2017/7/9 18:24
 * Comment:
 *
 * @author Adaministrator
 */
public class Test1 {
    private ReentrantLock lock = new ReentrantLock(false);
    private int data = 100;
    
    private void increment() {
        // lock.lock();
        try {
            data++;
            System.out.println("自增1，data=" + data);
        } catch (Exception e) {
        
        } finally {
            // lock.unlock();
        }
    }
    
    private void decrement() {
        // lock.lock();
        try {
            data--;
            System.out.println("自减1，data=" + data);
        } catch (Exception e) {
        
        } finally {
            // lock.unlock();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        final Test1 test1 = new Test1();
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                public void run() {
                    test1.increment();
                }
            }).start();
        }
        
        for (int j = 0; j < 10; j++) {
            new Thread(new Runnable() {
                public void run() {
                    test1.decrement();
                }
            }).start();
        }
        
        Thread.sleep(5000);
        System.out.println("now data, data=" + test1.data);
    }
}
