package com.liminghuang.queue;

import java.util.Stack;

/**
 * ProjectName: example
 * PackageName: com.liminghuang
 * Description: 两个栈实现队列的数据结构
 * <p>
 * CreateTime: 2017/8/15 22:33
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/15 22:33
 * Comment:
 *
 * @author Adaministrator
 */
public class StackQueue {
    
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;
    
    protected StackQueue() {
        stack1 = new Stack<Integer>();
        stack2 = new Stack<Integer>();
    }
    
    public void enqueue(Integer e) {
        stack1.push(e);
    }
    
    public Integer dequeue() {
        if (stack2.isEmpty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }
        System.out.println("stack2: " + stack2.toString());
        return stack2.pop();
    }
    
    public String toString() {
        return stack2.toString();
    }
    
    public boolean isEmpty() {
        return stack2.isEmpty();
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
