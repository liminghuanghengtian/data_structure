package com.liminghuang.sort;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.sort
 * Description: 快速排序
 * 原理：
 * <p>{@link com.liminghuang.search.BinarySearch#quickSort(int[])}</p>
 * <p>
 * CreateTime: 2020/4/19 11:28
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/19 11:28
 * Comment:
 *
 * @author Adaministrator
 */
public class QuickSort {
    public static void main(String[] args) {
        int score[] = {90, 69, 72, 76, 75, 88, 100, 74, 85, 67, 89, 99, 91, 96, 99, 87};
        int p = partition(score, 0, score.length - 1);
        System.out.println("p: " + p);
        
    }
    
    // 切分值查找
    private static int partition(int[] array, int beg, int end) {
        int last = array[end];
        System.out.println("last: " + last);
        int i = beg - 1;
        System.out.println("i: " + i + "\n");
        
        for (int j = beg; j <= end - 1; j++) {
            if (array[j] <= last) {// 数组前面所有元素和末端元素比较大小
                i++;// 满足条件i++之后就会跟上j
                System.out.println("i: " + i + " ,j: " + j);
                System.out.println("a[" + i + "]=" + array[i] + ", a[" + j + "]=" + array[j]);
                if (i != j) {
                    System.out.println("swap a[" + i + "] and a[" + j + "]\n");
                    swap(array, i, j);
                }
            }
        }
        
        if ((i + 1) != end) {
            swap(array, i + 1, end);
        }
        return i + 1;
    }
    
    private static void swap(int[] array, int j, int k) {
        array[j] = array[j] ^ array[k];
        array[k] = array[j] ^ array[k];
        array[j] = array[j] ^ array[k];
    }
}
