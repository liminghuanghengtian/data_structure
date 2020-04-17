package com.liminghuang.thread.join;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.thread.join
 * Description: wait释放锁，sleep不释放锁
 * <p>
 * CreateTime: 2020/3/22 14:36
 * Modifier: Adaministrator
 * ModifyTime: 2020/3/22 14:36
 * Comment:
 *
 * @author Adaministrator
 */
public class Test1 {
    
    public static class WaitThread extends Thread {
        private final Object lock;
        
        public WaitThread(Object lock, String name) {
            super(name);
            this.lock = lock;
        }
        
        @Override
        public void run() {
            // 每个线程都能及时获得锁，因为其他线程wait时就会释放
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread() + " run");
                    // 释放锁
                    lock.wait();
                    System.out.println(Thread.currentThread() + " resume execute");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        final Object obj = new Byte[1];
        for (int i = 0; i < 10; i++) {
            Thread thread = new WaitThread(obj, "Thread-" + i);
            thread.start();
            // if (i == 5) {
            //     try {
            //         System.out.println("等待" + thread.getName() + "执行完成");
            //         thread.join();
            //     } catch (InterruptedException e) {
            //         e.printStackTrace();
            //     }
            // }
        }
        
        try {
            System.out.println("sleep 3s");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        synchronized (obj) {
            System.out.println("开始唤醒其他等待线程");
            obj.notifyAll();
            System.out.println("主线程执行完毕");
        }
    }
}
