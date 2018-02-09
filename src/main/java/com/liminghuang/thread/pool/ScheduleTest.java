package com.liminghuang.thread.pool;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.thread.pool
 * Description:
 * <p>
 * CreateTime: 2018/2/9 22:04
 * Modifier: Adaministrator
 * ModifyTime: 2018/2/9 22:04
 * Comment:
 *
 * @author Adaministrator
 */
public class ScheduleTest {
    public static final String TAG = "ScheduleTest";
    private final Logger logger = Logger.getLogger(TAG);
    
    private ScheduledExecutorService mScheduleExecutorService = new ScheduledThreadPoolExecutor(4,
            new ThreadFactory() {
                private AtomicInteger seq = new AtomicInteger(1);
                
                @Override
                public Thread newThread(Runnable r) {
                    int i = seq.getAndIncrement();
                    return new Thread(r, "Schedule-Executor-Pool-Thread-" + i);
                }
            });
    /** 轮询等候人数定时任务 */
    private Runnable mGetRoomWaiterTask;
    private static final long AWAIT_TIME = 1000L;
    private static final long SLEEP_TIME = 2000L;
    /** 轮询等候人数的定时任务 */
    private ScheduledFuture<?> mRoomWaiterFuture;
    
    /**
     * 查询候诊人数任务
     */
    private final class GetRoomWaiterRunnable implements Runnable {
        private String name = "GetRoomWaiterRunnable";
        
        GetRoomWaiterRunnable(String name) {
            this.name = name;
        }
        
        @Override
        public void run() {
            logger.info(Thread.currentThread().getName() + " [" + name + "] is Running !");
            // 执行抛异常的话将导致周期性失效，不再执行
            // try {
            
            for (int i = 0; i < 100000; i++) {
                if (!Thread.interrupted()) {
                    if (i < 15 || i > 99985) {
                        logger.info(System.currentTimeMillis() + " -> " + i);
                    }
                } else {
                    logger.info(Thread.currentThread().getName() + " already interrupted!");
                    break;
                }
            }
            
            // 提供给shutdownNow方法的作用是向所有执行中的线程发出interrupted以中止线程的运行
            //TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            // } catch (InterruptedException e) {
            //     logger.info("interrupted: " + e.getMessage());
            // }
        }
    }
    
    private void cancelRoomWaiterTask() {
        // 取消轮询候诊人数的任务
        if (mRoomWaiterFuture != null) {
            boolean isCancelled = mRoomWaiterFuture.isCancelled();
            logger.info("mRoomWaiterFuture#isCancelled: " + isCancelled);
            if (!isCancelled) {
                logger.info("取消轮询候诊人数定时任务");
                boolean isCancelSucceed = mRoomWaiterFuture.cancel(true);
                if (!isCancelSucceed) {
                    logger.info("取消轮询候诊人数定时任务失败，因为任务已执行完成");
                }
            }
        }
        // 关停线程池
        if (mScheduleExecutorService != null && !mScheduleExecutorService.isShutdown()) {
            logger.info("准备关停线程池");
            try {
                // 中断没有在执行的任务
                mScheduleExecutorService.shutdown();
                
                // (所有的任务都结束的时候，返回TRUE)
                if (!mScheduleExecutorService.awaitTermination(AWAIT_TIME, TimeUnit.MILLISECONDS)) {
                    logger.info("线程池仍有任务未结束（超时），直接关停线程抛出InterruptedException");
                    // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                    List<Runnable> list = mScheduleExecutorService.shutdownNow();
                    logger.info("超时任务数量：" + list.size());
                    StringBuilder result = new StringBuilder();
                    for (Runnable runnable : list) {
                        if (runnable instanceof GetRoomWaiterRunnable) {
                            result.append(((GetRoomWaiterRunnable) runnable).name).append(" | ");
                        }
                        if (runnable instanceof TimeTask) {
                            result.append(((TimeTask) runnable).name).append(" | ");
                        }
                    }
                    logger.info("超时任务：" + result.toString());
                }
            } catch (InterruptedException e) {
                // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
                logger.info("awaitTermination interrupted: " + e);
                logger.info("关停线程池");
                mScheduleExecutorService.shutdownNow();
            }
        }
    }
    
    private void startRoomWaiterSchedule() {
        logger.info("开启查询候诊人数定时任务");
        if (mGetRoomWaiterTask == null) {
            mGetRoomWaiterTask = new GetRoomWaiterRunnable("get-room-waiter-task");
        }
        mRoomWaiterFuture = mScheduleExecutorService.scheduleAtFixedRate(mGetRoomWaiterTask, 0,
                6000L, TimeUnit.MILLISECONDS);
    }
    
    private final class TimeTask implements Runnable {
        public String name;
        private int count = 0;
        
        public TimeTask(String name) {
            this.name = name;
        }
        
        @Override
        public void run() {
            logger.info(Thread.currentThread().getName() + " [" + name + "] is Running !");
            try {
                count++;
                if (count == 3) {
                    logger.info("取消RoomWaiterTask");
                    cancelRoomWaiterTask();
                }
                logger.info(System.currentTimeMillis() + "");
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                logger.info(Thread.currentThread().getName() + " -> ScheduleTime#" + count + " interrupted!");
            }
        }
    }
    
    private void startScheduleTime() {
        logger.info("开启时间沙漏");
        mScheduleExecutorService.scheduleAtFixedRate(new TimeTask("ScheduleTime"), 0, 2000L, TimeUnit.MILLISECONDS);
    }
    
    public static void main(String[] args) {
        ScheduleTest test = new ScheduleTest();
        test.startRoomWaiterSchedule();
        test.startScheduleTime();
    }
}
