package com.liminghuang.thread.tl;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.thread
 * Description: 线程读取自己甚至的内容
 * <p>
 * CreateTime: 2017/7/13 22:29
 * Modifier: Adaministrator
 * ModifyTime: 2017/7/13 22:29
 * Comment:
 *
 * @author Adaministrator
 */
public class Test3 {
    private static Test3 test = new Test3();
    private ThreadLocal<Integer> dataInt = new ThreadLocal<Integer>();
    private ThreadLocal<String> dataStr = new ThreadLocal<String>();
    private int dataj;
    
    private void read() {
        System.out.println(Thread.currentThread() + " " + dataInt.get() + " " + dataStr.get() + " " + dataj);
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                public void run() {
                    test.dataInt.set(finalI);
                    test.dataStr.set("我是：" + finalI);
                    test.dataj = finalI;
                    test.read();
                }
            }).start();
        }
    }
}
