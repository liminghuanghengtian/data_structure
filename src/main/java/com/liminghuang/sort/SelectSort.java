package com.liminghuang.sort;

import java.util.Arrays;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.sort
 * Description: 选择排序
 * 原理：选择一个值array[0]作为标杆，然后循环找到除这个值外最小的值（查找小于标杆的最小值），交换这两个值，这时最小值就被放到了array[0]上，然后再将array[1]作为标杆，从剩下未排序的值中找到最小值，并交换这两个值。
 * <p>
 * CreateTime: 2017/8/28 22:13
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/28 22:13
 * Comment:
 *
 * @author Adaministrator
 */
public class SelectSort {
    
    public static void main(String[] args) {
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
    
    private static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
}
