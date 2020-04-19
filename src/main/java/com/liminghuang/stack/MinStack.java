package com.liminghuang.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * ProjectName: example
 * PackageName: com.liminghuang
 * Description: 两个栈实现可以获取最小值的栈结构
 * <p>
 * CreateTime: 2017/8/15 21:48
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/15 21:48
 * Comment:
 *
 * @author Adaministrator
 */
public class MinStack {
    
    private Stack<Integer> originStack;// 正常存放所有数据的栈结构
    private Stack<Integer> minValueStack;// 临时存放最小值的栈结构
    
    protected MinStack() {
        originStack = new Stack<Integer>();
        minValueStack = new Stack<Integer>();
    }
    
    public Integer pop() {
        Integer ele = originStack.pop();
        if (ele.equals(minValueStack.peek())) {
            minValueStack.pop();
        }
        return ele;
    }
    
    public void push(Integer e) {
        originStack.push(e);
        if (minValueStack.isEmpty()) {// 空的时候直接存入
            minValueStack.push(e);
        } else {
            Integer ele = minValueStack.peek();//
            if (e < ele) {
                minValueStack.pop();
                minValueStack.push(e);
            }
        }
        System.out.println(Arrays.toString(minValueStack.toArray()));
    }
    
    public Integer getMin() {
        return minValueStack.peek();
    }
    
    public Integer peek() {
        return originStack.peek();
    }
    
    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        Integer[] array = {6, 1, 2, 3, 4, 5, 0};
        for (Integer i : array) {
            minStack.push(i);
        }
        
        System.out.println("当前栈顶元素：" + minStack.peek());
        System.out.println("当前最小值：" + minStack.getMin());
        
    }
}
