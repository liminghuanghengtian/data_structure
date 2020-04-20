package com.liminghuang.heap;

import java.util.Arrays;
import java.util.Comparator;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.heap
 * Description: 小根堆
 * <p>
 * CreateTime: 2020/4/20 19:57
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/20 19:57
 * Comment:
 *
 * @author Adaministrator
 */
public class Heap {
    private static class PriorityHeap<T> {
        private Object[] queue;
        private Comparator<? super T> cmp;
        int size;
        
        PriorityHeap(Comparator<? super T> comparator) {
            queue = new Object[11];
            cmp = comparator;
        }
        
        public void put(T t) {
            siftUpUsingComparator(size, t, queue, cmp);
            size++;
        }
        
        public T take() {
            int n = size - 1;
            if (n < 0)
                return null;
            
            // 因为是小根堆，所以从头部获取最小值
            T result = (T) queue[0];
            T x = (T) queue[n];
            queue[n] = null;
            
            siftDownUsingComparator(0, x, queue, n, cmp);
            size = n;
            return result;
        }
        
        private static <T> void siftDownUsingComparator(int k, T x, Object[] array,
                                                        int n,
                                                        Comparator<? super T> cmp) {
            // TODO: 2020/4/20 待分析
            if (n > 0) {
                // 尾节点的中间节点
                int half = n >>> 1;
                while (k < half) {
                    // 查找k的子节点的右节点
                    int child = (k << 1) + 1;
                    Object c = array[child];
                    int right = child + 1;
                    if (right < n && cmp.compare((T) c, (T) array[right]) > 0)
                        c = array[child = right];
                    if (cmp.compare(x, (T) c) <= 0)
                        break;
                    array[k] = c;
                    k = child;
                }
                array[k] = x;
            }
        }
        
        private static <T> void siftUpUsingComparator(int n, T e, Object[] array, Comparator<? super T> cmp) {
            if (cmp != null) {
                int k = n;
                while (k > 0) {
                    int parent = (k - 1) >>> 1;
                    
                    // 因为是小根堆，所以大值在最后
                    if (cmp.compare(e, (T) array[parent]) >= 0) {
                        break;
                    }
                    
                    // e小于parent节点，将parent交换下来
                    array[k] = array[parent];
                    k = parent;
                }
                array[k] = e;
            }
        }
        
        public void print() {
            System.out.println(Arrays.toString(queue));
        }
    }
    
    
    public static void main(String[] args) {
        PriorityHeap<Integer> priority = new PriorityHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        priority.put(280);
        priority.print();
        
        priority.put(15);
        priority.print();
        
        priority.put(24);
        priority.print();
        
        priority.put(19);
        priority.print();
        
        priority.put(20);
        priority.print();
        
        priority.put(12);
        priority.print();
        
        priority.put(3);
        priority.print();
        
        priority.put(8);
        priority.print();
    }
}
