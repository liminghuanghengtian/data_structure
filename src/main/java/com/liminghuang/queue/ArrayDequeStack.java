package com.liminghuang.queue;

import java.util.ArrayDeque;
import java.util.function.Consumer;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.queue
 * Description:
 * <p>
 * CreateTime: 2020/4/18 17:10
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/18 17:10
 * Comment:
 *
 * @author Adaministrator
 */
public class ArrayDequeStack {
    private ArrayDeque<Object> mStack = new ArrayDeque<Object>();
    
    public void push(Object obj) {
        mStack.push(obj);
        // mStack.addFirst(obj);
        
        // 以下三个方法是一致的，通过addLast实现
        // mStack.offer(obj);
        // mStack.offerLast(obj);
        // mStack.addLast(obj);
    }
    
    public void pop() {
        mStack.pop();
        // mStack.removeFirst();
    }
    
    public void print() {
        System.out.println("----start----");
        mStack.forEach(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                System.out.println(o);
            }
        });
        System.out.println("----end----\n");
    }
    
    public static void main(String[] args) {
        ArrayDequeStack stack = new ArrayDequeStack();
        stack.push("1");
        stack.push(2);
        stack.push("3");
        stack.push(4);
        stack.print();
        
        stack.pop();
        stack.print();
        
        stack.push("5");
        stack.print();
        
        for (int i = 6; i < 20; i++) {
            stack.push(String.valueOf(i));
        }
        stack.print();
    }
}
