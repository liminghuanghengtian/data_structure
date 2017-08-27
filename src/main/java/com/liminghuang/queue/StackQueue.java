package com.liminghuang.queue;

import java.util.Stack;

/**
 * ProjectName: example
 * PackageName: com.liminghuang
 * Description: 基于两个栈结构实现队列基本功能
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
        originStack.push(e);
    }
    
    public Integer dequeue() {
        // 当倒叙栈空时，将原栈数据倒入到倒叙栈
        if (reversalStack.isEmpty()) {
            while (!originStack.empty()) {
                reversalStack.push(originStack.pop());
            }
        }
        System.out.println("reversalStack: " + reversalStack.toString());
        return reversalStack.pop();
    }
    
    public String toString() {
        return reversalStack.toString();
    }
    
    public boolean isEmpty() {
        return reversalStack.isEmpty();
    }
    
    public static void main(String[] args) {
        StackQueue stackQueue = new StackQueue();
        Integer[] array = {1, 2, 3, 4, 5, 0};
        for (Integer i : array) {
            stackQueue.enqueue(i);
        }
        
        System.out.println("dequeue e: " + stackQueue.dequeue());
        while (!stackQueue.isEmpty()) {
            System.out.println("dequeue e: " + stackQueue.dequeue());
        }
    }
}
