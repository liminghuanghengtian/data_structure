package com.liminghuang.arithmetic;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.search
 * Description: 从一串字符串中找出第一个不重复的字符
 * <p>
 * CreateTime: 2017/8/9 21:29
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/9 21:29
 * Comment:
 *
 * @author Adaministrator
 */
public class FirstUnRepeatIndexSearch {
    
    public static void main(String[] args) {
        String target = "abecdebadecfgbhd";
        char[] targetChar = target.toCharArray();
        System.out.println(targetChar);
        
        
        // 存放重复的字符的数组
        List<Character> repeted = new ArrayList<>();
        // 存放不重复的字符
        Stack<Character> charStack = new Stack<>();
        
        for (char temp : targetChar) {
            if (charStack.contains(temp)) {// Vector#contains，同步
                if (!repeted.contains(temp)) {// ArrayList#contains，非同步，与Vector内部实现一直
                    repeted.add(temp);
                }
                
                // 出现重复的字符，则将重复出现的字符出栈
                int index = charStack.indexOf(temp);
                System.out.println("字符：" + temp + " 已存在，索引：" + index);
                charStack.remove(index);
            } else if (repeted.contains(temp)) {
                System.out.println("字符：" + temp + " 重复出现多次");
            } else {// 未重复则入栈
                charStack.push(temp);
                System.out.println("push: " + temp);
            }
        }
        System.out.println(Arrays.toString(charStack.toArray()));
    }
}