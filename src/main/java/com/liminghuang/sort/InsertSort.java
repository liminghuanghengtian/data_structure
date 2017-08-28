package com.liminghuang.sort;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.sort
 * Description: 原理：插入排序的思想是数组是部门有序的，然后将无序的部分循环插入到已有序的序列中
 * <p>
 * CreateTime: 2017/8/28 22:29
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/28 22:29
 * Comment:
 *
 * @author Adaministrator
 */
public class InsertSort {
    public static void main(String[] args) {
        int unsorted[] = {90, 69, 75, 100, 67, 89, 99, 87};
        
        for (int out = 1; out < unsorted.length; out++) {
            int temp = unsorted[out];//
            int in = out;
            //如果轮循值大于被标记值则往后移
            while (in > 0 && temp < unsorted[in - 1]) {
                unsorted[in] = unsorted[in - 1];
                in--;
            }
            //将被标记值插入最终移出的空位置
            unsorted[in] = temp;
        }
    }
}
