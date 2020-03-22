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
            SerializableThread serializableThread = new SerializableThread(previousThread, i);
            serializableThread.start();
            previousThread = serializableThread;
        }
        System.out.println("主线程执行完毕");
    }
}
