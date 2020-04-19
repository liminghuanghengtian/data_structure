package com.liminghuang.queue;

import java.util.Stack;

/**
 * ProjectName: example
 * PackageName: com.liminghuang
 * Description: 基于两个栈结构实现队列基本功能。
 * 原理分析：队列是先进先出，即从队尾入列，队头出列。采用两个栈的结构，互相倒腾，保证队头和队尾都能被访问到.
 * <p>
 * CreateTime: 2017/8/15 22:33
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/15 22:33
 * Comment:
 *
 * @author Adaministrator
 */
public class StackQueue {
    
    private Stack<Integer> originStack;
    private Stack<Integer> reversalStack;
    
    protected StackQueue() {
        originStack = new Stack<>();
        reversalStack = new Stack<>();
    }
    
    public void enqueue(Integer e) {
        // 1. 先把数据倒回来，暴露出队尾
        while (!reversalStack.isEmpty()) {
            originStack.push(reversalStack.pop());
        }
        // System.out.println("originStack: " + originStack.toString());
        originStack.push(e);
    }
    
    public Integer dequeue() {
        // 1. 先把数据倒出来，暴露出队头
        while (!originStack.empty()) {
            reversalStack.push(originStack.pop());
        }
        // System.out.println("reversalStack: " + reversalStack.toString());
        return reversalStack.pop();
    }
    
    public String toString() {
        while (!reversalStack.isEmpty()) {
            originStack.push(reversalStack.pop());
        }
        return originStack.toString();
    }
    
    public boolean isEmpty() {
        return reversalStack.isEmpty() && originStack.isEmpty();
    }
    
    public static void main(String[] args) {
        StackQueue stackQueue = new StackQueue();
        Integer[] array = {1, 2, 3, 4, 5, 0};
        for (Integer i : array) {
            stackQueue.enqueue(i);
        }
        System.out.println("queue: " + stackQueue.toString());
        
        System.out.println("dequeue e: " + stackQueue.dequeue());
        System.out.println("queue: " + stackQueue.toString());
        
        stackQueue.enqueue(6);
        stackQueue.enqueue(7);
        System.out.println("queue: " + stackQueue.toString());
        
        System.out.println("dequeue e: " + stackQueue.dequeue());
        System.out.println("queue: " + stackQueue.toString());
    }
}
