package com.liminghuang.thread;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.thread
 * Description:
 * <p>
 * CreateTime: 2017/7/10 20:56
 * Modifier: Adaministrator
 * ModifyTime: 2017/7/10 20:56
 * Comment:
 *
 * @author Adaministrator
 */
public class Test2 {
    private static Test2 test2 = new Test2();
    private int data = 100;
    
    private void change(String arg) {
        synchronized (arg.intern()) {
            for (int j = 0; j < 3; j++) {
                System.out.println(Thread.currentThread().getName() + ", data++=" + data++);
            }
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + ", data--=" + data--);
            }
        }
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    test2.change("test2");
                }
            }).start();
        }
    }
}

