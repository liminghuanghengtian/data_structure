package com.liminghuang.sort;

import java.util.Arrays;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.sort
 * Description: 选择排序
 * <p>原理：首先，从数组中找到最小的元素，将他和数组第一个元素交换位置；其次，再从剩下的元素中找到最小的元素，将他和数组第二个元素交换位置。如此往复，直到排序完成。选择排序：不断地选择剩余元素中的最小者</p>
 * 大致过程：
 * <p>1. 选择一个值array[0]作为标杆，然后循环找到除这个值外最小的值（查找小于标杆的最小值），交换这两个值，这时最小值就被放到了array[0]上;</p>
 * <p>2. 然后再将array[1]作为标杆，从剩下未排序的值中找到最小值，并交换这两个值。</p>
 * <p>
 * CreateTime: 2017/8/28 22:13
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/28 22:13
 * Comment:
 *
 * @author Adaministrator
 */
public class SelectSort {
    
    public static void main1(String[] args) {
        int unsorted[] = {90, 69, 75, 100, 67, 89, 99, 87};
        for (int i = 0; i < unsorted.length; i++) {// 标识第几趟排序
            int index = i;// 标杆
            for (int j = i + 1; j < unsorted.length; j++) {//遍历值和标杆比较大小
                if (unsorted[index] > unsorted[j]) {
                    index = j;// 临时改变标杆指向，永远指向一趟排序中的最小值
                }
            }
            
            // 一趟排序比较结束后再交换位置
            if (i != index)
                swap(unsorted, i, index);
            System.out.println("第" + i + "趟： " + Arrays.toString(unsorted));
        }
        System.out.println(Arrays.toString(unsorted));
    }
    
    /**
     * 1. 从一堆数中选出最小的一个数与第一个位置的数交换
     * 2. 然后在剩下的数中再找最小的与第二个位置的数交换
     * 3. 依次类推
     *
     * @param args
     */
    public static void main(String[] args) {
        int unsorted[] = {90, 69, 75, 100, 67, 89, 99, 87};
        for (int i = 0; i < unsorted.length; i++) {// i是标杆位置
            int minIndexGuard = i;// 最小值哨兵
            for (int j = i + 1; j < unsorted.length; j++) {// 遍历查找最小值
                if (unsorted[minIndexGuard] > unsorted[j]) {
                    minIndexGuard = j;// 重置最小值哨兵
                }
            }
            
            if (i != minIndexGuard) {
                swap(unsorted, i, minIndexGuard);
            }
        }
        System.out.println(Arrays.toString(unsorted));
        
        
        // for (int i = 0; i < unsorted.length; i++) {
        //     int minIndexGuard = i;// 哨兵minIndexGuard始终指向最小值的索引
        //     for (int j = i + 1; j < unsorted.length; j++) {
        //         // 从标杆i后方查找比标杆更小的数字
        //         if (unsorted[minIndexGuard] > unsorted[j]) {
        //             minIndexGuard = j;
        //         }
        //     }
        //
        //     if (i != minIndexGuard) {
        //         swap(unsorted, i, minIndexGuard);
        //     } else {
        //         System.out.println("标杆[" + i + "]处是最小值");
        //     }
        //     System.out.println("第" + i + "趟： " + Arrays.toString(unsorted));
        // }
        // System.out.println(Arrays.toString(unsorted));
    }
    
    private static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
}
