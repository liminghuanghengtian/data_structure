package com.liminghuang.thread.latch;

import java.util.concurrent.CountDownLatch;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.thread.latch
 * Description:
 * <p>
 * CreateTime: 2020/4/19 20:13
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/19 20:13
 * Comment:
 *
 * @author Adaministrator
 */
public class CountDownLatchDemo {
    private static final int COUNT = 10;
    
    public static void main(String[] args) {
        CountDownLatch shareRes = new CountDownLatch(COUNT);
        for (int i = 0; i < COUNT; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (shareRes) {
                            /*** 每次减少一个容量*/
                            System.out.println("Work-" + finalI + " start work");
                            shareRes.countDown();
                            // System.out.println(Thread.currentThread() + " remain counts = " + (shareRes.getCount()));
                        }
                        
                        System.out.println(Thread.currentThread() + " 已完成，等待大伙儿一起下班");
                        shareRes.await();
                        System.out.println(Thread.currentThread() + " 下班下班下班咯");
                    } catch (Exception e) {
                    }
                }
            }, "WorkThread-" + i).start();
        }
        
        System.out.println("老板开始监工，等待员工完成工作");
        try {
            shareRes.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("活干完了，监工结束");
    }
}
