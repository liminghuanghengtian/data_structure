package com.liminghuang.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.leetcode
 * Description: @see
 * <a href="https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/">无重复字符的最长子串</a>
 * <p>
 * CreateTime: 2020/4/30 16:59
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/30 16:59
 * Comment:
 *
 * @author Adaministrator
 */
public class LongestUnRepeatedSubStr {
    public static void main(String[] args) {
        // System.out.println(new Solution().standard("abcafegbb"));
        // System.out.println(new Solution().standard("abcabcbb"));
        // System.out.println(new Solution().standard("bbbbb"));
        // System.out.println(new Solution().standard("pwwkew"));
        // System.out.println(new Solution().standard("     "));
        // System.out.println(new Solution().standard("au"));
        System.out.println(new Solution().standard("abcdcefghc"));
    }
    
    static class Solution {
        public int standard(String s) {
            int n = s.length(), maxL = 0;
            Map<Character, Integer> map = new HashMap<>(); // current index of character
            // try to extend the range [head, tail]
            for (int tail = 0, head = 0; tail < n; tail++) {
                char c;
                if (map.containsKey(c = s.charAt(tail))) {
                    head = Math.max(map.get(c), head);
                }
                maxL = Math.max(maxL, tail - head + 1);
                map.put(c, tail + 1);
                
                // 优化
                if (n - head <= maxL) {
                    break;
                }
            }
            return maxL;
        }
        
        public int lengthOfLongestSubstring(String s) {
            int n = s.length();
            if (n == 1) {
                return 1;
            }
            
            int head = 0, tail = 0, maxL = 0;
            Map<Character, Integer> map = new HashMap<>();
            int t = tail;
            do {
                char c;
                if (!map.containsKey(c = s.charAt(t))) {
                    // 当前字符的索引存入
                    map.put(c, t);
                } else {// 出现重复字符
                    maxL = Math.max(maxL, tail - head);
                    // 注意：head从出现重复字符开始计算，这是一个优化
                    head = map.get(c) + 1;
                    // head += 1;
                }
                
                // 优化
                if ((n - head) + 1 <= maxL) {
                    break;
                }
                
                t = tail++;
            } while (t < n);
            
            
            return maxL;
        }
        
        /**
         * 提交leetcode的代码.
         * 缺点：效率太差
         *
         * @param s
         * @return
         */
        public int commit(String s) {
            int n = s.length();
            if (n == 1) {
                return 1;
            }
            
            // 标记最长的长度
            int maxLength = 0;
            Stack<Character> unrepeatedSubStr = new Stack<>();
            
            for (int i = 0; i < n; i++) {
                unrepeatedSubStr.clear();
                unrepeatedSubStr.push(s.charAt(i));
                
                for (int j = i + 1; j < n; j++) {
                    char c = s.charAt(j);
                    // 字符重复，一次字串搜索结束
                    if (unrepeatedSubStr.contains(c)) {
                        // 子串长度
                        int length = unrepeatedSubStr.size();
                        if (length > maxLength) {
                            maxLength = length;
                        }
                        break;
                    } else {
                        unrepeatedSubStr.push(c);
                        // 子串长度
                        int length = unrepeatedSubStr.size();
                        if (length > maxLength) {
                            maxLength = length;
                        }
                    }
                }
            }
            
            return maxLength;
        }
    }
}
