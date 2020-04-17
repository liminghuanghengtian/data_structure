package com.liminghuang.thread;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.thread
 * Description: ThreadLocal演示，内部数据结构探究
 * <p>
 * CreateTime: 2020/4/17 22:46
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/17 22:46
 * Comment:
 *
 * @author Adaministrator
 */
public class TestThreadLocal {
    private static final ThreadLocal<Integer> sThreadLocal = new ThreadLocal<>();
    private static final InheritableThreadLocal<Integer> sInheritThreadLocal = new InheritableThreadLocal<>();
    final ThreadLocal<String> mThreadLocal = new ThreadLocal<>();
    
    public static void main(String[] args) {
        TestThreadLocal demo = new TestThreadLocal();
        demo.mThreadLocal.set("main");
        sThreadLocal.set(1);
        sInheritThreadLocal.set(1);
        for (int i = 0; i < 2; i++) {
            Thread temp = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread() + ", sThreadLocal: " + sThreadLocal.get());
                    System.out.println(Thread.currentThread() + ", sInheritThreadLocal: " + sInheritThreadLocal.get());
                    sThreadLocal.set(2);
                    sInheritThreadLocal.set(2);
                    System.out.println(Thread.currentThread() + ", sThreadLocal: " + sThreadLocal.get());
                    System.out.println(Thread.currentThread() + ", sInheritThreadLocal: " + sInheritThreadLocal.get());
                    
                    System.out.println(Thread.currentThread() + ", demo.mThreadLocal: " + demo.mThreadLocal.get());
                    demo.mThreadLocal.set("name->" + Thread.currentThread().getName());
                    System.out.println(Thread.currentThread() + ", demo.mThreadLocal: " + demo.mThreadLocal.get());
                }
            }, "ThreadLocal-" + i);
            temp.start();
            try {
                temp.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(Thread.currentThread() + ", sThreadLocal: " + sThreadLocal.get());
        System.out.println(Thread.currentThread() + ", sInheritThreadLocal: " + sInheritThreadLocal.get());
        System.out.println(Thread.currentThread() + ", demo.mThreadLocal: " + demo.mThreadLocal.get());
    }
}
