package com.liminghuang.sort;

import java.util.Arrays;

/**
 * ProjectName: example
 * PackageName: com.liminghuang
 * Description: 冒泡排序-冒泡增序
 * 原理：
 * <p>从数组的第一个位置开始两两比较array[index]和array[index+1]，
 * 如果array[index]大于array[index+1]则交换array[index]和array[index+1]的位置，止到数组结束；
 * </p>
 * <p>
 * 从数组的第一个位置开始，重复上面的动作，止到数组长度减一个位置结束；
 * <p>
 * 从数组的第一个位置开始，重复上面的动作，止到数组长度减二个位置结束；
 * <p>
 * CreateTime: 2017/8/8 20:07
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/8 20:07
 * Comment:
 *
 * @author Adaministrator
 */
public class BubbleSort {
    public static void main1(String[] args) {
        int unsorted[] = {90, 69, 75, 100, 67, 89, 99, 87};
        for (int i = 0; i < unsorted.length; i++) {// 标识第几趟排序，上限用N是有问题的，会多比较一次
            for (int j = 0; j < unsorted.length - i - 1; j++) {// 每次都从第一项开始，紧挨着的两两比较
                if (unsorted[j] > unsorted[j + 1]) {
                    swap(unsorted, j, j + 1);
                }
            }
            System.out.println("第" + i + "趟： " + Arrays.toString(unsorted));
        }
        System.out.println(Arrays.toString(unsorted));
    }
    
    
    public static void main(String[] args) {
        int unsorted[] = {90, 69, 99, 75, 100, 67, 89, 87};
        int N = unsorted.length;
        System.out.println("数据总数： " + N);
        for (int i = 0; i < N - 1; i++) {// N个数据，比较的轮次是N-1
            boolean isSwap = false;
            for (int j = 0; j < (N - i) - 1; j++) {// (N-i)个数据，一轮比较下来，比较的次数是(N-i)-1
                if (unsorted[j] > unsorted[j + 1]) {
                    swap(unsorted, j, j + 1);
                    isSwap = true;
                }
            }
            
            // 已经不再交换数据，说明已经有序
            if (!isSwap) {
                break;
            }
            System.out.println("第" + (i + 1) + "趟： " + Arrays.toString(unsorted));
        }
        System.out.println(Arrays.toString(unsorted));
    }
    
    
    private static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
}
