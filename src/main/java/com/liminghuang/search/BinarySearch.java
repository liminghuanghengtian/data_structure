package com.liminghuang.search;

/**
 * ProjectName: example
 * PackageName: com.liminghuang
 * Description: 有序数组的二分查找
 * <p>
 * CreateTime: 2017/8/8 20:19
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/8 20:19
 * Comment:
 *
 * @author Adaministrator
 */
public class BinarySearch {
    
    public static void main(String[] args) {
        int score[] = {90, 69, 72, 76, 75, 100, 88, 74, 85, 67, 89, 99, 91, 96, 99, 87};
        quickSort(score);
        int index = binarySearch(score, 85, score.length / 2);
        System.out.println("searched index： " + index);
    }
    
    public static int binarySearch(int[] array, int target, int index) {
        int start = 0, end = array.length - 1;
        if (array[index] == target) {
            return index;
        } else if (array[index] < target) {
            start = index;
        } else {
            end = index;
        }
        if (start == end) {
            return start;
        }
        int newIndex = (start + end) / 2;
        return binarySearch(array, target, newIndex);
    }
    
    public static void quickSort(int[] array) {
        if (array != null) {
            quickSort(array, 0, array.length - 1);
        }
    }
    
    private static void quickSort(int[] array, int beg, int end) {
        if (beg >= end || array == null)
            return;
        // 分治思想
        int p = partition(array, beg, end);
        quickSort(array, beg, p - 1);
        quickSort(array, p + 1, end);
    }
    
    private static int partition(int[] array, int beg, int end) {
        int last = array[end];// 末端元素
        int i = beg - 1;
        
        // 从beg到end-1遍历
        for (int j = beg; j <= end - 1; j++) {
            if (array[j] <= last) {// 数组前面所有元素和末端元素比较大小
                i++;// 满足条件i++之后就会跟上j
                if (i != j) {
                    array[i] = array[i] ^ array[j];
                    array[j] = array[i] ^ array[j];
                    array[i] = array[i] ^ array[j];
                }
            }
        }
        
        if ((i + 1) != end) {
            array[i + 1] = array[i + 1] ^ array[end];
            array[end] = array[i + 1] ^ array[end];
            array[i + 1] = array[i + 1] ^ array[end];
        }
        return i + 1;
    }
    
    private static void swap(int[] array, int j, int k) {
        array[j] = array[j] ^ array[k];
        array[k] = array[j] ^ array[k];
        array[j] = array[j] ^ array[k];
    }
}
