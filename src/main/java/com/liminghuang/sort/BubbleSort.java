package com.liminghuang.sort;

import java.util.Arrays;

/**
 * ProjectName: example
 * PackageName: com.liminghuang
 * Description: 冒泡排序，从小到大
 * <p>
 * CreateTime: 2017/8/8 20:07
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/8 20:07
 * Comment:
 *
 * @author Adaministrator
 */
public class BubbleSort {
    public static void main(String[] args) {
        int unsorted[] = {90, 69, 75, 100, 67, 89, 99, 87};
        // 这是选择排序吗？
        // for (int i = 0; i < score.length; i++) {
        //     for (int j = i + 1; j < score.length; j++) {
        //         if (score[i] > score[j]) {
        //             int temp = score[i];
        //             score[i] = score[j];
        //             score[j] = temp;
        //         }
        //     }
        // }
        int temp;
        for (int i = 0; i < unsorted.length; i++) {
            for (int j = 0; j < unsorted.length - i - 1; j++) {
                if (unsorted[j] > unsorted[j + 1]) {
                    temp = unsorted[j + 1];
                    unsorted[j + 1] = unsorted[j];
                    unsorted[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(unsorted));
    }
}
