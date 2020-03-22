package com.liminghuang.thread.join;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.thread.join
 * Description:
 * <p>
 * CreateTime: 2020/3/21 20:42
 * Modifier: Adaministrator
 * ModifyTime: 2020/3/21 20:42
 * Comment:
 *
 * @author Adaministrator
 */
public class Test {
    public static class SerializableThread extends Thread {
        int i;
        // 上一个线程
        Thread preThread;
        
        public SerializableThread(Thread previousThread, int i) {
            super("SerializableThread" + i);
            this.preThread = previousThread;
            this.i = i;
        }
        
        @Override
        public void run() {
            // System.out.println("Thread run: " + Thread.currentThread());
            try {
                // 等preThread执行完毕
                preThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread: " + Thread.currentThread() + ", num:" + i);
        }
        
    }
    
    public static void main(String[] args) {
        Thread previousThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            System.out.println("i = " + i);
            SerializableThread serializableThread = new SerializableThread(previousThread, i);
            serializableThread.start();
            // 1. 主线程和线程SerializableThread0互相等待，进入WAITING，不再创建和运行后续的线程
            // 2. 如果SerializableThread内部try-catch逻辑注释掉，则创建线程并会按序执行
            try {
                serializableThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            previousThread = serializableThread;
        }
        System.out.println("主线程执行完毕");
    }
}
