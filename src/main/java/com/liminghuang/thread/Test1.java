package com.liminghuang.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.thread
 * Description: 共享资源临界操作加锁保证线程安全
 * <p>
 * CreateTime: 2017/7/9 18:24
 * Modifier: Adaministrator
 * ModifyTime: 2017/7/9 18:24
 * Comment:
 *
 * @author Adaministrator
 */
public class Test1 {
    // 公平锁会按开启顺序进行锁分配，此处非公平锁
    private ReentrantLock lock = new ReentrantLock(false);
    private int data = 100;
    
    private void increment() {
        lock.lock();
        try {
            data++;
            System.out.println(Thread.currentThread() + " 自增1，data=" + data);
        } catch (Exception e) {
        
        } finally {
            lock.unlock();
        }
    }
    
    private void decrement() {
        lock.lock();
        try {
            data--;
            System.out.println(Thread.currentThread() + " 自减1，data=" + data);
        } catch (Exception e) {
        
        } finally {
            lock.unlock();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        final Test1 test1 = new Test1();
        int k = 100;
        int i = 0;
        int j = 0;
        while (k > 0) {
            System.out.println("k = " + k);
            if (k % 2 == 0) {
                new Thread(new Runnable() {
                    public void run() {
                        test1.increment();
                    }
                }, "inThread-" + i).start();
                i++;
            } else {
                new Thread(new Runnable() {
                    public void run() {
                        test1.decrement();
                    }
                }, "deThread-" + j).start();
                j++;
            }
            
            k--;
        }
        
        Thread.sleep(3000);
        System.out.println("now data, data=" + test1.data);
    }
}
