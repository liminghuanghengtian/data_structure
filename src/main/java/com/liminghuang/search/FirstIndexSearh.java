package com.liminghuang.search;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.search
 * Description:
 * <p>
 * CreateTime: 2017/8/9 21:29
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/9 21:29
 * Comment:
 *
 * @author Adaministrator
 */
public class FirstIndexSearh {
    
    public static void main(String[] args) {
        String target = "abecdebadecfgbhd";
        List<Character> repeted = new ArrayList<Character>();
        Stack<Character> charStack = new Stack<Character>();
        char[] targetChar = target.toCharArray();
        System.out.println(targetChar);
        for (char temp : targetChar) {
            if (charStack.contains(temp)) {
                if (!repeted.contains(temp)) {
                    repeted.add(temp);
                }
                
                int index = charStack.indexOf(temp);
                System.out.println("字符：" + temp + " 已存在，索引：" + index);
                
                charStack.remove(index);
            } else if (repeted.contains(temp)) {
                System.out.println("字符：" + temp + " 重复出现多次");
            } else {
                charStack.push(temp);
                System.out.println("push: " + temp);
            }
        }
        System.out.println(Arrays.toString(charStack.toArray()));
    }
}
