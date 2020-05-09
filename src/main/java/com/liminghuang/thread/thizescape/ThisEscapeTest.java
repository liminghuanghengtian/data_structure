package com.liminghuang.thread.thizescape;

import java.util.Observable;
import java.util.Observer;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.thread.thizescape
 * Description: {@see <a href="https://zhuanlan.zhihu.com/p/40987808">this引用逃逸</a>}
 * <p>this引用逃逸是一件很危险的事情，其他线程可能通过这个引用访问到“初始化了一半”的对象</p>
 * <p>final的可见性：被final修饰的字段在构造器中一旦初始化完成，并且构造器没有把"this"的引用传递出去（即this引用逃逸），那么其他线程就能看见final字段的值，无须同步就能被其他线程正确访问</p>
 * <p>
 * CreateTime: 2020/5/9 18:40
 * Modifier: Adaministrator
 * ModifyTime: 2020/5/9 18:40
 * Comment:
 *
 * @author Adaministrator
 */
public class ThisEscapeTest {
    private final int num;
    MyObservable observable;
    
    public ThisEscapeTest() {
        observable = new MyObservable();
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                observable.addObserver(new Observer() {
                    @Override
                    public void update(Observable o, Object arg) {
                        doSomething(o);
                    }
                });
                observable.update();
            }
        });
        t.start();
        
        // 模拟this引用逃逸的线程先运行
        // try {
        //     t.join();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num = 10;
    }
    
    private void doSomething(Observable e) {
        if (num != 10) {
            System.out.println("Race condition detected");
        }
    }
    
    public static void main(String[] args) {
        new ThisEscapeTest();
    }
    
    private static final class MyObservable extends Observable {
        public void update() {
            this.setChanged();
            this.notifyObservers();
        }
    }
}
