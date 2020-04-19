package com.liminghuang.sort;

import java.util.Arrays;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.sort
 * Description: 原理：插入排序的思想是数组是部分有序的，然后将无序的部分循环插入到已有序的序列中。
 * 原理：对于1到N-1之间的每一个i, 将a[i]与a[i-1]-a[0]中比它小的所有元素依次有序的交换。在索引i从左向右变化的过程中，它左侧的元素总是有序的，当i到达数组右端时排序就完成了。
 * <p>
 * CreateTime: 2017/8/28 22:29
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/28 22:29
 * Comment:
 *
 * @author Adaministrator
 */
public class InsertSort {
    
    public static void main1(String[] args) {
        /*[69, 90, 75, 100, 67, 89, 99, 87]
[69, 75, 90, 100, 67, 89, 99, 87]
[69, 75, 90, 100, 67, 89, 99, 87]
[67, 69, 75, 90, 100, 89, 99, 87]
[67, 69, 75, 89, 90, 100, 99, 87]
[67, 69, 75, 89, 90, 99, 100, 87]
[67, 69, 75, 87, 89, 90, 99, 100]*/
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
            System.out.println(Arrays.toString(unsorted));
        }
    }
    
    
    public static void main(String[] args) {
        /*[69, 90, 75, 100, 67, 89, 99, 87]
[69, 75, 90, 100, 67, 89, 99, 87]
[69, 75, 90, 100, 67, 89, 99, 87]
[67, 69, 75, 90, 100, 89, 99, 87]
[67, 69, 75, 89, 90, 100, 99, 87]
[67, 69, 75, 89, 90, 99, 100, 87]
[67, 69, 75, 87, 89, 90, 99, 100]*/
        int unsorted[] = {90, 69, 75, 100, 67, 89, 99, 87};
        //
        for (int out = 1; out < unsorted.length; out++) {
            int becompareElement = unsorted[out];
            // System.out.println("被比较的元素: " + becompareElement);
            
            // 从unsorted[in]往前遍历至unsorted[0]，互相比较，后元素小于前元素则交互位置；若从unsorted[0]开始，则需要数组移动
            for (int j = out; j > 0; j--) {
                // 后面元素小于前面元素
                if (unsorted[j] < unsorted[j - 1]) {
                    swap(unsorted, j, j - 1);
                    continue;
                }
                break;
            }
            
            System.out.println(Arrays.toString(unsorted));
        }
    }
    
    private static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
}
