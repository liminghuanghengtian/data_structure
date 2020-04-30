package com.liminghuang.leetcode;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ProjectName: example
 * PackageName: com.liminghuang.leetcode
 * Description: @see <a href="https://leetcode-cn.com/problems/first-unique-character-in-a-string/">387.
 * 字符串中的第一个唯一字符</a>
 * <p>
 * CreateTime: 2020/4/30 15:59
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/30 15:59
 * Comment:
 *
 * @author Adaministrator
 */
public class FirstUnrepeatChar {
    public static void main(String[] args) {
        System.out.println(new Solution().firstUniqChar("leetcode"));
    }
    
    static class Solution {
        public int firstUniqChar(String s) {
            if (s == null || s.isEmpty()) {
                return -1;
            }
            
            int n = s.length();
            // 此处必须使用LinkedHashMap而不是HashMap，遍历时需要有序查找
            Map<Character, Integer> map = new LinkedHashMap<>(n);
            for (int i = 0; i < n; i++) {
                char t;
                // 字符已存在
                if (map.containsKey(t = s.charAt(i))) {
                    map.put(t, -1);// -1表示已重复
                } else {
                    map.put(t, i);
                }
            }
            Iterator<Map.Entry<Character, Integer>> iterator = map.entrySet().iterator();
            // 获取第一个，遍历的话可以获取后续第N个
            while (iterator.hasNext()) {
                int index;
                if ((index = iterator.next().getValue()) >= 0) {
                    return index;
                }
            }
            
            return -1;
        }
    }
}
