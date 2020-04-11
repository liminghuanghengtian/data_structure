package com.liminghuang.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.queue
 * Description: 简易生产者-消费者案例
 * <p>
 * CreateTime: 2020/4/11 17:25
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/11 17:25
 * Comment:
 *
 * @author Adaministrator
 */
public class HBlockingQueue {
    private static final String TAG = "HBlockingQueue";
    private static final Logger log = Logger.getLogger(TAG);
    private static BlockingQueue<Integer> dataQueue = new ArrayBlockingQueue<Integer>(5, true);
    
    public static void main(String[] args) {
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("consumer run...");
                Integer data = null;
                try {
                    while ((data = dataQueue.take()) != null) {
                        log.info("consume data: " + data);
                    }
                } catch (InterruptedException e) {
                    log.warning("consumer 中断, data: " + data);
                    e.printStackTrace();
                }
                log.info("consumer terminated!!!");
            }
        }, "consumer-t");
        consumer.start();
        
        try {
            log.info("主线程睡眠3s后开始生产数据");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("producer run...");
                for (int i = 0; i < 200; i++) {
                    log.info("ready to put data: " + i);
                    try {
                        dataQueue.put(i);
                        log.info("put " + i + " success");
                    } catch (InterruptedException e) {
                        log.warning("producer 中断, i: " + i);
                        e.printStackTrace();
                        break;
                    }
                }
                log.info("producer terminated!!!");
            }
        }, "producer-t");
        producer.start();
        
        
        try {
            log.info("10ms后中断生产者producer");
            TimeUnit.MILLISECONDS.sleep(10);
            producer.interrupt();
            
            log.info("10ms后中断消费者consumer");
            TimeUnit.MILLISECONDS.sleep(10);
            consumer.interrupt();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
