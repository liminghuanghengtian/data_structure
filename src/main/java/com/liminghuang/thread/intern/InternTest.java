package com.liminghuang.thread.intern;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.thread
 * Description: intern常量池锁练习
 * <p>
 * CreateTime: 2017/7/10 20:56
 * Modifier: Adaministrator
 * ModifyTime: 2017/7/10 20:56
 * Comment:
 *
 * @author Adaministrator
 */
public class InternTest {
    private static InternTest test2 = new InternTest();
    private int data = 100;
    
    private void change(String arg) {
        // intern返回常量池中的同一个对象，只要锁住同一个对象的话，就能防止并发，保持同步
        synchronized (arg.intern()) {
            for (int j = 0; j < 100; j++) {
                System.out.println(Thread.currentThread().getName() + ", data++=" + data++);
            }
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ", data--=" + data--);
            }
        }
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                public void run() {
                    if (finalI == 0) {
                        test2.change(String.valueOf(100));
                    } else if (finalI == 1) {
                        test2.change(String.valueOf(100));
                    } else {
                        test2.change(String.valueOf(100));
                    }
                }
            }).start();
        }
    }
}

