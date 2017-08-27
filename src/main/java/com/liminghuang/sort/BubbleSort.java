package com.liminghuang.sort;

import java.util.Arrays;

/**
 * ProjectName: example
 * PackageName: com.liminghuang
 * Description:
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
        int score[] = {90, 69, 75, 100, 67, 89, 99, 87};
        for (int i = 0; i < score.length; i++) {
            for (int j = i + 1; j < score.length; j++) {
                if (score[i] > score[j]) {
                    int temp = score[i];
                    score[i] = score[j];
                    score[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(score));
    }
}
