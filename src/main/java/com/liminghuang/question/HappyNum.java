package com.liminghuang.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.question
 * Description: @see <a href="https://leetcode-cn.com/problems/happy-number/">快乐数</a>
 * <p>
 * CreateTime: 2020/4/30 14:11
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/30 14:11
 * Comment:
 *
 * @author Adaministrator
 */
public class HappyNum {
    
    public static void main(String[] args) {
        int num = 116;
        System.out.print(split(num) ? num + "是快乐数" : num + "不是快乐数");
    }
    
    private static boolean split(int n) {
        if (n < 0) {
            return false;
        }
        
        Set<Integer> set = new HashSet<>();
        
        int quotient;// 商
        int remainder;// 余数
        int result;// 累加结果
        do {
            result = 0;
            
            // 获取各个位的数值
            while (n != 0) {
                quotient = n / 10;
                remainder = n % 10;// 余数即各位的值
                n = quotient;
                // 各个位平方累加
                result += remainder * remainder;
            }
            
            if (set.contains(result)) {
                return false;
            } else {
                set.add(result);
            }
            
            n = result;
        } while (result != 1);
        
        return true;
        
    }
}
